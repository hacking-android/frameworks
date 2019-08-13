/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.address.TelURLImpl;
import gov.nist.javax.sip.parser.StringMsgParser;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.TelURL;
import javax.sip.address.URI;

public class AddressFactoryImpl
implements AddressFactory {
    @Override
    public Address createAddress() {
        return new AddressImpl();
    }

    @Override
    public Address createAddress(String object) throws ParseException {
        if (object != null) {
            if (((String)object).equals("*")) {
                AddressImpl addressImpl = new AddressImpl();
                addressImpl.setAddressType(3);
                object = new SipUri();
                object.setUser("*");
                addressImpl.setURI((URI)object);
                return addressImpl;
            }
            return new StringMsgParser().parseAddress((String)object);
        }
        throw new NullPointerException("null address");
    }

    @Override
    public Address createAddress(String string, URI uRI) {
        if (uRI != null) {
            AddressImpl addressImpl = new AddressImpl();
            if (string != null) {
                addressImpl.setDisplayName(string);
            }
            addressImpl.setURI(uRI);
            return addressImpl;
        }
        throw new NullPointerException("null  URI");
    }

    @Override
    public Address createAddress(URI uRI) {
        if (uRI != null) {
            AddressImpl addressImpl = new AddressImpl();
            addressImpl.setURI(uRI);
            return addressImpl;
        }
        throw new NullPointerException("null address");
    }

    @Override
    public SipURI createSipURI(String object) throws ParseException {
        if (object != null) {
            try {
                StringMsgParser stringMsgParser = new StringMsgParser();
                object = stringMsgParser.parseSIPUrl((String)object);
                return object;
            }
            catch (ParseException parseException) {
                throw new ParseException(parseException.getMessage(), 0);
            }
        }
        throw new NullPointerException("null URI");
    }

    @Override
    public SipURI createSipURI(String object, String string) throws ParseException {
        if (string != null) {
            StringBuffer stringBuffer = new StringBuffer("sip:");
            if (object != null) {
                stringBuffer.append((String)object);
                stringBuffer.append("@");
            }
            object = string;
            if (string.indexOf(58) != string.lastIndexOf(58)) {
                object = string;
                if (string.trim().charAt(0) != '[') {
                    object = new StringBuilder();
                    ((StringBuilder)object).append('[');
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(']');
                    object = ((StringBuilder)object).toString();
                }
            }
            stringBuffer.append((String)object);
            object = new StringMsgParser();
            try {
                object = ((StringMsgParser)object).parseSIPUrl(stringBuffer.toString());
                return object;
            }
            catch (ParseException parseException) {
                throw new ParseException(parseException.getMessage(), 0);
            }
        }
        throw new NullPointerException("null host");
    }

    @Override
    public TelURL createTelURL(String object) throws ParseException {
        if (object != null) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("tel:");
            ((StringBuilder)object2).append((String)object);
            object = ((StringBuilder)object2).toString();
            try {
                object2 = new StringMsgParser();
                object = (TelURLImpl)((StringMsgParser)object2).parseUrl((String)object);
                return object;
            }
            catch (ParseException parseException) {
                throw new ParseException(parseException.getMessage(), 0);
            }
        }
        throw new NullPointerException("null url");
    }

    @Override
    public URI createURI(String object) throws ParseException {
        if (object != null) {
            block7 : {
                block8 : {
                    URLParser uRLParser;
                    String string;
                    try {
                        uRLParser = new URLParser((String)object);
                        string = uRLParser.peekScheme();
                        if (string == null) break block7;
                    }
                    catch (ParseException parseException) {
                        throw new ParseException(parseException.getMessage(), 0);
                    }
                    if (string.equalsIgnoreCase("sip")) {
                        return uRLParser.sipURL(true);
                    }
                    if (string.equalsIgnoreCase("sips")) {
                        return uRLParser.sipURL(true);
                    }
                    if (!string.equalsIgnoreCase("tel")) break block8;
                    object = uRLParser.telURL(true);
                    return object;
                }
                return new GenericURI((String)object);
            }
            object = new ParseException("bad scheme", 0);
            throw object;
        }
        throw new NullPointerException("null arg");
    }
}

