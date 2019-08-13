/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;

public class XNumber
extends XObject {
    static final long serialVersionUID = -2720400709619020193L;
    double m_val;

    public XNumber(double d) {
        this.m_val = d;
    }

    public XNumber(Number number) {
        this.m_val = number.doubleValue();
        this.setObject(number);
    }

    private static String zeros(int n) {
        if (n < 1) {
            return "";
        }
        char[] arrc = new char[n];
        for (int i = 0; i < n; ++i) {
            arrc[i] = (char)48;
        }
        return new String(arrc);
    }

    @Override
    public boolean bool() {
        boolean bl = !Double.isNaN(this.m_val) && this.m_val != 0.0;
        return bl;
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        xPathVisitor.visitNumberLiteral(expressionOwner, this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(XObject xObject) {
        int n = xObject.getType();
        if (n == 4) {
            try {
                return xObject.equals(this);
            }
            catch (TransformerException transformerException) {
                throw new WrappedRuntimeException(transformerException);
            }
        }
        boolean bl = false;
        boolean bl2 = false;
        if (n == 1) {
            if (xObject.bool() != this.bool()) return bl2;
            return true;
        }
        double d = this.m_val;
        double d2 = xObject.num();
        bl2 = bl;
        if (d != d2) return bl2;
        return true;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getTypeString() {
        return "#NUMBER";
    }

    @Override
    public boolean isStableNumber() {
        return true;
    }

    @Override
    public double num() {
        return this.m_val;
    }

    @Override
    public double num(XPathContext xPathContext) throws TransformerException {
        return this.m_val;
    }

    @Override
    public Object object() {
        if (this.m_obj == null) {
            this.setObject(new Double(this.m_val));
        }
        return this.m_obj;
    }

    @Override
    public String str() {
        String string;
        int n;
        if (Double.isNaN(this.m_val)) {
            return "NaN";
        }
        if (Double.isInfinite(this.m_val)) {
            if (this.m_val > 0.0) {
                return "Infinity";
            }
            return "-Infinity";
        }
        String string2 = Double.toString(this.m_val);
        if (string2.charAt((n = string2.length()) - 2) == '.' && string2.charAt(n - 1) == '0') {
            String string3 = string2.substring(0, n - 2);
            if (string3.equals("-0")) {
                return "0";
            }
            return string3;
        }
        int n2 = string2.indexOf(69);
        if (n2 < 0) {
            if (string2.charAt(n - 1) == '0') {
                return string2.substring(0, n - 1);
            }
            return string2;
        }
        int n3 = Integer.parseInt(string2.substring(n2 + 1));
        if (string2.charAt(0) == '-') {
            string = "-";
            string2 = string2.substring(1);
            --n2;
        } else {
            string = "";
        }
        int n4 = n2 - 2;
        n = n2;
        if (n3 >= n4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(string2.substring(0, 1));
            stringBuilder.append(string2.substring(2, n2));
            stringBuilder.append(XNumber.zeros(n3 - n4));
            return stringBuilder.toString();
        }
        while (string2.charAt(n - 1) == '0') {
            --n;
        }
        if (n3 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(string2.substring(0, 1));
            stringBuilder.append(string2.substring(2, n3 + 2));
            stringBuilder.append(".");
            stringBuilder.append(string2.substring(n3 + 2, n));
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("0.");
        stringBuilder.append(XNumber.zeros(-1 - n3));
        stringBuilder.append(string2.substring(0, 1));
        stringBuilder.append(string2.substring(2, n));
        return stringBuilder.toString();
    }
}

