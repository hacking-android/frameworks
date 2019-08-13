/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.io.IOException;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;

final class URI {
    private static boolean DEBUG = false;
    private static final String MARK_CHARACTERS = "-_.!~*'() ";
    private static final String RESERVED_CHARACTERS = ";/?:@&=+$,";
    private static final String SCHEME_CHARACTERS = "+-.";
    private static final String USERINFO_CHARACTERS = ";:&=+$,";
    private String m_fragment = null;
    private String m_host = null;
    private String m_path = null;
    private int m_port = -1;
    private String m_queryString = null;
    private String m_scheme = null;
    private String m_userinfo = null;

    static {
        DEBUG = false;
    }

    public URI() {
    }

    public URI(String string) throws MalformedURIException {
        this((URI)null, string);
    }

    public URI(String string, String string2) throws MalformedURIException {
        if (string != null && string.trim().length() != 0) {
            if (string2 != null && string2.trim().length() != 0) {
                this.setScheme(string);
                this.setPath(string2);
                return;
            }
            throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
        }
        throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
    }

    public URI(String string, String string2, String string3, int n, String string4, String string5, String string6) throws MalformedURIException {
        if (string != null && string.trim().length() != 0) {
            if (string3 == null) {
                if (string2 == null) {
                    if (n != -1) {
                        throw new MalformedURIException(Utils.messages.createMessage("ER_NO_PORT_IF_NO_HOST", null));
                    }
                } else {
                    throw new MalformedURIException(Utils.messages.createMessage("ER_NO_USERINFO_IF_NO_HOST", null));
                }
            }
            if (string4 != null) {
                if (string4.indexOf(63) != -1 && string5 != null) {
                    throw new MalformedURIException(Utils.messages.createMessage("ER_NO_QUERY_STRING_IN_PATH", null));
                }
                if (string4.indexOf(35) != -1 && string6 != null) {
                    throw new MalformedURIException(Utils.messages.createMessage("ER_NO_FRAGMENT_STRING_IN_PATH", null));
                }
            }
            this.setScheme(string);
            this.setHost(string3);
            this.setPort(n);
            this.setUserinfo(string2);
            this.setPath(string4);
            this.setQueryString(string5);
            this.setFragment(string6);
            return;
        }
        throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_REQUIRED", null));
    }

    public URI(String string, String string2, String string3, String string4, String string5) throws MalformedURIException {
        this(string, null, string2, -1, string3, string4, string5);
    }

    public URI(URI uRI) {
        this.initialize(uRI);
    }

    public URI(URI uRI, String string) throws MalformedURIException {
        this.initialize(uRI, string);
    }

    private void initialize(URI uRI) {
        this.m_scheme = uRI.getScheme();
        this.m_userinfo = uRI.getUserinfo();
        this.m_host = uRI.getHost();
        this.m_port = uRI.getPort();
        this.m_path = uRI.getPath();
        this.m_queryString = uRI.getQueryString();
        this.m_fragment = uRI.getFragment();
    }

