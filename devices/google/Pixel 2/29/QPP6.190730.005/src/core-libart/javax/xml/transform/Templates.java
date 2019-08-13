/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import java.util.Properties;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

public interface Templates {
    public Properties getOutputProperties();

    public Transformer newTransformer() throws TransformerConfigurationException;
}

