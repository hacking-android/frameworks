/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import java.util.ListIterator;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface PChargingFunctionAddressesHeader
extends Parameters,
Header {
    public static final String NAME = "P-Charging-Function-Addresses";

    public void addChargingCollectionFunctionAddress(String var1) throws ParseException;

    public void addEventChargingFunctionAddress(String var1) throws ParseException;

    public ListIterator getChargingCollectionFunctionAddresses();

    public ListIterator getEventChargingFunctionAddresses();

    public void removeChargingCollectionFunctionAddress(String var1) throws ParseException;

    public void removeEventChargingFunctionAddress(String var1) throws ParseException;

    public void setChargingCollectionFunctionAddress(String var1) throws ParseException;

    public void setEventChargingFunctionAddress(String var1) throws ParseException;
}

