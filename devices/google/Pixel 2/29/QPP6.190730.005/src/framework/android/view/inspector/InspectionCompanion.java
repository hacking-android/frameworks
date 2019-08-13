/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

public interface InspectionCompanion<T> {
    public void mapProperties(PropertyMapper var1);

    public void readProperties(T var1, PropertyReader var2);

    public static class UninitializedPropertyMapException
    extends RuntimeException {
        public UninitializedPropertyMapException() {
            super("Unable to read properties of an inspectable before mapping their IDs.");
        }
    }

}

