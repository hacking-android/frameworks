/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.content.Context;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.ITvInputSession;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.Surface;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;

public class ITvInputSessionWrapper
extends ITvInputSession.Stub
implements HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 9;
    private static final int DO_CREATE_OVERLAY_VIEW = 10;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 4;
    private static final int DO_RELAYOUT_OVERLAY_VIEW = 11;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_OVERLAY_VIEW = 12;
    private static final int DO_SELECT_TRACK = 8;
    private static final int DO_SET_CAPTION_ENABLED = 7;
    private static final int DO_SET_MAIN = 2;
    private static final int DO_SET_STREAM_VOLUME = 5;
    private static final int DO_SET_SURFACE = 3;
    private static final int DO_START_RECORDING = 20;
    private static final int DO_STOP_RECORDING = 21;
    private static final int DO_TIME_SHIFT_ENABLE_POSITION_TRACKING = 19;
    private static final int DO_TIME_SHIFT_PAUSE = 15;
    private static final int DO_TIME_SHIFT_PLAY = 14;
    private static final int DO_TIME_SHIFT_RESUME = 16;
    private static final int DO_TIME_SHIFT_SEEK_TO = 17;
    private static final int DO_TIME_SHIFT_SET_PLAYBACK_PARAMS = 18;
    private static final int DO_TUNE = 6;
    private static final int DO_UNBLOCK_CONTENT = 13;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 50;
    private static final int EXECUTE_MESSAGE_TUNE_TIMEOUT_MILLIS = 2000;
    private static final String TAG = "TvInputSessionWrapper";
    private final HandlerCaller mCaller;
    private InputChannel mChannel;
    private final boolean mIsRecordingSession;
    private TvInputEventReceiver mReceiver;
    private TvInputService.RecordingSession mTvInputRecordingSessionImpl;
    private TvInputService.Session mTvInputSessionImpl;

    public ITvInputSessionWrapper(Context context, TvInputService.RecordingSession recordingSession) {
        this.mIsRecordingSession = true;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mTvInputRecordingSessionImpl = recordingSession;
    }

    public ITvInputSessionWrapper(Context context, TvInputService.Session session, InputChannel inputChannel) {
        this.mIsRecordingSession = false;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mTvInputSessionImpl = session;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new TvInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    @Override
    public void appPrivateCommand(String string2, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(9, string2, bundle));
    }

    @Override
    public void createOverlayView(IBinder iBinder, Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(10, iBinder, rect));
    }

    @Override
    public void dispatchSurfaceChanged(int n, int n2, int n3) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIII(4, n, n2, n3, 0));
    }

    @Override
    public void executeMessage(Message object) {
        Object object2;
        if (this.mIsRecordingSession && this.mTvInputRecordingSessionImpl == null || !this.mIsRecordingSession && this.mTvInputSessionImpl == null) {
            return;
        }
        long l = System.nanoTime();
        switch (((Message)object).what) {
            default: {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unhandled message code: ");
                ((StringBuilder)object2).append(((Message)object).what);
                Log.w(TAG, ((StringBuilder)object2).toString());
                break;
            }
            case 21: {
                this.mTvInputRecordingSessionImpl.stopRecording();
                break;
            }
            case 20: {
                this.mTvInputRecordingSessionImpl.startRecording((Uri)((Message)object).obj);
                break;
            }
            case 19: {
                this.mTvInputSessionImpl.timeShiftEnablePositionTracking((Boolean)((Message)object).obj);
                break;
            }
            case 18: {
                this.mTvInputSessionImpl.timeShiftSetPlaybackParams((PlaybackParams)((Message)object).obj);
                break;
            }
            case 17: {
                this.mTvInputSessionImpl.timeShiftSeekTo((Long)((Message)object).obj);
                break;
            }
            case 16: {
                this.mTvInputSessionImpl.timeShiftResume();
                break;
            }
            case 15: {
                this.mTvInputSessionImpl.timeShiftPause();
                break;
            }
            case 14: {
                this.mTvInputSessionImpl.timeShiftPlay((Uri)((Message)object).obj);
                break;
            }
            case 13: {
                this.mTvInputSessionImpl.unblockContent((String)((Message)object).obj);
                break;
            }
            case 12: {
                this.mTvInputSessionImpl.removeOverlayView(true);
                break;
            }
            case 11: {
                this.mTvInputSessionImpl.relayoutOverlayView((Rect)((Message)object).obj);
                break;
            }
            case 10: {
                object2 = (SomeArgs)((Message)object).obj;
                this.mTvInputSessionImpl.createOverlayView((IBinder)((SomeArgs)object2).arg1, (Rect)((SomeArgs)object2).arg2);
                ((SomeArgs)object2).recycle();
                break;
            }
            case 9: {
                object2 = (SomeArgs)((Message)object).obj;
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.appPrivateCommand((String)((SomeArgs)object2).arg1, (Bundle)((SomeArgs)object2).arg2);
                } else {
                    this.mTvInputSessionImpl.appPrivateCommand((String)((SomeArgs)object2).arg1, (Bundle)((SomeArgs)object2).arg2);
                }
                ((SomeArgs)object2).recycle();
                break;
            }
            case 8: {
                object2 = (SomeArgs)((Message)object).obj;
                this.mTvInputSessionImpl.selectTrack((Integer)((SomeArgs)object2).arg1, (String)((SomeArgs)object2).arg2);
                ((SomeArgs)object2).recycle();
                break;
            }
            case 7: {
                this.mTvInputSessionImpl.setCaptionEnabled((Boolean)((Message)object).obj);
                break;
            }
            case 6: {
                object2 = (SomeArgs)((Message)object).obj;
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.tune((Uri)((SomeArgs)object2).arg1, (Bundle)((SomeArgs)object2).arg2);
                } else {
                    this.mTvInputSessionImpl.tune((Uri)((SomeArgs)object2).arg1, (Bundle)((SomeArgs)object2).arg2);
                }
                ((SomeArgs)object2).recycle();
                break;
            }
            case 5: {
                this.mTvInputSessionImpl.setStreamVolume(((Float)((Message)object).obj).floatValue());
                break;
            }
            case 4: {
                object2 = (SomeArgs)((Message)object).obj;
                this.mTvInputSessionImpl.dispatchSurfaceChanged(((SomeArgs)object2).argi1, ((SomeArgs)object2).argi2, ((SomeArgs)object2).argi3);
                ((SomeArgs)object2).recycle();
                break;
            }
            case 3: {
                this.mTvInputSessionImpl.setSurface((Surface)((Message)object).obj);
                break;
            }
            case 2: {
                this.mTvInputSessionImpl.setMain((Boolean)((Message)object).obj);
                break;
            }
            case 1: {
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.release();
                    this.mTvInputRecordingSessionImpl = null;
                    break;
                }
                this.mTvInputSessionImpl.release();
                this.mTvInputSessionImpl = null;
                object2 = this.mReceiver;
                if (object2 != null) {
                    ((InputEventReceiver)object2).dispose();
                    this.mReceiver = null;
                }
                if ((object2 = this.mChannel) == null) break;
                ((InputChannel)object2).dispose();
                this.mChannel = null;
            }
        }
        l = (System.nanoTime() - l) / 1000000L;
        if (l > 50L) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Handling message (");
            ((StringBuilder)object2).append(((Message)object).what);
            ((StringBuilder)object2).append(") took too long time (duration=");
            ((StringBuilder)object2).append(l);
            ((StringBuilder)object2).append("ms)");
            Log.w(TAG, ((StringBuilder)object2).toString());
            if (((Message)object).what == 6 && l > 2000L) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Too much time to handle tune request. (");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append("ms > ");
                ((StringBuilder)object).append(2000);
                ((StringBuilder)object).append("ms) Consider handling the tune request in a separate thread.");
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            if (l > 5000L) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Too much time to handle a request. (type=");
                ((StringBuilder)object2).append(((Message)object).what);
                ((StringBuilder)object2).append(", ");
                ((StringBuilder)object2).append(l);
                ((StringBuilder)object2).append("ms > ");
                ((StringBuilder)object2).append(5000);
                ((StringBuilder)object2).append("ms).");
                throw new RuntimeException(((StringBuilder)object2).toString());
            }
        }
    }

    @Override
    public void relayoutOverlayView(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(11, rect));
    }

    @Override
    public void release() {
        if (!this.mIsRecordingSession) {
            this.mTvInputSessionImpl.scheduleOverlayViewCleanup();
        }
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(1));
    }

    @Override
    public void removeOverlayView() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(12));
    }

    @Override
    public void selectTrack(int n, String string2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(8, n, string2));
    }

    @Override
    public void setCaptionEnabled(boolean bl) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(7, bl));
    }

    @Override
    public void setMain(boolean bl) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(2, bl));
    }

    @Override
    public void setSurface(Surface surface) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(3, surface));
    }

    @Override
    public final void setVolume(float f) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(5, Float.valueOf(f)));
    }

    @Override
    public void startRecording(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(20, uri));
    }

    @Override
    public void stopRecording() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(21));
    }

    @Override
    public void timeShiftEnablePositionTracking(boolean bl) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(19, bl));
    }

    @Override
    public void timeShiftPause() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(15));
    }

    @Override
    public void timeShiftPlay(Uri uri) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(14, uri));
    }

    @Override
    public void timeShiftResume() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(16));
    }

    @Override
    public void timeShiftSeekTo(long l) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(17, l));
    }

    @Override
    public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(18, playbackParams));
    }

    @Override
    public void tune(Uri uri, Bundle bundle) {
        this.mCaller.removeMessages(6);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(6, uri, bundle));
    }

    @Override
    public void unblockContent(String string2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(13, string2));
    }

    private final class TvInputEventReceiver
    extends InputEventReceiver {
        public TvInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override
        public void onInputEvent(InputEvent inputEvent) {
            TvInputService.Session session = ITvInputSessionWrapper.this.mTvInputSessionImpl;
            boolean bl = false;
            if (session == null) {
                this.finishInputEvent(inputEvent, false);
                return;
            }
            int n = ITvInputSessionWrapper.this.mTvInputSessionImpl.dispatchInputEvent(inputEvent, this);
            if (n != -1) {
                if (n == 1) {
                    bl = true;
                }
                this.finishInputEvent(inputEvent, bl);
            }
        }
    }

}

