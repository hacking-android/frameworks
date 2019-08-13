/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public final class BluetoothUuid {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final ParcelUuid AdvAudioDist;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final ParcelUuid AudioSink;
    public static final ParcelUuid AudioSource;
    public static final ParcelUuid AvrcpController;
    public static final ParcelUuid AvrcpTarget;
    public static final ParcelUuid BASE_UUID;
    public static final ParcelUuid BNEP;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final ParcelUuid HSP;
    public static final ParcelUuid HSP_AG;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final ParcelUuid Handsfree;
    public static final ParcelUuid Handsfree_AG;
    public static final ParcelUuid HearingAid;
    public static final ParcelUuid Hid;
    @UnsupportedAppUsage
    public static final ParcelUuid Hogp;
    public static final ParcelUuid MAP;
    public static final ParcelUuid MAS;
    public static final ParcelUuid MNS;
    @UnsupportedAppUsage
    public static final ParcelUuid NAP;
    @UnsupportedAppUsage
    public static final ParcelUuid ObexObjectPush;
    public static final ParcelUuid PANU;
    public static final ParcelUuid PBAP_PCE;
    @UnsupportedAppUsage
    public static final ParcelUuid PBAP_PSE;
    @UnsupportedAppUsage
    public static final ParcelUuid[] RESERVED_UUIDS;
    public static final ParcelUuid SAP;
    public static final int UUID_BYTES_128_BIT = 16;
    public static final int UUID_BYTES_16_BIT = 2;
    public static final int UUID_BYTES_32_BIT = 4;

    static {
        AudioSink = ParcelUuid.fromString("0000110B-0000-1000-8000-00805F9B34FB");
        AudioSource = ParcelUuid.fromString("0000110A-0000-1000-8000-00805F9B34FB");
        AdvAudioDist = ParcelUuid.fromString("0000110D-0000-1000-8000-00805F9B34FB");
        HSP = ParcelUuid.fromString("00001108-0000-1000-8000-00805F9B34FB");
        HSP_AG = ParcelUuid.fromString("00001112-0000-1000-8000-00805F9B34FB");
        Handsfree = ParcelUuid.fromString("0000111E-0000-1000-8000-00805F9B34FB");
        Handsfree_AG = ParcelUuid.fromString("0000111F-0000-1000-8000-00805F9B34FB");
        AvrcpController = ParcelUuid.fromString("0000110E-0000-1000-8000-00805F9B34FB");
        AvrcpTarget = ParcelUuid.fromString("0000110C-0000-1000-8000-00805F9B34FB");
        ObexObjectPush = ParcelUuid.fromString("00001105-0000-1000-8000-00805f9b34fb");
        Hid = ParcelUuid.fromString("00001124-0000-1000-8000-00805f9b34fb");
        Hogp = ParcelUuid.fromString("00001812-0000-1000-8000-00805f9b34fb");
        PANU = ParcelUuid.fromString("00001115-0000-1000-8000-00805F9B34FB");
        NAP = ParcelUuid.fromString("00001116-0000-1000-8000-00805F9B34FB");
        BNEP = ParcelUuid.fromString("0000000f-0000-1000-8000-00805F9B34FB");
        PBAP_PCE = ParcelUuid.fromString("0000112e-0000-1000-8000-00805F9B34FB");
        PBAP_PSE = ParcelUuid.fromString("0000112f-0000-1000-8000-00805F9B34FB");
        MAP = ParcelUuid.fromString("00001134-0000-1000-8000-00805F9B34FB");
        MNS = ParcelUuid.fromString("00001133-0000-1000-8000-00805F9B34FB");
        MAS = ParcelUuid.fromString("00001132-0000-1000-8000-00805F9B34FB");
        SAP = ParcelUuid.fromString("0000112D-0000-1000-8000-00805F9B34FB");
        HearingAid = ParcelUuid.fromString("0000FDF0-0000-1000-8000-00805f9b34fb");
        BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
        RESERVED_UUIDS = new ParcelUuid[]{AudioSink, AudioSource, AdvAudioDist, HSP, Handsfree, AvrcpController, AvrcpTarget, ObexObjectPush, PANU, NAP, MAP, MNS, MAS, SAP};
    }

    public static boolean containsAllUuids(ParcelUuid[] object, ParcelUuid[] arrparcelUuid) {
        boolean bl = true;
        if (object == null && arrparcelUuid == null) {
            return true;
        }
        if (object == null) {
            if (arrparcelUuid.length != 0) {
                bl = false;
            }
            return bl;
        }
        if (arrparcelUuid == null) {
            return true;
        }
        object = new HashSet<ParcelUuid>(Arrays.asList(object));
        int n = arrparcelUuid.length;
        for (int i = 0; i < n; ++i) {
            if (((HashSet)object).contains(arrparcelUuid[i])) continue;
            return false;
        }
        return true;
    }

    @UnsupportedAppUsage
    public static boolean containsAnyUuid(ParcelUuid[] object, ParcelUuid[] arrparcelUuid) {
        boolean bl = true;
        boolean bl2 = true;
        if (object == null && arrparcelUuid == null) {
            return true;
        }
        if (object == null) {
            if (arrparcelUuid.length != 0) {
                bl2 = false;
            }
            return bl2;
        }
        if (arrparcelUuid == null) {
            bl2 = ((ParcelUuid[])object).length == 0 ? bl : false;
            return bl2;
        }
        object = new HashSet<ParcelUuid>(Arrays.asList(object));
        int n = arrparcelUuid.length;
        for (int i = 0; i < n; ++i) {
            if (!((HashSet)object).contains(arrparcelUuid[i])) continue;
            return true;
        }
        return false;
    }

    public static int getServiceIdentifierFromParcelUuid(ParcelUuid parcelUuid) {
        return (int)((parcelUuid.getUuid().getMostSignificantBits() & -4294967296L) >>> 32);
    }

    @UnsupportedAppUsage
    public static boolean is16BitUuid(ParcelUuid object) {
        object = ((ParcelUuid)object).getUuid();
        long l = ((UUID)object).getLeastSignificantBits();
        long l2 = BASE_UUID.getUuid().getLeastSignificantBits();
        boolean bl = false;
        if (l != l2) {
            return false;
        }
        if ((((UUID)object).getMostSignificantBits() & -281470681743361L) == 4096L) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean is32BitUuid(ParcelUuid parcelUuid) {
        UUID uUID = parcelUuid.getUuid();
        long l = uUID.getLeastSignificantBits();
        long l2 = BASE_UUID.getUuid().getLeastSignificantBits();
        boolean bl = false;
        if (l != l2) {
            return false;
        }
        if (BluetoothUuid.is16BitUuid(parcelUuid)) {
            return false;
        }
        if ((uUID.getMostSignificantBits() & 0xFFFFFFFFL) == 4096L) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isAdvAudioDist(ParcelUuid parcelUuid) {
        return parcelUuid.equals(AdvAudioDist);
    }

    public static boolean isAudioSink(ParcelUuid parcelUuid) {
        return parcelUuid.equals(AudioSink);
    }

    @UnsupportedAppUsage
    public static boolean isAudioSource(ParcelUuid parcelUuid) {
        return parcelUuid.equals(AudioSource);
    }

    public static boolean isAvrcpController(ParcelUuid parcelUuid) {
        return parcelUuid.equals(AvrcpController);
    }

    @UnsupportedAppUsage
    public static boolean isAvrcpTarget(ParcelUuid parcelUuid) {
        return parcelUuid.equals(AvrcpTarget);
    }

    public static boolean isBnep(ParcelUuid parcelUuid) {
        return parcelUuid.equals(BNEP);
    }

    public static boolean isHandsfree(ParcelUuid parcelUuid) {
        return parcelUuid.equals(Handsfree);
    }

    public static boolean isHeadset(ParcelUuid parcelUuid) {
        return parcelUuid.equals(HSP);
    }

    public static boolean isInputDevice(ParcelUuid parcelUuid) {
        return parcelUuid.equals(Hid);
    }

    public static boolean isMap(ParcelUuid parcelUuid) {
        return parcelUuid.equals(MAP);
    }

    public static boolean isMas(ParcelUuid parcelUuid) {
        return parcelUuid.equals(MAS);
    }

    public static boolean isMns(ParcelUuid parcelUuid) {
        return parcelUuid.equals(MNS);
    }

    public static boolean isNap(ParcelUuid parcelUuid) {
        return parcelUuid.equals(NAP);
    }

    public static boolean isPanu(ParcelUuid parcelUuid) {
        return parcelUuid.equals(PANU);
    }

    public static boolean isSap(ParcelUuid parcelUuid) {
        return parcelUuid.equals(SAP);
    }

    @UnsupportedAppUsage
    public static boolean isUuidPresent(ParcelUuid[] arrparcelUuid, ParcelUuid parcelUuid) {
        if ((arrparcelUuid == null || arrparcelUuid.length == 0) && parcelUuid == null) {
            return true;
        }
        if (arrparcelUuid == null) {
            return false;
        }
        int n = arrparcelUuid.length;
        for (int i = 0; i < n; ++i) {
            if (!arrparcelUuid[i].equals(parcelUuid)) continue;
            return true;
        }
        return false;
    }

    public static ParcelUuid parseUuidFrom(byte[] object) {
        if (object != null) {
            long l;
            int n = ((byte[])object).length;
            if (n != 2 && n != 4 && n != 16) {
                object = new StringBuilder();
                ((StringBuilder)object).append("uuidBytes length invalid - ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (n == 16) {
                object = ByteBuffer.wrap((byte[])object).order(ByteOrder.LITTLE_ENDIAN);
                return new ParcelUuid(new UUID(((ByteBuffer)object).getLong(8), ((ByteBuffer)object).getLong(0)));
            }
            if (n == 2) {
                l = (long)(object[0] & 255) + (long)((object[1] & 255) << 8);
            } else {
                long l2 = object[0] & 255;
                l = (object[1] & 255) << 8;
                long l3 = (object[2] & 255) << 16;
                l = (long)((object[3] & 255) << 24) + (l2 + l + l3);
            }
            return new ParcelUuid(new UUID(BASE_UUID.getUuid().getMostSignificantBits() + (l << 32), BASE_UUID.getUuid().getLeastSignificantBits()));
        }
        throw new IllegalArgumentException("uuidBytes cannot be null");
    }

    public static byte[] uuidToBytes(ParcelUuid arrby) {
        if (arrby != null) {
            if (BluetoothUuid.is16BitUuid((ParcelUuid)arrby)) {
                int n = BluetoothUuid.getServiceIdentifierFromParcelUuid((ParcelUuid)arrby);
                return new byte[]{(byte)(n & 255), (byte)((65280 & n) >> 8)};
            }
            if (BluetoothUuid.is32BitUuid((ParcelUuid)arrby)) {
                int n = BluetoothUuid.getServiceIdentifierFromParcelUuid((ParcelUuid)arrby);
                return new byte[]{(byte)(n & 255), (byte)((65280 & n) >> 8), (byte)((16711680 & n) >> 16), (byte)((-16777216 & n) >> 24)};
            }
            long l = arrby.getUuid().getMostSignificantBits();
            long l2 = arrby.getUuid().getLeastSignificantBits();
            arrby = new byte[16];
            ByteBuffer byteBuffer = ByteBuffer.wrap(arrby).order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.putLong(8, l);
            byteBuffer.putLong(0, l2);
            return arrby;
        }
        throw new IllegalArgumentException("uuid cannot be null");
    }
}

