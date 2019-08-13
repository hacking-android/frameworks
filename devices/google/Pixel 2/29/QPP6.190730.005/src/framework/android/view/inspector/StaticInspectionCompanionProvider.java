/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.InspectionCompanionProvider;

public class StaticInspectionCompanionProvider
implements InspectionCompanionProvider {
    private static final String COMPANION_SUFFIX = "$InspectionCompanion";

    @Override
    public <T> InspectionCompanion<T> provide(Class<T> object) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(((Class)object).getName());
        charSequence.append(COMPANION_SUFFIX);
        charSequence = charSequence.toString();
        try {
            object = ((Class)object).getClassLoader().loadClass((String)charSequence);
            if (InspectionCompanion.class.isAssignableFrom((Class<?>)object)) {
                object = (InspectionCompanion)((Class)object).newInstance();
                return object;
            }
            return null;
        }
        catch (InstantiationException instantiationException) {
            Throwable throwable = instantiationException.getCause();
            if (!(throwable instanceof RuntimeException)) {
                if (throwable instanceof Error) {
                    throw (Error)throwable;
                }
                throw new RuntimeException(throwable);
            }
            throw (RuntimeException)throwable;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }
}

