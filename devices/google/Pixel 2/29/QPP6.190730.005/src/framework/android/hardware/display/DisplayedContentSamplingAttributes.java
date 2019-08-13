/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

public final class DisplayedContentSamplingAttributes {
    private int mComponentMask;
    private int mDataspace;
    private int mPixelFormat;

    public DisplayedContentSamplingAttributes(int n, int n2, int n3) {
        this.mPixelFormat = n;
        this.mDataspace = n2;
        this.mComponentMask = n3;
    }

    public int getComponentMask() {
        return this.mComponentMask;
    }

    public int getDataspace() {
        return this.mDataspace;
    }

    public int getPixelFormat() {
        return this.mPixelFormat;
    }
}

