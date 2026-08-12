package fastpointer;

import fastcore.FastCore;

/**
 * FastPointerNative — JNI Native Loader using FastCore.
 */
public final class FastPointerNative {

    private static boolean loaded = false;

    static {
        try {
            FastCore.load("FastPointer");
            loaded = true;
        } catch (Throwable t) {
            // Fallback to Unsafe pure Java mode if native DLL is not loaded
            loaded = false;
        }
    }

    public static boolean isNativeLoaded() {
        return loaded;
    }

    public static native boolean validateAddress(long address);
}
