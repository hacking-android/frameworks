/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class RedEyeFilter
extends Filter {
    private static final float DEFAULT_RED_INTENSITY = 1.3f;
    private static final float MIN_RADIUS = 10.0f;
    private static final float RADIUS_RATIO = 0.06f;
    private final Canvas mCanvas = new Canvas();
    @GenerateFieldPort(name="centers")
    private float[] mCenters;
    private int mHeight = 0;
    private final Paint mPaint = new Paint();
    private Program mProgram;
    private float mRadius;
    private Bitmap mRedEyeBitmap;
    private Frame mRedEyeFrame;
    private final String mRedEyeShader;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;

    public RedEyeFilter(String string2) {
        super(string2);
        this.mRedEyeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n";
    }

    private void createRedEyeFrame(FilterContext filterContext) {
        Object object;
        int n = this.mWidth / 2;
        int n2 = this.mHeight / 2;
        Bitmap bitmap = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmap);
        this.mPaint.setColor(-1);
        this.mRadius = Math.max(10.0f, (float)Math.min(n, n2) * 0.06f);
        for (int i = 0; i < ((float[])(object = this.mCenters)).length; i += 2) {
            this.mCanvas.drawCircle(object[i] * (float)n, object[i + 1] * (float)n2, this.mRadius, this.mPaint);
        }
        object = ImageFormat.create(n, n2, 3, 3);
        this.mRedEyeFrame = filterContext.getFrameManager().newFrame((FrameFormat)object);
        this.mRedEyeFrame.setBitmap(bitmap);
        bitmap.recycle();
    }

    private void updateProgramParams() {
        if (this.mCenters.length % 2 != 1) {
            return;
        }
        throw new RuntimeException("The size of center array must be even.");
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateProgramParams();
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float intensity;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  if (mask.a > 0.0) {\n    float green_blue = color.g + color.b;\n    float red_intensity = color.r / green_blue;\n    if (red_intensity > intensity) {\n      color.r = 0.5 * green_blue;\n    }\n  }\n  gl_FragColor = color;\n}\n");
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mProgram = object;
            this.mProgram.setHostValue("intensity", Float.valueOf(1.3f));
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter RedEye does not support frames of target ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        Frame frame2 = ((FilterContext)object).getFrameManager().newFrame(frameFormat);
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram((FilterContext)object, frameFormat.getTarget());
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.mWidth = frameFormat.getWidth();
            this.mHeight = frameFormat.getHeight();
        }
        this.createRedEyeFrame((FilterContext)object);
        object = this.mRedEyeFrame;
        this.mProgram.process(new Frame[]{frame, object}, frame2);
        this.pushOutput("image", frame2);
        frame2.release();
        this.mRedEyeFrame.release();
        this.mRedEyeFrame = null;
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

