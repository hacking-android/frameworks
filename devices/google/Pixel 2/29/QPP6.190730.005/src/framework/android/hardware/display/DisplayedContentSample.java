/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

public final class DisplayedContentSample {
    private long mNumFrames;
    private long[] mSamplesComponent0;
    private long[] mSamplesComponent1;
    private long[] mSamplesComponent2;
    private long[] mSamplesComponent3;

    public DisplayedContentSample(long l, long[] arrl, long[] arrl2, long[] arrl3, long[] arrl4) {
        this.mNumFrames = l;
        this.mSamplesComponent0 = arrl;
        this.mSamplesComponent1 = arrl2;
        this.mSamplesComponent2 = arrl3;
        this.mSamplesComponent3 = arrl4;
    }

    public long getNumFrames() {
        return this.mNumFrames;
    }

    public long[] getSampleComponent(ColorComponent colorComponent) {
        int n = 1.$SwitchMap$android$hardware$display$DisplayedContentSample$ColorComponent[colorComponent.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return this.mSamplesComponent3;
                    }
                    throw new ArrayIndexOutOfBoundsException();
                }
                return this.mSamplesComponent2;
            }
            return this.mSamplesComponent1;
        }
        return this.mSamplesComponent0;
    }

    public static enum ColorComponent {
        CHANNEL0,
        CHANNEL1,
        CHANNEL2,
        CHANNEL3;
        
    }

}

