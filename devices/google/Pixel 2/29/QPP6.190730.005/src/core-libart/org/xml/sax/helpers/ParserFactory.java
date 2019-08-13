/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import org.xml.sax.Parser;
import org.xml.sax.helpers.NewInstance;

@Deprecated
public class ParserFactory {
    private ParserFactory() {
    }

    public static Parser makeParser() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NullPointerException, ClassCastException {
        String string = System.getProperty("org.xml.sax.parser");
        if (string != null) {
            return ParserFactory.makeParser(string);
        }
        throw new NullPointerException("No value for sax.parser property");
    }

    public static Parser makeParser(String string) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ClassCastException {
        return (Parser)NewInstance.newInstance(NewInstance.getClassLoader(), string);
    }
}

