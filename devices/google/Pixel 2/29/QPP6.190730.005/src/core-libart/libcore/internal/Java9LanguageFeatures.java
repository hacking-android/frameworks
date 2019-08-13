/*
 * Decompiled with CFR 0.145.
 */
package libcore.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Java9LanguageFeatures {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] copy(byte[] object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        object = new ByteArrayInputStream((byte[])object);
        try {
            int n;
            while ((n = ((InputStream)object).read()) != -1) {
                byteArrayOutputStream.write(n);
            }
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    ((InputStream)object).close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
                throw throwable2;
            }
        }
        ((InputStream)object).close();
        return byteArrayOutputStream.toByteArray();
    }

    @SafeVarargs
    public static <T> String toListString(T ... arrT) {
        return Java9LanguageFeatures.toString(arrT).toString();
    }

    @SafeVarargs
    private static <T> List<String> toString(T ... arrT) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrT[i].toString());
        }
        return arrayList;
    }

    public <T> AtomicReference<T> createReference(T t) {
        return new AtomicReference<T>(t){};
    }

    public static interface Person {
        private String reverse(String string) {
            return new StringBuilder(string).reverse().toString();
        }

        default public boolean isPalindrome() {
            return this.name().equals(this.reverse(this.name()));
        }

        default public boolean isPalindromeIgnoreCase() {
            return this.name().equalsIgnoreCase(this.reverse(this.name()));
        }

        public String name();
    }

}

