import sys
import os
import shutil
import argparse
import datetime

import coderewriter
from androguard.core.bytecodes import apk


starttime = datetime.datetime.now()
# preparation
working_dir=sys.path[0]
apktool_path=os.path.join(working_dir, "lib", "apktool", "apktool.jar")
tempdir=os.path.join(working_dir, "temp")
originfolder=os.path.join(tempdir, "origin")
originsmali=os.path.join(originfolder, "smali")
newfolder=os.path.join(tempdir, "new")
newsmali=os.path.join(newfolder, "smali")
filedir=os.path.join(working_dir, "apksamples")
if os.path.exists(tempdir):
    shutil.rmtree(tempdir)
os.makedirs(tempdir)
apkfile=os.path.join(filedir, "googlemap2.apk")
api_config=os.path.join(working_dir, "config", "sensingAPI")

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
# os.system ("xcopy /s %s %s /Q" % (originfolder, newfolder))

# linux copy
os.system("cp -r %s %s" % (originfolder, newfolder))


#    bytecode rewriter
print "smali code instrumenting"

ruleconfig = os.path.join(newsmali, "config", "rewrite.rules")
rule = open(ruleconfig)
coderewriter.apply(originsmali, newsmali, rule)

#    move Util library to new smali directory
pmlightutil = os.path.join(working_dir, "instru")
newsmaliutil = os.path.join(newsmali, "instru")
# windows copy
# os.makedirs(newsmaliutil)
# os.system ("xcopy /s %s %s /Q" % (pmlightutil, newsmaliutil))

# linux copy
os.system("cp -r %s %s" % (pmlightutil, newsmaliutil))

# apk adapter --> smali
os.system("java -jar %s -q b -f %s %s" % (apktool_path, newfolder, newapk))

print "signing apk"
# os.system("jarsigner -verbose -keystore pmlight.keystore -signedjar %s %s pmlight.keystore" % (newapk, newapk))
cert_path = os.path.join(working_dir, "config", "apkil.cert")
apk.sign_apk(newapk, cert_path, "apkil", "apkilapkil" )

# interval calculate
endtime = datetime.datetime.now()
interval = (endtime - starttime).seconds
print "done in %d seconds!" % interval