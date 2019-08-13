/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import javax.sip.address.Hop;
import javax.sip.address.Router;

public interface RouterExt
extends Router {
    public void transactionTimeout(Hop var1);
}

