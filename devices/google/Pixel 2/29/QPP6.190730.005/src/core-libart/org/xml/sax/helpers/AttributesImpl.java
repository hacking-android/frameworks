/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.xml.sax.Attributes;

public class AttributesImpl
implements Attributes {
    @UnsupportedAppUsage
    String[] data;
    @UnsupportedAppUsage
    int length;

    public AttributesImpl() {
        this.length = 0;
        this.data = null;
    }

    public AttributesImpl(Attributes attributes) {
        this.setAttributes(attributes);
    }

    @UnsupportedAppUsage
    private void badIndex(int n) throws ArrayIndexOutOfBoundsException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempt to modify attribute at illegal index: ");
        stringBuilder.append(n);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private void ensureCapacity(int n) {
        int n2;
        if (n <= 0) {
            return;
        }
        String[] arrstring = this.data;
        if (arrstring != null && arrstring.length != 0) {
            if (arrstring.length >= n * 5) {
                return;
            }
            n2 = arrstring.length;
        } else {
            n2 = 25;
        }
        while (n2 < n * 5) {
            n2 *= 2;
        }
        arrstring = new String[n2];
        n = this.length;
        if (n > 0) {
            System.arraycopy(this.data, 0, arrstring, 0, n * 5);
        }
        this.data = arrstring;
    }

    public void addAttribute(String string, String string2, String string3, String string4, String string5) {
        this.ensureCapacity(this.length + 1);
        String[] arrstring = this.data;
        int n = this.length;
        arrstring[n * 5] = string;
        arrstring[n * 5 + 1] = string2;
        arrstring[n * 5 + 2] = string3;
        arrstring[n * 5 + 3] = string4;
        arrstring[n * 5 + 4] = string5;
        this.length = n + 1;
    }

    public void clear() {
        if (this.data != null) {
            for (int i = 0; i < this.length * 5; ++i) {
                this.data[i] = null;
            }
        }
        this.length = 0;
    }

    @Override
    public int getIndex(String string) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i + 2].equals(string)) continue;
            return i / 5;
        }
        return -1;
    }

    @Override
    public int getIndex(String string, String string2) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i].equals(string) || !this.data[i + 1].equals(string2)) continue;
            return i / 5;
        }
        return -1;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public String getLocalName(int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 1];
        }
        return null;
    }

    @Override
    public String getQName(int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 2];
        }
        return null;
    }

    @Override
    public String getType(int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 3];
        }
        return null;
    }

    @Override
    public String getType(String string) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i + 2].equals(string)) continue;
            return this.data[i + 3];
        }
        return null;
    }

    @Override
    public String getType(String string, String string2) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i].equals(string) || !this.data[i + 1].equals(string2)) continue;
            return this.data[i + 3];
        }
        return null;
    }

    @Override
    public String getURI(int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5];
        }
        return null;
    }

    @Override
    public String getValue(int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 4];
        }
        return null;
    }

    @Override
    public String getValue(String string) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i + 2].equals(string)) continue;
            return this.data[i + 4];
        }
        return null;
    }

    @Override
    public String getValue(String string, String string2) {
        int n = this.length;
        for (int i = 0; i < n * 5; i += 5) {
            if (!this.data[i].equals(string) || !this.data[i + 1].equals(string2)) continue;
            return this.data[i + 4];
        }
        return null;
    }

    public void removeAttribute(int n) {
        int n2;
        if (n >= 0 && n < (n2 = this.length)) {
            String[] arrstring;
            if (n < n2 - 1) {
                arrstring = this.data;
                System.arraycopy(arrstring, (n + 1) * 5, arrstring, n * 5, (n2 - n - 1) * 5);
            }
            n = this.length;
            n2 = (n - 1) * 5;
            arrstring = this.data;
            int n3 = n2 + 1;
            arrstring[n2] = null;
            n2 = n3 + 1;
            arrstring[n3] = null;
            n3 = n2 + 1;
            arrstring[n2] = null;
            arrstring[n3] = null;
            arrstring[n3 + 1] = null;
            this.length = n - 1;
        } else {
            this.badIndex(n);
        }
    }

    public void setAttribute(int n, String string, String string2, String string3, String string4, String string5) {
        if (n >= 0 && n < this.length) {
            String[] arrstring = this.data;
            arrstring[n * 5] = string;
            arrstring[n * 5 + 1] = string2;
            arrstring[n * 5 + 2] = string3;
            arrstring[n * 5 + 3] = string4;
            arrstring[n * 5 + 4] = string5;
        } else {
            this.badIndex(n);
        }
    }

    public void setAttributes(Attributes attributes) {
        this.clear();
        int n = this.length = attributes.getLength();
        if (n > 0) {
            this.data = new String[n * 5];
            for (n = 0; n < this.length; ++n) {
                this.data[n * 5] = attributes.getURI(n);
                this.data[n * 5 + 1] = attributes.getLocalName(n);
                this.data[n * 5 + 2] = attributes.getQName(n);
                this.data[n * 5 + 3] = attributes.getType(n);
                this.data[n * 5 + 4] = attributes.getValue(n);
            }
        }
    }

    public void setLocalName(int n, String string) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 1] = string;
        } else {
            this.badIndex(n);
        }
    }

    public void setQName(int n, String string) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 2] = string;
        } else {
            this.badIndex(n);
        }
    }

    public void setType(int n, String string) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 3] = string;
        } else {
            this.badIndex(n);
        }
    }

    public void setURI(int n, String string) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5] = string;
        } else {
            this.badIndex(n);
        }
    }

    public void setValue(int n, String string) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 4] = string;
        } else {
            this.badIndex(n);
        }
    }
}

