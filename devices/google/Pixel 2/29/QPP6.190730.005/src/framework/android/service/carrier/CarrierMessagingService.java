/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.carrier.ICarrierMessagingCallback;
import android.service.carrier.ICarrierMessagingService;
import android.service.carrier.MessagePdu;
import java.util.List;

public abstract class CarrierMessagingService
extends Service {
    public static final int DOWNLOAD_STATUS_ERROR = 2;
    public static final int DOWNLOAD_STATUS_OK = 0;
    public static final int DOWNLOAD_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final int RECEIVE_OPTIONS_DEFAULT = 0;
    public static final int RECEIVE_OPTIONS_DROP = 1;
    public static final int RECEIVE_OPTIONS_SKIP_NOTIFY_WHEN_CREDENTIAL_PROTECTED_STORAGE_UNAVAILABLE = 2;
    public static final int SEND_FLAG_REQUEST_DELIVERY_STATUS = 1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_OK = 0;
    public static final int SEND_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final String SERVICE_INTERFACE = "android.service.carrier.CarrierMessagingService";
    private final ICarrierMessagingWrapper mWrapper = new ICarrierMessagingWrapper();

    @Override
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        return this.mWrapper;
    }

    public void onDownloadMms(Uri uri, int n, Uri uri2, ResultCallback<Integer> resultCallback) {
        try {
            resultCallback.onReceiveResult(1);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Deprecated
    public void onFilterSms(MessagePdu messagePdu, String string2, int n, int n2, ResultCallback<Boolean> resultCallback) {
        try {
            resultCallback.onReceiveResult(true);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onReceiveTextSms(MessagePdu messagePdu, String string2, int n, int n2, final ResultCallback<Integer> resultCallback) {
        this.onFilterSms(messagePdu, string2, n, n2, new ResultCallback<Boolean>(){

            @Override
            public void onReceiveResult(Boolean bl) throws RemoteException {
                ResultCallback resultCallback2 = resultCallback;
                int n = bl != false ? 0 : 3;
                resultCallback2.onReceiveResult(n);
            }
        });
    }

    public void onSendDataSms(byte[] arrby, int n, String string2, int n2, int n3, ResultCallback<SendSmsResult> resultCallback) {
        this.onSendDataSms(arrby, n, string2, n2, resultCallback);
    }

    @Deprecated
    public void onSendDataSms(byte[] object, int n, String string2, int n2, ResultCallback<SendSmsResult> resultCallback) {
        try {
            object = new SendSmsResult(1, 0);
            resultCallback.onReceiveResult((SendSmsResult)object);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSendMms(Uri object, int n, Uri uri, ResultCallback<SendMmsResult> resultCallback) {
        try {
            object = new SendMmsResult(1, null);
            resultCallback.onReceiveResult((SendMmsResult)object);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSendMultipartTextSms(List<String> list, int n, String string2, int n2, ResultCallback<SendMultipartSmsResult> resultCallback) {
        this.onSendMultipartTextSms(list, n, string2, resultCallback);
    }

    @Deprecated
    public void onSendMultipartTextSms(List<String> object, int n, String string2, ResultCallback<SendMultipartSmsResult> resultCallback) {
        try {
            object = new SendMultipartSmsResult(1, null);
            resultCallback.onReceiveResult((SendMultipartSmsResult)object);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSendTextSms(String string2, int n, String string3, int n2, ResultCallback<SendSmsResult> resultCallback) {
        this.onSendTextSms(string2, n, string3, resultCallback);
    }

    @Deprecated
    public void onSendTextSms(String object, int n, String string2, ResultCallback<SendSmsResult> resultCallback) {
        try {
            object = new SendSmsResult(1, 0);
            resultCallback.onReceiveResult((SendSmsResult)object);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private class ICarrierMessagingWrapper
    extends ICarrierMessagingService.Stub {
        private ICarrierMessagingWrapper() {
        }

        @Override
        public void downloadMms(Uri uri, int n, Uri uri2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onDownloadMms(uri, n, uri2, new ResultCallback<Integer>(){

                @Override
                public void onReceiveResult(Integer n) throws RemoteException {
                    iCarrierMessagingCallback.onDownloadMmsComplete(n);
                }
            });
        }

        @Override
        public void filterSms(MessagePdu messagePdu, String string2, int n, int n2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onReceiveTextSms(messagePdu, string2, n, n2, new ResultCallback<Integer>(){

                @Override
                public void onReceiveResult(Integer n) throws RemoteException {
                    iCarrierMessagingCallback.onFilterComplete(n);
                }
            });
        }

        @Override
        public void sendDataSms(byte[] arrby, int n, String string2, int n2, int n3, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendDataSms(arrby, n, string2, n2, n3, new ResultCallback<SendSmsResult>(){

                @Override
                public void onReceiveResult(SendSmsResult sendSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

        @Override
        public void sendMms(Uri uri, int n, Uri uri2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendMms(uri, n, uri2, new ResultCallback<SendMmsResult>(){

                @Override
                public void onReceiveResult(SendMmsResult sendMmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendMmsComplete(sendMmsResult.getSendStatus(), sendMmsResult.getSendConfPdu());
                }
            });
        }

        @Override
        public void sendMultipartTextSms(List<String> list, int n, String string2, int n2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendMultipartTextSms(list, n, string2, n2, new ResultCallback<SendMultipartSmsResult>(){

                @Override
                public void onReceiveResult(SendMultipartSmsResult sendMultipartSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendMultipartSmsComplete(sendMultipartSmsResult.getSendStatus(), sendMultipartSmsResult.getMessageRefs());
                }
            });
        }

        @Override
        public void sendTextSms(String string2, int n, String string3, int n2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendTextSms(string2, n, string3, n2, new ResultCallback<SendSmsResult>(){

                @Override
                public void onReceiveResult(SendSmsResult sendSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

    }

    public static interface ResultCallback<T> {
        public void onReceiveResult(T var1) throws RemoteException;
    }

    public static final class SendMmsResult {
        private byte[] mSendConfPdu;
        private int mSendStatus;

        public SendMmsResult(int n, byte[] arrby) {
            this.mSendStatus = n;
            this.mSendConfPdu = arrby;
        }

        public byte[] getSendConfPdu() {
            return this.mSendConfPdu;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    public static final class SendMultipartSmsResult {
        private final int[] mMessageRefs;
        private final int mSendStatus;

        public SendMultipartSmsResult(int n, int[] arrn) {
            this.mSendStatus = n;
            this.mMessageRefs = arrn;
        }

        public int[] getMessageRefs() {
            return this.mMessageRefs;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    public static final class SendSmsResult {
        private final int mMessageRef;
        private final int mSendStatus;

        public SendSmsResult(int n, int n2) {
            this.mSendStatus = n;
            this.mMessageRef = n2;
        }

        public int getMessageRef() {
            return this.mMessageRef;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

}

