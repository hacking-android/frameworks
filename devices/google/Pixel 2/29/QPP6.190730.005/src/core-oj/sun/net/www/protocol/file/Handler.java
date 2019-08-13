/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.file;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import sun.net.www.ParseUtil;
import sun.net.www.protocol.file.FileURLConnection;

public class Handler
extends URLStreamHandler {
    private String getHost(URL object) {
        String string = ((URL)object).getHost();
        object = string;
        if (string == null) {
            object = "";
        }
        return object;
    }

    protected URLConnection createFileURLConnection(URL uRL, File file) {
        return new FileURLConnection(uRL, file);
    }

    @Override
    protected boolean hostsEqual(URL uRL, URL uRL2) {
        String string = uRL.getHost();
        String string2 = uRL2.getHost();
        if ("localhost".equalsIgnoreCase(string) && (string2 == null || "".equals(string2))) {
            return true;
        }
        if ("localhost".equalsIgnoreCase(string2) && (string == null || "".equals(string))) {
            return true;
        }
        return super.hostsEqual(uRL, uRL2);
    }

    @Override
    public URLConnection openConnection(URL object) throws IOException {
        synchronized (this) {
            object = this.openConnection((URL)object, null);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public URLConnection openConnection(URL object, Proxy object22) throws IOException {
        synchronized (this) {
            Object object3;
            boolean bl;
            String string = ((URL)object).getHost();
            if (!(string == null || string.equals("") || string.equals("~") || (bl = string.equalsIgnoreCase("localhost")))) {
                Object object4;
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(((URL)object).getFile());
                    if (((URL)object).getRef() == null) {
                        object4 = "";
                    } else {
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("#");
                        ((StringBuilder)object4).append(((URL)object).getRef());
                        object4 = ((StringBuilder)object4).toString();
                    }
                    stringBuilder.append((String)object4);
                    URL uRL = new URL("ftp", string, stringBuilder.toString());
                    object3 = object3 != null ? uRL.openConnection((Proxy)object3) : uRL.openConnection();
                }
                catch (IOException object22) {
                    object3 = null;
                }
                if (object3 != null) {
                    return object3;
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Unable to connect to: ");
                ((StringBuilder)object3).append(((URL)object).toExternalForm());
                object4 = new IOException(((StringBuilder)object3).toString());
                throw object4;
            }
            object3 = new File(ParseUtil.decode(((URL)object).getPath()));
            return this.createFileURLConnection((URL)object, (File)object3);
        }
    }

    @Override
    protected void parseURL(URL uRL, String string, int n, int n2) {
        super.parseURL(uRL, string.replace(File.separatorChar, '/'), n, n2);
    }
}

