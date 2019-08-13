/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.objects.XBooleanStatic;
import org.apache.xpath.objects.XObject;

public class XBoolean
extends XObject {
    public static final XBoolean S_FALSE;
    public static final XBoolean S_TRUE;
    static final long serialVersionUID = -2964933058866100881L;
    private final boolean m_val;

    static {
        S_TRUE = new XBooleanStatic(true);
        S_FALSE = new XBooleanStatic(false);
    }

    public XBoolean(Boolean bl) {
        this.m_val = bl;
        this.setObject(bl);
    }

    public XBoolean(boolean bl) {
        this.m_val = bl;
    }

    @Override
    public boolean bool() {
        return this.m_val;
    }

    @Override
    public boolean equals(XObject xObject) {
        if (xObject.getType() == 4) {
            return xObject.equals(this);
        }
        try {
            boolean bl = this.m_val;
            boolean bl2 = xObject.bool();
            bl2 = bl == bl2;
            return bl2;
        }
        catch (TransformerException transformerException) {
            throw new WrappedRuntimeException(transformerException);
        }
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public String getTypeString() {
        return "#BOOLEAN";
    }

    @Override
    public double num() {
        double d = this.m_val ? 1.0 : 0.0;
        return d;
    }

    @Override
    public Object object() {
        if (this.m_obj == null) {
            this.setObject(new Boolean(this.m_val));
        }
        return this.m_obj;
    }

    @Override
    public String str() {
        String string = this.m_val ? "true" : "false";
        return string;
    }
}

