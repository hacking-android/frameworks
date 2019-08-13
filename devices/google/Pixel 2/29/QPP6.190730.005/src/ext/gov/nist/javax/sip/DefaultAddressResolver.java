/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.stack.HopImpl;
import gov.nist.javax.sip.stack.MessageProcessor;
import javax.sip.address.Hop;

public class DefaultAddressResolver
implements AddressResolver {
    @Override
    public Hop resolveAddress(Hop hop) {
        if (hop.getPort() != -1) {
            return hop;
        }
        return new HopImpl(hop.getHost(), MessageProcessor.getDefaultPort(hop.getTransport()), hop.getTransport());
    }
}

