/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.xalan.processor.XSLTAttributeDef;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.processor.XSLTSchema;
import org.apache.xml.utils.QName;

public class XSLTElementDef {
    static final int T_ANY = 3;
    static final int T_ELEMENT = 1;
    static final int T_PCDATA = 2;
    private XSLTAttributeDef[] m_attributes;
    private Class m_classObject;
    private XSLTElementProcessor m_elementProcessor;
    private XSLTElementDef[] m_elements;
    private boolean m_has_required;
    boolean m_isOrdered;
    private int m_lastOrder;
    private boolean m_multiAllowed;
    private String m_name;
    private String m_nameAlias;
    private String m_namespace;
    private int m_order;
    private boolean m_required;
    Hashtable m_requiredFound;
    private int m_type;

    XSLTElementDef() {
        this.m_type = 1;
        this.m_has_required = false;
        this.m_required = false;
        this.m_isOrdered = false;
        this.m_order = -1;
        this.m_lastOrder = -1;
        this.m_multiAllowed = true;
    }

    XSLTElementDef(Class class_, XSLTElementProcessor xSLTElementProcessor, int n) {
        this.m_type = 1;
        this.m_has_required = false;
        this.m_required = false;
        this.m_isOrdered = false;
        this.m_order = -1;
        this.m_lastOrder = -1;
        this.m_multiAllowed = true;
        this.m_classObject = class_;
        this.m_type = n;
        this.setElementProcessor(xSLTElementProcessor);
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_) {
        block1 : {
            this.m_type = 1;
            this.m_has_required = false;
            this.m_required = false;
            this.m_isOrdered = false;
            this.m_order = -1;
            this.m_lastOrder = -1;
            this.m_multiAllowed = true;
            this.build(string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_);
            if (string == null || !string.equals("http://www.w3.org/1999/XSL/Transform") && !string.equals("http://xml.apache.org/xalan") && !string.equals("http://xml.apache.org/xslt")) break block1;
            xSLTSchema.addAvailableElement(new QName(string, string2));
            if (string3 != null) {
                xSLTSchema.addAvailableElement(new QName(string, string3));
            }
        }
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, int n, boolean bl) {
        this(xSLTSchema, string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_);
        this.m_order = n;
        this.m_multiAllowed = bl;
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, boolean bl) {
        block1 : {
            this.m_type = 1;
            this.m_has_required = false;
            this.m_required = false;
            this.m_isOrdered = false;
            this.m_order = -1;
            this.m_lastOrder = -1;
            this.m_multiAllowed = true;
            this.m_has_required = bl;
            this.build(string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_);
            if (string == null || !string.equals("http://www.w3.org/1999/XSL/Transform") && !string.equals("http://xml.apache.org/xalan") && !string.equals("http://xml.apache.org/xslt")) break block1;
            xSLTSchema.addAvailableElement(new QName(string, string2));
            if (string3 != null) {
                xSLTSchema.addAvailableElement(new QName(string, string3));
            }
        }
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, boolean bl, int n, boolean bl2) {
        this(xSLTSchema, string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_, n, bl2);
        this.m_isOrdered = bl;
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, boolean bl, boolean bl2) {
        this(xSLTSchema, string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_, bl);
        this.m_required = bl2;
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, boolean bl, boolean bl2, int n, boolean bl3) {
        this(xSLTSchema, string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_, bl, bl2);
        this.m_order = n;
        this.m_multiAllowed = bl3;
    }

    XSLTElementDef(XSLTSchema xSLTSchema, String string, String string2, String string3, XSLTElementDef[] arrxSLTElementDef, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_, boolean bl, boolean bl2, boolean bl3, int n, boolean bl4) {
        this(xSLTSchema, string, string2, string3, arrxSLTElementDef, arrxSLTAttributeDef, xSLTElementProcessor, class_, bl, bl2);
        this.m_order = n;
        this.m_multiAllowed = bl4;
        this.m_isOrdered = bl3;
    }

