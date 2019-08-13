/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

public class Assert {
    protected Assert() {
    }

    public static void assertEquals(byte by, byte by2) {
        Assert.assertEquals(null, by, by2);
    }

    public static void assertEquals(char c, char c2) {
        Assert.assertEquals(null, c, c2);
    }

    public static void assertEquals(double d, double d2, double d3) {
        Assert.assertEquals(null, d, d2, d3);
    }

    public static void assertEquals(float f, float f2, float f3) {
        Assert.assertEquals(null, f, f2, f3);
    }

    public static void assertEquals(int n, int n2) {
        Assert.assertEquals(null, n, n2);
    }

    public static void assertEquals(long l, long l2) {
        Assert.assertEquals(null, l, l2);
    }

    public static void assertEquals(Object object, Object object2) {
        Assert.assertEquals(null, object, object2);
    }

    public static void assertEquals(String string, byte by, byte by2) {
        Assert.assertEquals(string, new Byte(by), new Byte(by2));
    }

    public static void assertEquals(String string, char c, char c2) {
        Assert.assertEquals(string, new Character(c), new Character(c2));
    }

    public static void assertEquals(String string, double d, double d2, double d3) {
        if (Double.compare(d, d2) == 0) {
            return;
        }
        if (!(Math.abs(d - d2) <= d3)) {
            Assert.failNotEquals(string, new Double(d), new Double(d2));
        }
    }

    public static void assertEquals(String string, float f, float f2, float f3) {
        if (Float.compare(f, f2) == 0) {
            return;
        }
        if (!(Math.abs(f - f2) <= f3)) {
            Assert.failNotEquals(string, new Float(f), new Float(f2));
        }
    }

    public static void assertEquals(String string, int n, int n2) {
        Assert.assertEquals(string, new Integer(n), new Integer(n2));
    }

    public static void assertEquals(String string, long l, long l2) {
        Assert.assertEquals(string, new Long(l), new Long(l2));
    }

    public static void assertEquals(String string, Object object, Object object2) {
        if (object == null && object2 == null) {
            return;
        }
        if (object != null && object.equals(object2)) {
            return;
        }
        Assert.failNotEquals(string, object, object2);
    }

    public static void assertEquals(String string, String string2) {
        Assert.assertEquals(null, string, string2);
    }

    public static void assertEquals(String string, String string2, String string3) {
        if (string2 == null && string3 == null) {
            return;
        }
        if (string2 != null && string2.equals(string3)) {
            return;
        }
        if (string == null) {
            string = "";
        }
        throw new ComparisonFailure(string, string2, string3);
    }

    public static void assertEquals(String string, short s, short s2) {
        Assert.assertEquals(string, new Short(s), new Short(s2));
    }

    public static void assertEquals(String string, boolean bl, boolean bl2) {
        Assert.assertEquals(string, (Object)bl, (Object)bl2);
    }

    public static void assertEquals(short s, short s2) {
        Assert.assertEquals(null, s, s2);
    }

    public static void assertEquals(boolean bl, boolean bl2) {
        Assert.assertEquals(null, bl, bl2);
    }

    public static void assertFalse(String string, boolean bl) {
        Assert.assertTrue(string, bl ^ true);
    }

    public static void assertFalse(boolean bl) {
        Assert.assertFalse(null, bl);
    }

    public static void assertNotNull(Object object) {
        Assert.assertNotNull(null, object);
    }

    public static void assertNotNull(String string, Object object) {
        boolean bl = object != null;
        Assert.assertTrue(string, bl);
    }

    public static void assertNotSame(Object object, Object object2) {
        Assert.assertNotSame(null, object, object2);
    }

    public static void assertNotSame(String string, Object object, Object object2) {
        if (object == object2) {
            Assert.failSame(string);
        }
    }

    public static void assertNull(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected: <null> but was: ");
        stringBuilder.append(String.valueOf(object));
        Assert.assertNull(stringBuilder.toString(), object);
    }

    public static void assertNull(String string, Object object) {
        boolean bl = object == null;
        Assert.assertTrue(string, bl);
    }

    public static void assertSame(Object object, Object object2) {
        Assert.assertSame(null, object, object2);
    }

    public static void assertSame(String string, Object object, Object object2) {
        if (object == object2) {
            return;
        }
        Assert.failNotSame(string, object, object2);
    }

    public static void assertTrue(String string, boolean bl) {
        if (!bl) {
            Assert.fail(string);
        }
    }

    public static void assertTrue(boolean bl) {
        Assert.assertTrue(null, bl);
    }

    public static void fail() {
        Assert.fail(null);
    }

    public static void fail(String string) {
        if (string == null) {
            throw new AssertionFailedError();
        }
        throw new AssertionFailedError(string);
    }

    public static void failNotEquals(String string, Object object, Object object2) {
        Assert.fail(Assert.format(string, object, object2));
    }

    public static void failNotSame(String charSequence, Object object, Object object2) {
        CharSequence charSequence2 = "";
        if (charSequence != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(" ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("expected same:<");
        ((StringBuilder)charSequence).append(object);
        ((StringBuilder)charSequence).append("> was not:<");
        ((StringBuilder)charSequence).append(object2);
        ((StringBuilder)charSequence).append(">");
        Assert.fail(((StringBuilder)charSequence).toString());
    }

    public static void failSame(String charSequence) {
        CharSequence charSequence2 = "";
        if (charSequence != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(" ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("expected not same");
        Assert.fail(((StringBuilder)charSequence).toString());
    }

    public static String format(String charSequence, Object object, Object object2) {
        String string = "";
        CharSequence charSequence2 = string;
        if (charSequence != null) {
            charSequence2 = string;
            if (((String)charSequence).length() > 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(" ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("expected:<");
        ((StringBuilder)charSequence).append(object);
        ((StringBuilder)charSequence).append("> but was:<");
        ((StringBuilder)charSequence).append(object2);
        ((StringBuilder)charSequence).append(">");
        return ((StringBuilder)charSequence).toString();
    }
}

