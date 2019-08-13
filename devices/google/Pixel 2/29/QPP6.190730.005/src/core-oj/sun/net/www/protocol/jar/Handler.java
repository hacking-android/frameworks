/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.jar;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import sun.net.www.ParseUtil;
import sun.net.www.protocol.jar.JarURLConnection;

public class Handler
extends URLStreamHandler {
    private static final String separator = "!/";

    private static int indexOfBangSlash(String string) {
        int n = string.length();
        while ((n = string.lastIndexOf(33, n)) != -1) {
            if (n != string.length() - 1 && string.charAt(n + 1) == '/') {
                return n + 1;
            }
            --n;
        }
        return -1;
    }

    private String parseAbsoluteSpec(String string) {
        int n = Handler.indexOfBangSlash(string);
        if (n != -1) {
            try {
                new URL(string.substring(0, n - 1));
                return string;
            }
            catch (MalformedURLException malformedURLException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid url: ");
                stringBuilder.append(string);
                stringBuilder.append(" (");
                stringBuilder.append(malformedURLException);
                stringBuilder.append(")");
                throw new NullPointerException(stringBuilder.toString());
            }
        }
        throw new NullPointerException("no !/ in spec");
    }

    private String parseContextSpec(URL serializable, String charSequence) {
        int n;
        String string;
        String string2 = string = ((URL)serializable).getFile();
        if (((String)charSequence).startsWith("/")) {
            n = Handler.indexOfBangSlash(string);
            if (n != -1) {
                string2 = string.substring(0, n);
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("malformed context url:");
                ((StringBuilder)charSequence).append(serializable);
                ((StringBuilder)charSequence).append(": no !/");
                throw new NullPointerException(((StringBuilder)charSequence).toString());
            }
        }
        string = string2;
        if (!string2.endsWith("/")) {
            string = string2;
            if (!((String)charSequence).startsWith("/")) {
                n = string2.lastIndexOf(47);
                if (n != -1) {
                    string = string2.substring(0, n + 1);
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("malformed context url:");
                    ((StringBuilder)charSequence).append(serializable);
                    throw new NullPointerException(((StringBuilder)charSequence).toString());
                }
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append((String)charSequence);
        return ((StringBuilder)serializable).toString();
    }

    @Override
    protected int hashCode(URL object) {
        int n;
        int n2 = 0;
        String string = ((URL)object).getProtocol();
        if (string != null) {
            n2 = 0 + string.hashCode();
        }
        if ((n = ((String)(object = ((URL)object).getFile())).indexOf(separator)) == -1) {
            return ((String)object).hashCode() + n2;
        }
        string = ((String)object).substring(0, n);
        try {
            URL uRL = new URL(string);
            int n3 = uRL.hashCode();
            n2 += n3;
        }
        catch (MalformedURLException malformedURLException) {
            n2 += string.hashCode();
        }
        return n2 + ((String)object).substring(n + 2).hashCode();
    }

    @Override
    protected URLConnection openConnection(URL uRL) throws IOException {
        return new JarURLConnection(uRL, this);
    }

    @Override
    protected void parseURL(URL uRL, String string, int n, int n2) {
        String string2;
        CharSequence charSequence = null;
        int n3 = string.indexOf(35, n2);
        boolean bl = n3 == n;
        if (n3 > -1) {
            string2 = string.substring(n3 + 1, string.length());
            if (bl) {
                charSequence = uRL.getFile();
            }
        } else {
            string2 = null;
        }
        boolean bl2 = string.length() >= 4 ? string.substring(0, 4).equalsIgnoreCase("jar:") : false;
        string = string.substring(n, n2);
        if (bl2) {
            string = this.parseAbsoluteSpec(string);
        } else if (!bl) {
            charSequence = this.parseContextSpec(uRL, string);
            n = Handler.indexOfBangSlash((String)charSequence);
            string = ((String)charSequence).substring(0, n);
            charSequence = ((String)charSequence).substring(n);
            String string3 = new ParseUtil().canonizeString((String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(string3);
            string = ((StringBuilder)charSequence).toString();
        } else {
            string = charSequence;
        }
        this.setURL(uRL, "jar", "", -1, string, string2);
    }

    @Override
    protected boolean sameFile(URL uRL, URL uRL2) {
        if (uRL.getProtocol().equals("jar") && uRL2.getProtocol().equals("jar")) {
            String string = uRL.getFile();
            Object object = uRL2.getFile();
            int n = string.indexOf(separator);
            int n2 = ((String)object).indexOf(separator);
            if (n != -1 && n2 != -1) {
                if (!string.substring(n + 2).equals(((String)object).substring(n2 + 2))) {
                    return false;
                }
                try {
                    URL uRL3 = new URL(string.substring(0, n));
                    object = new URL(((String)object).substring(0, n2));
                    return super.sameFile(uRL3, (URL)object);
                }
                catch (MalformedURLException malformedURLException) {
                    return super.sameFile(uRL, uRL2);
                }
            }
            return super.sameFile(uRL, uRL2);
        }
        return false;
    }
}

