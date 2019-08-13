/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.AccessController;
import java.util.AbstractSequentialList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.misc.MetaIndex;
import sun.security.action.GetPropertyAction;

public class JarIndex {
    public static final String INDEX_NAME = "META-INF/INDEX.LIST";
    private static final boolean metaInfFilenames = "true".equals(AccessController.doPrivileged(new GetPropertyAction("sun.misc.JarIndex.metaInfFilenames")));
    private HashMap<String, LinkedList<String>> indexMap = new HashMap();
    private String[] jarFiles;
    private HashMap<String, LinkedList<String>> jarMap = new HashMap();

    public JarIndex() {
    }

    public JarIndex(InputStream inputStream) throws IOException {
        this();
        this.read(inputStream);
    }

    public JarIndex(String[] arrstring) throws IOException {
        this();
        this.jarFiles = arrstring;
        this.parseJars(arrstring);
    }

    private void addMapping(String string, String string2) {
        this.addToList(string, string2, this.indexMap);
        this.addToList(string2, string, this.jarMap);
    }

    private void addToList(String string, String string2, HashMap<String, LinkedList<String>> hashMap) {
        LinkedList<String> linkedList = hashMap.get(string);
        if (linkedList == null) {
            linkedList = new LinkedList();
            linkedList.add(string2);
            hashMap.put(string, linkedList);
        } else if (!linkedList.contains(string2)) {
            linkedList.add(string2);
        }
    }

    public static JarIndex getJarIndex(JarFile jarFile) throws IOException {
        return JarIndex.getJarIndex(jarFile, null);
    }

    public static JarIndex getJarIndex(JarFile jarFile, MetaIndex object) throws IOException {
        Object var2_2 = null;
        if (object != null && !((MetaIndex)object).mayContain(INDEX_NAME)) {
            return null;
        }
        JarEntry jarEntry = jarFile.getJarEntry(INDEX_NAME);
        object = var2_2;
        if (jarEntry != null) {
            object = new JarIndex(jarFile.getInputStream(jarEntry));
        }
        return object;
    }

    private void parseJars(String[] arrstring) throws IOException {
        if (arrstring == null) {
            return;
        }
        for (int i = 0; i < arrstring.length; ++i) {
            String string = arrstring[i];
            ZipFile zipFile = new ZipFile(string.replace('/', File.separatorChar));
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                String string2 = zipEntry.getName();
                if (string2.equals("META-INF/") || string2.equals(INDEX_NAME) || string2.equals("META-INF/MANIFEST.MF")) continue;
                if (metaInfFilenames && string2.startsWith("META-INF/")) {
                    if (zipEntry.isDirectory()) continue;
                    this.addMapping(string2, string);
                    continue;
                }
                this.add(string2, string);
            }
            zipFile.close();
        }
    }

    public void add(String string, String string2) {
        int n = string.lastIndexOf("/");
        if (n != -1) {
            string = string.substring(0, n);
        }
        this.addMapping(string, string2);
    }

    public LinkedList<String> get(String string) {
        LinkedList<String> linkedList;
        LinkedList<String> linkedList2;
        LinkedList<String> linkedList3 = linkedList2 = (linkedList = this.indexMap.get(string));
        if (linkedList == null) {
            int n = string.lastIndexOf("/");
            linkedList3 = linkedList2;
            if (n != -1) {
                linkedList3 = this.indexMap.get(string.substring(0, n));
            }
        }
        return linkedList3;
    }

    public String[] getJarFiles() {
        return this.jarFiles;
    }

    /*
     * WARNING - void declaration
     */
    public void merge(JarIndex jarIndex, String string) {
        for (Map.Entry<String, LinkedList<String>> entry : this.indexMap.entrySet()) {
            String string2 = entry.getKey();
            Iterator iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                void var4_8;
                String string3;
                String string4 = string3 = (String)iterator.next();
                if (string != null) {
                    String string5 = string.concat(string3);
                }
                jarIndex.addMapping(string2, (String)var4_8);
            }
        }
    }

    public void read(InputStream object) throws IOException {
        String string;
        Object object2;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)object, "UTF8"));
        Object object3 = null;
        Vector<Object> vector = new Vector<Object>();
        do {
            String string2;
            string = string2 = bufferedReader.readLine();
            object = string;
            object2 = object3;
            if (string2 == null) break;
            object = string;
            object2 = object3;
        } while (!string.endsWith(".jar"));
        while (object != null) {
            if (((String)object).length() != 0) {
                if (((String)object).endsWith(".jar")) {
                    vector.add(object);
                    object2 = object;
                } else {
                    this.addMapping((String)object, (String)object2);
                }
            }
            object = bufferedReader.readLine();
        }
        this.jarFiles = vector.toArray(new String[vector.size()]);
    }

    public void write(OutputStream closeable) throws IOException {
        closeable = new BufferedWriter(new OutputStreamWriter((OutputStream)closeable, "UTF8"));
        ((Writer)closeable).write("JarIndex-Version: 1.0\n\n");
        if (this.jarFiles != null) {
            Object object;
            for (int i = 0; i < ((String[])(object = this.jarFiles)).length; ++i) {
                CharSequence charSequence = object[i];
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("\n");
                ((Writer)closeable).write(((StringBuilder)object).toString());
                object = this.jarMap.get(charSequence);
                if (object != null) {
                    object = ((AbstractSequentialList)object).iterator();
                    while (object.hasNext()) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)object.next());
                        ((StringBuilder)charSequence).append("\n");
                        ((Writer)closeable).write(((StringBuilder)charSequence).toString());
                    }
                }
                ((Writer)closeable).write("\n");
            }
            ((BufferedWriter)closeable).flush();
        }
    }
}

