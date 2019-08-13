/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.LogPrinter;
import android.util.Pair;
import android.util.Printer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.Space;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RemoteViews.RemoteView
public class GridLayout
extends ViewGroup {
    private static final int ALIGNMENT_MODE = 6;
    public static final int ALIGN_BOUNDS = 0;
    public static final int ALIGN_MARGINS = 1;
    public static final Alignment BASELINE;
    public static final Alignment BOTTOM;
    private static final int CAN_STRETCH = 2;
    public static final Alignment CENTER;
    private static final int COLUMN_COUNT = 3;
    private static final int COLUMN_ORDER_PRESERVED = 4;
    private static final int DEFAULT_ALIGNMENT_MODE = 1;
    static final int DEFAULT_CONTAINER_MARGIN = 0;
    private static final int DEFAULT_COUNT = Integer.MIN_VALUE;
    private static final boolean DEFAULT_ORDER_PRESERVED = true;
    private static final int DEFAULT_ORIENTATION = 0;
    private static final boolean DEFAULT_USE_DEFAULT_MARGINS = false;
    public static final Alignment END;
    public static final Alignment FILL;
    public static final int HORIZONTAL = 0;
    private static final int INFLEXIBLE = 0;
    private static final Alignment LEADING;
    public static final Alignment LEFT;
    static final Printer LOG_PRINTER;
    static final int MAX_SIZE = 100000;
    static final Printer NO_PRINTER;
    private static final int ORIENTATION = 0;
    public static final Alignment RIGHT;
    private static final int ROW_COUNT = 1;
    private static final int ROW_ORDER_PRESERVED = 2;
    public static final Alignment START;
    public static final Alignment TOP;
    private static final Alignment TRAILING;
    public static final int UNDEFINED = Integer.MIN_VALUE;
    @UnsupportedAppUsage
    static final Alignment UNDEFINED_ALIGNMENT;
    static final int UNINITIALIZED_HASH = 0;
    private static final int USE_DEFAULT_MARGINS = 5;
    public static final int VERTICAL = 1;
    int mAlignmentMode = 1;
    int mDefaultGap;
    final Axis mHorizontalAxis = new Axis(true);
    int mLastLayoutParamsHashCode = 0;
    int mOrientation = 0;
    Printer mPrinter = LOG_PRINTER;
    boolean mUseDefaultMargins = false;
    final Axis mVerticalAxis = new Axis(false);

    static {
        Alignment alignment;
        Alignment alignment2;
        LOG_PRINTER = new LogPrinter(3, GridLayout.class.getName());
        NO_PRINTER = new Printer(){

            @Override
            public void println(String string2) {
            }
        };
        UNDEFINED_ALIGNMENT = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return Integer.MIN_VALUE;
            }

            @Override
            int getGravityOffset(View view, int n) {
                return Integer.MIN_VALUE;
            }
        };
        LEADING = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return 0;
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }
        };
        TRAILING = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return n;
            }

            @Override
            int getGravityOffset(View view, int n) {
                return n;
            }
        };
        TOP = alignment2 = LEADING;
        BOTTOM = alignment = TRAILING;
        START = alignment2;
        END = alignment;
        LEFT = GridLayout.createSwitchingAlignment(START, END);
        RIGHT = GridLayout.createSwitchingAlignment(END, START);
        CENTER = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return n >> 1;
            }

            @Override
            int getGravityOffset(View view, int n) {
                return n >> 1;
            }
        };
        BASELINE = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                block1 : {
                    if (view.getVisibility() == 8) {
                        return 0;
                    }
                    n = view.getBaseline();
                    if (n != -1) break block1;
                    n = Integer.MIN_VALUE;
                }
                return n;
            }

            @Override
            public Bounds getBounds() {
                return new Bounds(){
                    private int size;

                    @Override
                    protected int getOffset(GridLayout gridLayout, View view, Alignment alignment, int n, boolean bl) {
                        return Math.max(0, super.getOffset(gridLayout, view, alignment, n, bl));
                    }

                    @Override
                    protected void include(int n, int n2) {
                        super.include(n, n2);
                        this.size = Math.max(this.size, n + n2);
                    }

                    @Override
                    protected void reset() {
                        super.reset();
                        this.size = Integer.MIN_VALUE;
                    }

                    @Override
                    protected int size(boolean bl) {
                        return Math.max(super.size(bl), this.size);
                    }
                };
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }

        };
        FILL = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return Integer.MIN_VALUE;
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }

            @Override
            public int getSizeInCell(View view, int n, int n2) {
                return n2;
            }
        };
    }

    public GridLayout(Context context) {
        this(context, null);
    }

    public GridLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GridLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public GridLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mDefaultGap = context.getResources().getDimensionPixelOffset(17105114);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.GridLayout, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.GridLayout, attributeSet, typedArray, n, n2);
        try {
            this.setRowCount(typedArray.getInt(1, Integer.MIN_VALUE));
            this.setColumnCount(typedArray.getInt(3, Integer.MIN_VALUE));
            this.setOrientation(typedArray.getInt(0, 0));
            this.setUseDefaultMargins(typedArray.getBoolean(5, false));
            this.setAlignmentMode(typedArray.getInt(6, 1));
            this.setRowOrderPreserved(typedArray.getBoolean(2, true));
            this.setColumnOrderPreserved(typedArray.getBoolean(4, true));
            return;
        }
        finally {
            typedArray.recycle();
        }
    }

    static int adjust(int n, int n2) {
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(n + n2), View.MeasureSpec.getMode(n));
    }

    static <T> T[] append(T[] arrT, T[] arrT2) {
        Object[] arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), arrT.length + arrT2.length);
        System.arraycopy(arrT, 0, arrobject, 0, arrT.length);
        System.arraycopy(arrT2, 0, arrobject, arrT.length, arrT2.length);
        return arrobject;
    }

    static boolean canStretch(int n) {
        boolean bl = (n & 2) != 0;
        return bl;
    }

    private void checkLayoutParams(LayoutParams object, boolean bl) {
        String string2 = bl ? "column" : "row";
        object = bl ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
        Interval interval = ((Spec)object).span;
        if (interval.min != Integer.MIN_VALUE && interval.min < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" indices must be positive");
            GridLayout.handleInvalidParams(((StringBuilder)object).toString());
        }
        object = bl ? this.mHorizontalAxis : this.mVerticalAxis;
        int n = ((Axis)object).definedCount;
        if (n != Integer.MIN_VALUE) {
            if (interval.max > n) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" indices (start + span) mustn't exceed the ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" count");
                GridLayout.handleInvalidParams(((StringBuilder)object).toString());
            }
            if (interval.size() > n) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" span mustn't exceed the ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" count");
                GridLayout.handleInvalidParams(((StringBuilder)object).toString());
            }
        }
    }

    private static int clip(Interval interval, boolean bl, int n) {
        int n2 = interval.size();
        if (n == 0) {
            return n2;
        }
        int n3 = bl ? Math.min(interval.min, n) : 0;
        return Math.min(n2, n - n3);
    }

    private int computeLayoutParamsHashCode() {
        int n = 1;
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            n = n * 31 + ((LayoutParams)view.getLayoutParams()).hashCode();
        }
        return n;
    }

    private void consistencyCheck() {
        int n = this.mLastLayoutParamsHashCode;
        if (n == 0) {
            this.validateLayoutParams();
            this.mLastLayoutParamsHashCode = this.computeLayoutParamsHashCode();
        } else if (n != this.computeLayoutParamsHashCode()) {
            this.mPrinter.println("The fields of some layout parameters were modified in between layout operations. Check the javadoc for GridLayout.LayoutParams#rowSpec.");
            this.invalidateStructure();
            this.consistencyCheck();
        }
    }

    private static Alignment createSwitchingAlignment(Alignment alignment, final Alignment alignment2) {
        return new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                Alignment alignment = !view.isLayoutRtl() ? Alignment.this : alignment2;
                return alignment.getAlignmentValue(view, n, n2);
            }

            @Override
            int getGravityOffset(View view, int n) {
                Alignment alignment = !view.isLayoutRtl() ? Alignment.this : alignment2;
                return alignment.getGravityOffset(view, n);
            }
        };
    }

    private void drawLine(Canvas canvas, int n, int n2, int n3, int n4, Paint paint) {
        if (this.isLayoutRtl()) {
            int n5 = this.getWidth();
            canvas.drawLine(n5 - n, n2, n5 - n3, n4, paint);
        } else {
            canvas.drawLine(n, n2, n3, n4, paint);
        }
    }

    private static boolean fits(int[] arrn, int n, int n2, int n3) {
        if (n3 > arrn.length) {
            return false;
        }
        while (n2 < n3) {
            if (arrn[n2] > n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static Alignment getAlignment(int n, boolean bl) {
        int n2;
        int n3 = bl ? 7 : 112;
        if ((n = (n & n3) >> (n2 = bl ? 0 : 4)) != 1) {
            if (n != 3) {
                if (n != 5) {
                    if (n != 7) {
                        if (n != 8388611) {
                            if (n != 8388613) {
                                return UNDEFINED_ALIGNMENT;
                            }
                            return END;
                        }
                        return START;
                    }
                    return FILL;
                }
                Alignment alignment = bl ? RIGHT : BOTTOM;
                return alignment;
            }
            Alignment alignment = bl ? LEFT : TOP;
            return alignment;
        }
        return CENTER;
    }

    private int getDefaultMargin(View view, LayoutParams object, boolean bl, boolean bl2) {
        boolean bl3;
        block5 : {
            block6 : {
                Axis axis;
                boolean bl4;
                block4 : {
                    bl3 = this.mUseDefaultMargins;
                    bl4 = false;
                    if (!bl3) {
                        return 0;
                    }
                    object = bl ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                    axis = bl ? this.mHorizontalAxis : this.mVerticalAxis;
                    object = ((Spec)object).span;
                    bl3 = bl && this.isLayoutRtl() ? !bl2 : bl2;
                    if (!bl3) break block4;
                    bl3 = bl4;
                    if (((Interval)object).min != 0) break block5;
                    break block6;
                }
                bl3 = bl4;
                if (((Interval)object).max != axis.getCount()) break block5;
            }
            bl3 = true;
        }
        return this.getDefaultMargin(view, bl3, bl, bl2);
    }

    private int getDefaultMargin(View view, boolean bl, boolean bl2) {
        if (view.getClass() == Space.class) {
            return 0;
        }
        return this.mDefaultGap / 2;
    }

    private int getDefaultMargin(View view, boolean bl, boolean bl2, boolean bl3) {
        return this.getDefaultMargin(view, bl2, bl3);
    }

    private int getMargin(View object, boolean bl, boolean bl2) {
        if (this.mAlignmentMode == 1) {
            return this.getMargin1((View)object, bl, bl2);
        }
        Object object2 = bl ? this.mHorizontalAxis : this.mVerticalAxis;
        object2 = bl2 ? object2.getLeadingMargins() : object2.getTrailingMargins();
        object = this.getLayoutParams((View)object);
        object = bl ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
        object = ((Spec)object).span;
        int n = bl2 ? ((Interval)object).min : ((Interval)object).max;
        return object2[n];
    }

    private int getMeasurement(View view, boolean bl) {
        int n = bl ? view.getMeasuredWidth() : view.getMeasuredHeight();
        return n;
    }

    private int getTotalMargin(View view, boolean bl) {
        return this.getMargin(view, bl, true) + this.getMargin(view, bl, false);
    }

    private static void handleInvalidParams(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(". ");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void invalidateStructure() {
        this.mLastLayoutParamsHashCode = 0;
        this.mHorizontalAxis.invalidateStructure();
        this.mVerticalAxis.invalidateStructure();
        this.invalidateValues();
    }

    private void invalidateValues() {
        Axis axis = this.mHorizontalAxis;
        if (axis != null && this.mVerticalAxis != null) {
            axis.invalidateValues();
            this.mVerticalAxis.invalidateValues();
        }
    }

    static int max2(int[] arrn, int n) {
        int n2 = n;
        int n3 = arrn.length;
        for (n = 0; n < n3; ++n) {
            n2 = Math.max(n2, arrn[n]);
        }
        return n2;
    }

    private void measureChildWithMargins2(View view, int n, int n2, int n3, int n4) {
        n = GridLayout.getChildMeasureSpec(n, this.getTotalMargin(view, true), n3);
        view.measure(n, GridLayout.getChildMeasureSpec(n2, this.getTotalMargin(view, false), n4));
    }

    private void measureChildrenWithMargins(int n, int n2, boolean bl) {
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = this.getLayoutParams(view);
            if (bl) {
                this.measureChildWithMargins2(view, n, n2, layoutParams.width, layoutParams.height);
                continue;
            }
            boolean bl2 = this.mOrientation == 0;
            Object object = bl2 ? layoutParams.columnSpec : layoutParams.rowSpec;
            if (((Spec)object).getAbsoluteAlignment(bl2) != FILL) continue;
            Interval interval = object.span;
            object = bl2 ? this.mHorizontalAxis : this.mVerticalAxis;
            object = object.getLocations();
            int n4 = object[interval.max] - object[interval.min] - this.getTotalMargin(view, bl2);
            if (bl2) {
                this.measureChildWithMargins2(view, n, n2, n4, layoutParams.height);
                continue;
            }
            this.measureChildWithMargins2(view, n, n2, layoutParams.width, n4);
        }
    }

    private static void procrusteanFill(int[] arrn, int n, int n2, int n3) {
        int n4 = arrn.length;
        Arrays.fill(arrn, Math.min(n, n4), Math.min(n2, n4), n3);
    }

    private static void setCellGroup(LayoutParams layoutParams, int n, int n2, int n3, int n4) {
        layoutParams.setRowSpecSpan(new Interval(n, n + n2));
        layoutParams.setColumnSpecSpan(new Interval(n3, n3 + n4));
    }

    public static Spec spec(int n) {
        return GridLayout.spec(n, 1);
    }

    public static Spec spec(int n, float f) {
        return GridLayout.spec(n, 1, f);
    }

    public static Spec spec(int n, int n2) {
        return GridLayout.spec(n, n2, UNDEFINED_ALIGNMENT);
    }

    public static Spec spec(int n, int n2, float f) {
        return GridLayout.spec(n, n2, UNDEFINED_ALIGNMENT, f);
    }

    public static Spec spec(int n, int n2, Alignment alignment) {
        return GridLayout.spec(n, n2, alignment, 0.0f);
    }

    public static Spec spec(int n, int n2, Alignment alignment, float f) {
        boolean bl = n != Integer.MIN_VALUE;
        return new Spec(bl, n, n2, alignment, f);
    }

    public static Spec spec(int n, Alignment alignment) {
        return GridLayout.spec(n, 1, alignment);
    }

    public static Spec spec(int n, Alignment alignment, float f) {
        return GridLayout.spec(n, 1, alignment, f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateLayoutParams() {
        Object object = this;
        int n = ((GridLayout)object).mOrientation;
        int n2 = 0;
        boolean bl = n == 0;
        object = bl ? ((GridLayout)object).mHorizontalAxis : ((GridLayout)object).mVerticalAxis;
        if (((Axis)object).definedCount != Integer.MIN_VALUE) {
            n2 = ((Axis)object).definedCount;
        }
        int n3 = 0;
        int n4 = 0;
        int[] arrn = new int[n2];
        int n5 = 0;
        n = this.getChildCount();
        do {
            int n6;
            int n7;
            int n8;
            LayoutParams layoutParams;
            block14 : {
                block13 : {
                    int n9;
                    boolean bl2;
                    int n10;
                    block11 : {
                        block10 : {
                            block12 : {
                                if (n5 >= n) {
                                    return;
                                }
                                layoutParams = (LayoutParams)this.getChildAt(n5).getLayoutParams();
                                Spec spec = bl ? layoutParams.rowSpec : layoutParams.columnSpec;
                                Interval interval = spec.span;
                                boolean bl3 = spec.startDefined;
                                n6 = interval.size();
                                if (bl3) {
                                    n3 = interval.min;
                                }
                                spec = bl ? layoutParams.columnSpec : layoutParams.rowSpec;
                                interval = spec.span;
                                bl2 = spec.startDefined;
                                n8 = GridLayout.clip(interval, bl2, n2);
                                if (bl2) {
                                    n4 = interval.min;
                                }
                                if (n2 == 0) break block10;
                                n10 = n3;
                                n9 = n4;
                                n7 = n;
                                if (!bl3) break block11;
                                if (bl2) break block12;
                                n10 = n3;
                                n9 = n4;
                                n7 = n;
                                break block11;
                            }
                            n7 = n4;
                            n4 = n;
                            break block13;
                        }
                        n7 = n4;
                        break block14;
                    }
                    do {
                        n = n7;
                        n3 = ++n10;
                        n7 = ++n9;
                        n4 = n;
                        if (GridLayout.fits(arrn, n10, n9, n9 + n8)) break;
                        if (bl2) {
                            n7 = n;
                            continue;
                        }
                        if (n9 + n8 <= n2) {
                            n7 = n;
                            continue;
                        }
                        n9 = 0;
                        ++n10;
                        n7 = n;
                    } while (true);
                }
                GridLayout.procrusteanFill(arrn, n7, n7 + n8, n3 + n6);
                n = n4;
            }
            if (bl) {
                GridLayout.setCellGroup(layoutParams, n3, n6, n7, n8);
            } else {
                GridLayout.setCellGroup(layoutParams, n7, n8, n3, n6);
            }
            n4 = n7 + n8;
            ++n5;
        } while (true);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) {
            return false;
        }
        layoutParams = (LayoutParams)layoutParams;
        this.checkLayoutParams((LayoutParams)layoutParams, true);
        this.checkLayoutParams((LayoutParams)layoutParams, false);
        return true;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return GridLayout.class.getName();
    }

    public int getAlignmentMode() {
        return this.mAlignmentMode;
    }

    public int getColumnCount() {
        return this.mHorizontalAxis.getCount();
    }

    final LayoutParams getLayoutParams(View view) {
        return (LayoutParams)view.getLayoutParams();
    }

    int getMargin1(View view, boolean bl, boolean bl2) {
        int n;
        block0 : {
            LayoutParams layoutParams = this.getLayoutParams(view);
            n = bl ? (bl2 ? layoutParams.leftMargin : layoutParams.rightMargin) : (bl2 ? layoutParams.topMargin : layoutParams.bottomMargin);
            if (n != Integer.MIN_VALUE) break block0;
            n = this.getDefaultMargin(view, layoutParams, bl, bl2);
        }
        return n;
    }

    final int getMeasurementIncludingMargin(View view, boolean bl) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        return this.getMeasurement(view, bl) + this.getTotalMargin(view, bl);
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public Printer getPrinter() {
        return this.mPrinter;
    }

    public int getRowCount() {
        return this.mVerticalAxis.getCount();
    }

    public boolean getUseDefaultMargins() {
        return this.mUseDefaultMargins;
    }

    public boolean isColumnOrderPreserved() {
        return this.mHorizontalAxis.isOrderPreserved();
    }

    public boolean isRowOrderPreserved() {
        return this.mVerticalAxis.isOrderPreserved();
    }

    @Override
    protected void onChildVisibilityChanged(View view, int n, int n2) {
        super.onChildVisibilityChanged(view, n, n2);
        if (n == 8 || n2 == 8) {
            this.invalidateStructure();
        }
    }

    @Override
    protected void onDebugDraw(Canvas canvas) {
        int n;
        int n2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(50, 255, 255, 255));
        int[] arrn = this.getOpticalInsets();
        int n3 = this.getPaddingTop() + arrn.top;
        int n4 = this.getPaddingLeft() + arrn.left;
        int n5 = this.getWidth();
        int n6 = this.getPaddingRight();
        int n7 = arrn.right;
        int n8 = this.getHeight();
        int n9 = this.getPaddingBottom();
        int n10 = arrn.bottom;
        arrn = this.mHorizontalAxis.locations;
        if (arrn != null) {
            n2 = arrn.length;
            for (n = 0; n < n2; ++n) {
                int n11 = n4 + arrn[n];
                this.drawLine(canvas, n11, n3, n11, n8 - n9 - n10, paint);
            }
        }
        if ((arrn = this.mVerticalAxis.locations) != null) {
            n = arrn.length;
            for (n2 = 0; n2 < n; ++n2) {
                n10 = n3 + arrn[n2];
                this.drawLine(canvas, n4, n10, n5 - n6 - n7, n10, paint);
            }
        }
        super.onDebugDraw(canvas);
    }

    @Override
    protected void onDebugDrawMargins(Canvas canvas, Paint paint) {
        LayoutParams layoutParams = new LayoutParams();
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            layoutParams.setMargins(this.getMargin1(view, true, true), this.getMargin1(view, false, true), this.getMargin1(view, true, false), this.getMargin1(view, false, false));
            layoutParams.onDebugDraw(view, canvas, paint);
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int[] arrn = this;
        this.consistencyCheck();
        int n5 = n3 - n;
        n2 = n4 - n2;
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingRight();
        n = this.getPaddingBottom();
        arrn.mHorizontalAxis.layout(n5 - n6 - n8);
        arrn.mVerticalAxis.layout(n2 - n7 - n);
        int[] arrn2 = arrn.mHorizontalAxis.getLocations();
        arrn = arrn.mVerticalAxis.getLocations();
        int n9 = this.getChildCount();
        n3 = 0;
        do {
            int[] arrn3 = this;
            if (n3 >= n9) break;
            View view = arrn3.getChildAt(n3);
            if (view.getVisibility() != 8) {
                Object object = arrn3.getLayoutParams(view);
                Object object2 = ((LayoutParams)object).columnSpec;
                object = ((LayoutParams)object).rowSpec;
                Object object3 = ((Spec)object2).span;
                Object object4 = ((Spec)object).span;
                n4 = arrn2[((Interval)object3).min];
                int n10 = arrn[((Interval)object4).min];
                int n11 = arrn2[((Interval)object3).max];
                int n12 = arrn[((Interval)object4).max];
                int n13 = n11 - n4;
                int n14 = n12 - n10;
                int n15 = GridLayout.super.getMeasurement(view, true);
                int n16 = GridLayout.super.getMeasurement(view, false);
                object2 = ((Spec)object2).getAbsoluteAlignment(true);
                object3 = ((Spec)object).getAbsoluteAlignment(false);
                object4 = arrn3.mHorizontalAxis.getGroupBounds().getValue(n3);
                object = arrn3.mVerticalAxis.getGroupBounds().getValue(n3);
                int n17 = ((Alignment)object2).getGravityOffset(view, n13 - ((Bounds)object4).size(true));
                n12 = ((Alignment)object3).getGravityOffset(view, n14 - ((Bounds)object).size(true));
                int n18 = GridLayout.super.getMargin(view, true, true);
                n11 = GridLayout.super.getMargin(view, false, true);
                int n19 = GridLayout.super.getMargin(view, true, false);
                int n20 = GridLayout.super.getMargin(view, false, false);
                int n21 = n18 + n19;
                int n22 = n11 + n20;
                int n23 = ((Bounds)object4).getOffset((GridLayout)this, view, (Alignment)object2, n15 + n21, true);
                n20 = ((Bounds)object).getOffset((GridLayout)this, view, (Alignment)object3, n16 + n22, false);
                n13 = ((Alignment)object2).getSizeInCell(view, n15, n13 - n21);
                n14 = ((Alignment)object3).getSizeInCell(view, n16, n14 - n22);
                n4 = n4 + n17 + n23;
                n4 = !this.isLayoutRtl() ? n6 + n18 + n4 : n5 - n13 - n8 - n19 - n4;
                n10 = n7 + n10 + n12 + n20 + n11;
                if (n13 != view.getMeasuredWidth() || n14 != view.getMeasuredHeight()) {
                    view.measure(View.MeasureSpec.makeMeasureSpec(n13, 1073741824), View.MeasureSpec.makeMeasureSpec(n14, 1073741824));
                }
                view.layout(n4, n10, n4 + n13, n10 + n14);
            }
            ++n3;
        } while (true);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        this.consistencyCheck();
        this.invalidateValues();
        int n5 = this.getPaddingLeft() + this.getPaddingRight();
        int n6 = this.getPaddingTop() + this.getPaddingBottom();
        int n7 = GridLayout.adjust(n, -n5);
        int n8 = GridLayout.adjust(n2, -n6);
        this.measureChildrenWithMargins(n7, n8, true);
        if (this.mOrientation == 0) {
            n4 = this.mHorizontalAxis.getMeasure(n7);
            this.measureChildrenWithMargins(n7, n8, false);
            n3 = this.mVerticalAxis.getMeasure(n8);
        } else {
            n3 = this.mVerticalAxis.getMeasure(n8);
            this.measureChildrenWithMargins(n7, n8, false);
            n4 = this.mHorizontalAxis.getMeasure(n7);
        }
        n4 = Math.max(n4 + n5, this.getSuggestedMinimumWidth());
        n3 = Math.max(n3 + n6, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(GridLayout.resolveSizeAndState(n4, n, 0), GridLayout.resolveSizeAndState(n3, n2, 0));
    }

    @Override
    protected void onSetLayoutParams(View view, ViewGroup.LayoutParams layoutParams) {
        super.onSetLayoutParams(view, layoutParams);
        if (!this.checkLayoutParams(layoutParams)) {
            GridLayout.handleInvalidParams("supplied LayoutParams are of the wrong type");
        }
        this.invalidateStructure();
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        this.invalidateStructure();
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.invalidateStructure();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        this.invalidateValues();
    }

    public void setAlignmentMode(int n) {
        this.mAlignmentMode = n;
        this.requestLayout();
    }

    public void setColumnCount(int n) {
        this.mHorizontalAxis.setCount(n);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setColumnOrderPreserved(boolean bl) {
        this.mHorizontalAxis.setOrderPreserved(bl);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setOrientation(int n) {
        if (this.mOrientation != n) {
            this.mOrientation = n;
            this.invalidateStructure();
            this.requestLayout();
        }
    }

    public void setPrinter(Printer printer) {
        if (printer == null) {
            printer = NO_PRINTER;
        }
        this.mPrinter = printer;
    }

    public void setRowCount(int n) {
        this.mVerticalAxis.setCount(n);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setRowOrderPreserved(boolean bl) {
        this.mVerticalAxis.setOrderPreserved(bl);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setUseDefaultMargins(boolean bl) {
        this.mUseDefaultMargins = bl;
        this.requestLayout();
    }

    public static abstract class Alignment {
        Alignment() {
        }

        abstract int getAlignmentValue(View var1, int var2, int var3);

        Bounds getBounds() {
            return new Bounds();
        }

        abstract int getGravityOffset(View var1, int var2);

        int getSizeInCell(View view, int n, int n2) {
            return n;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AlignmentMode {
    }

    static final class Arc {
        public final Interval span;
        public boolean valid = true;
        public final MutableInt value;

        public Arc(Interval interval, MutableInt mutableInt) {
            this.span = interval;
            this.value = mutableInt;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.span);
            stringBuilder.append(" ");
            String string2 = !this.valid ? "+>" : "->";
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        }
    }

    static final class Assoc<K, V>
    extends ArrayList<Pair<K, V>> {
        private final Class<K> keyType;
        private final Class<V> valueType;

        private Assoc(Class<K> class_, Class<V> class_2) {
            this.keyType = class_;
            this.valueType = class_2;
        }

        public static <K, V> Assoc<K, V> of(Class<K> class_, Class<V> class_2) {
            return new Assoc<K, V>(class_, class_2);
        }

        public PackedMap<K, V> pack() {
            int n = this.size();
            Object[] arrobject = (Object[])Array.newInstance(this.keyType, n);
            Object[] arrobject2 = (Object[])Array.newInstance(this.valueType, n);
            for (int i = 0; i < n; ++i) {
                arrobject[i] = ((Pair)this.get((int)i)).first;
                arrobject2[i] = ((Pair)this.get((int)i)).second;
            }
            return new PackedMap(arrobject, arrobject2);
        }

        public void put(K k, V v) {
            this.add(Pair.create(k, v));
        }
    }

    final class Axis {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int COMPLETE = 2;
        private static final int NEW = 0;
        private static final int PENDING = 1;
        public Arc[] arcs;
        public boolean arcsValid = false;
        PackedMap<Interval, MutableInt> backwardLinks;
        public boolean backwardLinksValid = false;
        public int definedCount = Integer.MIN_VALUE;
        public int[] deltas;
        PackedMap<Interval, MutableInt> forwardLinks;
        public boolean forwardLinksValid = false;
        PackedMap<Spec, Bounds> groupBounds;
        public boolean groupBoundsValid = false;
        public boolean hasWeights;
        public boolean hasWeightsValid = false;
        public final boolean horizontal;
        public int[] leadingMargins;
        public boolean leadingMarginsValid = false;
        public int[] locations;
        public boolean locationsValid = false;
        private int maxIndex = Integer.MIN_VALUE;
        boolean orderPreserved = true;
        private MutableInt parentMax = new MutableInt(-100000);
        private MutableInt parentMin = new MutableInt(0);
        public int[] trailingMargins;
        public boolean trailingMarginsValid = false;

        private Axis(boolean bl) {
            this.horizontal = bl;
        }

        private void addComponentSizes(List<Arc> list, PackedMap<Interval, MutableInt> packedMap) {
            for (int i = 0; i < ((Interval[])packedMap.keys).length; ++i) {
                this.include(list, ((Interval[])packedMap.keys)[i], ((MutableInt[])packedMap.values)[i], false);
            }
        }

        private String arcsToString(List<Arc> object) {
            String string2 = this.horizontal ? "x" : "y";
            Object object2 = new StringBuilder();
            boolean bl = true;
            Iterator<Arc> iterator = object.iterator();
            object = object2;
            while (iterator.hasNext()) {
                object2 = iterator.next();
                if (bl) {
                    bl = false;
                } else {
                    object = ((StringBuilder)object).append(", ");
                }
                int n = object2.span.min;
                int n2 = object2.span.max;
                int n3 = object2.value.value;
                if (n < n2) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append("-");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(">=");
                    ((StringBuilder)object2).append(n3);
                    object2 = ((StringBuilder)object2).toString();
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append("-");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append("<=");
                    ((StringBuilder)object2).append(-n3);
                    object2 = ((StringBuilder)object2).toString();
                }
                ((StringBuilder)object).append((String)object2);
            }
            return ((StringBuilder)object).toString();
        }

        private int calculateMaxIndex() {
            int n;
            block1 : {
                n = -1;
                int n2 = GridLayout.this.getChildCount();
                for (int i = 0; i < n2; ++i) {
                    Object object = GridLayout.this.getChildAt(i);
                    object = GridLayout.this.getLayoutParams((View)object);
                    object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                    object = ((Spec)object).span;
                    n = Math.max(Math.max(Math.max(n, ((Interval)object).min), ((Interval)object).max), ((Interval)object).size());
                }
                if (n != -1) break block1;
                n = Integer.MIN_VALUE;
            }
            return n;
        }

        private float calculateTotalWeight() {
            float f = 0.0f;
            int n = GridLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                Object object = GridLayout.this.getChildAt(i);
                if (((View)object).getVisibility() == 8) continue;
                object = GridLayout.this.getLayoutParams((View)object);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                f += ((Spec)object).weight;
            }
            return f;
        }

        private void computeArcs() {
            this.getForwardLinks();
            this.getBackwardLinks();
        }

        private void computeGroupBounds() {
            int n;
            Object object = (Bounds[])this.groupBounds.values;
            for (n = 0; n < ((Bounds[])object).length; ++n) {
                object[n].reset();
            }
            int n2 = GridLayout.this.getChildCount();
            for (n = 0; n < n2; ++n) {
                View view = GridLayout.this.getChildAt(n);
                object = GridLayout.this.getLayoutParams(view);
                object = this.horizontal ? object.columnSpec : object.rowSpec;
                int n3 = GridLayout.this.getMeasurementIncludingMargin(view, this.horizontal);
                int n4 = object.weight == 0.0f ? 0 : this.getDeltas()[n];
                this.groupBounds.getValue(n).include(GridLayout.this, view, (Spec)object, this, n3 + n4);
            }
        }

        private boolean computeHasWeights() {
            int n = GridLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                Object object = GridLayout.this.getChildAt(i);
                if (((View)object).getVisibility() == 8) continue;
                object = GridLayout.this.getLayoutParams((View)object);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                if (((Spec)object).weight == 0.0f) continue;
                return true;
            }
            return false;
        }

        private void computeLinks(PackedMap<Interval, MutableInt> packedMap, boolean bl) {
            int n;
            Object object = (MutableInt[])packedMap.values;
            for (n = 0; n < ((MutableInt[])object).length; ++n) {
                object[n].reset();
            }
            Bounds[] arrbounds = (Bounds[])this.getGroupBounds().values;
            for (n = 0; n < arrbounds.length; ++n) {
                int n2 = arrbounds[n].size(bl);
                object = packedMap.getValue(n);
                int n3 = object.value;
                if (!bl) {
                    n2 = -n2;
                }
                object.value = Math.max(n3, n2);
            }
        }

        private void computeLocations(int[] arrn) {
            if (!this.hasWeights()) {
                this.solve(arrn);
            } else {
                this.solveAndDistributeSpace(arrn);
            }
            if (!this.orderPreserved) {
                int n = arrn[0];
                int n2 = arrn.length;
                for (int i = 0; i < n2; ++i) {
                    arrn[i] = arrn[i] - n;
                }
            }
        }

        private void computeMargins(boolean bl) {
            int[] arrn = bl ? this.leadingMargins : this.trailingMargins;
            int n = GridLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = GridLayout.this.getChildAt(i);
                if (view.getVisibility() == 8) continue;
                Object object = GridLayout.this.getLayoutParams(view);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                object = ((Spec)object).span;
                int n2 = bl ? ((Interval)object).min : ((Interval)object).max;
                arrn[n2] = Math.max(arrn[n2], GridLayout.this.getMargin1(view, this.horizontal, bl));
            }
        }

        private Arc[] createArcs() {
            int n;
            ArrayList<Arc> arrayList = new ArrayList<Arc>();
            ArrayList<Arc> arrayList2 = new ArrayList<Arc>();
            this.addComponentSizes(arrayList, this.getForwardLinks());
            this.addComponentSizes(arrayList2, this.getBackwardLinks());
            if (this.orderPreserved) {
                for (n = 0; n < this.getCount(); ++n) {
                    this.include(arrayList, new Interval(n, n + 1), new MutableInt(0));
                }
            }
            n = this.getCount();
            this.include(arrayList, new Interval(0, n), this.parentMin, false);
            this.include(arrayList2, new Interval(n, 0), this.parentMax, false);
            return GridLayout.append(this.topologicalSort(arrayList), this.topologicalSort(arrayList2));
        }

        private PackedMap<Spec, Bounds> createGroupBounds() {
            Assoc<Spec, Bounds> assoc = Assoc.of(Spec.class, Bounds.class);
            int n = GridLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                Object object = GridLayout.this.getChildAt(i);
                object = GridLayout.this.getLayoutParams((View)object);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                assoc.put((Spec)object, ((Spec)object).getAbsoluteAlignment(this.horizontal).getBounds());
            }
            return assoc.pack();
        }

        private PackedMap<Interval, MutableInt> createLinks(boolean bl) {
            Assoc<Interval, MutableInt> assoc = Assoc.of(Interval.class, MutableInt.class);
            Spec[] arrspec = (Spec[])this.getGroupBounds().keys;
            int n = arrspec.length;
            for (int i = 0; i < n; ++i) {
                Interval interval = bl ? arrspec[i].span : arrspec[i].span.inverse();
                assoc.put(interval, new MutableInt());
            }
            return assoc.pack();
        }

        private PackedMap<Interval, MutableInt> getBackwardLinks() {
            if (this.backwardLinks == null) {
                this.backwardLinks = this.createLinks(false);
            }
            if (!this.backwardLinksValid) {
                this.computeLinks(this.backwardLinks, false);
                this.backwardLinksValid = true;
            }
            return this.backwardLinks;
        }

        private PackedMap<Interval, MutableInt> getForwardLinks() {
            if (this.forwardLinks == null) {
                this.forwardLinks = this.createLinks(true);
            }
            if (!this.forwardLinksValid) {
                this.computeLinks(this.forwardLinks, true);
                this.forwardLinksValid = true;
            }
            return this.forwardLinks;
        }

        private int getMaxIndex() {
            if (this.maxIndex == Integer.MIN_VALUE) {
                this.maxIndex = Math.max(0, this.calculateMaxIndex());
            }
            return this.maxIndex;
        }

        private int getMeasure(int n, int n2) {
            this.setParentConstraints(n, n2);
            return this.size(this.getLocations());
        }

        private boolean hasWeights() {
            if (!this.hasWeightsValid) {
                this.hasWeights = this.computeHasWeights();
                this.hasWeightsValid = true;
            }
            return this.hasWeights;
        }

        private void include(List<Arc> list, Interval interval, MutableInt mutableInt) {
            this.include(list, interval, mutableInt, true);
        }

        private void include(List<Arc> list, Interval interval, MutableInt mutableInt, boolean bl) {
            if (interval.size() == 0) {
                return;
            }
            if (bl) {
                Iterator<Arc> iterator = list.iterator();
                while (iterator.hasNext()) {
                    if (!iterator.next().span.equals(interval)) continue;
                    return;
                }
            }
            list.add(new Arc(interval, mutableInt));
        }

        private void init(int[] arrn) {
            Arrays.fill(arrn, 0);
        }

        private void logError(String string2, Arc[] object, boolean[] object2) {
            ArrayList<Arc> arrayList = new ArrayList<Arc>();
            ArrayList<Arc> arrayList2 = new ArrayList<Arc>();
            for (int i = 0; i < ((Arc[])object).length; ++i) {
                Arc arc = object[i];
                if (object2[i]) {
                    arrayList.add(arc);
                }
                if (arc.valid) continue;
                arrayList2.add(arc);
            }
            object = GridLayout.this.mPrinter;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" constraints: ");
            ((StringBuilder)object2).append(this.arcsToString(arrayList));
            ((StringBuilder)object2).append(" are inconsistent; permanently removing: ");
            ((StringBuilder)object2).append(this.arcsToString(arrayList2));
            ((StringBuilder)object2).append(". ");
            object.println(((StringBuilder)object2).toString());
        }

        private boolean relax(int[] arrn, Arc arc) {
            if (!arc.valid) {
                return false;
            }
            Interval interval = arc.span;
            int n = interval.min;
            int n2 = arc.value.value;
            int n3 = interval.max;
            if ((n = arrn[n] + n2) > arrn[n3]) {
                arrn[n3] = n;
                return true;
            }
            return false;
        }

        private void setParentConstraints(int n, int n2) {
            this.parentMin.value = n;
            this.parentMax.value = -n2;
            this.locationsValid = false;
        }

        private void shareOutDelta(int n, float f) {
            Arrays.fill(this.deltas, 0);
            int n2 = 0;
            int n3 = GridLayout.this.getChildCount();
            float f2 = f;
            int n4 = n;
            for (n = n2; n < n3; ++n) {
                Object object = GridLayout.this.getChildAt(n);
                if (((View)object).getVisibility() == 8) {
                    n2 = n4;
                    f = f2;
                } else {
                    object = GridLayout.this.getLayoutParams((View)object);
                    object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                    float f3 = ((Spec)object).weight;
                    n2 = n4;
                    f = f2;
                    if (f3 != 0.0f) {
                        this.deltas[n] = n2 = Math.round((float)n4 * f3 / f2);
                        n2 = n4 - n2;
                        f = f2 - f3;
                    }
                }
                n4 = n2;
                f2 = f;
            }
        }

        private int size(int[] arrn) {
            return arrn[this.getCount()];
        }

        private boolean solve(int[] arrn) {
            return this.solve(this.getArcs(), arrn);
        }

        private boolean solve(Arc[] arrarc, int[] arrn) {
            return this.solve(arrarc, arrn, true);
        }

        private boolean solve(Arc[] arrarc, int[] arrn, boolean bl) {
            String string2 = this.horizontal ? "horizontal" : "vertical";
            int n = this.getCount() + 1;
            boolean[] arrbl = null;
            block0 : for (int i = 0; i < arrarc.length; ++i) {
                int n2;
                int n3;
                int n4;
                this.init(arrn);
                for (n3 = 0; n3 < n; ++n3) {
                    n2 = 0;
                    int n5 = arrarc.length;
                    for (n4 = 0; n4 < n5; ++n4) {
                        n2 |= this.relax(arrn, arrarc[n4]);
                    }
                    if (n2 != 0) continue;
                    if (arrbl != null) {
                        this.logError(string2, arrarc, arrbl);
                    }
                    return true;
                }
                if (!bl) {
                    return false;
                }
                boolean[] arrbl2 = new boolean[arrarc.length];
                for (n3 = 0; n3 < n; ++n3) {
                    n2 = arrarc.length;
                    for (n4 = 0; n4 < n2; ++n4) {
                        arrbl2[n4] = arrbl2[n4] | this.relax(arrn, arrarc[n4]);
                    }
                }
                if (i == 0) {
                    arrbl = arrbl2;
                }
                for (n3 = 0; n3 < arrarc.length; ++n3) {
                    if (!arrbl2[n3]) continue;
                    Arc arc = arrarc[n3];
                    if (arc.span.min < arc.span.max) continue;
                    arc.valid = false;
                    continue block0;
                }
            }
            return true;
        }

        private void solveAndDistributeSpace(int[] arrn) {
            Arrays.fill(this.getDeltas(), 0);
            this.solve(arrn);
            int n = this.parentMin.value * GridLayout.this.getChildCount() + 1;
            if (n < 2) {
                return;
            }
            int n2 = 0;
            float f = this.calculateTotalWeight();
            int n3 = -1;
            boolean bl = true;
            while (n2 < n) {
                int n4 = (int)(((long)n2 + (long)n) / 2L);
                this.invalidateValues();
                this.shareOutDelta(n4, f);
                bl = this.solve(this.getArcs(), arrn, false);
                if (bl) {
                    n3 = n4;
                    n2 = n4 + 1;
                    continue;
                }
                n = n4;
            }
            if (n3 > 0 && !bl) {
                this.invalidateValues();
                this.shareOutDelta(n3, f);
                this.solve(arrn);
            }
        }

        private Arc[] topologicalSort(List<Arc> list) {
            return this.topologicalSort(list.toArray(new Arc[list.size()]));
        }

        private Arc[] topologicalSort(final Arc[] arrarc) {
            return new Object(){
                static final /* synthetic */ boolean $assertionsDisabled = false;
                Arc[][] arcsByVertex;
                int cursor;
                Arc[] result;
                int[] visited;
                {
                    Axis.this = arrarc;
                    this.result = new Arc[Axis.this.length];
                    this.cursor = this.result.length - 1;
                    this.arcsByVertex = Axis.this.groupArcsByFirstVertex(Axis.this);
                    this.visited = new int[Axis.this.getCount() + 1];
                }

                Arc[] sort() {
                    int n = this.arcsByVertex.length;
                    for (int i = 0; i < n; ++i) {
                        this.walk(i);
                    }
                    return this.result;
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                void walk(int n) {
                    int[] arrn = this.visited;
                    int n2 = arrn[n];
                    if (n2 != 0) {
                        if (n2 == 1) return;
                    }
                    arrn[n] = 1;
                    for (Arc arc : this.arcsByVertex[n]) {
                        this.walk(arc.span.max);
                        Arc[] arrarc2 = this.result;
                        int n3 = this.cursor;
                        this.cursor = n3 - 1;
                        arrarc2[n3] = arc;
                    }
                    this.visited[n] = 2;
                }
            }.sort();
        }

        public Arc[] getArcs() {
            if (this.arcs == null) {
                this.arcs = this.createArcs();
            }
            if (!this.arcsValid) {
                this.computeArcs();
                this.arcsValid = true;
            }
            return this.arcs;
        }

        public int getCount() {
            return Math.max(this.definedCount, this.getMaxIndex());
        }

        public int[] getDeltas() {
            if (this.deltas == null) {
                this.deltas = new int[GridLayout.this.getChildCount()];
            }
            return this.deltas;
        }

        public PackedMap<Spec, Bounds> getGroupBounds() {
            if (this.groupBounds == null) {
                this.groupBounds = this.createGroupBounds();
            }
            if (!this.groupBoundsValid) {
                this.computeGroupBounds();
                this.groupBoundsValid = true;
            }
            return this.groupBounds;
        }

        public int[] getLeadingMargins() {
            if (this.leadingMargins == null) {
                this.leadingMargins = new int[this.getCount() + 1];
            }
            if (!this.leadingMarginsValid) {
                this.computeMargins(true);
                this.leadingMarginsValid = true;
            }
            return this.leadingMargins;
        }

        public int[] getLocations() {
            if (this.locations == null) {
                this.locations = new int[this.getCount() + 1];
            }
            if (!this.locationsValid) {
                this.computeLocations(this.locations);
                this.locationsValid = true;
            }
            return this.locations;
        }

        public int getMeasure(int n) {
            int n2 = View.MeasureSpec.getMode(n);
            n = View.MeasureSpec.getSize(n);
            if (n2 != Integer.MIN_VALUE) {
                if (n2 != 0) {
                    if (n2 != 1073741824) {
                        return 0;
                    }
                    return this.getMeasure(n, n);
                }
                return this.getMeasure(0, 100000);
            }
            return this.getMeasure(0, n);
        }

        public int[] getTrailingMargins() {
            if (this.trailingMargins == null) {
                this.trailingMargins = new int[this.getCount() + 1];
            }
            if (!this.trailingMarginsValid) {
                this.computeMargins(false);
                this.trailingMarginsValid = true;
            }
            return this.trailingMargins;
        }

        Arc[][] groupArcsByFirstVertex(Arc[] arrarc) {
            int n;
            int n2 = this.getCount() + 1;
            Arc[][] arrarc2 = new Arc[n2][];
            int[] arrn = new int[n2];
            int n3 = arrarc.length;
            int n4 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                n = arrarc[n2].span.min;
                arrn[n] = arrn[n] + 1;
            }
            for (n2 = 0; n2 < arrn.length; ++n2) {
                arrarc2[n2] = new Arc[arrn[n2]];
            }
            Arrays.fill(arrn, 0);
            n3 = arrarc.length;
            for (n2 = n4; n2 < n3; ++n2) {
                Arc arc = arrarc[n2];
                n = arc.span.min;
                Arc[] arrarc3 = arrarc2[n];
                n4 = arrn[n];
                arrn[n] = n4 + 1;
                arrarc3[n4] = arc;
            }
            return arrarc2;
        }

        public void invalidateStructure() {
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBounds = null;
            this.forwardLinks = null;
            this.backwardLinks = null;
            this.leadingMargins = null;
            this.trailingMargins = null;
            this.arcs = null;
            this.locations = null;
            this.deltas = null;
            this.hasWeightsValid = false;
            this.invalidateValues();
        }

        public void invalidateValues() {
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
        }

        public boolean isOrderPreserved() {
            return this.orderPreserved;
        }

        public void layout(int n) {
            this.setParentConstraints(n, n);
            this.getLocations();
        }

        public void setCount(int n) {
            if (n != Integer.MIN_VALUE && n < this.getMaxIndex()) {
                StringBuilder stringBuilder = new StringBuilder();
                String string2 = this.horizontal ? "column" : "row";
                stringBuilder.append(string2);
                stringBuilder.append("Count must be greater than or equal to the maximum of all grid indices (and spans) defined in the LayoutParams of each child");
                GridLayout.handleInvalidParams(stringBuilder.toString());
            }
            this.definedCount = n;
        }

        public void setOrderPreserved(boolean bl) {
            this.orderPreserved = bl;
            this.invalidateStructure();
        }

    }

    static class Bounds {
        public int after;
        public int before;
        public int flexibility;

        private Bounds() {
            this.reset();
        }

        protected int getOffset(GridLayout gridLayout, View view, Alignment alignment, int n, boolean bl) {
            return this.before - alignment.getAlignmentValue(view, n, gridLayout.getLayoutMode());
        }

        protected void include(int n, int n2) {
            this.before = Math.max(this.before, n);
            this.after = Math.max(this.after, n2);
        }

        protected final void include(GridLayout gridLayout, View view, Spec spec, Axis axis, int n) {
            this.flexibility &= spec.getFlexibility();
            boolean bl = axis.horizontal;
            int n2 = spec.getAbsoluteAlignment(axis.horizontal).getAlignmentValue(view, n, gridLayout.getLayoutMode());
            this.include(n2, n - n2);
        }

        protected void reset() {
            this.before = Integer.MIN_VALUE;
            this.after = Integer.MIN_VALUE;
            this.flexibility = 2;
        }

        protected int size(boolean bl) {
            if (!bl && GridLayout.canStretch(this.flexibility)) {
                return 100000;
            }
            return this.before + this.after;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bounds{before=");
            stringBuilder.append(this.before);
            stringBuilder.append(", after=");
            stringBuilder.append(this.after);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    static final class Interval {
        public final int max;
        public final int min;

        public Interval(int n, int n2) {
            this.min = n;
            this.max = n2;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (Interval)object;
                if (this.max != ((Interval)object).max) {
                    return false;
                }
                return this.min == ((Interval)object).min;
            }
            return false;
        }

        public int hashCode() {
            return this.min * 31 + this.max;
        }

        Interval inverse() {
            return new Interval(this.max, this.min);
        }

        int size() {
            return this.max - this.min;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.min);
            stringBuilder.append(", ");
            stringBuilder.append(this.max);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int BOTTOM_MARGIN = 6;
        private static final int COLUMN = 1;
        private static final int COLUMN_SPAN = 4;
        private static final int COLUMN_WEIGHT = 6;
        private static final int DEFAULT_COLUMN = Integer.MIN_VALUE;
        private static final int DEFAULT_HEIGHT = -2;
        private static final int DEFAULT_MARGIN = Integer.MIN_VALUE;
        private static final int DEFAULT_ROW = Integer.MIN_VALUE;
        private static final Interval DEFAULT_SPAN = new Interval(Integer.MIN_VALUE, -2147483647);
        private static final int DEFAULT_SPAN_SIZE = DEFAULT_SPAN.size();
        private static final int DEFAULT_WIDTH = -2;
        private static final int GRAVITY = 0;
        private static final int LEFT_MARGIN = 3;
        private static final int MARGIN = 2;
        private static final int RIGHT_MARGIN = 5;
        private static final int ROW = 2;
        private static final int ROW_SPAN = 3;
        private static final int ROW_WEIGHT = 5;
        private static final int TOP_MARGIN = 4;
        public Spec columnSpec = Spec.UNDEFINED;
        public Spec rowSpec = Spec.UNDEFINED;

        public LayoutParams() {
            this(Spec.UNDEFINED, Spec.UNDEFINED);
        }

        private LayoutParams(int n, int n2, int n3, int n4, int n5, int n6, Spec spec, Spec spec2) {
            super(n, n2);
            this.setMargins(n3, n4, n5, n6);
            this.rowSpec = spec;
            this.columnSpec = spec2;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.reInitSuper(context, attributeSet);
            this.init(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.rowSpec = layoutParams.rowSpec;
            this.columnSpec = layoutParams.columnSpec;
        }

        public LayoutParams(Spec spec, Spec spec2) {
            this(-2, -2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, spec, spec2);
        }

        private void init(Context object, AttributeSet attributeSet) {
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.GridLayout_Layout);
            try {
                int n = ((TypedArray)object).getInt(0, 0);
                int n2 = ((TypedArray)object).getInt(1, Integer.MIN_VALUE);
                int n3 = ((TypedArray)object).getInt(4, DEFAULT_SPAN_SIZE);
                float f = ((TypedArray)object).getFloat(6, 0.0f);
                this.columnSpec = GridLayout.spec(n2, n3, GridLayout.getAlignment(n, true), f);
                n2 = ((TypedArray)object).getInt(2, Integer.MIN_VALUE);
                n3 = ((TypedArray)object).getInt(3, DEFAULT_SPAN_SIZE);
                f = ((TypedArray)object).getFloat(5, 0.0f);
                this.rowSpec = GridLayout.spec(n2, n3, GridLayout.getAlignment(n, false), f);
                return;
            }
            finally {
                ((TypedArray)object).recycle();
            }
        }

        private void reInitSuper(Context context, AttributeSet object) {
            object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.ViewGroup_MarginLayout);
            try {
                int n = ((TypedArray)object).getDimensionPixelSize(2, Integer.MIN_VALUE);
                this.leftMargin = ((TypedArray)object).getDimensionPixelSize(3, n);
                this.topMargin = ((TypedArray)object).getDimensionPixelSize(4, n);
                this.rightMargin = ((TypedArray)object).getDimensionPixelSize(5, n);
                this.bottomMargin = ((TypedArray)object).getDimensionPixelSize(6, n);
                return;
            }
            finally {
                ((TypedArray)object).recycle();
            }
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (LayoutParams)object;
                if (!this.columnSpec.equals(((LayoutParams)object).columnSpec)) {
                    return false;
                }
                return this.rowSpec.equals(((LayoutParams)object).rowSpec);
            }
            return false;
        }

        public int hashCode() {
            return this.rowSpec.hashCode() * 31 + this.columnSpec.hashCode();
        }

        @Override
        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = typedArray.getLayoutDimension(n, -2);
            this.height = typedArray.getLayoutDimension(n2, -2);
        }

        final void setColumnSpecSpan(Interval interval) {
            this.columnSpec = this.columnSpec.copyWriteSpan(interval);
        }

        public void setGravity(int n) {
            this.rowSpec = this.rowSpec.copyWriteAlignment(GridLayout.getAlignment(n, false));
            this.columnSpec = this.columnSpec.copyWriteAlignment(GridLayout.getAlignment(n, true));
        }

        final void setRowSpecSpan(Interval interval) {
            this.rowSpec = this.rowSpec.copyWriteSpan(interval);
        }
    }

    static final class MutableInt {
        public int value;

        public MutableInt() {
            this.reset();
        }

        public MutableInt(int n) {
            this.value = n;
        }

        public void reset() {
            this.value = Integer.MIN_VALUE;
        }

        public String toString() {
            return Integer.toString(this.value);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Orientation {
    }

    static final class PackedMap<K, V> {
        public final int[] index;
        public final K[] keys;
        public final V[] values;

        private PackedMap(K[] arrK, V[] arrV) {
            this.index = PackedMap.createIndex(arrK);
            this.keys = PackedMap.compact(arrK, this.index);
            this.values = PackedMap.compact(arrV, this.index);
        }

        private static <K> K[] compact(K[] arrK, int[] arrn) {
            int n = arrK.length;
            Object[] arrobject = (Object[])Array.newInstance(arrK.getClass().getComponentType(), GridLayout.max2(arrn, -1) + 1);
            for (int i = 0; i < n; ++i) {
                arrobject[arrn[i]] = arrK[i];
            }
            return arrobject;
        }

        private static <K> int[] createIndex(K[] arrK) {
            int n = arrK.length;
            int[] arrn = new int[n];
            HashMap<K, Integer> hashMap = new HashMap<K, Integer>();
            for (int i = 0; i < n; ++i) {
                Integer n2;
                K k = arrK[i];
                Integer n3 = n2 = (Integer)hashMap.get(k);
                if (n2 == null) {
                    n3 = hashMap.size();
                    hashMap.put(k, n3);
                }
                arrn[i] = n3;
            }
            return arrn;
        }

        public V getValue(int n) {
            return this.values[this.index[n]];
        }
    }

    public static class Spec {
        static final float DEFAULT_WEIGHT = 0.0f;
        static final Spec UNDEFINED = GridLayout.spec(Integer.MIN_VALUE);
        final Alignment alignment;
        final Interval span;
        final boolean startDefined;
        final float weight;

        private Spec(boolean bl, int n, int n2, Alignment alignment, float f) {
            this(bl, new Interval(n, n + n2), alignment, f);
        }

        private Spec(boolean bl, Interval interval, Alignment alignment, float f) {
            this.startDefined = bl;
            this.span = interval;
            this.alignment = alignment;
            this.weight = f;
        }

        private Alignment getAbsoluteAlignment(boolean bl) {
            if (this.alignment != UNDEFINED_ALIGNMENT) {
                return this.alignment;
            }
            if (this.weight == 0.0f) {
                Alignment alignment = bl ? START : BASELINE;
                return alignment;
            }
            return FILL;
        }

        final Spec copyWriteAlignment(Alignment alignment) {
            return new Spec(this.startDefined, this.span, alignment, this.weight);
        }

        final Spec copyWriteSpan(Interval interval) {
            return new Spec(this.startDefined, interval, this.alignment, this.weight);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (Spec)object;
                if (!this.alignment.equals(((Spec)object).alignment)) {
                    return false;
                }
                return this.span.equals(((Spec)object).span);
            }
            return false;
        }

        final int getFlexibility() {
            int n = this.alignment == UNDEFINED_ALIGNMENT && this.weight == 0.0f ? 0 : 2;
            return n;
        }

        public int hashCode() {
            return this.span.hashCode() * 31 + this.alignment.hashCode();
        }
    }

}

