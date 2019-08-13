/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPObject;

public final class CallIdentifier
extends SIPObject {
    private static final long serialVersionUID = 7314773655675451377L;
    protected String host;
    protected String localId;

    public CallIdentifier() {
    }

    public CallIdentifier(String string) throws IllegalArgumentException {
        this.setCallID(string);
    }

    public CallIdentifier(String string, String string2) {
        this.localId = string;
        this.host = string2;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.localId);
        if (this.host != null) {
            stringBuffer.append("@");
            stringBuffer.append(this.host);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        CallIdentifier callIdentifier = (CallIdentifier)object;
        if (this.localId.compareTo(callIdentifier.localId) != 0) {
            return false;
        }
        object = this.host;
        String string = callIdentifier.host;
        if (object == string) {
            return true;
        }
        if (object == null && string != null || this.host != null && callIdentifier.host == null) {
            return false;
        }
        return this.host.compareToIgnoreCase(callIdentifier.host) == 0;
    }

    public String getHost() {
        return this.host;
    }

    public String getLocalId() {
        return this.localId;
    }

    public int hashCode() {
        String string = this.localId;
        if (string != null) {
            return string.hashCode();
        }
        throw new UnsupportedOperationException("Hash code called before id is set");
    }

    public void setCallID(String string) throws IllegalArgumentException {
        block2 : {
            block5 : {
                block4 : {
                    int n;
                    block3 : {
                        if (string == null) break block2;
                        n = string.indexOf(64);
                        if (n != -1) break block3;
                        this.localId = string;
                        this.host = null;
                        break block4;
                    }
                    this.localId = string.substring(0, n);
                    this.host = string.substring(n + 1, string.length());
                    if (this.localId == null || this.host == null) break block5;
                }
                return;
            }
            throw new IllegalArgumentException("CallID  must be token@token or token");
        }
        throw new IllegalArgumentException("NULL!");
    }

    public void setHost(String string) {
        this.host = string;
    }

    public void setLocalId(String string) {
        this.localId = string;
    }
}

