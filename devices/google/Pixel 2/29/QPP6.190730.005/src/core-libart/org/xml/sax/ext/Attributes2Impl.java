/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import dalvik.annotation.compat.UnsupportedAppUsage;
import libcore.util.EmptyArray;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.helpers.AttributesImpl;

public class Attributes2Impl
extends AttributesImpl
implements Attributes2 {
    @UnsupportedAppUsage
    private boolean[] declared;
    @UnsupportedAppUsage
    private boolean[] specified;

    public Attributes2Impl() {
        this.declared = EmptyArray.BOOLEAN;
        this.specified = EmptyArray.BOOLEAN;
    }

    public Attributes2Impl(Attributes attributes) {
        super(attributes);
    }

    @Override
    public void addAttribute(String arrbl, String arrbl2, String string, String string2, String string3) {
        super.addAttribute((String)arrbl, (String)arrbl2, string, string2, string3);
        int n = this.getLength();
        if (n > this.specified.length) {
            arrbl2 = new boolean[n];
            arrbl = this.declared;
            System.arraycopy(arrbl, 0, arrbl2, 0, arrbl.length);
            this.declared = arrbl2;
            arrbl = new boolean[n];
            arrbl2 = this.specified;
            System.arraycopy(arrbl2, 0, arrbl, 0, arrbl2.length);
            this.specified = arrbl;
        }
        this.specified[n - 1] = true;
        this.declared[n - 1] = true ^ "CDATA".equals(string2);
    }

    @Override
    public boolean isDeclared(int n) {
        if (n >= 0 && n < this.getLength()) {
            return this.declared[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No attribute at index: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    public boolean isDeclared(String string) {
        int n = this.getIndex(string);
        if (n >= 0) {
            return this.declared[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No such attribute: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public boolean isDeclared(String string, String string2) {
        int n = this.getIndex(string, string2);
        if (n >= 0) {
            return this.declared[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No such attribute: local=");
        stringBuilder.append(string2);
        stringBuilder.append(", namespace=");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public boolean isSpecified(int n) {
        if (n >= 0 && n < this.getLength()) {
            return this.specified[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No attribute at index: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    public boolean isSpecified(String string) {
        int n = this.getIndex(string);
        if (n >= 0) {
            return this.specified[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No such attribute: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public boolean isSpecified(String string, String string2) {
        int n = this.getIndex(string, string2);
        if (n >= 0) {
            return this.specified[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No such attribute: local=");
        stringBuilder.append(string2);
        stringBuilder.append(", namespace=");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void removeAttribute(int n) {
        int n2 = this.getLength() - 1;
        super.removeAttribute(n);
        if (n != n2) {
            boolean[] arrbl = this.declared;
            System.arraycopy(arrbl, n + 1, arrbl, n, n2 - n);
            arrbl = this.specified;
            System.arraycopy(arrbl, n + 1, arrbl, n, n2 - n);
        }
    }

    @Override
    public void setAttributes(Attributes attributes) {
        int n = attributes.getLength();
        super.setAttributes(attributes);
        this.declared = new boolean[n];
        this.specified = new boolean[n];
        if (attributes instanceof Attributes2) {
            attributes = (Attributes2)attributes;
            for (int i = 0; i < n; ++i) {
                this.declared[i] = attributes.isDeclared(i);
                this.specified[i] = attributes.isSpecified(i);
            }
        } else {
            for (int i = 0; i < n; ++i) {
                this.declared[i] = "CDATA".equals(attributes.getType(i)) ^ true;
                this.specified[i] = true;
            }
        }
    }

    public void setDeclared(int n, boolean bl) {
        if (n >= 0 && n < this.getLength()) {
            this.declared[n] = bl;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No attribute at index: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public void setSpecified(int n, boolean bl) {
        if (n >= 0 && n < this.getLength()) {
            this.specified[n] = bl;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No attribute at index: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }
}

