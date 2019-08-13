/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringJoiner;

public class MessageHeader {
    private String[] keys;
    private int nkeys;
    private String[] values;

    public MessageHeader() {
        this.grow();
    }

    public MessageHeader(InputStream inputStream) throws IOException {
        this.parseHeader(inputStream);
    }

    public static String canonicalID(String string) {
        block4 : {
            int n;
            char c;
            if (string == null) {
                return "";
            }
            int n2 = 0;
            int n3 = string.length();
            char c2 = '\u0000';
            do {
                n = n3;
                c = c2;
                if (n2 >= n3) break;
                char c3 = string.charAt(n2);
                if (c3 != '<') {
                    n = n3;
                    c = c2;
                    if (c3 > ' ') break;
                }
                ++n2;
                c2 = '\u0001';
            } while (true);
            while (n2 < n && ((c2 = string.charAt(n - 1)) == '>' || c2 <= ' ')) {
                --n;
                c = '\u0001';
            }
            if (c == '\u0000') break block4;
            string = string.substring(n2, n);
        }
        return string;
    }

    private void grow() {
        String[] arrstring = this.keys;
        if (arrstring == null || this.nkeys >= arrstring.length) {
            int n = this.nkeys;
            arrstring = new String[n + 4];
            String[] arrstring2 = new String[n + 4];
            String[] arrstring3 = this.keys;
            if (arrstring3 != null) {
                System.arraycopy(arrstring3, 0, arrstring, 0, n);
            }
            if ((arrstring3 = this.values) != null) {
                System.arraycopy(arrstring3, 0, arrstring2, 0, this.nkeys);
            }
            this.keys = arrstring;
            this.values = arrstring2;
        }
    }

