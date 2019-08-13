/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.DistributionPoint;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class CRLDistributionPointsExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.CRLDistributionPoints";
    public static final String NAME = "CRLDistributionPoints";
    public static final String POINTS = "points";
    private List<DistributionPoint> distributionPoints;
    private String extensionName;

    public CRLDistributionPointsExtension(Boolean bl, Object object) throws IOException {
        this(PKIXExtensions.CRLDistributionPoints_Id, bl, object, NAME);
    }

    public CRLDistributionPointsExtension(List<DistributionPoint> list) throws IOException {
        this(false, list);
    }

    protected CRLDistributionPointsExtension(ObjectIdentifier object, Boolean object2, Object object3, String string) throws IOException {
        this.extensionId = object;
        this.critical = (Boolean)object2;
        if (object3 instanceof byte[]) {
            this.extensionValue = (byte[])object3;
            object2 = new DerValue(this.extensionValue);
            if (((DerValue)object2).tag == 48) {
                this.distributionPoints = new ArrayList<DistributionPoint>();
                while (((DerValue)object2).data.available() != 0) {
                    object = new DistributionPoint(((DerValue)object2).data.getDerValue());
                    this.distributionPoints.add((DistributionPoint)object);
                }
                this.extensionName = string;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid encoding for ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" extension.");
            throw new IOException(((StringBuilder)object).toString());
        }
        throw new IOException("Illegal argument type");
    }

    protected CRLDistributionPointsExtension(ObjectIdentifier objectIdentifier, boolean bl, List<DistributionPoint> list, String string) throws IOException {
        this.extensionId = objectIdentifier;
        this.critical = bl;
        this.distributionPoints = list;
        this.encodeThis();
        this.extensionName = string;
    }

    public CRLDistributionPointsExtension(boolean bl, List<DistributionPoint> list) throws IOException {
        this(PKIXExtensions.CRLDistributionPoints_Id, bl, list, NAME);
    }

    private void encodeThis() throws IOException {
        if (this.distributionPoints.isEmpty()) {
            this.extensionValue = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            Object object = this.distributionPoints.iterator();
            while (object.hasNext()) {
                object.next().encode(derOutputStream);
            }
            object = new DerOutputStream();
            ((DerOutputStream)object).write((byte)48, derOutputStream);
            this.extensionValue = ((ByteArrayOutputStream)object).toByteArray();
        }
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(POINTS)) {
            this.distributionPoints = Collections.emptyList();
            this.encodeThis();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:");
        stringBuilder.append(this.extensionName);
        stringBuilder.append('.');
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        this.encode(outputStream, PKIXExtensions.CRLDistributionPoints_Id, false);
    }

    protected void encode(OutputStream outputStream, ObjectIdentifier objectIdentifier, boolean bl) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = objectIdentifier;
            this.critical = bl;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public List<DistributionPoint> get(String string) throws IOException {
        if (string.equalsIgnoreCase(POINTS)) {
            return this.distributionPoints;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:");
        stringBuilder.append(this.extensionName);
        stringBuilder.append(".");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(POINTS);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return this.extensionName;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(POINTS)) {
            if (object instanceof List) {
                this.distributionPoints = (List)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type List.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:");
        ((StringBuilder)object).append(this.extensionName);
        ((StringBuilder)object).append(".");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(this.extensionName);
        stringBuilder.append(" [\n  ");
        stringBuilder.append(this.distributionPoints);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

