/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import dalvik.annotation.compat.UnsupportedAppUsage;

final class DexCache {
    @UnsupportedAppUsage
    private long dexFile;
    private String location;
    private int numPreResolvedStrings;
    private int numResolvedCallSites;
    private int numResolvedFields;
    private int numResolvedMethodTypes;
    private int numResolvedMethods;
    private int numResolvedTypes;
    private int numStrings;
    private long preResolvedStrings;
    private long resolvedCallSites;
    private long resolvedFields;
    private long resolvedMethodTypes;
    private long resolvedMethods;
    private long resolvedTypes;
    private long strings;

    private DexCache() {
    }
}

