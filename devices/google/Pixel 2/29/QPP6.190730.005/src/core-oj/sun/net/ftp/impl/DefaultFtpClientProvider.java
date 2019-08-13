/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp.impl;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpClientProvider;

public class DefaultFtpClientProvider
extends FtpClientProvider {
    @Override
    public FtpClient createFtpClient() {
        return sun.net.ftp.impl.FtpClient.create();
    }
}

