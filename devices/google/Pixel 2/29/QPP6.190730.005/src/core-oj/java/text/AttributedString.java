/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.io.Serializable;
import java.text.Annotation;
import java.text.AttributeEntry;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class AttributedString {
    private static final int ARRAY_SIZE_INCREMENT = 10;
    int runArraySize;
    Vector<Object>[] runAttributeValues;
    Vector<AttributedCharacterIterator.Attribute>[] runAttributes;
    int runCount;
    int[] runStarts;
    String text;

    public AttributedString(String string) {
        if (string != null) {
            this.text = string;
            return;
        }
        throw new NullPointerException();
    }

    public AttributedString(String object, Map<? extends AttributedCharacterIterator.Attribute, ?> object2) {
        if (object != null && object2 != null) {
            this.text = object;
            if (((String)((Object)object)).length() == 0) {
                if (object2.isEmpty()) {
                    return;
                }
                throw new IllegalArgumentException("Can't add attribute to 0-length text");
            }
            int n = object2.size();
            if (n > 0) {
                this.createRunAttributeDataVectors();
                object = new Vector<AttributedCharacterIterator.Attribute>(n);
                Vector vector = new Vector(n);
                this.runAttributes[0] = object;
                this.runAttributeValues[0] = vector;
                for (Map.Entry entry : object2.entrySet()) {
                    object.addElement((AttributedCharacterIterator.Attribute)entry.getKey());
                    vector.addElement(entry.getValue());
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    public AttributedString(AttributedCharacterIterator attributedCharacterIterator) {
        this(attributedCharacterIterator, attributedCharacterIterator.getBeginIndex(), attributedCharacterIterator.getEndIndex(), null);
    }

    public AttributedString(AttributedCharacterIterator attributedCharacterIterator, int n, int n2) {
        this(attributedCharacterIterator, n, n2, null);
    }

    public AttributedString(AttributedCharacterIterator attributedCharacterIterator, int n, int n2, AttributedCharacterIterator.Attribute[] object) {
        if (attributedCharacterIterator != null) {
            int n3 = attributedCharacterIterator.getBeginIndex();
            int n4 = attributedCharacterIterator.getEndIndex();
            if (n >= n3 && n2 <= n4 && n <= n2) {
                Serializable serializable = new StringBuffer();
                attributedCharacterIterator.setIndex(n);
                int n5 = n4 = (int)attributedCharacterIterator.current();
                while (attributedCharacterIterator.getIndex() < n2) {
                    ((StringBuffer)serializable).append((char)n5);
                    n5 = n4 = attributedCharacterIterator.next();
                }
                this.text = ((StringBuffer)serializable).toString();
                if (n == n2) {
                    return;
                }
                serializable = new HashSet();
                if (object == null) {
                    ((AbstractCollection)((Object)serializable)).addAll(attributedCharacterIterator.getAllAttributeKeys());
                } else {
                    for (n4 = 0; n4 < ((AttributedCharacterIterator.Attribute[])object).length; ++n4) {
                        ((HashSet)serializable).add(object[n4]);
                    }
                    ((AbstractCollection)((Object)serializable)).retainAll(attributedCharacterIterator.getAllAttributeKeys());
                }
                if (((HashSet)serializable).isEmpty()) {
                    return;
                }
                object = ((HashSet)serializable).iterator();
                block2 : while (object.hasNext()) {
                    serializable = (AttributedCharacterIterator.Attribute)object.next();
                    attributedCharacterIterator.setIndex(n3);
                    while (attributedCharacterIterator.getIndex() < n2) {
                        n4 = attributedCharacterIterator.getRunStart((AttributedCharacterIterator.Attribute)serializable);
                        int n6 = attributedCharacterIterator.getRunLimit((AttributedCharacterIterator.Attribute)serializable);
                        Object object2 = attributedCharacterIterator.getAttribute((AttributedCharacterIterator.Attribute)serializable);
                        int n7 = n6;
                        if (object2 != null) {
                            if (object2 instanceof Annotation) {
                                if (n4 >= n && n6 <= n2) {
                                    this.addAttribute((AttributedCharacterIterator.Attribute)serializable, object2, n4 - n, n6 - n);
                                    n7 = n6;
                                } else {
                                    n7 = n6;
                                    if (n6 > n2) {
                                        continue block2;
                                    }
                                }
                            } else {
                                if (n4 >= n2) continue block2;
                                n7 = n6;
                                if (n6 > n) {
                                    int n8 = n4;
                                    if (n4 < n) {
                                        n8 = n;
                                    }
                                    n4 = n6;
                                    if (n6 > n2) {
                                        n4 = n2;
                                    }
                                    n7 = n4;
                                    if (n8 != n4) {
                                        this.addAttribute((AttributedCharacterIterator.Attribute)serializable, object2, n8 - n, n4 - n);
                                        n7 = n4;
                                    }
                                }
                            }
                        }
                        attributedCharacterIterator.setIndex(n7);
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Invalid substring range");
        }
        throw new NullPointerException();
    }

    AttributedString(AttributedCharacterIterator[] arrattributedCharacterIterator) {
        if (arrattributedCharacterIterator != null) {
            if (arrattributedCharacterIterator.length == 0) {
                this.text = "";
            } else {
                int n;
                Object object = new StringBuffer();
                for (n = 0; n < arrattributedCharacterIterator.length; ++n) {
                    this.appendContents((StringBuffer)object, arrattributedCharacterIterator[n]);
                }
                this.text = ((StringBuffer)object).toString();
                if (this.text.length() > 0) {
                    int n2 = 0;
                    object = null;
                    for (n = 0; n < arrattributedCharacterIterator.length; ++n) {
                        AttributedCharacterIterator attributedCharacterIterator = arrattributedCharacterIterator[n];
                        int n3 = attributedCharacterIterator.getBeginIndex();
                        int n4 = attributedCharacterIterator.getEndIndex();
                        int n5 = n3;
                        while (n5 < n4) {
                            attributedCharacterIterator.setIndex(n5);
                            Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
                            if (AttributedString.mapsDiffer(object, map)) {
                                this.setAttributes(map, n5 - n3 + n2);
                            }
                            object = map;
                            n5 = attributedCharacterIterator.getRunLimit();
                        }
                        n2 += n4 - n3;
                    }
                }
            }
            return;
        }
        throw new NullPointerException("Iterators must not be null");
    }

    private void addAttributeImpl(AttributedCharacterIterator.Attribute attribute, Object object, int n, int n2) {
        synchronized (this) {
            if (this.runCount == 0) {
                this.createRunAttributeDataVectors();
            }
            this.addAttributeRunData(attribute, object, this.ensureRunBreak(n), this.ensureRunBreak(n2));
            return;
        }
    }

    private void addAttributeRunData(AttributedCharacterIterator.Attribute attribute, Object object, int n, int n2) {
        while (n < n2) {
            int n3 = -1;
            Object object2 = this.runAttributes;
            if (object2[n] == null) {
                Vector vector = new Vector();
                object2 = new Vector();
                this.runAttributes[n] = vector;
                this.runAttributeValues[n] = object2;
            } else {
                n3 = object2[n].indexOf(attribute);
            }
            if (n3 == -1) {
                n3 = this.runAttributes[n].size();
                this.runAttributes[n].addElement(attribute);
                try {
                    this.runAttributeValues[n].addElement(object);
                }
                catch (Exception exception) {
                    this.runAttributes[n].setSize(n3);
                    this.runAttributeValues[n].setSize(n3);
                }
            } else {
                this.runAttributeValues[n].set(n3, object);
            }
            ++n;
        }
    }

    private final void appendContents(StringBuffer stringBuffer, CharacterIterator characterIterator) {
        int n = characterIterator.getEndIndex();
        for (int i = characterIterator.getBeginIndex(); i < n; ++i) {
            characterIterator.setIndex(i);
            stringBuffer.append(characterIterator.current());
        }
    }

    private boolean attributeValuesMatch(Set<? extends AttributedCharacterIterator.Attribute> object, int n, int n2) {
        Iterator<? extends AttributedCharacterIterator.Attribute> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (AttributedString.valuesMatch(this.getAttribute((AttributedCharacterIterator.Attribute)object, n), this.getAttribute((AttributedCharacterIterator.Attribute)object, n2))) continue;
            return false;
        }
        return true;
    }

    private char charAt(int n) {
        return this.text.charAt(n);
    }

    private final void createRunAttributeDataVectors() {
        int[] arrn = new int[10];
        Vector[] arrvector = new Vector[10];
        Vector[] arrvector2 = new Vector[10];
        this.runStarts = arrn;
        this.runAttributes = arrvector;
        this.runAttributeValues = arrvector2;
        this.runArraySize = 10;
        this.runCount = 1;
    }

    private final int ensureRunBreak(int n) {
        return this.ensureRunBreak(n, true);
    }

    private final int ensureRunBreak(int n, boolean bl) {
        Object object;
        int n2;
        Object object2;
        Vector[] arrvector;
        Object object3;
        if (n == this.length()) {
            return this.runCount;
        }
        for (n2 = 0; n2 < this.runCount && this.runStarts[n2] < n; ++n2) {
        }
        if (n2 < this.runCount && this.runStarts[n2] == n) {
            return n2;
        }
        int n3 = this.runCount;
        int n4 = this.runArraySize;
        if (n3 == n4) {
            object2 = new int[n4 += 10];
            object = new Vector[n4];
            object3 = new Vector[n4];
            for (n3 = 0; n3 < this.runArraySize; ++n3) {
                object2[n3] = this.runStarts[n3];
                object[n3] = this.runAttributes[n3];
                object3[n3] = this.runAttributeValues[n3];
            }
            this.runStarts = object2;
            this.runAttributes = object;
            this.runAttributeValues = object3;
            this.runArraySize = n4;
        }
        object2 = null;
        object = null;
        object3 = arrvector = null;
        if (bl) {
            object2 = this.runAttributes[n2 - 1];
            Vector<Object> vector = this.runAttributeValues[n2 - 1];
            if (object2 != null) {
                object = new Vector(object2);
            }
            object2 = object;
            object3 = arrvector;
            if (vector != null) {
                object3 = new Vector<Object>(vector);
                object2 = object;
            }
        }
        ++this.runCount;
        for (n3 = this.runCount - 1; n3 > n2; --n3) {
            object = this.runStarts;
            object[n3] = object[n3 - 1];
            object = this.runAttributes;
            object[n3] = object[n3 - 1];
            object = this.runAttributeValues;
            object[n3] = object[n3 - 1];
        }
        this.runStarts[n2] = n;
        this.runAttributes[n2] = object2;
        this.runAttributeValues[n2] = object3;
        return n2;
    }

    private Object getAttribute(AttributedCharacterIterator.Attribute object, int n) {
        synchronized (this) {
            block6 : {
                Vector<AttributedCharacterIterator.Attribute> vector;
                Vector<Object> vector2;
                block5 : {
                    vector = this.runAttributes[n];
                    vector2 = this.runAttributeValues[n];
                    if (vector != null) break block5;
                    return null;
                }
                n = vector.indexOf(object);
                if (n == -1) break block6;
                object = vector2.elementAt(n);
                return object;
            }
            return null;
        }
    }

    private Object getAttributeCheckRange(AttributedCharacterIterator.Attribute attribute, int n, int n2, int n3) {
        Object object = this.getAttribute(attribute, n);
        if (object instanceof Annotation) {
            int n4;
            if (n2 > 0) {
                n4 = n;
                int n5 = this.runStarts[n4];
                while (n5 >= n2 && AttributedString.valuesMatch(object, this.getAttribute(attribute, n4 - 1))) {
                    n5 = this.runStarts[--n4];
                }
                if (n5 < n2) {
                    return null;
                }
            }
            if (n3 < (n2 = this.length())) {
                n4 = n;
                n = n4 < this.runCount - 1 ? this.runStarts[n4 + 1] : n2;
                while (n <= n3 && AttributedString.valuesMatch(object, this.getAttribute(attribute, n4 + 1))) {
                    if (++n4 < this.runCount - 1) {
                        n = this.runStarts[n4 + 1];
                        continue;
                    }
                    n = n2;
                }
                if (n > n3) {
                    return null;
                }
            }
        }
        return object;
    }

    private static <K, V> boolean mapsDiffer(Map<K, V> map, Map<K, V> map2) {
        boolean bl = true;
        if (map == null) {
            if (map2 == null || map2.size() <= 0) {
                bl = false;
            }
            return bl;
        }
        return true ^ map.equals(map2);
    }

    private void setAttributes(Map<AttributedCharacterIterator.Attribute, Object> object2, int n) {
        int n2;
        if (this.runCount == 0) {
            this.createRunAttributeDataVectors();
        }
        int n3 = this.ensureRunBreak(n2, false);
        if (object2 != null && (n2 = object2.size()) > 0) {
            Vector<AttributedCharacterIterator.Attribute> vector = new Vector<AttributedCharacterIterator.Attribute>(n2);
            Vector vector2 = new Vector(n2);
            for (Map.Entry entry : object2.entrySet()) {
                vector.add((AttributedCharacterIterator.Attribute)entry.getKey());
                vector2.add(entry.getValue());
            }
            this.runAttributes[n3] = vector;
            this.runAttributeValues[n3] = vector2;
        }
    }

    private static final boolean valuesMatch(Object object, Object object2) {
        if (object == null) {
            boolean bl = object2 == null;
            return bl;
        }
        return object.equals(object2);
    }

    public void addAttribute(AttributedCharacterIterator.Attribute attribute, Object object) {
        if (attribute != null) {
            int n = this.length();
            if (n != 0) {
                this.addAttributeImpl(attribute, object, 0, n);
                return;
            }
            throw new IllegalArgumentException("Can't add attribute to 0-length text");
        }
        throw new NullPointerException();
    }

    public void addAttribute(AttributedCharacterIterator.Attribute attribute, Object object, int n, int n2) {
        if (attribute != null) {
            if (n >= 0 && n2 <= this.length() && n < n2) {
                this.addAttributeImpl(attribute, object, n, n2);
                return;
            }
            throw new IllegalArgumentException("Invalid substring range");
        }
        throw new NullPointerException();
    }

    public void addAttributes(Map<? extends AttributedCharacterIterator.Attribute, ?> object, int n, int n2) {
        if (object != null) {
            if (n >= 0 && n2 <= this.length() && n <= n2) {
                if (n == n2) {
                    if (object.isEmpty()) {
                        return;
                    }
                    throw new IllegalArgumentException("Can't add attribute to 0-length text");
                }
                if (this.runCount == 0) {
                    this.createRunAttributeDataVectors();
                }
                n = this.ensureRunBreak(n);
                n2 = this.ensureRunBreak(n2);
                for (Map.Entry entry : object.entrySet()) {
                    this.addAttributeRunData((AttributedCharacterIterator.Attribute)entry.getKey(), entry.getValue(), n, n2);
                }
                return;
            }
            throw new IllegalArgumentException("Invalid substring range");
        }
        throw new NullPointerException();
    }

    public AttributedCharacterIterator getIterator() {
        return this.getIterator(null, 0, this.length());
    }

    public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] arrattribute) {
        return this.getIterator(arrattribute, 0, this.length());
    }

    public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] arrattribute, int n, int n2) {
        return new AttributedStringIterator(arrattribute, n, n2);
    }

    int length() {
        return this.text.length();
    }

    private final class AttributeMap
    extends AbstractMap<AttributedCharacterIterator.Attribute, Object> {
        int beginIndex;
        int endIndex;
        int runIndex;

        AttributeMap(int n, int n2, int n3) {
            this.runIndex = n;
            this.beginIndex = n2;
            this.endIndex = n3;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Set<Map.Entry<AttributedCharacterIterator.Attribute, Object>> entrySet() {
            HashSet<Map.Entry<AttributedCharacterIterator.Attribute, Object>> hashSet = new HashSet<Map.Entry<AttributedCharacterIterator.Attribute, Object>>();
            AttributedString attributedString = AttributedString.this;
            synchronized (attributedString) {
                int n = AttributedString.this.runAttributes[this.runIndex].size();
                int n2 = 0;
                do {
                    block9 : {
                        Object object;
                        AttributedCharacterIterator.Attribute attribute;
                        Object object2;
                        block8 : {
                            if (n2 >= n) {
                                return hashSet;
                            }
                            attribute = AttributedString.this.runAttributes[this.runIndex].get(n2);
                            object = object2 = AttributedString.this.runAttributeValues[this.runIndex].get(n2);
                            if (!(object2 instanceof Annotation)) break block8;
                            object = object2 = AttributedString.this.getAttributeCheckRange(attribute, this.runIndex, this.beginIndex, this.endIndex);
                            if (object2 == null) break block9;
                        }
                        object2 = new AttributeEntry(attribute, object);
                        hashSet.add((Map.Entry<AttributedCharacterIterator.Attribute, Object>)object2);
                    }
                    ++n2;
                } while (true);
            }
        }

        @Override
        public Object get(Object object) {
            return AttributedString.this.getAttributeCheckRange((AttributedCharacterIterator.Attribute)object, this.runIndex, this.beginIndex, this.endIndex);
        }
    }

    private final class AttributedStringIterator
    implements AttributedCharacterIterator {
        private int beginIndex;
        private int currentIndex;
        private int currentRunIndex;
        private int currentRunLimit;
        private int currentRunStart;
        private int endIndex;
        private AttributedCharacterIterator.Attribute[] relevantAttributes;

        AttributedStringIterator(AttributedCharacterIterator.Attribute[] arrattribute, int n, int n2) {
            if (n >= 0 && n <= n2 && n2 <= AttributedString.this.length()) {
                this.beginIndex = n;
                this.endIndex = n2;
                this.currentIndex = n;
                this.updateRunInfo();
                if (arrattribute != null) {
                    this.relevantAttributes = (AttributedCharacterIterator.Attribute[])arrattribute.clone();
                }
                return;
            }
            throw new IllegalArgumentException("Invalid substring range");
        }

        private AttributedString getString() {
            return AttributedString.this;
        }

        private char internalSetIndex(int n) {
            this.currentIndex = n;
            if (n < this.currentRunStart || n >= this.currentRunLimit) {
                this.updateRunInfo();
            }
            if (this.currentIndex == this.endIndex) {
                return '\uffff';
            }
            return AttributedString.this.charAt(n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void updateRunInfo() {
            int n = this.currentIndex;
            int n2 = this.endIndex;
            if (n == n2) {
                this.currentRunLimit = n2;
                this.currentRunStart = n2;
                this.currentRunIndex = -1;
                return;
            }
            AttributedString attributedString = AttributedString.this;
            synchronized (attributedString) {
                for (n2 = -1; n2 < AttributedString.this.runCount - 1 && AttributedString.this.runStarts[n2 + 1] <= this.currentIndex; ++n2) {
                }
                this.currentRunIndex = n2;
                if (n2 >= 0) {
                    this.currentRunStart = AttributedString.this.runStarts[n2];
                    if (this.currentRunStart < this.beginIndex) {
                        this.currentRunStart = this.beginIndex;
                    }
                } else {
                    this.currentRunStart = this.beginIndex;
                }
                if (n2 < AttributedString.this.runCount - 1) {
                    this.currentRunLimit = AttributedString.this.runStarts[n2 + 1];
                    if (this.currentRunLimit <= this.endIndex) return;
                    this.currentRunLimit = this.endIndex;
                } else {
                    this.currentRunLimit = this.endIndex;
                }
                return;
            }
        }

        @Override
        public Object clone() {
            try {
                AttributedStringIterator attributedStringIterator = (AttributedStringIterator)super.clone();
                return attributedStringIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError(cloneNotSupportedException);
            }
        }

        @Override
        public char current() {
            int n = this.currentIndex;
            if (n == this.endIndex) {
                return '\uffff';
            }
            return AttributedString.this.charAt(n);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof AttributedStringIterator)) {
                return false;
            }
            if (AttributedString.this != AttributedStringIterator.super.getString()) {
                return false;
            }
            return this.currentIndex == ((AttributedStringIterator)object).currentIndex && this.beginIndex == ((AttributedStringIterator)object).beginIndex && this.endIndex == ((AttributedStringIterator)object).endIndex;
            {
            }
        }

        @Override
        public char first() {
            return this.internalSetIndex(this.beginIndex);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Set<AttributedCharacterIterator.Attribute> getAllAttributeKeys() {
            if (AttributedString.this.runAttributes == null) {
                return new HashSet<AttributedCharacterIterator.Attribute>();
            }
            AttributedString attributedString = AttributedString.this;
            synchronized (attributedString) {
                HashSet<AttributedCharacterIterator.Attribute> hashSet = new HashSet<AttributedCharacterIterator.Attribute>();
                int n = 0;
                while (n < AttributedString.this.runCount) {
                    Vector<AttributedCharacterIterator.Attribute> vector;
                    if (AttributedString.this.runStarts[n] < this.endIndex && (n == AttributedString.this.runCount - 1 || AttributedString.this.runStarts[n + 1] > this.beginIndex) && (vector = AttributedString.this.runAttributes[n]) != null) {
                        int n2 = vector.size();
                        do {
                            int n3 = n2 - 1;
                            if (n2 <= 0) break;
                            hashSet.add(vector.get(n3));
                            n2 = n3;
                        } while (true);
                    }
                    ++n;
                }
                return hashSet;
            }
        }

        @Override
        public Object getAttribute(AttributedCharacterIterator.Attribute attribute) {
            int n = this.currentRunIndex;
            if (n < 0) {
                return null;
            }
            return AttributedString.this.getAttributeCheckRange(attribute, n, this.beginIndex, this.endIndex);
        }

        @Override
        public Map<AttributedCharacterIterator.Attribute, Object> getAttributes() {
            Vector<AttributedCharacterIterator.Attribute>[] arrvector;
            int n;
            if (AttributedString.this.runAttributes != null && this.currentRunIndex != -1 && (arrvector = AttributedString.this.runAttributes)[n = this.currentRunIndex] != null) {
                return new AttributeMap(n, this.beginIndex, this.endIndex);
            }
            return new Hashtable<AttributedCharacterIterator.Attribute, Object>();
        }

        @Override
        public int getBeginIndex() {
            return this.beginIndex;
        }

        @Override
        public int getEndIndex() {
            return this.endIndex;
        }

        @Override
        public int getIndex() {
            return this.currentIndex;
        }

        @Override
        public int getRunLimit() {
            return this.currentRunLimit;
        }

        @Override
        public int getRunLimit(AttributedCharacterIterator.Attribute attribute) {
            if (this.currentRunLimit != this.endIndex && this.currentRunIndex != -1) {
                Object object = this.getAttribute(attribute);
                int n = this.currentRunLimit;
                int n2 = this.currentRunIndex;
                while (n < this.endIndex && AttributedString.valuesMatch(object, AttributedString.this.getAttribute(attribute, n2 + 1))) {
                    if (++n2 < AttributedString.this.runCount - 1) {
                        n = AttributedString.this.runStarts[n2 + 1];
                        continue;
                    }
                    n = this.endIndex;
                }
                n2 = n;
                if (n > this.endIndex) {
                    n2 = this.endIndex;
                }
                return n2;
            }
            return this.currentRunLimit;
        }

        @Override
        public int getRunLimit(Set<? extends AttributedCharacterIterator.Attribute> set) {
            if (this.currentRunLimit != this.endIndex && this.currentRunIndex != -1) {
                int n = this.currentRunLimit;
                int n2 = this.currentRunIndex;
                while (n < this.endIndex && AttributedString.this.attributeValuesMatch(set, this.currentRunIndex, n2 + 1)) {
                    if (++n2 < AttributedString.this.runCount - 1) {
                        n = AttributedString.this.runStarts[n2 + 1];
                        continue;
                    }
                    n = this.endIndex;
                }
                n2 = n;
                if (n > this.endIndex) {
                    n2 = this.endIndex;
                }
                return n2;
            }
            return this.currentRunLimit;
        }

        @Override
        public int getRunStart() {
            return this.currentRunStart;
        }

        @Override
        public int getRunStart(AttributedCharacterIterator.Attribute attribute) {
            if (this.currentRunStart != this.beginIndex && this.currentRunIndex != -1) {
                Object object = this.getAttribute(attribute);
                int n = this.currentRunStart;
                int n2 = this.currentRunIndex;
                while (n > this.beginIndex && AttributedString.valuesMatch(object, AttributedString.this.getAttribute(attribute, n2 - 1))) {
                    n = AttributedString.this.runStarts[--n2];
                }
                n2 = n;
                if (n < this.beginIndex) {
                    n2 = this.beginIndex;
                }
                return n2;
            }
            return this.currentRunStart;
        }

        @Override
        public int getRunStart(Set<? extends AttributedCharacterIterator.Attribute> set) {
            if (this.currentRunStart != this.beginIndex && this.currentRunIndex != -1) {
                int n = this.currentRunStart;
                int n2 = this.currentRunIndex;
                while (n > this.beginIndex && AttributedString.this.attributeValuesMatch(set, this.currentRunIndex, n2 - 1)) {
                    n = AttributedString.this.runStarts[--n2];
                }
                n2 = n;
                if (n < this.beginIndex) {
                    n2 = this.beginIndex;
                }
                return n2;
            }
            return this.currentRunStart;
        }

        public int hashCode() {
            return AttributedString.this.text.hashCode() ^ this.currentIndex ^ this.beginIndex ^ this.endIndex;
        }

        @Override
        public char last() {
            int n = this.endIndex;
            if (n == this.beginIndex) {
                return this.internalSetIndex(n);
            }
            return this.internalSetIndex(n - 1);
        }

        @Override
        public char next() {
            int n = this.currentIndex;
            if (n < this.endIndex) {
                return this.internalSetIndex(n + 1);
            }
            return '\uffff';
        }

        @Override
        public char previous() {
            int n = this.currentIndex;
            if (n > this.beginIndex) {
                return this.internalSetIndex(n - 1);
            }
            return '\uffff';
        }

        @Override
        public char setIndex(int n) {
            if (n >= this.beginIndex && n <= this.endIndex) {
                return this.internalSetIndex(n);
            }
            throw new IllegalArgumentException("Invalid index");
        }
    }

}

