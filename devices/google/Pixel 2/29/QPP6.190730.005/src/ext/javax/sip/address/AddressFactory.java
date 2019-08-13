/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.address.TelURL;
import javax.sip.address.URI;

public interface AddressFactory {
    public Address createAddress();

    public Address createAddress(String var1) throws ParseException;

    public Address createAddress(String var1, URI var2) throws ParseException;

    public Address createAddress(URI var1);

    public SipURI createSipURI(String var1) throws ParseException;

    public SipURI createSipURI(String var1, String var2) throws ParseException;

    public TelURL createTelURL(String var1) throws ParseException;

    public URI createURI(String var1) throws ParseException;
}

