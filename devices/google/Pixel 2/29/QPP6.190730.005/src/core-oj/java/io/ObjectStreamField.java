/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.lang.reflect.Field;
import sun.reflect.CallerSensitive;

public class ObjectStreamField
implements Comparable<Object> {
    private final Field field;
    private final String name;
    private int offset;
    private final String signature;
    private final Class<?> type;
    private final boolean unshared;

    public ObjectStreamField(String string, Class<?> class_) {
        this(string, class_, false);
    }

    public ObjectStreamField(String string, Class<?> class_, boolean bl) {
        this.offset = 0;
        if (string != null) {
            this.name = string;
            this.type = class_;
            this.unshared = bl;
            this.signature = ObjectStreamField.getClassSignature(class_).intern();
            this.field = null;
            return;
        }
        throw new NullPointerException();
    }

    ObjectStreamField(String string, String string2, boolean bl) {
        block5 : {
            block12 : {
                block6 : {
                    block7 : {
                        block8 : {
                            block9 : {
                                block10 : {
                                    block11 : {
                                        this.offset = 0;
                                        if (string == null) break block5;
                                        this.name = string;
                                        this.signature = string2.intern();
                                        this.unshared = bl;
                                        this.field = null;
                                        char c = string2.charAt(0);
                                        if (c == 'F') break block6;
                                        if (c == 'L') break block7;
                                        if (c == 'S') break block8;
                                        if (c == 'I') break block9;
                                        if (c == 'J') break block10;
                                        if (c == 'Z') break block11;
                                        if (c == '[') break block7;
                                        switch (c) {
                                            default: {
                                                throw new IllegalArgumentException("illegal signature");
                                            }
                                            case 'D': {
                                                this.type = Double.TYPE;
                                                break;
                                            }
                                            case 'C': {
                                                this.type = Character.TYPE;
                                                break;
                                            }
                                            case 'B': {
                                                this.type = Byte.TYPE;
                                                break;
                                            }
                                        }
                                        break block12;
                                    }
                                    this.type = Boolean.TYPE;
                                    break block12;
                                }
                                this.type = Long.TYPE;
                                break block12;
                            }
                            this.type = Integer.TYPE;
                            break block12;
                        }
                        this.type = Short.TYPE;
                        break block12;
                    }
                    this.type = Object.class;
                    break block12;
                }
                this.type = Float.TYPE;
            }
            return;
        }
        throw new NullPointerException();
    }

    ObjectStreamField(Field object, boolean bl, boolean bl2) {
        this.offset = 0;
        this.field = object;
        this.unshared = bl;
        this.name = ((Field)object).getName();
        Class<?> class_ = ((Field)object).getType();
        object = !bl2 && !class_.isPrimitive() ? Object.class : class_;
        this.type = object;
        this.signature = ObjectStreamField.getClassSignature(class_).intern();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String getClassSignature(Class<?> class_) {
        StringBuilder stringBuilder = new StringBuilder();
        while (class_.isArray()) {
            stringBuilder.append('[');
            class_ = class_.getComponentType();
        }
        if (class_.isPrimitive()) {
            if (class_ == Integer.TYPE) {
                stringBuilder.append('I');
                return stringBuilder.toString();
            } else if (class_ == Byte.TYPE) {
                stringBuilder.append('B');
                return stringBuilder.toString();
            } else if (class_ == Long.TYPE) {
                stringBuilder.append('J');
                return stringBuilder.toString();
            } else if (class_ == Float.TYPE) {
                stringBuilder.append('F');
                return stringBuilder.toString();
            } else if (class_ == Double.TYPE) {
                stringBuilder.append('D');
                return stringBuilder.toString();
            } else if (class_ == Short.TYPE) {
                stringBuilder.append('S');
                return stringBuilder.toString();
            } else if (class_ == Character.TYPE) {
                stringBuilder.append('C');
                return stringBuilder.toString();
            } else if (class_ == Boolean.TYPE) {
                stringBuilder.append('Z');
                return stringBuilder.toString();
            } else {
                if (class_ != Void.TYPE) throw new InternalError();
                stringBuilder.append('V');
            }
            return stringBuilder.toString();
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append('L');
            stringBuilder2.append(class_.getName().replace('.', '/'));
            stringBuilder2.append(';');
            stringBuilder.append(stringBuilder2.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Object object) {
        object = (ObjectStreamField)object;
        boolean bl = this.isPrimitive();
        if (bl != ((ObjectStreamField)object).isPrimitive()) {
            int n = bl ? -1 : 1;
            return n;
        }
        return this.name.compareTo(((ObjectStreamField)object).name);
    }

    Field getField() {
        return this.field;
    }

    public String getName() {
        return this.name;
    }

    public int getOffset() {
        return this.offset;
    }

    String getSignature() {
        return this.signature;
    }

    @CallerSensitive
    public Class<?> getType() {
        return this.type;
    }

    public char getTypeCode() {
        return this.signature.charAt(0);
    }

    public String getTypeString() {
        String string = this.isPrimitive() ? null : this.signature;
        return string;
    }

    public boolean isPrimitive() {
        String string = this.signature;
        boolean bl = false;
        char c = string.charAt(0);
        boolean bl2 = bl;
        if (c != 'L') {
            bl2 = bl;
            if (c != '[') {
                bl2 = true;
            }
        }
        return bl2;
    }

    public boolean isUnshared() {
        return this.unshared;
    }

    protected void setOffset(int n) {
        this.offset = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.signature);
        stringBuilder.append(' ');
        stringBuilder.append(this.name);
        return stringBuilder.toString();
    }
}

