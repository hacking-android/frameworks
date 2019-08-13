/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import javax.sip.header.ViaHeader;

public interface ViaHeaderExt
extends ViaHeader {
    @Override
    public String getSentByField();

    @Override
    public String getSentProtocolField();
}

