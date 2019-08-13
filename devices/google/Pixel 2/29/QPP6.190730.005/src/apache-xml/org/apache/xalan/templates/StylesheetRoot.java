/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExtensionNamespacesManager;
import org.apache.xalan.processor.XSLTSchema;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.DecimalFormatProperties;
import org.apache.xalan.templates.ElemApplyTemplates;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemValueOf;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.TemplateList;
import org.apache.xalan.templates.WhiteSpaceInfo;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;

public class StylesheetRoot
extends StylesheetComposed
implements Serializable,
Templates {
    static final long serialVersionUID = 3875353123529147855L;
    private HashMap m_attrSets;
    private HashMap m_availElems;
    private transient ComposeState m_composeState;
    private Hashtable m_decimalFormatSymbols;
    private ElemTemplate m_defaultRootRule;
    private ElemTemplate m_defaultRule;
    private ElemTemplate m_defaultTextRule;
    private transient ExtensionNamespacesManager m_extNsMgr = null;
    private String m_extensionHandlerClass = "org.apache.xalan.extensions.ExtensionHandlerExsltFunction";
    private StylesheetComposed[] m_globalImportList;
    private boolean m_incremental = false;
    private boolean m_isSecureProcessing = false;
    private Vector m_keyDecls;
    private Hashtable m_namespaceAliasComposed;
    private boolean m_optimizer = true;
    private boolean m_outputMethodSet = false;
    private OutputProperties m_outputProperties;
    XPath m_selectDefault;
    private boolean m_source_location = false;
    private ElemTemplate m_startRule;
    private TemplateList m_templateList;
    private Vector m_variables;
    private TemplateList m_whiteSpaceInfoList;

    public StylesheetRoot(ErrorListener errorListener) throws TransformerConfigurationException {
        super(null);
        this.setStylesheetRoot(this);
        try {
            XPath xPath;
            this.m_selectDefault = xPath = new XPath("node()", this, this, 0, errorListener);
            this.initDefaultRule(errorListener);
            return;
        }
        catch (TransformerException transformerException) {
            throw new TransformerConfigurationException(XSLMessages.createMessage("ER_CANNOT_INIT_DEFAULT_TEMPLATES", null), transformerException);
        }
    }

    public StylesheetRoot(XSLTSchema xSLTSchema, ErrorListener errorListener) throws TransformerConfigurationException {
        this(errorListener);
        this.m_availElems = xSLTSchema.getElemsAvailable();
    }

    private void QuickSort2(Vector vector, int n, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n2 > n) {
            ElemTemplateElement elemTemplateElement = (ElemTemplateElement)vector.elementAt((n + n2) / 2);
            while (n3 <= n4) {
                int n5;
                int n6 = n3;
                do {
                    n5 = n4;
                    if (n6 >= n2) break;
                    n5 = n4;
                    if (((ElemTemplateElement)vector.elementAt(n6)).compareTo(elemTemplateElement) >= 0) break;
                    ++n6;
                } while (true);
                while (n5 > n && ((ElemTemplateElement)vector.elementAt(n5)).compareTo(elemTemplateElement) > 0) {
                    --n5;
                }
                n3 = n6;
                n4 = n5;
                if (n6 > n5) continue;
                ElemTemplateElement elemTemplateElement2 = (ElemTemplateElement)vector.elementAt(n6);
                vector.setElementAt(vector.elementAt(n5), n6);
                vector.setElementAt(elemTemplateElement2, n5);
                n3 = n6 + 1;
                n4 = n5 - 1;
            }
            if (n < n4) {
                this.QuickSort2(vector, n, n4);
            }
            if (n3 < n2) {
                this.QuickSort2(vector, n3, n2);
            }
        }
    }

    private void clearComposeState() {
        this.m_composeState = null;
    }

    private void initDefaultRule(ErrorListener object) throws TransformerException {
        this.m_defaultRule = new ElemTemplate();
        this.m_defaultRule.setStylesheet(this);
        Serializable serializable = new XPath("*", this, this, 1, (ErrorListener)object);
        this.m_defaultRule.setMatch((XPath)serializable);
        serializable = new ElemApplyTemplates();
        ((ElemApplyTemplates)serializable).setIsDefaultTemplate(true);
        ((ElemForEach)serializable).setSelect(this.m_selectDefault);
        this.m_defaultRule.appendChild((ElemTemplateElement)serializable);
        this.m_startRule = this.m_defaultRule;
        this.m_defaultTextRule = new ElemTemplate();
        this.m_defaultTextRule.setStylesheet(this);
        serializable = new XPath("text() | @*", this, this, 1, (ErrorListener)object);
        this.m_defaultTextRule.setMatch((XPath)serializable);
        serializable = new ElemValueOf();
        this.m_defaultTextRule.appendChild((ElemTemplateElement)serializable);
        ((ElemValueOf)serializable).setSelect(new XPath(".", this, this, 0, (ErrorListener)object));
        this.m_defaultRootRule = new ElemTemplate();
        this.m_defaultRootRule.setStylesheet(this);
        object = new XPath("/", this, this, 1, (ErrorListener)object);
        this.m_defaultRootRule.setMatch((XPath)object);
        object = new ElemApplyTemplates();
        ((ElemApplyTemplates)object).setIsDefaultTemplate(true);
        this.m_defaultRootRule.appendChild((ElemTemplateElement)object);
        ((ElemForEach)object).setSelect(this.m_selectDefault);
    }

    protected void addImports(Stylesheet stylesheet, boolean bl, Vector vector) {
        int n;
        int n2 = stylesheet.getImportCount();
        if (n2 > 0) {
            for (n = 0; n < n2; ++n) {
                this.addImports(stylesheet.getImport(n), true, vector);
            }
        }
        if ((n2 = stylesheet.getIncludeCount()) > 0) {
            for (n = 0; n < n2; ++n) {
                this.addImports(stylesheet.getInclude(n), false, vector);
            }
        }
        if (bl) {
            vector.addElement(stylesheet);
        }
    }

    @Override
    public boolean canStripWhiteSpace() {
        boolean bl = this.m_whiteSpaceInfoList != null;
        return bl;
    }

    void composeTemplates(ElemTemplateElement elemTemplateElement) throws TransformerException {
        elemTemplateElement.compose(this);
        for (ElemTemplateElement elemTemplateElement2 = elemTemplateElement.getFirstChildElem(); elemTemplateElement2 != null; elemTemplateElement2 = elemTemplateElement2.getNextSiblingElem()) {
            this.composeTemplates(elemTemplateElement2);
        }
        elemTemplateElement.endCompose(this);
    }

    public ArrayList getAttributeSetComposed(QName qName) throws ArrayIndexOutOfBoundsException {
        return (ArrayList)this.m_attrSets.get(qName);
    }

    public HashMap getAvailableElements() {
        return this.m_availElems;
    }

    ComposeState getComposeState() {
        return this.m_composeState;
    }

    public DecimalFormatSymbols getDecimalFormatComposed(QName qName) {
        return (DecimalFormatSymbols)this.m_decimalFormatSymbols.get(qName);
    }

    public Properties getDefaultOutputProps() {
        return this.m_outputProperties.getProperties();
    }

    public final ElemTemplate getDefaultRootRule() {
        return this.m_defaultRootRule;
    }

    public final ElemTemplate getDefaultRule() {
        return this.m_defaultRule;
    }

    public final ElemTemplate getDefaultTextRule() {
        return this.m_defaultTextRule;
    }

    public String getExtensionHandlerClass() {
        return this.m_extensionHandlerClass;
    }

    public ExtensionNamespacesManager getExtensionNamespacesManager() {
        if (this.m_extNsMgr == null) {
            this.m_extNsMgr = new ExtensionNamespacesManager();
        }
        return this.m_extNsMgr;
    }

    public Vector getExtensions() {
        Object object = this.m_extNsMgr;
        object = object != null ? ((ExtensionNamespacesManager)object).getExtensions() : null;
        return object;
    }

    public StylesheetComposed getGlobalImport(int n) {
        return this.m_globalImportList[n];
    }

    public int getGlobalImportCount() {
        StylesheetComposed[] arrstylesheetComposed = this.m_globalImportList;
        int n = arrstylesheetComposed != null ? arrstylesheetComposed.length : 1;
        return n;
    }

    public int getImportNumber(StylesheetComposed stylesheetComposed) {
        if (this == stylesheetComposed) {
            return 0;
        }
        int n = this.getGlobalImportCount();
        for (int i = 0; i < n; ++i) {
            if (stylesheetComposed != this.getGlobalImport(i)) continue;
            return i;
        }
        return -1;
    }

    public boolean getIncremental() {
        return this.m_incremental;
    }

    public Vector getKeysComposed() {
        return this.m_keyDecls;
    }

    public NamespaceAlias getNamespaceAliasComposed(String string) {
        Hashtable hashtable = this.m_namespaceAliasComposed;
        string = hashtable == null ? null : hashtable.get(string);
        return (NamespaceAlias)((Object)string);
    }

    public boolean getOptimizer() {
        return this.m_optimizer;
    }

    public OutputProperties getOutputComposed() {
        return this.m_outputProperties;
    }

    @Override
    public Properties getOutputProperties() {
        return (Properties)this.getDefaultOutputProps().clone();
    }

    public boolean getSource_location() {
        return this.m_source_location;
    }

    public final ElemTemplate getStartRule() {
        return this.m_startRule;
    }

    public ElemTemplate getTemplateComposed(QName qName) {
        return this.m_templateList.getTemplate(qName);
    }

    public ElemTemplate getTemplateComposed(XPathContext xPathContext, int n, QName qName, int n2, int n3, boolean bl, DTM dTM) throws TransformerException {
        return this.m_templateList.getTemplate(xPathContext, n, qName, n2, n3, bl, dTM);
    }

    public ElemTemplate getTemplateComposed(XPathContext xPathContext, int n, QName qName, boolean bl, DTM dTM) throws TransformerException {
        return this.m_templateList.getTemplate(xPathContext, n, qName, bl, dTM);
    }

    public final TemplateList getTemplateListComposed() {
        return this.m_templateList;
    }

    public ElemVariable getVariableOrParamComposed(QName qName) {
        Serializable serializable = this.m_variables;
        if (serializable != null) {
            int n = ((Vector)serializable).size();
            for (int i = 0; i < n; ++i) {
                serializable = (ElemVariable)this.m_variables.elementAt(i);
                if (!((ElemVariable)serializable).getName().equals(qName)) continue;
                return serializable;
            }
        }
        return null;
    }

    public Vector getVariablesAndParamsComposed() {
        return this.m_variables;
    }

    public WhiteSpaceInfo getWhiteSpaceInfo(XPathContext xPathContext, int n, DTM dTM) throws TransformerException {
        TemplateList templateList = this.m_whiteSpaceInfoList;
        if (templateList != null) {
            return (WhiteSpaceInfo)templateList.getTemplate(xPathContext, n, null, false, dTM);
        }
        return null;
    }

    void initComposeState() {
        this.m_composeState = new ComposeState();
    }

    public boolean isOutputMethodSet() {
        return this.m_outputMethodSet;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    public boolean isSecureProcessing() {
        return this.m_isSecureProcessing;
    }

    @Override
    public Transformer newTransformer() {
        return new TransformerImpl(this);
    }

    public void recompose() throws TransformerException {
        int n;
        int n2;
        Object object = new Vector();
        if (this.m_globalImportList == null) {
            Vector vector = new Vector();
            this.addImports(this, true, vector);
            this.m_globalImportList = new StylesheetComposed[vector.size()];
            n = 0;
            n2 = vector.size() - 1;
            while (n < vector.size()) {
                this.m_globalImportList[n2] = (StylesheetComposed)vector.elementAt(n);
                StylesheetComposed[] arrstylesheetComposed = this.m_globalImportList;
                arrstylesheetComposed[n2].recomposeIncludes(arrstylesheetComposed[n2]);
                this.m_globalImportList[n2].recomposeImports();
                ++n;
                --n2;
            }
        }
        n = this.getGlobalImportCount();
        for (n2 = 0; n2 < n; ++n2) {
            this.getGlobalImport(n2).recompose((Vector)object);
        }
        this.QuickSort2((Vector)object, 0, ((Vector)object).size() - 1);
        this.m_outputProperties = new OutputProperties("");
        this.m_attrSets = new HashMap();
        this.m_decimalFormatSymbols = new Hashtable();
        this.m_keyDecls = new Vector();
        this.m_namespaceAliasComposed = new Hashtable();
        this.m_templateList = new TemplateList();
        this.m_variables = new Vector();
        for (n2 = object.size() - 1; n2 >= 0; --n2) {
            ((ElemTemplateElement)((Vector)object).elementAt(n2)).recompose(this);
        }
        this.initComposeState();
        this.m_templateList.compose(this);
        this.m_outputProperties.compose(this);
        this.m_outputProperties.endCompose(this);
        int n3 = this.getGlobalImportCount();
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.getGlobalImport(n2);
            int n4 = ((StylesheetComposed)object).getIncludeCountComposed();
            for (n = -1; n < n4; ++n) {
                this.composeTemplates(((StylesheetComposed)object).getIncludeComposed(n));
            }
        }
        object = this.m_extNsMgr;
        if (object != null) {
            ((ExtensionNamespacesManager)object).registerUnregisteredNamespaces();
        }
        this.clearComposeState();
    }

    void recomposeAttributeSets(ElemAttributeSet elemAttributeSet) {
        ArrayList<ElemAttributeSet> arrayList;
        ArrayList<ElemAttributeSet> arrayList2 = arrayList = (ArrayList<ElemAttributeSet>)this.m_attrSets.get(elemAttributeSet.getName());
        if (arrayList == null) {
            arrayList2 = new ArrayList<ElemAttributeSet>();
            this.m_attrSets.put(elemAttributeSet.getName(), arrayList2);
        }
        arrayList2.add(elemAttributeSet);
    }

    void recomposeDecimalFormats(DecimalFormatProperties object) {
        DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)this.m_decimalFormatSymbols.get(((DecimalFormatProperties)object).getName());
        if (decimalFormatSymbols == null) {
            this.m_decimalFormatSymbols.put(((DecimalFormatProperties)object).getName(), ((DecimalFormatProperties)object).getDecimalFormatSymbols());
        } else if (!((DecimalFormatProperties)object).getDecimalFormatSymbols().equals(decimalFormatSymbols)) {
            object = ((DecimalFormatProperties)object).getName().equals(new QName("")) ? XSLMessages.createWarning("WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", new Object[0]) : XSLMessages.createWarning("WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", new Object[]{((DecimalFormatProperties)object).getName()});
            this.error((String)object);
        }
    }

    void recomposeKeys(KeyDeclaration keyDeclaration) {
        this.m_keyDecls.addElement(keyDeclaration);
    }

    void recomposeNamespaceAliases(NamespaceAlias namespaceAlias) {
        this.m_namespaceAliasComposed.put(namespaceAlias.getStylesheetNamespace(), namespaceAlias);
    }

    void recomposeOutput(OutputProperties outputProperties) throws TransformerException {
        this.m_outputProperties.copyFrom(outputProperties);
    }

    void recomposeTemplates(ElemTemplate elemTemplate) {
        this.m_templateList.setTemplate(elemTemplate);
    }

    void recomposeVariables(ElemVariable elemVariable) {
        if (this.getVariableOrParamComposed(elemVariable.getName()) == null) {
            elemVariable.setIsTopLevel(true);
            elemVariable.setIndex(this.m_variables.size());
            this.m_variables.addElement(elemVariable);
        }
    }

    void recomposeWhiteSpaceInfo(WhiteSpaceInfo whiteSpaceInfo) {
        if (this.m_whiteSpaceInfoList == null) {
            this.m_whiteSpaceInfoList = new TemplateList();
        }
        this.m_whiteSpaceInfoList.setTemplate(whiteSpaceInfo);
    }

    public String setExtensionHandlerClass(String string) {
        String string2 = this.m_extensionHandlerClass;
        this.m_extensionHandlerClass = string;
        return string2;
    }

    public void setIncremental(boolean bl) {
        this.m_incremental = bl;
    }

    public void setOptimizer(boolean bl) {
        this.m_optimizer = bl;
    }

    public void setSecureProcessing(boolean bl) {
        this.m_isSecureProcessing = bl;
    }

    public void setSource_location(boolean bl) {
        this.m_source_location = bl;
    }

    public final void setTemplateListComposed(TemplateList templateList) {
        this.m_templateList = templateList;
    }

    public boolean shouldCheckWhitespace() {
        boolean bl = this.m_whiteSpaceInfoList != null;
        return bl;
    }

    public boolean shouldStripWhiteSpace(XPathContext xPathContext, int n) throws TransformerException {
        if (this.m_whiteSpaceInfoList != null) {
            while (-1 != n) {
                DTM dTM = xPathContext.getDTM(n);
                WhiteSpaceInfo whiteSpaceInfo = (WhiteSpaceInfo)this.m_whiteSpaceInfoList.getTemplate(xPathContext, n, null, false, dTM);
                if (whiteSpaceInfo != null) {
                    return whiteSpaceInfo.getShouldStripSpace();
                }
                if (-1 != (n = dTM.getParent(n)) && 1 == dTM.getNodeType(n)) continue;
                n = -1;
            }
        }
        return false;
    }

    class ComposeState {
        private ExpandedNameTable m_ent = new ExpandedNameTable();
        IntStack m_marks = new IntStack();
        private int m_maxStackFrameSize;
        private Vector m_variableNames = new Vector();

        ComposeState() {
            int n = StylesheetRoot.this.m_variables.size();
            for (int i = 0; i < n; ++i) {
                ElemVariable elemVariable = (ElemVariable)StylesheetRoot.this.m_variables.elementAt(i);
                this.m_variableNames.addElement(elemVariable.getName());
            }
        }

        int addVariableName(QName qName) {
            int n = this.m_variableNames.size();
            this.m_variableNames.addElement(qName);
            int n2 = this.m_variableNames.size();
            int n3 = this.getGlobalsSize();
            int n4 = this.m_maxStackFrameSize;
            if (n2 - n3 > n4) {
                this.m_maxStackFrameSize = n4 + 1;
            }
            return n;
        }

        int getCurrentStackFrameSize() {
            return this.m_variableNames.size();
        }

        int getFrameSize() {
            return this.m_maxStackFrameSize;
        }

        int getGlobalsSize() {
            return StylesheetRoot.this.m_variables.size();
        }

        public int getQNameID(QName qName) {
            return this.m_ent.getExpandedTypeID(qName.getNamespace(), qName.getLocalName(), 1);
        }

        Vector getVariableNames() {
            return this.m_variableNames;
        }

        void popStackMark() {
            this.setCurrentStackFrameSize(this.m_marks.pop());
        }

        void pushStackMark() {
            this.m_marks.push(this.getCurrentStackFrameSize());
        }

        void resetStackFrameSize() {
            this.m_maxStackFrameSize = 0;
        }

        void setCurrentStackFrameSize(int n) {
            this.m_variableNames.setSize(n);
        }
    }

}

