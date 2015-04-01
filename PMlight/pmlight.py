import sys
import os
import smali
import shutil
import argparse
import basicblock
import rulegenerator
from subprocess import call

# preparation
working_dir=sys.path[0]
apktool_path=os.path.join(working_dir, "lib", "apktool", "apktool.jar")
tempdir=os.path.join(working_dir, "temp")
originfolder=os.path.join(tempdir, "origin")
newfolder=os.path.join(tempdir, "new")
filedir=os.path.join(working_dir, "testfile")
if os.path.exists(tempdir):
    shutil.rmtree(tempdir)
os.makedirs(tempdir)
apkfile=os.path.join(filedir, "PrivacyLeaker.apk")
api_config=os.path.join(working_dir, "config", "sources")

parser = argparse.ArgumentParser(description=\
'Repackage apk to monitor privacy leakages.')
parser.add_argument('-o, --output', metavar='dirpath', type=str, nargs=1,
                    help='output directory', 
                    dest='output',
                    default=tempdir)
parser.add_argument('-f filename', type=str, nargs=1,
                    help='path of APK file',
                    dest='filename',
                    default=apkfile)
args = parser.parse_args()
apk_name = os.path.basename(args.filename)
root_name, ext = os.path.splitext(apk_name)
if ext != ".apk":
    print "error: not an APK file"
    sys.exit(2)
if args.output:
    outdir = args.output
else:
    outdir = tempdir
newapk=os.path.join(outdir, "new.apk")

# apk adapter --> baksmali
os.system("java -jar %s -q d -r -f %s %s" % (apktool_path, args.filename, originfolder))
if os.path.exists(newfolder):
    shutil.rmtree(newfolder)

# windows copy
# os.makedirs(newfolder)
# os.system ("xcopy /s %s %s" % (originfolder, newfolder))

# linux copy
os.system("cp -r %s %s" % (originfolder, newfolder))

# smali parser
print "building smali tree"
methodidmap = {}
st = smali.SmaliTree(originfolder)
index = 0
for c in st.classes:
    for m in c.methods:
        m.id = index
        m.methodname = "%s->%s;" % (c.name, m.descriptor)
        methodidmap[m.methodname] = index
        index += 1

#    generate basic block
print "generating basic blocks"
for c in st.classes:
    for m in c.methods:
        # print "\t%s->%s" % (c.name, m.name)
        m.genbb(st, methodidmap)

#    analyze basic blocks
print "generating taint sets, clean sets, and infect sets"
basicblock.analyzeBasicBlock(st, methodidmap)

#   rule generator
print "generating instrumentation rules"
rule = rulegenerator.rules(st)

# apk adapter --> smali
# under windows
# os.system("java -jar %s -q b -f %s %s" % (apktool_path, newfolder, newapk))

# under osx
apktool_script = os.path.join(working_dir, "lib", "apktool", "apktool")
os.system("%s -q b -f %s %s" % (apktool_script, newfolder, newapk))

print "signing apk"
os.system("jarsigner -verbose -keystore pmlight.keystore -signedjar %s %s pmlight.keystore" % (newapk, newapk))