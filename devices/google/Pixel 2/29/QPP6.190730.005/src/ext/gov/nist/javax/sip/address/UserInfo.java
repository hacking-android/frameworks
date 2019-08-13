/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.javax.sip.address.NetObject;

public final class UserInfo
extends NetObject {
    public static final int TELEPHONE_SUBSCRIBER = 1;
    public static final int USER = 2;
    private static final long serialVersionUID = 7268593273924256144L;
    protected String password;
    protected String user;
    protected int userType;

    public void clearPassword() {
        this.password = null;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        if (this.password != null) {
            stringBuffer.append(this.user);
            stringBuffer.append(":");
            stringBuffer.append(this.password);
        } else {
            stringBuffer.append(this.user);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Object object2 = (UserInfo)object;
        if (this.userType != ((UserInfo)object2).userType) {
            return false;
        }
        if (!this.user.equalsIgnoreCase(((UserInfo)object2).user)) {
            return false;
        }
        if (this.password != null && ((UserInfo)object2).password == null) {
            return false;
        }
        if (((UserInfo)object2).password != null && this.password == null) {
            return false;
        }
        object = this.password;
        object2 = ((UserInfo)object2).password;
        if (object == object2) {
            return true;
        }
        return ((String)object).equals(object2);
    }

    public String getPassword() {
        return this.password;
    }

    public String getUser() {
        return this.user;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setPassword(String string) {
        this.password = string;
    }

    public void setUser(String string) {
        this.user = string;
        if (string != null && (string.indexOf("#") >= 0 || string.indexOf(";") >= 0)) {
            this.setUserType(1);
        } else {
            this.setUserType(2);
        }
    }

    public void setUserType(int n) throws IllegalArgumentException {
        if (n != 1 && n != 2) {
            throw new IllegalArgumentException("Parameter not in range");
        }
        this.userType = n;
    }
}

