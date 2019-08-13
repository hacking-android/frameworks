/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.StringTokenizer;
import java.util.UUID;

public class LoudnessEnhancer
extends AudioEffect {
    public static final int PARAM_TARGET_GAIN_MB = 0;
    private static final String TAG = "LoudnessEnhancer";
    private BaseParameterListener mBaseParamListener = null;
    private OnParameterChangeListener mParamListener = null;
    private final Object mParamListenerLock = new Object();

    public LoudnessEnhancer(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, 0, n);
        if (n == 0) {
            Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public LoudnessEnhancer(int n, int n2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_LOUDNESS_ENHANCER, EFFECT_TYPE_NULL, n, n2);
        if (n2 == 0) {
            Log.w(TAG, "WARNING: attaching a LoudnessEnhancer to global output mix is deprecated!");
        }
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        Settings settings = new Settings();
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(0, arrn));
        settings.targetGainmB = arrn[0];
        return settings;
    }

    public float getTargetGain() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(0, arrn));
        return arrn[0];
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

    public void setProperties(Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(0, settings.targetGainmB));
    }

    public void setTargetGain(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(0, n));
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
            Object object2 = LoudnessEnhancer.this.mParamListenerLock;
            // MONITORENTER : object2
            if (LoudnessEnhancer.this.mParamListener != null) {
                object = LoudnessEnhancer.this.mParamListener;
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
            object.onParameterChange(LoudnessEnhancer.this, n, n2);
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(LoudnessEnhancer var1, int var2, int var3);
    }

    public static class Settings {
        public int targetGainmB;

        public Settings() {
        }

        public Settings(String string2) {
            Object object = new StringTokenizer(string2, "=;");
            if (((StringTokenizer)object).countTokens() == 3) {
                string2 = ((StringTokenizer)object).nextToken();
                if (string2.equals(LoudnessEnhancer.TAG)) {
                    String string3;
                    block13 : {
                        try {
                            string2 = string3 = ((StringTokenizer)object).nextToken();
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("invalid value for key: ");
                            stringBuilder.append(string2);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        if (!string3.equals("targetGainmB")) break block13;
                        string2 = string3;
                        this.targetGainmB = Integer.parseInt(((StringTokenizer)object).nextToken());
                        return;
                    }
                    string2 = string3;
                    string2 = string3;
                    string2 = string3;
                    object = new StringBuilder();
                    string2 = string3;
                    ((StringBuilder)object).append("invalid key name: ");
                    string2 = string3;
                    ((StringBuilder)object).append(string3);
                    string2 = string3;
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                    string2 = string3;
                    throw illegalArgumentException;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid settings for LoudnessEnhancer: ");
                stringBuilder.append(string2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("settings: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("LoudnessEnhancer;targetGainmB=");
            stringBuilder.append(Integer.toString(this.targetGainmB));
            return new String(stringBuilder.toString());
        }
    }

}

