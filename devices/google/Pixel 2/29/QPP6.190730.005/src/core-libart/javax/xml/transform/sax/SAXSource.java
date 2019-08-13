/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.sax;

import java.io.InputStream;
import java.io.Reader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class SAXSource
implements Source {
    public static final String FEATURE = "http://javax.xml.transform.sax.SAXSource/feature";
    private InputSource inputSource;
    private XMLReader reader;

    public SAXSource() {
    }

    public SAXSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    public SAXSource(XMLReader xMLReader, InputSource inputSource) {
        this.reader = xMLReader;
        this.inputSource = inputSource;
    }

    public static InputSource sourceToInputSource(Source source) {
        if (source instanceof SAXSource) {
            return ((SAXSource)source).getInputSource();
        }
        if (source instanceof StreamSource) {
            source = (StreamSource)source;
            InputSource inputSource = new InputSource(((StreamSource)source).getSystemId());
            inputSource.setByteStream(((StreamSource)source).getInputStream());
            inputSource.setCharacterStream(((StreamSource)source).getReader());
            inputSource.setPublicId(((StreamSource)source).getPublicId());
            return inputSource;
        }
        return null;
    }

    public InputSource getInputSource() {
        return this.inputSource;
    }

    @Override
    public String getSystemId() {
        InputSource inputSource = this.inputSource;
        if (inputSource == null) {
            return null;
        }
        return inputSource.getSystemId();
    }

    public XMLReader getXMLReader() {
        return this.reader;
    }

    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    @Override
    public void setSystemId(String string) {
        InputSource inputSource = this.inputSource;
        if (inputSource == null) {
            this.inputSource = new InputSource(string);
        } else {
            inputSource.setSystemId(string);
        }
    }

    public void setXMLReader(XMLReader xMLReader) {
        this.reader = xMLReader;
    }
}

