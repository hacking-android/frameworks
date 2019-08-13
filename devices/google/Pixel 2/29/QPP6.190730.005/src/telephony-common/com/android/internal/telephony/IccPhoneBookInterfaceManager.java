/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.AdnRecordCache;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class IccPhoneBookInterfaceManager {
    @UnsupportedAppUsage
    protected static final boolean DBG = true;
    protected static final int EVENT_GET_SIZE_DONE = 1;
    protected static final int EVENT_LOAD_DONE = 2;
    protected static final int EVENT_UPDATE_DONE = 3;
    static final String LOG_TAG = "IccPhoneBookIM";
    @UnsupportedAppUsage
    protected AdnRecordCache mAdnCache;
    @UnsupportedAppUsage
    protected Handler mBaseHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void notifyPending(Request request, Object object) {
            if (request == null) return;
            synchronized (request) {
                request.mResult = object;
                request.mStatus.set(true);
                request.notifyAll();
                return;
            }
        }

        public void handleMessage(Message object) {
            Object object2 = (AsyncResult)((Message)object).obj;
            Request request = (Request)((AsyncResult)object2).userObj;
            int n = ((Message)object).what;
            boolean bl = false;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        if (((AsyncResult)object2).exception == null) {
                            bl = true;
                        }
                        if (!bl) {
                            IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = IccPhoneBookInterfaceManager.this;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("EVENT_UPDATE_DONE - failed; ex=");
                            ((StringBuilder)object).append(((AsyncResult)object2).exception);
                            iccPhoneBookInterfaceManager.loge(((StringBuilder)object).toString());
                        }
                        this.notifyPending(request, bl);
                    }
                } else {
                    object = null;
                    if (((AsyncResult)object2).exception == null) {
                        object = (List)((AsyncResult)object2).result;
                    } else {
                        IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = IccPhoneBookInterfaceManager.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("EVENT_LOAD_DONE: Cannot load ADN records; ex=");
                        stringBuilder.append(((AsyncResult)object2).exception);
                        iccPhoneBookInterfaceManager.loge(stringBuilder.toString());
                    }
                    this.notifyPending(request, object);
                }
            } else {
                object = null;
                if (((AsyncResult)object2).exception == null) {
                    object = (int[])((AsyncResult)object2).result;
                    IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = IccPhoneBookInterfaceManager.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("GET_RECORD_SIZE Size ");
                    ((StringBuilder)object2).append((int)object[0]);
                    ((StringBuilder)object2).append(" total ");
                    ((StringBuilder)object2).append((int)object[1]);
                    ((StringBuilder)object2).append(" #record ");
                    ((StringBuilder)object2).append((int)object[2]);
                    iccPhoneBookInterfaceManager.logd(((StringBuilder)object2).toString());
                } else {
                    IccPhoneBookInterfaceManager iccPhoneBookInterfaceManager = IccPhoneBookInterfaceManager.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("EVENT_GET_SIZE_DONE: failed; ex=");
                    stringBuilder.append(((AsyncResult)object2).exception);
                    iccPhoneBookInterfaceManager.loge(stringBuilder.toString());
                }
                this.notifyPending(request, object);
            }
        }
    };
    @UnsupportedAppUsage
    protected Phone mPhone;

    public IccPhoneBookInterfaceManager(Phone handler) {
        this.mPhone = handler;
        handler = handler.getIccRecords();
        if (handler != null) {
            this.mAdnCache = handler.getAdnCache();
        }
    }

    @UnsupportedAppUsage
    private int updateEfForIccType(int n) {
        if (n == 28474 && this.mPhone.getCurrentUiccAppType() == IccCardApplicationStatus.AppType.APPTYPE_USIM) {
            return 20272;
        }
        return n;
    }

    @UnsupportedAppUsage
    protected void checkThread() {
        if (!this.mBaseHandler.getLooper().equals((Object)Looper.myLooper())) {
            return;
        }
        this.loge("query() called on the main UI thread!");
        throw new IllegalStateException("You cannot call query on this provder from the main UI thread.");
    }

    public void dispose() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<AdnRecord> getAdnRecordsInEf(int n) {
        if (this.mPhone.getContext().checkCallingOrSelfPermission("android.permission.READ_CONTACTS") != 0) {
            throw new SecurityException("Requires android.permission.READ_CONTACTS permission");
        }
        n = this.updateEfForIccType(n);
        Object object = new StringBuilder();
        ((StringBuilder)object).append("getAdnRecordsInEF: efid=0x");
        ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
        this.logd(((StringBuilder)object).toString());
        this.checkThread();
        object = new Request();
        synchronized (object) {
            Message message = this.mBaseHandler.obtainMessage(2, object);
            if (this.mAdnCache != null) {
                this.mAdnCache.requestLoadAllAdnLike(n, this.mAdnCache.extensionEfForEf(n), message);
                this.waitForResult((Request)object);
            } else {
                this.loge("Failure while trying to load from SIM due to uninitialised adncache");
            }
            return (List)((Request)object).mResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] getAdnRecordsSize(int n) {
        int[] arrn = new StringBuilder();
        arrn.append("getAdnRecordsSize: efid=");
        arrn.append(n);
        this.logd(arrn.toString());
        this.checkThread();
        arrn = new Request();
        synchronized (arrn) {
            Message message = this.mBaseHandler.obtainMessage(1, (Object)arrn);
            IccFileHandler iccFileHandler = this.mPhone.getIccFileHandler();
            if (iccFileHandler != null) {
                iccFileHandler.getEFLinearRecordSize(n, message);
                this.waitForResult((Request)arrn);
            }
        }
        if (arrn.mResult != null) return (int[])arrn.mResult;
        return new int[3];
    }

    @UnsupportedAppUsage
    protected void logd(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[IccPbInterfaceManager] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[IccPbInterfaceManager] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean updateAdnRecordsInEfByIndex(int n, String string, String string2, int n2, String string3) {
        if (this.mPhone.getContext().checkCallingOrSelfPermission("android.permission.WRITE_CONTACTS") != 0) {
            throw new SecurityException("Requires android.permission.WRITE_CONTACTS permission");
        }
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateAdnRecordsInEfByIndex: efid=0x");
        ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
        ((StringBuilder)object).append(" Index=");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" ==> (");
        ((StringBuilder)object).append(Rlog.pii((String)LOG_TAG, (Object)string));
        ((StringBuilder)object).append(",");
        ((StringBuilder)object).append(Rlog.pii((String)LOG_TAG, (Object)string2));
        ((StringBuilder)object).append(") pin2=");
        ((StringBuilder)object).append(Rlog.pii((String)LOG_TAG, (Object)string3));
        this.logd(((StringBuilder)object).toString());
        this.checkThread();
        object = new Request();
        synchronized (object) {
            Message message = this.mBaseHandler.obtainMessage(3, object);
            AdnRecord adnRecord = new AdnRecord(string, string2);
            if (this.mAdnCache != null) {
                this.mAdnCache.updateAdnByIndex(n, adnRecord, n2, string3, message);
                this.waitForResult((Request)object);
            } else {
                this.loge("Failure while trying to update by index due to uninitialised adncache");
            }
            return (Boolean)((Request)object).mResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean updateAdnRecordsInEfBySearch(int n, String object, String string, String string2, String string3, String string4) {
        if (this.mPhone.getContext().checkCallingOrSelfPermission("android.permission.WRITE_CONTACTS") != 0) {
            throw new SecurityException("Requires android.permission.WRITE_CONTACTS permission");
        }
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("updateAdnRecordsInEfBySearch: efid=0x");
        ((StringBuilder)object2).append(Integer.toHexString(n).toUpperCase());
        ((StringBuilder)object2).append(" (");
        ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)object));
        ((StringBuilder)object2).append(",");
        ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string));
        ((StringBuilder)object2).append(")==> (");
        ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string2));
        ((StringBuilder)object2).append(",");
        ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string3));
        ((StringBuilder)object2).append(") pin2=");
        ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string4));
        this.logd(((StringBuilder)object2).toString());
        n = this.updateEfForIccType(n);
        this.checkThread();
        object2 = new Request();
        synchronized (object2) {
            Message message = this.mBaseHandler.obtainMessage(3, object2);
            AdnRecord adnRecord = new AdnRecord((String)object, string);
            object = new AdnRecord(string2, string3);
            if (this.mAdnCache != null) {
                this.mAdnCache.updateAdnBySearch(n, adnRecord, (AdnRecord)object, string4, message);
                this.waitForResult((Request)object2);
            } else {
                this.loge("Failure while trying to update by search due to uninitialised adncache");
            }
            return (Boolean)((Request)object2).mResult;
        }
    }

    public void updateIccRecords(IccRecords iccRecords) {
        this.mAdnCache = iccRecords != null ? iccRecords.getAdnCache() : null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void waitForResult(Request request) {
        synchronized (request) {
            boolean bl;
            while (!(bl = request.mStatus.get())) {
                try {
                    request.wait();
                }
                catch (InterruptedException interruptedException) {
                    this.logd("interrupted while trying to update by search");
                    continue;
                }
                break;
            }
            return;
        }
    }

    private static final class Request {
        Object mResult = null;
        AtomicBoolean mStatus = new AtomicBoolean(false);

        private Request() {
        }
    }

}

