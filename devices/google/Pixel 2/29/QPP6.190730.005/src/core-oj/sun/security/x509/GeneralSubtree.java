/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralName;

public class GeneralSubtree {
    private static final int MIN_DEFAULT = 0;
    private static final byte TAG_MAX = 1;
    private static final byte TAG_MIN = 0;
    private int maximum = -1;
    private int minimum = 0;
    private int myhash = -1;
    private GeneralName name;

    public GeneralSubtree(DerValue derValue) throws IOException {
        if (derValue.tag == 48) {
            this.name = new GeneralName(derValue.data.getDerValue(), true);
            while (derValue.data.available() != 0) {
                DerValue derValue2 = derValue.data.getDerValue();
                if (derValue2.isContextSpecific((byte)0) && !derValue2.isConstructed()) {
                    derValue2.resetTag((byte)2);
                    this.minimum = derValue2.getInteger();
                    continue;
                }
                if (derValue2.isContextSpecific((byte)1) && !derValue2.isConstructed()) {
                    derValue2.resetTag((byte)2);
                    this.maximum = derValue2.getInteger();
                    continue;
                }
                throw new IOException("Invalid encoding of GeneralSubtree.");
            }
            return;
        }
        throw new IOException("Invalid encoding for GeneralSubtree.");
    }

    public GeneralSubtree(GeneralName generalName, int n, int n2) {
        this.name = generalName;
        this.minimum = n;
        this.maximum = n2;
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2;
        DerOutputStream derOutputStream3 = new DerOutputStream();
        this.name.encode(derOutputStream3);
        if (this.minimum != 0) {
            derOutputStream2 = new DerOutputStream();
            derOutputStream2.putInteger(this.minimum);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, false, (byte)0), derOutputStream2);
        }
        if (this.maximum != -1) {
            derOutputStream2 = new DerOutputStream();
            derOutputStream2.putInteger(this.maximum);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream3);
    }

    public boolean equals(Object object) {
        if (!(object instanceof GeneralSubtree)) {
            return false;
        }
        GeneralSubtree generalSubtree = (GeneralSubtree)object;
        object = this.name;
        if (object == null ? generalSubtree.name != null : !((GeneralName)object).equals(generalSubtree.name)) {
            return false;
        }
        if (this.minimum != generalSubtree.minimum) {
            return false;
        }
        return this.maximum == generalSubtree.maximum;
    }

    public int getMaximum() {
        return this.maximum;
    }

    public int getMinimum() {
        return this.minimum;
    }

    public GeneralName getName() {
        return this.name;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            int n;
            this.myhash = 17;
            GeneralName generalName = this.name;
            if (generalName != null) {
                this.myhash = this.myhash * 37 + generalName.hashCode();
            }
            if ((n = this.minimum) != 0) {
                this.myhash = this.myhash * 37 + n;
            }
            if ((n = this.maximum) != -1) {
                this.myhash = this.myhash * 37 + n;
            }
        }
        return this.myhash;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n   GeneralSubtree: [\n    GeneralName: ");
        Object object = this.name;
        object = object == null ? "" : ((GeneralName)object).toString();
        stringBuilder.append((String)object);
        stringBuilder.append("\n    Minimum: ");
        stringBuilder.append(this.minimum);
        object = stringBuilder.toString();
        if (this.maximum == -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("\t    Maximum: undefined");
            object = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("\t    Maximum: ");
            stringBuilder.append(this.maximum);
            object = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append("    ]\n");
        return stringBuilder.toString();
    }
}

