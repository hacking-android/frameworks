/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.reflect.AnnotatedElements
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;
import libcore.reflect.AnnotatedElements;

public final class Parameter
implements AnnotatedElement {
    private final Executable executable;
    private final int index;
    private final int modifiers;
    private final String name;
    private volatile transient Class<?> parameterClassCache = null;
    private volatile transient Type parameterTypeCache = null;

    Parameter(String string, int n, Executable executable, int n2) {
        this.name = string;
        this.modifiers = n;
        this.executable = executable;
        this.index = n2;
    }

    @FastNative
    private static native <A extends Annotation> A getAnnotationNative(Executable var0, int var1, Class<A> var2);

    public boolean equals(Object object) {
        boolean bl = object instanceof Parameter;
        boolean bl2 = false;
        if (bl) {
            object = (Parameter)object;
            bl = bl2;
            if (((Parameter)object).executable.equals(this.executable)) {
                bl = bl2;
                if (((Parameter)object).index == this.index) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        Objects.requireNonNull(class_);
        return Parameter.getAnnotationNative(this.executable, this.index, class_);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> class_) {
        return AnnotatedElements.getDirectOrIndirectAnnotationsByType((AnnotatedElement)this, class_);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> class_) {
        return this.getAnnotation(class_);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.executable.getParameterAnnotations()[this.index];
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> class_) {
        return this.getAnnotationsByType(class_);
    }

    public Executable getDeclaringExecutable() {
        return this.executable;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public String getName() {
        CharSequence charSequence = this.name;
        if (charSequence != null && !((String)charSequence).equals("")) {
            return this.name;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("arg");
        ((StringBuilder)charSequence).append(this.index);
        return ((StringBuilder)charSequence).toString();
    }

    public Type getParameterizedType() {
        Type type;
        Type type2 = type = this.parameterTypeCache;
        if (type == null) {
            this.parameterTypeCache = type2 = this.executable.getAllGenericParameterTypes()[this.index];
        }
        return type2;
    }

    String getRealName() {
        return this.name;
    }

    public Class<?> getType() {
        Class<?> class_;
        Class<?> class_2 = class_ = this.parameterClassCache;
        if (class_ == null) {
            this.parameterClassCache = class_2 = this.executable.getParameterTypes()[this.index];
        }
        return class_2;
    }

    public int hashCode() {
        return this.executable.hashCode() ^ this.index;
    }

    public boolean isImplicit() {
        return Modifier.isMandated(this.getModifiers());
    }

    public boolean isNamePresent() {
        boolean bl = this.executable.hasRealParameterData() && this.name != null;
        return bl;
    }

    public boolean isSynthetic() {
        return Modifier.isSynthetic(this.getModifiers());
    }

    public boolean isVarArgs() {
        boolean bl = this.executable.isVarArgs();
        boolean bl2 = true;
        if (!bl || this.index != this.executable.getParameterCount() - 1) {
            bl2 = false;
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.getParameterizedType().getTypeName();
        stringBuilder.append(Modifier.toString(this.getModifiers()));
        if (this.modifiers != 0) {
            stringBuilder.append(' ');
        }
        if (this.isVarArgs()) {
            stringBuilder.append(string.replaceFirst("\\[\\]$", "..."));
        } else {
            stringBuilder.append(string);
        }
        stringBuilder.append(' ');
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }
}

