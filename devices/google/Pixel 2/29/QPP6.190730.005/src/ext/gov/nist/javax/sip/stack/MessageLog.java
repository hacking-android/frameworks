/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.LogRecord;

class MessageLog
implements LogRecord {
    private String callId;
    private String destination;
    private String firstLine;
    private boolean isSender;
    private String message;
    private String source;
    private String tid;
    private long timeStamp;
    private long timeStampHeaderValue;

    public MessageLog(String string, String string2, String string3, long l, boolean bl, String string4, String string5, String string6, long l2) {
        if (string != null && !string.equals("")) {
            this.message = string;
            this.source = string2;
            this.destination = string3;
            if (l >= 0L) {
                this.timeStamp = l;
                this.isSender = bl;
                this.firstLine = string4;
                this.tid = string5;
                this.callId = string6;
                this.timeStampHeaderValue = l2;
                return;
            }
            throw new IllegalArgumentException("negative ts");
        }
        throw new IllegalArgumentException("null msg");
    }

    public MessageLog(String object, String string, String string2, String string3, boolean bl, String string4, String string5, String string6, long l) {
        if (object != null && !((String)object).equals("")) {
            block5 : {
                long l2;
                this.message = object;
                this.source = string;
                this.destination = string2;
                try {
                    l2 = Long.parseLong(string3);
                    if (l2 < 0L) break block5;
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Bad number format ");
                    stringBuilder.append(string3);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.timeStamp = l2;
                this.isSender = bl;
                this.firstLine = string4;
                this.tid = string5;
                this.callId = string6;
                this.timeStampHeaderValue = l;
                return;
            }
            object = new IllegalArgumentException("Bad time stamp ");
            throw object;
        }
        throw new IllegalArgumentException("null msg");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof MessageLog;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (MessageLog)object;
        bl = bl2;
        if (((MessageLog)object).message.equals(this.message)) {
            bl = bl2;
            if (((MessageLog)object).timeStamp == this.timeStamp) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("<message\nfrom=\"");
        ((StringBuilder)charSequence2).append(this.source);
        ((StringBuilder)charSequence2).append("\" \nto=\"");
        ((StringBuilder)charSequence2).append(this.destination);
        ((StringBuilder)charSequence2).append("\" \ntime=\"");
        ((StringBuilder)charSequence2).append(this.timeStamp);
        ((StringBuilder)charSequence2).append("\"");
        if (this.timeStampHeaderValue != 0L) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("\ntimeStamp = \"");
            ((StringBuilder)charSequence).append(this.timeStampHeaderValue);
            ((StringBuilder)charSequence).append("\"");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\nisSender=\"");
        ((StringBuilder)charSequence2).append(this.isSender);
        ((StringBuilder)charSequence2).append("\" \ntransactionId=\"");
        ((StringBuilder)charSequence2).append(this.tid);
        ((StringBuilder)charSequence2).append("\" \ncallId=\"");
        ((StringBuilder)charSequence2).append(this.callId);
        ((StringBuilder)charSequence2).append("\" \nfirstLine=\"");
        ((StringBuilder)charSequence2).append(this.firstLine.trim());
        ((StringBuilder)charSequence2).append("\" \n>\n");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("<![CDATA[");
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(this.message);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("]]>\n");
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("</message>\n");
        return ((StringBuilder)charSequence2).toString();
    }
}

