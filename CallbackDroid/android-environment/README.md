Android Framework for JVM
=========================

About
-----

This is the Android framework library ported from Android/Dalvik to JVM.
It's mainly used for testing.

How to Compile?
---------------

Use `ant` to compile all. The output will be in `out/` directory.
JDK1.6 is recommended.

How to Run Android App?
-----------------------

First you need to translate (e.g. using `dare` or `dex2jar`) an Android app from Dalvik to Java bytecode.
Put the `out` directory in your classpath.
Write a entry class with `main` method to call functions in the Android app.

Example
-----------

Directory `apps/DroidBench/AndroidSpecific_PrivateDataLeak1/verify` contains a example of running PrivateDataLeak app with JPF.
