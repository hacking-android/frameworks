/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.internal.policy.PhoneWindow;
import java.util.Formatter;
import java.util.Locale;

public class MediaController
extends FrameLayout {
    private static final int sDefaultTimeout = 3000;
    private final AccessibilityManager mAccessibilityManager;
    @UnsupportedAppUsage
    private View mAnchor;
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private TextView mCurrentTime;
    @UnsupportedAppUsage
    private View mDecor;
    @UnsupportedAppUsage
    private WindowManager.LayoutParams mDecorLayoutParams;
    private boolean mDragging;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private TextView mEndTime;
    private final Runnable mFadeOut = new Runnable(){

        @Override
        public void run() {
            MediaController.this.hide();
        }
    };
    @UnsupportedAppUsage
    private ImageButton mFfwdButton;
    @UnsupportedAppUsage
    private final View.OnClickListener mFfwdListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            int n = MediaController.this.mPlayer.getCurrentPosition();
            MediaController.this.mPlayer.seekTo(n + 15000);
            MediaController.this.setProgress();
            MediaController.this.show(3000);
        }
    };
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private boolean mFromXml;
    private final View.OnLayoutChangeListener mLayoutChangeListener = new View.OnLayoutChangeListener(){

        @Override
        public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            MediaController.this.updateFloatingWindowLayout();
            if (MediaController.this.mShowing) {
                MediaController.this.mWindowManager.updateViewLayout(MediaController.this.mDecor, MediaController.this.mDecorLayoutParams);
            }
        }
    };
    private boolean mListenersSet;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ImageButton mNextButton;
    private View.OnClickListener mNextListener;
    @UnsupportedAppUsage
    private ImageButton mPauseButton;
    private CharSequence mPauseDescription;
    private final View.OnClickListener mPauseListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            MediaController.this.doPauseResume();
            MediaController.this.show(3000);
        }
    };
    private CharSequence mPlayDescription;
    @UnsupportedAppUsage
    private MediaPlayerControl mPlayer;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ImageButton mPrevButton;
    private View.OnClickListener mPrevListener;
    @UnsupportedAppUsage
    private ProgressBar mProgress;
    @UnsupportedAppUsage
    private ImageButton mRewButton;
    @UnsupportedAppUsage
    private final View.OnClickListener mRewListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            int n = MediaController.this.mPlayer.getCurrentPosition();
            MediaController.this.mPlayer.seekTo(n - 5000);
            MediaController.this.setProgress();
            MediaController.this.show(3000);
        }
    };
    @UnsupportedAppUsage
    private View mRoot;
    @UnsupportedAppUsage
    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
            if (!bl) {
                return;
            }
            long l = MediaController.this.mPlayer.getDuration();
            l = (long)n * l / 1000L;
            MediaController.this.mPlayer.seekTo((int)l);
            if (MediaController.this.mCurrentTime != null) {
                MediaController.this.mCurrentTime.setText(MediaController.this.stringForTime((int)l));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar view) {
            MediaController.this.show(3600000);
            MediaController.this.mDragging = true;
            view = MediaController.this;
            view.removeCallbacks(((MediaController)view).mShowProgress);
        }

        @Override
        public void onStopTrackingTouch(SeekBar view) {
            MediaController.this.mDragging = false;
            MediaController.this.setProgress();
            MediaController.this.updatePausePlay();
            MediaController.this.show(3000);
            view = MediaController.this;
            view.post(((MediaController)view).mShowProgress);
        }
    };
    private final Runnable mShowProgress = new Runnable(){

        @Override
        public void run() {
            int n = MediaController.this.setProgress();
            if (!MediaController.this.mDragging && MediaController.this.mShowing && MediaController.this.mPlayer.isPlaying()) {
                MediaController mediaController = MediaController.this;
                mediaController.postDelayed(mediaController.mShowProgress, 1000 - n % 1000);
            }
        }
    };
    @UnsupportedAppUsage
    private boolean mShowing;
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && MediaController.this.mShowing) {
                MediaController.this.hide();
            }
            return false;
        }
    };
    private final boolean mUseFastForward;
    @UnsupportedAppUsage
    private Window mWindow;
    @UnsupportedAppUsage
    private WindowManager mWindowManager;

    public MediaController(Context context) {
        this(context, true);
    }

    public MediaController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoot = this;
        this.mContext = context;
        this.mUseFastForward = true;
        this.mFromXml = true;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
    }

    public MediaController(Context context, boolean bl) {
        super(context);
        this.mContext = context;
        this.mUseFastForward = bl;
        this.initFloatingWindowLayout();
        this.initFloatingWindow();
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
    }

    private void disableUnsupportedButtons() {
        try {
            if (this.mPauseButton != null && !this.mPlayer.canPause()) {
                this.mPauseButton.setEnabled(false);
            }
            if (this.mRewButton != null && !this.mPlayer.canSeekBackward()) {
                this.mRewButton.setEnabled(false);
            }
            if (this.mFfwdButton != null && !this.mPlayer.canSeekForward()) {
                this.mFfwdButton.setEnabled(false);
            }
            if (this.mProgress != null && !this.mPlayer.canSeekBackward() && !this.mPlayer.canSeekForward()) {
                this.mProgress.setEnabled(false);
            }
        }
        catch (IncompatibleClassChangeError incompatibleClassChangeError) {
            // empty catch block
        }
    }

    private void doPauseResume() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.pause();
        } else {
            this.mPlayer.start();
        }
        this.updatePausePlay();
    }

    private void initControllerView(View view) {
        int n;
        Object object = this.mContext.getResources();
        this.mPlayDescription = ((Resources)object).getText(17040290);
        this.mPauseDescription = ((Resources)object).getText(17040289);
        this.mPauseButton = (ImageButton)view.findViewById(16909218);
        object = this.mPauseButton;
        if (object != null) {
            ((View)object).requestFocus();
            this.mPauseButton.setOnClickListener(this.mPauseListener);
        }
        this.mFfwdButton = (ImageButton)view.findViewById(16908911);
        object = this.mFfwdButton;
        int n2 = 0;
        if (object != null) {
            ((View)object).setOnClickListener(this.mFfwdListener);
            if (!this.mFromXml) {
                object = this.mFfwdButton;
                n = this.mUseFastForward ? 0 : 8;
                ((ImageView)object).setVisibility(n);
            }
        }
        this.mRewButton = (ImageButton)view.findViewById(16909290);
        object = this.mRewButton;
        if (object != null) {
            ((View)object).setOnClickListener(this.mRewListener);
            if (!this.mFromXml) {
                object = this.mRewButton;
                n = this.mUseFastForward ? n2 : 8;
                ((ImageView)object).setVisibility(n);
            }
        }
        this.mNextButton = (ImageButton)view.findViewById(16909144);
        object = this.mNextButton;
        if (object != null && !this.mFromXml && !this.mListenersSet) {
            ((ImageView)object).setVisibility(8);
        }
        this.mPrevButton = (ImageButton)view.findViewById(16909254);
        object = this.mPrevButton;
        if (object != null && !this.mFromXml && !this.mListenersSet) {
            ((ImageView)object).setVisibility(8);
        }
        this.mProgress = (ProgressBar)view.findViewById(16909103);
        object = this.mProgress;
        if (object != null) {
            if (object instanceof SeekBar) {
                ((SeekBar)object).setOnSeekBarChangeListener(this.mSeekListener);
            }
            this.mProgress.setMax(1000);
        }
        this.mEndTime = (TextView)view.findViewById(16909460);
        this.mCurrentTime = (TextView)view.findViewById(16909463);
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        this.installPrevNextListeners();
    }

    private void initFloatingWindow() {
        this.mWindowManager = (WindowManager)this.mContext.getSystemService("window");
        this.mWindow = new PhoneWindow(this.mContext);
        this.mWindow.setWindowManager(this.mWindowManager, null, null);
        this.mWindow.requestFeature(1);
        this.mDecor = this.mWindow.getDecorView();
        this.mDecor.setOnTouchListener(this.mTouchListener);
        this.mWindow.setContentView(this);
        this.mWindow.setBackgroundDrawableResource(17170445);
        this.mWindow.setVolumeControlStream(3);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setDescendantFocusability(262144);
        this.requestFocus();
    }

    private void initFloatingWindowLayout() {
        WindowManager.LayoutParams layoutParams = this.mDecorLayoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = 51;
        layoutParams.height = -2;
        layoutParams.x = 0;
        layoutParams.format = -3;
        layoutParams.type = 1000;
        layoutParams.flags |= 8519712;
        layoutParams.token = null;
        layoutParams.windowAnimations = 0;
    }

    private void installPrevNextListeners() {
        boolean bl;
        ImageButton imageButton = this.mNextButton;
        boolean bl2 = true;
        if (imageButton != null) {
            imageButton.setOnClickListener(this.mNextListener);
            imageButton = this.mNextButton;
            bl = this.mNextListener != null;
            imageButton.setEnabled(bl);
        }
        if ((imageButton = this.mPrevButton) != null) {
            imageButton.setOnClickListener(this.mPrevListener);
            imageButton = this.mPrevButton;
            bl = this.mPrevListener != null ? bl2 : false;
            imageButton.setEnabled(bl);
        }
    }

    private int setProgress() {
        Object object = this.mPlayer;
        if (object != null && !this.mDragging) {
            int n = object.getCurrentPosition();
            int n2 = this.mPlayer.getDuration();
            object = this.mProgress;
            if (object != null) {
                if (n2 > 0) {
                    ((ProgressBar)object).setProgress((int)((long)n * 1000L / (long)n2));
                }
                int n3 = this.mPlayer.getBufferPercentage();
                this.mProgress.setSecondaryProgress(n3 * 10);
            }
            if ((object = this.mEndTime) != null) {
                ((TextView)object).setText(this.stringForTime(n2));
            }
            if ((object = this.mCurrentTime) != null) {
                ((TextView)object).setText(this.stringForTime(n));
            }
            return n;
        }
        return 0;
    }

    private String stringForTime(int n) {
        int n2 = n / 1000;
        int n3 = n2 % 60;
        n = n2 / 60 % 60;
        this.mFormatBuilder.setLength(0);
        if ((n2 /= 3600) > 0) {
            return this.mFormatter.format("%d:%02d:%02d", n2, n, n3).toString();
        }
        return this.mFormatter.format("%02d:%02d", n, n3).toString();
    }

    private void updateFloatingWindowLayout() {
        int[] arrn = new int[2];
        this.mAnchor.getLocationOnScreen(arrn);
        this.mDecor.measure(View.MeasureSpec.makeMeasureSpec(this.mAnchor.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(this.mAnchor.getHeight(), Integer.MIN_VALUE));
        WindowManager.LayoutParams layoutParams = this.mDecorLayoutParams;
        layoutParams.width = this.mAnchor.getWidth();
        layoutParams.x = arrn[0] + (this.mAnchor.getWidth() - layoutParams.width) / 2;
        layoutParams.y = arrn[1] + this.mAnchor.getHeight() - this.mDecor.getMeasuredHeight();
    }

    @UnsupportedAppUsage
    private void updatePausePlay() {
        if (this.mRoot != null && this.mPauseButton != null) {
            if (this.mPlayer.isPlaying()) {
                this.mPauseButton.setImageResource(17301539);
                this.mPauseButton.setContentDescription(this.mPauseDescription);
            } else {
                this.mPauseButton.setImageResource(17301540);
                this.mPauseButton.setContentDescription(this.mPlayDescription);
            }
            return;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent object) {
        int n = ((KeyEvent)object).getKeyCode();
        boolean bl = ((KeyEvent)object).getRepeatCount() == 0 && ((KeyEvent)object).getAction() == 0;
        if (n != 79 && n != 85 && n != 62) {
            if (n == 126) {
                if (bl && !this.mPlayer.isPlaying()) {
                    this.mPlayer.start();
                    this.updatePausePlay();
                    this.show(3000);
                }
                return true;
            }
            if (n != 86 && n != 127) {
                if (n != 25 && n != 24 && n != 164 && n != 27) {
                    if (n != 4 && n != 82) {
                        this.show(3000);
                        return super.dispatchKeyEvent((KeyEvent)object);
                    }
                    if (bl) {
                        this.hide();
                    }
                    return true;
                }
                return super.dispatchKeyEvent((KeyEvent)object);
            }
            if (bl && this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                this.updatePausePlay();
                this.show(3000);
            }
            return true;
        }
        if (bl) {
            this.doPauseResume();
            this.show(3000);
            object = this.mPauseButton;
            if (object != null) {
                ((View)object).requestFocus();
            }
        }
        return true;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return MediaController.class.getName();
    }

    public void hide() {
        if (this.mAnchor == null) {
            return;
        }
        if (this.mShowing) {
            try {
                this.removeCallbacks(this.mShowProgress);
                this.mWindowManager.removeView(this.mDecor);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.w("MediaController", "already removed");
            }
            this.mShowing = false;
        }
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    protected View makeControllerView() {
        this.mRoot = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(17367184, null);
        this.initControllerView(this.mRoot);
        return this.mRoot;
    }

    @Override
    public void onFinishInflate() {
        View view = this.mRoot;
        if (view != null) {
            this.initControllerView(view);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n != 0) {
            if (n != 1) {
                if (n == 3) {
                    this.hide();
                }
            } else {
                this.show(3000);
            }
        } else {
            this.show(0);
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        this.show(3000);
        return false;
    }

    public void setAnchorView(View object) {
        View view = this.mAnchor;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.mLayoutChangeListener);
        }
        this.mAnchor = object;
        object = this.mAnchor;
        if (object != null) {
            ((View)object).addOnLayoutChangeListener(this.mLayoutChangeListener);
        }
        object = new FrameLayout.LayoutParams(-1, -1);
        this.removeAllViews();
        this.addView(this.makeControllerView(), (ViewGroup.LayoutParams)object);
    }

    @Override
    public void setEnabled(boolean bl) {
        boolean bl2;
        View view = this.mPauseButton;
        if (view != null) {
            view.setEnabled(bl);
        }
        if ((view = this.mFfwdButton) != null) {
            view.setEnabled(bl);
        }
        if ((view = this.mRewButton) != null) {
            view.setEnabled(bl);
        }
        view = this.mNextButton;
        boolean bl3 = true;
        if (view != null) {
            bl2 = bl && this.mNextListener != null;
            view.setEnabled(bl2);
        }
        if ((view = this.mPrevButton) != null) {
            bl2 = bl && this.mPrevListener != null ? bl3 : false;
            view.setEnabled(bl2);
        }
        if ((view = this.mProgress) != null) {
            view.setEnabled(bl);
        }
        this.disableUnsupportedButtons();
        super.setEnabled(bl);
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mPlayer = mediaPlayerControl;
        this.updatePausePlay();
    }

    public void setPrevNextListeners(View.OnClickListener object, View.OnClickListener onClickListener) {
        this.mNextListener = object;
        this.mPrevListener = onClickListener;
        this.mListenersSet = true;
        if (this.mRoot != null) {
            this.installPrevNextListeners();
            object = this.mNextButton;
            if (object != null && !this.mFromXml) {
                ((ImageView)object).setVisibility(0);
            }
            if ((object = this.mPrevButton) != null && !this.mFromXml) {
                ((ImageView)object).setVisibility(0);
            }
        }
    }

    public void show() {
        this.show(3000);
    }

    public void show(int n) {
        if (!this.mShowing && this.mAnchor != null) {
            this.setProgress();
            ImageButton imageButton = this.mPauseButton;
            if (imageButton != null) {
                imageButton.requestFocus();
            }
            this.disableUnsupportedButtons();
            this.updateFloatingWindowLayout();
            this.mWindowManager.addView(this.mDecor, this.mDecorLayoutParams);
            this.mShowing = true;
        }
        this.updatePausePlay();
        this.post(this.mShowProgress);
        if (n != 0 && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.removeCallbacks(this.mFadeOut);
            this.postDelayed(this.mFadeOut, n);
        }
    }

    public static interface MediaPlayerControl {
        public boolean canPause();

        public boolean canSeekBackward();

        public boolean canSeekForward();

        public int getAudioSessionId();

        public int getBufferPercentage();

        public int getCurrentPosition();

        public int getDuration();

        public boolean isPlaying();

        public void pause();

        public void seekTo(int var1);

        public void start();
    }

}

