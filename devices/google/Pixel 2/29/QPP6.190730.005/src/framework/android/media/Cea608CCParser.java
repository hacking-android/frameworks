/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.accessibility.CaptioningManager;
import java.util.ArrayList;
import java.util.Arrays;

class Cea608CCParser {
    private static final int AOF = 34;
    private static final int AON = 35;
    private static final int BS = 33;
    private static final int CR = 45;
    private static final boolean DEBUG = Log.isLoggable("Cea608CCParser", 3);
    private static final int DER = 36;
    private static final int EDM = 44;
    private static final int ENM = 46;
    private static final int EOC = 47;
    private static final int FON = 40;
    private static final int INVALID = -1;
    public static final int MAX_COLS = 32;
    public static final int MAX_ROWS = 15;
    private static final int MODE_PAINT_ON = 1;
    private static final int MODE_POP_ON = 3;
    private static final int MODE_ROLL_UP = 2;
    private static final int MODE_TEXT = 4;
    private static final int MODE_UNKNOWN = 0;
    private static final int RCL = 32;
    private static final int RDC = 41;
    private static final int RTD = 43;
    private static final int RU2 = 37;
    private static final int RU3 = 38;
    private static final int RU4 = 39;
    private static final String TAG = "Cea608CCParser";
    private static final int TR = 42;
    private static final char TS = '\u00a0';
    private CCMemory mDisplay = new CCMemory();
    private final DisplayListener mListener;
    private int mMode = 1;
    private CCMemory mNonDisplay = new CCMemory();
    private int mPrevCtrlCode = -1;
    private int mRollUpSize = 4;
    private CCMemory mTextMem = new CCMemory();

    Cea608CCParser(DisplayListener displayListener) {
        this.mListener = displayListener;
    }

    private CCMemory getMemory() {
        int n = this.mMode;
        if (n != 1 && n != 2) {
            if (n != 3) {
                if (n != 4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unrecoginized mode: ");
                    stringBuilder.append(this.mMode);
                    Log.w(TAG, stringBuilder.toString());
                    return this.mDisplay;
                }
                return this.mTextMem;
            }
            return this.mNonDisplay;
        }
        return this.mDisplay;
    }

    private boolean handleCtrlCode(CCData cCData) {
        int n = cCData.getCtrlCode();
        int n2 = this.mPrevCtrlCode;
        if (n2 != -1 && n2 == n) {
            this.mPrevCtrlCode = -1;
            return true;
        }
        switch (n) {
            default: {
                this.mPrevCtrlCode = -1;
                return false;
            }
            case 47: {
                this.swapMemory();
                this.mMode = 3;
                this.updateDisplay();
                break;
            }
            case 46: {
                this.mNonDisplay.erase();
                break;
            }
            case 45: {
                if (this.mMode == 2) {
                    this.getMemory().rollUp(this.mRollUpSize);
                } else {
                    this.getMemory().cr();
                }
                if (this.mMode != 2) break;
                this.updateDisplay();
                break;
            }
            case 44: {
                this.mDisplay.erase();
                this.updateDisplay();
                break;
            }
            case 43: {
                this.mMode = 4;
                break;
            }
            case 42: {
                this.mMode = 4;
                this.mTextMem.erase();
                break;
            }
            case 41: {
                this.mMode = 1;
                break;
            }
            case 40: {
                Log.i(TAG, "Flash On");
                break;
            }
            case 37: 
            case 38: 
            case 39: {
                this.mRollUpSize = n - 35;
                if (this.mMode != 2) {
                    this.mDisplay.erase();
                    this.mNonDisplay.erase();
                }
                this.mMode = 2;
                break;
            }
            case 36: {
                this.getMemory().der();
                break;
            }
            case 33: {
                this.getMemory().bs();
                break;
            }
            case 32: {
                this.mMode = 3;
            }
        }
        this.mPrevCtrlCode = n;
        return true;
    }

    private boolean handleDisplayableChars(CCData cCData) {
        if (!cCData.isDisplayableChar()) {
            return false;
        }
        if (cCData.isExtendedChar()) {
            this.getMemory().bs();
        }
        this.getMemory().writeText(cCData.getDisplayText());
        int n = this.mMode;
        if (n == 1 || n == 2) {
            this.updateDisplay();
        }
        return true;
    }

