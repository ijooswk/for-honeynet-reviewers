__author__ = 'xuyn'
# -*- coding:utf-8 -*-

import os

def unzipOneApk(filedir):
    if not os.path.exists(filedir+"_dir"):
        os.system("unzip "+filedir+" -d "+filedir+"_dir")

    dexdir = filedir +"_dir/classes.dex"
    if not os.path.exists(filedir+"/baksmaliout"):
        os.system("java -jar /home/xuyn/Develop/apptest/bin/baksmali-2.0.3.jar "+ dexdir+ " -o "+ filedir+"_dir/baksmaliout")

if __name__ == '__main__':
    unzipOneApk("/home/xuyn/Develop/apptest/apps/360MobileSafe_5.4.0.1045.apk")