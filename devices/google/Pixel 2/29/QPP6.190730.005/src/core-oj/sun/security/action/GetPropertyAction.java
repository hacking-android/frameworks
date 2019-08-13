/*
 * Decompiled with CFR 0.145.
 */
package sun.security.action;

import java.security.PrivilegedAction;

public class GetPropertyAction
implements PrivilegedAction<String> {
    private String defaultVal;
    private String theProp;

    public GetPropertyAction(String string) {
        this.theProp = string;
    }

    public GetPropertyAction(String string, String string2) {
        this.theProp = string;
        this.defaultVal = string2;
    }

    @Override
    public String run() {
        String string;
        block0 : {
            string = System.getProperty(this.theProp);
            if (string != null) break block0;
            string = this.defaultVal;
        }
        return string;
    }
}

