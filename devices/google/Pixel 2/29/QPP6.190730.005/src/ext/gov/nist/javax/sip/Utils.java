/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.UtilsExt;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPResponse;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Random;

public class Utils
implements UtilsExt {
    private static int callIDCounter;
    private static long counter;
    private static MessageDigest digester;
    private static Utils instance;
    private static Random rand;
    private static String signature;
    private static final char[] toHex;

    static {
        counter = 0L;
        instance = new Utils();
        toHex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            digester = MessageDigest.getInstance("MD5");
            rand = new Random();
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not intialize Digester ", exception);
        }
        signature = Utils.toHexString(Integer.toString(Math.abs(rand.nextInt() % 1000)).getBytes());
    }

    public static Utils getInstance() {
        return instance;
    }

    public static String getQuotedString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\"');
        stringBuilder.append(string.replace("\"", "\\\""));
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public static String getSignature() {
        return signature;
    }

    public static void main(String[] object) {
        object = new HashSet();
        for (int i = 0; i < 100000; ++i) {
            String string = Utils.getInstance().generateBranchId();
            if (!((HashSet)object).contains(string)) {
                ((HashSet)object).add(string);
                continue;
            }
            throw new RuntimeException("Duplicate Branch ID");
        }
        System.out.println("Done!!");
    }

    protected static String reduceString(String string) {
        String string2 = string.toLowerCase();
        int n = string2.length();
        string = "";
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence = string;
            if (string2.charAt(i) != ' ') {
                if (string2.charAt(i) == '\t') {
                    charSequence = string;
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(string2.charAt(i));
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            string = charSequence;
        }
        return string;
    }

    public static String toHexString(byte[] arrby) {
        int n = 0;
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n2 = n + 1;
            char[] arrc2 = toHex;
            arrc[n] = arrc2[arrby[i] >> 4 & 15];
            n = n2 + 1;
            arrc[n2] = arrc2[arrby[i] & 15];
        }
        return new String(arrc);
    }

    @Override
    public String generateBranchId() {
        synchronized (this) {
            long l = rand.nextLong();
            long l2 = counter;
            counter = 1L + l2;
            long l3 = System.currentTimeMillis();
            Object object = digester.digest(Long.toString(l + l2 + l3).getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("z9hG4bK");
            stringBuilder.append(Utils.toHexString(object));
            stringBuilder.append(signature);
            object = stringBuilder.toString();
            return object;
        }
    }

    @Override
    public String generateCallIdentifier(String string) {
        synchronized (this) {
            long l = System.currentTimeMillis();
            int n = callIDCounter;
            callIDCounter = n + 1;
            String string2 = Long.toString(l + (long)n + rand.nextLong());
            string2 = Utils.toHexString(digester.digest(string2.getBytes()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("@");
            stringBuilder.append(string);
            string = stringBuilder.toString();
            return string;
        }
    }

    @Override
    public String generateTag() {
        synchronized (this) {
            String string = Integer.toHexString(rand.nextInt());
            return string;
        }
    }

    public boolean responseBelongsToUs(SIPResponse object) {
        boolean bl = (object = ((SIPMessage)object).getTopmostVia().getBranch()) != null && ((String)object).endsWith(signature);
        return bl;
    }
}

