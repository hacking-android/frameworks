/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.GeneralNames;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.SerialNumber;

public class AuthorityKeyIdentifierExtension
extends Extension
implements CertAttrSet<String> {
    public static final String AUTH_NAME = "auth_name";
    public static final String IDENT = "x509.info.extensions.AuthorityKeyIdentifier";
    public static final String KEY_ID = "key_id";
    public static final String NAME = "AuthorityKeyIdentifier";
    public static final String SERIAL_NUMBER = "serial_number";
    private static final byte TAG_ID = 0;
    private static final byte TAG_NAMES = 1;
    private static final byte TAG_SERIAL_NUM = 2;
    private KeyIdentifier id = null;
    private GeneralNames names = null;
    private SerialNumber serialNum = null;

    public AuthorityKeyIdentifierExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.AuthorityKey_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object2 = new DerValue(this.extensionValue);
        if (((DerValue)object2).tag == 48) {
            while (((DerValue)object2).data != null && ((DerValue)object2).data.available() != 0) {
                object = ((DerValue)object2).data.getDerValue();
                if (((DerValue)object).isContextSpecific((byte)0) && !((DerValue)object).isConstructed()) {
                    if (this.id == null) {
                        ((DerValue)object).resetTag((byte)4);
                        this.id = new KeyIdentifier((DerValue)object);
                        continue;
                    }
                    throw new IOException("Duplicate KeyIdentifier in AuthorityKeyIdentifier.");
                }
                if (((DerValue)object).isContextSpecific((byte)1) && ((DerValue)object).isConstructed()) {
                    if (this.names == null) {
                        ((DerValue)object).resetTag((byte)48);
                        this.names = new GeneralNames((DerValue)object);
                        continue;
                    }
                    throw new IOException("Duplicate GeneralNames in AuthorityKeyIdentifier.");
                }
                if (((DerValue)object).isContextSpecific((byte)2) && !((DerValue)object).isConstructed()) {
                    if (this.serialNum == null) {
                        ((DerValue)object).resetTag((byte)2);
                        this.serialNum = new SerialNumber((DerValue)object);
                        continue;
                    }
                    throw new IOException("Duplicate SerialNumber in AuthorityKeyIdentifier.");
                }
                throw new IOException("Invalid encoding of AuthorityKeyIdentifierExtension.");
            }
            return;
        }
        throw new IOException("Invalid encoding for AuthorityKeyIdentifierExtension.");
    }

    public AuthorityKeyIdentifierExtension(KeyIdentifier keyIdentifier, GeneralNames generalNames, SerialNumber serialNumber) throws IOException {
        this.id = keyIdentifier;
        this.names = generalNames;
        this.serialNum = serialNumber;
        this.extensionId = PKIXExtensions.AuthorityKey_Id;
        this.critical = false;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream;
        DerOutputStream derOutputStream2;
        block5 : {
            DerOutputStream derOutputStream3;
            if (this.id == null && this.names == null && this.serialNum == null) {
                this.extensionValue = null;
                return;
            }
            derOutputStream2 = new DerOutputStream();
            derOutputStream = new DerOutputStream();
            if (this.id != null) {
                derOutputStream3 = new DerOutputStream();
                this.id.encode(derOutputStream3);
                derOutputStream.writeImplicit(DerValue.createTag((byte)-128, false, (byte)0), derOutputStream3);
            }
            try {
                if (this.names != null) {
                    derOutputStream3 = new DerOutputStream();
                    this.names.encode(derOutputStream3);
                    derOutputStream.writeImplicit(DerValue.createTag((byte)-128, true, (byte)1), derOutputStream3);
                }
                if (this.serialNum == null) break block5;
                derOutputStream3 = new DerOutputStream();
            }
            catch (Exception exception) {
                throw new IOException(exception.toString());
            }
            this.serialNum.encode(derOutputStream3);
            derOutputStream.writeImplicit(DerValue.createTag((byte)-128, false, (byte)2), derOutputStream3);
        }
        derOutputStream2.write((byte)48, derOutputStream);
        this.extensionValue = derOutputStream2.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (!string.equalsIgnoreCase(KEY_ID)) break block2;
                        this.id = null;
                        break block3;
                    }
                    if (!string.equalsIgnoreCase(AUTH_NAME)) break block4;
                    this.names = null;
                    break block3;
                }
                if (!string.equalsIgnoreCase(SERIAL_NUMBER)) break block5;
                this.serialNum = null;
            }
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.AuthorityKey_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Object get(String string) throws IOException {
        if (string.equalsIgnoreCase(KEY_ID)) {
            return this.id;
        }
        if (string.equalsIgnoreCase(AUTH_NAME)) {
            return this.names;
        }
        if (string.equalsIgnoreCase(SERIAL_NUMBER)) {
            return this.serialNum;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(KEY_ID);
        attributeNameEnumeration.addElement(AUTH_NAME);
        attributeNameEnumeration.addElement(SERIAL_NUMBER);
        return attributeNameEnumeration.elements();
    }

    public byte[] getEncodedKeyIdentifier() throws IOException {
        if (this.id != null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            this.id.encode(derOutputStream);
            return derOutputStream.toByteArray();
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block7 : {
            block8 : {
                block4 : {
                    block5 : {
                        block6 : {
                            block2 : {
                                block3 : {
                                    if (!string.equalsIgnoreCase(KEY_ID)) break block2;
                                    if (!(object instanceof KeyIdentifier)) break block3;
                                    this.id = (KeyIdentifier)object;
                                    break block4;
                                }
                                throw new IOException("Attribute value should be of type KeyIdentifier.");
                            }
                            if (!string.equalsIgnoreCase(AUTH_NAME)) break block5;
                            if (!(object instanceof GeneralNames)) break block6;
                            this.names = (GeneralNames)object;
                            break block4;
                        }
                        throw new IOException("Attribute value should be of type GeneralNames.");
                    }
                    if (!string.equalsIgnoreCase(SERIAL_NUMBER)) break block7;
                    if (!(object instanceof SerialNumber)) break block8;
                    this.serialNum = (SerialNumber)object;
                }
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type SerialNumber.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(super.toString());
        ((StringBuilder)charSequence).append("AuthorityKeyIdentifier [\n");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.id != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.id.toString());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.names != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.names.toString());
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.serialNum != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.serialNum.toString());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("]\n");
        return ((StringBuilder)charSequence2).toString();
    }
}

