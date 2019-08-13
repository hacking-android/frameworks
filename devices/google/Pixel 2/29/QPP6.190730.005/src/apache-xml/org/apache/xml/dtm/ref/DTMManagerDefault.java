/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMException;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMDefaultBase;
import org.apache.xml.dtm.ref.DTMNodeProxy;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.dtm.ref.dom2dtm.DOM2DTM;
import org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode;
import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLReaderManager;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DTMManagerDefault
extends DTMManager {
    private static final boolean DEBUG = false;
    private static final boolean DUMPTREE = false;
    protected DefaultHandler m_defaultHandler = new DefaultHandler();
    int[] m_dtm_offsets = new int[256];
    protected DTM[] m_dtms = new DTM[256];
    private ExpandedNameTable m_expandedNameTable = new ExpandedNameTable();
    protected XMLReaderManager m_readerManager = null;

    public void addDTM(DTM dTM, int n) {
        synchronized (this) {
            this.addDTM(dTM, n, 0);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addDTM(DTM object, int n, int n2) {
        synchronized (this) {
            Throwable throwable2;
            if (n < 65536) {
                try {
                    int n3 = this.m_dtms.length;
                    if (n3 <= n) {
                        int n4 = Math.min(n + 256, 65536);
                        Object[] arrobject = new DTM[n4];
                        System.arraycopy(this.m_dtms, 0, arrobject, 0, n3);
                        this.m_dtms = arrobject;
                        arrobject = new int[n4];
                        System.arraycopy(this.m_dtm_offsets, 0, arrobject, 0, n3);
                        this.m_dtm_offsets = arrobject;
                    }
                    this.m_dtms[n] = object;
                    this.m_dtm_offsets[n] = n2;
                    object.documentRegistration();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new DTMException(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public DTMIterator createDTMIterator(int n) {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public DTMIterator createDTMIterator(int n, DTMFilter dTMFilter, boolean bl) {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public DTMIterator createDTMIterator(Object object, int n) {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public DTMIterator createDTMIterator(String string, PrefixResolver prefixResolver) {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DTM createDocumentFragment() {
        synchronized (this) {
            try {
                try {
                    Object object = DocumentBuilderFactory.newInstance();
                    ((DocumentBuilderFactory)object).setNamespaceAware(true);
                    object = ((DocumentBuilderFactory)object).newDocumentBuilder().newDocument().createDocumentFragment();
                    DOMSource dOMSource = new DOMSource((Node)object);
                    return this.getDTM(dOMSource, true, null, false, false);
                }
                catch (Exception exception) {
                    DTMException dTMException = new DTMException(exception);
                    throw dTMException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public DTM getDTM(int n) {
        // MONITORENTER : this
        DTM dTM = this.m_dtms[n >>> 16];
        // MONITOREXIT : this
        return dTM;
        {
            catch (Throwable throwable22) {
                throw throwable22;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
            if (n != -1) throw arrayIndexOutOfBoundsException;
            // MONITOREXIT : this
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public DTM getDTM(Source var1_1, boolean var2_20, DTMWSFilter var3_21, boolean var4_23, boolean var5_24) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int getDTMHandleFromNode(Node var1_1) {
        block9 : {
            // MONITORENTER : this
            if (var1_1 == null) ** GOTO lbl10
            if (var1_1 instanceof DTMNodeProxy) {
                var2_3 = ((DTMNodeProxy)var1_1).getDTMNodeNumber();
                // MONITOREXIT : this
                return var2_3;
            }
            var3_5 = this.m_dtms.length;
            break block9;
lbl10: // 1 sources:
            var1_1 = new IllegalArgumentException(XMLMessages.createXMLMessage("ER_NODE_NON_NULL", null));
            throw var1_1;
        }
        for (var2_4 = 0; var2_4 < var3_5; ++var2_4) {
            var4_6 = this.m_dtms[var2_4];
            if (var4_6 == null || !(var4_6 instanceof DOM2DTM) || (var5_7 = ((DOM2DTM)var4_6).getHandleOfNode((Node)var1_1)) == -1) continue;
            // MONITOREXIT : this
            return var5_7;
        }
        var6_8 = var1_1;
        for (var4_6 = var6_8.getNodeType() == 2 ? ((Attr)var6_8).getOwnerElement() : var6_8.getParentNode(); var4_6 != null; var4_6 = var4_6.getParentNode()) {
            var6_8 = var4_6;
        }
        var4_6 = new DOMSource((Node)var6_8);
        var4_6 = (DOM2DTM)this.getDTM((Source)var4_6, false, null, true, true);
        var2_4 = var1_1 instanceof DOM2DTMdefaultNamespaceDeclarationNode != false ? var4_6.getAttributeNode(var4_6.getHandleOfNode(((Attr)var1_1).getOwnerElement()), var1_1.getNamespaceURI(), var1_1.getLocalName()) : var4_6.getHandleOfNode((Node)var1_1);
        if (-1 == var2_4) break block8;
        {
            block8 : {
                // MONITOREXIT : this
                return var2_4;
            }
            var1_1 = new RuntimeException(XMLMessages.createXMLMessage("ER_COULD_NOT_RESOLVE_NODE", null));
            throw var1_1;
        }
    }

    @Override
    public int getDTMIdentity(DTM dTM) {
        synchronized (this) {
            block6 : {
                block7 : {
                    if (!(dTM instanceof DTMDefaultBase)) break block6;
                    if (((DTMDefaultBase)(dTM = (DTMDefaultBase)dTM)).getManager() != this) break block7;
                    int n = ((DTMDefaultBase)dTM).getDTMIDs().elementAt(0);
                    return n;
                }
                return -1;
            }
            int n = this.m_dtms.length;
            for (int i = 0; i < n; ++i) {
                int n2;
                if (this.m_dtms[i] != dTM || (n2 = this.m_dtm_offsets[i]) != 0) continue;
                return i << 16;
            }
            return -1;
        }
    }

    public ExpandedNameTable getExpandedNameTable(DTM dTM) {
        return this.m_expandedNameTable;
    }

    public int getFirstFreeDTMID() {
        synchronized (this) {
            int n = this.m_dtms.length;
            for (int i = 1; i < n; ++i) {
                DTM dTM = this.m_dtms[i];
                if (dTM != null) continue;
                return i;
            }
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public XMLReader getXMLReader(Source object) {
        synchronized (this) {
            try {
                try {
                    object = object instanceof SAXSource ? ((SAXSource)object).getXMLReader() : null;
                    Object object2 = object;
                    if (object != null) return object2;
                    if (this.m_readerManager != null) return this.m_readerManager.getXMLReader();
                    this.m_readerManager = XMLReaderManager.getInstance();
                    return this.m_readerManager.getXMLReader();
                }
                catch (SAXException sAXException) {
                    DTMException dTMException = new DTMException(sAXException.getMessage(), sAXException);
                    throw dTMException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean release(DTM dTM, boolean bl) {
        synchronized (this) {
            if (dTM instanceof SAX2DTM) {
                ((SAX2DTM)dTM).clearCoRoutine();
            }
            if (dTM instanceof DTMDefaultBase) {
                SuballocatedIntVector suballocatedIntVector = ((DTMDefaultBase)dTM).getDTMIDs();
                for (int i = suballocatedIntVector.size() - 1; i >= 0; --i) {
                    this.m_dtms[suballocatedIntVector.elementAt((int)i) >>> 16] = null;
                }
            } else {
                int n = this.getDTMIdentity(dTM);
                if (n >= 0) {
                    this.m_dtms[n >>> 16] = null;
                }
            }
            dTM.documentRelease();
            return true;
        }
    }

    public void releaseXMLReader(XMLReader xMLReader) {
        synchronized (this) {
            if (this.m_readerManager != null) {
                this.m_readerManager.releaseXMLReader(xMLReader);
            }
            return;
        }
    }
}

