/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.validation;

import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

public abstract class Schema {
    protected Schema() {
    }

    public abstract Validator newValidator();

    public abstract ValidatorHandler newValidatorHandler();
}

