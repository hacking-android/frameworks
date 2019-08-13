/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.server;

import android.content.ComponentName;
import android.content.pm.FeatureInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemProperties;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SystemConfig {
    private static final int ALLOW_ALL = -1;
    private static final int ALLOW_APP_CONFIGS = 8;
    private static final int ALLOW_ASSOCIATIONS = 128;
    private static final int ALLOW_FEATURES = 1;
    private static final int ALLOW_HIDDENAPI_WHITELISTING = 64;
    private static final int ALLOW_LIBS = 2;
    private static final int ALLOW_OEM_PERMISSIONS = 32;
    private static final int ALLOW_PERMISSIONS = 4;
    private static final int ALLOW_PRIVAPP_PERMISSIONS = 16;
    private static final String SKU_PROPERTY = "ro.boot.product.hardware.sku";
    static final String TAG = "SystemConfig";
    static SystemConfig sInstance;
    final ArraySet<String> mAllowIgnoreLocationSettings = new ArraySet();
    final ArraySet<String> mAllowImplicitBroadcasts = new ArraySet();
    final ArraySet<String> mAllowInDataUsageSave = new ArraySet();
    final ArraySet<String> mAllowInPowerSave = new ArraySet();
    final ArraySet<String> mAllowInPowerSaveExceptIdle = new ArraySet();
    final ArraySet<String> mAllowUnthrottledLocation = new ArraySet();
    final ArrayMap<String, ArraySet<String>> mAllowedAssociations = new ArrayMap();
    final ArrayMap<String, FeatureInfo> mAvailableFeatures = new ArrayMap();
    final ArraySet<ComponentName> mBackupTransportWhitelist = new ArraySet();
    private final ArraySet<String> mBugreportWhitelistedPackages = new ArraySet();
    final ArraySet<ComponentName> mDefaultVrComponents = new ArraySet();
    final ArraySet<String> mDisabledUntilUsedPreinstalledCarrierApps = new ArraySet();
    final ArrayMap<String, List<String>> mDisabledUntilUsedPreinstalledCarrierAssociatedApps = new ArrayMap();
    int[] mGlobalGids;
    final ArraySet<String> mHiddenApiPackageWhitelist = new ArraySet();
    final ArraySet<String> mLinkedApps = new ArraySet();
    final ArrayMap<String, ArrayMap<String, Boolean>> mOemPermissions = new ArrayMap();
    final ArrayMap<String, PermissionEntry> mPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mPrivAppDenyPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mPrivAppPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppDenyPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mProductPrivAppPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppDenyPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mProductServicesPrivAppPermissions = new ArrayMap();
    final ArrayMap<String, SharedLibraryEntry> mSharedLibraries = new ArrayMap();
    final ArrayList<PermissionManager.SplitPermissionInfo> mSplitPermissions = new ArrayList();
    final SparseArray<ArraySet<String>> mSystemPermissions = new SparseArray();
    final ArraySet<String> mSystemUserBlacklistedApps = new ArraySet();
    final ArraySet<String> mSystemUserWhitelistedApps = new ArraySet();
    final ArraySet<String> mUnavailableFeatures = new ArraySet();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppDenyPermissions = new ArrayMap();
    final ArrayMap<String, ArraySet<String>> mVendorPrivAppPermissions = new ArrayMap();

    SystemConfig() {
        this.readPermissions(Environment.buildPath(Environment.getRootDirectory(), "etc", "sysconfig"), -1);
        this.readPermissions(Environment.buildPath(Environment.getRootDirectory(), "etc", "permissions"), -1);
        int n = 147;
        if (Build.VERSION.FIRST_SDK_INT <= 27) {
            n = 147 | 12;
        }
        this.readPermissions(Environment.buildPath(Environment.getVendorDirectory(), "etc", "sysconfig"), n);
        this.readPermissions(Environment.buildPath(Environment.getVendorDirectory(), "etc", "permissions"), n);
        this.readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "sysconfig"), n);
        this.readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "permissions"), n);
        String string2 = SystemProperties.get(SKU_PROPERTY, "");
        if (!string2.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sku_");
            stringBuilder.append(string2);
            string2 = stringBuilder.toString();
            this.readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "sysconfig", string2), n);
            this.readPermissions(Environment.buildPath(Environment.getOdmDirectory(), "etc", "permissions", string2), n);
        }
        this.readPermissions(Environment.buildPath(Environment.getOemDirectory(), "etc", "sysconfig"), 161);
        this.readPermissions(Environment.buildPath(Environment.getOemDirectory(), "etc", "permissions"), 161);
        this.readPermissions(Environment.buildPath(Environment.getProductDirectory(), "etc", "sysconfig"), -1);
        this.readPermissions(Environment.buildPath(Environment.getProductDirectory(), "etc", "permissions"), -1);
        this.readPermissions(Environment.buildPath(Environment.getProductServicesDirectory(), "etc", "sysconfig"), -1);
        this.readPermissions(Environment.buildPath(Environment.getProductServicesDirectory(), "etc", "permissions"), -1);
    }

    private void addFeature(String string2, int n) {
        FeatureInfo featureInfo = this.mAvailableFeatures.get(string2);
        if (featureInfo == null) {
            featureInfo = new FeatureInfo();
            featureInfo.name = string2;
            featureInfo.version = n;
            this.mAvailableFeatures.put(string2, featureInfo);
        } else {
            featureInfo.version = Math.max(featureInfo.version, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SystemConfig getInstance() {
        synchronized (SystemConfig.class) {
            SystemConfig systemConfig;
            if (sInstance != null) return sInstance;
            sInstance = systemConfig = new SystemConfig();
            return sInstance;
        }
    }

    private void logNotAllowedInPartition(String string2, File file, XmlPullParser xmlPullParser) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(string2);
        stringBuilder.append("> not allowed in partition of ");
        stringBuilder.append(file);
        stringBuilder.append(" at ");
        stringBuilder.append(xmlPullParser.getPositionDescription());
        Slog.w(TAG, stringBuilder.toString());
    }

    /*
     * Exception decompiling
     */
    private void readPermissionsFromXml(File var1_1, int var2_6) {
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

    private void readPrivAppPermissions(XmlPullParser xmlPullParser, ArrayMap<String, ArraySet<String>> object, ArrayMap<String, ArraySet<String>> arrayMap) throws IOException, XmlPullParserException {
        String string2 = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(string2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("package is required for <privapp-permissions> in ");
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        ArraySet<String> arraySet = ((ArrayMap)object).get(string2);
        ArraySet<String> arraySet2 = arraySet;
        if (arraySet == null) {
            arraySet2 = new ArraySet();
        }
        arraySet = arrayMap.get(string2);
        int n = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, n)) {
            Object object2 = xmlPullParser.getName();
            if ("permission".equals(object2)) {
                object2 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty((CharSequence)object2)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("name is required for <permission> in ");
                    ((StringBuilder)object2).append(xmlPullParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object2).toString());
                    continue;
                }
                arraySet2.add((String)object2);
                continue;
            }
            if (!"deny-permission".equals(object2)) continue;
            String string3 = xmlPullParser.getAttributeValue(null, "name");
            if (TextUtils.isEmpty(string3)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("name is required for <deny-permission> in ");
                ((StringBuilder)object2).append(xmlPullParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object2).toString());
                continue;
            }
            object2 = arraySet;
            if (arraySet == null) {
                object2 = new ArraySet<String>();
            }
            ((ArraySet)object2).add(string3);
            arraySet = object2;
        }
        ((ArrayMap)object).put(string2, arraySet2);
        if (arraySet != null) {
            arrayMap.put(string2, arraySet);
        }
    }

    private void readSplitPermission(XmlPullParser xmlPullParser, File serializable) throws IOException, XmlPullParserException {
        CharSequence charSequence = xmlPullParser.getAttributeValue(null, "name");
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("<split-permission> without name in ");
            ((StringBuilder)charSequence).append(serializable);
            ((StringBuilder)charSequence).append(" at ");
            ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)charSequence).toString());
            XmlUtils.skipCurrentTag(xmlPullParser);
            return;
        }
        CharSequence charSequence2 = xmlPullParser.getAttributeValue(null, "targetSdk");
        int n = 10001;
        if (!TextUtils.isEmpty(charSequence2)) {
            try {
                n = Integer.parseInt((String)charSequence2);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<split-permission> targetSdk not an integer in ");
                stringBuilder.append(serializable);
                stringBuilder.append(" at ");
                stringBuilder.append(xmlPullParser.getPositionDescription());
                Slog.w(TAG, stringBuilder.toString());
                XmlUtils.skipCurrentTag(xmlPullParser);
                return;
            }
        }
        int n2 = xmlPullParser.getDepth();
        serializable = new ArrayList();
        while (XmlUtils.nextElementWithin(xmlPullParser, n2)) {
            if ("new-permission".equals(xmlPullParser.getName())) {
                charSequence2 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(charSequence2)) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("name is required for <new-permission> in ");
                    ((StringBuilder)charSequence2).append(xmlPullParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)charSequence2).toString());
                    continue;
                }
                serializable.add(charSequence2);
                continue;
            }
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
        if (!serializable.isEmpty()) {
            this.mSplitPermissions.add(new PermissionManager.SplitPermissionInfo((String)charSequence, (List<String>)((Object)serializable), n));
        }
    }

    private void removeFeature(String string2) {
        if (this.mAvailableFeatures.remove(string2) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removed unavailable feature ");
            stringBuilder.append(string2);
            Slog.d(TAG, stringBuilder.toString());
        }
    }

    public ArraySet<String> getAllowIgnoreLocationSettings() {
        return this.mAllowIgnoreLocationSettings;
    }

    public ArraySet<String> getAllowImplicitBroadcasts() {
        return this.mAllowImplicitBroadcasts;
    }

    public ArraySet<String> getAllowInDataUsageSave() {
        return this.mAllowInDataUsageSave;
    }

    public ArraySet<String> getAllowInPowerSave() {
        return this.mAllowInPowerSave;
    }

    public ArraySet<String> getAllowInPowerSaveExceptIdle() {
        return this.mAllowInPowerSaveExceptIdle;
    }

    public ArraySet<String> getAllowUnthrottledLocation() {
        return this.mAllowUnthrottledLocation;
    }

    public ArrayMap<String, ArraySet<String>> getAllowedAssociations() {
        return this.mAllowedAssociations;
    }

    public ArrayMap<String, FeatureInfo> getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    public ArraySet<ComponentName> getBackupTransportWhitelist() {
        return this.mBackupTransportWhitelist;
    }

    public ArraySet<String> getBugreportWhitelistedPackages() {
        return this.mBugreportWhitelistedPackages;
    }

    public ArraySet<ComponentName> getDefaultVrComponents() {
        return this.mDefaultVrComponents;
    }

    public ArraySet<String> getDisabledUntilUsedPreinstalledCarrierApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierApps;
    }

    public ArrayMap<String, List<String>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps;
    }

    public int[] getGlobalGids() {
        return this.mGlobalGids;
    }

    public ArraySet<String> getHiddenApiWhitelistedApps() {
        return this.mHiddenApiPackageWhitelist;
    }

    public ArraySet<String> getLinkedApps() {
        return this.mLinkedApps;
    }

    public Map<String, Boolean> getOemPermissions(String object) {
        if ((object = (Map)this.mOemPermissions.get(object)) != null) {
            return object;
        }
        return Collections.emptyMap();
    }

    public ArrayMap<String, PermissionEntry> getPermissions() {
        return this.mPermissions;
    }

    public ArraySet<String> getPrivAppDenyPermissions(String string2) {
        return this.mPrivAppDenyPermissions.get(string2);
    }

    public ArraySet<String> getPrivAppPermissions(String string2) {
        return this.mPrivAppPermissions.get(string2);
    }

    public ArraySet<String> getProductPrivAppDenyPermissions(String string2) {
        return this.mProductPrivAppDenyPermissions.get(string2);
    }

    public ArraySet<String> getProductPrivAppPermissions(String string2) {
        return this.mProductPrivAppPermissions.get(string2);
    }

    public ArraySet<String> getProductServicesPrivAppDenyPermissions(String string2) {
        return this.mProductServicesPrivAppDenyPermissions.get(string2);
    }

    public ArraySet<String> getProductServicesPrivAppPermissions(String string2) {
        return this.mProductServicesPrivAppPermissions.get(string2);
    }

    public ArrayMap<String, SharedLibraryEntry> getSharedLibraries() {
        return this.mSharedLibraries;
    }

    public ArrayList<PermissionManager.SplitPermissionInfo> getSplitPermissions() {
        return this.mSplitPermissions;
    }

    public SparseArray<ArraySet<String>> getSystemPermissions() {
        return this.mSystemPermissions;
    }

    public ArraySet<String> getSystemUserBlacklistedApps() {
        return this.mSystemUserBlacklistedApps;
    }

    public ArraySet<String> getSystemUserWhitelistedApps() {
        return this.mSystemUserWhitelistedApps;
    }

    public ArraySet<String> getVendorPrivAppDenyPermissions(String string2) {
        return this.mVendorPrivAppDenyPermissions.get(string2);
    }

    public ArraySet<String> getVendorPrivAppPermissions(String string2) {
        return this.mVendorPrivAppPermissions.get(string2);
    }

    void readOemPermissions(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String string2 = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(string2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package is required for <oem-permissions> in ");
            stringBuilder.append(xmlPullParser.getPositionDescription());
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        Object object = this.mOemPermissions.get(string2);
        ArrayMap<String, Boolean> arrayMap = object;
        if (object == null) {
            arrayMap = new ArrayMap();
        }
        int n = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, n)) {
            object = xmlPullParser.getName();
            if ("permission".equals(object)) {
                object = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty((CharSequence)object)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("name is required for <permission> in ");
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object).toString());
                    continue;
                }
                arrayMap.put((String)object, Boolean.TRUE);
                continue;
            }
            if (!"deny-permission".equals(object)) continue;
            object = xmlPullParser.getAttributeValue(null, "name");
            if (TextUtils.isEmpty((CharSequence)object)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("name is required for <deny-permission> in ");
                ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object).toString());
                continue;
            }
            arrayMap.put((String)object, Boolean.FALSE);
        }
        this.mOemPermissions.put(string2, arrayMap);
    }

    void readPermission(XmlPullParser object, String charSequence) throws IOException, XmlPullParserException {
        if (!this.mPermissions.containsKey(charSequence)) {
            int n;
            PermissionEntry permissionEntry = new PermissionEntry((String)charSequence, XmlUtils.readBooleanAttribute((XmlPullParser)object, "perUser", false));
            this.mPermissions.put((String)charSequence, permissionEntry);
            int n2 = object.getDepth();
            while ((n = object.next()) != 1 && (n != 3 || object.getDepth() > n2)) {
                if (n == 3 || n == 4) continue;
                if ("group".equals(object.getName())) {
                    charSequence = object.getAttributeValue(null, "gid");
                    if (charSequence != null) {
                        n = Process.getGidForName((String)charSequence);
                        permissionEntry.gids = ArrayUtils.appendInt(permissionEntry.gids, n);
                    } else {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("<group> without gid at ");
                        ((StringBuilder)charSequence).append(object.getPositionDescription());
                        Slog.w(TAG, ((StringBuilder)charSequence).toString());
                    }
                }
                XmlUtils.skipCurrentTag((XmlPullParser)object);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Duplicate permission definition for ");
        ((StringBuilder)object).append((String)charSequence);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    void readPermissions(File file, int n) {
        if (file.exists() && file.isDirectory()) {
            if (!file.canRead()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Directory ");
                stringBuilder.append(file);
                stringBuilder.append(" cannot be read");
                Slog.w(TAG, stringBuilder.toString());
                return;
            }
            File file2 = null;
            for (File file3 : file.listFiles()) {
                StringBuilder stringBuilder;
                if (!file3.isFile()) continue;
                if (file3.getPath().endsWith("etc/permissions/platform.xml")) {
                    file2 = file3;
                    continue;
                }
                if (!file3.getPath().endsWith(".xml")) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Non-xml file ");
                    stringBuilder.append(file3);
                    stringBuilder.append(" in ");
                    stringBuilder.append(file);
                    stringBuilder.append(" directory, ignoring");
                    Slog.i(TAG, stringBuilder.toString());
                    continue;
                }
                if (!file3.canRead()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Permissions library file ");
                    stringBuilder.append(file3);
                    stringBuilder.append(" cannot be read");
                    Slog.w(TAG, stringBuilder.toString());
                    continue;
                }
                this.readPermissionsFromXml(file3, n);
            }
            if (file2 != null) {
                this.readPermissionsFromXml(file2, n);
            }
            return;
        }
        if (n == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No directory ");
            stringBuilder.append(file);
            stringBuilder.append(", skipping");
            Slog.w(TAG, stringBuilder.toString());
        }
    }

    public static final class PermissionEntry {
        public int[] gids;
        public final String name;
        public boolean perUser;

        PermissionEntry(String string2, boolean bl) {
            this.name = string2;
            this.perUser = bl;
        }
    }

    public static final class SharedLibraryEntry {
        public final String[] dependencies;
        public final String filename;
        public final String name;

        SharedLibraryEntry(String string2, String string3, String[] arrstring) {
            this.name = string2;
            this.filename = string3;
            this.dependencies = arrstring;
        }
    }

}

