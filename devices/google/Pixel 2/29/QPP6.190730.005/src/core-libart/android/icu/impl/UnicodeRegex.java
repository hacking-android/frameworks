/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.StringTransform;
import android.icu.text.SymbolTable;
import android.icu.text.UnicodeSet;
import android.icu.util.Freezable;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class UnicodeRegex
implements Cloneable,
Freezable<UnicodeRegex>,
StringTransform {
    private static final UnicodeRegex STANDARD = new UnicodeRegex();
    private Comparator<Object> LongestFirst = new Comparator<Object>(){

        @Override
        public int compare(Object object, Object object2) {
            int n;
            object = object.toString();
            object2 = object2.toString();
            int n2 = ((String)object).length();
            if (n2 != (n = ((String)object2).length())) {
                return n - n2;
            }
            return ((String)object).compareTo((String)object2);
        }
    };
    private String bnfCommentString = "#";
    private String bnfLineSeparator = "\n";
    private String bnfVariableInfix = "=";
    private SymbolTable symbolTable;

    public static List<String> appendLines(List<String> list, InputStream closeable, String string) throws UnsupportedEncodingException, IOException {
        if (string == null) {
            string = "UTF-8";
        }
        closeable = new BufferedReader(new InputStreamReader((InputStream)closeable, string));
        while ((string = ((BufferedReader)closeable).readLine()) != null) {
            list.add(string);
        }
        return list;
    }

    public static List<String> appendLines(List<String> list, String object, String string) throws IOException {
        object = new FileInputStream((String)object);
        try {
            list = UnicodeRegex.appendLines(list, (InputStream)object, string);
            return list;
        }
        finally {
            ((InputStream)object).close();
        }
    }

    public static Pattern compile(String string) {
        return Pattern.compile(STANDARD.transform(string));
    }

    public static Pattern compile(String string, int n) {
        return Pattern.compile(STANDARD.transform(string), n);
    }

    public static String fix(String string) {
        return STANDARD.transform(string);
    }

    private Map<String, String> getVariables(List<String> object) {
        TreeMap<Object, String> treeMap = new TreeMap<Object, String>(this.LongestFirst);
        Object object2 = null;
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        Iterator<String> iterator = object.iterator();
        while (iterator.hasNext()) {
            block13 : {
                boolean bl;
                block12 : {
                    Object object3;
                    block9 : {
                        block10 : {
                            block11 : {
                                int n2;
                                object = iterator.next();
                                ++n;
                                if (((String)object).length() == 0) continue;
                                Object object4 = object;
                                if (((String)object).charAt(0) == '\ufeff') {
                                    object4 = ((String)object).substring(1);
                                }
                                object3 = this.bnfCommentString;
                                object = object4;
                                if (object3 != null) {
                                    n2 = ((String)object4).indexOf((String)object3);
                                    object = object4;
                                    if (n2 >= 0) {
                                        object = ((String)object4).substring(0, n2);
                                    }
                                }
                                if (((String)(object3 = ((String)object).trim())).length() == 0 || ((String)(object4 = object)).trim().length() == 0) continue;
                                bl = ((String)object3).endsWith(";");
                                object3 = object4;
                                if (bl) {
                                    object3 = ((String)object4).substring(0, ((String)object4).lastIndexOf(59));
                                }
                                if ((n2 = ((String)object3).indexOf(this.bnfVariableInfix)) < 0) break block9;
                                if (object2 != null) break block10;
                                object2 = ((String)object3).substring(0, n2).trim();
                                if (treeMap.containsKey(object2)) break block11;
                                stringBuffer.append(((String)object3).substring(n2 + 1).trim());
                                object = object2;
                                break block12;
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Duplicate variable definition in ");
                            ((StringBuilder)object2).append((String)object);
                            throw new IllegalArgumentException(((StringBuilder)object2).toString());
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Missing ';' before ");
                        ((StringBuilder)object2).append(n);
                        ((StringBuilder)object2).append(") ");
                        ((StringBuilder)object2).append((String)object);
                        throw new IllegalArgumentException(((StringBuilder)object2).toString());
                    }
                    if (object2 == null) break block13;
                    stringBuffer.append(this.bnfLineSeparator);
                    stringBuffer.append((String)object3);
                    object = object2;
                }
                object2 = object;
                if (!bl) continue;
                treeMap.put(object, stringBuffer.toString());
                object2 = null;
                stringBuffer.setLength(0);
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Missing '=' at ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(") ");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        if (object2 == null) {
            return treeMap;
        }
        throw new IllegalArgumentException("Missing ';' at end");
    }

    private int processSet(String string, int n, StringBuilder stringBuilder, UnicodeSet unicodeSet, ParsePosition parsePosition) {
        try {
            parsePosition.setIndex(n);
            unicodeSet = unicodeSet.clear().applyPattern(string, parsePosition, this.symbolTable, 0);
            unicodeSet.complement().complement();
            stringBuilder.append(unicodeSet.toPattern(false));
            n = parsePosition.getIndex();
        }
        catch (Exception exception) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Error in ");
            stringBuilder.append(string);
            throw (IllegalArgumentException)new IllegalArgumentException(stringBuilder.toString()).initCause(exception);
        }
        return n - 1;
    }

    @Override
    public UnicodeRegex cloneAsThawed() {
        try {
            UnicodeRegex unicodeRegex = (UnicodeRegex)this.clone();
            return unicodeRegex;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalArgumentException();
        }
    }

    public String compileBnf(String string) {
        return this.compileBnf(Arrays.asList(string.split("\\r\\n?|\\n")));
    }

    public String compileBnf(List<String> collection) {
        Map<String, String> map = this.getVariables((List<String>)collection);
        collection = new LinkedHashSet<String>(map.keySet());
        for (int i = 0; i < 2; ++i) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String string = entry.getKey();
                String string2 = entry.getValue();
                for (Object object : map.entrySet()) {
                    String string3;
                    String string4 = object.getKey();
                    object = object.getValue();
                    if (string.equals(string4) || (string3 = ((String)object).replace(string, string2)).equals(object)) continue;
                    collection.remove(string);
                    map.put(string4, string3);
                }
            }
        }
        if (collection.size() == 1) {
            return map.get(collection.iterator().next());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a single root: ");
        stringBuilder.append(collection);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public UnicodeRegex freeze() {
        return this;
    }

    public String getBnfCommentString() {
        return this.bnfCommentString;
    }

    public String getBnfLineSeparator() {
        return this.bnfLineSeparator;
    }

    public String getBnfVariableInfix() {
        return this.bnfVariableInfix;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    @Override
    public boolean isFrozen() {
        return true;
    }

    public void setBnfCommentString(String string) {
        this.bnfCommentString = string;
    }

    public void setBnfLineSeparator(String string) {
        this.bnfLineSeparator = string;
    }

    public void setBnfVariableInfix(String string) {
        this.bnfVariableInfix = string;
    }

    public UnicodeRegex setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        return this;
    }

    @Override
    public String transform(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        UnicodeSet unicodeSet = new UnicodeSet();
        ParsePosition parsePosition = new ParsePosition(0);
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c;
            block8 : {
                block5 : {
                    block6 : {
                        block7 : {
                            block9 : {
                                c = string.charAt(i);
                                if (n == 0) break block5;
                                if (n == true) break block6;
                                if (n == 2) break block7;
                                if (n != 3) break block8;
                                if (c != 'E') break block9;
                                n = 0;
                                break block8;
                            }
                            if (c == '\\') break block8;
                            n = 2;
                            break block8;
                        }
                        if (c != '\\') break block8;
                        n = 3;
                        break block8;
                    }
                    n = c == 'Q' ? 2 : 0;
                    break block8;
                }
                if (c == '\\') {
                    if (UnicodeSet.resemblesPattern(string, i)) {
                        i = this.processSet(string, i, stringBuilder, unicodeSet, parsePosition);
                        continue;
                    }
                    n = 1;
                } else if (c == '[' && UnicodeSet.resemblesPattern(string, i)) {
                    i = this.processSet(string, i, stringBuilder, unicodeSet, parsePosition);
                    continue;
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

}

