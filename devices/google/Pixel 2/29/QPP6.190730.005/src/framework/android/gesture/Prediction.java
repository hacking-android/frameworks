/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

public class Prediction {
    public final String name;
    public double score;

    Prediction(String string2, double d) {
        this.name = string2;
        this.score = d;
    }

    public String toString() {
        return this.name;
    }
}

