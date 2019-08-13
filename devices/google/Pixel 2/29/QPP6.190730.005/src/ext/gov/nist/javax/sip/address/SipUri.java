/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.Debug;
import gov.nist.core.GenericObject;
import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.Authority;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.NetObject;
import gov.nist.javax.sip.address.RFC2396UrlDecoder;
import gov.nist.javax.sip.address.SipURIExt;
import gov.nist.javax.sip.address.TelephoneNumber;
import gov.nist.javax.sip.address.UserInfo;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.SipURI;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;

public class SipUri
extends GenericURI
implements SipURI,
SipURIExt {
    private static final long serialVersionUID = 7749781076218987044L;
    protected Authority authority;
    protected NameValueList qheaders;
    protected TelephoneNumber telephoneSubscriber;
    protected NameValueList uriParms;

    public SipUri() {
        this.scheme = "sip";
        this.uriParms = new NameValueList();
        this.qheaders = new NameValueList();
        this.qheaders.setSeparator("&");
    }

    public void clearPassword() {
        NetObject netObject = this.authority;
        if (netObject != null && (netObject = ((Authority)netObject).getUserInfo()) != null) {
            ((UserInfo)netObject).clearPassword();
        }
    }

    public void clearQheaders() {
        this.qheaders = new NameValueList();
    }

    public void clearUriParms() {
        this.uriParms = new NameValueList();
    }

    @Override
    public Object clone() {
        SipUri sipUri = (SipUri)super.clone();
        Cloneable cloneable = this.authority;
        if (cloneable != null) {
            sipUri.authority = (Authority)((Authority)cloneable).clone();
        }
        if ((cloneable = this.uriParms) != null) {
            sipUri.uriParms = (NameValueList)((NameValueList)cloneable).clone();
        }
        if ((cloneable = this.qheaders) != null) {
            sipUri.qheaders = (NameValueList)((NameValueList)cloneable).clone();
        }
        if ((cloneable = this.telephoneSubscriber) != null) {
            sipUri.telephoneSubscriber = (TelephoneNumber)((TelephoneNumber)cloneable).clone();
        }
        return sipUri;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.scheme);
        stringBuffer.append(":");
        Authority authority = this.authority;
        if (authority != null) {
            authority.encode(stringBuffer);
        }
        if (!this.uriParms.isEmpty()) {
            stringBuffer.append(";");
            this.uriParms.encode(stringBuffer);
        }
        if (!this.qheaders.isEmpty()) {
            stringBuffer.append("?");
            this.qheaders.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof SipURI) {
            CharSequence charSequence;
            String string;
            boolean bl;
            object = (SipURI)object;
            if (this.isSecure() ^ object.isSecure()) {
                return false;
            }
            boolean bl2 = this.getUser() == null;
            if (bl2 ^ (bl = object.getUser() == null)) {
                return false;
            }
            bl2 = this.getUserPassword() == null;
            if (bl2 ^ (bl = object.getUserPassword() == null)) {
                return false;
            }
            if (this.getUser() != null && !RFC2396UrlDecoder.decode(this.getUser()).equals(RFC2396UrlDecoder.decode(object.getUser()))) {
                return false;
            }
            if (this.getUserPassword() != null && !RFC2396UrlDecoder.decode(this.getUserPassword()).equals(RFC2396UrlDecoder.decode(object.getUserPassword()))) {
                return false;
            }
            bl2 = this.getHost() == null;
            if (bl2 ^ (bl = object.getHost() == null)) {
                return false;
            }
            if (this.getHost() != null && !this.getHost().equalsIgnoreCase(object.getHost())) {
                return false;
            }
            if (this.getPort() != object.getPort()) {
                return false;
            }
            Object object2 = this.getParameterNames();
            while (object2.hasNext()) {
                string = (String)object2.next();
                charSequence = this.getParameter(string);
                string = object.getParameter(string);
                if (charSequence == null || string == null || RFC2396UrlDecoder.decode((String)charSequence).equalsIgnoreCase(RFC2396UrlDecoder.decode(string))) continue;
                return false;
            }
            bl2 = this.getTransportParam() == null;
            if (bl2 ^ (bl = object.getTransportParam() == null)) {
                return false;
            }
            bl2 = this.getUserParam() == null;
            if (bl2 ^ (bl = object.getUserParam() == null)) {
                return false;
            }
            bl2 = this.getTTLParam() == -1;
            if (bl2 ^ (bl = object.getTTLParam() == -1)) {
                return false;
            }
            bl2 = this.getMethodParam() == null;
            if (bl2 ^ (bl = object.getMethodParam() == null)) {
                return false;
            }
            bl2 = this.getMAddrParam() == null;
            if (bl2 ^ (bl = object.getMAddrParam() == null)) {
                return false;
            }
            if (this.getHeaderNames().hasNext() && !object.getHeaderNames().hasNext()) {
                return false;
            }
            if (!this.getHeaderNames().hasNext() && object.getHeaderNames().hasNext()) {
                return false;
            }
            if (this.getHeaderNames().hasNext() && object.getHeaderNames().hasNext()) {
                Iterator iterator;
                try {
                    object2 = SipFactory.getInstance().createHeaderFactory();
                    iterator = this.getHeaderNames();
                }
                catch (PeerUnavailableException peerUnavailableException) {
                    Debug.logError("Cannot get the header factory to parse the header of the sip uris to compare", peerUnavailableException);
                    return false;
                }
                while (iterator.hasNext()) {
                    string = (String)iterator.next();
                    charSequence = this.getHeader(string);
                    String string2 = object.getHeader(string);
                    if (charSequence == null && string2 != null) {
                        return false;
                    }
                    if (string2 == null && charSequence != null) {
                        return false;
                    }
                    if (charSequence == null && string2 == null) continue;
                    try {
                        boolean bl3 = object2.createHeader(string, RFC2396UrlDecoder.decode((String)charSequence)).equals(object2.createHeader(string, RFC2396UrlDecoder.decode(string2)));
                        if (bl3) continue;
                        return false;
                    }
                    catch (ParseException parseException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Cannot parse one of the header of the sip uris to compare ");
                        ((StringBuilder)charSequence).append(this);
                        ((StringBuilder)charSequence).append(" ");
                        ((StringBuilder)charSequence).append(object);
                        Debug.logError(((StringBuilder)charSequence).toString(), parseException);
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Authority getAuthority() {
        return this.authority;
    }

    public String getGrParam() {
        return (String)this.uriParms.getValue("gr");
    }

    @Override
    public String getHeader(String string) {
        string = this.qheaders.getValue(string) != null ? this.qheaders.getValue(string).toString() : null;
        return string;
    }

    @Override
    public Iterator<String> getHeaderNames() {
        return this.qheaders.getNames();
    }

    @Override
    public String getHost() {
        Authority authority = this.authority;
        if (authority == null) {
            return null;
        }
        if (authority.getHost() == null) {
            return null;
        }
        return this.authority.getHost().encode();
    }

    public HostPort getHostPort() {
        Authority authority = this.authority;
        if (authority != null && authority.getHost() != null) {
            return this.authority.getHostPort();
        }
        return null;
    }

    @Override
    public String getLrParam() {
        String string = this.hasParameter("lr") ? "true" : null;
        return string;
    }

    @Override
    public String getMAddrParam() {
        NameValue nameValue = this.uriParms.getNameValue("maddr");
        if (nameValue == null) {
            return null;
        }
        return (String)nameValue.getValueAsObject();
    }

    public String getMethod() {
        return (String)this.getParm("method");
    }

    @Override
    public String getMethodParam() {
        return this.getParameter("method");
    }

    @Override
    public String getParameter(String object) {
        if ((object = this.uriParms.getValue((String)object)) == null) {
            return null;
        }
        if (object instanceof GenericObject) {
            return ((GenericObject)object).encode();
        }
        return object.toString();
    }

    @Override
    public Iterator<String> getParameterNames() {
        return this.uriParms.getNames();
    }

    public NameValueList getParameters() {
        return this.uriParms;
    }

    public Object getParm(String string) {
        return this.uriParms.getValue(string);
    }

    @Override
    public int getPort() {
        HostPort hostPort = this.getHostPort();
        if (hostPort == null) {
            return -1;
        }
        return hostPort.getPort();
    }

    public NameValueList getQheaders() {
        return this.qheaders;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public int getTTLParam() {
        Integer n = (Integer)this.uriParms.getValue("ttl");
        if (n != null) {
            return n;
        }
        return -1;
    }

    public TelephoneNumber getTelephoneSubscriber() {
        if (this.telephoneSubscriber == null) {
            this.telephoneSubscriber = new TelephoneNumber();
        }
        return this.telephoneSubscriber;
    }

    @Override
    public String getTransportParam() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            return (String)nameValueList.getValue("transport");
        }
        return null;
    }

    @Override
    public String getUser() {
        return this.authority.getUser();
    }

    @Override
    public String getUserAtHost() {
        CharSequence charSequence = "";
        if (this.authority.getUserInfo() != null) {
            charSequence = this.authority.getUserInfo().getUser();
        }
        String string = this.authority.getHost().encode();
        charSequence = ((String)charSequence).equals("") ? new StringBuffer() : new StringBuffer((String)charSequence).append("@");
        ((StringBuffer)charSequence).append(string);
        return ((StringBuffer)charSequence).toString();
    }

    @Override
    public String getUserAtHostPort() {
        CharSequence charSequence = "";
        if (this.authority.getUserInfo() != null) {
            charSequence = this.authority.getUserInfo().getUser();
        }
        String string = this.authority.getHost().encode();
        int n = this.authority.getPort();
        charSequence = ((String)charSequence).equals("") ? new StringBuffer() : new StringBuffer((String)charSequence).append("@");
        if (n != -1) {
            ((StringBuffer)charSequence).append(string);
            ((StringBuffer)charSequence).append(":");
            ((StringBuffer)charSequence).append(n);
            return ((StringBuffer)charSequence).toString();
        }
        ((StringBuffer)charSequence).append(string);
        return ((StringBuffer)charSequence).toString();
    }

    @Override
    public String getUserParam() {
        return this.getParameter("user");
    }

    @Override
    public String getUserPassword() {
        Authority authority = this.authority;
        if (authority == null) {
            return null;
        }
        return authority.getPassword();
    }

    @Override
    public String getUserType() {
        return (String)this.uriParms.getValue("user");
    }

    @Override
    public boolean hasGrParam() {
        boolean bl = this.uriParms.getNameValue("gr") != null;
        return bl;
    }

    @Override
    public boolean hasLrParam() {
        boolean bl = this.uriParms.getNameValue("lr") != null;
        return bl;
    }

    public boolean hasParameter(String string) {
        boolean bl = this.uriParms.getValue(string) != null;
        return bl;
    }

    @Override
    public boolean hasTransport() {
        return this.hasParameter("transport");
    }

    @Override
    public boolean isSecure() {
        return this.getScheme().equalsIgnoreCase("sips");
    }

    @Override
    public boolean isSipURI() {
        return true;
    }

    public boolean isUserTelephoneSubscriber() {
        String string = (String)this.uriParms.getValue("user");
        if (string == null) {
            return false;
        }
        return string.equalsIgnoreCase("phone");
    }

    @Override
    public void removeHeader(String string) {
        NameValueList nameValueList = this.qheaders;
        if (nameValueList != null) {
            nameValueList.delete(string);
        }
    }

    @Override
    public void removeHeaders() {
        this.qheaders = new NameValueList();
    }

    public void removeMAddr() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            nameValueList.delete("maddr");
        }
    }

    public void removeMethod() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            nameValueList.delete("method");
        }
    }

    @Override
    public void removeParameter(String string) {
        this.uriParms.delete(string);
    }

    public void removeParameters() {
        this.uriParms = new NameValueList();
    }

    public void removePort() {
        this.authority.removePort();
    }

    public void removeTTL() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            nameValueList.delete("ttl");
        }
    }

    public void removeTransport() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            nameValueList.delete("transport");
        }
    }

    public void removeUser() {
        this.authority.removeUserInfo();
    }

    @Override
    public void removeUserType() {
        NameValueList nameValueList = this.uriParms;
        if (nameValueList != null) {
            nameValueList.delete("user");
        }
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setDefaultParm(String object, Object object2) {
        if (this.uriParms.getValue((String)object) == null) {
            object = new NameValue((String)object, object2);
            this.uriParms.set((NameValue)object);
        }
    }

    @Override
    public void setGrParam(String string) {
        this.uriParms.set("gr", string);
    }

    @Override
    public void setHeader(String object, String string) {
        object = new NameValue((String)object, string);
        this.qheaders.set((NameValue)object);
    }

    public void setHost(Host host) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.setHost(host);
    }

    @Override
    public void setHost(String string) throws ParseException {
        this.setHost(new Host(string));
    }

    public void setHostPort(HostPort hostPort) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.setHostPort(hostPort);
    }

    public void setIsdnSubAddress(String string) {
        if (this.telephoneSubscriber == null) {
            this.telephoneSubscriber = new TelephoneNumber();
        }
        this.telephoneSubscriber.setIsdnSubaddress(string);
    }

    @Override
    public void setLrParam() {
        this.uriParms.set("lr", null);
    }

    public void setMAddr(String object) {
        NameValue nameValue = this.uriParms.getNameValue("maddr");
        Host host = new Host();
        host.setAddress((String)object);
        if (nameValue != null) {
            nameValue.setValueAsObject(host);
        } else {
            object = new NameValue("maddr", host);
            this.uriParms.set((NameValue)object);
        }
    }

    @Override
    public void setMAddrParam(String string) throws ParseException {
        if (string != null) {
            this.setParameter("maddr", string);
            return;
        }
        throw new NullPointerException("bad maddr");
    }

    public void setMethod(String string) {
        this.uriParms.set("method", string);
    }

    @Override
    public void setMethodParam(String string) throws ParseException {
        this.setParameter("method", string);
    }

    @Override
    public void setParameter(String string, String string2) throws ParseException {
        if (string.equalsIgnoreCase("ttl")) {
            try {
                Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad parameter ");
                stringBuilder.append(string2);
                throw new ParseException(stringBuilder.toString(), 0);
            }
        }
        this.uriParms.set(string, string2);
    }

    @Override
    public void setPort(int n) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.setPort(n);
    }

    public void setQHeader(NameValue nameValue) {
        this.qheaders.set(nameValue);
    }

    public void setQheaders(NameValueList nameValueList) {
        this.qheaders = nameValueList;
    }

    public void setScheme(String string) {
        if (string.compareToIgnoreCase("sip") != 0 && string.compareToIgnoreCase("sips") != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad scheme ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.scheme = string.toLowerCase();
    }

    @Override
    public void setSecure(boolean bl) {
        this.scheme = bl ? "sips" : "sip";
    }

    @Override
    public void setTTLParam(int n) {
        if (n > 0) {
            if (this.uriParms != null) {
                NameValue nameValue = new NameValue("ttl", n);
                this.uriParms.set(nameValue);
            }
            return;
        }
        throw new IllegalArgumentException("Bad ttl value");
    }

    public void setTelephoneSubscriber(TelephoneNumber telephoneNumber) {
        this.telephoneSubscriber = telephoneNumber;
    }

    @Override
    public void setTransportParam(String object) throws ParseException {
        if (object != null) {
            if (((String)object).compareToIgnoreCase("UDP") != 0 && ((String)object).compareToIgnoreCase("TLS") != 0 && ((String)object).compareToIgnoreCase("TCP") != 0 && ((String)object).compareToIgnoreCase("SCTP") != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad transport ");
                stringBuilder.append((String)object);
                throw new ParseException(stringBuilder.toString(), 0);
            }
            object = new NameValue("transport", ((String)object).toLowerCase());
            this.uriParms.set((NameValue)object);
            return;
        }
        throw new NullPointerException("null arg");
    }

    public void setUriParameter(NameValue nameValue) {
        this.uriParms.set(nameValue);
    }

    public void setUriParm(String object, Object object2) {
        object = new NameValue((String)object, object2);
        this.uriParms.set((NameValue)object);
    }

    public void setUriParms(NameValueList nameValueList) {
        this.uriParms = nameValueList;
    }

    @Override
    public void setUser(String string) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.setUser(string);
    }

    @Override
    public void setUserParam(String string) {
        this.uriParms.set("user", string);
    }

    @Override
    public void setUserPassword(String string) {
        if (this.authority == null) {
            this.authority = new Authority();
        }
        this.authority.setPassword(string);
    }

    @Override
    public String toString() {
        return this.encode();
    }
}

