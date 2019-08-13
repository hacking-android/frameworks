/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.Metadata;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewParent;
import android.widget.MediaController;
import java.io.InputStream;
import java.util.Map;
import java.util.Vector;

public class VideoView
extends SurfaceView
implements MediaController.MediaPlayerControl,
SubtitleController.Anchor {
    private static final int STATE_ERROR = -1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final String TAG = "VideoView";
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(1).setContentType(3).build();
    private int mAudioFocusType = 1;
    private AudioManager mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
    private int mAudioSession;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener(){

        @Override
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int n) {
            VideoView.this.mCurrentBufferPercentage = n;
        }
    };
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            VideoView.this.mCurrentState = 5;
            VideoView.this.mTargetState = 5;
            if (VideoView.this.mMediaController != null) {
                VideoView.this.mMediaController.hide();
            }
            if (VideoView.this.mOnCompletionListener != null) {
                VideoView.this.mOnCompletionListener.onCompletion(VideoView.this.mMediaPlayer);
            }
            if (VideoView.this.mAudioFocusType != 0) {
                VideoView.this.mAudioManager.abandonAudioFocus(null);
            }
        }
    };
    @UnsupportedAppUsage
    private int mCurrentBufferPercentage;
    @UnsupportedAppUsage
    private int mCurrentState = 0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener(){

        @Override
        public boolean onError(MediaPlayer object, int n, int n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(",");
            ((StringBuilder)object).append(n2);
            Log.d(VideoView.TAG, ((StringBuilder)object).toString());
            VideoView.this.mCurrentState = -1;
            VideoView.this.mTargetState = -1;
            if (VideoView.this.mMediaController != null) {
                VideoView.this.mMediaController.hide();
            }
            if (VideoView.this.mOnErrorListener != null && VideoView.this.mOnErrorListener.onError(VideoView.this.mMediaPlayer, n, n2)) {
                return true;
            }
            if (VideoView.this.getWindowToken() != null) {
                VideoView.this.mContext.getResources();
                n = n == 200 ? 17039381 : 17039377;
                new AlertDialog.Builder(VideoView.this.mContext).setMessage(n).setPositiveButton(17039376, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int n) {
                        if (VideoView.this.mOnCompletionListener != null) {
                            VideoView.this.mOnCompletionListener.onCompletion(VideoView.this.mMediaPlayer);
                        }
                    }
                }).setCancelable(false).show();
            }
            return true;
        }

    };
    @UnsupportedAppUsage
    private Map<String, String> mHeaders;
    private MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener(){

        @Override
        public boolean onInfo(MediaPlayer mediaPlayer, int n, int n2) {
            if (VideoView.this.mOnInfoListener != null) {
                VideoView.this.mOnInfoListener.onInfo(mediaPlayer, n, n2);
            }
            return true;
        }
    };
    @UnsupportedAppUsage
    private MediaController mMediaController;
    @UnsupportedAppUsage
    private MediaPlayer mMediaPlayer = null;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private final Vector<Pair<InputStream, MediaFormat>> mPendingSubtitleTracks = new Vector();
    @UnsupportedAppUsage
    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener(){

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            VideoView.this.mCurrentState = 2;
            Object object = mediaPlayer.getMetadata(false, false);
            if (object != null) {
                VideoView videoView = VideoView.this;
                boolean bl = !((Metadata)object).has(1) || ((Metadata)object).getBoolean(1);
                videoView.mCanPause = bl;
                videoView = VideoView.this;
                bl = !((Metadata)object).has(2) || ((Metadata)object).getBoolean(2);
                videoView.mCanSeekBack = bl;
                videoView = VideoView.this;
                bl = !((Metadata)object).has(3) || ((Metadata)object).getBoolean(3);
                videoView.mCanSeekForward = bl;
            } else {
                object = VideoView.this;
                ((VideoView)object).mCanPause = (((VideoView)object).mCanSeekBack = (((VideoView)object).mCanSeekForward = true));
            }
            if (VideoView.this.mOnPreparedListener != null) {
                VideoView.this.mOnPreparedListener.onPrepared(VideoView.this.mMediaPlayer);
            }
            if (VideoView.this.mMediaController != null) {
                VideoView.this.mMediaController.setEnabled(true);
            }
            VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
            VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
            int n = VideoView.this.mSeekWhenPrepared;
            if (n != 0) {
                VideoView.this.seekTo(n);
            }
            if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                if (VideoView.this.mSurfaceWidth == VideoView.this.mVideoWidth && VideoView.this.mSurfaceHeight == VideoView.this.mVideoHeight) {
                    if (VideoView.this.mTargetState == 3) {
                        VideoView.this.start();
                        if (VideoView.this.mMediaController != null) {
                            VideoView.this.mMediaController.show();
                        }
                    } else if (!(VideoView.this.isPlaying() || n == 0 && VideoView.this.getCurrentPosition() <= 0 || VideoView.this.mMediaController == null)) {
                        VideoView.this.mMediaController.show(0);
                    }
                }
            } else if (VideoView.this.mTargetState == 3) {
                VideoView.this.start();
            }
        }
    };
    @UnsupportedAppUsage
    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback(){

        @Override
        public void surfaceChanged(SurfaceHolder object, int n, int n2, int n3) {
            VideoView.this.mSurfaceWidth = n2;
            VideoView.this.mSurfaceHeight = n3;
            n = VideoView.this.mTargetState;
            int n4 = 1;
            n = n == 3 ? 1 : 0;
            n2 = VideoView.this.mVideoWidth == n2 && VideoView.this.mVideoHeight == n3 ? n4 : 0;
            if (VideoView.this.mMediaPlayer != null && n != 0 && n2 != 0) {
                if (VideoView.this.mSeekWhenPrepared != 0) {
                    object = VideoView.this;
                    ((VideoView)object).seekTo(((VideoView)object).mSeekWhenPrepared);
                }
                VideoView.this.start();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            VideoView.this.mSurfaceHolder = surfaceHolder;
            VideoView.this.openVideo();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            VideoView.this.mSurfaceHolder = null;
            if (VideoView.this.mMediaController != null) {
                VideoView.this.mMediaController.hide();
            }
            VideoView.this.release(true);
        }
    };
    private int mSeekWhenPrepared;
    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener(){

        @Override
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int n, int n2) {
            VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
            VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
            if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                VideoView.this.requestLayout();
            }
        }
    };
    private SubtitleTrack.RenderingWidget mSubtitleWidget;
    private SubtitleTrack.RenderingWidget.OnChangedListener mSubtitlesChangedListener;
    private int mSurfaceHeight;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private SurfaceHolder mSurfaceHolder = null;
    private int mSurfaceWidth;
    @UnsupportedAppUsage
    private int mTargetState = 0;
    @UnsupportedAppUsage
    private Uri mUri;
    @UnsupportedAppUsage
    private int mVideoHeight = 0;
    @UnsupportedAppUsage
    private int mVideoWidth = 0;

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public VideoView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.getHolder().addCallback(this.mSHCallback);
        this.getHolder().setType(3);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    private void attachMediaController() {
        View view;
        if (this.mMediaPlayer != null && (view = this.mMediaController) != null) {
            view.setMediaPlayer(this);
            view = this.getParent() instanceof View ? (View)((Object)this.getParent()) : this;
            this.mMediaController.setAnchorView(view);
            this.mMediaController.setEnabled(this.isInPlaybackState());
        }
    }

    private boolean isInPlaybackState() {
        int n;
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        boolean bl = true;
        if (mediaPlayer == null || (n = this.mCurrentState) == -1 || n == 0 || n == 1) {
            bl = false;
        }
        return bl;
    }

    private void measureAndLayoutSubtitleWidget() {
        int n = this.getWidth();
        int n2 = this.getPaddingLeft();
        int n3 = this.getPaddingRight();
        int n4 = this.getHeight();
        int n5 = this.getPaddingTop();
        int n6 = this.getPaddingBottom();
        this.mSubtitleWidget.setSize(n - n2 - n3, n4 - n5 - n6);
    }

    /*
     * Exception decompiling
     */
    private void openVideo() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @UnsupportedAppUsage
    private void release(boolean bl) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mPendingSubtitleTracks.clear();
            this.mCurrentState = 0;
            if (bl) {
                this.mTargetState = 0;
            }
            if (this.mAudioFocusType != 0) {
                this.mAudioManager.abandonAudioFocus(null);
            }
        }
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void addSubtitleSource(InputStream inputStream, MediaFormat mediaFormat) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            this.mPendingSubtitleTracks.add(Pair.create(inputStream, mediaFormat));
        } else {
            try {
                mediaPlayer.addSubtitleSource(inputStream, mediaFormat);
            }
            catch (IllegalStateException illegalStateException) {
                this.mInfoListener.onInfo(this.mMediaPlayer, 901, 0);
            }
        }
    }

    @Override
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mSubtitleWidget != null) {
            int n = canvas.save();
            canvas.translate(this.getPaddingLeft(), this.getPaddingTop());
            this.mSubtitleWidget.draw(canvas);
            canvas.restoreToCount(n);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return VideoView.class.getName();
    }

    @Override
    public int getAudioSessionId() {
        if (this.mAudioSession == 0) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mAudioSession = mediaPlayer.getAudioSessionId();
            mediaPlayer.release();
        }
        return this.mAudioSession;
    }

    @Override
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (this.isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if (this.isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override
    public Looper getSubtitleLooper() {
        return Looper.getMainLooper();
    }

    @Override
    public boolean isPlaying() {
        boolean bl = this.isInPlaybackState() && this.mMediaPlayer.isPlaying();
        return bl;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SubtitleTrack.RenderingWidget renderingWidget = this.mSubtitleWidget;
        if (renderingWidget != null) {
            renderingWidget.onAttachedToWindow();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SubtitleTrack.RenderingWidget renderingWidget = this.mSubtitleWidget;
        if (renderingWidget != null) {
            renderingWidget.onDetachedFromWindow();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        boolean bl = n != 4 && n != 24 && n != 25 && n != 164 && n != 82 && n != 5 && n != 6;
        if (!this.isInPlaybackState()) return super.onKeyDown(n, keyEvent);
        if (!bl) return super.onKeyDown(n, keyEvent);
        if (this.mMediaController == null) return super.onKeyDown(n, keyEvent);
        if (n != 79 && n != 85) {
            if (n == 126) {
                if (this.mMediaPlayer.isPlaying()) return true;
                this.start();
                this.mMediaController.hide();
                return true;
            }
            if (n != 86 && n != 127) {
                this.toggleMediaControlsVisiblity();
                return super.onKeyDown(n, keyEvent);
            }
            if (!this.mMediaPlayer.isPlaying()) return true;
            this.pause();
            this.mMediaController.show();
            return true;
        }
        if (this.mMediaPlayer.isPlaying()) {
            this.pause();
            this.mMediaController.show();
            return true;
        }
        this.start();
        this.mMediaController.hide();
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mSubtitleWidget != null) {
            this.measureAndLayoutSubtitleWidget();
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = VideoView.getDefaultSize(this.mVideoWidth, n);
        int n4 = VideoView.getDefaultSize(this.mVideoHeight, n2);
        int n5 = n3;
        int n6 = n4;
        if (this.mVideoWidth > 0) {
            n5 = n3;
            n6 = n4;
            if (this.mVideoHeight > 0) {
                int n7 = View.MeasureSpec.getMode(n);
                n = View.MeasureSpec.getSize(n);
                int n8 = View.MeasureSpec.getMode(n2);
                n2 = View.MeasureSpec.getSize(n2);
                if (n7 == 1073741824 && n8 == 1073741824) {
                    n4 = this.mVideoWidth;
                    n3 = this.mVideoHeight;
                    if (n4 * n2 < n * n3) {
                        n5 = n4 * n2 / n3;
                        n6 = n2;
                    } else {
                        n5 = n;
                        n6 = n2;
                        if (n4 * n2 > n * n3) {
                            n6 = n3 * n / n4;
                            n5 = n;
                        }
                    }
                } else if (n7 == 1073741824) {
                    n4 = this.mVideoHeight * n / this.mVideoWidth;
                    n5 = n;
                    n6 = n4;
                    if (n8 == Integer.MIN_VALUE) {
                        n5 = n;
                        n6 = n4;
                        if (n4 > n2) {
                            n5 = n;
                            n6 = n2;
                        }
                    }
                } else if (n8 == 1073741824) {
                    n5 = n4 = this.mVideoWidth * n2 / this.mVideoHeight;
                    n6 = n2;
                    if (n7 == Integer.MIN_VALUE) {
                        n5 = n4;
                        n6 = n2;
                        if (n4 > n) {
                            n5 = n;
                            n6 = n2;
                        }
                    }
                } else {
                    n5 = this.mVideoWidth;
                    n6 = this.mVideoHeight;
                    n3 = n5;
                    n4 = n6;
                    if (n8 == Integer.MIN_VALUE) {
                        n3 = n5;
                        n4 = n6;
                        if (n6 > n2) {
                            n3 = this.mVideoWidth * n2 / this.mVideoHeight;
                            n4 = n2;
                        }
                    }
                    n5 = n3;
                    n6 = n4;
                    if (n7 == Integer.MIN_VALUE) {
                        n5 = n3;
                        n6 = n4;
                        if (n3 > n) {
                            n6 = this.mVideoHeight * n / this.mVideoWidth;
                            n5 = n;
                        }
                    }
                }
            }
        }
        this.setMeasuredDimension(n5, n6);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.isInPlaybackState() && this.mMediaController != null) {
            this.toggleMediaControlsVisiblity();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && this.isInPlaybackState() && this.mMediaController != null) {
            this.toggleMediaControlsVisiblity();
        }
        return super.onTrackballEvent(motionEvent);
    }

    @Override
    public void pause() {
        if (this.isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
        }
        this.mTargetState = 4;
    }

    public int resolveAdjustedSize(int n, int n2) {
        return VideoView.getDefaultSize(n, n2);
    }

    public void resume() {
        this.openVideo();
    }

    @Override
    public void seekTo(int n) {
        if (this.isInPlaybackState()) {
            this.mMediaPlayer.seekTo(n);
            this.mSeekWhenPrepared = 0;
        } else {
            this.mSeekWhenPrepared = n;
        }
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            this.mAudioAttributes = audioAttributes;
            return;
        }
        throw new IllegalArgumentException("Illegal null AudioAttributes");
    }

    public void setAudioFocusRequest(int n) {
        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal audio focus type ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mAudioFocusType = n;
    }

    public void setMediaController(MediaController mediaController) {
        MediaController mediaController2 = this.mMediaController;
        if (mediaController2 != null) {
            mediaController2.hide();
        }
        this.mMediaController = mediaController;
        this.attachMediaController();
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    @Override
    public void setSubtitleWidget(SubtitleTrack.RenderingWidget renderingWidget) {
        if (this.mSubtitleWidget == renderingWidget) {
            return;
        }
        boolean bl = this.isAttachedToWindow();
        SubtitleTrack.RenderingWidget renderingWidget2 = this.mSubtitleWidget;
        if (renderingWidget2 != null) {
            if (bl) {
                renderingWidget2.onDetachedFromWindow();
            }
            this.mSubtitleWidget.setOnChangedListener(null);
        }
        this.mSubtitleWidget = renderingWidget;
        if (renderingWidget != null) {
            if (this.mSubtitlesChangedListener == null) {
                this.mSubtitlesChangedListener = new SubtitleTrack.RenderingWidget.OnChangedListener(){

                    @Override
                    public void onChanged(SubtitleTrack.RenderingWidget renderingWidget) {
                        VideoView.this.invalidate();
                    }
                };
            }
            this.setWillNotDraw(false);
            renderingWidget.setOnChangedListener(this.mSubtitlesChangedListener);
            if (bl) {
                renderingWidget.onAttachedToWindow();
                this.requestLayout();
            }
        } else {
            this.setWillNotDraw(true);
        }
        this.invalidate();
    }

    public void setVideoPath(String string2) {
        this.setVideoURI(Uri.parse(string2));
    }

    public void setVideoURI(Uri uri) {
        this.setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, Map<String, String> map) {
        this.mUri = uri;
        this.mHeaders = map;
        this.mSeekWhenPrepared = 0;
        this.openVideo();
        this.requestLayout();
        this.invalidate();
    }

    @Override
    public void start() {
        if (this.isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void stopPlayback() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            this.mAudioManager.abandonAudioFocus(null);
        }
    }

    public void suspend() {
        this.release(false);
    }

}

