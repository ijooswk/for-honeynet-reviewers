__author__ = 'xuyn'
# -*- coding:utf-8 -*-

import os

def javapAllAPI():
    dir = "/home/xuyn/tmp/api"

    walkreslut = os.walk(dir)
    for (nowdir,sondir,files) in walkreslut:
        for file in files:
            classfile = nowdir+"/"+file

            if "class" in classfile[-6:]:
                os.system( "javap "+ classfile + " > "+ classfile+".javap")

javapAllAPI()