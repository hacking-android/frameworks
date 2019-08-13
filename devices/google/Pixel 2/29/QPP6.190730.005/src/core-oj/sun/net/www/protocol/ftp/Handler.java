/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.ftp;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class Handler
extends URLStreamHandler {
    @Override
    protected boolean equals(URL uRL, URL uRL2) {
        String string = uRL.getUserInfo();
        String string2 = uRL2.getUserInfo();
        boolean bl = super.equals(uRL, uRL2) && (string == null ? string2 == null : string.equals(string2));
        return bl;
    }

    @Override
    protected int getDefaultPort() {
        return 21;
    }

    @Override
    protected URLConnection openConnection(URL uRL) throws IOException {
        return this.openConnection(uRL, null);
    }

    @Override
    protected URLConnection openConnection(URL uRL, Proxy proxy) throws IOException {
        return new FtpURLConnection(uRL, proxy);
    }
}

