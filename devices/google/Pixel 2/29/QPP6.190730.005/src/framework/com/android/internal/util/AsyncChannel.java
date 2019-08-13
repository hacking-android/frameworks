/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.util.AsyncService;
import java.util.Objects;
import java.util.Stack;

public class AsyncChannel {
    private static final int BASE = 69632;
    public static final int CMD_CHANNEL_DISCONNECT = 69635;
    public static final int CMD_CHANNEL_DISCONNECTED = 69636;
    public static final int CMD_CHANNEL_FULLY_CONNECTED = 69634;
    @UnsupportedAppUsage
    public static final int CMD_CHANNEL_FULL_CONNECTION = 69633;
    @UnsupportedAppUsage
    public static final int CMD_CHANNEL_HALF_CONNECTED = 69632;
    private static final int CMD_TO_STRING_COUNT = 5;
    private static final boolean DBG = false;
    public static final int STATUS_BINDING_UNSUCCESSFUL = 1;
    public static final int STATUS_FULL_CONNECTION_REFUSED_ALREADY_CONNECTED = 3;
    public static final int STATUS_REMOTE_DISCONNECTION = 4;
    public static final int STATUS_SEND_UNSUCCESSFUL = 2;
    @UnsupportedAppUsage
    public static final int STATUS_SUCCESSFUL = 0;
    private static final String TAG = "AsyncChannel";
    private static String[] sCmdToString;
    private AsyncChannelConnection mConnection;
    private DeathMonitor mDeathMonitor;
    private Messenger mDstMessenger;
    private Context mSrcContext;
    private Handler mSrcHandler;
    private Messenger mSrcMessenger;

