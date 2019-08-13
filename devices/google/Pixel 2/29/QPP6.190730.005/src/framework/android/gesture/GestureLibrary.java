/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.Gesture;
import android.gesture.GestureStore;
import android.gesture.Learner;
import android.gesture.Prediction;
import java.util.ArrayList;
import java.util.Set;

public abstract class GestureLibrary {
    protected final GestureStore mStore = new GestureStore();

    protected GestureLibrary() {
    }

    public void addGesture(String string2, Gesture gesture) {
        this.mStore.addGesture(string2, gesture);
    }

    public Set<String> getGestureEntries() {
        return this.mStore.getGestureEntries();
    }

    public ArrayList<Gesture> getGestures(String string2) {
        return this.mStore.getGestures(string2);
    }

    public Learner getLearner() {
        return this.mStore.getLearner();
    }

    public int getOrientationStyle() {
        return this.mStore.getOrientationStyle();
    }

    public int getSequenceType() {
        return this.mStore.getSequenceType();
    }

    public boolean isReadOnly() {
        return false;
    }

    public abstract boolean load();

    public ArrayList<Prediction> recognize(Gesture gesture) {
        return this.mStore.recognize(gesture);
    }

    public void removeEntry(String string2) {
        this.mStore.removeEntry(string2);
    }

    public void removeGesture(String string2, Gesture gesture) {
        this.mStore.removeGesture(string2, gesture);
    }

    public abstract boolean save();

    public void setOrientationStyle(int n) {
        this.mStore.setOrientationStyle(n);
    }

    public void setSequenceType(int n) {
        this.mStore.setSequenceType(n);
    }
}

