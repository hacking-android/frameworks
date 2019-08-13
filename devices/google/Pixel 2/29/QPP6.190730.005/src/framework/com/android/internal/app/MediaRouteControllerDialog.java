/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaRouter;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MediaRouteControllerDialog
extends AlertDialog {
    private static final int VOLUME_UPDATE_DELAY_MILLIS = 250;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private View mControlView;
    private boolean mCreated;
    private Drawable mCurrentIconDrawable;
    private Drawable mMediaRouteButtonDrawable;
    private int[] mMediaRouteConnectingState = new int[]{16842912, 16842910};
    private int[] mMediaRouteOnState = new int[]{16843518, 16842910};
    private final MediaRouter.RouteInfo mRoute;
    private final MediaRouter mRouter;
    private boolean mVolumeControlEnabled = true;
    private LinearLayout mVolumeLayout;
    private SeekBar mVolumeSlider;
    private boolean mVolumeSliderTouched;

    public MediaRouteControllerDialog(Context context, int n) {
        super(context, n);
        this.mRouter = (MediaRouter)context.getSystemService("media_router");
        this.mCallback = new MediaRouterCallback();
        this.mRoute = this.mRouter.getSelectedRoute();
    }

    private Drawable getIconDrawable() {
        Drawable drawable2 = this.mMediaRouteButtonDrawable;
        if (!(drawable2 instanceof StateListDrawable)) {
            return drawable2;
        }
        if (this.mRoute.isConnecting()) {
            drawable2 = (StateListDrawable)this.mMediaRouteButtonDrawable;
            drawable2.setState(this.mMediaRouteConnectingState);
            return ((DrawableContainer)drawable2).getCurrent();
        }
        drawable2 = (StateListDrawable)this.mMediaRouteButtonDrawable;
        drawable2.setState(this.mMediaRouteOnState);
        return ((DrawableContainer)drawable2).getCurrent();
    }

    private boolean isVolumeControlAvailable() {
        boolean bl = this.mVolumeControlEnabled;
        boolean bl2 = true;
        if (!bl || this.mRoute.getVolumeHandling() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    private Drawable obtainMediaRouteButtonDrawable() {
        Object object = this.getContext();
        Object object2 = new TypedValue();
        if (!((Context)object).getTheme().resolveAttribute(16843693, (TypedValue)object2, true)) {
            return null;
        }
        object = ((Context)object).obtainStyledAttributes(((TypedValue)object2).data, new int[]{17956922});
        object2 = ((TypedArray)object).getDrawable(0);
        ((TypedArray)object).recycle();
        return object2;
    }

    private boolean update() {
        if (this.mRoute.isSelected() && !this.mRoute.isDefault()) {
            this.setTitle(this.mRoute.getName());
            this.updateVolume();
            Drawable drawable2 = this.getIconDrawable();
            if (drawable2 != this.mCurrentIconDrawable) {
                this.mCurrentIconDrawable = drawable2;
                Drawable drawable3 = drawable2;
                if (drawable2 instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable)drawable2;
                    if (!this.mAttachedToWindow && !this.mRoute.isConnecting()) {
                        if (animationDrawable.isRunning()) {
                            animationDrawable.stop();
                        }
                        drawable3 = animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1);
                    } else {
                        drawable3 = drawable2;
                        if (!animationDrawable.isRunning()) {
                            animationDrawable.start();
                            drawable3 = drawable2;
                        }
                    }
                }
                this.setIcon(drawable3);
            }
            return true;
        }
        this.dismiss();
        return false;
    }

    private void updateVolume() {
        if (!this.mVolumeSliderTouched) {
            if (this.isVolumeControlAvailable()) {
                this.mVolumeLayout.setVisibility(0);
                this.mVolumeSlider.setMax(this.mRoute.getVolumeMax());
                this.mVolumeSlider.setProgress(this.mRoute.getVolume());
            } else {
                this.mVolumeLayout.setVisibility(8);
            }
        }
    }

    public View getMediaControlView() {
        return this.mControlView;
    }

    public MediaRouter.RouteInfo getRoute() {
        return this.mRoute;
    }

    public boolean isVolumeControlEnabled() {
        return this.mVolumeControlEnabled;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(0, this.mCallback, 2);
        this.update();
    }

    @Override
    protected void onCreate(Bundle object) {
        this.setTitle(this.mRoute.getName());
        this.setButton(-2, (CharSequence)this.getContext().getResources().getString(17040312), new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int n) {
                if (MediaRouteControllerDialog.this.mRoute.isSelected()) {
                    if (MediaRouteControllerDialog.this.mRoute.isBluetooth()) {
                        MediaRouteControllerDialog.this.mRouter.getDefaultRoute().select();
                    } else {
                        MediaRouteControllerDialog.this.mRouter.getFallbackRoute().select();
                    }
                }
                MediaRouteControllerDialog.this.dismiss();
            }
        });
        View view = this.getLayoutInflater().inflate(17367186, null);
        this.setView(view, 0, 0, 0, 0);
        super.onCreate((Bundle)object);
        Object object2 = this.getWindow().findViewById(16908853);
        if (object2 != null) {
            ((View)object2).setMinimumHeight(0);
        }
        this.mVolumeLayout = (LinearLayout)view.findViewById(16909100);
        this.mVolumeSlider = (SeekBar)view.findViewById(16909101);
        this.mVolumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            private final Runnable mStopTrackingTouch = new Runnable(){

                @Override
                public void run() {
                    if (MediaRouteControllerDialog.this.mVolumeSliderTouched) {
                        MediaRouteControllerDialog.this.mVolumeSliderTouched = false;
                        MediaRouteControllerDialog.this.updateVolume();
                    }
                }
            };

            @Override
            public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
                if (bl) {
                    MediaRouteControllerDialog.this.mRoute.requestSetVolume(n);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (MediaRouteControllerDialog.this.mVolumeSliderTouched) {
                    MediaRouteControllerDialog.this.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
                } else {
                    MediaRouteControllerDialog.this.mVolumeSliderTouched = true;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaRouteControllerDialog.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 250L);
            }

        });
        this.mMediaRouteButtonDrawable = this.obtainMediaRouteButtonDrawable();
        this.mCreated = true;
        if (this.update()) {
            this.mControlView = this.onCreateMediaControlView((Bundle)object);
            object = (FrameLayout)view.findViewById(16909097);
            object2 = this.mControlView;
            if (object2 != null) {
                ((ViewGroup)object).addView((View)object2);
                ((View)object).setVisibility(0);
            } else {
                ((View)object).setVisibility(8);
            }
        }
    }

    public View onCreateMediaControlView(Bundle bundle) {
        return null;
    }

    @Override
    public void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        this.mAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent object) {
        if (n != 25 && n != 24) {
            return super.onKeyDown(n, (KeyEvent)object);
        }
        object = this.mRoute;
        n = n == 25 ? -1 : 1;
        ((MediaRouter.RouteInfo)object).requestUpdateVolume(n);
        return true;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (n != 25 && n != 24) {
            return super.onKeyUp(n, keyEvent);
        }
        return true;
    }

    public void setVolumeControlEnabled(boolean bl) {
        if (this.mVolumeControlEnabled != bl) {
            this.mVolumeControlEnabled = bl;
            if (this.mCreated) {
                this.updateVolume();
            }
        }
    }

    private final class MediaRouterCallback
    extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerDialog.this.update();
        }

        @Override
        public void onRouteGrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int n) {
            MediaRouteControllerDialog.this.update();
        }

        @Override
        public void onRouteUngrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
            MediaRouteControllerDialog.this.update();
        }

        @Override
        public void onRouteUnselected(MediaRouter mediaRouter, int n, MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerDialog.this.update();
        }

        @Override
        public void onRouteVolumeChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            if (routeInfo == MediaRouteControllerDialog.this.mRoute) {
                MediaRouteControllerDialog.this.updateVolume();
            }
        }
    }

}

