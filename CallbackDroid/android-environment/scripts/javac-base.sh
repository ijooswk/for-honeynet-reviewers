mkdir -p out/base
export JAVA_HOME=/usr/lib/jvm/java-1.6.0-oracle-1.6.0.45 ; export "PATH=$JAVA_HOME/bin:$PATH"
javac -J-Xmx512M -target 1.5 -encoding ascii -bootclasspath out/libcore -classpath out/libcore:out/bouncycastle:out/external -encoding UTF-8 -g -extdirs "" -d out/base \@src-base.txt \@src-basegen.txt \@src-policy.txt \@src-mock.txt \@src-mockbase.txt