    static {
        String[] arrstring = sCmdToString = new String[5];
        arrstring[0] = "CMD_CHANNEL_HALF_CONNECTED";
        arrstring[1] = "CMD_CHANNEL_FULL_CONNECTION";
        arrstring[2] = "CMD_CHANNEL_FULLY_CONNECTED";
        arrstring[3] = "CMD_CHANNEL_DISCONNECT";
        arrstring[4] = "CMD_CHANNEL_DISCONNECTED";
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected static String cmdToString(int n) {
        String[] arrstring;
        if ((n -= 69632) >= 0 && n < (arrstring = sCmdToString).length) {
            return arrstring[n];
        }
        return null;
    }

    private boolean linkToDeathMonitor() {
        if (this.mConnection == null && this.mDeathMonitor == null) {
            this.mDeathMonitor = new DeathMonitor();
            try {
                this.mDstMessenger.getBinder().linkToDeath(this.mDeathMonitor, 0);
            }
            catch (RemoteException remoteException) {
                this.mDeathMonitor = null;
                return false;
            }
        }
        return true;
    }

    private static void log(String string2) {
        Slog.d(TAG, string2);
    }

    private void replyDisconnected(int n) {
        Object object = this.mSrcHandler;
        if (object == null) {
            return;
        }
        object = ((Handler)object).obtainMessage(69636);
        ((Message)object).arg1 = n;
        ((Message)object).obj = this;
        ((Message)object).replyTo = this.mDstMessenger;
        this.mSrcHandler.sendMessage((Message)object);
    }

    private void replyHalfConnected(int n) {
        Message message = this.mSrcHandler.obtainMessage(69632);
        message.arg1 = n;
        message.obj = this;
        message.replyTo = this.mDstMessenger;
        if (!this.linkToDeathMonitor()) {
            message.arg1 = 1;
        }
        this.mSrcHandler.sendMessage(message);
    }

    public void connect(Context context, Handler handler, Handler handler2) {
        this.connect(context, handler, new Messenger(handler2));
    }

    @UnsupportedAppUsage
    public void connect(Context context, Handler handler, Messenger messenger) {
        this.connected(context, handler, messenger);
        this.replyHalfConnected(0);
    }

    public void connect(Context context, Handler handler, Class<?> class_) {
        this.connect(context, handler, class_.getPackage().getName(), class_.getName());
    }

    public void connect(Context context, Handler handler, String string2, String string3) {
        new Thread(new 1ConnectAsync(context, handler, string2, string3)).start();
    }

    public void connect(AsyncService asyncService, Messenger messenger) {
        this.connect((Context)asyncService, asyncService.getHandler(), messenger);
    }

    public int connectSrcHandlerToPackageSync(Context context, Handler object, String string2, String string3) {
        this.mConnection = new AsyncChannelConnection();
        this.mSrcContext = context;
        this.mSrcHandler = object;
        this.mSrcMessenger = new Messenger((Handler)object);
        this.mDstMessenger = null;
        object = new Intent("android.intent.action.MAIN");
        ((Intent)object).setClassName(string2, string3);
        return context.bindService((Intent)object, this.mConnection, 1) ^ true;
    }

    public int connectSync(Context context, Handler handler, Handler handler2) {
        return this.connectSync(context, handler, new Messenger(handler2));
    }

    @UnsupportedAppUsage
    public int connectSync(Context context, Handler handler, Messenger messenger) {
        this.connected(context, handler, messenger);
        return 0;
    }

    @UnsupportedAppUsage
    public void connected(Context context, Handler handler, Messenger messenger) {
        this.mSrcContext = context;
        this.mSrcHandler = handler;
        this.mSrcMessenger = new Messenger(this.mSrcHandler);
        this.mDstMessenger = messenger;
    }

    @UnsupportedAppUsage
    public void disconnect() {
        Context context;
        Object object = this.mConnection;
        if (object != null && (context = this.mSrcContext) != null) {
            context.unbindService((ServiceConnection)object);
            this.mConnection = null;
        }
        try {
            object = Message.obtain();
            ((Message)object).what = 69636;
            ((Message)object).replyTo = this.mSrcMessenger;
            this.mDstMessenger.send((Message)object);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.replyDisconnected(0);
        this.mSrcHandler = null;
        if (this.mConnection == null && (object = this.mDstMessenger) != null && this.mDeathMonitor != null) {
            ((Messenger)object).getBinder().unlinkToDeath(this.mDeathMonitor, 0);
            this.mDeathMonitor = null;
        }
    }

    public void disconnected() {
        this.mSrcContext = null;
        this.mSrcHandler = null;
        this.mSrcMessenger = null;
        this.mDstMessenger = null;
        this.mDeathMonitor = null;
        this.mConnection = null;
    }

    public int fullyConnectSync(Context context, Handler handler, Handler handler2) {
        int n;
        int n2 = n = this.connectSync(context, handler, handler2);
        if (n == 0) {
            n2 = this.sendMessageSynchronously((int)69633).arg1;
        }
        return n2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void replyToMessage(Message message, int n) {
        Message message2 = Message.obtain();
        message2.what = n;
        this.replyToMessage(message, message2);
    }

    @UnsupportedAppUsage
    public void replyToMessage(Message message, int n, int n2) {
        Message message2 = Message.obtain();
        message2.what = n;
        message2.arg1 = n2;
        this.replyToMessage(message, message2);
    }

    public void replyToMessage(Message message, int n, int n2, int n3) {
        Message message2 = Message.obtain();
        message2.what = n;
        message2.arg1 = n2;
        message2.arg2 = n3;
        this.replyToMessage(message, message2);
    }

    @UnsupportedAppUsage
    public void replyToMessage(Message message, int n, int n2, int n3, Object object) {
        Message message2 = Message.obtain();
        message2.what = n;
        message2.arg1 = n2;
        message2.arg2 = n3;
        message2.obj = object;
        this.replyToMessage(message, message2);
    }

    @UnsupportedAppUsage
    public void replyToMessage(Message message, int n, Object object) {
        Message message2 = Message.obtain();
        message2.what = n;
        message2.obj = object;
        this.replyToMessage(message, message2);
    }

    @UnsupportedAppUsage
    public void replyToMessage(Message message, Message object) {
        try {
            ((Message)object).replyTo = this.mSrcMessenger;
            message.replyTo.send((Message)object);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("TODO: handle replyToMessage RemoteException");
            ((StringBuilder)object).append(remoteException);
            AsyncChannel.log(((StringBuilder)object).toString());
            remoteException.printStackTrace();
        }
    }

    @UnsupportedAppUsage
    public void sendMessage(int n) {
        Message message = Message.obtain();
        message.what = n;
        this.sendMessage(message);
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, int n2) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        this.sendMessage(message);
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, int n2, int n3) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        this.sendMessage(message);
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, int n2, int n3, Object object) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        message.obj = object;
        this.sendMessage(message);
    }

    public void sendMessage(int n, Object object) {
        Message message = Message.obtain();
        message.what = n;
        message.obj = object;
        this.sendMessage(message);
    }

    @UnsupportedAppUsage
    public void sendMessage(Message message) {
        message.replyTo = this.mSrcMessenger;
        try {
            this.mDstMessenger.send(message);
        }
        catch (RemoteException remoteException) {
            this.replyDisconnected(2);
        }
    }

    public Message sendMessageSynchronously(int n) {
        Message message = Message.obtain();
        message.what = n;
        return this.sendMessageSynchronously(message);
    }

