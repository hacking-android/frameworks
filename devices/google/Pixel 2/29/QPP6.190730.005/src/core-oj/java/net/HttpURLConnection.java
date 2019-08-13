/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.NetPermission;
import java.net.ProtocolException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.Date;

public abstract class HttpURLConnection
extends URLConnection {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_BAD_GATEWAY = 502;
    public static final int HTTP_BAD_METHOD = 405;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_CLIENT_TIMEOUT = 408;
    public static final int HTTP_CONFLICT = 409;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_ENTITY_TOO_LARGE = 413;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_GATEWAY_TIMEOUT = 504;
    public static final int HTTP_GONE = 410;
    public static final int HTTP_INTERNAL_ERROR = 500;
    public static final int HTTP_LENGTH_REQUIRED = 411;
    public static final int HTTP_MOVED_PERM = 301;
    public static final int HTTP_MOVED_TEMP = 302;
    public static final int HTTP_MULT_CHOICE = 300;
    public static final int HTTP_NOT_ACCEPTABLE = 406;
    public static final int HTTP_NOT_AUTHORITATIVE = 203;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_NOT_IMPLEMENTED = 501;
    public static final int HTTP_NOT_MODIFIED = 304;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_OK = 200;
    public static final int HTTP_PARTIAL = 206;
    public static final int HTTP_PAYMENT_REQUIRED = 402;
    public static final int HTTP_PRECON_FAILED = 412;
    public static final int HTTP_PROXY_AUTH = 407;
    public static final int HTTP_REQ_TOO_LONG = 414;
    public static final int HTTP_RESET = 205;
    public static final int HTTP_SEE_OTHER = 303;
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_UNAVAILABLE = 503;
    public static final int HTTP_UNSUPPORTED_TYPE = 415;
    public static final int HTTP_USE_PROXY = 305;
    public static final int HTTP_VERSION = 505;
    private static boolean followRedirects = true;
    private static final String[] methods = new String[]{"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
    protected int chunkLength = -1;
    protected int fixedContentLength = -1;
    protected long fixedContentLengthLong = -1L;
    protected boolean instanceFollowRedirects = followRedirects;
    protected String method = "GET";
    protected int responseCode = -1;
    protected String responseMessage = null;

    protected HttpURLConnection(URL uRL) {
        super(uRL);
    }

    public static boolean getFollowRedirects() {
        return followRedirects;
    }

    public static void setFollowRedirects(boolean bl) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        followRedirects = bl;
    }

    public abstract void disconnect();

    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public String getHeaderField(int n) {
        return null;
    }

    @Override
    public long getHeaderFieldDate(String charSequence, long l) {
        String string = this.getHeaderField((String)charSequence);
        charSequence = string;
        try {
            if (string.indexOf("GMT") == -1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" GMT");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            long l2 = Date.parse((String)charSequence);
            return l2;
        }
        catch (Exception exception) {
            return l;
        }
    }

    @Override
    public String getHeaderFieldKey(int n) {
        return null;
    }

    public boolean getInstanceFollowRedirects() {
        return this.instanceFollowRedirects;
    }

    @Override
    public Permission getPermission() throws IOException {
        int n = this.url.getPort();
        if (n < 0) {
            n = 80;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.url.getHost());
        stringBuilder.append(":");
        stringBuilder.append(n);
        return new SocketPermission(stringBuilder.toString(), "connect");
    }

    public String getRequestMethod() {
        return this.method;
    }

    public int getResponseCode() throws IOException {
        int n;
        int n2 = this.responseCode;
        if (n2 != -1) {
            return n2;
        }
        Object var2_2 = null;
        try {
            this.getInputStream();
        }
        catch (Exception exception) {
            // empty catch block
        }
        String string = this.getHeaderField(0);
        if (string == null) {
            if (var2_2 != null) {
                if (var2_2 instanceof RuntimeException) {
                    throw (RuntimeException)var2_2;
                }
                throw (IOException)var2_2;
            }
            return -1;
        }
        if (string.startsWith("HTTP/1.") && (n = string.indexOf(32)) > 0) {
            int n3 = string.indexOf(32, n + 1);
            if (n3 > 0 && n3 < string.length()) {
                this.responseMessage = string.substring(n3 + 1);
            }
            n2 = n3;
            if (n3 < 0) {
                n2 = string.length();
            }
            try {
                n2 = this.responseCode = Integer.parseInt(string.substring(n + 1, n2));
                return n2;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return -1;
    }

    public String getResponseMessage() throws IOException {
        this.getResponseCode();
        return this.responseMessage;
    }

    public void setChunkedStreamingMode(int n) {
        if (!this.connected) {
            if (this.fixedContentLength == -1 && this.fixedContentLengthLong == -1L) {
                if (n <= 0) {
                    n = 4096;
                }
                this.chunkLength = n;
                return;
            }
            throw new IllegalStateException("Fixed length streaming mode set");
        }
        throw new IllegalStateException("Can't set streaming mode: already connected");
    }

    public void setFixedLengthStreamingMode(int n) {
        if (!this.connected) {
            if (this.chunkLength == -1) {
                if (n >= 0) {
                    this.fixedContentLength = n;
                    return;
                }
                throw new IllegalArgumentException("invalid content length");
            }
            throw new IllegalStateException("Chunked encoding streaming mode set");
        }
        throw new IllegalStateException("Already connected");
    }

    public void setFixedLengthStreamingMode(long l) {
        if (!this.connected) {
            if (this.chunkLength == -1) {
                if (l >= 0L) {
                    this.fixedContentLengthLong = l;
                    return;
                }
                throw new IllegalArgumentException("invalid content length");
            }
            throw new IllegalStateException("Chunked encoding streaming mode set");
        }
        throw new IllegalStateException("Already connected");
    }

    public void setInstanceFollowRedirects(boolean bl) {
        this.instanceFollowRedirects = bl;
    }

    public void setRequestMethod(String string) throws ProtocolException {
        if (!this.connected) {
            Object object;
            for (int i = 0; i < ((String[])(object = methods)).length; ++i) {
                if (!object[i].equals(string)) continue;
                if (string.equals("TRACE") && (object = System.getSecurityManager()) != null) {
                    ((SecurityManager)object).checkPermission(new NetPermission("allowHttpTrace"));
                }
                this.method = string;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid HTTP method: ");
            ((StringBuilder)object).append(string);
            throw new ProtocolException(((StringBuilder)object).toString());
        }
        throw new ProtocolException("Can't reset method: already connected");
    }

    public abstract boolean usingProxy();
}