    private void initialize(URI object, String object2) throws MalformedURIException {
        if (object == null && (object2 == null || ((String)object2).trim().length() == 0)) {
            throw new MalformedURIException(Utils.messages.createMessage("ER_CANNOT_INIT_URI_EMPTY_PARMS", null));
        }
        if (object2 != null && ((String)object2).trim().length() != 0) {
            object2 = ((String)object2).trim();
            int n = ((String)object2).length();
            int n2 = 0;
            int n3 = ((String)object2).indexOf(58);
            if (n3 < 0) {
                if (object == null) {
                    throw new MalformedURIException(Utils.messages.createMessage("ER_NO_SCHEME_IN_URI", new Object[]{object2}));
                }
            } else {
                this.initializeScheme((String)object2);
                object2 = ((String)object2).substring(n3 + 1);
                n = ((String)object2).length();
            }
            if (((String)object2).startsWith("//")) {
                char c;
                for (n2 = n3 = 0 + 2; n2 < n && (c = ((String)object2).charAt(n2)) != '/' && c != '?' && c != '#'; ++n2) {
                }
                if (n2 > n3) {
                    this.initializeAuthority(((String)object2).substring(n3, n2));
                } else {
                    this.m_host = "";
                }
            }
            this.initializePath(((String)object2).substring(n2));
            if (object != null) {
                if (this.m_path.length() == 0 && this.m_scheme == null && this.m_host == null) {
                    this.m_scheme = ((URI)object).getScheme();
                    this.m_userinfo = ((URI)object).getUserinfo();
                    this.m_host = ((URI)object).getHost();
                    this.m_port = ((URI)object).getPort();
                    this.m_path = ((URI)object).getPath();
                    if (this.m_queryString == null) {
                        this.m_queryString = ((URI)object).getQueryString();
                    }
                    return;
                }
                if (this.m_scheme == null) {
                    this.m_scheme = ((URI)object).getScheme();
                }
                if (this.m_host == null) {
                    this.m_userinfo = ((URI)object).getUserinfo();
                    this.m_host = ((URI)object).getHost();
                    this.m_port = ((URI)object).getPort();
                    if (this.m_path.length() > 0 && this.m_path.startsWith("/")) {
                        return;
                    }
                    object2 = new String();
                    String string = ((URI)object).getPath();
                    object = object2;
                    if (string != null) {
                        n2 = string.lastIndexOf(47);
                        object = object2;
                        if (n2 != -1) {
                            object = string.substring(0, n2 + 1);
                        }
                    }
                    object2 = ((String)object).concat(this.m_path);
                    while ((n2 = ((String)object2).indexOf("/./")) != -1) {
                        object2 = ((String)object2).substring(0, n2 + 1).concat(((String)object2).substring(n2 + 3));
                    }
                    object = object2;
                    if (((String)object2).endsWith("/.")) {
                        object = ((String)object2).substring(0, ((String)object2).length() - 1);
                    }
                    while ((n2 = ((String)object).indexOf("/../")) > 0) {
                        object2 = ((String)object).substring(0, ((String)object).indexOf("/../"));
                        n = ((String)object2).lastIndexOf(47);
                        if (n == -1 || ((String)object2).substring(n).equals("..")) continue;
                        object = ((String)object).substring(0, n + 1).concat(((String)object).substring(n2 + 4));
                    }
                    object2 = object;
                    if (((String)object).endsWith("/..")) {
                        n2 = ((String)object).substring(0, ((String)object).length() - 3).lastIndexOf(47);
                        object2 = object;
                        if (n2 != -1) {
                            object2 = ((String)object).substring(0, n2 + 1);
                        }
                    }
                    this.m_path = object2;
                } else {
                    return;
                }
            }
            return;
        }
        this.initialize((URI)object);
    }

    private void initializeAuthority(String string) throws MalformedURIException {
        int n = 0;
        int n2 = 0;
        int n3 = string.length();
        int n4 = 0;
        int n5 = 0;
        CharSequence charSequence = null;
        if (string.indexOf(64, 0) != -1) {
            n4 = n5;
            for (n = n2; n < n3 && (n4 = (int)string.charAt(n)) != 64; ++n) {
            }
            charSequence = string.substring(0, n);
            ++n;
        }
        n2 = n;
        while ((n5 = n2) < n3 && (n4 = (int)string.charAt(n5)) != 58) {
            n2 = n5 + 1;
        }
        String string2 = string.substring(n, n5);
        n = n2 = -1;
        if (string2.length() > 0) {
            n = n2;
            if (n4 == 58) {
                for (n = n4 = n5 + 1; n < n3; ++n) {
                }
                string = string.substring(n4, n);
                n = n2;
                if (string.length() > 0) {
                    for (n = 0; n < string.length(); ++n) {
                        if (URI.isDigit(string.charAt(n))) {
                            continue;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(" is invalid. Port should only contain digits!");
                        throw new MalformedURIException(((StringBuilder)charSequence).toString());
                    }
                    try {
                        n = Integer.parseInt(string);
                    }
                    catch (NumberFormatException numberFormatException) {
                        n = n2;
                    }
                }
            }
        }
        this.setHost(string2);
        this.setPort(n);
        this.setUserinfo((String)charSequence);
    }

    private void initializePath(String charSequence) throws MalformedURIException {
        if (charSequence != null) {
            int n;
            int n2;
            int n3 = ((String)charSequence).length();
            int n4 = 0;
            for (n = 0; n < n3; ++n) {
                n4 = n2 = ((String)charSequence).charAt(n);
                if (n2 == 63) break;
                if (n2 == 35) {
                    n4 = n2;
                    break;
                }
                if (n2 == 37) {
                    if (n + 2 >= n3 || !URI.isHex(((String)charSequence).charAt(n + 1)) || !URI.isHex(((String)charSequence).charAt(n + 2))) {
                        throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", null));
                    }
                } else if (!URI.isReservedCharacter((char)n2) && !URI.isUnreservedCharacter((char)n2) && 92 != n2) {
                    throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_INVALID_CHAR", new Object[]{String.valueOf((char)n2)}));
                }
                n4 = n2;
            }
            this.m_path = ((String)charSequence).substring(0, n);
            int n5 = n;
            int n6 = n4;
            if (n4 == 63) {
                for (n = n6 = n + 1; n < n3; ++n) {
                    n2 = ((String)charSequence).charAt(n);
                    if (n2 == 35) {
                        n4 = n2;
                        break;
                    }
                    if (n2 == 37) {
                        if (n + 2 >= n3 || !URI.isHex(((String)charSequence).charAt(n + '\u0001')) || !URI.isHex(((String)charSequence).charAt(n + 2))) {
                            throw new MalformedURIException("Query string contains invalid escape sequence!");
                        }
                    } else if (!URI.isReservedCharacter((char)n2) && !URI.isUnreservedCharacter((char)n2)) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Query string contains invalid character:");
                        ((StringBuilder)charSequence).append((char)n2);
                        throw new MalformedURIException(((StringBuilder)charSequence).toString());
                    }
                    n4 = n2;
                }
                this.m_queryString = ((String)charSequence).substring(n6, n);
                n6 = n4;
                n5 = n;
            }
            if (n6 == 35) {
                for (n = n4 = n5 + 1; n < n3; ++n) {
                    n2 = ((String)charSequence).charAt(n);
                    if (n2 == 37) {
                        if (n + 2 < n3 && URI.isHex(((String)charSequence).charAt(n + '\u0001')) && URI.isHex(((String)charSequence).charAt(n + 2))) continue;
                        throw new MalformedURIException("Fragment contains invalid escape sequence!");
                    }
                    if (URI.isReservedCharacter((char)n2) || URI.isUnreservedCharacter((char)n2)) continue;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Fragment contains invalid character:");
                    ((StringBuilder)charSequence).append((char)n2);
                    throw new MalformedURIException(((StringBuilder)charSequence).toString());
                }
                this.m_fragment = ((String)charSequence).substring(n4, n);
            }
            return;
        }
        throw new MalformedURIException("Cannot initialize path from null string!");
    }

