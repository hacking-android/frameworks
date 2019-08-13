/*
 * Decompiled with CFR 0.145.
 */
package android.attention;

public abstract class AttentionManagerInternal {
    public abstract void cancelAttentionCheck(AttentionCallbackInternal var1);

    public abstract boolean checkAttention(long var1, AttentionCallbackInternal var3);

    public abstract boolean isAttentionServiceSupported();

    public static abstract class AttentionCallbackInternal {
        public abstract void onFailure(int var1);

        public abstract void onSuccess(int var1, long var2);
    }

}

