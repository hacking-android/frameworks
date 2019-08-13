/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RemoteException
 *  android.telephony.Rlog
 *  android.telephony.ims.ImsCallForwardInfo
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.ImsSsData
 *  android.telephony.ims.ImsSsInfo
 *  com.android.ims.ImsException
 *  com.android.ims.ImsUtInterface
 *  com.android.ims.internal.IImsUt
 *  com.android.ims.internal.IImsUtListener
 *  com.android.ims.internal.IImsUtListener$Stub
 */
package com.android.ims;

import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Registrant;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import com.android.ims.ImsException;
import com.android.ims.ImsUtInterface;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImsUt
implements ImsUtInterface {
    public static final String CATEGORY_CB = "CB";
    public static final String CATEGORY_CDIV = "CDIV";
    public static final String CATEGORY_CONF = "CONF";
    public static final String CATEGORY_CW = "CW";
    public static final String CATEGORY_OIP = "OIP";
    public static final String CATEGORY_OIR = "OIR";
    public static final String CATEGORY_TIP = "TIP";
    public static final String CATEGORY_TIR = "TIR";
    private static final boolean DBG = true;
    public static final String KEY_ACTION = "action";
    public static final String KEY_CATEGORY = "category";
    private static final int SERVICE_CLASS_NONE = 0;
    private static final int SERVICE_CLASS_VOICE = 1;
    private static final String TAG = "ImsUt";
    private Object mLockObj = new Object();
    private HashMap<Integer, Message> mPendingCmds = new HashMap();
    private Registrant mSsIndicationRegistrant;
    private final IImsUt miUt;

    public ImsUt(IImsUt object) {
        IImsUt iImsUt = this.miUt = object;
        if (iImsUt != null) {
            try {
                iImsUt.setListener((IImsUtListener)object);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    private void sendFailureReport(Message message, ImsReasonInfo imsReasonInfo) {
        if (message != null && imsReasonInfo != null) {
            String string = imsReasonInfo.mExtraMessage == null ? Resources.getSystem().getString(17040444) : new String(imsReasonInfo.mExtraMessage);
            AsyncResult.forMessage((Message)message, null, (Throwable)new ImsException(string, imsReasonInfo.mCode));
            message.sendToTarget();
            return;
        }
    }

    private void sendSuccessReport(Message message) {
        if (message == null) {
            return;
        }
        AsyncResult.forMessage((Message)message, null, null);
        message.sendToTarget();
    }

    private void sendSuccessReport(Message message, Object object) {
        if (message == null) {
            return;
        }
        AsyncResult.forMessage((Message)message, (Object)object, null);
        message.sendToTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        Object object = this.mLockObj;
        synchronized (object) {
            IImsUt iImsUt = this.miUt;
            if (iImsUt != null) {
                try {
                    this.miUt.close();
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            if (!this.mPendingCmds.isEmpty()) {
                Map.Entry[] arrentry = this.mPendingCmds.entrySet().toArray(new Map.Entry[this.mPendingCmds.size()]);
                int n = arrentry.length;
                for (int i = 0; i < n; ++i) {
                    iImsUt = (Message)arrentry[i].getValue();
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport((Message)iImsUt, imsReasonInfo);
                }
                this.mPendingCmds.clear();
            }
            return;
        }
    }

    public boolean isBinderAlive() {
        return this.miUt.asBinder().isBinderAlive();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCLIP(Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCLIP :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.queryCLIP();
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCLIR(Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCLIR :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.queryCLIR();
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCOLP(Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCOLP :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.queryCOLP();
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCOLR(Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCOLR :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.queryCOLR();
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public void queryCallBarring(int n, Message message) {
        this.queryCallBarring(n, message, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCallBarring(int n, Message message, int n2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCallBarring :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", cbType=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", serviceClass=");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.queryCallBarringForServiceClass(n, n2);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCallForward(int n, String string, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCallForward :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", condition=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", number=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string));
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.queryCallForward(n, string);
                    if (n < 0) {
                        string = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, (ImsReasonInfo)string);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void queryCallWaiting(Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("queryCallWaiting :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.queryCallWaiting();
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public void registerForSuppServiceIndication(Handler handler, int n, Object object) {
        this.mSsIndicationRegistrant = new Registrant(handler, n, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void transact(Bundle bundle, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("transact :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", ssInfo=");
        ((StringBuilder)object).append((Object)bundle);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.transact(bundle);
                    if (n < 0) {
                        bundle = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, (ImsReasonInfo)bundle);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public void unregisterForSuppServiceIndication(Handler handler) {
        this.mSsIndicationRegistrant.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCLIP(boolean bl, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCLIP :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", enable=");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.updateCLIP(bl);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCLIR(int n, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCLIR :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", clirMode=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.updateCLIR(n);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCOLP(boolean bl, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCallWaiting :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", enable=");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    int n = this.miUt.updateCOLP(bl);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCOLR(int n, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCOLR :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", presentation=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.updateCOLR(n);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    public void updateCallBarring(int n, int n2, Message message, String[] arrstring) {
        this.updateCallBarring(n, n2, message, arrstring, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCallBarring(int n, int n2, Message message, String[] imsReasonInfo, int n3) {
        Object object;
        if (imsReasonInfo != null) {
            StringBuilder stringBuilder;
            object = new String();
            for (int i = 0; i < ((ImsReasonInfo)imsReasonInfo).length; ++i) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)imsReasonInfo[i]);
                stringBuilder.append(" ");
                ((String)object).concat(stringBuilder.toString());
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateCallBarring :: Ut=");
            stringBuilder.append((Object)this.miUt);
            stringBuilder.append(", cbType=");
            stringBuilder.append(n);
            stringBuilder.append(", action=");
            stringBuilder.append(n2);
            stringBuilder.append(", serviceClass=");
            stringBuilder.append(n3);
            stringBuilder.append(", barrList=");
            stringBuilder.append((String)object);
            this.log(stringBuilder.toString());
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateCallBarring :: Ut=");
            ((StringBuilder)object).append((Object)this.miUt);
            ((StringBuilder)object).append(", cbType=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", action=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(", serviceClass=");
            ((StringBuilder)object).append(n3);
            this.log(((StringBuilder)object).toString());
        }
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.updateCallBarringForServiceClass(n, n2, (String[])imsReasonInfo, n3);
                    if (n < 0) {
                        imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo2 = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo2);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCallForward(int n, int n2, String string, int n3, int n4, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCallForward :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", action=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", condition=");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(", number=");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string));
        ((StringBuilder)object).append(", serviceClass=");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(", timeSeconds=");
        ((StringBuilder)object).append(n4);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.updateCallForward(n, n2, string, n3, n4);
                    if (n < 0) {
                        string = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, (ImsReasonInfo)string);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCallWaiting(boolean bl, int n, Message message) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateCallWaiting :: Ut=");
        ((StringBuilder)object).append((Object)this.miUt);
        ((StringBuilder)object).append(", enable=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(",serviceClass=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            try {
                try {
                    n = this.miUt.updateCallWaiting(bl, n);
                    if (n < 0) {
                        ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                        this.sendFailureReport(message, imsReasonInfo);
                        return;
                    }
                    this.mPendingCmds.put(n, message);
                }
                catch (RemoteException remoteException) {
                    ImsReasonInfo imsReasonInfo = new ImsReasonInfo(802, 0);
                    this.sendFailureReport(message, imsReasonInfo);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private class IImsUtListenerProxy
    extends IImsUtListener.Stub {
        private IImsUtListenerProxy() {
        }

        public void onSupplementaryServiceIndication(ImsSsData imsSsData) {
            if (ImsUt.this.mSsIndicationRegistrant != null) {
                ImsUt.this.mSsIndicationRegistrant.notifyResult((Object)imsSsData);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationCallBarringQueried(IImsUt object, int n, ImsSsInfo[] arrimsSsInfo) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendSuccessReport((Message)ImsUt.this.mPendingCmds.get(n2), arrimsSsInfo);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationCallForwardQueried(IImsUt object, int n, ImsCallForwardInfo[] arrimsCallForwardInfo) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendSuccessReport((Message)ImsUt.this.mPendingCmds.get(n2), arrimsCallForwardInfo);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationCallWaitingQueried(IImsUt object, int n, ImsSsInfo[] arrimsSsInfo) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendSuccessReport((Message)ImsUt.this.mPendingCmds.get(n2), arrimsSsInfo);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationQueried(IImsUt object, int n, Bundle bundle) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendSuccessReport((Message)ImsUt.this.mPendingCmds.get(n2), (Object)bundle);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationQueryFailed(IImsUt object, int n, ImsReasonInfo imsReasonInfo) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendFailureReport((Message)ImsUt.this.mPendingCmds.get(n2), imsReasonInfo);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationUpdateFailed(IImsUt object, int n, ImsReasonInfo imsReasonInfo) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendFailureReport((Message)ImsUt.this.mPendingCmds.get(n2), imsReasonInfo);
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void utConfigurationUpdated(IImsUt object, int n) {
            Integer n2 = n;
            object = ImsUt.this.mLockObj;
            synchronized (object) {
                ImsUt.this.sendSuccessReport((Message)ImsUt.this.mPendingCmds.get(n2));
                ImsUt.this.mPendingCmds.remove(n2);
                return;
            }
        }
    }

}

