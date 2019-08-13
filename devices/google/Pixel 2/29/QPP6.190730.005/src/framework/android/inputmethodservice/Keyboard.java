/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.inputmethodservice;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
public class Keyboard {
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    private static final int GRID_HEIGHT = 5;
    private static final int GRID_SIZE = 50;
    private static final int GRID_WIDTH = 10;
    public static final int KEYCODE_ALT = -6;
    public static final int KEYCODE_CANCEL = -3;
    public static final int KEYCODE_DELETE = -5;
    public static final int KEYCODE_DONE = -4;
    public static final int KEYCODE_MODE_CHANGE = -2;
    public static final int KEYCODE_SHIFT = -1;
    private static float SEARCH_DISTANCE = 0.0f;
    static final String TAG = "Keyboard";
    private static final String TAG_KEY = "Key";
    private static final String TAG_KEYBOARD = "Keyboard";
    private static final String TAG_ROW = "Row";
    private int mCellHeight;
    private int mCellWidth;
    private int mDefaultHeight;
    private int mDefaultHorizontalGap;
    private int mDefaultVerticalGap;
    private int mDefaultWidth;
    private int mDisplayHeight;
    private int mDisplayWidth;
    private int[][] mGridNeighbors;
    private int mKeyHeight;
    private int mKeyWidth;
    private int mKeyboardMode;
    private List<Key> mKeys;
    private CharSequence mLabel;
    @UnsupportedAppUsage
    private List<Key> mModifierKeys;
    private int mProximityThreshold;
    private int[] mShiftKeyIndices = new int[]{-1, -1};
    private Key[] mShiftKeys = new Key[]{null, null};
    private boolean mShifted;
    @UnsupportedAppUsage
    private int mTotalHeight;
    @UnsupportedAppUsage
    private int mTotalWidth;
    private ArrayList<Row> rows = new ArrayList();

    static {
        SEARCH_DISTANCE = 1.8f;
    }

    public Keyboard(Context context, int n) {
        this(context, n, 0);
    }

    public Keyboard(Context context, int n, int n2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisplayWidth = displayMetrics.widthPixels;
        this.mDisplayHeight = displayMetrics.heightPixels;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new ArrayList<Key>();
        this.mModifierKeys = new ArrayList<Key>();
        this.mKeyboardMode = n2;
        this.loadKeyboard(context, context.getResources().getXml(n));
    }

    public Keyboard(Context context, int n, int n2, int n3, int n4) {
        this.mDisplayWidth = n3;
        this.mDisplayHeight = n4;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new ArrayList<Key>();
        this.mModifierKeys = new ArrayList<Key>();
        this.mKeyboardMode = n2;
        this.loadKeyboard(context, context.getResources().getXml(n));
    }

    public Keyboard(Context object, int n, CharSequence charSequence, int n2, int n3) {
        this((Context)object, n);
        int n4 = 0;
        int n5 = 0;
        n = 0;
        this.mTotalWidth = 0;
        Row row = new Row(this);
        row.defaultHeight = this.mDefaultHeight;
        row.defaultWidth = this.mDefaultWidth;
        row.defaultHorizontalGap = this.mDefaultHorizontalGap;
        row.verticalGap = this.mDefaultVerticalGap;
        row.rowEdgeFlags = 12;
        int n6 = n2 == -1 ? Integer.MAX_VALUE : n2;
        n2 = n4;
        for (int i = 0; i < charSequence.length(); ++i) {
            char c;
            int n7;
            block6 : {
                block5 : {
                    c = charSequence.charAt(i);
                    if (n >= n6) break block5;
                    n7 = n2;
                    n4 = n5;
                    if (this.mDefaultWidth + n2 + n3 <= this.mDisplayWidth) break block6;
                }
                n7 = 0;
                n4 = n5 + (this.mDefaultVerticalGap + this.mDefaultHeight);
                n = 0;
            }
            object = new Key(row);
            ((Key)object).x = n7;
            ((Key)object).y = n4;
            ((Key)object).label = String.valueOf(c);
            ((Key)object).codes = new int[]{c};
            ++n;
            n2 = n7 + (((Key)object).width + ((Key)object).gap);
            this.mKeys.add((Key)object);
            row.mKeys.add((Key)object);
            if (n2 > this.mTotalWidth) {
                this.mTotalWidth = n2;
            }
            n5 = n4;
        }
        this.mTotalHeight = this.mDefaultHeight + n5;
        this.rows.add(row);
    }

