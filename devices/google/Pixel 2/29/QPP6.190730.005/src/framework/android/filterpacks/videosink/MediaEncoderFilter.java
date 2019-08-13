/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.videosink;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.filterpacks.videosink.MediaRecorderStopException;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;

public class MediaEncoderFilter
extends Filter {
    private static final int NO_AUDIO_SOURCE = -1;
    private static final String TAG = "MediaEncoderFilter";
    @GenerateFieldPort(hasDefault=true, name="audioSource")
    private int mAudioSource = -1;
    private boolean mCaptureTimeLapse = false;
    @GenerateFieldPort(hasDefault=true, name="errorListener")
    private MediaRecorder.OnErrorListener mErrorListener = null;
    @GenerateFieldPort(hasDefault=true, name="outputFileDescriptor")
    private FileDescriptor mFd = null;
    @GenerateFieldPort(hasDefault=true, name="framerate")
    private int mFps = 30;
    @GenerateFieldPort(hasDefault=true, name="height")
    private int mHeight = 0;
    @GenerateFieldPort(hasDefault=true, name="infoListener")
    private MediaRecorder.OnInfoListener mInfoListener = null;
    private long mLastTimeLapseFrameRealTimestampNs = 0L;
    private boolean mLogVerbose = Log.isLoggable("MediaEncoderFilter", 2);
    @GenerateFieldPort(hasDefault=true, name="maxDurationMs")
    private int mMaxDurationMs = 0;
    @GenerateFieldPort(hasDefault=true, name="maxFileSize")
    private long mMaxFileSize = 0L;
    private MediaRecorder mMediaRecorder;
    private int mNumFramesEncoded = 0;
    @GenerateFieldPort(hasDefault=true, name="orientationHint")
    private int mOrientationHint = 0;
    @GenerateFieldPort(hasDefault=true, name="outputFile")
    private String mOutputFile = new String("/sdcard/MediaEncoderOut.mp4");
    @GenerateFieldPort(hasDefault=true, name="outputFormat")
    private int mOutputFormat = 2;
    @GenerateFieldPort(hasDefault=true, name="recordingProfile")
    private CamcorderProfile mProfile = null;
    private ShaderProgram mProgram;
    @GenerateFieldPort(hasDefault=true, name="recording")
    private boolean mRecording = true;
    private boolean mRecordingActive = false;
    @GenerateFieldPort(hasDefault=true, name="recordingDoneListener")
    private OnRecordingDoneListener mRecordingDoneListener = null;
    private GLFrame mScreen;
    @GenerateFieldPort(hasDefault=true, name="inputRegion")
    private Quad mSourceRegion = new Quad(new Point(0.0f, 0.0f), new Point(1.0f, 0.0f), new Point(0.0f, 1.0f), new Point(1.0f, 1.0f));
    private int mSurfaceId;
    @GenerateFieldPort(hasDefault=true, name="timelapseRecordingIntervalUs")
    private long mTimeBetweenTimeLapseFrameCaptureUs = 0L;
    private long mTimestampNs = 0L;
    @GenerateFieldPort(hasDefault=true, name="videoEncoder")
    private int mVideoEncoder = 2;
    @GenerateFieldPort(hasDefault=true, name="width")
    private int mWidth = 0;

    public MediaEncoderFilter(String string2) {
        super(string2);
    }

    private void startRecording(FilterContext filterContext) {
        int n;
        if (this.mLogVerbose) {
            Log.v(TAG, "Starting recording");
        }
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(2, 3);
        mutableFrameFormat.setBytesPerSample(4);
        int n2 = this.mWidth > 0 && this.mHeight > 0 ? 1 : 0;
        CamcorderProfile camcorderProfile = this.mProfile;
        if (camcorderProfile != null && n2 == 0) {
            n2 = camcorderProfile.videoFrameWidth;
            n = this.mProfile.videoFrameHeight;
        } else {
            n2 = this.mWidth;
            n = this.mHeight;
        }
        mutableFrameFormat.setDimensions(n2, n);
        this.mScreen = (GLFrame)filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
        this.mMediaRecorder = new MediaRecorder();
        this.updateMediaRecorderParams();
        try {
            this.mMediaRecorder.prepare();
        }
        catch (Exception exception) {
            throw new RuntimeException("Unknown Exception inMediaRecorder.prepare()!", exception);
        }
        catch (IOException iOException) {
            throw new RuntimeException("IOException inMediaRecorder.prepare()!", iOException);
        }
        catch (IllegalStateException illegalStateException) {
            throw illegalStateException;
        }
        this.mMediaRecorder.start();
        if (this.mLogVerbose) {
            Log.v(TAG, "Open: registering surface from Mediarecorder");
        }
        this.mSurfaceId = filterContext.getGLEnvironment().registerSurfaceFromMediaRecorder(this.mMediaRecorder);
        this.mNumFramesEncoded = 0;
        this.mRecordingActive = true;
        return;
    }

    private void stopRecording(FilterContext object) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Stopping recording");
        }
        this.mRecordingActive = false;
        this.mNumFramesEncoded = 0;
        object = ((FilterContext)object).getGLEnvironment();
        if (this.mLogVerbose) {
            Log.v(TAG, String.format("Unregistering surface %d", this.mSurfaceId));
        }
        ((GLEnvironment)object).unregisterSurfaceId(this.mSurfaceId);
        try {
            this.mMediaRecorder.stop();
        }
        catch (RuntimeException runtimeException) {
            throw new MediaRecorderStopException("MediaRecorder.stop() failed!", runtimeException);
        }
        this.mMediaRecorder.release();
        this.mMediaRecorder = null;
        this.mScreen.release();
        this.mScreen = null;
        object = this.mRecordingDoneListener;
        if (object != null) {
            object.onRecordingDone();
        }
    }

    private void updateMediaRecorderParams() {
        int n;
        Object object;
        boolean bl = this.mTimeBetweenTimeLapseFrameCaptureUs > 0L;
        this.mCaptureTimeLapse = bl;
        this.mMediaRecorder.setVideoSource(2);
        if (!this.mCaptureTimeLapse && (n = this.mAudioSource) != -1) {
            this.mMediaRecorder.setAudioSource(n);
        }
        if ((object = this.mProfile) != null) {
            this.mMediaRecorder.setProfile((CamcorderProfile)object);
            this.mFps = this.mProfile.videoFrameRate;
            int n2 = this.mWidth;
            if (n2 > 0 && (n = this.mHeight) > 0) {
                this.mMediaRecorder.setVideoSize(n2, n);
            }
        } else {
            this.mMediaRecorder.setOutputFormat(this.mOutputFormat);
            this.mMediaRecorder.setVideoEncoder(this.mVideoEncoder);
            this.mMediaRecorder.setVideoSize(this.mWidth, this.mHeight);
            this.mMediaRecorder.setVideoFrameRate(this.mFps);
        }
        this.mMediaRecorder.setOrientationHint(this.mOrientationHint);
        this.mMediaRecorder.setOnInfoListener(this.mInfoListener);
        this.mMediaRecorder.setOnErrorListener(this.mErrorListener);
        object = this.mFd;
        if (object != null) {
            this.mMediaRecorder.setOutputFile((FileDescriptor)object);
        } else {
            this.mMediaRecorder.setOutputFile(this.mOutputFile);
        }
        try {
            this.mMediaRecorder.setMaxFileSize(this.mMaxFileSize);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Setting maxFileSize on MediaRecorder unsuccessful! ");
            ((StringBuilder)object).append(exception.getMessage());
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        this.mMediaRecorder.setMaxDuration(this.mMaxDurationMs);
    }

    private void updateSourceRegion() {
        Quad quad = new Quad();
        quad.p0 = this.mSourceRegion.p2;
        quad.p1 = this.mSourceRegion.p3;
        quad.p2 = this.mSourceRegion.p0;
        quad.p3 = this.mSourceRegion.p1;
        this.mProgram.setSourceRegion(quad);
    }

    @Override
    public void close(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing");
        }
        if (this.mRecordingActive) {
            this.stopRecording(filterContext);
        }
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext object) {
        if (this.mLogVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Port ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" has been updated");
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        if (string2.equals("recording")) {
            return;
        }
        if (string2.equals("inputRegion")) {
            if (this.isOpen()) {
                this.updateSourceRegion();
            }
            return;
        }
        if (this.isOpen() && this.mRecordingActive) {
            throw new RuntimeException("Cannot change recording parameters when the filter is recording!");
        }
    }

    @Override
    public void open(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Opening");
        }
        this.updateSourceRegion();
        if (this.mRecording) {
            this.startRecording(filterContext);
        }
    }

    @Override
    public void prepare(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Preparing");
        }
        this.mProgram = ShaderProgram.createIdentity(filterContext);
        this.mRecordingActive = false;
    }

    @Override
    public void process(FilterContext filterContext) {
        GLEnvironment gLEnvironment = filterContext.getGLEnvironment();
        Frame frame = this.pullInput("videoframe");
        if (!this.mRecordingActive && this.mRecording) {
            this.startRecording(filterContext);
        }
        if (this.mRecordingActive && !this.mRecording) {
            this.stopRecording(filterContext);
        }
        if (!this.mRecordingActive) {
            return;
        }
        if (this.mCaptureTimeLapse) {
            if (this.skipFrameAndModifyTimestamp(frame.getTimestamp())) {
                return;
            }
        } else {
            this.mTimestampNs = frame.getTimestamp();
        }
        gLEnvironment.activateSurfaceWithId(this.mSurfaceId);
        this.mProgram.process(frame, (Frame)this.mScreen);
        gLEnvironment.setSurfaceTimestamp(this.mTimestampNs);
        gLEnvironment.swapBuffers();
        ++this.mNumFramesEncoded;
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("videoframe", ImageFormat.create(3, 3));
    }

    public boolean skipFrameAndModifyTimestamp(long l) {
        StringBuilder stringBuilder;
        int n = this.mNumFramesEncoded;
        if (n == 0) {
            this.mLastTimeLapseFrameRealTimestampNs = l;
            this.mTimestampNs = l;
            if (this.mLogVerbose) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("timelapse: FIRST frame, last real t= ");
                stringBuilder2.append(this.mLastTimeLapseFrameRealTimestampNs);
                stringBuilder2.append(", setting t = ");
                stringBuilder2.append(this.mTimestampNs);
                Log.v(TAG, stringBuilder2.toString());
            }
            return false;
        }
        if (n >= 2 && l < this.mLastTimeLapseFrameRealTimestampNs + this.mTimeBetweenTimeLapseFrameCaptureUs * 1000L) {
            if (this.mLogVerbose) {
                Log.v(TAG, "timelapse: skipping intermediate frame");
            }
            return true;
        }
        if (this.mLogVerbose) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("timelapse: encoding frame, Timestamp t = ");
            stringBuilder.append(l);
            stringBuilder.append(", last real t= ");
            stringBuilder.append(this.mLastTimeLapseFrameRealTimestampNs);
            stringBuilder.append(", interval = ");
            stringBuilder.append(this.mTimeBetweenTimeLapseFrameCaptureUs);
            Log.v(TAG, stringBuilder.toString());
        }
        this.mLastTimeLapseFrameRealTimestampNs = l;
        this.mTimestampNs += 1000000000L / (long)this.mFps;
        if (this.mLogVerbose) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("timelapse: encoding frame, setting t = ");
            stringBuilder.append(this.mTimestampNs);
            stringBuilder.append(", delta t = ");
            stringBuilder.append(1000000000L / (long)this.mFps);
            stringBuilder.append(", fps = ");
            stringBuilder.append(this.mFps);
            Log.v(TAG, stringBuilder.toString());
        }
        return false;
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mMediaRecorder;
        if (object != null) {
            ((MediaRecorder)object).release();
        }
        if ((object = this.mScreen) != null) {
            ((Frame)object).release();
        }
    }

    public static interface OnRecordingDoneListener {
        public void onRecordingDone();
    }

}

