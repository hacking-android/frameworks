/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.Debug;
import gov.nist.core.GenericObjectList;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.Match;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class GenericObject
implements Serializable,
Cloneable {
    protected static final String AND = "&";
    protected static final String AT = "@";
    protected static final String COLON = ":";
    protected static final String COMMA = ",";
    protected static final String DOT = ".";
    protected static final String DOUBLE_QUOTE = "\"";
    protected static final String EQUALS = "=";
    protected static final String GREATER_THAN = ">";
    protected static final String HT = "\t";
    protected static final String LESS_THAN = "<";
    protected static final String LPAREN = "(";
    protected static final String NEWLINE = "\r\n";
    protected static final String PERCENT = "%";
    protected static final String POUND = "#";
    protected static final String QUESTION = "?";
    protected static final String QUOTE = "'";
    protected static final String RETURN = "\n";
    protected static final String RPAREN = ")";
    protected static final String SEMICOLON = ";";
    protected static final String SLASH = "/";
    protected static final String SP = " ";
    protected static final String STAR = "*";
    static final String[] immutableClassNames;
    protected static final Set<Class<?>> immutableClasses;
    protected int indentation = 0;
    protected Match matchExpression;
    protected String stringRepresentation = "";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        immutableClasses = new HashSet(10);
        immutableClassNames = new String[]{"String", "Character", "Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double"};
        int n = 0;
        try {
            do {
                if (n >= immutableClassNames.length) {
                    return;
                }
                Set<Class<?>> set = immutableClasses;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("java.lang.");
                stringBuilder.append(immutableClassNames[n]);
                set.add(Class.forName(stringBuilder.toString()));
                ++n;
            } while (true);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("Internal error", classNotFoundException);
        }
    }

    protected GenericObject() {
    }

    public static Class<?> getClassFromName(String object) {
        try {
            object = Class.forName((String)object);
            return object;
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException(exception);
            return null;
        }
    }

    public static boolean isMySubclass(Class<?> class_) {
        return GenericObject.class.isAssignableFrom(class_);
    }

    public static Object makeClone(Object object) {
        if (object != null) {
            Object object2;
            Class<?> class_ = object.getClass();
            Object object3 = object;
            if (immutableClasses.contains(class_)) {
                return object;
            }
            if (class_.isArray()) {
                if (!(class_ = class_.getComponentType()).isPrimitive()) {
                    object2 = ((Object[])object).clone();
                } else {
                    if (class_ == Character.TYPE) {
                        object2 = ((char[])object).clone();
                    } else {
                        object2 = object3;
                        if (class_ == Boolean.TYPE) {
                            object2 = ((boolean[])object).clone();
                        }
                    }
                    if (class_ == Byte.TYPE) {
                        object2 = ((byte[])object).clone();
                    } else if (class_ == Short.TYPE) {
                        object2 = ((short[])object).clone();
                    } else if (class_ == Integer.TYPE) {
                        object2 = ((int[])object).clone();
                    } else if (class_ == Long.TYPE) {
                        object2 = ((long[])object).clone();
                    } else if (class_ == Float.TYPE) {
                        object2 = ((float[])object).clone();
                    } else if (class_ == Double.TYPE) {
                        object2 = ((double[])object).clone();
                    }
                }
            } else if (GenericObject.class.isAssignableFrom(class_)) {
                object2 = ((GenericObject)object).clone();
            } else if (GenericObjectList.class.isAssignableFrom(class_)) {
                object2 = ((GenericObjectList)object).clone();
            } else {
                object2 = object3;
                if (Cloneable.class.isAssignableFrom(class_)) {
                    try {
                        object2 = class_.getMethod("clone", null).invoke(object, null);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        object2 = object3;
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        object2 = object3;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        object2 = object3;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        InternalErrorHandler.handleException(illegalArgumentException);
                        object2 = object3;
                    }
                    catch (SecurityException securityException) {
                        object2 = object3;
                    }
                }
            }
            return object2;
        }
        throw new NullPointerException("null obj!");
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("Internal error");
        }
    }

    protected void dbgPrint() {
        Debug.println(this.debugDump());
    }

    protected void dbgPrint(String string) {
        Debug.println(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String debugDump() {
        this.stringRepresentation = "";
        Field[] arrfield = this.getClass();
        this.sprint(arrfield.getName());
        this.sprint("{");
        arrfield = arrfield.getDeclaredFields();
        int n = 0;
        do {
            if (n >= arrfield.length) {
                this.sprint("}");
                return this.stringRepresentation;
            }
            Field field = arrfield[n];
            if ((field.getModifiers() & 2) != 2) {
                Serializable serializable = field.getType();
                CharSequence charSequence = field.getName();
                if (((String)charSequence).compareTo("stringRepresentation") != 0 && ((String)charSequence).compareTo("indentation") != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append(COLON);
                    this.sprint(stringBuilder.toString());
                    try {
                        if (((Class)serializable).isPrimitive()) {
                            charSequence = ((Class)serializable).toString();
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append((String)charSequence);
                            ((StringBuilder)serializable).append(COLON);
                            this.sprint(((StringBuilder)serializable).toString());
                            if (((String)charSequence).compareTo("int") == 0) {
                                this.sprint(field.getInt(this));
                            } else if (((String)charSequence).compareTo("short") == 0) {
                                this.sprint(field.getShort(this));
                            } else if (((String)charSequence).compareTo("char") == 0) {
                                this.sprint(field.getChar(this));
                            } else if (((String)charSequence).compareTo("long") == 0) {
                                this.sprint(field.getLong(this));
                            } else if (((String)charSequence).compareTo("boolean") == 0) {
                                this.sprint(field.getBoolean(this));
                            } else if (((String)charSequence).compareTo("double") == 0) {
                                this.sprint(field.getDouble(this));
                            } else if (((String)charSequence).compareTo("float") == 0) {
                                this.sprint(field.getFloat(this));
                            }
                        } else {
                            boolean bl = GenericObject.class.isAssignableFrom((Class<?>)serializable);
                            if (bl) {
                                if (field.get(this) != null) {
                                    this.sprint(((GenericObject)field.get(this)).debugDump(this.indentation + 1));
                                } else {
                                    this.sprint("<null>");
                                }
                            } else if (GenericObjectList.class.isAssignableFrom((Class<?>)serializable)) {
                                if (field.get(this) != null) {
                                    this.sprint(((GenericObjectList)field.get(this)).debugDump(this.indentation + 1));
                                } else {
                                    this.sprint("<null>");
                                }
                            } else {
                                if (field.get(this) != null) {
                                    serializable = new StringBuilder();
                                    ((StringBuilder)serializable).append(field.get(this).getClass().getName());
                                    ((StringBuilder)serializable).append(COLON);
                                    this.sprint(((StringBuilder)serializable).toString());
                                } else {
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append(((Class)serializable).getName());
                                    ((StringBuilder)charSequence).append(COLON);
                                    this.sprint(((StringBuilder)charSequence).toString());
                                }
                                this.sprint("{");
                                if (field.get(this) != null) {
                                    this.sprint(field.get(this).toString());
                                } else {
                                    this.sprint("<null>");
                                }
                                this.sprint("}");
                            }
                        }
                    }
                    catch (Exception exception) {
                        InternalErrorHandler.handleException(exception);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
            }
            ++n;
        } while (true);
    }

    public String debugDump(int n) {
        this.indentation = n;
        String string = this.debugDump();
        this.indentation = 0;
        return string;
    }

    public abstract String encode();

    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.encode());
        return stringBuffer;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        Class<?> class_ = this.getClass();
        Class<?> class_2 = object.getClass();
        do {
            Field[] arrfield = class_.getDeclaredFields();
            Field[] arrfield2 = class_2.getDeclaredFields();
            for (int i = 0; i < arrfield.length; ++i) {
                Field field;
                Field field2;
                block28 : {
                    block27 : {
                        block26 : {
                            block25 : {
                                block18 : {
                                    Object object2;
                                    block24 : {
                                        block23 : {
                                            block22 : {
                                                block21 : {
                                                    block20 : {
                                                        block19 : {
                                                            field = arrfield[i];
                                                            field2 = arrfield2[i];
                                                            if ((field.getModifiers() & 2) == 2) continue;
                                                            object2 = field.getType();
                                                            String string = field.getName();
                                                            if (string.compareTo("stringRepresentation") == 0 || string.compareTo("indentation") == 0) continue;
                                                            if (!((Class)object2).isPrimitive()) break block18;
                                                            if (((String)(object2 = ((Class)object2).toString())).compareTo("int") != 0) break block19;
                                                            if (field.getInt(this) == field2.getInt(object)) continue;
                                                            return false;
                                                        }
                                                        if (((String)object2).compareTo("short") != 0) break block20;
                                                        if (field.getShort(this) == field2.getShort(object)) continue;
                                                        return false;
                                                    }
                                                    if (((String)object2).compareTo("char") != 0) break block21;
                                                    if (field.getChar(this) == field2.getChar(object)) continue;
                                                    return false;
                                                }
                                                if (((String)object2).compareTo("long") != 0) break block22;
                                                if (field.getLong(this) == field2.getLong(object)) continue;
                                                return false;
                                            }
                                            if (((String)object2).compareTo("boolean") != 0) break block23;
                                            if (field.getBoolean(this) == field2.getBoolean(object)) continue;
                                            return false;
                                        }
                                        if (((String)object2).compareTo("double") != 0) break block24;
                                        if (field.getDouble(this) == field2.getDouble(object)) continue;
                                        return false;
                                    }
                                    if (((String)object2).compareTo("float") != 0 || field.getFloat(this) == field2.getFloat(object)) continue;
                                    return false;
                                }
                                if (field2.get(object) != field.get(this)) break block25;
                                return true;
                            }
                            if (field.get(this) != null) break block26;
                            return false;
                        }
                        if (field2.get(object) != null) break block27;
                        return false;
                    }
                    if (field2.get(object) != null || field.get(this) == null) break block28;
                    return false;
                }
                try {
                    boolean bl = field.get(this).equals(field2.get(object));
                    if (bl) continue;
                    return false;
                }
                catch (IllegalAccessException illegalAccessException) {
                    InternalErrorHandler.handleException(illegalAccessException);
                }
            }
            if (class_.equals(GenericObject.class)) {
                return true;
            }
            class_ = class_.getSuperclass();
            class_2 = class_2.getSuperclass();
        } while (true);
    }

    protected String getIndentation() {
        char[] arrc = new char[this.indentation];
        Arrays.fill(arrc, ' ');
        return new String(arrc);
    }

    public Match getMatcher() {
        return this.matchExpression;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean match(Object object) {
        if (object == null) {
            return true;
        }
        bl = this.getClass().equals(object.getClass());
        bl2 = false;
        if (!bl) {
            return false;
        }
        genericObject = (GenericObject)object;
        arrfield = this.getClass().getDeclaredFields();
        arrfield2 = object.getClass().getDeclaredFields();
        i = 0;
        while (i < arrfield.length) {
            block17 : {
                block18 : {
                    object3 = arrfield[i];
                    object = arrfield2[i];
                    if ((object3.getModifiers() & 2) == 2) break block17;
                    object2 = object3.getType();
                    string = object3.getName();
                    if (string.compareTo("stringRepresentation") == 0 || string.compareTo("indentation") == 0) break block17;
                    if (object2.isPrimitive()) {
                        if ((object2 = object2.toString()).compareTo("int") == 0 ? object3.getInt(this) != object.getInt(genericObject) : (object2.compareTo("short") == 0 ? object3.getShort(this) != object.getShort(genericObject) : (object2.compareTo("char") == 0 ? object3.getChar(this) != object.getChar(genericObject) : (object2.compareTo("long") == 0 ? object3.getLong(this) != object.getLong(genericObject) : (object2.compareTo("boolean") == 0 ? object3.getBoolean(this) != object.getBoolean(genericObject) : (object2.compareTo("double") == 0 ? object3.getDouble(this) != object.getDouble(genericObject) : object2.compareTo("float") == 0 && object3.getFloat(this) != object.getFloat(genericObject))))))) {
                            return bl2;
                        }
                        break block17;
                    }
                    object3 = object3.get(this);
                    object2 = object.get(genericObject);
                    if (object2 != null && object3 == null) {
                        return bl2;
                    }
                    if (object2 == null && object3 != null || object2 == null && object3 == null) break block17;
                    if (!(object2 instanceof String) || !(object3 instanceof String)) ** GOTO lbl38
                    object = ((String)object2).trim();
                    try {
                        if (object.equals("")) {
                            bl2 = false;
                        } else {
                            if (((String)object3).compareToIgnoreCase((String)object2) != 0) {
                                return false;
                            }
                            bl2 = false;
                        }
                        break block17;
lbl38: // 1 sources:
                        if (GenericObject.isMySubclass(object3.getClass()) && !((GenericObject)object3).match(object2)) {
                            return false;
                        }
                        if (GenericObjectList.isMySubclass(object3.getClass())) {
                            bl2 = ((GenericObjectList)object3).match(object2);
                            if (!bl2) {
                                return false;
                            }
                            bl2 = false;
                        } else {
                            bl2 = false;
                        }
                        break block17;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        bl2 = false;
                        break block18;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
                InternalErrorHandler.handleException((Exception)object);
            }
            ++i;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void merge(Object object) {
        if (object == null) {
            return;
        }
        if (!object.getClass().equals(this.getClass())) {
            throw new IllegalArgumentException("Bad override object");
        }
        Class<?> class_ = this.getClass();
        do {
            Field[] arrfield = class_.getDeclaredFields();
            for (int i = 0; i < arrfield.length; ++i) {
                Field field = arrfield[i];
                int n = field.getModifiers();
                if (Modifier.isPrivate(n) || Modifier.isStatic(n) || Modifier.isInterface(n)) continue;
                Object object2 = field.getType();
                Object object3 = ((Class)object2).toString();
                try {
                    if (((Class)object2).isPrimitive()) {
                        if (((String)object3).compareTo("int") == 0) {
                            field.setInt(this, field.getInt(object));
                            continue;
                        }
                        if (((String)object3).compareTo("short") == 0) {
                            field.setShort(this, field.getShort(object));
                            continue;
                        }
                        if (((String)object3).compareTo("char") == 0) {
                            field.setChar(this, field.getChar(object));
                            continue;
                        }
                        if (((String)object3).compareTo("long") == 0) {
                            field.setLong(this, field.getLong(object));
                            continue;
                        }
                        if (((String)object3).compareTo("boolean") == 0) {
                            field.setBoolean(this, field.getBoolean(object));
                            continue;
                        }
                        if (((String)object3).compareTo("double") == 0) {
                            field.setDouble(this, field.getDouble(object));
                            continue;
                        }
                        if (((String)object3).compareTo("float") != 0) continue;
                        field.setFloat(this, field.getFloat(object));
                        continue;
                    }
                    object3 = field.get(this);
                    object2 = field.get(object);
                    if (object2 == null) continue;
                    if (object3 == null) {
                        field.set(this, object2);
                        continue;
                    }
                    if (object3 instanceof GenericObject) {
                        ((GenericObject)object3).merge(object2);
                        continue;
                    }
                    field.set(this, object2);
                    continue;
                }
                catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        } while (!(class_ = class_.getSuperclass()).equals(GenericObject.class));
    }

    public void setMatcher(Match match) {
        if (match != null) {
            this.matchExpression = match;
            return;
        }
        throw new IllegalArgumentException("null arg!");
    }

    protected void sprint(char c) {
        this.sprint(String.valueOf(c));
    }

    protected void sprint(double d) {
        this.sprint(String.valueOf(d));
    }

    protected void sprint(float f) {
        this.sprint(String.valueOf(f));
    }

    protected void sprint(int n) {
        this.sprint(String.valueOf(n));
    }

    protected void sprint(long l) {
        this.sprint(String.valueOf(l));
    }

    protected void sprint(Object object) {
        this.sprint(object.toString());
    }

    protected void sprint(String charSequence) {
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.stringRepresentation);
            ((StringBuilder)charSequence).append(this.getIndentation());
            this.stringRepresentation = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.stringRepresentation);
            ((StringBuilder)charSequence).append("<null>\n");
            this.stringRepresentation = ((StringBuilder)charSequence).toString();
            return;
        }
        if (((String)charSequence).compareTo("}") == 0 || ((String)charSequence).compareTo("]") == 0) {
            --this.indentation;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRepresentation);
        stringBuilder.append(this.getIndentation());
        this.stringRepresentation = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRepresentation);
        stringBuilder.append((String)charSequence);
        this.stringRepresentation = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRepresentation);
        stringBuilder.append("\n");
        this.stringRepresentation = stringBuilder.toString();
        if (((String)charSequence).compareTo("{") == 0 || ((String)charSequence).compareTo("[") == 0) {
            ++this.indentation;
        }
    }

    protected void sprint(short s) {
        this.sprint(String.valueOf(s));
    }

    protected void sprint(boolean bl) {
        this.sprint(String.valueOf(bl));
    }
}

