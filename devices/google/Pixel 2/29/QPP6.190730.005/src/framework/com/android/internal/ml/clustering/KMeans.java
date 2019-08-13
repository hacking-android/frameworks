/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.ml.clustering;

import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {
    private static final boolean DEBUG = false;
    private static final String TAG = "KMeans";
    private final int mMaxIterations;
    private final Random mRandomState;
    private float mSqConvergenceEpsilon;

    public KMeans() {
        this(new Random());
    }

    public KMeans(Random random) {
        this(random, 30, 0.005f);
    }

    public KMeans(Random random, int n, float f) {
        this.mRandomState = random;
        this.mMaxIterations = n;
        this.mSqConvergenceEpsilon = f * f;
    }

    @VisibleForTesting
    public static Mean nearestMean(float[] arrf, List<Mean> list) {
        Mean mean = null;
        float f = Float.MAX_VALUE;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Mean mean2 = list.get(i);
            float f2 = KMeans.sqDistance(arrf, mean2.mCentroid);
            float f3 = f;
            if (f2 < f) {
                mean = mean2;
                f3 = f2;
            }
            f = f3;
        }
        return mean;
    }

    public static double score(List<Mean> list) {
        double d = 0.0;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Mean mean = list.get(i);
            for (int j = 0; j < n; ++j) {
                Mean mean2 = list.get(j);
                if (mean == mean2) continue;
                d += Math.sqrt(KMeans.sqDistance(mean.mCentroid, mean2.mCentroid));
            }
        }
        return d;
    }

    @VisibleForTesting
    public static float sqDistance(float[] arrf, float[] arrf2) {
        float f = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            f += (arrf[i] - arrf2[i]) * (arrf[i] - arrf2[i]);
        }
        return f;
    }

    private boolean step(ArrayList<Mean> arrayList, float[][] arrf) {
        Object object;
        int n;
        for (n = arrayList.size() - 1; n >= 0; --n) {
            arrayList.get((int)n).mClosestItems.clear();
        }
        for (n = arrf.length - 1; n >= 0; --n) {
            object = arrf[n];
            KMeans.nearestMean((float[])object, arrayList).mClosestItems.add((float[])object);
        }
        boolean bl = true;
        for (n = arrayList.size() - 1; n >= 0; --n) {
            int n2;
            float[] arrf2;
            object = arrayList.get(n);
            if (object.mClosestItems.size() == 0) continue;
            arrf = object.mCentroid;
            object.mCentroid = new float[arrf.length];
            for (n2 = 0; n2 < object.mClosestItems.size(); ++n2) {
                for (int i = 0; i < object.mCentroid.length; ++i) {
                    arrf2 = object.mCentroid;
                    arrf2[i] = arrf2[i] + object.mClosestItems.get(n2)[i];
                }
            }
            for (n2 = 0; n2 < object.mCentroid.length; ++n2) {
                arrf2 = object.mCentroid;
                arrf2[n2] = arrf2[n2] / (float)object.mClosestItems.size();
            }
            if (!(KMeans.sqDistance((float[])arrf, object.mCentroid) > this.mSqConvergenceEpsilon)) continue;
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting
    public void checkDataSetSanity(float[][] arrf) {
        if (arrf != null) {
            if (arrf.length != 0) {
                if (arrf[0] != null) {
                    int n = arrf[0].length;
                    int n2 = arrf.length;
                    for (int i = 1; i < n2; ++i) {
                        if (arrf[i] != null && arrf[i].length == n) {
                            continue;
                        }
                        throw new IllegalArgumentException("Bad data set format.");
                    }
                    return;
                }
                throw new IllegalArgumentException("Bad data set format.");
            }
            throw new IllegalArgumentException("Data set is empty.");
        }
        throw new IllegalArgumentException("Data set is null.");
    }

    public List<Mean> predict(int n, float[][] arrf) {
        this.checkDataSetSanity(arrf);
        int n2 = arrf[0].length;
        ArrayList<Mean> arrayList = new ArrayList<Mean>();
        for (int i = 0; i < n; ++i) {
            Mean mean = new Mean(n2);
            for (int j = 0; j < n2; ++j) {
                mean.mCentroid[j] = this.mRandomState.nextFloat();
            }
            arrayList.add(mean);
        }
        for (n = 0; n < this.mMaxIterations && !this.step(arrayList, arrf); ++n) {
        }
        return arrayList;
    }

    public static class Mean {
        float[] mCentroid;
        final ArrayList<float[]> mClosestItems = new ArrayList();

        public Mean(int n) {
            this.mCentroid = new float[n];
        }

        public Mean(float ... arrf) {
            this.mCentroid = arrf;
        }

        public float[] getCentroid() {
            return this.mCentroid;
        }

        public List<float[]> getItems() {
            return this.mClosestItems;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Mean(centroid: ");
            stringBuilder.append(Arrays.toString(this.mCentroid));
            stringBuilder.append(", size: ");
            stringBuilder.append(this.mClosestItems.size());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

