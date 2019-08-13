/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.GeneralName;

public final class AccessDescription {
    public static final ObjectIdentifier Ad_CAISSUERS_Id;
    public static final ObjectIdentifier Ad_CAREPOSITORY_Id;
    public static final ObjectIdentifier Ad_OCSP_Id;
    public static final ObjectIdentifier Ad_TIMESTAMPING_Id;
    private GeneralName accessLocation;
    private ObjectIdentifier accessMethod;
    private int myhash = -1;

    static {
        Ad_OCSP_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 1});
        Ad_CAISSUERS_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 2});
        Ad_TIMESTAMPING_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 3});
        Ad_CAREPOSITORY_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 5});
    }

    public AccessDescription(DerValue object) throws IOException {
        object = ((DerValue)object).getData();
        this.accessMethod = ((DerInputStream)object).getOID();
        this.accessLocation = new GeneralName(((DerInputStream)object).getDerValue());
    }

    public AccessDescription(ObjectIdentifier objectIdentifier, GeneralName generalName) {
        this.accessMethod = objectIdentifier;
        this.accessLocation = generalName;
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.accessMethod);
        this.accessLocation.encode(derOutputStream2);
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof AccessDescription) {
            if (this == (object = (AccessDescription)object)) {
                return true;
            }
            if (this.accessMethod.equals((Object)((AccessDescription)object).getAccessMethod()) && this.accessLocation.equals(((AccessDescription)object).getAccessLocation())) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public GeneralName getAccessLocation() {
        return this.accessLocation;
    }

    public ObjectIdentifier getAccessMethod() {
        return this.accessMethod;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.accessMethod.hashCode() + this.accessLocation.hashCode();
        }
        return this.myhash;
    }

    public String toString() {
        String string = this.accessMethod.equals((Object)Ad_CAISSUERS_Id) ? "caIssuers" : (this.accessMethod.equals((Object)Ad_CAREPOSITORY_Id) ? "caRepository" : (this.accessMethod.equals((Object)Ad_TIMESTAMPING_Id) ? "timeStamping" : (this.accessMethod.equals((Object)Ad_OCSP_Id) ? "ocsp" : this.accessMethod.toString())));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n   accessMethod: ");
        stringBuilder.append(string);
        stringBuilder.append("\n   accessLocation: ");
        stringBuilder.append(this.accessLocation.toString());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

