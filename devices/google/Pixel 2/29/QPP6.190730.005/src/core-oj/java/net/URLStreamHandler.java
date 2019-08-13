/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Objects;
import sun.net.util.IPAddressUtil;

public abstract class URLStreamHandler {
    protected boolean equals(URL uRL, URL uRL2) {
        boolean bl = Objects.equals(uRL.getRef(), uRL2.getRef()) && Objects.equals(uRL.getQuery(), uRL2.getQuery()) && this.sameFile(uRL, uRL2);
        return bl;
    }

    protected int getDefaultPort() {
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected InetAddress getHostAddress(URL serializable) {
        synchronized (this) {
            if (((URL)serializable).hostAddress != null) {
                return ((URL)serializable).hostAddress;
            }
            Object object = ((URL)serializable).getHost();
            if (object == null) return null;
            boolean bl = ((String)object).equals("");
            if (!bl) {
                try {
                    try {
                        object = InetAddress.getByName((String)object);
                    }
                    catch (UnknownHostException unknownHostException) {
                        // empty catch block
                    }
                    try {
                        ((URL)serializable).hostAddress = object;
                        return ((URL)serializable).hostAddress;
                    }
                    catch (UnknownHostException unknownHostException) {
                    }
                }
                catch (SecurityException securityException) {
                    return null;
                }
                return null;
            }
            return null;
        }
    }

    protected int hashCode(URL uRL) {
        return Objects.hash(uRL.getRef(), uRL.getQuery(), uRL.getProtocol(), uRL.getFile(), uRL.getHost(), uRL.getPort());
    }

    protected boolean hostsEqual(URL uRL, URL uRL2) {
        if (uRL.getHost() != null && uRL2.getHost() != null) {
            return uRL.getHost().equalsIgnoreCase(uRL2.getHost());
        }
        boolean bl = uRL.getHost() == null && uRL2.getHost() == null;
        return bl;
    }

    protected abstract URLConnection openConnection(URL var1) throws IOException;

    protected URLConnection openConnection(URL uRL, Proxy proxy) throws IOException {
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void parseURL(URL serializable, String charSequence, int n, int n2) {
        int n3;
        void var7_15;
        String string;
        CharSequence charSequence2;
        int n4;
        int n5;
        String string2 = ((URL)serializable).getProtocol();
        String string3 = ((URL)serializable).getAuthority();
        String string4 = string = ((URL)serializable).getUserInfo();
        CharSequence charSequence3 = ((URL)serializable).getHost();
        int n6 = n5 = ((URL)serializable).getPort();
        CharSequence charSequence4 = ((URL)serializable).getPath();
        CharSequence charSequence5 = ((URL)serializable).getQuery();
        String string5 = ((URL)serializable).getRef();
        if (n < n2) {
            n3 = ((String)charSequence).indexOf(63);
            boolean bl = n3 == n;
            if (n3 != -1 && n3 < n2) {
                charSequence5 = ((String)charSequence).substring(n3 + 1, n2);
                n4 = n2;
                if (n2 > n3) {
                    n4 = n3;
                }
                charSequence2 = ((String)charSequence).substring(0, n3);
                n3 = 1;
            } else {
                n3 = 0;
                n4 = n2;
                charSequence2 = charSequence;
            }
        } else {
            n3 = 0;
            charSequence2 = charSequence;
            n4 = n2;
        }
        if (!false && n <= n4 - 2 && ((String)charSequence2).charAt(n) == '/' && ((String)charSequence2).charAt(n + 1) == '/') {
            void var7_11;
            for (n2 = n += 2; n2 < n4 && (n5 = (int)((String)charSequence2).charAt(n2)) != 35 && n5 != 47 && n5 != 63 && n5 != 92; ++n2) {
            }
            string3 = string4 = ((String)charSequence2).substring(n, n2);
            n = string3.indexOf(64);
            if (n != -1) {
                if (n != string3.lastIndexOf(64)) {
                    Object var7_8 = null;
                    charSequence = null;
                } else {
                    String string6 = string3.substring(0, n);
                    charSequence = string3.substring(n + 1);
                }
            } else {
                Object var7_10 = null;
                charSequence = string3;
            }
            if (charSequence != null) {
                if (((String)charSequence).length() > 0 && ((String)charSequence).charAt(0) == '[') {
                    n = ((String)charSequence).indexOf(93);
                    if (n <= 2) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Invalid authority field: ");
                        ((StringBuilder)serializable).append(string3);
                        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                    }
                    charSequence3 = ((String)charSequence).substring(0, n + 1);
                    if (!IPAddressUtil.isIPv6LiteralAddress(((String)charSequence3).substring(1, n))) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Invalid host: ");
                        ((StringBuilder)serializable).append((String)charSequence3);
                        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                    }
                    if (((String)charSequence).length() > n + 1) {
                        if (((String)charSequence).charAt(n + 1) != ':') {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Invalid authority field: ");
                            ((StringBuilder)serializable).append(string3);
                            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                        }
                        n = ((String)charSequence).length() > ++n + 1 ? Integer.parseInt(((String)charSequence).substring(n + 1)) : -1;
                    } else {
                        n = -1;
                    }
                    charSequence = charSequence3;
                } else {
                    n6 = ((String)charSequence).indexOf(58);
                    n = -1;
                    if (n6 >= 0) {
                        if (((String)charSequence).length() > n6 + 1) {
                            n = ((String)charSequence).charAt(n6 + 1);
                            if (n >= 48 && n <= 57) {
                                n = Integer.parseInt(((String)charSequence).substring(n6 + 1));
                            } else {
                                serializable = new StringBuilder();
                                ((StringBuilder)serializable).append("invalid port: ");
                                ((StringBuilder)serializable).append(((String)charSequence).substring(n6 + 1));
                                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                            }
                        }
                        charSequence = ((String)charSequence).substring(0, n6);
                    }
                }
            } else {
                charSequence = "";
                n = n6;
            }
            charSequence3 = charSequence5;
            if (n < -1) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Invalid port number :");
                ((StringBuilder)serializable).append(n);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
            string3 = null;
            if (n3 == 0) {
                charSequence3 = null;
                n3 = n;
                charSequence5 = charSequence;
                charSequence = string3;
                n = n2;
                string3 = var7_11;
                CharSequence charSequence6 = charSequence3;
            } else {
                n3 = n;
                charSequence5 = charSequence;
                charSequence = string3;
                n = n2;
                string3 = var7_11;
                CharSequence charSequence7 = charSequence3;
            }
        } else {
            n3 = n5;
            string4 = string3;
            string3 = string;
            charSequence = charSequence4;
            String string7 = charSequence5;
            charSequence5 = charSequence3;
        }
        charSequence3 = charSequence5 == null ? "" : charSequence5;
        if (n < n4) {
            if (((String)charSequence2).charAt(n) != '/' && ((String)charSequence2).charAt(n) != '\\') {
                if (charSequence != null && ((String)charSequence).length() > 0) {
                    n2 = ((String)charSequence).lastIndexOf(47);
                    charSequence5 = charSequence4 = "";
                    if (n2 == -1) {
                        charSequence5 = charSequence4;
                        if (string4 != null) {
                            charSequence5 = "/";
                        }
                    }
                    charSequence4 = new StringBuilder();
                    ((StringBuilder)charSequence4).append(((String)charSequence).substring(0, n2 + 1));
                    ((StringBuilder)charSequence4).append((String)charSequence5);
                    ((StringBuilder)charSequence4).append(((String)charSequence2).substring(n, n4));
                    charSequence = ((StringBuilder)charSequence4).toString();
                } else {
                    charSequence = string4 != null ? "/" : "";
                    charSequence5 = new StringBuilder();
                    ((StringBuilder)charSequence5).append((String)charSequence);
                    ((StringBuilder)charSequence5).append(((String)charSequence2).substring(n, n4));
                    charSequence = ((StringBuilder)charSequence5).toString();
                }
            } else {
                charSequence = ((String)charSequence2).substring(n, n4);
            }
        }
        charSequence5 = charSequence;
        if (charSequence == null) {
            charSequence5 = "";
        }
        while ((n = ((String)charSequence5).indexOf("/./")) >= 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((String)charSequence5).substring(0, n));
            ((StringBuilder)charSequence).append(((String)charSequence5).substring(n + 2));
            charSequence5 = ((StringBuilder)charSequence).toString();
        }
        n2 = 0;
        n = n4;
        do {
            n4 = n2 = (n6 = ((String)charSequence5).indexOf("/../", n2));
            n4 = n;
            charSequence = charSequence5;
            if (n6 < 0) break;
            if (n2 == 0) {
                charSequence5 = ((String)charSequence5).substring(n2 + 3);
                n2 = 0;
                continue;
            }
            n4 = n;
            if (n2 > 0) {
                n4 = n = (n6 = ((String)charSequence5).lastIndexOf(47, n2 - 1));
                if (n6 >= 0) {
                    n4 = n;
                    if (((String)charSequence5).indexOf("/../", n) != 0) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(((String)charSequence5).substring(0, n));
                        ((StringBuilder)charSequence).append(((String)charSequence5).substring(n2 + 3));
                        charSequence5 = ((StringBuilder)charSequence).toString();
                        n2 = 0;
                        continue;
                    }
                }
            }
            n2 += 3;
            n = n4;
        } while (true);
        while (((String)charSequence).endsWith("/..")) {
            n4 = ((String)charSequence).indexOf("/..");
            n = n2 = ((String)charSequence).lastIndexOf(47, n4 - 1);
            if (n2 < 0) break;
            charSequence = ((String)charSequence).substring(0, n + 1);
            n4 = n;
        }
        charSequence5 = charSequence;
        if (((String)charSequence).startsWith("./")) {
            charSequence5 = charSequence;
            if (((String)charSequence).length() > 2) {
                charSequence5 = ((String)charSequence).substring(2);
            }
        }
        if (((String)charSequence5).endsWith("/.")) {
            charSequence5 = ((String)charSequence5).substring(0, ((String)charSequence5).length() - 1);
        }
        if (((String)charSequence5).endsWith("?")) {
            charSequence5 = ((String)charSequence5).substring(0, ((String)charSequence5).length() - 1);
        }
        this.setURL((URL)serializable, string2, (String)charSequence3, n3, string4, string3, (String)charSequence5, (String)var7_15, string5);
    }

    protected boolean sameFile(URL uRL, URL uRL2) {
        int n;
        if (!(uRL.getProtocol() == uRL2.getProtocol() || uRL.getProtocol() != null && uRL.getProtocol().equalsIgnoreCase(uRL2.getProtocol()))) {
            return false;
        }
        if (!(uRL.getFile() == uRL2.getFile() || uRL.getFile() != null && uRL.getFile().equals(uRL2.getFile()))) {
            return false;
        }
        int n2 = uRL.getPort() != -1 ? uRL.getPort() : uRL.handler.getDefaultPort();
        if (n2 != (n = uRL2.getPort() != -1 ? uRL2.getPort() : uRL2.handler.getDefaultPort())) {
            return false;
        }
        return this.hostsEqual(uRL, uRL2);
    }

    @Deprecated
    protected void setURL(URL uRL, String string, String string2, int n, String string3, String string4) {
        int n2;
        String string5;
        String string6;
        CharSequence charSequence;
        if (string2 != null && string2.length() != 0) {
            if (n == -1) {
                charSequence = string2;
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(":");
                ((StringBuilder)charSequence).append(n);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            n2 = string2.lastIndexOf(64);
            if (n2 != -1) {
                string5 = string2.substring(0, n2);
                string2 = string2.substring(n2 + 1);
            } else {
                string5 = null;
            }
        } else {
            charSequence = null;
            string5 = null;
        }
        if (string3 != null) {
            n2 = string3.lastIndexOf(63);
            if (n2 != -1) {
                string6 = string3.substring(n2 + 1);
                string3 = string3.substring(0, n2);
            } else {
                string6 = null;
            }
        } else {
            string3 = null;
            string6 = null;
        }
        this.setURL(uRL, string, string2, n, (String)charSequence, string5, string3, string6, string4);
    }

    protected void setURL(URL uRL, String string, String string2, int n, String string3, String string4, String string5, String string6, String string7) {
        if (this == uRL.handler) {
            uRL.set(uRL.getProtocol(), string2, n, string3, string4, string5, string6, string7);
            return;
        }
        throw new SecurityException("handler for url different from this handler");
    }

    protected String toExternalForm(URL uRL) {
        String string;
        int n;
        int n2 = n = uRL.getProtocol().length() + 1;
        if (uRL.getAuthority() != null) {
            n2 = n;
            if (uRL.getAuthority().length() > 0) {
                n2 = n + (uRL.getAuthority().length() + 2);
            }
        }
        n = n2;
        if (uRL.getPath() != null) {
            n = n2 + uRL.getPath().length();
        }
        n2 = n;
        if (uRL.getQuery() != null) {
            n2 = n + (uRL.getQuery().length() + 1);
        }
        n = n2;
        if (uRL.getRef() != null) {
            n = n2 + (uRL.getRef().length() + 1);
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(uRL.getProtocol());
        stringBuilder.append(":");
        if (uRL.getAuthority() != null) {
            stringBuilder.append("//");
            stringBuilder.append(uRL.getAuthority());
        }
        if ((string = uRL.getFile()) != null) {
            stringBuilder.append(string);
        }
        if (uRL.getRef() != null) {
            stringBuilder.append("#");
            stringBuilder.append(uRL.getRef());
        }
        return stringBuilder.toString();
    }
}

