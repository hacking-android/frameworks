/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package java.net;

import dalvik.system.VMRuntime;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryCookieStore
implements CookieStore {
    private final boolean applyMCompatibility;
    private ReentrantLock lock = null;
    private Map<URI, List<HttpCookie>> uriIndex = new HashMap<URI, List<HttpCookie>>();

    public InMemoryCookieStore() {
        this(VMRuntime.getRuntime().getTargetSdkVersion());
    }

    public InMemoryCookieStore(int n) {
        boolean bl = false;
        this.lock = new ReentrantLock(false);
        if (n <= 23) {
            bl = true;
        }
        this.applyMCompatibility = bl;
    }

    private <T> void addIndex(Map<T, List<HttpCookie>> map, T t, HttpCookie httpCookie) {
        List<HttpCookie> list = map.get(t);
        if (list != null) {
            list.remove(httpCookie);
            list.add(httpCookie);
        } else {
            list = new ArrayList<HttpCookie>();
            list.add(httpCookie);
            map.put(t, list);
        }
    }

    private URI getEffectiveURI(URI uRI) {
        if (uRI == null) {
            return null;
        }
        try {
            URI uRI2;
            uRI = uRI2 = new URI("http", uRI.getHost(), null, null, null);
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return uRI;
    }

    private void getInternal1(List<HttpCookie> list, Map<URI, List<HttpCookie>> object, String string) {
        ArrayList<HttpCookie> arrayList = new ArrayList<HttpCookie>();
        Iterator<Map.Entry<URI, List<HttpCookie>>> iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            object = iterator.next().getValue();
            Iterator iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                HttpCookie httpCookie = (HttpCookie)iterator2.next();
                String string2 = httpCookie.getDomain();
                if ((httpCookie.getVersion() != 0 || !this.netscapeDomainMatches(string2, string)) && (httpCookie.getVersion() != 1 || !HttpCookie.domainMatches(string2, string))) continue;
                if (!httpCookie.hasExpired()) {
                    if (list.contains(httpCookie)) continue;
                    list.add(httpCookie);
                    continue;
                }
                arrayList.add(httpCookie);
            }
            iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                object.remove((HttpCookie)iterator2.next());
            }
            arrayList.clear();
        }
    }

    private <T extends Comparable<T>> void getInternal2(List<HttpCookie> list, Map<T, List<HttpCookie>> map, T comparable) {
        for (Object object : map.keySet()) {
            if (object != comparable && (object == null || comparable.compareTo((Comparable)object) != 0) || (object = map.get(object)) == null) continue;
            object = object.iterator();
            while (object.hasNext()) {
                HttpCookie httpCookie = (HttpCookie)object.next();
                if (!httpCookie.hasExpired()) {
                    if (list.contains(httpCookie)) continue;
                    list.add(httpCookie);
                    continue;
                }
                object.remove();
            }
        }
    }

    private boolean netscapeDomainMatches(String string, String string2) {
        boolean bl = false;
        if (string != null && string2 != null) {
            int n;
            boolean bl2 = ".local".equalsIgnoreCase(string);
            int n2 = n = string.indexOf(46);
            if (n == 0) {
                n2 = string.indexOf(46, 1);
            }
            if (!(bl2 || n2 != -1 && n2 != string.length() - 1)) {
                return false;
            }
            if (string2.indexOf(46) == -1 && bl2) {
                return true;
            }
            n2 = string.length();
            n2 = string2.length() - n2;
            if (n2 == 0) {
                return string2.equalsIgnoreCase(string);
            }
            if (n2 > 0) {
                string2 = string2.substring(n2);
                if (this.applyMCompatibility && !string.startsWith(".")) {
                    return false;
                }
                return string2.equalsIgnoreCase(string);
            }
            if (n2 == -1) {
                if (string.charAt(0) == '.' && string2.equalsIgnoreCase(string.substring(1))) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }
        return false;
    }

    @Override
    public void add(URI uRI, HttpCookie httpCookie) {
        if (httpCookie != null) {
            this.lock.lock();
            try {
                this.addIndex(this.uriIndex, this.getEffectiveURI(uRI), httpCookie);
                return;
            }
            finally {
                this.lock.unlock();
            }
        }
        throw new NullPointerException("cookie is null");
    }

    @Override
    public List<HttpCookie> get(URI uRI) {
        if (uRI != null) {
            ArrayList<HttpCookie> arrayList = new ArrayList<HttpCookie>();
            this.lock.lock();
            try {
                this.getInternal1(arrayList, this.uriIndex, uRI.getHost());
                this.getInternal2(arrayList, this.uriIndex, this.getEffectiveURI(uRI));
                return arrayList;
            }
            finally {
                this.lock.unlock();
            }
        }
        throw new NullPointerException("uri is null");
    }

    @Override
    public List<HttpCookie> getCookies() {
        ArrayList<HttpCookie> arrayList = new ArrayList();
        this.lock.lock();
        try {
            Iterator<List<HttpCookie>> iterator = this.uriIndex.values().iterator();
            while (iterator.hasNext()) {
                Iterator<HttpCookie> iterator2 = iterator.next().iterator();
                while (iterator2.hasNext()) {
                    HttpCookie httpCookie = iterator2.next();
                    if (httpCookie.hasExpired()) {
                        iterator2.remove();
                        continue;
                    }
                    if (arrayList.contains(httpCookie)) continue;
                    arrayList.add(httpCookie);
                }
            }
        }
        catch (Throwable throwable) {
            Collections.unmodifiableList(arrayList);
            this.lock.unlock();
            throw throwable;
        }
        arrayList = Collections.unmodifiableList(arrayList);
        this.lock.unlock();
        return arrayList;
    }

    @Override
    public List<URI> getURIs() {
        this.lock.lock();
        try {
            List<URI> list = new List<URI>(this.uriIndex.keySet());
            list.remove(null);
            list = Collections.unmodifiableList(list);
            return list;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean remove(URI object, HttpCookie httpCookie) {
        if (httpCookie != null) {
            block7 : {
                block6 : {
                    this.lock.lock();
                    object = this.getEffectiveURI((URI)object);
                    List<HttpCookie> list = this.uriIndex.get(object);
                    if (list != null) break block6;
                    this.lock.unlock();
                    return false;
                }
                object = this.uriIndex.get(object);
                if (object == null) break block7;
                boolean bl = object.remove(httpCookie);
                return bl;
            }
            this.lock.unlock();
            return false;
            finally {
                this.lock.unlock();
            }
        }
        throw new NullPointerException("cookie is null");
    }

    @Override
    public boolean removeAll() {
        this.lock.lock();
        boolean bl = !this.uriIndex.isEmpty();
        try {
            this.uriIndex.clear();
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }
}

