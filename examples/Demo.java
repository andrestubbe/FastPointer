package examples;

import fastpointer.Pointer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== FastPointer 0.1.0 Interactive Demo ===");

        // Get Unsafe to allocate a raw native memory block for demo
        Unsafe unsafe = getUnsafe();
        if (unsafe == null) {
            System.err.println("Unsafe not available.");
            return;
        }

        // Allocate 128 bytes of native off-heap memory
        long memoryAddress = unsafe.allocateMemory(128);
        System.out.printf("Allocated native memory at address: 0x%016X%n", memoryAddress);

        try {
            // 1. Wrap in FastPointer
            Pointer basePtr = Pointer.of(memoryAddress);
            System.out.println("Wrapped Base Pointer: " + basePtr);

            // 2. Perform zero-allocation offset arithmetic
            Pointer offsetPtr = basePtr.offset(32);
            System.out.println("Offset Pointer (+32 bytes): " + offsetPtr);

            // 3. Write primitive data directly to native memory
            offsetPtr.setInt(0, 1337);
            offsetPtr.setFloat(4, 3.14159f);

            // 4. Read data back
            int intVal = offsetPtr.getInt(0);
            float floatVal = offsetPtr.getFloat(4);

            System.out.printf("Read at offset +32: int = %d, float = %.5f%n", intVal, floatVal);
            System.out.println("=== Demo finished successfully! ===");
        } finally {
            unsafe.freeMemory(memoryAddress);
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
