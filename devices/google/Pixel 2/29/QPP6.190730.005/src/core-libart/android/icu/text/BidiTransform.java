/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.ArabicShaping;
import android.icu.text.ArabicShapingException;
import android.icu.text.Bidi;
import android.icu.text.UTF16;

public class BidiTransform {
    private Bidi bidi;
    private int reorderingOptions;
    private int shapingOptions;
    private String text;

    private static boolean IsLTR(byte by) {
        boolean bl = (by & 1) == 0;
        return bl;
    }

    private static boolean IsLogical(Order order) {
        return Order.LOGICAL.equals((Object)order);
    }

    private static boolean IsRTL(byte by) {
        boolean bl = true;
        if ((by & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    private static boolean IsVisual(Order order) {
        return Order.VISUAL.equals((Object)order);
    }

    private ReorderingScheme findMatchingScheme(byte by, Order order, byte by2, Order order2) {
        for (ReorderingScheme reorderingScheme : ReorderingScheme.values()) {
            if (!reorderingScheme.matches(by, order, by2, order2)) continue;
            return reorderingScheme;
        }
        return null;
    }

    private void mirror() {
        int n;
        if ((this.reorderingOptions & 2) == 0) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(this.text);
        byte[] arrby = this.bidi.getLevels();
        int n2 = arrby.length;
        for (int i = 0; i < n2; i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(stringBuffer, i);
            if ((arrby[i] & 1) == 0) continue;
            UTF16.setCharAt(stringBuffer, i, UCharacter.getMirror(n));
        }
        this.text = stringBuffer.toString();
        this.reorderingOptions &= -3;
    }

    private void reorder() {
        this.text = this.bidi.writeReordered(this.reorderingOptions);
        this.reorderingOptions = 0;
    }

    private void resolve(byte by, int n) {
        Bidi bidi = this.bidi;
        boolean bl = (n & 5) != 0;
        bidi.setInverse(bl);
        this.bidi.setReorderingMode(n);
        this.bidi.setPara(this.text, by, null);
    }

    private void resolveBaseDirection(byte[] arrby) {
        if (Bidi.IsDefaultLevel(arrby[0])) {
            byte by = Bidi.getBaseDirection(this.text);
            if (by == 3) {
                by = arrby[0] == 127 ? (byte)1 : 0;
            }
            arrby[0] = by;
        } else {
            arrby[0] = (byte)(arrby[0] & 1);
        }
        arrby[1] = Bidi.IsDefaultLevel(arrby[1]) ? arrby[0] : (byte)((byte)(arrby[1] & 1));
    }

    private void reverse() {
        this.text = Bidi.writeReverse(this.text, 0);
    }

    private void shapeArabic(int n) {
        if (n != 0) {
            ArabicShaping arabicShaping = new ArabicShaping(n);
            try {
                this.text = arabicShaping.shape(this.text);
            }
            catch (ArabicShapingException arabicShapingException) {
                // empty catch block
            }
        }
    }

    private void shapeArabic(int n, int n2) {
        if (n == n2) {
            this.shapeArabic(this.shapingOptions | n);
        } else {
            this.shapeArabic(this.shapingOptions & -25 | n);
            this.shapeArabic(this.shapingOptions & -225 | n2);
        }
    }

    public String transform(CharSequence object, byte by, Order order, byte by2, Order order2, Mirroring mirroring, int n) {
        if (object != null && order != null && order2 != null && mirroring != null) {
            this.text = object.toString();
            int n2 = 2;
            object = new byte[]{by, by2};
            this.resolveBaseDirection((byte[])object);
            object = this.findMatchingScheme(object[0], order, object[1], order2);
            if (object != null) {
                this.bidi = new Bidi();
                by = Mirroring.ON.equals((Object)mirroring) ? (byte)n2 : (byte)0;
                this.reorderingOptions = by;
                this.shapingOptions = n & -5;
                ((ReorderingScheme)((Object)object)).doTransform(this);
            }
            return this.text;
        }
        throw new IllegalArgumentException();
    }

    public static enum Mirroring {
        OFF,
        ON;
        
    }

    public static enum Order {
        LOGICAL,
        VISUAL;
        
    }

    private static enum ReorderingScheme {
        LOG_LTR_TO_VIS_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.shapeArabic(0, 0);
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.reorder();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsLogical(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        LOG_RTL_TO_VIS_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)1, 0);
                bidiTransform.reorder();
                bidiTransform.shapeArabic(0, 4);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsLogical(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        LOG_LTR_TO_VIS_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.shapeArabic(0, 0);
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.reorder();
                bidiTransform.reverse();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsLogical(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        LOG_RTL_TO_VIS_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)1, 0);
                bidiTransform.reorder();
                bidiTransform.shapeArabic(0, 4);
                bidiTransform.reverse();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsLogical(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        VIS_LTR_TO_LOG_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.shapeArabic(0, 4);
                bidiTransform.resolve((byte)1, 5);
                bidiTransform.reorder();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsVisual(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        VIS_RTL_TO_LOG_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.reverse();
                bidiTransform.shapeArabic(0, 4);
                bidiTransform.resolve((byte)1, 5);
                bidiTransform.reorder();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsVisual(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        VIS_LTR_TO_LOG_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)0, 5);
                bidiTransform.reorder();
                bidiTransform.shapeArabic(0, 0);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsVisual(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        VIS_RTL_TO_LOG_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.reverse();
                bidiTransform.resolve((byte)0, 5);
                bidiTransform.reorder();
                bidiTransform.shapeArabic(0, 0);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsVisual(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        LOG_LTR_TO_LOG_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.shapeArabic(0, 0);
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.resolve((byte)0, 3);
                bidiTransform.reorder();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsLogical(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        LOG_RTL_TO_LOG_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)1, 0);
                bidiTransform.mirror();
                bidiTransform.resolve((byte)1, 3);
                bidiTransform.reorder();
                bidiTransform.shapeArabic(0, 0);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsLogical(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        VIS_LTR_TO_VIS_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(0, 4);
                bidiTransform.reverse();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsVisual(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        VIS_RTL_TO_VIS_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.reverse();
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(0, 4);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsVisual(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        LOG_LTR_TO_LOG_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(0, 0);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsLogical(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        LOG_RTL_TO_LOG_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)1, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(4, 0);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsLogical(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsLogical(order2);
                return bl;
            }
        }
        ,
        VIS_LTR_TO_VIS_LTR{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(0, 4);
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsLTR(by) && BidiTransform.IsVisual(order) && BidiTransform.IsLTR(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        }
        ,
        VIS_RTL_TO_VIS_RTL{

            @Override
            void doTransform(BidiTransform bidiTransform) {
                bidiTransform.reverse();
                bidiTransform.resolve((byte)0, 0);
                bidiTransform.mirror();
                bidiTransform.shapeArabic(0, 4);
                bidiTransform.reverse();
            }

            @Override
            boolean matches(byte by, Order order, byte by2, Order order2) {
                boolean bl = BidiTransform.IsRTL(by) && BidiTransform.IsVisual(order) && BidiTransform.IsRTL(by2) && BidiTransform.IsVisual(order2);
                return bl;
            }
        };
        

        abstract void doTransform(BidiTransform var1);

        abstract boolean matches(byte var1, Order var2, byte var3, Order var4);

    }

}

