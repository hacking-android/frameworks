/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.Match;
import java.lang.reflect.Field;

public abstract class NetObject
extends GenericObject {
    protected static final String CORE_PACKAGE = "gov.nist.core";
    protected static final String GRUU = "gr";
    protected static final String LR = "lr";
    protected static final String MADDR = "maddr";
    protected static final String METHOD = "method";
    protected static final String NET_PACKAGE = "gov.nist.javax.sip.address";
    protected static final String PARSER_PACKAGE = "gov.nist.javax.sip.parser";
    protected static final String PHONE = "phone";
    protected static final String SIP = "sip";
    protected static final String SIPS = "sips";
    protected static final String TCP = "tcp";
    protected static final String TLS = "tls";
    protected static final String TRANSPORT = "transport";
    protected static final String TTL = "ttl";
    protected static final String UDP = "udp";
    protected static final String USER = "user";
    protected static final long serialVersionUID = 6149926203633320729L;

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
                String string = field.getName();
                if (string.compareTo("stringRepresentation") != 0 && string.compareTo("indentation") != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append(":");
                    this.sprint(stringBuilder.toString());
                    try {
                        if (((Class)object).isPrimitive()) {
                            object = ((Class)object).toString();
                            stringBuilder = new StringBuilder();
                            stringBuilder.append((String)object);
                            stringBuilder.append(":");
                            this.sprint(stringBuilder.toString());
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
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(((Class)object).getName());
                                    stringBuilder.append(":");
                                    this.sprint(stringBuilder.toString());
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
    public boolean equals(Object object) {
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
                block23 : {
                    block22 : {
                        block15 : {
                            Object object2;
                            block21 : {
                                block20 : {
                                    block19 : {
                                        block18 : {
                                            block17 : {
                                                block16 : {
                                                    field = arrfield[i];
                                                    field2 = arrfield2[i];
                                                    if ((field.getModifiers() & 2) == 2) continue;
                                                    object2 = field.getType();
                                                    String string = field.getName();
                                                    if (string.compareTo("stringRepresentation") == 0 || string.compareTo("indentation") == 0) continue;
                                                    if (!((Class)object2).isPrimitive()) break block15;
                                                    if (((String)(object2 = ((Class)object2).toString())).compareTo("int") != 0) break block16;
                                                    if (field.getInt(this) == field2.getInt(object)) continue;
                                                    return false;
                                                }
                                                if (((String)object2).compareTo("short") != 0) break block17;
                                                if (field.getShort(this) == field2.getShort(object)) continue;
                                                return false;
                                            }
                                            if (((String)object2).compareTo("char") != 0) break block18;
                                            if (field.getChar(this) == field2.getChar(object)) continue;
                                            return false;
                                        }
                                        if (((String)object2).compareTo("long") != 0) break block19;
                                        if (field.getLong(this) == field2.getLong(object)) continue;
                                        return false;
                                    }
                                    if (((String)object2).compareTo("boolean") != 0) break block20;
                                    if (field.getBoolean(this) == field2.getBoolean(object)) continue;
                                    return false;
                                }
                                if (((String)object2).compareTo("double") != 0) break block21;
                                if (field.getDouble(this) == field2.getDouble(object)) continue;
                                return false;
                            }
                            if (((String)object2).compareTo("float") != 0 || field.getFloat(this) == field2.getFloat(object)) continue;
                            return false;
                        }
                        if (field2.get(object) == field.get(this)) continue;
                        if (field.get(this) != null || field2.get(object) == null) break block22;
                        return false;
                    }
                    if (field2.get(object) != null || field.get(object) == null) break block23;
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
            if (class_.equals(NetObject.class)) {
                return true;
            }
            class_ = class_.getSuperclass();
            class_2 = class_2.getSuperclass();
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
    public boolean match(Object class_) {
        if (class_ == null) {
            return true;
        }
        bl = this.getClass().equals(class_.getClass());
        bl2 = false;
        if (!bl) {
            return false;
        }
        genericObject = (GenericObject)class_;
        class_ = class_.getClass();
        class_2 = this.getClass();
        do {
            arrfield = class_2.getDeclaredFields();
            arrfield2 = class_.getDeclaredFields();
            for (i = 0; i < arrfield.length; ++i) {
                block18 : {
                    object2 = arrfield[i];
                    object = arrfield2[i];
                    if ((object2.getModifiers() & 2) == 2) continue;
                    object3 = object2.getType();
                    string = object2.getName();
                    if (string.compareTo("stringRepresentation") == 0 || string.compareTo("indentation") == 0) continue;
                    if (object3.isPrimitive()) {
                        if (!((object3 = object3.toString()).compareTo("int") == 0 ? object2.getInt(this) != object.getInt(genericObject) : (object3.compareTo("short") == 0 ? object2.getShort(this) != object.getShort(genericObject) : (object3.compareTo("char") == 0 ? object2.getChar(this) != object.getChar(genericObject) : (object3.compareTo("long") == 0 ? object2.getLong(this) != object.getLong(genericObject) : (object3.compareTo("boolean") == 0 ? object2.getBoolean(this) != object.getBoolean(genericObject) : (object3.compareTo("double") == 0 ? object2.getDouble(this) != object.getDouble(genericObject) : object3.compareTo("float") == 0 && object2.getFloat(this) != object.getFloat(genericObject)))))))) continue;
                        return bl2;
                    }
                    object2 = object2.get(this);
                    if ((object = object.get(genericObject)) != null && object2 == null) {
                        return bl2;
                    }
                    if (object == null && object2 != null || object == null && object2 == null) continue;
                    if (!(object instanceof String) || !(object2 instanceof String)) ** GOTO lbl38
                    object3 = (String)object;
                    try {
                        if (object3.equals("")) {
                            bl2 = false;
                            continue;
                        }
                        if (((String)object2).compareToIgnoreCase((String)object) != 0) {
                            return false;
                        }
                        bl2 = false;
                        continue;
lbl38: // 1 sources:
                        if (GenericObject.isMySubclass(object2.getClass()) && GenericObject.isMySubclass(object.getClass()) && object2.getClass().equals(object.getClass()) && ((GenericObject)object).getMatcher() != null) {
                            object2 = ((GenericObject)object2).encode();
                            if (!((GenericObject)object).getMatcher().match((String)object2)) {
                                return false;
                            }
                            bl2 = false;
                            continue;
                        }
                        if (GenericObject.isMySubclass(object2.getClass()) && !((GenericObject)object2).match(object)) {
                            return false;
                        }
                        if (GenericObjectList.isMySubclass(object2.getClass())) {
                            bl2 = ((GenericObjectList)object2).match(object);
                            if (!bl2) {
                                return false;
                            }
                            bl2 = false;
                            continue;
                        }
                        bl2 = false;
                        continue;
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
            if (class_2.equals(NetObject.class)) {
                return true;
            }
            class_2 = class_2.getSuperclass();
            class_ = class_.getSuperclass();
        } while (true);
    }

    public String toString() {
        return this.encode();
    }
}

