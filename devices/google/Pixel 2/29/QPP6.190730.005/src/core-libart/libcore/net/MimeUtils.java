/*
 * Decompiled with CFR 0.145.
 */
package libcore.net;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class MimeUtils {
    private static final Map<String, String> extensionToMimeTypeMap;
    private static final Map<String, String> mimeTypeToExtensionMap;
    private static final Pattern splitPattern;

    static {
        splitPattern = Pattern.compile("\\s+");
        mimeTypeToExtensionMap = new HashMap<String, String>();
        extensionToMimeTypeMap = new HashMap<String, String>();
        MimeUtils.parseTypes("mime.types");
        MimeUtils.parseTypes("android.mime.types");
    }

    private MimeUtils() {
    }

    private static boolean allowedInMap(String string) {
        boolean bl = string != null && !string.isEmpty();
        return bl;
    }

    private static String canonicalize(String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    @UnsupportedAppUsage
    public static String guessExtensionFromMimeType(String string) {
        if (!MimeUtils.allowedInMap(string)) {
            return null;
        }
        string = MimeUtils.canonicalize(string);
        return mimeTypeToExtensionMap.get(string);
    }

    @UnsupportedAppUsage
    public static String guessMimeTypeFromExtension(String string) {
        if (!MimeUtils.allowedInMap(string)) {
            return null;
        }
        string = MimeUtils.canonicalize(string);
        return extensionToMimeTypeMap.get(string);
    }

    public static boolean hasExtension(String string) {
        boolean bl = MimeUtils.guessMimeTypeFromExtension(string) != null;
        return bl;
    }

    public static boolean hasMimeType(String string) {
        boolean bl = MimeUtils.guessExtensionFromMimeType(string) != null;
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void parseTypes(String var0) {
        var2_3 = new InputStreamReader(MimeUtils.class.getResourceAsStream(var0));
        var1_1 = new BufferedReader((Reader)var2_3);
        try {
            block11 : do lbl-1000: // 3 sources:
            {
                block19 : {
                    block18 : {
                        var2_3 = var3_5 = var1_1.readLine();
                        if (var3_5 == null) break block18;
                        var4_8 = var2_3.indexOf(35);
                        var3_5 = var2_3;
                        if (var4_8 >= 0) {
                            var3_5 = var2_3.substring(0, var4_8);
                        }
                        if ((var5_9 = var3_5.trim()).equals("")) ** GOTO lbl-1000
                        var6_10 = MimeUtils.splitPattern.split(var5_9);
                        var7_11 = MimeUtils.canonicalize(var6_10[0]);
                        var8_12 = MimeUtils.allowedInMap((String)var7_11);
                        if (!var8_12) {
                            var3_5 = new StringBuilder();
                            var3_5.append("Invalid mimeType ");
                            var3_5.append((String)var7_11);
                            var3_5.append(" in: ");
                            var3_5.append(var5_9);
                            var2_3 = new IllegalArgumentException(var3_5.toString());
                            throw var2_3;
                        }
                        break block19;
                    }
                    var1_1.close();
                    return;
                }
                var4_8 = 1;
                do {
                    if (var4_8 >= var6_10.length) continue block11;
                    var3_5 = MimeUtils.canonicalize(var6_10[var4_8]);
                    if (!MimeUtils.allowedInMap((String)var3_5)) break block11;
                    if (var3_5.endsWith("!")) {
                        var2_3 = var3_5.substring(0, var3_5.length() - 1);
                        MimeUtils.mimeTypeToExtensionMap.put((String)var7_11, (String)var2_3);
                    } else {
                        var2_3 = var3_5;
                        if (!MimeUtils.mimeTypeToExtensionMap.containsKey(var7_11)) {
                            MimeUtils.mimeTypeToExtensionMap.put((String)var7_11, (String)var3_5);
                            var2_3 = var3_5;
                        }
                    }
                    MimeUtils.extensionToMimeTypeMap.put((String)var2_3, (String)var7_11);
                    ++var4_8;
                } while (true);
                break;
            } while (true);
            var7_11 = new StringBuilder();
            var7_11.append("Invalid extension ");
            var7_11.append((String)var3_5);
            var7_11.append(" in: ");
            var7_11.append(var5_9);
            var2_3 = new IllegalArgumentException(var7_11.toString());
            throw var2_3;
        }
        catch (Throwable var2_4) {
            try {
                throw var2_4;
            }
            catch (Throwable var3_6) {
                try {
                    var1_1.close();
                    throw var3_6;
                }
                catch (Throwable var1_2) {
                    try {
                        var2_4.addSuppressed(var1_2);
                        throw var3_6;
                    }
                    catch (IOException var3_7) {
                        var2_3 = new StringBuilder();
                        var2_3.append("Failed to parse ");
                        var2_3.append(var0);
                        throw new RuntimeException(var2_3.toString(), var3_7);
                    }
                }
            }
        }
    }
}

