/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.LogRecord;
import gov.nist.javax.sip.LogRecordFactory;
import gov.nist.javax.sip.stack.MessageLog;

public class DefaultMessageLogFactory
implements LogRecordFactory {
    @Override
    public LogRecord createLogRecord(String string, String string2, String string3, long l, boolean bl, String string4, String string5, String string6, long l2) {
        return new MessageLog(string, string2, string3, l, bl, string4, string5, string6, l2);
    }

    public LogRecord createLogRecord(String string, String string2, String string3, String string4, boolean bl, String string5, String string6, String string7, long l) {
        return new MessageLog(string, string2, string3, string4, bl, string5, string6, string7, l);
    }
}

