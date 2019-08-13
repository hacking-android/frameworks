/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

public final class RulesUpdaterContract {
    public static final String ACTION_TRIGGER_RULES_UPDATE_CHECK = "com.android.intent.action.timezone.TRIGGER_RULES_UPDATE_CHECK";
    public static final String EXTRA_CHECK_TOKEN = "com.android.intent.extra.timezone.CHECK_TOKEN";
    public static final String TRIGGER_TIME_ZONE_RULES_CHECK_PERMISSION = "android.permission.TRIGGER_TIME_ZONE_RULES_CHECK";
    public static final String UPDATE_TIME_ZONE_RULES_PERMISSION = "android.permission.UPDATE_TIME_ZONE_RULES";

    public static Intent createUpdaterIntent(String string2) {
        Intent intent = new Intent(ACTION_TRIGGER_RULES_UPDATE_CHECK);
        intent.setPackage(string2);
        intent.setFlags(32);
        return intent;
    }

    public static void sendBroadcast(Context context, String object, byte[] arrby) {
        object = RulesUpdaterContract.createUpdaterIntent((String)object);
        ((Intent)object).putExtra(EXTRA_CHECK_TOKEN, arrby);
        context.sendBroadcastAsUser((Intent)object, UserHandle.SYSTEM, UPDATE_TIME_ZONE_RULES_PERMISSION);
    }
}

