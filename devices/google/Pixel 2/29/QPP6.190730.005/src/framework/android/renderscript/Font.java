/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.renderscript.BaseObj;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.util.DisplayMetrics;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Font
extends BaseObj {
    private static Map<String, FontFamily> sFontFamilyMap;
    private static final String[] sMonoNames;
    private static final String[] sSansNames;
    private static final String[] sSerifNames;

    static {
        sSansNames = new String[]{"sans-serif", "arial", "helvetica", "tahoma", "verdana"};
        sSerifNames = new String[]{"serif", "times", "times new roman", "palatino", "georgia", "baskerville", "goudy", "fantasy", "cursive", "ITC Stone Serif"};
        sMonoNames = new String[]{"monospace", "courier", "courier new", "monaco"};
        Font.initFontFamilyMap();
    }

    Font(long l, RenderScript renderScript) {
        super(l, renderScript);
        this.guard.open("destroy");
    }

    private static void addFamilyToMap(FontFamily fontFamily) {
        for (int i = 0; i < fontFamily.mNames.length; ++i) {
            sFontFamilyMap.put(fontFamily.mNames[i], fontFamily);
        }
    }

    @UnsupportedAppUsage
    public static Font create(RenderScript renderScript, Resources resources, String string2, Style object, float f) {
        object = Font.getFontFileName(string2, object);
        string2 = Environment.getRootDirectory().getAbsolutePath();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("/fonts/");
        stringBuilder.append((String)object);
        return Font.createFromFile(renderScript, resources, stringBuilder.toString(), f);
    }

    public static Font createFromAsset(RenderScript object, Resources resources, String string2, float f) {
        ((RenderScript)object).validate();
        long l = ((RenderScript)object).nFontCreateFromAsset(resources.getAssets(), string2, f, resources.getDisplayMetrics().densityDpi);
        if (l != 0L) {
            return new Font(l, (RenderScript)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to create font from asset ");
        ((StringBuilder)object).append(string2);
        throw new RSRuntimeException(((StringBuilder)object).toString());
    }

    public static Font createFromFile(RenderScript renderScript, Resources resources, File file, float f) {
        return Font.createFromFile(renderScript, resources, file.getAbsolutePath(), f);
    }

    public static Font createFromFile(RenderScript object, Resources resources, String string2, float f) {
        ((RenderScript)object).validate();
        long l = ((RenderScript)object).nFontCreateFromFile(string2, f, resources.getDisplayMetrics().densityDpi);
        if (l != 0L) {
            return new Font(l, (RenderScript)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to create font from file ");
        ((StringBuilder)object).append(string2);
        throw new RSRuntimeException(((StringBuilder)object).toString());
    }

    public static Font createFromResource(RenderScript object, Resources resources, int n, float f) {
        int n2;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("R.");
        ((StringBuilder)object2).append(Integer.toString(n));
        String string2 = ((StringBuilder)object2).toString();
        ((RenderScript)object).validate();
        try {
            object2 = resources.openRawResource(n);
            n2 = resources.getDisplayMetrics().densityDpi;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to open resource ");
            stringBuilder.append(n);
            throw new RSRuntimeException(stringBuilder.toString());
        }
        if (object2 instanceof AssetManager.AssetInputStream) {
            long l = ((RenderScript)object).nFontCreateFromAssetStream(string2, f, n2, ((AssetManager.AssetInputStream)object2).getNativeAsset());
            if (l != 0L) {
                return new Font(l, (RenderScript)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to create font from resource ");
            ((StringBuilder)object).append(n);
            throw new RSRuntimeException(((StringBuilder)object).toString());
        }
        throw new RSRuntimeException("Unsupported asset stream created");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String getFontFileName(String object, Style style2) {
        if ((object = sFontFamilyMap.get(object)) == null) return "DroidSans.ttf";
        int n = 1.$SwitchMap$android$renderscript$Font$Style[style2.ordinal()];
        if (n == 1) return ((FontFamily)object).mNormalFileName;
        if (n == 2) return ((FontFamily)object).mBoldFileName;
        if (n == 3) return ((FontFamily)object).mItalicFileName;
        if (n != 4) return "DroidSans.ttf";
        return ((FontFamily)object).mBoldItalicFileName;
    }

    private static void initFontFamilyMap() {
        sFontFamilyMap = new HashMap<String, FontFamily>();
        FontFamily fontFamily = new FontFamily();
        fontFamily.mNames = sSansNames;
        fontFamily.mNormalFileName = "Roboto-Regular.ttf";
        fontFamily.mBoldFileName = "Roboto-Bold.ttf";
        fontFamily.mItalicFileName = "Roboto-Italic.ttf";
        fontFamily.mBoldItalicFileName = "Roboto-BoldItalic.ttf";
        Font.addFamilyToMap(fontFamily);
        fontFamily = new FontFamily();
        fontFamily.mNames = sSerifNames;
        fontFamily.mNormalFileName = "NotoSerif-Regular.ttf";
        fontFamily.mBoldFileName = "NotoSerif-Bold.ttf";
        fontFamily.mItalicFileName = "NotoSerif-Italic.ttf";
        fontFamily.mBoldItalicFileName = "NotoSerif-BoldItalic.ttf";
        Font.addFamilyToMap(fontFamily);
        fontFamily = new FontFamily();
        fontFamily.mNames = sMonoNames;
        fontFamily.mNormalFileName = "DroidSansMono.ttf";
        fontFamily.mBoldFileName = "DroidSansMono.ttf";
        fontFamily.mItalicFileName = "DroidSansMono.ttf";
        fontFamily.mBoldItalicFileName = "DroidSansMono.ttf";
        Font.addFamilyToMap(fontFamily);
    }

    private static class FontFamily {
        String mBoldFileName;
        String mBoldItalicFileName;
        String mItalicFileName;
        String[] mNames;
        String mNormalFileName;

        private FontFamily() {
        }
    }

    public static enum Style {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC;
        
    }

}

