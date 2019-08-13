/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.GestureUtils;
import android.gesture.Instance;
import android.gesture.Learner;
import android.gesture.Prediction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

class InstanceLearner
extends Learner {
    private static final Comparator<Prediction> sComparator = new Comparator<Prediction>(){

        @Override
        public int compare(Prediction prediction, Prediction prediction2) {
            double d = prediction.score;
            double d2 = prediction2.score;
            if (d > d2) {
                return -1;
            }
            return d < d2;
        }
    };

    InstanceLearner() {
    }

    @Override
    ArrayList<Prediction> classify(int n, int n2, float[] object) {
        ArrayList<Prediction> arrayList = new ArrayList<Prediction>();
        ArrayList<Instance> arrayList2 = this.getInstances();
        int n3 = arrayList2.size();
        TreeMap<String, Double> treeMap = new TreeMap<String, Double>();
        for (int i = 0; i < n3; ++i) {
            Instance instance = arrayList2.get(i);
            if (instance.vector.length != ((Object)object).length) continue;
            double d = n == 2 ? (double)GestureUtils.minimumCosineDistance(instance.vector, (float[])object, n2) : (double)GestureUtils.squaredEuclideanDistance(instance.vector, (float[])object);
            d = d == 0.0 ? Double.MAX_VALUE : 1.0 / d;
            Object object2 = (Double)treeMap.get(instance.label);
            if (object2 != null && !(d > (Double)object2)) continue;
            treeMap.put(instance.label, d);
        }
        for (Object object2 : treeMap.keySet()) {
            arrayList.add(new Prediction((String)object2, (Double)treeMap.get(object2)));
        }
        Collections.sort(arrayList, sComparator);
        return arrayList;
    }

}

