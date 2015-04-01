Step 1: compile app
	cmd-compile-baigd.sh

Step 2: Generate Hashtable for the views 
	run-generator.sh

Step 3: copy the generated code to Driver.java and recompile

Step 4: run app
	cmd-run-baigd.sh

Step 5: Compile TaintListener.java: 
	javac -classpath ~/workspace/jpf-core/build/jpf.jar:~/workspace/jpf-core/build/jpf-classes.jar TaintListener.java
	or
	cmd-compile-taintlistener.sh

Step 6: Run jpf: 
	java -Xmx1024m -ea -cp .:/home/baigd/workspace/jpf-core/build/RunJPF.jar:/home/baigd/workspace/jpf-core/build/jpf.jar gov.nasa.jpf.tool.RunJPF TaintListener-baigd.jpf
	or
	cmd-run-jpf.sh

note1: you should set JPF_HOME environment variable to the root of you jpf directory, eg:
	export JPF_HOME=/Users/yuanchun/project/workplace-eclipse/jpf
note2: your jpf-core jars should be built from source, DO NOT use the snapshots version.
note3: modify the classpaths of JPF and JDK to correct directory. i.e. take a look in *.sh and *.jpf and make the correction.