    private boolean handleMidRowCode(CCData object) {
        if ((object = ((CCData)object).getMidRow()) != null) {
            this.getMemory().writeMidRowCode((StyleCode)object);
            return true;
        }
        return false;
    }

    private boolean handlePACCode(CCData object) {
        if ((object = ((CCData)object).getPAC()) != null) {
            if (this.mMode == 2) {
                this.getMemory().moveBaselineTo(((PAC)object).getRow(), this.mRollUpSize);
            }
            this.getMemory().writePAC((PAC)object);
            return true;
        }
        return false;
    }

    private boolean handleTabOffsets(CCData cCData) {
        int n = cCData.getTabOffset();
        if (n > 0) {
            this.getMemory().tab(n);
            return true;
        }
        return false;
    }

    private void swapMemory() {
        CCMemory cCMemory = this.mDisplay;
        this.mDisplay = this.mNonDisplay;
        this.mNonDisplay = cCMemory;
    }

    private void updateDisplay() {
        Object object = this.mListener;
        if (object != null) {
            object = object.getCaptionStyle();
            this.mListener.onDisplayChanged(this.mDisplay.getStyledText((CaptioningManager.CaptionStyle)object));
        }
    }

    public void parse(byte[] arrobject) {
        arrobject = CCData.fromByteArray(arrobject);
        for (int i = 0; i < arrobject.length; ++i) {
            if (DEBUG) {
                Log.d(TAG, arrobject[i].toString());
            }
            if (this.handleCtrlCode((CCData)arrobject[i]) || this.handleTabOffsets((CCData)arrobject[i]) || this.handlePACCode((CCData)arrobject[i]) || this.handleMidRowCode((CCData)arrobject[i])) continue;
            this.handleDisplayableChars((CCData)arrobject[i]);
        }
    }

    private static class CCData {
        private static final String[] mCtrlCodeMap = new String[]{"RCL", "BS", "AOF", "AON", "DER", "RU2", "RU3", "RU4", "FON", "RDC", "TR", "RTD", "EDM", "CR", "ENM", "EOC"};
        private static final String[] mProtugueseCharMap;
        private static final String[] mSpanishCharMap;
        private static final String[] mSpecialCharMap;
        private final byte mData1;
        private final byte mData2;
        private final byte mType;

        static {
            mSpecialCharMap = new String[]{"\u00ae", "\u00b0", "\u00bd", "\u00bf", "\u2122", "\u00a2", "\u00a3", "\u266a", "\u00e0", "\u00a0", "\u00e8", "\u00e2", "\u00ea", "\u00ee", "\u00f4", "\u00fb"};
            mSpanishCharMap = new String[]{"\u00c1", "\u00c9", "\u00d3", "\u00da", "\u00dc", "\u00fc", "\u2018", "\u00a1", "*", "'", "\u2014", "\u00a9", "\u2120", "\u2022", "\u201c", "\u201d", "\u00c0", "\u00c2", "\u00c7", "\u00c8", "\u00ca", "\u00cb", "\u00eb", "\u00ce", "\u00cf", "\u00ef", "\u00d4", "\u00d9", "\u00f9", "\u00db", "\u00ab", "\u00bb"};
            mProtugueseCharMap = new String[]{"\u00c3", "\u00e3", "\u00cd", "\u00cc", "\u00ec", "\u00d2", "\u00f2", "\u00d5", "\u00f5", "{", "}", "\\", "^", "_", "|", "~", "\u00c4", "\u00e4", "\u00d6", "\u00f6", "\u00df", "\u00a5", "\u00a4", "\u2502", "\u00c5", "\u00e5", "\u00d8", "\u00f8", "\u250c", "\u2510", "\u2514", "\u2518"};
        }

        CCData(byte by, byte by2, byte by3) {
            this.mType = by;
            this.mData1 = by2;
            this.mData2 = by3;
        }

        private String ctrlCodeToString(int n) {
            return mCtrlCodeMap[n - 32];
        }

        static CCData[] fromByteArray(byte[] arrby) {
            CCData[] arrcCData = new CCData[arrby.length / 3];
            for (int i = 0; i < arrcCData.length; ++i) {
                arrcCData[i] = new CCData(arrby[i * 3], arrby[i * 3 + 1], arrby[i * 3 + 2]);
            }
            return arrcCData;
        }

