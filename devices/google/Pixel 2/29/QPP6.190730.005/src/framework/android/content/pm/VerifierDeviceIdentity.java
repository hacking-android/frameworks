/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;

public class VerifierDeviceIdentity
implements Parcelable {
    public static final Parcelable.Creator<VerifierDeviceIdentity> CREATOR;
    private static final char[] ENCODE;
    private static final int GROUP_SIZE = 4;
    private static final int LONG_SIZE = 13;
    private static final char SEPARATOR = '-';
    private final long mIdentity;
    private final String mIdentityString;

    static {
        ENCODE = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};
        CREATOR = new Parcelable.Creator<VerifierDeviceIdentity>(){

            @Override
            public VerifierDeviceIdentity createFromParcel(Parcel parcel) {
                return new VerifierDeviceIdentity(parcel);
            }

            public VerifierDeviceIdentity[] newArray(int n) {
                return new VerifierDeviceIdentity[n];
            }
        };
    }

    public VerifierDeviceIdentity(long l) {
        this.mIdentity = l;
        this.mIdentityString = VerifierDeviceIdentity.encodeBase32(l);
    }

    private VerifierDeviceIdentity(Parcel parcel) {
        long l;
        this.mIdentity = l = parcel.readLong();
        this.mIdentityString = VerifierDeviceIdentity.encodeBase32(l);
    }

    private static final long decodeBase32(byte[] object) throws IllegalArgumentException {
        long l = 0L;
        int n = 0;
        int n2 = ((byte[])object).length;
        for (int i = 0; i < n2; ++i) {
            int n3;
            block12 : {
                block8 : {
                    block11 : {
                        block10 : {
                            block9 : {
                                block7 : {
                                    n3 = object[i];
                                    if (65 > n3 || n3 > 90) break block7;
                                    n3 -= 65;
                                    break block8;
                                }
                                if (50 > n3 || n3 > 55) break block9;
                                n3 -= 24;
                                break block8;
                            }
                            if (n3 == 45) continue;
                            if (97 > n3 || n3 > 122) break block10;
                            n3 -= 97;
                            break block8;
                        }
                        if (n3 != 48) break block11;
                        n3 = 14;
                        break block8;
                    }
                    if (n3 != 49) break block12;
                    n3 = 8;
                }
                l = l << 5 | (long)n3;
                if (++n == 1) {
                    if ((n3 & 15) == n3) continue;
                    throw new IllegalArgumentException("illegal start character; will overflow");
                }
                if (n <= 13) {
                    continue;
                }
                throw new IllegalArgumentException("too long; should have 13 characters");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("base base-32 character: ");
            ((StringBuilder)object).append(n3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (n == 13) {
            return l;
        }
        throw new IllegalArgumentException("too short; should have 13 characters");
    }

    private static final String encodeBase32(long l) {
        char[] arrc = ENCODE;
        char[] arrc2 = new char[16];
        int n = arrc2.length;
        for (int i = 0; i < 13; ++i) {
            int n2 = n;
            if (i > 0) {
                n2 = n;
                if (i % 4 == 1) {
                    n2 = n - 1;
                    arrc2[n2] = (char)45;
                }
            }
            int n3 = (int)(31L & l);
            l >>>= 5;
            n = n2 - 1;
            arrc2[n] = arrc[n3];
        }
        return String.valueOf(arrc2);
    }

    public static VerifierDeviceIdentity generate() {
        return VerifierDeviceIdentity.generate(new SecureRandom());
    }

    @VisibleForTesting
    static VerifierDeviceIdentity generate(Random random) {
        return new VerifierDeviceIdentity(random.nextLong());
    }

    public static VerifierDeviceIdentity parse(String arrby) throws IllegalArgumentException {
        try {
            arrby = arrby.getBytes("US-ASCII");
            return new VerifierDeviceIdentity(VerifierDeviceIdentity.decodeBase32(arrby));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException("bad base-32 characters in input");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof VerifierDeviceIdentity;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (VerifierDeviceIdentity)object;
        if (this.mIdentity == ((VerifierDeviceIdentity)object).mIdentity) {
            bl2 = true;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.mIdentity;
    }

    public String toString() {
        return this.mIdentityString;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mIdentity);
    }

}

