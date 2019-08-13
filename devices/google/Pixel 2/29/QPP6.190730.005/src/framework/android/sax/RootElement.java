/*
 * Decompiled with CFR 0.145.
 */
package android.sax;

import android.sax.BadXmlException;
import android.sax.Children;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RootElement
extends Element {
    final Handler handler = new Handler();

    public RootElement(String string2) {
        this("", string2);
    }

    public RootElement(String string2, String string3) {
        super(null, string2, string3, 0);
    }

    public ContentHandler getContentHandler() {
        return this.handler;
    }

    class Handler
    extends DefaultHandler {
        StringBuilder bodyBuilder = null;
        Element current = null;
        int depth = -1;
        Locator locator;

        Handler() {
        }

        @Override
        public void characters(char[] arrc, int n, int n2) throws SAXException {
            StringBuilder stringBuilder = this.bodyBuilder;
            if (stringBuilder != null) {
                stringBuilder.append(arrc, n, n2);
            }
        }

        @Override
        public void endElement(String object, String charSequence, String string2) throws SAXException {
            object = this.current;
            if (this.depth == ((Element)object).depth) {
                ((Element)object).checkRequiredChildren(this.locator);
                if (((Element)object).endElementListener != null) {
                    ((Element)object).endElementListener.end();
                }
                if ((charSequence = this.bodyBuilder) != null) {
                    charSequence = ((StringBuilder)charSequence).toString();
                    this.bodyBuilder = null;
                    ((Element)object).endTextElementListener.end((String)charSequence);
                }
                this.current = ((Element)object).parent;
            }
            --this.depth;
        }

        @Override
        public void setDocumentLocator(Locator locator) {
            this.locator = locator;
        }

        void start(Element element, Attributes attributes) {
            this.current = element;
            if (element.startElementListener != null) {
                element.startElementListener.start(attributes);
            }
            if (element.endTextElementListener != null) {
                this.bodyBuilder = new StringBuilder();
            }
            element.resetRequiredChildren();
            element.visited = true;
        }

        @Override
        public void startElement(String object, String string2, String object2, Attributes attributes) throws SAXException {
            int n;
            this.depth = n = this.depth + 1;
            if (n == 0) {
                this.startRoot((String)object, string2, attributes);
                return;
            }
            if (this.bodyBuilder == null) {
                if (n == this.current.depth + 1 && (object2 = this.current.children) != null && (object = ((Children)object2).get((String)object, string2)) != null) {
                    this.start((Element)object, attributes);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Encountered mixed content within text element named ");
            ((StringBuilder)object).append(this.current);
            ((StringBuilder)object).append(".");
            throw new BadXmlException(((StringBuilder)object).toString(), this.locator);
        }

        void startRoot(String string2, String string3, Attributes object) throws SAXException {
            RootElement rootElement = RootElement.this;
            if (rootElement.uri.compareTo(string2) == 0 && rootElement.localName.compareTo(string3) == 0) {
                this.start(rootElement, (Attributes)object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Root element name does not match. Expected: ");
            ((StringBuilder)object).append(rootElement);
            ((StringBuilder)object).append(", Got: ");
            ((StringBuilder)object).append(Element.toString(string2, string3));
            throw new BadXmlException(((StringBuilder)object).toString(), this.locator);
        }
    }

}

