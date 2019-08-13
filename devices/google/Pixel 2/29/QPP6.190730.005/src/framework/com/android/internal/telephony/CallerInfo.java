/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.i18n.phonenumbers.NumberParseException
 *  com.android.i18n.phonenumbers.PhoneNumberUtil
 *  com.android.i18n.phonenumbers.Phonenumber
 *  com.android.i18n.phonenumbers.Phonenumber$PhoneNumber
 *  com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import com.android.internal.telephony.CallerInfoAsyncQuery;
import java.util.Locale;

public class CallerInfo {
    private static final String TAG = "CallerInfo";
    public static final long USER_TYPE_CURRENT = 0L;
    public static final long USER_TYPE_WORK = 1L;
    private static final boolean VDBG = Rlog.isLoggable("CallerInfo", 2);
    public Drawable cachedPhoto;
    public Bitmap cachedPhotoIcon;
    public String cnapName;
    public Uri contactDisplayPhotoUri;
    public boolean contactExists;
    @UnsupportedAppUsage
    public long contactIdOrZero;
    public Uri contactRefUri;
    public Uri contactRingtoneUri;
    public String geoDescription;
    public boolean isCachedPhotoCurrent;
    public String lookupKey;
    private boolean mIsEmergency = false;
    private boolean mIsVoiceMail = false;
    @UnsupportedAppUsage
    public String name;
    public int namePresentation;
    public boolean needUpdate;
    public String normalizedNumber;
    @UnsupportedAppUsage
    public String numberLabel;
    public int numberPresentation;
    @UnsupportedAppUsage
    public int numberType;
    public String phoneLabel;
    @UnsupportedAppUsage
    public String phoneNumber;
    public int photoResource;
    public ComponentName preferredPhoneAccountComponent;
    public String preferredPhoneAccountId;
    public boolean shouldSendToVoicemail;
    public long userType = 0L;

    static CallerInfo doSecondaryLookupIfNecessary(Context context, String string2, CallerInfo callerInfo) {
        CallerInfo callerInfo2 = callerInfo;
        if (!callerInfo.contactExists) {
            callerInfo2 = callerInfo;
            if (PhoneNumberUtils.isUriNumber(string2)) {
                string2 = PhoneNumberUtils.getUsernameFromUriNumber(string2);
                callerInfo2 = callerInfo;
                if (PhoneNumberUtils.isGlobalPhoneNumber(string2)) {
                    callerInfo2 = CallerInfo.getCallerInfo(context, Uri.withAppendedPath(ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, Uri.encode(string2)));
                }
            }
        }
        return callerInfo2;
    }

    @UnsupportedAppUsage
    public static CallerInfo getCallerInfo(Context context, Uri uri) {
        CallerInfo callerInfo = null;
        ContentResolver contentResolver = CallerInfoAsyncQuery.getCurrentProfileContentResolver(context);
        CallerInfo callerInfo2 = callerInfo;
        if (contentResolver != null) {
            try {
                callerInfo2 = CallerInfo.getCallerInfo(context, uri, contentResolver.query(uri, null, null, null, null));
            }
            catch (RuntimeException runtimeException) {
                Rlog.e(TAG, "Error getting caller info.", runtimeException);
                callerInfo2 = callerInfo;
            }
        }
        return callerInfo2;
    }