    private void initializeScheme(String string) throws MalformedURIException {
        int n;
        char c;
        int n2 = string.length();
        for (n = 0; n < n2 && (c = string.charAt(n)) != ':' && c != '/' && c != '?' && c != '#'; ++n) {
        }
        if ((string = string.substring(0, n)).length() != 0) {
            this.setScheme(string);
            return;
        }
        throw new MalformedURIException(Utils.messages.createMessage("ER_NO_SCHEME_INURI", null));
    }

    private static boolean isAlpha(char c) {
        boolean bl = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
        return bl;
    }

    private static boolean isAlphanum(char c) {
        boolean bl = URI.isAlpha(c) || URI.isDigit(c);
        return bl;
    }

    public static boolean isConformantSchemeName(String string) {
        if (string != null && string.trim().length() != 0) {
            if (!URI.isAlpha(string.charAt(0))) {
                return false;
            }
            for (int i = 1; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (URI.isAlphanum(c) || SCHEME_CHARACTERS.indexOf(c) != -1) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isDigit(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    private static boolean isHex(char c) {
        boolean bl = URI.isDigit(c) || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
        return bl;
    }

    private static boolean isReservedCharacter(char c) {
        boolean bl = RESERVED_CHARACTERS.indexOf(c) != -1;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isURIString(String string) {
        if (string == null) {
            return false;
        }
        int n = string.length();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            char c = string.charAt(n2);
            if (c == '%') {
                if (n2 + 2 >= n || !URI.isHex(string.charAt(n2 + 1)) || !URI.isHex(string.charAt(n2 + 2))) return false;
                n3 = n2 + 2;
            } else {
                n3 = n2;
                if (!URI.isReservedCharacter(c)) {
                    if (!URI.isUnreservedCharacter(c)) return false;
                    n3 = n2;
                }
            }
            n2 = n3 + 1;
        }
        return true;
    }

    private static boolean isUnreservedCharacter(char c) {
        boolean bl = URI.isAlphanum(c) || MARK_CHARACTERS.indexOf(c) != -1;
        return bl;
    }

    public static boolean isWellFormedAddress(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.trim();
        int n = string2.length();
        if (n != 0 && n <= 255) {
            if (!string2.startsWith(".") && !string2.startsWith("-")) {
                int n2;
                int n3 = n2 = string2.lastIndexOf(46);
                if (string2.endsWith(".")) {
                    n3 = string2.substring(0, n2).lastIndexOf(46);
                }
                if (n3 + 1 < n && URI.isDigit(string.charAt(n3 + 1))) {
                    n2 = 0;
                    for (n3 = 0; n3 < n; ++n3) {
                        char c = string2.charAt(n3);
                        if (c == '.') {
                            if (URI.isDigit(string2.charAt(n3 - 1)) && (n3 + 1 >= n || URI.isDigit(string2.charAt(n3 + 1)))) {
                                ++n2;
                                continue;
                            }
                            return false;
                        }
                        if (URI.isDigit(c)) continue;
                        return false;
                    }
                    if (n2 != 3) {
                        return false;
                    }
                } else {
                    for (n3 = 0; n3 < n; ++n3) {
                        char c = string2.charAt(n3);
                        if (c == '.') {
                            if (!URI.isAlphanum(string2.charAt(n3 - 1))) {
                                return false;
                            }
                            if (n3 + 1 >= n || URI.isAlphanum(string2.charAt(n3 + 1))) continue;
                            return false;
                        }
                        if (URI.isAlphanum(c) || c == '-') continue;
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public void appendPath(String string) throws MalformedURIException {
        if (string != null && string.trim().length() != 0) {
            if (URI.isURIString(string)) {
                CharSequence charSequence = this.m_path;
                if (charSequence != null && ((String)charSequence).trim().length() != 0) {
                    if (this.m_path.endsWith("/")) {
                        this.m_path = string.startsWith("/") ? this.m_path.concat(string.substring(1)) : this.m_path.concat(string);
                    } else if (string.startsWith("/")) {
                        this.m_path = this.m_path.concat(string);
                    } else {
                        String string2 = this.m_path;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("/");
                        ((StringBuilder)charSequence).append(string);
                        this.m_path = string2.concat(((StringBuilder)charSequence).toString());
                    }
                } else if (string.startsWith("/")) {
                    this.m_path = string;
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("/");
                    ((StringBuilder)charSequence).append(string);
                    this.m_path = ((StringBuilder)charSequence).toString();
                }
                return;
            }
            throw new MalformedURIException(Utils.messages.createMessage("ER_PATH_INVALID_CHAR", new Object[]{string}));
        }
    }

    public boolean equals(Object object) {
        if (object instanceof URI) {
            String string;
            String string2;
            object = (URI)object;
            if ((this.m_scheme == null && ((URI)object).m_scheme == null || (string2 = this.m_scheme) != null && (string = ((URI)object).m_scheme) != null && string2.equals(string)) && (this.m_userinfo == null && ((URI)object).m_userinfo == null || (string2 = this.m_userinfo) != null && (string = ((URI)object).m_userinfo) != null && string2.equals(string)) && (this.m_host == null && ((URI)object).m_host == null || (string2 = this.m_host) != null && (string = ((URI)object).m_host) != null && string2.equals(string)) && this.m_port == ((URI)object).m_port && (this.m_path == null && ((URI)object).m_path == null || (string = this.m_path) != null && (string2 = ((URI)object).m_path) != null && string.equals(string2)) && (this.m_queryString == null && ((URI)object).m_queryString == null || (string2 = this.m_queryString) != null && (string = ((URI)object).m_queryString) != null && string2.equals(string)) && (this.m_fragment == null && ((URI)object).m_fragment == null || (string2 = this.m_fragment) != null && (object = ((URI)object).m_fragment) != null && string2.equals(object))) {
                return true;
            }
        }
        return false;
    }

    public String getFragment() {
        return this.m_fragment;
    }

    public String getHost() {
        return this.m_host;
    }

    public String getPath() {
        return this.m_path;
    }

    public String getPath(boolean bl, boolean bl2) {
        StringBuffer stringBuffer = new StringBuffer(this.m_path);
        if (bl && this.m_queryString != null) {
            stringBuffer.append('?');
            stringBuffer.append(this.m_queryString);
        }
        if (bl2 && this.m_fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(this.m_fragment);
        }
        return stringBuffer.toString();
    }

    public int getPort() {
        return this.m_port;
    }

    public String getQueryString() {
        return this.m_queryString;
    }

    public String getScheme() {
        return this.m_scheme;
    }

    public String getSchemeSpecificPart() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.m_userinfo != null || this.m_host != null || this.m_port != -1) {
            stringBuffer.append("//");
        }
        if ((string = this.m_userinfo) != null) {
            stringBuffer.append(string);
            stringBuffer.append('@');
        }
        if ((string = this.m_host) != null) {
            stringBuffer.append(string);
        }
        if (this.m_port != -1) {
            stringBuffer.append(':');
            stringBuffer.append(this.m_port);
        }
        if ((string = this.m_path) != null) {
            stringBuffer.append(string);
        }
        if (this.m_queryString != null) {
            stringBuffer.append('?');
            stringBuffer.append(this.m_queryString);
        }
        if (this.m_fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(this.m_fragment);
        }
        return stringBuffer.toString();
    }

    public String getUserinfo() {
        return this.m_userinfo;
    }

    public boolean isGenericURI() {
        boolean bl = this.m_host != null;
        return bl;
    }

    public void setFragment(String string) throws MalformedURIException {
        block4 : {
            block5 : {
                block6 : {
                    block3 : {
                        block2 : {
                            if (string != null) break block2;
                            this.m_fragment = null;
                            break block3;
                        }
                        if (!this.isGenericURI()) break block4;
                        if (this.getPath() == null) break block5;
                        if (!URI.isURIString(string)) break block6;
                        this.m_fragment = string;
                    }
                    return;
                }
                throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_INVALID_CHAR", null));
            }
            throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_WHEN_PATH_NULL", null));
        }
        throw new MalformedURIException(Utils.messages.createMessage("ER_FRAG_FOR_GENERIC_URI", null));
    }

    public void setHost(String string) throws MalformedURIException {
        if (string != null && string.trim().length() != 0) {
            if (!URI.isWellFormedAddress(string)) {
                throw new MalformedURIException(Utils.messages.createMessage("ER_HOST_ADDRESS_NOT_WELLFORMED", null));
            }
        } else {
            this.m_host = string;
            this.m_userinfo = null;
            this.m_port = -1;
        }
        this.m_host = string;
    }

    public void setPath(String string) throws MalformedURIException {
        if (string == null) {
            this.m_path = null;
            this.m_queryString = null;
            this.m_fragment = null;
        } else {
            this.initializePath(string);
        }
    }

    public void setPort(int n) throws MalformedURIException {
        block7 : {
            block6 : {
                block5 : {
                    if (n < 0 || n > 65535) break block5;
                    if (this.m_host == null) {
                        throw new MalformedURIException(Utils.messages.createMessage("ER_PORT_WHEN_HOST_NULL", null));
                    }
                    break block6;
                }
                if (n != -1) break block7;
            }
            this.m_port = n;
            return;
        }
        throw new MalformedURIException(Utils.messages.createMessage("ER_INVALID_PORT", null));
    }

    public void setQueryString(String string) throws MalformedURIException {
        block4 : {
            block5 : {
                block6 : {
                    block3 : {
                        block2 : {
                            if (string != null) break block2;
                            this.m_queryString = null;
                            break block3;
                        }
                        if (!this.isGenericURI()) break block4;
                        if (this.getPath() == null) break block5;
                        if (!URI.isURIString(string)) break block6;
                        this.m_queryString = string;
                    }
                    return;
                }
                throw new MalformedURIException("Query string contains invalid character!");
            }
            throw new MalformedURIException("Query string cannot be set when path is null!");
        }
        throw new MalformedURIException("Query string can only be set for a generic URI!");
    }

    public void setScheme(String string) throws MalformedURIException {
        if (string != null) {
            if (URI.isConformantSchemeName(string)) {
                this.m_scheme = string.toLowerCase();
                return;
            }
            throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_NOT_CONFORMANT", null));
        }
        throw new MalformedURIException(Utils.messages.createMessage("ER_SCHEME_FROM_NULL_STRING", null));
    }

    public void setUserinfo(String charSequence) throws MalformedURIException {
        block5 : {
            block4 : {
                block3 : {
                    if (charSequence != null) break block3;
                    this.m_userinfo = null;
                    break block4;
                }
                if (this.m_host != null) {
                    int n = ((String)charSequence).length();
                    for (int i = 0; i < n; ++i) {
                        char c = ((String)charSequence).charAt(i);
                        if (c == '%') {
                            if (i + 2 < n && URI.isHex(((String)charSequence).charAt(i + 1)) && URI.isHex(((String)charSequence).charAt(i + 2))) continue;
                            throw new MalformedURIException("Userinfo contains invalid escape sequence!");
                        }
                        if (URI.isUnreservedCharacter(c) || USERINFO_CHARACTERS.indexOf(c) != -1) continue;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Userinfo contains invalid character:");
                        ((StringBuilder)charSequence).append(c);
                        throw new MalformedURIException(((StringBuilder)charSequence).toString());
                    }
                }
                break block5;
            }
            this.m_userinfo = charSequence;
            return;
        }
        throw new MalformedURIException("Userinfo cannot be set when host is null!");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.m_scheme;
        if (string != null) {
            stringBuffer.append(string);
            stringBuffer.append(':');
        }
        stringBuffer.append(this.getSchemeSpecificPart());
        return stringBuffer.toString();
    }

    public static class MalformedURIException
    extends IOException {
        public MalformedURIException() {
        }

        public MalformedURIException(String string) {
            super(string);
        }
    }

}

