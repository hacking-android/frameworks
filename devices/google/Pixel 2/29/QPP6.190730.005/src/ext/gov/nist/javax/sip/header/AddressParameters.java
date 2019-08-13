/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import java.util.Map;
import javax.sip.address.Address;
import javax.sip.header.Parameters;

public interface AddressParameters
extends Parameters {
    public Address getAddress();

    public Map<String, Map.Entry<String, String>> getParameters();

    public void setAddress(Address var1);
}

