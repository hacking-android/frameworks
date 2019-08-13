/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.telephony.SmsMessage;
import android.telephony.ims.aidl.IImsSmsListener;
import android.util.Log;
import com.android.internal.telephony.SmsMessageBase;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public class ImsSmsImplBase {
    public static final int DELIVER_STATUS_ERROR_GENERIC = 2;
    public static final int DELIVER_STATUS_ERROR_NO_MEMORY = 3;
    public static final int DELIVER_STATUS_ERROR_REQUEST_NOT_SUPPORTED = 4;
    public static final int DELIVER_STATUS_OK = 1;
    private static final String LOG_TAG = "SmsImplBase";
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_ERROR_FALLBACK = 4;
    public static final int SEND_STATUS_ERROR_RETRY = 3;
    public static final int SEND_STATUS_OK = 1;
    public static final int STATUS_REPORT_STATUS_ERROR = 2;
    public static final int STATUS_REPORT_STATUS_OK = 1;
    private IImsSmsListener mListener;
    private final Object mLock = new Object();

    public void acknowledgeSms(int n, int n2, int n3) {
        Log.e(LOG_TAG, "acknowledgeSms() not implemented.");
    }

    public void acknowledgeSmsReport(int n, int n2, int n3) {
        Log.e(LOG_TAG, "acknowledgeSmsReport() not implemented.");
    }

    public String getSmsFormat() {
        return "3gpp";
    }

    public void onReady() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onSendSmsResult(int n, int n2, int n3, int n4) throws RuntimeException {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mListener;
            if (object2 == null) {
                object2 = new RuntimeException("Feature not ready.");
                throw object2;
            }
            try {
                this.mListener.onSendSmsResult(n, n2, n3, n4);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onSmsReceived(int n, String object, byte[] arrby) throws RuntimeException {
        Object object2 = this.mLock;
        synchronized (object2) {
            IImsSmsListener iImsSmsListener = this.mListener;
            if (iImsSmsListener == null) {
                object = new RuntimeException("Feature not ready.");
                throw object;
            }
            try {
                this.mListener.onSmsReceived(n, (String)object, arrby);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can not deliver sms: ");
                stringBuilder.append(remoteException.getMessage());
                Log.e(LOG_TAG, stringBuilder.toString());
                object = SmsMessage.createFromPdu(arrby, (String)object);
                if (object != null && ((SmsMessage)object).mWrappedSmsMessage != null) {
                    this.acknowledgeSms(n, object.mWrappedSmsMessage.mMessageRef, 2);
                }
                Log.w(LOG_TAG, "onSmsReceived: Invalid pdu entered.");
                this.acknowledgeSms(n, 0, 2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onSmsStatusReportReceived(int n, int n2, String object, byte[] arrby) throws RuntimeException {
        Object object2 = this.mLock;
        synchronized (object2) {
            IImsSmsListener iImsSmsListener = this.mListener;
            if (iImsSmsListener == null) {
                object = new RuntimeException("Feature not ready.");
                throw object;
            }
            try {
                this.mListener.onSmsStatusReportReceived(n, n2, (String)object, arrby);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can not process sms status report: ");
                ((StringBuilder)object).append(remoteException.getMessage());
                Log.e(LOG_TAG, ((StringBuilder)object).toString());
                this.acknowledgeSmsReport(n, n2, 2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void registerSmsListener(IImsSmsListener iImsSmsListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mListener = iImsSmsListener;
            return;
        }
    }

    public void sendSms(int n, int n2, String string2, String charSequence, boolean bl, byte[] arrby) {
        try {
            this.onSendSmsResult(n, n2, 2, 1);
        }
        catch (RuntimeException runtimeException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Can not send sms: ");
            ((StringBuilder)charSequence).append(runtimeException.getMessage());
            Log.e(LOG_TAG, ((StringBuilder)charSequence).toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DeliverStatusResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SendStatusResult {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StatusReportResult {
    }

}

