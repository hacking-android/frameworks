/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.duration.impl.RecordReader;
import android.icu.lang.UCharacter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordReader
implements RecordReader {
    private boolean atTag;
    private List<String> nameStack;
    private Reader r;
    private String tag;

    public XMLRecordReader(Reader reader) {
        this.r = reader;
        this.nameStack = new ArrayList<String>();
        if (this.getTag().startsWith("?xml")) {
            this.advance();
        }
        if (this.getTag().startsWith("!--")) {
            this.advance();
        }
    }

    private void advance() {
        this.tag = null;
    }

    private String getTag() {
        if (this.tag == null) {
            this.tag = this.readNextTag();
        }
        return this.tag;
    }

    private boolean match(String string) {
        if (this.getTag().equals(string)) {
            this.advance();
            return true;
        }
        return false;
    }

    private String readData() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        while ((n = this.readChar()) != -1 && n != 60) {
            int n3;
            block18 : {
                Object object;
                Appendable appendable;
                block19 : {
                    int n4;
                    n3 = n;
                    if (n != 38) break block18;
                    n3 = this.readChar();
                    if (n3 != 35) break block19;
                    appendable = new StringBuilder();
                    n = 10;
                    n3 = n4 = this.readChar();
                    if (n4 == 120) {
                        n = 16;
                        n3 = this.readChar();
                    }
                    while (n3 != 59 && n3 != -1) {
                        ((StringBuilder)appendable).append((char)n3);
                        n3 = this.readChar();
                    }
                    try {
                        n3 = Integer.parseInt(((StringBuilder)appendable).toString(), n);
                    }
                    catch (NumberFormatException numberFormatException) {
                        object = System.err;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("numbuf: ");
                        stringBuilder.append(((StringBuilder)appendable).toString());
                        stringBuilder.append(" radix: ");
                        stringBuilder.append(n);
                        ((PrintStream)object).println(stringBuilder.toString());
                        throw numberFormatException;
                    }
                    n3 = (char)n3;
                    break block18;
                }
                appendable = new StringBuilder();
                while (n3 != 59 && n3 != -1) {
                    ((StringBuilder)appendable).append((char)n3);
                    n3 = this.readChar();
                }
                object = ((StringBuilder)appendable).toString();
                if (((String)object).equals("lt")) {
                    n3 = 60;
                } else if (((String)object).equals("gt")) {
                    n3 = 62;
                } else if (((String)object).equals("quot")) {
                    n3 = 34;
                } else if (((String)object).equals("apos")) {
                    n3 = 39;
                } else if (((String)object).equals("amp")) {
                    n3 = 38;
                } else {
                    appendable = System.err;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("unrecognized character entity: '");
                    stringBuilder2.append((String)object);
                    stringBuilder2.append("'");
                    ((PrintStream)appendable).println(stringBuilder2.toString());
                    continue;
                }
            }
            if (UCharacter.isWhitespace(n3)) {
                if (n2 != 0) continue;
                n2 = 32;
                n3 = 1;
            } else {
                n = 0;
                n2 = n3;
                n3 = n;
            }
            stringBuilder.append((char)n2);
            n2 = n3;
        }
        boolean bl = n == 60;
        this.atTag = bl;
        return stringBuilder.toString();
    }

    private String readNextTag() {
        int n;
        StringBuilder stringBuilder;
        while (!this.atTag) {
            n = this.readChar();
            if (n != 60 && n != -1) {
                if (UCharacter.isWhitespace(n)) continue;
                PrintStream printStream = System.err;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected non-whitespace character ");
                stringBuilder.append(Integer.toHexString(n));
                printStream.println(stringBuilder.toString());
                break;
            }
            if (n != 60) break;
            this.atTag = true;
            break;
        }
        if (this.atTag) {
            this.atTag = false;
            stringBuilder = new StringBuilder();
            while ((n = this.readChar()) != 62 && n != -1) {
                stringBuilder.append((char)n);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public boolean bool(String string) {
        if ((string = this.string(string)) != null) {
            return "true".equals(string);
        }
        return false;
    }

    @Override
    public boolean[] boolArray(String arrstring) {
        if ((arrstring = this.stringArray((String)arrstring)) != null) {
            boolean[] arrbl = new boolean[arrstring.length];
            for (int i = 0; i < arrstring.length; ++i) {
                arrbl[i] = "true".equals(arrstring[i]);
            }
            return arrbl;
        }
        return null;
    }

    @Override
    public char character(String string) {
        if ((string = this.string(string)) != null) {
            return string.charAt(0);
        }
        return '\uffff';
    }

    @Override
    public char[] characterArray(String arrstring) {
        if ((arrstring = this.stringArray((String)arrstring)) != null) {
            char[] arrc = new char[arrstring.length];
            for (int i = 0; i < arrstring.length; ++i) {
                arrc[i] = arrstring[i].charAt(0);
            }
            return arrc;
        }
        return null;
    }

    @Override
    public boolean close() {
        int n = this.nameStack.size() - 1;
        String string = this.nameStack.get(n);
        String string2 = this.getTag();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(string);
        if (string2.equals(stringBuilder.toString())) {
            this.nameStack.remove(n);
            this.advance();
            return true;
        }
        return false;
    }

    @Override
    public byte namedIndex(String string, String[] arrstring) {
        if ((string = this.string(string)) != null) {
            for (int i = 0; i < arrstring.length; ++i) {
                if (!string.equals(arrstring[i])) continue;
                return (byte)i;
            }
        }
        return -1;
    }

    @Override
    public byte[] namedIndexArray(String arrby, String[] arrstring) {
        String[] arrstring2 = this.stringArray((String)arrby);
        if (arrstring2 != null) {
            arrby = new byte[arrstring2.length];
            block0 : for (int i = 0; i < arrstring2.length; ++i) {
                String string = arrstring2[i];
                for (int j = 0; j < arrstring.length; ++j) {
                    if (!arrstring[j].equals(string)) continue;
                    arrby[i] = (byte)j;
                    continue block0;
                }
                arrby[i] = (byte)-1;
            }
            return arrby;
        }
        return null;
    }

    @Override
    public boolean open(String string) {
        if (this.getTag().equals(string)) {
            this.nameStack.add(string);
            this.advance();
            return true;
        }
        return false;
    }

    int readChar() {
        try {
            int n = this.r.read();
            return n;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    @Override
    public String string(String string) {
        if (this.match(string)) {
            String string2 = this.readData();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(string);
            if (this.match(stringBuilder.toString())) {
                return string2;
            }
        }
        return null;
    }

    @Override
    public String[] stringArray(String string) {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("List");
        if (this.match(((StringBuilder)charSequence).toString())) {
            ArrayList<String> arrayList = new ArrayList<String>();
            do {
                CharSequence charSequence2 = this.string(string);
                charSequence = charSequence2;
                if (charSequence2 == null) break;
                charSequence2 = charSequence;
                if ("Null".equals(charSequence)) {
                    charSequence2 = null;
                }
                arrayList.add((String)charSequence2);
            } while (true);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("List");
            if (this.match(((StringBuilder)charSequence).toString())) {
                return arrayList.toArray(new String[arrayList.size()]);
            }
        }
        return null;
    }

    @Override
    public String[][] stringTable(String string) {
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("Table");
        if (this.match(((StringBuilder)serializable).toString())) {
            Object object;
            serializable = new ArrayList();
            while ((object = this.stringArray(string)) != null) {
                serializable.add(object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("Table");
            if (this.match(((StringBuilder)object).toString())) {
                return (String[][])serializable.toArray((T[])new String[serializable.size()][]);
            }
        }
        return null;
    }
}

