/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import java.util.StringTokenizer;
import java.util.UUID;

public class PresetReverb
extends AudioEffect {
    public static final int PARAM_PRESET = 0;
    public static final short PRESET_LARGEHALL = 5;
    public static final short PRESET_LARGEROOM = 3;
    public static final short PRESET_MEDIUMHALL = 4;
    public static final short PRESET_MEDIUMROOM = 2;
    public static final short PRESET_NONE = 0;
    public static final short PRESET_PLATE = 6;
    public static final short PRESET_SMALLROOM = 1;
    private static final String TAG = "PresetReverb";
    private BaseParameterListener mBaseParamListener = null;
    private OnParameterChangeListener mParamListener = null;
    private final Object mParamListenerLock = new Object();

    public PresetReverb(int n, int n2) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_PRESET_REVERB, EFFECT_TYPE_NULL, n, n2);
    }

    public short getPreset() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(0, arrs));
        return arrs[0];
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        Settings settings = new Settings();
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(0, arrs));
        settings.preset = arrs[0];
        return settings;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setParameterListener(OnParameterChangeListener object) {
        Object object2 = this.mParamListenerLock;
        synchronized (object2) {
            if (this.mParamListener == null) {
                this.mParamListener = object;
                this.mBaseParamListener = object = new BaseParameterListener();
                super.setParameterListener(this.mBaseParamListener);
            }
            return;
        }
    }

    public void setPreset(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(0, s));
    }

    public void setProperties(Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(0, settings.preset));
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
            object = null;
            Object object2 = PresetReverb.this.mParamListenerLock;
            // MONITORENTER : object2
            if (PresetReverb.this.mParamListener != null) {
                object = PresetReverb.this.mParamListener;
            }
            // MONITOREXIT : object2
            if (object == null) return;
            int n2 = -1;
            short s = -1;
            if (arrby.length == 4) {
                n2 = AudioEffect.byteArrayToInt(arrby, 0);
            }
            short s2 = s;
            if (arrby2.length == 2) {
                s2 = s = (short)AudioEffect.byteArrayToShort(arrby2, 0);
            }
            if (n2 == -1) return;
            if (s2 == -1) return;
            object.onParameterChange(PresetReverb.this, n, n2, s2);
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(PresetReverb var1, int var2, int var3, short var4);
    }

    public static class Settings {
        public short preset;

        public Settings() {
        }

        public Settings(String string2) {
            Object object = new StringTokenizer(string2, "=;");
            ((StringTokenizer)object).countTokens();
            if (((StringTokenizer)object).countTokens() == 3) {
                string2 = ((StringTokenizer)object).nextToken();
                if (string2.equals(PresetReverb.TAG)) {
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
                        if (!string3.equals("preset")) break block13;
                        string2 = string3;
                        this.preset = Short.parseShort(((StringTokenizer)object).nextToken());
                        return;
                    }
                    string2 = string3;
                    string2 = string3;
                    string2 = string3;
                    StringBuilder stringBuilder = new StringBuilder();
                    string2 = string3;
                    stringBuilder.append("invalid key name: ");
                    string2 = string3;
                    stringBuilder.append(string3);
                    string2 = string3;
                    object = new IllegalArgumentException(stringBuilder.toString());
                    string2 = string3;
                    throw object;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid settings for PresetReverb: ");
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
            stringBuilder.append("PresetReverb;preset=");
            stringBuilder.append(Short.toString(this.preset));
            return new String(stringBuilder.toString());
        }
    }

}

