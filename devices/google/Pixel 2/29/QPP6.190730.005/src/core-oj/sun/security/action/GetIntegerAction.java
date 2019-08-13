/*
 * Decompiled with CFR 0.145.
 */
package sun.security.action;

import java.security.PrivilegedAction;

public class GetIntegerAction
implements PrivilegedAction<Integer> {
    private boolean defaultSet = false;
    private int defaultVal;
    private String theProp;

    public GetIntegerAction(String string) {
        this.theProp = string;
    }

    public GetIntegerAction(String string, int n) {
        this.theProp = string;
        this.defaultVal = n;
        this.defaultSet = true;
    }

    @Override
    public Integer run() {
        Integer n = Integer.getInteger(this.theProp);
        if (n == null && this.defaultSet) {
            return new Integer(this.defaultVal);
        }
        return n;
    }
}

