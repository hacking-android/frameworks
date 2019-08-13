/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.PlaybackParams;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class TvView
extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final WeakReference<TvView> NULL_TV_VIEW = new WeakReference<Object>(null);
    private static final String TAG = "TvView";
    private static final int ZORDER_MEDIA = 0;
    private static final int ZORDER_MEDIA_OVERLAY = 1;
    private static final int ZORDER_ON_TOP = 2;
    private static WeakReference<TvView> sMainTvView;
    private static final Object sMainTvViewLock;
    private final AttributeSet mAttrs;
    private TvInputCallback mCallback;
    private Boolean mCaptionEnabled;
    private final int mDefStyleAttr;
    private final TvInputManager.Session.FinishedInputEventCallback mFinishedInputEventCallback = new TvInputManager.Session.FinishedInputEventCallback(){

        @Override
        public void onFinishedInputEvent(Object object, boolean bl) {
            if (bl) {
                return;
            }
            if (TvView.this.dispatchUnhandledInputEvent((InputEvent)(object = (InputEvent)object))) {
                return;
            }
            ViewRootImpl viewRootImpl = TvView.this.getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchUnhandledInputEvent((InputEvent)object);
            }
        }
    };
    private final Handler mHandler = new Handler();
    private OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private boolean mOverlayViewCreated;
    private Rect mOverlayViewFrame;
    private final Queue<Pair<String, Bundle>> mPendingAppPrivateCommands = new ArrayDeque<Pair<String, Bundle>>();
    private TvInputManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private Float mStreamVolume;
    private Surface mSurface;
    private boolean mSurfaceChanged;
    private int mSurfaceFormat;
    private int mSurfaceHeight;
    private final SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback(){

        @Override
        public void surfaceChanged(SurfaceHolder object, int n, int n2, int n3) {
            TvView.this.mSurfaceFormat = n;
            TvView.this.mSurfaceWidth = n2;
            TvView.this.mSurfaceHeight = n3;
            TvView.this.mSurfaceChanged = true;
            object = TvView.this;
            ((TvView)object).dispatchSurfaceChanged(((TvView)object).mSurfaceFormat, TvView.this.mSurfaceWidth, TvView.this.mSurfaceHeight);
        }

        @Override
        public void surfaceCreated(SurfaceHolder object) {
            TvView.this.mSurface = object.getSurface();
            object = TvView.this;
            ((TvView)object).setSessionSurface(((TvView)object).mSurface);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            TvView.this.mSurface = null;
            TvView.this.mSurfaceChanged = false;
            TvView.this.setSessionSurface(null);
        }
    };
    private SurfaceView mSurfaceView;
    private int mSurfaceViewBottom;
    private int mSurfaceViewLeft;
    private int mSurfaceViewRight;
    private int mSurfaceViewTop;
    private int mSurfaceWidth;
    private TimeShiftPositionCallback mTimeShiftPositionCallback;
    private final TvInputManager mTvInputManager;
    private boolean mUseRequestedSurfaceLayout;
    private int mWindowZOrder;

    static {
        sMainTvViewLock = new Object();
        sMainTvView = NULL_TV_VIEW;
    }

    public TvView(Context context) {
        this(context, null, 0);
    }

    public TvView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mAttrs = attributeSet;
        this.mDefStyleAttr = n;
        this.resetSurfaceView();
        this.mTvInputManager = (TvInputManager)this.getContext().getSystemService("tv_input");
    }

    private boolean checkChangeHdmiCecActiveSourcePermission() {
        boolean bl = this.getContext().checkSelfPermission("android.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE") == 0;
        return bl;
    }

    private void createSessionOverlayView() {
        if (this.mSession != null && this.isAttachedToWindow() && !this.mOverlayViewCreated && this.mWindowZOrder == 0) {
            this.mOverlayViewFrame = this.getViewFrameOnScreen();
            this.mSession.createOverlayView(this, this.mOverlayViewFrame);
            this.mOverlayViewCreated = true;
            return;
        }
    }

    private void dispatchSurfaceChanged(int n, int n2, int n3) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.dispatchSurfaceChanged(n, n2, n3);
    }

    private void ensurePositionTracking() {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        boolean bl = this.mTimeShiftPositionCallback != null;
        session.timeShiftEnablePositionTracking(bl);
    }

    private Rect getViewFrameOnScreen() {
        Rect rect = new Rect();
        this.getGlobalVisibleRect(rect);
        RectF rectF = new RectF(rect);
        this.getMatrix().mapRect(rectF);
        rectF.round(rect);
        return rect;
    }

    private void relayoutSessionOverlayView() {
        if (this.mSession != null && this.isAttachedToWindow() && this.mOverlayViewCreated && this.mWindowZOrder == 0) {
            Rect rect = this.getViewFrameOnScreen();
            if (rect.equals(this.mOverlayViewFrame)) {
                return;
            }
            this.mSession.relayoutOverlayView(rect);
            this.mOverlayViewFrame = rect;
            return;
        }
    }

    private void removeSessionOverlayView() {
        TvInputManager.Session session = this.mSession;
        if (session != null && this.mOverlayViewCreated) {
            session.removeOverlayView();
            this.mOverlayViewCreated = false;
            this.mOverlayViewFrame = null;
            return;
        }
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        this.mPendingAppPrivateCommands.clear();
        if (this.mSession != null) {
            this.setSessionSurface(null);
            this.removeSessionOverlayView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            this.resetSurfaceView();
        }
    }

    private void resetSurfaceView() {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            this.removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        this.mSurfaceView = new SurfaceView(this.getContext(), this.mAttrs, this.mDefStyleAttr){

            @Override
            protected void updateSurface() {
                super.updateSurface();
                TvView.this.relayoutSessionOverlayView();
            }
        };
        this.mSurfaceView.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        int n = this.mWindowZOrder;
        if (n == 1) {
            this.mSurfaceView.setZOrderMediaOverlay(true);
        } else if (n == 2) {
            this.mSurfaceView.setZOrderOnTop(true);
        }
        this.addView(this.mSurfaceView);
    }

    private void setSessionSurface(Surface surface) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setSurface(surface);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent inputEvent) {
        boolean bl = super.dispatchGenericMotionEvent((MotionEvent)inputEvent);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        if (this.mSession.dispatchInputEvent(inputEvent = inputEvent.copy(), inputEvent, this.mFinishedInputEventCallback, this.mHandler) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent inputEvent) {
        boolean bl = super.dispatchKeyEvent((KeyEvent)inputEvent);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        if (this.mSession.dispatchInputEvent(inputEvent = inputEvent.copy(), inputEvent, this.mFinishedInputEventCallback, this.mHandler) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent inputEvent) {
        boolean bl = super.dispatchTouchEvent((MotionEvent)inputEvent);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        if (this.mSession.dispatchInputEvent(inputEvent = inputEvent.copy(), inputEvent, this.mFinishedInputEventCallback, this.mHandler) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent inputEvent) {
        boolean bl = super.dispatchTrackballEvent((MotionEvent)inputEvent);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        if (this.mSession.dispatchInputEvent(inputEvent = inputEvent.copy(), inputEvent, this.mFinishedInputEventCallback, this.mHandler) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean dispatchUnhandledInputEvent(InputEvent inputEvent) {
        OnUnhandledInputEventListener onUnhandledInputEventListener = this.mOnUnhandledInputEventListener;
        if (onUnhandledInputEventListener != null && onUnhandledInputEventListener.onUnhandledInputEvent(inputEvent)) {
            return true;
        }
        return this.onUnhandledInputEvent(inputEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dispatchWindowFocusChanged(boolean bl) {
        super.dispatchWindowFocusChanged(bl);
        Object object = sMainTvViewLock;
        synchronized (object) {
            if (bl && this == sMainTvView.get() && this.mSession != null && this.checkChangeHdmiCecActiveSourcePermission()) {
                this.mSession.setMain();
            }
            return;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        super.draw(canvas);
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        if (this.mWindowZOrder != 2 && region != null) {
            int n = this.getWidth();
            int n2 = this.getHeight();
            if (n > 0 && n2 > 0) {
                int[] arrn = new int[2];
                this.getLocationInWindow(arrn);
                int n3 = arrn[0];
                int n4 = arrn[1];
                region.op(n3, n4, n3 + n, n4 + n2, Region.Op.UNION);
            }
        }
        return super.gatherTransparentRegion(region);
    }

    public String getSelectedTrack(int n) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return null;
        }
        return session.getSelectedTrack(n);
    }

    public List<TvTrackInfo> getTracks(int n) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return null;
        }
        return session.getTracks(n);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.createSessionOverlayView();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.removeSessionOverlayView();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, n3 - n, n4 - n2);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        this.mSurfaceView.measure(n, n2);
        int n3 = this.mSurfaceView.getMeasuredWidth();
        int n4 = this.mSurfaceView.getMeasuredHeight();
        int n5 = this.mSurfaceView.getMeasuredState();
        this.setMeasuredDimension(TvView.resolveSizeAndState(n3, n, n5), TvView.resolveSizeAndState(n4, n2, n5 << 16));
    }

    public boolean onUnhandledInputEvent(InputEvent inputEvent) {
        return false;
    }

    @Override
    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        this.mSurfaceView.setVisibility(n);
        if (n == 0) {
            this.createSessionOverlayView();
        } else {
            this.removeSessionOverlayView();
        }
    }

    public void requestUnblockContent(TvContentRating tvContentRating) {
        this.unblockContent(tvContentRating);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        Object object = sMainTvViewLock;
        synchronized (object) {
            if (this == sMainTvView.get()) {
                sMainTvView = NULL_TV_VIEW;
            }
        }
        this.resetInternal();
    }

    public void selectTrack(int n, String string2) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.selectTrack(n, string2);
        }
    }

    public void sendAppPrivateCommand(String string2, Bundle bundle) {
        if (!TextUtils.isEmpty(string2)) {
            Object object = this.mSession;
            if (object != null) {
                ((TvInputManager.Session)object).sendAppPrivateCommand(string2, bundle);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendAppPrivateCommand - session not yet created (action \"");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("\" pending)");
                Log.w(TAG, ((StringBuilder)object).toString());
                this.mPendingAppPrivateCommands.add(Pair.create(string2, bundle));
            }
            return;
        }
        throw new IllegalArgumentException("action cannot be null or an empty string");
    }

    public void setCallback(TvInputCallback tvInputCallback) {
        this.mCallback = tvInputCallback;
    }

    public void setCaptionEnabled(boolean bl) {
        this.mCaptionEnabled = bl;
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.setCaptionEnabled(bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void setMain() {
        Object object = sMainTvViewLock;
        synchronized (object) {
            WeakReference<TvView> weakReference = new WeakReference<TvView>(this);
            sMainTvView = weakReference;
            if (this.hasWindowFocus() && this.mSession != null) {
                this.mSession.setMain();
            }
            return;
        }
    }

    public void setOnUnhandledInputEventListener(OnUnhandledInputEventListener onUnhandledInputEventListener) {
        this.mOnUnhandledInputEventListener = onUnhandledInputEventListener;
    }

    public void setStreamVolume(float f) {
        this.mStreamVolume = Float.valueOf(f);
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setStreamVolume(f);
    }

    public void setTimeShiftPositionCallback(TimeShiftPositionCallback timeShiftPositionCallback) {
        this.mTimeShiftPositionCallback = timeShiftPositionCallback;
        this.ensurePositionTracking();
    }

    public void setZOrderMediaOverlay(boolean bl) {
        if (bl) {
            this.mWindowZOrder = 1;
            this.removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            this.createSessionOverlayView();
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderOnTop(false);
            this.mSurfaceView.setZOrderMediaOverlay(bl);
        }
    }

    public void setZOrderOnTop(boolean bl) {
        if (bl) {
            this.mWindowZOrder = 2;
            this.removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            this.createSessionOverlayView();
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderMediaOverlay(false);
            this.mSurfaceView.setZOrderOnTop(bl);
        }
    }

    public void timeShiftPause() {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftPause();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void timeShiftPlay(String object, Uri object2) {
        if (TextUtils.isEmpty((CharSequence)object)) throw new IllegalArgumentException("inputId cannot be null or an empty string");
        Object object3 = sMainTvViewLock;
        synchronized (object3) {
            if (sMainTvView.get() == null) {
                WeakReference<TvView> weakReference = new WeakReference<TvView>(this);
                sMainTvView = weakReference;
            }
        }
        object3 = this.mSessionCallback;
        if (object3 != null && TextUtils.equals(((MySessionCallback)object3).mInputId, (CharSequence)object)) {
            object = this.mSession;
            if (object != null) {
                ((TvInputManager.Session)object).timeShiftPlay((Uri)object2);
                return;
            }
            this.mSessionCallback.mRecordedProgramUri = object2;
            return;
        }
        this.resetInternal();
        this.mSessionCallback = new MySessionCallback((String)object, (Uri)object2);
        object2 = this.mTvInputManager;
        if (object2 == null) return;
        ((TvInputManager)object2).createSession((String)object, this.mSessionCallback, this.mHandler);
    }

    public void timeShiftResume() {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftResume();
        }
    }

    public void timeShiftSeekTo(long l) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftSeekTo(l);
        }
    }

    public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftSetPlaybackParams(playbackParams);
        }
    }

    public void tune(String string2, Uri uri) {
        this.tune(string2, uri, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void tune(String object, Uri object2, Bundle bundle) {
        if (TextUtils.isEmpty((CharSequence)object)) throw new IllegalArgumentException("inputId cannot be null or an empty string");
        Object object3 = sMainTvViewLock;
        synchronized (object3) {
            if (sMainTvView.get() == null) {
                WeakReference<TvView> weakReference = new WeakReference<TvView>(this);
                sMainTvView = weakReference;
            }
        }
        object3 = this.mSessionCallback;
        if (object3 != null && TextUtils.equals(((MySessionCallback)object3).mInputId, (CharSequence)object)) {
            object = this.mSession;
            if (object != null) {
                ((TvInputManager.Session)object).tune((Uri)object2, bundle);
                return;
            }
            object = this.mSessionCallback;
            ((MySessionCallback)object).mChannelUri = object2;
            ((MySessionCallback)object).mTuneParams = bundle;
            return;
        }
        this.resetInternal();
        this.mSessionCallback = new MySessionCallback((String)object, (Uri)object2, bundle);
        object2 = this.mTvInputManager;
        if (object2 == null) return;
        ((TvInputManager)object2).createSession((String)object, this.mSessionCallback, this.mHandler);
    }

    @SystemApi
    public void unblockContent(TvContentRating tvContentRating) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.unblockContent(tvContentRating);
        }
    }

    private class MySessionCallback
    extends TvInputManager.SessionCallback {
        Uri mChannelUri;
        final String mInputId;
        Uri mRecordedProgramUri;
        Bundle mTuneParams;

        MySessionCallback(String string2, Uri uri) {
            this.mInputId = string2;
            this.mRecordedProgramUri = uri;
        }

        MySessionCallback(String string2, Uri uri, Bundle bundle) {
            this.mInputId = string2;
            this.mChannelUri = uri;
            this.mTuneParams = bundle;
        }

        @Override
        public void onChannelRetuned(TvInputManager.Session session, Uri uri) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onChannelRetuned - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onChannelRetuned(this.mInputId, uri);
            }
        }

        @Override
        public void onContentAllowed(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onContentAllowed - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onContentAllowed(this.mInputId);
            }
        }

        @Override
        public void onContentBlocked(TvInputManager.Session session, TvContentRating tvContentRating) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onContentBlocked - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onContentBlocked(this.mInputId, tvContentRating);
            }
        }

        @Override
        public void onLayoutSurface(TvInputManager.Session session, int n, int n2, int n3, int n4) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvView.this.mSurfaceViewLeft = n;
            TvView.this.mSurfaceViewTop = n2;
            TvView.this.mSurfaceViewRight = n3;
            TvView.this.mSurfaceViewBottom = n4;
            TvView.this.mUseRequestedSurfaceLayout = true;
            TvView.this.requestLayout();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onSessionCreated(TvInputManager.Session object) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionCreated - session already created");
                if (object == null) return;
                ((TvInputManager.Session)object).release();
                return;
            }
            TvView.this.mSession = (TvInputManager.Session)object;
            if (object == null) {
                TvView.this.mSessionCallback = null;
                if (TvView.this.mCallback == null) return;
                TvView.this.mCallback.onConnectionFailed(this.mInputId);
                return;
            }
            for (Pair pair : TvView.this.mPendingAppPrivateCommands) {
                TvView.this.mSession.sendAppPrivateCommand((String)pair.first, (Bundle)pair.second);
            }
            TvView.this.mPendingAppPrivateCommands.clear();
            object = sMainTvViewLock;
            // MONITORENTER : object
            if (TvView.this.hasWindowFocus() && TvView.this == sMainTvView.get() && TvView.this.checkChangeHdmiCecActiveSourcePermission()) {
                TvView.this.mSession.setMain();
            }
            // MONITOREXIT : object
            if (TvView.this.mSurface != null) {
                object = TvView.this;
                ((TvView)object).setSessionSurface(((TvView)object).mSurface);
                if (TvView.this.mSurfaceChanged) {
                    object = TvView.this;
                    ((TvView)object).dispatchSurfaceChanged(((TvView)object).mSurfaceFormat, TvView.this.mSurfaceWidth, TvView.this.mSurfaceHeight);
                }
            }
            TvView.this.createSessionOverlayView();
            if (TvView.this.mStreamVolume != null) {
                TvView.this.mSession.setStreamVolume(TvView.this.mStreamVolume.floatValue());
            }
            if (TvView.this.mCaptionEnabled != null) {
                TvView.this.mSession.setCaptionEnabled(TvView.this.mCaptionEnabled);
            }
            if (this.mChannelUri != null) {
                TvView.this.mSession.tune(this.mChannelUri, this.mTuneParams);
            } else {
                TvView.this.mSession.timeShiftPlay(this.mRecordedProgramUri);
            }
            TvView.this.ensurePositionTracking();
        }

        @Override
        public void onSessionEvent(TvInputManager.Session session, String string2, Bundle bundle) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionEvent - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onEvent(this.mInputId, string2, bundle);
            }
        }

        @Override
        public void onSessionReleased(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionReleased - session not created");
                return;
            }
            TvView.this.mOverlayViewCreated = false;
            TvView.this.mOverlayViewFrame = null;
            TvView.this.mSessionCallback = null;
            TvView.this.mSession = null;
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onDisconnected(this.mInputId);
            }
        }

        @Override
        public void onTimeShiftCurrentPositionChanged(TvInputManager.Session session, long l) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftCurrentPositionChanged - session not created");
                return;
            }
            if (TvView.this.mTimeShiftPositionCallback != null) {
                TvView.this.mTimeShiftPositionCallback.onTimeShiftCurrentPositionChanged(this.mInputId, l);
            }
        }

        @Override
        public void onTimeShiftStartPositionChanged(TvInputManager.Session session, long l) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftStartPositionChanged - session not created");
                return;
            }
            if (TvView.this.mTimeShiftPositionCallback != null) {
                TvView.this.mTimeShiftPositionCallback.onTimeShiftStartPositionChanged(this.mInputId, l);
            }
        }

        @Override
        public void onTimeShiftStatusChanged(TvInputManager.Session session, int n) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftStatusChanged - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTimeShiftStatusChanged(this.mInputId, n);
            }
        }

        @Override
        public void onTrackSelected(TvInputManager.Session session, int n, String string2) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTrackSelected - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTrackSelected(this.mInputId, n, string2);
            }
        }

        @Override
        public void onTracksChanged(TvInputManager.Session session, List<TvTrackInfo> list) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTracksChanged - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTracksChanged(this.mInputId, list);
            }
        }

        @Override
        public void onVideoAvailable(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoAvailable - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoAvailable(this.mInputId);
            }
        }

        @Override
        public void onVideoSizeChanged(TvInputManager.Session session, int n, int n2) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoSizeChanged - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoSizeChanged(this.mInputId, n, n2);
            }
        }

        @Override
        public void onVideoUnavailable(TvInputManager.Session session, int n) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoUnavailable - session not created");
                return;
            }
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoUnavailable(this.mInputId, n);
            }
        }
    }

    public static interface OnUnhandledInputEventListener {
        public boolean onUnhandledInputEvent(InputEvent var1);
    }

    public static abstract class TimeShiftPositionCallback {
        public void onTimeShiftCurrentPositionChanged(String string2, long l) {
        }

        public void onTimeShiftStartPositionChanged(String string2, long l) {
        }
    }

    public static abstract class TvInputCallback {
        public void onChannelRetuned(String string2, Uri uri) {
        }

        public void onConnectionFailed(String string2) {
        }

        public void onContentAllowed(String string2) {
        }

        public void onContentBlocked(String string2, TvContentRating tvContentRating) {
        }

        public void onDisconnected(String string2) {
        }

        @SystemApi
        public void onEvent(String string2, String string3, Bundle bundle) {
        }

        public void onTimeShiftStatusChanged(String string2, int n) {
        }

        public void onTrackSelected(String string2, int n, String string3) {
        }

        public void onTracksChanged(String string2, List<TvTrackInfo> list) {
        }

        public void onVideoAvailable(String string2) {
        }

        public void onVideoSizeChanged(String string2, int n, int n2) {
        }

        public void onVideoUnavailable(String string2, int n) {
        }
    }

}

