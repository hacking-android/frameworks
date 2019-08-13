/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import java.lang.reflect.Constructor;
import javax.xml.transform.TransformerException;
import org.apache.xalan.extensions.ExtensionHandler;

public class ExtensionNamespaceSupport {
    Object[] m_args = null;
    String m_handlerClass = null;
    String m_namespace = null;
    Class[] m_sig = null;

    public ExtensionNamespaceSupport(String arrobject, String string, Object[] arrobject2) {
        this.m_namespace = arrobject;
        this.m_handlerClass = string;
        this.m_args = arrobject2;
        this.m_sig = new Class[this.m_args.length];
        for (int i = 0; i < (arrobject = this.m_args).length; ++i) {
            if (arrobject[i] != null) {
                this.m_sig[i] = arrobject[i].getClass();
                continue;
            }
            this.m_sig = null;
            break;
        }
    }

    public String getNamespace() {
        return this.m_namespace;
    }

    public ExtensionHandler launch() throws TransformerException {
        Constructor constructor;
        block9 : {
            constructor = ExtensionHandler.getClassForName(this.m_handlerClass);
            Object var2_3 = null;
            if (this.m_sig != null) {
                constructor = ((Class)((Object)constructor)).getConstructor(this.m_sig);
                break block9;
            }
            Constructor<?>[] arrconstructor = ((Class)((Object)constructor)).getConstructors();
            int n = 0;
            do {
                block10 : {
                    constructor = var2_3;
                    if (n >= arrconstructor.length) break;
                    if (arrconstructor[n].getParameterTypes().length != this.m_args.length) break block10;
                    constructor = arrconstructor[n];
                    break;
                }
                ++n;
            } while (true);
        }
        if (constructor != null) {
            constructor = (ExtensionHandler)constructor.newInstance(this.m_args);
            return constructor;
        }
        try {
            constructor = new Constructor("ExtensionHandler constructor not found");
            throw constructor;
        }
        catch (Exception exception) {
            throw new TransformerException(exception);
        }
    }
}

