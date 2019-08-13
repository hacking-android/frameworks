/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.util.StringTokenizer;
import java.util.UUID;

public class BassBoost
extends AudioEffect {
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    private static final String TAG = "BassBoost";
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private boolean mStrengthSupported;

    public BassBoost(int n, int n2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_BASS_BOOST, EFFECT_TYPE_NULL, n, n2);
        boolean bl = false;
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (n2 == 0) {
            Log.w(TAG, "WARNING: attaching a BassBoost to global output mix is deprecated!");
        }
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(0, arrn));
        if (arrn[0] != 0) {
            bl = true;
        }
        this.mStrengthSupported = bl;
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        Settings settings = new Settings();
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(1, arrs));
        settings.strength = arrs[0];
        return settings;
    }

    public short getRoundedStrength() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(1, arrs));
        return arrs[0];
    }

    public boolean getStrengthSupported() {
        return this.mStrengthSupported;
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

    public void setProperties(Settings settings) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(1, settings.strength));
    }

    public void setStrength(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(1, s));
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
            Object object2 = BassBoost.this.mParamListenerLock;
            // MONITORENTER : object2
            if (BassBoost.this.mParamListener != null) {
                object = BassBoost.this.mParamListener;
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
            object.onParameterChange(BassBoost.this, n, n2, s2);
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(BassBoost var1, int var2, int var3, short var4);
    }

    public static class Settings {
        public short strength;

        public Settings() {
        }

        public Settings(String string2) {
            Object object = new StringTokenizer(string2, "=;");
            ((StringTokenizer)object).countTokens();
            if (((StringTokenizer)object).countTokens() == 3) {
                string2 = ((StringTokenizer)object).nextToken();
                if (string2.equals(BassBoost.TAG)) {
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
                        if (!string3.equals("strength")) break block13;
                        string2 = string3;
                        this.strength = Short.parseShort(((StringTokenizer)object).nextToken());
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
                stringBuilder.append("invalid settings for BassBoost: ");
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
            stringBuilder.append("BassBoost;strength=");
            stringBuilder.append(Short.toString(this.strength));
            return new String(stringBuilder.toString());
        }
    }

}

