/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

public class TtsEngines {
    private static final boolean DBG = false;
    private static final String LOCALE_DELIMITER_NEW = "_";
    private static final String LOCALE_DELIMITER_OLD = "-";
    private static final String TAG = "TtsEngines";
    private static final String XML_TAG_NAME = "tts-engine";
    private static final Map<String, String> sNormalizeCountry;
    private static final Map<String, String> sNormalizeLanguage;
    private final Context mContext;

    static {
        Object object;
        Object object2;
        int n;
        String[] arrstring = new HashMap();
        Object object3 = Locale.getISOLanguages();
        int n2 = ((String[])object3).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object2 = object3[n];
            try {
                object = new Locale((String)object2);
                arrstring.put(((Locale)object).getISO3Language(), object2);
                continue;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        sNormalizeLanguage = Collections.unmodifiableMap(arrstring);
        object3 = new HashMap();
        arrstring = Locale.getISOCountries();
        n2 = arrstring.length;
        for (n = n3; n < n2; ++n) {
            object = arrstring[n];
            try {
                object2 = new Locale("", (String)object);
                ((HashMap)object3).put(((Locale)object2).getISO3Country(), object);
                continue;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        sNormalizeCountry = Collections.unmodifiableMap(object3);
    }

    @UnsupportedAppUsage
    public TtsEngines(Context context) {
        this.mContext = context;
    }

    private TextToSpeech.EngineInfo getEngineInfo(ResolveInfo resolveInfo, PackageManager object) {
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        if (serviceInfo != null) {
            TextToSpeech.EngineInfo engineInfo = new TextToSpeech.EngineInfo();
            engineInfo.name = serviceInfo.packageName;
            object = TextUtils.isEmpty((CharSequence)(object = serviceInfo.loadLabel((PackageManager)object))) ? engineInfo.name : object.toString();
            engineInfo.label = object;
            engineInfo.icon = serviceInfo.getIconResource();
            engineInfo.priority = resolveInfo.priority;
            engineInfo.system = this.isSystemEngine(serviceInfo);
            return engineInfo;
        }
        return null;
    }

    private boolean isSystemEngine(ServiceInfo packageItemInfo) {
        packageItemInfo = packageItemInfo.applicationInfo;
        boolean bl = true;
        if (packageItemInfo == null || (((ApplicationInfo)packageItemInfo).flags & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public static Locale normalizeTTSLocale(Locale locale) {
        String string2;
        String string3;
        String string4 = string3 = locale.getLanguage();
        if (!TextUtils.isEmpty(string3)) {
            string2 = sNormalizeLanguage.get(string3);
            string4 = string3;
            if (string2 != null) {
                string4 = string2;
            }
        }
        string2 = string3 = locale.getCountry();
        if (!TextUtils.isEmpty(string3)) {
            String string5 = sNormalizeCountry.get(string3);
            string2 = string3;
            if (string5 != null) {
                string2 = string5;
            }
        }
        return new Locale(string4, string2, locale.getVariant());
    }

    private static String parseEnginePrefFromList(String string22, String string3) {
        if (TextUtils.isEmpty(string22)) {
            return null;
        }
        for (String string22 : string22.split(",")) {
            int n = string22.indexOf(58);
            if (n <= 0 || !string3.equals(string22.substring(0, n))) continue;
            return string22.substring(n + 1);
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    private String settingsActivityFromServiceInfo(ServiceInfo var1_1, PackageManager var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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

    public static String[] toOldLocaleStringFormat(Locale locale) {
        String[] arrstring = new String[]{"", "", ""};
        try {
            arrstring[0] = locale.getISO3Language();
            arrstring[1] = locale.getISO3Country();
            arrstring[2] = locale.getVariant();
            return arrstring;
        }
        catch (MissingResourceException missingResourceException) {
            return new String[]{"eng", "USA", ""};
        }
    }

    private String updateValueInCommaSeparatedList(String arrstring, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TextUtils.isEmpty((CharSequence)arrstring)) {
            stringBuilder.append(string2);
            stringBuilder.append(':');
            stringBuilder.append(string3);
        } else {
            arrstring = arrstring.split(",");
            boolean bl = false;
            int n = arrstring.length;
            boolean bl2 = true;
            for (int i = 0; i < n; ++i) {
                String string4 = arrstring[i];
                int n2 = string4.indexOf(58);
                boolean bl3 = bl;
                boolean bl4 = bl2;
                if (n2 > 0) {
                    if (string2.equals(string4.substring(0, n2))) {
                        if (bl2) {
                            bl2 = false;
                        } else {
                            stringBuilder.append(',');
                        }
                        bl3 = true;
                        stringBuilder.append(string2);
                        stringBuilder.append(':');
                        stringBuilder.append(string3);
                        bl4 = bl2;
                    } else {
                        if (bl2) {
                            bl2 = false;
                        } else {
                            stringBuilder.append(',');
                        }
                        stringBuilder.append(string4);
                        bl4 = bl2;
                        bl3 = bl;
                    }
                }
                bl = bl3;
                bl2 = bl4;
            }
            if (!bl) {
                stringBuilder.append(',');
                stringBuilder.append(string2);
                stringBuilder.append(':');
                stringBuilder.append(string3);
            }
        }
        return stringBuilder.toString();
    }

    public String getDefaultEngine() {
        String string2 = Settings.Secure.getString(this.mContext.getContentResolver(), "tts_default_synth");
        if (!this.isEngineInstalled(string2)) {
            string2 = this.getHighestRankedEngineName();
        }
        return string2;
    }

    public TextToSpeech.EngineInfo getEngineInfo(String object) {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.TTS_SERVICE");
        intent.setPackage((String)object);
        object = packageManager.queryIntentServices(intent, 65536);
        if (object != null && object.size() == 1) {
            return this.getEngineInfo((ResolveInfo)object.get(0), packageManager);
        }
        return null;
    }

    @UnsupportedAppUsage
    public List<TextToSpeech.EngineInfo> getEngines() {
        Cloneable cloneable;
        PackageManager packageManager = this.mContext.getPackageManager();
        Object object = packageManager.queryIntentServices((Intent)(cloneable = new Intent("android.intent.action.TTS_SERVICE")), 65536);
        if (object == null) {
            return Collections.emptyList();
        }
        cloneable = new ArrayList(object.size());
        Iterator<ResolveInfo> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = this.getEngineInfo(iterator.next(), packageManager);
            if (object == null) continue;
            cloneable.add(object);
        }
        Collections.sort(cloneable, EngineInfoComparator.INSTANCE);
        return cloneable;
    }

    public String getHighestRankedEngineName() {
        List<TextToSpeech.EngineInfo> list = this.getEngines();
        if (list.size() > 0 && list.get((int)0).system) {
            return list.get((int)0).name;
        }
        return null;
    }

    @UnsupportedAppUsage
    public Locale getLocalePrefForEngine(String string2) {
        return this.getLocalePrefForEngine(string2, Settings.Secure.getString(this.mContext.getContentResolver(), "tts_default_locale"));
    }

    public Locale getLocalePrefForEngine(String object, String object2) {
        String string2 = TtsEngines.parseEnginePrefFromList((String)object2, (String)object);
        if (TextUtils.isEmpty(string2)) {
            return Locale.getDefault();
        }
        object = object2 = this.parseLocaleString(string2);
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to parse locale ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(", returning en_US instead");
            Log.w(TAG, ((StringBuilder)object).toString());
            object = Locale.US;
        }
        return object;
    }

    @UnsupportedAppUsage
    public Intent getSettingsIntent(String string2) {
        Object object = this.mContext.getPackageManager();
        Object object2 = new Intent("android.intent.action.TTS_SERVICE");
        ((Intent)object2).setPackage(string2);
        object2 = ((PackageManager)object).queryIntentServices((Intent)object2, 65664);
        if (object2 != null && object2.size() == 1 && (object2 = ((ResolveInfo)object2.get((int)0)).serviceInfo) != null && (object = this.settingsActivityFromServiceInfo((ServiceInfo)object2, (PackageManager)object)) != null) {
            object2 = new Intent();
            ((Intent)object2).setClassName(string2, (String)object);
            return object2;
        }
        return null;
    }

    public boolean isEngineInstalled(String string2) {
        boolean bl = false;
        if (string2 == null) {
            return false;
        }
        if (this.getEngineInfo(string2) != null) {
            bl = true;
        }
        return bl;
    }

    public boolean isLocaleSetToDefaultForEngine(String string2) {
        return TextUtils.isEmpty(TtsEngines.parseEnginePrefFromList(Settings.Secure.getString(this.mContext.getContentResolver(), "tts_default_locale"), string2));
    }

    @UnsupportedAppUsage
    public Locale parseLocaleString(String string2) {
        String string3 = "";
        String string4 = "";
        String string5 = "";
        Object object = string4;
        String string6 = string5;
        if (!TextUtils.isEmpty(string2)) {
            String[] arrstring = string2.split("[-_]");
            String string7 = arrstring[0].toLowerCase();
            if (arrstring.length == 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to convert ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" to a valid Locale object. Only separators");
                Log.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
            if (arrstring.length > 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to convert ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" to a valid Locale object. Too many separators");
                Log.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
            if (arrstring.length >= 2) {
                string4 = arrstring[1].toUpperCase();
            }
            string3 = string7;
            object = string4;
            string6 = string5;
            if (arrstring.length >= 3) {
                string6 = arrstring[2];
                object = string4;
                string3 = string7;
            }
        }
        if ((string4 = sNormalizeLanguage.get(string3)) != null) {
            string3 = string4;
        }
        if ((string4 = sNormalizeCountry.get(object)) != null) {
            object = string4;
        }
        object = new Locale(string3, (String)object, string6);
        try {
            ((Locale)object).getISO3Language();
            ((Locale)object).getISO3Country();
            return object;
        }
        catch (MissingResourceException missingResourceException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to convert ");
            stringBuilder.append(string2);
            stringBuilder.append(" to a valid Locale object.");
            Log.w(TAG, stringBuilder.toString());
            return null;
        }
    }

    @UnsupportedAppUsage
    public void updateLocalePrefForEngine(String string2, Locale object) {
        synchronized (this) {
            String string3;
            block6 : {
                block5 : {
                    string3 = Settings.Secure.getString(this.mContext.getContentResolver(), "tts_default_locale");
                    if (object == null) break block5;
                    object = ((Locale)object).toString();
                    break block6;
                }
                object = "";
            }
            string2 = this.updateValueInCommaSeparatedList(string3, string2, (String)object);
            Settings.Secure.putString(this.mContext.getContentResolver(), "tts_default_locale", string2.toString());
            return;
        }
    }

    private static class EngineInfoComparator
    implements Comparator<TextToSpeech.EngineInfo> {
        static EngineInfoComparator INSTANCE = new EngineInfoComparator();

        private EngineInfoComparator() {
        }

        @Override
        public int compare(TextToSpeech.EngineInfo engineInfo, TextToSpeech.EngineInfo engineInfo2) {
            if (engineInfo.system && !engineInfo2.system) {
                return -1;
            }
            if (engineInfo2.system && !engineInfo.system) {
                return 1;
            }
            return engineInfo2.priority - engineInfo.priority;
        }
    }

}

