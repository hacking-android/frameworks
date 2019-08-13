/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralSubtree;
import com.android.org.bouncycastle.asn1.x509.NameConstraintValidatorException;

public interface NameConstraintValidator {
    public void addExcludedSubtree(GeneralSubtree var1);

    public void checkExcluded(GeneralName var1) throws NameConstraintValidatorException;

    public void checkPermitted(GeneralName var1) throws NameConstraintValidatorException;

    public void intersectEmptyPermittedSubtree(int var1);

    public void intersectPermittedSubtree(GeneralSubtree var1);

    public void intersectPermittedSubtree(GeneralSubtree[] var1);
}

