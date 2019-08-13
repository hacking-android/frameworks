/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref.sax2dtm;

import java.util.Vector;
import javax.xml.transform.Source;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.IntVector;
import org.apache.xml.utils.StringVector;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLStringFactory;
import org.xml.sax.SAXException;

public class SAX2RTFDTM
extends SAX2DTM {
    private static final boolean DEBUG = false;
    private int m_currentDocumentNode = -1;
    int m_emptyCharsCount;
    int m_emptyDataCount;
    int m_emptyDataQNCount;
    int m_emptyNSDeclSetCount;
    int m_emptyNSDeclSetElemsCount;
    int m_emptyNodeCount;
    IntStack mark_char_size = new IntStack();
    IntStack mark_data_size = new IntStack();
    IntStack mark_doq_size = new IntStack();
    IntStack mark_nsdeclelem_size = new IntStack();
    IntStack mark_nsdeclset_size = new IntStack();
    IntStack mark_size = new IntStack();

    public SAX2RTFDTM(DTMManager object, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        super((DTMManager)object, source, n, dTMWSFilter, xMLStringFactory, bl);
        int n2 = 0;
        bl = this.m_useSourceLocationProperty = false;
        source = null;
        object = bl ? new StringVector() : null;
        this.m_sourceSystemId = object;
        object = this.m_useSourceLocationProperty ? new IntVector() : null;
        this.m_sourceLine = object;
        object = source;
        if (this.m_useSourceLocationProperty) {
            object = new IntVector();
        }
        this.m_sourceColumn = object;
        this.m_emptyNodeCount = this.m_size;
        n = this.m_namespaceDeclSets == null ? 0 : this.m_namespaceDeclSets.size();
        this.m_emptyNSDeclSetCount = n;
        n = this.m_namespaceDeclSetElements == null ? n2 : this.m_namespaceDeclSetElements.size();
        this.m_emptyNSDeclSetElemsCount = n;
        this.m_emptyDataCount = this.m_data.size();
        this.m_emptyCharsCount = this.m_chars.size();
        this.m_emptyDataQNCount = this.m_dataOrQName.size();
    }

    protected int _documentRoot(int n) {
        if (n == -1) {
            return -1;
        }
        int n2 = this._parent(n);
        int n3 = n;
        n = n2;
        while (n != -1) {
            n3 = n;
            n = this._parent(n3);
        }
        return n3;
    }

    @Override
    public void endDocument() throws SAXException {
        this.charactersFlush();
        this.m_nextsib.setElementAt(-1, this.m_currentDocumentNode);
        if (this.m_firstch.elementAt(this.m_currentDocumentNode) == -2) {
            this.m_firstch.setElementAt(-1, this.m_currentDocumentNode);
        }
        if (-1 != this.m_previous) {
            this.m_nextsib.setElementAt(-1, this.m_previous);
        }
        this.m_parents = null;
        this.m_prefixMappings = null;
        this.m_contextIndexes = null;
        this.m_currentDocumentNode = -1;
        this.m_endDocumentOccured = true;
    }

    @Override
    public int getDocument() {
        return this.makeNodeHandle(this.m_currentDocumentNode);
    }

    @Override
    public int getDocumentRoot(int n) {
        n = this.makeNodeIdentity(n);
        while (n != -1) {
            if (this._type(n) == 9) {
                return this.makeNodeHandle(n);
            }
            n = this._parent(n);
        }
        return -1;
    }

    public boolean isTreeIncomplete() {
        return this.m_endDocumentOccured ^ true;
    }

    public boolean popRewindMark() {
        boolean bl = this.mark_size.empty();
        int n = bl ? this.m_emptyNodeCount : this.mark_size.pop();
        this.m_size = n;
        this.m_exptype.setSize(this.m_size);
        this.m_firstch.setSize(this.m_size);
        this.m_nextsib.setSize(this.m_size);
        this.m_prevsib.setSize(this.m_size);
        this.m_parent.setSize(this.m_size);
        this.m_elemIndexes = null;
        n = bl ? this.m_emptyNSDeclSetCount : this.mark_nsdeclset_size.pop();
        if (this.m_namespaceDeclSets != null) {
            this.m_namespaceDeclSets.setSize(n);
        }
        n = bl ? this.m_emptyNSDeclSetElemsCount : this.mark_nsdeclelem_size.pop();
        if (this.m_namespaceDeclSetElements != null) {
            this.m_namespaceDeclSetElements.setSize(n);
        }
        Object object = this.m_data;
        n = bl ? this.m_emptyDataCount : this.mark_data_size.pop();
        ((SuballocatedIntVector)object).setSize(n);
        object = this.m_chars;
        n = bl ? this.m_emptyCharsCount : this.mark_char_size.pop();
        ((FastStringBuffer)object).setLength(n);
        object = this.m_dataOrQName;
        n = bl ? this.m_emptyDataQNCount : this.mark_doq_size.pop();
        ((SuballocatedIntVector)object).setSize(n);
        bl = this.m_size == 0;
        return bl;
    }

    public void pushRewindMark() {
        if (!this.m_indexing && this.m_elemIndexes == null) {
            this.mark_size.push(this.m_size);
            IntStack intStack = this.mark_nsdeclset_size;
            Cloneable cloneable = this.m_namespaceDeclSets;
            int n = 0;
            int n2 = cloneable == null ? 0 : this.m_namespaceDeclSets.size();
            intStack.push(n2);
            cloneable = this.mark_nsdeclelem_size;
            n2 = this.m_namespaceDeclSetElements == null ? n : this.m_namespaceDeclSetElements.size();
            ((IntStack)cloneable).push(n2);
            this.mark_data_size.push(this.m_data.size());
            this.mark_char_size.push(this.m_chars.size());
            this.mark_doq_size.push(this.m_dataOrQName.size());
            return;
        }
        throw new NullPointerException("Coding error; Don't try to mark/rewind an indexed DTM");
    }

    @Override
    public void startDocument() throws SAXException {
        this.m_endDocumentOccured = false;
        this.m_prefixMappings = new Vector();
        this.m_contextIndexes = new IntStack();
        this.m_parents = new IntStack();
        this.m_currentDocumentNode = this.m_size;
        super.startDocument();
    }
}

