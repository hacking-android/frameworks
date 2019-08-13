/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import java.lang.reflect.Field;

public abstract class MessageObject
extends GenericObject {
    protected MessageObject() {
    }

    public String dbgPrint(int n) {
        int n2 = this.indentation;
        this.indentation = n;
        String string = this.toString();
        this.indentation = n2;
        return string;
    }

    @Override
    public void dbgPrint() {
        super.dbgPrint();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
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
            if (field.getModifiers() != 2) {
                Object object = field.getType();
                CharSequence charSequence = field.getName();
                if (((String)charSequence).compareTo("stringRepresentation") != 0 && ((String)charSequence).compareTo("indentation") != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append(":");
                    this.sprint(stringBuilder.toString());
                    try {
                        if (((Class)object).isPrimitive()) {
                            object = ((Class)object).toString();
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append((String)object);
                            ((StringBuilder)charSequence).append(":");
                            this.sprint(((StringBuilder)charSequence).toString());
                            if (((String)object).compareTo("int") == 0) {
                                this.sprint(field.getInt(this));
                            } else if (((String)object).compareTo("short") == 0) {
                                this.sprint(field.getShort(this));
                            } else if (((String)object).compareTo("char") == 0) {
                                this.sprint(field.getChar(this));
                            } else if (((String)object).compareTo("long") == 0) {
                                this.sprint(field.getLong(this));
                            } else if (((String)object).compareTo("boolean") == 0) {
                                this.sprint(field.getBoolean(this));
                            } else if (((String)object).compareTo("double") == 0) {
                                this.sprint(field.getDouble(this));
                            } else if (((String)object).compareTo("float") == 0) {
                                this.sprint(field.getFloat(this));
                            }
                        } else {
                            boolean bl = GenericObject.class.isAssignableFrom((Class<?>)object);
                            if (bl) {
                                if (field.get(this) != null) {
                                    this.sprint(((GenericObject)field.get(this)).debugDump(this.indentation + 1));
                                } else {
                                    this.sprint("<null>");
                                }
                            } else if (GenericObjectList.class.isAssignableFrom((Class<?>)object)) {
                                if (field.get(this) != null) {
                                    this.sprint(((GenericObjectList)field.get(this)).debugDump(this.indentation + 1));
                                } else {
                                    this.sprint("<null>");
                                }
                            } else {
                                if (field.get(this) != null) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append(field.get(this).getClass().getName());
                                    ((StringBuilder)object).append(":");
                                    this.sprint(((StringBuilder)object).toString());
                                } else {
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append(((Class)object).getName());
                                    ((StringBuilder)charSequence).append(":");
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
                    catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
            }
            ++n;
        } while (true);
    }

    @Override
    public abstract String encode();
}

