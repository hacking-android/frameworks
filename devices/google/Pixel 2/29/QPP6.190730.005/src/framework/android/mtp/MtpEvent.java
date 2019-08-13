/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

public class MtpEvent {
    public static final int EVENT_CANCEL_TRANSACTION = 16385;
    public static final int EVENT_CAPTURE_COMPLETE = 16397;
    public static final int EVENT_DEVICE_INFO_CHANGED = 16392;
    public static final int EVENT_DEVICE_PROP_CHANGED = 16390;
    public static final int EVENT_DEVICE_RESET = 16395;
    public static final int EVENT_OBJECT_ADDED = 16386;
    public static final int EVENT_OBJECT_INFO_CHANGED = 16391;
    public static final int EVENT_OBJECT_PROP_CHANGED = 51201;
    public static final int EVENT_OBJECT_PROP_DESC_CHANGED = 51202;
    public static final int EVENT_OBJECT_REFERENCES_CHANGED = 51203;
    public static final int EVENT_OBJECT_REMOVED = 16387;
    public static final int EVENT_REQUEST_OBJECT_TRANSFER = 16393;
    public static final int EVENT_STORAGE_INFO_CHANGED = 16396;
    public static final int EVENT_STORE_ADDED = 16388;
    public static final int EVENT_STORE_FULL = 16394;
    public static final int EVENT_STORE_REMOVED = 16389;
    public static final int EVENT_UNDEFINED = 16384;
    public static final int EVENT_UNREPORTED_STATUS = 16398;
    private int mEventCode = 16384;
    private int mParameter1;
    private int mParameter2;
    private int mParameter3;

    private MtpEvent() {
    }

    public int getDevicePropCode() {
        int n = this.mEventCode;
        if (n == 16390) {
            return this.mParameter1;
        }
        throw new IllegalParameterAccess("devicePropCode", n);
    }

    public int getEventCode() {
        return this.mEventCode;
    }

    public int getObjectFormatCode() {
        int n = this.mEventCode;
        if (n == 51202) {
            return this.mParameter2;
        }
        throw new IllegalParameterAccess("objectFormatCode", n);
    }

    public int getObjectHandle() {
        int n = this.mEventCode;
        if (n != 16386) {
            if (n != 16387) {
                if (n != 16391) {
                    if (n != 16393) {
                        if (n != 51201) {
                            if (n == 51203) {
                                return this.mParameter1;
                            }
                            throw new IllegalParameterAccess("objectHandle", n);
                        }
                        return this.mParameter1;
                    }
                    return this.mParameter1;
                }
                return this.mParameter1;
            }
            return this.mParameter1;
        }
        return this.mParameter1;
    }

    public int getObjectPropCode() {
        int n = this.mEventCode;
        switch (n) {
            default: {
                throw new IllegalParameterAccess("objectPropCode", n);
            }
            case 51202: {
                return this.mParameter1;
            }
            case 51201: 
        }
        return this.mParameter2;
    }

    public int getParameter1() {
        return this.mParameter1;
    }

    public int getParameter2() {
        return this.mParameter2;
    }

    public int getParameter3() {
        return this.mParameter3;
    }

    public int getStorageId() {
        int n = this.mEventCode;
        if (n != 16388) {
            if (n != 16389) {
                if (n != 16394) {
                    if (n == 16396) {
                        return this.mParameter1;
                    }
                    throw new IllegalParameterAccess("storageID", n);
                }
                return this.mParameter1;
            }
            return this.mParameter1;
        }
        return this.mParameter1;
    }

    public int getTransactionId() {
        int n = this.mEventCode;
        if (n == 16397) {
            return this.mParameter1;
        }
        throw new IllegalParameterAccess("transactionID", n);
    }

    private static class IllegalParameterAccess
    extends UnsupportedOperationException {
        public IllegalParameterAccess(String string2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot obtain ");
            stringBuilder.append(string2);
            stringBuilder.append(" for the event: ");
            stringBuilder.append(n);
            stringBuilder.append(".");
            super(stringBuilder.toString());
        }
    }

}

