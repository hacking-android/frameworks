/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeaderNames;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public abstract class SIPHeaderNamesCache {
    private static final HashMap lowercaseMap = new HashMap();

    static {
        Field[] arrfield = SIPHeaderNames.class.getFields();
        for (int i = 0; i < arrfield.length; ++i) {
            Object object = arrfield[i];
            if (!((Field)object).getType().equals(String.class) || !Modifier.isStatic(((Field)object).getModifiers())) continue;
            try {
                String string = (String)((Field)object).get(null);
                object = string.toLowerCase();
                lowercaseMap.put(string, object);
                lowercaseMap.put(object, object);
                continue;
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
    }

    public static String toLowerCase(String string) {
        String string2 = (String)lowercaseMap.get(string);
        if (string2 == null) {
            return string.toLowerCase();
        }
        return string2;
    }
}

