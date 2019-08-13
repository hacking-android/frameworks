/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class DeltaCRLIndicatorExtension
extends CRLNumberExtension {
    private static final String LABEL = "Base CRL Number";
    public static final String NAME = "DeltaCRLIndicator";

    public DeltaCRLIndicatorExtension(int n) throws IOException {
        super(PKIXExtensions.DeltaCRLIndicator_Id, true, BigInteger.valueOf(n), NAME, LABEL);
    }

    public DeltaCRLIndicatorExtension(Boolean bl, Object object) throws IOException {
        super(PKIXExtensions.DeltaCRLIndicator_Id, bl, object, NAME, LABEL);
    }

    public DeltaCRLIndicatorExtension(BigInteger bigInteger) throws IOException {
        super(PKIXExtensions.DeltaCRLIndicator_Id, true, bigInteger, NAME, LABEL);
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        new DerOutputStream();
        super.encode(outputStream, PKIXExtensions.DeltaCRLIndicator_Id, true);
    }
}

