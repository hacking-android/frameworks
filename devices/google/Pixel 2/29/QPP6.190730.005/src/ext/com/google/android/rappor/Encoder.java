/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.rappor;

import com.google.android.rappor.HmacDrbg;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class Encoder {
    private static final byte HMAC_DRBG_TYPE_COHORT = 0;
    private static final byte HMAC_DRBG_TYPE_PRR = 1;
    public static final int MAX_BITS = 4096;
    public static final int MAX_BLOOM_HASHES = 8;
    public static final int MAX_COHORTS = 128;
    public static final int MIN_USER_SECRET_BYTES = 48;
    public static final long VERSION = 3L;
    private final int cohort;
    private final byte[] encoderIdBytes;
    private final BitSet inputMask;
    private final MessageDigest md5;
    private final int numBits;
    private final int numBloomHashes;
    private final int numCohorts;
    private final double probabilityF;
    private final double probabilityP;
    private final double probabilityQ;
    private final Random random;
    private final MessageDigest sha256;
    private final byte[] userSecret;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Encoder(Random random, MessageDigest messageDigest, MessageDigest messageDigest2, byte[] arrby, String string, int n, double d, double d2, double d3, int n2, int n3) {
        if (messageDigest != null) {
            this.md5 = messageDigest;
        } else {
            this.md5 = MessageDigest.getInstance("MD5");
        }
        this.md5.reset();
        if (messageDigest2 != null) {
            this.sha256 = messageDigest2;
        } else {
            this.sha256 = MessageDigest.getInstance("SHA-256");
        }
        this.sha256.reset();
        this.encoderIdBytes = string.getBytes(StandardCharsets.UTF_8);
        this.random = random != null ? random : new SecureRandom();
        boolean bl = arrby.length >= 48;
        Encoder.checkArgument(bl, "userSecret must be at least 48 bytes of high-quality entropy.");
        this.userSecret = arrby;
        bl = d >= 0.0 && d <= 1.0;
        Encoder.checkArgument(bl, "probabilityF must be on range [0.0, 1.0]");
        this.probabilityF = (double)Math.round(d * 128.0) / 128.0;
        bl = d2 >= 0.0 && d2 <= 1.0;
        Encoder.checkArgument(bl, "probabilityP must be on range [0.0, 1.0]");
        this.probabilityP = d2;
        bl = d3 >= 0.0 && d3 <= 1.0;
        Encoder.checkArgument(bl, "probabilityQ must be on range [0.0, 1.0]");
        this.probabilityQ = d3;
        bl = n >= 1 && n <= 4096;
        Encoder.checkArgument(bl, "numBits must be on range [1, 4096].");
        this.numBits = n;
        this.inputMask = new BitSet(n);
        this.inputMask.set(0, n, true);
        bl = n3 >= 1 && n3 <= n;
        Encoder.checkArgument(bl, "numBloomHashes must be on range [1, numBits).");
        this.numBloomHashes = n3;
        bl = n2 >= 1 && n2 <= 128;
        Encoder.checkArgument(bl, "numCohorts must be on range [1, 128].");
        bl = (n2 - 1 & n2) == 0;
        Encoder.checkArgument(bl, "numCohorts must be a power of 2.");
        this.numCohorts = n2;
        this.cohort = n2 - 1 & Math.abs(ByteBuffer.wrap(new HmacDrbg(arrby, new byte[]{0}).nextBytes(4)).getInt()) % 128;
        return;
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    public Encoder(byte[] arrby, String string, int n, double d, double d2, double d3, int n2, int n3) {
        this(null, null, null, arrby, string, n, d, d2, d3, n2, n3);
    }

    private static void checkArgument(boolean bl, Object object) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(String.valueOf(object));
    }

    private BitSet computeInstantaneousRandomizedResponse(BitSet bitSet) {
        BitSet bitSet2 = new BitSet();
        bitSet2.or(bitSet);
        bitSet2.andNot(this.inputMask);
        Encoder.checkArgument(bitSet2.isEmpty(), "Input bits had bits set past Encoder's numBits limit.");
        if (this.probabilityP == 0.0 && this.probabilityQ == 1.0) {
            return bitSet;
        }
        bitSet2 = new BitSet(this.numBits);
        for (int i = 0; i < this.numBits; ++i) {
            double d = bitSet.get(i) ? this.probabilityQ : this.probabilityP;
            boolean bl = (double)this.random.nextFloat() < d;
            bitSet2.set(i, bl);
        }
        return bitSet2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private BitSet computePermanentRandomizedResponse(BitSet bitSet) {
        Object object = new BitSet();
        ((BitSet)object).or(bitSet);
        ((BitSet)object).andNot(this.inputMask);
        Encoder.checkArgument(((BitSet)object).isEmpty(), "Input bits had bits set past Encoder's numBits limit.");
        if (this.probabilityF == 0.0) {
            return bitSet;
        }
        synchronized (this) {
            object = new byte[Math.min(20, this.sha256.getDigestLength() + 1)];
            object[0] = (byte)(true ? 1 : 0);
            this.sha256.reset();
            this.sha256.update(this.encoderIdBytes);
            this.sha256.update(new byte[]{0});
            this.sha256.update(bitSet.toByteArray());
            System.arraycopy(this.sha256.digest((byte[])object), 0, object, 1, ((byte[])object).length - 1);
        }
        byte[] arrby = new HmacDrbg(this.userSecret, (byte[])object).nextBytes(this.numBits);
        boolean bl = this.numBits <= arrby.length;
        Encoder.verify(bl);
        int n = (int)Math.round(this.probabilityF * 128.0);
        object = new BitSet(this.numBits);
        int n2 = 0;
        while (n2 < this.numBits) {
            int n3 = arrby[n2] & 255;
            boolean bl2 = n3 >> 1 < n;
            if (bl2) {
                bl = (n3 & 1) > 0;
                ((BitSet)object).set(n2, bl);
            } else {
                ((BitSet)object).set(n2, bitSet.get(n2));
            }
            ++n2;
        }
        return object;
    }

    private byte[] encodeBits(BitSet arrby) {
        byte[] arrby2;
        boolean bl = (arrby = this.computeInstantaneousRandomizedResponse(this.computePermanentRandomizedResponse((BitSet)arrby)).toByteArray()).length <= (arrby2 = new byte[(this.numBits + 7) / 8]).length;
        Encoder.verify(bl);
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby2;
    }

    private static void verify(boolean bl) {
        if (bl) {
            return;
        }
        throw new IllegalStateException();
    }

    public byte[] encodeBits(byte[] arrby) {
        return this.encodeBits(BitSet.valueOf(arrby));
    }

    public byte[] encodeBoolean(boolean bl) {
        BitSet bitSet = new BitSet(this.numBits);
        bitSet.set(0, bl);
        return this.encodeBits(bitSet);
    }

    public byte[] encodeOrdinal(int n) {
        boolean bl = n >= 0 && n < this.numBits;
        Encoder.checkArgument(bl, "Ordinal value must be in range [0, numBits).");
        BitSet bitSet = new BitSet(this.numBits);
        bitSet.set(n, true);
        return this.encodeBits(bitSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] encodeString(String object) {
        byte[] arrby;
        object = ((String)object).getBytes(StandardCharsets.UTF_8);
        object = ByteBuffer.allocate(((byte[])object).length + 4).putInt(this.cohort).put((byte[])object).array();
        synchronized (this) {
            this.md5.reset();
            arrby = this.md5.digest((byte[])object);
        }
        int n = arrby.length;
        boolean bl = false;
        boolean bl2 = n == 16;
        Encoder.verify(bl2);
        bl2 = bl;
        if (this.numBloomHashes <= arrby.length / 2) {
            bl2 = true;
        }
        Encoder.verify(bl2);
        object = new BitSet(this.numBits);
        n = 0;
        while (n < this.numBloomHashes) {
            ((BitSet)object).set(((arrby[n * 2] & 255) * 256 + (arrby[n * 2 + 1] & 255)) % this.numBits, true);
            ++n;
        }
        return this.encodeBits((BitSet)object);
    }

    public int getCohort() {
        return this.cohort;
    }

    public String getEncoderId() {
        return new String(this.encoderIdBytes, StandardCharsets.UTF_8);
    }

    public int getNumBits() {
        return this.numBits;
    }

    public int getNumBloomHashes() {
        return this.numBloomHashes;
    }

    public int getNumCohorts() {
        return this.numCohorts;
    }

    public double getProbabilityF() {
        return this.probabilityF;
    }

    public double getProbabilityP() {
        return this.probabilityP;
    }

    public double getProbabilityQ() {
        return this.probabilityQ;
    }
}

