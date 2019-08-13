/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.graphics.Color;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class Cea708CCParser {
    public static final int CAPTION_EMIT_TYPE_BUFFER = 1;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CLW = 4;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CWX = 3;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DFX = 16;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLC = 10;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLW = 8;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLY = 9;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DSW = 5;
    public static final int CAPTION_EMIT_TYPE_COMMAND_HDW = 6;
    public static final int CAPTION_EMIT_TYPE_COMMAND_RST = 11;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPA = 12;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPC = 13;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPL = 14;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SWA = 15;
    public static final int CAPTION_EMIT_TYPE_COMMAND_TGW = 7;
    public static final int CAPTION_EMIT_TYPE_CONTROL = 2;
    private static final boolean DEBUG = false;
    private static final String MUSIC_NOTE_CHAR = new String("\u266b".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    private static final String TAG = "Cea708CCParser";
    private final StringBuffer mBuffer = new StringBuffer();
    private int mCommand = 0;
    private DisplayListener mListener = new DisplayListener(){

        @Override
        public void emitEvent(CaptionEvent captionEvent) {
        }
    };

    Cea708CCParser(DisplayListener displayListener) {
        if (displayListener != null) {
            this.mListener = displayListener;
        }
    }

    private void emitCaptionBuffer() {
        if (this.mBuffer.length() > 0) {
            this.mListener.emitEvent(new CaptionEvent(1, this.mBuffer.toString()));
            this.mBuffer.setLength(0);
        }
    }

    private void emitCaptionEvent(CaptionEvent captionEvent) {
        this.emitCaptionBuffer();
        this.mListener.emitEvent(captionEvent);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int parseC0(byte[] var1_1, int var2_3) {
        block10 : {
            var3_4 = this.mCommand;
            if (var3_4 < 24 || var3_4 > 31) break block10;
            if (var3_4 != 24) return var2_3 += 2;
            if (var1_1[var2_3] != 0) ** GOTO lbl9
            try {
                this.mBuffer.append((char)var1_1[var2_3 + 1]);
                return var2_3 += 2;
lbl9: // 1 sources:
                var4_5 = new String(Arrays.copyOfRange(var1_1, var2_3, var2_3 + 2), "EUC-KR");
                this.mBuffer.append(var4_5);
                return var2_3 += 2;
            }
            catch (UnsupportedEncodingException var1_2) {
                Log.e("Cea708CCParser", "P16 Code - Could not find supported encoding", var1_2);
            }
            return var2_3 += 2;
        }
        var3_4 = this.mCommand;
        if (var3_4 >= 16 && var3_4 <= 23) {
            return ++var2_3;
        }
        var3_4 = this.mCommand;
        if (var3_4 == 0) return var2_3;
        if (var3_4 == 3) {
            this.emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char)var3_4)));
            return var2_3;
        }
        if (var3_4 == 8) {
            this.emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char)var3_4)));
            return var2_3;
        }
        switch (var3_4) {
            default: {
                return var2_3;
            }
            case 14: {
                this.emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char)var3_4)));
                return var2_3;
            }
            case 13: {
                this.mBuffer.append('\n');
                return var2_3;
            }
            case 12: 
        }
        this.emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char)var3_4)));
        return var2_3;
    }

    private int parseC1(byte[] arrby, int n) {
        int n2 = this.mCommand;
        switch (n2) {
            default: {
                break;
            }
            case 152: 
            case 153: 
            case 154: 
            case 155: 
            case 156: 
            case 157: 
            case 158: 
            case 159: {
                boolean bl = (arrby[n] & 32) != 0;
                boolean bl2 = (arrby[n] & 16) != 0;
                boolean bl3 = (arrby[n] & 8) != 0;
                byte by = arrby[n];
                boolean bl4 = (arrby[n + 1] & 128) != 0;
                byte by2 = arrby[n + 1];
                byte by3 = arrby[n + 2];
                byte by4 = arrby[n + 3];
                byte by5 = arrby[n + 3];
                byte by6 = arrby[n + 4];
                byte by7 = arrby[n + 5];
                this.emitCaptionEvent(new CaptionEvent(16, new CaptionWindow(n2 - 152, bl, bl2, bl3, by & 7, bl4, by2 & 127, by3 & 255, (by4 & 240) >> 4, 15 & by5, by6 & 63, 7 & arrby[n + 5], (by7 & 56) >> 3)));
                n += 6;
                break;
            }
            case 151: {
                CaptionColor captionColor = new CaptionColor((arrby[n] & 192) >> 6, (arrby[n] & 48) >> 4, (arrby[n] & 12) >> 2, arrby[n] & 3);
                byte by = arrby[n + 1];
                byte by8 = arrby[n + 2];
                CaptionColor captionColor2 = new CaptionColor(0, (arrby[n + 1] & 48) >> 4, (arrby[n + 1] & 12) >> 2, arrby[n + 1] & 3);
                boolean bl = (arrby[n + 2] & 64) != 0;
                byte by9 = arrby[n + 2];
                byte by10 = arrby[n + 2];
                n2 = arrby[n + 2];
                byte by11 = arrby[n + 3];
                this.emitCaptionEvent(new CaptionEvent(15, new CaptionWindowAttr(captionColor, captionColor2, (by8 & 128) >> 5 | (by & 192) >> 6, bl, (by9 & 48) >> 4, (by10 & 12) >> 2, n2 & 3, (12 & arrby[n + 3]) >> 2, (by11 & 240) >> 4, 3 & arrby[n + 3])));
                n += 4;
                break;
            }
            case 146: {
                this.emitCaptionEvent(new CaptionEvent(14, new CaptionPenLocation(arrby[n] & 15, arrby[n + 1] & 63)));
                n += 2;
                break;
            }
            case 145: {
                CaptionColor captionColor = new CaptionColor((arrby[n] & 192) >> 6, (arrby[n] & 48) >> 4, (arrby[n] & 12) >> 2, arrby[n] & 3);
                CaptionColor captionColor3 = new CaptionColor((arrby[++n] & 192) >> 6, (arrby[n] & 48) >> 4, (arrby[n] & 12) >> 2, arrby[n] & 3);
                this.emitCaptionEvent(new CaptionEvent(13, new CaptionPenColor(captionColor, captionColor3, new CaptionColor(0, (arrby[++n] & 48) >> 4, (12 & arrby[n]) >> 2, arrby[n] & 3))));
                ++n;
                break;
            }
            case 144: {
                byte by = arrby[n];
                byte by12 = arrby[n];
                n2 = arrby[n];
                boolean bl = (arrby[n + 1] & 128) != 0;
                boolean bl5 = (arrby[n + 1] & 64) != 0;
                byte by13 = arrby[n + 1];
                this.emitCaptionEvent(new CaptionEvent(12, new CaptionPenAttr(by12 & 3, (n2 & 12) >> 2, (by & 240) >> 4, 7 & arrby[n + 1], (by13 & 56) >> 3, bl5, bl)));
                n += 2;
                break;
            }
            case 143: {
                this.emitCaptionEvent(new CaptionEvent(11, null));
                break;
            }
            case 142: {
                this.emitCaptionEvent(new CaptionEvent(10, null));
                break;
            }
            case 141: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(9, by & 255));
                break;
            }
            case 140: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(8, by & 255));
                break;
            }
            case 139: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(7, by & 255));
                break;
            }
            case 138: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(6, by & 255));
                break;
            }
            case 137: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(5, by & 255));
                break;
            }
            case 136: {
                byte by = arrby[n];
                ++n;
                this.emitCaptionEvent(new CaptionEvent(4, by & 255));
                break;
            }
            case 128: 
            case 129: 
            case 130: 
            case 131: 
            case 132: 
            case 133: 
            case 134: 
            case 135: {
                this.emitCaptionEvent(new CaptionEvent(3, n2 - 128));
            }
        }
        return n;
    }

    private int parseC2(byte[] arrby, int n) {
        int n2 = this.mCommand;
        if (n2 >= 0 && n2 <= 7) {
            n2 = n;
        } else {
            n2 = this.mCommand;
            if (n2 >= 8 && n2 <= 15) {
                n2 = n + 1;
            } else {
                n2 = this.mCommand;
                if (n2 >= 16 && n2 <= 23) {
                    n2 = n + 2;
                } else {
                    int n3 = this.mCommand;
                    n2 = n;
                    if (n3 >= 24) {
                        n2 = n;
                        if (n3 <= 31) {
                            n2 = n + 3;
                        }
                    }
                }
            }
        }
        return n2;
    }

    private int parseC3(byte[] arrby, int n) {
        int n2 = this.mCommand;
        if (n2 >= 128 && n2 <= 135) {
            n2 = n + 4;
        } else {
            int n3 = this.mCommand;
            n2 = n;
            if (n3 >= 136) {
                n2 = n;
                if (n3 <= 143) {
                    n2 = n + 5;
                }
            }
        }
        return n2;
    }

    private int parseExt1(byte[] arrby, int n) {
        this.mCommand = arrby[n] & 255;
        int n2 = n + 1;
        n = this.mCommand;
        if (n >= 0 && n <= 31) {
            n = this.parseC2(arrby, n2);
        } else {
            n = this.mCommand;
            if (n >= 128 && n <= 159) {
                n = this.parseC3(arrby, n2);
            } else {
                n = this.mCommand;
                if (n >= 32 && n <= 127) {
                    n = this.parseG2(arrby, n2);
                } else {
                    int n3 = this.mCommand;
                    n = n2;
                    if (n3 >= 160) {
                        n = n2;
                        if (n3 <= 255) {
                            n = this.parseG3(arrby, n2);
                        }
                    }
                }
            }
        }
        return n;
    }

    private int parseG0(byte[] arrby, int n) {
        int n2 = this.mCommand;
        if (n2 == 127) {
            this.mBuffer.append(MUSIC_NOTE_CHAR);
        } else {
            this.mBuffer.append((char)n2);
        }
        return n;
    }

    private int parseG1(byte[] arrby, int n) {
        this.mBuffer.append((char)this.mCommand);
        return n;
    }

    private int parseG2(byte[] arrby, int n) {
        int n2 = this.mCommand;
        if (n2 == 32 || n2 == 33 || n2 != 48) {
            // empty if block
        }
        return n;
    }

    private int parseG3(byte[] arrby, int n) {
        int n2 = this.mCommand;
        return n;
    }

    private int parseServiceBlockData(byte[] arrby, int n) {
        this.mCommand = arrby[n] & 255;
        int n2 = n + 1;
        n = this.mCommand;
        if (n == 16) {
            n = this.parseExt1(arrby, n2);
        } else if (n >= 0 && n <= 31) {
            n = this.parseC0(arrby, n2);
        } else {
            n = this.mCommand;
            if (n >= 128 && n <= 159) {
                n = this.parseC1(arrby, n2);
            } else {
                n = this.mCommand;
                if (n >= 32 && n <= 127) {
                    n = this.parseG0(arrby, n2);
                } else {
                    int n3 = this.mCommand;
                    n = n2;
                    if (n3 >= 160) {
                        n = n2;
                        if (n3 <= 255) {
                            n = this.parseG1(arrby, n2);
                        }
                    }
                }
            }
        }
        return n;
    }

    public void parse(byte[] arrby) {
        int n = 0;
        while (n < arrby.length) {
            n = this.parseServiceBlockData(arrby, n);
        }
        this.emitCaptionBuffer();
    }

    public static class CaptionColor {
        private static final int[] COLOR_MAP = new int[]{0, 15, 240, 255};
        public static final int OPACITY_FLASH = 1;
        private static final int[] OPACITY_MAP = new int[]{255, 254, 128, 0};
        public static final int OPACITY_SOLID = 0;
        public static final int OPACITY_TRANSLUCENT = 2;
        public static final int OPACITY_TRANSPARENT = 3;
        public final int blue;
        public final int green;
        public final int opacity;
        public final int red;

        public CaptionColor(int n, int n2, int n3, int n4) {
            this.opacity = n;
            this.red = n2;
            this.green = n3;
            this.blue = n4;
        }

        public int getArgbValue() {
            int n = OPACITY_MAP[this.opacity];
            int[] arrn = COLOR_MAP;
            return Color.argb(n, arrn[this.red], arrn[this.green], arrn[this.blue]);
        }
    }

    public static class CaptionEvent {
        public final Object obj;
        public final int type;

        public CaptionEvent(int n, Object object) {
            this.type = n;
            this.obj = object;
        }
    }

    public static class CaptionPenAttr {
        public static final int OFFSET_NORMAL = 1;
        public static final int OFFSET_SUBSCRIPT = 0;
        public static final int OFFSET_SUPERSCRIPT = 2;
        public static final int PEN_SIZE_LARGE = 2;
        public static final int PEN_SIZE_SMALL = 0;
        public static final int PEN_SIZE_STANDARD = 1;
        public final int edgeType;
        public final int fontTag;
        public final boolean italic;
        public final int penOffset;
        public final int penSize;
        public final int textTag;
        public final boolean underline;

        public CaptionPenAttr(int n, int n2, int n3, int n4, int n5, boolean bl, boolean bl2) {
            this.penSize = n;
            this.penOffset = n2;
            this.textTag = n3;
            this.fontTag = n4;
            this.edgeType = n5;
            this.underline = bl;
            this.italic = bl2;
        }
    }

    public static class CaptionPenColor {
        public final CaptionColor backgroundColor;
        public final CaptionColor edgeColor;
        public final CaptionColor foregroundColor;

        public CaptionPenColor(CaptionColor captionColor, CaptionColor captionColor2, CaptionColor captionColor3) {
            this.foregroundColor = captionColor;
            this.backgroundColor = captionColor2;
            this.edgeColor = captionColor3;
        }
    }

    public static class CaptionPenLocation {
        public final int column;
        public final int row;

        public CaptionPenLocation(int n, int n2) {
            this.row = n;
            this.column = n2;
        }
    }

    public static class CaptionWindow {
        public final int anchorHorizontal;
        public final int anchorId;
        public final int anchorVertical;
        public final int columnCount;
        public final boolean columnLock;
        public final int id;
        public final int penStyle;
        public final int priority;
        public final boolean relativePositioning;
        public final int rowCount;
        public final boolean rowLock;
        public final boolean visible;
        public final int windowStyle;

        public CaptionWindow(int n, boolean bl, boolean bl2, boolean bl3, int n2, boolean bl4, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
            this.id = n;
            this.visible = bl;
            this.rowLock = bl2;
            this.columnLock = bl3;
            this.priority = n2;
            this.relativePositioning = bl4;
            this.anchorVertical = n3;
            this.anchorHorizontal = n4;
            this.anchorId = n5;
            this.rowCount = n6;
            this.columnCount = n7;
            this.penStyle = n8;
            this.windowStyle = n9;
        }
    }

    public static class CaptionWindowAttr {
        public final CaptionColor borderColor;
        public final int borderType;
        public final int displayEffect;
        public final int effectDirection;
        public final int effectSpeed;
        public final CaptionColor fillColor;
        public final int justify;
        public final int printDirection;
        public final int scrollDirection;
        public final boolean wordWrap;

        public CaptionWindowAttr(CaptionColor captionColor, CaptionColor captionColor2, int n, boolean bl, int n2, int n3, int n4, int n5, int n6, int n7) {
            this.fillColor = captionColor;
            this.borderColor = captionColor2;
            this.borderType = n;
            this.wordWrap = bl;
            this.printDirection = n2;
            this.scrollDirection = n3;
            this.justify = n4;
            this.effectDirection = n5;
            this.effectSpeed = n6;
            this.displayEffect = n7;
        }
    }

    private static class Const {
        public static final int CODE_C0_BS = 8;
        public static final int CODE_C0_CR = 13;
        public static final int CODE_C0_ETX = 3;
        public static final int CODE_C0_EXT1 = 16;
        public static final int CODE_C0_FF = 12;
        public static final int CODE_C0_HCR = 14;
        public static final int CODE_C0_NUL = 0;
        public static final int CODE_C0_P16 = 24;
        public static final int CODE_C0_RANGE_END = 31;
        public static final int CODE_C0_RANGE_START = 0;
        public static final int CODE_C0_SKIP1_RANGE_END = 23;
        public static final int CODE_C0_SKIP1_RANGE_START = 16;
        public static final int CODE_C0_SKIP2_RANGE_END = 31;
        public static final int CODE_C0_SKIP2_RANGE_START = 24;
        public static final int CODE_C1_CLW = 136;
        public static final int CODE_C1_CW0 = 128;
        public static final int CODE_C1_CW1 = 129;
        public static final int CODE_C1_CW2 = 130;
        public static final int CODE_C1_CW3 = 131;
        public static final int CODE_C1_CW4 = 132;
        public static final int CODE_C1_CW5 = 133;
        public static final int CODE_C1_CW6 = 134;
        public static final int CODE_C1_CW7 = 135;
        public static final int CODE_C1_DF0 = 152;
        public static final int CODE_C1_DF1 = 153;
        public static final int CODE_C1_DF2 = 154;
        public static final int CODE_C1_DF3 = 155;
        public static final int CODE_C1_DF4 = 156;
        public static final int CODE_C1_DF5 = 157;
        public static final int CODE_C1_DF6 = 158;
        public static final int CODE_C1_DF7 = 159;
        public static final int CODE_C1_DLC = 142;
        public static final int CODE_C1_DLW = 140;
        public static final int CODE_C1_DLY = 141;
        public static final int CODE_C1_DSW = 137;
        public static final int CODE_C1_HDW = 138;
        public static final int CODE_C1_RANGE_END = 159;
        public static final int CODE_C1_RANGE_START = 128;
        public static final int CODE_C1_RST = 143;
        public static final int CODE_C1_SPA = 144;
        public static final int CODE_C1_SPC = 145;
        public static final int CODE_C1_SPL = 146;
        public static final int CODE_C1_SWA = 151;
        public static final int CODE_C1_TGW = 139;
        public static final int CODE_C2_RANGE_END = 31;
        public static final int CODE_C2_RANGE_START = 0;
        public static final int CODE_C2_SKIP0_RANGE_END = 7;
        public static final int CODE_C2_SKIP0_RANGE_START = 0;
        public static final int CODE_C2_SKIP1_RANGE_END = 15;
        public static final int CODE_C2_SKIP1_RANGE_START = 8;
        public static final int CODE_C2_SKIP2_RANGE_END = 23;
        public static final int CODE_C2_SKIP2_RANGE_START = 16;
        public static final int CODE_C2_SKIP3_RANGE_END = 31;
        public static final int CODE_C2_SKIP3_RANGE_START = 24;
        public static final int CODE_C3_RANGE_END = 159;
        public static final int CODE_C3_RANGE_START = 128;
        public static final int CODE_C3_SKIP4_RANGE_END = 135;
        public static final int CODE_C3_SKIP4_RANGE_START = 128;
        public static final int CODE_C3_SKIP5_RANGE_END = 143;
        public static final int CODE_C3_SKIP5_RANGE_START = 136;
        public static final int CODE_G0_MUSICNOTE = 127;
        public static final int CODE_G0_RANGE_END = 127;
        public static final int CODE_G0_RANGE_START = 32;
        public static final int CODE_G1_RANGE_END = 255;
        public static final int CODE_G1_RANGE_START = 160;
        public static final int CODE_G2_BLK = 48;
        public static final int CODE_G2_NBTSP = 33;
        public static final int CODE_G2_RANGE_END = 127;
        public static final int CODE_G2_RANGE_START = 32;
        public static final int CODE_G2_TSP = 32;
        public static final int CODE_G3_CC = 160;
        public static final int CODE_G3_RANGE_END = 255;
        public static final int CODE_G3_RANGE_START = 160;

        private Const() {
        }
    }

    static interface DisplayListener {
        public void emitEvent(CaptionEvent var1);
    }

}

