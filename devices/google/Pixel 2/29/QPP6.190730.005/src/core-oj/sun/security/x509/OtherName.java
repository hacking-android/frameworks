/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.OIDMap;

public class OtherName
implements GeneralNameInterface {
    private static final byte TAG_VALUE = 0;
    private GeneralNameInterface gni = null;
    private int myhash = -1;
    private String name;
    private byte[] nameValue = null;
    private ObjectIdentifier oid;

    public OtherName(DerValue object) throws IOException {
        object = ((DerValue)object).toDerInputStream();
        this.oid = ((DerInputStream)object).getOID();
        this.nameValue = ((DerInputStream)object).getDerValue().toByteArray();
        this.gni = this.getGNI(this.oid, this.nameValue);
        object = this.gni;
        if (object != null) {
            this.name = object.toString();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unrecognized ObjectIdentifier: ");
            ((StringBuilder)object).append(this.oid.toString());
            this.name = ((StringBuilder)object).toString();
        }
    }

    public OtherName(ObjectIdentifier objectIdentifier, byte[] object) throws IOException {
        if (objectIdentifier != null && object != null) {
            this.oid = objectIdentifier;
            this.nameValue = object;
            this.gni = this.getGNI(objectIdentifier, (byte[])object);
            object = this.gni;
            if (object != null) {
                this.name = object.toString();
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unrecognized ObjectIdentifier: ");
                ((StringBuilder)object).append(objectIdentifier.toString());
                this.name = ((StringBuilder)object).toString();
            }
            return;
        }
        throw new NullPointerException("parameters may not be null");
    }

    private GeneralNameInterface getGNI(ObjectIdentifier object, byte[] arrby) throws IOException {
        block3 : {
            try {
                object = OIDMap.getClass((ObjectIdentifier)object);
                if (object != null) break block3;
                return null;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Instantiation error: ");
                ((StringBuilder)object).append(exception);
                throw new IOException(((StringBuilder)object).toString(), exception);
            }
        }
        object = ((Class)object).getConstructor(Object.class);
        object = (GeneralNameInterface)((Constructor)object).newInstance(new Object[]{arrby});
        return object;
    }

    @Override
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 0) {
            return -1;
        }
        throw new UnsupportedOperationException("Narrowing, widening, and matching are not supported for OtherName.");
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        Object object = this.gni;
        if (object != null) {
            object.encode(derOutputStream);
            return;
        }
        object = new DerOutputStream();
        ((DerOutputStream)object).putOID(this.oid);
        ((DerOutputStream)object).write(DerValue.createTag((byte)-128, true, (byte)0), this.nameValue);
        derOutputStream.write((byte)48, (DerOutputStream)object);
    }

    public boolean equals(Object object) {
        boolean bl;
        block9 : {
            block8 : {
                GeneralNameInterface generalNameInterface;
                bl = true;
                if (this == object) {
                    return true;
                }
                if (!(object instanceof OtherName)) {
                    return false;
                }
                object = (OtherName)object;
                if (!((OtherName)object).oid.equals((Object)this.oid)) {
                    return false;
                }
                try {
                    generalNameInterface = this.getGNI(((OtherName)object).oid, ((OtherName)object).nameValue);
                    if (generalNameInterface == null) break block8;
                }
                catch (IOException iOException) {
                    return false;
                }
                try {
                    int n = generalNameInterface.constrains(this);
                    if (n != 0) {
                        bl = false;
                    }
                    break block9;
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                    bl = false;
                }
                break block9;
            }
            bl = Arrays.equals(this.nameValue, ((OtherName)object).nameValue);
        }
        return bl;
    }

    public byte[] getNameValue() {
        return (byte[])this.nameValue.clone();
    }

    public ObjectIdentifier getOID() {
        return this.oid;
    }

    @Override
    public int getType() {
        return 0;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            byte[] arrby;
            this.myhash = this.oid.hashCode() + 37;
            for (int i = 0; i < (arrby = this.nameValue).length; ++i) {
                this.myhash = this.myhash * 37 + arrby[i];
            }
        }
        return this.myhash;
    }

    @Override
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth() not supported for generic OtherName");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Other-Name: ");
        stringBuilder.append(this.name);
        return stringBuilder.toString();
    }
}

