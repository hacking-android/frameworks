/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.DuplicateNameValueList;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddressesHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.sip.header.ExtensionHeader;

public class PChargingFunctionAddresses
extends ParametersHeader
implements PChargingFunctionAddressesHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PChargingFunctionAddresses() {
        super("P-Charging-Function-Addresses");
    }

    @Override
    public void addChargingCollectionFunctionAddress(String string) throws ParseException {
        if (string != null) {
            this.parameters.set("ccf", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setChargingCollectionFunctionAddress(), the ccfAddress parameter is null.");
    }

    @Override
    public void addEventChargingFunctionAddress(String string) throws ParseException {
        if (string != null) {
            this.parameters.set("ecf", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setEventChargingFunctionAddress(), the ecfAddress parameter is null.");
    }

    public boolean delete(String string, String string2) {
        Iterator<NameValue> iterator = this.parameters.iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            NameValue nameValue = iterator.next();
            if (!((String)nameValue.getValueAsObject()).equalsIgnoreCase(string) || !nameValue.getName().equalsIgnoreCase(string2)) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.duplicates.isEmpty()) {
            stringBuffer.append(this.duplicates.encode());
        }
        return stringBuffer.toString();
    }

    @Override
    public ListIterator getChargingCollectionFunctionAddresses() {
        Iterator<NameValue> iterator = this.parameters.iterator();
        LinkedList<NameValue> linkedList = new LinkedList<NameValue>();
        while (iterator.hasNext()) {
            NameValue nameValue = iterator.next();
            if (!nameValue.getName().equalsIgnoreCase("ccf")) continue;
            NameValue nameValue2 = new NameValue();
            nameValue2.setName(nameValue.getName());
            nameValue2.setValueAsObject(nameValue.getValueAsObject());
            linkedList.add(nameValue2);
        }
        return linkedList.listIterator();
    }

    @Override
    public ListIterator<NameValue> getEventChargingFunctionAddresses() {
        Object object = new LinkedList();
        Iterator<NameValue> iterator = this.parameters.iterator();
        object = ((AbstractList)object).listIterator();
        while (iterator.hasNext()) {
            NameValue nameValue = iterator.next();
            if (!nameValue.getName().equalsIgnoreCase("ecf")) continue;
            NameValue nameValue2 = new NameValue();
            nameValue2.setName(nameValue.getName());
            nameValue2.setValueAsObject(nameValue.getValueAsObject());
            object.add(nameValue2);
        }
        return object;
    }

    @Override
    public void removeChargingCollectionFunctionAddress(String string) throws ParseException {
        if (string != null) {
            if (this.delete(string, "ccf")) {
                return;
            }
            throw new ParseException("CCF Address Not Removed", 0);
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setChargingCollectionFunctionAddress(), the ccfAddress parameter is null.");
    }

    @Override
    public void removeEventChargingFunctionAddress(String string) throws ParseException {
        if (string != null) {
            if (this.delete(string, "ecf")) {
                return;
            }
            throw new ParseException("ECF Address Not Removed", 0);
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setEventChargingFunctionAddress(), the ecfAddress parameter is null.");
    }

    @Override
    public void setChargingCollectionFunctionAddress(String string) throws ParseException {
        if (string != null) {
            this.setMultiParameter("ccf", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setChargingCollectionFunctionAddress(), the ccfAddress parameter is null.");
    }

    @Override
    public void setEventChargingFunctionAddress(String string) throws ParseException {
        if (string != null) {
            this.setMultiParameter("ecf", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, P-Charging-Function-Addresses, setEventChargingFunctionAddress(), the ecfAddress parameter is null.");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

