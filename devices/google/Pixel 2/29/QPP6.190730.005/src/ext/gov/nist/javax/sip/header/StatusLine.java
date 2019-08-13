/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.Match;
import gov.nist.javax.sip.header.SIPObject;
import gov.nist.javax.sip.header.SipStatusLine;

public final class StatusLine
extends SIPObject
implements SipStatusLine {
    private static final long serialVersionUID = -4738092215519950414L;
    protected boolean matchStatusClass;
    protected String reasonPhrase = null;
    protected String sipVersion = "SIP/2.0";
    protected int statusCode;

    @Override
    public String encode() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("SIP/2.0 ");
        ((StringBuilder)charSequence).append(this.statusCode);
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.reasonPhrase != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.reasonPhrase);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\r\n");
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public String getSipVersion() {
        return this.sipVersion;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getVersionMajor() {
        if (this.sipVersion == null) {
            return null;
        }
        StringBuilder stringBuilder = null;
        boolean bl = false;
        for (int i = 0; i < this.sipVersion.length(); ++i) {
            if (this.sipVersion.charAt(i) == '.') {
                bl = false;
            }
            CharSequence charSequence = stringBuilder;
            if (bl) {
                if (stringBuilder == null) {
                    charSequence = new StringBuilder();
                    charSequence.append("");
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                } else {
                    charSequence = new StringBuilder();
                    charSequence.append((String)((Object)stringBuilder));
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                }
            }
            if (this.sipVersion.charAt(i) == '/') {
                bl = true;
            }
            stringBuilder = charSequence;
        }
        return stringBuilder;
    }

    @Override
    public String getVersionMinor() {
        if (this.sipVersion == null) {
            return null;
        }
        StringBuilder stringBuilder = null;
        boolean bl = false;
        for (int i = 0; i < this.sipVersion.length(); ++i) {
            CharSequence charSequence = stringBuilder;
            if (bl) {
                if (stringBuilder == null) {
                    charSequence = new StringBuilder();
                    charSequence.append("");
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                } else {
                    charSequence = new StringBuilder();
                    charSequence.append((String)((Object)stringBuilder));
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                }
            }
            if (this.sipVersion.charAt(i) == '.') {
                bl = true;
            }
            stringBuilder = charSequence;
        }
        return stringBuilder;
    }

    @Override
    public boolean match(Object object) {
        if (!(object instanceof StatusLine)) {
            return false;
        }
        object = (StatusLine)object;
        if (((StatusLine)object).matchExpression != null) {
            return ((StatusLine)object).matchExpression.match(this.encode());
        }
        String string = ((StatusLine)object).sipVersion;
        if (string != null && !string.equals(this.sipVersion)) {
            return false;
        }
        int n = ((StatusLine)object).statusCode;
        if (n != 0) {
            if (this.matchStatusClass) {
                int n2 = ((StatusLine)object).statusCode;
                String string2 = Integer.toString(n);
                string = Integer.toString(this.statusCode);
                if (string2.charAt(0) != string.charAt(0)) {
                    return false;
                }
            } else if (this.statusCode != n) {
                return false;
            }
        }
        if ((object = ((StatusLine)object).reasonPhrase) != null && (string = this.reasonPhrase) != object) {
            return string.equals(object);
        }
        return true;
    }

    public void setMatchStatusClass(boolean bl) {
        this.matchStatusClass = bl;
    }

    @Override
    public void setReasonPhrase(String string) {
        this.reasonPhrase = string;
    }

    @Override
    public void setSipVersion(String string) {
        this.sipVersion = string;
    }

    @Override
    public void setStatusCode(int n) {
        this.statusCode = n;
    }
}

