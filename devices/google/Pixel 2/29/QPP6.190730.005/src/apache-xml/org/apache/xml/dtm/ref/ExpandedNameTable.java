/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.ref.ExtendedType;

public class ExpandedNameTable {
    public static final int ATTRIBUTE = 2;
    public static final int CDATA_SECTION = 4;
    public static final int COMMENT = 8;
    public static final int DOCUMENT = 9;
    public static final int DOCUMENT_FRAGMENT = 11;
    public static final int DOCUMENT_TYPE = 10;
    public static final int ELEMENT = 1;
    public static final int ENTITY = 6;
    public static final int ENTITY_REFERENCE = 5;
    public static final int NAMESPACE = 13;
    public static final int NOTATION = 12;
    public static final int PROCESSING_INSTRUCTION = 7;
    public static final int TEXT = 3;
    private static ExtendedType[] m_defaultExtendedTypes;
    private static int m_initialCapacity;
    private static int m_initialSize;
    private static float m_loadFactor;
    ExtendedType hashET = new ExtendedType(-1, "", "");
    private int m_capacity;
    private ExtendedType[] m_extendedTypes;
    private int m_nextType;
    private HashEntry[] m_table;
    private int m_threshold;

    static {
        m_initialSize = 128;
        m_loadFactor = 0.75f;
        m_initialCapacity = 203;
        m_defaultExtendedTypes = new ExtendedType[14];
        for (int i = 0; i < 14; ++i) {
            ExpandedNameTable.m_defaultExtendedTypes[i] = new ExtendedType(i, "", "");
        }
    }

    public ExpandedNameTable() {
        int n = this.m_capacity = m_initialCapacity;
        this.m_threshold = (int)((float)n * m_loadFactor);
        this.m_table = new HashEntry[n];
        this.initExtendedTypes();
    }

    private void initExtendedTypes() {
        this.m_extendedTypes = new ExtendedType[m_initialSize];
        for (int i = 0; i < 14; ++i) {
            ExtendedType[] arrextendedType = this.m_extendedTypes;
            ExtendedType[] arrextendedType2 = m_defaultExtendedTypes;
            arrextendedType[i] = arrextendedType2[i];
            this.m_table[i] = new HashEntry(arrextendedType2[i], i, i, null);
        }
        this.m_nextType = 14;
    }

    private void rehash() {
        int n;
        int n2 = this.m_capacity;
        HashEntry[] arrhashEntry = this.m_table;
        this.m_capacity = n = n2 * 2 + 1;
        this.m_threshold = (int)((float)n * m_loadFactor);
        this.m_table = new HashEntry[n];
        --n2;
        while (n2 >= 0) {
            HashEntry hashEntry = arrhashEntry[n2];
            while (hashEntry != null) {
                int n3;
                HashEntry hashEntry2 = hashEntry.next;
                int n4 = n3 = hashEntry.hash % n;
                if (n3 < 0) {
                    n4 = -n3;
                }
                HashEntry[] arrhashEntry2 = this.m_table;
                hashEntry.next = arrhashEntry2[n4];
                arrhashEntry2[n4] = hashEntry;
                hashEntry = hashEntry2;
            }
            --n2;
        }
    }

    public int getExpandedTypeID(int n) {
        return n;
    }

    public int getExpandedTypeID(String string, String string2, int n) {
        return this.getExpandedTypeID(string, string2, n, false);
    }

    public int getExpandedTypeID(String object, String arrextendedType, int n, boolean bl) {
        int n2;
        ExtendedType[] arrextendedType2 = object;
        if (object == null) {
            arrextendedType2 = "";
        }
        object = arrextendedType;
        if (arrextendedType == null) {
            object = "";
        }
        int n3 = arrextendedType2.hashCode() + n + ((String)object).hashCode();
        this.hashET.redefine(n, (String)arrextendedType2, (String)object, n3);
        int n4 = n2 = n3 % this.m_capacity;
        if (n2 < 0) {
            n4 = -n2;
        }
        arrextendedType = this.m_table[n4];
        while (arrextendedType != null) {
            if (arrextendedType.hash == n3 && arrextendedType.key.equals(this.hashET)) {
                return arrextendedType.value;
            }
            arrextendedType = arrextendedType.next;
        }
        if (bl) {
            return -1;
        }
        if (this.m_nextType > this.m_threshold) {
            this.rehash();
            n4 = n2 = n3 % this.m_capacity;
            if (n2 < 0) {
                n4 = -n2;
            }
        }
        object = new ExtendedType(n, (String)arrextendedType2, (String)object, n3);
        arrextendedType2 = this.m_extendedTypes;
        if (arrextendedType2.length == this.m_nextType) {
            arrextendedType = new ExtendedType[arrextendedType2.length * 2];
            System.arraycopy(arrextendedType2, 0, arrextendedType, 0, arrextendedType2.length);
            this.m_extendedTypes = arrextendedType;
        }
        arrextendedType = this.m_extendedTypes;
        n = this.m_nextType;
        arrextendedType[n] = object;
        this.m_table[n4] = object = new HashEntry((ExtendedType)object, n, n3, this.m_table[n4]);
        n = this.m_nextType;
        this.m_nextType = n + 1;
        return n;
    }

    public ExtendedType[] getExtendedTypes() {
        return this.m_extendedTypes;
    }

    public String getLocalName(int n) {
        return this.m_extendedTypes[n].getLocalName();
    }

    public final int getLocalNameID(int n) {
        if (this.m_extendedTypes[n].getLocalName().equals("")) {
            return 0;
        }
        return n;
    }

    public String getNamespace(int n) {
        String string;
        block0 : {
            string = this.m_extendedTypes[n].getNamespace();
            if (!string.equals("")) break block0;
            string = null;
        }
        return string;
    }

    public final int getNamespaceID(int n) {
        if (this.m_extendedTypes[n].getNamespace().equals("")) {
            return 0;
        }
        return n;
    }

    public int getSize() {
        return this.m_nextType;
    }

    public final short getType(int n) {
        return (short)this.m_extendedTypes[n].getNodeType();
    }

    private static final class HashEntry {
        int hash;
        ExtendedType key;
        HashEntry next;
        int value;

        protected HashEntry(ExtendedType extendedType, int n, int n2, HashEntry hashEntry) {
            this.key = extendedType;
            this.value = n;
            this.hash = n2;
            this.next = hashEntry;
        }
    }

}