    private void computeNearestNeighbors() {
        this.mCellWidth = (this.getMinWidth() + 10 - 1) / 10;
        this.mCellHeight = (this.getHeight() + 5 - 1) / 5;
        this.mGridNeighbors = new int[50][];
        int[] arrn = new int[this.mKeys.size()];
        int n = this.mCellWidth;
        int n2 = this.mCellHeight;
        for (int i = 0; i < n * 10; i += this.mCellWidth) {
            int n3;
            for (int j = 0; j < n2 * 5; j += n3) {
                Object object;
                int n4 = 0;
                for (n3 = 0; n3 < this.mKeys.size(); ++n3) {
                    int n5;
                    block6 : {
                        block5 : {
                            object = this.mKeys.get(n3);
                            if (((Key)object).squaredDistanceFrom(i, j) < this.mProximityThreshold || ((Key)object).squaredDistanceFrom(this.mCellWidth + i - 1, j) < this.mProximityThreshold || ((Key)object).squaredDistanceFrom(this.mCellWidth + i - 1, this.mCellHeight + j - 1) < this.mProximityThreshold) break block5;
                            n5 = n4;
                            if (((Key)object).squaredDistanceFrom(i, this.mCellHeight + j - 1) >= this.mProximityThreshold) break block6;
                        }
                        arrn[n4] = n3;
                        n5 = n4 + 1;
                    }
                    n4 = n5;
                }
                object = new int[n4];
                System.arraycopy(arrn, 0, object, 0, n4);
                int[][] arrn2 = this.mGridNeighbors;
                n3 = this.mCellHeight;
                arrn2[j / n3 * 10 + i / this.mCellWidth] = object;
            }
        }
    }

    static int getDimensionOrFraction(TypedArray typedArray, int n, int n2, int n3) {
        TypedValue typedValue = typedArray.peekValue(n);
        if (typedValue == null) {
            return n3;
        }
        if (typedValue.type == 5) {
            return typedArray.getDimensionPixelOffset(n, n3);
        }
        if (typedValue.type == 6) {
            return Math.round(typedArray.getFraction(n, n2, n2, n3));
        }
        return n3;
    }

    /*
     * Exception decompiling
     */
    private void loadKeyboard(Context var1_1, XmlResourceParser var2_10) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [17[UNCONDITIONALDOLOOP]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void parseKeyboardAttributes(Resources object, XmlResourceParser xmlResourceParser) {
        object = ((Resources)object).obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard);
        int n = this.mDisplayWidth;
        this.mDefaultWidth = Keyboard.getDimensionOrFraction((TypedArray)object, 0, n, n / 10);
        this.mDefaultHeight = Keyboard.getDimensionOrFraction((TypedArray)object, 1, this.mDisplayHeight, 50);
        this.mDefaultHorizontalGap = Keyboard.getDimensionOrFraction((TypedArray)object, 2, this.mDisplayWidth, 0);
        this.mDefaultVerticalGap = Keyboard.getDimensionOrFraction((TypedArray)object, 3, this.mDisplayHeight, 0);
        n = this.mProximityThreshold = (int)((float)this.mDefaultWidth * SEARCH_DISTANCE);
        this.mProximityThreshold = n * n;
        ((TypedArray)object).recycle();
    }

    private void skipToEndOfRow(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        int n;
        while (!((n = xmlResourceParser.next()) == 1 || n == 3 && xmlResourceParser.getName().equals(TAG_ROW))) {
        }
    }

    protected Key createKeyFromXml(Resources resources, Row row, int n, int n2, XmlResourceParser xmlResourceParser) {
        return new Key(resources, row, n, n2, xmlResourceParser);
    }

    protected Row createRowFromXml(Resources resources, XmlResourceParser xmlResourceParser) {
        return new Row(resources, this, xmlResourceParser);
    }

    public int getHeight() {
        return this.mTotalHeight;
    }

    protected int getHorizontalGap() {
        return this.mDefaultHorizontalGap;
    }

    protected int getKeyHeight() {
        return this.mDefaultHeight;
    }

    protected int getKeyWidth() {
        return this.mDefaultWidth;
    }

    public List<Key> getKeys() {
        return this.mKeys;
    }

    public int getMinWidth() {
        return this.mTotalWidth;
    }

    public List<Key> getModifierKeys() {
        return this.mModifierKeys;
    }

    public int[] getNearestKeys(int n, int n2) {
        if (this.mGridNeighbors == null) {
            this.computeNearestNeighbors();
        }
        if (n >= 0 && n < this.getMinWidth() && n2 >= 0 && n2 < this.getHeight() && (n = n2 / this.mCellHeight * 10 + n / this.mCellWidth) < 50) {
            return this.mGridNeighbors[n];
        }
        return new int[0];
    }

    public int getShiftKeyIndex() {
        return this.mShiftKeyIndices[0];
    }

    public int[] getShiftKeyIndices() {
        return this.mShiftKeyIndices;
    }

    protected int getVerticalGap() {
        return this.mDefaultVerticalGap;
    }