    public Message sendMessageSynchronously(int n, int n2) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        return this.sendMessageSynchronously(message);
    }

    @UnsupportedAppUsage
    public Message sendMessageSynchronously(int n, int n2, int n3) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        return this.sendMessageSynchronously(message);
    }

    public Message sendMessageSynchronously(int n, int n2, int n3, Object object) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        message.obj = object;
        return this.sendMessageSynchronously(message);
    }

    public Message sendMessageSynchronously(int n, Object object) {
        Message message = Message.obtain();
        message.what = n;
        message.obj = object;
        return this.sendMessageSynchronously(message);
    }

    @UnsupportedAppUsage
    public Message sendMessageSynchronously(Message message) {
        return SyncMessenger.sendMessageSynchronously(this.mDstMessenger, message);
    }

    final class 1ConnectAsync
    implements Runnable {
        String mDstClassName;
        String mDstPackageName;
        Context mSrcCtx;
        Handler mSrcHdlr;

        1ConnectAsync(Context context, Handler handler, String string2, String string3) {
            this.mSrcCtx = context;
            this.mSrcHdlr = handler;
            this.mDstPackageName = string2;
            this.mDstClassName = string3;
        }

        @Override
        public void run() {
            int n = AsyncChannel.this.connectSrcHandlerToPackageSync(this.mSrcCtx, this.mSrcHdlr, this.mDstPackageName, this.mDstClassName);
            AsyncChannel.this.replyHalfConnected(n);
        }
    }

    class AsyncChannelConnection
    implements ServiceConnection {
        AsyncChannelConnection() {
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AsyncChannel.this.mDstMessenger = new Messenger(iBinder);
            AsyncChannel.this.replyHalfConnected(0);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            AsyncChannel.this.replyDisconnected(0);
        }
    }

    private final class DeathMonitor
    implements IBinder.DeathRecipient {
        DeathMonitor() {
        }

        @Override
        public void binderDied() {
            AsyncChannel.this.replyDisconnected(4);
        }
    }

    private static class SyncMessenger {
        private static int sCount;
        private static Stack<SyncMessenger> sStack;
        private SyncHandler mHandler;
        private HandlerThread mHandlerThread;
        private Messenger mMessenger;

        static {
            sStack = new Stack();
            sCount = 0;
        }

        private SyncMessenger() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static SyncMessenger obtain() {
            Stack<SyncMessenger> stack = sStack;
            synchronized (stack) {
                if (!sStack.isEmpty()) return sStack.pop();
                SyncMessenger syncMessenger = new SyncMessenger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SyncHandler-");
                int n = sCount;
                sCount = n + 1;
                stringBuilder.append(n);
                Object object = new HandlerThread(stringBuilder.toString());
                syncMessenger.mHandlerThread = object;
                syncMessenger.mHandlerThread.start();
                Objects.requireNonNull(syncMessenger);
                syncMessenger.mHandler = object = syncMessenger.new SyncHandler(syncMessenger.mHandlerThread.getLooper());
                syncMessenger.mMessenger = object = new Messenger(syncMessenger.mHandler);
                return syncMessenger;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void recycle() {
            Stack<SyncMessenger> stack = sStack;
            synchronized (stack) {
                sStack.push(this);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private static Message sendMessageSynchronously(Messenger parcelable, Message message) {
            SyncMessenger syncMessenger = SyncMessenger.obtain();
            Parcelable parcelable2 = null;
            Parcelable parcelable3 = null;
            Parcelable parcelable4 = null;
            Parcelable parcelable5 = null;
            Parcelable parcelable6 = parcelable4;
            if (parcelable != null) {
                parcelable6 = parcelable4;
                if (message != null) {
                    parcelable4 = parcelable2;
                    parcelable6 = parcelable3;
                    message.replyTo = syncMessenger.mMessenger;
                    parcelable4 = parcelable2;
                    parcelable6 = parcelable3;
                    Object object = syncMessenger.mHandler.mLockObject;
                    parcelable4 = parcelable2;
                    parcelable6 = parcelable3;
                    // MONITORENTER : object
                    parcelable6 = parcelable5;
                    try {
                        if (syncMessenger.mHandler.mResultMsg != null) {
                            parcelable6 = parcelable5;
                            Slog.wtf(AsyncChannel.TAG, "mResultMsg should be null here");
                            parcelable6 = parcelable5;
                            syncMessenger.mHandler.mResultMsg = null;
                        }
                        parcelable6 = parcelable5;
                        ((Messenger)parcelable).send(message);
                        parcelable6 = parcelable5;
                        syncMessenger.mHandler.mLockObject.wait();
                        parcelable6 = parcelable5;
                        parcelable6 = parcelable = syncMessenger.mHandler.mResultMsg;
                        syncMessenger.mHandler.mResultMsg = null;
                        parcelable6 = parcelable;
                        // MONITOREXIT : object
                        parcelable6 = parcelable;
                    }
                    catch (Throwable throwable) {
                        // MONITOREXIT : object
                        parcelable4 = parcelable6;
                        try {
                            throw throwable;
                        }
                        catch (RemoteException remoteException) {
                            Slog.e(AsyncChannel.TAG, "error in sendMessageSynchronously", remoteException);
                            parcelable6 = parcelable4;
                        }
                        catch (InterruptedException interruptedException) {
                            Slog.e(AsyncChannel.TAG, "error in sendMessageSynchronously", interruptedException);
                        }
                    }
                }
            }
            syncMessenger.recycle();
            return parcelable6;
        }

        private class SyncHandler
        extends Handler {
            private Object mLockObject;
            private Message mResultMsg;

            private SyncHandler(Looper looper) {
                super(looper);
                this.mLockObject = new Object();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void handleMessage(Message object) {
                Message message = Message.obtain();
                message.copyFrom((Message)object);
                object = this.mLockObject;
                synchronized (object) {
                    this.mResultMsg = message;
                    this.mLockObject.notify();
                    return;
                }
            }
        }

    }

}

