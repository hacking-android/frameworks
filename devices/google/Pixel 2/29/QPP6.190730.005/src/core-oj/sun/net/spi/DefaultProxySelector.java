/*
 * Decompiled with CFR 0.145.
 */
package sun.net.spi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.net.NetProperties;
import sun.net.SocksProxy;

public class DefaultProxySelector
extends ProxySelector {
    private static final String SOCKS_PROXY_VERSION = "socksProxyVersion";
    private static boolean hasSystemProxies;
    static final String[][] props;

    static {
        props = new String[][]{{"http", "http.proxy", "proxy", "socksProxy"}, {"https", "https.proxy", "proxy", "socksProxy"}, {"ftp", "ftp.proxy", "ftpProxy", "proxy", "socksProxy"}, {"gopher", "gopherProxy", "socksProxy"}, {"socket", "socksProxy"}};
        hasSystemProxies = false;
    }

    private int defaultPort(String string) {
        if ("http".equalsIgnoreCase(string)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(string)) {
            return 443;
        }
        if ("ftp".equalsIgnoreCase(string)) {
            return 80;
        }
        if ("socket".equalsIgnoreCase(string)) {
            return 1080;
        }
        if ("gopher".equalsIgnoreCase(string)) {
            return 80;
        }
        return -1;
    }

    static String disjunctToRegex(String string) {
        if (string.startsWith("*")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(".*");
            stringBuilder.append(Pattern.quote(string.substring(1)));
            string = stringBuilder.toString();
        } else if (string.endsWith("*")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Pattern.quote(string.substring(0, string.length() - 1)));
            stringBuilder.append(".*");
            string = stringBuilder.toString();
        } else {
            string = Pattern.quote(string);
        }
        return string;
    }

    static boolean shouldNotUseProxyFor(Pattern pattern, String string) {
        if (pattern != null && !string.isEmpty()) {
            return pattern.matcher(string).matches();
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    static Pattern toPattern(String object2) {
        void var0_5;
        boolean bl = true;
        StringJoiner stringJoiner = new StringJoiner("|");
        for (String string : ((String)object2).split("\\|")) {
            if (string.isEmpty()) continue;
            bl = false;
            stringJoiner.add(DefaultProxySelector.disjunctToRegex(string.toLowerCase()));
        }
        if (bl) {
            Object var0_3 = null;
        } else {
            Pattern pattern = Pattern.compile(stringJoiner.toString());
        }
        return var0_5;
    }

    @Override
    public void connectFailed(URI uRI, SocketAddress socketAddress, IOException iOException) {
        if (uRI != null && socketAddress != null && iOException != null) {
            return;
        }
        throw new IllegalArgumentException("Arguments can't be null.");
    }

    @Override
    public List<Proxy> select(URI object) {
        if (object != null) {
            Object object2;
            final String string = ((URI)object).getScheme();
            Object object3 = object2 = ((URI)object).getHost();
            if (object2 == null) {
                String string2 = ((URI)object).getAuthority();
                object3 = object2;
                if (string2 != null) {
                    int n = string2.indexOf(64);
                    object = string2;
                    if (n >= 0) {
                        object = string2.substring(n + 1);
                    }
                    n = ((String)object).lastIndexOf(58);
                    object3 = object;
                    if (n >= 0) {
                        object3 = ((String)object).substring(0, n);
                    }
                }
            }
            if (string != null && object3 != null) {
                object2 = new ArrayList(1);
                object = null;
                if ("http".equalsIgnoreCase(string)) {
                    object = NonProxyInfo.httpNonProxyInfo;
                } else if ("https".equalsIgnoreCase(string)) {
                    object = NonProxyInfo.httpsNonProxyInfo;
                } else if ("ftp".equalsIgnoreCase(string)) {
                    object = NonProxyInfo.ftpNonProxyInfo;
                } else if ("socket".equalsIgnoreCase(string)) {
                    object = NonProxyInfo.socksNonProxyInfo;
                }
                object2.add(AccessController.doPrivileged(new PrivilegedAction<Proxy>((NonProxyInfo)object, ((String)object3).toLowerCase()){
                    final /* synthetic */ NonProxyInfo val$nprop;
                    final /* synthetic */ String val$urlhost;
                    {
                        this.val$nprop = nonProxyInfo;
                        this.val$urlhost = string2;
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public Proxy run() {
                        Object object;
                        Object object2;
                        int n;
                        int n2;
                        block21 : {
                            object2 = null;
                            n2 = 0;
                            while (n2 < props.length) {
                                if (!props[n2][0].equalsIgnoreCase(string)) {
                                    ++n2;
                                    continue;
                                }
                                break block21;
                            }
                            return Proxy.NO_PROXY;
                        }
                        int n3 = 1;
                        do {
                            object = object2;
                            if (n3 >= props[n2].length) break;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append(props[n2][n3]);
                            ((StringBuilder)object2).append("Host");
                            object2 = NetProperties.get(((StringBuilder)object2).toString());
                            if (object2 != null && ((String)object2).length() != 0) {
                                object = object2;
                                break;
                            }
                            ++n3;
                        } while (true);
                        if (object == null) return Proxy.NO_PROXY;
                        if (((String)object).length() == 0) {
                            return Proxy.NO_PROXY;
                        }
                        object2 = this.val$nprop;
                        if (object2 != null) {
                            String string2 = NetProperties.get(((NonProxyInfo)object2).property);
                            NonProxyInfo nonProxyInfo = this.val$nprop;
                            synchronized (nonProxyInfo) {
                                if (string2 == null) {
                                    if (this.val$nprop.defaultVal != null) {
                                        object2 = this.val$nprop.defaultVal;
                                    } else {
                                        this.val$nprop.hostsSource = null;
                                        this.val$nprop.pattern = null;
                                        object2 = string2;
                                    }
                                } else {
                                    object2 = string2;
                                    if (string2.length() != 0) {
                                        object2 = new StringBuilder();
                                        ((StringBuilder)object2).append(string2);
                                        ((StringBuilder)object2).append("|localhost|127.*|[::1]|0.0.0.0|[::0]");
                                        object2 = ((StringBuilder)object2).toString();
                                    }
                                }
                                if (object2 != null && !((String)object2).equals(this.val$nprop.hostsSource)) {
                                    this.val$nprop.pattern = DefaultProxySelector.toPattern((String)object2);
                                    this.val$nprop.hostsSource = object2;
                                }
                                if (DefaultProxySelector.shouldNotUseProxyFor(this.val$nprop.pattern, this.val$urlhost)) {
                                    return Proxy.NO_PROXY;
                                }
                            }
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(props[n2][n3]);
                        ((StringBuilder)object2).append("Port");
                        int n4 = n = NetProperties.getInteger(((StringBuilder)object2).toString(), 0).intValue();
                        if (n == 0) {
                            n4 = n;
                            if (n3 < props[n2].length - 1) {
                                int n5 = 1;
                                do {
                                    n4 = n;
                                    if (n5 >= props[n2].length - 1) break;
                                    n4 = n;
                                    if (n5 != n3) {
                                        n4 = n;
                                        if (n == 0) {
                                            object2 = new StringBuilder();
                                            ((StringBuilder)object2).append(props[n2][n5]);
                                            ((StringBuilder)object2).append("Port");
                                            n4 = NetProperties.getInteger(((StringBuilder)object2).toString(), 0);
                                        }
                                    }
                                    ++n5;
                                    n = n4;
                                } while (true);
                            }
                        }
                        n = n4;
                        if (n4 == 0) {
                            n = n3 == props[n2].length - 1 ? DefaultProxySelector.this.defaultPort("socket") : DefaultProxySelector.this.defaultPort(string);
                        }
                        object2 = InetSocketAddress.createUnresolved((String)object, n);
                        if (n3 != props[n2].length - 1) return new Proxy(Proxy.Type.HTTP, (SocketAddress)object2);
                        return SocksProxy.create((SocketAddress)object2, NetProperties.getInteger(DefaultProxySelector.SOCKS_PROXY_VERSION, 5));
                    }
                }));
                return object2;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("protocol = ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" host = ");
            ((StringBuilder)object).append((String)object3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("URI can't be null.");
    }

    static class NonProxyInfo {
        static final String defStringVal = "localhost|127.*|[::1]|0.0.0.0|[::0]";
        static NonProxyInfo ftpNonProxyInfo = new NonProxyInfo("ftp.nonProxyHosts", null, null, "localhost|127.*|[::1]|0.0.0.0|[::0]");
        static NonProxyInfo httpNonProxyInfo = new NonProxyInfo("http.nonProxyHosts", null, null, "localhost|127.*|[::1]|0.0.0.0|[::0]");
        static NonProxyInfo httpsNonProxyInfo;
        static NonProxyInfo socksNonProxyInfo;
        final String defaultVal;
        String hostsSource;
        Pattern pattern;
        final String property;

        static {
            socksNonProxyInfo = new NonProxyInfo("socksNonProxyHosts", null, null, defStringVal);
            httpsNonProxyInfo = new NonProxyInfo("https.nonProxyHosts", null, null, defStringVal);
        }

        NonProxyInfo(String string, String string2, Pattern pattern, String string3) {
            this.property = string;
            this.hostsSource = string2;
            this.pattern = pattern;
            this.defaultVal = string3;
        }
    }

}

