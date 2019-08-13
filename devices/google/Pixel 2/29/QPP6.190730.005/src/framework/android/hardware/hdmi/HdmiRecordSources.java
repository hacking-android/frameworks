/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.util.Log;

@SystemApi
public final class HdmiRecordSources {
    public static final int ANALOGUE_BROADCAST_TYPE_CABLE = 0;
    public static final int ANALOGUE_BROADCAST_TYPE_SATELLITE = 1;
    public static final int ANALOGUE_BROADCAST_TYPE_TERRESTRIAL = 2;
    public static final int BROADCAST_SYSTEM_NTSC_M = 3;
    public static final int BROADCAST_SYSTEM_PAL_BG = 0;
    public static final int BROADCAST_SYSTEM_PAL_DK = 8;
    public static final int BROADCAST_SYSTEM_PAL_I = 4;
    public static final int BROADCAST_SYSTEM_PAL_M = 2;
    public static final int BROADCAST_SYSTEM_PAL_OTHER_SYSTEM = 31;
    public static final int BROADCAST_SYSTEM_SECAM_BG = 6;
    public static final int BROADCAST_SYSTEM_SECAM_DK = 5;
    public static final int BROADCAST_SYSTEM_SECAM_L = 7;
    public static final int BROADCAST_SYSTEM_SECAM_LP = 1;
    private static final int CHANNEL_NUMBER_FORMAT_1_PART = 1;
    private static final int CHANNEL_NUMBER_FORMAT_2_PART = 2;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB = 0;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_BS = 8;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_CS = 9;
    public static final int DIGITAL_BROADCAST_TYPE_ARIB_T = 10;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC = 1;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_CABLE = 16;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_SATELLITE = 17;
    public static final int DIGITAL_BROADCAST_TYPE_ATSC_TERRESTRIAL = 18;
    public static final int DIGITAL_BROADCAST_TYPE_DVB = 2;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_C = 24;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_S = 25;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_S2 = 26;
    public static final int DIGITAL_BROADCAST_TYPE_DVB_T = 27;
    private static final int RECORD_SOURCE_TYPE_ANALOGUE_SERVICE = 3;
    private static final int RECORD_SOURCE_TYPE_DIGITAL_SERVICE = 2;
    private static final int RECORD_SOURCE_TYPE_EXTERNAL_PHYSICAL_ADDRESS = 5;
    private static final int RECORD_SOURCE_TYPE_EXTERNAL_PLUG = 4;
    private static final int RECORD_SOURCE_TYPE_OWN_SOURCE = 1;
    private static final String TAG = "HdmiRecordSources";

    private HdmiRecordSources() {
    }

