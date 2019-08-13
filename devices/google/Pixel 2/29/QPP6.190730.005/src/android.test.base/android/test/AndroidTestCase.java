/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.util.Log
 */
package android.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import junit.framework.TestCase;

@Deprecated
public class AndroidTestCase
extends TestCase {
    protected Context mContext;
    private Context mTestContext;

    public void assertActivityRequiresPermission(String charSequence, String string, String string2) {
        Intent intent = new Intent();
        intent.setClassName((String)charSequence, string);
        intent.addFlags(268435456);
        try {
            this.getContext().startActivity(intent);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("expected security exception for ");
            ((StringBuilder)charSequence).append(string2);
            AndroidTestCase.fail(((StringBuilder)charSequence).toString());
        }
        catch (SecurityException securityException) {
            AndroidTestCase.assertNotNull("security exception's error message.", securityException.getMessage());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("error message should contain ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(".");
            AndroidTestCase.assertTrue(((StringBuilder)charSequence).toString(), securityException.getMessage().contains(string2));
        }
    }

    public void assertReadingContentUriRequiresPermission(Uri object, String string) {
        try {
            this.getContext().getContentResolver().query((Uri)object, null, null, null, null);
            object = new StringBuilder();
            ((StringBuilder)object).append("expected SecurityException requiring ");
            ((StringBuilder)object).append(string);
            AndroidTestCase.fail(((StringBuilder)object).toString());
        }
        catch (SecurityException securityException) {
            AndroidTestCase.assertNotNull("security exception's error message.", securityException.getMessage());
            object = new StringBuilder();
            ((StringBuilder)object).append("error message should contain ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(".");
            AndroidTestCase.assertTrue(((StringBuilder)object).toString(), securityException.getMessage().contains(string));
        }
    }

    public void assertWritingContentUriRequiresPermission(Uri object, String string) {
        try {
            ContentResolver contentResolver = this.getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentResolver.insert((Uri)object, contentValues);
            object = new StringBuilder();
            ((StringBuilder)object).append("expected SecurityException requiring ");
            ((StringBuilder)object).append(string);
            AndroidTestCase.fail(((StringBuilder)object).toString());
        }
        catch (SecurityException securityException) {
            AndroidTestCase.assertNotNull("security exception's error message.", securityException.getMessage());
            object = new StringBuilder();
            ((StringBuilder)object).append("error message should contain \"");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("\". Got: \"");
            ((StringBuilder)object).append(securityException.getMessage());
            ((StringBuilder)object).append("\".");
            AndroidTestCase.assertTrue(((StringBuilder)object).toString(), securityException.getMessage().contains(string));
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public Context getTestContext() {
        return this.mTestContext;
    }

    protected void scrubClass(Class<?> class_) throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType().isPrimitive() || Modifier.isStatic(field.getModifiers())) continue;
            try {
                field.setAccessible(true);
                field.set(this, null);
            }
            catch (Exception exception) {
                Log.d((String)"TestCase", (String)"Error: Could not nullify field!");
            }
            if (field.get(this) == null) continue;
            Log.d((String)"TestCase", (String)"Error: Could not nullify field!");
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setTestContext(Context context) {
        this.mTestContext = context;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Suppress
    public void testAndroidTestCaseSetupProperly() {
        AndroidTestCase.assertNotNull("Context is null. setContext should be called before tests are run", (Object)this.mContext);
    }
}

