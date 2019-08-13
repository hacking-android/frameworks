/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import org.apache.xml.serializer.ElemDesc;

final class ElemContext {
    final int m_currentElemDepth;
    ElemDesc m_elementDesc = null;
    String m_elementLocalName = null;
    String m_elementName = null;
    String m_elementURI = null;
    boolean m_isCdataSection;
    boolean m_isRaw = false;
    private ElemContext m_next;
    final ElemContext m_prev;
    boolean m_startTagOpen = false;

    ElemContext() {
        this.m_prev = this;
        this.m_currentElemDepth = 0;
    }

    private ElemContext(ElemContext elemContext) {
        this.m_prev = elemContext;
        this.m_currentElemDepth = elemContext.m_currentElemDepth + 1;
    }

    final ElemContext pop() {
        return this.m_prev;
    }

    final ElemContext push() {
        ElemContext elemContext;
        ElemContext elemContext2 = elemContext = this.m_next;
        if (elemContext == null) {
            this.m_next = elemContext2 = new ElemContext(this);
        }
        elemContext2.m_startTagOpen = true;
        return elemContext2;
    }

    final ElemContext push(String string, String string2, String string3) {
        ElemContext elemContext;
        ElemContext elemContext2 = elemContext = this.m_next;
        if (elemContext == null) {
            this.m_next = elemContext2 = new ElemContext(this);
        }
        elemContext2.m_elementName = string3;
        elemContext2.m_elementLocalName = string2;
        elemContext2.m_elementURI = string;
        elemContext2.m_isCdataSection = false;
        elemContext2.m_startTagOpen = true;
        return elemContext2;
    }
}

