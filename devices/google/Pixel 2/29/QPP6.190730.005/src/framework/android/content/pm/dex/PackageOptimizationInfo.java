/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

public class PackageOptimizationInfo {
    private final int mCompilationFilter;
    private final int mCompilationReason;

    public PackageOptimizationInfo(int n, int n2) {
        this.mCompilationReason = n2;
        this.mCompilationFilter = n;
    }

    public static PackageOptimizationInfo createWithNoInfo() {
        return new PackageOptimizationInfo(-1, -1);
    }

    public int getCompilationFilter() {
        return this.mCompilationFilter;
    }

    public int getCompilationReason() {
        return this.mCompilationReason;
    }
}

