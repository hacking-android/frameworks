/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import java.util.ArrayList;
import org.xml.sax.AttributeList;

@Deprecated
public class AttributeListImpl
implements AttributeList {
    private ArrayList<String> names = new ArrayList();
    private ArrayList<String> types = new ArrayList();
    private ArrayList<String> values = new ArrayList();

    public AttributeListImpl() {
    }

    public AttributeListImpl(AttributeList attributeList) {
        this.setAttributeList(attributeList);
    }

    public void addAttribute(String string, String string2, String string3) {
        this.names.add(string);
        this.types.add(string2);
        this.values.add(string3);
    }

    public void clear() {
        this.names.clear();
        this.types.clear();
        this.values.clear();
    }

    @Override
    public int getLength() {
        return this.names.size();
    }

    @Override
    public String getName(int n) {
        if (n >= 0 && n < this.names.size()) {
            return this.names.get(n);
        }
        return null;
    }

    @Override
    public String getType(int n) {
        if (n >= 0 && n < this.types.size()) {
            return this.types.get(n);
        }
        return null;
    }

    @Override
    public String getType(String string) {
        return this.getType(this.names.indexOf(string));
    }

    @Override
    public String getValue(int n) {
        if (n >= 0 && n < this.values.size()) {
            return this.values.get(n);
        }
        return null;
    }

    @Override
    public String getValue(String string) {
        return this.getValue(this.names.indexOf(string));
    }

    public void removeAttribute(String string) {
        int n = this.names.indexOf(string);
        if (n != -1) {
            this.names.remove(n);
            this.types.remove(n);
            this.values.remove(n);
        }
    }

    public void setAttributeList(AttributeList attributeList) {
        int n = attributeList.getLength();
        this.clear();
        for (int i = 0; i < n; ++i) {
            this.addAttribute(attributeList.getName(i), attributeList.getType(i), attributeList.getValue(i));
        }
    }
}

