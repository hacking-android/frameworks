/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import libcore.reflect.ListOfTypes;

public final class TypeVariableImpl<D extends GenericDeclaration>
implements TypeVariable<D> {
    private ListOfTypes bounds;
    private final GenericDeclaration declOfVarUser;
    private TypeVariableImpl<D> formalVar;
    private D genericDeclaration;
    private final String name;

    TypeVariableImpl(D d, String string) {
        this.name = string;
        this.declOfVarUser = d;
    }

    TypeVariableImpl(D d, String string, ListOfTypes listOfTypes) {
        this.genericDeclaration = d;
        this.name = string;
        this.bounds = listOfTypes;
        this.formalVar = this;
        this.declOfVarUser = null;
    }

    static TypeVariable findFormalVar(GenericDeclaration arrtypeVariable, String string) {
        for (TypeVariable<?> typeVariable : arrtypeVariable.getTypeParameters()) {
            if (!string.equals(typeVariable.getName())) continue;
            return typeVariable;
        }
        return null;
    }

    private static GenericDeclaration nextLayer(GenericDeclaration executable) {
        if (executable instanceof Class) {
            Class class_ = (Class)((Object)executable);
            executable = class_.getEnclosingMethod();
            if (executable == null) {
                executable = class_.getEnclosingConstructor();
            }
            if (executable != null) {
                return executable;
            }
            return class_.getEnclosingClass();
        }
        if (executable instanceof Method) {
            return ((Method)executable).getDeclaringClass();
        }
        if (executable instanceof Constructor) {
            return ((Constructor)executable).getDeclaringClass();
        }
        throw new AssertionError();
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof TypeVariable;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (TypeVariable)object;
            if (!this.getName().equals(object.getName()) || !this.getGenericDeclaration().equals(object.getGenericDeclaration())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public Type[] getBounds() {
        this.resolve();
        return (Type[])this.bounds.getResolvedTypes().clone();
    }

    @Override
    public D getGenericDeclaration() {
        this.resolve();
        return this.genericDeclaration;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.getName().hashCode() * 31 + this.getGenericDeclaration().hashCode();
    }

    void resolve() {
        TypeVariable typeVariable;
        if (this.formalVar != null) {
            return;
        }
        AnnotatedElement annotatedElement = this.declOfVarUser;
        while ((typeVariable = TypeVariableImpl.findFormalVar(annotatedElement, this.name)) == null) {
            if ((annotatedElement = TypeVariableImpl.nextLayer(annotatedElement)) == null) {
                throw new AssertionError((Object)"illegal type variable reference");
            }
        }
        this.formalVar = (TypeVariableImpl)typeVariable;
        annotatedElement = this.formalVar;
        this.genericDeclaration = ((TypeVariableImpl)annotatedElement).genericDeclaration;
        this.bounds = ((TypeVariableImpl)annotatedElement).bounds;
    }

    public String toString() {
        return this.name;
    }
}

