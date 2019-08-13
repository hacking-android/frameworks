/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.UUID;

public final class DynamicsProcessing
extends AudioEffect {
    private static final int CHANNEL_COUNT_MAX = 32;
    private static final float CHANNEL_DEFAULT_INPUT_GAIN = 0.0f;
    private static final int CONFIG_DEFAULT_MBC_BANDS = 6;
    private static final int CONFIG_DEFAULT_POSTEQ_BANDS = 6;
    private static final int CONFIG_DEFAULT_PREEQ_BANDS = 6;
    private static final boolean CONFIG_DEFAULT_USE_LIMITER = true;
    private static final boolean CONFIG_DEFAULT_USE_MBC = true;
    private static final boolean CONFIG_DEFAULT_USE_POSTEQ = true;
    private static final boolean CONFIG_DEFAULT_USE_PREEQ = true;
    private static final int CONFIG_DEFAULT_VARIANT = 0;
    private static final float CONFIG_PREFERRED_FRAME_DURATION_MS = 10.0f;
    private static final float DEFAULT_MAX_FREQUENCY = 20000.0f;
    private static final float DEFAULT_MIN_FREQUENCY = 220.0f;
    private static final float EQ_DEFAULT_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_ATTACK_TIME = 1.0f;
    private static final boolean LIMITER_DEFAULT_ENABLED = true;
    private static final int LIMITER_DEFAULT_LINK_GROUP = 0;
    private static final float LIMITER_DEFAULT_POST_GAIN = 0.0f;
    private static final float LIMITER_DEFAULT_RATIO = 10.0f;
    private static final float LIMITER_DEFAULT_RELEASE_TIME = 60.0f;
    private static final float LIMITER_DEFAULT_THRESHOLD = -2.0f;
    private static final float MBC_DEFAULT_ATTACK_TIME = 3.0f;
    private static final boolean MBC_DEFAULT_ENABLED = true;
    private static final float MBC_DEFAULT_EXPANDER_RATIO = 1.0f;
    private static final float MBC_DEFAULT_KNEE_WIDTH = 0.0f;
    private static final float MBC_DEFAULT_NOISE_GATE_THRESHOLD = -90.0f;
    private static final float MBC_DEFAULT_POST_GAIN = 0.0f;
    private static final float MBC_DEFAULT_PRE_GAIN = 0.0f;
    private static final float MBC_DEFAULT_RATIO = 1.0f;
    private static final float MBC_DEFAULT_RELEASE_TIME = 80.0f;
    private static final float MBC_DEFAULT_THRESHOLD = -45.0f;
    private static final int PARAM_ENGINE_ARCHITECTURE = 48;
    private static final int PARAM_GET_CHANNEL_COUNT = 16;
    private static final int PARAM_INPUT_GAIN = 32;
    private static final int PARAM_LIMITER = 112;
    private static final int PARAM_MBC = 80;
    private static final int PARAM_MBC_BAND = 85;
    private static final int PARAM_POST_EQ = 96;
    private static final int PARAM_POST_EQ_BAND = 101;
    private static final int PARAM_PRE_EQ = 64;
    private static final int PARAM_PRE_EQ_BAND = 69;
    private static final boolean POSTEQ_DEFAULT_ENABLED = true;
    private static final boolean PREEQ_DEFAULT_ENABLED = true;
    private static final String TAG = "DynamicsProcessing";
    public static final int VARIANT_FAVOR_FREQUENCY_RESOLUTION = 0;
    public static final int VARIANT_FAVOR_TIME_RESOLUTION = 1;
    private static final float mMaxFreqLog;
    private static final float mMinFreqLog;
    private BaseParameterListener mBaseParamListener = null;
    private int mChannelCount = 0;
    private OnParameterChangeListener mParamListener = null;
    private final Object mParamListenerLock = new Object();

    static {
        mMinFreqLog = (float)Math.log10(220.0);
        mMaxFreqLog = (float)Math.log10(20000.0);
    }

    public DynamicsProcessing(int n) {
        this(0, n);
    }

    public DynamicsProcessing(int n, int n2) {
        this(n, n2, null);
    }

    public DynamicsProcessing(int n, int n2, Config config) {
        super(EFFECT_TYPE_DYNAMICS_PROCESSING, EFFECT_TYPE_NULL, n, n2);
        if (n2 == 0) {
            Log.w(TAG, "WARNING: attaching a DynamicsProcessing to global output mix isdeprecated!");
        }
        this.mChannelCount = this.getChannelCount();
        config = config == null ? new Config.Builder(0, this.mChannelCount, true, 6, true, 6, true, 6, true).build() : new Config(this.mChannelCount, config);
        this.setEngineArchitecture(config.getVariant(), config.getPreferredFrameDuration(), config.isPreEqInUse(), config.getPreEqBandCount(), config.isMbcInUse(), config.getMbcBandCount(), config.isPostEqInUse(), config.getPostEqBandCount(), config.isLimiterInUse());
        for (n = 0; n < this.mChannelCount; ++n) {
            this.updateEngineChannelByChannelIndex(n, config.getChannelByChannelIndex(n));
        }
    }

    private void byteArrayToNumberArray(byte[] object, Number[] arrnumber) {
        int n = 0;
        int n2 = 0;
        while (n < ((byte[])object).length && n2 < arrnumber.length) {
            if (arrnumber[n2] instanceof Integer) {
                arrnumber[n2] = DynamicsProcessing.byteArrayToInt((byte[])object, n);
                n += 4;
                ++n2;
                continue;
            }
            if (arrnumber[n2] instanceof Float) {
                arrnumber[n2] = Float.valueOf(DynamicsProcessing.byteArrayToFloat((byte[])object, n));
                n += 4;
                ++n2;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("can't convert ");
            ((StringBuilder)object).append(arrnumber[n2].getClass());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (n2 == arrnumber.length) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("only converted ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" values out of ");
        ((StringBuilder)object).append(arrnumber.length);
        ((StringBuilder)object).append(" expected");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private int getOneInt(int n) {
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(new int[]{n}, arrn));
        return arrn[0];
    }

    private float getTwoFloat(int n, int n2) {
        byte[] arrby = new byte[4];
        this.checkStatus(this.getParameter(new int[]{n, n2}, arrby));
        return DynamicsProcessing.byteArrayToFloat(arrby);
    }

    private byte[] numberArrayToByteArray(Number[] arrnumber) {
        int n = 0;
        for (int i = 0; i < arrnumber.length; ++i) {
            if (arrnumber[i] instanceof Integer) {
                n += 4;
                continue;
            }
            if (arrnumber[i] instanceof Float) {
                n += 4;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown value type ");
            stringBuilder.append(arrnumber[i].getClass());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        byteBuffer.order(ByteOrder.nativeOrder());
        for (n = 0; n < arrnumber.length; ++n) {
            if (arrnumber[n] instanceof Integer) {
                byteBuffer.putInt(arrnumber[n].intValue());
                continue;
            }
            if (!(arrnumber[n] instanceof Float)) continue;
            byteBuffer.putFloat(arrnumber[n].floatValue());
        }
        return byteBuffer.array();
    }

    private Channel queryEngineByChannelIndex(int n) {
        float f = this.getTwoFloat(32, n);
        Eq eq = this.queryEngineEqByChannelIndex(64, n);
        Mbc mbc = this.queryEngineMbcByChannelIndex(n);
        Eq eq2 = this.queryEngineEqByChannelIndex(96, n);
        Limiter limiter = this.queryEngineLimiterByChannelIndex(n);
        Channel channel = new Channel(f, eq.isInUse(), eq.getBandCount(), mbc.isInUse(), mbc.getBandCount(), eq2.isInUse(), eq2.getBandCount(), limiter.isInUse());
        channel.setInputGain(f);
        channel.setPreEq(eq);
        channel.setMbc(mbc);
        channel.setPostEq(eq2);
        channel.setLimiter(limiter);
        return channel;
    }

    private EqBand queryEngineEqBandByChannelIndex(int n, int n2, int n3) {
        boolean bl = false;
        Number[] arrnumber = new Number[3];
        arrnumber[0] = 0;
        byte[] arrby = Float.valueOf(0.0f);
        arrnumber[1] = arrby;
        arrnumber[2] = arrby;
        byte[] arrby2 = this.numberArrayToByteArray(new Number[]{n, n2, n3});
        arrby = this.numberArrayToByteArray(arrnumber);
        this.getParameter(arrby2, arrby);
        this.byteArrayToNumberArray(arrby, arrnumber);
        if (arrnumber[0].intValue() > 0) {
            bl = true;
        }
        return new EqBand(bl, arrnumber[1].floatValue(), arrnumber[2].floatValue());
    }

    private Eq queryEngineEqByChannelIndex(int n, int n2) {
        int n3 = n == 64 ? 64 : 96;
        boolean bl = false;
        Number[] arrnumber = new Number[]{0, 0, 0};
        Object object = this.numberArrayToByteArray(new Number[]{n3, n2});
        byte[] arrby = this.numberArrayToByteArray(arrnumber);
        this.getParameter((byte[])object, arrby);
        this.byteArrayToNumberArray(arrby, arrnumber);
        int n4 = arrnumber[2].intValue();
        boolean bl2 = arrnumber[0].intValue() > 0;
        if (arrnumber[1].intValue() > 0) {
            bl = true;
        }
        object = new Eq(bl2, bl, n4);
        for (n3 = 0; n3 < n4; ++n3) {
            int n5 = n == 64 ? 69 : 101;
            ((Eq)object).setBand(n3, this.queryEngineEqBandByChannelIndex(n5, n2, n3));
        }
        return object;
    }

    private Limiter queryEngineLimiterByChannelIndex(int n) {
        byte[] arrby = 0;
        Number[] arrnumber = new Number[8];
        arrnumber[0] = arrby;
        arrnumber[1] = arrby;
        arrnumber[2] = arrby;
        arrby = Float.valueOf(0.0f);
        arrnumber[3] = arrby;
        arrnumber[4] = arrby;
        arrnumber[5] = arrby;
        arrnumber[6] = arrby;
        arrnumber[7] = arrby;
        arrby = this.numberArrayToByteArray(new Number[]{112, n});
        byte[] arrby2 = this.numberArrayToByteArray(arrnumber);
        this.getParameter(arrby, arrby2);
        this.byteArrayToNumberArray(arrby2, arrnumber);
        boolean bl = arrnumber[0].intValue() > 0;
        boolean bl2 = arrnumber[1].intValue() > 0;
        return new Limiter(bl, bl2, arrnumber[2].intValue(), arrnumber[3].floatValue(), arrnumber[4].floatValue(), arrnumber[5].floatValue(), arrnumber[6].floatValue(), arrnumber[7].floatValue());
    }

    private MbcBand queryEngineMbcBandByChannelIndex(int n, int n2) {
        Number[] arrnumber = new Number[11];
        arrnumber[0] = 0;
        byte[] arrby = Float.valueOf(0.0f);
        arrnumber[1] = arrby;
        arrnumber[2] = arrby;
        arrnumber[3] = arrby;
        arrnumber[4] = arrby;
        arrnumber[5] = arrby;
        arrnumber[6] = arrby;
        arrnumber[7] = arrby;
        arrnumber[8] = arrby;
        arrnumber[9] = arrby;
        arrnumber[10] = arrby;
        byte[] arrby2 = this.numberArrayToByteArray(new Number[]{85, n, n2});
        arrby = this.numberArrayToByteArray(arrnumber);
        this.getParameter(arrby2, arrby);
        this.byteArrayToNumberArray(arrby, arrnumber);
        boolean bl = arrnumber[0].intValue() > 0;
        return new MbcBand(bl, arrnumber[1].floatValue(), arrnumber[2].floatValue(), arrnumber[3].floatValue(), arrnumber[4].floatValue(), arrnumber[5].floatValue(), arrnumber[6].floatValue(), arrnumber[7].floatValue(), arrnumber[8].floatValue(), arrnumber[9].floatValue(), arrnumber[10].floatValue());
    }

    private Mbc queryEngineMbcByChannelIndex(int n) {
        boolean bl = false;
        byte[] arrby = Integer.valueOf(0);
        Object object = new Number[]{arrby, arrby, arrby};
        arrby = this.numberArrayToByteArray(new Number[]{80, n});
        byte[] arrby2 = this.numberArrayToByteArray((Number[])object);
        this.getParameter(arrby, arrby2);
        this.byteArrayToNumberArray(arrby2, (Number[])object);
        int n2 = object[2].intValue();
        boolean bl2 = object[0].intValue() > 0;
        if (object[1].intValue() > 0) {
            bl = true;
        }
        object = new Mbc(bl2, bl, n2);
        for (int i = 0; i < n2; ++i) {
            ((Mbc)object).setBand(i, this.queryEngineMbcBandByChannelIndex(n, i));
        }
        return object;
    }

    private void setEngineArchitecture(int n, float f, boolean bl, int n2, boolean bl2, int n3, boolean bl3, int n4, boolean bl4) {
        this.setNumberArray(new Number[]{48}, new Number[]{n, Float.valueOf(f), (int)bl, n2, (int)bl2, n3, (int)bl3, n4, (int)bl4});
    }

    private void setNumberArray(Number[] arrnumber, Number[] arrnumber2) {
        this.checkStatus(this.setParameter(this.numberArrayToByteArray(arrnumber), this.numberArrayToByteArray(arrnumber2)));
    }

    private void setTwoFloat(int n, int n2, float f) {
        byte[] arrby = DynamicsProcessing.floatToByteArray(f);
        this.checkStatus(this.setParameter(new int[]{n, n2}, arrby));
    }

    private void updateEffectArchitecture() {
        this.mChannelCount = this.getChannelCount();
    }

    private void updateEngineChannelByChannelIndex(int n, Channel channel) {
        this.setTwoFloat(32, n, channel.getInputGain());
        this.updateEngineEqByChannelIndex(64, n, channel.getPreEq());
        this.updateEngineMbcByChannelIndex(n, channel.getMbc());
        this.updateEngineEqByChannelIndex(96, n, channel.getPostEq());
        this.updateEngineLimiterByChannelIndex(n, channel.getLimiter());
    }

    private void updateEngineEqBandByChannelIndex(int n, int n2, int n3, EqBand eqBand) {
        int n4 = eqBand.isEnabled();
        float f = eqBand.getCutoffFrequency();
        float f2 = eqBand.getGain();
        this.setNumberArray(new Number[]{n, n2, n3}, new Number[]{n4, Float.valueOf(f), Float.valueOf(f2)});
    }

    private void updateEngineEqByChannelIndex(int n, int n2, Eq eq) {
        int n3 = eq.getBandCount();
        int n4 = eq.isInUse();
        int n5 = eq.isEnabled();
        this.setNumberArray(new Number[]{n, n2}, new Number[]{n4, n5, n3});
        for (n5 = 0; n5 < n3; ++n5) {
            EqBand eqBand = eq.getBand(n5);
            n4 = n == 64 ? 69 : 101;
            this.updateEngineEqBandByChannelIndex(n4, n2, n5, eqBand);
        }
    }

    private void updateEngineLimiterByChannelIndex(int n, Limiter limiter) {
        int n2 = limiter.isInUse();
        int n3 = limiter.isEnabled();
        int n4 = limiter.getLinkGroup();
        float f = limiter.getAttackTime();
        float f2 = limiter.getReleaseTime();
        float f3 = limiter.getRatio();
        float f4 = limiter.getThreshold();
        float f5 = limiter.getPostGain();
        this.setNumberArray(new Number[]{112, n}, new Number[]{n2, n3, n4, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5)});
    }

    private void updateEngineMbcBandByChannelIndex(int n, int n2, MbcBand mbcBand) {
        int n3 = mbcBand.isEnabled();
        float f = mbcBand.getCutoffFrequency();
        float f2 = mbcBand.getAttackTime();
        float f3 = mbcBand.getReleaseTime();
        float f4 = mbcBand.getRatio();
        float f5 = mbcBand.getThreshold();
        float f6 = mbcBand.getKneeWidth();
        float f7 = mbcBand.getNoiseGateThreshold();
        float f8 = mbcBand.getExpanderRatio();
        float f9 = mbcBand.getPreGain();
        float f10 = mbcBand.getPostGain();
        this.setNumberArray(new Number[]{85, n, n2}, new Number[]{n3, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8), Float.valueOf(f9), Float.valueOf(f10)});
    }

    private void updateEngineMbcByChannelIndex(int n, Mbc mbc) {
        int n2 = mbc.getBandCount();
        int n3 = mbc.isInUse();
        int n4 = mbc.isEnabled();
        this.setNumberArray(new Number[]{80, n}, new Number[]{n3, n4, n2});
        for (n4 = 0; n4 < n2; ++n4) {
            this.updateEngineMbcBandByChannelIndex(n, n4, mbc.getBand(n4));
        }
    }

    public Channel getChannelByChannelIndex(int n) {
        return this.queryEngineByChannelIndex(n);
    }

    public int getChannelCount() {
        return this.getOneInt(16);
    }

    public Config getConfig() {
        byte[] arrby = Integer.valueOf(0);
        Object object = new Number[]{arrby, Float.valueOf(0.0f), arrby, arrby, arrby, arrby, arrby, arrby, arrby};
        arrby = this.numberArrayToByteArray(new Number[]{48});
        byte[] arrby2 = this.numberArrayToByteArray((Number[])object);
        this.getParameter(arrby, arrby2);
        this.byteArrayToNumberArray(arrby2, (Number[])object);
        int n = object[0].intValue();
        int n2 = this.mChannelCount;
        boolean bl = object[2].intValue() > 0;
        int n3 = object[3].intValue();
        boolean bl2 = object[4].intValue() > 0;
        int n4 = object[5].intValue();
        boolean bl3 = object[6].intValue() > 0;
        int n5 = object[7].intValue();
        boolean bl4 = object[8].intValue() > 0;
        object = new Config.Builder(n, n2, bl, n3, bl2, n4, bl3, n5, bl4).setPreferredFrameDuration(object[1].floatValue()).build();
        for (n = 0; n < this.mChannelCount; ++n) {
            ((Config)object).setChannelTo(n, this.queryEngineByChannelIndex(n));
        }
        return object;
    }

    public float getInputGainByChannelIndex(int n) {
        return this.getTwoFloat(32, n);
    }

    public Limiter getLimiterByChannelIndex(int n) {
        return this.queryEngineLimiterByChannelIndex(n);
    }

    public MbcBand getMbcBandByChannelIndex(int n, int n2) {
        return this.queryEngineMbcBandByChannelIndex(n, n2);
    }

    public Mbc getMbcByChannelIndex(int n) {
        return this.queryEngineMbcByChannelIndex(n);
    }

    public EqBand getPostEqBandByChannelIndex(int n, int n2) {
        return this.queryEngineEqBandByChannelIndex(101, n, n2);
    }

    public Eq getPostEqByChannelIndex(int n) {
        return this.queryEngineEqByChannelIndex(96, n);
    }

    public EqBand getPreEqBandByChannelIndex(int n, int n2) {
        return this.queryEngineEqBandByChannelIndex(69, n, n2);
    }

    public Eq getPreEqByChannelIndex(int n) {
        return this.queryEngineEqByChannelIndex(64, n);
    }

    public Settings getProperties() {
        Settings settings = new Settings();
        settings.channelCount = this.getChannelCount();
        if (settings.channelCount <= 32) {
            settings.inputGain = new float[settings.channelCount];
            for (int i = 0; i < settings.channelCount; ++i) {
            }
            return settings;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("too many channels Settings:");
        stringBuilder.append(settings);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setAllChannelsTo(Channel channel) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setChannelTo(i, channel);
        }
    }

    public void setChannelTo(int n, Channel channel) {
        this.updateEngineChannelByChannelIndex(n, channel);
    }

    public void setInputGainAllChannelsTo(float f) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setInputGainbyChannel(i, f);
        }
    }

    public void setInputGainbyChannel(int n, float f) {
        this.setTwoFloat(32, n, f);
    }

    public void setLimiterAllChannelsTo(Limiter limiter) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setLimiterByChannelIndex(i, limiter);
        }
    }

    public void setLimiterByChannelIndex(int n, Limiter limiter) {
        this.updateEngineLimiterByChannelIndex(n, limiter);
    }

    public void setMbcAllChannelsTo(Mbc mbc) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setMbcByChannelIndex(i, mbc);
        }
    }

    public void setMbcBandAllChannelsTo(int n, MbcBand mbcBand) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setMbcBandByChannelIndex(i, n, mbcBand);
        }
    }

    public void setMbcBandByChannelIndex(int n, int n2, MbcBand mbcBand) {
        this.updateEngineMbcBandByChannelIndex(n, n2, mbcBand);
    }

    public void setMbcByChannelIndex(int n, Mbc mbc) {
        this.updateEngineMbcByChannelIndex(n, mbc);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setParameterListener(OnParameterChangeListener onParameterChangeListener) {
        Object object = this.mParamListenerLock;
        synchronized (object) {
            if (this.mParamListener == null) {
                BaseParameterListener baseParameterListener;
                this.mBaseParamListener = baseParameterListener = new BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
            this.mParamListener = onParameterChangeListener;
            return;
        }
    }

    public void setPostEqAllChannelsTo(Eq eq) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setPostEqByChannelIndex(i, eq);
        }
    }

    public void setPostEqBandAllChannelsTo(int n, EqBand eqBand) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setPostEqBandByChannelIndex(i, n, eqBand);
        }
    }

    public void setPostEqBandByChannelIndex(int n, int n2, EqBand eqBand) {
        this.updateEngineEqBandByChannelIndex(101, n, n2, eqBand);
    }

    public void setPostEqByChannelIndex(int n, Eq eq) {
        this.updateEngineEqByChannelIndex(96, n, eq);
    }

    public void setPreEqAllChannelsTo(Eq eq) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setPreEqByChannelIndex(i, eq);
        }
    }

    public void setPreEqBandAllChannelsTo(int n, EqBand eqBand) {
        for (int i = 0; i < this.mChannelCount; ++i) {
            this.setPreEqBandByChannelIndex(i, n, eqBand);
        }
    }

    public void setPreEqBandByChannelIndex(int n, int n2, EqBand eqBand) {
        this.updateEngineEqBandByChannelIndex(69, n, n2, eqBand);
    }

    public void setPreEqByChannelIndex(int n, Eq eq) {
        this.updateEngineEqByChannelIndex(64, n, eq);
    }

    public void setProperties(Settings settings) {
        if (settings.channelCount == settings.inputGain.length && settings.channelCount == this.mChannelCount) {
            for (int i = 0; i < this.mChannelCount; ++i) {
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("settings invalid channel count: ");
        stringBuilder.append(settings.channelCount);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static class BandBase {
        private float mCutoffFrequency;
        private boolean mEnabled;

        public BandBase(boolean bl, float f) {
            this.mEnabled = bl;
            this.mCutoffFrequency = f;
        }

        public float getCutoffFrequency() {
            return this.mCutoffFrequency;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public void setCutoffFrequency(float f) {
            this.mCutoffFrequency = f;
        }

        public void setEnabled(boolean bl) {
            this.mEnabled = bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format(" Enabled: %b\n", this.mEnabled));
            stringBuilder.append(String.format(" CutoffFrequency: %f\n", Float.valueOf(this.mCutoffFrequency)));
            return stringBuilder.toString();
        }
    }

    public static class BandStage
    extends Stage {
        private int mBandCount;

        public BandStage(boolean bl, boolean bl2, int n) {
            super(bl, bl2);
            if (!this.isInUse()) {
                n = 0;
            }
            this.mBandCount = n;
        }

        public int getBandCount() {
            return this.mBandCount;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            if (this.isInUse()) {
                stringBuilder.append(String.format(" Band Count: %d\n", this.mBandCount));
            }
            return stringBuilder.toString();
        }
    }

    private class BaseParameterListener
    implements AudioEffect.OnParameterChangeListener {
        private BaseParameterListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onParameterChange(AudioEffect object, int n, byte[] arrby, byte[] arrby2) {
            if (n != 0) {
                return;
            }
            object = null;
            Object object2 = DynamicsProcessing.this.mParamListenerLock;
            // MONITORENTER : object2
            if (DynamicsProcessing.this.mParamListener != null) {
                object = DynamicsProcessing.this.mParamListener;
            }
            // MONITOREXIT : object2
            if (object == null) return;
            n = -1;
            int n2 = Integer.MIN_VALUE;
            if (arrby.length == 4) {
                n = AudioEffect.byteArrayToInt(arrby, 0);
            }
            if (arrby2.length == 4) {
                n2 = AudioEffect.byteArrayToInt(arrby2, 0);
            }
            if (n == -1) return;
            if (n2 == Integer.MIN_VALUE) return;
            object.onParameterChange(DynamicsProcessing.this, n, n2);
        }
    }

    public static final class Channel {
        private float mInputGain;
        private Limiter mLimiter;
        private Mbc mMbc;
        private Eq mPostEq;
        private Eq mPreEq;

        public Channel(float f, boolean bl, int n, boolean bl2, int n2, boolean bl3, int n3, boolean bl4) {
            this.mInputGain = f;
            this.mPreEq = new Eq(bl, true, n);
            this.mMbc = new Mbc(bl2, true, n2);
            this.mPostEq = new Eq(bl3, true, n3);
            this.mLimiter = new Limiter(bl4, true, 0, 1.0f, 60.0f, 10.0f, -2.0f, 0.0f);
        }

        public Channel(Channel channel) {
            this.mInputGain = channel.mInputGain;
            this.mPreEq = new Eq(channel.mPreEq);
            this.mMbc = new Mbc(channel.mMbc);
            this.mPostEq = new Eq(channel.mPostEq);
            this.mLimiter = new Limiter(channel.mLimiter);
        }

        public float getInputGain() {
            return this.mInputGain;
        }

        public Limiter getLimiter() {
            return this.mLimiter;
        }

        public Mbc getMbc() {
            return this.mMbc;
        }

        public MbcBand getMbcBand(int n) {
            return this.mMbc.getBand(n);
        }

        public Eq getPostEq() {
            return this.mPostEq;
        }

        public EqBand getPostEqBand(int n) {
            return this.mPostEq.getBand(n);
        }

        public Eq getPreEq() {
            return this.mPreEq;
        }

        public EqBand getPreEqBand(int n) {
            return this.mPreEq.getBand(n);
        }

        public void setInputGain(float f) {
            this.mInputGain = f;
        }

        public void setLimiter(Limiter limiter) {
            this.mLimiter = new Limiter(limiter);
        }

        public void setMbc(Mbc mbc) {
            if (mbc.getBandCount() == this.mMbc.getBandCount()) {
                this.mMbc = new Mbc(mbc);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MbcBandCount changed from ");
            stringBuilder.append(this.mMbc.getBandCount());
            stringBuilder.append(" to ");
            stringBuilder.append(mbc.getBandCount());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setMbcBand(int n, MbcBand mbcBand) {
            this.mMbc.setBand(n, mbcBand);
        }

        public void setPostEq(Eq eq) {
            if (eq.getBandCount() == this.mPostEq.getBandCount()) {
                this.mPostEq = new Eq(eq);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PostEqBandCount changed from ");
            stringBuilder.append(this.mPostEq.getBandCount());
            stringBuilder.append(" to ");
            stringBuilder.append(eq.getBandCount());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setPostEqBand(int n, EqBand eqBand) {
            this.mPostEq.setBand(n, eqBand);
        }

        public void setPreEq(Eq eq) {
            if (eq.getBandCount() == this.mPreEq.getBandCount()) {
                this.mPreEq = new Eq(eq);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PreEqBandCount changed from ");
            stringBuilder.append(this.mPreEq.getBandCount());
            stringBuilder.append(" to ");
            stringBuilder.append(eq.getBandCount());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setPreEqBand(int n, EqBand eqBand) {
            this.mPreEq.setBand(n, eqBand);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format(" InputGain: %f\n", Float.valueOf(this.mInputGain)));
            stringBuilder.append("-->PreEq\n");
            stringBuilder.append(this.mPreEq.toString());
            stringBuilder.append("-->MBC\n");
            stringBuilder.append(this.mMbc.toString());
            stringBuilder.append("-->PostEq\n");
            stringBuilder.append(this.mPostEq.toString());
            stringBuilder.append("-->Limiter\n");
            stringBuilder.append(this.mLimiter.toString());
            return stringBuilder.toString();
        }
    }

    public static final class Config {
        private final Channel[] mChannel;
        private final int mChannelCount;
        private final boolean mLimiterInUse;
        private final int mMbcBandCount;
        private final boolean mMbcInUse;
        private final int mPostEqBandCount;
        private final boolean mPostEqInUse;
        private final int mPreEqBandCount;
        private final boolean mPreEqInUse;
        private final float mPreferredFrameDuration;
        private final int mVariant;

        public Config(int n, float f, int n2, boolean bl, int n3, boolean bl2, int n4, boolean bl3, int n5, boolean bl4, Channel[] arrchannel) {
            this.mVariant = n;
            this.mPreferredFrameDuration = f;
            this.mChannelCount = n2;
            this.mPreEqInUse = bl;
            this.mPreEqBandCount = n3;
            this.mMbcInUse = bl2;
            this.mMbcBandCount = n4;
            this.mPostEqInUse = bl3;
            this.mPostEqBandCount = n5;
            this.mLimiterInUse = bl4;
            this.mChannel = new Channel[this.mChannelCount];
            for (n = 0; n < this.mChannelCount; ++n) {
                if (n >= arrchannel.length) continue;
                this.mChannel[n] = new Channel(arrchannel[n]);
            }
        }

        public Config(int n, Config config) {
            this.mVariant = config.mVariant;
            this.mPreferredFrameDuration = config.mPreferredFrameDuration;
            this.mChannelCount = config.mChannelCount;
            this.mPreEqInUse = config.mPreEqInUse;
            this.mPreEqBandCount = config.mPreEqBandCount;
            this.mMbcInUse = config.mMbcInUse;
            this.mMbcBandCount = config.mMbcBandCount;
            this.mPostEqInUse = config.mPostEqInUse;
            this.mPostEqBandCount = config.mPostEqBandCount;
            this.mLimiterInUse = config.mLimiterInUse;
            if (this.mChannelCount == config.mChannel.length) {
                if (n >= 1) {
                    this.mChannel = new Channel[n];
                    for (int i = 0; i < n; ++i) {
                        int n2 = this.mChannelCount;
                        this.mChannel[i] = i < n2 ? new Channel(config.mChannel[i]) : new Channel(config.mChannel[n2 - 1]);
                    }
                    return;
                }
                throw new IllegalArgumentException("channel resizing less than 1 not allowed");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("configuration channel counts differ ");
            stringBuilder.append(this.mChannelCount);
            stringBuilder.append(" !=");
            stringBuilder.append(config.mChannel.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Config(Config config) {
            this(config.mChannelCount, config);
        }

        private void checkChannel(int n) {
            if (n >= 0 && n < this.mChannel.length) {
                return;
            }
            throw new IllegalArgumentException("ChannelIndex out of bounds");
        }

        public Channel getChannelByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n];
        }

        public float getInputGainByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n].getInputGain();
        }

        public Limiter getLimiterByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n].getLimiter();
        }

        public MbcBand getMbcBandByChannelIndex(int n, int n2) {
            this.checkChannel(n);
            return this.mChannel[n].getMbcBand(n2);
        }

        public int getMbcBandCount() {
            return this.mMbcBandCount;
        }

        public Mbc getMbcByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n].getMbc();
        }

        public EqBand getPostEqBandByChannelIndex(int n, int n2) {
            this.checkChannel(n);
            return this.mChannel[n].getPostEqBand(n2);
        }

        public int getPostEqBandCount() {
            return this.mPostEqBandCount;
        }

        public Eq getPostEqByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n].getPostEq();
        }

        public EqBand getPreEqBandByChannelIndex(int n, int n2) {
            this.checkChannel(n);
            return this.mChannel[n].getPreEqBand(n2);
        }

        public int getPreEqBandCount() {
            return this.mPreEqBandCount;
        }

        public Eq getPreEqByChannelIndex(int n) {
            this.checkChannel(n);
            return this.mChannel[n].getPreEq();
        }

        public float getPreferredFrameDuration() {
            return this.mPreferredFrameDuration;
        }

        public int getVariant() {
            return this.mVariant;
        }

        public boolean isLimiterInUse() {
            return this.mLimiterInUse;
        }

        public boolean isMbcInUse() {
            return this.mMbcInUse;
        }

        public boolean isPostEqInUse() {
            return this.mPostEqInUse;
        }

        public boolean isPreEqInUse() {
            return this.mPreEqInUse;
        }

        public void setAllChannelsTo(Channel channel) {
            for (int i = 0; i < this.mChannel.length; ++i) {
                this.setChannelTo(i, channel);
            }
        }

        public void setChannelTo(int n, Channel channel) {
            this.checkChannel(n);
            if (this.mMbcBandCount == channel.getMbc().getBandCount()) {
                if (this.mPreEqBandCount == channel.getPreEq().getBandCount()) {
                    if (this.mPostEqBandCount == channel.getPostEq().getBandCount()) {
                        this.mChannel[n] = new Channel(channel);
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("PostEqBandCount changed from ");
                    stringBuilder.append(this.mPostEqBandCount);
                    stringBuilder.append(" to ");
                    stringBuilder.append(channel.getPostEq().getBandCount());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PreEqBandCount changed from ");
                stringBuilder.append(this.mPreEqBandCount);
                stringBuilder.append(" to ");
                stringBuilder.append(channel.getPreEq().getBandCount());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MbcBandCount changed from ");
            stringBuilder.append(this.mMbcBandCount);
            stringBuilder.append(" to ");
            stringBuilder.append(channel.getPreEq().getBandCount());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setInputGainAllChannelsTo(float f) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setInputGain(f);
            }
        }

        public void setInputGainByChannelIndex(int n, float f) {
            this.checkChannel(n);
            this.mChannel[n].setInputGain(f);
        }

        public void setLimiterAllChannelsTo(Limiter limiter) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setLimiter(limiter);
            }
        }

        public void setLimiterByChannelIndex(int n, Limiter limiter) {
            this.checkChannel(n);
            this.mChannel[n].setLimiter(limiter);
        }

        public void setMbcAllChannelsTo(Mbc mbc) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setMbc(mbc);
            }
        }

        public void setMbcBandAllChannelsTo(int n, MbcBand mbcBand) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setMbcBand(n, mbcBand);
            }
        }

        public void setMbcBandByChannelIndex(int n, int n2, MbcBand mbcBand) {
            this.checkChannel(n);
            this.mChannel[n].setMbcBand(n2, mbcBand);
        }

        public void setMbcByChannelIndex(int n, Mbc mbc) {
            this.checkChannel(n);
            this.mChannel[n].setMbc(mbc);
        }

        public void setPostEqAllChannelsTo(Eq eq) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setPostEq(eq);
            }
        }

        public void setPostEqBandAllChannelsTo(int n, EqBand eqBand) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setPostEqBand(n, eqBand);
            }
        }

        public void setPostEqBandByChannelIndex(int n, int n2, EqBand eqBand) {
            this.checkChannel(n);
            this.mChannel[n].setPostEqBand(n2, eqBand);
        }

        public void setPostEqByChannelIndex(int n, Eq eq) {
            this.checkChannel(n);
            this.mChannel[n].setPostEq(eq);
        }

        public void setPreEqAllChannelsTo(Eq eq) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setPreEq(eq);
            }
        }

        public void setPreEqBandAllChannelsTo(int n, EqBand eqBand) {
            Channel[] arrchannel;
            for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                arrchannel[i].setPreEqBand(n, eqBand);
            }
        }

        public void setPreEqBandByChannelIndex(int n, int n2, EqBand eqBand) {
            this.checkChannel(n);
            this.mChannel[n].setPreEqBand(n2, eqBand);
        }

        public void setPreEqByChannelIndex(int n, Eq eq) {
            this.checkChannel(n);
            this.mChannel[n].setPreEq(eq);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Variant: %d\n", this.mVariant));
            stringBuilder.append(String.format("PreferredFrameDuration: %f\n", Float.valueOf(this.mPreferredFrameDuration)));
            stringBuilder.append(String.format("ChannelCount: %d\n", this.mChannelCount));
            stringBuilder.append(String.format("PreEq inUse: %b, bandCount:%d\n", this.mPreEqInUse, this.mPreEqBandCount));
            stringBuilder.append(String.format("Mbc inUse: %b, bandCount: %d\n", this.mMbcInUse, this.mMbcBandCount));
            stringBuilder.append(String.format("PostEq inUse: %b, bandCount: %d\n", this.mPostEqInUse, this.mPostEqBandCount));
            stringBuilder.append(String.format("Limiter inUse: %b\n", this.mLimiterInUse));
            for (int i = 0; i < this.mChannel.length; ++i) {
                stringBuilder.append(String.format("==Channel %d\n", i));
                stringBuilder.append(this.mChannel[i].toString());
            }
            return stringBuilder.toString();
        }

        public static final class Builder {
            private Channel[] mChannel;
            private int mChannelCount;
            private boolean mLimiterInUse;
            private int mMbcBandCount;
            private boolean mMbcInUse;
            private int mPostEqBandCount;
            private boolean mPostEqInUse;
            private int mPreEqBandCount;
            private boolean mPreEqInUse;
            private float mPreferredFrameDuration = 10.0f;
            private int mVariant;

            public Builder(int n, int n2, boolean bl, int n3, boolean bl2, int n4, boolean bl3, int n5, boolean bl4) {
                this.mVariant = n;
                this.mChannelCount = n2;
                this.mPreEqInUse = bl;
                this.mPreEqBandCount = n3;
                this.mMbcInUse = bl2;
                this.mMbcBandCount = n4;
                this.mPostEqInUse = bl3;
                this.mPostEqBandCount = n5;
                this.mLimiterInUse = bl4;
                this.mChannel = new Channel[this.mChannelCount];
                for (n = 0; n < this.mChannelCount; ++n) {
                    this.mChannel[n] = new Channel(0.0f, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse);
                }
            }

            private void checkChannel(int n) {
                if (n >= 0 && n < this.mChannel.length) {
                    return;
                }
                throw new IllegalArgumentException("ChannelIndex out of bounds");
            }

            public Config build() {
                return new Config(this.mVariant, this.mPreferredFrameDuration, this.mChannelCount, this.mPreEqInUse, this.mPreEqBandCount, this.mMbcInUse, this.mMbcBandCount, this.mPostEqInUse, this.mPostEqBandCount, this.mLimiterInUse, this.mChannel);
            }

            public Builder setAllChannelsTo(Channel channel) {
                for (int i = 0; i < this.mChannel.length; ++i) {
                    this.setChannelTo(i, channel);
                }
                return this;
            }

            public Builder setChannelTo(int n, Channel channel) {
                this.checkChannel(n);
                if (this.mMbcBandCount == channel.getMbc().getBandCount()) {
                    if (this.mPreEqBandCount == channel.getPreEq().getBandCount()) {
                        if (this.mPostEqBandCount == channel.getPostEq().getBandCount()) {
                            this.mChannel[n] = new Channel(channel);
                            return this;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("PostEqBandCount changed from ");
                        stringBuilder.append(this.mPostEqBandCount);
                        stringBuilder.append(" to ");
                        stringBuilder.append(channel.getPostEq().getBandCount());
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("PreEqBandCount changed from ");
                    stringBuilder.append(this.mPreEqBandCount);
                    stringBuilder.append(" to ");
                    stringBuilder.append(channel.getPreEq().getBandCount());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MbcBandCount changed from ");
                stringBuilder.append(this.mMbcBandCount);
                stringBuilder.append(" to ");
                stringBuilder.append(channel.getPreEq().getBandCount());
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            public Builder setInputGainAllChannelsTo(float f) {
                Channel[] arrchannel;
                for (int i = 0; i < (arrchannel = this.mChannel).length; ++i) {
                    arrchannel[i].setInputGain(f);
                }
                return this;
            }

            public Builder setInputGainByChannelIndex(int n, float f) {
                this.checkChannel(n);
                this.mChannel[n].setInputGain(f);
                return this;
            }

            public Builder setLimiterAllChannelsTo(Limiter limiter) {
                for (int i = 0; i < this.mChannel.length; ++i) {
                    this.setLimiterByChannelIndex(i, limiter);
                }
                return this;
            }

            public Builder setLimiterByChannelIndex(int n, Limiter limiter) {
                this.checkChannel(n);
                this.mChannel[n].setLimiter(limiter);
                return this;
            }

            public Builder setMbcAllChannelsTo(Mbc mbc) {
                for (int i = 0; i < this.mChannel.length; ++i) {
                    this.setMbcByChannelIndex(i, mbc);
                }
                return this;
            }

            public Builder setMbcByChannelIndex(int n, Mbc mbc) {
                this.checkChannel(n);
                this.mChannel[n].setMbc(mbc);
                return this;
            }

            public Builder setPostEqAllChannelsTo(Eq eq) {
                for (int i = 0; i < this.mChannel.length; ++i) {
                    this.setPostEqByChannelIndex(i, eq);
                }
                return this;
            }

            public Builder setPostEqByChannelIndex(int n, Eq eq) {
                this.checkChannel(n);
                this.mChannel[n].setPostEq(eq);
                return this;
            }

            public Builder setPreEqAllChannelsTo(Eq eq) {
                for (int i = 0; i < this.mChannel.length; ++i) {
                    this.setPreEqByChannelIndex(i, eq);
                }
                return this;
            }

            public Builder setPreEqByChannelIndex(int n, Eq eq) {
                this.checkChannel(n);
                this.mChannel[n].setPreEq(eq);
                return this;
            }

            public Builder setPreferredFrameDuration(float f) {
                if (!(f < 0.0f)) {
                    this.mPreferredFrameDuration = f;
                    return this;
                }
                throw new IllegalArgumentException("Expected positive frameDuration");
            }
        }

    }

    public static final class Eq
    extends BandStage {
        private final EqBand[] mBands;

        public Eq(Eq eq) {
            super(eq.isInUse(), eq.isEnabled(), eq.getBandCount());
            if (this.isInUse()) {
                EqBand[] arreqBand;
                this.mBands = new EqBand[eq.mBands.length];
                for (int i = 0; i < (arreqBand = this.mBands).length; ++i) {
                    arreqBand[i] = new EqBand(eq.mBands[i]);
                }
            } else {
                this.mBands = null;
            }
        }

        public Eq(boolean bl, boolean bl2, int n) {
            super(bl, bl2, n);
            if (this.isInUse()) {
                this.mBands = new EqBand[n];
                for (int i = 0; i < n; ++i) {
                    float f = 20000.0f;
                    if (n > 1) {
                        f = (float)Math.pow(10.0, mMinFreqLog + (float)i * (mMaxFreqLog - mMinFreqLog) / (float)(n - 1));
                    }
                    this.mBands[i] = new EqBand(true, f, 0.0f);
                }
            } else {
                this.mBands = null;
            }
        }

        private void checkBand(int n) {
            Object object = this.mBands;
            if (object != null && n >= 0 && n < ((EqBand[])object).length) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("band index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" out of bounds");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public EqBand getBand(int n) {
            this.checkBand(n);
            return this.mBands[n];
        }

        public void setBand(int n, EqBand eqBand) {
            this.checkBand(n);
            this.mBands[n] = new EqBand(eqBand);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            if (this.isInUse()) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("--->EqBands: ");
                stringBuilder2.append(this.mBands.length);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
                for (int i = 0; i < this.mBands.length; ++i) {
                    stringBuilder.append(String.format("  Band %d\n", i));
                    stringBuilder.append(this.mBands[i].toString());
                }
            }
            return stringBuilder.toString();
        }
    }

    public static final class EqBand
    extends BandBase {
        private float mGain;

        public EqBand(EqBand eqBand) {
            super(eqBand.isEnabled(), eqBand.getCutoffFrequency());
            this.mGain = eqBand.mGain;
        }

        public EqBand(boolean bl, float f, float f2) {
            super(bl, f);
            this.mGain = f2;
        }

        public float getGain() {
            return this.mGain;
        }

        public void setGain(float f) {
            this.mGain = f;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append(String.format(" Gain: %f\n", Float.valueOf(this.mGain)));
            return stringBuilder.toString();
        }
    }

    public static final class Limiter
    extends Stage {
        private float mAttackTime;
        private int mLinkGroup;
        private float mPostGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public Limiter(Limiter limiter) {
            super(limiter.isInUse(), limiter.isEnabled());
            this.mLinkGroup = limiter.mLinkGroup;
            this.mAttackTime = limiter.mAttackTime;
            this.mReleaseTime = limiter.mReleaseTime;
            this.mRatio = limiter.mRatio;
            this.mThreshold = limiter.mThreshold;
            this.mPostGain = limiter.mPostGain;
        }

        public Limiter(boolean bl, boolean bl2, int n, float f, float f2, float f3, float f4, float f5) {
            super(bl, bl2);
            this.mLinkGroup = n;
            this.mAttackTime = f;
            this.mReleaseTime = f2;
            this.mRatio = f3;
            this.mThreshold = f4;
            this.mPostGain = f5;
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public int getLinkGroup() {
            return this.mLinkGroup;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public void setLinkGroup(int n) {
            this.mLinkGroup = n;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            if (this.isInUse()) {
                stringBuilder.append(String.format(" LinkGroup: %d (group)\n", this.mLinkGroup));
                stringBuilder.append(String.format(" AttackTime: %f (ms)\n", Float.valueOf(this.mAttackTime)));
                stringBuilder.append(String.format(" ReleaseTime: %f (ms)\n", Float.valueOf(this.mReleaseTime)));
                stringBuilder.append(String.format(" Ratio: 1:%f\n", Float.valueOf(this.mRatio)));
                stringBuilder.append(String.format(" Threshold: %f (dB)\n", Float.valueOf(this.mThreshold)));
                stringBuilder.append(String.format(" PostGain: %f (dB)\n", Float.valueOf(this.mPostGain)));
            }
            return stringBuilder.toString();
        }
    }

    public static final class Mbc
    extends BandStage {
        private final MbcBand[] mBands;

        public Mbc(Mbc mbc) {
            super(mbc.isInUse(), mbc.isEnabled(), mbc.getBandCount());
            if (this.isInUse()) {
                MbcBand[] arrmbcBand;
                this.mBands = new MbcBand[mbc.mBands.length];
                for (int i = 0; i < (arrmbcBand = this.mBands).length; ++i) {
                    arrmbcBand[i] = new MbcBand(mbc.mBands[i]);
                }
            } else {
                this.mBands = null;
            }
        }

        public Mbc(boolean bl, boolean bl2, int n) {
            super(bl, bl2, n);
            if (this.isInUse()) {
                this.mBands = new MbcBand[n];
                for (int i = 0; i < n; ++i) {
                    float f = 20000.0f;
                    if (n > 1) {
                        f = (float)Math.pow(10.0, mMinFreqLog + (float)i * (mMaxFreqLog - mMinFreqLog) / (float)(n - 1));
                    }
                    this.mBands[i] = new MbcBand(true, f, 3.0f, 80.0f, 1.0f, -45.0f, 0.0f, -90.0f, 1.0f, 0.0f, 0.0f);
                }
            } else {
                this.mBands = null;
            }
        }

        private void checkBand(int n) {
            Object object = this.mBands;
            if (object != null && n >= 0 && n < ((MbcBand[])object).length) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("band index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" out of bounds");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public MbcBand getBand(int n) {
            this.checkBand(n);
            return this.mBands[n];
        }

        public void setBand(int n, MbcBand mbcBand) {
            this.checkBand(n);
            this.mBands[n] = new MbcBand(mbcBand);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            if (this.isInUse()) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("--->MbcBands: ");
                stringBuilder2.append(this.mBands.length);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
                for (int i = 0; i < this.mBands.length; ++i) {
                    stringBuilder.append(String.format("  Band %d\n", i));
                    stringBuilder.append(this.mBands[i].toString());
                }
            }
            return stringBuilder.toString();
        }
    }

    public static final class MbcBand
    extends BandBase {
        private float mAttackTime;
        private float mExpanderRatio;
        private float mKneeWidth;
        private float mNoiseGateThreshold;
        private float mPostGain;
        private float mPreGain;
        private float mRatio;
        private float mReleaseTime;
        private float mThreshold;

        public MbcBand(MbcBand mbcBand) {
            super(mbcBand.isEnabled(), mbcBand.getCutoffFrequency());
            this.mAttackTime = mbcBand.mAttackTime;
            this.mReleaseTime = mbcBand.mReleaseTime;
            this.mRatio = mbcBand.mRatio;
            this.mThreshold = mbcBand.mThreshold;
            this.mKneeWidth = mbcBand.mKneeWidth;
            this.mNoiseGateThreshold = mbcBand.mNoiseGateThreshold;
            this.mExpanderRatio = mbcBand.mExpanderRatio;
            this.mPreGain = mbcBand.mPreGain;
            this.mPostGain = mbcBand.mPostGain;
        }

        public MbcBand(boolean bl, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            super(bl, f);
            this.mAttackTime = f2;
            this.mReleaseTime = f3;
            this.mRatio = f4;
            this.mThreshold = f5;
            this.mKneeWidth = f6;
            this.mNoiseGateThreshold = f7;
            this.mExpanderRatio = f8;
            this.mPreGain = f9;
            this.mPostGain = f10;
        }

        public float getAttackTime() {
            return this.mAttackTime;
        }

        public float getExpanderRatio() {
            return this.mExpanderRatio;
        }

        public float getKneeWidth() {
            return this.mKneeWidth;
        }

        public float getNoiseGateThreshold() {
            return this.mNoiseGateThreshold;
        }

        public float getPostGain() {
            return this.mPostGain;
        }

        public float getPreGain() {
            return this.mPreGain;
        }

        public float getRatio() {
            return this.mRatio;
        }

        public float getReleaseTime() {
            return this.mReleaseTime;
        }

        public float getThreshold() {
            return this.mThreshold;
        }

        public void setAttackTime(float f) {
            this.mAttackTime = f;
        }

        public void setExpanderRatio(float f) {
            this.mExpanderRatio = f;
        }

        public void setKneeWidth(float f) {
            this.mKneeWidth = f;
        }

        public void setNoiseGateThreshold(float f) {
            this.mNoiseGateThreshold = f;
        }

        public void setPostGain(float f) {
            this.mPostGain = f;
        }

        public void setPreGain(float f) {
            this.mPreGain = f;
        }

        public void setRatio(float f) {
            this.mRatio = f;
        }

        public void setReleaseTime(float f) {
            this.mReleaseTime = f;
        }

        public void setThreshold(float f) {
            this.mThreshold = f;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append(String.format(" AttackTime: %f (ms)\n", Float.valueOf(this.mAttackTime)));
            stringBuilder.append(String.format(" ReleaseTime: %f (ms)\n", Float.valueOf(this.mReleaseTime)));
            stringBuilder.append(String.format(" Ratio: 1:%f\n", Float.valueOf(this.mRatio)));
            stringBuilder.append(String.format(" Threshold: %f (dB)\n", Float.valueOf(this.mThreshold)));
            stringBuilder.append(String.format(" NoiseGateThreshold: %f(dB)\n", Float.valueOf(this.mNoiseGateThreshold)));
            stringBuilder.append(String.format(" ExpanderRatio: %f:1\n", Float.valueOf(this.mExpanderRatio)));
            stringBuilder.append(String.format(" PreGain: %f (dB)\n", Float.valueOf(this.mPreGain)));
            stringBuilder.append(String.format(" PostGain: %f (dB)\n", Float.valueOf(this.mPostGain)));
            return stringBuilder.toString();
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(DynamicsProcessing var1, int var2, int var3);
    }

    public static class Settings {
        public int channelCount;
        public float[] inputGain;

        public Settings() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Settings(String object) {
            Object object2 = new StringTokenizer((String)object, "=;");
            if (((StringTokenizer)object2).countTokens() == 3) {
                Object object3 = ((StringTokenizer)object2).nextToken();
                if (((String)object3).equals(DynamicsProcessing.TAG)) {
                    CharSequence charSequence;
                    block46 : {
                        block47 : {
                            block48 : {
                                charSequence = ((StringTokenizer)object2).nextToken();
                                object3 = charSequence;
                                boolean bl = ((String)charSequence).equals("channelCount");
                                if (!bl) break block46;
                                object3 = charSequence;
                                this.channelCount = Short.parseShort(((StringTokenizer)object2).nextToken());
                                object3 = charSequence;
                                if (this.channelCount > 32) break block47;
                                object3 = charSequence;
                                if (((StringTokenizer)object2).countTokens() != this.channelCount * 1) break block48;
                                object3 = charSequence;
                                this.inputGain = new float[this.channelCount];
                                int n = 0;
                                object = charSequence;
                                do {
                                    object3 = object;
                                    if (n >= this.channelCount) return;
                                    object3 = object;
                                    object3 = object = ((StringTokenizer)object2).nextToken();
                                    object3 = object;
                                    object3 = object;
                                    ((StringBuilder)charSequence).append(n);
                                    object3 = object;
                                    ((StringBuilder)charSequence).append("_inputGain");
                                    object3 = object;
                                    if (!((String)object).equals(((StringBuilder)charSequence).toString())) break;
                                    object3 = object;
                                    this.inputGain[n] = Float.parseFloat(((StringTokenizer)object2).nextToken());
                                    ++n;
                                    continue;
                                    break;
                                } while (true);
                                object3 = object;
                                object3 = object;
                                object3 = object;
                                try {
                                    super();
                                    object3 = object;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("invalid value for key: ");
                                    stringBuilder.append((String)object3);
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                ((StringBuilder)charSequence).append("invalid key name: ");
                                object3 = object;
                                ((StringBuilder)charSequence).append((String)object);
                                object3 = object;
                                object2 = new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                object3 = object;
                                throw object2;
                            }
                            object3 = charSequence;
                            object3 = charSequence;
                            object3 = charSequence;
                            StringBuilder stringBuilder = new StringBuilder();
                            object3 = charSequence;
                            stringBuilder.append("settings: ");
                            object3 = charSequence;
                            stringBuilder.append((String)object);
                            object3 = charSequence;
                            object2 = new IllegalArgumentException(stringBuilder.toString());
                            object3 = charSequence;
                            throw object2;
                        }
                        object3 = charSequence;
                        object3 = charSequence;
                        object3 = charSequence;
                        object2 = new StringBuilder();
                        object3 = charSequence;
                        ((StringBuilder)object2).append("too many channels Settings:");
                        object3 = charSequence;
                        ((StringBuilder)object2).append((String)object);
                        object3 = charSequence;
                        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object2).toString());
                        object3 = charSequence;
                        throw illegalArgumentException;
                    }
                    object3 = charSequence;
                    object3 = charSequence;
                    object3 = charSequence;
                    object2 = new StringBuilder();
                    object3 = charSequence;
                    ((StringBuilder)object2).append("invalid key name: ");
                    object3 = charSequence;
                    ((StringBuilder)object2).append((String)charSequence);
                    object3 = charSequence;
                    object = new IllegalArgumentException(((StringBuilder)object2).toString());
                    object3 = charSequence;
                    throw object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("invalid settings for DynamicsProcessing: ");
                ((StringBuilder)object).append((String)object3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("settings: ");
            stringBuilder.append((String)object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("DynamicsProcessing;channelCount=");
            ((StringBuilder)charSequence).append(Integer.toString(this.channelCount));
            charSequence = new String(((StringBuilder)charSequence).toString());
            for (int i = 0; i < this.channelCount; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(";");
                stringBuilder.append(i);
                stringBuilder.append("_inputGain=");
                stringBuilder.append(Float.toString(this.inputGain[i]));
                charSequence = ((String)charSequence).concat(stringBuilder.toString());
            }
            return charSequence;
        }
    }

    public static class Stage {
        private boolean mEnabled;
        private boolean mInUse;

        public Stage(boolean bl, boolean bl2) {
            this.mInUse = bl;
            this.mEnabled = bl2;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isInUse() {
            return this.mInUse;
        }

        public void setEnabled(boolean bl) {
            this.mEnabled = bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format(" Stage InUse: %b\n", this.isInUse()));
            if (this.isInUse()) {
                stringBuilder.append(String.format(" Stage Enabled: %b\n", this.mEnabled));
            }
            return stringBuilder.toString();
        }
    }

}

