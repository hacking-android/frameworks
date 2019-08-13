/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.io.Serializable;
import java.text.ParseException;
import javax.sip.address.URI;

public interface Address
extends Cloneable,
Serializable {
    public Object clone();

    public boolean equals(Object var1);

    public String getDisplayName();

    public String getHost();

    public int getPort();

    public URI getURI();

    public String getUserAtHostPort();

    public boolean hasDisplayName();

    public int hashCode();

    public boolean isSIPAddress();

    public boolean isWildcard();

    public void setDisplayName(String var1) throws ParseException;

    public void setURI(URI var1);

    public void setWildCardFlag();
}

