/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.telephony.IccOpenLogicalChannelResponse
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc.euicc.apdu;

import android.os.Handler;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.euicc.apdu.ApduCommand;
import com.android.internal.telephony.uicc.euicc.apdu.ApduException;
import com.android.internal.telephony.uicc.euicc.apdu.ApduSenderResultCallback;
import com.android.internal.telephony.uicc.euicc.apdu.CloseLogicalChannelInvocation;
import com.android.internal.telephony.uicc.euicc.apdu.OpenLogicalChannelInvocation;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;
import com.android.internal.telephony.uicc.euicc.apdu.RequestProvider;
import com.android.internal.telephony.uicc.euicc.apdu.TransmitApduLogicalChannelInvocation;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ApduSender {
    private static final int INS_GET_MORE_RESPONSE = 192;
    private static final String LOG_TAG = "ApduSender";
    private static final int STATUS_NO_ERROR = 36864;
    private static final int SW1_MORE_RESPONSE = 97;
    private static final int SW1_NO_ERROR = 145;
    private final String mAid;
    private final Object mChannelLock = new Object();
    private boolean mChannelOpened;
    private final CloseLogicalChannelInvocation mCloseChannel;
    private final OpenLogicalChannelInvocation mOpenChannel;
    private final boolean mSupportExtendedApdu;
    private final TransmitApduLogicalChannelInvocation mTransmitApdu;

    public ApduSender(CommandsInterface commandsInterface, String string, boolean bl) {
        this.mAid = string;
        this.mSupportExtendedApdu = bl;
        this.mOpenChannel = new OpenLogicalChannelInvocation(commandsInterface);
        this.mCloseChannel = new CloseLogicalChannelInvocation(commandsInterface);
        this.mTransmitApdu = new TransmitApduLogicalChannelInvocation(commandsInterface);
    }

    private void closeAndReturn(int n, final byte[] arrby, final Throwable throwable, final ApduSenderResultCallback apduSenderResultCallback, Handler handler) {
        this.mCloseChannel.invoke(n, new AsyncResultCallback<Boolean>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onResult(Boolean object) {
                object = ApduSender.this.mChannelLock;
                synchronized (object) {
                    ApduSender.this.mChannelOpened = false;
                }
                object = throwable;
                if (object == null) {
                    apduSenderResultCallback.onResult(arrby);
                    return;
                }
                apduSenderResultCallback.onException((Throwable)object);
            }
        }, handler);
    }

    private void getCompleteResponse(final int n, IccIoResult iccIoResult, final ByteArrayOutputStream byteArrayOutputStream, final AsyncResultCallback<IccIoResult> asyncResultCallback, final Handler handler) {
        if (byteArrayOutputStream == null) {
            byteArrayOutputStream = new ByteArrayOutputStream();
        }
        if (iccIoResult.payload != null) {
            try {
                byteArrayOutputStream.write(iccIoResult.payload);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (iccIoResult.sw1 != 97) {
            iccIoResult.payload = byteArrayOutputStream.toByteArray();
            asyncResultCallback.onResult(iccIoResult);
            return;
        }
        this.mTransmitApdu.invoke(new ApduCommand(n, 0, 192, 0, 0, iccIoResult.sw2, ""), new AsyncResultCallback<IccIoResult>(){

            @Override
            public void onResult(IccIoResult iccIoResult) {
                ApduSender.this.getCompleteResponse(n, iccIoResult, byteArrayOutputStream, asyncResultCallback, handler);
            }
        }, handler);
    }

    private static void logv(String string) {
        Rlog.v((String)LOG_TAG, (String)string);
    }

    private void sendCommand(final List<ApduCommand> list, final int n, final ApduSenderResultCallback apduSenderResultCallback, final Handler handler) {
        final ApduCommand apduCommand = list.get(n);
        this.mTransmitApdu.invoke(apduCommand, new AsyncResultCallback<IccIoResult>(){

            @Override
            public void onResult(IccIoResult iccIoResult) {
                ApduSender.this.getCompleteResponse(apduCommand.channel, iccIoResult, null, new AsyncResultCallback<IccIoResult>(){

                    @Override
                    public void onResult(IccIoResult iccIoResult) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Full APDU response: ");
                        stringBuilder.append(iccIoResult);
                        ApduSender.logv(stringBuilder.toString());
                        int n = iccIoResult.sw1 << 8 | iccIoResult.sw2;
                        if (n != 36864 && iccIoResult.sw1 != 145) {
                            ApduSender.this.closeAndReturn(apduCommand.channel, null, new ApduException(n), apduSenderResultCallback, handler);
                            return;
                        }
                        n = n < list.size() - 1 && apduSenderResultCallback.shouldContinueOnIntermediateResult(iccIoResult) ? 1 : 0;
                        if (n != 0) {
                            ApduSender.this.sendCommand(list, n + 1, apduSenderResultCallback, handler);
                        } else {
                            ApduSender.this.closeAndReturn(apduCommand.channel, iccIoResult.payload, null, apduSenderResultCallback, handler);
                        }
                    }
                }, handler);
            }

        }, handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void send(RequestProvider object, final ApduSenderResultCallback apduSenderResultCallback, Handler handler) {
        Object object2 = this.mChannelLock;
        synchronized (object2) {
            if (this.mChannelOpened) {
                object = new ApduException("Logical channel has already been opened.");
                AsyncResultHelper.throwException((Throwable)object, apduSenderResultCallback, handler);
                return;
            }
            this.mChannelOpened = true;
        }
        this.mOpenChannel.invoke(this.mAid, new AsyncResultCallback<IccOpenLogicalChannelResponse>((RequestProvider)object, handler){
            final /* synthetic */ Handler val$handler;
            final /* synthetic */ RequestProvider val$requestProvider;
            {
                this.val$requestProvider = requestProvider;
                this.val$handler = handler;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onResult(IccOpenLogicalChannelResponse object) {
                int n = object.getChannel();
                int n2 = object.getStatus();
                if (n != -1 && n2 == 1) {
                    RequestBuilder requestBuilder = new RequestBuilder(n, ApduSender.this.mSupportExtendedApdu);
                    try {
                        this.val$requestProvider.buildRequest(object.getSelectResponse(), requestBuilder);
                        object = null;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                    if (!requestBuilder.getCommands().isEmpty() && object == null) {
                        ApduSender.this.sendCommand(requestBuilder.getCommands(), 0, apduSenderResultCallback, this.val$handler);
                        return;
                    }
                    ApduSender.this.closeAndReturn(n, null, (Throwable)object, apduSenderResultCallback, this.val$handler);
                    return;
                }
                Object object2 = ApduSender.this.mChannelLock;
                synchronized (object2) {
                    ApduSender.this.mChannelOpened = false;
                }
                object2 = apduSenderResultCallback;
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to open logical channel opened for AID: ");
                ((StringBuilder)object).append(ApduSender.this.mAid);
                ((StringBuilder)object).append(", with status: ");
                ((StringBuilder)object).append(n2);
                ((AsyncResultCallback)object2).onException(new ApduException(((StringBuilder)object).toString()));
            }
        }, handler);
    }

}

