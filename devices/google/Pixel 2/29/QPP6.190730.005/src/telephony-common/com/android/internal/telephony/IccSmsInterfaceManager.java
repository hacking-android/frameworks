/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteException
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.os.UserManager
 *  android.telephony.Rlog
 *  android.telephony.SmsManager
 *  android.telephony.SmsMessage
 *  android.util.LocalLog
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.SmsRawData
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.HexDump
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.LocalLog;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IntRangeManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsNumberUtils;
import com.android.internal.telephony.SmsPermissions;
import com.android.internal.telephony.SmsRawData;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony._$$Lambda$IccSmsInterfaceManager$rB1zRNxMbL7VadRMSxZ5tebvHwM;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.HexDump;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IccSmsInterfaceManager {
    static final boolean DBG = true;
    private static final int EVENT_LOAD_DONE = 1;
    protected static final int EVENT_SET_BROADCAST_ACTIVATION_DONE = 3;
    protected static final int EVENT_SET_BROADCAST_CONFIG_DONE = 4;
    private static final int EVENT_UPDATE_DONE = 2;
    static final String LOG_TAG = "IccSmsInterfaceManager";
    private static final int SMS_CB_CODE_SCHEME_MAX = 255;
    private static final int SMS_CB_CODE_SCHEME_MIN = 0;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    @UnsupportedAppUsage
    protected final AppOpsManager mAppOps;
    private CdmaBroadcastRangeManager mCdmaBroadcastRangeManager = new CdmaBroadcastRangeManager();
    private final LocalLog mCellBroadcastLocalLog = new LocalLog(100);
    @UnsupportedAppUsage
    private CellBroadcastRangeManager mCellBroadcastRangeManager = new CellBroadcastRangeManager();
    @UnsupportedAppUsage
    protected final Context mContext;
    @VisibleForTesting
    public SmsDispatchersController mDispatchersController;
    @UnsupportedAppUsage
    protected Handler mHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message object) {
            int n = object.what;
            boolean bl = true;
            boolean bl2 = true;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3 && n != 4) {
                        return;
                    }
                    AsyncResult asyncResult = (AsyncResult)object.obj;
                    object = IccSmsInterfaceManager.this.mLock;
                    synchronized (object) {
                        IccSmsInterfaceManager iccSmsInterfaceManager = IccSmsInterfaceManager.this;
                        if (asyncResult.exception != null) {
                            bl2 = false;
                        }
                        iccSmsInterfaceManager.mSuccess = bl2;
                        IccSmsInterfaceManager.this.mLock.notifyAll();
                        return;
                    }
                }
                AsyncResult asyncResult = (AsyncResult)object.obj;
                object = IccSmsInterfaceManager.this.mLock;
                synchronized (object) {
                    IccSmsInterfaceManager iccSmsInterfaceManager = IccSmsInterfaceManager.this;
                    bl2 = asyncResult.exception == null ? bl : false;
                    iccSmsInterfaceManager.mSuccess = bl2;
                    IccSmsInterfaceManager.this.mLock.notifyAll();
                    return;
                }
            }
            AsyncResult asyncResult = (AsyncResult)object.obj;
            object = IccSmsInterfaceManager.this.mLock;
            synchronized (object) {
                if (asyncResult.exception == null) {
                    IccSmsInterfaceManager.this.mSms = IccSmsInterfaceManager.this.buildValidRawData((ArrayList)asyncResult.result);
                    IccSmsInterfaceManager.this.markMessagesAsRead((ArrayList)asyncResult.result);
                } else {
                    if (Rlog.isLoggable((String)"SMS", (int)3)) {
                        IccSmsInterfaceManager.this.log("Cannot load Sms records");
                    }
                    IccSmsInterfaceManager.this.mSms = null;
                }
                IccSmsInterfaceManager.this.mLock.notifyAll();
                return;
            }
        }
    };
    @UnsupportedAppUsage
    protected final Object mLock = new Object();
    @UnsupportedAppUsage
    protected Phone mPhone;
    @UnsupportedAppUsage
    private List<SmsRawData> mSms;
    private SmsPermissions mSmsPermissions;
    @UnsupportedAppUsage
    protected boolean mSuccess;

    protected IccSmsInterfaceManager(Phone phone) {
        this(phone, phone.getContext(), (AppOpsManager)phone.getContext().getSystemService("appops"), (UserManager)phone.getContext().getSystemService("user"), new SmsDispatchersController(phone, phone.mSmsStorageMonitor, phone.mSmsUsageMonitor));
    }

    @VisibleForTesting
    public IccSmsInterfaceManager(Phone phone, Context context, AppOpsManager appOpsManager, UserManager userManager, SmsDispatchersController smsDispatchersController) {
        this.mPhone = phone;
        this.mContext = context;
        this.mAppOps = appOpsManager;
        this.mDispatchersController = smsDispatchersController;
        this.mSmsPermissions = new SmsPermissions(phone, context, appOpsManager);
    }

    @UnsupportedAppUsage
    private String filterDestAddress(String string) {
        block0 : {
            String string2 = SmsNumberUtils.filterDestAddr(this.mPhone, string);
            if (string2 == null) break block0;
            string = string2;
        }
        return string;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean isFailedOrDraft(ContentResolver contentResolver, Uri uri) {
        Throwable throwable2222;
        ContentResolver contentResolver2;
        long l;
        block7 : {
            block8 : {
                block9 : {
                    ContentResolver contentResolver3;
                    block5 : {
                        boolean bl;
                        block6 : {
                            l = Binder.clearCallingIdentity();
                            contentResolver3 = null;
                            contentResolver2 = null;
                            bl = false;
                            contentResolver = contentResolver.query(uri, new String[]{"type"}, null, null, null);
                            if (contentResolver == null) break block5;
                            contentResolver2 = contentResolver;
                            contentResolver3 = contentResolver;
                            if (!contentResolver.moveToFirst()) break block5;
                            contentResolver2 = contentResolver;
                            contentResolver3 = contentResolver;
                            int n = contentResolver.getInt(0);
                            if (n != 3 && n != 5) break block6;
                            bl = true;
                        }
                        contentResolver.close();
                        Binder.restoreCallingIdentity((long)l);
                        return bl;
                    }
                    if (contentResolver == null) break block8;
                    break block9;
                    {
                        catch (Throwable throwable2222) {
                            break block7;
                        }
                        catch (SQLiteException sQLiteException) {}
                        contentResolver2 = contentResolver3;
                        {
                            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]isFailedOrDraft: query message type failed", (Throwable)sQLiteException);
                            if (contentResolver3 == null) break block8;
                            contentResolver = contentResolver3;
                        }
                    }
                }
                contentResolver.close();
            }
            Binder.restoreCallingIdentity((long)l);
            return false;
        }
        if (contentResolver2 != null) {
            contentResolver2.close();
        }
        Binder.restoreCallingIdentity((long)l);
        throw throwable2222;
    }

    static /* synthetic */ void lambda$injectSmsPdu$0(PendingIntent pendingIntent, int n) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(n);
            }
            catch (PendingIntent.CanceledException canceledException) {
                Rlog.d((String)LOG_TAG, (String)"receivedIntent cancelled.");
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String[] loadTextAndAddress(ContentResolver contentResolver, Uri object) {
        Throwable throwable2222;
        ContentResolver contentResolver2;
        long l;
        block6 : {
            block7 : {
                block8 : {
                    ContentResolver contentResolver3;
                    block5 : {
                        l = Binder.clearCallingIdentity();
                        contentResolver3 = null;
                        contentResolver2 = null;
                        contentResolver = contentResolver.query((Uri)object, new String[]{"body", "address"}, null, null, null);
                        if (contentResolver == null) break block5;
                        contentResolver2 = contentResolver;
                        contentResolver3 = contentResolver;
                        if (!contentResolver.moveToFirst()) break block5;
                        contentResolver2 = contentResolver;
                        contentResolver3 = contentResolver;
                        object = contentResolver.getString(0);
                        contentResolver2 = contentResolver;
                        contentResolver3 = contentResolver;
                        String string = contentResolver.getString(1);
                        contentResolver.close();
                        Binder.restoreCallingIdentity((long)l);
                        return new String[]{object, string};
                    }
                    if (contentResolver == null) break block7;
                    break block8;
                    {
                        catch (Throwable throwable2222) {
                            break block6;
                        }
                        catch (SQLiteException sQLiteException) {}
                        contentResolver2 = contentResolver3;
                        {
                            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]loadText: query message text failed", (Throwable)sQLiteException);
                            if (contentResolver3 == null) break block7;
                            contentResolver = contentResolver3;
                        }
                    }
                }
                contentResolver.close();
            }
            Binder.restoreCallingIdentity((long)l);
            return null;
        }
        if (contentResolver2 != null) {
            contentResolver2.close();
        }
        Binder.restoreCallingIdentity((long)l);
        throw throwable2222;
    }

    private void returnUnspecifiedFailure(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(1);
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
        }
    }

    private void returnUnspecifiedFailure(List<PendingIntent> object) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.returnUnspecifiedFailure((PendingIntent)object.next());
        }
    }

    private void sendDataInternal(String string, String string2, String string3, int n, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        if (Rlog.isLoggable((String)"SMS", (int)2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendData: destAddr=");
            stringBuilder.append(string2);
            stringBuilder.append(" scAddr=");
            stringBuilder.append(string3);
            stringBuilder.append(" destPort=");
            stringBuilder.append(n);
            stringBuilder.append(" data='");
            stringBuilder.append(HexDump.toHexString((byte[])arrby));
            stringBuilder.append("' sentIntent=");
            stringBuilder.append((Object)pendingIntent);
            stringBuilder.append(" deliveryIntent=");
            stringBuilder.append((Object)pendingIntent2);
            stringBuilder.append(" isForVVM=");
            stringBuilder.append(bl);
            this.log(stringBuilder.toString());
        }
        string2 = this.filterDestAddress(string2);
        this.mDispatchersController.sendData(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, bl);
    }

    private void sendTextInternal(String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n, boolean bl2, int n2, boolean bl3) {
        if (Rlog.isLoggable((String)"SMS", (int)2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendText: destAddr=");
            stringBuilder.append(string2);
            stringBuilder.append(" scAddr=");
            stringBuilder.append(string3);
            stringBuilder.append(" text='");
            stringBuilder.append(string4);
            stringBuilder.append("' sentIntent=");
            stringBuilder.append((Object)pendingIntent);
            stringBuilder.append(" deliveryIntent=");
            stringBuilder.append((Object)pendingIntent2);
            stringBuilder.append(" priority=");
            stringBuilder.append(n);
            stringBuilder.append(" expectMore=");
            stringBuilder.append(bl2);
            stringBuilder.append(" validityPeriod=");
            stringBuilder.append(n2);
            stringBuilder.append(" isForVVM=");
            stringBuilder.append(bl3);
            this.log(stringBuilder.toString());
        }
        string2 = this.filterDestAddress(string2);
        this.mDispatchersController.sendText(string2, string3, string4, pendingIntent, pendingIntent2, null, string, bl, n, bl2, n2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean setCdmaBroadcastActivation(boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Calling setCdmaBroadcastActivation(");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(")");
        this.log(((StringBuilder)object).toString());
        object = this.mLock;
        synchronized (object) {
            Message message = this.mHandler.obtainMessage(3);
            this.mSuccess = false;
            this.mPhone.mCi.setCdmaBroadcastActivation(bl, message);
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to set cdma broadcast activation");
            }
            return this.mSuccess;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private boolean setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] arrcdmaSmsBroadcastConfigInfo) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Calling setCdmaBroadcastConfig with ");
        ((StringBuilder)object).append(arrcdmaSmsBroadcastConfigInfo.length);
        ((StringBuilder)object).append(" configurations");
        this.log(((StringBuilder)object).toString());
        object = this.mLock;
        synchronized (object) {
            Message message = this.mHandler.obtainMessage(4);
            this.mSuccess = false;
            this.mPhone.mCi.setCdmaBroadcastConfig(arrcdmaSmsBroadcastConfigInfo, message);
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to set cdma broadcast config");
            }
            return this.mSuccess;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean setCellBroadcastActivation(boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Calling setCellBroadcastActivation(");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(')');
        this.log(((StringBuilder)object).toString());
        object = this.mLock;
        synchronized (object) {
            Message message = this.mHandler.obtainMessage(3);
            this.mSuccess = false;
            this.mPhone.mCi.setGsmBroadcastActivation(bl, message);
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to set cell broadcast activation");
            }
            return this.mSuccess;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private boolean setCellBroadcastConfig(SmsBroadcastConfigInfo[] arrsmsBroadcastConfigInfo) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Calling setGsmBroadcastConfig with ");
        ((StringBuilder)object).append(arrsmsBroadcastConfigInfo.length);
        ((StringBuilder)object).append(" configurations");
        this.log(((StringBuilder)object).toString());
        object = this.mLock;
        synchronized (object) {
            Message message = this.mHandler.obtainMessage(4);
            this.mSuccess = false;
            this.mPhone.mCi.setGsmBroadcastConfig(arrsmsBroadcastConfigInfo, message);
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to set cell broadcast config");
            }
            return this.mSuccess;
        }
    }

    protected ArrayList<SmsRawData> buildValidRawData(ArrayList<byte[]> arrayList) {
        int n = arrayList.size();
        ArrayList<SmsRawData> arrayList2 = new ArrayList<SmsRawData>(n);
        for (int i = 0; i < n; ++i) {
            if ((arrayList.get(i)[0] & 1) == 0) {
                arrayList2.add(null);
                continue;
            }
            arrayList2.add(new SmsRawData(arrayList.get(i)));
        }
        return arrayList2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean copyMessageToIccEf(String object, int n, byte[] arrby, byte[] arrby2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("copyMessageToIccEf: status=");
        stringBuilder.append(n);
        stringBuilder.append(" ==> pdu=(");
        stringBuilder.append(Arrays.toString(arrby));
        stringBuilder.append("), smsc=(");
        stringBuilder.append(Arrays.toString(arrby2));
        stringBuilder.append(")");
        this.log(stringBuilder.toString());
        this.enforceReceiveAndSend("Copying message to Icc");
        if (this.mAppOps.noteOp(22, Binder.getCallingUid(), (String)object) != 0) {
            return false;
        }
        object = this.mLock;
        synchronized (object) {
            this.mSuccess = false;
            stringBuilder = this.mHandler.obtainMessage(2);
            if (1 == this.mPhone.getPhoneType()) {
                this.mPhone.mCi.writeSmsToSim(n, IccUtils.bytesToHexString((byte[])arrby2), IccUtils.bytesToHexString((byte[])arrby), (Message)stringBuilder);
            } else {
                this.mPhone.mCi.writeSmsToRuim(n, IccUtils.bytesToHexString((byte[])arrby), (Message)stringBuilder);
            }
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to update by index");
            }
            return this.mSuccess;
        }
    }

    @UnsupportedAppUsage
    public boolean disableCdmaBroadcastRange(int n, int n2) {
        synchronized (this) {
            boolean bl;
            block7 : {
                CharSequence charSequence;
                block6 : {
                    this.mContext.enforceCallingPermission("android.permission.RECEIVE_SMS", "Disabling cell broadcast SMS");
                    charSequence = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
                    boolean bl2 = this.mCdmaBroadcastRangeManager.disableRange(n, n2, (String)charSequence);
                    bl = false;
                    if (bl2) break block6;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to remove cdma broadcast channels range ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" to ");
                    ((StringBuilder)charSequence).append(n2);
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.log((String)charSequence);
                    this.mCellBroadcastLocalLog.log((String)charSequence);
                    return false;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Removed cdma broadcast channels range ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" to ");
                ((StringBuilder)charSequence).append(n2);
                charSequence = ((StringBuilder)charSequence).toString();
                this.log((String)charSequence);
                this.mCellBroadcastLocalLog.log((String)charSequence);
                if (this.mCdmaBroadcastRangeManager.isEmpty()) break block7;
                bl = true;
            }
            this.setCdmaBroadcastActivation(bl);
            return true;
        }
    }

    public boolean disableCellBroadcast(int n, int n2) {
        return this.disableCellBroadcastRange(n, n, n2);
    }

    public boolean disableCellBroadcastRange(int n, int n2, int n3) {
        if (n3 == 0) {
            return this.disableGsmBroadcastRange(n, n2);
        }
        if (n3 == 1) {
            return this.disableCdmaBroadcastRange(n, n2);
        }
        throw new IllegalArgumentException("Not a supported RAN Type");
    }

    @UnsupportedAppUsage
    public boolean disableGsmBroadcastRange(int n, int n2) {
        synchronized (this) {
            boolean bl;
            block7 : {
                CharSequence charSequence;
                block6 : {
                    this.mContext.enforceCallingPermission("android.permission.RECEIVE_SMS", "Disabling cell broadcast SMS");
                    charSequence = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
                    boolean bl2 = this.mCellBroadcastRangeManager.disableRange(n, n2, (String)charSequence);
                    bl = false;
                    if (bl2) break block6;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to remove GSM cell broadcast channels range ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" to ");
                    ((StringBuilder)charSequence).append(n2);
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.log((String)charSequence);
                    this.mCellBroadcastLocalLog.log((String)charSequence);
                    return false;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Removed GSM cell broadcast channels range ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" to ");
                ((StringBuilder)charSequence).append(n2);
                charSequence = ((StringBuilder)charSequence).toString();
                this.log((String)charSequence);
                this.mCellBroadcastLocalLog.log((String)charSequence);
                if (this.mCellBroadcastRangeManager.isEmpty()) break block7;
                bl = true;
            }
            this.setCellBroadcastActivation(bl);
            return true;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("CellBroadcast log:");
        this.mCellBroadcastLocalLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.println("SMS dispatcher controller log:");
        this.mDispatchersController.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
    }

    @UnsupportedAppUsage
    public boolean enableCdmaBroadcastRange(int n, int n2) {
        synchronized (this) {
            boolean bl;
            block7 : {
                CharSequence charSequence;
                block6 : {
                    this.mContext.enforceCallingPermission("android.permission.RECEIVE_SMS", "Enabling cdma broadcast SMS");
                    charSequence = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
                    boolean bl2 = this.mCdmaBroadcastRangeManager.enableRange(n, n2, (String)charSequence);
                    bl = false;
                    if (bl2) break block6;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to add cdma broadcast channels range ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" to ");
                    ((StringBuilder)charSequence).append(n2);
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.log((String)charSequence);
                    this.mCellBroadcastLocalLog.log((String)charSequence);
                    return false;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Added cdma broadcast channels range ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" to ");
                ((StringBuilder)charSequence).append(n2);
                charSequence = ((StringBuilder)charSequence).toString();
                this.log((String)charSequence);
                this.mCellBroadcastLocalLog.log((String)charSequence);
                if (this.mCdmaBroadcastRangeManager.isEmpty()) break block7;
                bl = true;
            }
            this.setCdmaBroadcastActivation(bl);
            return true;
        }
    }

    public boolean enableCellBroadcast(int n, int n2) {
        return this.enableCellBroadcastRange(n, n, n2);
    }

    public boolean enableCellBroadcastRange(int n, int n2, int n3) {
        if (n3 == 0) {
            return this.enableGsmBroadcastRange(n, n2);
        }
        if (n3 == 1) {
            return this.enableCdmaBroadcastRange(n, n2);
        }
        throw new IllegalArgumentException("Not a supported RAN Type");
    }

    @UnsupportedAppUsage
    public boolean enableGsmBroadcastRange(int n, int n2) {
        synchronized (this) {
            boolean bl;
            block7 : {
                CharSequence charSequence;
                block6 : {
                    this.mContext.enforceCallingPermission("android.permission.RECEIVE_SMS", "Enabling cell broadcast SMS");
                    charSequence = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
                    boolean bl2 = this.mCellBroadcastRangeManager.enableRange(n, n2, (String)charSequence);
                    bl = false;
                    if (bl2) break block6;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to add GSM cell broadcast channels range ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" to ");
                    ((StringBuilder)charSequence).append(n2);
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.log((String)charSequence);
                    this.mCellBroadcastLocalLog.log((String)charSequence);
                    return false;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Added GSM cell broadcast channels range ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" to ");
                ((StringBuilder)charSequence).append(n2);
                charSequence = ((StringBuilder)charSequence).toString();
                this.log((String)charSequence);
                this.mCellBroadcastLocalLog.log((String)charSequence);
                if (this.mCellBroadcastRangeManager.isEmpty()) break block7;
                bl = true;
            }
            this.setCellBroadcastActivation(bl);
            return true;
        }
    }

    @UnsupportedAppUsage
    protected void enforceReceiveAndSend(String string) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.RECEIVE_SMS", string);
        this.mContext.enforceCallingOrSelfPermission("android.permission.SEND_SMS", string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public List<SmsRawData> getAllMessagesFromIccEf(String object) {
        this.log("getAllMessagesFromEF");
        this.mContext.enforceCallingOrSelfPermission("android.permission.RECEIVE_SMS", "Reading messages from Icc");
        if (this.mAppOps.noteOp(21, Binder.getCallingUid(), (String)object) != 0) {
            return new ArrayList<SmsRawData>();
        }
        object = this.mLock;
        synchronized (object) {
            IccFileHandler iccFileHandler = this.mPhone.getIccFileHandler();
            if (iccFileHandler == null) {
                Rlog.e((String)LOG_TAG, (String)"Cannot load Sms records. No icc card?");
                this.mSms = null;
                return this.mSms;
            }
            iccFileHandler.loadEFLinearFixedAll(28476, this.mHandler.obtainMessage(1));
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to load from the Icc");
            }
            return this.mSms;
        }
    }

    @UnsupportedAppUsage
    public String getImsSmsFormat() {
        return this.mDispatchersController.getImsSmsFormat();
    }

    @UnsupportedAppUsage
    public int getPremiumSmsPermission(String string) {
        return this.mDispatchersController.getPremiumSmsPermission(string);
    }

    @UnsupportedAppUsage
    public void injectSmsPdu(byte[] arrby, String string, PendingIntent pendingIntent) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            this.mSmsPermissions.enforceCallerIsImsAppOrCarrierApp("injectSmsPdu");
        }
        if (Rlog.isLoggable((String)"SMS", (int)2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("pdu: ");
            stringBuilder.append(arrby);
            stringBuilder.append("\n format=");
            stringBuilder.append(string);
            stringBuilder.append("\n receivedIntent=");
            stringBuilder.append((Object)pendingIntent);
            this.log(stringBuilder.toString());
        }
        this.mDispatchersController.injectSmsPdu(arrby, string, new _$$Lambda$IccSmsInterfaceManager$rB1zRNxMbL7VadRMSxZ5tebvHwM(pendingIntent));
    }

    @UnsupportedAppUsage
    public boolean isImsSmsSupported() {
        return this.mDispatchersController.isIms();
    }

    @UnsupportedAppUsage
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[IccSmsInterfaceManager] ");
        stringBuilder.append(string);
        Log.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    protected byte[] makeSmsRecordData(int n, byte[] arrby) {
        byte[] arrby2 = 1 == this.mPhone.getPhoneType() ? new byte[176] : new byte[255];
        arrby2[0] = (byte)(n & 7);
        System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
        for (n = arrby.length + 1; n < arrby2.length; ++n) {
            arrby2[n] = (byte)-1;
        }
        return arrby2;
    }

    protected void markMessagesAsRead(ArrayList<byte[]> arrayList) {
        if (arrayList == null) {
            return;
        }
        IccFileHandler iccFileHandler = this.mPhone.getIccFileHandler();
        if (iccFileHandler == null) {
            if (Rlog.isLoggable((String)"SMS", (int)3)) {
                this.log("markMessagesAsRead - aborting, no icc card present.");
            }
            return;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            byte[] arrby = arrayList.get(i);
            if (arrby[0] != 3) continue;
            int n2 = arrby.length;
            Object object = new byte[n2 - 1];
            System.arraycopy(arrby, 1, object, 0, n2 - 1);
            iccFileHandler.updateEFLinearFixed(28476, i + 1, this.makeSmsRecordData(1, (byte[])object), null, null);
            if (!Rlog.isLoggable((String)"SMS", (int)3)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("SMS ");
            ((StringBuilder)object).append(i + 1);
            ((StringBuilder)object).append(" marked as read");
            this.log(((StringBuilder)object).toString());
        }
    }

    @UnsupportedAppUsage
    public void sendData(String string, String string2, String string3, int n, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        if (!this.mSmsPermissions.checkCallingCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        this.sendDataInternal(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, false);
    }

    public void sendDataWithSelfPermissions(String string, String string2, String string3, int n, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        if (!this.mSmsPermissions.checkCallingOrSelfCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        this.sendDataInternal(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, bl);
    }

    public void sendMultipartText(String string, String string2, String string3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl) {
        this.sendMultipartTextWithOptions(string, string2, string3, list, list2, list3, bl, -1, false, -1);
    }

    public void sendMultipartTextWithOptions(String string, String string2, String string3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl, int n, boolean bl2, int n2) {
        CharSequence charSequence;
        int n3;
        if (!this.mSmsPermissions.checkCallingCanSendText(bl, string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(list2);
            return;
        }
        if (Rlog.isLoggable((String)"SMS", (int)2)) {
            n3 = 0;
            for (String string4 : list) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("sendMultipartTextWithOptions: destAddr=");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(", srAddr=");
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append(", part[");
                ((StringBuilder)charSequence).append(n3);
                ((StringBuilder)charSequence).append("]=");
                ((StringBuilder)charSequence).append(string4);
                this.log(((StringBuilder)charSequence).toString());
                ++n3;
            }
        }
        charSequence = this.filterDestAddress(string2);
        if (list.size() > 1 && list.size() < 10 && !SmsMessage.hasEmsSupport()) {
            for (n3 = 0; n3 < list.size(); ++n3) {
                Object object;
                String string4;
                string2 = list.get(n3);
                if (SmsMessage.shouldAppendPageNumberAsPrefix()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(String.valueOf(n3 + 1));
                    ((StringBuilder)object).append('/');
                    ((StringBuilder)object).append(list.size());
                    ((StringBuilder)object).append(' ');
                    ((StringBuilder)object).append(string2);
                    string2 = ((StringBuilder)object).toString();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(' ');
                    ((StringBuilder)object).append(String.valueOf(n3 + 1));
                    ((StringBuilder)object).append('/');
                    ((StringBuilder)object).append(list.size());
                    string2 = string2.concat(((StringBuilder)object).toString());
                }
                object = list2 != null && list2.size() > n3 ? list2.get(n3) : null;
                string4 = list3 != null && list3.size() > n3 ? list3.get(n3) : null;
                this.mDispatchersController.sendText((String)charSequence, string3, string2, (PendingIntent)object, (PendingIntent)string4, null, string, bl, n, bl2, n2, false);
            }
            return;
        }
        this.mDispatchersController.sendMultipartText((String)charSequence, string3, (ArrayList)list, (ArrayList)list2, (ArrayList)list3, null, string, bl, n, bl2, n2);
    }

    @UnsupportedAppUsage
    public void sendStoredMultipartText(String string, Uri uri, String string2, List<PendingIntent> list, List<PendingIntent> pendingIntent) {
        if (!this.mSmsPermissions.checkCallingCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(list);
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!this.isFailedOrDraft(contentResolver, uri)) {
            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]sendStoredMultipartText: not FAILED or DRAFT message");
            this.returnUnspecifiedFailure(list);
            return;
        }
        String[] arrstring = this.loadTextAndAddress(contentResolver, uri);
        if (arrstring == null) {
            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]sendStoredMultipartText: can not load text");
            this.returnUnspecifiedFailure(list);
            return;
        }
        ArrayList arrayList = SmsManager.getDefault().divideMessage(arrstring[0]);
        if (arrayList != null) {
            int n = arrayList.size();
            int n2 = 1;
            if (n >= 1) {
                arrstring[1] = this.filterDestAddress(arrstring[1]);
                if (arrayList.size() > 1 && arrayList.size() < 10 && !SmsMessage.hasEmsSupport()) {
                    n = 0;
                    do {
                        StringBuilder stringBuilder;
                        Object object = pendingIntent;
                        if (n >= arrayList.size()) break;
                        String string3 = (String)arrayList.get(n);
                        if (SmsMessage.shouldAppendPageNumberAsPrefix()) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(String.valueOf(n + 1));
                            stringBuilder.append('/');
                            stringBuilder.append(arrayList.size());
                            stringBuilder.append(' ');
                            stringBuilder.append(string3);
                            string3 = stringBuilder.toString();
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(' ');
                            stringBuilder.append(String.valueOf(n + 1));
                            stringBuilder.append('/');
                            stringBuilder.append(arrayList.size());
                            string3 = string3.concat(stringBuilder.toString());
                        }
                        stringBuilder = list != null && list.size() > n ? list.get(n) : null;
                        object = object != null && pendingIntent.size() > n ? object.get(n) : null;
                        this.mDispatchersController.sendText(arrstring[n2], string2, string3, (PendingIntent)stringBuilder, (PendingIntent)object, uri, string, true, -1, false, -1, false);
                        ++n;
                    } while (true);
                    return;
                }
                this.mDispatchersController.sendMultipartText(arrstring[1], string2, arrayList, (ArrayList)list, (ArrayList)pendingIntent, uri, string, true, -1, false, -1);
                return;
            }
        }
        Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]sendStoredMultipartText: can not divide text");
        this.returnUnspecifiedFailure(list);
    }

    @UnsupportedAppUsage
    public void sendStoredText(String string, Uri uri, String string2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        String[] arrstring;
        if (!this.mSmsPermissions.checkCallingCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        if (Rlog.isLoggable((String)"SMS", (int)2)) {
            arrstring = new StringBuilder();
            arrstring.append("sendStoredText: scAddr=");
            arrstring.append(string2);
            arrstring.append(" messageUri=");
            arrstring.append((Object)uri);
            arrstring.append(" sentIntent=");
            arrstring.append((Object)pendingIntent);
            arrstring.append(" deliveryIntent=");
            arrstring.append((Object)pendingIntent2);
            this.log(arrstring.toString());
        }
        if (!this.isFailedOrDraft((ContentResolver)(arrstring = this.mContext.getContentResolver()), uri)) {
            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]sendStoredText: not FAILED or DRAFT message");
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        if ((arrstring = this.loadTextAndAddress((ContentResolver)arrstring, uri)) == null) {
            Log.e((String)LOG_TAG, (String)"[IccSmsInterfaceManager]sendStoredText: can not load text");
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        arrstring[1] = this.filterDestAddress(arrstring[1]);
        this.mDispatchersController.sendText(arrstring[1], string2, arrstring[0], pendingIntent, pendingIntent2, uri, string, true, -1, false, -1, false);
    }

    public void sendText(String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        this.sendTextInternal(string, string2, string3, string4, pendingIntent, pendingIntent2, bl, -1, false, -1, false);
    }

    public void sendTextWithOptions(String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n, boolean bl2, int n2) {
        if (!this.mSmsPermissions.checkCallingOrSelfCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        this.sendTextInternal(string, string2, string3, string4, pendingIntent, pendingIntent2, bl, n, bl2, n2, false);
    }

    public void sendTextWithSelfPermissions(String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, boolean bl2) {
        if (!this.mSmsPermissions.checkCallingOrSelfCanSendSms(string, "Sending SMS message")) {
            this.returnUnspecifiedFailure(pendingIntent);
            return;
        }
        this.sendTextInternal(string, string2, string3, string4, pendingIntent, pendingIntent2, bl, -1, false, -1, bl2);
    }

    @UnsupportedAppUsage
    public void setPremiumSmsPermission(String string, int n) {
        this.mDispatchersController.setPremiumSmsPermission(string, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean updateMessageOnIccEf(String object, int n, int n2, byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateMessageOnIccEf: index=");
        stringBuilder.append(n);
        stringBuilder.append(" status=");
        stringBuilder.append(n2);
        stringBuilder.append(" ==> (");
        stringBuilder.append(Arrays.toString(arrby));
        stringBuilder.append(")");
        this.log(stringBuilder.toString());
        this.enforceReceiveAndSend("Updating message on Icc");
        if (this.mAppOps.noteOp(22, Binder.getCallingUid(), (String)object) != 0) {
            return false;
        }
        object = this.mLock;
        synchronized (object) {
            this.mSuccess = false;
            stringBuilder = this.mHandler.obtainMessage(2);
            if ((n2 & 1) == 0) {
                if (1 == this.mPhone.getPhoneType()) {
                    this.mPhone.mCi.deleteSmsOnSim(n, (Message)stringBuilder);
                } else {
                    this.mPhone.mCi.deleteSmsOnRuim(n, (Message)stringBuilder);
                }
            } else {
                IccFileHandler iccFileHandler = this.mPhone.getIccFileHandler();
                if (iccFileHandler == null) {
                    stringBuilder.recycle();
                    return this.mSuccess;
                }
                iccFileHandler.updateEFLinearFixed(28476, n, this.makeSmsRecordData(n2, arrby), null, (Message)stringBuilder);
            }
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                this.log("interrupted while trying to update by index");
            }
            return this.mSuccess;
        }
    }

    class CdmaBroadcastRangeManager
    extends IntRangeManager {
        private ArrayList<CdmaSmsBroadcastConfigInfo> mConfigList = new ArrayList();

        CdmaBroadcastRangeManager() {
        }

        @Override
        protected void addRange(int n, int n2, boolean bl) {
            this.mConfigList.add(new CdmaSmsBroadcastConfigInfo(n, n2, 1, bl));
        }

        @Override
        protected boolean finishUpdate() {
            if (this.mConfigList.isEmpty()) {
                return true;
            }
            CdmaSmsBroadcastConfigInfo[] arrcdmaSmsBroadcastConfigInfo = this.mConfigList;
            arrcdmaSmsBroadcastConfigInfo = arrcdmaSmsBroadcastConfigInfo.toArray(new CdmaSmsBroadcastConfigInfo[arrcdmaSmsBroadcastConfigInfo.size()]);
            return IccSmsInterfaceManager.this.setCdmaBroadcastConfig(arrcdmaSmsBroadcastConfigInfo);
        }

        @Override
        protected void startUpdate() {
            this.mConfigList.clear();
        }
    }

    class CellBroadcastRangeManager
    extends IntRangeManager {
        private ArrayList<SmsBroadcastConfigInfo> mConfigList = new ArrayList();

        CellBroadcastRangeManager() {
        }

        @Override
        protected void addRange(int n, int n2, boolean bl) {
            this.mConfigList.add(new SmsBroadcastConfigInfo(n, n2, 0, 255, bl));
        }

        @Override
        protected boolean finishUpdate() {
            if (this.mConfigList.isEmpty()) {
                return true;
            }
            SmsBroadcastConfigInfo[] arrsmsBroadcastConfigInfo = this.mConfigList;
            arrsmsBroadcastConfigInfo = arrsmsBroadcastConfigInfo.toArray((T[])new SmsBroadcastConfigInfo[arrsmsBroadcastConfigInfo.size()]);
            return IccSmsInterfaceManager.this.setCellBroadcastConfig(arrsmsBroadcastConfigInfo);
        }

        @Override
        protected void startUpdate() {
            this.mConfigList.clear();
        }
    }

}

