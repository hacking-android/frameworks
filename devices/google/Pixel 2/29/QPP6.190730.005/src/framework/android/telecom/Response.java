/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

public interface Response<IN, OUT> {
    public void onError(IN var1, int var2, String var3);

    public void onResult(IN var1, OUT ... var2);
}

