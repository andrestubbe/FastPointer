package examples;

import fastpointer.Pointer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== FastPointer 0.1.0 - Performance Comparison ===");
        System.out.println("Native Memory vs Java Heap Objects");

        Unsafe unsafe = getUnsafe();
        if (unsafe == null) {
            System.err.println("Unsafe not available.");
            return;
        }

        // Test with realistic data volume
        int messageCount = 1_000_000;
        int messageSize = 24; // 8 bytes timestamp + 8 bytes senderId + 8 bytes payload
        long bufferSize = (long) messageCount * messageSize;
        
        System.out.println("\nTest scenario: " + messageCount + " messages, each 24 bytes");
        System.out.println("Total data size: " + (bufferSize / 1024 / 1024) + " MB");

        // ===== NATIVE MEMORY APPROACH (FastPointer) =====
        System.out.println("\n--- FastPointer (Native Memory) ---");
        
        long nativeMemoryAddress = unsafe.allocateMemory(bufferSize);
        System.out.println("Allocated native memory: " + (bufferSize / 1024 / 1024) + " MB");
        
        long nativeWriteTime = 0;
        long nativeReadTime = 0;
        
        try {
            Pointer nativeBuffer = Pointer.of(nativeMemoryAddress);
            
            // Write test
            System.gc();
            long nativeWriteStart = System.nanoTime();
            for (int i = 0; i < messageCount; i++) {
                Pointer msgPtr = nativeBuffer.offset(i * messageSize);
                msgPtr.setLong(0, System.currentTimeMillis());
                msgPtr.setLong(8, i);
                msgPtr.setLong(16, i * 1000);
            }
            nativeWriteTime = System.nanoTime() - nativeWriteStart;
            System.out.println("Write time: " + (nativeWriteTime / 1_000_000) + " ms");
            
            // Read test
            long nativeReadStart = System.nanoTime();
            long nativeSum = 0;
            for (int i = 0; i < messageCount; i++) {
                Pointer msgPtr = nativeBuffer.offset(i * messageSize);
                nativeSum += msgPtr.getLong(16);
            }
            nativeReadTime = System.nanoTime() - nativeReadStart;
            System.out.println("Read time: " + (nativeReadTime / 1_000_000) + " ms");
            System.out.println("Memory overhead: ~0 bytes (off-heap, no GC)");
            
        } finally {
            unsafe.freeMemory(nativeMemoryAddress);
        }

        // ===== JAVA HEAP APPROACH (Traditional) =====
        System.out.println("\n--- Java Objects (Heap Memory) ---");
        
        List<Message> heapMessages = new ArrayList<>(messageCount);
        
        // Write test
        System.gc();
        long heapWriteStart = System.nanoTime();
        for (int i = 0; i < messageCount; i++) {
            heapMessages.add(new Message(System.currentTimeMillis(), i, i * 1000));
        }
        long heapWriteTime = System.nanoTime() - heapWriteStart;
        System.out.println("Write time: " + (heapWriteTime / 1_000_000) + " ms");
        
        // Read test
        long heapReadStart = System.nanoTime();
        long heapSum = 0;
        for (Message msg : heapMessages) {
            heapSum += msg.payload;
        }
        long heapReadTime = System.nanoTime() - heapReadStart;
        System.out.println("Read time: " + (heapReadTime / 1_000_000) + " ms");
        
        // Estimate memory overhead
        long estimatedHeapMemory = (long) (messageCount * 48); // ~48 bytes per object (object header + fields)
        System.out.println("Memory overhead: ~" + (estimatedHeapMemory / 1024 / 1024) + " MB (object headers + pointers)");

        // ===== RESULTS =====
        System.out.println("\n=== Performance Results ===");
        System.out.println("Write speedup: " + String.format("%.1fx", (double) heapWriteTime / nativeWriteTime));
        System.out.println("Read speedup: " + String.format("%.1fx", (double) heapReadTime / nativeReadTime));
        System.out.println("Memory saved: " + String.format("%.1f MB", (estimatedHeapMemory - bufferSize) / 1024.0 / 1024.0));
        System.out.println("GC pressure: Native = NONE | Heap = HIGH");
        
        System.out.println("\n=== Demo finished successfully! ===");
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

    static class Message {
        long timestamp;
        long senderId;
        long payload;

        Message(long timestamp, long senderId, long payload) {
            this.timestamp = timestamp;
            this.senderId = senderId;
            this.payload = payload;
        }
    }
}
