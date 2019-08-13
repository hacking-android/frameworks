/*
 * Decompiled with CFR 0.145.
 */
package android.service.quicksettings;

import android.annotation.SystemApi;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.IQSTileService;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TileService
extends Service {
    public static final String ACTION_QS_TILE = "android.service.quicksettings.action.QS_TILE";
    public static final String ACTION_QS_TILE_PREFERENCES = "android.service.quicksettings.action.QS_TILE_PREFERENCES";
    public static final String ACTION_REQUEST_LISTENING = "android.service.quicksettings.action.REQUEST_LISTENING";
    private static final boolean DEBUG = false;
    public static final String EXTRA_SERVICE = "service";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_TOKEN = "token";
    public static final String META_DATA_ACTIVE_TILE = "android.service.quicksettings.ACTIVE_TILE";
    private static final String TAG = "TileService";
    private final H mHandler = new H(Looper.getMainLooper());
    private boolean mListening = false;
    private IQSService mService;
    private Tile mTile;
    private IBinder mTileToken;
    private IBinder mToken;
    private Runnable mUnlockRunnable;

    public static boolean isQuickSettingsSupported() {
        return Resources.getSystem().getBoolean(17891497);
    }

    public static final void requestListeningState(Context context, ComponentName componentName) {
        Intent intent = new Intent(ACTION_REQUEST_LISTENING);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
        intent.setPackage("com.android.systemui");
        context.sendBroadcast(intent, "android.permission.BIND_QUICK_SETTINGS_TILE");
    }

    public final Tile getQsTile() {
        return this.mTile;
    }

    public final boolean isLocked() {
        try {
            boolean bl = this.mService.isLocked();
            return bl;
        }
        catch (RemoteException remoteException) {
            return true;
        }
    }

    public final boolean isSecure() {
        try {
            boolean bl = this.mService.isSecure();
            return bl;
        }
        catch (RemoteException remoteException) {
            return true;
        }
    }

    @Override
    public IBinder onBind(Intent parcelable) {
        block2 : {
            this.mService = IQSService.Stub.asInterface(((Intent)parcelable).getIBinderExtra(EXTRA_SERVICE));
            this.mTileToken = ((Intent)parcelable).getIBinderExtra(EXTRA_TOKEN);
            try {
                this.mTile = this.mService.getTile(this.mTileToken);
                parcelable = this.mTile;
                if (parcelable == null) break block2;
                ((Tile)parcelable).setService(this.mService, this.mTileToken);
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException("Unable to reach IQSService", remoteException);
            }
            this.mHandler.sendEmptyMessage(7);
        }
        return new IQSTileService.Stub(){

            @Override
            public void onClick(IBinder iBinder) throws RemoteException {
                TileService.this.mHandler.obtainMessage(5, iBinder).sendToTarget();
            }

            @Override
            public void onStartListening() throws RemoteException {
                TileService.this.mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onStopListening() throws RemoteException {
                TileService.this.mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onTileAdded() throws RemoteException {
                TileService.this.mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onTileRemoved() throws RemoteException {
                TileService.this.mHandler.sendEmptyMessage(4);
            }

            @Override
            public void onUnlockComplete() throws RemoteException {
                TileService.this.mHandler.sendEmptyMessage(6);
            }
        };
    }

    public void onClick() {
    }

    @Override
    public void onDestroy() {
        if (this.mListening) {
            this.onStopListening();
            this.mListening = false;
        }
        super.onDestroy();
    }

    public void onStartListening() {
    }

    public void onStopListening() {
    }

    public void onTileAdded() {
    }

    public void onTileRemoved() {
    }

    @SystemApi
    public final void setStatusIcon(Icon icon, String string2) {
        IQSService iQSService = this.mService;
        if (iQSService != null) {
            try {
                iQSService.updateStatusIcon(this.mTileToken, icon, string2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public final void showDialog(Dialog dialog) {
        dialog.getWindow().getAttributes().token = this.mToken;
        dialog.getWindow().setType(2035);
        dialog.getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(){

            @Override
            public void onViewAttachedToWindow(View view) {
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                try {
                    TileService.this.mService.onDialogHidden(TileService.this.mTileToken);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        });
        dialog.show();
        try {
            this.mService.onShowDialog(this.mTileToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void startActivityAndCollapse(Intent intent) {
        this.startActivity(intent);
        try {
            this.mService.onStartActivity(this.mTileToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void unlockAndRun(Runnable runnable) {
        this.mUnlockRunnable = runnable;
        try {
            this.mService.startUnlockAndRun(this.mTileToken);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private class H
    extends Handler {
        private static final int MSG_START_LISTENING = 1;
        private static final int MSG_START_SUCCESS = 7;
        private static final int MSG_STOP_LISTENING = 2;
        private static final int MSG_TILE_ADDED = 3;
        private static final int MSG_TILE_CLICKED = 5;
        private static final int MSG_TILE_REMOVED = 4;
        private static final int MSG_UNLOCK_COMPLETE = 6;
        private final String mTileServiceName;

        public H(Looper looper) {
            super(looper);
            this.mTileServiceName = TileService.this.getClass().getSimpleName();
        }

        private void logMessage(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mTileServiceName);
            stringBuilder.append(" Handler - ");
            stringBuilder.append(string2);
            Log.d(TileService.TAG, stringBuilder.toString());
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    break;
                }
                case 7: {
                    try {
                        TileService.this.mService.onStartSuccessful(TileService.this.mTileToken);
                    }
                    catch (RemoteException remoteException) {}
                    break;
                }
                case 6: {
                    if (TileService.this.mUnlockRunnable == null) break;
                    TileService.this.mUnlockRunnable.run();
                    break;
                }
                case 5: {
                    TileService.this.mToken = (IBinder)message.obj;
                    TileService.this.onClick();
                    break;
                }
                case 4: {
                    if (TileService.this.mListening) {
                        TileService.this.mListening = false;
                        TileService.this.onStopListening();
                    }
                    TileService.this.onTileRemoved();
                    break;
                }
                case 3: {
                    TileService.this.onTileAdded();
                    break;
                }
                case 2: {
                    if (!TileService.this.mListening) break;
                    TileService.this.mListening = false;
                    TileService.this.onStopListening();
                    break;
                }
                case 1: {
                    if (TileService.this.mListening) break;
                    TileService.this.mListening = true;
                    TileService.this.onStartListening();
                }
            }
        }
    }

}

