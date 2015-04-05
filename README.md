# for-honeynet-reviewers
Here are some of my code samples related to the honeynet project.

# About

I applied for the Honeynet GSoC project: Online Android Sandbox. The projects listed in this repo are what I think are related to the project. This repo include two app static analysis projects, one dynamic analysis project, one web application with backend(controllers and models) and frontend(views), and one Android application project. I was (or am) the lead developer in these projects.

# Project 1. CallbackDroid

State-of-the-art static analysis framework (eg. `Amandroid`, `FlowDroid`) lacks a proper solution of dealing with callback APIs. CallbackDroid aims at building precise models of callback APIs. Static analysis of Android Framework give pairs of callbacks and registers, and dynamic execution via JPF tell us the precise behaviors (control flow and data flow) of these callback APIs. The detailed documents (in Markdown) are in corresponding directories (`CallbackDroid/*`).

## Language
C++, Java, Python

## Keywords
JPF dynamic analysis, Android JVM environment

# Project 2. Fan2Fan (f2f) Web Application

A web application with Model-View-Controller structure. We used Mysql database and our db scripts are in `Fan2fan/f2fbackend/db` directory. Models and controllers are implemented in `Fan2fan/f2fbackend/src/main` based on Spring framework, and simple testcases are implemented in `Fan2fan/f2fbackend/src/test`. `Fan2fan/f2fview/1/user` contains some simple html views， which can be viewed in browser locally.

## Language
Java, HTML, Sql

## Keywords
Model-View-Controller, Spring, jquery

# Project 3 & 4. DsfsSensorOpt & PMlight

These two projects make use of reverse engineering tools (`baksmali`, `apktool`, `Androguard`) to analyse and repackage apks. `DsfsSensorOpt` aims to optimise energy consumption of sensing apps by enabling them to scale sampling frequency adaptively. It repackages apps and redirects Android sensing API calls to modified sensing APIs. `PMlight` builds CFGs of apk methods and insert taint propagation code to each basic block to monitor infomation flows during execution.

## Language
Python, Smali(Dalvik IR)

## Keywords
Reverse engineering, Repackage, Control Flow Graph

# Project 5. gedeng-android

# Project 6. honeynet-socket-io
A coding task of the Honeynet project.

An Android application developed in Intellij IDE. I like this app and the latest version is downloadable in market. Maybe we can create an Android app of Online Sandbox Tool? Never mind.
