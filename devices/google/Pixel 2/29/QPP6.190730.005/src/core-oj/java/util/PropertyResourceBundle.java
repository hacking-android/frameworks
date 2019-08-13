/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import sun.util.ResourceBundleEnumeration;

public class PropertyResourceBundle
extends ResourceBundle {
    private final Map<String, Object> lookup;

    public PropertyResourceBundle(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        this.lookup = new HashMap<Object, Object>(properties);
    }

    public PropertyResourceBundle(Reader reader) throws IOException {
        Properties properties = new Properties();
        properties.load(reader);
        this.lookup = new HashMap<Object, Object>(properties);
    }

    @Override
    public Enumeration<String> getKeys() {
        Enumeration<String> enumeration = this.parent;
        Set<String> set = this.lookup.keySet();
        enumeration = enumeration != null ? ((ResourceBundle)((Object)enumeration)).getKeys() : null;
        return new ResourceBundleEnumeration(set, enumeration);
    }

    @Override
    public Object handleGetObject(String string) {
        if (string != null) {
            return this.lookup.get(string);
        }
        throw new NullPointerException();
    }

    @Override
    protected Set<String> handleKeySet() {
        return this.lookup.keySet();
    }
}

