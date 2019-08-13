/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import android.view.inspector.InspectionCompanion;

public interface InspectionCompanionProvider {
    public <T> InspectionCompanion<T> provide(Class<T> var1);
}

