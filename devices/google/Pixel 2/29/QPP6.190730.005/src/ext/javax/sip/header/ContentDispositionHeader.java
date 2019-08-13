/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface ContentDispositionHeader
extends Header,
Parameters {
    public static final String ALERT = "Alert";
    public static final String ICON = "Icon";
    public static final String NAME = "Content-Disposition";
    public static final String RENDER = "Render";
    public static final String SESSION = "Session";

    public String getDispositionType();

    public String getHandling();

    public void setDispositionType(String var1) throws ParseException;

    public void setHandling(String var1) throws ParseException;
}

