/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

public interface ReferToHeader
extends HeaderAddress,
Header,
Parameters {
    public static final String NAME = "Refer-To";
}

