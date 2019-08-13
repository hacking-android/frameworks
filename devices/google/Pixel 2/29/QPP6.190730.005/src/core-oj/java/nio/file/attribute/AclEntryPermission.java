/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

public final class AclEntryPermission
extends Enum<AclEntryPermission> {
    private static final /* synthetic */ AclEntryPermission[] $VALUES;
    public static final AclEntryPermission ADD_FILE;
    public static final AclEntryPermission ADD_SUBDIRECTORY;
    public static final /* enum */ AclEntryPermission APPEND_DATA;
    public static final /* enum */ AclEntryPermission DELETE;
    public static final /* enum */ AclEntryPermission DELETE_CHILD;
    public static final /* enum */ AclEntryPermission EXECUTE;
    public static final AclEntryPermission LIST_DIRECTORY;
    public static final /* enum */ AclEntryPermission READ_ACL;
    public static final /* enum */ AclEntryPermission READ_ATTRIBUTES;
    public static final /* enum */ AclEntryPermission READ_DATA;
    public static final /* enum */ AclEntryPermission READ_NAMED_ATTRS;
    public static final /* enum */ AclEntryPermission SYNCHRONIZE;
    public static final /* enum */ AclEntryPermission WRITE_ACL;
    public static final /* enum */ AclEntryPermission WRITE_ATTRIBUTES;
    public static final /* enum */ AclEntryPermission WRITE_DATA;
    public static final /* enum */ AclEntryPermission WRITE_NAMED_ATTRS;
    public static final /* enum */ AclEntryPermission WRITE_OWNER;

    static {
        READ_DATA = new AclEntryPermission();
        WRITE_DATA = new AclEntryPermission();
        APPEND_DATA = new AclEntryPermission();
        READ_NAMED_ATTRS = new AclEntryPermission();
        WRITE_NAMED_ATTRS = new AclEntryPermission();
        EXECUTE = new AclEntryPermission();
        DELETE_CHILD = new AclEntryPermission();
        READ_ATTRIBUTES = new AclEntryPermission();
        WRITE_ATTRIBUTES = new AclEntryPermission();
        DELETE = new AclEntryPermission();
        READ_ACL = new AclEntryPermission();
        WRITE_ACL = new AclEntryPermission();
        WRITE_OWNER = new AclEntryPermission();
        SYNCHRONIZE = new AclEntryPermission();
        AclEntryPermission aclEntryPermission = READ_DATA;
        AclEntryPermission aclEntryPermission2 = WRITE_DATA;
        AclEntryPermission aclEntryPermission3 = APPEND_DATA;
        $VALUES = new AclEntryPermission[]{aclEntryPermission, aclEntryPermission2, aclEntryPermission3, READ_NAMED_ATTRS, WRITE_NAMED_ATTRS, EXECUTE, DELETE_CHILD, READ_ATTRIBUTES, WRITE_ATTRIBUTES, DELETE, READ_ACL, WRITE_ACL, WRITE_OWNER, SYNCHRONIZE};
        LIST_DIRECTORY = aclEntryPermission;
        ADD_FILE = aclEntryPermission2;
        ADD_SUBDIRECTORY = aclEntryPermission3;
    }

    public static AclEntryPermission valueOf(String string) {
        return Enum.valueOf(AclEntryPermission.class, string);
    }

    public static AclEntryPermission[] values() {
        return (AclEntryPermission[])$VALUES.clone();
    }
}

