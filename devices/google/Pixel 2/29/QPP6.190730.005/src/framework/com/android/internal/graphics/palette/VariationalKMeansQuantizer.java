/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics.palette;

import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.ml.clustering.KMeans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class VariationalKMeansQuantizer
implements Quantizer {
    private static final boolean DEBUG = false;
    private static final String TAG = "KMeansQuantizer";
    private final int mInitializations;
    private final KMeans mKMeans = new KMeans(new Random(0L), 30, 0.0f);
    private final float mMinClusterSqDistance;
    private List<Palette.Swatch> mQuantizedColors;

    public VariationalKMeansQuantizer() {
        this(0.25f);
    }

    public VariationalKMeansQuantizer(float f) {
        this(f, 1);
    }

    public VariationalKMeansQuantizer(float f, int n) {
        this.mMinClusterSqDistance = f * f;
        this.mInitializations = n;
    }

    private List<KMeans.Mean> getOptimalKMeans(int n, float[][] arrf) {
        List<KMeans.Mean> list = null;
        double d = -1.7976931348623157E308;
        for (int i = this.mInitializations; i > 0; --i) {
            double d2;
            block4 : {
                double d3;
                List<KMeans.Mean> list2;
                block3 : {
                    list2 = this.mKMeans.predict(n, arrf);
                    d3 = KMeans.score(list2);
                    if (list == null) break block3;
                    d2 = d;
                    if (!(d3 > d)) break block4;
                }
                d2 = d3;
                list = list2;
            }
            d = d2;
        }
        return list;
    }

    @Override
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }

    @Override
    public void quantize(int[] object, int n, Palette.Filter[] object2) {
        float[] arrf;
        int n2;
        object2 = new float[3];
        Object[] arrobject = object2;
        arrobject[0] = (Palette.Filter)0.0f;
        arrobject[1] = (Palette.Filter)0.0f;
        arrobject[2] = (Palette.Filter)0.0f;
        Object object3 = new float[((int[])object).length][3];
        for (n2 = 0; n2 < ((int[])object).length; ++n2) {
            ColorUtils.colorToHSL(object[n2], (float[])object2);
            object3[n2][0] = (float)(object2[0] / 360.0f);
            object3[n2][1] = object2[1];
            object3[n2][2] = object2[2];
        }
        object3 = this.getOptimalKMeans(n, (float[][])object3);
        object = object2;
        for (n = 0; n < object3.size(); ++n) {
            object2 = (KMeans.Mean)object3.get(n);
            arrf = ((KMeans.Mean)object2).getCentroid();
            for (n2 = n + 1; n2 < object3.size(); ++n2) {
                KMeans.Mean mean = (KMeans.Mean)object3.get(n2);
                float[] arrf2 = mean.getCentroid();
                if (!(KMeans.sqDistance(arrf, arrf2) < this.mMinClusterSqDistance)) continue;
                object3.remove(mean);
                ((KMeans.Mean)object2).getItems().addAll(mean.getItems());
                for (int i = 0; i < arrf.length; ++i) {
                    arrf[i] = (float)((double)arrf[i] + (double)(arrf2[i] - arrf[i]) / 2.0);
                }
                --n2;
            }
        }
        this.mQuantizedColors = new ArrayList<Palette.Swatch>();
        object = object3.iterator();
        while (object.hasNext()) {
            object2 = (KMeans.Mean)object.next();
            if (((KMeans.Mean)object2).getItems().size() == 0) continue;
            arrf = ((KMeans.Mean)object2).getCentroid();
            object3 = this.mQuantizedColors;
            float f = arrf[0];
            float f2 = arrf[1];
            float f3 = arrf[2];
            n = ((KMeans.Mean)object2).getItems().size();
            object3.add(new Palette.Swatch(new float[]{f * 360.0f, f2, f3}, n));
        }
    }
}

