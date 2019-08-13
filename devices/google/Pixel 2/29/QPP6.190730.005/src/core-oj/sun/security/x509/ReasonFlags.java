/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Enumeration;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;

public class ReasonFlags {
    public static final String AA_COMPROMISE = "aa_compromise";
    public static final String AFFILIATION_CHANGED = "affiliation_changed";
    public static final String CA_COMPROMISE = "ca_compromise";
    public static final String CERTIFICATE_HOLD = "certificate_hold";
    public static final String CESSATION_OF_OPERATION = "cessation_of_operation";
    public static final String KEY_COMPROMISE = "key_compromise";
    private static final String[] NAMES = new String[]{"unused", "key_compromise", "ca_compromise", "affiliation_changed", "superseded", "cessation_of_operation", "certificate_hold", "privilege_withdrawn", "aa_compromise"};
    public static final String PRIVILEGE_WITHDRAWN = "privilege_withdrawn";
    public static final String SUPERSEDED = "superseded";
    public static final String UNUSED = "unused";
    private boolean[] bitString;

    public ReasonFlags(BitArray bitArray) {
        this.bitString = bitArray.toBooleanArray();
    }

    public ReasonFlags(DerInputStream derInputStream) throws IOException {
        this.bitString = derInputStream.getDerValue().getUnalignedBitString(true).toBooleanArray();
    }

    public ReasonFlags(DerValue derValue) throws IOException {
        this.bitString = derValue.getUnalignedBitString(true).toBooleanArray();
    }

    public ReasonFlags(byte[] arrby) {
        this.bitString = new BitArray(arrby.length * 8, arrby).toBooleanArray();
    }

    public ReasonFlags(boolean[] arrbl) {
        this.bitString = arrbl;
    }

    private boolean isSet(int n) {
        boolean[] arrbl = this.bitString;
        boolean bl = n < arrbl.length && arrbl[n];
        return bl;
    }

    private static int name2Index(String string) throws IOException {
        String[] arrstring;
        for (int i = 0; i < (arrstring = NAMES).length; ++i) {
            if (!arrstring[i].equalsIgnoreCase(string)) continue;
            return i;
        }
        throw new IOException("Name not recognized by ReasonFlags");
    }

    private void set(int n, boolean bl) {
        boolean[] arrbl = this.bitString;
        if (n >= arrbl.length) {
            boolean[] arrbl2 = new boolean[n + 1];
            System.arraycopy((Object)arrbl, 0, (Object)arrbl2, 0, arrbl.length);
            this.bitString = arrbl2;
        }
        this.bitString[n] = bl;
    }

    public void delete(String string) throws IOException {
        this.set(string, Boolean.FALSE);
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
    }

    public Object get(String string) throws IOException {
        return this.isSet(ReasonFlags.name2Index(string));
    }

    public Enumeration<String> getElements() {
        String[] arrstring;
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        for (int i = 0; i < (arrstring = NAMES).length; ++i) {
            attributeNameEnumeration.addElement(arrstring[i]);
        }
        return attributeNameEnumeration.elements();
    }

    public boolean[] getFlags() {
        return this.bitString;
    }

    public void set(String string, Object object) throws IOException {
        if (object instanceof Boolean) {
            boolean bl = (Boolean)object;
            this.set(ReasonFlags.name2Index(string), bl);
            return;
        }
        throw new IOException("Attribute must be of type Boolean.");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Reason Flags [\n");
        if (this.isSet(0)) {
            stringBuilder.append("  Unused\n");
        }
        if (this.isSet(1)) {
            stringBuilder.append("  Key Compromise\n");
        }
        if (this.isSet(2)) {
            stringBuilder.append("  CA Compromise\n");
        }
        if (this.isSet(3)) {
            stringBuilder.append("  Affiliation_Changed\n");
        }
        if (this.isSet(4)) {
            stringBuilder.append("  Superseded\n");
        }
        if (this.isSet(5)) {
            stringBuilder.append("  Cessation Of Operation\n");
        }
        if (this.isSet(6)) {
            stringBuilder.append("  Certificate Hold\n");
        }
        if (this.isSet(7)) {
            stringBuilder.append("  Privilege Withdrawn\n");
        }
        if (this.isSet(8)) {
            stringBuilder.append("  AA Compromise\n");
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

