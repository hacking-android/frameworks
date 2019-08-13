/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.hardware.soundtrigger;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public class KeyphraseEnrollmentInfo {
    public static final String ACTION_MANAGE_VOICE_KEYPHRASES = "com.android.intent.action.MANAGE_VOICE_KEYPHRASES";
    public static final String EXTRA_VOICE_KEYPHRASE_ACTION = "com.android.intent.extra.VOICE_KEYPHRASE_ACTION";
    public static final String EXTRA_VOICE_KEYPHRASE_HINT_TEXT = "com.android.intent.extra.VOICE_KEYPHRASE_HINT_TEXT";
    public static final String EXTRA_VOICE_KEYPHRASE_LOCALE = "com.android.intent.extra.VOICE_KEYPHRASE_LOCALE";
    private static final String TAG = "KeyphraseEnrollmentInfo";
    private static final String VOICE_KEYPHRASE_META_DATA = "android.voice_enrollment";
    private final Map<KeyphraseMetadata, String> mKeyphrasePackageMap;
    private final KeyphraseMetadata[] mKeyphrases;
    private String mParseError;

    public KeyphraseEnrollmentInfo(PackageManager packageManager) {
        Object object = packageManager.queryIntentActivities(new Intent(ACTION_MANAGE_VOICE_KEYPHRASES), 65536);
        if (object != null && !object.isEmpty()) {
            LinkedList<String> linkedList = new LinkedList<String>();
            this.mKeyphrasePackageMap = new HashMap<KeyphraseMetadata, String>();
            object = object.iterator();
            while (object.hasNext()) {
                Object object2;
                Object object3 = (ResolveInfo)object.next();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(object3.activityInfo.packageName, 128);
                if ((applicationInfo.privateFlags & 8) == 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(applicationInfo.packageName);
                    ((StringBuilder)object2).append("is not a privileged system app");
                    Slog.w(TAG, ((StringBuilder)object2).toString());
                    continue;
                }
                if (!"android.permission.MANAGE_VOICE_KEYPHRASES".equals(applicationInfo.permission)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(applicationInfo.packageName);
                    ((StringBuilder)object2).append(" does not require MANAGE_VOICE_KEYPHRASES");
                    Slog.w(TAG, ((StringBuilder)object2).toString());
                    continue;
                }
                object2 = this.getKeyphraseMetadataFromApplicationInfo(packageManager, applicationInfo, linkedList);
                if (object2 == null) continue;
                try {
                    this.mKeyphrasePackageMap.put((KeyphraseMetadata)object2, applicationInfo.packageName);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("error parsing voice enrollment meta-data for ");
                    ((StringBuilder)object2).append(object3.activityInfo.packageName);
                    object2 = ((StringBuilder)object2).toString();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append((String)object2);
                    ((StringBuilder)object3).append(": ");
                    ((StringBuilder)object3).append(nameNotFoundException);
                    linkedList.add(((StringBuilder)object3).toString());
                    Slog.w(TAG, (String)object2, nameNotFoundException);
                }
            }
            if (this.mKeyphrasePackageMap.isEmpty()) {
                linkedList.add("No suitable enrollment application found");
                Slog.w(TAG, "No suitable enrollment application found");
                this.mKeyphrases = null;
            } else {
                this.mKeyphrases = this.mKeyphrasePackageMap.keySet().toArray(new KeyphraseMetadata[this.mKeyphrasePackageMap.size()]);
            }
            if (!linkedList.isEmpty()) {
                this.mParseError = TextUtils.join((CharSequence)"\n", linkedList);
            }
            return;
        }
        this.mParseError = "No enrollment applications found";
        this.mKeyphrasePackageMap = Collections.emptyMap();
        this.mKeyphrases = null;
    }

    private KeyphraseMetadata getKeyphraseFromTypedArray(TypedArray object, String string2, List<String> list) {
        int n;
        int n2 = ((TypedArray)object).getInt(0, -1);
        if (n2 <= 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No valid searchKeyphraseId specified in meta-data for ");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
            list.add((String)object);
            Slog.w(TAG, (String)object);
            return null;
        }
        String string3 = ((TypedArray)object).getString(1);
        if (string3 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No valid searchKeyphrase specified in meta-data for ");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
            list.add((String)object);
            Slog.w(TAG, (String)object);
            return null;
        }
        String[] arrstring = ((TypedArray)object).getString(2);
        if (arrstring == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No valid searchKeyphraseSupportedLocales specified in meta-data for ");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
            list.add((String)object);
            Slog.w(TAG, (String)object);
            return null;
        }
        ArraySet<Locale> arraySet = new ArraySet<Locale>();
        if (!TextUtils.isEmpty((CharSequence)arrstring)) {
            try {
                arrstring = arrstring.split(",");
                for (n = 0; n < arrstring.length; ++n) {
                    arraySet.add(Locale.forLanguageTag(arrstring[n]));
                }
            }
            catch (Exception exception) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append("Error reading searchKeyphraseSupportedLocales from meta-data for ");
                charSequence.append(string2);
                charSequence = charSequence.toString();
                list.add((String)charSequence);
                Slog.w(TAG, (String)charSequence);
                return null;
            }
            {
                continue;
                break;
            }
        }
        if ((n = ((TypedArray)object).getInt(3, -1)) < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No valid searchKeyphraseRecognitionFlags specified in meta-data for ");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
            list.add((String)object);
            Slog.w(TAG, (String)object);
            return null;
        }
        return new KeyphraseMetadata(n2, string3, arraySet, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private KeyphraseMetadata getKeyphraseMetadataFromApplicationInfo(PackageManager var1_1, ApplicationInfo var2_3, List<String> var3_5) {
        block18 : {
            block16 : {
                block17 : {
                    var4_6 = null;
                    var5_7 = null;
                    var6_11 = null;
                    var7_12 = null;
                    var7_12 = null;
                    var7_12 = null;
                    var7_12 = null;
                    var8_13 = null;
                    var9_20 = var2_3.packageName;
                    var10_21 = null;
                    var11_22 = null;
                    var12_23 = null;
                    var13_24 = null;
                    var14_25 = null;
                    var15_26 = null;
                    var7_12 = var2_3.loadXmlMetaData((PackageManager)var1_1, "android.voice_enrollment");
                    if (var7_12 == null) {
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1 = new StringBuilder();
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1.append("No android.voice_enrollment meta-data for ");
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1.append(var9_20);
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1 = var1_1.toString();
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var3_5.add((String)var1_1);
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        Slog.w("KeyphraseEnrollmentInfo", (String)var1_1);
                        if (var7_12 == null) return null;
                        var7_12.close();
                        return null;
                    }
                    var8_13 = var7_12;
                    var4_6 = var7_12;
                    var5_7 = var7_12;
                    var6_11 = var7_12;
                    var1_1 = var1_1.getResourcesForApplication((ApplicationInfo)var2_3);
                    var8_13 = var7_12;
                    var4_6 = var7_12;
                    var5_7 = var7_12;
                    var6_11 = var7_12;
                    var2_3 = Xml.asAttributeSet(var7_12);
                    do {
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                    } while ((var16_27 = var7_12.next()) != 1 && var16_27 != 2);
                    var8_13 = var7_12;
                    var4_6 = var7_12;
                    var5_7 = var7_12;
                    var6_11 = var7_12;
                    if (!"voice-enrollment-application".equals(var7_12.getName())) {
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1 = new StringBuilder();
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1.append("Meta-data does not start with voice-enrollment-application tag for ");
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1.append(var9_20);
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var1_1 = var1_1.toString();
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        var3_5.add((String)var1_1);
                        var8_13 = var7_12;
                        var4_6 = var7_12;
                        var5_7 = var7_12;
                        var6_11 = var7_12;
                        Slog.w("KeyphraseEnrollmentInfo", (String)var1_1);
                        var7_12.close();
                        return null;
                    }
                    var8_13 = var7_12;
                    var4_6 = var7_12;
                    var5_7 = var7_12;
                    var6_11 = var7_12;
                    var17_28 = var1_1.obtainAttributes((AttributeSet)var2_3, R.styleable.VoiceEnrollmentApplication);
                    var8_13 = var15_26;
                    var4_6 = var10_21;
                    var5_7 = var11_22;
                    var1_1 = var7_12;
                    try {
                        var8_13 = var2_3 = this.getKeyphraseFromTypedArray(var17_28, var9_20, var3_5);
                        var4_6 = var2_3;
                        var5_7 = var2_3;
                        var1_1 = var7_12;
                        var17_28.recycle();
                        var1_1 = var2_3;
                        break block16;
                    }
                    catch (PackageManager.NameNotFoundException var5_8) {
                        var2_3 = var8_13;
                        break block17;
                    }
                    catch (IOException var8_14) {
                        var2_3 = var4_6;
                        ** GOTO lbl196
                    }
                    catch (XmlPullParserException var8_15) {
                        var2_3 = var5_7;
                        ** GOTO lbl229
                    }
                    catch (Throwable var1_2) {
                        break block18;
                    }
                    catch (PackageManager.NameNotFoundException var5_9) {
                        var7_12 = var4_6;
                        var2_3 = var12_23;
                    }
                    catch (IOException var8_16) {
                        var7_12 = var5_7;
                        var2_3 = var13_24;
                        ** GOTO lbl196
                    }
                    catch (XmlPullParserException var8_17) {
                        var7_12 = var6_11;
                        var2_3 = var14_25;
                        ** GOTO lbl229
                    }
                }
                var1_1 = var7_12;
                try {
                    var1_1 = var7_12;
                    var8_13 = new StringBuilder();
                    var1_1 = var7_12;
                    var8_13.append("Error parsing keyphrase enrollment meta-data for ");
                    var1_1 = var7_12;
                    var8_13.append(var9_20);
                    var1_1 = var7_12;
                    var8_13 = var8_13.toString();
                    var1_1 = var7_12;
                    var1_1 = var7_12;
                    var4_6 = new StringBuilder();
                    var1_1 = var7_12;
                    var4_6.append((String)var8_13);
                    var1_1 = var7_12;
                    var4_6.append(": ");
                    var1_1 = var7_12;
                    var4_6.append(var5_10);
                    var1_1 = var7_12;
                    var3_5.add(var4_6.toString());
                    var1_1 = var7_12;
                    Slog.w("KeyphraseEnrollmentInfo", (String)var8_13, (Throwable)var5_10);
                    var1_1 = var2_3;
                    if (var7_12 == null) return var1_1;
                    var1_1 = var2_3;
                    break block16;
lbl196: // 2 sources:
                    var1_1 = var7_12;
                    var1_1 = var7_12;
                    var4_6 = new StringBuilder();
                    var1_1 = var7_12;
                    var4_6.append("Error parsing keyphrase enrollment meta-data for ");
                    var1_1 = var7_12;
                    var4_6.append(var9_20);
                    var1_1 = var7_12;
                    var4_6 = var4_6.toString();
                    var1_1 = var7_12;
                    var1_1 = var7_12;
                    var5_7 = new StringBuilder();
                    var1_1 = var7_12;
                    var5_7.append((String)var4_6);
                    var1_1 = var7_12;
                    var5_7.append(": ");
                    var1_1 = var7_12;
                    var5_7.append(var8_18);
                    var1_1 = var7_12;
                    var3_5.add(var5_7.toString());
                    var1_1 = var7_12;
                    Slog.w("KeyphraseEnrollmentInfo", (String)var4_6, (Throwable)var8_18);
                    var1_1 = var2_3;
                    if (var7_12 == null) return var1_1;
                    var1_1 = var2_3;
                    break block16;
lbl229: // 2 sources:
                    var1_1 = var7_12;
                    var1_1 = var7_12;
                    var4_6 = new StringBuilder();
                    var1_1 = var7_12;
                    var4_6.append("Error parsing keyphrase enrollment meta-data for ");
                    var1_1 = var7_12;
                    var4_6.append(var9_20);
                    var1_1 = var7_12;
                    var4_6 = var4_6.toString();
                    var1_1 = var7_12;
                    var1_1 = var7_12;
                    var5_7 = new StringBuilder();
                    var1_1 = var7_12;
                    var5_7.append((String)var4_6);
                    var1_1 = var7_12;
                    var5_7.append(": ");
                    var1_1 = var7_12;
                    var5_7.append(var8_19);
                    var1_1 = var7_12;
                    var3_5.add(var5_7.toString());
                    var1_1 = var7_12;
                    Slog.w("KeyphraseEnrollmentInfo", (String)var4_6, (Throwable)var8_19);
                    var1_1 = var2_3;
                    if (var7_12 == null) return var1_1;
                    var1_1 = var2_3;
                }
                catch (Throwable var2_4) {
                    var8_13 = var1_1;
                    var1_1 = var2_4;
                }
            }
            var7_12.close();
            return var1_1;
        }
        if (var8_13 == null) throw var1_1;
        var8_13.close();
        throw var1_1;
    }

    public KeyphraseMetadata getKeyphraseMetadata(String string2, Locale locale) {
        Object object = this.mKeyphrases;
        if (object != null && ((KeyphraseMetadata[])object).length > 0) {
            for (Object object2 : object) {
                if (!((KeyphraseMetadata)object2).supportsPhrase(string2) || !((KeyphraseMetadata)object2).supportsLocale(locale)) continue;
                return object2;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No enrollment application supports the given keyphrase/locale: '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'/");
        ((StringBuilder)object).append(locale);
        Slog.w(TAG, ((StringBuilder)object).toString());
        return null;
    }

    public Intent getManageKeyphraseIntent(int n, String string2, Locale locale) {
        Object object = this.mKeyphrasePackageMap;
        if (object != null && !object.isEmpty()) {
            object = this.getKeyphraseMetadata(string2, locale);
            if (object != null) {
                return new Intent(ACTION_MANAGE_VOICE_KEYPHRASES).setPackage(this.mKeyphrasePackageMap.get(object)).putExtra(EXTRA_VOICE_KEYPHRASE_HINT_TEXT, string2).putExtra(EXTRA_VOICE_KEYPHRASE_LOCALE, locale.toLanguageTag()).putExtra(EXTRA_VOICE_KEYPHRASE_ACTION, n);
            }
            return null;
        }
        Slog.w(TAG, "No enrollment application exists");
        return null;
    }

    public String getParseError() {
        return this.mParseError;
    }

    public KeyphraseMetadata[] listKeyphraseMetadata() {
        return this.mKeyphrases;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KeyphraseEnrollmentInfo [Keyphrases=");
        stringBuilder.append(this.mKeyphrasePackageMap.toString());
        stringBuilder.append(", ParseError=");
        stringBuilder.append(this.mParseError);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

