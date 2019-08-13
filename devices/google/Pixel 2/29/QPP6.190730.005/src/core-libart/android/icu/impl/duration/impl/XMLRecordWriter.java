/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.duration.impl.RecordWriter;
import android.icu.lang.UCharacter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordWriter
implements RecordWriter {
    private static final String INDENT = "    ";
    static final String NULL_NAME = "Null";
    private List<String> nameStack;
    private Writer w;

    public XMLRecordWriter(Writer writer) {
        this.w = writer;
        this.nameStack = new ArrayList<String>();
    }

    private static String ctos(char c) {
        if (c == '<') {
            return "&lt;";
        }
        if (c == '&') {
            return "&amp;";
        }
        return String.valueOf(c);
    }

    private void internalString(String string, String string2) {
        if (string2 != null) {
            this.newline();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<");
            stringBuilder.append(string);
            stringBuilder.append(">");
            stringBuilder.append(string2);
            stringBuilder.append("</");
            stringBuilder.append(string);
            stringBuilder.append(">");
            this.writeString(stringBuilder.toString());
        }
    }

    private void internalStringArray(String string, String[] arrstring) {
        if (arrstring != null) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append("List");
            this.push(charSequence.toString());
            for (int i = 0; i < arrstring.length; ++i) {
                String string2 = arrstring[i];
                charSequence = string2;
                if (string2 == null) {
                    charSequence = NULL_NAME;
                }
                this.string(string, (String)charSequence);
            }
            this.pop();
        }
    }

    private void newline() {
        this.writeString("\n");
        for (int i = 0; i < this.nameStack.size(); ++i) {
            this.writeString(INDENT);
        }
    }

    public static String normalize(String string) {
        if (string == null) {
            return null;
        }
        CharSequence charSequence = null;
        char c = '\u0000';
        for (int i = 0; i < string.length(); ++i) {
            char c2;
            StringBuilder stringBuilder;
            char c3;
            char c4;
            block13 : {
                char c5;
                block10 : {
                    block11 : {
                        block12 : {
                            c5 = string.charAt(i);
                            if (!UCharacter.isWhitespace(c5)) break block10;
                            stringBuilder = charSequence;
                            if (charSequence != null) break block11;
                            if (c != '\u0000') break block12;
                            stringBuilder = charSequence;
                            if (c5 == ' ') break block11;
                        }
                        stringBuilder = new StringBuilder(string.substring(0, i));
                    }
                    if (c != '\u0000') {
                        charSequence = stringBuilder;
                        continue;
                    }
                    c2 = '\u0001';
                    c3 = '\u0000';
                    c4 = c = ' ';
                    break block13;
                }
                char c6 = '\u0000';
                c2 = c5 != '<' && c5 != '&' ? (char)'\u0000' : '\u0001';
                c = c2;
                stringBuilder = charSequence;
                c2 = c6;
                c4 = c5;
                c3 = c;
                if (c != '\u0000') {
                    stringBuilder = charSequence;
                    c2 = c6;
                    c4 = c5;
                    c3 = c;
                    if (charSequence == null) {
                        stringBuilder = new StringBuilder(string.substring(0, i));
                        c3 = c;
                        c4 = c5;
                        c2 = c6;
                    }
                }
            }
            charSequence = stringBuilder;
            c = c2;
            if (stringBuilder == null) continue;
            if (c3 != '\u0000') {
                charSequence = c4 == '<' ? "&lt;" : "&amp;";
                stringBuilder.append((String)charSequence);
                charSequence = stringBuilder;
                c = c2;
                continue;
            }
            stringBuilder.append(c4);
            c = c2;
            charSequence = stringBuilder;
        }
        if (charSequence != null) {
            return charSequence.toString();
        }
        return string;
    }

    private void pop() {
        int n = this.nameStack.size();
        String string = this.nameStack.remove(n - 1);
        this.newline();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</");
        stringBuilder.append(string);
        stringBuilder.append(">");
        this.writeString(stringBuilder.toString());
    }

    private void push(String string) {
        this.newline();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(string);
        stringBuilder.append(">");
        this.writeString(stringBuilder.toString());
        this.nameStack.add(string);
    }

    private void writeString(String string) {
        Writer writer = this.w;
        if (writer != null) {
            try {
                writer.write(string);
            }
            catch (IOException iOException) {
                System.err.println(iOException.getMessage());
                this.w = null;
            }
        }
    }

    @Override
    public void bool(String string, boolean bl) {
        this.internalString(string, String.valueOf(bl));
    }

    @Override
    public void boolArray(String string, boolean[] arrbl) {
        if (arrbl != null) {
            String[] arrstring = new String[arrbl.length];
            for (int i = 0; i < arrbl.length; ++i) {
                arrstring[i] = String.valueOf(arrbl[i]);
            }
            this.stringArray(string, arrstring);
        }
    }

    @Override
    public void character(String string, char c) {
        if (c != '\uffff') {
            this.internalString(string, XMLRecordWriter.ctos(c));
        }
    }

    @Override
    public void characterArray(String string, char[] arrc) {
        if (arrc != null) {
            String[] arrstring = new String[arrc.length];
            for (int i = 0; i < arrc.length; ++i) {
                char c = arrc[i];
                arrstring[i] = c == '\uffff' ? NULL_NAME : XMLRecordWriter.ctos(c);
            }
            this.internalStringArray(string, arrstring);
        }
    }

    @Override
    public boolean close() {
        int n = this.nameStack.size() - 1;
        if (n >= 0) {
            String string = this.nameStack.remove(n);
            this.newline();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("</");
            stringBuilder.append(string);
            stringBuilder.append(">");
            this.writeString(stringBuilder.toString());
            return true;
        }
        return false;
    }

    public void flush() {
        try {
            this.w.flush();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void namedIndex(String string, String[] arrstring, int n) {
        if (n >= 0) {
            this.internalString(string, arrstring[n]);
        }
    }

    @Override
    public void namedIndexArray(String string, String[] arrstring, byte[] arrby) {
        if (arrby != null) {
            String[] arrstring2 = new String[arrby.length];
            for (int i = 0; i < arrby.length; ++i) {
                byte by = arrby[i];
                arrstring2[i] = by < 0 ? NULL_NAME : arrstring[by];
            }
            this.internalStringArray(string, arrstring2);
        }
    }

    @Override
    public boolean open(String string) {
        this.newline();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(string);
        stringBuilder.append(">");
        this.writeString(stringBuilder.toString());
        this.nameStack.add(string);
        return true;
    }

    @Override
    public void string(String string, String string2) {
        this.internalString(string, XMLRecordWriter.normalize(string2));
    }

    @Override
    public void stringArray(String string, String[] arrstring) {
        if (arrstring != null) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append("List");
            this.push(charSequence.toString());
            for (int i = 0; i < arrstring.length; ++i) {
                String string2 = XMLRecordWriter.normalize(arrstring[i]);
                charSequence = string2;
                if (string2 == null) {
                    charSequence = NULL_NAME;
                }
                this.internalString(string, (String)charSequence);
            }
            this.pop();
        }
    }

    @Override
    public void stringTable(String string, String[][] arrstring) {
        if (arrstring != null) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("Table");
            this.push(((StringBuilder)object).toString());
            for (int i = 0; i < arrstring.length; ++i) {
                object = arrstring[i];
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("List");
                    this.internalString(((StringBuilder)object).toString(), NULL_NAME);
                    continue;
                }
                this.stringArray(string, (String[])object);
            }
            this.pop();
        }
    }
}

