#Callback api executor

##How to use

Step 1. Compile micro callback code with Android jvm library.

```shell
    mkdir bin
    sh cmd-compile-apicaller.sh
```

Step 2. Compile CallbackListener.java with JPF library.

```shell
    sh cmd-compile-callbacklistener.sh
```

Step 3. Run micro callback code via JPF

```shell
    sh cmd-run-jpf.sh
```

##Notes
Note 1: you should set JPF_HOME environment variable to the root of you jpf directory, eg:

```shell
    export JPF_HOME=/Users/yuanchun/project/workplace-eclipse/jpf
```

Note 2: your jpf-core jars should be built from source, DO NOT use the snapshots version.

Note 3: modify the classpaths of JPF and JDK to correct directory. i.e. take a look in *.sh and *.jpf and make the correction.

##Result
It should generate output like this:
```shell
====================================================== system under test
com.lynnlyc.apicaller.Driver.main()

====================================================== search started: 1/30/15 10:09 PM
hello world!
trigger entered: android.os.AsyncTask.execute([Ljava/lang/Object;)Landroid/os/AsyncTask;
arg0:com.lynnlyc.apicaller.AppAsyncTask@5cd
arg1:[Ljava.lang.Object;@5fc
callback entered: com.lynnlyc.apicaller.AppAsyncTask.onPreExecute()V
arg0:com.lynnlyc.apicaller.AppAsyncTask@5cd
	tag:android.os.AsyncTask.execute([Ljava/lang/Object;)Landroid/os/AsyncTask;-arg0
callback exited: com.lynnlyc.apicaller.AppAsyncTask.onPreExecute()V
ret: V
trigger exited: android.os.AsyncTask.execute([Ljava/lang/Object;)Landroid/os/AsyncTask;
ret: Landroid/os/AsyncTask;
	tag:com.lynnlyc.apicaller.AppAsyncTask.onPreExecute()V-arg0
callback entered: com.lynnlyc.apicaller.AppAsyncTask.doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
arg0:com.lynnlyc.apicaller.AppAsyncTask@5cd
	tag:com.lynnlyc.apicaller.AppAsyncTask.onPreExecute()V-arg0
arg1:[Ljava.lang.Object;@5fc
	tag:android.os.AsyncTask.execute([Ljava/lang/Object;)Landroid/os/AsyncTask;-arg1
callback exited: com.lynnlyc.apicaller.AppAsyncTask.doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
ret: Ljava/lang/Object;
Logging...PN 0 4 DroidPF: ==>sendToTarget()
callback entered: com.lynnlyc.apicaller.AppAsyncTask.onPostExecute(Ljava/lang/Object;)V
arg0:com.lynnlyc.apicaller.AppAsyncTask@5cd
	tag:com.lynnlyc.apicaller.AppAsyncTask.doInBackground([Ljava/lang/Object;)Ljava/lang/Object;-arg0
arg1:java.lang.Object@50e
	tag:com.lynnlyc.apicaller.AppAsyncTask.doInBackground([Ljava/lang/Object;)Ljava/lang/Object;-ret
java.lang.Object@44739
callback exited: com.lynnlyc.apicaller.AppAsyncTask.onPostExecute(Ljava/lang/Object;)V
ret: V
...
```
