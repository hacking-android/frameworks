/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.videosrc;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.Matrix;
import android.util.Log;

public class MediaSource
extends Filter {
    private static final int NEWFRAME_TIMEOUT = 100;
    private static final int NEWFRAME_TIMEOUT_REPEAT = 10;
    private static final int PREP_TIMEOUT = 100;
    private static final int PREP_TIMEOUT_REPEAT = 100;
    private static final String TAG = "MediaSource";
    private static final float[] mSourceCoords_0 = new float[]{1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final float[] mSourceCoords_180;
    private static final float[] mSourceCoords_270;
    private static final float[] mSourceCoords_90;
    private boolean mCompleted;
    @GenerateFieldPort(hasDefault=true, name="context")
    private Context mContext = null;
    private ShaderProgram mFrameExtractor;
    private final String mFrameShader;
    private boolean mGotSize;
    private int mHeight;
    private final boolean mLogVerbose = Log.isLoggable("MediaSource", 2);
    @GenerateFieldPort(hasDefault=true, name="loop")
    private boolean mLooping = true;
    private GLFrame mMediaFrame;
    private MediaPlayer mMediaPlayer;
    private boolean mNewFrameAvailable = false;
    @GenerateFieldPort(hasDefault=true, name="orientation")
    private int mOrientation = 0;
    private boolean mOrientationUpdated;
    private MutableFrameFormat mOutputFormat;
    private boolean mPaused;
    private boolean mPlaying;
    private boolean mPrepared;
    @GenerateFieldPort(hasDefault=true, name="sourceIsUrl")
    private boolean mSelectedIsUrl = false;
    @GenerateFieldPort(hasDefault=true, name="sourceAsset")
    private AssetFileDescriptor mSourceAsset = null;
    @GenerateFieldPort(hasDefault=true, name="sourceUrl")
    private String mSourceUrl = "";
    private SurfaceTexture mSurfaceTexture;
    @GenerateFieldPort(hasDefault=true, name="volume")
    private float mVolume = 0.0f;
    @GenerateFinalPort(hasDefault=true, name="waitForNewFrame")
    private boolean mWaitForNewFrame = true;
    private int mWidth;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (MediaSource.this.mLogVerbose) {
                Log.v(MediaSource.TAG, "MediaPlayer has completed playback");
            }
            MediaSource mediaSource = MediaSource.this;
            synchronized (mediaSource) {
                MediaSource.this.mCompleted = true;
                return;
            }
        }
    };
    private SurfaceTexture.OnFrameAvailableListener onMediaFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (MediaSource.this.mLogVerbose) {
                Log.v(MediaSource.TAG, "New frame from media player");
            }
            MediaSource mediaSource = MediaSource.this;
            synchronized (mediaSource) {
                if (MediaSource.this.mLogVerbose) {
                    Log.v(MediaSource.TAG, "New frame: notify");
                }
                MediaSource.this.mNewFrameAvailable = true;
                MediaSource.this.notify();
                if (MediaSource.this.mLogVerbose) {
                    Log.v(MediaSource.TAG, "New frame: notify done");
                }
                return;
            }
        }
    };
    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            if (MediaSource.this.mLogVerbose) {
                Log.v(MediaSource.TAG, "MediaPlayer is prepared");
            }
            MediaSource mediaSource = MediaSource.this;
            synchronized (mediaSource) {
                MediaSource.this.mPrepared = true;
                MediaSource.this.notify();
                return;
            }
        }
    };
    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onVideoSizeChanged(MediaPlayer object, int n, int n2) {
            if (MediaSource.this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("MediaPlayer sent dimensions: ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" x ");
                ((StringBuilder)object).append(n2);
                Log.v(MediaSource.TAG, ((StringBuilder)object).toString());
            }
            if (!MediaSource.this.mGotSize) {
                if (MediaSource.this.mOrientation != 0 && MediaSource.this.mOrientation != 180) {
                    MediaSource.this.mOutputFormat.setDimensions(n2, n);
                } else {
                    MediaSource.this.mOutputFormat.setDimensions(n, n2);
                }
                MediaSource.this.mWidth = n;
                MediaSource.this.mHeight = n2;
            } else if (MediaSource.this.mOutputFormat.getWidth() != n || MediaSource.this.mOutputFormat.getHeight() != n2) {
                Log.e(MediaSource.TAG, "Multiple video size change events received!");
            }
            MediaSource mediaSource = MediaSource.this;
            synchronized (mediaSource) {
                MediaSource.this.mGotSize = true;
                MediaSource.this.notify();
                return;
            }
        }
    };

    static {
        mSourceCoords_270 = new float[]{0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
        mSourceCoords_180 = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f};
        mSourceCoords_90 = new float[]{1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    public MediaSource(String string2) {
        super(string2);
        this.mFrameShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
    }

    private void createFormats() {
        this.mOutputFormat = ImageFormat.create(3, 3);
    }

    /*
     * Exception decompiling
     */
    private boolean setupMediaPlayer(boolean var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [13[SIMPLE_IF_TAKEN]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    @Override
    public void close(FilterContext filterContext) {
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
        }
        this.mPrepared = false;
        this.mGotSize = false;
        this.mPlaying = false;
        this.mPaused = false;
        this.mCompleted = false;
        this.mNewFrameAvailable = false;
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
        if (this.mLogVerbose) {
            Log.v(TAG, "MediaSource closed");
        }
    }

    @Override
    public void fieldPortValueUpdated(String object, FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Parameter update");
        }
        if (((String)object).equals("sourceUrl")) {
            if (this.isOpen()) {
                boolean bl;
                if (this.mLogVerbose) {
                    Log.v(TAG, "Opening new source URL");
                }
                if (bl = this.mSelectedIsUrl) {
                    this.setupMediaPlayer(bl);
                }
            }
        } else if (((String)object).equals("sourceAsset")) {
            if (this.isOpen()) {
                boolean bl;
                if (this.mLogVerbose) {
                    Log.v(TAG, "Opening new source FD");
                }
                if (!(bl = this.mSelectedIsUrl)) {
                    this.setupMediaPlayer(bl);
                }
            }
        } else if (((String)object).equals("loop")) {
            if (this.isOpen()) {
                this.mMediaPlayer.setLooping(this.mLooping);
            }
        } else if (((String)object).equals("sourceIsUrl")) {
            if (this.isOpen()) {
                if (this.mSelectedIsUrl) {
                    if (this.mLogVerbose) {
                        Log.v(TAG, "Opening new source URL");
                    }
                } else if (this.mLogVerbose) {
                    Log.v(TAG, "Opening new source Asset");
                }
                this.setupMediaPlayer(this.mSelectedIsUrl);
            }
        } else if (((String)object).equals("volume")) {
            if (this.isOpen()) {
                object = this.mMediaPlayer;
                float f = this.mVolume;
                ((MediaPlayer)object).setVolume(f, f);
            }
        } else if (((String)object).equals("orientation") && this.mGotSize) {
            int n = this.mOrientation;
            if (n != 0 && n != 180) {
                this.mOutputFormat.setDimensions(this.mHeight, this.mWidth);
            } else {
                this.mOutputFormat.setDimensions(this.mWidth, this.mHeight);
            }
            this.mOrientationUpdated = true;
        }
    }

    @Override
    public void open(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Opening MediaSource");
            if (this.mSelectedIsUrl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Current URL is ");
                stringBuilder.append(this.mSourceUrl);
                Log.v(TAG, stringBuilder.toString());
            } else {
                Log.v(TAG, "Current source is Asset!");
            }
        }
        this.mMediaFrame = (GLFrame)filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mSurfaceTexture = new SurfaceTexture(this.mMediaFrame.getTextureId());
        if (this.setupMediaPlayer(this.mSelectedIsUrl)) {
            return;
        }
        throw new RuntimeException("Error setting up MediaPlayer!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void pauseVideo(boolean bl) {
        synchronized (this) {
            if (this.isOpen()) {
                if (bl && !this.mPaused) {
                    this.mMediaPlayer.pause();
                } else if (!bl && this.mPaused) {
                    this.mMediaPlayer.start();
                }
            }
            this.mPaused = bl;
            return;
        }
    }

    @Override
    protected void prepare(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Preparing MediaSource");
        }
        this.mFrameExtractor = new ShaderProgram(filterContext, "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n");
        this.mFrameExtractor.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.createFormats();
    }

    @Override
    public void process(FilterContext object) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Processing new frame");
        }
        if (this.mMediaPlayer != null) {
            int n;
            Object object2;
            block28 : {
                if (this.mCompleted) {
                    this.closeOutputPort("video");
                    return;
                }
                if (!this.mPlaying) {
                    if (this.mLogVerbose) {
                        Log.v(TAG, "Waiting for preparation to complete");
                    }
                    n = 0;
                    do {
                        if (this.mGotSize && this.mPrepared) {
                            if (this.mLogVerbose) {
                                Log.v(TAG, "Starting playback");
                            }
                            this.mMediaPlayer.start();
                            break block28;
                        }
                        try {
                            this.wait(100L);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                        if (!this.mCompleted) continue;
                        this.closeOutputPort("video");
                        return;
                    } while (++n != 100);
                    this.mMediaPlayer.release();
                    throw new RuntimeException("MediaPlayer timed out while preparing!");
                }
            }
            if (!this.mPaused || !this.mPlaying) {
                if (this.mWaitForNewFrame) {
                    if (this.mLogVerbose) {
                        Log.v(TAG, "Waiting for new frame");
                    }
                    n = 0;
                    while (!this.mNewFrameAvailable) {
                        block29 : {
                            if (n == 10) {
                                if (this.mCompleted) {
                                    this.closeOutputPort("video");
                                    return;
                                }
                                throw new RuntimeException("Timeout waiting for new frame!");
                            }
                            try {
                                this.wait(100L);
                            }
                            catch (InterruptedException interruptedException) {
                                if (!this.mLogVerbose) break block29;
                                Log.v(TAG, "interrupted");
                            }
                        }
                        ++n;
                    }
                    this.mNewFrameAvailable = false;
                    if (this.mLogVerbose) {
                        Log.v(TAG, "Got new frame");
                    }
                }
                this.mSurfaceTexture.updateTexImage();
                this.mOrientationUpdated = true;
            }
            if (this.mOrientationUpdated) {
                Object object3 = new float[16];
                this.mSurfaceTexture.getTransformMatrix((float[])object3);
                object2 = new float[16];
                n = this.mOrientation;
                if (n != 90) {
                    if (n != 180) {
                        if (n != 270) {
                            Matrix.multiplyMM((float[])object2, 0, (float[])object3, 0, mSourceCoords_0, 0);
                        } else {
                            Matrix.multiplyMM((float[])object2, 0, (float[])object3, 0, mSourceCoords_270, 0);
                        }
                    } else {
                        Matrix.multiplyMM((float[])object2, 0, (float[])object3, 0, mSourceCoords_180, 0);
                    }
                } else {
                    Matrix.multiplyMM((float[])object2, 0, (float[])object3, 0, mSourceCoords_90, 0);
                }
                if (this.mLogVerbose) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("OrientationHint = ");
                    ((StringBuilder)object3).append(this.mOrientation);
                    Log.v(TAG, ((StringBuilder)object3).toString());
                    Log.v(TAG, String.format("SetSourceRegion: %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f", Float.valueOf((float)object2[4]), Float.valueOf((float)object2[5]), Float.valueOf((float)object2[0]), Float.valueOf((float)object2[1]), Float.valueOf((float)object2[12]), Float.valueOf((float)object2[13]), Float.valueOf((float)object2[8]), Float.valueOf((float)object2[9])));
                }
                this.mFrameExtractor.setSourceRegion((float)object2[4], (float)object2[5], (float)object2[0], (float)object2[1], (float)object2[12], (float)object2[13], (float)object2[8], (float)object2[9]);
                this.mOrientationUpdated = false;
            }
            object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
            this.mFrameExtractor.process(this.mMediaFrame, (Frame)object);
            long l = this.mSurfaceTexture.getTimestamp();
            if (this.mLogVerbose) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Timestamp: ");
                ((StringBuilder)object2).append((double)l / 1.0E9);
                ((StringBuilder)object2).append(" s");
                Log.v(TAG, ((StringBuilder)object2).toString());
            }
            ((Frame)object).setTimestamp(l);
            this.pushOutput("video", (Frame)object);
            ((Frame)object).release();
            this.mPlaying = true;
            return;
        }
        throw new NullPointerException("Unexpected null media player!");
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("video", ImageFormat.create(3, 3));
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mMediaFrame;
        if (object != null) {
            ((Frame)object).release();
        }
    }

}

