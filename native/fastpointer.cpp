#include "fastpointer.h"
#include <jni.h>

extern "C" {

JNIEXPORT jboolean JNICALL Java_fastpointer_FastPointerNative_validateAddress(JNIEnv* env, jclass clazz, jlong address) {
    if (address == 0) return JNI_FALSE;
    void* ptr = reinterpret_cast<void*>(static_cast<uintptr_t>(address));
    return FastPointer_IsValidReadPtr(ptr, 1) ? JNI_TRUE : JNI_FALSE;
}

}
