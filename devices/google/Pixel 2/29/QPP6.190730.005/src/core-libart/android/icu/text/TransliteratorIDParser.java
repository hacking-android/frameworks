/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.impl.Utility;
import android.icu.text.SymbolTable;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.icu.util.CaseInsensitiveString;
import java.io.Serializable;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class TransliteratorIDParser {
    private static final String ANY = "Any";
    private static final char CLOSE_REV = ')';
    private static final int FORWARD = 0;
    private static final char ID_DELIM = ';';
    private static final char OPEN_REV = '(';
    private static final int REVERSE = 1;
    private static final Map<CaseInsensitiveString, String> SPECIAL_INVERSES = Collections.synchronizedMap(new HashMap());
    private static final char TARGET_SEP = '-';
    private static final char VARIANT_SEP = '/';

    TransliteratorIDParser() {
    }

    public static String[] IDtoSTV(String string) {
        String string2;
        String string3;
        int n;
        String string4 = ANY;
        int n2 = string.indexOf(45);
        int n3 = n = string.indexOf(47);
        if (n < 0) {
            n3 = string.length();
        }
        int n4 = 0;
        int n5 = 0;
        n = 0;
        if (n2 < 0) {
            string3 = string.substring(0, n3);
            string = string.substring(n3);
            n = n5;
        } else if (n2 < n3) {
            if (n2 > 0) {
                string4 = string.substring(0, n2);
                n = 1;
            }
            string3 = string.substring(n2 + 1, n3);
            string = string.substring(n3);
        } else {
            n = n4;
            if (n3 > 0) {
                string4 = string.substring(0, n3);
                n = 1;
            }
            string3 = string.substring(n3, n2);
            string2 = string.substring(n2 + 1);
            string = string3;
            string3 = string2;
        }
        string2 = string;
        if (string.length() > 0) {
            string2 = string.substring(1);
        }
        string = n != 0 ? "" : null;
        return new String[]{string4, string3, string2, string};
    }

    public static String STVtoID(String charSequence, String string, String string2) {
        if (((StringBuilder)(charSequence = new StringBuilder((String)charSequence))).length() == 0) {
            ((StringBuilder)charSequence).append(ANY);
        }
        ((StringBuilder)charSequence).append('-');
        ((StringBuilder)charSequence).append(string);
        if (string2 != null && string2.length() != 0) {
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(string2);
        }
        return ((StringBuilder)charSequence).toString();
    }

    static List<Transliterator> instantiateList(List<SingleID> object) {
        Serializable serializable = new ArrayList<Transliterator>();
        Iterator<SingleID> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((SingleID)object).basicID.length() == 0) continue;
            Transliterator transliterator = ((SingleID)object).getInstance();
            if (transliterator != null) {
                serializable.add((Transliterator)transliterator);
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Illegal ID ");
            ((StringBuilder)serializable).append(((SingleID)object).canonID);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        if (serializable.size() == 0) {
            object = Transliterator.getBasicInstance("Any-Null", null);
            if (object != null) {
                serializable.add((Transliterator)object);
            } else {
                throw new IllegalArgumentException("Internal error; cannot instantiate Any-Null");
            }
        }
        return serializable;
    }

    public static boolean parseCompoundID(String string, int n, StringBuffer object, List<SingleID> list, UnicodeSet[] arrunicodeSet) {
        int[] arrn = new int[]{0};
        int[] arrn2 = new int[1];
        list.clear();
        arrunicodeSet[0] = null;
        ((StringBuffer)object).setLength(0);
        arrn2[0] = 0;
        Object object2 = TransliteratorIDParser.parseGlobalFilter(string, arrn, n, arrn2, (StringBuffer)object);
        if (object2 != null) {
            if (!Utility.parseChar(string, arrn, ';')) {
                ((StringBuffer)object).setLength(0);
                arrn[0] = 0;
            }
            if (n == 0) {
                arrunicodeSet[0] = object2;
            }
        }
        boolean bl = true;
        while ((object2 = TransliteratorIDParser.parseSingleID(string, arrn, n)) != null) {
            if (n == 0) {
                list.add((SingleID)object2);
            } else {
                list.add(0, (SingleID)object2);
            }
            if (Utility.parseChar(string, arrn, ';')) continue;
            bl = false;
            break;
        }
        if (list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            ((StringBuffer)object).append(list.get((int)i).canonID);
            if (i == list.size() - 1) continue;
            ((StringBuffer)object).append(';');
        }
        if (bl) {
            arrn2[0] = 1;
            if ((object = TransliteratorIDParser.parseGlobalFilter(string, arrn, n, arrn2, (StringBuffer)object)) != null) {
                Utility.parseChar(string, arrn, ';');
                if (n == 1) {
                    arrunicodeSet[0] = object;
                }
            }
        }
        arrn[0] = PatternProps.skipWhiteSpace(string, arrn[0]);
        return arrn[0] == string.length();
    }

    public static SingleID parseFilterID(String object, int[] object2) {
        int n = object2[0];
        if ((object = TransliteratorIDParser.parseFilterID((String)object, object2, true)) == null) {
            object2[0] = n;
            return null;
        }
        object2 = TransliteratorIDParser.specsToID((Specs)object, 0);
        object2.filter = ((Specs)object).filter;
        return object2;
    }

    private static Specs parseFilterID(String string, int[] object, boolean bl) {
        String string2 = null;
        String string3 = null;
        Object object2 = null;
        Object object3 = null;
        String string4 = null;
        char c = '\u0000';
        int n = 0;
        int n2 = object[0];
        do {
            Object object4;
            block19 : {
                block18 : {
                    char c2;
                    object[0] = PatternProps.skipWhiteSpace(string, object[0]);
                    if (object[0] == string.length()) break block18;
                    if (bl && string4 == null && UnicodeSet.resemblesPattern(string, object[0])) {
                        object4 = new ParsePosition(object[0]);
                        new UnicodeSet(string, (ParsePosition)object4, null);
                        string4 = string.substring(object[0], ((ParsePosition)object4).getIndex());
                        object[0] = ((ParsePosition)object4).getIndex();
                        continue;
                    }
                    if (c == '\u0000' && ((c2 = string.charAt(object[0])) == '-' && object2 == null || c2 == '/' && object3 == null)) {
                        c = c2;
                        object[0] = object[0] + 1;
                        continue;
                    }
                    if ((c != '\u0000' || n <= 0) && (object4 = Utility.parseUnicodeIdentifier(string, object)) != null) break block19;
                }
                object4 = string3;
                string = object2;
                if (string2 != null) {
                    if (object2 == null) {
                        string = string2;
                        object4 = string3;
                    } else {
                        string = object2;
                        object4 = string2;
                    }
                }
                if (object4 == null && string == null) {
                    object[0] = n2;
                    return null;
                }
                bl = true;
                object = object4;
                if (object4 == null) {
                    object = ANY;
                    bl = false;
                }
                string2 = string;
                if (string == null) {
                    string2 = ANY;
                }
                return new Specs((String)object, string2, (String)object3, bl, string4);
            }
            if (c != '\u0000') {
                if (c != '-') {
                    if (c == '/') {
                        object3 = object4;
                    }
                } else {
                    object2 = object4;
                }
            } else {
                string2 = object4;
            }
            ++n;
            c = '\u0000';
        } while (true);
    }

    public static UnicodeSet parseGlobalFilter(String charSequence, int[] object, int n, int[] arrn, StringBuffer stringBuffer) {
        Object object2 = null;
        int n2 = object[0];
        if (arrn[0] == -1) {
            arrn[0] = Utility.parseChar((String)charSequence, (int[])object, '(') ? 1 : 0;
        } else if (arrn[0] == 1 && !Utility.parseChar((String)charSequence, (int[])object, '(')) {
            object[0] = n2;
            return null;
        }
        object[0] = PatternProps.skipWhiteSpace(charSequence, object[0]);
        if (UnicodeSet.resemblesPattern((String)charSequence, object[0])) {
            UnicodeSet unicodeSet;
            object2 = new ParsePosition(object[0]);
            try {
                unicodeSet = new UnicodeSet((String)charSequence, (ParsePosition)object2, null);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object[0] = n2;
                return null;
            }
            String string = ((String)charSequence).substring(object[0], ((ParsePosition)object2).getIndex());
            object[0] = ((ParsePosition)object2).getIndex();
            if (arrn[0] == 1 && !Utility.parseChar((String)charSequence, (int[])object, ')')) {
                object[0] = n2;
                return null;
            }
            object2 = unicodeSet;
            if (stringBuffer != null) {
                if (n == 0) {
                    charSequence = string;
                    if (arrn[0] == 1) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(String.valueOf('('));
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(')');
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append(';');
                    stringBuffer.append(((StringBuilder)object).toString());
                    object2 = unicodeSet;
                } else {
                    charSequence = string;
                    if (arrn[0] == 0) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(String.valueOf('('));
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(')');
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append(';');
                    stringBuffer.insert(0, ((StringBuilder)object).toString());
                    object2 = unicodeSet;
                }
            }
        }
        return object2;
    }

    public static SingleID parseSingleID(String object, int[] object2, int n) {
        Specs specs;
        int n2;
        Specs specs2;
        int n3 = object2[0];
        Specs specs3 = null;
        Specs specs4 = null;
        int n4 = 0;
        int n5 = 1;
        do {
            specs = specs3;
            specs2 = specs4;
            n2 = n4;
            if (n5 > 2) break;
            if (n5 == 2) {
                specs3 = specs = TransliteratorIDParser.parseFilterID((String)object, object2, true);
                if (specs == null) {
                    object2[0] = n3;
                    return null;
                }
            }
            if (Utility.parseChar((String)object, object2, '(')) {
                n5 = 1;
                specs = specs3;
                specs2 = specs4;
                n2 = n5;
                if (Utility.parseChar((String)object, object2, ')')) break;
                specs2 = TransliteratorIDParser.parseFilterID((String)object, object2, true);
                if (specs2 != null) {
                    specs = specs3;
                    n2 = n5;
                    if (Utility.parseChar((String)object, object2, ')')) break;
                }
                object2[0] = n3;
                return null;
            }
            ++n5;
        } while (true);
        if (n2 != 0) {
            if (n == 0) {
                object2 = TransliteratorIDParser.specsToID(specs, 0);
                object = new StringBuilder();
                object.append(object2.canonID);
                object.append('(');
                object.append(TransliteratorIDParser.specsToID(specs2, (int)0).canonID);
                object.append(')');
                object2.canonID = object.toString();
                object = object2;
                if (specs != null) {
                    object2.filter = specs.filter;
                    object = object2;
                }
            } else {
                object2 = TransliteratorIDParser.specsToID(specs2, 0);
                object = new StringBuilder();
                object.append(object2.canonID);
                object.append('(');
                object.append(TransliteratorIDParser.specsToID((Specs)specs, (int)0).canonID);
                object.append(')');
                object2.canonID = object.toString();
                object = object2;
                if (specs2 != null) {
                    object2.filter = specs2.filter;
                    object = object2;
                }
            }
        } else {
            if (n == 0) {
                object = TransliteratorIDParser.specsToID(specs, 0);
            } else {
                object = TransliteratorIDParser.specsToSpecialInverse(specs);
                if (object == null) {
                    object = TransliteratorIDParser.specsToID(specs, 1);
                }
            }
            object.filter = specs.filter;
        }
        return object;
    }

    public static void registerSpecialInverse(String string, String string2, boolean bl) {
        SPECIAL_INVERSES.put(new CaseInsensitiveString(string), string2);
        if (bl && !string.equalsIgnoreCase(string2)) {
            SPECIAL_INVERSES.put(new CaseInsensitiveString(string2), string);
        }
    }

    private static SingleID specsToID(Specs specs, int n) {
        CharSequence charSequence = "";
        CharSequence charSequence2 = "";
        CharSequence charSequence3 = "";
        if (specs != null) {
            charSequence = new StringBuilder();
            if (n == 0) {
                if (specs.sawSource) {
                    ((StringBuilder)charSequence).append(specs.source);
                    ((StringBuilder)charSequence).append('-');
                } else {
                    charSequence3 = new StringBuilder();
                    ((StringBuilder)charSequence3).append(specs.source);
                    ((StringBuilder)charSequence3).append('-');
                    charSequence3 = ((StringBuilder)charSequence3).toString();
                }
                ((StringBuilder)charSequence).append(specs.target);
            } else {
                ((StringBuilder)charSequence).append(specs.target);
                ((StringBuilder)charSequence).append('-');
                ((StringBuilder)charSequence).append(specs.source);
            }
            if (specs.variant != null) {
                ((StringBuilder)charSequence).append('/');
                ((StringBuilder)charSequence).append(specs.variant);
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence3);
            ((StringBuilder)charSequence2).append(((StringBuilder)charSequence).toString());
            charSequence2 = ((StringBuilder)charSequence2).toString();
            if (specs.filter != null) {
                ((StringBuilder)charSequence).insert(0, specs.filter);
            }
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return new SingleID((String)charSequence, (String)charSequence2);
    }

    private static SingleID specsToSpecialInverse(Specs specs) {
        if (!specs.source.equalsIgnoreCase(ANY)) {
            return null;
        }
        String string = SPECIAL_INVERSES.get(new CaseInsensitiveString(specs.target));
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (specs.filter != null) {
                stringBuilder.append(specs.filter);
            }
            if (specs.sawSource) {
                stringBuilder.append(ANY);
                stringBuilder.append('-');
            }
            stringBuilder.append(string);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Any-");
            ((StringBuilder)charSequence).append(string);
            string = ((StringBuilder)charSequence).toString();
            charSequence = string;
            if (specs.variant != null) {
                stringBuilder.append('/');
                stringBuilder.append(specs.variant);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append('/');
                ((StringBuilder)charSequence).append(specs.variant);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return new SingleID(stringBuilder.toString(), (String)charSequence);
        }
        return null;
    }

    static class SingleID {
        public String basicID;
        public String canonID;
        public String filter;

        SingleID(String string, String string2) {
            this(string, string2, null);
        }

        SingleID(String string, String string2, String string3) {
            this.canonID = string;
            this.basicID = string2;
            this.filter = string3;
        }

        Transliterator getInstance() {
            String string;
            Object object = this.basicID;
            object = object != null && ((String)object).length() != 0 ? Transliterator.getBasicInstance(this.basicID, this.canonID) : Transliterator.getBasicInstance("Any-Null", this.canonID);
            if (object != null && (string = this.filter) != null) {
                ((Transliterator)object).setFilter(new UnicodeSet(string));
            }
            return object;
        }
    }

    private static class Specs {
        public String filter;
        public boolean sawSource;
        public String source;
        public String target;
        public String variant;

        Specs(String string, String string2, String string3, boolean bl, String string4) {
            this.source = string;
            this.target = string2;
            this.variant = string3;
            this.sawSource = bl;
            this.filter = string4;
        }
    }

}

