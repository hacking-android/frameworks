/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioDevicePort;
import android.media.AudioFormat;
import android.media.AudioHandle;
import android.os.Build;
import android.util.SparseIntArray;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public final class AudioDeviceInfo {
    private static final SparseIntArray EXT_TO_INT_DEVICE_MAPPING;
    private static final SparseIntArray INT_TO_EXT_DEVICE_MAPPING;
    public static final int TYPE_AUX_LINE = 19;
    public static final int TYPE_BLUETOOTH_A2DP = 8;
    public static final int TYPE_BLUETOOTH_SCO = 7;
    public static final int TYPE_BUILTIN_EARPIECE = 1;
    public static final int TYPE_BUILTIN_MIC = 15;
    public static final int TYPE_BUILTIN_SPEAKER = 2;
    public static final int TYPE_BUS = 21;
    public static final int TYPE_DOCK = 13;
    public static final int TYPE_FM = 14;
    public static final int TYPE_FM_TUNER = 16;
    public static final int TYPE_HDMI = 9;
    public static final int TYPE_HDMI_ARC = 10;
    public static final int TYPE_HEARING_AID = 23;
    public static final int TYPE_IP = 20;
    public static final int TYPE_LINE_ANALOG = 5;
    public static final int TYPE_LINE_DIGITAL = 6;
    public static final int TYPE_TELEPHONY = 18;
    public static final int TYPE_TV_TUNER = 17;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_USB_ACCESSORY = 12;
    public static final int TYPE_USB_DEVICE = 11;
    public static final int TYPE_USB_HEADSET = 22;
    public static final int TYPE_WIRED_HEADPHONES = 4;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final AudioDevicePort mPort;

    static {
        INT_TO_EXT_DEVICE_MAPPING = new SparseIntArray();
        INT_TO_EXT_DEVICE_MAPPING.put(1, 1);
        INT_TO_EXT_DEVICE_MAPPING.put(2, 2);
        INT_TO_EXT_DEVICE_MAPPING.put(4, 3);
        INT_TO_EXT_DEVICE_MAPPING.put(8, 4);
        INT_TO_EXT_DEVICE_MAPPING.put(16, 7);
        INT_TO_EXT_DEVICE_MAPPING.put(32, 7);
        INT_TO_EXT_DEVICE_MAPPING.put(64, 7);
        INT_TO_EXT_DEVICE_MAPPING.put(128, 8);
        INT_TO_EXT_DEVICE_MAPPING.put(256, 8);
        INT_TO_EXT_DEVICE_MAPPING.put(512, 8);
        INT_TO_EXT_DEVICE_MAPPING.put(1024, 9);
        INT_TO_EXT_DEVICE_MAPPING.put(2048, 13);
        INT_TO_EXT_DEVICE_MAPPING.put(4096, 13);
        INT_TO_EXT_DEVICE_MAPPING.put(8192, 12);
        INT_TO_EXT_DEVICE_MAPPING.put(16384, 11);
        INT_TO_EXT_DEVICE_MAPPING.put(67108864, 22);
        INT_TO_EXT_DEVICE_MAPPING.put(65536, 18);
        INT_TO_EXT_DEVICE_MAPPING.put(131072, 5);
        INT_TO_EXT_DEVICE_MAPPING.put(262144, 10);
        INT_TO_EXT_DEVICE_MAPPING.put(524288, 6);
        INT_TO_EXT_DEVICE_MAPPING.put(1048576, 14);
        INT_TO_EXT_DEVICE_MAPPING.put(2097152, 19);
        INT_TO_EXT_DEVICE_MAPPING.put(8388608, 20);
        INT_TO_EXT_DEVICE_MAPPING.put(16777216, 21);
        INT_TO_EXT_DEVICE_MAPPING.put(134217728, 23);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483644, 15);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483640, 7);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483632, 3);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483616, 9);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483584, 18);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483520, 15);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147483136, 13);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147482624, 13);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147481600, 12);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147479552, 11);
        INT_TO_EXT_DEVICE_MAPPING.put(-2113929216, 22);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147475456, 16);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147467264, 17);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147450880, 5);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147418112, 6);
        INT_TO_EXT_DEVICE_MAPPING.put(-2147352576, 8);
        INT_TO_EXT_DEVICE_MAPPING.put(-2146959360, 20);
        INT_TO_EXT_DEVICE_MAPPING.put(-2146435072, 21);
        EXT_TO_INT_DEVICE_MAPPING = new SparseIntArray();
        EXT_TO_INT_DEVICE_MAPPING.put(1, 1);
        EXT_TO_INT_DEVICE_MAPPING.put(2, 2);
        EXT_TO_INT_DEVICE_MAPPING.put(3, 4);
        EXT_TO_INT_DEVICE_MAPPING.put(4, 8);
        EXT_TO_INT_DEVICE_MAPPING.put(5, 131072);
        EXT_TO_INT_DEVICE_MAPPING.put(6, 524288);
        EXT_TO_INT_DEVICE_MAPPING.put(7, 16);
        EXT_TO_INT_DEVICE_MAPPING.put(8, 128);
        EXT_TO_INT_DEVICE_MAPPING.put(9, 1024);
        EXT_TO_INT_DEVICE_MAPPING.put(10, 262144);
        EXT_TO_INT_DEVICE_MAPPING.put(11, 16384);
        EXT_TO_INT_DEVICE_MAPPING.put(22, 67108864);
        EXT_TO_INT_DEVICE_MAPPING.put(12, 8192);
        EXT_TO_INT_DEVICE_MAPPING.put(13, 2048);
        EXT_TO_INT_DEVICE_MAPPING.put(14, 1048576);
        EXT_TO_INT_DEVICE_MAPPING.put(15, -2147483644);
        EXT_TO_INT_DEVICE_MAPPING.put(16, -2147475456);
        EXT_TO_INT_DEVICE_MAPPING.put(17, -2147467264);
        EXT_TO_INT_DEVICE_MAPPING.put(18, 65536);
        EXT_TO_INT_DEVICE_MAPPING.put(19, 2097152);
        EXT_TO_INT_DEVICE_MAPPING.put(20, 8388608);
        EXT_TO_INT_DEVICE_MAPPING.put(21, 16777216);
        EXT_TO_INT_DEVICE_MAPPING.put(23, 134217728);
    }

    AudioDeviceInfo(AudioDevicePort audioDevicePort) {
        this.mPort = audioDevicePort;
    }

    public static int convertDeviceTypeToInternalDevice(int n) {
        return EXT_TO_INT_DEVICE_MAPPING.get(n, 0);
    }

    public static int convertInternalDeviceToDeviceType(int n) {
        return INT_TO_EXT_DEVICE_MAPPING.get(n, 0);
    }

    static boolean isValidAudioDeviceTypeOut(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
        }
        return true;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AudioDeviceInfo)object;
            return Objects.equals(this.getPort(), ((AudioDeviceInfo)object).getPort());
        }
        return false;
    }

    public String getAddress() {
        return this.mPort.address();
    }

    public int[] getChannelCounts() {
        int n;
        int n2;
        Object object = new TreeSet<Integer>();
        int[] arrn = this.getChannelMasks();
        int n3 = arrn.length;
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            n2 = arrn[n];
            n2 = this.isSink() ? AudioFormat.channelCountFromOutChannelMask(n2) : AudioFormat.channelCountFromInChannelMask(n2);
            ((TreeSet)object).add(n2);
        }
        arrn = this.getChannelIndexMasks();
        n2 = arrn.length;
        for (n = n4; n < n2; ++n) {
            ((TreeSet)object).add(Integer.bitCount(arrn[n]));
        }
        arrn = new int[((TreeSet)object).size()];
        n = 0;
        object = ((TreeSet)object).iterator();
        while (object.hasNext()) {
            arrn[n] = (Integer)object.next();
            ++n;
        }
        return arrn;
    }

    public int[] getChannelIndexMasks() {
        return this.mPort.channelIndexMasks();
    }

    public int[] getChannelMasks() {
        return this.mPort.channelMasks();
    }

    public int[] getEncodings() {
        return AudioFormat.filterPublicFormats(this.mPort.formats());
    }

    public int getId() {
        return this.mPort.handle().id();
    }

    public AudioDevicePort getPort() {
        return this.mPort;
    }

    public CharSequence getProductName() {
        String string2 = this.mPort.name();
        if (string2.length() == 0) {
            string2 = Build.MODEL;
        }
        return string2;
    }

    public int[] getSampleRates() {
        return this.mPort.samplingRates();
    }

    public int getType() {
        return INT_TO_EXT_DEVICE_MAPPING.get(this.mPort.type(), 0);
    }

    public int hashCode() {
        return Objects.hash(this.getPort());
    }

    public boolean isSink() {
        boolean bl = this.mPort.role() == 2;
        return bl;
    }

    public boolean isSource() {
        int n = this.mPort.role();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AudioDeviceTypeOut {
    }

}

