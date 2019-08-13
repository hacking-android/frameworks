/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IWindowFocusObserver;
import android.view.IWindowId;
import java.util.HashMap;

public class WindowId
implements Parcelable {
    public static final Parcelable.Creator<WindowId> CREATOR = new Parcelable.Creator<WindowId>(){

        @Override
        public WindowId createFromParcel(Parcel object) {
            object = (object = ((Parcel)object).readStrongBinder()) != null ? new WindowId((IBinder)object) : null;
            return object;
        }

        public WindowId[] newArray(int n) {
            return new WindowId[n];
        }
    };
    private final IWindowId mToken;

    public WindowId(IBinder iBinder) {
        this.mToken = IWindowId.Stub.asInterface(iBinder);
    }

    public WindowId(IWindowId iWindowId) {
        this.mToken = iWindowId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof WindowId) {
            return this.mToken.asBinder().equals(((WindowId)object).mToken.asBinder());
        }
        return false;
    }

    public IWindowId getTarget() {
        return this.mToken;
    }

    public int hashCode() {
        return this.mToken.asBinder().hashCode();
    }

    public boolean isFocused() {
        try {
            boolean bl = this.mToken.isFocused();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFocusObserver(FocusObserver object) {
        HashMap<IBinder, WindowId> hashMap = ((FocusObserver)object).mRegistrations;
        synchronized (hashMap) {
            if (((FocusObserver)object).mRegistrations.containsKey(this.mToken.asBinder())) {
                object = new IllegalStateException("Focus observer already registered with input token");
                throw object;
            }
            ((FocusObserver)object).mRegistrations.put(this.mToken.asBinder(), this);
            try {
                this.mToken.registerFocusObserver(((FocusObserver)object).mIObserver);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("IntentSender{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(": ");
        stringBuilder.append(this.mToken.asBinder());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFocusObserver(FocusObserver object) {
        HashMap<IBinder, WindowId> hashMap = ((FocusObserver)object).mRegistrations;
        synchronized (hashMap) {
            WindowId windowId = ((FocusObserver)object).mRegistrations.remove(this.mToken.asBinder());
            if (windowId == null) {
                object = new IllegalStateException("Focus observer not registered with input token");
                throw object;
            }
            try {
                this.mToken.unregisterFocusObserver(((FocusObserver)object).mIObserver);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.mToken.asBinder());
    }

    public static abstract class FocusObserver {
        final Handler mHandler = new H();
        final IWindowFocusObserver.Stub mIObserver = new IWindowFocusObserver.Stub(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void focusGained(IBinder object) {
                HashMap<IBinder, WindowId> hashMap = FocusObserver.this.mRegistrations;
                // MONITORENTER : hashMap
                object = FocusObserver.this.mRegistrations.get(object);
                // MONITOREXIT : hashMap
                if (FocusObserver.this.mHandler != null) {
                    FocusObserver.this.mHandler.sendMessage(FocusObserver.this.mHandler.obtainMessage(1, object));
                    return;
                }
                FocusObserver.this.onFocusGained((WindowId)object);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void focusLost(IBinder object) {
                HashMap<IBinder, WindowId> hashMap = FocusObserver.this.mRegistrations;
                // MONITORENTER : hashMap
                object = FocusObserver.this.mRegistrations.get(object);
                // MONITOREXIT : hashMap
                if (FocusObserver.this.mHandler != null) {
                    FocusObserver.this.mHandler.sendMessage(FocusObserver.this.mHandler.obtainMessage(2, object));
                    return;
                }
                FocusObserver.this.onFocusLost((WindowId)object);
            }
        };
        final HashMap<IBinder, WindowId> mRegistrations = new HashMap();

        public abstract void onFocusGained(WindowId var1);

        public abstract void onFocusLost(WindowId var1);

        class H
        extends Handler {
            H() {
            }

            @Override
            public void handleMessage(Message message) {
                int n = message.what;
                if (n != 1) {
                    if (n != 2) {
                        super.handleMessage(message);
                    } else {
                        FocusObserver.this.onFocusLost((WindowId)message.obj);
                    }
                } else {
                    FocusObserver.this.onFocusGained((WindowId)message.obj);
                }
            }
        }

    }

}

