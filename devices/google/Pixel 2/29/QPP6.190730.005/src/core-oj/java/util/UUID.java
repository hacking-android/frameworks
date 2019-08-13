/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class UUID
implements Serializable,
Comparable<UUID> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -4856846361193249489L;
    private final long leastSigBits;
    private final long mostSigBits;

    public UUID(long l, long l2) {
        this.mostSigBits = l;
        this.leastSigBits = l2;
    }

    private UUID(byte[] arrby) {
        int n;
        long l = 0L;
        long l2 = 0L;
        for (n = 0; n < 8; ++n) {
            l = l << 8 | (long)(arrby[n] & 255);
        }
        for (n = 8; n < 16; ++n) {
            l2 = l2 << 8 | (long)(arrby[n] & 255);
        }
        this.mostSigBits = l;
        this.leastSigBits = l2;
    }

    private static String digits(long l, int n) {
        long l2 = 1L << n * 4;
        return Long.toHexString(l2 - 1L & l | l2).substring(1);
    }

    public static UUID fromString(String charSequence) {
        Object object = ((String)charSequence).split("-");
        if (((String[])object).length == 5) {
            for (int i = 0; i < 5; ++i) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("0x");
                ((StringBuilder)charSequence).append((String)object[i]);
                object[i] = ((StringBuilder)charSequence).toString();
            }
            return new UUID((Long.decode((String)object[0]) << 16 | Long.decode((String)object[1])) << 16 | Long.decode((String)object[2]), Long.decode((String)object[3]) << 48 | Long.decode((String)object[4]));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid UUID string: ");
        ((StringBuilder)object).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static UUID nameUUIDFromBytes(byte[] arrby) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            arrby = messageDigest.digest(arrby);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new InternalError("MD5 not supported", noSuchAlgorithmException);
        }
        arrby[6] = (byte)(arrby[6] & 15);
        arrby[6] = (byte)(arrby[6] | 48);
        arrby[8] = (byte)(arrby[8] & 63);
        arrby[8] = (byte)(arrby[8] | 128);
        return new UUID(arrby);
    }

    public static UUID randomUUID() {
        SecureRandom secureRandom = Holder.numberGenerator;
        byte[] arrby = new byte[16];
        secureRandom.nextBytes(arrby);
        arrby[6] = (byte)(arrby[6] & 15);
        arrby[6] = (byte)(arrby[6] | 64);
        arrby[8] = (byte)(arrby[8] & 63);
        arrby[8] = (byte)(arrby[8] | 128);
        return new UUID(arrby);
    }

    public int clockSequence() {
        if (this.version() == 1) {
            return (int)((this.leastSigBits & 4611404543450677248L) >>> 48);
        }
        throw new UnsupportedOperationException("Not a time-based UUID");
    }

    @Override
    public int compareTo(UUID uUID) {
        long l = this.mostSigBits;
        long l2 = uUID.mostSigBits;
        int n = 1;
        if (l < l2) {
            n = -1;
        } else if (l <= l2) {
            l = this.leastSigBits;
            l2 = uUID.leastSigBits;
            if (l < l2) {
                n = -1;
            } else if (l <= l2) {
                n = 0;
            }
        }
        return n;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass() == UUID.class) {
            object = (UUID)object;
            boolean bl2 = bl;
            if (this.mostSigBits == ((UUID)object).mostSigBits) {
                bl2 = bl;
                if (this.leastSigBits == ((UUID)object).leastSigBits) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public long getLeastSignificantBits() {
        return this.leastSigBits;
    }

    public long getMostSignificantBits() {
        return this.mostSigBits;
    }

    public int hashCode() {
        long l = this.mostSigBits ^ this.leastSigBits;
        return (int)(l >> 32) ^ (int)l;
    }

    public long node() {
        if (this.version() == 1) {
            return this.leastSigBits & 0xFFFFFFFFFFFFL;
        }
        throw new UnsupportedOperationException("Not a time-based UUID");
    }

    public long timestamp() {
        if (this.version() == 1) {
            long l = this.mostSigBits;
            return l >>> 32 | ((4095L & l) << 48 | (l >> 16 & 65535L) << 32);
        }
        throw new UnsupportedOperationException("Not a time-based UUID");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UUID.digits(this.mostSigBits >> 32, 8));
        stringBuilder.append("-");
        stringBuilder.append(UUID.digits(this.mostSigBits >> 16, 4));
        stringBuilder.append("-");
        stringBuilder.append(UUID.digits(this.mostSigBits, 4));
        stringBuilder.append("-");
        stringBuilder.append(UUID.digits(this.leastSigBits >> 48, 4));
        stringBuilder.append("-");
        stringBuilder.append(UUID.digits(this.leastSigBits, 12));
        return stringBuilder.toString();
    }

    public int variant() {
        long l = this.leastSigBits;
        return (int)(l >> 63 & l >>> (int)(64L - (l >>> 62)));
    }

    public int version() {
        return (int)(this.mostSigBits >> 12 & 15L);
    }

    private static class Holder {
        static final SecureRandom numberGenerator = new SecureRandom();

        private Holder() {
        }
    }

}

