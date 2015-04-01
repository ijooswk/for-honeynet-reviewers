__author__ = 'xuyn'
# -*- coding:utf-8 -*-
import os

apiInfo = []

# 得到全部由继承自api中的类的类的方法
def analysisExtendsFromApi(dir):
    smaliList = []                  #全部smali类文件
    lable = set()
    result = set()
    # 得到全部smali文件
    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            print "smali",nowdir+"/"+file
            smaliList.append(nowdir+"/"+file)

    return result

# 从反汇编的结果中读取 api信息
def readApiInfo(dir):
    apiresult = os.walk(dir)
    for (nowdir,sondir,files) in apiresult:
        for file in files:
            if ".javap" in file[-6:]:
                filedir = nowdir+"/"+file
                apifile = open(filedir)
                while 1:
                    line = apifile.readline()
                    if not line:
                        break
                    if ";" in line:
                        print "api",line

if __name__ == '__main__':
    file = "/home/xuyn/tmp/xuyntest/baksmaliout"
    apiReslutdir = "/home/xuyn/tmp/api"

    re1 = set()
    re2 = set()
    readApiInfo(apiReslutdir)
    #re1 = analysisExtendsFromApi(file)