/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.IClipboard;
import android.content.IOnPrimaryClipChangedListener;
import android.content._$$Lambda$ClipboardManager$1$hQk8olbGAgUi4WWNG4ZuDZsM39s;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;

public class ClipboardManager
extends android.text.ClipboardManager {
    private final Context mContext;
    private final Handler mHandler;
    private final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners = new ArrayList();
    private final IOnPrimaryClipChangedListener.Stub mPrimaryClipChangedServiceListener = new IOnPrimaryClipChangedListener.Stub(){

        @Override
        public void dispatchPrimaryClipChanged() {
            ClipboardManager.this.mHandler.post(new _$$Lambda$ClipboardManager$1$hQk8olbGAgUi4WWNG4ZuDZsM39s(this));
        }

        public /* synthetic */ void lambda$dispatchPrimaryClipChanged$0$ClipboardManager$1() {
            ClipboardManager.this.reportPrimaryClipChanged();
        }
    };
    private final IClipboard mService;

    @UnsupportedAppUsage
    public ClipboardManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
        this.mService = IClipboard.Stub.asInterface(ServiceManager.getServiceOrThrow("clipboard"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ArrayList<OnPrimaryClipChangedListener> arrayList = this.mPrimaryClipChangedListeners;
        synchronized (arrayList) {
            boolean bl = this.mPrimaryClipChangedListeners.isEmpty();
            if (bl) {
                try {
                    this.mService.addPrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getUserId());
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            this.mPrimaryClipChangedListeners.add(onPrimaryClipChangedListener);
            return;
        }
    }

    public void clearPrimaryClip() {
        try {
            this.mService.clearPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ClipData getPrimaryClip() {
        try {
            ClipData clipData = this.mService.getPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
            return clipData;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ClipDescription getPrimaryClipDescription() {
        try {
            ClipDescription clipDescription = this.mService.getPrimaryClipDescription(this.mContext.getOpPackageName(), this.mContext.getUserId());
            return clipDescription;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public CharSequence getText() {
        ClipData clipData = this.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            return clipData.getItemAt(0).coerceToText(this.mContext);
        }
        return null;
    }

    public boolean hasPrimaryClip() {
        try {
            boolean bl = this.mService.hasPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public boolean hasText() {
        try {
            boolean bl = this.mService.hasClipboardText(this.mContext.getOpPackageName(), this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ArrayList<OnPrimaryClipChangedListener> arrayList = this.mPrimaryClipChangedListeners;
        synchronized (arrayList) {
            this.mPrimaryClipChangedListeners.remove(onPrimaryClipChangedListener);
            boolean bl = this.mPrimaryClipChangedListeners.isEmpty();
            if (bl) {
                try {
                    this.mService.removePrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getUserId());
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void reportPrimaryClipChanged() {
        Object[] arrobject;
        ArrayList<OnPrimaryClipChangedListener> arrayList = this.mPrimaryClipChangedListeners;
        synchronized (arrayList) {
            if (this.mPrimaryClipChangedListeners.size() <= 0) {
                return;
            }
            arrobject = this.mPrimaryClipChangedListeners.toArray();
        }
        int n = 0;
        while (n < arrobject.length) {
            ((OnPrimaryClipChangedListener)arrobject[n]).onPrimaryClipChanged();
            ++n;
        }
        return;
    }

    public void setPrimaryClip(ClipData clipData) {
        try {
            Preconditions.checkNotNull(clipData);
            clipData.prepareToLeaveProcess(true);
            this.mService.setPrimaryClip(clipData, this.mContext.getOpPackageName(), this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void setText(CharSequence charSequence) {
        this.setPrimaryClip(ClipData.newPlainText(null, charSequence));
    }

    public static interface OnPrimaryClipChangedListener {
        public void onPrimaryClipChanged();
    }

}