    public boolean isShifted() {
        return this.mShifted;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final void resize(int n, int n2) {
        int n3 = this.rows.size();
        for (n2 = 0; n2 < n3; ++n2) {
            int n4;
            Key key;
            int n5;
            Row row = this.rows.get(n2);
            int n6 = row.mKeys.size();
            int n7 = 0;
            int n8 = 0;
            for (n4 = 0; n4 < n6; ++n4) {
                key = row.mKeys.get(n4);
                n5 = n7;
                if (n4 > 0) {
                    n5 = n7 + key.gap;
                }
                n8 += key.width;
                n7 = n5;
            }
            if (n7 + n8 <= n) continue;
            n5 = 0;
            float f = (float)(n - n7) / (float)n8;
            n8 = n5;
            for (n4 = 0; n4 < n6; ++n4) {
                key = row.mKeys.get(n4);
                key.width = (int)((float)key.width * f);
                key.x = n8;
                n8 += key.width + key.gap;
            }
        }
        this.mTotalWidth = n;
    }

    protected void setHorizontalGap(int n) {
        this.mDefaultHorizontalGap = n;
    }

    protected void setKeyHeight(int n) {
        this.mDefaultHeight = n;
    }

    protected void setKeyWidth(int n) {
        this.mDefaultWidth = n;
    }

    public boolean setShifted(boolean bl) {
        for (Key key : this.mShiftKeys) {
            if (key == null) continue;
            key.on = bl;
        }
        if (this.mShifted != bl) {
            this.mShifted = bl;
            return true;
        }
        return false;
    }

    protected void setVerticalGap(int n) {
        this.mDefaultVerticalGap = n;
    }

    public static class Key {
        private static final int[] KEY_STATE_NORMAL;
        private static final int[] KEY_STATE_NORMAL_OFF;
        private static final int[] KEY_STATE_NORMAL_ON;
        private static final int[] KEY_STATE_PRESSED;
        private static final int[] KEY_STATE_PRESSED_OFF;
        private static final int[] KEY_STATE_PRESSED_ON;
        public int[] codes;
        public int edgeFlags;
        public int gap;
        public int height;
        public Drawable icon;
        public Drawable iconPreview;
        private Keyboard keyboard;
        public CharSequence label;
        public boolean modifier;
        public boolean on;
        public CharSequence popupCharacters;
        public int popupResId;
        public boolean pressed;
        public boolean repeatable;
        public boolean sticky;
        public CharSequence text;
        public int width;
        public int x;
        public int y;

        static {
            KEY_STATE_NORMAL_ON = new int[]{16842911, 16842912};
            KEY_STATE_PRESSED_ON = new int[]{16842919, 16842911, 16842912};
            KEY_STATE_NORMAL_OFF = new int[]{16842911};
            KEY_STATE_PRESSED_OFF = new int[]{16842919, 16842911};
            KEY_STATE_NORMAL = new int[0];
            KEY_STATE_PRESSED = new int[]{16842919};
        }

        public Key(Resources object, Row object2, int n, int n2, XmlResourceParser object3) {
            this((Row)object2);
            this.x = n;
            this.y = n2;
            TypedArray typedArray = ((Resources)object).obtainAttributes(Xml.asAttributeSet((XmlPullParser)object3), R.styleable.Keyboard);
            this.width = Keyboard.getDimensionOrFraction(typedArray, 0, this.keyboard.mDisplayWidth, ((Row)object2).defaultWidth);
            this.height = Keyboard.getDimensionOrFraction(typedArray, 1, this.keyboard.mDisplayHeight, ((Row)object2).defaultHeight);
            this.gap = Keyboard.getDimensionOrFraction(typedArray, 2, this.keyboard.mDisplayWidth, ((Row)object2).defaultHorizontalGap);
            typedArray.recycle();
            object = ((Resources)object).obtainAttributes(Xml.asAttributeSet((XmlPullParser)object3), R.styleable.Keyboard_Key);
            this.x += this.gap;
            object3 = new TypedValue();
            ((TypedArray)object).getValue(0, (TypedValue)object3);
            if (((TypedValue)object3).type != 16 && ((TypedValue)object3).type != 17) {
                if (((TypedValue)object3).type == 3) {
                    this.codes = this.parseCSV(((TypedValue)object3).string.toString());
                }
            } else {
                this.codes = new int[]{((TypedValue)object3).data};
            }
            this.iconPreview = ((TypedArray)object).getDrawable(7);
            object3 = this.iconPreview;
            if (object3 != null) {
                ((Drawable)object3).setBounds(0, 0, ((Drawable)object3).getIntrinsicWidth(), this.iconPreview.getIntrinsicHeight());
            }
            this.popupCharacters = ((TypedArray)object).getText(2);
            this.popupResId = ((TypedArray)object).getResourceId(1, 0);
            this.repeatable = ((TypedArray)object).getBoolean(6, false);
            this.modifier = ((TypedArray)object).getBoolean(4, false);
            this.sticky = ((TypedArray)object).getBoolean(5, false);
            this.edgeFlags = ((TypedArray)object).getInt(3, 0);
            this.edgeFlags |= ((Row)object2).rowEdgeFlags;
            this.icon = ((TypedArray)object).getDrawable(10);
            object2 = this.icon;
            if (object2 != null) {
                ((Drawable)object2).setBounds(0, 0, ((Drawable)object2).getIntrinsicWidth(), this.icon.getIntrinsicHeight());
            }
            this.label = ((TypedArray)object).getText(9);
            this.text = ((TypedArray)object).getText(8);
            if (this.codes == null && !TextUtils.isEmpty(this.label)) {
                this.codes = new int[]{this.label.charAt(0)};
            }
            ((TypedArray)object).recycle();
        }

        public Key(Row row) {
            this.keyboard = row.parent;
            this.height = row.defaultHeight;
            this.width = row.defaultWidth;
            this.gap = row.defaultHorizontalGap;
            this.edgeFlags = row.rowEdgeFlags;
        }

        public int[] getCurrentDrawableState() {
            int[] arrn = KEY_STATE_NORMAL;
            if (this.on) {
                arrn = this.pressed ? KEY_STATE_PRESSED_ON : KEY_STATE_NORMAL_ON;
            } else if (this.sticky) {
                arrn = this.pressed ? KEY_STATE_PRESSED_OFF : KEY_STATE_NORMAL_OFF;
            } else if (this.pressed) {
                arrn = KEY_STATE_PRESSED;
            }
            return arrn;
        }

        public boolean isInside(int n, int n2) {
            int n3 = (this.edgeFlags & 1) > 0 ? 1 : 0;
            boolean bl = (this.edgeFlags & 2) > 0;
            boolean bl2 = (this.edgeFlags & 4) > 0;
            boolean bl3 = (this.edgeFlags & 8) > 0;
            int n4 = this.x;
            return (n >= n4 || n3 != 0 && n <= n4 + this.width) && (n < this.width + (n3 = this.x) || bl && n >= n3) && (n2 >= (n = this.y) || bl2 && n2 <= n + this.height) && (n2 < this.height + (n = this.y) || bl3 && n2 >= n);
            {
            }
        }

        public void onPressed() {
            this.pressed ^= true;
        }

        public void onReleased(boolean bl) {
            this.pressed ^= true;
            if (this.sticky && bl) {
                this.on ^= true;
            }
        }

        int[] parseCSV(String string2) {
            int n;
            int n2 = 0;
            int n3 = 0;
            if (string2.length() > 0) {
                int n4;
                n = 0 + 1;
                do {
                    n3 = n4 = string2.indexOf(",", n3 + 1);
                    n2 = n++;
                } while (n4 > 0);
            }
            int[] arrn = new int[n2];
            n = 0;
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ",");
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    arrn[n] = Integer.parseInt(stringTokenizer.nextToken());
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error parsing keycodes ");
                    stringBuilder.append(string2);
                    Log.e("Keyboard", stringBuilder.toString());
                }
                ++n;
            }
            return arrn;
        }

        public int squaredDistanceFrom(int n, int n2) {
            n = this.x + this.width / 2 - n;
            n2 = this.y + this.height / 2 - n2;
            return n * n + n2 * n2;
        }
    }

    public static class Row {
        public int defaultHeight;
        public int defaultHorizontalGap;
        public int defaultWidth;
        ArrayList<Key> mKeys = new ArrayList();
        public int mode;
        private Keyboard parent;
        public int rowEdgeFlags;
        public int verticalGap;

        public Row(Resources object, Keyboard keyboard, XmlResourceParser xmlResourceParser) {
            this.parent = keyboard;
            TypedArray typedArray = ((Resources)object).obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard);
            this.defaultWidth = Keyboard.getDimensionOrFraction(typedArray, 0, keyboard.mDisplayWidth, keyboard.mDefaultWidth);
            this.defaultHeight = Keyboard.getDimensionOrFraction(typedArray, 1, keyboard.mDisplayHeight, keyboard.mDefaultHeight);
            this.defaultHorizontalGap = Keyboard.getDimensionOrFraction(typedArray, 2, keyboard.mDisplayWidth, keyboard.mDefaultHorizontalGap);
            this.verticalGap = Keyboard.getDimensionOrFraction(typedArray, 3, keyboard.mDisplayHeight, keyboard.mDefaultVerticalGap);
            typedArray.recycle();
            object = ((Resources)object).obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard_Row);
            this.rowEdgeFlags = ((TypedArray)object).getInt(0, 0);
            this.mode = ((TypedArray)object).getResourceId(1, 0);
        }

        public Row(Keyboard keyboard) {
            this.parent = keyboard;
        }
    }

}

