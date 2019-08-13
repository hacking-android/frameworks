/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;

public class DistributionPoint {
    public static final int AA_COMPROMISE = 8;
    public static final int AFFILIATION_CHANGED = 3;
    public static final int CA_COMPROMISE = 2;
    public static final int CERTIFICATE_HOLD = 6;
    public static final int CESSATION_OF_OPERATION = 5;
    public static final int KEY_COMPROMISE = 1;
    public static final int PRIVILEGE_WITHDRAWN = 7;
    private static final String[] REASON_STRINGS = new String[]{null, "key compromise", "CA compromise", "affiliation changed", "superseded", "cessation of operation", "certificate hold", "privilege withdrawn", "AA compromise"};
    public static final int SUPERSEDED = 4;
    private static final byte TAG_DIST_PT = 0;
    private static final byte TAG_FULL_NAME = 0;
    private static final byte TAG_ISSUER = 2;
    private static final byte TAG_REASONS = 1;
    private static final byte TAG_REL_NAME = 1;
    private GeneralNames crlIssuer;
    private GeneralNames fullName;
    private volatile int hashCode;
    private boolean[] reasonFlags;
    private RDN relativeName;

    public DistributionPoint(DerValue derValue) throws IOException {
        if (derValue.tag == 48) {
            while (derValue.data != null && derValue.data.available() != 0) {
                DerValue derValue2 = derValue.data.getDerValue();
                if (derValue2.isContextSpecific((byte)0) && derValue2.isConstructed()) {
                    if (this.fullName == null && this.relativeName == null) {
                        derValue2 = derValue2.data.getDerValue();
                        if (derValue2.isContextSpecific((byte)0) && derValue2.isConstructed()) {
                            derValue2.resetTag((byte)48);
                            this.fullName = new GeneralNames(derValue2);
                            continue;
                        }
                        if (derValue2.isContextSpecific((byte)1) && derValue2.isConstructed()) {
                            derValue2.resetTag((byte)49);
                            this.relativeName = new RDN(derValue2);
                            continue;
                        }
                        throw new IOException("Invalid DistributionPointName in DistributionPoint");
                    }
                    throw new IOException("Duplicate DistributionPointName in DistributionPoint.");
                }
                if (derValue2.isContextSpecific((byte)1) && !derValue2.isConstructed()) {
                    if (this.reasonFlags == null) {
                        derValue2.resetTag((byte)3);
                        this.reasonFlags = derValue2.getUnalignedBitString().toBooleanArray();
                        continue;
                    }
                    throw new IOException("Duplicate Reasons in DistributionPoint.");
                }
                if (derValue2.isContextSpecific((byte)2) && derValue2.isConstructed()) {
                    if (this.crlIssuer == null) {
                        derValue2.resetTag((byte)48);
                        this.crlIssuer = new GeneralNames(derValue2);
                        continue;
                    }
                    throw new IOException("Duplicate CRLIssuer in DistributionPoint.");
                }
                throw new IOException("Invalid encoding of DistributionPoint.");
            }
            if (this.crlIssuer == null && this.fullName == null && this.relativeName == null) {
                throw new IOException("One of fullName, relativeName,  and crlIssuer has to be set");
            }
            return;
        }
        throw new IOException("Invalid encoding of DistributionPoint.");
    }

    public DistributionPoint(GeneralNames generalNames, boolean[] arrbl, GeneralNames generalNames2) {
        if (generalNames == null && generalNames2 == null) {
            throw new IllegalArgumentException("fullName and crlIssuer may not both be null");
        }
        this.fullName = generalNames;
        this.reasonFlags = arrbl;
        this.crlIssuer = generalNames2;
    }

    public DistributionPoint(RDN rDN, boolean[] arrbl, GeneralNames generalNames) {
        if (rDN == null && generalNames == null) {
            throw new IllegalArgumentException("relativeName and crlIssuer may not both be null");
        }
        this.relativeName = rDN;
        this.reasonFlags = arrbl;
        this.crlIssuer = generalNames;
    }

