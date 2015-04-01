__author__ = 'xuyn'
# -*- coding:utf-8 -*-
import os,sys
from androguard.core import *
from androguard.core.androgen import *
from androguard.core.androconf import *
from androguard.core.bytecode import *
from androguard.core.bytecodes.jvm import *
from androguard.core.bytecodes.dvm import *
from androguard.core.bytecodes.apk import *

from androguard.core.analysis.analysis import *
from androguard.core.analysis.ganalysis import *
from androguard.core.analysis.risk import *

from androguard.decompiler.decompiler import *

from androguard.core import androconf


import androguard.decompiler
a = APK("/home/xuyn/tmp/app-debug.apk")
d = DalvikVMFormat( a.get_dex() )
dx = VMAnalysis( d )
gx = GVMAnalysis( dx, None )
d.set_vmanalysis( dx )
d.set_gvmanalysis( gx )

d.create_xref()
d.create_dref()

for current_method in d.get_methods():
  print current_method.get_name()