    private boolean QNameEquals(String string, String string2) {
        boolean bl = XSLTElementDef.equalsMayBeNullOrZeroLen(this.m_namespace, string) && (XSLTElementDef.equalsMayBeNullOrZeroLen(this.m_name, string2) || XSLTElementDef.equalsMayBeNullOrZeroLen(this.m_nameAlias, string2));
        return bl;
    }

    private static boolean equalsMayBeNull(Object object, Object object2) {
        boolean bl = object2 == object || object != null && object2 != null && object2.equals(object);
        return bl;
    }

    private static boolean equalsMayBeNullOrZeroLen(String string, String string2) {
        int n;
        boolean bl = false;
        int n2 = string == null ? 0 : string.length();
        if (n2 == (n = string2 == null ? 0 : string2.length())) {
            bl = n2 == 0 ? true : string.equals(string2);
        }
        return bl;
    }

    void build(String object, String string, String object2, XSLTElementDef[] object3, XSLTAttributeDef[] arrxSLTAttributeDef, XSLTElementProcessor xSLTElementProcessor, Class class_) {
        this.m_namespace = object;
        this.m_name = string;
        this.m_nameAlias = object2;
        this.m_elements = object3;
        this.m_attributes = arrxSLTAttributeDef;
        this.setElementProcessor(xSLTElementProcessor);
        this.m_classObject = class_;
        if (this.hasRequired() && (object = this.m_elements) != null) {
            int n = ((XSLTElementDef[])object).length;
            for (int i = 0; i < n; ++i) {
                object2 = this.m_elements[i];
                if (object2 == null || !((XSLTElementDef)object2).getRequired()) continue;
                if (this.m_requiredFound == null) {
                    this.m_requiredFound = new Hashtable();
                }
                object3 = this.m_requiredFound;
                string = ((XSLTElementDef)object2).getName();
                object = new StringBuilder();
                ((StringBuilder)object).append("xsl:");
                ((StringBuilder)object).append(((XSLTElementDef)object2).getName());
                ((Hashtable)object3).put(string, ((StringBuilder)object).toString());
            }
        }
    }

    XSLTAttributeDef getAttributeDef(String string, String string2) {
        XSLTAttributeDef xSLTAttributeDef = null;
        for (XSLTAttributeDef xSLTAttributeDef2 : this.getAttributes()) {
            XSLTAttributeDef xSLTAttributeDef3;
            String string3 = xSLTAttributeDef2.getNamespace();
            String string4 = xSLTAttributeDef2.getName();
            if (string4.equals("*") && (XSLTElementDef.equalsMayBeNullOrZeroLen(string, string3) || string3 != null && string3.equals("*") && string != null && string.length() > 0)) {
                return xSLTAttributeDef2;
            }
            if (string4.equals("*") && string3 == null) {
                xSLTAttributeDef3 = xSLTAttributeDef2;
            } else {
                xSLTAttributeDef3 = xSLTAttributeDef;
                if (XSLTElementDef.equalsMayBeNullOrZeroLen(string, string3)) {
                    xSLTAttributeDef3 = xSLTAttributeDef;
                    if (string2.equals(string4)) {
                        return xSLTAttributeDef2;
                    }
                }
            }
            xSLTAttributeDef = xSLTAttributeDef3;
        }
        if (xSLTAttributeDef == null && string.length() > 0 && !XSLTElementDef.equalsMayBeNullOrZeroLen(string, "http://www.w3.org/1999/XSL/Transform")) {
            return XSLTAttributeDef.m_foreignAttr;
        }
        return xSLTAttributeDef;
    }

    XSLTAttributeDef[] getAttributes() {
        return this.m_attributes;
    }

    Class getClassObject() {
        return this.m_classObject;
    }

    public XSLTElementProcessor getElementProcessor() {
        return this.m_elementProcessor;
    }

    public XSLTElementDef[] getElements() {
        return this.m_elements;
    }

    int getLastOrder() {
        return this.m_lastOrder;
    }

    boolean getMultiAllowed() {
        return this.m_multiAllowed;
    }

