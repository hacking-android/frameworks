/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.HttpCookie;
import java.net.URI;

public interface CookiePolicy {
    public static final CookiePolicy ACCEPT_ALL = new CookiePolicy(){

        @Override
        public boolean shouldAccept(URI uRI, HttpCookie httpCookie) {
            return true;
        }
    };
    public static final CookiePolicy ACCEPT_NONE = new CookiePolicy(){

        @Override
        public boolean shouldAccept(URI uRI, HttpCookie httpCookie) {
            return false;
        }
    };
    public static final CookiePolicy ACCEPT_ORIGINAL_SERVER = new CookiePolicy(){

        @Override
        public boolean shouldAccept(URI uRI, HttpCookie httpCookie) {
            if (uRI != null && httpCookie != null) {
                return HttpCookie.domainMatches(httpCookie.getDomain(), uRI.getHost());
            }
            return false;
        }
    };

    public boolean shouldAccept(URI var1, HttpCookie var2);

}

