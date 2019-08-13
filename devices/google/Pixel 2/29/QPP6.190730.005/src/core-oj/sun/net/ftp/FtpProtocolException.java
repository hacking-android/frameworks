/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp;

import sun.net.ftp.FtpReplyCode;

public class FtpProtocolException
extends Exception {
    private static final long serialVersionUID = 5978077070276545054L;
    private final FtpReplyCode code;

    public FtpProtocolException(String string) {
        super(string);
        this.code = FtpReplyCode.UNKNOWN_ERROR;
    }

    public FtpProtocolException(String string, FtpReplyCode ftpReplyCode) {
        super(string);
        this.code = ftpReplyCode;
    }

    public FtpReplyCode getReplyCode() {
        return this.code;
    }
}

