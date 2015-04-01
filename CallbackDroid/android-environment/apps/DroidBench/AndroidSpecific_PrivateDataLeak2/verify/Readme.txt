Step 1: compile app
	cmd-compile-baigd.sh

Step 2: Generate Hashtable for the views 
	run-generator.sh

Step 3: copy the generated code to Driver.java and recompile

Step 4: run app
	cmd-run-baigd.sh

Step 5: Compile TaintListener.java: 
	javac -classpath ~/workspace/jpf-core/build/jpf.jar:~/workspace/jpf-core/build/jpf-classes.jar TaintListener.java

Step 6: Run jpf: 
	java -Xmx1024m -ea -cp .:~/workspace/jpf-core/build/RunJPF.jar:~/workspace/jpf-core/build/jpf.jar gov.nasa.jpf.tool.RunJPF TaintListener-baigd.jpf
