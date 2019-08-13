/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import sun.util.locale.LocaleEquivalentMaps;

public final class LocaleMatcher {
    private LocaleMatcher() {
    }

    public static List<Locale> filter(List<Locale.LanguageRange> list, Collection<Locale> object, Locale.FilteringMode filteringMode) {
        if (!list.isEmpty() && !object.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(((Locale)object.next()).toLanguageTag());
            }
            object = LocaleMatcher.filterTags(list, arrayList, filteringMode);
            list = new ArrayList<Locale.LanguageRange>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                list.add((Locale.LanguageRange)((Object)Locale.forLanguageTag((String)object.next())));
            }
            return list;
        }
        return new ArrayList<Locale>();
    }

    private static List<String> filterBasic(List<Locale.LanguageRange> object, Collection<String> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<Locale.LanguageRange> iterator = object.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next().getRange();
            if (string.equals("*")) {
                return new ArrayList<String>(collection);
            }
            Iterator<String> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next().toLowerCase();
                if (!((String)object).startsWith(string)) continue;
                int n = string.length();
                if (((String)object).length() != n && ((String)object).charAt(n) != '-' || arrayList.contains(object)) continue;
                arrayList.add((String)object);
            }
        }
        return arrayList;
    }

    private static List<String> filterExtended(List<Locale.LanguageRange> object, Collection<String> collection) {
        ArrayList<String> arrayList = new ArrayList<String>();
        object = object.iterator();
        while (object.hasNext()) {
            String[] arrstring = ((Locale.LanguageRange)object.next()).getRange();
            if (arrstring.equals("*")) {
                return new ArrayList<String>(collection);
            }
            arrstring = arrstring.split("-");
            Iterator<String> iterator = collection.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next().toLowerCase();
                String[] arrstring2 = string.split("-");
                if (!arrstring[0].equals(arrstring2[0]) && !arrstring[0].equals("*")) continue;
                int n = 1;
                int n2 = 1;
                while (n < arrstring.length && n2 < arrstring2.length) {
                    if (arrstring[n].equals("*")) {
                        ++n;
                        continue;
                    }
                    if (arrstring[n].equals(arrstring2[n2])) {
                        ++n;
                        ++n2;
                        continue;
                    }
                    if (arrstring2[n2].length() == 1 && !arrstring2[n2].equals("*")) break;
                    ++n2;
                }
                if (arrstring.length != n || arrayList.contains(string)) continue;
                arrayList.add(string);
            }
        }
        return arrayList;
    }

    public static List<String> filterTags(List<Locale.LanguageRange> object, Collection<String> collection, Locale.FilteringMode filteringMode) {
        if (!object.isEmpty() && !collection.isEmpty()) {
            if (filteringMode == Locale.FilteringMode.EXTENDED_FILTERING) {
                return LocaleMatcher.filterExtended(object, collection);
            }
            ArrayList<Locale.LanguageRange> arrayList = new ArrayList<Locale.LanguageRange>();
            Iterator<Locale.LanguageRange> iterator = object.iterator();
            while (iterator.hasNext()) {
                Locale.LanguageRange languageRange = iterator.next();
                String string = languageRange.getRange();
                if (!string.startsWith("*-") && string.indexOf("-*") == -1) {
                    arrayList.add(languageRange);
                    continue;
                }
                if (filteringMode == Locale.FilteringMode.AUTOSELECT_FILTERING) {
                    return LocaleMatcher.filterExtended(object, collection);
                }
                if (filteringMode == Locale.FilteringMode.MAP_EXTENDED_RANGES) {
                    string = string.charAt(0) == '*' ? "*" : string.replaceAll("-[*]", "");
                    arrayList.add(new Locale.LanguageRange(string, languageRange.getWeight()));
                    continue;
                }
                if (filteringMode != Locale.FilteringMode.REJECT_EXTENDED_RANGES) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("An extended range \"");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("\" found in REJECT_EXTENDED_RANGES mode.");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return LocaleMatcher.filterBasic(arrayList, collection);
        }
        return new ArrayList<String>();
    }

    private static String getEquivalentForRegionAndVariant(String string) {
        int n = LocaleMatcher.getExtentionKeyIndex(string);
        for (String string2 : LocaleEquivalentMaps.regionVariantEquivMap.keySet()) {
            int n2 = string.indexOf(string2);
            if (n2 == -1 || n != Integer.MIN_VALUE && n2 > n) continue;
            n2 = string2.length() + n2;
            if (string.length() != n2 && string.charAt(n2) != '-') continue;
            return LocaleMatcher.replaceFirstSubStringMatch(string, string2, LocaleEquivalentMaps.regionVariantEquivMap.get(string2));
        }
        return null;
    }

    private static String[] getEquivalentsForLanguage(String string) {
        String string2 = string;
        while (string2.length() > 0) {
            int n;
            if (LocaleEquivalentMaps.singleEquivMap.containsKey(string2)) {
                return new String[]{LocaleMatcher.replaceFirstSubStringMatch(string, string2, LocaleEquivalentMaps.singleEquivMap.get(string2))};
            }
            if (LocaleEquivalentMaps.multiEquivsMap.containsKey(string2)) {
                String[] arrstring = LocaleEquivalentMaps.multiEquivsMap.get(string2);
                String[] arrstring2 = new String[arrstring.length];
                for (n = 0; n < arrstring.length; ++n) {
                    arrstring2[n] = LocaleMatcher.replaceFirstSubStringMatch(string, string2, arrstring[n]);
                }
                return arrstring2;
            }
            n = string2.lastIndexOf(45);
            if (n == -1) break;
            string2 = string2.substring(0, n);
        }
        return null;
    }

    private static int getExtentionKeyIndex(String arrc) {
        arrc = arrc.toCharArray();
        int n = Integer.MIN_VALUE;
        for (int i = 1; i < arrc.length; ++i) {
            int n2 = n;
            if (arrc[i] == '-') {
                if (i - n == 2) {
                    return n;
                }
                n2 = i;
            }
            n = n2;
        }
        return Integer.MIN_VALUE;
    }

    public static Locale lookup(List<Locale.LanguageRange> object, Collection<Locale> object2) {
        if (!object.isEmpty() && !object2.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                arrayList.add(((Locale)object2.next()).toLanguageTag());
            }
            if ((object = LocaleMatcher.lookupTag(object, arrayList)) == null) {
                return null;
            }
            return Locale.forLanguageTag((String)object);
        }
        return null;
    }

    public static String lookupTag(List<Locale.LanguageRange> object, Collection<String> collection) {
        if (!object.isEmpty() && !collection.isEmpty()) {
            Iterator<Locale.LanguageRange> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next().getRange();
                if (((String)object).equals("*")) continue;
                object = ((String)object).replace("*", "\\p{Alnum}*");
                while (((String)object).length() > 0) {
                    String string;
                    Iterator<String> iterator2 = collection.iterator();
                    while (iterator2.hasNext()) {
                        string = iterator2.next().toLowerCase();
                        if (!string.matches((String)object)) continue;
                        return string;
                    }
                    int n = ((String)object).lastIndexOf(45);
                    if (n >= 0) {
                        string = ((String)object).substring(0, n);
                        object = string;
                        if (string.lastIndexOf(45) != string.length() - 2) continue;
                        object = string.substring(0, string.length() - 2);
                        continue;
                    }
                    object = "";
                }
            }
            return null;
        }
        return null;
    }

    public static List<Locale.LanguageRange> mapEquivalents(List<Locale.LanguageRange> object, Map<String, List<String>> map) {
        if (object.isEmpty()) {
            return new ArrayList<Locale.LanguageRange>();
        }
        if (map != null && !map.isEmpty()) {
            Object object2;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            Object object3 = map.keySet().iterator();
            while (object3.hasNext()) {
                object2 = object3.next();
                hashMap.put(((String)object2).toLowerCase(), object2);
            }
            object2 = new ArrayList();
            Iterator<Locale.LanguageRange> iterator = object.iterator();
            while (iterator.hasNext()) {
                int n;
                Locale.LanguageRange languageRange = iterator.next();
                object = object3 = languageRange.getRange();
                int n2 = 0;
                do {
                    n = n2;
                    if (((String)object).length() <= 0) break;
                    if (hashMap.containsKey(object)) {
                        n2 = 1;
                        Object object4 = map.get(hashMap.get(object));
                        n = n2;
                        if (object4 == null) break;
                        n = ((String)object).length();
                        Iterator<String> iterator2 = object4.iterator();
                        while (iterator2.hasNext()) {
                            object = iterator2.next();
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append(((String)object).toLowerCase());
                            ((StringBuilder)object4).append(((String)object3).substring(n));
                            object2.add(new Locale.LanguageRange(((StringBuilder)object4).toString(), languageRange.getWeight()));
                        }
                        n = n2;
                        break;
                    }
                    n = ((String)object).lastIndexOf(45);
                    if (n == -1) {
                        n = n2;
                        break;
                    }
                    object = ((String)object).substring(0, n);
                } while (true);
                if (n != 0) continue;
                object2.add(languageRange);
            }
            return object2;
        }
        return new ArrayList<Locale.LanguageRange>((Collection<Locale.LanguageRange>)object);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static List<Locale.LanguageRange> parse(String object) {
        String string = ((String)object).replace(" ", "").toLowerCase();
        if (string.startsWith("accept-language:")) {
            string = string.substring(16);
        }
        object = string.split(",");
        ArrayList<Locale.LanguageRange> arrayList = new ArrayList<Locale.LanguageRange>(((Object)object).length);
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        int n = ((Object)object).length;
        int n2 = 0;
        int i = 0;
        while (i < n) {
            block15 : {
                int n4;
                double d;
                int n3;
                String[] arrstring2;
                block16 : {
                    String[] arrstring;
                    block14 : {
                        arrstring = object[i];
                        n3 = arrstring.indexOf(";q=");
                        if (n3 == -1) {
                            arrstring2 = arrstring;
                            d = 1.0;
                        } else {
                            arrstring2 = arrstring.substring(0, n3);
                            n3 += 3;
                            d = Double.parseDouble(arrstring.substring(n3));
                            if (d < 0.0 || d > 1.0) break block14;
                        }
                        if (arrayList2.contains(arrstring2)) break block15;
                        break block16;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("weight=");
                    ((StringBuilder)object).append(d);
                    ((StringBuilder)object).append(" for language range \"");
                    ((StringBuilder)object).append((String)arrstring2);
                    ((StringBuilder)object).append("\". It must be between ");
                    ((StringBuilder)object).append(0.0);
                    ((StringBuilder)object).append(" and ");
                    ((StringBuilder)object).append(1.0);
                    ((StringBuilder)object).append(".");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("weight=\"");
                        stringBuilder.append(arrstring.substring(n3));
                        stringBuilder.append("\" for language range \"");
                        stringBuilder.append((String)arrstring2);
                        stringBuilder.append("\"");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                Locale.LanguageRange languageRange = new Locale.LanguageRange((String)arrstring2, d);
                int n5 = n2;
                n3 = 0;
                do {
                    n4 = n5;
                    if (n3 >= n2) break;
                    if (((Locale.LanguageRange)arrayList.get(n3)).getWeight() < d) {
                        n4 = n3;
                        break;
                    }
                    ++n3;
                } while (true);
                arrayList.add(n4, languageRange);
                n3 = n2 + 1;
                arrayList2.add(arrstring2);
                String string2 = LocaleMatcher.getEquivalentForRegionAndVariant((String)arrstring2);
                n2 = n3;
                if (string2 != null) {
                    n2 = n3;
                    if (!arrayList2.contains(string2)) {
                        arrayList.add(n4 + 1, new Locale.LanguageRange(string2, d));
                        n2 = n3 + 1;
                        arrayList2.add(string2);
                    }
                }
                if ((arrstring2 = LocaleMatcher.getEquivalentsForLanguage((String)arrstring2)) != null) {
                    for (String string3 : arrstring2) {
                        if (!arrayList2.contains(string3)) {
                            arrayList.add(n4 + 1, new Locale.LanguageRange(string3, d));
                            n3 = n2 + 1;
                            arrayList2.add(string3);
                        } else {
                            n3 = n2;
                        }
                        String string4 = LocaleMatcher.getEquivalentForRegionAndVariant(string3);
                        n2 = n3;
                        if (string4 == null) continue;
                        n2 = n3;
                        if (arrayList2.contains(string4)) continue;
                        arrayList.add(n4 + 1, new Locale.LanguageRange(string4, d));
                        n2 = n3 + 1;
                        arrayList2.add(string4);
                    }
                }
            }
            ++i;
        }
        return arrayList;
    }

    private static String replaceFirstSubStringMatch(String string, String string2, String string3) {
        int n = string.indexOf(string2);
        if (n == -1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.substring(0, n));
        stringBuilder.append(string3);
        stringBuilder.append(string.substring(string2.length() + n));
        return stringBuilder.toString();
    }
}

