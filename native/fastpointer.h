#ifndef FASTPOINTER_H
#define FASTPOINTER_H

#include <windows.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

// FastPointer Utility API for C++
inline void* FastPointer_FromAddress(uint64_t addr) {
    return reinterpret_cast<void*>(static_cast<uintptr_t>(addr));
}

inline uint64_t FastPointer_ToAddress(const void* ptr) {
    return static_cast<uint64_t>(reinterpret_cast<uintptr_t>(ptr));
}

inline BOOL FastPointer_IsValidReadPtr(const void* ptr, size_t size) {
    if (!ptr) return FALSE;
    return !IsBadReadPtr(ptr, size);
}

#ifdef __cplusplus
}
#endif

#endif // FASTPOINTER_H
