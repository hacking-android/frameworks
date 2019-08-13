/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import java.util.Vector;
import org.apache.xalan.extensions.ExtensionHandler;
import org.apache.xalan.extensions.ExtensionNamespaceSupport;

public class ExtensionNamespacesManager {
    private Vector m_extensions = new Vector();
    private Vector m_predefExtensions = new Vector(7);
    private Vector m_unregisteredExtensions = new Vector();

    public ExtensionNamespacesManager() {
        this.setPredefinedNamespaces();
    }

    private void setPredefinedNamespaces() {
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xalan/java", "org.apache.xalan.extensions.ExtensionHandlerJavaPackage", new Object[]{"http://xml.apache.org/xalan/java", "javapackage", ""}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xslt/java", "org.apache.xalan.extensions.ExtensionHandlerJavaPackage", new Object[]{"http://xml.apache.org/xslt/java", "javapackage", ""}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xsl.lotus.com/java", "org.apache.xalan.extensions.ExtensionHandlerJavaPackage", new Object[]{"http://xsl.lotus.com/java", "javapackage", ""}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xalan", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://xml.apache.org/xalan", "javaclass", "org.apache.xalan.lib.Extensions"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xslt", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://xml.apache.org/xslt", "javaclass", "org.apache.xalan.lib.Extensions"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xalan/redirect", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://xml.apache.org/xalan/redirect", "javaclass", "org.apache.xalan.lib.Redirect"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xalan/PipeDocument", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://xml.apache.org/xalan/PipeDocument", "javaclass", "org.apache.xalan.lib.PipeDocument"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://xml.apache.org/xalan/sql", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://xml.apache.org/xalan/sql", "javaclass", "org.apache.xalan.lib.sql.XConnection"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/common", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/common", "javaclass", "org.apache.xalan.lib.ExsltCommon"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/math", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/math", "javaclass", "org.apache.xalan.lib.ExsltMath"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/sets", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/sets", "javaclass", "org.apache.xalan.lib.ExsltSets"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/dates-and-times", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/dates-and-times", "javaclass", "org.apache.xalan.lib.ExsltDatetime"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/dynamic", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/dynamic", "javaclass", "org.apache.xalan.lib.ExsltDynamic"}));
        this.m_predefExtensions.add(new ExtensionNamespaceSupport("http://exslt.org/strings", "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{"http://exslt.org/strings", "javaclass", "org.apache.xalan.lib.ExsltStrings"}));
    }

    public ExtensionNamespaceSupport defineJavaNamespace(String string) {
        return this.defineJavaNamespace(string, string);
    }

    public ExtensionNamespaceSupport defineJavaNamespace(String string, String object) {
        if (string != null && string.trim().length() != 0) {
            String string2 = object;
            object = string2;
            if (string2.startsWith("class:")) {
                object = string2.substring(6);
            }
            int n = ((String)object).lastIndexOf("/");
            string2 = object;
            if (-1 != n) {
                string2 = ((String)object).substring(n + 1);
            }
            if (string2 != null && string2.trim().length() != 0) {
                try {
                    ExtensionHandler.getClassForName(string2);
                    object = new ExtensionNamespaceSupport(string, "org.apache.xalan.extensions.ExtensionHandlerJavaClass", new Object[]{string, "javaclass", string2});
                    return object;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append(".");
                    return new ExtensionNamespaceSupport(string, "org.apache.xalan.extensions.ExtensionHandlerJavaPackage", new Object[]{string, "javapackage", stringBuilder.toString()});
                }
            }
            return null;
        }
        return null;
    }

    public Vector getExtensions() {
        return this.m_extensions;
    }

    public int namespaceIndex(String string, Vector vector) {
        for (int i = 0; i < vector.size(); ++i) {
            if (!((ExtensionNamespaceSupport)vector.get(i)).getNamespace().equals(string)) continue;
            return i;
        }
        return -1;
    }

    public void registerExtension(String string) {
        if (this.namespaceIndex(string, this.m_extensions) == -1) {
            int n = this.namespaceIndex(string, this.m_predefExtensions);
            if (n != -1) {
                this.m_extensions.add(this.m_predefExtensions.get(n));
            } else if (!this.m_unregisteredExtensions.contains(string)) {
                this.m_unregisteredExtensions.add(string);
            }
        }
    }

    public void registerExtension(ExtensionNamespaceSupport extensionNamespaceSupport) {
        String string = extensionNamespaceSupport.getNamespace();
        if (this.namespaceIndex(string, this.m_extensions) == -1) {
            this.m_extensions.add(extensionNamespaceSupport);
            if (this.m_unregisteredExtensions.contains(string)) {
                this.m_unregisteredExtensions.remove(string);
            }
        }
    }

    public void registerUnregisteredNamespaces() {
        for (int i = 0; i < this.m_unregisteredExtensions.size(); ++i) {
            ExtensionNamespaceSupport extensionNamespaceSupport = this.defineJavaNamespace((String)this.m_unregisteredExtensions.get(i));
            if (extensionNamespaceSupport == null) continue;
            this.m_extensions.add(extensionNamespaceSupport);
        }
    }
}

