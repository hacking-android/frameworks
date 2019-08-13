/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.harmony.xml.dom.AttrImpl;
import org.apache.harmony.xml.dom.CDATASectionImpl;
import org.apache.harmony.xml.dom.CommentImpl;
import org.apache.harmony.xml.dom.DOMErrorImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.apache.harmony.xml.dom.ProcessingInstructionImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class DOMConfigurationImpl
implements DOMConfiguration {
    private static final Map<String, Parameter> PARAMETERS = new TreeMap<String, Parameter>(String.CASE_INSENSITIVE_ORDER);
    private boolean cdataSections = true;
    private boolean comments = true;
    private boolean datatypeNormalization = false;
    private boolean entities = true;
    private DOMErrorHandler errorHandler;
    private boolean namespaces = true;
    private String schemaLocation;
    private String schemaType;
    private boolean splitCdataSections = true;
    private boolean validate = false;
    private boolean wellFormed = true;

    static {
        Object object = PARAMETERS;
        Boolean bl = false;
        object.put((String)"canonical-form", (Parameter)new FixedParameter(bl));
        PARAMETERS.put("cdata-sections", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.cdataSections;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.cdataSections = (Boolean)object;
            }
        });
        PARAMETERS.put("check-character-normalization", new FixedParameter(bl));
        PARAMETERS.put("comments", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.comments;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.comments = (Boolean)object;
            }
        });
        PARAMETERS.put("datatype-normalization", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.datatypeNormalization;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                if (((Boolean)object).booleanValue()) {
                    dOMConfigurationImpl.datatypeNormalization = true;
                    dOMConfigurationImpl.validate = true;
                } else {
                    dOMConfigurationImpl.datatypeNormalization = false;
                }
            }
        });
        Map<String, Parameter> map = PARAMETERS;
        object = true;
        map.put("element-content-whitespace", new FixedParameter(object));
        PARAMETERS.put("entities", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.entities;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.entities = (Boolean)object;
            }
        });
        PARAMETERS.put("error-handler", new Parameter(){

            @Override
            public boolean canSet(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                boolean bl = object == null || object instanceof DOMErrorHandler;
                return bl;
            }

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.errorHandler;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.errorHandler = (DOMErrorHandler)object;
            }
        });
        PARAMETERS.put("infoset", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                boolean bl = !dOMConfigurationImpl.entities && !dOMConfigurationImpl.datatypeNormalization && !dOMConfigurationImpl.cdataSections && dOMConfigurationImpl.wellFormed && dOMConfigurationImpl.comments && dOMConfigurationImpl.namespaces;
                return bl;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                if (((Boolean)object).booleanValue()) {
                    dOMConfigurationImpl.entities = false;
                    dOMConfigurationImpl.datatypeNormalization = false;
                    dOMConfigurationImpl.cdataSections = false;
                    dOMConfigurationImpl.wellFormed = true;
                    dOMConfigurationImpl.comments = true;
                    dOMConfigurationImpl.namespaces = true;
                }
            }
        });
        PARAMETERS.put("namespaces", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.namespaces;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.namespaces = (Boolean)object;
            }
        });
        PARAMETERS.put("namespace-declarations", new FixedParameter(object));
        PARAMETERS.put("normalize-characters", new FixedParameter(bl));
        PARAMETERS.put("schema-location", new Parameter(){

            @Override
            public boolean canSet(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                boolean bl = object == null || object instanceof String;
                return bl;
            }

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.schemaLocation;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.schemaLocation = (String)object;
            }
        });
        PARAMETERS.put("schema-type", new Parameter(){

            @Override
            public boolean canSet(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                boolean bl = object == null || object instanceof String;
                return bl;
            }

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.schemaType;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.schemaType = (String)object;
            }
        });
        PARAMETERS.put("split-cdata-sections", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.splitCdataSections;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.splitCdataSections = (Boolean)object;
            }
        });
        PARAMETERS.put("validate", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.validate;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.validate = (Boolean)object;
            }
        });
        PARAMETERS.put("validate-if-schema", new FixedParameter(bl));
        PARAMETERS.put("well-formed", new BooleanParameter(){

            @Override
            public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
                return dOMConfigurationImpl.wellFormed;
            }

            @Override
            public void set(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
                dOMConfigurationImpl.wellFormed = (Boolean)object;
            }
        });
    }

    private void checkTextValidity(CharSequence charSequence) {
        if (this.wellFormed && !this.isValid(charSequence)) {
            this.report((short)2, "wf-invalid-character");
        }
    }

    private static DOMStringList internalGetParameterNames() {
        return new DOMStringList(PARAMETERS.keySet().toArray(new String[PARAMETERS.size()])){
            final /* synthetic */ String[] val$result;
            {
                this.val$result = arrstring;
            }

            @Override
            public boolean contains(String string) {
                return PARAMETERS.containsKey(string);
            }

            @Override
            public int getLength() {
                return this.val$result.length;
            }

            @Override
            public String item(int n) {
                Object object = this.val$result;
                object = n < ((String[])object).length ? object[n] : null;
                return object;
            }
        };
    }

    private boolean isValid(CharSequence charSequence) {
        int n = 0;
        do {
            int n2;
            block5 : {
                int n3;
                char c;
                block6 : {
                    n2 = charSequence.length();
                    n3 = 1;
                    if (n >= n2) break;
                    c = charSequence.charAt(n);
                    n2 = n3;
                    if (c == '\t') break block5;
                    n2 = n3;
                    if (c == '\n') break block5;
                    n2 = n3;
                    if (c == '\r') break block5;
                    if (c < ' ') break block6;
                    n2 = n3;
                    if (c <= '\ud7ff') break block5;
                }
                n2 = c >= '\ue000' && c <= '\ufffd' ? n3 : 0;
            }
            if (n2 == 0) {
                return false;
            }
            ++n;
        } while (true);
        return true;
    }

    private void report(short s, String string) {
        DOMErrorHandler dOMErrorHandler = this.errorHandler;
        if (dOMErrorHandler != null) {
            dOMErrorHandler.handleError(new DOMErrorImpl(s, string));
        }
    }

    @Override
    public boolean canSetParameter(String object, Object object2) {
        boolean bl = (object = PARAMETERS.get(object)) != null && object.canSet(this, object2);
        return bl;
    }

    @Override
    public Object getParameter(String string) throws DOMException {
        Object object = PARAMETERS.get(string);
        if (object != null) {
            return object.get(this);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No such parameter: ");
        ((StringBuilder)object).append(string);
        throw new DOMException(8, ((StringBuilder)object).toString());
    }

    @Override
    public DOMStringList getParameterNames() {
        return DOMConfigurationImpl.internalGetParameterNames();
    }

    public void normalize(Node object) {
        Object object2 = object;
        switch (object.getNodeType()) {
            default: {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unsupported node type ");
                ((StringBuilder)object2).append(object.getNodeType());
                throw new DOMException(9, ((StringBuilder)object2).toString());
            }
            case 8: {
                object = (CommentImpl)object;
                if (!this.comments) {
                    ((LeafNodeImpl)object).getParentNode().removeChild((Node)object);
                    break;
                }
                if (((CommentImpl)object).containsDashDash()) {
                    this.report((short)2, "wf-invalid-character");
                }
                this.checkTextValidity(((CommentImpl)object).buffer);
                break;
            }
            case 7: {
                this.checkTextValidity(((ProcessingInstructionImpl)object).getData());
                break;
            }
            case 5: 
            case 6: 
            case 10: 
            case 12: {
                break;
            }
            case 4: {
                object = (CDATASectionImpl)object;
                if (this.cdataSections) {
                    if (((CDATASectionImpl)object).needsSplitting()) {
                        if (this.splitCdataSections) {
                            ((CDATASectionImpl)object).split();
                            this.report((short)1, "cdata-sections-splitted");
                        } else {
                            this.report((short)2, "wf-invalid-character");
                        }
                    }
                    this.checkTextValidity(((CDATASectionImpl)object).buffer);
                    break;
                }
                object2 = ((CDATASectionImpl)object).replaceWithText();
            }
            case 3: {
                object = ((TextImpl)object2).minimize();
                if (object == null) break;
                this.checkTextValidity(((TextImpl)object).buffer);
                break;
            }
            case 2: {
                this.checkTextValidity(((AttrImpl)object).getValue());
                break;
            }
            case 1: {
                object2 = ((ElementImpl)object).getAttributes();
                for (int i = 0; i < object2.getLength(); ++i) {
                    this.normalize(object2.item(i));
                }
            }
            case 9: 
            case 11: {
                object = object.getFirstChild();
                while (object != null) {
                    object2 = object.getNextSibling();
                    this.normalize((Node)object);
                    object = object2;
                }
                break block0;
            }
        }
    }

    @Override
    public void setParameter(String string, Object object) throws DOMException {
        Parameter parameter = PARAMETERS.get(string);
        if (parameter != null) {
            try {
                parameter.set(this, object);
                return;
            }
            catch (ClassCastException classCastException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type for ");
                stringBuilder.append(string);
                stringBuilder.append(": ");
                stringBuilder.append(object.getClass());
                throw new DOMException(17, stringBuilder.toString());
            }
            catch (NullPointerException nullPointerException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Null not allowed for ");
                stringBuilder.append(string);
                throw new DOMException(17, stringBuilder.toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No such parameter: ");
        ((StringBuilder)object).append(string);
        throw new DOMException(8, ((StringBuilder)object).toString());
    }

    static abstract class BooleanParameter
    implements Parameter {
        BooleanParameter() {
        }

        @Override
        public boolean canSet(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
            return object instanceof Boolean;
        }
    }

    static class FixedParameter
    implements Parameter {
        final Object onlyValue;

        FixedParameter(Object object) {
            this.onlyValue = object;
        }

        @Override
        public boolean canSet(DOMConfigurationImpl dOMConfigurationImpl, Object object) {
            return this.onlyValue.equals(object);
        }

        @Override
        public Object get(DOMConfigurationImpl dOMConfigurationImpl) {
            return this.onlyValue;
        }

        @Override
        public void set(DOMConfigurationImpl object, Object object2) {
            if (this.onlyValue.equals(object2)) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported value: ");
            ((StringBuilder)object).append(object2);
            throw new DOMException(9, ((StringBuilder)object).toString());
        }
    }

    static interface Parameter {
        public boolean canSet(DOMConfigurationImpl var1, Object var2);

        public Object get(DOMConfigurationImpl var1);

        public void set(DOMConfigurationImpl var1, Object var2);
    }

}

