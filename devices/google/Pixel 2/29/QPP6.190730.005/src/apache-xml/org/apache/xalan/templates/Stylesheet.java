/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.DecimalFormatProperties;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.WhiteSpaceInfo;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.StringVector;
import org.apache.xml.utils.SystemIDResolver;
import org.apache.xml.utils.WrappedRuntimeException;

public class Stylesheet
extends ElemTemplateElement
implements Serializable {
    public static final String STYLESHEET_EXT = ".lxc";
    static final long serialVersionUID = 2085337282743043776L;
    Stack m_DecimalFormatDeclarations;
    private StringVector m_ExcludeResultPrefixs;
    private StringVector m_ExtensionElementURIs;
    private String m_Id;
    private Hashtable m_NonXslTopLevel;
    private String m_Version;
    private String m_XmlnsXsl;
    private Vector m_attributeSets;
    private String m_href = null;
    private Vector m_imports;
    private Vector m_includes;
    private boolean m_isCompatibleMode = false;
    private Vector m_keyDeclarations;
    private Vector m_output;
    private Vector m_prefix_aliases;
    private String m_publicId;
    private Stylesheet m_stylesheetParent;
    private StylesheetRoot m_stylesheetRoot;
    private String m_systemId;
    private Vector m_templates;
    private Vector m_topLevelVariables;
    private Vector m_whitespacePreservingElements;
    private Vector m_whitespaceStrippingElements;

    public Stylesheet(Stylesheet stylesheet) {
        if (stylesheet != null) {
            this.m_stylesheetParent = stylesheet;
            this.m_stylesheetRoot = stylesheet.getStylesheetRoot();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, TransformerException {
        try {
            objectInputStream.defaultReadObject();
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new TransformerException(classNotFoundException);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    @Override
    protected boolean accept(XSLTVisitor xSLTVisitor) {
        return xSLTVisitor.visitStylesheet(this);
    }

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        int n;
        Object object;
        int n2 = this.getImportCount();
        for (n = 0; n < n2; ++n) {
            this.getImport(n).callVisitors(xSLTVisitor);
        }
        n2 = this.getIncludeCount();
        for (n = 0; n < n2; ++n) {
            this.getInclude(n).callVisitors(xSLTVisitor);
        }
        n2 = this.getOutputCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getOutput(n));
        }
        n2 = this.getAttributeSetCount();
        for (n = 0; n < n2; ++n) {
            object = this.getAttributeSet(n);
            if (!xSLTVisitor.visitTopLevelInstruction((ElemTemplateElement)object)) continue;
            ((ElemTemplateElement)object).callChildVisitors(xSLTVisitor);
        }
        n2 = this.getDecimalFormatCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getDecimalFormat(n));
        }
        n2 = this.getKeyCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getKey(n));
        }
        n2 = this.getNamespaceAliasCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getNamespaceAlias(n));
        }
        n2 = this.getTemplateCount();
        for (n = 0; n < n2; ++n) {
            try {
                object = this.getTemplate(n);
                if (!xSLTVisitor.visitTopLevelInstruction((ElemTemplateElement)object)) continue;
                ((ElemTemplateElement)object).callChildVisitors(xSLTVisitor);
                continue;
            }
            catch (TransformerException transformerException) {
                throw new WrappedRuntimeException(transformerException);
            }
        }
        n2 = this.getVariableOrParamCount();
        for (n = 0; n < n2; ++n) {
            object = this.getVariableOrParam(n);
            if (!xSLTVisitor.visitTopLevelVariableOrParamDecl((ElemTemplateElement)object)) continue;
            ((ElemTemplateElement)object).callChildVisitors(xSLTVisitor);
        }
        n2 = this.getStripSpaceCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getStripSpace(n));
        }
        n2 = this.getPreserveSpaceCount();
        for (n = 0; n < n2; ++n) {
            xSLTVisitor.visitTopLevelInstruction(this.getPreserveSpace(n));
        }
        object = this.m_NonXslTopLevel;
        if (object != null) {
            object = ((Hashtable)object).elements();
            while (object.hasMoreElements()) {
                ElemTemplateElement elemTemplateElement = (ElemTemplateElement)object.nextElement();
                if (!xSLTVisitor.visitTopLevelInstruction(elemTemplateElement)) continue;
                elemTemplateElement.callChildVisitors(xSLTVisitor);
            }
        }
    }

    @Override
    public boolean containsExcludeResultPrefix(String string, String string2) {
        if (this.m_ExcludeResultPrefixs != null && string2 != null) {
            for (int i = 0; i < this.m_ExcludeResultPrefixs.size(); ++i) {
                if (!string2.equals(this.getNamespaceForPrefix(this.m_ExcludeResultPrefixs.elementAt(i)))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean containsExtensionElementURI(String string) {
        StringVector stringVector = this.m_ExtensionElementURIs;
        if (stringVector == null) {
            return false;
        }
        return stringVector.contains(string);
    }

    public ElemAttributeSet getAttributeSet(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_attributeSets;
        if (vector != null) {
            return (ElemAttributeSet)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getAttributeSetCount() {
        Vector vector = this.m_attributeSets;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public boolean getCompatibleMode() {
        return this.m_isCompatibleMode;
    }

    public DecimalFormatProperties getDecimalFormat(int n) throws ArrayIndexOutOfBoundsException {
        Stack stack = this.m_DecimalFormatDeclarations;
        if (stack != null) {
            return (DecimalFormatProperties)stack.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public DecimalFormatProperties getDecimalFormat(QName qName) {
        if (this.m_DecimalFormatDeclarations == null) {
            return null;
        }
        for (int i = this.getDecimalFormatCount() - 1; i >= 0; ++i) {
            DecimalFormatProperties decimalFormatProperties = this.getDecimalFormat(i);
            if (!decimalFormatProperties.getName().equals(qName)) continue;
            return decimalFormatProperties;
        }
        return null;
    }

    public int getDecimalFormatCount() {
        Stack stack = this.m_DecimalFormatDeclarations;
        int n = stack != null ? stack.size() : 0;
        return n;
    }

    public String getExcludeResultPrefix(int n) throws ArrayIndexOutOfBoundsException {
        StringVector stringVector = this.m_ExcludeResultPrefixs;
        if (stringVector != null) {
            return stringVector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getExcludeResultPrefixCount() {
        StringVector stringVector = this.m_ExcludeResultPrefixs;
        int n = stringVector != null ? stringVector.size() : 0;
        return n;
    }

    public String getExtensionElementPrefix(int n) throws ArrayIndexOutOfBoundsException {
        StringVector stringVector = this.m_ExtensionElementURIs;
        if (stringVector != null) {
            return stringVector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getExtensionElementPrefixCount() {
        StringVector stringVector = this.m_ExtensionElementURIs;
        int n = stringVector != null ? stringVector.size() : 0;
        return n;
    }

    public String getHref() {
        return this.m_href;
    }

    public String getId() {
        return this.m_Id;
    }

    public StylesheetComposed getImport(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_imports;
        if (vector != null) {
            return (StylesheetComposed)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getImportCount() {
        Vector vector = this.m_imports;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public Stylesheet getInclude(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_includes;
        if (vector != null) {
            return (Stylesheet)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getIncludeCount() {
        Vector vector = this.m_includes;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public KeyDeclaration getKey(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_keyDeclarations;
        if (vector != null) {
            return (KeyDeclaration)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getKeyCount() {
        Vector vector = this.m_keyDeclarations;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public NamespaceAlias getNamespaceAlias(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_prefix_aliases;
        if (vector != null) {
            return (NamespaceAlias)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getNamespaceAliasCount() {
        Vector vector = this.m_prefix_aliases;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    @Override
    public String getNodeName() {
        return "stylesheet";
    }

    @Override
    public short getNodeType() {
        return 9;
    }

    public Object getNonXslTopLevel(QName qName) {
        Hashtable hashtable = this.m_NonXslTopLevel;
        qName = hashtable != null ? hashtable.get(qName) : null;
        return qName;
    }

    public OutputProperties getOutput(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_output;
        if (vector != null) {
            return (OutputProperties)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getOutputCount() {
        Vector vector = this.m_output;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public ElemParam getParam(QName qName) {
        if (this.m_topLevelVariables != null) {
            int n = this.getVariableOrParamCount();
            for (int i = 0; i < n; ++i) {
                ElemVariable elemVariable = this.getVariableOrParam(i);
                if (elemVariable.getXSLToken() != 41 || !elemVariable.getName().equals(qName)) continue;
                return (ElemParam)elemVariable;
            }
        }
        return null;
    }

    public WhiteSpaceInfo getPreserveSpace(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_whitespacePreservingElements;
        if (vector != null) {
            return (WhiteSpaceInfo)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getPreserveSpaceCount() {
        Vector vector = this.m_whitespacePreservingElements;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public WhiteSpaceInfo getStripSpace(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_whitespaceStrippingElements;
        if (vector != null) {
            return (WhiteSpaceInfo)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getStripSpaceCount() {
        Vector vector = this.m_whitespaceStrippingElements;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    @Override
    public Stylesheet getStylesheet() {
        return this;
    }

    @Override
    public StylesheetComposed getStylesheetComposed() {
        Stylesheet stylesheet = this;
        while (!stylesheet.isAggregatedType()) {
            stylesheet = stylesheet.getStylesheetParent();
        }
        return (StylesheetComposed)stylesheet;
    }

    public Stylesheet getStylesheetParent() {
        return this.m_stylesheetParent;
    }

    @Override
    public StylesheetRoot getStylesheetRoot() {
        return this.m_stylesheetRoot;
    }

    public ElemTemplate getTemplate(int n) throws TransformerException {
        Vector vector = this.m_templates;
        if (vector != null) {
            return (ElemTemplate)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getTemplateCount() {
        Vector vector = this.m_templates;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public ElemVariable getVariable(QName qName) {
        if (this.m_topLevelVariables != null) {
            int n = this.getVariableOrParamCount();
            for (int i = 0; i < n; ++i) {
                ElemVariable elemVariable = this.getVariableOrParam(i);
                if (elemVariable.getXSLToken() != 73 || !elemVariable.getName().equals(qName)) continue;
                return elemVariable;
            }
        }
        return null;
    }

    public ElemVariable getVariableOrParam(int n) throws ArrayIndexOutOfBoundsException {
        Vector vector = this.m_topLevelVariables;
        if (vector != null) {
            return (ElemVariable)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public ElemVariable getVariableOrParam(QName qName) {
        if (this.m_topLevelVariables != null) {
            int n = this.getVariableOrParamCount();
            for (int i = 0; i < n; ++i) {
                ElemVariable elemVariable = this.getVariableOrParam(i);
                if (!elemVariable.getName().equals(qName)) continue;
                return elemVariable;
            }
        }
        return null;
    }

    public int getVariableOrParamCount() {
        Vector vector = this.m_topLevelVariables;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    public String getVersion() {
        return this.m_Version;
    }

    @Override
    public int getXSLToken() {
        return 25;
    }

    public String getXmlnsXsl() {
        return this.m_XmlnsXsl;
    }

    public boolean isAggregatedType() {
        return false;
    }

    public boolean isRoot() {
        return false;
    }

    public void replaceTemplate(ElemTemplate elemTemplate, int n) throws TransformerException {
        Vector vector = this.m_templates;
        if (vector != null) {
            this.replaceChild(elemTemplate, (ElemTemplateElement)vector.elementAt(n));
            this.m_templates.setElementAt(elemTemplate, n);
            elemTemplate.setStylesheet(this);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void setAttributeSet(ElemAttributeSet elemAttributeSet) {
        if (this.m_attributeSets == null) {
            this.m_attributeSets = new Vector();
        }
        this.m_attributeSets.addElement(elemAttributeSet);
    }

    public void setDecimalFormat(DecimalFormatProperties decimalFormatProperties) {
        if (this.m_DecimalFormatDeclarations == null) {
            this.m_DecimalFormatDeclarations = new Stack();
        }
        this.m_DecimalFormatDeclarations.push(decimalFormatProperties);
    }

    public void setExcludeResultPrefixes(StringVector stringVector) {
        this.m_ExcludeResultPrefixs = stringVector;
    }

    public void setExtensionElementPrefixes(StringVector stringVector) {
        this.m_ExtensionElementURIs = stringVector;
    }

    public void setHref(String string) {
        this.m_href = string;
    }

    public void setId(String string) {
        this.m_Id = string;
    }

    public void setImport(StylesheetComposed stylesheetComposed) {
        if (this.m_imports == null) {
            this.m_imports = new Vector();
        }
        this.m_imports.addElement(stylesheetComposed);
    }

    public void setInclude(Stylesheet stylesheet) {
        if (this.m_includes == null) {
            this.m_includes = new Vector();
        }
        this.m_includes.addElement(stylesheet);
    }

    public void setKey(KeyDeclaration keyDeclaration) {
        if (this.m_keyDeclarations == null) {
            this.m_keyDeclarations = new Vector();
        }
        this.m_keyDeclarations.addElement(keyDeclaration);
    }

    @Override
    public void setLocaterInfo(SourceLocator sourceLocator) {
        if (sourceLocator != null) {
            this.m_publicId = sourceLocator.getPublicId();
            this.m_systemId = sourceLocator.getSystemId();
            String string = this.m_systemId;
            if (string != null) {
                try {
                    this.m_href = SystemIDResolver.getAbsoluteURI(string, null);
                }
                catch (TransformerException transformerException) {
                    // empty catch block
                }
            }
            super.setLocaterInfo(sourceLocator);
        }
    }

    public void setNamespaceAlias(NamespaceAlias namespaceAlias) {
        if (this.m_prefix_aliases == null) {
            this.m_prefix_aliases = new Vector();
        }
        this.m_prefix_aliases.addElement(namespaceAlias);
    }

    public void setNonXslTopLevel(QName qName, Object object) {
        if (this.m_NonXslTopLevel == null) {
            this.m_NonXslTopLevel = new Hashtable();
        }
        this.m_NonXslTopLevel.put(qName, object);
    }

    public void setOutput(OutputProperties outputProperties) {
        if (this.m_output == null) {
            this.m_output = new Vector();
        }
        this.m_output.addElement(outputProperties);
    }

    public void setParam(ElemParam elemParam) {
        this.setVariable(elemParam);
    }

    public void setPreserveSpaces(WhiteSpaceInfo whiteSpaceInfo) {
        if (this.m_whitespacePreservingElements == null) {
            this.m_whitespacePreservingElements = new Vector();
        }
        this.m_whitespacePreservingElements.addElement(whiteSpaceInfo);
    }

    public void setStripSpaces(WhiteSpaceInfo whiteSpaceInfo) {
        if (this.m_whitespaceStrippingElements == null) {
            this.m_whitespaceStrippingElements = new Vector();
        }
        this.m_whitespaceStrippingElements.addElement(whiteSpaceInfo);
    }

    public void setStylesheetParent(Stylesheet stylesheet) {
        this.m_stylesheetParent = stylesheet;
    }

    public void setStylesheetRoot(StylesheetRoot stylesheetRoot) {
        this.m_stylesheetRoot = stylesheetRoot;
    }

    public void setTemplate(ElemTemplate elemTemplate) {
        if (this.m_templates == null) {
            this.m_templates = new Vector();
        }
        this.m_templates.addElement(elemTemplate);
        elemTemplate.setStylesheet(this);
    }

    public void setVariable(ElemVariable elemVariable) {
        if (this.m_topLevelVariables == null) {
            this.m_topLevelVariables = new Vector();
        }
        this.m_topLevelVariables.addElement(elemVariable);
    }

    public void setVersion(String string) {
        this.m_Version = string;
        boolean bl = Double.valueOf(string) > 1.0;
        this.m_isCompatibleMode = bl;
    }

    public void setXmlnsXsl(String string) {
        this.m_XmlnsXsl = string;
    }
}