    public void add(String string, String string2) {
        synchronized (this) {
            this.grow();
            this.keys[this.nkeys] = string;
            this.values[this.nkeys] = string2;
            ++this.nkeys;
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, List<String>> filterAndAddHeaders(String[] object, Map<String, List<String>> object2) {
        synchronized (this) {
            int n = 0;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            int n2 = this.nkeys;
            do {
                int n3;
                block15 : {
                    block16 : {
                        Object object3;
                        block14 : {
                            if ((n3 = n2 - 1) < 0) break block14;
                            n2 = n;
                            if (object == null) break block15;
                            break block16;
                        }
                        if (object3 != null) {
                            for (Map.Entry entry : object3.entrySet()) {
                                object = object3 = (List)hashMap.get(entry.getKey());
                                if (object3 == null) {
                                    object = new ArrayList();
                                    hashMap.put((String)entry.getKey(), object);
                                }
                                object.addAll((Collection)entry.getValue());
                            }
                        }
                        object = hashMap.keySet().iterator();
                        do {
                            if (!object.hasNext()) {
                                return Collections.unmodifiableMap(hashMap);
                            }
                            object3 = (String)object.next();
                            hashMap.put((String)object3, Collections.unmodifiableList((List)hashMap.get(object3)));
                        } while (true);
                    }
                    int n4 = 0;
                    do {
                        n2 = n;
                        if (n4 >= ((Object)object).length) break;
                        if (object[n4] != null && ((String)object[n4]).equalsIgnoreCase(this.keys[n3])) {
                            n2 = 1;
                            break;
                        }
                        ++n4;
                    } while (true);
                }
                if (n2 == 0) {
                    void var9_9;
                    Object object4;
                    Object object5 = object4 = (List)hashMap.get(this.keys[n3]);
                    if (object4 == null) {
                        ArrayList arrayList = new ArrayList();
                        hashMap.put(this.keys[n3], arrayList);
                    }
                    var9_9.add(this.values[n3]);
                    n = n2;
                    n2 = n3;
                    continue;
                }
                n = 0;
                n2 = n3;
            } while (true);
        }
    }

    /*
     * Exception decompiling
     */
    public boolean filterNTLMResponses(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String findNextValue(String string, String string2) {
        synchronized (this) {
            int n = 0;
            int n2 = 0;
            if (string == null) {
                int n3 = this.nkeys;
                while ((n = n3 - 1) >= 0) {
                    block13 : {
                        n3 = n;
                        if (this.keys[n] != null) continue;
                        if (n2 == 0) break block13;
                        return this.values[n];
                    }
                    n3 = n;
                    if (this.values[n] != string2) continue;
                    n2 = 1;
                    n3 = n;
                }
                return null;
            }
            int n4 = this.nkeys;
            n2 = n;
            while ((n = n4 - 1) >= 0) {
                block14 : {
                    n4 = n;
                    if (!string.equalsIgnoreCase(this.keys[n])) continue;
                    if (n2 == 0) break block14;
                    return this.values[n];
                }
                String string3 = this.values[n];
                n4 = n;
                if (string3 != string2) continue;
                n2 = 1;
                n4 = n;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String findValue(String string) {
        synchronized (this) {
            int n;
            if (string == null) {
                int n2;
                int n3 = this.nkeys;
                do {
                    if ((n2 = n3 - 1) < 0) return null;
                    n3 = n2;
                } while (this.keys[n2] != null);
                return this.values[n2];
            }
            int n2 = this.nkeys;
            do {
                if ((n = n2 - 1) < 0) return null;
                n2 = n;
            } while (!string.equalsIgnoreCase(this.keys[n]));
            return this.values[n];
        }
    }

    public String getHeaderNamesInList() {
        synchronized (this) {
            Object object = new StringJoiner(",");
            int n = 0;
            do {
                if (n >= this.nkeys) break;
                ((StringJoiner)object).add(this.keys[n]);
                ++n;
            } while (true);
            object = ((StringJoiner)object).toString();
            return object;
        }
    }

    public Map<String, List<String>> getHeaders() {
        synchronized (this) {
            Map<String, List<String>> map = this.getHeaders(null);
            return map;
        }
    }

    public Map<String, List<String>> getHeaders(String[] object) {
        synchronized (this) {
            object = this.filterAndAddHeaders((String[])object, null);
            return object;
        }
    }

    public int getKey(String string) {
        synchronized (this) {
            int n;
            int n2 = this.nkeys;
            while ((n = n2 - 1) >= 0) {
                block6 : {
                    if (this.keys[n] == string) break block6;
                    n2 = n;
                    if (string == null) continue;
                    boolean bl = string.equalsIgnoreCase(this.keys[n]);
                    n2 = n;
                    if (!bl) continue;
                }
                return n;
            }
            return -1;
        }
    }

    public String getKey(int n) {
        synchronized (this) {
            block6 : {
                if (n >= 0) {
                    if (n >= this.nkeys) {
                        break block6;
                    }
                    String string = this.keys[n];
                    return string;
                }
            }
            return null;
        }
    }

    public String getValue(int n) {
        synchronized (this) {
            block6 : {
                if (n >= 0) {
                    if (n >= this.nkeys) {
                        break block6;
                    }
                    String string = this.values[n];
                    return string;
                }
            }
            return null;
        }
    }

    public void mergeHeader(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        char[] arrc = new char[10];
        int n = inputStream.read();
        while (n != 10 && n != 13 && n >= 0) {
            int n2;
            int n3;
            String string;
            Object object;
            int n4;
            block16 : {
                int n5 = -1;
                n3 = n > 32 ? 1 : 0;
                n4 = 0 + 1;
                arrc[0] = (char)n;
                int n6 = n3;
                n3 = n5;
                do {
                    block20 : {
                        block19 : {
                            block17 : {
                                block18 : {
                                    n = n5 = inputStream.read();
                                    if (n5 < 0) break;
                                    if (n == 9) break block17;
                                    if (n == 10 || n == 13) break block18;
                                    n5 = n;
                                    if (n == 32) break block19;
                                    if (n != 58) {
                                        n5 = n6;
                                    } else {
                                        n5 = n3;
                                        if (n6 != 0) {
                                            n5 = n3;
                                            if (n4 > 0) {
                                                n5 = n4;
                                            }
                                        }
                                        n6 = 0;
                                        n3 = n5;
                                        n5 = n6;
                                    }
                                    break block20;
                                }
                                n5 = n2 = inputStream.read();
                                if (n == 13) {
                                    n5 = n2;
                                    if (n2 == 10) {
                                        n5 = n = inputStream.read();
                                        if (n == 13) {
                                            n5 = inputStream.read();
                                        }
                                    }
                                }
                                n = n5;
                                n2 = n4;
                                if (n5 == 10) break block16;
                                n = n5;
                                n2 = n4;
                                if (n5 == 13) break block16;
                                if (n5 > 32) {
                                    n = n5;
                                    n2 = n4;
                                    break block16;
                                }
                                n = 32;
                                n5 = n6;
                                break block20;
                            }
                            n5 = 32;
                        }
                        n6 = 0;
                        n = n5;
                        n5 = n6;
                    }
                    object = arrc;
                    if (n4 >= arrc.length) {
                        object = new char[arrc.length * 2];
                        System.arraycopy((Object)arrc, 0, object, 0, n4);
                    }
                    object[n4] = (char)n;
                    ++n4;
                    arrc = object;
                    n6 = n5;
                } while (true);
                n = -1;
                n2 = n4;
            }
            while (n2 > 0 && arrc[n2 - 1] <= ' ') {
                --n2;
            }
            if (n3 <= 0) {
                object = null;
                n3 = 0;
            } else {
                string = String.copyValueOf(arrc, 0, n3);
                n4 = n3 < n2 && arrc[n3] == ':' ? n3 + 1 : n3;
                do {
                    object = string;
                    n3 = n4;
                    if (n4 >= n2) break;
                    object = string;
                    n3 = n4;
                    if (arrc[n4] > ' ') break;
                    ++n4;
                } while (true);
            }
            string = n3 >= n2 ? new String() : String.copyValueOf(arrc, n3, n2 - n3);
            this.add((String)object, string);
        }
    }

    public Iterator<String> multiValueIterator(String string) {
        return new HeaderIterator(string, this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void parseHeader(InputStream inputStream) throws IOException {
        synchronized (this) {
            this.nkeys = 0;
        }
        this.mergeHeader(inputStream);
    }

    public void prepend(String string, String string2) {
        synchronized (this) {
            this.grow();
            for (int i = this.nkeys; i > 0; --i) {
                this.keys[i] = this.keys[i - 1];
                this.values[i] = this.values[i - 1];
            }
            this.keys[0] = string;
            this.values[0] = string2;
            ++this.nkeys;
            return;
        }
    }

    public void print(PrintStream printStream) {
        synchronized (this) {
            int n = 0;
            do {
                block7 : {
                    StringBuilder stringBuilder;
                    CharSequence charSequence;
                    block8 : {
                        if (n >= this.nkeys) break;
                        if (this.keys[n] == null) break block7;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(this.keys[n]);
                        if (this.values[n] != null) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(": ");
                            ((StringBuilder)charSequence).append(this.values[n]);
                            charSequence = ((StringBuilder)charSequence).toString();
                            break block8;
                        }
                        charSequence = "";
                    }
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("\r\n");
                    printStream.print(stringBuilder.toString());
                }
                ++n;
            } while (true);
            printStream.print("\r\n");
            printStream.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void remove(String string) {
        synchronized (this) {
            int n;
            int n2 = 0;
            if (string == null) {
                for (n = 0; n < this.nkeys; ++n) {
                    while (this.keys[n] == null && n < this.nkeys) {
                        for (n2 = n; n2 < this.nkeys - 1; ++n2) {
                            this.keys[n2] = this.keys[n2 + 1];
                            this.values[n2] = this.values[n2 + 1];
                        }
                        --this.nkeys;
                    }
                }
            } else {
                for (n = n2; n < this.nkeys; ++n) {
                    while (string.equalsIgnoreCase(this.keys[n]) && n < this.nkeys) {
                        for (n2 = n; n2 < this.nkeys - 1; ++n2) {
                            this.keys[n2] = this.keys[n2 + 1];
                            this.values[n2] = this.values[n2 + 1];
                        }
                        --this.nkeys;
                    }
                }
            }
            return;
        }
    }

    public void reset() {
        synchronized (this) {
            this.keys = null;
            this.values = null;
            this.nkeys = 0;
            this.grow();
            return;
        }
    }

    public void set(int n, String string, String string2) {
        synchronized (this) {
            block6 : {
                this.grow();
                if (n >= 0) break block6;
                return;
            }
            if (n >= this.nkeys) {
                this.add(string, string2);
            } else {
                this.keys[n] = string;
                this.values[n] = string2;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void set(String string, String string2) {
        synchronized (this) {
            int n;
            void var2_2;
            int n2 = this.nkeys;
            do {
                if ((n = n2 - 1) < 0) {
                    this.add(string, (String)var2_2);
                    return;
                }
                n2 = n;
            } while (!string.equalsIgnoreCase(this.keys[n]));
            this.values[n] = var2_2;
            return;
        }
    }

    public void setIfNotSet(String string, String string2) {
        synchronized (this) {
            if (this.findValue(string) == null) {
                this.add(string, string2);
            }
            return;
        }
    }

    public String toString() {
        synchronized (this) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(super.toString());
            charSequence.append(this.nkeys);
            charSequence.append(" pairs: ");
            charSequence = charSequence.toString();
            int n = 0;
            do {
                if (n >= this.keys.length || n >= this.nkeys) break;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append("{");
                stringBuilder.append(this.keys[n]);
                stringBuilder.append(": ");
                stringBuilder.append(this.values[n]);
                stringBuilder.append("}");
                charSequence = stringBuilder.toString();
                ++n;
            } while (true);
            return charSequence;
        }
    }

    class HeaderIterator
    implements Iterator<String> {
        boolean haveNext = false;
        int index = 0;
        String key;
        Object lock;
        int next = -1;

        public HeaderIterator(String string, Object object) {
            this.key = string;
            this.lock = object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean hasNext() {
            Object object = this.lock;
            synchronized (object) {
                if (this.haveNext) {
                    return true;
                }
                while (this.index < MessageHeader.this.nkeys) {
                    if (this.key.equalsIgnoreCase(MessageHeader.this.keys[this.index])) {
                        this.haveNext = true;
                        int n = this.index;
                        this.index = n + 1;
                        this.next = n;
                        return true;
                    }
                    ++this.index;
                }
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public String next() {
            Object object = this.lock;
            synchronized (object) {
                if (this.haveNext) {
                    this.haveNext = false;
                    return MessageHeader.this.values[this.next];
                }
                if (this.hasNext()) {
                    return this.next();
                }
                NoSuchElementException noSuchElementException = new NoSuchElementException("No more elements");
                throw noSuchElementException;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove not allowed");
        }
    }

}

