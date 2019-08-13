/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.validation;

import org.w3c.dom.TypeInfo;

public abstract class TypeInfoProvider {
    protected TypeInfoProvider() {
    }

    public abstract TypeInfo getAttributeTypeInfo(int var1);

    public abstract TypeInfo getElementTypeInfo();

    public abstract boolean isIdAttribute(int var1);

    public abstract boolean isSpecified(int var1);
}

