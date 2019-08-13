/*
 * Decompiled with CFR 0.145.
 */
package android.sax;

import android.sax.BadXmlException;
import android.sax.Children;
import android.sax.ElementListener;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;
import android.sax.TextElementListener;
import java.io.Serializable;
import java.util.ArrayList;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

public class Element {
    Children children;
    final int depth;
    EndElementListener endElementListener;
    EndTextElementListener endTextElementListener;
    final String localName;
    final Element parent;
    ArrayList<Element> requiredChilden;
    StartElementListener startElementListener;
    final String uri;
    boolean visited;

    Element(Element element, String string2, String string3, int n) {
        this.parent = element;
        this.uri = string2;
        this.localName = string3;
        this.depth = n;
    }

    static String toString(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        if (string2.equals("")) {
            string2 = string3;
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string2);
            stringBuilder2.append(":");
            stringBuilder2.append(string3);
            string2 = stringBuilder2.toString();
        }
        stringBuilder.append(string2);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    void checkRequiredChildren(Locator locator) throws SAXParseException {
        Serializable serializable = this.requiredChilden;
        if (serializable != null) {
            for (int i = serializable.size() - 1; i >= 0; --i) {
                Element element = ((ArrayList)serializable).get(i);
                if (element.visited) {
                    continue;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Element named ");
                ((StringBuilder)serializable).append(this);
                ((StringBuilder)serializable).append(" is missing required child element named ");
                ((StringBuilder)serializable).append(element);
                ((StringBuilder)serializable).append(".");
                throw new BadXmlException(((StringBuilder)serializable).toString(), locator);
            }
        }
    }

    public Element getChild(String string2) {
        return this.getChild("", string2);
    }

    public Element getChild(String string2, String string3) {
        if (this.endTextElementListener == null) {
            if (this.children == null) {
                this.children = new Children();
            }
            return this.children.getOrCreate(this, string2, string3);
        }
        throw new IllegalStateException("This element already has an end text element listener. It cannot have children.");
    }

    public Element requireChild(String string2) {
        return this.requireChild("", string2);
    }

    public Element requireChild(String arrayList, String object) {
        object = this.getChild((String)((Object)arrayList), (String)object);
        arrayList = this.requiredChilden;
        if (arrayList == null) {
            this.requiredChilden = new ArrayList();
            this.requiredChilden.add((Element)object);
        } else if (!arrayList.contains(object)) {
            this.requiredChilden.add((Element)object);
        }
        return object;
    }

    void resetRequiredChildren() {
        ArrayList<Element> arrayList = this.requiredChilden;
        if (arrayList != null) {
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                arrayList.get((int)i).visited = false;
            }
        }
    }

    public void setElementListener(ElementListener elementListener) {
        this.setStartElementListener(elementListener);
        this.setEndElementListener(elementListener);
    }

    public void setEndElementListener(EndElementListener endElementListener) {
        if (this.endElementListener == null) {
            this.endElementListener = endElementListener;
            return;
        }
        throw new IllegalStateException("End element listener has already been set.");
    }

    public void setEndTextElementListener(EndTextElementListener endTextElementListener) {
        if (this.endTextElementListener == null) {
            if (this.children == null) {
                this.endTextElementListener = endTextElementListener;
                return;
            }
            throw new IllegalStateException("This element already has children. It cannot have an end text element listener.");
        }
        throw new IllegalStateException("End text element listener has already been set.");
    }

    public void setStartElementListener(StartElementListener startElementListener) {
        if (this.startElementListener == null) {
            this.startElementListener = startElementListener;
            return;
        }
        throw new IllegalStateException("Start element listener has already been set.");
    }

    public void setTextElementListener(TextElementListener textElementListener) {
        this.setStartElementListener(textElementListener);
        this.setEndTextElementListener(textElementListener);
    }

    public String toString() {
        return Element.toString(this.uri, this.localName);
    }
}

