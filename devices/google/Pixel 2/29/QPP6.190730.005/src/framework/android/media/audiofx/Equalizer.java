/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.UUID;

public class Equalizer
extends AudioEffect {
    public static final int PARAM_BAND_FREQ_RANGE = 4;
    public static final int PARAM_BAND_LEVEL = 2;
    public static final int PARAM_CENTER_FREQ = 3;
    public static final int PARAM_CURRENT_PRESET = 6;
    public static final int PARAM_GET_BAND = 5;
    public static final int PARAM_GET_NUM_OF_PRESETS = 7;
    public static final int PARAM_GET_PRESET_NAME = 8;
    public static final int PARAM_LEVEL_RANGE = 1;
    public static final int PARAM_NUM_BANDS = 0;
    private static final int PARAM_PROPERTIES = 9;
    public static final int PARAM_STRING_SIZE_MAX = 32;
    private static final String TAG = "Equalizer";
    private BaseParameterListener mBaseParamListener = null;
    private short mNumBands = (short)(false ? 1 : 0);
    private int mNumPresets;
    private OnParameterChangeListener mParamListener = null;
    private final Object mParamListenerLock = new Object();
    private String[] mPresetNames;

    public Equalizer(int n, int n2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_EQUALIZER, EFFECT_TYPE_NULL, n, n2);
        if (n2 == 0) {
            Log.w(TAG, "WARNING: attaching an Equalizer to global output mix is deprecated!");
        }
        this.getNumberOfBands();
        n = this.mNumPresets = (int)this.getNumberOfPresets();
        if (n != 0) {
            this.mPresetNames = new String[n];
            byte[] arrby = new byte[32];
            int[] arrn = new int[2];
            arrn[0] = 8;
            for (n = 0; n < this.mNumPresets; ++n) {
                arrn[1] = n;
                this.checkStatus(this.getParameter(arrn, arrby));
                n2 = 0;
                while (arrby[n2] != 0) {
                    ++n2;
                }
                try {
                    String string2;
                    String[] arrstring = this.mPresetNames;
                    arrstring[n] = string2 = new String(arrby, 0, n2, "ISO-8859-1");
                    continue;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    Log.e(TAG, "preset name decode error");
                }
            }
        }
    }

    public short getBand(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(new int[]{5, n}, arrs));
        return arrs[0];
    }

    public int[] getBandFreqRange(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        int[] arrn = new int[2];
        this.checkStatus(this.getParameter(new int[]{4, s}, arrn));
        return arrn;
    }

    public short getBandLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(new int[]{2, s}, arrs));
        return arrs[0];
    }

    public short[] getBandLevelRange() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[2];
        this.checkStatus(this.getParameter(1, arrs));
        return arrs;
    }

    public int getCenterFreq(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(new int[]{3, s}, arrn));
        return arrn[0];
    }

    public short getCurrentPreset() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(6, arrs));
        return arrs[0];
    }

    public short getNumberOfBands() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short s = this.mNumBands;
        if (s != 0) {
            return s;
        }
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(new int[]{0}, arrs));
        this.mNumBands = arrs[0];
        return this.mNumBands;
    }

    public short getNumberOfPresets() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        short[] arrs = new short[1];
        this.checkStatus(this.getParameter(7, arrs));
        return arrs[0];
    }

    public String getPresetName(short s) {
        if (s >= 0 && s < this.mNumPresets) {
            return this.mPresetNames[s];
        }
        return "";
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[this.mNumBands * 2 + 4];
        this.checkStatus(this.getParameter(9, arrby));
        Settings settings = new Settings();
        settings.curPreset = Equalizer.byteArrayToShort(arrby, 0);
        settings.numBands = Equalizer.byteArrayToShort(arrby, 2);
        settings.bandLevels = new short[this.mNumBands];
        for (int i = 0; i < this.mNumBands; ++i) {
            settings.bandLevels[i] = Equalizer.byteArrayToShort(arrby, i * 2 + 4);
        }
        return settings;
    }

    public void setBandLevel(short s, short s2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(new int[]{2, s}, new short[]{s2}));
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
        if (settings.numBands == settings.bandLevels.length && settings.numBands == this.mNumBands) {
            byte[] arrby = Equalizer.concatArrays(Equalizer.shortToByteArray(settings.curPreset), Equalizer.shortToByteArray(this.mNumBands));
            for (int i = 0; i < this.mNumBands; ++i) {
                arrby = Equalizer.concatArrays(arrby, Equalizer.shortToByteArray(settings.bandLevels[i]));
            }
            this.checkStatus(this.setParameter(9, arrby));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("settings invalid band count: ");
        stringBuilder.append(settings.numBands);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void usePreset(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(6, s));
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
            int n2;
            int n3;
            object = null;
            Object object2 = Equalizer.this.mParamListenerLock;
            // MONITORENTER : object2
            if (Equalizer.this.mParamListener != null) {
                object = Equalizer.this.mParamListener;
            }
            // MONITOREXIT : object2
            if (object == null) return;
            int n4 = -1;
            int n5 = n3 = -1;
            if (arrby.length >= 4) {
                n4 = n2 = AudioEffect.byteArrayToInt(arrby, 0);
                n5 = n3;
                if (arrby.length >= 8) {
                    n5 = AudioEffect.byteArrayToInt(arrby, 4);
                    n4 = n2;
                }
            }
            n2 = arrby2.length == 2 ? (int)AudioEffect.byteArrayToShort(arrby2, 0) : (arrby2.length == 4 ? AudioEffect.byteArrayToInt(arrby2, 0) : -1);
            if (n4 == -1) return;
            if (n2 == -1) return;
            object.onParameterChange(Equalizer.this, n, n4, n5, n2);
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(Equalizer var1, int var2, int var3, int var4, int var5);
    }

    public static class Settings {
        public short[] bandLevels;
        public short curPreset;
        public short numBands;

        public Settings() {
            this.numBands = (short)(false ? 1 : 0);
            this.bandLevels = null;
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Settings(String charSequence) {
            int n = 0;
            this.numBands = (short)(false ? 1 : 0);
            this.bandLevels = null;
            Object object = new StringTokenizer((String)charSequence, "=;");
            ((StringTokenizer)object).countTokens();
            if (((StringTokenizer)object).countTokens() >= 5) {
                String string2 = ((StringTokenizer)object).nextToken();
                if (string2.equals(Equalizer.TAG)) {
                    CharSequence charSequence2;
                    block49 : {
                        block50 : {
                            block51 : {
                                charSequence2 = ((StringTokenizer)object).nextToken();
                                String string3 = charSequence2;
                                boolean bl = ((String)charSequence2).equals("curPreset");
                                if (!bl) break block49;
                                String string4 = charSequence2;
                                this.curPreset = Short.parseShort(((StringTokenizer)object).nextToken());
                                String string5 = charSequence2;
                                charSequence2 = ((StringTokenizer)object).nextToken();
                                String string6 = charSequence2;
                                if (!((String)charSequence2).equals("numBands")) break block50;
                                String string7 = charSequence2;
                                this.numBands = Short.parseShort(((StringTokenizer)object).nextToken());
                                String string8 = charSequence2;
                                if (((StringTokenizer)object).countTokens() != this.numBands * 2) break block51;
                                String string9 = charSequence2;
                                this.bandLevels = new short[this.numBands];
                                charSequence = charSequence2;
                                do {
                                    CharSequence charSequence3 = charSequence;
                                    if (n >= this.numBands) return;
                                    CharSequence charSequence4 = charSequence;
                                    CharSequence charSequence5 = charSequence = ((StringTokenizer)object).nextToken();
                                    CharSequence charSequence6 = charSequence;
                                    CharSequence charSequence7 = charSequence;
                                    ((StringBuilder)charSequence2).append("band");
                                    CharSequence charSequence8 = charSequence;
                                    ((StringBuilder)charSequence2).append(n + 1);
                                    CharSequence charSequence9 = charSequence;
                                    ((StringBuilder)charSequence2).append("Level");
                                    CharSequence charSequence10 = charSequence;
                                    if (!((String)charSequence).equals(((StringBuilder)charSequence2).toString())) break;
                                    CharSequence charSequence11 = charSequence;
                                    this.bandLevels[n] = Short.parseShort(((StringTokenizer)object).nextToken());
                                    ++n;
                                    continue;
                                    break;
                                } while (true);
                                CharSequence charSequence12 = charSequence;
                                CharSequence charSequence13 = charSequence;
                                CharSequence charSequence14 = charSequence;
                                try {
                                    super();
                                    CharSequence charSequence15 = charSequence;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    void var4_52;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("invalid value for key: ");
                                    stringBuilder.append((String)var4_52);
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                ((StringBuilder)charSequence2).append("invalid key name: ");
                                CharSequence charSequence16 = charSequence;
                                ((StringBuilder)charSequence2).append((String)charSequence);
                                CharSequence charSequence17 = charSequence;
                                object = new IllegalArgumentException(((StringBuilder)charSequence2).toString());
                                CharSequence charSequence18 = charSequence;
                                throw object;
                            }
                            String string10 = charSequence2;
                            String string11 = charSequence2;
                            String string12 = charSequence2;
                            StringBuilder stringBuilder = new StringBuilder();
                            String string13 = charSequence2;
                            stringBuilder.append("settings: ");
                            String string14 = charSequence2;
                            stringBuilder.append((String)charSequence);
                            CharSequence charSequence19 = charSequence2;
                            object = new IllegalArgumentException(stringBuilder.toString());
                            CharSequence charSequence20 = charSequence2;
                            throw object;
                        }
                        String string15 = charSequence2;
                        String string16 = charSequence2;
                        String string17 = charSequence2;
                        super();
                        String string18 = charSequence2;
                        ((StringBuilder)charSequence).append("invalid key name: ");
                        String string19 = charSequence2;
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        String string20 = charSequence2;
                        object = new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        String string21 = charSequence2;
                        throw object;
                    }
                    String string22 = charSequence2;
                    String string23 = charSequence2;
                    String string24 = charSequence2;
                    super();
                    String string25 = charSequence2;
                    ((StringBuilder)charSequence).append("invalid key name: ");
                    String string26 = charSequence2;
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    String string27 = charSequence2;
                    object = new IllegalArgumentException(((StringBuilder)charSequence).toString());
                    String string28 = charSequence2;
                    throw object;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("invalid settings for Equalizer: ");
                ((StringBuilder)charSequence).append(string2);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("settings: ");
            stringBuilder.append((String)charSequence);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Equalizer;curPreset=");
            ((StringBuilder)charSequence).append(Short.toString(this.curPreset));
            ((StringBuilder)charSequence).append(";numBands=");
            ((StringBuilder)charSequence).append(Short.toString(this.numBands));
            charSequence = new String(((StringBuilder)charSequence).toString());
            for (int i = 0; i < this.numBands; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(";band");
                stringBuilder.append(i + 1);
                stringBuilder.append("Level=");
                stringBuilder.append(Short.toString(this.bandLevels[i]));
                charSequence = ((String)charSequence).concat(stringBuilder.toString());
            }
            return charSequence;
        }
    }

}

