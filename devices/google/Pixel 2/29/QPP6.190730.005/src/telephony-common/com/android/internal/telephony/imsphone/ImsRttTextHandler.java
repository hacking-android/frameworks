/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telecom.Connection
 *  android.telecom.Connection$RttTextStream
 *  android.telephony.Rlog
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony.imsphone;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telecom.Connection;
import android.telephony.Rlog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.util.concurrent.CountDownLatch;

public class ImsRttTextHandler
extends Handler {
    private static final int APPEND_TO_NETWORK_BUFFER = 2;
    private static final int ATTEMPT_SEND_TO_NETWORK = 4;
    private static final int EXPIRE_SENT_CODEPOINT_COUNT = 5;
    private static final int INITIALIZE = 1;
    private static final String LOG_TAG = "ImsRttTextHandler";
    public static final int MAX_BUFFERED_CHARACTER_COUNT = 5;
    public static final int MAX_BUFFERING_DELAY_MILLIS = 200;
    public static final int MAX_CODEPOINTS_PER_SECOND = 30;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int SEND_TO_INCALL = 3;
    private static final int TEARDOWN = 9999;
    private StringBuffer mBufferedTextToIncall = new StringBuffer();
    private StringBuffer mBufferedTextToNetwork = new StringBuffer();
    private int mCodepointsAvailableForTransmission = 30;
    private final NetworkWriter mNetworkWriter;
    private CountDownLatch mReadNotifier;
    private InCallReaderThread mReaderThread;
    private Connection.RttTextStream mRttTextStream;

    public ImsRttTextHandler(Looper looper, NetworkWriter networkWriter) {
        super(looper);
        this.mNetworkWriter = networkWriter;
    }

    public String getNetworkBufferText() {
        return this.mBufferedTextToNetwork.toString();
    }

    public void handleMessage(Message object) {
        block26 : {
            block25 : {
                block24 : {
                    int n = ((Message)object).what;
                    if (n == 1) break block24;
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                if (n != 5) {
                                    if (n == 9999) {
                                        try {
                                            if (this.mReaderThread != null) {
                                                this.mReaderThread.interrupt();
                                                this.mReaderThread.join(1000L);
                                            }
                                        }
                                        catch (InterruptedException interruptedException) {
                                            // empty catch block
                                        }
                                        this.mReaderThread = null;
                                        this.mRttTextStream = null;
                                    }
                                } else {
                                    this.mCodepointsAvailableForTransmission += ((Message)object).arg1;
                                    if (this.mCodepointsAvailableForTransmission > 0) {
                                        this.sendMessage(this.obtainMessage(4));
                                    }
                                }
                            } else {
                                object = this.mBufferedTextToNetwork;
                                int n2 = Math.min(((StringBuffer)object).codePointCount(0, ((StringBuffer)object).length()), this.mCodepointsAvailableForTransmission);
                                if (n2 != 0) {
                                    n = this.mBufferedTextToNetwork.offsetByCodePoints(0, n2);
                                    object = this.mBufferedTextToNetwork.substring(0, n);
                                    this.mBufferedTextToNetwork.delete(0, n);
                                    this.mNetworkWriter.write((String)object);
                                    this.mCodepointsAvailableForTransmission -= n2;
                                    this.sendMessageDelayed(this.obtainMessage(5, n2, 0), 1000L);
                                }
                            }
                        } else {
                            object = (String)((Message)object).obj;
                            try {
                                this.mRttTextStream.write((String)object);
                            }
                            catch (IOException iOException) {
                                Rlog.e((String)LOG_TAG, (String)"IOException encountered writing to in-call: %s", (Throwable)iOException);
                                this.obtainMessage(9999).sendToTarget();
                                this.mBufferedTextToIncall.append((String)object);
                            }
                        }
                    } else {
                        this.mBufferedTextToNetwork.append((String)((Message)object).obj);
                        object = this.mBufferedTextToNetwork;
                        if (((StringBuffer)object).codePointCount(0, ((StringBuffer)object).length()) >= 5) {
                            this.sendMessage(this.obtainMessage(4));
                        } else {
                            this.sendEmptyMessageDelayed(4, 200L);
                        }
                    }
                    break block25;
                }
                if (this.mRttTextStream != null || this.mReaderThread != null) break block26;
                this.mRttTextStream = (Connection.RttTextStream)((Message)object).obj;
                this.mReaderThread = new InCallReaderThread(this.mRttTextStream);
                this.mReaderThread.start();
            }
            return;
        }
        Rlog.e((String)LOG_TAG, (String)"RTT text stream already initialized. Ignoring.");
    }

    public void initialize(Connection.RttTextStream rttTextStream) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Initializing: ");
        stringBuilder.append((Object)this);
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        this.obtainMessage(1, (Object)rttTextStream).sendToTarget();
    }

    public void sendToInCall(String string) {
        this.obtainMessage(3, (Object)string).sendToTarget();
    }

    @VisibleForTesting
    public void setReadNotifier(CountDownLatch countDownLatch) {
        this.mReadNotifier = countDownLatch;
    }

    public void tearDown() {
        this.obtainMessage(9999).sendToTarget();
    }

    private class InCallReaderThread
    extends Thread {
        private final Connection.RttTextStream mReaderThreadRttTextStream;

        public InCallReaderThread(Connection.RttTextStream rttTextStream) {
            this.mReaderThreadRttTextStream = rttTextStream;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            do {
                String string;
                block4 : {
                    string = this.mReaderThreadRttTextStream.read();
                    if (string != null) break block4;
                    Rlog.e((String)ImsRttTextHandler.LOG_TAG, (String)"RttReaderThread - Stream closed unexpectedly. Attempt to reinitialize.");
                    ImsRttTextHandler.this.obtainMessage(9999).sendToTarget();
                    return;
                }
                if (string.length() == 0) continue;
                ImsRttTextHandler.this.obtainMessage(2, (Object)string).sendToTarget();
                if (ImsRttTextHandler.this.mReadNotifier == null) continue;
                ImsRttTextHandler.this.mReadNotifier.countDown();
            } while (true);
            catch (IOException iOException) {
                Rlog.e((String)ImsRttTextHandler.LOG_TAG, (String)"RttReaderThread - IOException encountered reading from in-call: ", (Throwable)iOException);
                ImsRttTextHandler.this.obtainMessage(9999).sendToTarget();
                return;
            }
            catch (ClosedByInterruptException closedByInterruptException) {
                Rlog.i((String)ImsRttTextHandler.LOG_TAG, (String)"RttReaderThread - Thread interrupted. Finishing.");
            }
        }
    }

    public static interface NetworkWriter {
        public void write(String var1);
    }

}

