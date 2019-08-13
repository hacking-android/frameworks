/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics.palette;

import android.graphics.Color;
import android.util.TimingLogger;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

final class ColorCutQuantizer
implements Quantizer {
    static final int COMPONENT_BLUE = -1;
    static final int COMPONENT_GREEN = -2;
    static final int COMPONENT_RED = -3;
    private static final String LOG_TAG = "ColorCutQuantizer";
    private static final boolean LOG_TIMINGS = false;
    private static final int QUANTIZE_WORD_MASK = 31;
    private static final int QUANTIZE_WORD_WIDTH = 5;
    private static final Comparator<Vbox> VBOX_COMPARATOR_VOLUME = new Comparator<Vbox>(){

        @Override
        public int compare(Vbox vbox, Vbox vbox2) {
            return vbox2.getVolume() - vbox.getVolume();
        }
    };
    int[] mColors;
    Palette.Filter[] mFilters;
    int[] mHistogram;
    List<Palette.Swatch> mQuantizedColors;
    private final float[] mTempHsl = new float[3];
    TimingLogger mTimingLogger;

    ColorCutQuantizer() {
    }

    private static int approximateToRgb888(int n) {
        return ColorCutQuantizer.approximateToRgb888(ColorCutQuantizer.quantizedRed(n), ColorCutQuantizer.quantizedGreen(n), ColorCutQuantizer.quantizedBlue(n));
    }

    static int approximateToRgb888(int n, int n2, int n3) {
        return Color.rgb(ColorCutQuantizer.modifyWordWidth(n, 5, 8), ColorCutQuantizer.modifyWordWidth(n2, 5, 8), ColorCutQuantizer.modifyWordWidth(n3, 5, 8));
    }

    private List<Palette.Swatch> generateAverageColors(Collection<Vbox> object) {
        ArrayList<Palette.Swatch> arrayList = new ArrayList<Palette.Swatch>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Palette.Swatch swatch = ((Vbox)object.next()).getAverageColor();
            if (this.shouldIgnoreColor(swatch)) continue;
            arrayList.add(swatch);
        }
        return arrayList;
    }

    static void modifySignificantOctet(int[] arrn, int n, int n2, int n3) {
        block2 : {
            block3 : {
                if (n == -3) break block2;
                if (n == -2) break block3;
                if (n != -1) break block2;
                for (n = n2; n <= n3; ++n) {
                    n2 = arrn[n];
                    arrn[n] = ColorCutQuantizer.quantizedBlue(n2) << 10 | ColorCutQuantizer.quantizedGreen(n2) << 5 | ColorCutQuantizer.quantizedRed(n2);
                }
                break block2;
            }
            for (n = n2; n <= n3; ++n) {
                n2 = arrn[n];
                arrn[n] = ColorCutQuantizer.quantizedGreen(n2) << 10 | ColorCutQuantizer.quantizedRed(n2) << 5 | ColorCutQuantizer.quantizedBlue(n2);
            }
        }
    }

    private static int modifyWordWidth(int n, int n2, int n3) {
        n = n3 > n2 ? (n <<= n3 - n2) : (n >>= n2 - n3);
        return n & (1 << n3) - 1;
    }

    private static int quantizeFromRgb888(int n) {
        return ColorCutQuantizer.modifyWordWidth(Color.red(n), 8, 5) << 10 | ColorCutQuantizer.modifyWordWidth(Color.green(n), 8, 5) << 5 | ColorCutQuantizer.modifyWordWidth(Color.blue(n), 8, 5);
    }

    private List<Palette.Swatch> quantizePixels(int n) {
        PriorityQueue<Vbox> priorityQueue = new PriorityQueue<Vbox>(n, VBOX_COMPARATOR_VOLUME);
        priorityQueue.offer(new Vbox(0, this.mColors.length - 1));
        this.splitBoxes(priorityQueue, n);
        return this.generateAverageColors(priorityQueue);
    }

    static int quantizedBlue(int n) {
        return n & 31;
    }

    static int quantizedGreen(int n) {
        return n >> 5 & 31;
    }

    static int quantizedRed(int n) {
        return n >> 10 & 31;
    }

    private boolean shouldIgnoreColor(int n) {
        n = ColorCutQuantizer.approximateToRgb888(n);
        ColorUtils.colorToHSL(n, this.mTempHsl);
        return this.shouldIgnoreColor(n, this.mTempHsl);
    }

    private boolean shouldIgnoreColor(int n, float[] arrf) {
        Palette.Filter[] arrfilter = this.mFilters;
        if (arrfilter != null && arrfilter.length > 0) {
            int n2 = arrfilter.length;
            for (int i = 0; i < n2; ++i) {
                if (this.mFilters[i].isAllowed(n, arrf)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean shouldIgnoreColor(Palette.Swatch swatch) {
        return this.shouldIgnoreColor(swatch.getRgb(), swatch.getHsl());
    }

    private void splitBoxes(PriorityQueue<Vbox> priorityQueue, int n) {
        while (priorityQueue.size() < n) {
            Vbox vbox = priorityQueue.poll();
            if (vbox != null && vbox.canSplit()) {
                priorityQueue.offer(vbox.splitBox());
                priorityQueue.offer(vbox);
                continue;
            }
            return;
        }
    }

    @Override
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }

    @Override
    public void quantize(int[] arrn, int n, Palette.Filter[] arrobject) {
        int n2;
        int n3;
        int n4;
        this.mTimingLogger = null;
        this.mFilters = arrobject;
        arrobject = new int[32768];
        this.mHistogram = arrobject;
        for (n4 = 0; n4 < arrn.length; ++n4) {
            arrn[n4] = n2 = ColorCutQuantizer.quantizeFromRgb888(arrn[n4]);
            arrobject[n2] = arrobject[n2] + true;
        }
        n4 = 0;
        n2 = 0;
        do {
            n3 = arrobject.length;
            int n5 = 0;
            if (n2 >= n3) break;
            if (arrobject[n2] > 0 && this.shouldIgnoreColor(n2)) {
                arrobject[n2] = (Palette.Filter)false;
            }
            n3 = n4;
            if (arrobject[n2] > 0) {
                n3 = n4 + 1;
            }
            ++n2;
            n4 = n3;
        } while (true);
        arrn = new int[n4];
        this.mColors = arrn;
        n3 = 0;
        for (n2 = 0; n2 < arrobject.length; ++n2) {
            int n6 = n3;
            if (arrobject[n2] > 0) {
                arrn[n3] = n2;
                n6 = n3 + 1;
            }
            n3 = n6;
        }
        if (n4 <= n) {
            this.mQuantizedColors = new ArrayList<Palette.Swatch>();
            n4 = arrn.length;
            for (n = n5; n < n4; ++n) {
                n2 = arrn[n];
                this.mQuantizedColors.add(new Palette.Swatch(ColorCutQuantizer.approximateToRgb888(n2), (int)arrobject[n2]));
            }
        } else {
            this.mQuantizedColors = this.quantizePixels(n);
        }
    }

    private class Vbox {
        private int mLowerIndex;
        private int mMaxBlue;
        private int mMaxGreen;
        private int mMaxRed;
        private int mMinBlue;
        private int mMinGreen;
        private int mMinRed;
        private int mPopulation;
        private int mUpperIndex;

        Vbox(int n, int n2) {
            this.mLowerIndex = n;
            this.mUpperIndex = n2;
            this.fitBox();
        }

        final boolean canSplit() {
            int n = this.getColorCount();
            boolean bl = true;
            if (n <= 1) {
                bl = false;
            }
            return bl;
        }

        final int findSplitPoint() {
            int n;
            int n2 = this.getLongestColorDimension();
            int[] arrn = ColorCutQuantizer.this.mColors;
            int[] arrn2 = ColorCutQuantizer.this.mHistogram;
            ColorCutQuantizer.modifySignificantOctet(arrn, n2, this.mLowerIndex, this.mUpperIndex);
            Arrays.sort(arrn, this.mLowerIndex, this.mUpperIndex + 1);
            ColorCutQuantizer.modifySignificantOctet(arrn, n2, this.mLowerIndex, this.mUpperIndex);
            int n3 = this.mPopulation / 2;
            n2 = 0;
            for (int i = this.mLowerIndex; i <= (n = this.mUpperIndex); ++i) {
                if ((n2 += arrn2[arrn[i]]) < n3) continue;
                return Math.min(n - 1, i);
            }
            return this.mLowerIndex;
        }

        final void fitBox() {
            int[] arrn = ColorCutQuantizer.this.mColors;
            int[] arrn2 = ColorCutQuantizer.this.mHistogram;
            int n = Integer.MAX_VALUE;
            int n2 = Integer.MAX_VALUE;
            int n3 = Integer.MAX_VALUE;
            int n4 = Integer.MIN_VALUE;
            int n5 = Integer.MIN_VALUE;
            int n6 = Integer.MIN_VALUE;
            int n7 = 0;
            for (int i = this.mLowerIndex; i <= this.mUpperIndex; ++i) {
                int n8 = arrn[i];
                int n9 = n7 + arrn2[n8];
                int n10 = ColorCutQuantizer.quantizedRed(n8);
                int n11 = ColorCutQuantizer.quantizedGreen(n8);
                n8 = ColorCutQuantizer.quantizedBlue(n8);
                n7 = n4;
                if (n10 > n4) {
                    n7 = n10;
                }
                n4 = n;
                if (n10 < n) {
                    n4 = n10;
                }
                n10 = n6;
                if (n11 > n6) {
                    n10 = n11;
                }
                n6 = n3;
                if (n11 < n3) {
                    n6 = n11;
                }
                n11 = n5;
                if (n8 > n5) {
                    n11 = n8;
                }
                n5 = n2;
                if (n8 < n2) {
                    n5 = n8;
                }
                n = n4;
                n2 = n5;
                n3 = n6;
                n4 = n7;
                n5 = n11;
                n6 = n10;
                n7 = n9;
            }
            this.mMinRed = n;
            this.mMaxRed = n4;
            this.mMinGreen = n3;
            this.mMaxGreen = n6;
            this.mMinBlue = n2;
            this.mMaxBlue = n5;
            this.mPopulation = n7;
        }

        final Palette.Swatch getAverageColor() {
            int[] arrn = ColorCutQuantizer.this.mColors;
            int[] arrn2 = ColorCutQuantizer.this.mHistogram;
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            for (int i = this.mLowerIndex; i <= this.mUpperIndex; ++i) {
                int n5 = arrn[i];
                int n6 = arrn2[n5];
                n4 += n6;
                n += ColorCutQuantizer.quantizedRed(n5) * n6;
                n2 += ColorCutQuantizer.quantizedGreen(n5) * n6;
                n3 += ColorCutQuantizer.quantizedBlue(n5) * n6;
            }
            return new Palette.Swatch(ColorCutQuantizer.approximateToRgb888(Math.round((float)n / (float)n4), Math.round((float)n2 / (float)n4), Math.round((float)n3 / (float)n4)), n4);
        }

        final int getColorCount() {
            return this.mUpperIndex + 1 - this.mLowerIndex;
        }

        final int getLongestColorDimension() {
            int n = this.mMaxRed - this.mMinRed;
            int n2 = this.mMaxGreen - this.mMinGreen;
            int n3 = this.mMaxBlue - this.mMinBlue;
            if (n >= n2 && n >= n3) {
                return -3;
            }
            if (n2 >= n && n2 >= n3) {
                return -2;
            }
            return -1;
        }

        final int getVolume() {
            return (this.mMaxRed - this.mMinRed + 1) * (this.mMaxGreen - this.mMinGreen + 1) * (this.mMaxBlue - this.mMinBlue + 1);
        }

        final Vbox splitBox() {
            if (this.canSplit()) {
                int n = this.findSplitPoint();
                Vbox vbox = new Vbox(n + 1, this.mUpperIndex);
                this.mUpperIndex = n;
                this.fitBox();
                return vbox;
            }
            throw new IllegalStateException("Can not split a box with only 1 color");
        }
    }

}

