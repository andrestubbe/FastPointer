# FastPointer Architecture & Description

`FastPointer` provides zero-overhead 64-bit native pointer abstraction and address arithmetic for the JVM.

## Core Capabilities
- **Primitive Address Arithmetic**: Operates directly on `long` memory addresses (`address + offset`).
- **Zero-GC Overhead**: Eliminates object allocation during high-frequency loop processing.
- **Unsafe & Direct Memory Access**: Primitive getters/setters (`getByte`, `getInt`, `getFloat`, `getDouble`).
- **Native OS Handle Casting**: Type-safe handles for Win32 OS structures (`HWND`, `HDC`, `HANDLE`).
