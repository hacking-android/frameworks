/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.textclassifier;

public final class NamedVariant {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TYPE_BOOL = 5;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_FLOAT = 3;
    public static final int TYPE_INT = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_STRING = 6;
    private boolean boolValue;
    private double doubleValue;
    private float floatValue;
    private int intValue;
    private long longValue;
    private final String name;
    private String stringValue;
    private final int type;

    public NamedVariant(String string, double d) {
        this.name = string;
        this.doubleValue = d;
        this.type = 4;
    }

    public NamedVariant(String string, float f) {
        this.name = string;
        this.floatValue = f;
        this.type = 3;
    }

    public NamedVariant(String string, int n) {
        this.name = string;
        this.intValue = n;
        this.type = 1;
    }

    public NamedVariant(String string, long l) {
        this.name = string;
        this.longValue = l;
        this.type = 2;
    }

    public NamedVariant(String string, String string2) {
        this.name = string;
        this.stringValue = string2;
        this.type = 6;
    }

    public NamedVariant(String string, boolean bl) {
        this.name = string;
        this.boolValue = bl;
        this.type = 5;
    }

    public boolean getBool() {
        return this.boolValue;
    }

    public double getDouble() {
        return this.doubleValue;
    }

    public float getFloat() {
        return this.floatValue;
    }

    public int getInt() {
        return this.intValue;
    }

    public long getLong() {
        return this.longValue;
    }

    public String getName() {
        return this.name;
    }

    public String getString() {
        return this.stringValue;
    }

    public int getType() {
        return this.type;
    }
}

