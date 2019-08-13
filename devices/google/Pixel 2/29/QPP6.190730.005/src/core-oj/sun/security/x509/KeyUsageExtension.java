/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.BitArray;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class KeyUsageExtension
extends Extension
implements CertAttrSet<String> {
    public static final String CRL_SIGN = "crl_sign";
    public static final String DATA_ENCIPHERMENT = "data_encipherment";
    public static final String DECIPHER_ONLY = "decipher_only";
    public static final String DIGITAL_SIGNATURE = "digital_signature";
    public static final String ENCIPHER_ONLY = "encipher_only";
    public static final String IDENT = "x509.info.extensions.KeyUsage";
    public static final String KEY_AGREEMENT = "key_agreement";
    public static final String KEY_CERTSIGN = "key_certsign";
    public static final String KEY_ENCIPHERMENT = "key_encipherment";
    public static final String NAME = "KeyUsage";
    public static final String NON_REPUDIATION = "non_repudiation";
    private boolean[] bitString;

    public KeyUsageExtension() {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        this.bitString = new boolean[0];
    }

    public KeyUsageExtension(Boolean arrby, Object object) throws IOException {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = arrby.booleanValue();
        arrby = (byte[])object;
        this.extensionValue = arrby[0] == 4 ? new DerValue(arrby).getOctetString() : arrby;
        this.bitString = new DerValue(this.extensionValue).getUnalignedBitString().toBooleanArray();
    }

    public KeyUsageExtension(BitArray bitArray) throws IOException {
        this.bitString = bitArray.toBooleanArray();
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        this.encodeThis();
    }

    public KeyUsageExtension(byte[] arrby) throws IOException {
        this.bitString = new BitArray(arrby.length * 8, arrby).toBooleanArray();
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        this.encodeThis();
    }

    public KeyUsageExtension(boolean[] arrbl) throws IOException {
        this.bitString = arrbl;
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
        this.extensionValue = derOutputStream.toByteArray();
    }

    private boolean isSet(int n) {
        boolean[] arrbl = this.bitString;
        boolean bl = n < arrbl.length && arrbl[n];
        return bl;
    }

    private void set(int n, boolean bl) {
        boolean[] arrbl = this.bitString;
        if (n >= arrbl.length) {
            boolean[] arrbl2 = new boolean[n + 1];
            System.arraycopy((Object)arrbl, 0, (Object)arrbl2, 0, arrbl.length);
            this.bitString = arrbl2;
        }
        this.bitString[n] = bl;
    }

    @Override
    public void delete(String string) throws IOException {
        block11 : {
            block3 : {
                block10 : {
                    block9 : {
                        block8 : {
                            block7 : {
                                block6 : {
                                    block5 : {
                                        block4 : {
                                            block2 : {
                                                if (!string.equalsIgnoreCase(DIGITAL_SIGNATURE)) break block2;
                                                this.set(0, false);
                                                break block3;
                                            }
                                            if (!string.equalsIgnoreCase(NON_REPUDIATION)) break block4;
                                            this.set(1, false);
                                            break block3;
                                        }
                                        if (!string.equalsIgnoreCase(KEY_ENCIPHERMENT)) break block5;
                                        this.set(2, false);
                                        break block3;
                                    }
                                    if (!string.equalsIgnoreCase(DATA_ENCIPHERMENT)) break block6;
                                    this.set(3, false);
                                    break block3;
                                }
                                if (!string.equalsIgnoreCase(KEY_AGREEMENT)) break block7;
                                this.set(4, false);
                                break block3;
                            }
                            if (!string.equalsIgnoreCase(KEY_CERTSIGN)) break block8;
                            this.set(5, false);
                            break block3;
                        }
                        if (!string.equalsIgnoreCase(CRL_SIGN)) break block9;
                        this.set(6, false);
                        break block3;
                    }
                    if (!string.equalsIgnoreCase(ENCIPHER_ONLY)) break block10;
                    this.set(7, false);
                    break block3;
                }
                if (!string.equalsIgnoreCase(DECIPHER_ONLY)) break block11;
                this.set(8, false);
            }
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.KeyUsage_Id;
            this.critical = true;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Boolean get(String string) throws IOException {
        if (string.equalsIgnoreCase(DIGITAL_SIGNATURE)) {
            return this.isSet(0);
        }
        if (string.equalsIgnoreCase(NON_REPUDIATION)) {
            return this.isSet(1);
        }
        if (string.equalsIgnoreCase(KEY_ENCIPHERMENT)) {
            return this.isSet(2);
        }
        if (string.equalsIgnoreCase(DATA_ENCIPHERMENT)) {
            return this.isSet(3);
        }
        if (string.equalsIgnoreCase(KEY_AGREEMENT)) {
            return this.isSet(4);
        }
        if (string.equalsIgnoreCase(KEY_CERTSIGN)) {
            return this.isSet(5);
        }
        if (string.equalsIgnoreCase(CRL_SIGN)) {
            return this.isSet(6);
        }
        if (string.equalsIgnoreCase(ENCIPHER_ONLY)) {
            return this.isSet(7);
        }
        if (string.equalsIgnoreCase(DECIPHER_ONLY)) {
            return this.isSet(8);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
    }

    public boolean[] getBits() {
        return (boolean[])this.bitString.clone();
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(DIGITAL_SIGNATURE);
        attributeNameEnumeration.addElement(NON_REPUDIATION);
        attributeNameEnumeration.addElement(KEY_ENCIPHERMENT);
        attributeNameEnumeration.addElement(DATA_ENCIPHERMENT);
        attributeNameEnumeration.addElement(KEY_AGREEMENT);
        attributeNameEnumeration.addElement(KEY_CERTSIGN);
        attributeNameEnumeration.addElement(CRL_SIGN);
        attributeNameEnumeration.addElement(ENCIPHER_ONLY);
        attributeNameEnumeration.addElement(DECIPHER_ONLY);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block2 : {
            block12 : {
                block4 : {
                    boolean bl;
                    block11 : {
                        block10 : {
                            block9 : {
                                block8 : {
                                    block7 : {
                                        block6 : {
                                            block5 : {
                                                block3 : {
                                                    if (!(object instanceof Boolean)) break block2;
                                                    bl = (Boolean)object;
                                                    if (!string.equalsIgnoreCase(DIGITAL_SIGNATURE)) break block3;
                                                    this.set(0, bl);
                                                    break block4;
                                                }
                                                if (!string.equalsIgnoreCase(NON_REPUDIATION)) break block5;
                                                this.set(1, bl);
                                                break block4;
                                            }
                                            if (!string.equalsIgnoreCase(KEY_ENCIPHERMENT)) break block6;
                                            this.set(2, bl);
                                            break block4;
                                        }
                                        if (!string.equalsIgnoreCase(DATA_ENCIPHERMENT)) break block7;
                                        this.set(3, bl);
                                        break block4;
                                    }
                                    if (!string.equalsIgnoreCase(KEY_AGREEMENT)) break block8;
                                    this.set(4, bl);
                                    break block4;
                                }
                                if (!string.equalsIgnoreCase(KEY_CERTSIGN)) break block9;
                                this.set(5, bl);
                                break block4;
                            }
                            if (!string.equalsIgnoreCase(CRL_SIGN)) break block10;
                            this.set(6, bl);
                            break block4;
                        }
                        if (!string.equalsIgnoreCase(ENCIPHER_ONLY)) break block11;
                        this.set(7, bl);
                        break block4;
                    }
                    if (!string.equalsIgnoreCase(DECIPHER_ONLY)) break block12;
                    this.set(8, bl);
                }
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
        }
        throw new IOException("Attribute must be of type Boolean.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("KeyUsage [\n");
        if (this.isSet(0)) {
            stringBuilder.append("  DigitalSignature\n");
        }
        if (this.isSet(1)) {
            stringBuilder.append("  Non_repudiation\n");
        }
        if (this.isSet(2)) {
            stringBuilder.append("  Key_Encipherment\n");
        }
        if (this.isSet(3)) {
            stringBuilder.append("  Data_Encipherment\n");
        }
        if (this.isSet(4)) {
            stringBuilder.append("  Key_Agreement\n");
        }
        if (this.isSet(5)) {
            stringBuilder.append("  Key_CertSign\n");
        }
        if (this.isSet(6)) {
            stringBuilder.append("  Crl_Sign\n");
        }
        if (this.isSet(7)) {
            stringBuilder.append("  Encipher_Only\n");
        }
        if (this.isSet(8)) {
            stringBuilder.append("  Decipher_Only\n");
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

