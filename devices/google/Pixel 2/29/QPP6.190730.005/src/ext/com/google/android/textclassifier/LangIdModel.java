/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.textclassifier;

import java.util.concurrent.atomic.AtomicBoolean;

public final class LangIdModel
implements AutoCloseable {
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private long modelPtr;

    static {
        System.loadLibrary("textclassifier");
    }

    public LangIdModel(int n) {
        this.modelPtr = LangIdModel.nativeNew(n);
        if (this.modelPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize LangId from given file descriptor.");
    }

    public LangIdModel(String string) {
        this.modelPtr = LangIdModel.nativeNewFromPath(string);
        if (this.modelPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize LangId from given file.");
    }

    public static int getVersion(int n) {
        return LangIdModel.nativeGetVersionFromFd(n);
    }

    private native void nativeClose(long var1);

    private native LanguageResult[] nativeDetectLanguages(long var1, String var3);

    private native float nativeGetLangIdThreshold(long var1);

    private native int nativeGetVersion(long var1);

    private static native int nativeGetVersionFromFd(int var0);

    private static native long nativeNew(int var0);

    private static native long nativeNewFromPath(String var0);

    @Override
    public void close() {
        if (this.isClosed.compareAndSet(false, true)) {
            this.nativeClose(this.modelPtr);
            this.modelPtr = 0L;
        }
    }

    public LanguageResult[] detectLanguages(String string) {
        return this.nativeDetectLanguages(this.modelPtr, string);
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public float getLangIdThreshold() {
        return this.nativeGetLangIdThreshold(this.modelPtr);
    }

    public int getVersion() {
        return this.nativeGetVersion(this.modelPtr);
    }

    public static final class LanguageResult {
        final String mLanguage;
        final float mScore;

        LanguageResult(String string, float f) {
            this.mLanguage = string;
            this.mScore = f;
        }

        public final String getLanguage() {
            return this.mLanguage;
        }

        public final float getScore() {
            return this.mScore;
        }
    }

}

