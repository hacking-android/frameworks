/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.videoproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.opengl.GLES20;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BackDropperFilter
extends Filter {
    private static final float DEFAULT_ACCEPT_STDDEV = 0.85f;
    private static final float DEFAULT_ADAPT_RATE_BG = 0.0f;
    private static final float DEFAULT_ADAPT_RATE_FG = 0.0f;
    private static final String DEFAULT_AUTO_WB_SCALE = "0.25";
    private static final float[] DEFAULT_BG_FIT_TRANSFORM = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final float DEFAULT_EXPOSURE_CHANGE = 1.0f;
    private static final int DEFAULT_HIER_LRG_EXPONENT = 3;
    private static final float DEFAULT_HIER_LRG_SCALE = 0.7f;
    private static final int DEFAULT_HIER_MID_EXPONENT = 2;
    private static final float DEFAULT_HIER_MID_SCALE = 0.6f;
    private static final int DEFAULT_HIER_SML_EXPONENT = 0;
    private static final float DEFAULT_HIER_SML_SCALE = 0.5f;
    private static final float DEFAULT_LEARNING_ADAPT_RATE = 0.2f;
    private static final int DEFAULT_LEARNING_DONE_THRESHOLD = 20;
    private static final int DEFAULT_LEARNING_DURATION = 40;
    private static final int DEFAULT_LEARNING_VERIFY_DURATION = 10;
    private static final float DEFAULT_MASK_BLEND_BG = 0.65f;
    private static final float DEFAULT_MASK_BLEND_FG = 0.95f;
    private static final int DEFAULT_MASK_HEIGHT_EXPONENT = 8;
    private static final float DEFAULT_MASK_VERIFY_RATE = 0.25f;
    private static final int DEFAULT_MASK_WIDTH_EXPONENT = 8;
    private static final float DEFAULT_UV_SCALE_FACTOR = 1.35f;
    private static final float DEFAULT_WHITE_BALANCE_BLUE_CHANGE = 0.0f;
    private static final float DEFAULT_WHITE_BALANCE_RED_CHANGE = 0.0f;
    private static final int DEFAULT_WHITE_BALANCE_TOGGLE = 0;
    private static final float DEFAULT_Y_SCALE_FACTOR = 0.4f;
    private static final String DISTANCE_STORAGE_SCALE = "0.6";
    private static final String MASK_SMOOTH_EXPONENT = "2.0";
    private static final String MIN_VARIANCE = "3.0";
    private static final String RGB_TO_YUV_MATRIX = "0.299, -0.168736,  0.5,      0.000, 0.587, -0.331264, -0.418688, 0.000, 0.114,  0.5,      -0.081312, 0.000, 0.000,  0.5,       0.5,      1.000 ";
    private static final String TAG = "BackDropperFilter";
    private static final String VARIANCE_STORAGE_SCALE = "5.0";
    private static final String mAutomaticWhiteBalance = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float pyramid_depth;\nuniform bool autowb_toggle;\nvarying vec2 v_texcoord;\nvoid main() {\n   vec4 mean_video = texture2D(tex_sampler_0, v_texcoord, pyramid_depth);\n   vec4 mean_bg = texture2D(tex_sampler_1, v_texcoord, pyramid_depth);\n   float green_normalizer = mean_video.g / mean_bg.g;\n   vec4 adjusted_value = vec4(mean_bg.r / mean_video.r * green_normalizer, 1., \n                         mean_bg.b / mean_video.b * green_normalizer, 1.) * auto_wb_scale; \n   gl_FragColor = autowb_toggle ? adjusted_value : vec4(auto_wb_scale);\n}\n";
    private static final String mBgDistanceShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 variance = inv_var_scale * texture2D(tex_sampler_2, v_texcoord);\n\n  float dist_y = gauss_dist_y(fg.r, mean.r, variance.r);\n  float dist_uv = gauss_dist_uv(fg.gb, mean.gb, variance.gb);\n  gl_FragColor = vec4(0.5*fg.rg, dist_scale*dist_y, dist_scale*dist_uv);\n}\n";
    private static final String mBgMaskShader = "uniform sampler2D tex_sampler_0;\nuniform float accept_variance;\nuniform vec2 yuv_weights;\nuniform float scale_lrg;\nuniform float scale_mid;\nuniform float scale_sml;\nuniform float exp_lrg;\nuniform float exp_mid;\nuniform float exp_sml;\nvarying vec2 v_texcoord;\nbool is_fg(vec2 dist_yc, float accept_variance) {\n  return ( dot(yuv_weights, dist_yc) >= accept_variance );\n}\nvoid main() {\n  vec4 dist_lrg_sc = texture2D(tex_sampler_0, v_texcoord, exp_lrg);\n  vec4 dist_mid_sc = texture2D(tex_sampler_0, v_texcoord, exp_mid);\n  vec4 dist_sml_sc = texture2D(tex_sampler_0, v_texcoord, exp_sml);\n  vec2 dist_lrg = inv_dist_scale * dist_lrg_sc.ba;\n  vec2 dist_mid = inv_dist_scale * dist_mid_sc.ba;\n  vec2 dist_sml = inv_dist_scale * dist_sml_sc.ba;\n  vec2 norm_dist = 0.75 * dist_sml / accept_variance;\n  bool is_fg_lrg = is_fg(dist_lrg, accept_variance * scale_lrg);\n  bool is_fg_mid = is_fg_lrg || is_fg(dist_mid, accept_variance * scale_mid);\n  float is_fg_sml =\n      float(is_fg_mid || is_fg(dist_sml, accept_variance * scale_sml));\n  float alpha = 0.5 * is_fg_sml + 0.3 * float(is_fg_mid) + 0.2 * float(is_fg_lrg);\n  gl_FragColor = vec4(alpha, norm_dist, is_fg_sml);\n}\n";
    private static final String mBgSubtractForceShader = "  vec4 ghost_rgb = (fg_adjusted * 0.7 + vec4(0.3,0.3,0.4,0.))*0.65 + \n                   0.35*bg_rgb;\n  float glow_start = 0.75 * mask_blend_bg; \n  float glow_max   = mask_blend_bg; \n  gl_FragColor = mask.a < glow_start ? bg_rgb : \n                 mask.a < glow_max ? mix(bg_rgb, vec4(0.9,0.9,1.0,1.0), \n                                     (mask.a - glow_start) / (glow_max - glow_start) ) : \n                 mask.a < mask_blend_fg ? mix(vec4(0.9,0.9,1.0,1.0), ghost_rgb, \n                                    (mask.a - glow_max) / (mask_blend_fg - glow_max) ) : \n                 ghost_rgb;\n}\n";
    private static final String mBgSubtractShader = "uniform mat3 bg_fit_transform;\nuniform float mask_blend_bg;\nuniform float mask_blend_fg;\nuniform float exposure_change;\nuniform float whitebalancered_change;\nuniform float whitebalanceblue_change;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform sampler2D tex_sampler_3;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec2 bg_texcoord = (bg_fit_transform * vec3(v_texcoord, 1.)).xy;\n  vec4 bg_rgb = texture2D(tex_sampler_1, bg_texcoord);\n  vec4 wb_auto_scale = texture2D(tex_sampler_3, v_texcoord) * exposure_change / auto_wb_scale;\n  vec4 wb_manual_scale = vec4(1. + whitebalancered_change, 1., 1. + whitebalanceblue_change, 1.);\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord);\n  vec4 fg_adjusted = fg_rgb * wb_manual_scale * wb_auto_scale;\n  vec4 mask = texture2D(tex_sampler_2, v_texcoord, \n                      2.0);\n  float alpha = smoothstep(mask_blend_bg, mask_blend_fg, mask.a);\n  gl_FragColor = mix(bg_rgb, fg_adjusted, alpha);\n";
    private static final String[] mDebugOutputNames;
    private static final String[] mInputNames;
    private static final String mMaskVerifyShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float verify_rate;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 lastmask = texture2D(tex_sampler_0, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_1, v_texcoord);\n  float newmask = mix(lastmask.a, mask.a, verify_rate);\n  gl_FragColor = vec4(0., 0., 0., newmask);\n}\n";
    private static final String[] mOutputNames;
    private static String mSharedUtilShader;
    private static final String mUpdateBgModelMeanShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_2, v_texcoord, \n                      2.0);\n\n  float alpha = local_adapt_rate(mask.a);\n  vec4 new_mean = mix(mean, fg, alpha);\n  gl_FragColor = new_mean;\n}\n";
    private static final String mUpdateBgModelVarianceShader = "uniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform sampler2D tex_sampler_2;\nuniform sampler2D tex_sampler_3;\nuniform float subsample_level;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 fg_rgb = texture2D(tex_sampler_0, v_texcoord, subsample_level);\n  vec4 fg = coeff_yuv * vec4(fg_rgb.rgb, 1.);\n  vec4 mean = texture2D(tex_sampler_1, v_texcoord);\n  vec4 variance = inv_var_scale * texture2D(tex_sampler_2, v_texcoord);\n  vec4 mask = texture2D(tex_sampler_3, v_texcoord, \n                      2.0);\n\n  float alpha = local_adapt_rate(mask.a);\n  vec4 cur_variance = (fg-mean)*(fg-mean);\n  vec4 new_variance = mix(variance, cur_variance, alpha);\n  new_variance = max(new_variance, vec4(min_variance));\n  gl_FragColor = var_scale * new_variance;\n}\n";
    private final int BACKGROUND_FILL_CROP;
    private final int BACKGROUND_FIT;
    private final int BACKGROUND_STRETCH;
    private ShaderProgram copyShaderProgram;
    private boolean isOpen;
    @GenerateFieldPort(hasDefault=true, name="acceptStddev")
    private float mAcceptStddev = 0.85f;
    @GenerateFieldPort(hasDefault=true, name="adaptRateBg")
    private float mAdaptRateBg = 0.0f;
    @GenerateFieldPort(hasDefault=true, name="adaptRateFg")
    private float mAdaptRateFg = 0.0f;
    @GenerateFieldPort(hasDefault=true, name="learningAdaptRate")
    private float mAdaptRateLearning = 0.2f;
    private GLFrame mAutoWB;
    @GenerateFieldPort(hasDefault=true, name="autowbToggle")
    private int mAutoWBToggle = 0;
    private ShaderProgram mAutomaticWhiteBalanceProgram;
    private MutableFrameFormat mAverageFormat;
    @GenerateFieldPort(hasDefault=true, name="backgroundFitMode")
    private int mBackgroundFitMode = 2;
    private boolean mBackgroundFitModeChanged;
    private ShaderProgram mBgDistProgram;
    private GLFrame mBgInput;
    private ShaderProgram mBgMaskProgram;
    private GLFrame[] mBgMean;
    private ShaderProgram mBgSubtractProgram;
    private ShaderProgram mBgUpdateMeanProgram;
    private ShaderProgram mBgUpdateVarianceProgram;
    private GLFrame[] mBgVariance;
    @GenerateFieldPort(hasDefault=true, name="chromaScale")
    private float mChromaScale = 1.35f;
    private ShaderProgram mCopyOutProgram;
    private GLFrame mDistance;
    @GenerateFieldPort(hasDefault=true, name="exposureChange")
    private float mExposureChange = 1.0f;
    private int mFrameCount;
    @GenerateFieldPort(hasDefault=true, name="hierLrgExp")
    private int mHierarchyLrgExp = 3;
    @GenerateFieldPort(hasDefault=true, name="hierLrgScale")
    private float mHierarchyLrgScale = 0.7f;
    @GenerateFieldPort(hasDefault=true, name="hierMidExp")
    private int mHierarchyMidExp = 2;
    @GenerateFieldPort(hasDefault=true, name="hierMidScale")
    private float mHierarchyMidScale = 0.6f;
    @GenerateFieldPort(hasDefault=true, name="hierSmlExp")
    private int mHierarchySmlExp = 0;
    @GenerateFieldPort(hasDefault=true, name="hierSmlScale")
    private float mHierarchySmlScale = 0.5f;
    @GenerateFieldPort(hasDefault=true, name="learningDoneListener")
    private LearningDoneListener mLearningDoneListener = null;
    @GenerateFieldPort(hasDefault=true, name="learningDuration")
    private int mLearningDuration = 40;
    @GenerateFieldPort(hasDefault=true, name="learningVerifyDuration")
    private int mLearningVerifyDuration = 10;
    private final boolean mLogVerbose = Log.isLoggable("BackDropperFilter", 2);
    @GenerateFieldPort(hasDefault=true, name="lumScale")
    private float mLumScale = 0.4f;
    private GLFrame mMask;
    private GLFrame mMaskAverage;
    @GenerateFieldPort(hasDefault=true, name="maskBg")
    private float mMaskBg = 0.65f;
    @GenerateFieldPort(hasDefault=true, name="maskFg")
    private float mMaskFg = 0.95f;
    private MutableFrameFormat mMaskFormat;
    @GenerateFieldPort(hasDefault=true, name="maskHeightExp")
    private int mMaskHeightExp = 8;
    private GLFrame[] mMaskVerify;
    private ShaderProgram mMaskVerifyProgram;
    @GenerateFieldPort(hasDefault=true, name="maskWidthExp")
    private int mMaskWidthExp = 8;
    private MutableFrameFormat mMemoryFormat;
    @GenerateFieldPort(hasDefault=true, name="mirrorBg")
    private boolean mMirrorBg = false;
    @GenerateFieldPort(hasDefault=true, name="orientation")
    private int mOrientation = 0;
    private FrameFormat mOutputFormat;
    private boolean mPingPong;
    @GenerateFinalPort(hasDefault=true, name="provideDebugOutputs")
    private boolean mProvideDebugOutputs = false;
    private int mPyramidDepth;
    private float mRelativeAspect;
    private boolean mStartLearning;
    private int mSubsampleLevel;
    @GenerateFieldPort(hasDefault=true, name="useTheForce")
    private boolean mUseTheForce = false;
    @GenerateFieldPort(hasDefault=true, name="maskVerifyRate")
    private float mVerifyRate = 0.25f;
    private GLFrame mVideoInput;
    @GenerateFieldPort(hasDefault=true, name="whitebalanceblueChange")
    private float mWhiteBalanceBlueChange = 0.0f;
    @GenerateFieldPort(hasDefault=true, name="whitebalanceredChange")
    private float mWhiteBalanceRedChange = 0.0f;
    private long startTime = -1L;

    static {
        mInputNames = new String[]{"video", "background"};
        mOutputNames = new String[]{"video"};
        mDebugOutputNames = new String[]{"debug1", "debug2"};
        mSharedUtilShader = "precision mediump float;\nuniform float fg_adapt_rate;\nuniform float bg_adapt_rate;\nconst mat4 coeff_yuv = mat4(0.299, -0.168736,  0.5,      0.000, 0.587, -0.331264, -0.418688, 0.000, 0.114,  0.5,      -0.081312, 0.000, 0.000,  0.5,       0.5,      1.000 );\nconst float dist_scale = 0.6;\nconst float inv_dist_scale = 1. / dist_scale;\nconst float var_scale=5.0;\nconst float inv_var_scale = 1. / var_scale;\nconst float min_variance = inv_var_scale *3.0/ 256.;\nconst float auto_wb_scale = 0.25;\n\nfloat gauss_dist_y(float y, float mean, float variance) {\n  float dist = (y - mean) * (y - mean) / variance;\n  return dist;\n}\nfloat gauss_dist_uv(vec2 uv, vec2 mean, vec2 variance) {\n  vec2 dist = (uv - mean) * (uv - mean) / variance;\n  return dist.r + dist.g;\n}\nfloat local_adapt_rate(float alpha) {\n  return mix(bg_adapt_rate, fg_adapt_rate, alpha);\n}\n\n";
    }

    public BackDropperFilter(String string2) {
        super(string2);
        this.BACKGROUND_STRETCH = 0;
        this.BACKGROUND_FIT = 1;
        this.BACKGROUND_FILL_CROP = 2;
        string2 = SystemProperties.get("ro.media.effect.bgdropper.adj");
        if (string2.length() > 0) {
            try {
                this.mAcceptStddev += Float.parseFloat(string2);
                if (this.mLogVerbose) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Adjusting accept threshold by ");
                    stringBuilder.append(string2);
                    stringBuilder.append(", now ");
                    stringBuilder.append(this.mAcceptStddev);
                    Log.v(TAG, stringBuilder.toString());
                }
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Badly formatted property ro.media.effect.bgdropper.adj: ");
                stringBuilder.append(string2);
                Log.e(TAG, stringBuilder.toString());
            }
        }
    }

    private void allocateFrames(FrameFormat object, FilterContext filterContext) {
        int n;
        if (!this.createMemoryFormat((FrameFormat)object)) {
            return;
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Allocating BackDropperFilter frames");
        }
        int n2 = this.mMaskFormat.getSize();
        byte[] arrby = new byte[n2];
        object = new byte[n2];
        byte[] arrby2 = new byte[n2];
        for (n = 0; n < n2; ++n) {
            arrby[n] = (byte)-128;
            object[n] = (byte)10;
            arrby2[n] = (byte)(false ? 1 : 0);
        }
        for (n = 0; n < 2; ++n) {
            this.mBgMean[n] = (GLFrame)filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mBgMean[n].setData(arrby, 0, n2);
            this.mBgVariance[n] = (GLFrame)filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mBgVariance[n].setData((byte[])object, 0, n2);
            this.mMaskVerify[n] = (GLFrame)filterContext.getFrameManager().newFrame(this.mMaskFormat);
            this.mMaskVerify[n].setData(arrby2, 0, n2);
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Done allocating texture for Mean and Variance objects!");
        }
        this.mDistance = (GLFrame)filterContext.getFrameManager().newFrame(this.mMaskFormat);
        this.mMask = (GLFrame)filterContext.getFrameManager().newFrame(this.mMaskFormat);
        this.mAutoWB = (GLFrame)filterContext.getFrameManager().newFrame(this.mAverageFormat);
        this.mVideoInput = (GLFrame)filterContext.getFrameManager().newFrame(this.mMemoryFormat);
        this.mBgInput = (GLFrame)filterContext.getFrameManager().newFrame(this.mMemoryFormat);
        this.mMaskAverage = (GLFrame)filterContext.getFrameManager().newFrame(this.mAverageFormat);
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mBgDistanceShader);
        this.mBgDistProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        this.mBgDistProgram.setHostValue("subsample_level", Float.valueOf(this.mSubsampleLevel));
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mBgMaskShader);
        this.mBgMaskProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        object = this.mBgMaskProgram;
        float f = this.mAcceptStddev;
        ((ShaderProgram)object).setHostValue("accept_variance", Float.valueOf(f * f));
        float f2 = this.mLumScale;
        f = this.mChromaScale;
        this.mBgMaskProgram.setHostValue("yuv_weights", new float[]{f2, f});
        this.mBgMaskProgram.setHostValue("scale_lrg", Float.valueOf(this.mHierarchyLrgScale));
        this.mBgMaskProgram.setHostValue("scale_mid", Float.valueOf(this.mHierarchyMidScale));
        this.mBgMaskProgram.setHostValue("scale_sml", Float.valueOf(this.mHierarchySmlScale));
        this.mBgMaskProgram.setHostValue("exp_lrg", Float.valueOf(this.mSubsampleLevel + this.mHierarchyLrgExp));
        this.mBgMaskProgram.setHostValue("exp_mid", Float.valueOf(this.mSubsampleLevel + this.mHierarchyMidExp));
        this.mBgMaskProgram.setHostValue("exp_sml", Float.valueOf(this.mSubsampleLevel + this.mHierarchySmlExp));
        if (this.mUseTheForce) {
            object = new StringBuilder();
            ((StringBuilder)object).append(mSharedUtilShader);
            ((StringBuilder)object).append(mBgSubtractShader);
            ((StringBuilder)object).append(mBgSubtractForceShader);
            this.mBgSubtractProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append(mSharedUtilShader);
            ((StringBuilder)object).append(mBgSubtractShader);
            ((StringBuilder)object).append("}\n");
            this.mBgSubtractProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        }
        this.mBgSubtractProgram.setHostValue("bg_fit_transform", DEFAULT_BG_FIT_TRANSFORM);
        this.mBgSubtractProgram.setHostValue("mask_blend_bg", Float.valueOf(this.mMaskBg));
        this.mBgSubtractProgram.setHostValue("mask_blend_fg", Float.valueOf(this.mMaskFg));
        this.mBgSubtractProgram.setHostValue("exposure_change", Float.valueOf(this.mExposureChange));
        this.mBgSubtractProgram.setHostValue("whitebalanceblue_change", Float.valueOf(this.mWhiteBalanceBlueChange));
        this.mBgSubtractProgram.setHostValue("whitebalancered_change", Float.valueOf(this.mWhiteBalanceRedChange));
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mUpdateBgModelMeanShader);
        this.mBgUpdateMeanProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        this.mBgUpdateMeanProgram.setHostValue("subsample_level", Float.valueOf(this.mSubsampleLevel));
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mUpdateBgModelVarianceShader);
        this.mBgUpdateVarianceProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        this.mBgUpdateVarianceProgram.setHostValue("subsample_level", Float.valueOf(this.mSubsampleLevel));
        this.mCopyOutProgram = ShaderProgram.createIdentity(filterContext);
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mAutomaticWhiteBalance);
        this.mAutomaticWhiteBalanceProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        this.mAutomaticWhiteBalanceProgram.setHostValue("pyramid_depth", Float.valueOf(this.mPyramidDepth));
        this.mAutomaticWhiteBalanceProgram.setHostValue("autowb_toggle", this.mAutoWBToggle);
        object = new StringBuilder();
        ((StringBuilder)object).append(mSharedUtilShader);
        ((StringBuilder)object).append(mMaskVerifyShader);
        this.mMaskVerifyProgram = new ShaderProgram(filterContext, ((StringBuilder)object).toString());
        this.mMaskVerifyProgram.setHostValue("verify_rate", Float.valueOf(this.mVerifyRate));
        if (this.mLogVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Shader width set to ");
            ((StringBuilder)object).append(this.mMemoryFormat.getWidth());
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        this.mRelativeAspect = 1.0f;
        this.mFrameCount = 0;
        this.mStartLearning = true;
    }

    private boolean createMemoryFormat(FrameFormat frameFormat) {
        if (this.mMemoryFormat != null) {
            return false;
        }
        if (frameFormat.getWidth() != 0 && frameFormat.getHeight() != 0) {
            this.mMaskFormat = frameFormat.mutableCopy();
            int n = (int)Math.pow(2.0, this.mMaskWidthExp);
            int n2 = (int)Math.pow(2.0, this.mMaskHeightExp);
            this.mMaskFormat.setDimensions(n, n2);
            this.mPyramidDepth = Math.max(this.mMaskWidthExp, this.mMaskHeightExp);
            this.mMemoryFormat = this.mMaskFormat.mutableCopy();
            int n3 = Math.max(this.mMaskWidthExp, this.pyramidLevel(frameFormat.getWidth()));
            int n4 = Math.max(this.mMaskHeightExp, this.pyramidLevel(frameFormat.getHeight()));
            this.mPyramidDepth = Math.max(n3, n4);
            int n5 = Math.max(n, (int)Math.pow(2.0, n3));
            int n6 = Math.max(n2, (int)Math.pow(2.0, n4));
            this.mMemoryFormat.setDimensions(n5, n6);
            this.mSubsampleLevel = this.mPyramidDepth - Math.max(this.mMaskWidthExp, this.mMaskHeightExp);
            if (this.mLogVerbose) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Mask frames size ");
                stringBuilder.append(n);
                stringBuilder.append(" x ");
                stringBuilder.append(n2);
                Log.v(TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Pyramid levels ");
                stringBuilder.append(n3);
                stringBuilder.append(" x ");
                stringBuilder.append(n4);
                Log.v(TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Memory frames size ");
                stringBuilder.append(n5);
                stringBuilder.append(" x ");
                stringBuilder.append(n6);
                Log.v(TAG, stringBuilder.toString());
            }
            this.mAverageFormat = frameFormat.mutableCopy();
            this.mAverageFormat.setDimensions(1, 1);
            return true;
        }
        throw new RuntimeException("Attempting to process input frame with unknown size");
    }

    private int pyramidLevel(int n) {
        return (int)Math.floor(Math.log10(n) / Math.log10(2.0)) - 1;
    }

    private void updateBgScaling(Frame object, Frame frame, boolean bl) {
        float f = (float)((Frame)object).getFormat().getWidth() / (float)((Frame)object).getFormat().getHeight() / ((float)frame.getFormat().getWidth() / (float)frame.getFormat().getHeight());
        if (f != this.mRelativeAspect || bl) {
            float f2;
            this.mRelativeAspect = f;
            f = 0.0f;
            float f3 = 1.0f;
            float f4 = 0.0f;
            float f5 = 1.0f;
            int n = this.mBackgroundFitMode;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        f2 = this.mRelativeAspect;
                        if (f2 > 1.0f) {
                            f4 = 0.5f - 0.5f / f2;
                            f5 = 1.0f / f2;
                        } else {
                            f = 0.5f - f2 * 0.5f;
                            f3 = this.mRelativeAspect;
                        }
                    }
                } else {
                    f2 = this.mRelativeAspect;
                    if (f2 > 1.0f) {
                        f = 0.5f - f2 * 0.5f;
                        f3 = f2 * 1.0f;
                    } else {
                        f4 = 0.5f - 0.5f / f2;
                        f5 = 1.0f / f2;
                    }
                }
            }
            float f6 = f;
            f2 = f3;
            float f7 = f4;
            float f8 = f5;
            if (this.mMirrorBg) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Mirroring the background!");
                }
                if ((n = this.mOrientation) != 0 && n != 180) {
                    f8 = -f5;
                    f7 = 1.0f - f4;
                    f6 = f;
                    f2 = f3;
                } else {
                    f2 = -f3;
                    f6 = 1.0f - f;
                    f8 = f5;
                    f7 = f4;
                }
            }
            if (this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bgTransform: xMin, yMin, xWidth, yWidth : ");
                ((StringBuilder)object).append(f6);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(f7);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(f2);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(f8);
                ((StringBuilder)object).append(", mRelAspRatio = ");
                ((StringBuilder)object).append(this.mRelativeAspect);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            this.mBgSubtractProgram.setHostValue("bg_fit_transform", new float[]{f2, 0.0f, 0.0f, 0.0f, f8, 0.0f, f6, f7, 1.0f});
        }
    }

    @Override
    public void close(FilterContext filterContext) {
        if (this.mMemoryFormat == null) {
            return;
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Filter Closing!");
        }
        for (int i = 0; i < 2; ++i) {
            this.mBgMean[i].release();
            this.mBgVariance[i].release();
            this.mMaskVerify[i].release();
        }
        this.mDistance.release();
        this.mMask.release();
        this.mAutoWB.release();
        this.mVideoInput.release();
        this.mBgInput.release();
        this.mMaskAverage.release();
        this.mMemoryFormat = null;
    }

    @Override
    public void fieldPortValueUpdated(String object, FilterContext filterContext) {
        if (((String)object).equals("backgroundFitMode")) {
            this.mBackgroundFitModeChanged = true;
        } else if (((String)object).equals("acceptStddev")) {
            object = this.mBgMaskProgram;
            float f = this.mAcceptStddev;
            ((ShaderProgram)object).setHostValue("accept_variance", Float.valueOf(f * f));
        } else if (((String)object).equals("hierLrgScale")) {
            this.mBgMaskProgram.setHostValue("scale_lrg", Float.valueOf(this.mHierarchyLrgScale));
        } else if (((String)object).equals("hierMidScale")) {
            this.mBgMaskProgram.setHostValue("scale_mid", Float.valueOf(this.mHierarchyMidScale));
        } else if (((String)object).equals("hierSmlScale")) {
            this.mBgMaskProgram.setHostValue("scale_sml", Float.valueOf(this.mHierarchySmlScale));
        } else if (((String)object).equals("hierLrgExp")) {
            this.mBgMaskProgram.setHostValue("exp_lrg", Float.valueOf(this.mSubsampleLevel + this.mHierarchyLrgExp));
        } else if (((String)object).equals("hierMidExp")) {
            this.mBgMaskProgram.setHostValue("exp_mid", Float.valueOf(this.mSubsampleLevel + this.mHierarchyMidExp));
        } else if (((String)object).equals("hierSmlExp")) {
            this.mBgMaskProgram.setHostValue("exp_sml", Float.valueOf(this.mSubsampleLevel + this.mHierarchySmlExp));
        } else if (!((String)object).equals("lumScale") && !((String)object).equals("chromaScale")) {
            if (((String)object).equals("maskBg")) {
                this.mBgSubtractProgram.setHostValue("mask_blend_bg", Float.valueOf(this.mMaskBg));
            } else if (((String)object).equals("maskFg")) {
                this.mBgSubtractProgram.setHostValue("mask_blend_fg", Float.valueOf(this.mMaskFg));
            } else if (((String)object).equals("exposureChange")) {
                this.mBgSubtractProgram.setHostValue("exposure_change", Float.valueOf(this.mExposureChange));
            } else if (((String)object).equals("whitebalanceredChange")) {
                this.mBgSubtractProgram.setHostValue("whitebalancered_change", Float.valueOf(this.mWhiteBalanceRedChange));
            } else if (((String)object).equals("whitebalanceblueChange")) {
                this.mBgSubtractProgram.setHostValue("whitebalanceblue_change", Float.valueOf(this.mWhiteBalanceBlueChange));
            } else if (((String)object).equals("autowbToggle")) {
                this.mAutomaticWhiteBalanceProgram.setHostValue("autowb_toggle", this.mAutoWBToggle);
            }
        } else {
            float f = this.mLumScale;
            float f2 = this.mChromaScale;
            this.mBgMaskProgram.setHostValue("yuv_weights", new float[]{f, f2});
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        frameFormat = frameFormat.mutableCopy();
        if (!Arrays.asList(mOutputNames).contains(string2)) {
            ((MutableFrameFormat)frameFormat).setDimensions(0, 0);
        }
        return frameFormat;
    }

    @Override
    public void prepare(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Preparing BackDropperFilter!");
        }
        this.mBgMean = new GLFrame[2];
        this.mBgVariance = new GLFrame[2];
        this.mMaskVerify = new GLFrame[2];
        this.copyShaderProgram = ShaderProgram.createIdentity(filterContext);
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("video");
        Object object2 = this.pullInput("background");
        this.allocateFrames(frame.getFormat(), (FilterContext)object);
        if (this.mStartLearning) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Starting learning");
            }
            this.mBgUpdateMeanProgram.setHostValue("bg_adapt_rate", Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateMeanProgram.setHostValue("fg_adapt_rate", Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateVarianceProgram.setHostValue("bg_adapt_rate", Float.valueOf(this.mAdaptRateLearning));
            this.mBgUpdateVarianceProgram.setHostValue("fg_adapt_rate", Float.valueOf(this.mAdaptRateLearning));
            this.mFrameCount = 0;
        }
        int n = this.mPingPong;
        int n2 = n ^ true;
        this.mPingPong = n ^ true;
        this.updateBgScaling(frame, (Frame)object2, this.mBackgroundFitModeChanged);
        this.mBackgroundFitModeChanged = false;
        this.copyShaderProgram.process(frame, (Frame)this.mVideoInput);
        this.copyShaderProgram.process((Frame)object2, (Frame)this.mBgInput);
        this.mVideoInput.generateMipMap();
        this.mVideoInput.setTextureParameter(10241, 9985);
        this.mBgInput.generateMipMap();
        this.mBgInput.setTextureParameter(10241, 9985);
        if (this.mStartLearning) {
            this.copyShaderProgram.process(this.mVideoInput, (Frame)this.mBgMean[n2]);
            this.mStartLearning = false;
        }
        Object object3 = this.mVideoInput;
        Object object4 = this.mBgMean[n2];
        Object object5 = this.mBgVariance[n2];
        Object object6 = this.mBgDistProgram;
        Object object7 = this.mDistance;
        object6.process(new Frame[]{object3, object4, object5}, (Frame)object7);
        this.mDistance.generateMipMap();
        this.mDistance.setTextureParameter(10241, 9985);
        this.mBgMaskProgram.process(this.mDistance, (Frame)this.mMask);
        this.mMask.generateMipMap();
        this.mMask.setTextureParameter(10241, 9985);
        object7 = this.mVideoInput;
        object5 = this.mBgInput;
        object4 = this.mAutomaticWhiteBalanceProgram;
        object3 = this.mAutoWB;
        ((ShaderProgram)object4).process(new Frame[]{object7, object5}, (Frame)object3);
        if (this.mFrameCount <= this.mLearningDuration) {
            this.pushOutput("video", frame);
            int n3 = this.mFrameCount;
            int n4 = this.mLearningDuration;
            int n5 = this.mLearningVerifyDuration;
            if (n3 == n4 - n5) {
                this.copyShaderProgram.process(this.mMask, (Frame)this.mMaskVerify[n]);
                this.mBgUpdateMeanProgram.setHostValue("bg_adapt_rate", Float.valueOf(this.mAdaptRateBg));
                this.mBgUpdateMeanProgram.setHostValue("fg_adapt_rate", Float.valueOf(this.mAdaptRateFg));
                this.mBgUpdateVarianceProgram.setHostValue("bg_adapt_rate", Float.valueOf(this.mAdaptRateBg));
                this.mBgUpdateVarianceProgram.setHostValue("fg_adapt_rate", Float.valueOf(this.mAdaptRateFg));
            } else if (n3 > n4 - n5) {
                object7 = this.mMaskVerify;
                object2 = object7[n2];
                object3 = this.mMask;
                object4 = this.mMaskVerifyProgram;
                object7 = object7[n];
                ((ShaderProgram)object4).process(new Frame[]{object2, object3}, (Frame)object7);
                this.mMaskVerify[n].generateMipMap();
                this.mMaskVerify[n].setTextureParameter(10241, 9985);
            }
            if (this.mFrameCount == this.mLearningDuration) {
                this.copyShaderProgram.process(this.mMaskVerify[n], (Frame)this.mMaskAverage);
                n4 = this.mMaskAverage.getData().array()[3] & 255;
                if (this.mLogVerbose) {
                    Log.v(TAG, String.format("Mask_average is %d, threshold is %d", n4, 20));
                }
                if (n4 >= 20) {
                    this.mStartLearning = true;
                } else {
                    if (this.mLogVerbose) {
                        Log.v(TAG, "Learning done");
                    }
                    if ((object2 = this.mLearningDoneListener) != null) {
                        object2.onLearningDone(this);
                    }
                }
            }
        } else {
            object7 = ((FilterContext)object).getFrameManager().newFrame(frame.getFormat());
            object4 = this.mMask;
            object3 = this.mAutoWB;
            this.mBgSubtractProgram.process(new Frame[]{frame, object2, object4, object3}, (Frame)object7);
            this.pushOutput("video", (Frame)object7);
            ((Frame)object7).release();
        }
        if (this.mFrameCount < this.mLearningDuration - this.mLearningVerifyDuration || (double)this.mAdaptRateBg > 0.0 || (double)this.mAdaptRateFg > 0.0) {
            object2 = this.mVideoInput;
            object5 = this.mBgMean;
            object4 = object5[n2];
            object3 = this.mMask;
            object7 = this.mBgUpdateMeanProgram;
            object5 = object5[n];
            ((ShaderProgram)object7).process(new Frame[]{object2, object4, object3}, (Frame)object5);
            this.mBgMean[n].generateMipMap();
            this.mBgMean[n].setTextureParameter(10241, 9985);
            object5 = this.mVideoInput;
            object4 = this.mBgMean[n2];
            object6 = this.mBgVariance;
            object2 = object6[n2];
            object7 = this.mMask;
            object3 = this.mBgUpdateVarianceProgram;
            object6 = object6[n];
            ((ShaderProgram)object3).process(new Frame[]{object5, object4, object2, object7}, (Frame)object6);
            this.mBgVariance[n].generateMipMap();
            this.mBgVariance[n].setTextureParameter(10241, 9985);
        }
        if (this.mProvideDebugOutputs) {
            object2 = ((FilterContext)object).getFrameManager().newFrame(frame.getFormat());
            this.mCopyOutProgram.process(frame, (Frame)object2);
            this.pushOutput("debug1", (Frame)object2);
            ((Frame)object2).release();
            frame = ((FilterContext)object).getFrameManager().newFrame(this.mMemoryFormat);
            this.mCopyOutProgram.process(this.mMask, frame);
            this.pushOutput("debug2", frame);
            frame.release();
        }
        ++this.mFrameCount;
        if (this.mLogVerbose && this.mFrameCount % 30 == 0) {
            if (this.startTime == -1L) {
                ((FilterContext)object).getGLEnvironment().activate();
                GLES20.glFinish();
                this.startTime = SystemClock.elapsedRealtime();
            } else {
                ((FilterContext)object).getGLEnvironment().activate();
                GLES20.glFinish();
                long l = SystemClock.elapsedRealtime();
                object = new StringBuilder();
                ((StringBuilder)object).append("Avg. frame duration: ");
                ((StringBuilder)object).append(String.format("%.2f", (double)(l - this.startTime) / 30.0));
                ((StringBuilder)object).append(" ms. Avg. fps: ");
                ((StringBuilder)object).append(String.format("%.2f", 1000.0 / ((double)(l - this.startTime) / 30.0)));
                Log.v(TAG, ((StringBuilder)object).toString());
                this.startTime = l;
            }
        }
    }

    public void relearn() {
        synchronized (this) {
            this.mStartLearning = true;
            return;
        }
    }

    @Override
    public void setupPorts() {
        int n;
        int n2 = 0;
        String[] arrstring = ImageFormat.create(3, 0);
        String[] arrstring2 = mInputNames;
        int n3 = arrstring2.length;
        for (n = 0; n < n3; ++n) {
            this.addMaskedInputPort(arrstring2[n], (FrameFormat)arrstring);
        }
        arrstring = mOutputNames;
        n3 = arrstring.length;
        for (n = 0; n < n3; ++n) {
            this.addOutputBasedOnInput(arrstring[n], "video");
        }
        if (this.mProvideDebugOutputs) {
            arrstring = mDebugOutputNames;
            n3 = arrstring.length;
            for (n = n2; n < n3; ++n) {
                this.addOutputBasedOnInput(arrstring[n], "video");
            }
        }
    }

    public static interface LearningDoneListener {
        public void onLearningDone(BackDropperFilter var1);
    }

}

