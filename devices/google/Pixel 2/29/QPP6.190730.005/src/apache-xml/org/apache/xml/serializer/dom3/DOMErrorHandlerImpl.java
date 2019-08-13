/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.io.PrintStream;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;

final class DOMErrorHandlerImpl
implements DOMErrorHandler {
    DOMErrorHandlerImpl() {
    }

    @Override
    public boolean handleError(DOMError dOMError) {
        boolean bl;
        boolean bl2 = true;
        CharSequence charSequence = null;
        if (dOMError.getSeverity() == 1) {
            bl = false;
            charSequence = "[Warning]";
        } else if (dOMError.getSeverity() == 2) {
            charSequence = "[Error]";
            bl = bl2;
        } else {
            bl = bl2;
            if (dOMError.getSeverity() == 3) {
                charSequence = "[Fatal Error]";
                bl = bl2;
            }
        }
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(": ");
        stringBuilder.append(dOMError.getMessage());
        stringBuilder.append("\t");
        printStream.println(stringBuilder.toString());
        printStream = System.err;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Type : ");
        ((StringBuilder)charSequence).append(dOMError.getType());
        ((StringBuilder)charSequence).append("\tRelated Data: ");
        ((StringBuilder)charSequence).append(dOMError.getRelatedData());
        ((StringBuilder)charSequence).append("\tRelated Exception: ");
        ((StringBuilder)charSequence).append(dOMError.getRelatedException());
        printStream.println(((StringBuilder)charSequence).toString());
        return bl;
    }
}

