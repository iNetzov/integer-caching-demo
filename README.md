1) GIT CLONE  XXXX

2) **MVN CLEAN PACKAGE**

3) ADD THOUSE JVM ARG >>>  -XX:AutoBoxCacheMax=127 -XX:StartFlightRecording=name=gcTest,filename=recording.jfr -Xlog:gc+heap=trace:file=gc.log

4) Start the Jar with the JVM Using the arguments

4) Check how much lower the memory foot print is when usking cache + how with big load there is a segnificent performance over non-cached values


REQUIRED TECHNOLOGIES TO RUN:
**JDK 25, MAVEN**

RESPONSE IN MY CASE :
Benchmark                             (value)  Mode  Cnt        Score   Error  Units
IntegerCacheBenchmark.cached_valueOf       10  avgt             0.633          ns/op
IntegerCacheBenchmark.cached_valueOf    10000  avgt             1.098          ns/op
IntegerCacheBenchmark.consumeInLoop        10  avgt       **5646181.631**          ns/op
IntegerCacheBenchmark.consumeInLoop     10000  avgt       **9614186.353 **         ns/op    almoust 2X when running in loop
IntegerCacheBenchmark.new_integer          10  avgt             2.957          ns/op
IntegerCacheBenchmark.new_integer       10000  avgt             2.983          ns/op
