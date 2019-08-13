/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.FieldSet;
import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.TextFormatEscaper;
import com.android.framework.protobuf.UnknownFieldSetLite;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

final class MessageLiteToString {
    private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final String BYTES_SUFFIX = "Bytes";
    private static final String LIST_SUFFIX = "List";

    MessageLiteToString() {
    }

    private static final String camelCaseToSnakeCase(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (Character.isUpperCase(c)) {
                stringBuilder.append("_");
            }
            stringBuilder.append(Character.toLowerCase(c));
        }
        return stringBuilder.toString();
    }

    private static boolean isDefaultValue(Object object) {
        boolean bl = object instanceof Boolean;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = true;
        boolean bl6 = true;
        if (bl) {
            return (Boolean)object ^ true;
        }
        if (object instanceof Integer) {
            if ((Integer)object != 0) {
                bl6 = false;
            }
            return bl6;
        }
        if (object instanceof Float) {
            bl6 = ((Float)object).floatValue() == 0.0f ? bl2 : false;
            return bl6;
        }
        if (object instanceof Double) {
            bl6 = (Double)object == 0.0 ? bl3 : false;
            return bl6;
        }
        if (object instanceof String) {
            return object.equals("");
        }
        if (object instanceof ByteString) {
            return object.equals(ByteString.EMPTY);
        }
        if (object instanceof MessageLite) {
            bl6 = object == ((MessageLite)object).getDefaultInstanceForType() ? bl4 : false;
            return bl6;
        }
        if (object instanceof Enum) {
            bl6 = ((Enum)object).ordinal() == 0 ? bl5 : false;
            return bl6;
        }
        return false;
    }

    static final void printField(StringBuilder stringBuilder, int n, String string2, Object iterator) {
        int n2;
        if (iterator instanceof List) {
            iterator = ((List)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                MessageLiteToString.printField(stringBuilder, n, string2, iterator.next());
            }
            return;
        }
        stringBuilder.append('\n');
        for (n2 = 0; n2 < n; ++n2) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(string2);
        if (iterator instanceof String) {
            stringBuilder.append(": \"");
            stringBuilder.append(TextFormatEscaper.escapeText((String)((Object)iterator)));
            stringBuilder.append('\"');
        } else if (iterator instanceof ByteString) {
            stringBuilder.append(": \"");
            stringBuilder.append(TextFormatEscaper.escapeBytes((ByteString)((Object)iterator)));
            stringBuilder.append('\"');
        } else if (iterator instanceof GeneratedMessageLite) {
            stringBuilder.append(" {");
            MessageLiteToString.reflectivePrintWithIndent((GeneratedMessageLite)((Object)iterator), stringBuilder, n + 2);
            stringBuilder.append("\n");
            for (n2 = 0; n2 < n; ++n2) {
                stringBuilder.append(' ');
            }
            stringBuilder.append("}");
        } else {
            stringBuilder.append(": ");
            stringBuilder.append(iterator.toString());
        }
    }

    private static void reflectivePrintWithIndent(MessageLite messageLite, StringBuilder stringBuilder, int n) {
        Object object2 = new HashMap<String, Method>();
        Object object3 = new HashMap<String, Method>();
        Object object4 = new TreeSet();
        for (Method object5 : messageLite.getClass().getDeclaredMethods()) {
            object3.put(object5.getName(), object5);
            if (object5.getParameterTypes().length != 0) continue;
            object2.put(object5.getName(), object5);
            if (!object5.getName().startsWith("get")) continue;
            object4.add(object5.getName());
        }
        Iterator iterator = object4.iterator();
        while (iterator.hasNext()) {
            Object object;
            Object object7;
            object4 = ((String)iterator.next()).replaceFirst("get", "");
            if (((String)object4).endsWith(LIST_SUFFIX) && !((String)object4).endsWith(BUILDER_LIST_SUFFIX)) {
                object7 = new StringBuilder();
                ((StringBuilder)object7).append(((String)object4).substring(0, 1).toLowerCase());
                ((StringBuilder)object7).append(((String)object4).substring(1, ((String)object4).length() - LIST_SUFFIX.length()));
                object7 = ((StringBuilder)object7).toString();
                object = new StringBuilder();
                ((StringBuilder)object).append("get");
                ((StringBuilder)object).append((String)object4);
                object = (Method)object2.get(((StringBuilder)object).toString());
                if (object != null) {
                    MessageLiteToString.printField(stringBuilder, n, MessageLiteToString.camelCaseToSnakeCase((String)object7), GeneratedMessageLite.invokeOrDie((Method)object, messageLite, new Object[0]));
                    continue;
                }
            }
            object7 = new StringBuilder();
            ((StringBuilder)object7).append("set");
            ((StringBuilder)object7).append((String)object4);
            if ((Method)object3.get(((StringBuilder)object7).toString()) == null) continue;
            if (((String)object4).endsWith(BYTES_SUFFIX)) {
                object7 = new StringBuilder();
                ((StringBuilder)object7).append("get");
                ((StringBuilder)object7).append(((String)object4).substring(0, ((String)object4).length() - BYTES_SUFFIX.length()));
                if (object2.containsKey(((StringBuilder)object7).toString())) continue;
            }
            object7 = new StringBuilder();
            ((StringBuilder)object7).append(((String)object4).substring(0, 1).toLowerCase());
            ((StringBuilder)object7).append(((String)object4).substring(1));
            object7 = ((StringBuilder)object7).toString();
            object = new StringBuilder();
            ((StringBuilder)object).append("get");
            ((StringBuilder)object).append((String)object4);
            object = (Method)object2.get(((StringBuilder)object).toString());
            Object object5 = new StringBuilder();
            ((StringBuilder)object5).append("has");
            ((StringBuilder)object5).append((String)object4);
            object5 = (Method)object2.get(((StringBuilder)object5).toString());
            if (object == null) continue;
            object4 = GeneratedMessageLite.invokeOrDie((Method)object, messageLite, new Object[0]);
            boolean bl = object5 == null ? !MessageLiteToString.isDefaultValue(object4) : (Boolean)GeneratedMessageLite.invokeOrDie((Method)object5, messageLite, new Object[0]);
            if (!bl) continue;
            MessageLiteToString.printField(stringBuilder, n, MessageLiteToString.camelCaseToSnakeCase((String)object7), object4);
        }
        if (messageLite instanceof GeneratedMessageLite.ExtendableMessage) {
            object3 = ((GeneratedMessageLite.ExtendableMessage)messageLite).extensions.iterator();
            while (object3.hasNext()) {
                object2 = (Map.Entry)object3.next();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("[");
                stringBuilder2.append(((GeneratedMessageLite.ExtensionDescriptor)object2.getKey()).getNumber());
                stringBuilder2.append("]");
                MessageLiteToString.printField(stringBuilder, n, stringBuilder2.toString(), object2.getValue());
            }
        }
        if (((GeneratedMessageLite)messageLite).unknownFields != null) {
            ((GeneratedMessageLite)messageLite).unknownFields.printWithIndent(stringBuilder, n);
        }
    }

    static String toString(MessageLite messageLite, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("# ");
        stringBuilder.append(string2);
        MessageLiteToString.reflectivePrintWithIndent(messageLite, stringBuilder, 0);
        return stringBuilder.toString();
    }
}

