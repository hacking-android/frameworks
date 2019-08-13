/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import android.icu.util.TimeZone;
import java.io.File;
import java.io.IOException;
import libcore.icu.ICU;
import libcore.timezone.TimeZoneDataFiles;
import libcore.timezone.TzDataSetVersion;
import libcore.timezone.ZoneInfoDB;
import libcore.util.DebugInfo;

public class CoreLibraryDebug {
    private static final String CORE_LIBRARY_TIMEZONE_DEBUG_PREFIX = "core_library.timezone.";

    private CoreLibraryDebug() {
    }

    private static void addTzDataSetVersionDebugInfo(String object, String charSequence, DebugInfo debugInfo) {
        object = new File((String)object);
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("status");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        if (((File)object).exists()) {
            try {
                TzDataSetVersion tzDataSetVersion = TzDataSetVersion.readFromFile((File)object);
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append(tzDataSetVersion.formatMajorVersion);
                ((StringBuilder)object2).append(".");
                ((StringBuilder)object2).append(tzDataSetVersion.formatMinorVersion);
                object2 = ((StringBuilder)object2).toString();
                Object object3 = debugInfo.addStringEntry((String)charSequence2, "OK");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append("formatVersion");
                object2 = ((DebugInfo)object3).addStringEntry(stringBuilder.toString(), (String)object2);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)charSequence);
                ((StringBuilder)object3).append("rulesVersion");
                object2 = ((DebugInfo)object2).addStringEntry(((StringBuilder)object3).toString(), tzDataSetVersion.rulesVersion);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)charSequence);
                ((StringBuilder)object3).append("revision");
                ((DebugInfo)object2).addStringEntry(((StringBuilder)object3).toString(), tzDataSetVersion.revision);
            }
            catch (IOException | TzDataSetVersion.TzDataSetException exception) {
                debugInfo.addStringEntry((String)charSequence2, "ERROR");
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("exception_class");
                debugInfo.addStringEntry(((StringBuilder)charSequence2).toString(), exception.getClass().getName());
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("exception_msg");
                debugInfo.addStringEntry(((StringBuilder)charSequence2).toString(), exception.getMessage());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Error reading ");
                ((StringBuilder)charSequence).append(object);
                System.logE((String)((StringBuilder)charSequence).toString(), (Throwable)exception);
            }
        } else {
            debugInfo.addStringEntry((String)charSequence2, "NOT_FOUND");
        }
    }

    public static DebugInfo getDebugInfo() {
        DebugInfo debugInfo = new DebugInfo();
        CoreLibraryDebug.populateTimeZoneFilesInfo(debugInfo);
        CoreLibraryDebug.populateTimeZoneLibraryReportedVersion(debugInfo);
        return debugInfo;
    }

    private static void populateTimeZoneFilesInfo(DebugInfo debugInfo) {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("core_library.timezone.source.");
        ((StringBuilder)charSequence).append("tzdata_module_");
        charSequence = ((StringBuilder)charSequence).toString();
        CoreLibraryDebug.addTzDataSetVersionDebugInfo(TimeZoneDataFiles.getTimeZoneModuleFile("tz/tz_version"), (String)charSequence, debugInfo);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("core_library.timezone.source.");
        ((StringBuilder)charSequence).append("runtime_module_");
        charSequence = ((StringBuilder)charSequence).toString();
        CoreLibraryDebug.addTzDataSetVersionDebugInfo(TimeZoneDataFiles.getRuntimeModuleFile("tz/tz_version"), (String)charSequence, debugInfo);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("core_library.timezone.source.");
        ((StringBuilder)charSequence).append("system_");
        charSequence = ((StringBuilder)charSequence).toString();
        CoreLibraryDebug.addTzDataSetVersionDebugInfo(TimeZoneDataFiles.getSystemTimeZoneFile("tz_version"), (String)charSequence, debugInfo);
    }

    private static void populateTimeZoneLibraryReportedVersion(DebugInfo debugInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("core_library.timezone.lib.");
        stringBuilder.append("icu4j.tzdb_version");
        debugInfo.addStringEntry(stringBuilder.toString(), TimeZone.getTZDataVersion());
        stringBuilder = new StringBuilder();
        stringBuilder.append("core_library.timezone.lib.");
        stringBuilder.append("libcore.tzdb_version");
        debugInfo.addStringEntry(stringBuilder.toString(), ZoneInfoDB.getInstance().getVersion());
        stringBuilder = new StringBuilder();
        stringBuilder.append("core_library.timezone.lib.");
        stringBuilder.append("icu4c.tzdb_version");
        debugInfo.addStringEntry(stringBuilder.toString(), ICU.getTZDataVersion());
    }
}

