/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

public interface ToHeader
extends HeaderAddress,
Header,
Parameters {
    public static final String NAME = "To";

    public String getDisplayName();

    public String getTag();

    public String getUserAtHostPort();

    public boolean hasTag();

    public void removeTag();

    public void setTag(String var1) throws ParseException;
}

