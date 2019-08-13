/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.InMemoryCookieStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.util.logging.PlatformLogger;

public class CookieManager
extends CookieHandler {
    private CookieStore cookieJar = null;
    private CookiePolicy policyCallback;

    public CookieManager() {
        this(null, null);
    }

    public CookieManager(CookieStore cookieStore, CookiePolicy cookiePolicy) {
        if (cookiePolicy == null) {
            cookiePolicy = CookiePolicy.ACCEPT_ORIGINAL_SERVER;
        }
        this.policyCallback = cookiePolicy;
        this.cookieJar = cookieStore == null ? new InMemoryCookieStore() : cookieStore;
    }

    private static boolean isInPortList(String string, int n) {
        int n2 = string.indexOf(",");
        while (n2 > 0) {
            try {
                int n3 = Integer.parseInt(string.substring(0, n2));
                if (n3 == n) {
                    return true;
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            string = string.substring(n2 + 1);
            n2 = string.indexOf(",");
        }
        if (!string.isEmpty()) {
            try {
                n2 = Integer.parseInt(string);
                if (n2 == n) {
                    return true;
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return false;
    }

    private static String normalizePath(String charSequence) {
        String string = charSequence;
        if (charSequence == null) {
            string = "";
        }
        charSequence = string;
        if (!string.endsWith("/")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("/");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private static boolean pathMatches(URI uRI, HttpCookie httpCookie) {
        return CookieManager.normalizePath(uRI.getPath()).startsWith(CookieManager.normalizePath(httpCookie.getPath()));
    }

    private boolean shouldAcceptInternal(URI uRI, HttpCookie httpCookie) {
        try {
            boolean bl = this.policyCallback.shouldAccept(uRI, httpCookie);
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private List<String> sortByPath(List<HttpCookie> list) {
        Collections.sort(list, new CookiePathComparator());
        StringBuilder stringBuilder = new StringBuilder();
        int n = 1;
        for (HttpCookie httpCookie : list) {
            int n2 = n;
            if (httpCookie.getVersion() < n) {
                n2 = httpCookie.getVersion();
            }
            n = n2;
        }
        if (n == 1) {
            stringBuilder.append("$Version=\"1\"; ");
        }
        for (n = 0; n < list.size(); ++n) {
            if (n != 0) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(list.get(n).toString());
        }
        list = new ArrayList<HttpCookie>();
        list.add((HttpCookie)((Object)stringBuilder.toString()));
        return list;
    }

    @Override
    public Map<String, List<String>> get(URI uRI, Map<String, List<String>> map) throws IOException {
        if (uRI != null && map != null) {
            map = new HashMap<String, List<String>>();
            if (this.cookieJar == null) {
                return Collections.unmodifiableMap(map);
            }
            boolean bl = "https".equalsIgnoreCase(uRI.getScheme());
            ArrayList<HttpCookie> arrayList = new ArrayList<HttpCookie>();
            for (HttpCookie httpCookie : this.cookieJar.get(uRI)) {
                if (!CookieManager.pathMatches(uRI, httpCookie) || !bl && httpCookie.getSecure()) continue;
                String string = httpCookie.getPortlist();
                if (string != null && !string.isEmpty()) {
                    int n;
                    int n2 = n = uRI.getPort();
                    if (n == -1) {
                        n2 = "https".equals(uRI.getScheme()) ? 443 : 80;
                    }
                    if (!CookieManager.isInPortList(string, n2)) continue;
                    arrayList.add(httpCookie);
                    continue;
                }
                arrayList.add(httpCookie);
            }
            if (arrayList.isEmpty()) {
                return Collections.emptyMap();
            }
            map.put("Cookie", this.sortByPath(arrayList));
            return Collections.unmodifiableMap(map);
        }
        throw new IllegalArgumentException("Argument is null");
    }

    public CookieStore getCookieStore() {
        return this.cookieJar;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void put(URI var1_1, Map<String, List<String>> var2_2) throws IOException {
        if (var1_1 == null) throw new IllegalArgumentException("Argument is null");
        if (var2_2 == null) throw new IllegalArgumentException("Argument is null");
        if (this.cookieJar == null) {
            return;
        }
        var3_3 = PlatformLogger.getLogger("java.net.CookieManager");
        var4_4 = var2_2.keySet().iterator();
        block4 : do lbl-1000: // 3 sources:
        {
            if (var4_4.hasNext() == false) return;
            var5_6 = var4_4.next();
            if (var5_6 == null || !var5_6.equalsIgnoreCase("Set-Cookie2") && !var5_6.equalsIgnoreCase("Set-Cookie")) ** GOTO lbl-1000
            var6_28 = var2_2.get(var5_6).iterator();
            block5 : do {
                if (!var6_28.hasNext()) continue block4;
                var7_29 = var6_28.next();
                try {
                    var5_7 = HttpCookie.parse(var7_29);
                    ** GOTO lbl32
                }
                catch (IllegalArgumentException var5_8) {
                    try {
                        var5_9 = Collections.emptyList();
                        if (var3_3.isLoggable(PlatformLogger.Level.SEVERE)) {
                            var8_30 = new StringBuilder();
                            var8_30.append("Invalid cookie for ");
                            var8_30.append(var1_1);
                            var8_30.append(": ");
                            var8_30.append(var7_29);
                            var3_3.severe(var8_30.toString());
                        }
lbl32: // 4 sources:
                        var9_31 = var5_10.iterator();
                        do {
                            if (!var9_31.hasNext()) continue block5;
                            var8_30 = (HttpCookie)var9_31.next();
                            if (var8_30.getPath() == null) {
                                var5_12 = var7_29 = var1_1.getPath();
                                if (!var7_29.endsWith("/")) {
                                    var10_32 = var7_29.lastIndexOf("/");
                                    if (var10_32 > 0) {
                                        var5_13 = var7_29.substring(0, var10_32 + 1);
                                    } else {
                                        var5_14 = "/";
                                    }
                                }
                                var8_30.setPath((String)var5_15);
                            } else if (!CookieManager.pathMatches(var1_1, (HttpCookie)var8_30)) continue;
                            if (var8_30.getDomain() == null) {
                                var5_17 = var7_29 = var1_1.getHost();
                                if (var7_29 != null) {
                                    var5_18 = var7_29;
                                    if (!var7_29.contains(".")) {
                                        var5_19 = new StringBuilder();
                                        var5_19.append(var7_29);
                                        var5_19.append(".local");
                                        var5_20 = var5_19.toString();
                                    }
                                }
                                var8_30.setDomain((String)var5_21);
                            }
                            if ((var5_23 = var8_30.getPortlist()) != null) {
                                var10_32 = var11_33 = var1_1.getPort();
                                if (var11_33 == -1) {
                                    var10_32 = "https".equals(var1_1.getScheme()) != false ? 443 : 80;
                                }
                                if (var5_23.isEmpty()) {
                                    var5_24 = new StringBuilder();
                                    var5_24.append("");
                                    var5_24.append(var10_32);
                                    var8_30.setPortlist(var5_24.toString());
                                    if (!this.shouldAcceptInternal(var1_1, (HttpCookie)var8_30)) continue;
                                    this.cookieJar.add(var1_1, (HttpCookie)var8_30);
                                    continue;
                                }
                                if (!CookieManager.isInPortList(var5_23, var10_32) || !this.shouldAcceptInternal(var1_1, (HttpCookie)var8_30)) continue;
                                this.cookieJar.add(var1_1, (HttpCookie)var8_30);
                                continue;
                            }
                            if (!this.shouldAcceptInternal(var1_1, (HttpCookie)var8_30)) continue;
                            this.cookieJar.add(var1_1, (HttpCookie)var8_30);
                        } while (true);
                    }
                    catch (IllegalArgumentException var5_26) {
                        continue;
                    }
                }
                break;
            } while (true);
            break;
        } while (true);
    }

    public void setCookiePolicy(CookiePolicy cookiePolicy) {
        if (cookiePolicy != null) {
            this.policyCallback = cookiePolicy;
        }
    }

    static class CookiePathComparator
    implements Comparator<HttpCookie> {
        CookiePathComparator() {
        }

        @Override
        public int compare(HttpCookie object, HttpCookie object2) {
            if (object == object2) {
                return 0;
            }
            if (object == null) {
                return -1;
            }
            if (object2 == null) {
                return 1;
            }
            if (!((HttpCookie)object).getName().equals(((HttpCookie)object2).getName())) {
                return 0;
            }
            if (((String)(object = CookieManager.normalizePath(((HttpCookie)object).getPath()))).startsWith((String)(object2 = CookieManager.normalizePath(((HttpCookie)object2).getPath())))) {
                return -1;
            }
            return ((String)object2).startsWith((String)object);
        }
    }

}

