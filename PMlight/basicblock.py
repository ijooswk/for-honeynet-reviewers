sourcesmap = {
    "Landroid/content/ContentResolver;->query":                         "contact list",
    "Landroid/content/pm/PackageManager;->getInstalledApplications":    "installed apps",
    "Landroid/content/pm/PackageManager;->getInstalledPackages":        "installed apps",
    "Landroid/content/pm/PackageManager;->queryIntentActivities":       "installed apps",
    # TelephonyManager
    "Landroid/telephony/TelephonyManager;->getLine1Number":             "phone number",
    "Landroid/telephony/TelephonyManager;->getCellLocation":            "cell location",
    "Landroid/telephony/TelephonyManager;->getDeviceId":                "device id",
    # Location
    "Landroid/location/LocationManager;->getLastKnownLocation":         "last known location",
    
    # Wifi
    "Landroid/net/wifi/WifiManager;->getScanResults":                   "wifi ap list",
    "Landroid/net/wifi/WifiManager;->getConnectionInfo":                "wifi connection info"
    # reflect
    # Ljava/lang/reflect/Method;->invoke
}

methodidmap = {}

def analyzeBasicBlock(st, methodidmap_):
    methodidmap = methodidmap_
    for c in st.classes:
        for m in c.methods:
            for b in m.blocks:
                b.analyze(m.id)
    return

class Variable(object):
    
    def __init__(self, vtype, vvalue):
        # variable type:
        #    0--register
        #        value: <methodid>:<register name> eg: 2:v1
        #    1--static field
        #        value: f<field id> eg:f2
        #    2--instance field
        #        value: <variable value>.<field name> eg: 2:v1.field1
        #    3--method return value
        #        value: m<method id> eg:m3
        #    4--register cluster
        #        value: <register>;<register> eg: 2:v1;v2
        self.type = vtype
        self.value = vvalue

class propagation(object):
    
    def __init__(self, sourcevariable, destvariable, typee):
        self.source = sourcevariable
        self.dest = destvariable
        self.type = typee

class taintelement(object):
    def __init__(self, taintvariable, tainttype):
        self.variable = taintvariable
        self.type = tainttype

class cleanelement(object):
    def __init__(self, cleanvariable, cleantype):
        self.variable = cleanvariable
        self.type = cleantype
        
