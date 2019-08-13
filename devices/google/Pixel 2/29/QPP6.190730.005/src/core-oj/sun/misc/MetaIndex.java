/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaIndex {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static volatile Map<File, MetaIndex> jarMap;
    private String[] contents;
    private boolean isClassOnlyJar;

    private MetaIndex(List<String> list, boolean bl) throws IllegalArgumentException {
        if (list != null) {
            this.contents = list.toArray(new String[0]);
            this.isClassOnlyJar = bl;
            return;
        }
        throw new IllegalArgumentException();
    }

    public static MetaIndex forJar(File file) {
        return MetaIndex.getJarMap().get(file);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Map<File, MetaIndex> getJarMap() {
        if (jarMap != null) return jarMap;
        synchronized (MetaIndex.class) {
            if (jarMap != null) return jarMap;
            HashMap<File, MetaIndex> hashMap = new HashMap<File, MetaIndex>();
            jarMap = hashMap;
            return jarMap;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void registerDirectory(File object) {
        synchronized (MetaIndex.class) {
            block13 : {
                Object object2 = new File((File)object, "meta-index");
                boolean bl = ((File)object2).exists();
                if (bl) {
                    BufferedReader bufferedReader;
                    Object object3;
                    File file;
                    ArrayList<String> arrayList;
                    String string;
                    block12 : {
                        try {
                            object3 = new FileReader((File)object2);
                            bufferedReader = new BufferedReader((Reader)object3);
                            object3 = null;
                            bl = false;
                            arrayList = new ArrayList<String>();
                            object2 = MetaIndex.getJarMap();
                            file = ((File)object).getCanonicalFile();
                            string = bufferedReader.readLine();
                            if (string != null) {
                                object = object3;
                                if (string.equals("% VERSION 2")) break block12;
                            }
                            bufferedReader.close();
                        }
                        catch (IOException iOException) {
                            // empty catch block
                            break block13;
                        }
                        return;
                    }
                    while ((string = bufferedReader.readLine()) != null) {
                        char c = string.charAt(0);
                        if (c != '!' && c != '#') {
                            if (c == '%') continue;
                            if (c != '@') {
                                arrayList.add(string);
                                continue;
                            }
                        }
                        if (object != null && arrayList.size() > 0) {
                            object3 = new File(file, (String)object);
                            object = new MetaIndex(arrayList, bl);
                            object2.put(object3, object);
                            arrayList.clear();
                        }
                        object3 = string.substring(2);
                        if (string.charAt(0) == '!') {
                            bl = true;
                            object = object3;
                            continue;
                        }
                        object = object3;
                        if (!bl) continue;
                        bl = false;
                        object = object3;
                    }
                    if (object != null && arrayList.size() > 0) {
                        object3 = new File(file, (String)object);
                        object = new MetaIndex(arrayList, bl);
                        object2.put(object3, object);
                    }
                    bufferedReader.close();
                }
            }
            return;
        }
    }

    public boolean mayContain(String string) {
        if (this.isClassOnlyJar && !string.endsWith(".class")) {
            return false;
        }
        String[] arrstring = this.contents;
        for (int i = 0; i < arrstring.length; ++i) {
            if (!string.startsWith(arrstring[i])) continue;
            return true;
        }
        return false;
    }
}

