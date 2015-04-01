# pair p(loc, ins) in map "rules" indicates instruction string "ins" is to be inserted to position "loc"
# code in this file generate this map

class rules(object):
    
    def __init__(self, smalitree):
        return
    
    def checkset(self, smalitree):
        for c in smalitree.classes:
            for m in c.methods:            
                print m.methodname
                for b in m.blocks:
                    print "[[block]]"
                    for i in b.insns:
                        print "\t%s" % i
                    print " [taint]:"
                    for t in b.taint:
                        print "  %s, %s" % (t.type, t.variable.value)
                    print " [clean]:"
                    for c in b.clean:
                        print "  %s, %s" % (c.type, c.variable.value)
                    print " [infect]:"
                    for i in b.infect:
                        print "  %s, %s--->%s" % (i.type, i.source.value, i.dest.value)
                        
        return