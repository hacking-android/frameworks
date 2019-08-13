/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Authenticator;
import com.android.okhttp.Challenge;
import com.android.okhttp.Headers;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.Platform;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.HeaderParser;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class OkHeaders {
    private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>(){

        @Override
        public int compare(String string, String string2) {
            if (string == string2) {
                return 0;
            }
            if (string == null) {
                return -1;
            }
            if (string2 == null) {
                return 1;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(string, string2);
        }
    };
    static final String PREFIX = Platform.get().getPrefix();
    public static final String RECEIVED_MILLIS;
    public static final String RESPONSE_SOURCE;
    public static final String SELECTED_PROTOCOL;
    public static final String SENT_MILLIS;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Sent-Millis");
        SENT_MILLIS = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Received-Millis");
        RECEIVED_MILLIS = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Selected-Protocol");
        SELECTED_PROTOCOL = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("-Response-Source");
        RESPONSE_SOURCE = stringBuilder.toString();
    }

    private OkHeaders() {
    }

    public static void addCookies(Request.Builder builder, Map<String, List<String>> entry2) {
        for (Map.Entry<String, List<String>> entry2 : entry2.entrySet()) {
            String string = (String)entry2.getKey();
            if (!"Cookie".equalsIgnoreCase(string) && !"Cookie2".equalsIgnoreCase(string) || ((List)entry2.getValue()).isEmpty()) continue;
            builder.addHeader(string, OkHeaders.buildCookieHeader((List)entry2.getValue()));
        }
    }

    private static String buildCookieHeader(List<String> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(list.get(i));
        }
        return stringBuilder.toString();
    }

    public static long contentLength(Headers headers) {
        return OkHeaders.stringToLong(headers.get("Content-Length"));
    }

    public static long contentLength(Request request) {
        return OkHeaders.contentLength(request.headers());
    }

    public static long contentLength(Response response) {
        return OkHeaders.contentLength(response.headers());
    }

    public static boolean hasVaryAll(Headers headers) {
        return OkHeaders.varyFields(headers).contains("*");
    }

    public static boolean hasVaryAll(Response response) {
        return OkHeaders.hasVaryAll(response.headers());
    }

    static boolean isEndToEnd(String string) {
        boolean bl = !"Connection".equalsIgnoreCase(string) && !"Keep-Alive".equalsIgnoreCase(string) && !"Proxy-Authenticate".equalsIgnoreCase(string) && !"Proxy-Authorization".equalsIgnoreCase(string) && !"TE".equalsIgnoreCase(string) && !"Trailers".equalsIgnoreCase(string) && !"Transfer-Encoding".equalsIgnoreCase(string) && !"Upgrade".equalsIgnoreCase(string);
        return bl;
    }

    public static List<Challenge> parseChallenges(Headers headers, String string) {
        ArrayList<Challenge> arrayList = new ArrayList<Challenge>();
        int n = headers.size();
        block0 : for (int i = 0; i < n; ++i) {
            if (!string.equalsIgnoreCase(headers.name(i))) continue;
            String string2 = headers.value(i);
            int n2 = 0;
            while (n2 < string2.length()) {
                int n3 = HeaderParser.skipUntil(string2, n2, " ");
                String string3 = string2.substring(n2, n3).trim();
                n2 = HeaderParser.skipWhitespace(string2, n3);
                if (!string2.regionMatches(true, n2, "realm=\"", 0, "realm=\"".length())) continue block0;
                n3 = n2 + "realm=\"".length();
                n2 = HeaderParser.skipUntil(string2, n3, "\"");
                String string4 = string2.substring(n3, n2);
                n2 = HeaderParser.skipWhitespace(string2, HeaderParser.skipUntil(string2, n2 + 1, ",") + 1);
                arrayList.add(new Challenge(string3, string4));
            }
        }
        return arrayList;
    }

    public static Request processAuthHeader(Authenticator object, Response response, Proxy proxy) throws IOException {
        object = response.code() == 407 ? object.authenticateProxy(proxy, response) : object.authenticate(proxy, response);
        return object;
    }

    private static long stringToLong(String string) {
        if (string == null) {
            return -1L;
        }
        try {
            long l = Long.parseLong(string);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return -1L;
        }
    }

    public static Map<String, List<String>> toMultimap(Headers headers, String string) {
        TreeMap treeMap = new TreeMap(FIELD_NAME_COMPARATOR);
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            String string2 = headers.name(i);
            String string3 = headers.value(i);
            ArrayList<String> arrayList = new ArrayList<String>();
            List list = (List)treeMap.get(string2);
            if (list != null) {
                arrayList.addAll(list);
            }
            arrayList.add(string3);
            treeMap.put(string2, Collections.unmodifiableList(arrayList));
        }
        if (string != null) {
            treeMap.put(null, Collections.unmodifiableList(Collections.singletonList(string)));
        }
        return Collections.unmodifiableMap(treeMap);
    }

    public static Set<String> varyFields(Headers headers) {
        Set<String> set = Collections.emptySet();
        int n = headers.size();
        block0 : for (int i = 0; i < n; ++i) {
            if (!"Vary".equalsIgnoreCase(headers.name(i))) continue;
            String[] arrstring = headers.value(i);
            Set<String> set2 = set;
            if (set.isEmpty()) {
                set2 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            }
            arrstring = arrstring.split(",");
            int n2 = arrstring.length;
            int n3 = 0;
            do {
                set = set2;
                if (n3 >= n2) continue block0;
                set2.add(arrstring[n3].trim());
                ++n3;
            } while (true);
        }
        return set;
    }

    private static Set<String> varyFields(Response response) {
        return OkHeaders.varyFields(response.headers());
    }

    public static Headers varyHeaders(Headers headers, Headers object) {
        Set<String> set = OkHeaders.varyFields((Headers)object);
        if (set.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers.Builder builder = new Headers.Builder();
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            object = headers.name(i);
            if (!set.contains(object)) continue;
            builder.add((String)object, headers.value(i));
        }
        return builder.build();
    }

    public static Headers varyHeaders(Response response) {
        return OkHeaders.varyHeaders(response.networkResponse().request().headers(), response.headers());
    }

    /*
     * WARNING - void declaration
     */
    public static boolean varyMatches(Response object2, Headers headers, Request request) {
        for (String string : OkHeaders.varyFields((Response)object2)) {
            void var1_3;
            void var2_4;
            if (Util.equal(var1_3.values(string), var2_4.headers(string))) continue;
            return false;
        }
        return true;
    }

}

