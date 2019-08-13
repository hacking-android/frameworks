/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

public class DriverPropertyInfo {
    public String[] choices = null;
    public String description = null;
    public String name;
    public boolean required = false;
    public String value = null;

    public DriverPropertyInfo(String string, String string2) {
        this.name = string;
        this.value = string2;
    }
}

