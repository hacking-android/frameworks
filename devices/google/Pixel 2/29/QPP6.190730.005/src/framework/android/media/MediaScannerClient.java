/*
 * Decompiled with CFR 0.145.
 */
package android.media;

public interface MediaScannerClient {
    public void handleStringTag(String var1, String var2);

    public void scanFile(String var1, long var2, long var4, boolean var6, boolean var7);

    public void setMimeType(String var1);
}

