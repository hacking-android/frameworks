/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.fonts;

import android.graphics.FontListParser;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontCustomizationParser;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontVariationAxis;
import android.text.FontConfig;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class SystemFonts {
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final String TAG = "SystemFonts";
    private static final FontConfig.Alias[] sAliases;
    private static final List<Font> sAvailableFonts;
    private static final Map<String, FontFamily[]> sSystemFallbackMap;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        ArrayMap<String, FontFamily[]> arrayMap = new ArrayMap<String, FontFamily[]>();
        ArrayList<Font> arrayList = new ArrayList<Font>();
        sAliases = SystemFonts.buildSystemFallback("/system/etc/fonts.xml", "/system/fonts/", SystemFonts.readFontCustomization("/product/etc/fonts_customization.xml", "/product/fonts/"), arrayMap, arrayList);
        sSystemFallbackMap = Collections.unmodifiableMap(arrayMap);
        sAvailableFonts = Collections.unmodifiableList(arrayList);
    }

    private SystemFonts() {
    }

    private static void appendNamedFamily(FontConfig.Family object, HashMap<String, ByteBuffer> object2, ArrayMap<String, ArrayList<FontFamily>> arrayMap, ArrayList<Font> arrayList) {
        String string2 = ((FontConfig.Family)object).getName();
        object2 = SystemFonts.createFontFamily(string2, Arrays.asList(((FontConfig.Family)object).getFonts()), ((FontConfig.Family)object).getLanguages(), ((FontConfig.Family)object).getVariant(), object2, arrayList);
        if (object2 == null) {
            return;
        }
        object = new ArrayList();
        ((ArrayList)object).add(object2);
        arrayMap.put(string2, (ArrayList<FontFamily>)object);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public static FontConfig.Alias[] buildSystemFallback(String object, String arrayList, FontCustomizationParser.Result result, ArrayMap<String, FontFamily[]> arrayMap, ArrayList<Font> list) {
        FontConfig.Family family;
        int n;
        Object object2 = new FileInputStream((String)object);
        object = FontListParser.parse((InputStream)object2, arrayList);
        object2 = new HashMap();
        FontConfig.Family[] arrfamily = ((FontConfig)object).getFamilies();
        arrayList = new ArrayList<FontConfig.Alias>();
        int n2 = arrfamily.length;
        for (n = 0; n < n2; ++n) {
            family = arrfamily[n];
            if (family.getName() == null) continue;
            SystemFonts.appendNamedFamily(family, object2, arrayList, list);
        }
        for (n = 0; n < result.mAdditionalNamedFamilies.size(); ++n) {
            SystemFonts.appendNamedFamily(result.mAdditionalNamedFamilies.get(n), object2, arrayList, list);
        }
        for (n = 0; n < arrfamily.length; ++n) {
            family = arrfamily[n];
            if (n != 0 && family.getName() != null) continue;
            SystemFonts.pushFamilyToFallback(family, arrayList, object2, list);
        }
        for (n = 0; n < ((ArrayMap)((Object)arrayList)).size(); ++n) {
            object2 = ((ArrayMap)((Object)arrayList)).keyAt(n);
            list = (List)((ArrayMap)((Object)arrayList)).valueAt(n);
            arrayMap.put((String)object2, list.toArray(new FontFamily[list.size()]));
        }
        try {
            arrayList = new ArrayList<FontConfig.Alias>();
            arrayList.addAll(Arrays.asList(((FontConfig)object).getAliases()));
            arrayList.addAll(result.mAdditionalAliases);
            return arrayList.toArray(new FontConfig.Alias[arrayList.size()]);
        }
        catch (IOException | XmlPullParserException throwable) {
            Log.e(TAG, "Failed initialize system fallbacks.", throwable);
            return ArrayUtils.emptyArray(FontConfig.Alias.class);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static FontFamily createFontFamily(String var0, List<FontConfig.Font> var1_2, String var2_3, int var3_4, Map<String, ByteBuffer> var4_5, ArrayList<Font> var5_6) {
        block10 : {
            var6_7 = var1_2.size();
            var7_8 = null;
            if (var6_7 == 0) {
                return null;
            }
            var0 = null;
            var6_7 = 0;
            do lbl-1000: // 2 sources:
            {
                block11 : {
                    var8_9 = var1_2.size();
                    var9_10 = 0;
                    if (var6_7 >= var8_9) break block10;
                    var10_11 = var1_2.get(var6_7);
                    var11_12 = var10_11.getFontName();
                    var13_14 = var12_13 = var4_5.get(var11_12);
                    if (var12_13 != null) break block11;
                    if (var4_5.containsKey(var11_12)) break block12;
                    var12_13 = SystemFonts.mmap(var11_12);
                    var4_5.put(var11_12, (ByteBuffer)var12_13);
                    var13_14 = var12_13;
                    if (var12_13 == null) break block12;
                }
                var14_15 = new File(var11_12);
                var12_13 = new Font.Builder((ByteBuffer)var13_14, var14_15, var2_3);
                var13_14 = var12_13.setWeight(var10_11.getWeight());
                if (var10_11.isItalic()) {
                    var9_10 = 1;
                }
                var13_14 = var13_14.setSlant(var9_10).setTtcIndex(var10_11.getTtcIndex()).setFontVariationSettings(var10_11.getAxes()).build();
                var5_6.add((Font)var13_14);
                break;
            } while (true);
            catch (IOException var0_1) {
                throw new RuntimeException(var0_1);
            }
            {
                block12 : {
                    if (var0 == null) {
                        var0 = new FontFamily.Builder((Font)var13_14);
                    } else {
                        var0.addFont((Font)var13_14);
                    }
                }
                ++var6_7;
                ** while (true)
            }
        }
        if (var0 != null) return var0.build(var2_3, var3_4, false);
        return var7_8;
    }

    public static FontConfig.Alias[] getAliases() {
        return sAliases;
    }

    public static Set<Font> getAvailableFonts() {
        HashSet<Font> hashSet = new HashSet<Font>();
        hashSet.addAll(sAvailableFonts);
        return hashSet;
    }

    public static Map<String, FontFamily[]> getRawSystemFallbackMap() {
        return sSystemFallbackMap;
    }

    public static FontFamily[] getSystemFallback(String arrfontFamily) {
        block0 : {
            if ((arrfontFamily = sSystemFallbackMap.get(arrfontFamily)) != null) break block0;
            arrfontFamily = sSystemFallbackMap.get(DEFAULT_FAMILY);
        }
        return arrfontFamily;
    }

    /*
     * Exception decompiling
     */
    private static ByteBuffer mmap(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    private static void pushFamilyToFallback(FontConfig.Family family, ArrayMap<String, ArrayList<FontFamily>> arrayMap, Map<String, ByteBuffer> map, ArrayList<Font> arrayList) {
        Object object;
        Object object2;
        int n;
        String string2 = family.getLanguages();
        int n2 = family.getVariant();
        ArrayList<FontConfig.Font> arrayList2 = new ArrayList<FontConfig.Font>();
        ArrayMap<String, ArrayList<FontConfig.Font>> arrayMap2 = new ArrayMap<String, ArrayList<FontConfig.Font>>();
        FontConfig.Font[] arrfont = family.getFonts();
        int n3 = arrfont.length;
        for (n = 0; n < n3; ++n) {
            FontConfig.Font font = arrfont[n];
            String string3 = font.getFallbackFor();
            if (string3 == null) {
                arrayList2.add(font);
                continue;
            }
            object2 = (ArrayList)arrayMap2.get(string3);
            object = object2;
            if (object2 == null) {
                object = new ArrayList<FontConfig.Font>();
                arrayMap2.put(string3, (ArrayList<FontConfig.Font>)object);
            }
            ((ArrayList)object).add(font);
        }
        object = arrayList2.isEmpty() ? null : SystemFonts.createFontFamily(family.getName(), arrayList2, string2, n2, map, arrayList);
        for (n = 0; n < arrayMap.size(); ++n) {
            object2 = (ArrayList)arrayMap2.get(arrayMap.keyAt(n));
            if (object2 == null) {
                if (object == null) continue;
                arrayMap.valueAt(n).add((FontFamily)object);
                continue;
            }
            object2 = SystemFonts.createFontFamily(family.getName(), (List<FontConfig.Font>)object2, string2, n2, map, arrayList);
            if (object2 != null) {
                arrayMap.valueAt(n).add((FontFamily)object2);
                continue;
            }
            if (object == null) continue;
            arrayMap.valueAt(n).add((FontFamily)object);
        }
    }

    private static FontCustomizationParser.Result readFontCustomization(String object, String string2) {
        FileInputStream fileInputStream = new FileInputStream((String)object);
        try {
            object = FontCustomizationParser.parse(fileInputStream, string2);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    SystemFonts.$closeResource(throwable, fileInputStream);
                    throw throwable2;
                }
                catch (XmlPullParserException xmlPullParserException) {
                    Log.e(TAG, "Failed to parse font customization XML", xmlPullParserException);
                    return new FontCustomizationParser.Result();
                }
                catch (IOException iOException) {
                    return new FontCustomizationParser.Result();
                }
            }
        }
        SystemFonts.$closeResource(null, fileInputStream);
        return object;
    }
}

