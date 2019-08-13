/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class NamespaceMappings {
    private static final String EMPTYSTRING = "";
    private static final String XML_PREFIX = "xml";
    private int count = 0;
    private Hashtable m_namespaces = new Hashtable();
    private Stack m_nodeStack = new Stack();

    public NamespaceMappings() {
        this.initNamespaces();
    }

    private Stack createPrefixStack(String string) {
        Stack stack = new Stack();
        this.m_namespaces.put(string, stack);
        return stack;
    }

    private Stack getPrefixStack(String string) {
        return (Stack)this.m_namespaces.get(string);
    }

    private void initNamespaces() {
        MappingRecord mappingRecord = new MappingRecord(EMPTYSTRING, EMPTYSTRING, -1);
        this.createPrefixStack(EMPTYSTRING).push(mappingRecord);
        mappingRecord = new MappingRecord(XML_PREFIX, "http://www.w3.org/XML/1998/namespace", -1);
        this.createPrefixStack(XML_PREFIX).push(mappingRecord);
    }

    public Object clone() throws CloneNotSupportedException {
        NamespaceMappings namespaceMappings = new NamespaceMappings();
        namespaceMappings.m_nodeStack = (Stack)this.m_nodeStack.clone();
        namespaceMappings.count = this.count;
        namespaceMappings.m_namespaces = (Hashtable)this.m_namespaces.clone();
        namespaceMappings.count = this.count;
        return namespaceMappings;
    }

    public String generateNextPrefix() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ns");
        int n = this.count;
        this.count = n + 1;
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    MappingRecord getMappingFromPrefix(String object) {
        object = (object = (Stack)this.m_namespaces.get(object)) != null && !((Stack)object).isEmpty() ? (MappingRecord)((Stack)object).peek() : null;
        return object;
    }

    MappingRecord getMappingFromURI(String string) {
        MappingRecord mappingRecord;
        MappingRecord mappingRecord2 = null;
        Enumeration enumeration = this.m_namespaces.keys();
        do {
            mappingRecord = mappingRecord2;
        } while (enumeration.hasMoreElements() && ((mappingRecord = this.getMappingFromPrefix((String)enumeration.nextElement())) == null || !mappingRecord.m_uri.equals(string)));
        return mappingRecord;
    }

    public String[] lookupAllPrefixes(String arrstring) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Enumeration enumeration = this.m_namespaces.keys();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = this.lookupNamespace(string);
            if (string2 == null || !string2.equals(arrstring)) continue;
            arrayList.add(string);
        }
        arrstring = new String[arrayList.size()];
        arrayList.toArray(arrstring);
        return arrstring;
    }

    public String lookupNamespace(String string) {
        String string2 = null;
        Stack stack = this.getPrefixStack(string);
        string = string2;
        if (stack != null) {
            string = string2;
            if (!stack.isEmpty()) {
                string = ((MappingRecord)stack.peek()).m_uri;
            }
        }
        string2 = string;
        if (string == null) {
            string2 = EMPTYSTRING;
        }
        return string2;
    }

    public String lookupPrefix(String string) {
        String string2;
        String string3;
        String string4 = null;
        Enumeration enumeration = this.m_namespaces.keys();
        do {
            string2 = string4;
        } while (enumeration.hasMoreElements() && ((string3 = this.lookupNamespace(string2 = (String)enumeration.nextElement())) == null || !string3.equals(string)));
        return string2;
    }

    boolean popNamespace(String object) {
        if (((String)object).startsWith(XML_PREFIX)) {
            return false;
        }
        if ((object = this.getPrefixStack((String)object)) != null) {
            ((Stack)object).pop();
            return true;
        }
        return false;
    }

    void popNamespaces(int n, ContentHandler contentHandler) {
        do {
            String string;
            if (this.m_nodeStack.isEmpty()) {
                return;
            }
            Object object = (MappingRecord)this.m_nodeStack.peek();
            int n2 = ((MappingRecord)object).m_declarationDepth;
            if (n < 1 || ((MappingRecord)object).m_declarationDepth < n) break;
            MappingRecord mappingRecord = (MappingRecord)this.m_nodeStack.pop();
            if (mappingRecord != (MappingRecord)((Stack)(object = this.getPrefixStack(string = ((MappingRecord)object).m_prefix))).peek()) continue;
            ((Stack)object).pop();
            if (contentHandler == null) continue;
            try {
                contentHandler.endPrefixMapping(string);
            }
            catch (SAXException sAXException) {}
        } while (true);
    }

    public boolean pushNamespace(String object, String string, int n) {
        Object object2;
        if (((String)object).startsWith(XML_PREFIX)) {
            return false;
        }
        Object object3 = object2 = (Stack)this.m_namespaces.get(object);
        if (object2 == null) {
            Hashtable hashtable = this.m_namespaces;
            object3 = object2 = new Stack();
            hashtable.put(object, object2);
        }
        if (!((Stack)object3).empty()) {
            object2 = (MappingRecord)((Stack)object3).peek();
            if (string.equals(((MappingRecord)object2).m_uri) || n == ((MappingRecord)object2).m_declarationDepth) {
                return false;
            }
        }
        object = new MappingRecord((String)object, string, n);
        ((Stack)object3).push(object);
        this.m_nodeStack.push(object);
        return true;
    }

    final void reset() {
        this.count = 0;
        this.m_namespaces.clear();
        this.m_nodeStack.clear();
        this.initNamespaces();
    }

    class MappingRecord {
        final int m_declarationDepth;
        final String m_prefix;
        final String m_uri;

        MappingRecord(String string, String string2, int n) {
            this.m_prefix = string;
            NamespaceMappings.this = string2 == null ? NamespaceMappings.EMPTYSTRING : string2;
            this.m_uri = NamespaceMappings.this;
            this.m_declarationDepth = n;
        }
    }

    private class Stack {
        Object[] m_stack = new Object[this.max];
        private int max = 20;
        private int top = -1;

        public void clear() {
            for (int i = 0; i <= this.top; ++i) {
                this.m_stack[i] = null;
            }
            this.top = -1;
        }

        public Object clone() throws CloneNotSupportedException {
            Stack stack = new Stack();
            stack.max = this.max;
            stack.top = this.top;
            stack.m_stack = new Object[stack.max];
            for (int i = 0; i <= this.top; ++i) {
                stack.m_stack[i] = this.m_stack[i];
            }
            return stack;
        }

        public boolean empty() {
            boolean bl = this.top < 0;
            return bl;
        }

        public Object getElement(int n) {
            return this.m_stack[n];
        }

        public boolean isEmpty() {
            boolean bl = this.top < 0;
            return bl;
        }

        public Object peek() {
            int n = this.top;
            Object object = n >= 0 ? this.m_stack[n] : null;
            return object;
        }

        public Object peek(int n) {
            return this.m_stack[n];
        }

        public Object pop() {
            Object object;
            int n = this.top;
            if (n >= 0) {
                object = this.m_stack[n];
                this.top = n - 1;
            } else {
                object = null;
            }
            return object;
        }

        public Object push(Object object) {
            ++this.top;
            int n = this.max;
            if (n <= this.top) {
                int n2 = n * 2 + 1;
                Object[] arrobject = new Object[n2];
                System.arraycopy(this.m_stack, 0, arrobject, 0, n);
                this.max = n2;
                this.m_stack = arrobject;
            }
            this.m_stack[this.top] = object;
            return object;
        }
    }

}

