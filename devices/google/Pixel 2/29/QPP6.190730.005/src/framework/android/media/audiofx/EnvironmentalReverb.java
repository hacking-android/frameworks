/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.audiofx.AudioEffect;
import java.util.StringTokenizer;
import java.util.UUID;

public class EnvironmentalReverb
extends AudioEffect {
    public static final int PARAM_DECAY_HF_RATIO = 3;
    public static final int PARAM_DECAY_TIME = 2;
    public static final int PARAM_DENSITY = 9;
    public static final int PARAM_DIFFUSION = 8;
    private static final int PARAM_PROPERTIES = 10;
    public static final int PARAM_REFLECTIONS_DELAY = 5;
    public static final int PARAM_REFLECTIONS_LEVEL = 4;
    public static final int PARAM_REVERB_DELAY = 7;
    public static final int PARAM_REVERB_LEVEL = 6;
    public static final int PARAM_ROOM_HF_LEVEL = 1;
    public static final int PARAM_ROOM_LEVEL = 0;
    private static int PROPERTY_SIZE = 0;
    private static final String TAG = "EnvironmentalReverb";
    private BaseParameterListener mBaseParamListener = null;
    private OnParameterChangeListener mParamListener = null;
    private final Object mParamListenerLock = new Object();

    static {
        PROPERTY_SIZE = 26;
    }

    public EnvironmentalReverb(int n, int n2) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_ENV_REVERB, EFFECT_TYPE_NULL, n, n2);
    }

    public short getDecayHFRatio() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(3, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public int getDecayTime() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[4];
        this.checkStatus(this.getParameter(2, arrby));
        return EnvironmentalReverb.byteArrayToInt(arrby);
    }

    public short getDensity() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(9, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public short getDiffusion() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(8, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public Settings getProperties() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[PROPERTY_SIZE];
        this.checkStatus(this.getParameter(10, arrby));
        Settings settings = new Settings();
        settings.roomLevel = EnvironmentalReverb.byteArrayToShort(arrby, 0);
        settings.roomHFLevel = EnvironmentalReverb.byteArrayToShort(arrby, 2);
        settings.decayTime = EnvironmentalReverb.byteArrayToInt(arrby, 4);
        settings.decayHFRatio = EnvironmentalReverb.byteArrayToShort(arrby, 8);
        settings.reflectionsLevel = EnvironmentalReverb.byteArrayToShort(arrby, 10);
        settings.reflectionsDelay = EnvironmentalReverb.byteArrayToInt(arrby, 12);
        settings.reverbLevel = EnvironmentalReverb.byteArrayToShort(arrby, 16);
        settings.reverbDelay = EnvironmentalReverb.byteArrayToInt(arrby, 18);
        settings.diffusion = EnvironmentalReverb.byteArrayToShort(arrby, 22);
        settings.density = EnvironmentalReverb.byteArrayToShort(arrby, 24);
        return settings;
    }

    public int getReflectionsDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[4];
        this.checkStatus(this.getParameter(5, arrby));
        return EnvironmentalReverb.byteArrayToInt(arrby);
    }

    public short getReflectionsLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(4, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public int getReverbDelay() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[4];
        this.checkStatus(this.getParameter(7, arrby));
        return EnvironmentalReverb.byteArrayToInt(arrby);
    }

    public short getReverbLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(6, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public short getRoomHFLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(1, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public short getRoomLevel() throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        byte[] arrby = new byte[2];
        this.checkStatus(this.getParameter(0, arrby));
        return EnvironmentalReverb.byteArrayToShort(arrby);
    }

    public void setDecayHFRatio(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(3, EnvironmentalReverb.shortToByteArray(s)));
    }

    public void setDecayTime(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(2, EnvironmentalReverb.intToByteArray(n)));
    }

    public void setDensity(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(9, EnvironmentalReverb.shortToByteArray(s)));
    }

    public void setDiffusion(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(8, EnvironmentalReverb.shortToByteArray(s)));
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
        this.checkStatus(this.setParameter(10, EnvironmentalReverb.concatArrays(EnvironmentalReverb.shortToByteArray(settings.roomLevel), EnvironmentalReverb.shortToByteArray(settings.roomHFLevel), EnvironmentalReverb.intToByteArray(settings.decayTime), EnvironmentalReverb.shortToByteArray(settings.decayHFRatio), EnvironmentalReverb.shortToByteArray(settings.reflectionsLevel), EnvironmentalReverb.intToByteArray(settings.reflectionsDelay), EnvironmentalReverb.shortToByteArray(settings.reverbLevel), EnvironmentalReverb.intToByteArray(settings.reverbDelay), EnvironmentalReverb.shortToByteArray(settings.diffusion), EnvironmentalReverb.shortToByteArray(settings.density))));
    }

    public void setReflectionsDelay(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(5, EnvironmentalReverb.intToByteArray(n)));
    }

    public void setReflectionsLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(4, EnvironmentalReverb.shortToByteArray(s)));
    }

    public void setReverbDelay(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(7, EnvironmentalReverb.intToByteArray(n)));
    }

    public void setReverbLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(6, EnvironmentalReverb.shortToByteArray(s)));
    }

    public void setRoomHFLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(1, EnvironmentalReverb.shortToByteArray(s)));
    }

    public void setRoomLevel(short s) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        this.checkStatus(this.setParameter(0, EnvironmentalReverb.shortToByteArray(s)));
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
            Object object2 = EnvironmentalReverb.this.mParamListenerLock;
            // MONITORENTER : object2
            if (EnvironmentalReverb.this.mParamListener != null) {
                object = EnvironmentalReverb.this.mParamListener;
            }
            // MONITOREXIT : object2
            if (object == null) return;
            int n2 = -1;
            int n3 = -1;
            if (arrby.length == 4) {
                n2 = AudioEffect.byteArrayToInt(arrby, 0);
            }
            if (arrby2.length == 2) {
                n3 = AudioEffect.byteArrayToShort(arrby2, 0);
            } else if (arrby2.length == 4) {
                n3 = AudioEffect.byteArrayToInt(arrby2, 0);
            }
            if (n2 == -1) return;
            if (n3 == -1) return;
            object.onParameterChange(EnvironmentalReverb.this, n, n2, n3);
        }
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(EnvironmentalReverb var1, int var2, int var3, int var4);
    }

    public static class Settings {
        public short decayHFRatio;
        public int decayTime;
        public short density;
        public short diffusion;
        public int reflectionsDelay;
        public short reflectionsLevel;
        public int reverbDelay;
        public short reverbLevel;
        public short roomHFLevel;
        public short roomLevel;

        public Settings() {
        }

        public Settings(String string2) {
            Object object = new StringTokenizer(string2, "=;");
            ((StringTokenizer)object).countTokens();
            if (((StringTokenizer)object).countTokens() == 21) {
                string2 = ((StringTokenizer)object).nextToken();
                if (string2.equals(EnvironmentalReverb.TAG)) {
                    String string3;
                    block103 : {
                        block104 : {
                            block105 : {
                                block106 : {
                                    block107 : {
                                        block108 : {
                                            block109 : {
                                                block110 : {
                                                    block111 : {
                                                        block112 : {
                                                            try {
                                                                string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            }
                                                            catch (NumberFormatException numberFormatException) {
                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                stringBuilder.append("invalid value for key: ");
                                                                stringBuilder.append(string2);
                                                                throw new IllegalArgumentException(stringBuilder.toString());
                                                            }
                                                            boolean bl = string3.equals("roomLevel");
                                                            if (!bl) break block103;
                                                            string2 = string3;
                                                            this.roomLevel = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("roomHFLevel")) break block104;
                                                            string2 = string3;
                                                            this.roomHFLevel = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("decayTime")) break block105;
                                                            string2 = string3;
                                                            this.decayTime = Integer.parseInt(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("decayHFRatio")) break block106;
                                                            string2 = string3;
                                                            this.decayHFRatio = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("reflectionsLevel")) break block107;
                                                            string2 = string3;
                                                            this.reflectionsLevel = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("reflectionsDelay")) break block108;
                                                            string2 = string3;
                                                            this.reflectionsDelay = Integer.parseInt(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("reverbLevel")) break block109;
                                                            string2 = string3;
                                                            this.reverbLevel = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("reverbDelay")) break block110;
                                                            string2 = string3;
                                                            this.reverbDelay = Integer.parseInt(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("diffusion")) break block111;
                                                            string2 = string3;
                                                            this.diffusion = Short.parseShort(((StringTokenizer)object).nextToken());
                                                            string2 = string3;
                                                            string2 = string3 = ((StringTokenizer)object).nextToken();
                                                            if (!string3.equals("density")) break block112;
                                                            string2 = string3;
                                                            this.density = Short.parseShort(((StringTokenizer)object).nextToken());
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
                stringBuilder.append("invalid settings for EnvironmentalReverb: ");
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
            stringBuilder.append("EnvironmentalReverb;roomLevel=");
            stringBuilder.append(Short.toString(this.roomLevel));
            stringBuilder.append(";roomHFLevel=");
            stringBuilder.append(Short.toString(this.roomHFLevel));
            stringBuilder.append(";decayTime=");
            stringBuilder.append(Integer.toString(this.decayTime));
            stringBuilder.append(";decayHFRatio=");
            stringBuilder.append(Short.toString(this.decayHFRatio));
            stringBuilder.append(";reflectionsLevel=");
            stringBuilder.append(Short.toString(this.reflectionsLevel));
            stringBuilder.append(";reflectionsDelay=");
            stringBuilder.append(Integer.toString(this.reflectionsDelay));
            stringBuilder.append(";reverbLevel=");
            stringBuilder.append(Short.toString(this.reverbLevel));
            stringBuilder.append(";reverbDelay=");
            stringBuilder.append(Integer.toString(this.reverbDelay));
            stringBuilder.append(";diffusion=");
            stringBuilder.append(Short.toString(this.diffusion));
            stringBuilder.append(";density=");
            stringBuilder.append(Short.toString(this.density));
            return new String(stringBuilder.toString());
        }
    }

}

