/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.http.HttpDate
 */
package java.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import libcore.net.http.HttpDate;

public final class HttpCookie
implements Cloneable {
    static final TimeZone GMT;
    private static final long MAX_AGE_UNSPECIFIED = -1L;
    private static final Set<String> RESERVED_NAMES;
    private static final String SET_COOKIE = "set-cookie:";
    private static final String SET_COOKIE2 = "set-cookie2:";
    static final Map<String, CookieAttributeAssignor> assignors;
    private static final String tspecials = ",;= \t";
    private String comment;
    private String commentURL;
    private String domain;
    private final String header;
    private boolean httpOnly;
    private long maxAge = -1L;
    private final String name;
    private String path;
    private String portlist;
    private boolean secure;
    private boolean toDiscard;
    private String value;
    private int version = 1;
    private final long whenCreated;

    static {
        RESERVED_NAMES = new HashSet<String>();
        RESERVED_NAMES.add("comment");
        RESERVED_NAMES.add("commenturl");
        RESERVED_NAMES.add("discard");
        RESERVED_NAMES.add("domain");
        RESERVED_NAMES.add("expires");
        RESERVED_NAMES.add("httponly");
        RESERVED_NAMES.add("max-age");
        RESERVED_NAMES.add("path");
        RESERVED_NAMES.add("port");
        RESERVED_NAMES.add("secure");
        RESERVED_NAMES.add("version");
        assignors = new HashMap<String, CookieAttributeAssignor>();
        assignors.put("comment", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                if (httpCookie.getComment() == null) {
                    httpCookie.setComment(string2);
                }
            }
        });
        assignors.put("commenturl", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                if (httpCookie.getCommentURL() == null) {
                    httpCookie.setCommentURL(string2);
                }
            }
        });
        assignors.put("discard", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                httpCookie.setDiscard(true);
            }
        });
        assignors.put("domain", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                if (httpCookie.getDomain() == null) {
                    httpCookie.setDomain(string2);
                }
            }
        });
        assignors.put("max-age", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                try {
                    long l = Long.parseLong(string2);
                    if (httpCookie.getMaxAge() == -1L) {
                        httpCookie.setMaxAge(l);
                    }
                    return;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new IllegalArgumentException("Illegal cookie max-age attribute");
                }
            }
        });
        assignors.put("path", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                if (httpCookie.getPath() == null) {
                    httpCookie.setPath(string2);
                }
            }
        });
        assignors.put("port", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                if (httpCookie.getPortlist() == null) {
                    if (string2 == null) {
                        string2 = "";
                    }
                    httpCookie.setPortlist(string2);
                }
            }
        });
        assignors.put("secure", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                httpCookie.setSecure(true);
            }
        });
        assignors.put("httponly", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                httpCookie.setHttpOnly(true);
            }
        });
        assignors.put("version", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String string, String string2) {
                try {
                    httpCookie.setVersion(Integer.parseInt(string2));
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        });
        assignors.put("expires", new CookieAttributeAssignor(){

            @Override
            public void assign(HttpCookie httpCookie, String object, String string) {
                if (httpCookie.getMaxAge() == -1L) {
                    object = HttpDate.parse((String)string);
                    long l = 0L;
                    if (object != null) {
                        long l2;
                        l = l2 = (((Date)object).getTime() - httpCookie.whenCreated) / 1000L;
                        if (l2 == -1L) {
                            l = 0L;
                        }
                    }
                    httpCookie.setMaxAge(l);
                }
            }
        });
        GMT = TimeZone.getTimeZone("GMT");
    }

    public HttpCookie(String string, String string2) {
        this(string, string2, null);
    }

    private HttpCookie(String string, String string2, String string3) {
        string = string.trim();
        if (string.length() != 0 && HttpCookie.isToken(string) && string.charAt(0) != '$') {
            this.name = string;
            this.value = string2;
            this.toDiscard = false;
            this.secure = false;
            this.whenCreated = System.currentTimeMillis();
            this.portlist = null;
            this.header = string3;
            return;
        }
        throw new IllegalArgumentException("Illegal cookie name");
    }

    private static void assignAttribute(HttpCookie httpCookie, String string, String string2) {
        string2 = HttpCookie.stripOffSurroundingQuote(string2);
        CookieAttributeAssignor cookieAttributeAssignor = assignors.get(string.toLowerCase());
        if (cookieAttributeAssignor != null) {
            cookieAttributeAssignor.assign(httpCookie, string, string2);
        }
    }

    public static boolean domainMatches(String string, String string2) {
        block11 : {
            int n;
            boolean bl;
            block14 : {
                block15 : {
                    block16 : {
                        boolean bl2;
                        boolean bl3;
                        block12 : {
                            block13 : {
                                int n2;
                                bl = false;
                                bl2 = false;
                                if (string == null || string2 == null) break block11;
                                bl3 = ".local".equalsIgnoreCase(string);
                                n = n2 = string.indexOf(46);
                                if (n2 == 0) {
                                    n = string.indexOf(46, 1);
                                }
                                if (!(bl3 || n != -1 && n != string.length() - 1)) {
                                    return false;
                                }
                                if (string2.indexOf(46) != -1) break block12;
                                if (bl3) break block13;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(string2);
                                stringBuilder.append(".local");
                                if (!string.equalsIgnoreCase(stringBuilder.toString())) break block12;
                            }
                            return true;
                        }
                        n = string.length();
                        n = string2.length() - n;
                        if (n == 0) {
                            return string2.equalsIgnoreCase(string);
                        }
                        if (n <= 0) break block14;
                        string2.substring(0, n);
                        bl = bl2;
                        if (!string2.substring(n).equalsIgnoreCase(string)) break block15;
                        if (string.startsWith(".") && HttpCookie.isFullyQualifiedDomainName(string, 1)) break block16;
                        bl = bl2;
                        if (!bl3) break block15;
                    }
                    bl = true;
                }
                return bl;
            }
            if (n == -1) {
                if (string.charAt(0) == '.' && string2.equalsIgnoreCase(string.substring(1))) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }
        return false;
    }

    private static boolean equalsIgnoreCase(String string, String string2) {
        if (string == string2) {
            return true;
        }
        if (string != null && string2 != null) {
            return string.equalsIgnoreCase(string2);
        }
        return false;
    }

    private static int guessCookieVersion(String string) {
        int n = 0;
        if ((string = string.toLowerCase()).indexOf("expires=") != -1) {
            n = 0;
        } else if (string.indexOf("version=") != -1) {
            n = 1;
        } else if (string.indexOf("max-age") != -1) {
            n = 1;
        } else if (HttpCookie.startsWithIgnoreCase(string, SET_COOKIE2)) {
            n = 1;
        }
        return n;
    }

    private String header() {
        return this.header;
    }

    private static boolean isFullyQualifiedDomainName(String string, int n) {
        n = string.indexOf(46, n + 1);
        boolean bl = true;
        if (n == -1 || n >= string.length() - 1) {
            bl = false;
        }
        return bl;
    }

    private static boolean isToken(String string) {
        if (RESERVED_NAMES.contains(string.toLowerCase(Locale.US))) {
            return false;
        }
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c >= ' ' && c < '' && tspecials.indexOf(c) == -1) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static List<HttpCookie> parse(String string) {
        return HttpCookie.parse(string, false);
    }

    private static List<HttpCookie> parse(String object, boolean bl) {
        Object object2;
        int n = HttpCookie.guessCookieVersion((String)object);
        if (HttpCookie.startsWithIgnoreCase((String)object, SET_COOKIE2)) {
            object2 = ((String)object).substring(SET_COOKIE2.length());
        } else {
            object2 = object;
            if (HttpCookie.startsWithIgnoreCase((String)object, SET_COOKIE)) {
                object2 = ((String)object).substring(SET_COOKIE.length());
            }
        }
        object = new ArrayList();
        if (n == 0) {
            object2 = HttpCookie.parseInternal((String)object2, bl);
            ((HttpCookie)object2).setVersion(0);
            object.add(object2);
        } else {
            Iterator<String> iterator = HttpCookie.splitMultiCookies((String)object2).iterator();
            while (iterator.hasNext()) {
                object2 = HttpCookie.parseInternal(iterator.next(), bl);
                ((HttpCookie)object2).setVersion(1);
                object.add(object2);
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static HttpCookie parseInternal(String object, boolean bl) {
        StringTokenizer stringTokenizer;
        String string;
        String string2;
        int n;
        block8 : {
            stringTokenizer = new StringTokenizer((String)object, ";");
            try {
                string = stringTokenizer.nextToken();
                n = string.indexOf(61);
                if (n != -1) {
                    string2 = string.substring(0, n).trim();
                    string = string.substring(n + 1).trim();
                    object = bl ? new HttpCookie(string2, HttpCookie.stripOffSurroundingQuote(string), (String)object) : new HttpCookie(string2, HttpCookie.stripOffSurroundingQuote(string));
                    break block8;
                }
                object = new IllegalArgumentException("Invalid cookie name-value pair");
                throw object;
            }
            catch (NoSuchElementException noSuchElementException) {
                throw new IllegalArgumentException("Empty cookie header string");
            }
        }
        while (stringTokenizer.hasMoreTokens()) {
            string = stringTokenizer.nextToken();
            n = string.indexOf(61);
            if (n != -1) {
                string2 = string.substring(0, n).trim();
                string = string.substring(n + 1).trim();
            } else {
                string2 = string.trim();
                string = null;
            }
            HttpCookie.assignAttribute((HttpCookie)object, string2, string);
        }
        return object;
    }

    private static List<String> splitMultiCookies(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            int n3 = n;
            if (c == '\"') {
                n3 = n + 1;
            }
            int n4 = n2;
            if (c == ',') {
                n4 = n2;
                if (n3 % 2 == 0) {
                    arrayList.add(string.substring(n2, i));
                    n4 = i + 1;
                }
            }
            n = n3;
            n2 = n4;
        }
        arrayList.add(string.substring(n2));
        return arrayList;
    }

    private static boolean startsWithIgnoreCase(String string, String string2) {
        if (string != null && string2 != null) {
            return string.length() >= string2.length() && string2.equalsIgnoreCase(string.substring(0, string2.length()));
        }
        return false;
    }

    private static String stripOffSurroundingQuote(String string) {
        if (string != null && string.length() > 2 && string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"') {
            return string.substring(1, string.length() - 1);
        }
        if (string != null && string.length() > 2 && string.charAt(0) == '\'' && string.charAt(string.length() - 1) == '\'') {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    private String toNetscapeHeaderString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append("=");
        stringBuilder.append(this.getValue());
        return stringBuilder.toString();
    }

    private String toRFC2965HeaderString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append("=\"");
        stringBuilder.append(this.getValue());
        stringBuilder.append('\"');
        if (this.getPath() != null) {
            stringBuilder.append(";$Path=\"");
            stringBuilder.append(this.getPath());
            stringBuilder.append('\"');
        }
        if (this.getDomain() != null) {
            stringBuilder.append(";$Domain=\"");
            stringBuilder.append(this.getDomain());
            stringBuilder.append('\"');
        }
        if (this.getPortlist() != null) {
            stringBuilder.append(";$Port=\"");
            stringBuilder.append(this.getPortlist());
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException.getMessage());
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof HttpCookie)) {
            return false;
        }
        object = (HttpCookie)object;
        if (!(HttpCookie.equalsIgnoreCase(this.getName(), ((HttpCookie)object).getName()) && HttpCookie.equalsIgnoreCase(this.getDomain(), ((HttpCookie)object).getDomain()) && Objects.equals(this.getPath(), ((HttpCookie)object).getPath()))) {
            bl = false;
        }
        return bl;
    }

    public String getComment() {
        return this.comment;
    }

    public String getCommentURL() {
        return this.commentURL;
    }

    public boolean getDiscard() {
        return this.toDiscard;
    }

    public String getDomain() {
        return this.domain;
    }

    public long getMaxAge() {
        return this.maxAge;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public String getPortlist() {
        return this.portlist;
    }

    public boolean getSecure() {
        return this.secure;
    }

    public String getValue() {
        return this.value;
    }

    public int getVersion() {
        return this.version;
    }

    public boolean hasExpired() {
        long l = this.maxAge;
        if (l == 0L) {
            return true;
        }
        if (l == -1L) {
            return false;
        }
        return (System.currentTimeMillis() - this.whenCreated) / 1000L > this.maxAge;
    }

    public int hashCode() {
        int n = this.name.toLowerCase().hashCode();
        String string = this.domain;
        int n2 = 0;
        int n3 = string != null ? string.toLowerCase().hashCode() : 0;
        string = this.path;
        if (string != null) {
            n2 = string.hashCode();
        }
        return n + n3 + n2;
    }

    public boolean isHttpOnly() {
        return this.httpOnly;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setCommentURL(String string) {
        this.commentURL = string;
    }

    public void setDiscard(boolean bl) {
        this.toDiscard = bl;
    }

    public void setDomain(String string) {
        this.domain = string != null ? string.toLowerCase() : string;
    }

    public void setHttpOnly(boolean bl) {
        this.httpOnly = bl;
    }

    public void setMaxAge(long l) {
        this.maxAge = l;
    }

    public void setPath(String string) {
        this.path = string;
    }

    public void setPortlist(String string) {
        this.portlist = string;
    }

    public void setSecure(boolean bl) {
        this.secure = bl;
    }

    public void setValue(String string) {
        this.value = string;
    }

    public void setVersion(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("cookie version should be 0 or 1");
        }
        this.version = n;
    }

    public String toString() {
        if (this.getVersion() > 0) {
            return this.toRFC2965HeaderString();
        }
        return this.toNetscapeHeaderString();
    }

    static interface CookieAttributeAssignor {
        public void assign(HttpCookie var1, String var2, String var3);
    }

}

