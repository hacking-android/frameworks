/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.MessageNano;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class MessageNanoPrinter {
    private static final String INDENT = "  ";
    private static final int MAX_STRING_LEN = 200;

    private MessageNanoPrinter() {
    }

    private static void appendQuotedBytes(byte[] arrby, StringBuffer stringBuffer) {
        if (arrby == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            if (n != 92 && n != 34) {
                if (n >= 32 && n < 127) {
                    stringBuffer.append((char)n);
                    continue;
                }
                stringBuffer.append(String.format("\\%03o", n));
                continue;
            }
            stringBuffer.append('\\');
            stringBuffer.append((char)n);
        }
        stringBuffer.append('\"');
    }

    private static String deCamelCaseify(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(c));
                continue;
            }
            if (Character.isUpperCase(c)) {
                stringBuffer.append('_');
                stringBuffer.append(Character.toLowerCase(c));
                continue;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    private static String escapeString(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c >= ' ' && c <= '~' && c != '\"' && c != '\'') {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(String.format("\\u%04x", c));
        }
        return stringBuilder.toString();
    }

    public static <T extends MessageNano> String print(T object) {
        if (object == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StringBuffer stringBuffer2 = new StringBuffer();
            MessageNanoPrinter.print(null, object, stringBuffer2, stringBuffer);
            return stringBuffer.toString();
        }
        catch (InvocationTargetException invocationTargetException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error printing proto: ");
            ((StringBuilder)object).append(invocationTargetException.getMessage());
            return ((StringBuilder)object).toString();
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error printing proto: ");
            stringBuilder.append(illegalAccessException.getMessage());
            return stringBuilder.toString();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void print(String string, Object iterator, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        AccessibleObject[] arraccessibleObject;
        int n3;
        Class<?> class_;
        Object object;
        int i;
        String noSuchMethodException;
        int n;
        if (iterator == null) {
            return;
        }
        if (iterator instanceof MessageNano) {
            n = stringBuffer.length();
            if (string != null) {
                stringBuffer2.append(stringBuffer);
                stringBuffer2.append(MessageNanoPrinter.deCamelCaseify(string));
                stringBuffer2.append(" <\n");
                stringBuffer.append(INDENT);
            }
            class_ = iterator.getClass();
            arraccessibleObject = class_.getFields();
            i = arraccessibleObject.length;
        } else {
            if (iterator instanceof Map) {
                iterator = (Map)((Object)iterator);
                string = MessageNanoPrinter.deCamelCaseify(string);
                iterator = iterator.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(string);
                    stringBuffer2.append(" <\n");
                    int n2 = stringBuffer.length();
                    stringBuffer.append(INDENT);
                    MessageNanoPrinter.print("key", entry.getKey(), stringBuffer, stringBuffer2);
                    MessageNanoPrinter.print("value", entry.getValue(), stringBuffer, stringBuffer2);
                    stringBuffer.setLength(n2);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                }
                return;
            }
            string = MessageNanoPrinter.deCamelCaseify(string);
            stringBuffer2.append(stringBuffer);
            stringBuffer2.append(string);
            stringBuffer2.append(": ");
            if (iterator instanceof String) {
                string = MessageNanoPrinter.sanitizeString((String)((Object)iterator));
                stringBuffer2.append("\"");
                stringBuffer2.append(string);
                stringBuffer2.append("\"");
            } else if (iterator instanceof byte[]) {
                MessageNanoPrinter.appendQuotedBytes((byte[])iterator, stringBuffer2);
            } else {
                stringBuffer2.append(iterator);
            }
            stringBuffer2.append("\n");
            return;
        }
        for (n3 = 0; n3 < i; ++n3) {
            Field field = arraccessibleObject[n3];
            int n2 = field.getModifiers();
            noSuchMethodException = field.getName();
            if ("cachedSize".equals(noSuchMethodException) || (n2 & 1) != 1 || (n2 & 8) == 8 || noSuchMethodException.startsWith("_") || noSuchMethodException.endsWith("_")) continue;
            object = field.getType();
            Object object2 = field.get(iterator);
            if (((Class)object).isArray()) {
                if (((Class)object).getComponentType() == Byte.TYPE) {
                    MessageNanoPrinter.print(noSuchMethodException, object2, stringBuffer, stringBuffer2);
                    continue;
                }
                n2 = object2 == null ? 0 : Array.getLength(object2);
                for (i = 0; i < n2; ++i) {
                    MessageNanoPrinter.print(noSuchMethodException, Array.get(object2, i), stringBuffer, stringBuffer2);
                }
                continue;
            }
            MessageNanoPrinter.print(noSuchMethodException, object2, stringBuffer, stringBuffer2);
        }
        arraccessibleObject = class_.getMethods();
        n3 = arraccessibleObject.length;
        i = 0;
        do {
            block25 : {
                if (i >= n3) {
                    if (string == null) return;
                    stringBuffer.setLength(n);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                    return;
                }
                noSuchMethodException = ((Method)arraccessibleObject[i]).getName();
                if (noSuchMethodException.startsWith("set")) {
                    noSuchMethodException = noSuchMethodException.substring(3);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("has");
                    ((StringBuilder)object).append(noSuchMethodException);
                    object = ((StringBuilder)object).toString();
                    try {
                        object = class_.getMethod((String)object, new Class[0]);
                        if (!((Boolean)((Method)object).invoke(iterator, new Object[0])).booleanValue()) break block25;
                    }
                    catch (NoSuchMethodException noSuchMethodException2) {}
                    try {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("get");
                        ((StringBuilder)object).append(noSuchMethodException);
                        object = ((StringBuilder)object).toString();
                    }
                    catch (NoSuchMethodException noSuchMethodException2) {}
                    try {
                        object = class_.getMethod((String)object, new Class[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException3) {
                        break block25;
                    }
                    MessageNanoPrinter.print(noSuchMethodException, ((Method)object).invoke(iterator, new Object[0]), stringBuffer, stringBuffer2);
                    break block25;
                    catch (NoSuchMethodException noSuchMethodException3) {
                        // empty catch block
                    }
                }
            }
            ++i;
        } while (true);
    }

    private static String sanitizeString(String string) {
        CharSequence charSequence = string;
        if (!string.startsWith("http")) {
            charSequence = string;
            if (string.length() > 200) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string.substring(0, 200));
                ((StringBuilder)charSequence).append("[...]");
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        return MessageNanoPrinter.escapeString((String)charSequence);
    }
}

