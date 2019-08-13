/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import javax.xml.transform.SourceLocator;
import org.apache.xml.dtm.DTMException;

public class DTMConfigurationException
extends DTMException {
    static final long serialVersionUID = -4607874078818418046L;

    public DTMConfigurationException() {
        super("Configuration Error");
    }

    public DTMConfigurationException(String string) {
        super(string);
    }

    public DTMConfigurationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DTMConfigurationException(String string, SourceLocator sourceLocator) {
        super(string, sourceLocator);
    }

    public DTMConfigurationException(String string, SourceLocator sourceLocator, Throwable throwable) {
        super(string, sourceLocator, throwable);
    }

    public DTMConfigurationException(Throwable throwable) {
        super(throwable);
    }
}

