/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface AttributedCharacterIterator
extends CharacterIterator {
    public Set<Attribute> getAllAttributeKeys();

    public Object getAttribute(Attribute var1);

    public Map<Attribute, Object> getAttributes();

    public int getRunLimit();

    public int getRunLimit(Attribute var1);

    public int getRunLimit(Set<? extends Attribute> var1);

    public int getRunStart();

    public int getRunStart(Attribute var1);

    public int getRunStart(Set<? extends Attribute> var1);

    public static class Attribute
    implements Serializable {
        public static final Attribute INPUT_METHOD_SEGMENT;
        public static final Attribute LANGUAGE;
        public static final Attribute READING;
        private static final Map<String, Attribute> instanceMap;
        private static final long serialVersionUID = -9142742483513960612L;
        private String name;

        static {
            instanceMap = new HashMap<String, Attribute>(7);
            LANGUAGE = new Attribute("language");
            READING = new Attribute("reading");
            INPUT_METHOD_SEGMENT = new Attribute("input_method_segment");
        }

        protected Attribute(String string) {
            this.name = string;
            if (this.getClass() == Attribute.class) {
                instanceMap.put(string, this);
            }
        }

        public final boolean equals(Object object) {
            return super.equals(object);
        }

        protected String getName() {
            return this.name;
        }

        public final int hashCode() {
            return super.hashCode();
        }

        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Attribute.class) {
                Attribute attribute = instanceMap.get(this.getName());
                if (attribute != null) {
                    return attribute;
                }
                throw new InvalidObjectException("unknown attribute name");
            }
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append("(");
            stringBuilder.append(this.name);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

