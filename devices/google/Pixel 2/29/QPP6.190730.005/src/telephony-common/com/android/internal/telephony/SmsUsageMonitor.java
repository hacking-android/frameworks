/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AppGlobals
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.content.res.XmlResourceParser
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.util.AtomicFile
 *  com.android.internal.util.FastXmlSerializer
 *  com.android.internal.util.XmlUtils
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.util.AtomicFile;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SmsUsageMonitor {
    private static final String ATTR_COUNTRY = "country";
    private static final String ATTR_FREE = "free";
    private static final String ATTR_PACKAGE_NAME = "name";
    private static final String ATTR_PACKAGE_SMS_POLICY = "sms-policy";
    private static final String ATTR_PATTERN = "pattern";
    private static final String ATTR_PREMIUM = "premium";
    private static final String ATTR_STANDARD = "standard";
    private static final boolean DBG = false;
    private static final int DEFAULT_SMS_CHECK_PERIOD = 60000;
    private static final int DEFAULT_SMS_MAX_COUNT = 30;
    public static final int PREMIUM_SMS_PERMISSION_ALWAYS_ALLOW = 3;
    public static final int PREMIUM_SMS_PERMISSION_ASK_USER = 1;
    public static final int PREMIUM_SMS_PERMISSION_NEVER_ALLOW = 2;
    public static final int PREMIUM_SMS_PERMISSION_UNKNOWN = 0;
    private static final String SHORT_CODE_PATH = "/data/misc/sms/codes";
    private static final String SMS_POLICY_FILE_DIRECTORY = "/data/misc/sms";
    private static final String SMS_POLICY_FILE_NAME = "premium_sms_policy.xml";
    private static final String TAG = "SmsUsageMonitor";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_SHORTCODE = "shortcode";
    private static final String TAG_SHORTCODES = "shortcodes";
    private static final String TAG_SMS_POLICY_BODY = "premium-sms-policy";
    private static final boolean VDBG = false;
    private final AtomicBoolean mCheckEnabled = new AtomicBoolean(true);
    private final int mCheckPeriod;
    private final Context mContext;
    private String mCurrentCountry;
    private ShortCodePatternMatcher mCurrentPatternMatcher;
    private final int mMaxAllowed;
    private final File mPatternFile = new File("/data/misc/sms/codes");
    private long mPatternFileLastModified = 0L;
    private AtomicFile mPolicyFile;
    private final HashMap<String, Integer> mPremiumSmsPolicy = new HashMap();
    private final SettingsObserverHandler mSettingsObserverHandler;
    private final HashMap<String, ArrayList<Long>> mSmsStamp = new HashMap();

    @UnsupportedAppUsage
    public SmsUsageMonitor(Context context) {
        this.mContext = context;
        context = context.getContentResolver();
        this.mMaxAllowed = Settings.Global.getInt((ContentResolver)context, (String)"sms_outgoing_check_max_count", (int)30);
        this.mCheckPeriod = Settings.Global.getInt((ContentResolver)context, (String)"sms_outgoing_check_interval_ms", (int)60000);
        this.mSettingsObserverHandler = new SettingsObserverHandler(this.mContext, this.mCheckEnabled);
        this.loadPremiumSmsPolicyDb();
    }

    private static void checkCallerIsSystemOrPhoneApp() {
        int n = Binder.getCallingUid();
        int n2 = UserHandle.getAppId((int)n);
        if (n2 != 1000 && n2 != 1001 && n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Disallowed call for uid ");
            stringBuilder.append(n);
            throw new SecurityException(stringBuilder.toString());
        }
    }

    private static void checkCallerIsSystemOrPhoneOrSameApp(String string) {
        int n = Binder.getCallingUid();
        int n2 = UserHandle.getAppId((int)n);
        if (n2 != 1000 && n2 != 1001 && n != 0) {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(string, 0, UserHandle.getCallingUserId());
                if (UserHandle.isSameApp((int)applicationInfo.uid, (int)n)) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Calling uid ");
                stringBuilder.append(n);
                stringBuilder.append(" gave package");
                stringBuilder.append(string);
                stringBuilder.append(" which is owned by uid ");
                stringBuilder.append(applicationInfo.uid);
                SecurityException securityException = new SecurityException(stringBuilder.toString());
                throw securityException;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown package ");
                stringBuilder.append(string);
                stringBuilder.append("\n");
                stringBuilder.append((Object)remoteException);
                throw new SecurityException(stringBuilder.toString());
            }
        }
    }

    /*
     * Exception decompiling
     */
    private ShortCodePatternMatcher getPatternMatcherFromFile(String var1_1) {
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

    private ShortCodePatternMatcher getPatternMatcherFromResource(String object) {
        block4 : {
            XmlResourceParser xmlResourceParser;
            XmlResourceParser xmlResourceParser2 = null;
            try {
                xmlResourceParser2 = xmlResourceParser = this.mContext.getResources().getXml(18284564);
            }
            catch (Throwable throwable) {
                if (xmlResourceParser2 != null) {
                    xmlResourceParser2.close();
                }
                throw throwable;
            }
            object = this.getPatternMatcherFromXmlParser((XmlPullParser)xmlResourceParser, (String)object);
            if (xmlResourceParser == null) break block4;
            xmlResourceParser.close();
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ShortCodePatternMatcher getPatternMatcherFromXmlParser(XmlPullParser xmlPullParser, String string) {
        try {
            XmlUtils.beginDocument((XmlPullParser)xmlPullParser, (String)TAG_SHORTCODES);
            do {
                XmlUtils.nextElement((XmlPullParser)xmlPullParser);
                String string2 = xmlPullParser.getName();
                if (string2 == null) {
                    Rlog.e((String)TAG, (String)"Parsing pattern data found null");
                    return null;
                }
                if (string2.equals(TAG_SHORTCODE)) {
                    if (!string.equals(xmlPullParser.getAttributeValue(null, ATTR_COUNTRY))) continue;
                    return new ShortCodePatternMatcher(xmlPullParser.getAttributeValue(null, ATTR_PATTERN), xmlPullParser.getAttributeValue(null, ATTR_PREMIUM), xmlPullParser.getAttributeValue(null, ATTR_FREE), xmlPullParser.getAttributeValue(null, ATTR_STANDARD));
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error: skipping unknown XML tag ");
                stringBuilder.append(string2);
                Rlog.e((String)TAG, (String)stringBuilder.toString());
            } while (true);
        }
        catch (IOException iOException) {
            Rlog.e((String)TAG, (String)"I/O exception reading short code patterns", (Throwable)iOException);
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            Rlog.e((String)TAG, (String)"XML parser exception reading short code patterns", (Throwable)xmlPullParserException);
        }
        return null;
    }

    private boolean isUnderLimit(ArrayList<Long> arrayList, int n) {
        Long l = System.currentTimeMillis();
        long l2 = l;
        long l3 = this.mCheckPeriod;
        while (!arrayList.isEmpty() && arrayList.get(0) < l2 - l3) {
            arrayList.remove(0);
        }
        if (arrayList.size() + n <= this.mMaxAllowed) {
            for (int i = 0; i < n; ++i) {
                arrayList.add(l);
            }
            return true;
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    private void loadPremiumSmsPolicyDb() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 25[UNCONDITIONALDOLOOP]
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

    private static void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    public static int mergeShortCodeCategories(int n, int n2) {
        if (n > n2) {
            return n;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeExpiredTimestamps() {
        long l = System.currentTimeMillis();
        long l2 = this.mCheckPeriod;
        HashMap<String, ArrayList<Long>> hashMap = this.mSmsStamp;
        synchronized (hashMap) {
            Iterator<Map.Entry<String, ArrayList<Long>>> iterator = this.mSmsStamp.entrySet().iterator();
            while (iterator.hasNext()) {
                ArrayList<Long> arrayList = iterator.next().getValue();
                if (!arrayList.isEmpty() && arrayList.get(arrayList.size() - 1) >= l - l2) continue;
                iterator.remove();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writePremiumSmsPolicyDb() {
        HashMap<String, Integer> hashMap = this.mPremiumSmsPolicy;
        synchronized (hashMap) {
            FileOutputStream fileOutputStream = null;
            try {
                block6 : {
                    try {
                        FileOutputStream fileOutputStream2;
                        fileOutputStream = fileOutputStream2 = this.mPolicyFile.startWrite();
                        fileOutputStream = fileOutputStream2;
                        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                        fileOutputStream = fileOutputStream2;
                        fastXmlSerializer.setOutput((OutputStream)fileOutputStream2, StandardCharsets.UTF_8.name());
                        fileOutputStream = fileOutputStream2;
                        fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
                        fileOutputStream = fileOutputStream2;
                        fastXmlSerializer.startTag(null, TAG_SMS_POLICY_BODY);
                        fileOutputStream = fileOutputStream2;
                        Iterator<Map.Entry<String, Integer>> iterator = this.mPremiumSmsPolicy.entrySet().iterator();
                        do {
                            fileOutputStream = fileOutputStream2;
                            if (!iterator.hasNext()) break;
                            fileOutputStream = fileOutputStream2;
                            Map.Entry<String, Integer> entry = iterator.next();
                            fileOutputStream = fileOutputStream2;
                            fastXmlSerializer.startTag(null, TAG_PACKAGE);
                            fileOutputStream = fileOutputStream2;
                            fastXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, entry.getKey());
                            fileOutputStream = fileOutputStream2;
                            fastXmlSerializer.attribute(null, ATTR_PACKAGE_SMS_POLICY, entry.getValue().toString());
                            fileOutputStream = fileOutputStream2;
                            fastXmlSerializer.endTag(null, TAG_PACKAGE);
                        } while (true);
                        fileOutputStream = fileOutputStream2;
                        fastXmlSerializer.endTag(null, TAG_SMS_POLICY_BODY);
                        fileOutputStream = fileOutputStream2;
                        fastXmlSerializer.endDocument();
                        fileOutputStream = fileOutputStream2;
                        this.mPolicyFile.finishWrite(fileOutputStream2);
                    }
                    catch (IOException iOException) {
                        Rlog.e((String)TAG, (String)"Unable to write premium SMS policy database", (Throwable)iOException);
                        if (fileOutputStream == null) break block6;
                        this.mPolicyFile.failWrite(fileOutputStream);
                    }
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean check(String string, int n) {
        HashMap<String, ArrayList<Long>> hashMap = this.mSmsStamp;
        synchronized (hashMap) {
            ArrayList<Long> arrayList;
            this.removeExpiredTimestamps();
            ArrayList<Long> arrayList2 = arrayList = this.mSmsStamp.get(string);
            if (arrayList != null) return this.isUnderLimit(arrayList2, n);
            arrayList2 = new ArrayList<Long>();
            this.mSmsStamp.put(string, arrayList2);
            return this.isUnderLimit(arrayList2, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int checkDestination(String string, String string2) {
        SettingsObserverHandler settingsObserverHandler = this.mSettingsObserverHandler;
        synchronized (settingsObserverHandler) {
            if (PhoneNumberUtils.isEmergencyNumber((String)string, (String)string2)) {
                return 0;
            }
            if (!this.mCheckEnabled.get()) {
                return 0;
            }
            if (!(string2 == null || this.mCurrentCountry != null && string2.equals(this.mCurrentCountry) && this.mPatternFile.lastModified() == this.mPatternFileLastModified)) {
                this.mCurrentPatternMatcher = this.mPatternFile.exists() ? this.getPatternMatcherFromFile(string2) : this.getPatternMatcherFromResource(string2);
                this.mCurrentCountry = string2;
            }
            if (this.mCurrentPatternMatcher != null) {
                return this.mCurrentPatternMatcher.getNumberCategory(string);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No patterns for \"");
            stringBuilder.append(string2);
            stringBuilder.append("\": using generic short code rule");
            Rlog.e((String)TAG, (String)stringBuilder.toString());
            if (string.length() > 5) return 0;
            return 3;
        }
    }

    void dispose() {
        this.mSmsStamp.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getPremiumSmsPermission(String object) {
        SmsUsageMonitor.checkCallerIsSystemOrPhoneOrSameApp((String)object);
        HashMap<String, Integer> hashMap = this.mPremiumSmsPolicy;
        synchronized (hashMap) {
            object = this.mPremiumSmsPolicy.get(object);
            if (object != null) return (Integer)object;
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPremiumSmsPermission(String charSequence, int n) {
        SmsUsageMonitor.checkCallerIsSystemOrPhoneApp();
        if (n >= 1 && n <= 3) {
            HashMap<String, Integer> hashMap = this.mPremiumSmsPolicy;
            synchronized (hashMap) {
                this.mPremiumSmsPolicy.put((String)charSequence, n);
            }
            new Thread(new Runnable(){

                @Override
                public void run() {
                    SmsUsageMonitor.this.writePremiumSmsPolicyDb();
                }
            }).start();
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("invalid SMS permission type ");
        ((StringBuilder)charSequence).append(n);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static class SettingsObserver
    extends ContentObserver {
        private final Context mContext;
        private final AtomicBoolean mEnabled;

        SettingsObserver(Handler handler, Context context, AtomicBoolean atomicBoolean) {
            super(handler);
            this.mContext = context;
            this.mEnabled = atomicBoolean;
            this.onChange(false);
        }

        public void onChange(boolean bl) {
            AtomicBoolean atomicBoolean = this.mEnabled;
            ContentResolver contentResolver = this.mContext.getContentResolver();
            bl = true;
            if (Settings.Global.getInt((ContentResolver)contentResolver, (String)"sms_short_code_confirmation", (int)1) == 0) {
                bl = false;
            }
            atomicBoolean.set(bl);
        }
    }

    private static class SettingsObserverHandler
    extends Handler {
        SettingsObserverHandler(Context object, AtomicBoolean atomicBoolean) {
            ContentResolver contentResolver = object.getContentResolver();
            object = new SettingsObserver(this, (Context)object, atomicBoolean);
            contentResolver.registerContentObserver(Settings.Global.getUriFor((String)"sms_short_code_confirmation"), false, (ContentObserver)object);
        }
    }

    private static final class ShortCodePatternMatcher {
        private final Pattern mFreeShortCodePattern;
        private final Pattern mPremiumShortCodePattern;
        private final Pattern mShortCodePattern;
        private final Pattern mStandardShortCodePattern;

        ShortCodePatternMatcher(String object, String string, String string2, String string3) {
            Object var5_5 = null;
            object = object != null ? Pattern.compile((String)object) : null;
            this.mShortCodePattern = object;
            object = string != null ? Pattern.compile(string) : null;
            this.mPremiumShortCodePattern = object;
            object = string2 != null ? Pattern.compile(string2) : null;
            this.mFreeShortCodePattern = object;
            object = string3 != null ? Pattern.compile(string3) : var5_5;
            this.mStandardShortCodePattern = object;
        }

        int getNumberCategory(String string) {
            Pattern pattern = this.mFreeShortCodePattern;
            if (pattern != null && pattern.matcher(string).matches()) {
                return 1;
            }
            pattern = this.mStandardShortCodePattern;
            if (pattern != null && pattern.matcher(string).matches()) {
                return 2;
            }
            pattern = this.mPremiumShortCodePattern;
            if (pattern != null && pattern.matcher(string).matches()) {
                return 4;
            }
            pattern = this.mShortCodePattern;
            if (pattern != null && pattern.matcher(string).matches()) {
                return 3;
            }
            return 0;
        }
    }

}