    public static CallerInfo getCallerInfo(Context object, Uri uri, Cursor cursor) {
        CallerInfo callerInfo = new CallerInfo();
        callerInfo.photoResource = 0;
        callerInfo.phoneLabel = null;
        callerInfo.numberType = 0;
        callerInfo.numberLabel = null;
        callerInfo.cachedPhoto = null;
        callerInfo.isCachedPhotoCurrent = false;
        callerInfo.contactExists = false;
        callerInfo.userType = 0L;
        if (VDBG) {
            Rlog.v(TAG, "getCallerInfo() based on cursor...");
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int n;
                int n2 = cursor.getColumnIndex("display_name");
                if (n2 != -1) {
                    callerInfo.name = cursor.getString(n2);
                }
                if ((n2 = cursor.getColumnIndex("number")) != -1) {
                    callerInfo.phoneNumber = cursor.getString(n2);
                }
                if ((n2 = cursor.getColumnIndex("normalized_number")) != -1) {
                    callerInfo.normalizedNumber = cursor.getString(n2);
                }
                if ((n2 = cursor.getColumnIndex("label")) != -1 && (n = cursor.getColumnIndex("type")) != -1) {
                    callerInfo.numberType = cursor.getInt(n);
                    callerInfo.numberLabel = cursor.getString(n2);
                    callerInfo.phoneLabel = ContactsContract.CommonDataKinds.Phone.getDisplayLabel((Context)object, callerInfo.numberType, callerInfo.numberLabel).toString();
                }
                if ((n2 = CallerInfo.getColumnIndexForPersonId(uri, cursor)) != -1) {
                    long l = cursor.getLong(n2);
                    if (l != 0L && !ContactsContract.Contacts.isEnterpriseContactId(l)) {
                        callerInfo.contactIdOrZero = l;
                        if (VDBG) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("==> got info.contactIdOrZero: ");
                            ((StringBuilder)object).append(callerInfo.contactIdOrZero);
                            Rlog.v(TAG, ((StringBuilder)object).toString());
                        }
                    }
                    if (ContactsContract.Contacts.isEnterpriseContactId(l)) {
                        callerInfo.userType = 1L;
                    }
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't find contact_id column for ");
                    ((StringBuilder)object).append(uri);
                    Rlog.w(TAG, ((StringBuilder)object).toString());
                }
                n2 = cursor.getColumnIndex("lookup");
                if (n2 != -1) {
                    callerInfo.lookupKey = cursor.getString(n2);
                }
                callerInfo.contactDisplayPhotoUri = (n2 = cursor.getColumnIndex("photo_uri")) != -1 && cursor.getString(n2) != null ? Uri.parse(cursor.getString(n2)) : null;
                n2 = cursor.getColumnIndex("preferred_phone_account_component_name");
                if (n2 != -1 && cursor.getString(n2) != null) {
                    callerInfo.preferredPhoneAccountComponent = ComponentName.unflattenFromString(cursor.getString(n2));
                }
                if ((n2 = cursor.getColumnIndex("preferred_phone_account_id")) != -1 && cursor.getString(n2) != null) {
                    callerInfo.preferredPhoneAccountId = cursor.getString(n2);
                }
                callerInfo.contactRingtoneUri = (n2 = cursor.getColumnIndex("custom_ringtone")) != -1 && cursor.getString(n2) != null ? (TextUtils.isEmpty(cursor.getString(n2)) ? Uri.EMPTY : Uri.parse(cursor.getString(n2))) : null;
                n2 = cursor.getColumnIndex("send_to_voicemail");
                boolean bl = n2 != -1 && cursor.getInt(n2) == 1;
                callerInfo.shouldSendToVoicemail = bl;
                callerInfo.contactExists = true;
            }
            cursor.close();
        }
        callerInfo.needUpdate = false;
        callerInfo.name = CallerInfo.normalize(callerInfo.name);
        callerInfo.contactRefUri = uri;
        return callerInfo;
    }

    @UnsupportedAppUsage
    public static CallerInfo getCallerInfo(Context context, String string2) {
        if (VDBG) {
            Rlog.v(TAG, "getCallerInfo() based on number...");
        }
        return CallerInfo.getCallerInfo(context, string2, SubscriptionManager.getDefaultSubscriptionId());
    }

    @UnsupportedAppUsage
    public static CallerInfo getCallerInfo(Context object, String string2, int n) {
        if (TextUtils.isEmpty(string2)) {
            return null;
        }
        if (PhoneNumberUtils.isLocalEmergencyNumber((Context)object, string2)) {
            return new CallerInfo().markAsEmergency((Context)object);
        }
        if (PhoneNumberUtils.isVoiceMailNumber(n, string2)) {
            return new CallerInfo().markAsVoiceMail();
        }
        object = CallerInfo.doSecondaryLookupIfNecessary((Context)object, string2, CallerInfo.getCallerInfo((Context)object, Uri.withAppendedPath(ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, Uri.encode(string2))));
        if (TextUtils.isEmpty(((CallerInfo)object).phoneNumber)) {
            ((CallerInfo)object).phoneNumber = string2;
        }
        return object;
    }

    private static int getColumnIndexForPersonId(Uri object, Cursor object2) {
        CharSequence charSequence;
        if (VDBG) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("- getColumnIndexForPersonId: contactRef URI = '");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append("'...");
            Rlog.v(TAG, ((StringBuilder)charSequence).toString());
        }
        charSequence = ((Uri)object).toString();
        object = null;
        if (((String)charSequence).startsWith("content://com.android.contacts/data/phones")) {
            if (VDBG) {
                Rlog.v(TAG, "'data/phones' URI; using RawContacts.CONTACT_ID");
            }
            object = "contact_id";
        } else if (((String)charSequence).startsWith("content://com.android.contacts/data")) {
            if (VDBG) {
                Rlog.v(TAG, "'data' URI; using Data.CONTACT_ID");
            }
            object = "contact_id";
        } else if (((String)charSequence).startsWith("content://com.android.contacts/phone_lookup")) {
            if (VDBG) {
                Rlog.v(TAG, "'phone_lookup' URI; using PhoneLookup._ID");
            }
            object = "_id";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected prefix for contactRef '");
            stringBuilder.append((String)charSequence);
            stringBuilder.append("'");
            Rlog.w(TAG, stringBuilder.toString());
        }
        int n = object != null ? object2.getColumnIndex((String)object) : -1;
        if (VDBG) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("==> Using column '");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("' (columnIndex = ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(") for person_id lookup...");
            Rlog.v(TAG, ((StringBuilder)object2).toString());
        }
        return n;
    }

    protected static String getCurrentCountryIso(Context context) {
        return CallerInfo.getCurrentCountryIso(context, Locale.getDefault());
    }

    private static String getCurrentCountryIso(Context object, Locale locale) {
        Object object2 = null;
        CountryDetector countryDetector = (CountryDetector)((Context)object).getSystemService("country_detector");
        object = object2;
        if (countryDetector != null) {
            object = countryDetector.detectCountry();
            if (object != null) {
                object = ((Country)object).getCountryIso();
            } else {
                Rlog.e(TAG, "CountryDetector.detectCountry() returned null.");
                object = object2;
            }
        }
        object2 = object;
        if (object == null) {
            object2 = locale.getCountry();
            object = new StringBuilder();
            ((StringBuilder)object).append("No CountryDetector; falling back to countryIso based on locale: ");
            ((StringBuilder)object).append((String)object2);
            Rlog.w(TAG, ((StringBuilder)object).toString());
        }
        return object2;
    }

    public static String getGeoDescription(Context object, String charSequence) {
        StringBuilder stringBuilder;
        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder;
        Locale locale;
        block22 : {
            String string2;
            Object object2;
            block21 : {
                if (VDBG) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("getGeoDescription('");
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("')...");
                    Rlog.v(TAG, stringBuilder.toString());
                }
                if (TextUtils.isEmpty(charSequence)) {
                    return null;
                }
                object2 = PhoneNumberUtil.getInstance();
                phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();
                locale = object.getResources().getConfiguration().locale;
                string2 = CallerInfo.getCurrentCountryIso((Context)object, locale);
                stringBuilder = null;
                object = stringBuilder;
                if (!VDBG) break block21;
                object = stringBuilder;
                object = stringBuilder;
                StringBuilder stringBuilder2 = new StringBuilder();
                object = stringBuilder;
                stringBuilder2.append("parsing '");
                object = stringBuilder;
                stringBuilder2.append((String)charSequence);
                object = stringBuilder;
                stringBuilder2.append("' for countryIso '");
                object = stringBuilder;
                stringBuilder2.append(string2);
                object = stringBuilder;
                stringBuilder2.append("'...");
                object = stringBuilder;
                Rlog.v(TAG, stringBuilder2.toString());
            }
            object = stringBuilder;
            stringBuilder = object2.parse(charSequence, string2);
            object = stringBuilder;
            if (!VDBG) break block22;
            object = stringBuilder;
            object = stringBuilder;
            object2 = new StringBuilder();
            object = stringBuilder;
            ((StringBuilder)object2).append("- parsed number: ");
            object = stringBuilder;
            ((StringBuilder)object2).append((Object)stringBuilder);
            object = stringBuilder;
            try {
                Rlog.v(TAG, ((StringBuilder)object2).toString());
            }
            catch (NumberParseException numberParseException) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("getGeoDescription: NumberParseException for incoming number '");
                stringBuilder3.append(Rlog.pii(TAG, (Object)charSequence));
                stringBuilder3.append("'");
                Rlog.w(TAG, stringBuilder3.toString());
            }
        }
        object = stringBuilder;
        if (object != null) {
            object = phoneNumberOfflineGeocoder.getDescriptionForNumber((Phonenumber.PhoneNumber)object, locale);
            if (VDBG) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("- got description: '");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("'");
                Rlog.v(TAG, ((StringBuilder)charSequence).toString());
            }
            return object;
        }
        return null;
    }

    private static String normalize(String string2) {
        if (string2 != null && string2.length() <= 0) {
            return null;
        }
        return string2;
    }

    public boolean isEmergencyNumber() {
        return this.mIsEmergency;
    }

    public boolean isVoiceMailNumber() {
        return this.mIsVoiceMail;
    }

    CallerInfo markAsEmergency(Context context) {
        this.phoneNumber = context.getString(17039895);
        this.photoResource = 17303120;
        this.mIsEmergency = true;
        return this;
    }

    CallerInfo markAsVoiceMail() {
        return this.markAsVoiceMail(SubscriptionManager.getDefaultSubscriptionId());
    }

    CallerInfo markAsVoiceMail(int n) {
        this.mIsVoiceMail = true;
        try {
            this.phoneNumber = TelephonyManager.getDefault().getVoiceMailAlphaTag(n);
        }
        catch (SecurityException securityException) {
            Rlog.e(TAG, "Cannot access VoiceMail.", securityException);
        }
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        CharSequence charSequence = new StringBuilder();
        charSequence.append(super.toString());
        charSequence.append(" { ");
        stringBuilder.append(charSequence.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("name ");
        charSequence = this.name;
        String string2 = "null";
        charSequence = charSequence == null ? "null" : "non-null";
        stringBuilder2.append((String)charSequence);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", phoneNumber ");
        charSequence = this.phoneNumber == null ? string2 : "non-null";
        stringBuilder2.append((String)charSequence);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public void updateGeoDescription(Context context, String string2) {
        if (!TextUtils.isEmpty(this.phoneNumber)) {
            string2 = this.phoneNumber;
        }
        this.geoDescription = CallerInfo.getGeoDescription(context, string2);
    }
}

