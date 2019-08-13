/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public final class ReprocessFormatsMap {
    private final int[] mEntry;
    private final int mInputCount;

    public ReprocessFormatsMap(int[] arrn) {
        Preconditions.checkNotNull(arrn, "entry must not be null");
        int n = 0;
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < arrn.length) {
            int n4 = StreamConfigurationMap.checkArgumentFormatInternal(arrn[n3]);
            ++n3;
            if (--n2 >= 1) {
                int n5 = arrn[n3];
                int n6 = n2 - 1;
                int n7 = n3 + 1;
                for (n3 = 0; n3 < n5; ++n3) {
                    StreamConfigurationMap.checkArgumentFormatInternal(arrn[n7 + n3]);
                }
                n2 = n6;
                n3 = n7;
                if (n5 > 0) {
                    if (n6 >= n5) {
                        n3 = n7 + n5;
                        n2 = n6 - n5;
                    } else {
                        throw new IllegalArgumentException(String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", n4, n6, n5));
                    }
                }
                ++n;
                continue;
            }
            throw new IllegalArgumentException(String.format("Input %x had no output format length listed", n4));
        }
        this.mEntry = arrn;
        this.mInputCount = n;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof ReprocessFormatsMap) {
            object = (ReprocessFormatsMap)object;
            return Arrays.equals(this.mEntry, ((ReprocessFormatsMap)object).mEntry);
        }
        return false;
    }

    public int[] getInputs() {
        int[] arrn;
        int[] arrn2 = new int[this.mInputCount];
        int n = this.mEntry.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < (arrn = this.mEntry).length) {
            int n4 = arrn[n2];
            ++n2;
            if (--n >= 1) {
                int n5 = arrn[n2];
                int n6 = n - 1;
                int n7 = n2 + 1;
                n = n6;
                n2 = n7;
                if (n5 > 0) {
                    if (n6 >= n5) {
                        n2 = n7 + n5;
                        n = n6 - n5;
                    } else {
                        throw new AssertionError((Object)String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", n4, n6, n5));
                    }
                }
                arrn2[n3] = n4;
                ++n3;
                continue;
            }
            throw new AssertionError((Object)String.format("Input %x had no output format length listed", n4));
        }
        return StreamConfigurationMap.imageFormatToPublic(arrn2);
    }

    public int[] getOutputs(int n) {
        int[] arrn;
        int n2 = this.mEntry.length;
        for (int i = 0; i < (arrn = this.mEntry).length; i += n2) {
            int n3 = arrn[i];
            int n4 = n2 - 1;
            ++i;
            if (n4 >= 1) {
                n2 = arrn[i];
                ++i;
                if (n2 > 0 && --n4 < n2) {
                    throw new AssertionError((Object)String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", n, n4, n2));
                }
                if (n3 == n) {
                    arrn = new int[n2];
                    for (n = 0; n < n2; ++n) {
                        arrn[n] = this.mEntry[i + n];
                    }
                    return StreamConfigurationMap.imageFormatToPublic(arrn);
                }
                n2 = n4 - n2;
                continue;
            }
            throw new AssertionError((Object)String.format("Input %x had no output format length listed", n));
        }
        throw new IllegalArgumentException(String.format("Input format %x was not one in #getInputs", n));
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mEntry);
    }
}

