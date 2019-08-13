/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.FiniteField;

public interface ExtensionField
extends FiniteField {
    public int getDegree();

    public FiniteField getSubfield();
}

