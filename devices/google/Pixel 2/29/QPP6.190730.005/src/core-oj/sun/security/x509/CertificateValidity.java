/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Date;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;

public class CertificateValidity
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.validity";
    public static final String NAME = "validity";
    public static final String NOT_AFTER = "notAfter";
    public static final String NOT_BEFORE = "notBefore";
    private static final long YR_2050 = 2524636800000L;
    private Date notAfter;
    private Date notBefore;

    public CertificateValidity() {
    }

    public CertificateValidity(Date date, Date date2) {
        this.notBefore = date;
        this.notAfter = date2;
    }

    public CertificateValidity(DerInputStream derInputStream) throws IOException {
        this.construct(derInputStream.getDerValue());
    }

    private void construct(DerValue derValue) throws IOException {
        block2 : {
            block3 : {
                block4 : {
                    block7 : {
                        block10 : {
                            block9 : {
                                DerValue[] arrderValue;
                                block8 : {
                                    block6 : {
                                        block5 : {
                                            if (derValue.tag != 48) break block2;
                                            if (derValue.data.available() == 0) break block3;
                                            arrderValue = new DerInputStream(derValue.toByteArray()).getSequence(2);
                                            if (arrderValue.length != 2) break block4;
                                            if (arrderValue[0].tag != 23) break block5;
                                            this.notBefore = derValue.data.getUTCTime();
                                            break block6;
                                        }
                                        if (arrderValue[0].tag != 24) break block7;
                                        this.notBefore = derValue.data.getGeneralizedTime();
                                    }
                                    if (arrderValue[1].tag != 23) break block8;
                                    this.notAfter = derValue.data.getUTCTime();
                                    break block9;
                                }
                                if (arrderValue[1].tag != 24) break block10;
                                this.notAfter = derValue.data.getGeneralizedTime();
                            }
                            return;
                        }
                        throw new IOException("Invalid encoding for CertificateValidity");
                    }
                    throw new IOException("Invalid encoding for CertificateValidity");
                }
                throw new IOException("Invalid encoding for CertificateValidity");
            }
            throw new IOException("No data encoded for CertificateValidity");
        }
        throw new IOException("Invalid encoded CertificateValidity, starting sequence tag missing.");
    }

    private Date getNotAfter() {
        return new Date(this.notAfter.getTime());
    }

    private Date getNotBefore() {
        return new Date(this.notBefore.getTime());
    }

    @Override
    public void delete(String string) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!string.equalsIgnoreCase(NOT_BEFORE)) break block2;
                    this.notBefore = null;
                    break block3;
                }
                if (!string.equalsIgnoreCase(NOT_AFTER)) break block4;
                this.notAfter = null;
            }
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        if (this.notBefore != null && this.notAfter != null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            if (this.notBefore.getTime() < 2524636800000L) {
                derOutputStream.putUTCTime(this.notBefore);
            } else {
                derOutputStream.putGeneralizedTime(this.notBefore);
            }
            if (this.notAfter.getTime() < 2524636800000L) {
                derOutputStream.putUTCTime(this.notAfter);
            } else {
                derOutputStream.putGeneralizedTime(this.notAfter);
            }
            DerOutputStream derOutputStream2 = new DerOutputStream();
            derOutputStream2.write((byte)48, derOutputStream);
            outputStream.write(derOutputStream2.toByteArray());
            return;
        }
        throw new IOException("CertAttrSet:CertificateValidity: null values to encode.\n");
    }

    @Override
    public Date get(String string) throws IOException {
        if (string.equalsIgnoreCase(NOT_BEFORE)) {
            return this.getNotBefore();
        }
        if (string.equalsIgnoreCase(NOT_AFTER)) {
            return this.getNotAfter();
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(NOT_BEFORE);
        attributeNameEnumeration.addElement(NOT_AFTER);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (!(object instanceof Date)) break block2;
                        if (!string.equalsIgnoreCase(NOT_BEFORE)) break block3;
                        this.notBefore = (Date)object;
                        break block4;
                    }
                    if (!string.equalsIgnoreCase(NOT_AFTER)) break block5;
                    this.notAfter = (Date)object;
                }
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
        }
        throw new IOException("Attribute must be of type Date.");
    }

    @Override
    public String toString() {
        if (this.notBefore != null && this.notAfter != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Validity: [From: ");
            stringBuilder.append(this.notBefore.toString());
            stringBuilder.append(",\n               To: ");
            stringBuilder.append(this.notAfter.toString());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        return "";
    }

    public void valid() throws CertificateNotYetValidException, CertificateExpiredException {
        this.valid(new Date());
    }

    public void valid(Date serializable) throws CertificateNotYetValidException, CertificateExpiredException {
        if (!this.notBefore.after((Date)serializable)) {
            if (!this.notAfter.before((Date)serializable)) {
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("NotAfter: ");
            ((StringBuilder)serializable).append(this.notAfter.toString());
            throw new CertificateExpiredException(((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("NotBefore: ");
        ((StringBuilder)serializable).append(this.notBefore.toString());
        throw new CertificateNotYetValidException(((StringBuilder)serializable).toString());
    }
}