    private static String reasonToString(int n) {
        Object object;
        if (n > 0 && n < ((String[])(object = REASON_STRINGS)).length) {
            return object[n];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown reason ");
        ((StringBuilder)object).append(n);
        return ((StringBuilder)object).toString();
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2;
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.fullName != null || this.relativeName != null) {
            derOutputStream2 = new DerOutputStream();
            if (this.fullName != null) {
                DerOutputStream derOutputStream4 = new DerOutputStream();
                this.fullName.encode(derOutputStream4);
                derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream4);
            } else if (this.relativeName != null) {
                DerOutputStream derOutputStream5 = new DerOutputStream();
                this.relativeName.encode(derOutputStream5);
                derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, true, (byte)1), derOutputStream5);
            }
            derOutputStream3.write(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream2);
        }
        if (this.reasonFlags != null) {
            derOutputStream2 = new DerOutputStream();
            derOutputStream2.putTruncatedUnalignedBitString(new BitArray(this.reasonFlags));
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream2);
        }
        if (this.crlIssuer != null) {
            derOutputStream2 = new DerOutputStream();
            this.crlIssuer.encode(derOutputStream2);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, true, (byte)2), derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream3);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof DistributionPoint)) {
            return false;
        }
        object = (DistributionPoint)object;
        if (!(Objects.equals(this.fullName, ((DistributionPoint)object).fullName) && Objects.equals(this.relativeName, ((DistributionPoint)object).relativeName) && Objects.equals(this.crlIssuer, ((DistributionPoint)object).crlIssuer) && Arrays.equals(this.reasonFlags, ((DistributionPoint)object).reasonFlags))) {
            bl = false;
        }
        return bl;
    }

    public GeneralNames getCRLIssuer() {
        return this.crlIssuer;
    }

    public GeneralNames getFullName() {
        return this.fullName;
    }

    public boolean[] getReasonFlags() {
        return this.reasonFlags;
    }

    public RDN getRelativeName() {
        return this.relativeName;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hashCode;
        if (n == 0) {
            n = 1;
            Object object = this.fullName;
            if (object != null) {
                n = 1 + object.hashCode();
            }
            object = this.relativeName;
            n2 = n;
            if (object != null) {
                n2 = n + object.hashCode();
            }
            object = this.crlIssuer;
            n = n2;
            if (object != null) {
                n = n2 + object.hashCode();
            }
            n2 = n;
            if (this.reasonFlags != null) {
                int n3 = 0;
                do {
                    object = this.reasonFlags;
                    n2 = n;
                    if (n3 >= ((boolean[])object).length) break;
                    n2 = n;
                    if (object[n3]) {
                        n2 = n + n3;
                    }
                    ++n3;
                    n = n2;
                } while (true);
            }
            this.hashCode = n2;
        }
        return n2;
    }

    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.fullName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("DistributionPoint:\n     ");
            ((StringBuilder)object).append(this.fullName);
            ((StringBuilder)object).append("\n");
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.relativeName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("DistributionPoint:\n     ");
            ((StringBuilder)object).append(this.relativeName);
            ((StringBuilder)object).append("\n");
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.reasonFlags != null) {
            stringBuilder.append("   ReasonFlags:\n");
            for (int i = 0; i < ((boolean[])(object = this.reasonFlags)).length; ++i) {
                if (!object[i]) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("    ");
                ((StringBuilder)object).append(DistributionPoint.reasonToString(i));
                ((StringBuilder)object).append("\n");
                stringBuilder.append(((StringBuilder)object).toString());
            }
        }
        if (this.crlIssuer != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("   CRLIssuer:");
            ((StringBuilder)object).append(this.crlIssuer);
            ((StringBuilder)object).append("\n");
            stringBuilder.append(((StringBuilder)object).toString());
        }
        return stringBuilder.toString();
    }
}

