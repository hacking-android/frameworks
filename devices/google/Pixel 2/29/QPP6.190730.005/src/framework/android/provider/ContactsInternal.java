/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;
import java.util.List;

public class ContactsInternal {
    private static final int CONTACTS_URI_LOOKUP = 1001;
    private static final int CONTACTS_URI_LOOKUP_ID = 1000;
    private static final UriMatcher sContactsUriMatcher;

    static {
        UriMatcher uriMatcher = sContactsUriMatcher = new UriMatcher(-1);
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*", 1001);
        uriMatcher.addURI("com.android.contacts", "contacts/lookup/*/#", 1000);
    }

    private ContactsInternal() {
    }

    private static boolean maybeStartManagedQuickContact(Context object, Intent intent) {
        Object object2 = intent.getData();
        List<String> list = ((Uri)object2).getPathSegments();
        boolean bl = list.size() < 4;
        long l = bl ? ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE : ContentUris.parseId((Uri)object2);
        list = list.get(2);
        object2 = ((Uri)object2).getQueryParameter("directory");
        long l2 = object2 == null ? 1000000000L : Long.parseLong((String)object2);
        if (!TextUtils.isEmpty((CharSequence)((Object)list)) && ((String)((Object)list)).startsWith(ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX)) {
            if (ContactsContract.Contacts.isEnterpriseContactId(l)) {
                if (ContactsContract.Directory.isEnterpriseDirectoryId(l2)) {
                    ((Context)object).getSystemService(DevicePolicyManager.class).startManagedQuickContact(((String)((Object)list)).substring(ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX.length()), l - ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE, bl, l2 - 1000000000L, intent);
                    return true;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid enterprise directory id: ");
                ((StringBuilder)object).append(l2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid enterprise contact id: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return false;
    }

    @UnsupportedAppUsage
    public static void startQuickContactWithErrorToast(Context context, Intent intent) {
        Uri uri = intent.getData();
        int n = sContactsUriMatcher.match(uri);
        if ((n == 1000 || n == 1001) && ContactsInternal.maybeStartManagedQuickContact(context, intent)) {
            return;
        }
        ContactsInternal.startQuickContactWithErrorToastForUser(context, intent, context.getUser());
    }

    public static void startQuickContactWithErrorToastForUser(Context context, Intent intent, UserHandle userHandle) {
        try {
            context.startActivityAsUser(intent, userHandle);
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(context, 17040890, 0).show();
        }
    }
}

