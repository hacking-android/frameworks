/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;

public class EDIPartyName
implements GeneralNameInterface {
    private static final byte TAG_ASSIGNER = 0;
    private static final byte TAG_PARTYNAME = 1;
    private String assigner = null;
    private int myhash = -1;
    private String party = null;

    public EDIPartyName(String string) {
        this.party = string;
    }

    public EDIPartyName(String string, String string2) {
        this.assigner = string;
        this.party = string2;
    }

    public EDIPartyName(DerValue derValue) throws IOException {
        DerValue[] arrderValue = new DerInputStream(derValue.toByteArray()).getSequence(2);
        int n = arrderValue.length;
        if (n >= 1 && n <= 2) {
            for (int i = 0; i < n; ++i) {
                DerValue derValue2;
                derValue = derValue2 = arrderValue[i];
                if (derValue2.isContextSpecific((byte)0)) {
                    derValue = derValue2;
                    if (!derValue2.isConstructed()) {
                        if (this.assigner == null) {
                            derValue = derValue2.data.getDerValue();
                            this.assigner = derValue.getAsString();
                        } else {
                            throw new IOException("Duplicate nameAssigner found in EDIPartyName");
                        }
                    }
                }
                if (!derValue.isContextSpecific((byte)1) || derValue.isConstructed()) continue;
                if (this.party == null) {
                    this.party = derValue.data.getDerValue().getAsString();
                    continue;
                }
                throw new IOException("Duplicate partyName found in EDIPartyName");
            }
            return;
        }
        throw new IOException("Invalid encoding of EDIPartyName");
    }

    @Override
    public int constrains(GeneralNameInterface generalNameInterface) throws UnsupportedOperationException {
        if (generalNameInterface == null || generalNameInterface.getType() != 5) {
            return -1;
        }
        throw new UnsupportedOperationException("Narrowing, widening, and matching of names not supported for EDIPartyName");
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        Object object;
        DerOutputStream derOutputStream2 = new DerOutputStream();
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.assigner != null) {
            object = new DerOutputStream();
            ((DerOutputStream)object).putPrintableString(this.assigner);
            derOutputStream2.write(DerValue.createTag((byte)-128, false, (byte)0), (DerOutputStream)object);
        }
        if ((object = this.party) != null) {
            derOutputStream3.putPrintableString((String)object);
            derOutputStream2.write(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream3);
            derOutputStream.write((byte)48, derOutputStream2);
            return;
        }
        throw new IOException("Cannot have null partyName");
    }

    public boolean equals(Object object) {
        if (!(object instanceof EDIPartyName)) {
            return false;
        }
        String string = ((EDIPartyName)object).assigner;
        String string2 = this.assigner;
        if (string2 == null ? string != null : !string2.equals(string)) {
            return false;
        }
        string2 = ((EDIPartyName)object).party;
        object = this.party;
        return !(object == null ? string2 != null : !((String)object).equals(string2));
    }

    public String getAssignerName() {
        return this.assigner;
    }

    public String getPartyName() {
        return this.party;
    }

    @Override
    public int getType() {
        return 5;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            String string = this.party;
            int n = string == null ? 1 : string.hashCode();
            this.myhash = n + 37;
            string = this.assigner;
            if (string != null) {
                this.myhash = this.myhash * 37 + string.hashCode();
            }
        }
        return this.myhash;
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("subtreeDepth() not supported for EDIPartyName");
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EDIPartyName: ");
        if (this.assigner == null) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("  nameAssigner = ");
            ((StringBuilder)charSequence).append(this.assigner);
            ((StringBuilder)charSequence).append(",");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append("  partyName = ");
        stringBuilder.append(this.party);
        return stringBuilder.toString();
    }
}

