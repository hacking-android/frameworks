/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.objects.XBoolean;
import org.apache.xpath.objects.XObject;

public class XBooleanStatic
extends XBoolean {
    static final long serialVersionUID = -8064147275772687409L;
    private final boolean m_val;

    public XBooleanStatic(boolean bl) {
        super(bl);
        this.m_val = bl;
    }

    @Override
    public boolean equals(XObject xObject) {
        try {
            boolean bl = this.m_val;
            boolean bl2 = xObject.bool();
            bl = bl == bl2;
            return bl;
        }
        catch (TransformerException transformerException) {
            throw new WrappedRuntimeException(transformerException);
        }
    }
}

