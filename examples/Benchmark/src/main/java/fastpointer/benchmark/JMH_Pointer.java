package fastpointer.benchmark;

import fastpointer.Pointer;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class JMH_Pointer {

    private Pointer ptr;

    @Setup
    public void setup() {
        ptr = Pointer.of(0x7FFF0000L);
    }

    @Benchmark
    public Pointer testPointerOffsetArithmetic() {
        return ptr.add(64).add(-16);
    }

    @Benchmark
    public long testPrimitiveAddressRead() {
        return ptr.address();
    }
}
