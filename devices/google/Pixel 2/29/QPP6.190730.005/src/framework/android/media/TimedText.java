/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.os.Parcel;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class TimedText {
    private static final int FIRST_PRIVATE_KEY = 101;
    private static final int FIRST_PUBLIC_KEY = 1;
    private static final int KEY_BACKGROUND_COLOR_RGBA = 3;
    private static final int KEY_DISPLAY_FLAGS = 1;
    private static final int KEY_END_CHAR = 104;
    private static final int KEY_FONT_ID = 105;
    private static final int KEY_FONT_SIZE = 106;
    private static final int KEY_GLOBAL_SETTING = 101;
    private static final int KEY_HIGHLIGHT_COLOR_RGBA = 4;
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_SCROLL_DELAY = 5;
    private static final int KEY_START_CHAR = 103;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_BLINKING_TEXT_LIST = 8;
    private static final int KEY_STRUCT_FONT_LIST = 9;
    private static final int KEY_STRUCT_HIGHLIGHT_LIST = 10;
    private static final int KEY_STRUCT_HYPER_TEXT_LIST = 11;
    private static final int KEY_STRUCT_JUSTIFICATION = 15;
    private static final int KEY_STRUCT_KARAOKE_LIST = 12;
    private static final int KEY_STRUCT_STYLE_LIST = 13;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int KEY_STRUCT_TEXT_POS = 14;
    private static final int KEY_STYLE_FLAGS = 2;
    private static final int KEY_TEXT_COLOR_RGBA = 107;
    private static final int KEY_WRAP_TEXT = 6;
    private static final int LAST_PRIVATE_KEY = 107;
    private static final int LAST_PUBLIC_KEY = 16;
    private static final String TAG = "TimedText";
    private int mBackgroundColorRGBA = -1;
    private List<CharPos> mBlinkingPosList = null;
    private int mDisplayFlags = -1;
    private List<Font> mFontList = null;
    private int mHighlightColorRGBA = -1;
    private List<CharPos> mHighlightPosList = null;
    private List<HyperText> mHyperTextList = null;
    private Justification mJustification;
    private List<Karaoke> mKaraokeList = null;
    private final HashMap<Integer, Object> mKeyObjectMap = new HashMap();
    private int mScrollDelay = -1;
    private List<Style> mStyleList = null;
    private Rect mTextBounds = null;
    private String mTextChars = null;
    private int mWrapText = -1;

    public TimedText(Parcel parcel) {
        if (this.parseParcel(parcel)) {
            return;
        }
        this.mKeyObjectMap.clear();
        throw new IllegalArgumentException("parseParcel() fails");
    }

    public TimedText(String string2, Rect rect) {
        this.mTextChars = string2;
        this.mTextBounds = rect;
    }

    private boolean containsKey(int n) {
        return this.isValidKey(n) && this.mKeyObjectMap.containsKey(n);
    }

    @UnsupportedAppUsage
    private Object getObject(int n) {
        if (this.containsKey(n)) {
            return this.mKeyObjectMap.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid key: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private boolean isValidKey(int n) {
        return n >= 1 && n <= 16 || n >= 101 && n <= 107;
        {
        }
    }

    private Set keySet() {
        return this.mKeyObjectMap.keySet();
    }

    private boolean parseParcel(Parcel object) {
        Object object2;
        int n;
        ((Parcel)object).setDataPosition(0);
        if (((Parcel)object).dataAvail() == 0) {
            return false;
        }
        int n2 = ((Parcel)object).readInt();
        if (n2 == 102) {
            n = ((Parcel)object).readInt();
            if (n != 7) {
                return false;
            }
            n2 = ((Parcel)object).readInt();
            this.mKeyObjectMap.put(n, n2);
            if (((Parcel)object).readInt() != 16) {
                return false;
            }
            ((Parcel)object).readInt();
            object2 = ((Parcel)object).createByteArray();
            this.mTextChars = object2 != null && ((byte[])object2).length != 0 ? new String((byte[])object2) : null;
        } else if (n2 != 101) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid timed text key found: ");
            ((StringBuilder)object).append(n2);
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        while (((Parcel)object).dataAvail() > 0) {
            n = ((Parcel)object).readInt();
            if (!this.isValidKey(n)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid timed text key found: ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
                return false;
            }
            object2 = null;
            switch (n) {
                default: {
                    break;
                }
                case 15: {
                    this.mJustification = new Justification(((Parcel)object).readInt(), ((Parcel)object).readInt());
                    object2 = this.mJustification;
                    break;
                }
                case 14: {
                    int n3 = ((Parcel)object).readInt();
                    n2 = ((Parcel)object).readInt();
                    int n4 = ((Parcel)object).readInt();
                    this.mTextBounds = new Rect(n2, n3, ((Parcel)object).readInt(), n4);
                    break;
                }
                case 13: {
                    this.readStyle((Parcel)object);
                    object2 = this.mStyleList;
                    break;
                }
                case 12: {
                    this.readKaraoke((Parcel)object);
                    object2 = this.mKaraokeList;
                    break;
                }
                case 11: {
                    this.readHyperText((Parcel)object);
                    object2 = this.mHyperTextList;
                    break;
                }
                case 10: {
                    this.readHighlight((Parcel)object);
                    object2 = this.mHighlightPosList;
                    break;
                }
                case 9: {
                    this.readFont((Parcel)object);
                    object2 = this.mFontList;
                    break;
                }
                case 8: {
                    this.readBlinkingText((Parcel)object);
                    object2 = this.mBlinkingPosList;
                    break;
                }
                case 6: {
                    this.mWrapText = ((Parcel)object).readInt();
                    object2 = this.mWrapText;
                    break;
                }
                case 5: {
                    this.mScrollDelay = ((Parcel)object).readInt();
                    object2 = this.mScrollDelay;
                    break;
                }
                case 4: {
                    this.mHighlightColorRGBA = ((Parcel)object).readInt();
                    object2 = this.mHighlightColorRGBA;
                    break;
                }
                case 3: {
                    this.mBackgroundColorRGBA = ((Parcel)object).readInt();
                    object2 = this.mBackgroundColorRGBA;
                    break;
                }
                case 1: {
                    this.mDisplayFlags = ((Parcel)object).readInt();
                    object2 = this.mDisplayFlags;
                }
            }
            if (object2 == null) continue;
            if (this.mKeyObjectMap.containsKey(n)) {
                this.mKeyObjectMap.remove(n);
            }
            this.mKeyObjectMap.put(n, object2);
        }
        return true;
    }

    private void readBlinkingText(Parcel object) {
        object = new CharPos(((Parcel)object).readInt(), ((Parcel)object).readInt());
        if (this.mBlinkingPosList == null) {
            this.mBlinkingPosList = new ArrayList<CharPos>();
        }
        this.mBlinkingPosList.add((CharPos)object);
    }

    private void readFont(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            Font font = new Font(n2, new String(parcel.createByteArray(), 0, n3));
            if (this.mFontList == null) {
                this.mFontList = new ArrayList<Font>();
            }
            this.mFontList.add(font);
        }
    }

    private void readHighlight(Parcel object) {
        object = new CharPos(((Parcel)object).readInt(), ((Parcel)object).readInt());
        if (this.mHighlightPosList == null) {
            this.mHighlightPosList = new ArrayList<CharPos>();
        }
        this.mHighlightPosList.add((CharPos)object);
    }

    private void readHyperText(Parcel object) {
        int n = ((Parcel)object).readInt();
        int n2 = ((Parcel)object).readInt();
        int n3 = ((Parcel)object).readInt();
        String string2 = new String(((Parcel)object).createByteArray(), 0, n3);
        n3 = ((Parcel)object).readInt();
        object = new HyperText(n, n2, string2, new String(((Parcel)object).createByteArray(), 0, n3));
        if (this.mHyperTextList == null) {
            this.mHyperTextList = new ArrayList<HyperText>();
        }
        this.mHyperTextList.add((HyperText)object);
    }

    private void readKaraoke(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            Karaoke karaoke = new Karaoke(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            if (this.mKaraokeList == null) {
                this.mKaraokeList = new ArrayList<Karaoke>();
            }
            this.mKaraokeList.add(karaoke);
        }
    }

    private void readStyle(Parcel object) {
        boolean bl = false;
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n4 = -1;
        int n5 = -1;
        block7 : while (!bl && ((Parcel)object).dataAvail() > 0) {
            int n6 = ((Parcel)object).readInt();
            if (n6 != 2) {
                switch (n6) {
                    default: {
                        ((Parcel)object).setDataPosition(((Parcel)object).dataPosition() - 4);
                        bl = true;
                        continue block7;
                    }
                    case 107: {
                        n5 = ((Parcel)object).readInt();
                        continue block7;
                    }
                    case 106: {
                        n4 = ((Parcel)object).readInt();
                        continue block7;
                    }
                    case 105: {
                        n3 = ((Parcel)object).readInt();
                        continue block7;
                    }
                    case 104: {
                        n2 = ((Parcel)object).readInt();
                        continue block7;
                    }
                    case 103: 
                }
                n = ((Parcel)object).readInt();
                continue;
            }
            n6 = ((Parcel)object).readInt();
            bl2 = false;
            bl4 = n6 % 2 == 1;
            bl3 = n6 % 4 >= 2;
            if (n6 / 4 == 1) {
                bl2 = true;
            }
            boolean bl5 = bl4;
            bl4 = bl2;
            bl2 = bl5;
        }
        object = new Style(n, n2, n3, bl2, bl3, bl4, n4, n5);
        if (this.mStyleList == null) {
            this.mStyleList = new ArrayList<Style>();
        }
        this.mStyleList.add((Style)object);
    }

    public Rect getBounds() {
        return this.mTextBounds;
    }

    public String getText() {
        return this.mTextChars;
    }

    public static final class CharPos {
        public final int endChar;
        public final int startChar;

        public CharPos(int n, int n2) {
            this.startChar = n;
            this.endChar = n2;
        }
    }

    public static final class Font {
        public final int ID;
        public final String name;

        public Font(int n, String string2) {
            this.ID = n;
            this.name = string2;
        }
    }

    public static final class HyperText {
        public final String URL;
        public final String altString;
        public final int endChar;
        public final int startChar;

        public HyperText(int n, int n2, String string2, String string3) {
            this.startChar = n;
            this.endChar = n2;
            this.URL = string2;
            this.altString = string3;
        }
    }

    public static final class Justification {
        public final int horizontalJustification;
        public final int verticalJustification;

        public Justification(int n, int n2) {
            this.horizontalJustification = n;
            this.verticalJustification = n2;
        }
    }

    public static final class Karaoke {
        public final int endChar;
        public final int endTimeMs;
        public final int startChar;
        public final int startTimeMs;

        public Karaoke(int n, int n2, int n3, int n4) {
            this.startTimeMs = n;
            this.endTimeMs = n2;
            this.startChar = n3;
            this.endChar = n4;
        }
    }

    public static final class Style {
        public final int colorRGBA;
        public final int endChar;
        public final int fontID;
        public final int fontSize;
        public final boolean isBold;
        public final boolean isItalic;
        public final boolean isUnderlined;
        public final int startChar;

        public Style(int n, int n2, int n3, boolean bl, boolean bl2, boolean bl3, int n4, int n5) {
            this.startChar = n;
            this.endChar = n2;
            this.fontID = n3;
            this.isBold = bl;
            this.isItalic = bl2;
            this.isUnderlined = bl3;
            this.fontSize = n4;
            this.colorRGBA = n5;
        }
    }

}

