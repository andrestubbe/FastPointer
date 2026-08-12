# FastPointer — Zero-Overhead Native Address Arithmetic & Pointer Wrapper

> **Lightweight 64-bit Native Pointer & Address Arithmetic Abstraction for Java.**

---

## 🎯 Zweck & Aufgabe

`FastPointer` bildet die hauchdünne, extrem performante Brücke zwischen Java-Code und nativen 64-Bit Speicheradressen (`long`). Es erlaubt Adressarithmetik, Sub-Pointer-Offsetting und Native-Struct-Casting direkt in Java, ohne JNI-Aufruf-Overhead zu erzeugen.

---

## ⚙️ Was konkret implementiert werden muss

1. **Address Arithmetic (`long ptr + offset`)**:
   - Methoden für schnelles Offset-Rechnen (`add`, `slice`, `align`) ohne Objekt-Allokation.

2. **Direct Read/Write Accessors**:
   - Direkte Lese- und Schreiboperationen (`getByte`, `getInt`, `getFloat`, `getDouble`, `getNativePointer`) über `Unsafe` / `VarHandle` / Foreign Memory.

3. **Struct & Handle Wrapper**:
   - Typisierte Wrapper für Win32 OS Handles (`HWND`, `HDC`, `HANDLE`) und DirectX / Vulkan Pointer (`ID3D11Texture2D*`).

---

## 🔗 Wer bindet sich an `FastPointer`?

- **`FastSharedMemory`**: Gibt die physikalische Startadresse des gemappten Speicherblocks als `FastPointer` zurück.
- **`FastSIMD`**: Benötigt `FastPointer` als Zeiger-Input für Vektor-Scans, Memory-Copies und Formatkonvertierungen.
- **`FastCore`**: Verwendet `FastPointer` für geladene DLL-Funktionszeiger (`GetProcAddress`).
- **`FastConPTY`, `FastDWM`, `FastGraphics`**: Verwalten native OS- und Render-Handles über `FastPointer`.

---

## 🔄 Die Zero-Copy Pipeline

```
FastSharedMemory (IPC Shared RAM)
  └── FastMemory (Hält & sichert 32-Byte aligned RAM)
        └── FastPointer (Zeigt auf Startadresse `long` & rechnet Offsets)
              └── FastSIMD (Verarbeitet Daten via AVX2 / NEON)
```
