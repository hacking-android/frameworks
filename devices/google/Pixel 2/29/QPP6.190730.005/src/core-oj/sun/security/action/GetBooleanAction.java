/*
 * Decompiled with CFR 0.145.
 */
package sun.security.action;

import java.security.PrivilegedAction;

public class GetBooleanAction
implements PrivilegedAction<Boolean> {
    private String theProp;

    public GetBooleanAction(String string) {
        this.theProp = string;
    }

    @Override
    public Boolean run() {
        return Boolean.getBoolean(this.theProp);
    }
}

