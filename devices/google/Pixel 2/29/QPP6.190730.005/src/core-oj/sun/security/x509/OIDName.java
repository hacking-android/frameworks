/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.GeneralNameInterface;

public class OIDName
implements GeneralNameInterface {
    private ObjectIdentifier oid;

    public OIDName(String string) throws IOException {
        try {
            ObjectIdentifier objectIdentifier;
            this.oid = objectIdentifier = new ObjectIdentifier(string);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create OIDName: ");
            stringBuilder.append(exception);
            throw new IOException(stringBuilder.toString());
        }
    }

    public OIDName(DerValue derValue) throws IOException {
        this.oid = derValue.getOID();
    }

    public OIDName(ObjectIdentifier objectIdentifier) {
        this.oid = objectIdentifier;
    }

    @Override
    public int constrains(GeneralNameInterface generalNameInterface) throws UnsupportedOperationException {
        block5 : {
            int n;
            block3 : {
                block4 : {
                    block2 : {
                        if (generalNameInterface != null) break block2;
                        n = -1;
                        break block3;
                    }
                    if (generalNameInterface.getType() == 8) break block4;
                    n = -1;
                    break block3;
                }
                if (!this.equals((OIDName)generalNameInterface)) break block5;
                n = 0;
            }
            return n;
        }
        throw new UnsupportedOperationException("Narrowing and widening are not supported for OIDNames");
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putOID(this.oid);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OIDName)) {
            return false;
        }
        object = (OIDName)object;
        return this.oid.equals((Object)((OIDName)object).oid);
    }

    public ObjectIdentifier getOID() {
        return this.oid;
    }

    @Override
    public int getType() {
        return 8;
    }

    public int hashCode() {
        return this.oid.hashCode();
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("subtreeDepth() not supported for OIDName.");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OIDName: ");
        stringBuilder.append(this.oid.toString());
        return stringBuilder.toString();
    }
}

