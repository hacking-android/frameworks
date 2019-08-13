/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.res;

import java.util.ListResourceBundle;
import org.apache.xalan.res.XSLTErrorResources;
import org.apache.xpath.res.XPATHMessages;

public class XSLMessages
extends XPATHMessages {
    private static ListResourceBundle XSLTBundle = new XSLTErrorResources();

    public static final String createMessage(String string, Object[] arrobject) {
        return XSLMessages.createMsg(XSLTBundle, string, arrobject);
    }

    public static final String createWarning(String string, Object[] arrobject) {
        return XSLMessages.createMsg(XSLTBundle, string, arrobject);
    }
}

