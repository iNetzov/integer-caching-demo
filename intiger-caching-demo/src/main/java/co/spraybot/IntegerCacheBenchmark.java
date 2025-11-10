package co.spraybot;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(time = 12, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Measurement(time = 4, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Fork(value = 1)
public class IntegerCacheBenchmark {

    @Param({"10", "10000"}) // 10 = cached, 10000 = non-cached
    int value;

    // --------------------------------------------------------------------
    // 1. Benchmark: Integer.valueOf(value)
    // This hits the cache if "value" ∈ [-128..127], otherwise allocates.
    // --------------------------------------------------------------------
    @Benchmark
    public Integer cached_valueOf(Blackhole blackhole) {
        Integer x = Integer.valueOf(value);
        blackhole.consume(x); // force use, prevents elimination
        return x;
    }

    // --------------------------------------------------------------------
    // 2. Benchmark: new Integer(value)
    // This always allocates a new Integer (no cache).
    // --------------------------------------------------------------------
    @Benchmark
    public Integer new_integer(Blackhole blackhole) {
        Integer x = new Integer(value);
        blackhole.consume(x); // force use, prevents elimination
        return x;
    }

    @Benchmark
    public void consumeInLoop(Blackhole blackhole) {
        for (int i = 0; i < 10_000_000; i++) {
            blackhole.consume(Integer.valueOf(value));
        }
    }
}