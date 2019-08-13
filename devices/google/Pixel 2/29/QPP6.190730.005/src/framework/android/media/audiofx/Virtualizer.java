/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.UUID;

public class Virtualizer
extends AudioEffect {
    private static final boolean DEBUG = false;
    public static final int PARAM_FORCE_VIRTUALIZATION_MODE = 3;
    public static final int PARAM_STRENGTH = 1;
    public static final int PARAM_STRENGTH_SUPPORTED = 0;
    public static final int PARAM_VIRTUALIZATION_MODE = 4;
    public static final int PARAM_VIRTUAL_SPEAKER_ANGLES = 2;
    private static final String TAG = "Virtualizer";
    public static final int VIRTUALIZATION_MODE_AUTO = 1;
    public static final int VIRTUALIZATION_MODE_BINAURAL = 2;
    public static final int VIRTUALIZATION_MODE_OFF = 0;
    public static final int VIRTUALIZATION_MODE_TRANSAURAL = 3;
    private BaseParameterListener mBaseParamListener;
    private OnParameterChangeListener mParamListener;
    private final Object mParamListenerLock;
    private boolean mStrengthSupported;

    public Virtualizer(int n, int n2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_VIRTUALIZER, EFFECT_TYPE_NULL, n, n2);
        boolean bl = false;
        this.mStrengthSupported = false;
        this.mParamListener = null;
        this.mBaseParamListener = null;
        this.mParamListenerLock = new Object();
        if (n2 == 0) {
            Log.w(TAG, "WARNING: attaching a Virtualizer to global output mix is deprecated!");
        }
        int[] arrn = new int[1];
        this.checkStatus(this.getParameter(0, arrn));
        if (arrn[0] != 0) {
            bl = true;
        }
        this.mStrengthSupported = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int deviceToMode(int n) {
        if (n == 19) return 3;
        if (n == 22) return 2;
        switch (n) {
            default: {
                return 0;
            }
            case 1: 
            case 3: 
            case 4: 
            case 7: {
                return 2;
            }
            case 2: 
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
        }
        return 3;
    }

    private boolean getAnglesInt(int n, int n2, int[] object) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        if (n != 0) {
            if (n == 1) {
                n = 12;
            }
            int n3 = AudioFormat.channelCountFromOutChannelMask(n);
            if (object != null && ((int[])object).length < n3 * 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Size of array for angles cannot accomodate number of channels in mask (");
                stringBuilder.append(n3);
                stringBuilder.append(")");
                Log.e(TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Virtualizer: array for channel / angle pairs is too small: is ");
                stringBuilder.append(((int[])object).length);
                stringBuilder.append(", should be ");
                stringBuilder.append(n3 * 3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate(12);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.putInt(2);
            byteBuffer.putInt(AudioFormat.convertChannelOutMaskToNativeMask(n));
            byteBuffer.putInt(AudioDeviceInfo.convertDeviceTypeToInternalDevice(n2));
            Object object2 = new byte[n3 * 4 * 3];
            n = this.getParameter(byteBuffer.array(), (byte[])object2);
            if (n >= 0) {
                if (object != null) {
                    object2 = ByteBuffer.wrap((byte[])object2);
                    ((ByteBuffer)object2).order(ByteOrder.nativeOrder());
                    for (n = 0; n < n3; ++n) {
                        object[n * 3] = AudioFormat.convertNativeChannelMaskToOutMask(((ByteBuffer)object2).getInt(n * 4 * 3));
                        object[n * 3 + 1] = ((ByteBuffer)object2).getInt(n * 4 * 3 + 4);
                        object[n * 3 + 2] = ((ByteBuffer)object2).getInt(n * 4 * 3 + 8);
                    }
                }
                return true;
            }
            if (n == -4) {
                return false;
            }
            this.checkStatus(n);
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected status code ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" after getParameter(PARAM_VIRTUAL_SPEAKER_ANGLES)");
            Log.e(TAG, ((StringBuilder)object).toString());
            return false;
        }
        throw new IllegalArgumentException("Virtualizer: illegal CHANNEL_INVALID channel mask");
    }

    private static int getDeviceForModeForce(int n) throws IllegalArgumentException {
        if (n == 1) {
            return 0;
        }
        return Virtualizer.getDeviceForModeQuery(n);
    }

    private static int getDeviceForModeQuery(int n) throws IllegalArgumentException {
        if (n != 2) {
            if (n == 3) {
                return 2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Virtualizer: illegal virtualization mode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return 4;
    }

    public boolean canVirtualize(int n, int n2) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        return this.getAnglesInt(n, Virtualizer.getDeviceForModeQuery(n2), null);
    }

    public boolean forceVirtualizationMode(int n) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        if ((n = this.setParameter(3, AudioDeviceInfo.convertDeviceTypeToInternalDevice(Virtualizer.getDeviceForModeForce(n)))) >= 0) {
            return true;
        }
        if (n == -4) {
            return false;
        }
        this.checkStatus(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected status code ");
        stringBuilder.append(n);
        stringBuilder.append(" after setParameter(PARAM_FORCE_VIRTUALIZATION_MODE)");
        Log.e(TAG, stringBuilder.toString());
        return false;
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

    public boolean getSpeakerAngles(int n, int n2, int[] arrn) throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
        if (arrn != null) {
            return this.getAnglesInt(n, Virtualizer.getDeviceForModeQuery(n2), arrn);
        }
        throw new IllegalArgumentException("Virtualizer: illegal null channel / angle array");
    }

    public boolean getStrengthSupported() {
        return this.mStrengthSupported;
    }

    public int getVirtualizationMode() throws IllegalStateException, UnsupportedOperationException {
        Object object = new int[1];
        int n = this.getParameter(4, (int[])object);
        if (n >= 0) {
            return Virtualizer.deviceToMode(AudioDeviceInfo.convertInternalDeviceToDeviceType(object[0]));
        }
        if (n == -4) {
            return 0;
        }
        this.checkStatus(n);
        object = new StringBuilder();
        ((StringBuilder)object).append("unexpected status code ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" after getParameter(PARAM_VIRTUALIZATION_MODE)");
        Log.e(TAG, ((StringBuilder)object).toString());
        return 0;
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
            Object object2 = Virtualizer.this.mParamListenerLock;
            // MONITORENTER : object2
            if (Virtualizer.this.mParamListener != null) {
                object = Virtualizer.this.mParamListener;
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
            object.onParameterChange(Virtualizer.this, n, n2, s2);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ForceVirtualizationMode {
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(Virtualizer var1, int var2, int var3, short var4);
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
                if (string2.equals(Virtualizer.TAG)) {
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
                stringBuilder.append("invalid settings for Virtualizer: ");
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
            stringBuilder.append("Virtualizer;strength=");
            stringBuilder.append(Short.toString(this.strength));
            return new String(stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VirtualizationMode {
    }

}

