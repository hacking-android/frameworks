/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class FtpDirEntry {
    private Date created = null;
    private HashMap<String, String> facts = new HashMap();
    private String group = null;
    private Date lastModified = null;
    private final String name;
    private boolean[][] permissions = null;
    private long size = -1L;
    private Type type = Type.FILE;
    private String user = null;

    private FtpDirEntry() {
        this.name = null;
    }

    public FtpDirEntry(String string) {
        this.name = string;
    }

    public FtpDirEntry addFact(String string, String string2) {
        this.facts.put(string.toLowerCase(), string2);
        return this;
    }

    public boolean canExexcute(Permission permission) {
        boolean[][] arrbl = this.permissions;
        if (arrbl != null) {
            return arrbl[permission.value][2];
        }
        return false;
    }

    public boolean canRead(Permission permission) {
        boolean[][] arrbl = this.permissions;
        if (arrbl != null) {
            return arrbl[permission.value][0];
        }
        return false;
    }

    public boolean canWrite(Permission permission) {
        boolean[][] arrbl = this.permissions;
        if (arrbl != null) {
            return arrbl[permission.value][1];
        }
        return false;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getFact(String string) {
        return this.facts.get(string.toLowerCase());
    }

    public String getGroup() {
        return this.group;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public Type getType() {
        return this.type;
    }

    public String getUser() {
        return this.user;
    }

    public FtpDirEntry setCreated(Date date) {
        this.created = date;
        return this;
    }

    public FtpDirEntry setGroup(String string) {
        this.group = string;
        return this;
    }

    public FtpDirEntry setLastModified(Date date) {
        this.lastModified = date;
        return this;
    }

    public FtpDirEntry setPermissions(boolean[][] arrbl) {
        this.permissions = arrbl;
        return this;
    }

    public FtpDirEntry setSize(long l) {
        this.size = l;
        return this;
    }

    public FtpDirEntry setType(Type type) {
        this.type = type;
        return this;
    }

    public FtpDirEntry setUser(String string) {
        this.user = string;
        return this;
    }

    public String toString() {
        if (this.lastModified == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name);
            stringBuilder.append(" [");
            stringBuilder.append((Object)this.type);
            stringBuilder.append("] (");
            stringBuilder.append(this.user);
            stringBuilder.append(" / ");
            stringBuilder.append(this.group);
            stringBuilder.append(") ");
            stringBuilder.append(this.size);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(" [");
        stringBuilder.append((Object)this.type);
        stringBuilder.append("] (");
        stringBuilder.append(this.user);
        stringBuilder.append(" / ");
        stringBuilder.append(this.group);
        stringBuilder.append(") {");
        stringBuilder.append(this.size);
        stringBuilder.append("} ");
        stringBuilder.append(DateFormat.getDateInstance().format(this.lastModified));
        return stringBuilder.toString();
    }

    public static enum Permission {
        USER(0),
        GROUP(1),
        OTHERS(2);
        
        int value;

        private Permission(int n2) {
            this.value = n2;
        }
    }

    public static enum Type {
        FILE,
        DIR,
        PDIR,
        CDIR,
        LINK;
        
    }

}

