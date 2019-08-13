/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import javax.xml.transform.Source;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMConfigurationException;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ObjectFactory;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.XMLStringFactory;
import org.w3c.dom.Node;

public abstract class DTMManager {
    public static final int IDENT_DTM_DEFAULT = -65536;
    public static final int IDENT_DTM_NODE_BITS = 16;
    public static final int IDENT_MAX_DTMS = 65536;
    public static final int IDENT_NODE_DEFAULT = 65535;
    private static boolean debug = false;
    private static String defaultClassName = "org.apache.xml.dtm.ref.DTMManagerDefault";
    private static final String defaultPropName = "org.apache.xml.dtm.DTMManager";
    public boolean m_incremental = false;
    public boolean m_source_location = false;
    protected XMLStringFactory m_xsf = null;

    static {
        boolean bl = System.getProperty("dtm.debug") != null;
        try {
            debug = bl;
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
    }

    protected DTMManager() {
    }

    public static DTMManager newInstance(XMLStringFactory xMLStringFactory) throws DTMConfigurationException {
        try {
            DTMManager dTMManager = (DTMManager)ObjectFactory.createObject(defaultPropName, defaultClassName);
            if (dTMManager != null) {
                dTMManager.setXMLStringFactory(xMLStringFactory);
                return dTMManager;
            }
            throw new DTMConfigurationException(XMLMessages.createXMLMessage("ER_NO_DEFAULT_IMPL", null));
        }
        catch (ObjectFactory.ConfigurationError configurationError) {
            throw new DTMConfigurationException(XMLMessages.createXMLMessage("ER_NO_DEFAULT_IMPL", null), configurationError.getException());
        }
    }

    public abstract DTMIterator createDTMIterator(int var1);

    public abstract DTMIterator createDTMIterator(int var1, DTMFilter var2, boolean var3);

    public abstract DTMIterator createDTMIterator(Object var1, int var2);

    public abstract DTMIterator createDTMIterator(String var1, PrefixResolver var2);

    public abstract DTM createDocumentFragment();

    public abstract DTM getDTM(int var1);

    public abstract DTM getDTM(Source var1, boolean var2, DTMWSFilter var3, boolean var4, boolean var5);

    public abstract int getDTMHandleFromNode(Node var1);

    public abstract int getDTMIdentity(DTM var1);

    public int getDTMIdentityMask() {
        return -65536;
    }

    public boolean getIncremental() {
        return this.m_incremental;
    }

    public int getNodeIdentityMask() {
        return 65535;
    }

    public boolean getSource_location() {
        return this.m_source_location;
    }

    public XMLStringFactory getXMLStringFactory() {
        return this.m_xsf;
    }

    public abstract boolean release(DTM var1, boolean var2);

    public void setIncremental(boolean bl) {
        this.m_incremental = bl;
    }

    public void setSource_location(boolean bl) {
        this.m_source_location = bl;
    }

    public void setXMLStringFactory(XMLStringFactory xMLStringFactory) {
        this.m_xsf = xMLStringFactory;
    }
}

