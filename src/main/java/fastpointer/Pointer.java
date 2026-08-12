package fastpointer;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * FastPointer — Zero-Allocation Native Address Arithmetic & Memory Accessor.
 * Wraps 64-bit primitive memory addresses (long) with zero GC overhead.
 */
public final class Pointer {

    private static final Unsafe UNSAFE;
    private static final Pointer NULL = new Pointer(0L);

    static {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            try {
                Field f = Unsafe.class.getDeclaredField("Unsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            } catch (Exception ignored) {}
        }
        UNSAFE = unsafe;
    }

    private final long address;

    private Pointer(long address) {
        this.address = address;
    }

    /**
     * Creates a Pointer for a 64-bit primitive memory address.
     */
    public static Pointer of(long address) {
        if (address == 0L) return NULL;
        return new Pointer(address);
    }

    public static Pointer nullPointer() {
        return NULL;
    }

    public long address() {
        return address;
    }

    public boolean isNull() {
        return address == 0L;
    }

    public Pointer offset(long bytes) {
        if (address == 0L) return NULL;
        return new Pointer(address + bytes);
    }

    public Pointer add(long bytes) {
        return offset(bytes);
    }

    // Direct Unsafe Primitives (Zero-GC)
    public byte getByte(long offset) {
        return UNSAFE.getByte(address + offset);
    }

    public void setByte(long offset, byte value) {
        UNSAFE.putByte(address + offset, value);
    }

    public int getInt(long offset) {
        return UNSAFE.getInt(address + offset);
    }

    public void setInt(long offset, int value) {
        UNSAFE.putInt(address + offset, value);
    }

    public float getFloat(long offset) {
        return UNSAFE.getFloat(address + offset);
    }

    public void setFloat(long offset, float value) {
        UNSAFE.putFloat(address + offset, value);
    }

    public double getDouble(long offset) {
        return UNSAFE.getDouble(address + offset);
    }

    public void setDouble(long offset, double value) {
        UNSAFE.putDouble(address + offset, value);
    }

    public long getLong(long offset) {
        return UNSAFE.getLong(address + offset);
    }

    public void setLong(long offset, long value) {
        UNSAFE.putLong(address + offset, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pointer pointer = (Pointer) o;
        return address == pointer.address;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(address);
    }

    @Override
    public String toString() {
        return String.format("Pointer[0x%016X]", address);
    }
}