    String getName() {
        return this.m_name;
    }

    String getNameAlias() {
        return this.m_nameAlias;
    }

    String getNamespace() {
        return this.m_namespace;
    }

    int getOrder() {
        return this.m_order;
    }

    XSLTElementProcessor getProcessorFor(String string, String string2) {
        boolean bl;
        int n;
        XSLTElementProcessor xSLTElementProcessor = null;
        Object object = this.m_elements;
        if (object == null) {
            return null;
        }
        int n2 = ((XSLTElementDef[])object).length;
        int n3 = -1;
        boolean bl2 = true;
        int n4 = 0;
        do {
            object = xSLTElementProcessor;
            n = n3;
            bl = bl2;
            if (n4 >= n2) break;
            object = this.m_elements[n4];
            if (((XSLTElementDef)object).m_name.equals("*")) {
                if (!XSLTElementDef.equalsMayBeNullOrZeroLen(string, "http://www.w3.org/1999/XSL/Transform")) {
                    xSLTElementProcessor = ((XSLTElementDef)object).m_elementProcessor;
                    n3 = ((XSLTElementDef)object).getOrder();
                    bl2 = ((XSLTElementDef)object).getMultiAllowed();
                }
            } else if (XSLTElementDef.super.QNameEquals(string, string2)) {
                if (((XSLTElementDef)object).getRequired()) {
                    this.setRequiredFound(((XSLTElementDef)object).getName(), true);
                }
                n = ((XSLTElementDef)object).getOrder();
                bl = ((XSLTElementDef)object).getMultiAllowed();
                object = ((XSLTElementDef)object).m_elementProcessor;
                break;
            }
            ++n4;
        } while (true);
        if (object != null && this.isOrdered()) {
            n3 = this.getLastOrder();
            if (n > n3) {
                this.setLastOrder(n);
            } else {
                if (n == n3 && !bl) {
                    return null;
                }
                if (n < n3 && n > 0) {
                    return null;
                }
            }
        }
        return object;
    }

    XSLTElementProcessor getProcessorForUnknown(String string, String object) {
        object = this.m_elements;
        if (object == null) {
            return null;
        }
        int n = ((XSLTElementDef[])object).length;
        for (int i = 0; i < n; ++i) {
            object = this.m_elements[i];
            if (!object.m_name.equals("unknown") || string.length() <= 0) continue;
            return object.m_elementProcessor;
        }
        return null;
    }

    boolean getRequired() {
        return this.m_required;
    }

    String getRequiredElem() {
        Object object = this.m_requiredFound;
        if (object == null) {
            return null;
        }
        Enumeration enumeration = ((Hashtable)object).elements();
        object = "";
        boolean bl = true;
        while (enumeration.hasMoreElements()) {
            StringBuilder stringBuilder;
            if (bl) {
                bl = false;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(", ");
                object = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append((String)enumeration.nextElement());
            object = stringBuilder.toString();
        }
        return object;
    }

    boolean getRequiredFound() {
        Hashtable hashtable = this.m_requiredFound;
        if (hashtable == null) {
            return true;
        }
        return hashtable.isEmpty();
    }

    int getType() {
        return this.m_type;
    }

    boolean hasRequired() {
        return this.m_has_required;
    }

    boolean isOrdered() {
        return this.m_isOrdered;
    }

    public void setElementProcessor(XSLTElementProcessor xSLTElementProcessor) {
        if (xSLTElementProcessor != null) {
            this.m_elementProcessor = xSLTElementProcessor;
            this.m_elementProcessor.setElemDef(this);
        }
    }

    void setElements(XSLTElementDef[] arrxSLTElementDef) {
        this.m_elements = arrxSLTElementDef;
    }

    void setLastOrder(int n) {
        this.m_lastOrder = n;
    }

    void setRequiredFound(String string, boolean bl) {
        if (this.m_requiredFound.get(string) != null) {
            this.m_requiredFound.remove(string);
        }
    }

    void setType(int n) {
        this.m_type = n;
    }
}

