#CallbackDroid

##About

State-of-the-art static analysis framework (eg. `Amandroid`, `FlowDroid`) lacks a proper solution of dealing with callback APIs. CallbackDroid aims at building precise models of callback APIs. Static analysis of Android Framework give pairs of callbacks and registers (`EdgeMiner` already did this:) ), and dynamic execution via JPF tell us the precise behaviours(control flow and data flow) of these callback APIs.

##Prerequisites
- `JDK1.6`, `ant` for android JVM lib building
- `JavaPathFinder`(built from source) for dynamic execution of Android code.
- `Python2.7` for Callback Modeller to work. `pydot` for visualization of Callback Graph.(not necessary)
- `apktool` for repackaging original apk and attaching callback models.

##Modules
CallbackDroid consists of 4 modules.

###Android JVM Lib

Directory `android-environment` contains a model of android framework for JPF to executing Android App.

Original Android framework code is not executable on JVM because it contains Android-specific features. To make it runnable on JVM (thus analysiable by JPF), we make some modifications to framework code, including modelling Android views and simplifing Android Handler mechanism, etc.

###Callback API executor

CallbackAPI executor dynamically executes Micro Apps which invoke callback APIs via JPF. By monitoring the control flow and data flow behaviours it produces invariants for callback modelling.

###Callback Modeller

CallbackModeller models callback APIs. It works as follows:

1. Build a conservative callback graph. (According to static analysis result of Android Library)
2. Reduce the callback graph. (According to JPF execution results)
3. Convert the callback graph to code slices for existing static analysis framework using.

(undone)

###CallbackDroidAdapter

CallbackDroid Adapter takes a apk as input, repackages it and waves our callback model code into its code. Existing analysis framework can thus aware of callback API behaviour by analysing the repackaged apk.

##How to Use

###Offline modeling

Offline modeling just have to run once, and it produces a repository of callback model code.

1. In android-environment directory, build the android-jvm-lib with `ant`
2. In Callback API executor, execute the microcallback code. The control flow and data flow invariants during execution is outputed.
3. Execute the CallbackModeller, produce the callback model code.

###Online Analyzing

When analyzing a app, firstly repackage the app via CallbackDroidAdapter, then analyze the repackaged app with existing static analysis tool.