    @SystemApi
    public static boolean checkRecordSource(byte[] arrby) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        if (arrby != null && arrby.length != 0) {
            byte by = arrby[0];
            int n = arrby.length - 1;
            if (by != 1) {
                if (by != 2) {
                    if (by != 3) {
                        if (by != 4) {
                            if (by != 5) {
                                return false;
                            }
                            if (n == 2) {
                                bl5 = true;
                            }
                            return bl5;
                        }
                        bl5 = bl;
                        if (n == 1) {
                            bl5 = true;
                        }
                        return bl5;
                    }
                    bl5 = bl2;
                    if (n == 4) {
                        bl5 = true;
                    }
                    return bl5;
                }
                bl5 = bl3;
                if (n == 7) {
                    bl5 = true;
                }
                return bl5;
            }
            bl5 = bl4;
            if (n == 0) {
                bl5 = true;
            }
            return bl5;
        }
        return false;
    }

    public static AnalogueServiceSource ofAnalogue(int n, int n2, int n3) {
        if (n >= 0 && n <= 2) {
            if (n2 >= 0 && n2 <= 65535) {
                if (n3 >= 0 && n3 <= 31) {
                    return new AnalogueServiceSource(n, n2, n3);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid Broadcast system:");
                stringBuilder.append(n3);
                Log.w(TAG, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid Broadcast system:");
                stringBuilder.append(n3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid frequency value[0x0000-0xFFFF]:");
            stringBuilder.append(n2);
            Log.w(TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid frequency value[0x0000-0xFFFF]:");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Broadcast type:");
        stringBuilder.append(n);
        Log.w(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Broadcast type:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static DigitalServiceSource ofArib(int n, AribData object) {
        if (object != null) {
            if (n != 0) {
                switch (n) {
                    default: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid ARIB type:");
                        ((StringBuilder)object).append(n);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        throw new IllegalArgumentException("type should not be null.");
                    }
                    case 8: 
                    case 9: 
                    case 10: 
                }
            }
            return new DigitalServiceSource(0, n, (DigitalServiceIdentification)object);
        }
        throw new IllegalArgumentException("data should not be null.");
    }

    public static DigitalServiceSource ofAtsc(int n, AtscData object) {
        if (object != null) {
            if (n != 1) {
                switch (n) {
                    default: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid ATSC type:");
                        ((StringBuilder)object).append(n);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid ATSC type:");
                        ((StringBuilder)object).append(n);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    case 16: 
                    case 17: 
                    case 18: 
                }
            }
            return new DigitalServiceSource(0, n, (DigitalServiceIdentification)object);
        }
        throw new IllegalArgumentException("data should not be null.");
    }

    public static DigitalServiceSource ofDigitalChannelId(int n, DigitalChannelData object) {
        if (object != null) {
            if (n != 0 && n != 1 && n != 2) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Invalid broadcast type:");
                                        ((StringBuilder)object).append(n);
                                        Log.w(TAG, ((StringBuilder)object).toString());
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Invalid broadcast system value:");
                                        ((StringBuilder)object).append(n);
                                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                                    }
                                    case 24: 
                                    case 25: 
                                    case 26: 
                                    case 27: 
                                }
                            }
                            case 16: 
                            case 17: 
                            case 18: 
                        }
                    }
                    case 8: 
                    case 9: 
                    case 10: 
                }
            }
            return new DigitalServiceSource(1, n, (DigitalServiceIdentification)object);
        }
        throw new IllegalArgumentException("data should not be null.");
    }

    public static DigitalServiceSource ofDvb(int n, DvbData object) {
        if (object != null) {
            if (n != 2) {
                switch (n) {
                    default: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid DVB type:");
                        ((StringBuilder)object).append(n);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid DVB type:");
                        ((StringBuilder)object).append(n);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                }
            }
            return new DigitalServiceSource(0, n, (DigitalServiceIdentification)object);
        }
        throw new IllegalArgumentException("data should not be null.");
    }

    public static ExternalPhysicalAddress ofExternalPhysicalAddress(int n) {
        if ((-65536 & n) == 0) {
            return new ExternalPhysicalAddress(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid physical address:");
        stringBuilder.append(n);
        Log.w(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid physical address:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static ExternalPlugData ofExternalPlug(int n) {
        if (n >= 1 && n <= 255) {
            return new ExternalPlugData(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid plug number[1-255]");
        stringBuilder.append(n);
        Log.w(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid plug number[1-255]");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static OwnSource ofOwnSource() {
        return new OwnSource();
    }

    private static int shortToByteArray(short s, byte[] arrby, int n) {
        arrby[n] = (byte)(s >>> 8 & 255);
        arrby[n + 1] = (byte)(s & 255);
        return 2;
    }

    private static int threeFieldsToSixBytes(int n, int n2, int n3, byte[] arrby, int n4) {
        HdmiRecordSources.shortToByteArray((short)n, arrby, n4);
        HdmiRecordSources.shortToByteArray((short)n2, arrby, n4 + 2);
        HdmiRecordSources.shortToByteArray((short)n3, arrby, n4 + 4);
        return 6;
    }

    @SystemApi
    public static final class AnalogueServiceSource
    extends RecordSource {
        static final int EXTRA_DATA_SIZE = 4;
        private final int mBroadcastSystem;
        private final int mBroadcastType;
        private final int mFrequency;

        private AnalogueServiceSource(int n, int n2, int n3) {
            super(3, 4);
            this.mBroadcastType = n;
            this.mFrequency = n2;
            this.mBroadcastSystem = n3;
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            arrby[n] = (byte)this.mBroadcastType;
            HdmiRecordSources.shortToByteArray((short)this.mFrequency, arrby, n + 1);
            arrby[n + 3] = (byte)this.mBroadcastSystem;
            return 4;
        }
    }

    public static final class AribData
    implements DigitalServiceIdentification {
        private final int mOriginalNetworkId;
        private final int mServiceId;
        private final int mTransportStreamId;

        public AribData(int n, int n2, int n3) {
            this.mTransportStreamId = n;
            this.mServiceId = n2;
            this.mOriginalNetworkId = n3;
        }

        @Override
        public int toByteArray(byte[] arrby, int n) {
            return HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mServiceId, this.mOriginalNetworkId, arrby, n);
        }
    }

    public static final class AtscData
    implements DigitalServiceIdentification {
        private final int mProgramNumber;
        private final int mTransportStreamId;

        public AtscData(int n, int n2) {
            this.mTransportStreamId = n;
            this.mProgramNumber = n2;
        }

        @Override
        public int toByteArray(byte[] arrby, int n) {
            return HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mProgramNumber, 0, arrby, n);
        }
    }

    private static final class ChannelIdentifier {
        private final int mChannelNumberFormat;
        private final int mMajorChannelNumber;
        private final int mMinorChannelNumber;

        private ChannelIdentifier(int n, int n2, int n3) {
            this.mChannelNumberFormat = n;
            this.mMajorChannelNumber = n2;
            this.mMinorChannelNumber = n3;
        }

        private int toByteArray(byte[] arrby, int n) {
            int n2 = this.mChannelNumberFormat;
            int n3 = this.mMajorChannelNumber;
            arrby[n] = (byte)(n2 << 2 | n3 >>> 8 & 3);
            arrby[n + 1] = (byte)(n3 & 255);
            HdmiRecordSources.shortToByteArray((short)this.mMinorChannelNumber, arrby, n + 2);
            return 4;
        }
    }

    public static final class DigitalChannelData
    implements DigitalServiceIdentification {
        private final ChannelIdentifier mChannelIdentifier;

        private DigitalChannelData(ChannelIdentifier channelIdentifier) {
            this.mChannelIdentifier = channelIdentifier;
        }

        public static DigitalChannelData ofOneNumber(int n) {
            return new DigitalChannelData(new ChannelIdentifier(1, 0, n));
        }

        public static DigitalChannelData ofTwoNumbers(int n, int n2) {
            return new DigitalChannelData(new ChannelIdentifier(2, n, n2));
        }

        @Override
        public int toByteArray(byte[] arrby, int n) {
            this.mChannelIdentifier.toByteArray(arrby, n);
            arrby[n + 4] = (byte)(false ? 1 : 0);
            arrby[n + 5] = (byte)(false ? 1 : 0);
            return 6;
        }
    }

    private static interface DigitalServiceIdentification {
        public int toByteArray(byte[] var1, int var2);
    }

    @SystemApi
    public static final class DigitalServiceSource
    extends RecordSource {
        private static final int DIGITAL_SERVICE_IDENTIFIED_BY_CHANNEL = 1;
        private static final int DIGITAL_SERVICE_IDENTIFIED_BY_DIGITAL_ID = 0;
        static final int EXTRA_DATA_SIZE = 7;
        private final int mBroadcastSystem;
        private final DigitalServiceIdentification mIdentification;
        private final int mIdentificationMethod;

        private DigitalServiceSource(int n, int n2, DigitalServiceIdentification digitalServiceIdentification) {
            super(2, 7);
            this.mIdentificationMethod = n;
            this.mBroadcastSystem = n2;
            this.mIdentification = digitalServiceIdentification;
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            arrby[n] = (byte)(this.mIdentificationMethod << 7 | this.mBroadcastSystem & 127);
            this.mIdentification.toByteArray(arrby, n + 1);
            return 7;
        }
    }

    public static final class DvbData
    implements DigitalServiceIdentification {
        private final int mOriginalNetworkId;
        private final int mServiceId;
        private final int mTransportStreamId;

        public DvbData(int n, int n2, int n3) {
            this.mTransportStreamId = n;
            this.mServiceId = n2;
            this.mOriginalNetworkId = n3;
        }

        @Override
        public int toByteArray(byte[] arrby, int n) {
            return HdmiRecordSources.threeFieldsToSixBytes(this.mTransportStreamId, this.mServiceId, this.mOriginalNetworkId, arrby, n);
        }
    }

    @SystemApi
    public static final class ExternalPhysicalAddress
    extends RecordSource {
        static final int EXTRA_DATA_SIZE = 2;
        private final int mPhysicalAddress;

        private ExternalPhysicalAddress(int n) {
            super(5, 2);
            this.mPhysicalAddress = n;
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            HdmiRecordSources.shortToByteArray((short)this.mPhysicalAddress, arrby, n);
            return 2;
        }
    }

    @SystemApi
    public static final class ExternalPlugData
    extends RecordSource {
        static final int EXTRA_DATA_SIZE = 1;
        private final int mPlugNumber;

        private ExternalPlugData(int n) {
            super(4, 1);
            this.mPlugNumber = n;
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            arrby[n] = (byte)this.mPlugNumber;
            return 1;
        }
    }

    @SystemApi
    public static final class OwnSource
    extends RecordSource {
        private static final int EXTRA_DATA_SIZE = 0;

        private OwnSource() {
            super(1, 0);
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            return 0;
        }
    }

    @SystemApi
    public static abstract class RecordSource {
        final int mExtraDataSize;
        final int mSourceType;

        RecordSource(int n, int n2) {
            this.mSourceType = n;
            this.mExtraDataSize = n2;
        }

        abstract int extraParamToByteArray(byte[] var1, int var2);

        final int getDataSize(boolean bl) {
            int n;
            int n2 = n = this.mExtraDataSize;
            if (bl) {
                n2 = n + 1;
            }
            return n2;
        }

        final int toByteArray(boolean bl, byte[] arrby, int n) {
            int n2 = n;
            if (bl) {
                arrby[n] = (byte)this.mSourceType;
                n2 = n + 1;
            }
            this.extraParamToByteArray(arrby, n2);
            return this.getDataSize(bl);
        }
    }

}