class BlockNode(object):
    
    def __init__(self):
        self.parents=[]
        self.children=[]
        self.taint=[]
        self.clean=[]
        self.infect=[]
        self.startline=0
        self.endline=0
        self.insns=[]
        
    def addinfect(self, sourcevariable, destvariable, type):
        flag = False
        # if source is tainted, then the dest is tainted too
        for t in self.taint:
            if t.variable.value == sourcevariable.value:
                self.taint.append(taintelement(destvariable, t.type))
                flag = True
                break
        
        if flag: return
        # if source is cleaned, then the dest is cleaned too
        for c in self.clean:
            if c.variable.value == sourcevariable.value:
                self.clean.append(cleanelement(destvariable, c.type))
                flag = True
                break
        
        if flag: return
        p = propagation(sourcevariable, destvariable, type)
        for t in self.taint:
            if t.variable.value == destvariable.value:
                self.taint.remove(t)
        for c in self.clean:
            if c.variable.value == destvariable.value:
                self.clean.remove(c)
        for i in self.infect:
            if i.dest.value == destvariable.value:
                self.infect.remove(i)
        self.infect.append(p)
    
    def addtaint(self, destvariable, type):
        for c in self.clean:
            if c.variable.value == destvariable.value:
                self.clean.remove(c)
        for p in self.infect:
            if p.dest.value == destvariable.value:
                self.infect.remove(p)
        for t in self.taint:
            if t.variable.value == destvariable.value:
                return
        self.taint.append(taintelement(destvariable, type))
    
    def addclean(self, destvariable, type):
        for c in self.clean:
            if c.variable.value == destvariable.value:
                return
        for p in self.infect:
            if p.dest.value == destvariable.value:
                self.infect.remove(p)
        for t in self.taint:
            if t.variable.value == destvariable.value:
                self.taint.remove(t)
        self.clean.append(cleanelement(destvariable, type))
        
    def analyze(self, methodid):
        previns = ""
        for ins in self.insns:
            if ins.startswith("move-result"):
                segs = ins.split()
                returnvalue = Variable(0, "%d:%s" % (methodid, segs[-1]))
                if previns.startswith("invoke"):
                    segs = previns.split()
                    segnum = len(segs)
                    methodname = segs[-1]
                    methoddesc = methodname[:(methodname.index("("))]
                    if methodname in methodidmap.keys():
                        destmethodid = methodidmap[methodname]
                        i = 0
                        while i < segnum - 2:
                            sourcevalue = segs[i+1][:-1]
                            if i == 0:
                                sourcevalue = sourcevalue[1:]
                            if i == segnum - 3:
                                sourcevalue = sourcevalue[:-1]
                            source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                            dest = Variable(0, "%d:p%d" % (destmethodid, i))
                            self.addinfect(source, dest, "invoke(argument)")
                            sourcemethod = Variable(1, "m%d" % destmethodid)
                            self.addinfect(sourcemethod, returnvalue, "return")
                    elif methoddesc in sourcesmap.keys():
                        tainttype = sourcesmap[methoddesc]
                        self.addtaint(returnvalue, tainttype)
                    else:
                        sources=""
                        i = 0
                        while i < segnum - 2:
                            sourcevalue = segs[i+1][:-1]
                            if i == 0:
                                sourcevalue = sourcevalue[1:]
                            if i == segnum - 3:
                                sourcevalue = sourcevalue[:-1]
                            sources += "~"
                            sources += sourcevalue
                            i += 1
                        source = Variable(4, "%d:%s" % (methodid, sources))
                        self.addinfect(source, returnvalue, "invoke(externel)")
                else:
                    print "warning: %s after %s" % (ins, previns)
                    continue
            elif ins.startswith("move-exception"):
                continue
            elif ins.startswith("move"):
                segs = ins.split()
                if len(segs) != 3:
                    continue
                destvalue = segs[1][:-1]
                sourcevalue = segs[2]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addinfect(source, dest, "move")
            elif ins.startswith("return"):
                segs = ins.split()
                if len(segs) != 2:
                    continue
                sourcevalue = segs[1]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(3, "m%d" % methodid)
                self.addinfect(source, dest, "return")
            elif ins.startswith("const"):
                segs = ins.split()
                destvalue = segs[1][:-1]
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addclean(dest, "const")
            elif ins.startswith("instance-of") or ins.startswith("array-length") or ins.startswith("new-instance") or ins.startswith("new-array")\
                or ins.startswith("cmp"):
                segs = ins.split()
                destvalue = segs[1][:-1]
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addclean(dest, "instance-of/array-len/new")
            elif ins.startswith("fill-new-array"):
                previns = ins
                continue
            elif ins.startswith("fill-array-data"):
                # not handled
                continue
            elif ins.startswith("aget"):
                segs = ins.split()
                if len(segs) != 4:
                    print "warning: aget ins with wrong operands: %s" % ins
                    continue
                destvalue = segs[1][:-1]
                sourcevalue = segs[2][:-1]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addinfect(source, dest, "aget")
            elif ins.startswith("aput"):
                segs = ins.split()
                if len(segs) != 4:
                    print "warning: aput ins with wrong operands: %s" % ins
                    continue
                sourcevalue = segs[1][:-1]
                destvalue = segs[2][:-1]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addinfect(source, dest, "aput")
            elif ins.startswith("iget"):
                segs = ins.split()
                if len(segs) != 4:
                    print "warning: iget ins with wrong operands: %s" % ins
                    continue
                destvalue = segs[1][:-1]
                sourcevalue = segs[2][:-1]
                source = Variable(0, "%d:%s.%s" % (methodid, sourcevalue, segs[3]))
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addinfect(source, dest, "iget")
            elif ins.startswith("aput"):
                segs = ins.split()
                if len(segs) != 4:
                    print "warning: iput ins with wrong operands: %s" % ins
                    continue
                sourcevalue = segs[1][:-1]
                destvalue = segs[2][:-1]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(0, "%d:%s.%s" % (methodid, destvalue, segs[3]))
                self.addinfect(source, dest, "iput")
            elif ins.startswith("sget"):
                segs = ins.split()
                if len(segs) != 3:
                    print "warning: sget ins with wrong operands: %s" % ins
                    continue
                destvalue = segs[1][:-1]
                sourcevalue = segs[2]
                source = Variable(1, "f%s" % sourcevalue)
                dest = Variable(0, "%d:%s" % (methodid, destvalue))
                self.addinfect(source, dest, "sget")
            elif ins.startswith("sput"):
                segs = ins.split()
                if len(segs) != 3:
                    print "warning: sput ins with wrong operands: %s" % ins
                    continue
                sourcevalue = segs[1][:-1]
                destvalue = segs[2]
                source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                dest = Variable(1, "f%s" % destvalue)
                self.addinfect(source, dest, "sput")
            elif ins.startswith("invoke"):
                previns = ins
                continue
            else:
                segs = ins.split()
                opname = segs[0]
                if opname.startswith("neg-") or opname.startswith("not-") or opname.find("-to-") != -1 \
                    or opname.find("/2addr") != -1 or opname.find("/lit") != -1:
                    destvalue = segs[1][:-1]
                    if len(segs) == 3:
                        sourcevalue = segs[2]
                    elif len(segs) == 4:
                        sourcevalue = segs[2][:-1]
                    else:
                        print "warning: %s has %d segs" % (ins, len(segs))
                        continue
                    source = Variable(0, "%d:%s" % (methodid, sourcevalue))
                    dest = Variable(0, "%d:%s" % (methodid, destvalue))
                    self.addinfect(source, dest, "2-ele-op")
                elif len(segs) == 4 and (opname.find("-int") != -1 or opname.find("-long") != -1 or opname.find("-float") != -1 or opname.find("-double") != -1):
                    destvalue = segs[1][:-1]
                    dest = Variable(0, "%d%s" % (methodid, destvalue))
                    sourcevalue = segs[2][:-1] + ";" + segs[3]
                    source = Variable(4, "%d%s" % (methodid, sourcevalue))
                    self.addinfect(source, dest, "3-ele-op")
                    