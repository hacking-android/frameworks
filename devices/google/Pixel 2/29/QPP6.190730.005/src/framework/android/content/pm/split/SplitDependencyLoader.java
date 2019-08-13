/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.content.pm.split;

import android.content.pm.PackageParser;
import android.util.IntArray;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.BitSet;
import libcore.util.EmptyArray;

public abstract class SplitDependencyLoader<E extends Exception> {
    private final SparseArray<int[]> mDependencies;

    protected SplitDependencyLoader(SparseArray<int[]> sparseArray) {
        this.mDependencies = sparseArray;
    }

    private static int[] append(int[] arrn, int n) {
        if (arrn == null) {
            return new int[]{n};
        }
        int[] arrn2 = Arrays.copyOf(arrn, arrn.length + 1);
        arrn2[arrn.length] = n;
        return arrn2;
    }

    private int[] collectConfigSplitIndices(int n) {
        int[] arrn = this.mDependencies.get(n);
        if (arrn != null && arrn.length > 1) {
            return Arrays.copyOfRange(arrn, 1, arrn.length);
        }
        return EmptyArray.INT;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static SparseArray<int[]> createDependenciesFromPackage(PackageParser.PackageLite object) throws IllegalDependencyException {
        int n;
        int n2;
        Object object2 = new SparseArray<int[]>();
        ((SparseArray)object2).put(0, new int[]{-1});
        for (n = 0; n < ((PackageParser.PackageLite)object).splitNames.length; ++n) {
            if (!((PackageParser.PackageLite)object).isFeatureSplits[n]) continue;
            String string2 = ((PackageParser.PackageLite)object).usesSplitNames[n];
            if (string2 != null) {
                n2 = Arrays.binarySearch(((PackageParser.PackageLite)object).splitNames, string2);
                if (n2 < 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Split '");
                    ((StringBuilder)object2).append(((PackageParser.PackageLite)object).splitNames[n]);
                    ((StringBuilder)object2).append("' requires split '");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append("', which is missing.");
                    throw new IllegalDependencyException(((StringBuilder)object2).toString());
                }
            } else {
                n2 = 0;
            }
            ((SparseArray)object2).put(n + 1, (int[])new int[]{++n2});
        }
        for (n = 0; n < ((PackageParser.PackageLite)object).splitNames.length; ++n) {
            if (((PackageParser.PackageLite)object).isFeatureSplits[n]) continue;
            String string3 = ((PackageParser.PackageLite)object).configForSplit[n];
            if (string3 != null) {
                n2 = Arrays.binarySearch(((PackageParser.PackageLite)object).splitNames, string3);
                if (n2 < 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Split '");
                    ((StringBuilder)object2).append(((PackageParser.PackageLite)object).splitNames[n]);
                    ((StringBuilder)object2).append("' targets split '");
                    ((StringBuilder)object2).append(string3);
                    ((StringBuilder)object2).append("', which is missing.");
                    throw new IllegalDependencyException(((StringBuilder)object2).toString());
                }
                if (!((PackageParser.PackageLite)object).isFeatureSplits[n2]) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Split '");
                    ((StringBuilder)object2).append(((PackageParser.PackageLite)object).splitNames[n]);
                    ((StringBuilder)object2).append("' declares itself as configuration split for a non-feature split '");
                    ((StringBuilder)object2).append(((PackageParser.PackageLite)object).splitNames[n2]);
                    ((StringBuilder)object2).append("'");
                    throw new IllegalDependencyException(((StringBuilder)object2).toString());
                }
                ++n2;
            } else {
                n2 = 0;
            }
            ((SparseArray)object2).put(n2, (int[])SplitDependencyLoader.append((int[])((SparseArray)object2).get(n2), n + 1));
        }
        object = new BitSet();
        n2 = 0;
        int n3 = ((SparseArray)object2).size();
        while (n2 < n3) {
            n = ((SparseArray)object2).keyAt(n2);
            ((BitSet)object).clear();
            while (n != -1) {
                if (((BitSet)object).get(n)) {
                    throw new IllegalDependencyException("Cycle detected in split dependencies.");
                }
                ((BitSet)object).set(n);
                int[] arrn = (int[])((SparseArray)object2).get(n);
                if (arrn != null) {
                    n = arrn[0];
                    continue;
                }
                n = -1;
            }
            ++n2;
        }
        return object2;
    }

    protected abstract void constructSplit(int var1, int[] var2, int var3) throws Exception;

    protected abstract boolean isSplitCached(int var1);

    protected void loadDependenciesForSplit(int n) throws Exception {
        int[] arrn;
        if (this.isSplitCached(n)) {
            return;
        }
        if (n == 0) {
            this.constructSplit(0, this.collectConfigSplitIndices(0), -1);
            return;
        }
        IntArray intArray = new IntArray();
        intArray.add(n);
        while ((n = (arrn = this.mDependencies.get(n)) != null && arrn.length > 0 ? arrn[0] : -1) >= 0 && !this.isSplitCached(n)) {
            intArray.add(n);
        }
        int n2 = n;
        for (n = intArray.size() - 1; n >= 0; --n) {
            int n3 = intArray.get(n);
            this.constructSplit(n3, this.collectConfigSplitIndices(n3), n2);
            n2 = n3;
        }
    }

    public static class IllegalDependencyException
    extends Exception {
        private IllegalDependencyException(String string2) {
            super(string2);
        }
    }

}

