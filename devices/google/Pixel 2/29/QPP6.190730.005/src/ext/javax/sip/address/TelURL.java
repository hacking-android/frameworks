/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.Parameters;

public interface TelURL
extends URI,
Parameters {
    public String getIsdnSubAddress();

    public String getPhoneContext();

    public String getPhoneNumber();

    public String getPostDial();

    public boolean isGlobal();

    public void setGlobal(boolean var1);

    public void setIsdnSubAddress(String var1) throws ParseException;

    public void setPhoneContext(String var1) throws ParseException;

    public void setPhoneNumber(String var1) throws ParseException;

    public void setPostDial(String var1) throws ParseException;
}

