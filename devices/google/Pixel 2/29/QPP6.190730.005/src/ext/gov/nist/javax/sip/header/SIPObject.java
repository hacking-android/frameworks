/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.Match;
import java.io.PrintStream;
import java.lang.reflect.Field;

public abstract class SIPObject
extends GenericObject {
    protected SIPObject() {
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
            if ((field.getModifiers() & 2) != 2) {
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
    public String debugDump(int n) {
        int n2 = this.indentation;
        this.indentation = n;
        String string = this.debugDump();
        this.indentation = n2;
        return string;
    }

    @Override
    public abstract String encode();

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.encode());
        return stringBuffer;
    }

    @Override
    public boolean equals(Object class_) {
        if (!this.getClass().equals(class_.getClass())) {
            return false;
        }
        SIPObject sIPObject = (SIPObject)((Object)class_);
        Class<?> class_2 = this.getClass();
        class_ = class_.getClass();
        do {
            Field[] arrfield = class_2.getDeclaredFields();
            if (!class_.equals(class_2)) {
                return false;
            }
            Field[] arrfield2 = class_.getDeclaredFields();
            for (int i = 0; i < arrfield.length; ++i) {
                Object object;
                Object object2;
                Field field;
                CharSequence charSequence;
                int n;
                block24 : {
                    block23 : {
                        block16 : {
                            block22 : {
                                block21 : {
                                    block20 : {
                                        block19 : {
                                            block18 : {
                                                block17 : {
                                                    field = arrfield[i];
                                                    object = arrfield2[i];
                                                    n = field.getModifiers();
                                                    if ((n & 2) == 2) continue;
                                                    object2 = field.getType();
                                                    charSequence = field.getName();
                                                    if (((String)charSequence).compareTo("stringRepresentation") == 0 || ((String)charSequence).compareTo("indentation") == 0) continue;
                                                    if (!((Class)object2).isPrimitive()) break block16;
                                                    if (((String)(object2 = ((Class)object2).toString())).compareTo("int") != 0) break block17;
                                                    if (field.getInt(this) == ((Field)object).getInt(sIPObject)) continue;
                                                    return false;
                                                }
                                                if (((String)object2).compareTo("short") != 0) break block18;
                                                if (field.getShort(this) == ((Field)object).getShort(sIPObject)) continue;
                                                return false;
                                            }
                                            if (((String)object2).compareTo("char") != 0) break block19;
                                            if (field.getChar(this) == ((Field)object).getChar(sIPObject)) continue;
                                            return false;
                                        }
                                        if (((String)object2).compareTo("long") != 0) break block20;
                                        if (field.getLong(this) == ((Field)object).getLong(sIPObject)) continue;
                                        return false;
                                    }
                                    if (((String)object2).compareTo("boolean") != 0) break block21;
                                    if (field.getBoolean(this) == ((Field)object).getBoolean(sIPObject)) continue;
                                    return false;
                                }
                                if (((String)object2).compareTo("double") != 0) break block22;
                                if (field.getDouble(this) == ((Field)object).getDouble(sIPObject)) continue;
                                return false;
                            }
                            if (((String)object2).compareTo("float") != 0 || field.getFloat(this) == ((Field)object).getFloat(sIPObject)) continue;
                            return false;
                        }
                        if (((Field)object).get(sIPObject) == field.get(this)) continue;
                        if (field.get(this) != null || ((Field)object).get(sIPObject) == null) break block23;
                        return false;
                    }
                    if (((Field)object).get(sIPObject) != null || field.get(this) == null) break block24;
                    return false;
                }
                try {
                    boolean bl = field.get(this).equals(((Field)object).get(sIPObject));
                    if (bl) continue;
                    return false;
                }
                catch (IllegalAccessException illegalAccessException) {
                    object2 = System.out;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("accessed field ");
                    ((StringBuilder)object).append((String)charSequence);
                    ((PrintStream)object2).println(((StringBuilder)object).toString());
                    object = System.out;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("modifier  ");
                    ((StringBuilder)charSequence).append(n);
                    ((PrintStream)object).println(((StringBuilder)charSequence).toString());
                    System.out.println("modifier.private  2");
                    InternalErrorHandler.handleException(illegalAccessException);
                }
            }
            if (class_2.equals(SIPObject.class)) {
                return true;
            }
            class_2 = class_2.getSuperclass();
            class_ = class_.getSuperclass();
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean match(Object var1_1) {
        if (var1_1 == null) {
            return true;
        }
        var2_2 = this.getClass().equals(var1_1.getClass());
        var3_3 = false;
        if (!var2_2) {
            return false;
        }
        var4_4 = (GenericObject)var1_1;
        var5_5 = this.getClass();
        var1_1 = var1_1.getClass();
        do {
            var6_6 = var5_5.getDeclaredFields();
            var7_7 = var1_1.getDeclaredFields();
            for (var8_8 = 0; var8_8 < var6_6.length; ++var8_8) {
                block25 : {
                    var9_9 = var6_6[var8_8];
                    var10_10 = var7_7[var8_8];
                    if ((var9_9.getModifiers() & 2) == 2) continue;
                    var11_13 = var9_9.getType();
                    var12_14 = var9_9.getName();
                    if (var12_14.compareTo("stringRepresentation") == 0 || var12_14.compareTo("indentation") == 0) continue;
                    if (var11_13.isPrimitive()) {
                        var12_14 = var11_13.toString();
                        if (var12_14.compareTo("int") == 0) {
                            if (var9_9.getInt(this) == var10_10.getInt(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("short") == 0) {
                            if (var9_9.getShort(this) == var10_10.getShort(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("char") == 0) {
                            if (var9_9.getChar(this) == var10_10.getChar(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("long") == 0) {
                            if (var9_9.getLong(this) == var10_10.getLong(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("boolean") == 0) {
                            if (var9_9.getBoolean(this) == var10_10.getBoolean(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("double") == 0) {
                            if (var9_9.getDouble(this) == var10_10.getDouble(var4_4)) continue;
                            return var3_3;
                        }
                        if (var12_14.compareTo("float") == 0) {
                            if (var9_9.getFloat(this) == var10_10.getFloat(var4_4)) continue;
                            return var3_3;
                        }
                        InternalErrorHandler.handleException("unknown type");
                        continue;
                    }
                    var9_9 = var9_9.get(this);
                    if ((var10_10 = var10_10.get(var4_4)) != null && var9_9 == null) {
                        return var3_3;
                    }
                    if (var10_10 == null && var9_9 != null || var10_10 == null && var9_9 == null) continue;
                    if (!(var10_10 instanceof String) || !(var9_9 instanceof String)) ** GOTO lbl60
                    var12_14 = ((String)var10_10).trim();
                    try {
                        if (var12_14.equals("")) {
                            var3_3 = false;
                            continue;
                        }
                        if (((String)var9_9).compareToIgnoreCase((String)var10_10) != 0) {
                            return false;
                        }
                        var3_3 = false;
                        continue;
lbl60: // 1 sources:
                        if (var10_10 != null && GenericObject.isMySubclass(var9_9.getClass()) && GenericObject.isMySubclass(var10_10.getClass()) && var9_9.getClass().equals(var10_10.getClass()) && ((GenericObject)var10_10).getMatcher() != null) {
                            var9_9 = ((GenericObject)var9_9).encode();
                            if (!((GenericObject)var10_10).getMatcher().match((String)var9_9)) {
                                return false;
                            }
                            var3_3 = false;
                            continue;
                        }
                        if (GenericObject.isMySubclass(var9_9.getClass()) && !((GenericObject)var9_9).match(var10_10)) {
                            return false;
                        }
                        if (GenericObjectList.isMySubclass(var9_9.getClass())) {
                            var3_3 = ((GenericObjectList)var9_9).match(var10_10);
                            if (!var3_3) {
                                return false;
                            }
                            var3_3 = false;
                            continue;
                        }
                        var3_3 = false;
                        continue;
                    }
                    catch (IllegalAccessException var10_11) {
                        var3_3 = false;
                        break block25;
                    }
                    catch (IllegalAccessException var10_12) {
                        // empty catch block
                    }
                }
                InternalErrorHandler.handleException((Exception)var10_10);
            }
            if (var5_5.equals(SIPObject.class)) {
                return true;
            }
            var5_5 = var5_5.getSuperclass();
            var1_1 = var1_1.getSuperclass();
        } while (true);
    }

    public String toString() {
        return this.encode();
    }
}

