**START THE PROJECT:**

1) GIT CLONE XXXX

2) MVN CLEAN, PACKAGE

3) ADD THOUSE JVM ARG >>>  -XX:AutoBoxCacheMax=127 -Xlog:gc*:file=gc-%p.log
  3.1) -XX:AutoBoxCacheMax=127 from JDK 8 -> JDK 20 Integer was from [ -128....127 ] from JDK 20...25 It is a little but different JVM is too smart and they aways find way to          improving  https://bugs.java.com/bugdatabase/view_bug?bug_id=6968657 
   3.2) -Xlog:gc*:file=gc-%p.log collecting the logs in format 'gc-PID.log'
  

5) Start the created jar in the target with the JVM Using the JVM arg

4) You can check the benchmarks when they finish i have added them by default to 1.4 (min) for the whole thing you can (play with it ;) )

5) From the given bechnmarks consumeInLoop you can clearly see almoust 2X performance boost in both GC pressure and pure performance

**REQUIREMENTS**

JDK(25)(or change in POM), MAVEN

**RESULTS:**

Benchmark                               (value) Mode     Cnt     Score      Error   Units
IntegerCacheBenchmark.cached_valueOf       10   avgt             0.633              ns/op

IntegerCacheBenchmark.cached_valueOf    10000   avgt             1.098              ns/op

IntegerCacheBenchmark.consumeInLoop        10   avgt        **5646181.631**         ns/op

IntegerCacheBenchmark.consumeInLoop     10000   avgt        **9614186.353**        ns/op     almost **2X** when running in loop

IntegerCacheBenchmark.new_integer          10   avgt             2.957              ns/op

IntegerCacheBenchmark.new_integer       10000   avgt             2.983              ns/op



Benchmark                             (value)  Mode  Cnt        Score   Error  Units
IntegerCacheBenchmark.cached_valueOf       10  avgt             0.465          ns/op
IntegerCacheBenchmark.cached_valueOf    10000  avgt             1.113          ns/op
IntegerCacheBenchmark.consumeInLoop        10  avgt        **712092.999**          ns/op
IntegerCacheBenchmark.consumeInLoop     10000  avgt       **9634192.207**          ns/op     almost **2X** when running in loop
IntegerCacheBenchmark.new_integer          10  avgt             3.258          ns/op
IntegerCacheBenchmark.new_integer       10000  avgt             3.443          ns/op


**RESOULUTION(IN_MY_OPPINION):**
Benchmark runs the consumeInLoop with value=10 (cached) resolting in the JVM reuses the same Integer object in each iteration.
With value=10000 (not cached), a new Integer is allocated each time, causing tons of young-gen. This leads to >>  significantly more GC work, and ~2× slower execution.
