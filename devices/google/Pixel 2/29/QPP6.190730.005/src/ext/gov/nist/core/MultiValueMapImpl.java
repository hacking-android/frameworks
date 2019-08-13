/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.MultiValueMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiValueMapImpl<V>
implements MultiValueMap<String, V>,
Cloneable {
    private static final long serialVersionUID = 4275505380960964605L;
    private HashMap<String, ArrayList<V>> map = new HashMap();

    @Override
    public void clear() {
        Iterator<Map.Entry<String, ArrayList<V>>> iterator = this.map.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().getValue().clear();
        }
        this.map.clear();
    }

    public Object clone() {
        MultiValueMapImpl<V> multiValueMapImpl = new MultiValueMapImpl<V>();
        multiValueMapImpl.map = (HashMap)this.map.clone();
        return multiValueMapImpl;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        Set<Map.Entry<String, ArrayList<V>>> set = this.map.entrySet();
        if (set == null) {
            return false;
        }
        set = set.iterator();
        while (set.hasNext()) {
            if (!((ArrayList)((Map.Entry)set.next()).getValue()).contains(object)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Set entrySet() {
        return this.map.entrySet();
    }

    @Override
    public List<V> get(Object object) {
        return this.map.get(object);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public List<V> put(String string, V v) {
        ArrayList<V> arrayList;
        ArrayList<Object> arrayList2 = arrayList = this.map.get(string);
        if (arrayList == null) {
            arrayList2 = new ArrayList(10);
            this.map.put(string, arrayList2);
        }
        arrayList2.add(v);
        return arrayList2;
    }

    @Override
    public List<V> put(String string, List<V> list) {
        return this.map.put(string, (ArrayList)list);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<V>> map) {
        for (String string : map.keySet()) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(map.get(string));
            this.map.put(string, arrayList);
        }
    }

    @Override
    public List<V> remove(Object object) {
        return this.map.remove(object);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection values() {
        ArrayList<Object> arrayList = new ArrayList<Object>(this.map.size());
        Iterator<Map.Entry<String, ArrayList<V>>> iterator = this.map.entrySet().iterator();
        while (iterator.hasNext()) {
            Object[] arrobject = iterator.next().getValue().toArray();
            for (int i = 0; i < arrobject.length; ++i) {
                arrayList.add(arrobject[i]);
            }
        }
        return arrayList;
    }
}

