/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.ExtensionField;
import com.android.org.bouncycastle.math.field.Polynomial;

public interface PolynomialExtensionField
extends ExtensionField {
    public Polynomial getMinimalPolynomial();
}

