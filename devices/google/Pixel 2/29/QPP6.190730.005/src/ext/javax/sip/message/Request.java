/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.message;

import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.message.Message;

public interface Request
extends Message {
    public static final String ACK = "ACK";
    public static final String BYE = "BYE";
    public static final String CANCEL = "CANCEL";
    public static final String INFO = "INFO";
    public static final String INVITE = "INVITE";
    public static final String MESSAGE = "MESSAGE";
    public static final String NOTIFY = "NOTIFY";
    public static final String OPTIONS = "OPTIONS";
    public static final String PRACK = "PRACK";
    public static final String PUBLISH = "PUBLISH";
    public static final String REFER = "REFER";
    public static final String REGISTER = "REGISTER";
    public static final String SUBSCRIBE = "SUBSCRIBE";
    public static final String UPDATE = "UPDATE";

    public String getMethod();

    public URI getRequestURI();

    public void setMethod(String var1) throws ParseException;

    public void setRequestURI(URI var1);
}

