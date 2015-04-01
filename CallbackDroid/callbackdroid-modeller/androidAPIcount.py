__author__ = 'xuyn'
# -*- coding:utf-8 -*-

import os

def androidAllAPI():
    dir = "/home/xuyn/tmp/api"

    re = 0
    re2 = 0
    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            classfile = nowdir+"/"+file
            if "javap" in classfile[-6:]:
                re += 1
                f = open(classfile)
                while 1:
                    line = f.readline()
                    if not line:
                        break
                    if ";" in line:
                            re2 += 1

    print "java 文件个数",re
    print "方法个数",re2

androidAllAPI()