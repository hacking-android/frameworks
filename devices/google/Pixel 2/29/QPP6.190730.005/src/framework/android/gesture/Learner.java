/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.Instance;
import android.gesture.Prediction;
import java.util.ArrayList;
import java.util.Collection;

abstract class Learner {
    private final ArrayList<Instance> mInstances = new ArrayList();

    Learner() {
    }

    void addInstance(Instance instance) {
        this.mInstances.add(instance);
    }

    abstract ArrayList<Prediction> classify(int var1, int var2, float[] var3);

    ArrayList<Instance> getInstances() {
        return this.mInstances;
    }

    void removeInstance(long l) {
        ArrayList<Instance> arrayList = this.mInstances;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Instance instance = arrayList.get(i);
            if (l != instance.id) continue;
            arrayList.remove(instance);
            return;
        }
    }

    void removeInstances(String string2) {
        ArrayList<Instance> arrayList = new ArrayList<Instance>();
        ArrayList<Instance> arrayList2 = this.mInstances;
        int n = arrayList2.size();
        for (int i = 0; i < n; ++i) {
            Instance instance = arrayList2.get(i);
            if ((instance.label != null || string2 != null) && (instance.label == null || !instance.label.equals(string2))) continue;
            arrayList.add(instance);
        }
        arrayList2.removeAll(arrayList);
    }
}

