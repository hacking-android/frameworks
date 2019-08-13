/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sun.net.www.MessageHeader;

public abstract class URLConnection
extends java.net.URLConnection {
    private static HashMap<String, Void> proxiedHosts = new HashMap();
    private int contentLength = -1;
    private String contentType;
    protected MessageHeader properties = new MessageHeader();

    public URLConnection(URL uRL) {
        super(uRL);
    }

    public static boolean isProxiedHost(String string) {
        synchronized (URLConnection.class) {
            boolean bl = proxiedHosts.containsKey(string.toLowerCase());
            return bl;
        }
    }

    public static void setProxiedHost(String string) {
        synchronized (URLConnection.class) {
            proxiedHosts.put(string.toLowerCase(), null);
            return;
        }
    }

    @Override
    public void addRequestProperty(String string, String string2) {
        if (!this.connected) {
            if (string != null) {
                return;
            }
            throw new NullPointerException("key is null");
        }
        throw new IllegalStateException("Already connected");
    }

    public boolean canCache() {
        boolean bl = this.url.getFile().indexOf(63) < 0;
        return bl;
    }

    public void close() {
        this.url = null;
    }

    @Override
    public int getContentLength() {
        int n;
        block5 : {
            int n2;
            try {
                this.getInputStream();
                n = n2 = this.contentLength;
                if (n2 >= 0) break block5;
                n = n2;
            }
            catch (Exception exception) {
                return -1;
            }
            n = n2 = Integer.parseInt(this.properties.findValue("content-length"));
            try {
                this.setContentLength(n2);
                n = n2;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return n;
    }

    @Override
    public String getContentType() {
        block8 : {
            String string;
            block10 : {
                block9 : {
                    String string2;
                    if (this.contentType == null) {
                        this.contentType = this.getHeaderField("content-type");
                    }
                    if (this.contentType != null) break block8;
                    string = null;
                    try {
                        string = string2 = URLConnection.guessContentTypeFromStream(this.getInputStream());
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    String string3 = this.properties.findValue("content-encoding");
                    string2 = string;
                    if (string == null) {
                        string2 = string = this.properties.findValue("content-type");
                        if (string == null) {
                            string2 = this.url.getFile().endsWith("/") ? "text/html" : URLConnection.guessContentTypeFromName(this.url.getFile());
                        }
                    }
                    if (string2 == null) break block9;
                    string = string2;
                    if (string3 == null) break block10;
                    string = string2;
                    if (string3.equalsIgnoreCase("7bit")) break block10;
                    string = string2;
                    if (string3.equalsIgnoreCase("8bit")) break block10;
                    string = string2;
                    if (string3.equalsIgnoreCase("binary")) break block10;
                }
                string = "content/unknown";
            }
            this.setContentType(string);
        }
        return this.contentType;
    }

    @Override
    public String getHeaderField(int n) {
        String string = null;
        try {
            this.getInputStream();
            MessageHeader messageHeader = this.properties;
            if (messageHeader != null) {
                string = messageHeader.getValue(n);
            }
            return string;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getHeaderField(String string) {
        Object var2_3 = null;
        try {
            this.getInputStream();
            MessageHeader messageHeader = this.properties;
            string = messageHeader == null ? var2_3 : messageHeader.findValue(string);
            return string;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getHeaderFieldKey(int n) {
        String string = null;
        try {
            this.getInputStream();
            MessageHeader messageHeader = this.properties;
            if (messageHeader != null) {
                string = messageHeader.getKey(n);
            }
            return string;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public MessageHeader getProperties() {
        return this.properties;
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return Collections.emptyMap();
        }
        throw new IllegalStateException("Already connected");
    }

    @Override
    public String getRequestProperty(String string) {
        if (!this.connected) {
            return null;
        }
        throw new IllegalStateException("Already connected");
    }

    protected void setContentLength(int n) {
        this.contentLength = n;
        this.properties.set("content-length", String.valueOf(n));
    }

    public void setContentType(String string) {
        this.contentType = string;
        this.properties.set("content-type", string);
    }

    public void setProperties(MessageHeader messageHeader) {
        this.properties = messageHeader;
    }

    @Override
    public void setRequestProperty(String string, String string2) {
        if (!this.connected) {
            if (string != null) {
                this.properties.set(string, string2);
                return;
            }
            throw new NullPointerException("key cannot be null");
        }
        throw new IllegalAccessError("Already connected");
    }
}

