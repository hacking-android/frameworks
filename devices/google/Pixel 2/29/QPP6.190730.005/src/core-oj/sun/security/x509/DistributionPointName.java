/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Objects;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;

public class DistributionPointName {
    private static final byte TAG_FULL_NAME = 0;
    private static final byte TAG_RELATIVE_NAME = 1;
    private GeneralNames fullName;
    private volatile int hashCode;
    private RDN relativeName;

    public DistributionPointName(DerValue derValue) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    this.fullName = null;
                    this.relativeName = null;
                    if (!derValue.isContextSpecific((byte)0) || !derValue.isConstructed()) break block2;
                    derValue.resetTag((byte)48);
                    this.fullName = new GeneralNames(derValue);
                    break block3;
                }
                if (!derValue.isContextSpecific((byte)1) || !derValue.isConstructed()) break block4;
                derValue.resetTag((byte)49);
                this.relativeName = new RDN(derValue);
            }
            return;
        }
        throw new IOException("Invalid encoding for DistributionPointName");
    }

    public DistributionPointName(GeneralNames generalNames) {
        this.fullName = null;
        this.relativeName = null;
        if (generalNames != null) {
            this.fullName = generalNames;
            return;
        }
        throw new IllegalArgumentException("fullName must not be null");
    }

    public DistributionPointName(RDN rDN) {
        this.fullName = null;
        this.relativeName = null;
        if (rDN != null) {
            this.relativeName = rDN;
            return;
        }
        throw new IllegalArgumentException("relativeName must not be null");
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        GeneralNames generalNames = this.fullName;
        if (generalNames != null) {
            generalNames.encode(derOutputStream2);
            derOutputStream.writeImplicit(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream2);
        } else {
            this.relativeName.encode(derOutputStream2);
            derOutputStream.writeImplicit(DerValue.createTag((byte)-128, true, (byte)1), derOutputStream2);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof DistributionPointName)) {
            return false;
        }
        object = (DistributionPointName)object;
        if (!Objects.equals(this.fullName, ((DistributionPointName)object).fullName) || !Objects.equals(this.relativeName, ((DistributionPointName)object).relativeName)) {
            bl = false;
        }
        return bl;
    }

    public GeneralNames getFullName() {
        return this.fullName;
    }

    public RDN getRelativeName() {
        return this.relativeName;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hashCode;
        if (n == 0) {
            GeneralNames generalNames = this.fullName;
            n2 = generalNames != null ? 1 + generalNames.hashCode() : 1 + this.relativeName.hashCode();
            this.hashCode = n2;
        }
        return n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.fullName != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("DistributionPointName:\n     ");
            stringBuilder2.append(this.fullName);
            stringBuilder2.append("\n");
            stringBuilder.append(stringBuilder2.toString());
        } else {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("DistributionPointName:\n     ");
            stringBuilder3.append(this.relativeName);
            stringBuilder3.append("\n");
            stringBuilder.append(stringBuilder3.toString());
        }
        return stringBuilder.toString();
    }
}

