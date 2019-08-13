/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentProvider
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.UriMatcher
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.database.MergeCursor
 *  android.net.Uri
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.text.TextUtils
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.internal.telephony.IIccPhoneBook;
import com.android.internal.telephony.uicc.AdnRecord;
import java.util.List;

public class IccProvider
extends ContentProvider {
    @UnsupportedAppUsage
    private static final String[] ADDRESS_BOOK_COLUMN_NAMES = new String[]{"name", "number", "emails", "_id"};
    protected static final int ADN = 1;
    protected static final int ADN_ALL = 7;
    protected static final int ADN_SUB = 2;
    @UnsupportedAppUsage
    private static final boolean DBG = true;
    protected static final int FDN = 3;
    protected static final int FDN_SUB = 4;
    protected static final int SDN = 5;
    protected static final int SDN_SUB = 6;
    protected static final String STR_EMAILS = "emails";
    protected static final String STR_NUMBER = "number";
    protected static final String STR_PIN2 = "pin2";
    protected static final String STR_TAG = "tag";
    private static final String TAG = "IccProvider";
    private static final UriMatcher URL_MATCHER = new UriMatcher(-1);
    private SubscriptionManager mSubscriptionManager;

    static {
        URL_MATCHER.addURI("icc", "adn", 1);
        URL_MATCHER.addURI("icc", "adn/subId/#", 2);
        URL_MATCHER.addURI("icc", "fdn", 3);
        URL_MATCHER.addURI("icc", "fdn/subId/#", 4);
        URL_MATCHER.addURI("icc", "sdn", 5);
        URL_MATCHER.addURI("icc", "sdn/subId/#", 6);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addIccRecordToEf(int n, String charSequence, String string, String[] object, String string2, int n2) {
        boolean bl;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("addIccRecordToEf: efType=0x");
        stringBuilder.append(Integer.toHexString(n).toUpperCase());
        stringBuilder.append(", name=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)charSequence));
        stringBuilder.append(", number=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)string));
        stringBuilder.append(", emails=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)object));
        stringBuilder.append(", subscription=");
        stringBuilder.append(n2);
        this.log(stringBuilder.toString());
        boolean bl2 = false;
        boolean bl3 = false;
        try {
            object = IIccPhoneBook.Stub.asInterface(ServiceManager.getService((String)"simphonebook"));
            bl = bl3;
            if (object != null) {
                bl = object.updateAdnRecordsInEfBySearchForSubscriber(n2, n, "", "", (String)charSequence, string, string2);
            }
        }
        catch (SecurityException securityException) {
            this.log(securityException.toString());
            bl = bl2;
        }
        catch (RemoteException remoteException) {
            bl = bl3;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("addIccRecordToEf: ");
        ((StringBuilder)charSequence).append(bl);
        this.log(((StringBuilder)charSequence).toString());
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean deleteIccRecordFromEf(int n, String charSequence, String string, String[] object, String string2, int n2) {
        boolean bl;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("deleteIccRecordFromEf: efType=0x");
        stringBuilder.append(Integer.toHexString(n).toUpperCase());
        stringBuilder.append(", name=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)charSequence));
        stringBuilder.append(", number=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)string));
        stringBuilder.append(", emails=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)object));
        stringBuilder.append(", pin2=");
        stringBuilder.append(Rlog.pii((String)TAG, (Object)string2));
        stringBuilder.append(", subscription=");
        stringBuilder.append(n2);
        this.log(stringBuilder.toString());
        boolean bl2 = false;
        boolean bl3 = false;
        try {
            object = IIccPhoneBook.Stub.asInterface(ServiceManager.getService((String)"simphonebook"));
            bl = bl3;
            if (object != null) {
                bl = object.updateAdnRecordsInEfBySearchForSubscriber(n2, n, (String)charSequence, string, "", "", string2);
            }
        }
        catch (SecurityException securityException) {
            this.log(securityException.toString());
            bl = bl2;
        }
        catch (RemoteException remoteException) {
            bl = bl3;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("deleteIccRecordFromEf: ");
        ((StringBuilder)charSequence).append(bl);
        this.log(((StringBuilder)charSequence).toString());
        return bl;
    }

    private int getRequestSubId(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRequestSubId url: ");
        stringBuilder.append((Object)uri);
        this.log(stringBuilder.toString());
        try {
            int n = Integer.parseInt(uri.getLastPathSegment());
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unknown URL ");
            stringBuilder2.append((Object)uri);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    private Cursor loadAllSimContacts(int n) {
        Object object;
        List list = this.mSubscriptionManager.getActiveSubscriptionInfoList(false);
        if (list != null && list.size() != 0) {
            int n2 = list.size();
            Cursor[] arrcursor = new Cursor[n2];
            int n3 = 0;
            do {
                object = arrcursor;
                if (n3 < n2) {
                    int n4 = ((SubscriptionInfo)list.get(n3)).getSubscriptionId();
                    arrcursor[n3] = this.loadFromEf(n, n4);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ADN Records loaded for Subscription ::");
                    ((StringBuilder)object).append(n4);
                    Rlog.i((String)TAG, (String)((StringBuilder)object).toString());
                    ++n3;
                    continue;
                }
                break;
            } while (true);
        } else {
            object = new Cursor[]{};
        }
        return new MergeCursor((Cursor[])object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private MatrixCursor loadFromEf(int n, int n2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("loadFromEf: efType=0x");
        ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
        ((StringBuilder)object).append(", subscription=");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        StringBuilder stringBuilder = null;
        MatrixCursor matrixCursor = null;
        try {
            IIccPhoneBook iIccPhoneBook = IIccPhoneBook.Stub.asInterface(ServiceManager.getService((String)"simphonebook"));
            object = matrixCursor;
            if (iIccPhoneBook != null) {
                object = iIccPhoneBook.getAdnRecordsInEfForSubscriber(n2, n);
            }
        }
        catch (SecurityException securityException) {
            this.log(securityException.toString());
            object = stringBuilder;
        }
        catch (RemoteException remoteException) {
            object = matrixCursor;
        }
        if (object == null) {
            Rlog.w((String)TAG, (String)"Cannot load ADN records");
            return new MatrixCursor(ADDRESS_BOOK_COLUMN_NAMES);
        }
        n2 = object.size();
        matrixCursor = new MatrixCursor(ADDRESS_BOOK_COLUMN_NAMES, n2);
        stringBuilder = new StringBuilder();
        stringBuilder.append("adnRecords.size=");
        stringBuilder.append(n2);
        this.log(stringBuilder.toString());
        n = 0;
        while (n < n2) {
            this.loadRecord((AdnRecord)object.get(n), matrixCursor, n);
            ++n;
        }
        return matrixCursor;
    }

    /*
     * WARNING - void declaration
     */
    @UnsupportedAppUsage
    private void loadRecord(AdnRecord object2, MatrixCursor matrixCursor, int n) {
        if (!((AdnRecord)object2).isEmpty()) {
            void var3_5;
            void var2_4;
            Object[] arrobject = new Object[4];
            String[] arrstring = ((AdnRecord)object2).getAlphaTag();
            CharSequence charSequence = ((AdnRecord)object2).getNumber();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("loadRecord: ");
            stringBuilder.append((String)arrstring);
            stringBuilder.append(", ");
            stringBuilder.append(Rlog.pii((String)TAG, (Object)charSequence));
            this.log(stringBuilder.toString());
            arrobject[0] = arrstring;
            arrobject[1] = charSequence;
            arrstring = ((AdnRecord)object2).getEmails();
            if (arrstring != null) {
                stringBuilder = new StringBuilder();
                for (String string : arrstring) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Adding email:");
                    ((StringBuilder)charSequence).append(Rlog.pii((String)TAG, (Object)string));
                    this.log(((StringBuilder)charSequence).toString());
                    stringBuilder.append(string);
                    stringBuilder.append(",");
                }
                arrobject[2] = stringBuilder.toString();
            }
            arrobject[3] = (int)var3_5;
            var2_4.addRow(arrobject);
        }
    }

    @UnsupportedAppUsage
    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[IccProvider] ");
        stringBuilder.append(string);
        Rlog.d((String)TAG, (String)stringBuilder.toString());
    }

    private String normalizeValue(String string) {
        String string2;
        int n = string.length();
        if (n == 0) {
            this.log("len of input String is 0");
            return string;
        }
        String string3 = string2 = string;
        if (string.charAt(0) == '\'') {
            string3 = string2;
            if (string.charAt(n - 1) == '\'') {
                string3 = string.substring(1, n - 1);
            }
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean updateIccRecordInEf(int n, String charSequence, String string, String string2, String string3, String string4, int n2) {
        boolean bl;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateIccRecordInEf: efType=0x");
        ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
        ((StringBuilder)object).append(", oldname=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)charSequence));
        ((StringBuilder)object).append(", oldnumber=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string));
        ((StringBuilder)object).append(", newname=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string2));
        ((StringBuilder)object).append(", newnumber=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string2));
        ((StringBuilder)object).append(", subscription=");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        boolean bl2 = false;
        boolean bl3 = false;
        try {
            object = IIccPhoneBook.Stub.asInterface(ServiceManager.getService((String)"simphonebook"));
            bl = bl3;
            if (object != null) {
                bl = object.updateAdnRecordsInEfBySearchForSubscriber(n2, n, (String)charSequence, string, string2, string3, string4);
            }
        }
        catch (SecurityException securityException) {
            this.log(securityException.toString());
            bl = bl2;
        }
        catch (RemoteException remoteException) {
            bl = bl3;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("updateIccRecordInEf: ");
        ((StringBuilder)charSequence).append(bl);
        this.log(((StringBuilder)charSequence).toString());
        return bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public int delete(Uri uri, String charSequence, String[] object) {
        int n;
        void var3_5;
        int n2 = URL_MATCHER.match(uri);
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Cannot insert into URL: ");
                        ((StringBuilder)charSequence).append((Object)uri);
                        throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
                    }
                    n = this.getRequestSubId(uri);
                    n2 = 28475;
                } else {
                    n = SubscriptionManager.getDefaultSubscriptionId();
                    n2 = 28475;
                }
            } else {
                n = this.getRequestSubId(uri);
                n2 = 28474;
            }
        } else {
            n = SubscriptionManager.getDefaultSubscriptionId();
            n2 = 28474;
        }
        this.log("delete");
        String[] arrstring = ((String)charSequence).split(" AND ");
        int n3 = arrstring.length;
        String string = null;
        Object var3_4 = null;
        charSequence = null;
        String string2 = null;
        while (--n3 >= 0) {
            CharSequence charSequence2;
            String string3 = arrstring[n3];
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("parsing '");
            ((StringBuilder)object2).append(string3);
            ((StringBuilder)object2).append("'");
            this.log(((StringBuilder)object2).toString());
            object2 = string3.split("=", 2);
            if (((String[])object2).length != 2) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("resolve: bad whereClause parameter: ");
                ((StringBuilder)object2).append(string3);
                Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
                continue;
            }
            String string4 = object2[0].trim();
            String string5 = ((String)object2[1]).trim();
            if (STR_TAG.equals(string4)) {
                string3 = this.normalizeValue(string5);
                object2 = var3_5;
                charSequence2 = charSequence;
            } else if (STR_NUMBER.equals(string4)) {
                object2 = this.normalizeValue(string5);
                string3 = string;
                charSequence2 = charSequence;
            } else if (STR_EMAILS.equals(string4)) {
                charSequence2 = null;
                string3 = string;
                object2 = var3_5;
            } else {
                string3 = string;
                object2 = var3_5;
                charSequence2 = charSequence;
                if (STR_PIN2.equals(string4)) {
                    string2 = this.normalizeValue(string5);
                    charSequence2 = charSequence;
                    object2 = var3_5;
                    string3 = string;
                }
            }
            string = string3;
            Object object3 = object2;
            charSequence = charSequence2;
        }
        if (n2 == 3 && TextUtils.isEmpty(string2)) {
            return 0;
        }
        if (!this.deleteIccRecordFromEf(n2, string, (String)var3_5, (String[])charSequence, string2, n)) {
            return 0;
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }

    public String getType(Uri uri) {
        switch (URL_MATCHER.match(uri)) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown URL ");
                stringBuilder.append((Object)uri);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
        }
        return "vnd.android.cursor.dir/sim-contact";
    }

    /*
     * Enabled aggressive block sorting
     */
    public Uri insert(Uri uri, ContentValues object) {
        int n;
        int n2;
        String string;
        this.log("insert");
        int n3 = URL_MATCHER.match(uri);
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot insert into URL: ");
                        ((StringBuilder)object).append((Object)uri);
                        throw new UnsupportedOperationException(((StringBuilder)object).toString());
                    }
                    n = this.getRequestSubId(uri);
                    string = object.getAsString(STR_PIN2);
                    n2 = 28475;
                } else {
                    n = SubscriptionManager.getDefaultSubscriptionId();
                    string = object.getAsString(STR_PIN2);
                    n2 = 28475;
                }
            } else {
                n = this.getRequestSubId(uri);
                string = null;
                n2 = 28474;
            }
        } else {
            n = SubscriptionManager.getDefaultSubscriptionId();
            string = null;
            n2 = 28474;
        }
        if (!this.addIccRecordToEf(n2, object.getAsString(STR_TAG), object.getAsString(STR_NUMBER), null, string, n)) {
            return null;
        }
        object = new StringBuilder("content://icc/");
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 == 4) {
                        ((StringBuilder)object).append("fdn/subId/");
                    }
                } else {
                    ((StringBuilder)object).append("fdn/");
                }
            } else {
                ((StringBuilder)object).append("adn/subId/");
            }
        } else {
            ((StringBuilder)object).append("adn/");
        }
        ((StringBuilder)object).append(0);
        object = Uri.parse((String)((StringBuilder)object).toString());
        this.getContext().getContentResolver().notifyChange(uri, null);
        return object;
    }

    public boolean onCreate() {
        this.mSubscriptionManager = SubscriptionManager.from((Context)this.getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] object, String string, String[] arrstring, String string2) {
        this.log("query");
        switch (URL_MATCHER.match(uri)) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown URL ");
                ((StringBuilder)object).append((Object)uri);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 7: {
                return this.loadAllSimContacts(28474);
            }
            case 6: {
                return this.loadFromEf(28489, this.getRequestSubId(uri));
            }
            case 5: {
                return this.loadFromEf(28489, SubscriptionManager.getDefaultSubscriptionId());
            }
            case 4: {
                return this.loadFromEf(28475, this.getRequestSubId(uri));
            }
            case 3: {
                return this.loadFromEf(28475, SubscriptionManager.getDefaultSubscriptionId());
            }
            case 2: {
                return this.loadFromEf(28474, this.getRequestSubId(uri));
            }
            case 1: 
        }
        return this.loadFromEf(28474, SubscriptionManager.getDefaultSubscriptionId());
    }

    /*
     * Enabled aggressive block sorting
     */
    public int update(Uri uri, ContentValues object, String string, String[] arrstring) {
        int n;
        this.log("update");
        int n2 = URL_MATCHER.match(uri);
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot insert into URL: ");
                        ((StringBuilder)object).append((Object)uri);
                        throw new UnsupportedOperationException(((StringBuilder)object).toString());
                    }
                    n = this.getRequestSubId(uri);
                    string = object.getAsString(STR_PIN2);
                    n2 = 28475;
                } else {
                    n = SubscriptionManager.getDefaultSubscriptionId();
                    string = object.getAsString(STR_PIN2);
                    n2 = 28475;
                }
            } else {
                n = this.getRequestSubId(uri);
                string = null;
                n2 = 28474;
            }
        } else {
            n = SubscriptionManager.getDefaultSubscriptionId();
            string = null;
            n2 = 28474;
        }
        if (!this.updateIccRecordInEf(n2, object.getAsString(STR_TAG), object.getAsString(STR_NUMBER), object.getAsString("newTag"), object.getAsString("newNumber"), string, n)) {
            return 0;
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }
}

