/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.content.pm.PackageParser;
import android.util.ArrayMap;
import android.util.jar.StrictJarFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DexMetadataHelper {
    private static final String DEX_METADATA_FILE_EXTENSION = ".dm";

    private DexMetadataHelper() {
    }

    public static String buildDexMetadataPathForApk(String string2) {
        if (PackageParser.isApkPath(string2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2.substring(0, string2.length() - ".apk".length()));
            stringBuilder.append(DEX_METADATA_FILE_EXTENSION);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Corrupted package. Code path is not an apk ");
        stringBuilder.append(string2);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private static String buildDexMetadataPathForFile(File object) {
        if (PackageParser.isApkFile((File)object)) {
            object = DexMetadataHelper.buildDexMetadataPathForApk(((File)object).getPath());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((File)object).getPath());
            stringBuilder.append(DEX_METADATA_FILE_EXTENSION);
            object = stringBuilder.toString();
        }
        return object;
    }

    private static Map<String, String> buildPackageApkToDexMetadataMap(List<String> list) {
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
        for (int i = list.size() - 1; i >= 0; --i) {
            String string2 = list.get(i);
            String string3 = DexMetadataHelper.buildDexMetadataPathForFile(new File(string2));
            if (!Files.exists(Paths.get(string3, new String[0]), new LinkOption[0])) continue;
            arrayMap.put(string2, string3);
        }
        return arrayMap;
    }

    public static File findDexMetadataForFile(File file) {
        if (!(file = new File(DexMetadataHelper.buildDexMetadataPathForFile(file))).exists()) {
            file = null;
        }
        return file;
    }

    public static Map<String, String> getPackageDexMetadata(PackageParser.Package package_) {
        return DexMetadataHelper.buildPackageApkToDexMetadataMap(package_.getAllCodePaths());
    }

    private static Map<String, String> getPackageDexMetadata(PackageParser.PackageLite packageLite) {
        return DexMetadataHelper.buildPackageApkToDexMetadataMap(packageLite.getAllCodePaths());
    }

    public static long getPackageDexMetadataSize(PackageParser.PackageLite object) {
        long l = 0L;
        object = DexMetadataHelper.getPackageDexMetadata((PackageParser.PackageLite)object).values().iterator();
        while (object.hasNext()) {
            l += new File((String)object.next()).length();
        }
        return l;
    }

    public static boolean isDexMetadataFile(File file) {
        return DexMetadataHelper.isDexMetadataPath(file.getName());
    }

    private static boolean isDexMetadataPath(String string2) {
        return string2.endsWith(DEX_METADATA_FILE_EXTENSION);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void validateDexMetadataFile(String string2) throws PackageParser.PackageParserException {
        Throwable throwable2222;
        StrictJarFile strictJarFile = new StrictJarFile(string2, false, false);
        try {
            strictJarFile.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error opening ");
                stringBuilder.append(string2);
                PackageParser.PackageParserException packageParserException = new PackageParser.PackageParserException(-117, stringBuilder.toString(), iOException);
                throw packageParserException;
            }
        }
        if (!false) throw throwable2222;
        try {
            throw new NullPointerException();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    public static void validateDexPaths(String[] object) {
        int n;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n = 0; n < ((String[])object).length; ++n) {
            if (!PackageParser.isApkPath(object[n])) continue;
            arrayList.add(object[n]);
        }
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (n = 0; n < ((String[])object).length; ++n) {
            boolean bl;
            String string2 = object[n];
            if (!DexMetadataHelper.isDexMetadataPath(string2)) continue;
            boolean bl2 = false;
            int n2 = arrayList.size() - 1;
            do {
                bl = bl2;
                if (n2 < 0) break;
                if (string2.equals(DexMetadataHelper.buildDexMetadataPathForFile(new File((String)arrayList.get(n2))))) {
                    bl = true;
                    break;
                }
                --n2;
            } while (true);
            if (bl) continue;
            arrayList2.add(string2);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unmatched .dm files: ");
        ((StringBuilder)object).append(arrayList2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static void validatePackageDexMetadata(PackageParser.Package object) throws PackageParser.PackageParserException {
        object = DexMetadataHelper.getPackageDexMetadata((PackageParser.Package)object).values().iterator();
        while (object.hasNext()) {
            DexMetadataHelper.validateDexMetadataFile((String)object.next());
        }
    }
}

