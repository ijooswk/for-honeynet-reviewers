export JAVA_HOME=/usr/lib/jvm/java-1.6.0-oracle-1.6.0.45 ; export "PATH=$JAVA_HOME/bin:$PATH"
javac -J-Xmx512M -target 1.5 -encoding ascii -bootclasspath out/libcore -classpath out/libcore:out/bouncycastle:out/external:out/base:out/service -encoding UTF-8 -g -extdirs "" -d out/my -sourcepath my my/com/wyz/myandroid/MyHelloActivity.java
