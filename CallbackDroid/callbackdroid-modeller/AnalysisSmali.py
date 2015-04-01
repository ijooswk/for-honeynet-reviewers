__author__ = 'xuyn'
# -*- coding:utf-8 -*-
import os
import sys
# 得到全部由继承自api中的类的类的方法
def analysisSmaliFile(dir):

    smaliList = []
    methodList = []
    infomation = []
    lable = set()
    # 得到全部smali文件
    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            #print nowdir+"/"+file
            smaliList.append(nowdir+"/"+file)
    #for file in smaliList:
    #    print file
    dic = {}
    callback = set()
    for sm in smaliList:
        smfile = open(sm)
        methodList = []
        while 1:
            line = smfile.readline()
            if not line:
                break
            line = line.strip(' ')
            if line.startswith("."):
                #print line
                i = line.find(" ")
                lable.add(line[0:i])

                if("super" in line[0:i]):
                    superclass = line[i+1:-1]
                elif("class" in line[0:i]):
                    thisclass = line[i+1:-1]
                elif( "method" in line[0:i] ):
                    methodList.append(line[i+1:-1])
        # print thisclass,superclass,methodList
        dic[thisclass] = (superclass, methodList)
        superdir = "/home/xuyn/tmp/api/" + superclass[1:-1]+".class.javap"
        #print superdir
        for method in methodList:
            split = method.find("(")
            split2 = method[:split].rfind(" ")
            name = ""
            if split > 0 and split2 > 0:
                 #print method[:split]
                 #print method[:split][split2+1:]
                 name = method[:split][split2+1:]
            #print "name is ",name
            if os.path.exists(superdir) and len(name.strip()) > 0:
                classfile = open(superdir)
                while 1:
                    methodline = classfile.readline()
                    if not methodline:
                        break
                    if " "+name+"(" in methodline:
                        #print methodline
                        methodline = methodline.strip()
                        s2 = methodline.rfind(" ")
                        #callbackmethod = superdir[19:-12] + ";->" + methodline[s2:].strip()
                        callbackmethod = superdir[19:-12] + ";->" + name
                        #s1 = callbackmethod.rfind("(")
                        #callbackmethod = callbackmethod[:s1]
                        #callbackmethod = callbackmethod.strip(")")
                        #print callbackmethod

                        if callbackmethod not in callback:
                            callback.add(callbackmethod)

    #print lable
    #for i in callback:
        #print i
    return callback
# 获取被显示调用的
def getinvoke(dir):
    invokemethod = set()
    smaliList = []
    # 得到全部smali文件
    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            #print nowdir+"/"+file
            smaliList.append(nowdir+"/"+file)

    for sm in smaliList:
        smfile = open(sm)
        methodList = []
        while 1:
            line = smfile.readline()
            if not line:
                break
            line = line.strip(' ')
            if line.startswith("invoke"):
                #print line
                s1 = line.find("}")
                #print line[s1+4:]
                s2 = line.rfind("(")
                #print line[s1+4:s2]
                if line[s1+4:s2] not in invokemethod:
                    invokemethod.add(line[s1+4:s2])
    return invokemethod

def getArg(methodList):
    for me in methodList:
        s1 = me.find(";")
        package = me[:s1]
        s2 = me.find(">")
        method = me[s2+1:]
        print package,method
        apifilepath = "/home/xuyn/tmp/api/" +package+".class.javap"
        apifile = open(apifilepath)
        while 1:
            line = apifile.readline()
            if not line:
                break
            if " "+method+"(" in line:
                print "...." , line
#获取被用户重载的
def methodExtendsbyUser(dir):
    result = set()
    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            onesmalifile = nowdir+"/"+file
            print "smali",onesmalifile


    return result

def getOneApkCallbackMethod(dir):
    re1 = set()
    re2 = set()
    re1 = analysisSmaliFile(dir)       # 获取父类在SDK中的类中的方法+被用户重载的
    re2 = getinvoke(dir)               # 被显示调用的
    re = re1 - re2
    resultList = []
    for i in re:
        resultList.append(i)
    resultList.sort()

    return resultList
def getOneApkCallbackMethodSet(dir):
    re1 = set()
    re2 = set()
    re1 = analysisSmaliFile(dir)       # 获取父类在SDK中的类中的方法+被用户重载的
    re2 = getinvoke(dir)               # 被显示调用的
    re = re1 - re2
    return re

if __name__ == '__main__':

    file = "/home/xuyn/tmp/xuyntest/baksmaliout"
    if len(sys.argv) == 2:
        file = sys.argv[1]
    getArg(getOneApkCallbackMethod(file))
