/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public interface CookieStore {
    public void add(URI var1, HttpCookie var2);

    public List<HttpCookie> get(URI var1);

    public List<HttpCookie> getCookies();

    public List<URI> getURIs();

    public boolean remove(URI var1, HttpCookie var2);

    public boolean removeAll();
}

