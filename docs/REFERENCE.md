# FastPointer API Reference

## Class `fastpointer.Pointer`

### Construction
- `Pointer.of(long address)`: Wraps a raw 64-bit primitive memory address.
- `Pointer.nullPointer()`: Returns `Pointer[0x0000000000000000]`.

### Address Arithmetic
- `offset(long bytes)`: Calculates a new pointer offset (`address + bytes`).
- `address()`: Returns the primitive 64-bit `long` address.
- `isNull()`: Returns `true` if address == 0.

### Primitive Memory Access
- `getByte(long offset)` / `setByte(long offset, byte value)`
- `getInt(long offset)` / `setInt(long offset, int value)`
- `getFloat(long offset)` / `setFloat(long offset, float value)`
- `getDouble(long offset)` / `setDouble(long offset, double value)`
- `getLong(long offset)` / `setLong(long offset, long value)`
