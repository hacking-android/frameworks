/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp;

public enum FtpReplyCode {
    RESTART_MARKER(110),
    SERVICE_READY_IN(120),
    DATA_CONNECTION_ALREADY_OPEN(125),
    FILE_STATUS_OK(150),
    COMMAND_OK(200),
    NOT_IMPLEMENTED(202),
    SYSTEM_STATUS(211),
    DIRECTORY_STATUS(212),
    FILE_STATUS(213),
    HELP_MESSAGE(214),
    NAME_SYSTEM_TYPE(215),
    SERVICE_READY(220),
    SERVICE_CLOSING(221),
    DATA_CONNECTION_OPEN(225),
    CLOSING_DATA_CONNECTION(226),
    ENTERING_PASSIVE_MODE(227),
    ENTERING_EXT_PASSIVE_MODE(229),
    LOGGED_IN(230),
    SECURELY_LOGGED_IN(232),
    SECURITY_EXCHANGE_OK(234),
    SECURITY_EXCHANGE_COMPLETE(235),
    FILE_ACTION_OK(250),
    PATHNAME_CREATED(257),
    NEED_PASSWORD(331),
    NEED_ACCOUNT(332),
    NEED_ADAT(334),
    NEED_MORE_ADAT(335),
    FILE_ACTION_PENDING(350),
    SERVICE_NOT_AVAILABLE(421),
    CANT_OPEN_DATA_CONNECTION(425),
    CONNECTION_CLOSED(426),
    NEED_SECURITY_RESOURCE(431),
    FILE_ACTION_NOT_TAKEN(450),
    ACTION_ABORTED(451),
    INSUFFICIENT_STORAGE(452),
    COMMAND_UNRECOGNIZED(500),
    INVALID_PARAMETER(501),
    BAD_SEQUENCE(503),
    NOT_IMPLEMENTED_FOR_PARAMETER(504),
    NOT_LOGGED_IN(530),
    NEED_ACCOUNT_FOR_STORING(532),
    PROT_LEVEL_DENIED(533),
    REQUEST_DENIED(534),
    FAILED_SECURITY_CHECK(535),
    UNSUPPORTED_PROT_LEVEL(536),
    PROT_LEVEL_NOT_SUPPORTED_BY_SECURITY(537),
    FILE_UNAVAILABLE(550),
    PAGE_TYPE_UNKNOWN(551),
    EXCEEDED_STORAGE(552),
    FILE_NAME_NOT_ALLOWED(553),
    PROTECTED_REPLY(631),
    UNKNOWN_ERROR(999);
    
    private final int value;

    private FtpReplyCode(int n2) {
        this.value = n2;
    }

    public static FtpReplyCode find(int n) {
        for (FtpReplyCode ftpReplyCode : FtpReplyCode.values()) {
            if (ftpReplyCode.getValue() != n) continue;
            return ftpReplyCode;
        }
        return UNKNOWN_ERROR;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isAuthentication() {
        int n = this.value;
        boolean bl = n / 10 - n / 100 * 10 == 3;
        return bl;
    }

    public boolean isConnection() {
        int n = this.value;
        boolean bl = n / 10 - n / 100 * 10 == 2;
        return bl;
    }

    public boolean isFileSystem() {
        int n = this.value;
        boolean bl = n / 10 - n / 100 * 10 == 5;
        return bl;
    }

    public boolean isInformation() {
        int n = this.value;
        int n2 = n / 10;
        boolean bl = true;
        if (n2 - (n /= 100) * 10 != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isPermanentNegative() {
        int n = this.value;
        boolean bl = n >= 500 && n < 600;
        return bl;
    }

    public boolean isPositiveCompletion() {
        int n = this.value;
        boolean bl = n >= 200 && n < 300;
        return bl;
    }

    public boolean isPositiveIntermediate() {
        int n = this.value;
        boolean bl = n >= 300 && n < 400;
        return bl;
    }

    public boolean isPositivePreliminary() {
        int n = this.value;
        boolean bl = n >= 100 && n < 200;
        return bl;
    }

    public boolean isProtectedReply() {
        int n = this.value;
        boolean bl = n >= 600 && n < 700;
        return bl;
    }

    public boolean isSyntax() {
        int n = this.value;
        boolean bl = n / 10 - n / 100 * 10 == 0;
        return bl;
    }

    public boolean isTransientNegative() {
        int n = this.value;
        boolean bl = n >= 400 && n < 500;
        return bl;
    }

    public boolean isUnspecified() {
        int n = this.value;
        boolean bl = n / 10 - n / 100 * 10 == 4;
        return bl;
    }
}

