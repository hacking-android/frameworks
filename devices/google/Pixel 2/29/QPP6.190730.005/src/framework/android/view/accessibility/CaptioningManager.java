/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class CaptioningManager {
    private static final int DEFAULT_ENABLED = 0;
    private static final float DEFAULT_FONT_SCALE = 1.0f;
    private static final int DEFAULT_PRESET = 0;
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private final ArrayList<CaptioningChangeListener> mListeners = new ArrayList();
    private final Runnable mStyleChangedRunnable = new Runnable(){

        @Override
        public void run() {
            CaptioningManager.this.notifyUserStyleChanged();
        }
    };

    public CaptioningManager(Context context) {
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver = new MyContentObserver(new Handler(context.getMainLooper()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyEnabledChanged() {
        boolean bl = this.isEnabled();
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            Iterator<CaptioningChangeListener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onEnabledChanged(bl);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyFontScaleChanged() {
        float f = this.getFontScale();
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            Iterator<CaptioningChangeListener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onFontScaleChanged(f);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyLocaleChanged() {
        Locale locale = this.getLocale();
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            Iterator<CaptioningChangeListener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onLocaleChanged(locale);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyUserStyleChanged() {
        CaptionStyle captionStyle = this.getUserStyle();
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            Iterator<CaptioningChangeListener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onUserStyleChanged(captionStyle);
            }
            return;
        }
    }

    private void registerObserver(String string2) {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(string2), false, this.mContentObserver);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addCaptioningChangeListener(CaptioningChangeListener captioningChangeListener) {
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            if (this.mListeners.isEmpty()) {
                this.registerObserver("accessibility_captioning_enabled");
                this.registerObserver("accessibility_captioning_foreground_color");
                this.registerObserver("accessibility_captioning_background_color");
                this.registerObserver("accessibility_captioning_window_color");
                this.registerObserver("accessibility_captioning_edge_type");
                this.registerObserver("accessibility_captioning_edge_color");
                this.registerObserver("accessibility_captioning_typeface");
                this.registerObserver("accessibility_captioning_font_scale");
                this.registerObserver("accessibility_captioning_locale");
                this.registerObserver("accessibility_captioning_preset");
            }
            this.mListeners.add(captioningChangeListener);
            return;
        }
    }

    public final float getFontScale() {
        return Settings.Secure.getFloat(this.mContentResolver, "accessibility_captioning_font_scale", 1.0f);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Locale getLocale() {
        String[] arrstring = this.getRawLocale();
        if (TextUtils.isEmpty((CharSequence)arrstring)) return null;
        int n = (arrstring = arrstring.split("_")).length;
        if (n == 1) return new Locale(arrstring[0]);
        if (n == 2) return new Locale(arrstring[0], arrstring[1]);
        if (n != 3) return null;
        return new Locale(arrstring[0], arrstring[1], arrstring[2]);
    }

    public final String getRawLocale() {
        return Settings.Secure.getString(this.mContentResolver, "accessibility_captioning_locale");
    }

    public int getRawUserStyle() {
        return Settings.Secure.getInt(this.mContentResolver, "accessibility_captioning_preset", 0);
    }

    public CaptionStyle getUserStyle() {
        int n = this.getRawUserStyle();
        if (n == -1) {
            return CaptionStyle.getCustomStyle(this.mContentResolver);
        }
        return CaptionStyle.PRESETS[n];
    }

    public final boolean isEnabled() {
        ContentResolver contentResolver = this.mContentResolver;
        boolean bl = false;
        if (Settings.Secure.getInt(contentResolver, "accessibility_captioning_enabled", 0) == 1) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeCaptioningChangeListener(CaptioningChangeListener captioningChangeListener) {
        ArrayList<CaptioningChangeListener> arrayList = this.mListeners;
        synchronized (arrayList) {
            this.mListeners.remove(captioningChangeListener);
            if (this.mListeners.isEmpty()) {
                this.mContentResolver.unregisterContentObserver(this.mContentObserver);
            }
            return;
        }
    }

    public static final class CaptionStyle {
        private static final CaptionStyle BLACK_ON_WHITE;
        private static final int COLOR_NONE_OPAQUE = 255;
        public static final int COLOR_UNSPECIFIED = 16777215;
        public static final CaptionStyle DEFAULT;
        private static final CaptionStyle DEFAULT_CUSTOM;
        public static final int EDGE_TYPE_DEPRESSED = 4;
        public static final int EDGE_TYPE_DROP_SHADOW = 2;
        public static final int EDGE_TYPE_NONE = 0;
        public static final int EDGE_TYPE_OUTLINE = 1;
        public static final int EDGE_TYPE_RAISED = 3;
        public static final int EDGE_TYPE_UNSPECIFIED = -1;
        @UnsupportedAppUsage
        public static final CaptionStyle[] PRESETS;
        public static final int PRESET_CUSTOM = -1;
        private static final CaptionStyle UNSPECIFIED;
        private static final CaptionStyle WHITE_ON_BLACK;
        private static final CaptionStyle YELLOW_ON_BLACK;
        private static final CaptionStyle YELLOW_ON_BLUE;
        public final int backgroundColor;
        public final int edgeColor;
        public final int edgeType;
        public final int foregroundColor;
        private final boolean mHasBackgroundColor;
        private final boolean mHasEdgeColor;
        private final boolean mHasEdgeType;
        private final boolean mHasForegroundColor;
        private final boolean mHasWindowColor;
        private Typeface mParsedTypeface;
        public final String mRawTypeface;
        public final int windowColor;

        static {
            WHITE_ON_BLACK = new CaptionStyle(-1, -16777216, 0, -16777216, 255, null);
            BLACK_ON_WHITE = new CaptionStyle(-16777216, -1, 0, -16777216, 255, null);
            YELLOW_ON_BLACK = new CaptionStyle(-256, -16777216, 0, -16777216, 255, null);
            YELLOW_ON_BLUE = new CaptionStyle(-256, -16776961, 0, -16777216, 255, null);
            UNSPECIFIED = new CaptionStyle(16777215, 16777215, -1, 16777215, 16777215, null);
            CaptionStyle captionStyle = WHITE_ON_BLACK;
            PRESETS = new CaptionStyle[]{captionStyle, BLACK_ON_WHITE, YELLOW_ON_BLACK, YELLOW_ON_BLUE, UNSPECIFIED};
            DEFAULT_CUSTOM = captionStyle;
            DEFAULT = captionStyle;
        }

        private CaptionStyle(int n, int n2, int n3, int n4, int n5, String string2) {
            this.mHasForegroundColor = CaptionStyle.hasColor(n);
            this.mHasBackgroundColor = CaptionStyle.hasColor(n2);
            int n6 = 0;
            int n7 = -1;
            boolean bl = n3 != -1;
            this.mHasEdgeType = bl;
            this.mHasEdgeColor = CaptionStyle.hasColor(n4);
            this.mHasWindowColor = CaptionStyle.hasColor(n5);
            if (this.mHasForegroundColor) {
                n7 = n;
            }
            this.foregroundColor = n7;
            bl = this.mHasBackgroundColor;
            n7 = -16777216;
            if (!bl) {
                n2 = -16777216;
            }
            this.backgroundColor = n2;
            n = n6;
            if (this.mHasEdgeType) {
                n = n3;
            }
            this.edgeType = n;
            n = n7;
            if (this.mHasEdgeColor) {
                n = n4;
            }
            this.edgeColor = n;
            if (!this.mHasWindowColor) {
                n5 = 255;
            }
            this.windowColor = n5;
            this.mRawTypeface = string2;
        }

        public static CaptionStyle getCustomStyle(ContentResolver object) {
            CaptionStyle captionStyle = DEFAULT_CUSTOM;
            int n = Settings.Secure.getInt((ContentResolver)object, "accessibility_captioning_foreground_color", captionStyle.foregroundColor);
            int n2 = Settings.Secure.getInt((ContentResolver)object, "accessibility_captioning_background_color", captionStyle.backgroundColor);
            int n3 = Settings.Secure.getInt((ContentResolver)object, "accessibility_captioning_edge_type", captionStyle.edgeType);
            int n4 = Settings.Secure.getInt((ContentResolver)object, "accessibility_captioning_edge_color", captionStyle.edgeColor);
            int n5 = Settings.Secure.getInt((ContentResolver)object, "accessibility_captioning_window_color", captionStyle.windowColor);
            if ((object = Settings.Secure.getString((ContentResolver)object, "accessibility_captioning_typeface")) == null) {
                object = captionStyle.mRawTypeface;
            }
            return new CaptionStyle(n, n2, n3, n4, n5, (String)object);
        }

        public static boolean hasColor(int n) {
            boolean bl = n >>> 24 != 0 || (16776960 & n) == 0;
            return bl;
        }

        public CaptionStyle applyStyle(CaptionStyle object) {
            int n = ((CaptionStyle)object).hasForegroundColor() ? ((CaptionStyle)object).foregroundColor : this.foregroundColor;
            int n2 = ((CaptionStyle)object).hasBackgroundColor() ? ((CaptionStyle)object).backgroundColor : this.backgroundColor;
            int n3 = ((CaptionStyle)object).hasEdgeType() ? ((CaptionStyle)object).edgeType : this.edgeType;
            int n4 = ((CaptionStyle)object).hasEdgeColor() ? ((CaptionStyle)object).edgeColor : this.edgeColor;
            int n5 = ((CaptionStyle)object).hasWindowColor() ? ((CaptionStyle)object).windowColor : this.windowColor;
            object = ((CaptionStyle)object).mRawTypeface;
            if (object == null) {
                object = this.mRawTypeface;
            }
            return new CaptionStyle(n, n2, n3, n4, n5, (String)object);
        }

        public Typeface getTypeface() {
            if (this.mParsedTypeface == null && !TextUtils.isEmpty(this.mRawTypeface)) {
                this.mParsedTypeface = Typeface.create(this.mRawTypeface, 0);
            }
            return this.mParsedTypeface;
        }

        public boolean hasBackgroundColor() {
            return this.mHasBackgroundColor;
        }

        public boolean hasEdgeColor() {
            return this.mHasEdgeColor;
        }

        public boolean hasEdgeType() {
            return this.mHasEdgeType;
        }

        public boolean hasForegroundColor() {
            return this.mHasForegroundColor;
        }

        public boolean hasWindowColor() {
            return this.mHasWindowColor;
        }
    }

    public static abstract class CaptioningChangeListener {
        public void onEnabledChanged(boolean bl) {
        }

        public void onFontScaleChanged(float f) {
        }

        public void onLocaleChanged(Locale locale) {
        }

        public void onUserStyleChanged(CaptionStyle captionStyle) {
        }
    }

    private class MyContentObserver
    extends ContentObserver {
        private final Handler mHandler;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        @Override
        public void onChange(boolean bl, Uri object) {
            object = ((Uri)object).getPath();
            if ("accessibility_captioning_enabled".equals(object = ((String)object).substring(((String)object).lastIndexOf(47) + 1))) {
                CaptioningManager.this.notifyEnabledChanged();
            } else if ("accessibility_captioning_locale".equals(object)) {
                CaptioningManager.this.notifyLocaleChanged();
            } else if ("accessibility_captioning_font_scale".equals(object)) {
                CaptioningManager.this.notifyFontScaleChanged();
            } else {
                this.mHandler.removeCallbacks(CaptioningManager.this.mStyleChangedRunnable);
                this.mHandler.post(CaptioningManager.this.mStyleChangedRunnable);
            }
        }
    }

}

