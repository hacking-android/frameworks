/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.templates.AVTPart;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.XSLTVisitable;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathContext;

public class AVT
implements Serializable,
XSLTVisitable {
    private static final int INIT_BUFFER_CHUNK_BITS = 8;
    private static final boolean USE_OBJECT_POOL = false;
    static final long serialVersionUID = 5167607155517042691L;
    private String m_name;
    private Vector m_parts;
    private String m_rawName;
    private String m_simpleString;
    private String m_uri;

    /*
     * Exception decompiling
     */
    public AVT(StylesheetHandler var1_1, String var2_6, String var3_8, String var4_9, String var5_10, ElemTemplateElement var6_11) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [18[WHILELOOP]], but top level block is 4[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    private final FastStringBuffer getBuffer() {
        return new FastStringBuffer(8);
    }

    @Override
    public void callVisitors(XSLTVisitor xSLTVisitor) {
        Vector vector;
        if (xSLTVisitor.visitAVT(this) && (vector = this.m_parts) != null) {
            int n = vector.size();
            for (int i = 0; i < n; ++i) {
                ((AVTPart)this.m_parts.elementAt(i)).callVisitors(xSLTVisitor);
            }
        }
    }

    public boolean canTraverseOutsideSubtree() {
        Vector vector = this.m_parts;
        if (vector != null) {
            int n = vector.size();
            for (int i = 0; i < n; ++i) {
                if (!((AVTPart)this.m_parts.elementAt(i)).canTraverseOutsideSubtree()) continue;
                return true;
            }
        }
        return false;
    }

    public String evaluate(XPathContext object, int n, PrefixResolver prefixResolver) throws TransformerException {
        Object object2 = this.m_simpleString;
        if (object2 != null) {
            return object2;
        }
        if (this.m_parts != null) {
            object2 = this.getBuffer();
            int n2 = this.m_parts.size();
            for (int i = 0; i < n2; ++i) {
                ((AVTPart)this.m_parts.elementAt(i)).evaluate((XPathContext)object, (FastStringBuffer)object2, n, prefixResolver);
            }
            try {
                object = ((FastStringBuffer)object2).toString();
                return object;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                ((FastStringBuffer)object2).setLength(0);
            }
        }
        return "";
    }

    public void fixupVariables(Vector vector, int n) {
        Vector vector2 = this.m_parts;
        if (vector2 != null) {
            int n2 = vector2.size();
            for (int i = 0; i < n2; ++i) {
                ((AVTPart)this.m_parts.elementAt(i)).fixupVariables(vector, n);
            }
        }
    }

    public String getName() {
        return this.m_name;
    }

    public String getRawName() {
        return this.m_rawName;
    }

    public String getSimpleString() {
        Object object = this.m_simpleString;
        if (object != null) {
            return object;
        }
        if (this.m_parts != null) {
            object = this.getBuffer();
            int n = this.m_parts.size();
            for (int i = 0; i < n; ++i) {
                ((FastStringBuffer)object).append(((AVTPart)this.m_parts.elementAt(i)).getSimpleString());
            }
            try {
                String string = ((FastStringBuffer)object).toString();
                return string;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                ((FastStringBuffer)object).setLength(0);
            }
        }
        return "";
    }

    public String getURI() {
        return this.m_uri;
    }

    public boolean isContextInsensitive() {
        boolean bl = this.m_simpleString != null;
        return bl;
    }

    public boolean isSimple() {
        boolean bl = this.m_simpleString != null;
        return bl;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void setRawName(String string) {
        this.m_rawName = string;
    }

    public void setURI(String string) {
        this.m_uri = string;
    }
}

