/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class Headers {
    private final String[] namesAndValues;

    private Headers(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] arrstring) {
        this.namesAndValues = arrstring;
    }

    private static String get(String[] arrstring, String string) {
        for (int i = arrstring.length - 2; i >= 0; i -= 2) {
            if (!string.equalsIgnoreCase(arrstring[i])) continue;
            return arrstring[i + 1];
        }
        return null;
    }

    public static Headers of(Map<String, String> object) {
        if (object != null) {
            Object object2 = new String[object.size() * 2];
            int n = 0;
            for (Map.Entry<String, String> entry : object.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    object = entry.getKey().trim();
                    entry = entry.getValue().trim();
                    if (((String)object).length() != 0 && ((String)object).indexOf(0) == -1 && ((String)((Object)entry)).indexOf(0) == -1) {
                        object2[n] = object;
                        object2[n + 1] = entry;
                        n += 2;
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unexpected header: ");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(": ");
                    ((StringBuilder)object2).append((String)((Object)entry));
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                throw new IllegalArgumentException("Headers cannot be null");
            }
            return new Headers((String[])object2);
        }
        throw new IllegalArgumentException("Expected map with header names and values");
    }

    public static Headers of(String ... object) {
        if (object != null && ((String[])object).length % 2 == 0) {
            int n;
            Object object2 = (String[])object.clone();
            for (n = 0; n < ((String[])object2).length; ++n) {
                if (object2[n] != null) {
                    object2[n] = object2[n].trim();
                    continue;
                }
                throw new IllegalArgumentException("Headers cannot be null");
            }
            for (n = 0; n < ((String[])object2).length; n += 2) {
                object = object2[n];
                String string = object2[n + 1];
                if (((String)object).length() != 0 && ((String)object).indexOf(0) == -1 && string.indexOf(0) == -1) {
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unexpected header: ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(string);
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            return new Headers((String[])object2);
        }
        throw new IllegalArgumentException("Expected alternating header names and values");
    }

    public String get(String string) {
        return Headers.get(this.namesAndValues, string);
    }

    public Date getDate(String object) {
        object = (object = this.get((String)object)) != null ? HttpDate.parse((String)object) : null;
        return object;
    }

    public String name(int n) {
        String[] arrstring;
        if ((n *= 2) >= 0 && n < (arrstring = this.namesAndValues).length) {
            return arrstring[n];
        }
        return null;
    }

    public Set<String> names() {
        TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            treeSet.add(this.name(i));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        Collections.addAll(builder.namesAndValues, this.namesAndValues);
        return builder;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public Map<String, List<String>> toMultimap() {
        LinkedHashMap<String, List<String>> linkedHashMap = new LinkedHashMap<String, List<String>>();
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<String> arrayList;
            String string = this.name(i);
            ArrayList<String> arrayList2 = arrayList = (ArrayList<String>)linkedHashMap.get(string);
            if (arrayList == null) {
                arrayList2 = new ArrayList<String>(2);
                linkedHashMap.put(string, arrayList2);
            }
            arrayList2.add(this.value(i));
        }
        return linkedHashMap;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.name(i));
            stringBuilder.append(": ");
            stringBuilder.append(this.value(i));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String value(int n) {
        String[] arrstring;
        if ((n = n * 2 + 1) >= 0 && n < (arrstring = this.namesAndValues).length) {
            return arrstring[n];
        }
        return null;
    }

    public List<String> values(String list) {
        ArrayList<String> arrayList = null;
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<String> arrayList2 = arrayList;
            if (((String)((Object)list)).equalsIgnoreCase(this.name(i))) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<String>(2);
                }
                arrayList2.add(this.value(i));
            }
            arrayList = arrayList2;
        }
        list = arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
        return list;
    }

    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList<String>(20);

        private void checkNameAndValue(String string, String string2) {
            block7 : {
                block8 : {
                    block9 : {
                        int n;
                        int n2;
                        char c;
                        block11 : {
                            block12 : {
                                block10 : {
                                    if (string == null) break block7;
                                    if (string.isEmpty()) break block8;
                                    n2 = string.length();
                                    for (n = 0; n < n2; ++n) {
                                        c = string.charAt(n);
                                        if (c > '\u001f' && c < '') {
                                            continue;
                                        }
                                        throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", c, n, string));
                                    }
                                    if (string2 == null) break block9;
                                    n = string2.length();
                                    if (n < 2 || string2.charAt(n - 2) != '\r' || string2.charAt(n - 1) != '\n') break block10;
                                    string = string2.substring(0, string2.length() - 2);
                                    break block11;
                                }
                                string = string2;
                                if (n <= 0) break block11;
                                if (string2.charAt(n - 1) == '\n') break block12;
                                string = string2;
                                if (string2.charAt(n - 1) != '\r') break block11;
                            }
                            string = string2.substring(0, n - 1);
                        }
                        n2 = string.length();
                        for (n = 0; n < n2; ++n) {
                            c = string.charAt(n);
                            if ((c > '\u001f' || c == '\t') && c != '') {
                                continue;
                            }
                            throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header value: %s", c, n, string));
                        }
                        return;
                    }
                    throw new IllegalArgumentException("value == null");
                }
                throw new IllegalArgumentException("name is empty");
            }
            throw new IllegalArgumentException("name == null");
        }

        public Builder add(String string) {
            int n = string.indexOf(":");
            if (n != -1) {
                return this.add(string.substring(0, n).trim(), string.substring(n + 1));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected header: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder add(String string, String string2) {
            this.checkNameAndValue(string, string2);
            return this.addLenient(string, string2);
        }

        Builder addLenient(String string) {
            int n = string.indexOf(":", 1);
            if (n != -1) {
                return this.addLenient(string.substring(0, n), string.substring(n + 1));
            }
            if (string.startsWith(":")) {
                return this.addLenient("", string.substring(1));
            }
            return this.addLenient("", string);
        }

        Builder addLenient(String string, String string2) {
            this.namesAndValues.add(string);
            this.namesAndValues.add(string2.trim());
            return this;
        }

        public Headers build() {
            return new Headers(this);
        }

        public String get(String string) {
            for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
                if (!string.equalsIgnoreCase(this.namesAndValues.get(i))) continue;
                return this.namesAndValues.get(i + 1);
            }
            return null;
        }

        public Builder removeAll(String string) {
            int n = 0;
            while (n < this.namesAndValues.size()) {
                int n2 = n;
                if (string.equalsIgnoreCase(this.namesAndValues.get(n))) {
                    this.namesAndValues.remove(n);
                    this.namesAndValues.remove(n);
                    n2 = n - 2;
                }
                n = n2 + 2;
            }
            return this;
        }

        public Builder set(String string, String string2) {
            this.checkNameAndValue(string, string2);
            this.removeAll(string);
            this.addLenient(string, string2);
            return this;
        }
    }

}

