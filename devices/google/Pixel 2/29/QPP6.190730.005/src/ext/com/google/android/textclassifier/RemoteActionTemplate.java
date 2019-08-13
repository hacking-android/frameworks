/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.textclassifier;

import com.google.android.textclassifier.NamedVariant;

public final class RemoteActionTemplate {
    public final String action;
    public final String[] category;
    public final String data;
    public final String description;
    public final String descriptionWithAppName;
    public final NamedVariant[] extras;
    public final Integer flags;
    public final String packageName;
    public final Integer requestCode;
    public final String titleWithEntity;
    public final String titleWithoutEntity;
    public final String type;

    public RemoteActionTemplate(String string, String string2, String string3, String string4, String string5, String string6, String string7, Integer n, String[] arrstring, String string8, NamedVariant[] arrnamedVariant, Integer n2) {
        this.titleWithoutEntity = string;
        this.titleWithEntity = string2;
        this.description = string3;
        this.descriptionWithAppName = string4;
        this.action = string5;
        this.data = string6;
        this.type = string7;
        this.flags = n;
        this.category = arrstring;
        this.packageName = string8;
        this.extras = arrnamedVariant;
        this.requestCode = n2;
    }
}

