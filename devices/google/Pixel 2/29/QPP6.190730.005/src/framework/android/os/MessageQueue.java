/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import java.io.FileDescriptor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final String TAG = "MessageQueue";
    private boolean mBlocked;
    private SparseArray<FileDescriptorRecord> mFileDescriptorRecords;
    @UnsupportedAppUsage
    private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList();
    @UnsupportedAppUsage
    Message mMessages;
    @UnsupportedAppUsage
    private int mNextBarrierToken;
    private IdleHandler[] mPendingIdleHandlers;
    @UnsupportedAppUsage
    private long mPtr;
    @UnsupportedAppUsage
    private final boolean mQuitAllowed;
    private boolean mQuitting;

    MessageQueue(boolean bl) {
        this.mQuitAllowed = bl;
        this.mPtr = MessageQueue.nativeInit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private int dispatchEvents(int n, int n2) {
        FileDescriptorRecord fileDescriptorRecord;
        int n3;
        OnFileDescriptorEventListener onFileDescriptorEventListener;
        int n4;
        int n5;
        synchronized (this) {
            fileDescriptorRecord = this.mFileDescriptorRecords.get(n);
            if (fileDescriptorRecord == null) {
                return 0;
            }
            n5 = fileDescriptorRecord.mEvents;
            if ((n2 &= n5) == 0) {
                return n5;
            }
            onFileDescriptorEventListener = fileDescriptorRecord.mListener;
            n4 = fileDescriptorRecord.mSeq;
        }
        n2 = n3 = onFileDescriptorEventListener.onFileDescriptorEvents(fileDescriptorRecord.mDescriptor, n2);
        if (n3 != 0) {
            n2 = n3 | 4;
        }
        if (n2 == n5) return n2;
        synchronized (this) {
            n = this.mFileDescriptorRecords.indexOfKey(n);
            if (n < 0) return n2;
            if (this.mFileDescriptorRecords.valueAt(n) != fileDescriptorRecord) return n2;
            if (fileDescriptorRecord.mSeq != n4) return n2;
            fileDescriptorRecord.mEvents = n2;
            if (n2 != 0) return n2;
            this.mFileDescriptorRecords.removeAt(n);
            return n2;
        }
    }

    private void dispose() {
        long l = this.mPtr;
        if (l != 0L) {
            MessageQueue.nativeDestroy(l);
            this.mPtr = 0L;
        }
    }

    private boolean isPollingLocked() {
        boolean bl = !this.mQuitting && MessageQueue.nativeIsPolling(this.mPtr);
        return bl;
    }

    private static native void nativeDestroy(long var0);

    private static native long nativeInit();

    private static native boolean nativeIsPolling(long var0);

    @UnsupportedAppUsage
    private native void nativePollOnce(long var1, int var3);

    private static native void nativeSetFileDescriptorEvents(long var0, int var2, int var3);

    private static native void nativeWake(long var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int postSyncBarrier(long l) {
        synchronized (this) {
            Message message;
            int n = this.mNextBarrierToken;
            this.mNextBarrierToken = n + 1;
            Message message2 = Message.obtain();
            message2.markInUse();
            message2.when = l;
            message2.arg1 = n;
            Message message3 = null;
            Message message4 = null;
            Message message5 = message = this.mMessages;
            if (l != 0L) {
                do {
                    message3 = message4;
                    message5 = message;
                    if (message == null) break;
                    message3 = message4;
                    message5 = message;
                    if (message.when > l) break;
                    message4 = message;
                    message = message.next;
                } while (true);
            }
            if (message3 != null) {
                message2.next = message5;
                message3.next = message2;
            } else {
                message2.next = message5;
                this.mMessages = message2;
            }
            return n;
        }
    }

    private void removeAllFutureMessagesLocked() {
        long l = SystemClock.uptimeMillis();
        Message message = this.mMessages;
        if (message != null) {
            Message message2 = message;
            if (message.when > l) {
                this.removeAllMessagesLocked();
            } else {
                do {
                    if ((message = message2.next) == null) {
                        return;
                    }
                    if (message.when > l) {
                        message2.next = null;
                        message2 = message;
                        do {
                            message = message2.next;
                            message2.recycleUnchecked();
                            message2 = message;
                        } while (message != null);
                        break;
                    }
                    message2 = message;
                } while (true);
            }
        }
    }

    private void removeAllMessagesLocked() {
        Message message = this.mMessages;
        while (message != null) {
            Message message2 = message.next;
            message.recycleUnchecked();
            message = message2;
        }
        this.mMessages = null;
    }

    private void updateOnFileDescriptorEventListenerLocked(FileDescriptor object, int n, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        int n2 = object.getInt$();
        int n3 = -1;
        FileDescriptorRecord fileDescriptorRecord = null;
        SparseArray<FileDescriptorRecord> sparseArray = this.mFileDescriptorRecords;
        FileDescriptorRecord fileDescriptorRecord2 = fileDescriptorRecord;
        if (sparseArray != null) {
            int n4;
            n3 = n4 = sparseArray.indexOfKey(n2);
            fileDescriptorRecord2 = fileDescriptorRecord;
            if (n4 >= 0) {
                fileDescriptorRecord = this.mFileDescriptorRecords.valueAt(n4);
                n3 = n4;
                fileDescriptorRecord2 = fileDescriptorRecord;
                if (fileDescriptorRecord != null) {
                    n3 = n4;
                    fileDescriptorRecord2 = fileDescriptorRecord;
                    if (fileDescriptorRecord.mEvents == n) {
                        return;
                    }
                }
            }
        }
        if (n != 0) {
            n |= 4;
            if (fileDescriptorRecord2 == null) {
                if (this.mFileDescriptorRecords == null) {
                    this.mFileDescriptorRecords = new SparseArray<E>();
                }
                object = new FileDescriptorRecord((FileDescriptor)object, n, onFileDescriptorEventListener);
                this.mFileDescriptorRecords.put(n2, (FileDescriptorRecord)object);
            } else {
                fileDescriptorRecord2.mListener = onFileDescriptorEventListener;
                fileDescriptorRecord2.mEvents = n;
                ++fileDescriptorRecord2.mSeq;
            }
            MessageQueue.nativeSetFileDescriptorEvents(this.mPtr, n2, n);
        } else if (fileDescriptorRecord2 != null) {
            fileDescriptorRecord2.mEvents = 0;
            this.mFileDescriptorRecords.removeAt(n3);
            MessageQueue.nativeSetFileDescriptorEvents(this.mPtr, n2, 0);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addIdleHandler(IdleHandler idleHandler) {
        if (idleHandler != null) {
            synchronized (this) {
                this.mIdleHandlers.add(idleHandler);
                return;
            }
        }
        throw new NullPointerException("Can't add a null IdleHandler");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnFileDescriptorEventListener(FileDescriptor fileDescriptor, int n, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        if (fileDescriptor == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (onFileDescriptorEventListener != null) {
            synchronized (this) {
                this.updateOnFileDescriptorEventListenerLocked(fileDescriptor, n, onFileDescriptorEventListener);
                return;
            }
        }
        throw new IllegalArgumentException("listener must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dump(Printer printer, String string2, Handler object) {
        synchronized (this) {
            long l = SystemClock.uptimeMillis();
            int n = 0;
            Message message = this.mMessages;
            do {
                if (message == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append("(Total messages: ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(", polling=");
                    ((StringBuilder)object).append(this.isPollingLocked());
                    ((StringBuilder)object).append(", quitting=");
                    ((StringBuilder)object).append(this.mQuitting);
                    ((StringBuilder)object).append(")");
                    printer.println(((StringBuilder)object).toString());
                    return;
                }
                if (object == null || object == message.target) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append("Message ");
                    stringBuilder.append(n);
                    stringBuilder.append(": ");
                    stringBuilder.append(message.toString(l));
                    printer.println(stringBuilder.toString());
                }
                ++n;
                message = message.next;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean enqueueMessage(Message message, long l) {
        if (message.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (message.isInUse()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message);
            stringBuilder.append(" This message is already in use.");
            throw new IllegalStateException(stringBuilder.toString());
        }
        synchronized (this) {
            boolean bl = this.mQuitting;
            boolean bl2 = false;
            if (bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(message.target);
                stringBuilder.append(" sending message to a Handler on a dead thread");
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                Log.w("MessageQueue", illegalStateException.getMessage(), illegalStateException);
                message.recycle();
                return false;
            }
            message.markInUse();
            message.when = l;
            Message message2 = this.mMessages;
            if (message2 == null || l == 0L || l < message2.when) {
                message.next = message2;
                this.mMessages = message;
                bl = this.mBlocked;
            } else {
                Message message3 = message2;
                bl = bl2;
                if (this.mBlocked) {
                    message3 = message2;
                    bl = bl2;
                    if (message2.target == null) {
                        message3 = message2;
                        bl = bl2;
                        if (message.isAsynchronous()) {
                            bl = true;
                            message3 = message2;
                        }
                    }
                }
                while ((message2 = message3.next) != null && l >= message2.when) {
                    message3 = message2;
                    if (!bl) continue;
                    message3 = message2;
                    if (!message2.isAsynchronous()) continue;
                    bl = false;
                    message3 = message2;
                }
                message.next = message2;
                message3.next = message;
            }
            if (bl) {
                MessageQueue.nativeWake(this.mPtr);
            }
            return true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean hasMessages(Handler handler) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null) {
                if (message.target == handler) {
                    return true;
                }
                message = message.next;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean hasMessages(Handler handler, int n, Object object) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null) {
                if (message.target == handler && message.what == n && (object == null || message.obj == object)) {
                    return true;
                }
                message = message.next;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean hasMessages(Handler handler, Runnable runnable, Object object) {
        if (handler == null) {
            return false;
        }
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null) {
                if (message.target == handler && message.callback == runnable && (object == null || message.obj == object)) {
                    return true;
                }
                message = message.next;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isIdle() {
        synchronized (this) {
            long l = SystemClock.uptimeMillis();
            if (this.mMessages == null) return true;
            if (l >= this.mMessages.when) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isPolling() {
        synchronized (this) {
            return this.isPollingLocked();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    Message next() {
        var1_1 = this.mPtr;
        if (var1_1 == 0L) {
            return null;
        }
        var3_2 = -1;
        var4_3 = 0;
        do {
            block26 : {
                block27 : {
                    block25 : {
                        if (var4_3 != 0) {
                            Binder.flushPendingCommands();
                        }
                        this.nativePollOnce(var1_1, var4_3);
                        // MONITORENTER : this
                        var5_4 = SystemClock.uptimeMillis();
                        var7_5 = null;
                        var8_6 = this.mMessages;
                        var9_7 = var7_5;
                        var10_9 = var8_6;
                        if (var8_6 != null) {
                            var9_7 = var7_5;
                            var10_9 = var8_6;
                            if (var8_6.target == null) {
                                var10_9 = var8_6;
                                do {
                                    var8_6 = var10_9;
                                    var7_5 = var10_9.next;
                                    var9_7 = var8_6;
                                    var10_9 = var7_5;
                                    if (var7_5 == null) break block25;
                                    var10_9 = var7_5;
                                } while (!var7_5.isAsynchronous());
                                var10_9 = var7_5;
                                var9_7 = var8_6;
                            }
                        }
                    }
                    if (var10_9 == null) ** GOTO lbl45
                    if (var5_4 < var10_9.when) {
                        var4_3 = (int)Math.min(var10_9.when - var5_4, Integer.MAX_VALUE);
                    } else {
                        this.mBlocked = false;
                        if (var9_7 != null) {
                            var9_7.next = var10_9.next;
                        } else {
                            this.mMessages = var10_9.next;
                        }
                        var10_9.next = null;
                        var10_9.markInUse();
                        // MONITOREXIT : this
                        return var10_9;
lbl45: // 1 sources:
                        var4_3 = -1;
                    }
                    if (this.mQuitting) {
                        this.dispose();
                        // MONITOREXIT : this
                        return null;
                    }
                    var11_10 = var3_2;
                    if (var3_2 >= 0) break block26;
                    if (this.mMessages == null) break block27;
                    var11_10 = var3_2;
                    if (var5_4 >= this.mMessages.when) break block26;
                }
                var11_10 = this.mIdleHandlers.size();
            }
            if (var11_10 <= 0) {
                this.mBlocked = true;
                // MONITOREXIT : this
                var3_2 = var11_10;
                continue;
            }
            if (this.mPendingIdleHandlers == null) {
                this.mPendingIdleHandlers = new IdleHandler[Math.max(var11_10, 4)];
            }
            this.mPendingIdleHandlers = this.mIdleHandlers.toArray(this.mPendingIdleHandlers);
            // MONITOREXIT : this
            for (var3_2 = 0; var3_2 < var11_10; ++var3_2) {
                var9_7 = this.mPendingIdleHandlers;
                var10_9 = var9_7[var3_2];
                var9_7[var3_2] = null;
                var12_11 = false;
                try {
                    var13_12 = var10_9.queueIdle();
                }
                catch (Throwable var9_8) {
                    Log.wtf("MessageQueue", "IdleHandler threw exception", var9_8);
                    var13_12 = var12_11;
                }
                if (var13_12) continue;
                // MONITORENTER : this
                this.mIdleHandlers.remove(var10_9);
                // MONITOREXIT : this
            }
            var3_2 = 0;
            var4_3 = 0;
        } while (true);
    }

    public int postSyncBarrier() {
        return this.postSyncBarrier(SystemClock.uptimeMillis());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void quit(boolean bl) {
        if (!this.mQuitAllowed) {
            throw new IllegalStateException("Main thread not allowed to quit.");
        }
        synchronized (this) {
            if (this.mQuitting) {
                return;
            }
            this.mQuitting = true;
            if (bl) {
                this.removeAllFutureMessagesLocked();
            } else {
                this.removeAllMessagesLocked();
            }
            MessageQueue.nativeWake(this.mPtr);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeCallbacksAndMessages(Handler handler, Object object) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            Message message;
            Message message2 = this.mMessages;
            do {
                message = message2;
                if (message2 == null) break;
                message = message2;
                if (message2.target != handler) break;
                if (object != null) {
                    message = message2;
                    if (message2.obj != object) break;
                }
                this.mMessages = message = message2.next;
                message2.recycleUnchecked();
                message2 = message;
            } while (true);
            while (message != null) {
                message2 = message.next;
                if (message2 != null && message2.target == handler && (object == null || message2.obj == object)) {
                    Message message3 = message2.next;
                    message2.recycleUnchecked();
                    message.next = message3;
                    continue;
                }
                message = message2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeIdleHandler(IdleHandler idleHandler) {
        synchronized (this) {
            this.mIdleHandlers.remove(idleHandler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeMessages(Handler handler, int n, Object object) {
        if (handler == null) {
            return;
        }
        synchronized (this) {
            Message message;
            Message message2 = this.mMessages;
            do {
                message = message2;
                if (message2 == null) break;
                message = message2;
                if (message2.target != handler) break;
                message = message2;
                if (message2.what != n) break;
                if (object != null) {
                    message = message2;
                    if (message2.obj != object) break;
                }
                this.mMessages = message = message2.next;
                message2.recycleUnchecked();
                message2 = message;
            } while (true);
            while (message != null) {
                message2 = message.next;
                if (message2 != null && message2.target == handler && message2.what == n && (object == null || message2.obj == object)) {
                    Message message3 = message2.next;
                    message2.recycleUnchecked();
                    message.next = message3;
                    continue;
                }
                message = message2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeMessages(Handler handler, Runnable runnable, Object object) {
        if (handler == null) return;
        if (runnable == null) {
            return;
        }
        synchronized (this) {
            Message message;
            Message message2 = this.mMessages;
            do {
                message = message2;
                if (message2 == null) break;
                message = message2;
                if (message2.target != handler) break;
                message = message2;
                if (message2.callback != runnable) break;
                if (object != null) {
                    message = message2;
                    if (message2.obj != object) break;
                }
                this.mMessages = message = message2.next;
                message2.recycleUnchecked();
                message2 = message;
            } while (true);
            while (message != null) {
                message2 = message.next;
                if (message2 != null && message2.target == handler && message2.callback == runnable && (object == null || message2.obj == object)) {
                    Message message3 = message2.next;
                    message2.recycleUnchecked();
                    message.next = message3;
                    continue;
                }
                message = message2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeOnFileDescriptorEventListener(FileDescriptor fileDescriptor) {
        if (fileDescriptor != null) {
            synchronized (this) {
                this.updateOnFileDescriptorEventListenerLocked(fileDescriptor, 0, null);
                return;
            }
        }
        throw new IllegalArgumentException("fd must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeSyncBarrier(int n) {
        synchronized (this) {
            Message message = null;
            Object object = this.mMessages;
            while (object != null && (((Message)object).target != null || ((Message)object).arg1 != n)) {
                message = object;
                object = ((Message)object).next;
            }
            if (object == null) {
                object = new IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
                throw object;
            }
            if (message != null) {
                message.next = ((Message)object).next;
                n = 0;
            } else {
                this.mMessages = ((Message)object).next;
                n = this.mMessages != null && this.mMessages.target == null ? 0 : 1;
            }
            ((Message)object).recycleUnchecked();
            if (n != 0 && !this.mQuitting) {
                MessageQueue.nativeWake(this.mPtr);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        synchronized (this) {
            Message message = this.mMessages;
            do {
                if (message == null) {
                    protoOutputStream.write(1133871366146L, this.isPollingLocked());
                    protoOutputStream.write(1133871366147L, this.mQuitting);
                    // MONITOREXIT [2, 3, 4] lbl8 : MonitorExitStatement: MONITOREXIT : this
                    protoOutputStream.end(l);
                    return;
                }
                message.writeToProto(protoOutputStream, 2246267895809L);
                message = message.next;
            } while (true);
        }
    }

    private static final class FileDescriptorRecord {
        public final FileDescriptor mDescriptor;
        public int mEvents;
        public OnFileDescriptorEventListener mListener;
        public int mSeq;

        public FileDescriptorRecord(FileDescriptor fileDescriptor, int n, OnFileDescriptorEventListener onFileDescriptorEventListener) {
            this.mDescriptor = fileDescriptor;
            this.mEvents = n;
            this.mListener = onFileDescriptorEventListener;
        }
    }

    public static interface IdleHandler {
        public boolean queueIdle();
    }

    public static interface OnFileDescriptorEventListener {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;

        public int onFileDescriptorEvents(FileDescriptor var1, int var2);

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Events {
        }

    }

}

