/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class ProcessEnvironment {
    static final int MIN_NAME_LENGTH = 0;
    private static final HashMap<Variable, Value> theEnvironment;
    private static final Map<String, String> theUnmodifiableEnvironment;

    static {
        byte[][] arrby = ProcessEnvironment.environ();
        theEnvironment = new HashMap(arrby.length / 2 + 3);
        for (int i = arrby.length - 1; i > 0; i -= 2) {
            theEnvironment.put(Variable.valueOf(arrby[i - 1]), Value.valueOf(arrby[i]));
        }
        theUnmodifiableEnvironment = Collections.unmodifiableMap(new StringEnvironment(theEnvironment));
    }

    private ProcessEnvironment() {
    }

    private static int arrayCompare(byte[] arrby, byte[] arrby2) {
        int n = arrby.length < arrby2.length ? arrby.length : arrby2.length;
        for (int i = 0; i < n; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return arrby[i] - arrby2[i];
        }
        return arrby.length - arrby2.length;
    }

    private static boolean arrayEquals(byte[] arrby, byte[] arrby2) {
        if (arrby.length != arrby2.length) {
            return false;
        }
        for (int i = 0; i < arrby.length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    private static int arrayHash(byte[] arrby) {
        int n = 0;
        for (int i = 0; i < arrby.length; ++i) {
            n = n * 31 + arrby[i];
        }
        return n;
    }

    static Map<String, String> emptyEnvironment(int n) {
        return new StringEnvironment(new HashMap<Variable, Value>(n));
    }

    private static native byte[][] environ();

    static Map<String, String> environment() {
        return new StringEnvironment((Map)theEnvironment.clone());
    }

    static String getenv(String string) {
        return theUnmodifiableEnvironment.get(string);
    }

    static Map<String, String> getenv() {
        return theUnmodifiableEnvironment;
    }

    static byte[] toEnvironmentBlock(Map<String, String> object, int[] arrn) {
        object = object == null ? null : ((StringEnvironment)object).toEnvironmentBlock(arrn);
        return object;
    }

    private static void validateValue(String string) {
        if (string.indexOf(0) == -1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid environment variable value: \"");
        stringBuilder.append(string);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void validateVariable(String string) {
        if (string.indexOf(61) == -1 && string.indexOf(0) == -1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid environment variable name: \"");
        stringBuilder.append(string);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static abstract class ExternalData {
        protected final byte[] bytes;
        protected final String str;

        protected ExternalData(String string, byte[] arrby) {
            this.str = string;
            this.bytes = arrby;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ExternalData && ProcessEnvironment.arrayEquals(this.getBytes(), ((ExternalData)object).getBytes());
            return bl;
        }

        public byte[] getBytes() {
            return this.bytes;
        }

        public int hashCode() {
            return ProcessEnvironment.arrayHash(this.getBytes());
        }

        public String toString() {
            return this.str;
        }
    }

    private static class StringEntry
    implements Map.Entry<String, String> {
        private final Map.Entry<Variable, Value> e;

        public StringEntry(Map.Entry<Variable, Value> entry) {
            this.e = entry;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof StringEntry && this.e.equals(((StringEntry)object).e);
            return bl;
        }

        @Override
        public String getKey() {
            return this.e.getKey().toString();
        }

        @Override
        public String getValue() {
            return this.e.getValue().toString();
        }

        @Override
        public int hashCode() {
            return this.e.hashCode();
        }

        @Override
        public String setValue(String string) {
            return this.e.setValue(Value.valueOf(string)).toString();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)this.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String)this.getValue());
            return stringBuilder.toString();
        }
    }

    private static class StringEntrySet
    extends AbstractSet<Map.Entry<String, String>> {
        private final Set<Map.Entry<Variable, Value>> s;

        public StringEntrySet(Set<Map.Entry<Variable, Value>> set) {
            this.s = set;
        }

        private static Map.Entry<Variable, Value> vvEntry(final Object object) {
            if (object instanceof StringEntry) {
                return ((StringEntry)object).e;
            }
            return new Map.Entry<Variable, Value>(){

                @Override
                public Variable getKey() {
                    return Variable.valueOfQueryOnly(((Map.Entry)object).getKey());
                }

                @Override
                public Value getValue() {
                    return Value.valueOfQueryOnly(((Map.Entry)object).getValue());
                }

                @Override
                public Value setValue(Value value) {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.s.contains(StringEntrySet.vvEntry(object));
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof StringEntrySet && this.s.equals(((StringEntrySet)object).s);
            return bl;
        }

        @Override
        public int hashCode() {
            return this.s.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            return new Iterator<Map.Entry<String, String>>(){
                Iterator<Map.Entry<Variable, Value>> i;
                {
                    this.i = s.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public Map.Entry<String, String> next() {
                    return new StringEntry(this.i.next());
                }

                @Override
                public void remove() {
                    this.i.remove();
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            return this.s.remove(StringEntrySet.vvEntry(object));
        }

        @Override
        public int size() {
            return this.s.size();
        }

    }

    private static class StringEnvironment
    extends AbstractMap<String, String> {
        private Map<Variable, Value> m;

        public StringEnvironment(Map<Variable, Value> map) {
            this.m = map;
        }

        private static String toString(Value object) {
            object = object == null ? null : ((ExternalData)object).toString();
            return object;
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.m.containsKey(Variable.valueOfQueryOnly(object));
        }

        @Override
        public boolean containsValue(Object object) {
            return this.m.containsValue(Value.valueOfQueryOnly(object));
        }

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return new StringEntrySet(this.m.entrySet());
        }

        @Override
        public String get(Object object) {
            return StringEnvironment.toString(this.m.get(Variable.valueOfQueryOnly(object)));
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Set<String> keySet() {
            return new StringKeySet(this.m.keySet());
        }

        @Override
        public String put(String string, String string2) {
            return StringEnvironment.toString(this.m.put(Variable.valueOf(string), Value.valueOf(string2)));
        }

        @Override
        public String remove(Object object) {
            return StringEnvironment.toString(this.m.remove(Variable.valueOfQueryOnly(object)));
        }

        @Override
        public int size() {
            return this.m.size();
        }

        public byte[] toEnvironmentBlock(int[] arrn) {
            int n = this.m.size() * 2;
            for (Map.Entry<Variable, Value> object : this.m.entrySet()) {
                n = n + object.getKey().getBytes().length + object.getValue().getBytes().length;
            }
            byte[] arrby = new byte[n];
            n = 0;
            for (Map.Entry<Variable, Value> entry : this.m.entrySet()) {
                byte[] arrby3 = entry.getKey().getBytes();
                byte[] arrby2 = entry.getValue().getBytes();
                System.arraycopy(arrby3, 0, arrby, n, arrby3.length);
                int n2 = n + arrby3.length;
                n = n2 + 1;
                arrby[n2] = (byte)61;
                System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
                n = arrby2.length + 1 + n;
            }
            arrn[0] = this.m.size();
            return arrby;
        }

        @Override
        public Collection<String> values() {
            return new StringValues(this.m.values());
        }
    }

    private static class StringKeySet
    extends AbstractSet<String> {
        private final Set<Variable> s;

        public StringKeySet(Set<Variable> set) {
            this.s = set;
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.s.contains(Variable.valueOfQueryOnly(object));
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>(){
                Iterator<Variable> i;
                {
                    this.i = s.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public String next() {
                    return this.i.next().toString();
                }

                @Override
                public void remove() {
                    this.i.remove();
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            return this.s.remove(Variable.valueOfQueryOnly(object));
        }

        @Override
        public int size() {
            return this.s.size();
        }

    }

    private static class StringValues
    extends AbstractCollection<String> {
        private final Collection<Value> c;

        public StringValues(Collection<Value> collection) {
            this.c = collection;
        }

        @Override
        public void clear() {
            this.c.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.c.contains(Value.valueOfQueryOnly(object));
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof StringValues && this.c.equals(((StringValues)object).c);
            return bl;
        }

        @Override
        public int hashCode() {
            return this.c.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>(){
                Iterator<Value> i;
                {
                    this.i = c.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public String next() {
                    return this.i.next().toString();
                }

                @Override
                public void remove() {
                    this.i.remove();
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            return this.c.remove(Value.valueOfQueryOnly(object));
        }

        @Override
        public int size() {
            return this.c.size();
        }

    }

    private static class Value
    extends ExternalData
    implements Comparable<Value> {
        protected Value(String string, byte[] arrby) {
            super(string, arrby);
        }

        public static Value valueOf(String string) {
            ProcessEnvironment.validateValue(string);
            return Value.valueOfQueryOnly(string);
        }

        public static Value valueOf(byte[] arrby) {
            return new Value(new String(arrby), arrby);
        }

        public static Value valueOfQueryOnly(Object object) {
            return Value.valueOfQueryOnly((String)object);
        }

        public static Value valueOfQueryOnly(String string) {
            return new Value(string, string.getBytes());
        }

        @Override
        public int compareTo(Value value) {
            return ProcessEnvironment.arrayCompare(this.getBytes(), value.getBytes());
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Value && super.equals(object);
            return bl;
        }
    }

    private static class Variable
    extends ExternalData
    implements Comparable<Variable> {
        protected Variable(String string, byte[] arrby) {
            super(string, arrby);
        }

        public static Variable valueOf(String string) {
            ProcessEnvironment.validateVariable(string);
            return Variable.valueOfQueryOnly(string);
        }

        public static Variable valueOf(byte[] arrby) {
            return new Variable(new String(arrby), arrby);
        }

        public static Variable valueOfQueryOnly(Object object) {
            return Variable.valueOfQueryOnly((String)object);
        }

        public static Variable valueOfQueryOnly(String string) {
            return new Variable(string, string.getBytes());
        }

        @Override
        public int compareTo(Variable variable) {
            return ProcessEnvironment.arrayCompare(this.getBytes(), variable.getBytes());
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Variable && super.equals(object);
            return bl;
        }
    }

}

