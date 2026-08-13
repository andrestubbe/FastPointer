package fastpointer.benchmark;

import fastpointer.Pointer;
import org.openjdk.jmh.annotations.*;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class JMH_Pointer {

    private long rawAddress;
    private Pointer pointer;

    @Setup
    public void setup() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            rawAddress = unsafe.allocateMemory(1024);
            pointer = Pointer.of(rawAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public long testRawAddressOffset() {
        return rawAddress + 64;
    }

    @Benchmark
    public Pointer testFastPointerOffset() {
        return pointer.offset(64);
    }
}