        private char getBasicChar(byte by) {
            byte by2;
            if (by != 42) {
                if (by != 92) {
                    block0 : switch (by) {
                        default: {
                            switch (by) {
                                default: {
                                    by2 = by = (byte)by;
                                    break block0;
                                }
                                case 127: {
                                    by2 = by = (byte)9608;
                                    break block0;
                                }
                                case 126: {
                                    by2 = by = (byte)241;
                                    break block0;
                                }
                                case 125: {
                                    by2 = by = (byte)209;
                                    break block0;
                                }
                                case 124: {
                                    by2 = by = (byte)247;
                                    break block0;
                                }
                                case 123: 
                            }
                            by2 = by = (byte)231;
                            break;
                        }
                        case 96: {
                            by2 = by = (byte)250;
                            break;
                        }
                        case 95: {
                            by2 = by = (byte)243;
                            break;
                        }
                        case 94: {
                            by2 = by = (byte)237;
                            break;
                        }
                    }
                } else {
                    by2 = by = (byte)233;
                }
            } else {
                by2 = by = (byte)225;
            }
            return (char)by2;
        }

        private String getBasicChars() {
            byte by = this.mData1;
            if (by >= 32 && by <= 127) {
                StringBuilder stringBuilder = new StringBuilder(2);
                stringBuilder.append(this.getBasicChar(this.mData1));
                byte by2 = this.mData2;
                if (by2 >= 32 && by2 <= 127) {
                    stringBuilder.append(this.getBasicChar(by2));
                }
                return stringBuilder.toString();
            }
            return null;
        }

        private String getExtendedChar() {
            byte by = this.mData1;
            if ((by == 18 || by == 26) && (by = this.mData2) >= 32 && by <= 63) {
                return mSpanishCharMap[by - 32];
            }
            by = this.mData1;
            if ((by == 19 || by == 27) && (by = this.mData2) >= 32 && by <= 63) {
                return mProtugueseCharMap[by - 32];
            }
            return null;
        }

        private String getSpecialChar() {
            byte by = this.mData1;
            if ((by == 17 || by == 25) && (by = this.mData2) >= 48 && by <= 63) {
                return mSpecialCharMap[by - 48];
            }
            return null;
        }

        private boolean isBasicChar() {
            byte by = this.mData1;
            boolean bl = by >= 32 && by <= 127;
            return bl;
        }

        private boolean isExtendedChar() {
            byte by = this.mData1;
            boolean bl = (by == 18 || by == 26 || by == 19 || by == 27) && (by = this.mData2) >= 32 && by <= 63;
            return bl;
        }

        private boolean isSpecialChar() {
            byte by = this.mData1;
            boolean bl = (by == 17 || by == 25) && (by = this.mData2) >= 48 && by <= 63;
            return bl;
        }

        int getCtrlCode() {
            byte by = this.mData1;
            if ((by == 20 || by == 28) && (by = this.mData2) >= 32 && by <= 47) {
                return by;
            }
            return -1;
        }

        String getDisplayText() {
            String string2;
            String string3 = string2 = this.getBasicChars();
            if (string2 == null) {
                string3 = string2 = this.getSpecialChar();
                if (string2 == null) {
                    string3 = this.getExtendedChar();
                }
            }
            return string3;
        }

        StyleCode getMidRow() {
            byte by;
            byte by2 = this.mData1;
            if ((by2 == 17 || by2 == 25) && (by = this.mData2) >= 32 && by <= 47) {
                return StyleCode.fromByte(by);
            }
            return null;
        }

        PAC getPAC() {
            byte by;
            byte by2 = this.mData1;
            if ((by2 & 112) == 16 && ((by = this.mData2) & 64) == 64 && ((by2 & 7) != 0 || (by & 32) == 0)) {
                return PAC.fromBytes(this.mData1, this.mData2);
            }
            return null;
        }

        int getTabOffset() {
            byte by = this.mData1;
            if ((by == 23 || by == 31) && (by = this.mData2) >= 33 && by <= 35) {
                return by & 3;
            }
            return 0;
        }

        boolean isDisplayableChar() {
            boolean bl = this.isBasicChar() || this.isSpecialChar() || this.isExtendedChar();
            return bl;
        }

