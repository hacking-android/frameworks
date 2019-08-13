/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display._$$Lambda$NightDisplayListener$sOK1HmSbMnFLzc4SdDD1WpVWJiI;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import java.time.LocalTime;

public class NightDisplayListener {
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private final Handler mHandler;
    private final ColorDisplayManager mManager;
    private final int mUserId;

    public NightDisplayListener(Context context) {
        this(context, ActivityManager.getCurrentUser(), new Handler(Looper.getMainLooper()));
    }

    public NightDisplayListener(Context context, int n, Handler handler) {
        this.mContext = context.getApplicationContext();
        this.mManager = this.mContext.getSystemService(ColorDisplayManager.class);
        this.mUserId = n;
        this.mHandler = handler;
        this.mContentObserver = new ContentObserver(this.mHandler){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            @Override
            public void onChange(boolean var1_1, Uri var2_2) {
                block12 : {
                    super.onChange(var1_1, (Uri)var2_2);
                    var2_2 = var2_2 == null ? null : var2_2.getLastPathSegment();
                    if (var2_2 == null) return;
                    if (NightDisplayListener.access$000(NightDisplayListener.this) == null) return;
                    var3_3 = -1;
                    switch (var2_2.hashCode()) {
                        case 1578271348: {
                            if (!var2_2.equals("night_display_custom_start_time")) break;
                            var3_3 = 2;
                            ** break;
                        }
                        case 800115245: {
                            if (!var2_2.equals("night_display_activated")) break;
                            var3_3 = 0;
                            ** break;
                        }
                        case -969458956: {
                            if (!var2_2.equals("night_display_color_temperature")) break;
                            var3_3 = 4;
                            ** break;
                        }
                        case -1761668069: {
                            if (!var2_2.equals("night_display_custom_end_time")) break;
                            var3_3 = 3;
                            ** break;
                        }
                        case -2038150513: {
                            if (!var2_2.equals("night_display_auto_mode")) break;
                            var3_3 = 1;
                            break block12;
                        }
                    }
                    ** break;
                }
                if (var3_3 == 0) {
                    NightDisplayListener.access$000(NightDisplayListener.this).onActivated(NightDisplayListener.access$100(NightDisplayListener.this).isNightDisplayActivated());
                    return;
                }
                if (var3_3 == 1) {
                    NightDisplayListener.access$000(NightDisplayListener.this).onAutoModeChanged(NightDisplayListener.access$100(NightDisplayListener.this).getNightDisplayAutoMode());
                    return;
                }
                if (var3_3 == 2) {
                    NightDisplayListener.access$000(NightDisplayListener.this).onCustomStartTimeChanged(NightDisplayListener.access$100(NightDisplayListener.this).getNightDisplayCustomStartTime());
                    return;
                }
                if (var3_3 == 3) {
                    NightDisplayListener.access$000(NightDisplayListener.this).onCustomEndTimeChanged(NightDisplayListener.access$100(NightDisplayListener.this).getNightDisplayCustomEndTime());
                    return;
                }
                if (var3_3 != 4) {
                    return;
                }
                NightDisplayListener.access$000(NightDisplayListener.this).onColorTemperatureChanged(NightDisplayListener.access$100(NightDisplayListener.this).getNightDisplayColorTemperature());
            }
        };
    }

    public NightDisplayListener(Context context, Handler handler) {
        this(context, ActivityManager.getCurrentUser(), handler);
    }

    static /* synthetic */ Callback access$000(NightDisplayListener nightDisplayListener) {
        return nightDisplayListener.mCallback;
    }

    static /* synthetic */ ColorDisplayManager access$100(NightDisplayListener nightDisplayListener) {
        return nightDisplayListener.mManager;
    }

    private void setCallbackInternal(Callback object) {
        Callback callback = this.mCallback;
        if (callback != object) {
            this.mCallback = object;
            if (this.mCallback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            } else if (callback == null) {
                object = this.mContext.getContentResolver();
                ((ContentResolver)object).registerContentObserver(Settings.Secure.getUriFor("night_display_activated"), false, this.mContentObserver, this.mUserId);
                ((ContentResolver)object).registerContentObserver(Settings.Secure.getUriFor("night_display_auto_mode"), false, this.mContentObserver, this.mUserId);
                ((ContentResolver)object).registerContentObserver(Settings.Secure.getUriFor("night_display_custom_start_time"), false, this.mContentObserver, this.mUserId);
                ((ContentResolver)object).registerContentObserver(Settings.Secure.getUriFor("night_display_custom_end_time"), false, this.mContentObserver, this.mUserId);
                ((ContentResolver)object).registerContentObserver(Settings.Secure.getUriFor("night_display_color_temperature"), false, this.mContentObserver, this.mUserId);
            }
        }
    }

    public /* synthetic */ void lambda$setCallback$0$NightDisplayListener(Callback callback) {
        this.setCallbackInternal(callback);
    }

    public void setCallback(Callback callback) {
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            this.mHandler.post(new _$$Lambda$NightDisplayListener$sOK1HmSbMnFLzc4SdDD1WpVWJiI(this, callback));
        }
        this.setCallbackInternal(callback);
    }

    public static interface Callback {
        default public void onActivated(boolean bl) {
        }

        default public void onAutoModeChanged(int n) {
        }

        default public void onColorTemperatureChanged(int n) {
        }

        default public void onCustomEndTimeChanged(LocalTime localTime) {
        }

        default public void onCustomStartTimeChanged(LocalTime localTime) {
        }
    }

}

