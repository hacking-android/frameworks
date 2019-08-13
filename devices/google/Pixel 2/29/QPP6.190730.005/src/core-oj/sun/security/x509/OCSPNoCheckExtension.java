/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Enumeration;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class OCSPNoCheckExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.OCSPNoCheck";
    public static final String NAME = "OCSPNoCheck";

    public OCSPNoCheckExtension() throws IOException {
        this.extensionId = PKIXExtensions.OCSPNoCheck_Id;
        this.critical = false;
        this.extensionValue = new byte[0];
    }

    public OCSPNoCheckExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = PKIXExtensions.OCSPNoCheck_Id;
        this.critical = bl;
        this.extensionValue = new byte[0];
    }

    @Override
    public void delete(String string) throws IOException {
        throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
    }

    @Override
    public Object get(String string) throws IOException {
        throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
    }

    @Override
    public Enumeration<String> getElements() {
        return new AttributeNameEnumeration().elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
    }
}

