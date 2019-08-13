/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import gov.nist.javax.sip.address.AddressFactoryImpl;
import gov.nist.javax.sip.header.HeaderFactoryImpl;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sip.PeerUnavailableException;
import javax.sip.SipStack;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;

public class SipFactory {
    private static final String IP_ADDRESS_PROP = "javax.sip.IP_ADDRESS";
    private static final String STACK_NAME_PROP = "javax.sip.STACK_NAME";
    private static SipFactory sSipFactory = null;
    private Map<String, SipStack> mNameSipStackMap = new HashMap<String, SipStack>();

    private SipFactory() {
    }

    public static SipFactory getInstance() {
        synchronized (SipFactory.class) {
            SipFactory sipFactory;
            if (sSipFactory == null) {
                sSipFactory = sipFactory = new SipFactory();
            }
            sipFactory = sSipFactory;
            return sipFactory;
        }
    }

    public AddressFactory createAddressFactory() throws PeerUnavailableException {
        try {
            AddressFactoryImpl addressFactoryImpl = new AddressFactoryImpl();
            return addressFactoryImpl;
        }
        catch (Exception exception) {
            if (exception instanceof PeerUnavailableException) {
                throw (PeerUnavailableException)exception;
            }
            throw new PeerUnavailableException("Failed to create AddressFactory", exception);
        }
    }

    public HeaderFactory createHeaderFactory() throws PeerUnavailableException {
        try {
            HeaderFactoryImpl headerFactoryImpl = new HeaderFactoryImpl();
            return headerFactoryImpl;
        }
        catch (Exception exception) {
            if (exception instanceof PeerUnavailableException) {
                throw (PeerUnavailableException)exception;
            }
            throw new PeerUnavailableException("Failed to create HeaderFactory", exception);
        }
    }

    public MessageFactory createMessageFactory() throws PeerUnavailableException {
        try {
            MessageFactoryImpl messageFactoryImpl = new MessageFactoryImpl();
            return messageFactoryImpl;
        }
        catch (Exception exception) {
            if (exception instanceof PeerUnavailableException) {
                throw (PeerUnavailableException)exception;
            }
            throw new PeerUnavailableException("Failed to create MessageFactory", exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SipStack createSipStack(Properties serializable) throws PeerUnavailableException {
        synchronized (this) {
            Object object;
            Object object2 = ((Properties)serializable).getProperty(IP_ADDRESS_PROP);
            String string = object2;
            if (object2 == null && (string = ((Properties)serializable).getProperty(STACK_NAME_PROP)) == null) {
                serializable = new PeerUnavailableException("javax.sip.STACK_NAME property not found");
                throw serializable;
            }
            object2 = object = this.mNameSipStackMap.get(string);
            if (object == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("gov.nist.");
                ((StringBuilder)object2).append(SipStack.class.getCanonicalName());
                ((StringBuilder)object2).append("Impl");
                object = ((StringBuilder)object2).toString();
                try {
                    object2 = Class.forName((String)object).asSubclass(SipStack.class).getConstructor(Properties.class).newInstance(serializable);
                    this.mNameSipStackMap.put(string, (SipStack)object2);
                }
                catch (Exception exception) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Failed to initiate ");
                    ((StringBuilder)serializable).append((String)object);
                    object2 = new PeerUnavailableException(((StringBuilder)serializable).toString(), exception);
                    throw object2;
                }
            }
            return object2;
        }
    }

    public void resetFactory() {
        synchronized (this) {
            this.mNameSipStackMap.clear();
            return;
        }
    }
}

