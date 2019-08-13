/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.ParcelUuid
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.UserHandle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.telecom.PhoneAccountHandle
 *  android.telecom.TelecomManager
 *  android.telephony.RadioAccessFamily
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.UiccAccessRule
 *  android.telephony.UiccSlotInfo
 *  android.telephony.euicc.EuiccManager
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController$Nt_ojdeqo4C2mbuwymYLvwgOLGo
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController$u5xE-urXR6ElZ50305_6guo20Fc
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0
 *  com.android.internal.telephony.ISetOpportunisticDataCallback
 *  com.android.internal.telephony.ISub
 *  com.android.internal.telephony.ISub$Stub
 *  com.android.internal.telephony.ITelephonyRegistry
 *  com.android.internal.telephony.ITelephonyRegistry$Stub
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 *  com.android.internal.telephony.TelephonyPermissions
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.ArrayUtils
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.RadioAccessFamily;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.telephony.UiccSlotInfo;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConfigurationManager;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.ProxyController;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.TelephonyPermissions;
import com.android.internal.telephony._$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8;
import com.android.internal.telephony._$$Lambda$SubscriptionController$CaRmFtDrpSD7YdPKEdfMgAOlVZY;
import com.android.internal.telephony._$$Lambda$SubscriptionController$KLGYC8GQvJwXrWqyIaejMh0cYio;
import com.android.internal.telephony._$$Lambda$SubscriptionController$Nt_ojdeqo4C2mbuwymYLvwgOLGo;
import com.android.internal.telephony._$$Lambda$SubscriptionController$VCQsMNqRHpN3RyoXYzh2YUwA2yc;
import com.android.internal.telephony._$$Lambda$SubscriptionController$u5xE_urXR6ElZ50305_6guo20Fc;
import com.android.internal.telephony._$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0;
import com.android.internal.telephony._$$Lambda$SubscriptionController$z1ZWZtk5wqutKrKUs4Unkis2MRg;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.util.ArrayUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubscriptionController
extends ISub.Stub {
    private static final boolean DBG = true;
    private static final boolean DBG_CACHE = false;
    private static final int DEPRECATED_SETTING = -1;
    private static final ParcelUuid INVALID_GROUP_UUID;
    private static final String LOG_TAG = "SubscriptionController";
    private static final Comparator<SubscriptionInfo> SUBSCRIPTION_INFO_COMPARATOR;
    private static final boolean VDBG;
    private static int mDefaultFallbackSubId;
    @UnsupportedAppUsage
    private static int mDefaultPhoneId;
    private static SubscriptionController sInstance;
    protected static Phone[] sPhones;
    private static Map<Integer, ArrayList<Integer>> sSlotIndexToSubIds;
    @UnsupportedAppUsage
    private int[] colorArr;
    private AppOpsManager mAppOps;
    private final List<SubscriptionInfo> mCacheActiveSubInfoList = new ArrayList<SubscriptionInfo>();
    private List<SubscriptionInfo> mCacheOpportunisticSubInfoList = new ArrayList<SubscriptionInfo>();
    @UnsupportedAppUsage
    protected Context mContext;
    private long mLastISubServiceRegTime;
    private final LocalLog mLocalLog = new LocalLog(200);
    @UnsupportedAppUsage
    protected final Object mLock = new Object();
    private Object mSubInfoListLock = new Object();
    protected TelephonyManager mTelephonyManager;
    protected UiccController mUiccController;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        VDBG = Rlog.isLoggable((String)LOG_TAG, (int)2);
        INVALID_GROUP_UUID = ParcelUuid.fromString((String)"00000000-0000-0000-0000-000000000000");
        SUBSCRIPTION_INFO_COMPARATOR = _$$Lambda$SubscriptionController$Nt_ojdeqo4C2mbuwymYLvwgOLGo.INSTANCE;
        sInstance = null;
        sSlotIndexToSubIds = new ConcurrentHashMap<Integer, ArrayList<Integer>>();
        mDefaultFallbackSubId = -1;
        mDefaultPhoneId = Integer.MAX_VALUE;
    }

    protected SubscriptionController(Context context) {
        this.init(context);
        this.migrateImsSettings();
    }

    private SubscriptionController(Phone phone) {
        this.mContext = phone.getContext();
        this.mAppOps = (AppOpsManager)this.mContext.getSystemService(AppOpsManager.class);
        if (ServiceManager.getService((String)"isub") == null) {
            ServiceManager.addService((String)"isub", (IBinder)this);
        }
        this.migrateImsSettings();
        this.clearSlotIndexForSubInfoRecords();
        this.logdl("[SubscriptionController] init by Phone");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addToSubIdList(int n, int n2, int n3) {
        synchronized (this) {
            ArrayList<Integer> arrayList;
            ArrayList<Integer> arrayList2 = arrayList = sSlotIndexToSubIds.get(n);
            if (arrayList == null) {
                arrayList2 = new ArrayList<Integer>();
                sSlotIndexToSubIds.put(n, arrayList2);
            }
            if (arrayList2.contains(n2)) {
                this.logdl("slotIndex, subId combo already exists in the map. Not adding it again.");
                return false;
            }
            if (this.isSubscriptionForRemoteSim(n3)) {
                arrayList2.add(n2);
            } else {
                arrayList2.clear();
                arrayList2.add(n2);
            }
            this.logdl("slotIndex, subId combo is added to the map.");
            return true;
        }
    }

    @UnsupportedAppUsage
    private void broadcastDefaultDataSubIdChanged(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[broadcastDefaultDataSubIdChanged] subId=");
        stringBuilder.append(n);
        this.logdl(stringBuilder.toString());
        stringBuilder = new Intent("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        stringBuilder.addFlags(553648128);
        stringBuilder.putExtra("subscription", n);
        stringBuilder.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", n);
        this.mContext.sendStickyBroadcastAsUser((Intent)stringBuilder, UserHandle.ALL);
    }

    private void broadcastDefaultSmsSubIdChanged(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[broadcastDefaultSmsSubIdChanged] subId=");
        stringBuilder.append(n);
        this.logdl(stringBuilder.toString());
        stringBuilder = new Intent("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED");
        stringBuilder.addFlags(553648128);
        stringBuilder.putExtra("subscription", n);
        stringBuilder.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", n);
        this.mContext.sendStickyBroadcastAsUser((Intent)stringBuilder, UserHandle.ALL);
    }

    private void broadcastSimInfoContentChanged() {
        Intent intent = new Intent("android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE");
        this.mContext.sendBroadcast(intent);
        intent = new Intent("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
        this.mContext.sendBroadcast(intent);
    }

    private boolean checkCarrierPrivilegeOnSubList(int[] object, String string) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n = ((int[])object).length;
        for (int i = 0; i < n; ++i) {
            int n2 = object[i];
            if (this.isActiveSubId(n2)) {
                if (this.mTelephonyManager.hasCarrierPrivileges(n2)) continue;
                return false;
            }
            hashSet.add(n2);
        }
        if (hashSet.isEmpty()) {
            return true;
        }
        long l = Binder.clearCallingIdentity();
        try {
            SubscriptionManager subscriptionManager = (SubscriptionManager)this.mContext.getSystemService("telephony_subscription_service");
            SubscriptionInfo subscriptionInfo2 = this.getSubInfo(this.getSelectionForSubIdList((int[])object), null);
            if (subscriptionInfo2 != null && subscriptionInfo2.size() == ((int[])object).length) {
                for (SubscriptionInfo subscriptionInfo2 : subscriptionInfo2) {
                    if (!hashSet.contains(subscriptionInfo2.getSubscriptionId())) continue;
                    if (subscriptionInfo2.isEmbedded() && subscriptionManager.canManageSubscription(subscriptionInfo2, string)) {
                        hashSet.remove(subscriptionInfo2.getSubscriptionId());
                        continue;
                    }
                    return false;
                }
                boolean bl = hashSet.isEmpty();
                return bl;
            }
            object = new IllegalArgumentException("Invalid subInfoList.");
            throw object;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    private void clearSlotIndexForSubInfoRecords() {
        if (this.mContext == null) {
            this.logel("[clearSlotIndexForSubInfoRecords] TelephonyManager or mContext is null");
            return;
        }
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("sim_id", Integer.valueOf(-1));
        this.mContext.getContentResolver().update(SubscriptionManager.CONTENT_URI, contentValues, null, null);
    }

    private int databaseUpdateHelper(ContentValues contentValues, int n, boolean bl) {
        List<SubscriptionInfo> list = this.getSubscriptionsInGroup(this.getGroupUuid(n), this.mContext.getOpPackageName());
        if (bl && list != null && list.size() != 0) {
            int[] arrn = new int[list.size()];
            for (n = 0; n < list.size(); ++n) {
                arrn[n] = list.get(n).getSubscriptionId();
            }
            return this.mContext.getContentResolver().update(SubscriptionManager.CONTENT_URI, contentValues, this.getSelectionForSubIdList(arrn), null);
        }
        return this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), contentValues, null, null);
    }

    private void deactivateSubscription(SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo.isEmbedded()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[deactivateSubscription] eSIM profile ");
            stringBuilder.append(subscriptionInfo.getSubscriptionId());
            this.logd(stringBuilder.toString());
            ((EuiccManager)this.mContext.getSystemService("euicc")).switchToSubscription(-1, PendingIntent.getService((Context)this.mContext, (int)0, (Intent)new Intent(), (int)0));
        }
    }

    private boolean enableEmbeddedSubscription(SubscriptionInfo subscriptionInfo, boolean bl) {
        this.enableSubscriptionOverEuiccManager(subscriptionInfo.getSubscriptionId(), bl, -1);
        return false;
    }

    private boolean enablePhysicalSubscription(SubscriptionInfo subscriptionInfo, boolean bl) {
        if (bl && subscriptionInfo.getSimSlotIndex() == -1) {
            boolean bl2;
            Object object = this.mTelephonyManager.getUiccSlotsInfo();
            if (object == null) {
                return false;
            }
            boolean bl3 = false;
            int n = 0;
            do {
                bl2 = bl3;
                if (n >= ((UiccSlotInfo[])object).length) break;
                if (SubscriptionController.isInactiveInsertedPSim(object[n], subscriptionInfo.getCardString())) {
                    this.enableSubscriptionOverEuiccManager(subscriptionInfo.getSubscriptionId(), bl, n);
                    bl2 = true;
                    break;
                }
                ++n;
            } while (true);
            if (!bl2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("enablePhysicalSubscription subId ");
                ((StringBuilder)object).append(subscriptionInfo.getSubscriptionId());
                ((StringBuilder)object).append(" is not inserted.");
                this.logdl(((StringBuilder)object).toString());
            }
            return false;
        }
        return this.mTelephonyManager.enableModemForSlot(subscriptionInfo.getSimSlotIndex(), bl);
    }

    private void enableSubscriptionOverEuiccManager(int n, boolean bl, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("enableSubscriptionOverEuiccManager");
        String string = bl ? " enable " : " disable ";
        stringBuilder.append(string);
        stringBuilder.append("subId ");
        stringBuilder.append(n);
        stringBuilder.append(" on slotIndex ");
        stringBuilder.append(n2);
        this.logdl(stringBuilder.toString());
        string = new Intent("android.telephony.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED");
        string.addFlags(268435456);
        string.putExtra("android.telephony.euicc.extra.SUBSCRIPTION_ID", n);
        string.putExtra("android.telephony.euicc.extra.ENABLE_SUBSCRIPTION", bl);
        if (n2 != -1) {
            string.putExtra("android.telephony.euicc.extra.PHYSICAL_SLOT_ID", n2);
        }
        this.mContext.startActivity((Intent)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void enforceCarrierPrivilegeOnInactiveSub(int n, String object, String string) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), (String)object);
        SubscriptionManager subscriptionManager = (SubscriptionManager)this.mContext.getSystemService("telephony_subscription_service");
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("_id=");
        ((StringBuilder)object2).append(n);
        object2 = this.getSubInfo(((StringBuilder)object2).toString(), null);
        try {
            if (!this.isActiveSubId(n) && object2 != null && object2.size() == 1 && subscriptionManager.canManageSubscription((SubscriptionInfo)object2.get(0), (String)object)) {
                return;
            }
            object = new SecurityException(string);
            throw object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SecurityException(string);
        }
    }

    @UnsupportedAppUsage
    private void enforceModifyPhoneState(String string) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE", string);
    }

    private void enforceReadPrivilegedPhoneState(String string) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", string);
    }

    private ArrayList<Integer> getActiveSubIdArrayList() {
        synchronized (this) {
            Object object = new ArrayList(sSlotIndexToSubIds.entrySet());
            Collections.sort(object, _$$Lambda$SubscriptionController$u5xE_urXR6ElZ50305_6guo20Fc.INSTANCE);
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.addAll((Collection)((Map.Entry)object.next()).getValue());
            }
            return arrayList;
        }
    }

    private SubscriptionInfo getActiveSubscriptionInfoForIccIdInternal(String string) {
        if (string == null) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            StringBuilder stringBuilder;
            Object object = this.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            if (object != null) {
                Iterator<SubscriptionInfo> iterator = object.iterator();
                while (iterator.hasNext()) {
                    stringBuilder = iterator.next();
                    if (!string.equals(stringBuilder.getIccId())) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[getActiveSubInfoUsingIccId]+ iccId=");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" subInfo=");
                    ((StringBuilder)object).append((Object)stringBuilder);
                    this.logd(((StringBuilder)object).toString());
                    return stringBuilder;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("[getActiveSubInfoUsingIccId]+ iccId=");
            stringBuilder.append(string);
            stringBuilder.append(" subList=");
            stringBuilder.append(object);
            stringBuilder.append(" subInfo=null");
            this.logd(stringBuilder.toString());
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    public static SubscriptionController getInstance() {
        if (sInstance == null) {
            Log.wtf((String)LOG_TAG, (String)"getInstance null");
        }
        return sInstance;
    }

    public static int getNameSourcePriority(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return 0;
                }
                return 2;
            }
            return 3;
        }
        return 1;
    }

    private String getOptionalStringFromCursor(Cursor cursor, String string, String string2) {
        int n = cursor.getColumnIndex(string);
        if (n != -1) {
            string2 = cursor.getString(n);
        }
        return string2;
    }

    private String getOwnerPackageOfSubGroup(ParcelUuid object) {
        Object var2_2 = null;
        if (object == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("group_uuid='");
        stringBuilder.append(object.toString());
        stringBuilder.append("'");
        object = this.getSubInfo(stringBuilder.toString(), null);
        object = ArrayUtils.isEmpty((Collection)object) ? var2_2 : ((SubscriptionInfo)object.get(0)).getGroupOwner();
        return object;
    }

    private int getPhysicalSlotIndex(boolean bl, int n) {
        int n2;
        UiccSlotInfo[] arruiccSlotInfo = this.mTelephonyManager.getUiccSlotsInfo();
        int n3 = this.getSlotIndex(n);
        int n4 = -1;
        boolean bl2 = SubscriptionManager.isValidSlotIndex((int)n3);
        n = 0;
        do {
            n2 = n4;
            if (n >= arruiccSlotInfo.length) break;
            if (bl2 && arruiccSlotInfo[n].getLogicalSlotIdx() == n3 || !bl2 && arruiccSlotInfo[n].getIsEuicc() && bl) {
                n2 = n;
                break;
            }
            ++n;
        } while (true);
        return n2;
    }

    private int getPhysicalSlotIndexFromLogicalSlotIndex(int n) {
        int n2;
        int n3 = -1;
        UiccSlotInfo[] arruiccSlotInfo = this.mTelephonyManager.getUiccSlotsInfo();
        int n4 = 0;
        do {
            n2 = n3;
            if (n4 >= arruiccSlotInfo.length) break;
            if (arruiccSlotInfo[n4].getLogicalSlotIdx() == n) {
                n2 = n4;
                break;
            }
            ++n4;
        } while (true);
        return n2;
    }

    private String getSelectionForSubIdList(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id");
        stringBuilder.append(" IN (");
        for (int i = 0; i < arrn.length - 1; ++i) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(arrn[i]);
            stringBuilder2.append(", ");
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder.append(arrn[arrn.length - 1]);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    private SubscriptionInfo getSubInfoRecord(Cursor object) {
        int n = object.getInt(object.getColumnIndexOrThrow("_id"));
        String string = object.getString(object.getColumnIndexOrThrow("icc_id"));
        int n2 = object.getInt(object.getColumnIndexOrThrow("sim_id"));
        String string2 = object.getString(object.getColumnIndexOrThrow("display_name"));
        String string3 = object.getString(object.getColumnIndexOrThrow("carrier_name"));
        int n3 = object.getInt(object.getColumnIndexOrThrow("name_source"));
        int n4 = object.getInt(object.getColumnIndexOrThrow("color"));
        String string4 = object.getString(object.getColumnIndexOrThrow("number"));
        int n5 = object.getInt(object.getColumnIndexOrThrow("data_roaming"));
        Bitmap bitmap = BitmapFactory.decodeResource((Resources)this.mContext.getResources(), (int)17302815);
        String string5 = object.getString(object.getColumnIndexOrThrow("mcc_string"));
        String string6 = object.getString(object.getColumnIndexOrThrow("mnc_string"));
        Object object2 = object.getString(object.getColumnIndexOrThrow("ehplmns"));
        Object object3 = object.getString(object.getColumnIndexOrThrow("hplmns"));
        object2 = object2 == null ? null : object2.split(",");
        object3 = object3 == null ? null : object3.split(",");
        String string7 = object.getString(object.getColumnIndexOrThrow("card_id"));
        String string8 = object.getString(object.getColumnIndexOrThrow("iso_country_code"));
        int n6 = this.mUiccController.convertToPublicCardId(string7);
        int n7 = object.getInt(object.getColumnIndexOrThrow("is_embedded"));
        boolean bl = false;
        boolean bl2 = n7 == 1;
        n7 = object.getInt(object.getColumnIndexOrThrow("carrier_id"));
        Object[] arrobject = bl2 ? UiccAccessRule.decodeRules((byte[])object.getBlob(object.getColumnIndexOrThrow("access_rules"))) : null;
        if (object.getInt(object.getColumnIndexOrThrow("is_opportunistic")) == 1) {
            bl = true;
        }
        String string9 = object.getString(object.getColumnIndexOrThrow("group_uuid"));
        int n8 = object.getInt(object.getColumnIndexOrThrow("profile_class"));
        int n9 = object.getInt(object.getColumnIndexOrThrow("subscription_type"));
        String string10 = this.getOptionalStringFromCursor((Cursor)object, "group_owner", null);
        if (VDBG) {
            String string11 = SubscriptionInfo.givePrintableIccid((String)string);
            object = SubscriptionInfo.givePrintableIccid((String)string7);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[getSubInfoRecord] id:");
            stringBuilder.append(n);
            stringBuilder.append(" iccid:");
            stringBuilder.append(string11);
            stringBuilder.append(" simSlotIndex:");
            stringBuilder.append(n2);
            stringBuilder.append(" carrierid:");
            stringBuilder.append(n7);
            stringBuilder.append(" displayName:");
            stringBuilder.append(string2);
            stringBuilder.append(" nameSource:");
            stringBuilder.append(n3);
            stringBuilder.append(" iconTint:");
            stringBuilder.append(n4);
            stringBuilder.append(" dataRoaming:");
            stringBuilder.append(n5);
            stringBuilder.append(" mcc:");
            stringBuilder.append(string5);
            stringBuilder.append(" mnc:");
            stringBuilder.append(string6);
            stringBuilder.append(" countIso:");
            stringBuilder.append(string8);
            stringBuilder.append(" isEmbedded:");
            stringBuilder.append(bl2);
            stringBuilder.append(" accessRules:");
            stringBuilder.append(Arrays.toString(arrobject));
            stringBuilder.append(" cardId:");
            stringBuilder.append((String)object);
            stringBuilder.append(" publicCardId:");
            stringBuilder.append(n6);
            stringBuilder.append(" isOpportunistic:");
            stringBuilder.append(bl);
            stringBuilder.append(" groupUUID:");
            stringBuilder.append(string9);
            stringBuilder.append(" profileClass:");
            stringBuilder.append(n8);
            stringBuilder.append(" subscriptionType: ");
            stringBuilder.append(n9);
            this.logd(stringBuilder.toString());
        }
        if (TextUtils.isEmpty((CharSequence)(object = this.mTelephonyManager.getLine1Number(n))) || object.equals(string4)) {
            object = string4;
        }
        object = new SubscriptionInfo(n, string, n2, (CharSequence)string2, (CharSequence)string3, n3, n4, (String)object, n5, bitmap, string5, string6, string8, bl2, (UiccAccessRule[])arrobject, string7, n6, bl, string9, false, n7, n8, n9, string10);
        object.setAssociatedPlmns(object2, object3);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<SubscriptionInfo> getSubscriptionInfoListFromCacheHelper(String list, List<SubscriptionInfo> object) {
        boolean bl;
        try {
            bl = TelephonyPermissions.checkReadPhoneState((Context)this.mContext, (int)-1, (int)Binder.getCallingPid(), (int)Binder.getCallingUid(), (String)((Object)list), (String)"getSubscriptionInfoList");
        }
        catch (SecurityException securityException) {
            bl = false;
        }
        Object object2 = this.mSubInfoListLock;
        synchronized (object2) {
            if (bl) {
                return new List<Object>((Collection<SubscriptionInfo>)object);
            }
            object = object.stream();
            _$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8 _$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8 = new _$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8(this, (String)((Object)list));
            return object.filter(_$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8).collect(Collectors.toList());
        }
    }

    private int getUnusedColor(String object) {
        object = this.getActiveSubscriptionInfoList((String)object);
        this.colorArr = this.mContext.getResources().getIntArray(17236103);
        int n = 0;
        if (object != null) {
            for (n = 0; n < this.colorArr.length; ++n) {
                int n2;
                for (n2 = 0; n2 < object.size() && this.colorArr[n] != ((SubscriptionInfo)object.get(n2)).getIconTint(); ++n2) {
                }
                if (n2 != object.size()) continue;
                return this.colorArr[n];
            }
            n = object.size() % this.colorArr.length;
        }
        return this.colorArr[n];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SubscriptionController init(Context object, CommandsInterface[] object2) {
        synchronized (SubscriptionController.class) {
            if (sInstance == null) {
                sInstance = object2 = new SubscriptionController((Context)object);
                return sInstance;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times!  sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Log.wtf((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SubscriptionController init(Phone object) {
        synchronized (SubscriptionController.class) {
            if (sInstance == null) {
                SubscriptionController subscriptionController;
                sInstance = subscriptionController = new SubscriptionController((Phone)object);
                return sInstance;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times!  sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Log.wtf((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    private boolean isActiveSubscriptionId(int n) {
        if (!SubscriptionManager.isValidSubscriptionId((int)n)) {
            return false;
        }
        ArrayList<Integer> arrayList = this.getActiveSubIdArrayList();
        if (arrayList.isEmpty()) {
            return false;
        }
        return arrayList.contains(new Integer(n));
    }

    private static boolean isInactiveInsertedPSim(UiccSlotInfo uiccSlotInfo, String string) {
        boolean bl = !uiccSlotInfo.getIsEuicc() && !uiccSlotInfo.getIsActive() && uiccSlotInfo.getCardStateInfo() == 2 && TextUtils.equals((CharSequence)uiccSlotInfo.getCardId(), (CharSequence)string);
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isSubInfoReady() {
        return SubscriptionInfoUpdater.isSubInfoInitialized();
    }

    private boolean isSubscriptionForRemoteSim(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    private boolean isSubscriptionVisible(int n) {
        block2 : {
            SubscriptionInfo subscriptionInfo;
            boolean bl;
            Iterator<SubscriptionInfo> iterator = this.mCacheOpportunisticSubInfoList.iterator();
            do {
                boolean bl2 = iterator.hasNext();
                bl = true;
                if (!bl2) break block2;
            } while ((subscriptionInfo = iterator.next()).getSubscriptionId() != n);
            if (subscriptionInfo.getGroupUuid() != null) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    static /* synthetic */ int lambda$canPackageManageGroup$4(SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId();
    }

    static /* synthetic */ int lambda$getActiveSubIdArrayList$2(Map.Entry entry, Map.Entry entry2) {
        return ((Integer)entry.getKey()).compareTo((Integer)entry2.getKey());
    }

    static /* synthetic */ boolean lambda$setSubscriptionEnabled$6(int n, SubscriptionInfo subscriptionInfo) {
        boolean bl = subscriptionInfo.getSubscriptionId() == n;
        return bl;
    }

    static /* synthetic */ int lambda$static$0(SubscriptionInfo subscriptionInfo, SubscriptionInfo subscriptionInfo2) {
        int n = subscriptionInfo.getSimSlotIndex() - subscriptionInfo2.getSimSlotIndex();
        if (n == 0) {
            return subscriptionInfo.getSubscriptionId() - subscriptionInfo2.getSubscriptionId();
        }
        return n;
    }

    @UnsupportedAppUsage
    private void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @UnsupportedAppUsage
    private void logdl(String string) {
        this.logd(string);
        this.mLocalLog.log(string);
    }

    @UnsupportedAppUsage
    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private void logel(String string) {
        this.loge(string);
        this.mLocalLog.log(string);
    }

    private void logv(String string) {
        Rlog.v((String)LOG_TAG, (String)string);
    }

    private void logvl(String string) {
        this.logv(string);
        this.mLocalLog.log(string);
    }

    private void migrateImsSettingHelper(String string, String string2) {
        block4 : {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int n = this.getDefaultVoiceSubId();
            if (n == -1) {
                return;
            }
            int n2 = Settings.Global.getInt((ContentResolver)contentResolver, (String)string);
            if (n2 == -1) break block4;
            try {
                SubscriptionController.setSubscriptionPropertyIntoContentResolver(n, string2, Integer.toString(n2), contentResolver);
                Settings.Global.putInt((ContentResolver)contentResolver, (String)string, (int)-1);
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                // empty catch block
            }
        }
    }

    private void notifyOpportunisticSubscriptionInfoChanged() {
        ITelephonyRegistry iTelephonyRegistry = ITelephonyRegistry.Stub.asInterface((IBinder)ServiceManager.getService((String)"telephony.registry"));
        try {
            this.logd("notifyOpptSubscriptionInfoChanged:");
            iTelephonyRegistry.notifyOpportunisticSubscriptionInfoChanged();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private static void printStackTrace(String arrstackTraceElement) {
        RuntimeException runtimeException = new RuntimeException();
        StringBuilder serializable2 = new StringBuilder();
        serializable2.append("StackTrace - ");
        serializable2.append((String)arrstackTraceElement);
        SubscriptionController.slogd(serializable2.toString());
        arrstackTraceElement = runtimeException.getStackTrace();
        boolean bl = true;
        for (StackTraceElement stackTraceElement : arrstackTraceElement) {
            if (bl) {
                bl = false;
                continue;
            }
            SubscriptionController.slogd(stackTraceElement.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean refreshCachedOpportunisticSubscriptionInfoList() {
        Object object = this.mSubInfoListLock;
        synchronized (object) {
            boolean bl;
            List<SubscriptionInfo> list = this.mCacheOpportunisticSubInfoList;
            Object object2 = this.getSubInfo("is_opportunistic=1 AND (sim_id>=0 OR is_embedded=1)", null);
            if (object2 != null) {
                object2.sort(SUBSCRIPTION_INFO_COMPARATOR);
            } else {
                object2 = new ArrayList<SubscriptionInfo>();
            }
            this.mCacheOpportunisticSubInfoList = object2;
            Iterator<SubscriptionInfo> iterator = this.mCacheOpportunisticSubInfoList.iterator();
            do {
                boolean bl2 = iterator.hasNext();
                bl = true;
                if (!bl2) break;
                SubscriptionInfo subscriptionInfo = iterator.next();
                if (!this.shouldDisableSubGroup(subscriptionInfo.getGroupUuid())) continue;
                subscriptionInfo.setGroupDisabled(true);
                if (!this.isActiveSubId(subscriptionInfo.getSubscriptionId()) || !this.isSubInfoReady()) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[refreshCachedOpportunisticSubscriptionInfoList] Deactivating grouped opportunistic subscription ");
                ((StringBuilder)object2).append(subscriptionInfo.getSubscriptionId());
                this.logd(((StringBuilder)object2).toString());
                this.deactivateSubscription(subscriptionInfo);
            } while (true);
            if (list.equals(this.mCacheOpportunisticSubInfoList)) return false;
            return bl;
        }
    }

    private int setCarrierText(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setCarrierText]+ text:");
        stringBuilder.append(string);
        stringBuilder.append(" subId:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        this.enforceModifyPhoneState("setCarrierText");
        long l = Binder.clearCallingIdentity();
        try {
            stringBuilder = new ContentValues(1);
            stringBuilder.put("carrier_name", string);
            n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)stringBuilder, null, null);
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    private void setDefaultFallbackSubId(int n, int n2) {
        if (n != Integer.MAX_VALUE) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("[setDefaultFallbackSubId] subId=");
            charSequence.append(n);
            charSequence.append(", subscriptionType=");
            charSequence.append(n2);
            this.logdl(charSequence.toString());
            int n3 = this.getDefaultSubId();
            if (this.isSubscriptionForRemoteSim(n2)) {
                mDefaultFallbackSubId = n;
                return;
            }
            if (SubscriptionManager.isValidSubscriptionId((int)n)) {
                n2 = this.getPhoneId(n);
                if (n2 >= 0 && (n2 < this.mTelephonyManager.getPhoneCount() || this.mTelephonyManager.getSimCount() == 1)) {
                    charSequence = new StringBuilder();
                    charSequence.append("[setDefaultFallbackSubId] set mDefaultFallbackSubId=");
                    charSequence.append(n);
                    this.logdl(charSequence.toString());
                    mDefaultFallbackSubId = n;
                    charSequence = this.mTelephonyManager.getSimOperatorNumericForPhone(n2);
                    MccTable.updateMccMncConfiguration(this.mContext, (String)charSequence);
                } else {
                    charSequence = new StringBuilder();
                    charSequence.append("[setDefaultFallbackSubId] not set invalid phoneId=");
                    charSequence.append(n2);
                    charSequence.append(" subId=");
                    charSequence.append(n);
                    this.logdl(charSequence.toString());
                }
            }
            if (n3 != this.getDefaultSubId()) {
                this.sendDefaultChangedBroadcast(this.getDefaultSubId());
            }
            return;
        }
        throw new RuntimeException("setDefaultSubId called with DEFAULT_SUB_ID");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int setSubscriptionPropertyIntoContentResolver(int var0, String var1_1, String var2_2, ContentResolver var3_3) {
        block24 : {
            var4_4 = new ContentValues();
            switch (var1_1.hashCode()) {
                case 1604840288: {
                    if (!var1_1.equals("wfc_ims_roaming_enabled")) break;
                    var5_5 = 18;
                    break block24;
                }
                case 1334635646: {
                    if (!var1_1.equals("wfc_ims_mode")) break;
                    var5_5 = 16;
                    break block24;
                }
                case 1288054979: {
                    if (!var1_1.equals("enable_channel_50_alerts")) break;
                    var5_5 = 9;
                    break block24;
                }
                case 1270593452: {
                    if (!var1_1.equals("enable_etws_test_alerts")) break;
                    var5_5 = 8;
                    break block24;
                }
                case 462555599: {
                    if (!var1_1.equals("alert_reminder_interval")) break;
                    var5_5 = 5;
                    break block24;
                }
                case 407275608: {
                    if (!var1_1.equals("enable_cmas_severe_threat_alerts")) break;
                    var5_5 = 1;
                    break block24;
                }
                case 240841894: {
                    if (!var1_1.equals("show_cmas_opt_out_dialog")) break;
                    var5_5 = 11;
                    break block24;
                }
                case 203677434: {
                    if (!var1_1.equals("enable_cmas_amber_alerts")) break;
                    var5_5 = 2;
                    break block24;
                }
                case 180938212: {
                    if (!var1_1.equals("wfc_ims_roaming_mode")) break;
                    var5_5 = 17;
                    break block24;
                }
                case -349439993: {
                    if (!var1_1.equals("alert_sound_duration")) break;
                    var5_5 = 4;
                    break block24;
                }
                case -420099376: {
                    if (!var1_1.equals("vt_ims_enabled")) break;
                    var5_5 = 14;
                    break block24;
                }
                case -461686719: {
                    if (!var1_1.equals("enable_emergency_alerts")) break;
                    var5_5 = 3;
                    break block24;
                }
                case -1218173306: {
                    if (!var1_1.equals("wfc_ims_enabled")) break;
                    var5_5 = 15;
                    break block24;
                }
                case -1390801311: {
                    if (!var1_1.equals("enable_alert_speech")) break;
                    var5_5 = 7;
                    break block24;
                }
                case -1433878403: {
                    if (!var1_1.equals("enable_cmas_test_alerts")) break;
                    var5_5 = 10;
                    break block24;
                }
                case -1555340190: {
                    if (!var1_1.equals("enable_cmas_extreme_threat_alerts")) break;
                    var5_5 = 0;
                    break block24;
                }
                case -1819373132: {
                    if (!var1_1.equals("is_opportunistic")) break;
                    var5_5 = 13;
                    break block24;
                }
                case -1950380197: {
                    if (!var1_1.equals("volte_vt_enabled")) break;
                    var5_5 = 12;
                    break block24;
                }
                case -2000412720: {
                    if (!var1_1.equals("enable_alert_vibrate")) break;
                    var5_5 = 6;
                    break block24;
                }
            }
            ** break;
lbl80: // 1 sources:
            var5_5 = -1;
        }
        switch (var5_5) {
            default: {
                SubscriptionController.slogd("Invalid column name");
                return var3_3.update(SubscriptionManager.getUriForSubscriptionId((int)var0), var4_4, null, null);
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
        }
        var4_4.put(var1_1, Integer.valueOf(Integer.parseInt(var2_2)));
        return var3_3.update(SubscriptionManager.getUriForSubscriptionId((int)var0), var4_4, null, null);
    }

    private boolean shouldDisableSubGroup(ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return false;
        }
        for (SubscriptionInfo subscriptionInfo : this.mCacheActiveSubInfoList) {
            if (subscriptionInfo.isOpportunistic() || !parcelUuid.equals((Object)subscriptionInfo.getGroupUuid())) continue;
            return false;
        }
        return true;
    }

    private static void slogd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @UnsupportedAppUsage
    private void updateAllDataConnectionTrackers() {
        int n = sPhones.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[updateAllDataConnectionTrackers] sPhones.length=");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        for (int i = 0; i < n; ++i) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[updateAllDataConnectionTrackers] phoneId=");
            stringBuilder.append(i);
            this.logd(stringBuilder.toString());
            sPhones[i].updateDataConnectionTracker();
        }
    }

    private void updateDefaultSubIdsIfNeeded(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[updateDefaultSubIdsIfNeeded] newDefault=");
        stringBuilder.append(n);
        stringBuilder.append(", subscriptionType=");
        stringBuilder.append(n2);
        this.logdl(stringBuilder.toString());
        if (!this.isActiveSubscriptionId(this.getDefaultSubId())) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[updateDefaultSubIdsIfNeeded] set mDefaultFallbackSubId=");
            stringBuilder.append(n);
            this.logdl(stringBuilder.toString());
            this.setDefaultFallbackSubId(n, n2);
        }
        if (!this.isActiveSubscriptionId(this.getDefaultSmsSubId())) {
            this.setDefaultSmsSubId(n);
        }
        if (!this.isActiveSubscriptionId(this.getDefaultDataSubId())) {
            this.setDefaultDataSubId(n);
        }
        if (!this.isActiveSubscriptionId(this.getDefaultVoiceSubId())) {
            this.setDefaultVoiceSubId(n);
        }
    }

    private void updateEnabledSubscriptionGlobalSetting(int n, int n2) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("enabled_subscription_for_slot");
        stringBuilder.append(n2);
        Settings.Global.putInt((ContentResolver)contentResolver, (String)stringBuilder.toString(), (int)n);
    }

    private int updateGroupOwner(ParcelUuid parcelUuid, String string) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("group_owner", string);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        string = SubscriptionManager.CONTENT_URI;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("group_uuid=\"");
        stringBuilder.append((Object)parcelUuid);
        stringBuilder.append("\"");
        return contentResolver.update((Uri)string, contentValues, stringBuilder.toString(), null);
    }

    private void updateModemStackEnabledGlobalSetting(boolean bl, int n) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        CharSequence charSequence = new StringBuilder();
        charSequence.append("modem_stack_enabled_for_slot");
        charSequence.append(n);
        charSequence = charSequence.toString();
        Settings.Global.putInt((ContentResolver)contentResolver, (String)charSequence, (int)bl);
    }

    @UnsupportedAppUsage
    private void validateSubId(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("validateSubId subId: ");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        if (SubscriptionManager.isValidSubscriptionId((int)n)) {
            if (n != Integer.MAX_VALUE) {
                return;
            }
            throw new RuntimeException("Default sub id passed as parameter");
        }
        throw new RuntimeException("Invalid sub id passed as parameter");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int addSubInfo(String var1_1, String var2_14, int var3_16, int var4_17) {
        block51 : {
            block55 : {
                block59 : {
                    block58 : {
                        block57 : {
                            block56 : {
                                block54 : {
                                    block53 : {
                                        block52 : {
                                            block50 : {
                                                var5_18 = var3_16;
                                                var6_19 = var1_1;
                                                if (!this.isSubscriptionForRemoteSim(var4_17)) {
                                                    var6_19 = SubscriptionInfo.givePrintableIccid((String)var1_1);
                                                }
                                                var7_20 /* !! */  = new StringBuilder();
                                                var7_20 /* !! */ .append("[addSubInfoRecord]+ iccid: ");
                                                var7_20 /* !! */ .append((String)var6_19);
                                                var7_20 /* !! */ .append(", slotIndex: ");
                                                var7_20 /* !! */ .append(var5_18);
                                                var7_20 /* !! */ .append(", subscriptionType: ");
                                                var7_20 /* !! */ .append(var4_17);
                                                this.logdl(var7_20 /* !! */ .toString());
                                                this.enforceModifyPhoneState("addSubInfo");
                                                var8_21 = Binder.clearCallingIdentity();
                                                if (var1_1 == null) {
                                                    this.logdl("[addSubInfo]- null iccId");
                                                    Binder.restoreCallingIdentity((long)var8_21);
                                                    return -1;
                                                }
                                                var10_22 = this.mContext.getContentResolver();
                                                var11_23 = this.isSubscriptionForRemoteSim(var4_17);
                                                if (!var11_23) break block50;
                                                try {
                                                    var6_19 = new StringBuilder();
                                                    var6_19.append("icc_id=?");
                                                    var6_19.append(" AND subscription_type=?");
                                                    var7_20 /* !! */  = var6_19.toString();
                                                    var6_19 = Integer.toString(var4_17);
                                                }
                                                catch (Throwable var1_2) {
                                                    break block51;
                                                }
                                                var6_19 = new String[]{var1_1, var6_19};
                                                ** GOTO lbl52
                                            }
                                            var6_19 = new StringBuilder();
                                            var6_19.append("icc_id=?");
                                            var6_19.append(" OR icc_id=?");
                                            var7_20 /* !! */  = var6_19.toString();
                                            var6_19 = new String[]{var1_1, IccUtils.getDecimalSubstring((String)var1_1)};
lbl52: // 2 sources:
                                            var12_24 = SubscriptionManager.CONTENT_URI;
                                            var13_25 = var8_21;
                                            var6_19 = var10_22.query((Uri)var12_24, new String[]{"_id", "sim_id", "name_source", "icc_id", "card_id"}, (String)var7_20 /* !! */ , var6_19, null);
                                            var11_23 = false;
                                            if (var6_19 == null) break block52;
                                            try {
                                                var15_26 = var6_19.moveToFirst();
                                                if (!var15_26) break block52;
                                                var16_27 = 0;
                                                break block53;
                                            }
                                            catch (Throwable var1_3) {
                                                ** GOTO lbl284
                                            }
                                        }
                                        var16_27 = 1;
                                    }
                                    var15_26 = this.isSubscriptionForRemoteSim(var4_17);
                                    if (!var15_26) ** GOTO lbl90
                                    if (var16_27 == 0) break block54;
                                    var5_18 = -1;
                                    try {
                                        var7_20 /* !! */  = this.insertEmptySubInfoRecord((String)var1_1, (String)var2_14, -1, var4_17);
                                        var2_14 = new StringBuilder();
                                        var2_14.append("[addSubInfoRecord] New record created: ");
                                        var2_14.append((Object)var7_20 /* !! */ );
                                        this.logd(var2_14.toString());
                                        var3_16 = 0;
                                        break block55;
                                    }
                                    catch (Throwable var1_4) {
                                        ** GOTO lbl284
                                    }
                                }
                                try {
                                    this.logdl("[addSubInfoRecord] Record already exists");
                                    var3_16 = 0;
                                    break block55;
lbl90: // 1 sources:
                                    if (var16_27 == 0) break block56;
                                    var11_23 = var15_26 = true;
                                    var7_20 /* !! */  = this.insertEmptySubInfoRecord((String)var1_1, var5_18);
                                    var11_23 = var15_26;
                                    var11_23 = var15_26;
                                    var2_14 = new StringBuilder();
                                    var11_23 = var15_26;
                                    var2_14.append("[addSubInfoRecord] New record created: ");
                                    var11_23 = var15_26;
                                    var2_14.append((Object)var7_20 /* !! */ );
                                    var11_23 = var15_26;
                                    this.logdl(var2_14.toString());
                                    var3_16 = 1;
                                    break block55;
                                }
                                catch (Throwable var1_5) {
                                    ** GOTO lbl284
                                }
                            }
                            var17_28 = var6_19.getInt(0);
                            var18_29 = var6_19.getInt(1);
                            var16_27 = 0;
                            var6_19.getInt(2);
                            var12_24 = var6_19.getString(3);
                            var7_20 /* !! */  = var6_19.getString(4);
                            var2_14 = new ContentValues();
                            if (var5_18 == var18_29) ** GOTO lbl122
                            try {
                                var2_14.put("sim_id", Integer.valueOf(var3_16));
lbl122: // 2 sources:
                                if (var12_24 == null || var12_24.length() >= var1_1.length() || !var12_24.equals(IccUtils.getDecimalSubstring((String)var1_1))) break block57;
                                var2_14.put("icc_id", (String)var1_1);
                            }
                            catch (Throwable var1_6) {
                                ** GOTO lbl284
                            }
                        }
                        var12_24 = this.mUiccController.getUiccCardForPhone(var5_18);
                        if (var12_24 == null) break block58;
                        var12_24 = var12_24.getCardId();
                        if (var12_24 == null || var12_24 == var7_20 /* !! */ ) break block58;
                        var2_14.put("card_id", (String)var12_24);
                    }
                    var3_16 = var2_14.size();
                    if (var3_16 <= 0) break block59;
                    var10_22.update(SubscriptionManager.getUriForSubscriptionId((int)var17_28), (ContentValues)var2_14, null, null);
                }
                this.logdl("[addSubInfoRecord] Record already exists");
                var3_16 = var16_27;
            }
            if (var6_19 == null) ** GOTO lbl151
            var6_19.close();
lbl151: // 2 sources:
            if (this.isSubscriptionForRemoteSim(var4_17)) {
                var6_19 = new String[]{var1_1, Integer.toString(var4_17)};
                var2_14 = "icc_id=? AND subscription_type=?";
                var1_1 = var6_19;
            } else {
                var1_1 = new String[]{String.valueOf(var5_18)};
                var2_14 = "sim_id=?";
            }
            var1_1 = var10_22.query(SubscriptionManager.CONTENT_URI, null, (String)var2_14, (String[])var1_1, null);
            if (var1_1 == null) ** GOTO lbl229
            try {
                if (var1_1.moveToFirst()) {
                    do {
                        if (this.addToSubIdList(var5_18, var17_28 = var1_1.getInt(var1_1.getColumnIndexOrThrow("_id")), var4_17)) {
                            var16_27 = this.getActiveSubInfoCountMax();
                            var18_29 = this.getDefaultSubId();
                            var2_14 = new StringBuilder();
                            var2_14.append("[addSubInfoRecord] sSlotIndexToSubIds.size=");
                            var2_14.append(SubscriptionController.sSlotIndexToSubIds.size());
                            var2_14.append(" slotIndex=");
                            var2_14.append(var5_18);
                            var2_14.append(" subId=");
                            var2_14.append(var17_28);
                            var2_14.append(" defaultSubId=");
                            var2_14.append(var18_29);
                            var2_14.append(" simCount=");
                            var2_14.append(var16_27);
                            this.logdl(var2_14.toString());
                            if (!this.isSubscriptionForRemoteSim(var4_17)) {
                                if (!SubscriptionManager.isValidSubscriptionId((int)var18_29) || var16_27 == 1) {
                                    var2_14 = new StringBuilder();
                                    var2_14.append("setting default fallback subid to ");
                                    var2_14.append(var17_28);
                                    this.logdl(var2_14.toString());
                                    this.setDefaultFallbackSubId(var17_28, var4_17);
                                }
                                if (var16_27 == 1) {
                                    var2_14 = new StringBuilder();
                                    var2_14.append("[addSubInfoRecord] one sim set defaults to subId=");
                                    var2_14.append(var17_28);
                                    this.logdl(var2_14.toString());
                                    this.setDefaultDataSubId(var17_28);
                                    this.setDefaultSmsSubId(var17_28);
                                    this.setDefaultVoiceSubId(var17_28);
                                }
                            } else {
                                this.updateDefaultSubIdsIfNeeded(var17_28, var4_17);
                            }
                        } else {
                            this.logdl("[addSubInfoRecord] current SubId is already known, IGNORE");
                        }
                        var2_14 = new StringBuilder();
                        var2_14.append("[addSubInfoRecord] hashmap(");
                        var2_14.append(var5_18);
                        var2_14.append(",");
                        var2_14.append(var17_28);
                        var2_14.append(")");
                        this.logdl(var2_14.toString());
                    } while (var11_23 = var1_1.moveToNext());
                }
                ** GOTO lbl229
            }
            catch (Throwable var2_15) {
                block62 : {
                    block60 : {
                        block61 : {
                            var1_1.close();
                            throw var2_15;
lbl229: // 2 sources:
                            if (var1_1 != null) {
                                var1_1.close();
                            }
                            this.refreshCachedActiveSubscriptionInfoList();
                            if (this.isSubscriptionForRemoteSim(var4_17)) {
                                this.notifySubscriptionInfoChanged();
                                break block60;
                            }
                            var4_17 = this.getSubIdUsingPhoneId(var5_18);
                            if (SubscriptionManager.isValidSubscriptionId((int)var4_17)) break block61;
                            var1_1 = new StringBuilder();
                            var1_1.append("[addSubInfoRecord]- getSubId failed invalid subId = ");
                            var1_1.append(var4_17);
                            this.logdl(var1_1.toString());
                            Binder.restoreCallingIdentity((long)var13_25);
                            return -1;
                        }
                        if (var3_16 == 0) ** GOTO lbl267
                        try {
                            var1_1 = this.mTelephonyManager.getSimOperatorName(var4_17);
                            if (TextUtils.isEmpty((CharSequence)var1_1)) {
                                var1_1 = new StringBuilder();
                                var1_1.append("CARD ");
                                var1_1.append(Integer.toString(var5_18 + 1));
                                var1_1 = var1_1.toString();
                            }
                            var2_14 = new ContentValues();
                            var2_14.put("display_name", (String)var1_1);
                            var10_22.update(SubscriptionManager.getUriForSubscriptionId((int)var4_17), (ContentValues)var2_14, null, null);
                            this.refreshCachedActiveSubscriptionInfoList();
                            var2_14 = new StringBuilder();
                            var2_14.append("[addSubInfoRecord] sim name = ");
                            var2_14.append((String)var1_1);
                            this.logdl(var2_14.toString());
lbl267: // 2 sources:
                            SubscriptionController.sPhones[var5_18].updateDataConnectionTracker();
                            var1_1 = new StringBuilder();
                            var1_1.append("[addSubInfoRecord]- info size=");
                            var1_1.append(SubscriptionController.sSlotIndexToSubIds.size());
                            this.logdl(var1_1.toString());
                        }
                        catch (Throwable var1_10) {}
                    }
                    Binder.restoreCallingIdentity((long)var13_25);
                    return 0;
                    catch (Throwable var1_7) {
                        break block62;
                    }
                    catch (Throwable var1_8) {
                        break block62;
                    }
                    catch (Throwable var1_9) {
                        // empty catch block
                    }
                }
                if (var6_19 == null) throw var1_1;
                var6_19.close();
                throw var1_1;
            }
            break block51;
            catch (Throwable var1_11) {}
            break block51;
            catch (Throwable var1_12) {
                // empty catch block
            }
        }
        Binder.restoreCallingIdentity((long)var8_21);
        throw var1_13;
    }

    public int addSubInfoRecord(String string, int n) {
        return this.addSubInfo(string, null, n, 0);
    }

    public void addSubscriptionsIntoGroup(int[] object, ParcelUuid parcelUuid, String string) {
        if (object != null && ((int[])object).length != 0) {
            if (parcelUuid != null && !parcelUuid.equals((Object)INVALID_GROUP_UUID)) {
                if (!this.getSubscriptionsInGroup(parcelUuid, string).isEmpty()) {
                    long l;
                    block8 : {
                        this.mAppOps.checkPackage(Binder.getCallingUid(), string);
                        if (!(this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0 || this.checkCarrierPrivilegeOnSubList((int[])object, string) && this.canPackageManageGroup(parcelUuid, string))) {
                            throw new SecurityException("Requires MODIFY_PHONE_STATE or carrier privilege permissions on subscriptions and the group.");
                        }
                        l = Binder.clearCallingIdentity();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("addSubscriptionsIntoGroup sub list ");
                        stringBuilder.append(Arrays.toString((int[])object));
                        stringBuilder.append(" into group ");
                        stringBuilder.append((Object)parcelUuid);
                        this.logdl(stringBuilder.toString());
                        stringBuilder = new ContentValues();
                        stringBuilder.put("group_uuid", parcelUuid.toString());
                        int n = this.mContext.getContentResolver().update(SubscriptionManager.CONTENT_URI, (ContentValues)stringBuilder, this.getSelectionForSubIdList((int[])object), null);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("addSubscriptionsIntoGroup update DB result: ");
                        ((StringBuilder)object).append(n);
                        this.logdl(((StringBuilder)object).toString());
                        if (n <= 0) break block8;
                        this.updateGroupOwner(parcelUuid, string);
                        this.refreshCachedActiveSubscriptionInfoList();
                        this.notifySubscriptionInfoChanged();
                        MultiSimSettingController.getInstance().notifySubscriptionGroupChanged(parcelUuid);
                    }
                    return;
                    finally {
                        Binder.restoreCallingIdentity((long)l);
                    }
                }
                throw new IllegalArgumentException("Cannot add subscriptions to a non-existent group!");
            }
            throw new IllegalArgumentException("Invalid groupUuid");
        }
        throw new IllegalArgumentException("Invalid subId list");
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void broadcastDefaultVoiceSubIdChanged(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[broadcastDefaultVoiceSubIdChanged] subId=");
        stringBuilder.append(n);
        this.logdl(stringBuilder.toString());
        stringBuilder = new Intent("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        stringBuilder.addFlags(553648128);
        stringBuilder.putExtra("subscription", n);
        stringBuilder.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", n);
        this.mContext.sendStickyBroadcastAsUser((Intent)stringBuilder, UserHandle.ALL);
    }

    public boolean canPackageManageGroup(ParcelUuid object, String string) {
        if (object != null) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                long l = Binder.clearCallingIdentity();
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("group_uuid='");
                    stringBuilder.append(object.toString());
                    stringBuilder.append("'");
                    object = this.getSubInfo(stringBuilder.toString(), null);
                    if (ArrayUtils.isEmpty((Collection)object)) {
                        return true;
                    }
                    if (string.equals(((SubscriptionInfo)object.get(0)).getGroupOwner())) {
                        return true;
                    }
                    return this.checkCarrierPrivilegeOnSubList(object.stream().mapToInt(_$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0.INSTANCE).toArray(), string);
                }
                finally {
                    Binder.restoreCallingIdentity((long)l);
                }
            }
            throw new IllegalArgumentException("Empty callingPackage");
        }
        throw new IllegalArgumentException("Invalid groupUuid");
    }

    public int clearSubInfo() {
        this.enforceModifyPhoneState("clearSubInfo");
        long l = Binder.clearCallingIdentity();
        try {
            int n = sSlotIndexToSubIds.size();
            if (n == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[clearSubInfo]- no simInfo size=");
                stringBuilder.append(n);
                this.logdl(stringBuilder.toString());
                return 0;
            }
            sSlotIndexToSubIds.clear();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[clearSubInfo]- clear size=");
            stringBuilder.append(n);
            this.logdl(stringBuilder.toString());
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void clearSubInfoRecord(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[clearSubInfoRecord]+ iccId: slotIndex:");
        stringBuilder.append(n);
        this.logdl(stringBuilder.toString());
        List<SubscriptionInfo> list = this.getSubInfoUsingSlotIndexPrivileged(n);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        stringBuilder = new ContentValues(1);
        stringBuilder.put("sim_id", Integer.valueOf(-1));
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                contentResolver.update(SubscriptionManager.getUriForSubscriptionId((int)list.get(i).getSubscriptionId()), (ContentValues)stringBuilder, null, null);
            }
        }
        this.refreshCachedActiveSubscriptionInfoList();
        sSlotIndexToSubIds.remove(n);
    }

    public ParcelUuid createSubscriptionGroup(int[] object, String charSequence) {
        if (object != null && ((int[])object).length != 0) {
            this.mAppOps.checkPackage(Binder.getCallingUid(), (String)charSequence);
            if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && !this.checkCarrierPrivilegeOnSubList((int[])object, (String)charSequence)) {
                throw new SecurityException("CreateSubscriptionGroup needs MODIFY_PHONE_STATE or carrier privilege permission on all specified subscriptions");
            }
            long l = Binder.clearCallingIdentity();
            try {
                ParcelUuid parcelUuid = new ParcelUuid(UUID.randomUUID());
                ContentValues contentValues = new ContentValues();
                contentValues.put("group_uuid", parcelUuid.toString());
                contentValues.put("group_owner", (String)charSequence);
                int n = this.mContext.getContentResolver().update(SubscriptionManager.CONTENT_URI, contentValues, this.getSelectionForSubIdList((int[])object), null);
                object = new StringBuilder();
                ((StringBuilder)object).append("createSubscriptionGroup update DB result: ");
                ((StringBuilder)object).append(n);
                this.logdl(((StringBuilder)object).toString());
                this.refreshCachedActiveSubscriptionInfoList();
                this.notifySubscriptionInfoChanged();
                MultiSimSettingController.getInstance().notifySubscriptionGroupChanged(parcelUuid);
                return parcelUuid;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid subIdList ");
        ((StringBuilder)charSequence).append(object);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Requires DUMP");
        long l = Binder.clearCallingIdentity();
        try {
            Object object;
            printWriter.println("SubscriptionController:");
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(" mLastISubServiceRegTime=");
            ((StringBuilder)object2).append(this.mLastISubServiceRegTime);
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultSubId=");
            ((StringBuilder)object2).append(this.getDefaultSubId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultDataSubId=");
            ((StringBuilder)object2).append(this.getDefaultDataSubId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultVoiceSubId=");
            ((StringBuilder)object2).append(this.getDefaultVoiceSubId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultSmsSubId=");
            ((StringBuilder)object2).append(this.getDefaultSmsSubId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultDataPhoneId=");
            ((StringBuilder)object2).append(SubscriptionManager.from((Context)this.mContext).getDefaultDataPhoneId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultVoicePhoneId=");
            ((StringBuilder)object2).append(SubscriptionManager.getDefaultVoicePhoneId());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" defaultSmsPhoneId=");
            ((StringBuilder)object2).append(SubscriptionManager.from((Context)this.mContext).getDefaultSmsPhoneId());
            printWriter.println(((StringBuilder)object2).toString());
            printWriter.flush();
            for (Map.Entry entry2 : sSlotIndexToSubIds.entrySet()) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" sSlotIndexToSubId[");
                ((StringBuilder)object).append(entry2.getKey());
                ((StringBuilder)object).append("]: subIds=");
                ((StringBuilder)object).append(entry2);
                printWriter.println(((StringBuilder)object).toString());
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
            object2 = this.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            if (object2 != null) {
                printWriter.println(" ActiveSubInfoList:");
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo)object2.next();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  ");
                    ((StringBuilder)object).append(subscriptionInfo.toString());
                    printWriter.println(((StringBuilder)object).toString());
                }
            } else {
                printWriter.println(" ActiveSubInfoList: is null");
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
            object2 = this.getAllSubInfoList(this.mContext.getOpPackageName());
            if (object2 != null) {
                printWriter.println(" AllSubInfoList:");
                object = object2.iterator();
                while (object.hasNext()) {
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo)object.next();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("  ");
                    ((StringBuilder)object2).append(subscriptionInfo.toString());
                    printWriter.println(((StringBuilder)object2).toString());
                }
            } else {
                printWriter.println(" AllSubInfoList: is null");
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
            this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
            printWriter.flush();
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String charSequence) {
        long l;
        List<Object> list;
        block5 : {
            if (!((EuiccManager)this.mContext.getSystemService("euicc")).isEnabled()) {
                this.logdl("[getAccessibleSubInfoList] Embedded subscriptions are disabled");
                return null;
            }
            this.mAppOps.checkPackage(Binder.getCallingUid(), (String)charSequence);
            l = Binder.clearCallingIdentity();
            list = this.getSubInfo("is_embedded=1", null);
            if (list == null) {
                this.logdl("[getAccessibleSubInfoList] No info returned");
                return null;
            }
            list = list.stream().filter(new _$$Lambda$SubscriptionController$z1ZWZtk5wqutKrKUs4Unkis2MRg(this, (String)charSequence)).sorted(SUBSCRIPTION_INFO_COMPARATOR).collect(Collectors.toList());
            if (!VDBG) break block5;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[getAccessibleSubInfoList] ");
            ((StringBuilder)charSequence).append(list.size());
            ((StringBuilder)charSequence).append(" infos returned");
            this.logdl(((StringBuilder)charSequence).toString());
        }
        return list;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int[] getActiveSubIdList(boolean bl) {
        int[] arrn;
        Object object = arrn = this.getActiveSubIdArrayList();
        if (bl) {
            object = arrn.stream().filter(new _$$Lambda$SubscriptionController$VCQsMNqRHpN3RyoXYzh2YUwA2yc(this)).collect(Collectors.toList());
        }
        arrn = new int[object.size()];
        int n = 0;
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            arrn[n] = (Integer)object2.next();
            ++n;
        }
        if (VDBG) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("[getActiveSubIdList] allSubs=");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" subIdArr.length=");
            ((StringBuilder)object2).append(arrn.length);
            this.logdl(((StringBuilder)object2).toString());
        }
        return arrn;
    }

    @UnsupportedAppUsage
    public int getActiveSubInfoCount(String object) {
        if ((object = this.getActiveSubscriptionInfoList((String)object)) == null) {
            if (VDBG) {
                this.logd("[getActiveSubInfoCount] records null");
            }
            return 0;
        }
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[getActiveSubInfoCount]- count: ");
            stringBuilder.append(object.size());
            this.logd(stringBuilder.toString());
        }
        return object.size();
    }

    public int getActiveSubInfoCountMax() {
        return this.mTelephonyManager.getSimCount();
    }

    @UnsupportedAppUsage
    public SubscriptionInfo getActiveSubscriptionInfo(int n, String charSequence) {
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)n, (String)charSequence, (String)"getActiveSubscriptionInfo")) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Object object = this.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            if (object != null) {
                Iterator<SubscriptionInfo> iterator = object.iterator();
                while (iterator.hasNext()) {
                    charSequence = iterator.next();
                    if (charSequence.getSubscriptionId() != n) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[getActiveSubscriptionInfo]+ subId=");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" subInfo=");
                    ((StringBuilder)object).append((Object)charSequence);
                    this.logd(((StringBuilder)object).toString());
                    return charSequence;
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[getActiveSubscriptionInfo]- subId=");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" subList=");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(" subInfo=null");
            this.logd(((StringBuilder)charSequence).toString());
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public SubscriptionInfo getActiveSubscriptionInfoForIccId(String string, String string2) {
        int n = (string = this.getActiveSubscriptionInfoForIccIdInternal(string)) != null ? string.getSubscriptionId() : -1;
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)n, (String)string2, (String)"getActiveSubscriptionInfoForIccId")) {
            return null;
        }
        return string;
    }

    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int n, String object) {
        Object object2 = PhoneFactory.getPhone(n);
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[getActiveSubscriptionInfoForSimSlotIndex] no phone, slotIndex=");
            ((StringBuilder)object).append(n);
            this.loge(((StringBuilder)object).toString());
            return null;
        }
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)((Phone)object2).getSubId(), (String)object, (String)"getActiveSubscriptionInfoForSimSlotIndex")) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            object = this.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            if (object != null) {
                boolean bl;
                object2 = object.iterator();
                while (bl = object2.hasNext()) {
                    object = (SubscriptionInfo)object2.next();
                    if (object.getSimSlotIndex() != n) continue;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("[getActiveSubscriptionInfoForSimSlotIndex]+ slotIndex=");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" subId=");
                    ((StringBuilder)object2).append(object);
                    this.logd(((StringBuilder)object2).toString());
                    return object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("[getActiveSubscriptionInfoForSimSlotIndex]+ slotIndex=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" subId=null");
                this.logd(((StringBuilder)object).toString());
            } else {
                this.logd("[getActiveSubscriptionInfoForSimSlotIndex]+ subList=null");
            }
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    public List<SubscriptionInfo> getActiveSubscriptionInfoList(String string) {
        return this.getSubscriptionInfoListFromCacheHelper(string, this.mCacheActiveSubInfoList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getAllSubInfoCount(String var1_1) {
        this.logd("[getAllSubInfoCount]+");
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)-1, (String)var1_1, (String)"getAllSubInfoCount")) {
            return 0;
        }
        var2_3 = Binder.clearCallingIdentity();
        var1_1 = this.mContext.getContentResolver().query(SubscriptionManager.CONTENT_URI, null, null, null, null);
        if (var1_1 == null) ** GOTO lbl-1000
        {
            catch (Throwable var1_2) {
                Binder.restoreCallingIdentity((long)var2_3);
                throw var1_2;
            }
        }
        var4_4 = var1_1.getCount();
        var5_5 = new StringBuilder();
        var5_5.append("[getAllSubInfoCount]- ");
        var5_5.append(var4_4);
        var5_5.append(" SUB(s) in DB");
        this.logd(var5_5.toString());
        Binder.restoreCallingIdentity((long)var2_3);
        return var4_4;
lbl-1000: // 1 sources:
        {
            if (var1_1 != null) {
                var1_1.close();
            }
            this.logd("[getAllSubInfoCount]- no SUB in DB");
        }
        Binder.restoreCallingIdentity((long)var2_3);
        return 0;
        finally {
            ** try [egrp 2[TRYBLOCK] [2 : 103->109)] { 
lbl30: // 1 sources:
            var1_1.close();
        }
    }

    public List<SubscriptionInfo> getAllSubInfoList(String object) {
        if (VDBG) {
            this.logd("[getAllSubInfoList]+");
        }
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)-1, (String)object, (String)"getAllSubInfoList")) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            object = this.getSubInfo(null, null);
            if (object != null) {
                if (VDBG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("[getAllSubInfoList]- ");
                    stringBuilder.append(object.size());
                    stringBuilder.append(" infos return");
                    this.logd(stringBuilder.toString());
                }
            } else if (VDBG) {
                this.logd("[getAllSubInfoList]- no info return");
            }
            return object;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String object) {
        if (TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)-1, (String)object, (String)"getAvailableSubscriptionInfoList")) {
            long l = Binder.clearCallingIdentity();
            object = "sim_id>=0 OR subscription_type=1";
            try {
                if (((EuiccManager)this.mContext.getSystemService("euicc")).isEnabled()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("sim_id>=0 OR subscription_type=1");
                    ((StringBuilder)object).append(" OR is_embedded=1");
                    object = ((StringBuilder)object).toString();
                }
                if ((object = this.getSubInfo((String)object, null)) != null) {
                    object.sort(SUBSCRIPTION_INFO_COMPARATOR);
                    if (VDBG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("[getAvailableSubInfoList]- ");
                        stringBuilder.append(object.size());
                        stringBuilder.append(" infos return");
                        this.logdl(stringBuilder.toString());
                    }
                } else {
                    this.logdl("[getAvailableSubInfoList]- no info return");
                }
                return object;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Need READ_PHONE_STATE to call  getAvailableSubscriptionInfoList");
    }

    public String getDataEnabledOverrideRules(int n) {
        return TextUtils.emptyIfNull((String)this.getSubscriptionProperty(n, "data_enabled_override_rules"));
    }

    @UnsupportedAppUsage
    public int getDefaultDataSubId() {
        int n = Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_data_call", (int)-1);
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[getDefaultDataSubId] subId= ");
            stringBuilder.append(n);
            this.logd(stringBuilder.toString());
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getDefaultSmsSubId() {
        int n = Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_sms", (int)-1);
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[getDefaultSmsSubId] subId=");
            stringBuilder.append(n);
            this.logd(stringBuilder.toString());
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getDefaultSubId() {
        int n;
        int n2;
        StringBuilder stringBuilder;
        if (this.mContext.getResources().getBoolean(17891570)) {
            n2 = n = this.getDefaultVoiceSubId();
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("[getDefaultSubId] isVoiceCapable subId=");
                stringBuilder.append(n);
                this.logdl(stringBuilder.toString());
                n2 = n;
            }
        } else {
            n2 = n = this.getDefaultDataSubId();
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("[getDefaultSubId] NOT VoiceCapable subId=");
                stringBuilder.append(n);
                this.logdl(stringBuilder.toString());
                n2 = n;
            }
        }
        n = n2;
        if (!this.isActiveSubId(n2)) {
            n = n2 = mDefaultFallbackSubId;
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("[getDefaultSubId] NOT active use fall back subId=");
                stringBuilder.append(n2);
                this.logdl(stringBuilder.toString());
                n = n2;
            }
        }
        if (VDBG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[getDefaultSubId]- value = ");
            stringBuilder.append(n);
            this.logv(stringBuilder.toString());
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getDefaultVoiceSubId() {
        int n = Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_voice_call", (int)-1);
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[getDefaultVoiceSubId] subId=");
            stringBuilder.append(n);
            SubscriptionController.slogd(stringBuilder.toString());
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getEnabledSubscriptionId(int n) {
        long l;
        block8 : {
            int n2;
            StringBuilder stringBuilder;
            ContentResolver contentResolver;
            block10 : {
                block9 : {
                    this.enforceReadPrivilegedPhoneState("getEnabledSubscriptionId");
                    l = Binder.clearCallingIdentity();
                    if (!SubscriptionManager.isValidPhoneId((int)n)) break block8;
                    n2 = this.getPhysicalSlotIndexFromLogicalSlotIndex(n);
                    if (n2 != -1) break block9;
                    Binder.restoreCallingIdentity((long)l);
                    return -1;
                }
                contentResolver = this.mContext.getContentResolver();
                stringBuilder = new StringBuilder();
                stringBuilder.append("modem_stack_enabled_for_slot");
                stringBuilder.append(n2);
                int n3 = Settings.Global.getInt((ContentResolver)contentResolver, (String)stringBuilder.toString(), (int)1);
                if (n3 == 1) break block10;
                Binder.restoreCallingIdentity((long)l);
                return -1;
            }
            try {
                contentResolver = this.mContext.getContentResolver();
                stringBuilder = new StringBuilder();
                stringBuilder.append("enabled_subscription_for_slot");
                stringBuilder.append(n2);
                n = n2 = Settings.Global.getInt((ContentResolver)contentResolver, (String)stringBuilder.toString());
                return n;
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                n = this.getSubIdUsingPhoneId(n);
                return n;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getEnabledSubscriptionId with invalid logicalSlotIndex ");
            stringBuilder.append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public ParcelUuid getGroupUuid(int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("_id=");
        ((StringBuilder)object).append(n);
        object = this.getSubInfo(((StringBuilder)object).toString(), null);
        object = object != null && object.size() != 0 ? ((SubscriptionInfo)object.get(0)).getGroupUuid() : null;
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String getImsiPrivileged(int var1_1) {
        var2_2 = this.mContext.getContentResolver().query(SubscriptionManager.CONTENT_URI, null, "_id=?", new String[]{String.valueOf(var1_1)}, null);
        var3_3 = null;
        if (var2_2 == null) ** GOTO lbl8
        try {
            block5 : {
                if (var2_2.moveToNext()) {
                    var3_3 = this.getOptionalStringFromCursor(var2_2, "imsi", null);
                }
                break block5;
lbl8: // 1 sources:
                this.logd("getImsiPrivileged: failed to retrieve imsi.");
            }
            if (var2_2 == null) return var3_3;
        }
        catch (Throwable var3_4) {
            try {
                throw var3_4;
            }
            catch (Throwable var4_5) {
                if (var2_2 == null) throw var4_5;
                SubscriptionController.$closeResource(var3_4, (AutoCloseable)var2_2);
                throw var4_5;
            }
        }
        SubscriptionController.$closeResource(null, (AutoCloseable)var2_2);
        return var3_3;
    }

    public List<SubscriptionInfo> getOpportunisticSubscriptions(String string) {
        return this.getSubscriptionInfoListFromCacheHelper(string, this.mCacheOpportunisticSubInfoList);
    }

    @UnsupportedAppUsage
    public int getPhoneId(int n) {
        Object object;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[getPhoneId] subId=");
            ((StringBuilder)object).append(n);
            SubscriptionController.printStackTrace(((StringBuilder)object).toString());
        }
        int n2 = n;
        if (n == Integer.MAX_VALUE) {
            n2 = this.getDefaultSubId();
            object = new StringBuilder();
            ((StringBuilder)object).append("[getPhoneId] asked for default subId=");
            ((StringBuilder)object).append(n2);
            this.logd(((StringBuilder)object).toString());
        }
        if (!SubscriptionManager.isValidSubscriptionId((int)n2)) {
            if (VDBG) {
                this.logdl("[getPhoneId]- invalid subId return=-1");
            }
            return -1;
        }
        if (sSlotIndexToSubIds.size() == 0) {
            n = mDefaultPhoneId;
            if (VDBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("[getPhoneId]- no sims, returning default phoneId=");
                ((StringBuilder)object).append(n);
                this.logdl(((StringBuilder)object).toString());
            }
            return n;
        }
        object = sSlotIndexToSubIds.entrySet().iterator();
        while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            n = (Integer)object2.getKey();
            if ((object2 = (ArrayList)object2.getValue()) == null || !((ArrayList)object2).contains(n2)) continue;
            if (VDBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("[getPhoneId]- found subId=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" phoneId=");
                ((StringBuilder)object).append(n);
                this.logdl(((StringBuilder)object).toString());
            }
            return n;
        }
        n = mDefaultPhoneId;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[getPhoneId]- subId=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" not found return default phoneId=");
            ((StringBuilder)object).append(n);
            this.logd(((StringBuilder)object).toString());
        }
        return n;
    }

    public int getPreferredDataSubscriptionId() {
        this.enforceReadPrivilegedPhoneState("getPreferredDataSubscriptionId");
        long l = Binder.clearCallingIdentity();
        try {
            int n = PhoneSwitcher.getInstance().getOpportunisticDataSubscriptionId();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int getSimStateForSlotIndex(int n) {
        Object object;
        Object object2;
        if (n < 0) {
            object = IccCardConstants.State.UNKNOWN;
            object2 = "invalid slotIndex";
        } else {
            object2 = null;
            try {
                object = PhoneFactory.getPhone(n);
                object2 = object;
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
            if (object2 == null) {
                object = IccCardConstants.State.UNKNOWN;
                object2 = "phone == null";
            } else if ((object2 = ((Phone)object2).getIccCard()) == null) {
                object = IccCardConstants.State.UNKNOWN;
                object2 = "icc == null";
            } else {
                object = ((IccCard)object2).getState();
                object2 = "";
            }
        }
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSimStateForSlotIndex: ");
            stringBuilder.append((String)object2);
            stringBuilder.append(" simState=");
            stringBuilder.append(object);
            stringBuilder.append(" ordinal=");
            stringBuilder.append(object.ordinal());
            stringBuilder.append(" slotIndex=");
            stringBuilder.append(n);
            this.logd(stringBuilder.toString());
        }
        return object.ordinal();
    }

    public int getSlotIndex(int n) {
        Object object;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[getSlotIndex] subId=");
            ((StringBuilder)object).append(n);
            SubscriptionController.printStackTrace(((StringBuilder)object).toString());
        }
        int n2 = n;
        if (n == Integer.MAX_VALUE) {
            n2 = this.getDefaultSubId();
        }
        if (!SubscriptionManager.isValidSubscriptionId((int)n2)) {
            this.logd("[getSlotIndex]- subId invalid");
            return -1;
        }
        if (sSlotIndexToSubIds.size() == 0) {
            this.logd("[getSlotIndex]- size == 0, return SIM_NOT_INSERTED instead");
            return -1;
        }
        object = sSlotIndexToSubIds.entrySet().iterator();
        while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            n = (Integer)object2.getKey();
            if ((object2 = (ArrayList)object2.getValue()) == null || !((ArrayList)object2).contains(n2)) continue;
            if (VDBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("[getSlotIndex]- return = ");
                ((StringBuilder)object).append(n);
                this.logv(((StringBuilder)object).toString());
            }
            return n;
        }
        this.logd("[getSlotIndex]- return fail");
        return -1;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public Map<Integer, ArrayList<Integer>> getSlotIndexToSubIdsMap() {
        return sSlotIndexToSubIds;
    }

    @Deprecated
    @UnsupportedAppUsage
    public int[] getSubId(int n) {
        int[] arrn;
        if (VDBG) {
            arrn = new StringBuilder();
            arrn.append("[getSubId]+ slotIndex=");
            arrn.append(n);
            SubscriptionController.printStackTrace(arrn.toString());
        }
        int n2 = n;
        if (n == Integer.MAX_VALUE) {
            n2 = n = this.getSlotIndex(this.getDefaultSubId());
            if (VDBG) {
                arrn = new StringBuilder();
                arrn.append("[getSubId] map default slotIndex=");
                arrn.append(n);
                this.logd(arrn.toString());
                n2 = n;
            }
        }
        if (!SubscriptionManager.isValidSlotIndex((int)n2) && n2 != -1) {
            arrn = new StringBuilder();
            arrn.append("[getSubId]- invalid slotIndex=");
            arrn.append(n2);
            this.logd(arrn.toString());
            return null;
        }
        if (sSlotIndexToSubIds.size() == 0) {
            if (VDBG) {
                arrn = new StringBuilder();
                arrn.append("[getSubId]- sSlotIndexToSubIds.size == 0, return null slotIndex=");
                arrn.append(n2);
                this.logd(arrn.toString());
            }
            return null;
        }
        Serializable serializable = sSlotIndexToSubIds.get(n2);
        if (serializable != null && ((ArrayList)serializable).size() > 0) {
            arrn = new int[((ArrayList)serializable).size()];
            for (n = 0; n < ((ArrayList)serializable).size(); ++n) {
                arrn[n] = ((ArrayList)serializable).get(n);
            }
            if (VDBG) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("[getSubId]- subIdArr=");
                ((StringBuilder)serializable).append(arrn);
                this.logd(((StringBuilder)serializable).toString());
            }
            return arrn;
        }
        arrn = new StringBuilder();
        arrn.append("[getSubId]- numSubIds == 0, return null slotIndex=");
        arrn.append(n2);
        this.logd(arrn.toString());
        return null;
    }

    @UnsupportedAppUsage
    public int getSubIdUsingPhoneId(int n) {
        int[] arrn = this.getSubId(n);
        if (arrn != null && arrn.length != 0) {
            return arrn[0];
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public List<SubscriptionInfo> getSubInfo(String var1_1, Object var2_3) {
        block9 : {
            block8 : {
                block10 : {
                    if (SubscriptionController.VDBG) {
                        var3_4 = new StringBuilder();
                        var3_4.append("selection:");
                        var3_4.append((String)var1_1);
                        var3_4.append(", querykey: ");
                        var3_4.append(var2_3);
                        this.logd(var3_4.toString());
                    }
                    var3_4 = null;
                    if (var2_3 != null) {
                        var3_4 = new String[]{var2_3.toString()};
                    }
                    var4_5 = null;
                    var2_3 = null;
                    var3_4 = this.mContext.getContentResolver().query(SubscriptionManager.CONTENT_URI, null, (String)var1_1, var3_4, null);
                    if (var3_4 != null) break block10;
                    {
                        this.logd("Query fail");
                        var2_3 = var4_5;
                        break block11;
                    }
                }
                var1_1 = var2_3;
                {
                    block11 : {
                        do {
                            var2_3 = var1_1;
                            ** try [egrp 0[TRYBLOCK] [0 : 99->159)] { 
lbl27: // 1 sources:
                            if (!var3_4.moveToNext()) break;
                            var4_5 = this.getSubInfoRecord((Cursor)var3_4);
                            var2_3 = var1_1;
                            if (var4_5 != null) {
                                var2_3 = var1_1;
                                if (var1_1 == null) {
                                    var2_3 = new ArrayList<SubscriptionInfo>();
                                }
                                var2_3.add(var4_5);
                            }
                            var1_1 = var2_3;
                        } while (true);
                    }
                    if (var3_4 == null) break block8;
                }
lbl41: // 1 sources:
                catch (Throwable var1_2) {
                    break block9;
                }
                var3_4.close();
            }
            return var2_3;
        }
        if (var3_4 != null) {
            var3_4.close();
        }
        throw var1_2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public List<SubscriptionInfo> getSubInfoUsingSlotIndexPrivileged(int n) {
        void var5_14;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[getSubInfoUsingSlotIndexPrivileged]+ slotIndex:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        int n2 = n;
        if (n == Integer.MAX_VALUE) {
            n2 = this.getSlotIndex(this.getDefaultSubId());
        }
        if (!SubscriptionManager.isValidSlotIndex((int)n2)) {
            this.logd("[getSubInfoUsingSlotIndexPrivileged]- invalid slotIndex");
            return null;
        }
        Cursor cursor = this.mContext.getContentResolver().query(SubscriptionManager.CONTENT_URI, null, "sim_id=?", new String[]{String.valueOf(n2)}, null);
        Object var5_6 = null;
        stringBuilder = null;
        if (cursor != null) {
            do {
                StringBuilder stringBuilder2 = stringBuilder;
                try {
                    void var5_13;
                    if (!cursor.moveToNext()) break;
                    SubscriptionInfo subscriptionInfo = this.getSubInfoRecord(cursor);
                    StringBuilder stringBuilder3 = stringBuilder;
                    if (subscriptionInfo != null) {
                        void var5_12;
                        StringBuilder stringBuilder4 = stringBuilder;
                        if (stringBuilder == null) {
                            ArrayList arrayList = new ArrayList();
                        }
                        var5_12.add(subscriptionInfo);
                    }
                    stringBuilder = var5_13;
                }
                catch (Throwable throwable) {
                    cursor.close();
                    throw throwable;
                }
            } while (true);
        }
        if (cursor != null) {
            cursor.close();
        }
        this.logd("[getSubInfoUsingSlotIndex]- null info return");
        return var5_14;
    }

    public SubscriptionInfo getSubscriptionInfo(int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("_id=");
        ((StringBuilder)object).append(n);
        object = this.getSubInfo(((StringBuilder)object).toString(), null);
        if (object != null && !object.isEmpty()) {
            return (SubscriptionInfo)object.get(0);
        }
        return null;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public List<SubscriptionInfo> getSubscriptionInfoListForEmbeddedSubscriptionUpdate(String[] object, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append("is_embedded");
        stringBuilder.append("=1");
        if (bl) {
            stringBuilder.append(" AND ");
            stringBuilder.append("is_removable");
            stringBuilder.append("=1");
        }
        stringBuilder.append(") OR ");
        stringBuilder.append("icc_id");
        stringBuilder.append(" IN (");
        for (int i = 0; i < ((String[])object).length; ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"");
            stringBuilder.append(object[i]);
            stringBuilder.append("\"");
        }
        stringBuilder.append(")");
        object = this.getSubInfo(stringBuilder.toString(), null);
        if (object == null) {
            return Collections.emptyList();
        }
        return object;
    }

    /*
     * Exception decompiling
     */
    public String getSubscriptionProperty(int var1_1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [29[CASE]], but top level block is 1[TRYBLOCK]
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

    public String getSubscriptionProperty(int n, String string, String string2) {
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)n, (String)string2, (String)"getSubscriptionProperty")) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            string = this.getSubscriptionProperty(n, string);
            return string;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid object, String string) {
        long l;
        block5 : {
            l = Binder.clearCallingIdentity();
            List<SubscriptionInfo> list = this.getAllSubInfoList(this.mContext.getOpPackageName());
            if (object == null || list == null) break block5;
            boolean bl = list.isEmpty();
            if (bl) break block5;
            Binder.restoreCallingIdentity((long)l);
            return list.stream().filter(new _$$Lambda$SubscriptionController$CaRmFtDrpSD7YdPKEdfMgAOlVZY(this, (ParcelUuid)object, string)).collect(Collectors.toList());
        }
        try {
            object = new ArrayList();
            return object;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    protected void init(Context context) {
        this.mContext = context;
        this.mTelephonyManager = TelephonyManager.from((Context)this.mContext);
        try {
            this.mUiccController = UiccController.getInstance();
        }
        catch (RuntimeException runtimeException) {
            throw new RuntimeException("UiccController has to be initialised before SubscriptionController init");
        }
        this.mAppOps = (AppOpsManager)this.mContext.getSystemService("appops");
        if (ServiceManager.getService((String)"isub") == null) {
            ServiceManager.addService((String)"isub", (IBinder)this);
            this.mLastISubServiceRegTime = System.currentTimeMillis();
        }
        this.clearSlotIndexForSubInfoRecords();
        this.logdl("[SubscriptionController] init by Context");
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public Uri insertEmptySubInfoRecord(String string, int n) {
        return this.insertEmptySubInfoRecord(string, null, n, 0);
    }

    Uri insertEmptySubInfoRecord(String object, String string, int n, int n2) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("icc_id", (String)object);
        contentValues.put("color", Integer.valueOf(this.getUnusedColor(this.mContext.getOpPackageName())));
        contentValues.put("sim_id", Integer.valueOf(n));
        contentValues.put("carrier_name", "");
        contentValues.put("card_id", (String)object);
        contentValues.put("subscription_type", Integer.valueOf(n2));
        if (this.isSubscriptionForRemoteSim(n2)) {
            contentValues.put("display_name", string);
        } else {
            object = this.mUiccController.getUiccCardForPhone(n);
            if (object != null && (object = ((UiccCard)object).getCardId()) != null) {
                contentValues.put("card_id", (String)object);
            }
        }
        object = contentResolver.insert(SubscriptionManager.CONTENT_URI, contentValues);
        this.refreshCachedActiveSubscriptionInfoList();
        return object;
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean isActiveSubId(int n) {
        boolean bl = SubscriptionManager.isValidSubscriptionId((int)n) && this.getActiveSubIdArrayList().contains(n);
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[isActiveSubId]- ");
            stringBuilder.append(bl);
            this.logdl(stringBuilder.toString());
        }
        return bl;
    }

    public boolean isActiveSubId(int n, String string) {
        if (TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)n, (String)string, (String)"isActiveSubId")) {
            long l = Binder.clearCallingIdentity();
            try {
                boolean bl = this.isActiveSubId(n);
                return bl;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Requires READ_PHONE_STATE permission.");
    }

    public boolean isOpportunistic(int n) {
        SubscriptionInfo subscriptionInfo = this.getActiveSubscriptionInfo(n, this.mContext.getOpPackageName());
        boolean bl = subscriptionInfo != null && subscriptionInfo.isOpportunistic();
        return bl;
    }

    public boolean isSubscriptionEnabled(int n) {
        block5 : {
            long l;
            block6 : {
                boolean bl;
                block8 : {
                    boolean bl2;
                    block7 : {
                        Object object;
                        this.enforceReadPrivilegedPhoneState("isSubscriptionEnabled");
                        l = Binder.clearCallingIdentity();
                        try {
                            if (!SubscriptionManager.isUsableSubscriptionId((int)n)) break block5;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("_id=");
                            ((StringBuilder)object).append(n);
                            object = this.getSubInfo(((StringBuilder)object).toString(), null);
                            bl = false;
                            if (object == null) break block6;
                        }
                        catch (Throwable throwable) {
                            Binder.restoreCallingIdentity((long)l);
                            throw throwable;
                        }
                        if (object.isEmpty()) break block6;
                        if (!((SubscriptionInfo)object.get(0)).isEmbedded()) break block7;
                        bl = this.isActiveSubId(n);
                        Binder.restoreCallingIdentity((long)l);
                        return bl;
                    }
                    if (!this.isActiveSubId(n) || !(bl2 = PhoneConfigurationManager.getInstance().getPhoneStatus(PhoneFactory.getPhone(this.getPhoneId(n))))) break block8;
                    bl = true;
                }
                Binder.restoreCallingIdentity((long)l);
                return bl;
            }
            Binder.restoreCallingIdentity((long)l);
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isSubscriptionEnabled not usable subId ");
        stringBuilder.append(n);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
        throw illegalArgumentException;
    }

    public /* synthetic */ boolean lambda$getAccessibleSubscriptionInfoList$1$SubscriptionController(String string, SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.canManageSubscription(this.mContext, string);
    }

    public /* synthetic */ boolean lambda$getActiveSubIdList$3$SubscriptionController(Integer n) {
        return this.isSubscriptionVisible(n);
    }

    public /* synthetic */ boolean lambda$getSubscriptionInfoListFromCacheHelper$7$SubscriptionController(String string, SubscriptionInfo subscriptionInfo) {
        try {
            boolean bl = TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)subscriptionInfo.getSubscriptionId(), (String)string, (String)"getSubscriptionInfoList");
            return bl;
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    public /* synthetic */ boolean lambda$getSubscriptionsInGroup$5$SubscriptionController(ParcelUuid parcelUuid, String string, SubscriptionInfo subscriptionInfo) {
        boolean bl;
        block5 : {
            block4 : {
                bl = parcelUuid.equals((Object)subscriptionInfo.getGroupUuid());
                boolean bl2 = false;
                if (!bl) {
                    return false;
                }
                int n = subscriptionInfo.getSubscriptionId();
                if (TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.mContext, (int)n, (String)string, (String)"getSubscriptionsInGroup")) break block4;
                bl = bl2;
                if (!subscriptionInfo.isEmbedded()) break block5;
                bl = bl2;
                if (!subscriptionInfo.canManageSubscription(this.mContext, string)) break block5;
            }
            bl = true;
        }
        return bl;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void migrateImsSettings() {
        this.migrateImsSettingHelper("volte_vt_enabled", "volte_vt_enabled");
        this.migrateImsSettingHelper("vt_ims_enabled", "vt_ims_enabled");
        this.migrateImsSettingHelper("wfc_ims_enabled", "wfc_ims_enabled");
        this.migrateImsSettingHelper("wfc_ims_mode", "wfc_ims_mode");
        this.migrateImsSettingHelper("wfc_ims_roaming_mode", "wfc_ims_roaming_mode");
        this.migrateImsSettingHelper("wfc_ims_roaming_enabled", "wfc_ims_roaming_enabled");
    }

    public void notifySubInfoReady() {
        this.sendDefaultChangedBroadcast(SubscriptionManager.getDefaultSubscriptionId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void notifySubscriptionInfoChanged() {
        ArrayList<SubscriptionInfo> arrayList;
        Object object = ITelephonyRegistry.Stub.asInterface((IBinder)ServiceManager.getService((String)"telephony.registry"));
        try {
            this.logd("notifySubscriptionInfoChanged:");
            object.notifySubscriptionInfoChanged();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.broadcastSimInfoContentChanged();
        MultiSimSettingController.getInstance().notifySubscriptionInfoChanged();
        TelephonyMetrics telephonyMetrics = TelephonyMetrics.getInstance();
        object = this.mSubInfoListLock;
        synchronized (object) {
            arrayList = new ArrayList<SubscriptionInfo>(this.mCacheActiveSubInfoList);
        }
        telephonyMetrics.updateActiveSubscriptionInfoList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public void refreshCachedActiveSubscriptionInfoList() {
        Object object = this.mSubInfoListLock;
        // MONITORENTER : object
        List<SubscriptionInfo> list = this.getSubInfo("sim_id>=0 OR subscription_type=1", null);
        if (list != null) {
            if (this.mCacheActiveSubInfoList.size() != list.size() || !this.mCacheActiveSubInfoList.containsAll(list)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Active subscription info list changed. ");
                stringBuilder.append(list);
                this.logdl(stringBuilder.toString());
            }
            this.mCacheActiveSubInfoList.clear();
            list.sort(SUBSCRIPTION_INFO_COMPARATOR);
            this.mCacheActiveSubInfoList.addAll(list);
        } else {
            this.logd("activeSubscriptionInfoList is null.");
            this.mCacheActiveSubInfoList.clear();
        }
        boolean bl = this.refreshCachedOpportunisticSubscriptionInfoList();
        // MONITOREXIT : object
        if (!bl) return;
        this.notifyOpportunisticSubscriptionInfoChanged();
    }

    public int removeSubInfo(String object, int n) {
        int n2;
        int n3;
        List<Integer> list;
        block16 : {
            this.enforceModifyPhoneState("removeSubInfo");
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("[removeSubInfo] uniqueId: ");
            ((StringBuilder)((Object)list)).append((String)object);
            ((StringBuilder)((Object)list)).append(", subscriptionType: ");
            ((StringBuilder)((Object)list)).append(n);
            this.logd(((StringBuilder)((Object)list)).toString());
            for (SubscriptionInfo subscriptionInfo : this.mCacheActiveSubInfoList) {
                if (subscriptionInfo.getSubscriptionType() != n || !subscriptionInfo.getIccId().equalsIgnoreCase((String)object)) continue;
                n2 = subscriptionInfo.getSubscriptionId();
                n3 = subscriptionInfo.getSimSlotIndex();
                break block16;
            }
            n3 = -1;
            n2 = -1;
        }
        if (n2 == -1) {
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("Invalid subscription details: subscriptionType = ");
            ((StringBuilder)((Object)list)).append(n);
            ((StringBuilder)((Object)list)).append(", uniqueId = ");
            ((StringBuilder)((Object)list)).append((String)object);
            this.logd(((StringBuilder)((Object)list)).toString());
            return -1;
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("removing the subid : ");
        ((StringBuilder)((Object)list)).append(n2);
        this.logd(((StringBuilder)((Object)list)).toString());
        long l = Binder.clearCallingIdentity();
        try {
            int n4 = this.mContext.getContentResolver().delete(SubscriptionManager.CONTENT_URI, "_id=? AND subscription_type=?", new String[]{Integer.toString(n2), Integer.toString(n)});
            if (n4 != 1) {
                list = new List<Integer>();
                ((StringBuilder)((Object)list)).append("found NO subscription to remove with subscriptionType = ");
                ((StringBuilder)((Object)list)).append(n);
                ((StringBuilder)((Object)list)).append(", uniqueId = ");
                ((StringBuilder)((Object)list)).append((String)object);
                this.logd(((StringBuilder)((Object)list)).toString());
                return -1;
            }
            this.refreshCachedActiveSubscriptionInfoList();
            list = sSlotIndexToSubIds.get(n3);
            if (list == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sSlotIndexToSubIds has no entry for slotIndex = ");
                ((StringBuilder)object).append(n3);
                this.loge(((StringBuilder)object).toString());
            } else if (((ArrayList)list).contains(n2)) {
                object = new Integer(n2);
                ((ArrayList)list).remove(object);
                if (((ArrayList)list).isEmpty()) {
                    sSlotIndexToSubIds.remove(n3);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("sSlotIndexToSubIds has no subid: ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(", in index: ");
                ((StringBuilder)object).append(n3);
                this.loge(((StringBuilder)object).toString());
            }
            object = null;
            list = this.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            if (!list.isEmpty()) {
                object = (SubscriptionInfo)list.get(0);
            }
            this.updateDefaultSubIdsIfNeeded(object.getSubscriptionId(), object.getSubscriptionType());
            this.notifySubscriptionInfoChanged();
            return n4;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void removeSubscriptionsFromGroup(int[] object, ParcelUuid parcelUuid, String object2) {
        if (object != null && ((Object)object).length != 0) {
            long l;
            block7 : {
                this.mAppOps.checkPackage(Binder.getCallingUid(), (String)object2);
                if (!(this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0 || this.checkCarrierPrivilegeOnSubList((int[])object, (String)object2) && this.canPackageManageGroup(parcelUuid, (String)object2))) {
                    throw new SecurityException("removeSubscriptionsFromGroup needs MODIFY_PHONE_STATE or carrier privilege permission on all specified subscriptions");
                }
                l = Binder.clearCallingIdentity();
                for (SubscriptionInfo contentValues2 : this.getSubInfo(this.getSelectionForSubIdList((int[])object), null)) {
                    if (parcelUuid.equals((Object)contentValues2.getGroupUuid())) continue;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Subscription ");
                    ((StringBuilder)object).append(contentValues2.getSubscriptionId());
                    ((StringBuilder)object).append(" doesn't belong to group ");
                    ((StringBuilder)object).append((Object)parcelUuid);
                    object2 = new IllegalArgumentException(((StringBuilder)object).toString());
                    throw object2;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("group_uuid", (String)null);
                contentValues.put("group_owner", (String)null);
                int n = this.mContext.getContentResolver().update(SubscriptionManager.CONTENT_URI, contentValues, this.getSelectionForSubIdList((int[])object), null);
                object = new StringBuilder();
                ((StringBuilder)object).append("removeSubscriptionsFromGroup update DB result: ");
                ((StringBuilder)object).append(n);
                this.logdl(((StringBuilder)object).toString());
                if (n <= 0) break block7;
                this.updateGroupOwner(parcelUuid, (String)object2);
                this.refreshCachedActiveSubscriptionInfoList();
                this.notifySubscriptionInfoChanged();
            }
            return;
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
    }

    public void requestEmbeddedSubscriptionInfoListRefresh(int n) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS", "requestEmbeddedSubscriptionInfoListRefresh");
        long l = Binder.clearCallingIdentity();
        try {
            PhoneFactory.requestEmbeddedSubscriptionInfoListRefresh(n, null);
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void requestEmbeddedSubscriptionInfoListRefresh(int n, Runnable runnable) {
        PhoneFactory.requestEmbeddedSubscriptionInfoListRefresh(n, runnable);
    }

    public void requestEmbeddedSubscriptionInfoListRefresh(Runnable runnable) {
        PhoneFactory.requestEmbeddedSubscriptionInfoListRefresh(this.mTelephonyManager.getCardIdForDefaultEuicc(), runnable);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void resetStaticMembers() {
        mDefaultFallbackSubId = -1;
        mDefaultPhoneId = Integer.MAX_VALUE;
    }

    public void sendDefaultChangedBroadcast(int n) {
        int n2 = SubscriptionManager.getPhoneId((int)n);
        Intent intent = new Intent("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED");
        intent.addFlags(553648128);
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)n2, (int)n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[sendDefaultChangedBroadcast] broadcast default subId changed phoneId=");
        stringBuilder.append(n2);
        stringBuilder.append(" subId=");
        stringBuilder.append(n);
        this.logdl(stringBuilder.toString());
        this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }

    public boolean setAlwaysAllowMmsData(int n, boolean bl) {
        long l;
        Object object;
        block4 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("[setAlwaysAllowMmsData]+ alwaysAllow:");
            ((StringBuilder)object).append(bl);
            ((StringBuilder)object).append(" subId:");
            ((StringBuilder)object).append(n);
            this.logd(((StringBuilder)object).toString());
            this.enforceModifyPhoneState("setAlwaysAllowMmsData");
            l = Binder.clearCallingIdentity();
            this.validateSubId(n);
            object = PhoneFactory.getPhone(this.getPhoneId(n));
            if (object != null) break block4;
            Binder.restoreCallingIdentity((long)l);
            return false;
        }
        try {
            bl = ((Phone)object).getDataEnabledSettings().setAlwaysAllowMmsData(bl);
            return bl;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void setAssociatedPlmns(String[] object, String[] object2, int n) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("[setAssociatedPlmns]+ subId:");
        charSequence.append(n);
        this.logd(charSequence.toString());
        this.validateSubId(n);
        int n2 = this.getPhoneId(n);
        if (n2 >= 0 && n2 < this.mTelephonyManager.getPhoneCount()) {
            charSequence = "";
            object = object == null ? "" : String.join((CharSequence)",", (CharSequence[])object);
            object2 = object2 == null ? charSequence : String.join((CharSequence)",", object2);
            charSequence = new ContentValues(2);
            charSequence.put("ehplmns", (String)object);
            charSequence.put("hplmns", (String)object2);
            n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)charSequence, null, null);
            this.refreshCachedActiveSubscriptionInfoList();
            object = new StringBuilder();
            ((StringBuilder)object).append("[setAssociatedPlmns]- update result :");
            ((StringBuilder)object).append(n);
            this.logd(((StringBuilder)object).toString());
            this.notifySubscriptionInfoChanged();
            return;
        }
        this.logd("[setAssociatedPlmns]- fail");
    }

    public int setCarrierId(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setCarrierId]+ carrierId:");
        stringBuilder.append(n);
        stringBuilder.append(" subId:");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        this.enforceModifyPhoneState("setCarrierId");
        long l = Binder.clearCallingIdentity();
        try {
            this.validateSubId(n2);
            stringBuilder = new ContentValues(1);
            stringBuilder.put("carrier_id", Integer.valueOf(n));
            n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n2), (ContentValues)stringBuilder, null, null);
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int setCountryIso(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setCountryIso]+ iso:");
        stringBuilder.append(string);
        stringBuilder.append(" subId:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        stringBuilder = new ContentValues();
        stringBuilder.put("iso_country_code", string);
        n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)stringBuilder, null, null);
        this.refreshCachedActiveSubscriptionInfoList();
        this.notifySubscriptionInfoChanged();
        return n;
    }

    public boolean setDataEnabledOverrideRules(int n, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setDataEnabledOverrideRules]+ rules:");
        stringBuilder.append(string);
        stringBuilder.append(" subId:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        this.validateSubId(n);
        boolean bl = true;
        stringBuilder = new ContentValues(1);
        stringBuilder.put("data_enabled_override_rules", string);
        if (this.databaseUpdateHelper((ContentValues)stringBuilder, n, true) <= 0) {
            bl = false;
        }
        if (bl) {
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
        }
        return bl;
    }

    public int setDataRoaming(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setDataRoaming]+ roaming:");
        stringBuilder.append(n);
        stringBuilder.append(" subId:");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        this.enforceModifyPhoneState("setDataRoaming");
        long l = Binder.clearCallingIdentity();
        try {
            this.validateSubId(n2);
            if (n < 0) {
                this.logd("[setDataRoaming]- fail");
                return -1;
            }
            stringBuilder = new ContentValues(1);
            stringBuilder.put("data_roaming", Integer.valueOf(n));
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("[setDataRoaming]- roaming:");
            stringBuilder2.append(n);
            stringBuilder2.append(" set");
            this.logd(stringBuilder2.toString());
            n = this.databaseUpdateHelper((ContentValues)stringBuilder, n2, true);
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setDefaultDataSubId(int var1_1) {
        block9 : {
            this.enforceModifyPhoneState("setDefaultDataSubId");
            var2_2 = Binder.clearCallingIdentity();
            if (var1_1 == Integer.MAX_VALUE) ** GOTO lbl21
            try {
                block10 : {
                    block11 : {
                        var4_3 = ProxyController.getInstance();
                        var5_6 = SubscriptionController.sPhones.length;
                        var6_7 = new StringBuilder();
                        var6_7.append("[setDefaultDataSubId] num phones=");
                        var6_7.append(var5_6);
                        var6_7.append(", subId=");
                        var6_7.append(var1_1);
                        this.logdl(var6_7.toString());
                        if (!SubscriptionManager.isValidSubscriptionId((int)var1_1)) break block10;
                        var6_7 = new RadioAccessFamily[var5_6];
                        var7_8 = false;
                        break block11;
lbl21: // 1 sources:
                        var4_5 = new RuntimeException("setDefaultDataSubId called with DEFAULT_SUB_ID");
                        throw var4_5;
                    }
                    for (var8_9 = 0; var8_9 < var5_6; ++var8_9) {
                        var9_10 = SubscriptionController.sPhones[var8_9].getSubId();
                        if (var9_10 == var1_1) {
                            var10_11 = var4_3.getMaxRafSupported();
                            var7_8 = true;
                        } else {
                            var10_11 = var4_3.getMinRafSupported();
                        }
                        var11_12 = new StringBuilder();
                        var11_12.append("[setDefaultDataSubId] phoneId=");
                        var11_12.append(var8_9);
                        var11_12.append(" subId=");
                        var11_12.append(var9_10);
                        var11_12.append(" RAF=");
                        var11_12.append(var10_11);
                        this.logdl(var11_12.toString());
                        var6_7[var8_9] = new RadioAccessFamily(var8_9, var10_11);
                    }
                    if (var7_8) {
                        var4_3.setRadioCapability(var6_7);
                    } else {
                        this.logdl("[setDefaultDataSubId] no valid subId's found - not updating.");
                    }
                }
                this.updateAllDataConnectionTrackers();
                var8_9 = this.getDefaultSubId();
                Settings.Global.putInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_data_call", (int)var1_1);
                MultiSimSettingController.getInstance().notifyDefaultDataSubChanged();
                this.broadcastDefaultDataSubIdChanged(var1_1);
                if (var8_9 == this.getDefaultSubId()) break block9;
                this.sendDefaultChangedBroadcast(this.getDefaultSubId());
            }
            catch (Throwable var4_4) {}
        }
        Binder.restoreCallingIdentity((long)var2_2);
        return;
        Binder.restoreCallingIdentity((long)var2_2);
        throw var4_4;
    }

    @UnsupportedAppUsage
    public void setDefaultSmsSubId(int n) {
        this.enforceModifyPhoneState("setDefaultSmsSubId");
        if (n != Integer.MAX_VALUE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[setDefaultSmsSubId] subId=");
            stringBuilder.append(n);
            this.logdl(stringBuilder.toString());
            Settings.Global.putInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_sms", (int)n);
            this.broadcastDefaultSmsSubIdChanged(n);
            return;
        }
        throw new RuntimeException("setDefaultSmsSubId called with DEFAULT_SUB_ID");
    }

    @UnsupportedAppUsage
    public void setDefaultVoiceSubId(int n) {
        this.enforceModifyPhoneState("setDefaultVoiceSubId");
        if (n != Integer.MAX_VALUE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[setDefaultVoiceSubId] subId=");
            stringBuilder.append(n);
            this.logdl(stringBuilder.toString());
            int n2 = this.getDefaultSubId();
            Settings.Global.putInt((ContentResolver)this.mContext.getContentResolver(), (String)"multi_sim_voice_call", (int)n);
            this.broadcastDefaultVoiceSubIdChanged(n);
            stringBuilder = n == -1 ? null : this.mTelephonyManager.getPhoneAccountHandleForSubscriptionId(n);
            Object object = (TelecomManager)this.mContext.getSystemService(TelecomManager.class);
            if (!Objects.equals((Object)object.getUserSelectedOutgoingPhoneAccount(), stringBuilder)) {
                object.setUserSelectedOutgoingPhoneAccount((PhoneAccountHandle)stringBuilder);
                object = new StringBuilder();
                ((StringBuilder)object).append("[setDefaultVoiceSubId] change to phoneAccountHandle=");
                ((StringBuilder)object).append((Object)stringBuilder);
                this.logd(((StringBuilder)object).toString());
            } else {
                this.logd("[setDefaultVoiceSubId] default phone account not changed");
            }
            if (n2 != this.getDefaultSubId()) {
                this.sendDefaultChangedBroadcast(this.getDefaultSubId());
            }
            return;
        }
        throw new RuntimeException("setDefaultVoiceSubId called with DEFAULT_SUB_ID");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int setDisplayNameUsingSrc(String var1_1, int var2_3, int var3_4) {
        block7 : {
            block8 : {
                var4_5 = new StringBuilder();
                var4_5.append("[setDisplayName]+  displayName:");
                var4_5.append(var1_1);
                var4_5.append(" subId:");
                var4_5.append(var2_3);
                var4_5.append(" nameSource:");
                var4_5.append(var3_4);
                this.logd(var4_5.toString());
                this.enforceModifyPhoneState("setDisplayNameUsingSrc");
                var5_6 = Binder.clearCallingIdentity();
                this.validateSubId(var2_3);
                var4_5 = this.getSubInfo(null, null);
                if (var4_5 == null || var4_5.isEmpty()) break block7;
                var7_7 = var4_5.iterator();
                while (var7_7.hasNext()) {
                    var4_5 = (SubscriptionInfo)var7_7.next();
                    if (var4_5.getSubscriptionId() != var2_3 || SubscriptionController.getNameSourcePriority(var4_5.getNameSource()) <= SubscriptionController.getNameSourcePriority(var3_4) && (var1_1 == null || !(var8_8 = var1_1.equals(var4_5.getDisplayName())))) continue;
                }
                {
                    Binder.restoreCallingIdentity((long)var5_6);
                    return 0;
                }
                if (var1_1 != null) ** GOTO lbl33
                var4_5 = this.mContext.getString(17039374);
                break block8;
lbl33: // 1 sources:
                var4_5 = var1_1;
            }
            var7_7 = new ContentValues(1);
            var7_7.put("display_name", (String)var4_5);
            if (var3_4 >= 0) {
                var9_9 = new StringBuilder();
                var9_9.append("Set nameSource=");
                var9_9.append(var3_4);
                this.logd(var9_9.toString());
                var7_7.put("name_source", Integer.valueOf(var3_4));
            }
            var9_9 = new StringBuilder();
            var9_9.append("[setDisplayName]- mDisplayName:");
            var9_9.append((String)var4_5);
            var9_9.append(" set");
            this.logd(var9_9.toString());
            var4_5 = this.getSubscriptionInfo(var2_3);
            if (var4_5 != null && var4_5.isEmbedded()) {
                var3_4 = var4_5.getCardId();
                var4_5 = new StringBuilder();
                var4_5.append("Updating embedded sub nickname on cardId: ");
                var4_5.append(var3_4);
                this.logd(var4_5.toString());
                var10_10 = ((EuiccManager)this.mContext.getSystemService("euicc")).createForCardId(var3_4);
                var9_9 = this.mContext;
                var4_5 = new Intent();
                var10_10.updateSubscriptionNickname(var2_3, var1_1, PendingIntent.getService((Context)var9_9, (int)0, (Intent)var4_5, (int)0));
            }
            var2_3 = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)var2_3), var7_7, null, null);
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
            return var2_3;
        }
        Binder.restoreCallingIdentity((long)var5_6);
        return 0;
        finally {
            Binder.restoreCallingIdentity((long)var5_6);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setDisplayNumber(String charSequence, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setDisplayNumber]+ subId:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        this.enforceModifyPhoneState("setDisplayNumber");
        long l = Binder.clearCallingIdentity();
        try {
            this.validateSubId(n);
            int n2 = this.getPhoneId(n);
            if (charSequence != null && n2 >= 0 && n2 < this.mTelephonyManager.getPhoneCount()) {
                stringBuilder = new ContentValues(1);
                stringBuilder.put("number", (String)charSequence);
                n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)stringBuilder, null, null);
                this.refreshCachedActiveSubscriptionInfoList();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("[setDisplayNumber]- update result :");
                ((StringBuilder)charSequence).append(n);
                this.logd(((StringBuilder)charSequence).toString());
                this.notifySubscriptionInfoChanged();
                return n;
            }
            this.logd("[setDispalyNumber]- fail");
            return -1;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int setIconTint(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setIconTint]+ tint:");
        stringBuilder.append(n);
        stringBuilder.append(" subId:");
        stringBuilder.append(n2);
        this.logd(stringBuilder.toString());
        this.enforceModifyPhoneState("setIconTint");
        long l = Binder.clearCallingIdentity();
        try {
            this.validateSubId(n2);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("color", Integer.valueOf(n));
            stringBuilder = new StringBuilder();
            stringBuilder.append("[setIconTint]- tint:");
            stringBuilder.append(n);
            stringBuilder.append(" set");
            this.logd(stringBuilder.toString());
            n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n2), contentValues, null, null);
            this.refreshCachedActiveSubscriptionInfoList();
            this.notifySubscriptionInfoChanged();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int setImsi(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setImsi]+ imsi:");
        stringBuilder.append(string);
        stringBuilder.append(" subId:");
        stringBuilder.append(n);
        this.logd(stringBuilder.toString());
        stringBuilder = new ContentValues(1);
        stringBuilder.put("imsi", string);
        n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)stringBuilder, null, null);
        this.refreshCachedActiveSubscriptionInfoList();
        this.notifySubscriptionInfoChanged();
        return n;
    }

    public int setMccMnc(String charSequence, int n) {
        int n2;
        String string = ((String)charSequence).substring(0, 3);
        String string2 = ((String)charSequence).substring(3);
        int n3 = 0;
        int n4 = 0;
        n3 = n2 = Integer.parseInt(string);
        try {
            int n5;
            n3 = n5 = Integer.parseInt(string2);
            n4 = n2;
            n2 = n3;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[setMccMnc] - couldn't parse mcc/mnc: ");
            stringBuilder.append((String)charSequence);
            this.loge(stringBuilder.toString());
            n2 = n4;
            n4 = n3;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("[setMccMnc]+ mcc/mnc:");
        ((StringBuilder)charSequence).append(n4);
        ((StringBuilder)charSequence).append("/");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(" subId:");
        ((StringBuilder)charSequence).append(n);
        this.logd(((StringBuilder)charSequence).toString());
        charSequence = new ContentValues(4);
        charSequence.put("mcc", Integer.valueOf(n4));
        charSequence.put("mnc", Integer.valueOf(n2));
        charSequence.put("mcc_string", string);
        charSequence.put("mnc_string", string2);
        n = this.mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n), (ContentValues)charSequence, null, null);
        this.refreshCachedActiveSubscriptionInfoList();
        this.notifySubscriptionInfoChanged();
        return n;
    }

    public int setOpportunistic(boolean bl, int n, String string) {
        long l;
        block6 : {
            try {
                TelephonyPermissions.enforceCallingOrSelfModifyPermissionOrCarrierPrivilege((Context)this.mContext, (int)n, (String)string);
            }
            catch (SecurityException securityException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Caller requires permission on sub ");
                stringBuilder.append(n);
                this.enforceCarrierPrivilegeOnInactiveSub(n, string, stringBuilder.toString());
            }
            l = Binder.clearCallingIdentity();
            int n2 = bl ? 1 : 0;
            n = this.setSubscriptionProperty(n, "is_opportunistic", String.valueOf(n2));
            if (n == 0) break block6;
            this.notifySubscriptionInfoChanged();
        }
        return n;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean setPlmnSpn(int n, boolean bl, String string, boolean bl2, String string2) {
        Object object = this.mLock;
        synchronized (object) {
            n = this.getSubIdUsingPhoneId(n);
            if (this.mContext.getPackageManager().resolveContentProvider(SubscriptionManager.CONTENT_URI.getAuthority(), 0) != null && SubscriptionManager.isValidSubscriptionId((int)n)) {
                CharSequence charSequence = "";
                if (bl) {
                    String string3 = string;
                    charSequence = string3;
                    if (bl2) {
                        charSequence = string3;
                        if (!Objects.equals(string2, string)) {
                            string = this.mContext.getString(17040216).toString();
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(string3);
                            ((StringBuilder)charSequence).append(string);
                            ((StringBuilder)charSequence).append(string2);
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                    }
                } else if (bl2) {
                    charSequence = string2;
                }
                this.setCarrierText((String)charSequence, n);
                return true;
            }
            this.logd("[setPlmnSpn] No valid subscription to store info");
            this.notifySubscriptionInfoChanged();
            return false;
        }
    }

    public void setPreferredDataSubscriptionId(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) {
        this.enforceModifyPhoneState("setPreferredDataSubscriptionId");
        long l = Binder.clearCallingIdentity();
        try {
            PhoneSwitcher.getInstance().trySetOpportunisticDataSubscription(n, bl, iSetOpportunisticDataCallback);
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public boolean setSubscriptionEnabled(boolean bl, int n) {
        this.enforceModifyPhoneState("setSubscriptionEnabled");
        long l = Binder.clearCallingIdentity();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setSubscriptionEnabled");
        Object object2 = bl ? " enable " : " disable ";
        try {
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" subId ");
            ((StringBuilder)object).append(n);
            this.logd(((StringBuilder)object).toString());
            if (SubscriptionManager.isUsableSubscriptionId((int)n)) {
                object = SubscriptionController.getInstance().getAllSubInfoList(this.mContext.getOpPackageName()).stream();
                object2 = new _$$Lambda$SubscriptionController$KLGYC8GQvJwXrWqyIaejMh0cYio(n);
                if ((object2 = (SubscriptionInfo)object.filter(object2).findFirst().get()) == null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("setSubscriptionEnabled subId ");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" doesn't exist.");
                    this.logd(((StringBuilder)object2).toString());
                    return false;
                }
                if (object2.isEmbedded()) {
                    bl = this.enableEmbeddedSubscription((SubscriptionInfo)object2, bl);
                    return bl;
                }
                bl = this.enablePhysicalSubscription((SubscriptionInfo)object2, bl);
                return bl;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setSubscriptionEnabled not usable subId ");
            ((StringBuilder)object2).append(n);
            object = new IllegalArgumentException(((StringBuilder)object2).toString());
            throw object;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int setSubscriptionProperty(int n, String string, String string2) {
        this.enforceModifyPhoneState("setSubscriptionProperty");
        long l = Binder.clearCallingIdentity();
        try {
            this.validateSubId(n);
            n = SubscriptionController.setSubscriptionPropertyIntoContentResolver(n, string, string2, this.mContext.getContentResolver());
            this.refreshCachedActiveSubscriptionInfoList();
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void syncGroupedSetting(int n) {
        String string = this.getSubscriptionProperty(n, "data_enabled_override_rules");
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("data_enabled_override_rules", string);
        this.databaseUpdateHelper(contentValues, n, true);
    }

    public void updatePhonesAvailability(Phone[] arrphone) {
        sPhones = arrphone;
    }
}