        public String toString() {
            if (this.mData1 < 16 && this.mData2 < 16) {
                return String.format("[%d]Null: %02x %02x", this.mType, this.mData1, this.mData2);
            }
            int n = this.getCtrlCode();
            if (n != -1) {
                return String.format("[%d]%s", this.mType, this.ctrlCodeToString(n));
            }
            n = this.getTabOffset();
            if (n > 0) {
                return String.format("[%d]Tab%d", this.mType, n);
            }
            StyleCode styleCode = this.getPAC();
            if (styleCode != null) {
                return String.format("[%d]PAC: %s", this.mType, ((PAC)styleCode).toString());
            }
            styleCode = this.getMidRow();
            if (styleCode != null) {
                return String.format("[%d]Mid-row: %s", this.mType, styleCode.toString());
            }
            if (this.isDisplayableChar()) {
                return String.format("[%d]Displayable: %s (%02x %02x)", this.mType, this.getDisplayText(), this.mData1, this.mData2);
            }
            return String.format("[%d]Invalid: %02x %02x", this.mType, this.mData1, this.mData2);
        }
    }

    private static class CCLineBuilder {
        private final StringBuilder mDisplayChars;
        private final StyleCode[] mMidRowStyles;
        private final StyleCode[] mPACStyles;

        CCLineBuilder(String string2) {
            this.mDisplayChars = new StringBuilder(string2);
            this.mMidRowStyles = new StyleCode[this.mDisplayChars.length()];
            this.mPACStyles = new StyleCode[this.mDisplayChars.length()];
        }

