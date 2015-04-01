__author__ = 'xuyn'
# -*- coding:utf-8 -*-
import AnalysisSmali
import APKbreaker
import sys
import os


if __name__ == '__main__':
    apkrepo = "/home/xuyn/Develop/apptest/apps"
    apkrepodir = os.listdir(apkrepo)
    re = set()
    for file in apkrepodir:
        apkfile = apkrepo+"/"+file
        if not os.path.exists(apkfile+"_dir"):
            APKbreaker.unzipOneApk(apkfile)
        smaliOutFile = apkfile+"_dir"+"/baksmaliout"
        re = re | AnalysisSmali.getOneApkCallbackMethodSet(smaliOutFile)
    resultList = []
    for i in re:
        resultList.append(i)
    resultList.sort()
    AnalysisSmali.getArg(resultList)