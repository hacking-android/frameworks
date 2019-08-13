/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import sun.security.x509.PKIXExtensions;

public class FreshestCRLExtension
extends CRLDistributionPointsExtension {
    public static final String NAME = "FreshestCRL";

    public FreshestCRLExtension(Boolean bl, Object object) throws IOException {
        super(PKIXExtensions.FreshestCRL_Id, bl, object, NAME);
    }

    public FreshestCRLExtension(List<DistributionPoint> list) throws IOException {
        super(PKIXExtensions.FreshestCRL_Id, false, list, NAME);
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        super.encode(outputStream, PKIXExtensions.FreshestCRL_Id, false);
    }
}