        void applyStyleSpan(SpannableStringBuilder spannableStringBuilder, StyleCode styleCode, int n, int n2) {
            if (styleCode.isItalics()) {
                spannableStringBuilder.setSpan(new StyleSpan(2), n, n2, 33);
            }
            if (styleCode.isUnderline()) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), n, n2, 33);
            }
        }

        char charAt(int n) {
            return this.mDisplayChars.charAt(n);
        }

        SpannableStringBuilder getStyledText(CaptioningManager.CaptionStyle captionStyle) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mDisplayChars);
            int n = -1;
            int n2 = -1;
            Object object = null;
            for (int i = 0; i < this.mDisplayChars.length(); ++i) {
                Object object2;
                block14 : {
                    block15 : {
                        Object var7_7;
                        block13 : {
                            var7_7 = null;
                            object2 = this.mMidRowStyles;
                            if (object2[i] == null) break block13;
                            object2 = object2[i];
                            break block14;
                        }
                        object2 = var7_7;
                        if (this.mPACStyles[i] == null) break block14;
                        if (n2 < 0) break block15;
                        object2 = var7_7;
                        if (n >= 0) break block14;
                    }
                    object2 = this.mPACStyles[i];
                }
                int n3 = n2;
                if (object2 != null) {
                    object = object2;
                    if (n2 >= 0 && n >= 0) {
                        this.applyStyleSpan(spannableStringBuilder, (StyleCode)object2, n2, i);
                    }
                    n3 = i;
                }
                if (this.mDisplayChars.charAt(i) != '\u00a0') {
                    n2 = n;
                    if (n < 0) {
                        n2 = i;
                    }
                } else {
                    n2 = n;
                    if (n >= 0) {
                        if (this.mDisplayChars.charAt(n) != ' ') {
                            --n;
                        }
                        n2 = this.mDisplayChars.charAt(i - 1) == ' ' ? i : i + 1;
                        spannableStringBuilder.setSpan(new MutableBackgroundColorSpan(captionStyle.backgroundColor), n, n2, 33);
                        if (n3 >= 0) {
                            this.applyStyleSpan(spannableStringBuilder, (StyleCode)object, n3, n2);
                        }
                        n2 = -1;
                    }
                }
                n = n2;
                n2 = n3;
            }
            return spannableStringBuilder;
        }

        int length() {
            return this.mDisplayChars.length();
        }

        void setCharAt(int n, char c) {
            this.mDisplayChars.setCharAt(n, c);
            this.mMidRowStyles[n] = null;
        }

        void setMidRowAt(int n, StyleCode styleCode) {
            this.mDisplayChars.setCharAt(n, ' ');
            this.mMidRowStyles[n] = styleCode;
        }

        void setPACAt(int n, PAC pAC) {
            this.mPACStyles[n] = pAC;
        }
    }

    private static class CCMemory {
        private final String mBlankLine;
        private int mCol;
        private final CCLineBuilder[] mLines = new CCLineBuilder[17];
        private int mRow;

        CCMemory() {
            char[] arrc = new char[34];
            Arrays.fill(arrc, '\u00a0');
            this.mBlankLine = new String(arrc);
        }

        private static int clamp(int n, int n2, int n3) {
            block1 : {
                block0 : {
                    if (n >= n2) break block0;
                    n = n2;
                    break block1;
                }
                if (n <= n3) break block1;
                n = n3;
            }
            return n;
        }

        private CCLineBuilder getLineBuffer(int n) {
            CCLineBuilder[] arrcCLineBuilder = this.mLines;
            if (arrcCLineBuilder[n] == null) {
                arrcCLineBuilder[n] = new CCLineBuilder(this.mBlankLine);
            }
            return this.mLines[n];
        }

        private void moveBaselineTo(int n, int n2) {
            CCLineBuilder[] arrcCLineBuilder;
            int n3;
            if (this.mRow == n) {
                return;
            }
            int n4 = n3 = n2;
            if (n < n3) {
                n4 = n;
            }
            n3 = n4;
            if (this.mRow < n4) {
                n3 = this.mRow;
            }
            if (n < this.mRow) {
                for (n4 = n3 - 1; n4 >= 0; --n4) {
                    arrcCLineBuilder = this.mLines;
                    arrcCLineBuilder[n - n4] = arrcCLineBuilder[this.mRow - n4];
                }
            } else {
                for (n4 = 0; n4 < n3; ++n4) {
                    arrcCLineBuilder = this.mLines;
                    arrcCLineBuilder[n - n4] = arrcCLineBuilder[this.mRow - n4];
                }
            }
            for (n4 = 0; n4 <= n - n2; ++n4) {
                this.mLines[n4] = null;
            }
            ++n;
            while (n < (arrcCLineBuilder = this.mLines).length) {
                arrcCLineBuilder[n] = null;
                ++n;
            }
        }

        private void moveCursorByCol(int n) {
            this.mCol = CCMemory.clamp(this.mCol + n, 1, 32);
        }

        private void moveCursorTo(int n, int n2) {
            this.mRow = CCMemory.clamp(n, 1, 15);
            this.mCol = CCMemory.clamp(n2, 1, 32);
        }

        private void moveCursorToRow(int n) {
            this.mRow = CCMemory.clamp(n, 1, 15);
        }

        void bs() {
            this.moveCursorByCol(-1);
            CCLineBuilder[] arrcCLineBuilder = this.mLines;
            int n = this.mRow;
            if (arrcCLineBuilder[n] != null) {
                arrcCLineBuilder[n].setCharAt(this.mCol, '\u00a0');
                if (this.mCol == 31) {
                    this.mLines[this.mRow].setCharAt(32, '\u00a0');
                }
            }
        }

        void cr() {
            this.moveCursorTo(this.mRow + 1, 1);
        }

        void der() {
            if (this.mLines[this.mRow] != null) {
                for (int i = 0; i < this.mCol; ++i) {
                    if (this.mLines[this.mRow].charAt(i) == '\u00a0') continue;
                    for (i = this.mCol; i < this.mLines[this.mRow].length(); ++i) {
                        this.mLines[i].setCharAt(i, '\u00a0');
                    }
                    return;
                }
                this.mLines[this.mRow] = null;
            }
        }

        void erase() {
            CCLineBuilder[] arrcCLineBuilder;
            for (int i = 0; i < (arrcCLineBuilder = this.mLines).length; ++i) {
                arrcCLineBuilder[i] = null;
            }
            this.mRow = 15;
            this.mCol = 1;
        }

        SpannableStringBuilder[] getStyledText(CaptioningManager.CaptionStyle captionStyle) {
            ArrayList<CCLineBuilder[]> arrayList = new ArrayList<CCLineBuilder[]>(15);
            for (int i = 1; i <= 15; ++i) {
                Object object = this.mLines;
                object = object[i] != null ? object[i].getStyledText(captionStyle) : null;
                arrayList.add((CCLineBuilder[])object);
            }
            return arrayList.toArray(new SpannableStringBuilder[15]);
        }

        void rollUp(int n) {
            int n2;
            CCLineBuilder[] arrcCLineBuilder;
            int n3;
            for (n2 = 0; n2 <= (n3 = this.mRow) - n; ++n2) {
                this.mLines[n2] = null;
            }
            n = n2 = n3 - n + 1;
            if (n2 < 1) {
                n = 1;
            }
            while (n < this.mRow) {
                arrcCLineBuilder = this.mLines;
                arrcCLineBuilder[n] = arrcCLineBuilder[n + 1];
                ++n;
            }
            for (n = this.mRow; n < (arrcCLineBuilder = this.mLines).length; ++n) {
                arrcCLineBuilder[n] = null;
            }
            this.mCol = 1;
        }

        void tab(int n) {
            this.moveCursorByCol(n);
        }

        void writeMidRowCode(StyleCode styleCode) {
            this.getLineBuffer(this.mRow).setMidRowAt(this.mCol, styleCode);
            this.moveCursorByCol(1);
        }

        void writePAC(PAC pAC) {
            if (pAC.isIndentPAC()) {
                this.moveCursorTo(pAC.getRow(), pAC.getCol());
            } else {
                this.moveCursorTo(pAC.getRow(), 1);
            }
            this.getLineBuffer(this.mRow).setPACAt(this.mCol, pAC);
        }

        void writeText(String string2) {
            for (int i = 0; i < string2.length(); ++i) {
                this.getLineBuffer(this.mRow).setCharAt(this.mCol, string2.charAt(i));
                this.moveCursorByCol(1);
            }
        }
    }

    static interface DisplayListener {
        public CaptioningManager.CaptionStyle getCaptionStyle();

        public void onDisplayChanged(SpannableStringBuilder[] var1);
    }

    public static class MutableBackgroundColorSpan
    extends CharacterStyle
    implements UpdateAppearance {
        private int mColor;

        public MutableBackgroundColorSpan(int n) {
            this.mColor = n;
        }

        public int getBackgroundColor() {
            return this.mColor;
        }

        public void setBackgroundColor(int n) {
            this.mColor = n;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.bgColor = this.mColor;
        }
    }

    private static class PAC
    extends StyleCode {
        final int mCol;
        final int mRow;

        PAC(int n, int n2, int n3, int n4) {
            super(n3, n4);
            this.mRow = n;
            this.mCol = n2;
        }

        static PAC fromBytes(byte n, byte by) {
            int n2 = new int[]{11, 1, 3, 12, 14, 5, 7, 9}[n & 7] + ((by & 32) >> 5);
            n = 0;
            if ((by & 1) != 0) {
                n = 0 | 2;
            }
            if ((by & 16) != 0) {
                return new PAC(n2, (by >> 1 & 7) * 4, n, 0);
            }
            int n3 = by >> 1 & 7;
            int n4 = n;
            by = (byte)n3;
            if (n3 == 7) {
                by = 0;
                n4 = n | 1;
            }
            return new PAC(n2, -1, n4, by);
        }

        int getCol() {
            return this.mCol;
        }

        int getRow() {
            return this.mRow;
        }

        boolean isIndentPAC() {
            boolean bl = this.mCol >= 0;
            return bl;
        }

        @Override
        public String toString() {
            return String.format("{%d, %d}, %s", this.mRow, this.mCol, super.toString());
        }
    }

    private static class StyleCode {
        static final int COLOR_BLUE = 2;
        static final int COLOR_CYAN = 3;
        static final int COLOR_GREEN = 1;
        static final int COLOR_INVALID = 7;
        static final int COLOR_MAGENTA = 6;
        static final int COLOR_RED = 4;
        static final int COLOR_WHITE = 0;
        static final int COLOR_YELLOW = 5;
        static final int STYLE_ITALICS = 1;
        static final int STYLE_UNDERLINE = 2;
        static final String[] mColorMap = new String[]{"WHITE", "GREEN", "BLUE", "CYAN", "RED", "YELLOW", "MAGENTA", "INVALID"};
        final int mColor;
        final int mStyle;

        StyleCode(int n, int n2) {
            this.mStyle = n;
            this.mColor = n2;
        }

        static StyleCode fromByte(byte by) {
            int n = 0;
            int n2 = by >> 1 & 7;
            if ((by & 1) != 0) {
                n = 0 | 2;
            }
            int n3 = n;
            by = (byte)n2;
            if (n2 == 7) {
                by = 0;
                n3 = n | 1;
            }
            return new StyleCode(n3, by);
        }

        int getColor() {
            return this.mColor;
        }

        boolean isItalics() {
            int n = this.mStyle;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        boolean isUnderline() {
            boolean bl = (this.mStyle & 2) != 0;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(mColorMap[this.mColor]);
            if ((this.mStyle & 1) != 0) {
                stringBuilder.append(", ITALICS");
            }
            if ((this.mStyle & 2) != 0) {
                stringBuilder.append(", UNDERLINE");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

