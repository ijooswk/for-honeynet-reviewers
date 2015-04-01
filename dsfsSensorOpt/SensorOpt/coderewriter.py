import os
import shutil
def apply(newsmali, rules):
    rules = rule.rules
    # print rules
    totalline = 0
    if os.path.exists(newsmali):
        shutil.rmtree(newsmali)
    for (path, dirs, files) in os.walk(originsmali):
        pathrel = os.path.relpath(path, originsmali)
        newpath = os.path.join(newsmali, pathrel)
        if not os.path.exists(newpath):
            os.makedirs(newpath)
        for f in files:
            name = os.path.join(path, f)
            rel = os.path.relpath(name, originsmali)
            ext = os.path.splitext(name)[1]
            if ext != '.smali': continue
            outfile = os.path.join(newsmali, rel)
            fin = open(name, 'r')
            fout = open(outfile, 'w')
            line = fin.readline()
            segs = line.split()
            classname = segs[-1]
            # print "rewriting class: %s" % classname
            methodname = ""
            linecount = 0
            while line:
                if not line.strip():
                    line = fin.readline()
                    continue
                if line.startswith(".method "):
                    segs = line.split()
                    methodname = segs[-1]
                    # print "rewriting method: %s" % methodname
                    linecount = 0
                
                if line.split()[0] == ".locals" and methodregmap.has_key(classname) and methodregmap[classname].has_key(methodname):
                    regnum = methodregmap[classname][methodname]
                    line = "\t.locals %d\n" % regnum
                    
                if methodname != "" and rules.has_key(classname) and rules[classname].has_key(methodname) and rules[classname][methodname].has_key(linecount):
                    # print "inserting..."
                    extralines = rules[classname][methodname][linecount]
                    fout.write(extralines)
                        
                fout.write(line)
                totalline += 1
                linecount += 1
                line = fin.readline()
            fin.close()
            fout.close()
    print "inserted %d/%d lines" % (rule.insertedline, totalline)