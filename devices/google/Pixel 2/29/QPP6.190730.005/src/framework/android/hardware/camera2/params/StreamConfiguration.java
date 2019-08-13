/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Size;
import com.android.internal.util.Preconditions;

public class StreamConfiguration {
    protected int mFormat;
    protected int mHeight;
    protected boolean mInput;
    protected int mWidth;

    public StreamConfiguration(int n, int n2, int n3, boolean bl) {
        this.mFormat = StreamConfigurationMap.checkArgumentFormatInternal(n);
        this.mWidth = Preconditions.checkArgumentPositive(n2, "width must be positive");
        this.mHeight = Preconditions.checkArgumentPositive(n3, "height must be positive");
        this.mInput = bl;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof StreamConfiguration) {
            object = (StreamConfiguration)object;
            boolean bl2 = bl;
            if (this.mFormat == ((StreamConfiguration)object).mFormat) {
                bl2 = bl;
                if (this.mWidth == ((StreamConfiguration)object).mWidth) {
                    bl2 = bl;
                    if (this.mHeight == ((StreamConfiguration)object).mHeight) {
                        bl2 = bl;
                        if (this.mInput == ((StreamConfiguration)object).mInput) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Size getSize() {
        return new Size(this.mWidth, this.mHeight);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mFormat, this.mWidth, this.mHeight, this.mInput ? 1 : 0});
    }

    public boolean isInput() {
        return this.mInput;
    }

    public boolean isOutput() {
        return this.mInput ^ true;
    }
}

