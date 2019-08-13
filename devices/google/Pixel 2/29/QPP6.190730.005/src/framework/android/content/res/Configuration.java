/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.os.Build;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class Configuration
implements Parcelable,
Comparable<Configuration> {
    public static final int ASSETS_SEQ_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_MASK = 12;
    public static final int COLOR_MODE_HDR_NO = 4;
    public static final int COLOR_MODE_HDR_SHIFT = 2;
    public static final int COLOR_MODE_HDR_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_YES = 8;
    public static final int COLOR_MODE_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_MASK = 3;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_NO = 1;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_YES = 2;
    public static final Parcelable.Creator<Configuration> CREATOR;
    public static final int DENSITY_DPI_ANY = 65534;
    public static final int DENSITY_DPI_NONE = 65535;
    public static final int DENSITY_DPI_UNDEFINED = 0;
    public static final Configuration EMPTY;
    public static final int HARDKEYBOARDHIDDEN_NO = 1;
    public static final int HARDKEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int HARDKEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARDHIDDEN_NO = 1;
    public static final int KEYBOARDHIDDEN_SOFT = 3;
    public static final int KEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int KEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARD_12KEY = 3;
    public static final int KEYBOARD_NOKEYS = 1;
    public static final int KEYBOARD_QWERTY = 2;
    public static final int KEYBOARD_UNDEFINED = 0;
    public static final int MNC_ZERO = 65535;
    public static final int NATIVE_CONFIG_COLOR_MODE = 65536;
    public static final int NATIVE_CONFIG_DENSITY = 256;
    public static final int NATIVE_CONFIG_KEYBOARD = 16;
    public static final int NATIVE_CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int NATIVE_CONFIG_LAYOUTDIR = 16384;
    public static final int NATIVE_CONFIG_LOCALE = 4;
    public static final int NATIVE_CONFIG_MCC = 1;
    public static final int NATIVE_CONFIG_MNC = 2;
    public static final int NATIVE_CONFIG_NAVIGATION = 64;
    public static final int NATIVE_CONFIG_ORIENTATION = 128;
    public static final int NATIVE_CONFIG_SCREEN_LAYOUT = 2048;
    public static final int NATIVE_CONFIG_SCREEN_SIZE = 512;
    public static final int NATIVE_CONFIG_SMALLEST_SCREEN_SIZE = 8192;
    public static final int NATIVE_CONFIG_TOUCHSCREEN = 8;
    public static final int NATIVE_CONFIG_UI_MODE = 4096;
    public static final int NATIVE_CONFIG_VERSION = 1024;
    public static final int NAVIGATIONHIDDEN_NO = 1;
    public static final int NAVIGATIONHIDDEN_UNDEFINED = 0;
    public static final int NAVIGATIONHIDDEN_YES = 2;
    public static final int NAVIGATION_DPAD = 2;
    public static final int NAVIGATION_NONAV = 1;
    public static final int NAVIGATION_TRACKBALL = 3;
    public static final int NAVIGATION_UNDEFINED = 0;
    public static final int NAVIGATION_WHEEL = 4;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int ORIENTATION_PORTRAIT = 1;
    @Deprecated
    public static final int ORIENTATION_SQUARE = 3;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int SCREENLAYOUT_COMPAT_NEEDED = 268435456;
    public static final int SCREENLAYOUT_LAYOUTDIR_LTR = 64;
    public static final int SCREENLAYOUT_LAYOUTDIR_MASK = 192;
    public static final int SCREENLAYOUT_LAYOUTDIR_RTL = 128;
    public static final int SCREENLAYOUT_LAYOUTDIR_SHIFT = 6;
    public static final int SCREENLAYOUT_LAYOUTDIR_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_MASK = 48;
    public static final int SCREENLAYOUT_LONG_NO = 16;
    public static final int SCREENLAYOUT_LONG_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_YES = 32;
    public static final int SCREENLAYOUT_ROUND_MASK = 768;
    public static final int SCREENLAYOUT_ROUND_NO = 256;
    public static final int SCREENLAYOUT_ROUND_SHIFT = 8;
    public static final int SCREENLAYOUT_ROUND_UNDEFINED = 0;
    public static final int SCREENLAYOUT_ROUND_YES = 512;
    public static final int SCREENLAYOUT_SIZE_LARGE = 3;
    public static final int SCREENLAYOUT_SIZE_MASK = 15;
    public static final int SCREENLAYOUT_SIZE_NORMAL = 2;
    public static final int SCREENLAYOUT_SIZE_SMALL = 1;
    public static final int SCREENLAYOUT_SIZE_UNDEFINED = 0;
    public static final int SCREENLAYOUT_SIZE_XLARGE = 4;
    public static final int SCREENLAYOUT_UNDEFINED = 0;
    public static final int SCREEN_HEIGHT_DP_UNDEFINED = 0;
    public static final int SCREEN_WIDTH_DP_UNDEFINED = 0;
    public static final int SMALLEST_SCREEN_WIDTH_DP_UNDEFINED = 0;
    private static final String TAG = "Configuration";
    public static final int TOUCHSCREEN_FINGER = 3;
    public static final int TOUCHSCREEN_NOTOUCH = 1;
    @Deprecated
    public static final int TOUCHSCREEN_STYLUS = 2;
    public static final int TOUCHSCREEN_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_MASK = 48;
    public static final int UI_MODE_NIGHT_NO = 16;
    public static final int UI_MODE_NIGHT_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_YES = 32;
    public static final int UI_MODE_TYPE_APPLIANCE = 5;
    public static final int UI_MODE_TYPE_CAR = 3;
    public static final int UI_MODE_TYPE_DESK = 2;
    public static final int UI_MODE_TYPE_MASK = 15;
    public static final int UI_MODE_TYPE_NORMAL = 1;
    public static final int UI_MODE_TYPE_TELEVISION = 4;
    public static final int UI_MODE_TYPE_UNDEFINED = 0;
    public static final int UI_MODE_TYPE_VR_HEADSET = 7;
    public static final int UI_MODE_TYPE_WATCH = 6;
    private static final String XML_ATTR_APP_BOUNDS = "app_bounds";
    private static final String XML_ATTR_COLOR_MODE = "clrMod";
    private static final String XML_ATTR_DENSITY = "density";
    private static final String XML_ATTR_FONT_SCALE = "fs";
    private static final String XML_ATTR_HARD_KEYBOARD_HIDDEN = "hardKeyHid";
    private static final String XML_ATTR_KEYBOARD = "key";
    private static final String XML_ATTR_KEYBOARD_HIDDEN = "keyHid";
    private static final String XML_ATTR_LOCALES = "locales";
    private static final String XML_ATTR_MCC = "mcc";
    private static final String XML_ATTR_MNC = "mnc";
    private static final String XML_ATTR_NAVIGATION = "nav";
    private static final String XML_ATTR_NAVIGATION_HIDDEN = "navHid";
    private static final String XML_ATTR_ORIENTATION = "ori";
    private static final String XML_ATTR_ROTATION = "rot";
    private static final String XML_ATTR_SCREEN_HEIGHT = "height";
    private static final String XML_ATTR_SCREEN_LAYOUT = "scrLay";
    private static final String XML_ATTR_SCREEN_WIDTH = "width";
    private static final String XML_ATTR_SMALLEST_WIDTH = "sw";
    private static final String XML_ATTR_TOUCHSCREEN = "touch";
    private static final String XML_ATTR_UI_MODE = "ui";
    public int assetsSeq;
    public int colorMode;
    public int compatScreenHeightDp;
    public int compatScreenWidthDp;
    public int compatSmallestScreenWidthDp;
    public int densityDpi;
    public float fontScale;
    public int hardKeyboardHidden;
    public int keyboard;
    public int keyboardHidden;
    @Deprecated
    public Locale locale;
    private LocaleList mLocaleList;
    public int mcc;
    public int mnc;
    public int navigation;
    public int navigationHidden;
    public int orientation;
    public int screenHeightDp;
    public int screenLayout;
    public int screenWidthDp;
    @UnsupportedAppUsage
    public int seq;
    public int smallestScreenWidthDp;
    public int touchscreen;
    public int uiMode;
    @UnsupportedAppUsage
    public boolean userSetLocale;
    public final WindowConfiguration windowConfiguration = new WindowConfiguration();

    static {
        EMPTY = new Configuration();
        CREATOR = new Parcelable.Creator<Configuration>(){

            @Override
            public Configuration createFromParcel(Parcel parcel) {
                return new Configuration(parcel);
            }

            public Configuration[] newArray(int n) {
                return new Configuration[n];
            }
        };
    }

    public Configuration() {
        this.unset();
    }

    public Configuration(Configuration configuration) {
        this.setTo(configuration);
    }

    private Configuration(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static String configurationDiffToString(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if ((n & 1) != 0) {
            arrayList.add("CONFIG_MCC");
        }
        if ((n & 2) != 0) {
            arrayList.add("CONFIG_MNC");
        }
        if ((n & 4) != 0) {
            arrayList.add("CONFIG_LOCALE");
        }
        if ((n & 8) != 0) {
            arrayList.add("CONFIG_TOUCHSCREEN");
        }
        if ((n & 16) != 0) {
            arrayList.add("CONFIG_KEYBOARD");
        }
        if ((n & 32) != 0) {
            arrayList.add("CONFIG_KEYBOARD_HIDDEN");
        }
        if ((n & 64) != 0) {
            arrayList.add("CONFIG_NAVIGATION");
        }
        if ((n & 128) != 0) {
            arrayList.add("CONFIG_ORIENTATION");
        }
        if ((n & 256) != 0) {
            arrayList.add("CONFIG_SCREEN_LAYOUT");
        }
        if ((n & 16384) != 0) {
            arrayList.add("CONFIG_COLOR_MODE");
        }
        if ((n & 512) != 0) {
            arrayList.add("CONFIG_UI_MODE");
        }
        if ((n & 1024) != 0) {
            arrayList.add("CONFIG_SCREEN_SIZE");
        }
        if ((n & 2048) != 0) {
            arrayList.add("CONFIG_SMALLEST_SCREEN_SIZE");
        }
        if ((n & 8192) != 0) {
            arrayList.add("CONFIG_LAYOUT_DIRECTION");
        }
        if ((1073741824 & n) != 0) {
            arrayList.add("CONFIG_FONT_SCALE");
        }
        if ((Integer.MIN_VALUE & n) != 0) {
            arrayList.add("CONFIG_ASSETS_PATHS");
        }
        StringBuilder stringBuilder = new StringBuilder("{");
        int n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            stringBuilder.append((String)arrayList.get(n));
            if (n == n2 - 1) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private void fixUpLocaleList() {
        Object object;
        if (this.locale == null && !this.mLocaleList.isEmpty() || (object = this.locale) != null && !((Locale)object).equals(this.mLocaleList.get(0))) {
            object = this.locale;
            object = object == null ? LocaleList.getEmptyLocaleList() : new LocaleList(new Locale[]{object});
            this.mLocaleList = object;
        }
    }

    @UnsupportedAppUsage
    public static Configuration generateDelta(Configuration configuration, Configuration configuration2) {
        int n;
        int n2;
        Configuration configuration3 = new Configuration();
        float f = configuration.fontScale;
        float f2 = configuration2.fontScale;
        if (f != f2) {
            configuration3.fontScale = f2;
        }
        if ((n = configuration.mcc) != (n2 = configuration2.mcc)) {
            configuration3.mcc = n2;
        }
        if ((n2 = configuration.mnc) != (n = configuration2.mnc)) {
            configuration3.mnc = n;
        }
        configuration.fixUpLocaleList();
        configuration2.fixUpLocaleList();
        if (!configuration.mLocaleList.equals(configuration2.mLocaleList)) {
            configuration3.mLocaleList = configuration2.mLocaleList;
            configuration3.locale = configuration2.locale;
        }
        if ((n = configuration.touchscreen) != (n2 = configuration2.touchscreen)) {
            configuration3.touchscreen = n2;
        }
        if ((n = configuration.keyboard) != (n2 = configuration2.keyboard)) {
            configuration3.keyboard = n2;
        }
        if ((n2 = configuration.keyboardHidden) != (n = configuration2.keyboardHidden)) {
            configuration3.keyboardHidden = n;
        }
        if ((n = configuration.navigation) != (n2 = configuration2.navigation)) {
            configuration3.navigation = n2;
        }
        if ((n = configuration.navigationHidden) != (n2 = configuration2.navigationHidden)) {
            configuration3.navigationHidden = n2;
        }
        if ((n2 = configuration.orientation) != (n = configuration2.orientation)) {
            configuration3.orientation = n;
        }
        if (((n2 = configuration.screenLayout) & 15) != ((n = configuration2.screenLayout) & 15)) {
            configuration3.screenLayout |= n & 15;
        }
        if (((n = configuration.screenLayout) & 192) != ((n2 = configuration2.screenLayout) & 192)) {
            configuration3.screenLayout |= n2 & 192;
        }
        if (((n2 = configuration.screenLayout) & 48) != ((n = configuration2.screenLayout) & 48)) {
            configuration3.screenLayout |= n & 48;
        }
        if (((n2 = configuration.screenLayout) & 768) != ((n = configuration2.screenLayout) & 768)) {
            configuration3.screenLayout |= n & 768;
        }
        if (((n2 = configuration.colorMode) & 3) != ((n = configuration2.colorMode) & 3)) {
            configuration3.colorMode |= n & 3;
        }
        if (((n2 = configuration.colorMode) & 12) != ((n = configuration2.colorMode) & 12)) {
            configuration3.colorMode |= n & 12;
        }
        if (((n2 = configuration.uiMode) & 15) != ((n = configuration2.uiMode) & 15)) {
            configuration3.uiMode |= n & 15;
        }
        if (((n2 = configuration.uiMode) & 48) != ((n = configuration2.uiMode) & 48)) {
            configuration3.uiMode |= n & 48;
        }
        if ((n = configuration.screenWidthDp) != (n2 = configuration2.screenWidthDp)) {
            configuration3.screenWidthDp = n2;
        }
        if ((n2 = configuration.screenHeightDp) != (n = configuration2.screenHeightDp)) {
            configuration3.screenHeightDp = n;
        }
        if ((n = configuration.smallestScreenWidthDp) != (n2 = configuration2.smallestScreenWidthDp)) {
            configuration3.smallestScreenWidthDp = n2;
        }
        if ((n = configuration.densityDpi) != (n2 = configuration2.densityDpi)) {
            configuration3.densityDpi = n2;
        }
        if ((n2 = configuration.assetsSeq) != (n = configuration2.assetsSeq)) {
            configuration3.assetsSeq = n;
        }
        if (!configuration.windowConfiguration.equals(configuration2.windowConfiguration)) {
            configuration3.windowConfiguration.setTo(configuration2.windowConfiguration);
        }
        return configuration3;
    }

    private static int getScreenLayoutNoDirection(int n) {
        return n & -193;
    }

    public static String localesToResourceQualifier(LocaleList localeList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < localeList.size(); ++i) {
            Locale locale = localeList.get(i);
            int n = locale.getLanguage().length();
            if (n == 0) continue;
            int n2 = locale.getScript().length();
            int n3 = locale.getCountry().length();
            int n4 = locale.getVariant().length();
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            if (n == 2 && n2 == 0 && (n3 == 0 || n3 == 2) && n4 == 0) {
                stringBuilder.append(locale.getLanguage());
                if (n3 != 2) continue;
                stringBuilder.append("-r");
                stringBuilder.append(locale.getCountry());
                continue;
            }
            stringBuilder.append("b+");
            stringBuilder.append(locale.getLanguage());
            if (n2 != 0) {
                stringBuilder.append("+");
                stringBuilder.append(locale.getScript());
            }
            if (n3 != 0) {
                stringBuilder.append("+");
                stringBuilder.append(locale.getCountry());
            }
            if (n4 == 0) continue;
            stringBuilder.append("+");
            stringBuilder.append(locale.getVariant());
        }
        return stringBuilder.toString();
    }

    public static boolean needNewResources(int n, int n2) {
        boolean bl = (n & (Integer.MIN_VALUE | n2 | 1073741824)) != 0;
        return bl;
    }

    public static void readXmlAttrs(XmlPullParser xmlPullParser, Configuration configuration) throws XmlPullParserException, IOException {
        configuration.fontScale = Float.intBitsToFloat(XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_FONT_SCALE, 0));
        configuration.mcc = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_MCC, 0);
        configuration.mnc = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_MNC, 0);
        configuration.mLocaleList = LocaleList.forLanguageTags(XmlUtils.readStringAttribute(xmlPullParser, XML_ATTR_LOCALES));
        configuration.locale = configuration.mLocaleList.get(0);
        configuration.touchscreen = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_TOUCHSCREEN, 0);
        configuration.keyboard = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_KEYBOARD, 0);
        configuration.keyboardHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_KEYBOARD_HIDDEN, 0);
        configuration.hardKeyboardHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_HARD_KEYBOARD_HIDDEN, 0);
        configuration.navigation = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION, 0);
        configuration.navigationHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION_HIDDEN, 0);
        configuration.orientation = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_ORIENTATION, 0);
        configuration.screenLayout = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SCREEN_LAYOUT, 0);
        configuration.colorMode = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_COLOR_MODE, 0);
        configuration.uiMode = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_UI_MODE, 0);
        configuration.screenWidthDp = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SCREEN_WIDTH, 0);
        configuration.screenHeightDp = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SCREEN_HEIGHT, 0);
        configuration.smallestScreenWidthDp = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SMALLEST_WIDTH, 0);
        configuration.densityDpi = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_DENSITY, 0);
    }

    public static int reduceScreenLayout(int n, int n2, int n3) {
        boolean bl;
        int n4;
        if (n2 < 470) {
            n4 = 1;
            n2 = 0;
            bl = false;
        } else {
            n4 = n2 >= 960 && n3 >= 720 ? 4 : (n2 >= 640 && n3 >= 480 ? 3 : 2);
            bl = n3 > 321 || n2 > 570;
            n2 = n2 * 3 / 5 >= n3 - 1 ? 1 : 0;
        }
        n3 = n;
        if (n2 == 0) {
            n3 = n & -49 | 16;
        }
        n = n3;
        if (bl) {
            n = n3 | 268435456;
        }
        n2 = n;
        if (n4 < (n & 15)) {
            n2 = n & -16 | n4;
        }
        return n2;
    }

    public static int resetScreenLayout(int n) {
        return -268435520 & n | 36;
    }

    @UnsupportedAppUsage
    public static String resourceQualifierString(Configuration configuration) {
        return Configuration.resourceQualifierString(configuration, null);
    }

    public static String resourceQualifierString(Configuration object, DisplayMetrics displayMetrics) {
        int n;
        CharSequence charSequence;
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>();
        if (((Configuration)object).mcc != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(XML_ATTR_MCC);
            ((StringBuilder)charSequence).append(((Configuration)object).mcc);
            arrayList.add(((StringBuilder)charSequence).toString());
            if (((Configuration)object).mnc != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(XML_ATTR_MNC);
                ((StringBuilder)charSequence).append(((Configuration)object).mnc);
                arrayList.add(((StringBuilder)charSequence).toString());
            }
        }
        if (!((Configuration)object).mLocaleList.isEmpty() && !((String)(charSequence = Configuration.localesToResourceQualifier(((Configuration)object).mLocaleList))).isEmpty()) {
            arrayList.add(charSequence);
        }
        if ((n = ((Configuration)object).screenLayout & 192) != 64) {
            if (n == 128) {
                arrayList.add("ldrtl");
            }
        } else {
            arrayList.add("ldltr");
        }
        if (((Configuration)object).smallestScreenWidthDp != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(XML_ATTR_SMALLEST_WIDTH);
            ((StringBuilder)charSequence).append(((Configuration)object).smallestScreenWidthDp);
            ((StringBuilder)charSequence).append("dp");
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if (((Configuration)object).screenWidthDp != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("w");
            ((StringBuilder)charSequence).append(((Configuration)object).screenWidthDp);
            ((StringBuilder)charSequence).append("dp");
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if (((Configuration)object).screenHeightDp != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("h");
            ((StringBuilder)charSequence).append(((Configuration)object).screenHeightDp);
            ((StringBuilder)charSequence).append("dp");
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if ((n = ((Configuration)object).screenLayout & 15) != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        arrayList.add("xlarge");
                    }
                } else {
                    arrayList.add("large");
                }
            } else {
                arrayList.add("normal");
            }
        } else {
            arrayList.add("small");
        }
        n = ((Configuration)object).screenLayout & 48;
        if (n != 16) {
            if (n == 32) {
                arrayList.add("long");
            }
        } else {
            arrayList.add("notlong");
        }
        n = ((Configuration)object).screenLayout & 768;
        if (n != 256) {
            if (n == 512) {
                arrayList.add("round");
            }
        } else {
            arrayList.add("notround");
        }
        n = ((Configuration)object).colorMode & 3;
        if (n != 1) {
            if (n == 2) {
                arrayList.add("widecg");
            }
        } else {
            arrayList.add("nowidecg");
        }
        n = ((Configuration)object).colorMode & 12;
        if (n != 4) {
            if (n == 8) {
                arrayList.add("highdr");
            }
        } else {
            arrayList.add("lowdr");
        }
        n = ((Configuration)object).orientation;
        if (n != 1) {
            if (n == 2) {
                arrayList.add("land");
            }
        } else {
            arrayList.add("port");
        }
        switch (((Configuration)object).uiMode & 15) {
            default: {
                break;
            }
            case 7: {
                arrayList.add("vrheadset");
                break;
            }
            case 6: {
                arrayList.add("watch");
                break;
            }
            case 5: {
                arrayList.add("appliance");
                break;
            }
            case 4: {
                arrayList.add("television");
                break;
            }
            case 3: {
                arrayList.add("car");
                break;
            }
            case 2: {
                arrayList.add("desk");
            }
        }
        n = ((Configuration)object).uiMode & 48;
        if (n != 16) {
            if (n == 32) {
                arrayList.add("night");
            }
        } else {
            arrayList.add("notnight");
        }
        n = ((Configuration)object).densityDpi;
        if (n != 0) {
            if (n != 120) {
                if (n != 160) {
                    if (n != 213) {
                        if (n != 240) {
                            if (n != 320) {
                                if (n != 480) {
                                    if (n != 640) {
                                        switch (n) {
                                            default: {
                                                charSequence = new StringBuilder();
                                                ((StringBuilder)charSequence).append(((Configuration)object).densityDpi);
                                                ((StringBuilder)charSequence).append("dpi");
                                                arrayList.add(((StringBuilder)charSequence).toString());
                                                break;
                                            }
                                            case 65535: {
                                                arrayList.add("nodpi");
                                                break;
                                            }
                                            case 65534: {
                                                arrayList.add("anydpi");
                                                break;
                                            }
                                        }
                                    } else {
                                        arrayList.add("xxxhdpi");
                                    }
                                } else {
                                    arrayList.add("xxhdpi");
                                }
                            } else {
                                arrayList.add("xhdpi");
                            }
                        } else {
                            arrayList.add("hdpi");
                        }
                    } else {
                        arrayList.add("tvdpi");
                    }
                } else {
                    arrayList.add("mdpi");
                }
            } else {
                arrayList.add("ldpi");
            }
        }
        if ((n = ((Configuration)object).touchscreen) != 1) {
            if (n == 3) {
                arrayList.add("finger");
            }
        } else {
            arrayList.add("notouch");
        }
        n = ((Configuration)object).keyboardHidden;
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    arrayList.add("keyssoft");
                }
            } else {
                arrayList.add("keyshidden");
            }
        } else {
            arrayList.add("keysexposed");
        }
        n = ((Configuration)object).keyboard;
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    arrayList.add("12key");
                }
            } else {
                arrayList.add("qwerty");
            }
        } else {
            arrayList.add("nokeys");
        }
        n = ((Configuration)object).navigationHidden;
        if (n != 1) {
            if (n == 2) {
                arrayList.add("navhidden");
            }
        } else {
            arrayList.add("navexposed");
        }
        n = ((Configuration)object).navigation;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        arrayList.add("wheel");
                    }
                } else {
                    arrayList.add("trackball");
                }
            } else {
                arrayList.add("dpad");
            }
        } else {
            arrayList.add("nonav");
        }
        if (displayMetrics != null) {
            int n2;
            if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
                n = displayMetrics.widthPixels;
                n2 = displayMetrics.heightPixels;
            } else {
                n = displayMetrics.heightPixels;
                n2 = displayMetrics.widthPixels;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("x");
            ((StringBuilder)object).append(n2);
            arrayList.add(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("v");
        ((StringBuilder)object).append(Build.VERSION.RESOURCES_SDK_INT);
        arrayList.add(((StringBuilder)object).toString());
        return TextUtils.join((CharSequence)"-", arrayList);
    }

    public static String uiModeToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 7: {
                return "UI_MODE_TYPE_VR_HEADSET";
            }
            case 6: {
                return "UI_MODE_TYPE_WATCH";
            }
            case 5: {
                return "UI_MODE_TYPE_APPLIANCE";
            }
            case 4: {
                return "UI_MODE_TYPE_TELEVISION";
            }
            case 3: {
                return "UI_MODE_TYPE_CAR";
            }
            case 2: {
                return "UI_MODE_TYPE_DESK";
            }
            case 1: {
                return "UI_MODE_TYPE_NORMAL";
            }
            case 0: 
        }
        return "UI_MODE_TYPE_UNDEFINED";
    }

    public static void writeXmlAttrs(XmlSerializer xmlSerializer, Configuration configuration) throws IOException {
        XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_FONT_SCALE, Float.floatToIntBits(configuration.fontScale));
        int n = configuration.mcc;
        if (n != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_MCC, n);
        }
        if ((n = configuration.mnc) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_MNC, n);
        }
        configuration.fixUpLocaleList();
        if (!configuration.mLocaleList.isEmpty()) {
            XmlUtils.writeStringAttribute(xmlSerializer, XML_ATTR_LOCALES, configuration.mLocaleList.toLanguageTags());
        }
        if ((n = configuration.touchscreen) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_TOUCHSCREEN, n);
        }
        if ((n = configuration.keyboard) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_KEYBOARD, n);
        }
        if ((n = configuration.keyboardHidden) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_KEYBOARD_HIDDEN, n);
        }
        if ((n = configuration.hardKeyboardHidden) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_HARD_KEYBOARD_HIDDEN, n);
        }
        if ((n = configuration.navigation) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_NAVIGATION, n);
        }
        if ((n = configuration.navigationHidden) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_NAVIGATION_HIDDEN, n);
        }
        if ((n = configuration.orientation) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_ORIENTATION, n);
        }
        if ((n = configuration.screenLayout) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_SCREEN_LAYOUT, n);
        }
        if ((n = configuration.colorMode) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_COLOR_MODE, n);
        }
        if ((n = configuration.uiMode) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_UI_MODE, n);
        }
        if ((n = configuration.screenWidthDp) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_SCREEN_WIDTH, n);
        }
        if ((n = configuration.screenHeightDp) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_SCREEN_HEIGHT, n);
        }
        if ((n = configuration.smallestScreenWidthDp) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_SMALLEST_WIDTH, n);
        }
        if ((n = configuration.densityDpi) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_DENSITY, n);
        }
    }

    public void clearLocales() {
        this.mLocaleList = LocaleList.getEmptyLocaleList();
        this.locale = null;
    }

    @Override
    public int compareTo(Configuration configuration) {
        float f = this.fontScale;
        float f2 = configuration.fontScale;
        if (f < f2) {
            return -1;
        }
        if (f > f2) {
            return 1;
        }
        int n = this.mcc - configuration.mcc;
        if (n != 0) {
            return n;
        }
        n = this.mnc - configuration.mnc;
        if (n != 0) {
            return n;
        }
        this.fixUpLocaleList();
        configuration.fixUpLocaleList();
        if (this.mLocaleList.isEmpty()) {
            if (!configuration.mLocaleList.isEmpty()) {
                return 1;
            }
        } else {
            if (configuration.mLocaleList.isEmpty()) {
                return -1;
            }
            int n2 = Math.min(this.mLocaleList.size(), configuration.mLocaleList.size());
            for (n = 0; n < n2; ++n) {
                Locale locale = this.mLocaleList.get(n);
                Locale locale2 = configuration.mLocaleList.get(n);
                int n3 = locale.getLanguage().compareTo(locale2.getLanguage());
                if (n3 != 0) {
                    return n3;
                }
                n3 = locale.getCountry().compareTo(locale2.getCountry());
                if (n3 != 0) {
                    return n3;
                }
                n3 = locale.getVariant().compareTo(locale2.getVariant());
                if (n3 != 0) {
                    return n3;
                }
                n3 = locale.toLanguageTag().compareTo(locale2.toLanguageTag());
                if (n3 == 0) continue;
                return n3;
            }
            n = this.mLocaleList.size() - configuration.mLocaleList.size();
            if (n != 0) {
                return n;
            }
        }
        if ((n = this.touchscreen - configuration.touchscreen) != 0) {
            return n;
        }
        n = this.keyboard - configuration.keyboard;
        if (n != 0) {
            return n;
        }
        n = this.keyboardHidden - configuration.keyboardHidden;
        if (n != 0) {
            return n;
        }
        n = this.hardKeyboardHidden - configuration.hardKeyboardHidden;
        if (n != 0) {
            return n;
        }
        n = this.navigation - configuration.navigation;
        if (n != 0) {
            return n;
        }
        n = this.navigationHidden - configuration.navigationHidden;
        if (n != 0) {
            return n;
        }
        n = this.orientation - configuration.orientation;
        if (n != 0) {
            return n;
        }
        n = this.colorMode - configuration.colorMode;
        if (n != 0) {
            return n;
        }
        n = this.screenLayout - configuration.screenLayout;
        if (n != 0) {
            return n;
        }
        n = this.uiMode - configuration.uiMode;
        if (n != 0) {
            return n;
        }
        n = this.screenWidthDp - configuration.screenWidthDp;
        if (n != 0) {
            return n;
        }
        n = this.screenHeightDp - configuration.screenHeightDp;
        if (n != 0) {
            return n;
        }
        n = this.smallestScreenWidthDp - configuration.smallestScreenWidthDp;
        if (n != 0) {
            return n;
        }
        n = this.densityDpi - configuration.densityDpi;
        if (n != 0) {
            return n;
        }
        n = this.assetsSeq - configuration.assetsSeq;
        if (n != 0) {
            return n;
        }
        n = this.windowConfiguration.compareTo(configuration.windowConfiguration);
        if (n != 0) {
            return n;
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int diff(Configuration configuration) {
        return this.diff(configuration, false, false);
    }

    public int diff(Configuration configuration, boolean bl, boolean bl2) {
        int n;
        int n2;
        block127 : {
            block126 : {
                block125 : {
                    block124 : {
                        block123 : {
                            block122 : {
                                block121 : {
                                    block120 : {
                                        block119 : {
                                            int n3;
                                            block118 : {
                                                block117 : {
                                                    block116 : {
                                                        block115 : {
                                                            block114 : {
                                                                block113 : {
                                                                    block112 : {
                                                                        block111 : {
                                                                            block110 : {
                                                                                block109 : {
                                                                                    block108 : {
                                                                                        block107 : {
                                                                                            block106 : {
                                                                                                block105 : {
                                                                                                    block104 : {
                                                                                                        block103 : {
                                                                                                            block102 : {
                                                                                                                block101 : {
                                                                                                                    block100 : {
                                                                                                                        block99 : {
                                                                                                                            block98 : {
                                                                                                                                block97 : {
                                                                                                                                    block96 : {
                                                                                                                                        block95 : {
                                                                                                                                            block94 : {
                                                                                                                                                block93 : {
                                                                                                                                                    block92 : {
                                                                                                                                                        block91 : {
                                                                                                                                                            block90 : {
                                                                                                                                                                block89 : {
                                                                                                                                                                    block88 : {
                                                                                                                                                                        block87 : {
                                                                                                                                                                            block86 : {
                                                                                                                                                                                n2 = 0;
                                                                                                                                                                                if (bl) break block86;
                                                                                                                                                                                n = n2;
                                                                                                                                                                                if (!(configuration.fontScale > 0.0f)) break block87;
                                                                                                                                                                            }
                                                                                                                                                                            n = n2;
                                                                                                                                                                            if (this.fontScale != configuration.fontScale) {
                                                                                                                                                                                n = 0 | 1073741824;
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        if (bl) break block88;
                                                                                                                                                                        n2 = n;
                                                                                                                                                                        if (configuration.mcc == 0) break block89;
                                                                                                                                                                    }
                                                                                                                                                                    n2 = n;
                                                                                                                                                                    if (this.mcc != configuration.mcc) {
                                                                                                                                                                        n2 = n | 1;
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                if (bl) break block90;
                                                                                                                                                                n = n2;
                                                                                                                                                                if (configuration.mnc == 0) break block91;
                                                                                                                                                            }
                                                                                                                                                            n = n2;
                                                                                                                                                            if (this.mnc != configuration.mnc) {
                                                                                                                                                                n = n2 | 2;
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        this.fixUpLocaleList();
                                                                                                                                                        configuration.fixUpLocaleList();
                                                                                                                                                        if (bl) break block92;
                                                                                                                                                        n2 = n;
                                                                                                                                                        if (configuration.mLocaleList.isEmpty()) break block93;
                                                                                                                                                    }
                                                                                                                                                    n2 = n;
                                                                                                                                                    if (!this.mLocaleList.equals(configuration.mLocaleList)) {
                                                                                                                                                        n2 = n | 4 | 8192;
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                n3 = configuration.screenLayout & 192;
                                                                                                                                                if (bl) break block94;
                                                                                                                                                n = n2;
                                                                                                                                                if (n3 == 0) break block95;
                                                                                                                                            }
                                                                                                                                            n = n2;
                                                                                                                                            if (n3 != (this.screenLayout & 192)) {
                                                                                                                                                n = n2 | 8192;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        if (bl) break block96;
                                                                                                                                        n3 = n;
                                                                                                                                        if (configuration.touchscreen == 0) break block97;
                                                                                                                                    }
                                                                                                                                    n3 = n;
                                                                                                                                    if (this.touchscreen != configuration.touchscreen) {
                                                                                                                                        n3 = n | 8;
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                if (bl) break block98;
                                                                                                                                n2 = n3;
                                                                                                                                if (configuration.keyboard == 0) break block99;
                                                                                                                            }
                                                                                                                            n2 = n3;
                                                                                                                            if (this.keyboard != configuration.keyboard) {
                                                                                                                                n2 = n3 | 16;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        if (bl) break block100;
                                                                                                                        n = n2;
                                                                                                                        if (configuration.keyboardHidden == 0) break block101;
                                                                                                                    }
                                                                                                                    n = n2;
                                                                                                                    if (this.keyboardHidden != configuration.keyboardHidden) {
                                                                                                                        n = n2 | 32;
                                                                                                                    }
                                                                                                                }
                                                                                                                if (bl) break block102;
                                                                                                                n2 = n;
                                                                                                                if (configuration.hardKeyboardHidden == 0) break block103;
                                                                                                            }
                                                                                                            n2 = n;
                                                                                                            if (this.hardKeyboardHidden != configuration.hardKeyboardHidden) {
                                                                                                                n2 = n | 32;
                                                                                                            }
                                                                                                        }
                                                                                                        if (bl) break block104;
                                                                                                        n3 = n2;
                                                                                                        if (configuration.navigation == 0) break block105;
                                                                                                    }
                                                                                                    n3 = n2;
                                                                                                    if (this.navigation != configuration.navigation) {
                                                                                                        n3 = n2 | 64;
                                                                                                    }
                                                                                                }
                                                                                                if (bl) break block106;
                                                                                                n = n3;
                                                                                                if (configuration.navigationHidden == 0) break block107;
                                                                                            }
                                                                                            n = n3;
                                                                                            if (this.navigationHidden != configuration.navigationHidden) {
                                                                                                n = n3 | 32;
                                                                                            }
                                                                                        }
                                                                                        if (bl) break block108;
                                                                                        n2 = n;
                                                                                        if (configuration.orientation == 0) break block109;
                                                                                    }
                                                                                    n2 = n;
                                                                                    if (this.orientation != configuration.orientation) {
                                                                                        n2 = n | 128;
                                                                                    }
                                                                                }
                                                                                if (bl) break block110;
                                                                                n = n2;
                                                                                if (Configuration.getScreenLayoutNoDirection(configuration.screenLayout) == 0) break block111;
                                                                            }
                                                                            n = n2;
                                                                            if (Configuration.getScreenLayoutNoDirection(this.screenLayout) != Configuration.getScreenLayoutNoDirection(configuration.screenLayout)) {
                                                                                n = n2 | 256;
                                                                            }
                                                                        }
                                                                        if (bl) break block112;
                                                                        n2 = n;
                                                                        if ((configuration.colorMode & 12) == 0) break block113;
                                                                    }
                                                                    n2 = n;
                                                                    if ((this.colorMode & 12) != (configuration.colorMode & 12)) {
                                                                        n2 = n | 16384;
                                                                    }
                                                                }
                                                                if (bl) break block114;
                                                                n = n2;
                                                                if ((configuration.colorMode & 3) == 0) break block115;
                                                            }
                                                            n = n2;
                                                            if ((this.colorMode & 3) != (configuration.colorMode & 3)) {
                                                                n = n2 | 16384;
                                                            }
                                                        }
                                                        if (bl) break block116;
                                                        n3 = n;
                                                        if (configuration.uiMode == 0) break block117;
                                                    }
                                                    n3 = n;
                                                    if (this.uiMode != configuration.uiMode) {
                                                        n3 = n | 512;
                                                    }
                                                }
                                                if (bl) break block118;
                                                n2 = n3;
                                                if (configuration.screenWidthDp == 0) break block119;
                                            }
                                            n2 = n3;
                                            if (this.screenWidthDp != configuration.screenWidthDp) {
                                                n2 = n3 | 1024;
                                            }
                                        }
                                        if (bl) break block120;
                                        n = n2;
                                        if (configuration.screenHeightDp == 0) break block121;
                                    }
                                    n = n2;
                                    if (this.screenHeightDp != configuration.screenHeightDp) {
                                        n = n2 | 1024;
                                    }
                                }
                                if (bl) break block122;
                                n2 = n;
                                if (configuration.smallestScreenWidthDp == 0) break block123;
                            }
                            n2 = n;
                            if (this.smallestScreenWidthDp != configuration.smallestScreenWidthDp) {
                                n2 = n | 2048;
                            }
                        }
                        if (bl) break block124;
                        n = n2;
                        if (configuration.densityDpi == 0) break block125;
                    }
                    n = n2;
                    if (this.densityDpi != configuration.densityDpi) {
                        n = n2 | 4096;
                    }
                }
                if (bl) break block126;
                n2 = n;
                if (configuration.assetsSeq == 0) break block127;
            }
            n2 = n;
            if (this.assetsSeq != configuration.assetsSeq) {
                n2 = n | Integer.MIN_VALUE;
            }
        }
        n = n2;
        if (!bl2) {
            n = n2;
            if (this.windowConfiguration.diff(configuration.windowConfiguration, bl) != 0L) {
                n = n2 | 536870912;
            }
        }
        return n;
    }

    public int diffPublicOnly(Configuration configuration) {
        return this.diff(configuration, false, true);
    }

    public boolean equals(Configuration configuration) {
        boolean bl = false;
        if (configuration == null) {
            return false;
        }
        if (configuration == this) {
            return true;
        }
        if (this.compareTo(configuration) == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean equals(Object object) {
        try {
            boolean bl = this.equals((Configuration)object);
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public int getLayoutDirection() {
        int n = (this.screenLayout & 192) == 128 ? 1 : 0;
        return n;
    }

    public LocaleList getLocales() {
        this.fixUpLocaleList();
        return this.mLocaleList;
    }

    public int hashCode() {
        return ((((((((((((((((((17 * 31 + Float.floatToIntBits(this.fontScale)) * 31 + this.mcc) * 31 + this.mnc) * 31 + this.mLocaleList.hashCode()) * 31 + this.touchscreen) * 31 + this.keyboard) * 31 + this.keyboardHidden) * 31 + this.hardKeyboardHidden) * 31 + this.navigation) * 31 + this.navigationHidden) * 31 + this.orientation) * 31 + this.screenLayout) * 31 + this.colorMode) * 31 + this.uiMode) * 31 + this.screenWidthDp) * 31 + this.screenHeightDp) * 31 + this.smallestScreenWidthDp) * 31 + this.densityDpi) * 31 + this.assetsSeq;
    }

    public boolean isLayoutSizeAtLeast(int n) {
        int n2 = this.screenLayout & 15;
        boolean bl = false;
        if (n2 == 0) {
            return false;
        }
        if (n2 >= n) {
            bl = true;
        }
        return bl;
    }

    public boolean isOtherSeqNewer(Configuration configuration) {
        boolean bl = false;
        if (configuration == null) {
            return false;
        }
        int n = configuration.seq;
        if (n == 0) {
            return true;
        }
        int n2 = this.seq;
        if (n2 == 0) {
            return true;
        }
        if ((n -= n2) > 65536) {
            return false;
        }
        if (n > 0) {
            bl = true;
        }
        return bl;
    }

    public boolean isScreenHdr() {
        boolean bl = (this.colorMode & 12) == 8;
        return bl;
    }

    public boolean isScreenRound() {
        boolean bl = (this.screenLayout & 768) == 512;
        return bl;
    }

    public boolean isScreenWideColorGamut() {
        boolean bl = (this.colorMode & 3) == 2;
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void makeDefault() {
        this.setToDefaults();
    }

    public void readFromParcel(Parcel parcel) {
        this.fontScale = parcel.readFloat();
        this.mcc = parcel.readInt();
        this.mnc = parcel.readInt();
        LocaleList localeList = this.mLocaleList = (LocaleList)parcel.readParcelable(LocaleList.class.getClassLoader());
        boolean bl = false;
        this.locale = localeList.get(0);
        if (parcel.readInt() == 1) {
            bl = true;
        }
        this.userSetLocale = bl;
        this.touchscreen = parcel.readInt();
        this.keyboard = parcel.readInt();
        this.keyboardHidden = parcel.readInt();
        this.hardKeyboardHidden = parcel.readInt();
        this.navigation = parcel.readInt();
        this.navigationHidden = parcel.readInt();
        this.orientation = parcel.readInt();
        this.screenLayout = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.uiMode = parcel.readInt();
        this.screenWidthDp = parcel.readInt();
        this.screenHeightDp = parcel.readInt();
        this.smallestScreenWidthDp = parcel.readInt();
        this.densityDpi = parcel.readInt();
        this.compatScreenWidthDp = parcel.readInt();
        this.compatScreenHeightDp = parcel.readInt();
        this.compatSmallestScreenWidthDp = parcel.readInt();
        this.windowConfiguration.setTo((WindowConfiguration)parcel.readValue(null));
        this.assetsSeq = parcel.readInt();
        this.seq = parcel.readInt();
    }

    /*
     * Exception decompiling
     */
    public void readFromProto(ProtoInputStream var1_1, long var2_2) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [17[CASE]], but top level block is 2[TRYBLOCK]
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

    public void setLayoutDirection(Locale locale) {
        int n = TextUtils.getLayoutDirectionFromLocale(locale);
        this.screenLayout = this.screenLayout & -193 | n + 1 << 6;
    }

    public void setLocale(Locale object) {
        object = object == null ? LocaleList.getEmptyLocaleList() : new LocaleList(new Locale[]{object});
        this.setLocales((LocaleList)object);
    }

    public void setLocales(LocaleList localeList) {
        if (localeList == null) {
            localeList = LocaleList.getEmptyLocaleList();
        }
        this.mLocaleList = localeList;
        this.locale = this.mLocaleList.get(0);
        this.setLayoutDirection(this.locale);
    }

    public void setTo(Configuration configuration) {
        this.fontScale = configuration.fontScale;
        this.mcc = configuration.mcc;
        this.mnc = configuration.mnc;
        Locale locale = configuration.locale;
        locale = locale == null ? null : (Locale)locale.clone();
        this.locale = locale;
        configuration.fixUpLocaleList();
        this.mLocaleList = configuration.mLocaleList;
        this.userSetLocale = configuration.userSetLocale;
        this.touchscreen = configuration.touchscreen;
        this.keyboard = configuration.keyboard;
        this.keyboardHidden = configuration.keyboardHidden;
        this.hardKeyboardHidden = configuration.hardKeyboardHidden;
        this.navigation = configuration.navigation;
        this.navigationHidden = configuration.navigationHidden;
        this.orientation = configuration.orientation;
        this.screenLayout = configuration.screenLayout;
        this.colorMode = configuration.colorMode;
        this.uiMode = configuration.uiMode;
        this.screenWidthDp = configuration.screenWidthDp;
        this.screenHeightDp = configuration.screenHeightDp;
        this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        this.densityDpi = configuration.densityDpi;
        this.compatScreenWidthDp = configuration.compatScreenWidthDp;
        this.compatScreenHeightDp = configuration.compatScreenHeightDp;
        this.compatSmallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        this.assetsSeq = configuration.assetsSeq;
        this.seq = configuration.seq;
        this.windowConfiguration.setTo(configuration.windowConfiguration);
    }

    public void setToDefaults() {
        this.fontScale = 1.0f;
        this.mnc = 0;
        this.mcc = 0;
        this.mLocaleList = LocaleList.getEmptyLocaleList();
        this.locale = null;
        this.userSetLocale = false;
        this.touchscreen = 0;
        this.keyboard = 0;
        this.keyboardHidden = 0;
        this.hardKeyboardHidden = 0;
        this.navigation = 0;
        this.navigationHidden = 0;
        this.orientation = 0;
        this.screenLayout = 0;
        this.colorMode = 0;
        this.uiMode = 0;
        this.compatScreenWidthDp = 0;
        this.screenWidthDp = 0;
        this.compatScreenHeightDp = 0;
        this.screenHeightDp = 0;
        this.compatSmallestScreenWidthDp = 0;
        this.smallestScreenWidthDp = 0;
        this.densityDpi = 0;
        this.assetsSeq = 0;
        this.seq = 0;
        this.windowConfiguration.setToDefaults();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("{");
        stringBuilder.append(this.fontScale);
        stringBuilder.append(" ");
        int n = this.mcc;
        if (n != 0) {
            stringBuilder.append(n);
            stringBuilder.append(XML_ATTR_MCC);
        } else {
            stringBuilder.append("?mcc");
        }
        n = this.mnc;
        if (n != 0) {
            stringBuilder.append(n);
            stringBuilder.append(XML_ATTR_MNC);
        } else {
            stringBuilder.append("?mnc");
        }
        this.fixUpLocaleList();
        if (!this.mLocaleList.isEmpty()) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mLocaleList);
        } else {
            stringBuilder.append(" ?localeList");
        }
        n = this.screenLayout & 192;
        if (n != 0) {
            if (n != 64) {
                if (n != 128) {
                    stringBuilder.append(" layoutDir=");
                    stringBuilder.append(n >> 6);
                } else {
                    stringBuilder.append(" ldrtl");
                }
            } else {
                stringBuilder.append(" ldltr");
            }
        } else {
            stringBuilder.append(" ?layoutDir");
        }
        if (this.smallestScreenWidthDp != 0) {
            stringBuilder.append(" sw");
            stringBuilder.append(this.smallestScreenWidthDp);
            stringBuilder.append("dp");
        } else {
            stringBuilder.append(" ?swdp");
        }
        if (this.screenWidthDp != 0) {
            stringBuilder.append(" w");
            stringBuilder.append(this.screenWidthDp);
            stringBuilder.append("dp");
        } else {
            stringBuilder.append(" ?wdp");
        }
        if (this.screenHeightDp != 0) {
            stringBuilder.append(" h");
            stringBuilder.append(this.screenHeightDp);
            stringBuilder.append("dp");
        } else {
            stringBuilder.append(" ?hdp");
        }
        if (this.densityDpi != 0) {
            stringBuilder.append(" ");
            stringBuilder.append(this.densityDpi);
            stringBuilder.append("dpi");
        } else {
            stringBuilder.append(" ?density");
        }
        n = this.screenLayout & 15;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            stringBuilder.append(" layoutSize=");
                            stringBuilder.append(this.screenLayout & 15);
                        } else {
                            stringBuilder.append(" xlrg");
                        }
                    } else {
                        stringBuilder.append(" lrg");
                    }
                } else {
                    stringBuilder.append(" nrml");
                }
            } else {
                stringBuilder.append(" smll");
            }
        } else {
            stringBuilder.append(" ?lsize");
        }
        n = this.screenLayout & 48;
        if (n != 0) {
            if (n != 16) {
                if (n != 32) {
                    stringBuilder.append(" layoutLong=");
                    stringBuilder.append(this.screenLayout & 48);
                } else {
                    stringBuilder.append(" long");
                }
            }
        } else {
            stringBuilder.append(" ?long");
        }
        n = this.colorMode & 12;
        if (n != 0) {
            if (n != 4) {
                if (n != 8) {
                    stringBuilder.append(" dynamicRange=");
                    stringBuilder.append(this.colorMode & 12);
                } else {
                    stringBuilder.append(" hdr");
                }
            }
        } else {
            stringBuilder.append(" ?ldr");
        }
        n = this.colorMode & 3;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    stringBuilder.append(" wideColorGamut=");
                    stringBuilder.append(this.colorMode & 3);
                } else {
                    stringBuilder.append(" widecg");
                }
            }
        } else {
            stringBuilder.append(" ?wideColorGamut");
        }
        n = this.orientation;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    stringBuilder.append(" orien=");
                    stringBuilder.append(this.orientation);
                } else {
                    stringBuilder.append(" land");
                }
            } else {
                stringBuilder.append(" port");
            }
        } else {
            stringBuilder.append(" ?orien");
        }
        switch (this.uiMode & 15) {
            default: {
                stringBuilder.append(" uimode=");
                stringBuilder.append(this.uiMode & 15);
                break;
            }
            case 7: {
                stringBuilder.append(" vrheadset");
                break;
            }
            case 6: {
                stringBuilder.append(" watch");
                break;
            }
            case 5: {
                stringBuilder.append(" appliance");
                break;
            }
            case 4: {
                stringBuilder.append(" television");
                break;
            }
            case 3: {
                stringBuilder.append(" car");
                break;
            }
            case 2: {
                stringBuilder.append(" desk");
                break;
            }
            case 1: {
                break;
            }
            case 0: {
                stringBuilder.append(" ?uimode");
            }
        }
        n = this.uiMode & 48;
        if (n != 0) {
            if (n != 16) {
                if (n != 32) {
                    stringBuilder.append(" night=");
                    stringBuilder.append(this.uiMode & 48);
                } else {
                    stringBuilder.append(" night");
                }
            }
        } else {
            stringBuilder.append(" ?night");
        }
        n = this.touchscreen;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        stringBuilder.append(" touch=");
                        stringBuilder.append(this.touchscreen);
                    } else {
                        stringBuilder.append(" finger");
                    }
                } else {
                    stringBuilder.append(" stylus");
                }
            } else {
                stringBuilder.append(" -touch");
            }
        } else {
            stringBuilder.append(" ?touch");
        }
        n = this.keyboard;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        stringBuilder.append(" keys=");
                        stringBuilder.append(this.keyboard);
                    } else {
                        stringBuilder.append(" 12key");
                    }
                } else {
                    stringBuilder.append(" qwerty");
                }
            } else {
                stringBuilder.append(" -keyb");
            }
        } else {
            stringBuilder.append(" ?keyb");
        }
        n = this.keyboardHidden;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        stringBuilder.append("/");
                        stringBuilder.append(this.keyboardHidden);
                    } else {
                        stringBuilder.append("/s");
                    }
                } else {
                    stringBuilder.append("/h");
                }
            } else {
                stringBuilder.append("/v");
            }
        } else {
            stringBuilder.append("/?");
        }
        n = this.hardKeyboardHidden;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    stringBuilder.append("/");
                    stringBuilder.append(this.hardKeyboardHidden);
                } else {
                    stringBuilder.append("/h");
                }
            } else {
                stringBuilder.append("/v");
            }
        } else {
            stringBuilder.append("/?");
        }
        n = this.navigation;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            stringBuilder.append(" nav=");
                            stringBuilder.append(this.navigation);
                        } else {
                            stringBuilder.append(" wheel");
                        }
                    } else {
                        stringBuilder.append(" tball");
                    }
                } else {
                    stringBuilder.append(" dpad");
                }
            } else {
                stringBuilder.append(" -nav");
            }
        } else {
            stringBuilder.append(" ?nav");
        }
        n = this.navigationHidden;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    stringBuilder.append("/");
                    stringBuilder.append(this.navigationHidden);
                } else {
                    stringBuilder.append("/h");
                }
            } else {
                stringBuilder.append("/v");
            }
        } else {
            stringBuilder.append("/?");
        }
        stringBuilder.append(" winConfig=");
        stringBuilder.append(this.windowConfiguration);
        if (this.assetsSeq != 0) {
            stringBuilder.append(" as.");
            stringBuilder.append(this.assetsSeq);
        }
        if (this.seq != 0) {
            stringBuilder.append(" s.");
            stringBuilder.append(this.seq);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unset() {
        this.setToDefaults();
        this.fontScale = 0.0f;
    }

    public int updateFrom(Configuration configuration) {
        int n;
        int n2;
        int n3;
        int n4;
        block59 : {
            block60 : {
                n = 0;
                float f = configuration.fontScale;
                n4 = n;
                if (f > 0.0f) {
                    n4 = n;
                    if (this.fontScale != f) {
                        n4 = 0 | 1073741824;
                        this.fontScale = f;
                    }
                }
                n2 = configuration.mcc;
                n = n4;
                if (n2 != 0) {
                    n = n4;
                    if (this.mcc != n2) {
                        n = n4 | 1;
                        this.mcc = n2;
                    }
                }
                n2 = configuration.mnc;
                n4 = n;
                if (n2 != 0) {
                    n4 = n;
                    if (this.mnc != n2) {
                        n4 = n | 2;
                        this.mnc = n2;
                    }
                }
                this.fixUpLocaleList();
                configuration.fixUpLocaleList();
                n = n4;
                if (!configuration.mLocaleList.isEmpty()) {
                    n = n4;
                    if (!this.mLocaleList.equals(configuration.mLocaleList)) {
                        this.mLocaleList = configuration.mLocaleList;
                        n = n4 |= 4;
                        if (!configuration.locale.equals(this.locale)) {
                            this.locale = (Locale)configuration.locale.clone();
                            n = n4 | 8192;
                            this.setLayoutDirection(this.locale);
                        }
                    }
                }
                n3 = configuration.screenLayout & 192;
                n4 = n;
                if (n3 != 0) {
                    n2 = this.screenLayout;
                    n4 = n;
                    if (n3 != (n2 & 192)) {
                        this.screenLayout = n2 & -193 | n3;
                        n4 = n | 8192;
                    }
                }
                n2 = n4;
                if (!configuration.userSetLocale) break block59;
                if (!this.userSetLocale) break block60;
                n2 = n4;
                if ((n4 & 4) == 0) break block59;
            }
            n2 = n4 | 4;
            this.userSetLocale = true;
        }
        n4 = configuration.touchscreen;
        n = n2;
        if (n4 != 0) {
            n = n2;
            if (this.touchscreen != n4) {
                n = n2 | 8;
                this.touchscreen = n4;
            }
        }
        n2 = configuration.keyboard;
        n4 = n;
        if (n2 != 0) {
            n4 = n;
            if (this.keyboard != n2) {
                n4 = n | 16;
                this.keyboard = n2;
            }
        }
        n2 = configuration.keyboardHidden;
        n = n4;
        if (n2 != 0) {
            n = n4;
            if (this.keyboardHidden != n2) {
                n = n4 | 32;
                this.keyboardHidden = n2;
            }
        }
        n4 = configuration.hardKeyboardHidden;
        n2 = n;
        if (n4 != 0) {
            n2 = n;
            if (this.hardKeyboardHidden != n4) {
                n2 = n | 32;
                this.hardKeyboardHidden = n4;
            }
        }
        n = configuration.navigation;
        n4 = n2;
        if (n != 0) {
            n4 = n2;
            if (this.navigation != n) {
                n4 = n2 | 64;
                this.navigation = n;
            }
        }
        n2 = configuration.navigationHidden;
        n = n4;
        if (n2 != 0) {
            n = n4;
            if (this.navigationHidden != n2) {
                n = n4 | 32;
                this.navigationHidden = n2;
            }
        }
        n2 = configuration.orientation;
        n4 = n;
        if (n2 != 0) {
            n4 = n;
            if (this.orientation != n2) {
                n4 = n | 128;
                this.orientation = n2;
            }
        }
        n3 = configuration.screenLayout;
        n = n4;
        if ((n3 & 15) != 0) {
            n2 = this.screenLayout;
            n = n4;
            if ((n3 & 15) != (n2 & 15)) {
                n = n4 | 256;
                this.screenLayout = n3 & 15 | n2 & -16;
            }
        }
        n2 = configuration.screenLayout;
        n4 = n;
        if ((n2 & 48) != 0) {
            n3 = this.screenLayout;
            n4 = n;
            if ((n2 & 48) != (n3 & 48)) {
                n4 = n | 256;
                this.screenLayout = n2 & 48 | n3 & -49;
            }
        }
        n3 = configuration.screenLayout;
        n = n4;
        if ((n3 & 768) != 0) {
            n2 = this.screenLayout;
            n = n4;
            if ((n3 & 768) != (n2 & 768)) {
                n = n4 | 256;
                this.screenLayout = n3 & 768 | n2 & -769;
            }
        }
        n2 = configuration.screenLayout;
        n3 = this.screenLayout;
        n4 = n;
        if ((n2 & 268435456) != (n3 & 268435456)) {
            n4 = n;
            if (n2 != 0) {
                n4 = n | 256;
                this.screenLayout = n2 & 268435456 | -268435457 & n3;
            }
        }
        n3 = configuration.colorMode;
        n = n4;
        if ((n3 & 3) != 0) {
            n2 = this.colorMode;
            n = n4;
            if ((n3 & 3) != (n2 & 3)) {
                n = n4 | 16384;
                this.colorMode = n3 & 3 | n2 & -4;
            }
        }
        n2 = configuration.colorMode;
        n4 = n;
        if ((n2 & 12) != 0) {
            n3 = this.colorMode;
            n4 = n;
            if ((n2 & 12) != (n3 & 12)) {
                n4 = n | 16384;
                this.colorMode = n2 & 12 | n3 & -13;
            }
        }
        n2 = configuration.uiMode;
        n = n4;
        if (n2 != 0) {
            n3 = this.uiMode;
            n = n4;
            if (n3 != n2) {
                n4 |= 512;
                if ((n2 & 15) != 0) {
                    this.uiMode = n2 & 15 | n3 & -16;
                }
                n2 = configuration.uiMode;
                n = n4;
                if ((n2 & 48) != 0) {
                    this.uiMode = n2 & 48 | this.uiMode & -49;
                    n = n4;
                }
            }
        }
        n2 = configuration.screenWidthDp;
        n4 = n;
        if (n2 != 0) {
            n4 = n;
            if (this.screenWidthDp != n2) {
                n4 = n | 1024;
                this.screenWidthDp = n2;
            }
        }
        n2 = configuration.screenHeightDp;
        n = n4;
        if (n2 != 0) {
            n = n4;
            if (this.screenHeightDp != n2) {
                n = n4 | 1024;
                this.screenHeightDp = n2;
            }
        }
        n2 = configuration.smallestScreenWidthDp;
        n4 = n;
        if (n2 != 0) {
            n4 = n;
            if (this.smallestScreenWidthDp != n2) {
                n4 = n | 2048;
                this.smallestScreenWidthDp = n2;
            }
        }
        n2 = configuration.densityDpi;
        n = n4;
        if (n2 != 0) {
            n = n4;
            if (this.densityDpi != n2) {
                n = n4 | 4096;
                this.densityDpi = n2;
            }
        }
        if ((n4 = configuration.compatScreenWidthDp) != 0) {
            this.compatScreenWidthDp = n4;
        }
        if ((n4 = configuration.compatScreenHeightDp) != 0) {
            this.compatScreenHeightDp = n4;
        }
        if ((n4 = configuration.compatSmallestScreenWidthDp) != 0) {
            this.compatSmallestScreenWidthDp = n4;
        }
        n2 = configuration.assetsSeq;
        n4 = n;
        if (n2 != 0) {
            n4 = n;
            if (n2 != this.assetsSeq) {
                n4 = n | Integer.MIN_VALUE;
                this.assetsSeq = n2;
            }
        }
        if ((n = configuration.seq) != 0) {
            this.seq = n;
        }
        n = n4;
        if (this.windowConfiguration.updateFrom(configuration.windowConfiguration) != 0) {
            n = n4 | 536870912;
        }
        return n;
    }

    public void writeResConfigToProto(ProtoOutputStream protoOutputStream, long l, DisplayMetrics displayMetrics) {
        int n;
        int n2;
        if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
            n = displayMetrics.widthPixels;
            n2 = displayMetrics.heightPixels;
        } else {
            n = displayMetrics.heightPixels;
            n2 = displayMetrics.widthPixels;
        }
        l = protoOutputStream.start(l);
        this.writeToProto(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1155346202626L, Build.VERSION.RESOURCES_SDK_INT);
        protoOutputStream.write(1155346202627L, n);
        protoOutputStream.write(1155346202628L, n2);
        protoOutputStream.end(l);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.fontScale);
        parcel.writeInt(this.mcc);
        parcel.writeInt(this.mnc);
        this.fixUpLocaleList();
        parcel.writeParcelable(this.mLocaleList, n);
        if (this.userSetLocale) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.touchscreen);
        parcel.writeInt(this.keyboard);
        parcel.writeInt(this.keyboardHidden);
        parcel.writeInt(this.hardKeyboardHidden);
        parcel.writeInt(this.navigation);
        parcel.writeInt(this.navigationHidden);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.screenLayout);
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.uiMode);
        parcel.writeInt(this.screenWidthDp);
        parcel.writeInt(this.screenHeightDp);
        parcel.writeInt(this.smallestScreenWidthDp);
        parcel.writeInt(this.densityDpi);
        parcel.writeInt(this.compatScreenWidthDp);
        parcel.writeInt(this.compatScreenHeightDp);
        parcel.writeInt(this.compatSmallestScreenWidthDp);
        parcel.writeValue(this.windowConfiguration);
        parcel.writeInt(this.assetsSeq);
        parcel.writeInt(this.seq);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        this.writeToProto(protoOutputStream, l, false, false);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, boolean bl) {
        this.writeToProto(protoOutputStream, l, false, bl);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, boolean bl, boolean bl2) {
        l = protoOutputStream.start(l);
        if (!bl2) {
            protoOutputStream.write(1108101562369L, this.fontScale);
            protoOutputStream.write(1155346202626L, this.mcc);
            protoOutputStream.write(1155346202627L, this.mnc);
            Parcelable parcelable = this.mLocaleList;
            if (parcelable != null) {
                ((LocaleList)parcelable).writeToProto(protoOutputStream, 2246267895812L);
            }
            protoOutputStream.write(1155346202629L, this.screenLayout);
            protoOutputStream.write(1155346202630L, this.colorMode);
            protoOutputStream.write(1155346202631L, this.touchscreen);
            protoOutputStream.write(1155346202632L, this.keyboard);
            protoOutputStream.write(1155346202633L, this.keyboardHidden);
            protoOutputStream.write(1155346202634L, this.hardKeyboardHidden);
            protoOutputStream.write(1155346202635L, this.navigation);
            protoOutputStream.write(1155346202636L, this.navigationHidden);
            protoOutputStream.write(1155346202638L, this.uiMode);
            protoOutputStream.write(1155346202641L, this.smallestScreenWidthDp);
            protoOutputStream.write(1155346202642L, this.densityDpi);
            if (!bl && (parcelable = this.windowConfiguration) != null) {
                ((WindowConfiguration)parcelable).writeToProto(protoOutputStream, 1146756268051L);
            }
        }
        protoOutputStream.write(1155346202637L, this.orientation);
        protoOutputStream.write(1155346202639L, this.screenWidthDp);
        protoOutputStream.write(1155346202640L, this.screenHeightDp);
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NativeConfig {
    }

}

