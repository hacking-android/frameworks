/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;

public final class Message
implements Parcelable {
    public static final Parcelable.Creator<Message> CREATOR;
    static final int FLAGS_TO_CLEAR_ON_COPY_FROM = 1;
    static final int FLAG_ASYNCHRONOUS = 2;
    static final int FLAG_IN_USE = 1;
    private static final int MAX_POOL_SIZE = 50;
    public static final int UID_NONE = -1;
    private static boolean gCheckRecycle;
    private static Message sPool;
    private static int sPoolSize;
    public static final Object sPoolSync;
    public int arg1;
    public int arg2;
    @UnsupportedAppUsage
    Runnable callback;
    Bundle data;
    @UnsupportedAppUsage
    int flags;
    @UnsupportedAppUsage
    Message next;
    public Object obj;
    public Messenger replyTo;
    public int sendingUid = -1;
    @UnsupportedAppUsage
    Handler target;
    public int what;
    @UnsupportedAppUsage
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public long when;
    public int workSourceUid = -1;

    static {
        sPoolSync = new Object();
        sPoolSize = 0;
        gCheckRecycle = true;
        CREATOR = new Parcelable.Creator<Message>(){

            @Override
            public Message createFromParcel(Parcel parcel) {
                Message message = Message.obtain();
                message.readFromParcel(parcel);
                return message;
            }

            public Message[] newArray(int n) {
                return new Message[n];
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Message obtain() {
        Object object = sPoolSync;
        synchronized (object) {
            if (sPool != null) {
                Message message = sPool;
                sPool = message.next;
                message.next = null;
                message.flags = 0;
                --sPoolSize;
                return message;
            }
            return new Message();
        }
    }

    public static Message obtain(Handler handler) {
        Message message = Message.obtain();
        message.target = handler;
        return message;
    }

    public static Message obtain(Handler handler, int n) {
        Message message = Message.obtain();
        message.target = handler;
        message.what = n;
        return message;
    }

    public static Message obtain(Handler handler, int n, int n2, int n3) {
        Message message = Message.obtain();
        message.target = handler;
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        return message;
    }

    public static Message obtain(Handler handler, int n, int n2, int n3, Object object) {
        Message message = Message.obtain();
        message.target = handler;
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        message.obj = object;
        return message;
    }

    public static Message obtain(Handler handler, int n, Object object) {
        Message message = Message.obtain();
        message.target = handler;
        message.what = n;
        message.obj = object;
        return message;
    }

    public static Message obtain(Handler handler, Runnable runnable) {
        Message message = Message.obtain();
        message.target = handler;
        message.callback = runnable;
        return message;
    }

    public static Message obtain(Message message) {
        Message message2 = Message.obtain();
        message2.what = message.what;
        message2.arg1 = message.arg1;
        message2.arg2 = message.arg2;
        message2.obj = message.obj;
        message2.replyTo = message.replyTo;
        message2.sendingUid = message.sendingUid;
        message2.workSourceUid = message.workSourceUid;
        Bundle bundle = message.data;
        if (bundle != null) {
            message2.data = new Bundle(bundle);
        }
        message2.target = message.target;
        message2.callback = message.callback;
        return message2;
    }

    private void readFromParcel(Parcel parcel) {
        this.what = parcel.readInt();
        this.arg1 = parcel.readInt();
        this.arg2 = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.obj = parcel.readParcelable(this.getClass().getClassLoader());
        }
        this.when = parcel.readLong();
        this.data = parcel.readBundle();
        this.replyTo = Messenger.readMessengerOrNullFromParcel(parcel);
        this.sendingUid = parcel.readInt();
        this.workSourceUid = parcel.readInt();
    }

    public static void updateCheckRecycle(int n) {
        if (n < 21) {
            gCheckRecycle = false;
        }
    }

    public void copyFrom(Message parcelable) {
        this.flags = ((Message)parcelable).flags & -2;
        this.what = ((Message)parcelable).what;
        this.arg1 = ((Message)parcelable).arg1;
        this.arg2 = ((Message)parcelable).arg2;
        this.obj = ((Message)parcelable).obj;
        this.replyTo = ((Message)parcelable).replyTo;
        this.sendingUid = ((Message)parcelable).sendingUid;
        this.workSourceUid = ((Message)parcelable).workSourceUid;
        parcelable = ((Message)parcelable).data;
        this.data = parcelable != null ? (Bundle)((Bundle)parcelable).clone() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Runnable getCallback() {
        return this.callback;
    }

    public Bundle getData() {
        if (this.data == null) {
            this.data = new Bundle();
        }
        return this.data;
    }

    public Handler getTarget() {
        return this.target;
    }

    public long getWhen() {
        return this.when;
    }

    public boolean isAsynchronous() {
        boolean bl = (this.flags & 2) != 0;
        return bl;
    }

    boolean isInUse() {
        int n = this.flags;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    void markInUse() {
        this.flags |= 1;
    }

    public Bundle peekData() {
        return this.data;
    }

    public void recycle() {
        if (this.isInUse()) {
            if (!gCheckRecycle) {
                return;
            }
            throw new IllegalStateException("This message cannot be recycled because it is still in use.");
        }
        this.recycleUnchecked();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void recycleUnchecked() {
        this.flags = 1;
        this.what = 0;
        this.arg1 = 0;
        this.arg2 = 0;
        this.obj = null;
        this.replyTo = null;
        this.sendingUid = -1;
        this.workSourceUid = -1;
        this.when = 0L;
        this.target = null;
        this.callback = null;
        this.data = null;
        Object object = sPoolSync;
        synchronized (object) {
            if (sPoolSize < 50) {
                this.next = sPool;
                sPool = this;
                ++sPoolSize;
            }
            return;
        }
    }

    public void sendToTarget() {
        this.target.sendMessage(this);
    }

    public void setAsynchronous(boolean bl) {
        this.flags = bl ? (this.flags |= 2) : (this.flags &= -3);
    }

    @UnsupportedAppUsage
    public Message setCallback(Runnable runnable) {
        this.callback = runnable;
        return this;
    }

    public void setData(Bundle bundle) {
        this.data = bundle;
    }

    public void setTarget(Handler handler) {
        this.target = handler;
    }

    public Message setWhat(int n) {
        this.what = n;
        return this;
    }

    public String toString() {
        return this.toString(SystemClock.uptimeMillis());
    }

    @UnsupportedAppUsage
    String toString(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ when=");
        TimeUtils.formatDuration(this.when - l, stringBuilder);
        if (this.target != null) {
            if (this.callback != null) {
                stringBuilder.append(" callback=");
                stringBuilder.append(this.callback.getClass().getName());
            } else {
                stringBuilder.append(" what=");
                stringBuilder.append(this.what);
            }
            if (this.arg1 != 0) {
                stringBuilder.append(" arg1=");
                stringBuilder.append(this.arg1);
            }
            if (this.arg2 != 0) {
                stringBuilder.append(" arg2=");
                stringBuilder.append(this.arg2);
            }
            if (this.obj != null) {
                stringBuilder.append(" obj=");
                stringBuilder.append(this.obj);
            }
            stringBuilder.append(" target=");
            stringBuilder.append(this.target.getClass().getName());
        } else {
            stringBuilder.append(" barrier=");
            stringBuilder.append(this.arg1);
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.callback == null) {
            parcel.writeInt(this.what);
            parcel.writeInt(this.arg1);
            parcel.writeInt(this.arg2);
            Object object = this.obj;
            if (object != null) {
                try {
                    object = (Parcelable)object;
                    parcel.writeInt(1);
                    parcel.writeParcelable((Parcelable)object, n);
                }
                catch (ClassCastException classCastException) {
                    throw new RuntimeException("Can't marshal non-Parcelable objects across processes.");
                }
            } else {
                parcel.writeInt(0);
            }
            parcel.writeLong(this.when);
            parcel.writeBundle(this.data);
            Messenger.writeMessengerOrNullToParcel(this.replyTo, parcel);
            parcel.writeInt(this.sendingUid);
            parcel.writeInt(this.workSourceUid);
            return;
        }
        throw new RuntimeException("Can't marshal callbacks across processes.");
    }

    void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1112396529665L, this.when);
        if (this.target != null) {
            Object object = this.callback;
            if (object != null) {
                protoOutputStream.write(1138166333442L, object.getClass().getName());
            } else {
                protoOutputStream.write(1120986464259L, this.what);
            }
            int n = this.arg1;
            if (n != 0) {
                protoOutputStream.write(1120986464260L, n);
            }
            if ((n = this.arg2) != 0) {
                protoOutputStream.write(1120986464261L, n);
            }
            if ((object = this.obj) != null) {
                protoOutputStream.write(1138166333446L, object.toString());
            }
            protoOutputStream.write(1138166333447L, this.target.getClass().getName());
        } else {
            protoOutputStream.write(1120986464264L, this.arg1);
        }
        protoOutputStream.end(l);
    }

}

