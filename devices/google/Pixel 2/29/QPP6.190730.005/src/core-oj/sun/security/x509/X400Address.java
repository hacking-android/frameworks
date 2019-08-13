/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;

public class X400Address
implements GeneralNameInterface {
    byte[] nameValue = null;

    public X400Address(DerValue derValue) throws IOException {
        this.nameValue = derValue.toByteArray();
    }

    public X400Address(byte[] arrby) {
        this.nameValue = arrby;
    }

    @Override
    public int constrains(GeneralNameInterface generalNameInterface) throws UnsupportedOperationException {
        if (generalNameInterface == null || generalNameInterface.getType() != 3) {
            return -1;
        }
        throw new UnsupportedOperationException("Narrowing, widening, and match are not supported for X400Address.");
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putDerValue(new DerValue(this.nameValue));
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("subtreeDepth not supported for X400Address");
    }

    public String toString() {
        return "X400Address: <DER-encoded value>";
    }
}

