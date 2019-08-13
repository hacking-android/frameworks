/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class Extension
implements java.security.cert.Extension {
    private static final int hashMagic = 31;
    protected boolean critical = false;
    protected ObjectIdentifier extensionId = null;
    protected byte[] extensionValue = null;

    public Extension() {
    }

    public Extension(DerValue derValue) throws IOException {
        DerInputStream derInputStream = derValue.toDerInputStream();
        this.extensionId = derInputStream.getOID();
        derValue = derInputStream.getDerValue();
        if (derValue.tag == 1) {
            this.critical = derValue.getBoolean();
            this.extensionValue = derInputStream.getDerValue().getOctetString();
        } else {
            this.critical = false;
            this.extensionValue = derValue.getOctetString();
        }
    }

    public Extension(ObjectIdentifier objectIdentifier, boolean bl, byte[] arrby) throws IOException {
        this.extensionId = objectIdentifier;
        this.critical = bl;
        this.extensionValue = new DerValue(arrby).getOctetString();
    }

    public Extension(Extension extension) {
        this.extensionId = extension.extensionId;
        this.critical = extension.critical;
        this.extensionValue = extension.extensionValue;
    }

    public static Extension newExtension(ObjectIdentifier objectIdentifier, boolean bl, byte[] arrby) throws IOException {
        Extension extension = new Extension();
        extension.extensionId = objectIdentifier;
        extension.critical = bl;
        extension.extensionValue = arrby;
        return extension;
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            derOutputStream.putOID(this.extensionId);
            boolean bl = this.critical;
            if (bl) {
                derOutputStream.putBoolean(bl);
            }
            derOutputStream.putOctetString(this.extensionValue);
            derOutputStream2.write((byte)48, derOutputStream);
            outputStream.write(derOutputStream2.toByteArray());
            return;
        }
        throw new NullPointerException();
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        if (this.extensionId != null) {
            if (this.extensionValue != null) {
                DerOutputStream derOutputStream2 = new DerOutputStream();
                derOutputStream2.putOID(this.extensionId);
                boolean bl = this.critical;
                if (bl) {
                    derOutputStream2.putBoolean(bl);
                }
                derOutputStream2.putOctetString(this.extensionValue);
                derOutputStream.write((byte)48, derOutputStream2);
                return;
            }
            throw new IOException("No value to encode for the extension!");
        }
        throw new IOException("Null OID to encode for the extension!");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Extension)) {
            return false;
        }
        object = (Extension)object;
        if (this.critical != ((Extension)object).critical) {
            return false;
        }
        if (!this.extensionId.equals((Object)((Extension)object).extensionId)) {
            return false;
        }
        return Arrays.equals(this.extensionValue, ((Extension)object).extensionValue);
    }

    public ObjectIdentifier getExtensionId() {
        return this.extensionId;
    }

    public byte[] getExtensionValue() {
        return this.extensionValue;
    }

    @Override
    public String getId() {
        return this.extensionId.toString();
    }

    @Override
    public byte[] getValue() {
        return (byte[])this.extensionValue.clone();
    }

    public int hashCode() {
        int n;
        int n2 = 0;
        int n3 = 0;
        if (this.extensionValue != null) {
            byte[] arrby = this.extensionValue;
            n = arrby.length;
            do {
                n2 = n3;
                if (n <= 0) break;
                n2 = n - 1;
                n3 += n * arrby[n2];
                n = n2;
            } while (true);
        }
        n = this.extensionId.hashCode();
        n3 = this.critical ? 1231 : 1237;
        return (n2 * 31 + n) * 31 + n3;
    }

    @Override
    public boolean isCritical() {
        return this.critical;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("ObjectId: ");
        charSequence.append(this.extensionId.toString());
        charSequence = charSequence.toString();
        if (this.critical) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" Criticality=true\n");
            charSequence = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" Criticality=false\n");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }
}

