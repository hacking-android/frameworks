/*
 * Decompiled with CFR 0.145.
 */
package org.xmlpull.v1;

import java.io.PrintStream;
import org.xmlpull.v1.XmlPullParser;

public class XmlPullParserException
extends Exception {
    protected int column;
    protected Throwable detail;
    protected int row;

    public XmlPullParserException(String string) {
        super(string);
        this.row = -1;
        this.column = -1;
    }

    public XmlPullParserException(String charSequence, XmlPullParser xmlPullParser, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = "";
        if (charSequence == null) {
            charSequence = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)charSequence);
            stringBuilder2.append(" ");
            charSequence = stringBuilder2.toString();
        }
        stringBuilder.append((String)charSequence);
        if (xmlPullParser == null) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(position:");
            ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)charSequence).append(") ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        if (throwable == null) {
            charSequence = string;
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("caused by: ");
            ((StringBuilder)charSequence).append(throwable);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        super(stringBuilder.toString());
        this.row = -1;
        this.column = -1;
        if (xmlPullParser != null) {
            this.row = xmlPullParser.getLineNumber();
            this.column = xmlPullParser.getColumnNumber();
        }
        this.detail = throwable;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public Throwable getDetail() {
        return this.detail;
    }

    public int getLineNumber() {
        return this.row;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void printStackTrace() {
        if (this.detail == null) {
            super.printStackTrace();
            return;
        }
        PrintStream printStream = System.err;
        synchronized (printStream) {
            PrintStream printStream2 = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.getMessage());
            stringBuilder.append("; nested exception is:");
            printStream2.println(stringBuilder.toString());
            this.detail.printStackTrace();
            return;
        }
    }
}

