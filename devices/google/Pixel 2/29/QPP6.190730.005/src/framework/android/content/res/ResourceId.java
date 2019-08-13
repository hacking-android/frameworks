/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

public final class ResourceId {
    public static boolean isValid(int n) {
        boolean bl = n != -1 && (-16777216 & n) != 0 && (16711680 & n) != 0;
        return bl;
    }
}

