/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 *  com.google.android.textclassifier.AnnotatorModel$DatetimeResult
 */
package android.view.textclassifier.intent;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.CalendarContract;
import android.view.textclassifier.Log;
import android.view.textclassifier.intent.ClassificationIntentFactory;
import android.view.textclassifier.intent.LabeledIntent;
import com.google.android.textclassifier.AnnotatorModel;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class LegacyClassificationIntentFactory
implements ClassificationIntentFactory {
    private static final long DEFAULT_EVENT_DURATION;
    private static final long MIN_EVENT_FUTURE_MILLIS;
    private static final String TAG = "LegacyClassificationIntentFactory";

    static {
        MIN_EVENT_FUTURE_MILLIS = TimeUnit.MINUTES.toMillis(5L);
        DEFAULT_EVENT_DURATION = TimeUnit.HOURS.toMillis(1L);
    }

    private static LabeledIntent createCalendarCreateEventIntent(Context context, Instant instant, String string2) {
        boolean bl = "date".equals(string2);
        return new LabeledIntent(context.getString(17039466), null, context.getString(17039467), null, new Intent("android.intent.action.INSERT").setData(CalendarContract.Events.CONTENT_URI).putExtra("allDay", bl).putExtra("beginTime", instant.toEpochMilli()).putExtra("endTime", instant.toEpochMilli() + DEFAULT_EVENT_DURATION), instant.hashCode());
    }

    private static LabeledIntent createCalendarViewIntent(Context context, Instant instant) {
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, instant.toEpochMilli());
        return new LabeledIntent(context.getString(17041187), null, context.getString(17041188), null, new Intent("android.intent.action.VIEW").setData(builder.build()), 0);
    }

    private static List<LabeledIntent> createForAddress(Context object, String string2) {
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        try {
            String string3 = URLEncoder.encode(string2, "UTF-8");
            string2 = ((Context)object).getString(17040303);
            String string4 = ((Context)object).getString(17040304);
            object = new Intent("android.intent.action.VIEW");
            LabeledIntent labeledIntent = new LabeledIntent(string2, null, string4, null, ((Intent)object).setData(Uri.parse(String.format("geo:0,0?q=%s", string3))), 0);
            arrayList.add(labeledIntent);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e(TAG, "Could not encode address", unsupportedEncodingException);
        }
        return arrayList;
    }

    private static List<LabeledIntent> createForDatetime(Context context, String string2, Instant serializable, Instant instant) {
        Instant instant2 = serializable;
        if (serializable == null) {
            instant2 = Instant.now();
        }
        serializable = new ArrayList();
        serializable.add(LegacyClassificationIntentFactory.createCalendarViewIntent(context, instant));
        if (instant2.until(instant, ChronoUnit.MILLIS) > MIN_EVENT_FUTURE_MILLIS) {
            serializable.add(LegacyClassificationIntentFactory.createCalendarCreateEventIntent(context, instant, string2));
        }
        return serializable;
    }

    private static List<LabeledIntent> createForDictionary(Context context, String string2) {
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        arrayList.add(new LabeledIntent(context.getString(17039849), null, context.getString(17039850), null, new Intent("android.intent.action.DEFINE").putExtra("android.intent.extra.TEXT", string2), string2.hashCode()));
        return arrayList;
    }

    private static List<LabeledIntent> createForEmail(Context context, String string2) {
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        arrayList.add(new LabeledIntent(context.getString(17039888), null, context.getString(17039894), null, new Intent("android.intent.action.SENDTO").setData(Uri.parse(String.format("mailto:%s", string2))), 0));
        arrayList.add(new LabeledIntent(context.getString(17039468), null, context.getString(17039469), null, new Intent("android.intent.action.INSERT_OR_EDIT").setType("vnd.android.cursor.item/contact").putExtra("email", string2), string2.hashCode()));
        return arrayList;
    }

    private static List<LabeledIntent> createForFlight(Context context, String string2) {
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        arrayList.add(new LabeledIntent(context.getString(17041189), null, context.getString(17041190), null, new Intent("android.intent.action.WEB_SEARCH").putExtra("query", string2), string2.hashCode()));
        return arrayList;
    }

    private static List<LabeledIntent> createForPhone(Context context, String string2) {
        ArrayList<LabeledIntent> arrayList = new ArrayList<LabeledIntent>();
        Object object = context.getSystemService(UserManager.class);
        object = object != null ? ((UserManager)object).getUserRestrictions() : new Bundle();
        if (!((BaseBundle)object).getBoolean("no_outgoing_calls", false)) {
            arrayList.add(new LabeledIntent(context.getString(17039861), null, context.getString(17039862), null, new Intent("android.intent.action.DIAL").setData(Uri.parse(String.format("tel:%s", string2))), 0));
        }
        arrayList.add(new LabeledIntent(context.getString(17039468), null, context.getString(17039469), null, new Intent("android.intent.action.INSERT_OR_EDIT").setType("vnd.android.cursor.item/contact").putExtra("phone", string2), string2.hashCode()));
        if (!((BaseBundle)object).getBoolean("no_sms", false)) {
            arrayList.add(new LabeledIntent(context.getString(17041040), null, context.getString(17041045), null, new Intent("android.intent.action.SENDTO").setData(Uri.parse(String.format("smsto:%s", string2))), 0));
        }
        return arrayList;
    }

    private static List<LabeledIntent> createForUrl(Context context, String object) {
        CharSequence charSequence = object;
        if (Uri.parse((String)object).getScheme() == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("http://");
            ((StringBuilder)charSequence).append((String)object);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        object = new ArrayList();
        object.add(new LabeledIntent(context.getString(17039616), null, context.getString(17039617), null, new Intent("android.intent.action.VIEW").setDataAndNormalize(Uri.parse((String)charSequence)).putExtra("com.android.browser.application_id", context.getPackageName()), 0));
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public List<LabeledIntent> create(Context var1_1, String var2_2, boolean var3_3, Instant var4_4, AnnotatorModel.ClassificationResult var5_5) {
        block20 : {
            var6_6 = var5_5 != null ? var5_5.getCollection().trim().toLowerCase(Locale.ENGLISH) : "";
            var7_7 = var2_2.trim();
            var8_8 = -1;
            switch (var6_6.hashCode()) {
                case 1793702779: {
                    if (!var6_6.equals("datetime")) break;
                    var8_8 = 5;
                    ** break;
                }
                case 447049878: {
                    if (!var6_6.equals("dictionary")) break;
                    var8_8 = 7;
                    ** break;
                }
                case 106642798: {
                    if (!var6_6.equals("phone")) break;
                    var8_8 = 1;
                    ** break;
                }
                case 96619420: {
                    if (!var6_6.equals("email")) break;
                    var8_8 = 0;
                    ** break;
                }
                case 3076014: {
                    if (!var6_6.equals("date")) break;
                    var8_8 = 4;
                    ** break;
                }
                case 116079: {
                    if (!var6_6.equals("url")) break;
                    var8_8 = 3;
                    ** break;
                }
                case -1147692044: {
                    if (!var6_6.equals("address")) break;
                    var8_8 = 2;
                    ** break;
                }
                case -1271823248: {
                    if (!var6_6.equals("flight")) break;
                    var8_8 = 6;
                    break block20;
                }
            }
            ** break;
        }
        switch (var8_8) {
            default: {
                var2_2 = new ArrayList<LabeledIntent>();
                ** break;
            }
            case 7: {
                var2_2 = LegacyClassificationIntentFactory.createForDictionary(var1_1, var7_7);
                ** break;
            }
            case 6: {
                var2_2 = LegacyClassificationIntentFactory.createForFlight(var1_1, var7_7);
                ** break;
            }
            case 4: 
            case 5: {
                if (var5_5.getDatetimeResult() != null) {
                    var2_2 = LegacyClassificationIntentFactory.createForDatetime(var1_1, var6_6, var4_4, Instant.ofEpochMilli(var5_5.getDatetimeResult().getTimeMsUtc()));
                    ** break;
                }
                var2_2 = new ArrayList<LabeledIntent>();
                ** break;
            }
            case 3: {
                var2_2 = LegacyClassificationIntentFactory.createForUrl(var1_1, var7_7);
                ** break;
            }
            case 2: {
                var2_2 = LegacyClassificationIntentFactory.createForAddress(var1_1, var7_7);
                ** break;
            }
            case 1: {
                var2_2 = LegacyClassificationIntentFactory.createForPhone(var1_1, var7_7);
                ** break;
            }
            case 0: 
        }
        var2_2 = LegacyClassificationIntentFactory.createForEmail(var1_1, var7_7);
lbl66: // 9 sources:
        if (var3_3 == false) return var2_2;
        ClassificationIntentFactory.insertTranslateAction(var2_2, var1_1, var7_7);
        return var2_2;
    }
}

