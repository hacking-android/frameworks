/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.annotation.UnsupportedAppUsage;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.FallbackEventHandler;
import android.view.KeyEvent;
import android.view.View;
import com.android.internal.policy.PhoneWindow;

public class PhoneFallbackEventHandler
implements FallbackEventHandler {
    private static final boolean DEBUG = false;
    private static String TAG = "PhoneFallbackEventHandler";
    AudioManager mAudioManager;
    @UnsupportedAppUsage
    Context mContext;
    KeyguardManager mKeyguardManager;
    MediaSessionManager mMediaSessionManager;
    SearchManager mSearchManager;
    TelephonyManager mTelephonyManager;
    @UnsupportedAppUsage
    View mView;

    @UnsupportedAppUsage
    public PhoneFallbackEventHandler(Context context) {
        this.mContext = context;
    }

    private void handleMediaKeyEvent(KeyEvent keyEvent) {
        this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(keyEvent);
    }

    private void handleVolumeKeyEvent(KeyEvent keyEvent) {
        this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
    }

    private boolean isNotInstantAppAndKeyguardRestricted(KeyEvent.DispatcherState dispatcherState) {
        boolean bl = !this.mContext.getPackageManager().isInstantApp() && (this.getKeyguardManager().inKeyguardRestrictedInputMode() || dispatcherState == null);
        return bl;
    }

    private boolean isUserSetupComplete() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean bl = false;
        if (Settings.Secure.getInt(contentResolver, "user_setup_complete", 0) != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int n = keyEvent.getAction();
        int n2 = keyEvent.getKeyCode();
        if (n == 0) {
            return this.onKeyDown(n2, keyEvent);
        }
        return this.onKeyUp(n2, keyEvent);
    }

    AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        }
        return this.mAudioManager;
    }

    KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager)this.mContext.getSystemService("keyguard");
        }
        return this.mKeyguardManager;
    }

    MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager)this.mContext.getSystemService("media_session");
        }
        return this.mMediaSessionManager;
    }

    SearchManager getSearchManager() {
        if (this.mSearchManager == null) {
            this.mSearchManager = (SearchManager)this.mContext.getSystemService("search");
        }
        return this.mSearchManager;
    }

    TelephonyManager getTelephonyManager() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
        }
        return this.mTelephonyManager;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean onKeyDown(int var1_1, KeyEvent var2_2) {
        block37 : {
            block36 : {
                block34 : {
                    block35 : {
                        var3_5 = this.mView.getKeyDispatcherState();
                        if (var1_1 == 5) break block34;
                        if (var1_1 == 27) break block35;
                        if (var1_1 == 79 || var1_1 == 130) ** GOTO lbl-1000
                        if (var1_1 == 164) ** GOTO lbl41
                        if (var1_1 == 222) ** GOTO lbl-1000
                        if (var1_1 == 24 || var1_1 == 25) ** GOTO lbl41
                        if (var1_1 == 126 || var1_1 == 127) ** GOTO lbl-1000
                        switch (var1_1) {
                            default: {
                                break block36;
                            }
                            case 84: {
                                if (!this.isNotInstantAppAndKeyguardRestricted((KeyEvent.DispatcherState)var3_5)) {
                                    if (var2_2.getRepeatCount() == 0) {
                                        var3_5.startTracking((KeyEvent)var2_2, this);
                                    } else if (var2_2.isLongPress() && var3_5.isTracking((KeyEvent)var2_2)) {
                                        var4_6 = this.mContext.getResources().getConfiguration();
                                        if (var4_6.keyboard == 1 || var4_6.hardKeyboardHidden == 2) {
                                            if (this.isUserSetupComplete()) {
                                                var4_6 = new Intent("android.intent.action.SEARCH_LONG_PRESS");
                                                var4_6.setFlags(268435456);
                                                try {
                                                    this.mView.performHapticFeedback(0);
                                                    this.sendCloseSystemWindows();
                                                    this.getSearchManager().stopSearch();
                                                    this.mContext.startActivity((Intent)var4_6);
                                                    var3_5.performedLongPress((KeyEvent)var2_2);
                                                    return true;
                                                }
                                                catch (ActivityNotFoundException var2_3) {
                                                    ** GOTO lbl70
                                                }
                                            } else {
                                                Log.i(PhoneFallbackEventHandler.TAG, "Not dispatching SEARCH long press because user setup is in progress.");
                                            }
                                        }
                                    }
                                }
                                break block36;
                            }
                            case 85: lbl-1000: // 2 sources:
                            {
                                if (this.getTelephonyManager().getCallState() != 0) {
                                    return true;
                                }
                                ** GOTO lbl43
                            }
lbl41: // 2 sources:
                            this.handleVolumeKeyEvent((KeyEvent)var2_2);
                            return true;
lbl43: // 2 sources:
                            case 86: 
                            case 87: 
                            case 88: 
                            case 89: 
                            case 90: 
                            case 91: lbl-1000: // 3 sources:
                            {
                                this.handleMediaKeyEvent((KeyEvent)var2_2);
                                return true;
                            }
                        }
                    }
                    if (!this.isNotInstantAppAndKeyguardRestricted((KeyEvent.DispatcherState)var3_5)) {
                        if (var2_2.getRepeatCount() == 0) {
                            var3_5.startTracking((KeyEvent)var2_2, this);
                        } else if (var2_2.isLongPress() && var3_5.isTracking((KeyEvent)var2_2)) {
                            var3_5.performedLongPress((KeyEvent)var2_2);
                            if (this.isUserSetupComplete()) {
                                this.mView.performHapticFeedback(0);
                                this.sendCloseSystemWindows();
                                var3_5 = new Intent("android.intent.action.CAMERA_BUTTON", null);
                                var3_5.addFlags(268435456);
                                var3_5.putExtra("android.intent.extra.KEY_EVENT", var2_2);
                                this.mContext.sendOrderedBroadcastAsUser((Intent)var3_5, UserHandle.CURRENT_OR_SELF, null, null, null, 0, null, null);
                            } else {
                                Log.i(PhoneFallbackEventHandler.TAG, "Not dispatching CAMERA long press because user setup is in progress.");
                            }
                        }
                        return true;
                    }
                    break block36;
                }
                if (!this.isNotInstantAppAndKeyguardRestricted((KeyEvent.DispatcherState)var3_5)) break block37;
            }
            return false;
        }
        if (var2_2.getRepeatCount() == 0) {
            var3_5.startTracking((KeyEvent)var2_2, this);
        } else if (var2_2.isLongPress() && var3_5.isTracking((KeyEvent)var2_2)) {
            var3_5.performedLongPress((KeyEvent)var2_2);
            if (this.isUserSetupComplete()) {
                this.mView.performHapticFeedback(0);
                var2_2 = new Intent("android.intent.action.VOICE_COMMAND");
                var2_2.setFlags(268435456);
                try {
                    this.sendCloseSystemWindows();
                    this.mContext.startActivity((Intent)var2_2);
                }
                catch (ActivityNotFoundException var2_4) {
                    this.startCallActivity();
                }
            } else {
                Log.i(PhoneFallbackEventHandler.TAG, "Not starting call activity because user setup is in progress.");
            }
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    boolean onKeyUp(int var1_1, KeyEvent var2_2) {
        block11 : {
            block12 : {
                var3_3 = this.mView.getKeyDispatcherState();
                if (var3_3 != null) {
                    var3_3.handleUpEvent(var2_2);
                }
                if (var1_1 == 5) break block11;
                if (var1_1 == 27) break block12;
                if (var1_1 == 79 || var1_1 == 130) ** GOTO lbl-1000
                if (var1_1 == 164) ** GOTO lbl-1000
                if (var1_1 == 222) ** GOTO lbl-1000
                if (var1_1 != 24 && var1_1 != 25) {
                    if (var1_1 != 126 && var1_1 != 127) {
                        switch (var1_1) {
                            default: {
                                return false;
                            }
                        }
                    } else {
                        ** GOTO lbl15
                    }
                }
                ** GOTO lbl-1000
lbl15: // 2 sources:
                ** GOTO lbl-1000
lbl-1000: // 2 sources:
                {
                    if (var2_2.isCanceled()) return true;
                    this.handleVolumeKeyEvent(var2_2);
                    return true;
                    case 85: 
                    case 86: 
                    case 87: 
                    case 88: 
                    case 89: 
                    case 90: 
                    case 91: lbl-1000: // 4 sources:
                    {
                        this.handleMediaKeyEvent(var2_2);
                        return true;
                    }
                }
            }
            if (this.isNotInstantAppAndKeyguardRestricted(var3_3)) return false;
            if (!var2_2.isTracking()) return true;
            var2_2.isCanceled();
            return true;
        }
        if (this.isNotInstantAppAndKeyguardRestricted(var3_3)) {
            return false;
        }
        if (!var2_2.isTracking() || var2_2.isCanceled()) return true;
        if (this.isUserSetupComplete()) {
            this.startCallActivity();
            return true;
        } else {
            Log.i(PhoneFallbackEventHandler.TAG, "Not starting call activity because user setup is in progress.");
        }
        return true;
    }

    @Override
    public void preDispatchKeyEvent(KeyEvent keyEvent) {
        this.getAudioManager().preDispatchKeyEvent(keyEvent, Integer.MIN_VALUE);
    }

    void sendCloseSystemWindows() {
        PhoneWindow.sendCloseSystemWindows(this.mContext, null);
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @UnsupportedAppUsage
    void startCallActivity() {
        this.sendCloseSystemWindows();
        Intent intent = new Intent("android.intent.action.CALL_BUTTON");
        intent.setFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.w(TAG, "No activity found for android.intent.action.CALL_BUTTON.");
        }
    }
}

