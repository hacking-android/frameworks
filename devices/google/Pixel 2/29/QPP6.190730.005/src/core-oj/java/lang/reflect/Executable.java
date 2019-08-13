/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.reflect.AnnotatedElements
 *  libcore.reflect.GenericSignatureParser
 *  libcore.reflect.ListOfTypes
 *  libcore.reflect.Types
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;
import libcore.reflect.AnnotatedElements;
import libcore.reflect.GenericSignatureParser;
import libcore.reflect.ListOfTypes;
import libcore.reflect.Types;

public abstract class Executable
extends AccessibleObject
implements Member,
GenericDeclaration {
    private int accessFlags;
    private long artMethod;
    private Class<?> declaringClass;
    private Class<?> declaringClassOfOverriddenMethod;
    private int dexMethodIndex;
    private volatile transient boolean hasRealParameterData;
    private volatile transient Parameter[] parameters;

    Executable() {
    }

    private static int fixMethodFlags(int n) {
        int n2 = n;
        if ((n & 1024) != 0) {
            n2 = n & -257;
        }
        n = n2 &= -33;
        if ((n2 & 131072) != 0) {
            n = n2 | 32;
        }
        return 65535 & n;
    }

    @FastNative
    private native <T extends Annotation> T getAnnotationNative(Class<T> var1);

    @FastNative
    private native Annotation[] getDeclaredAnnotationsNative();

    @FastNative
    private native Annotation[][] getParameterAnnotationsNative();

    @FastNative
    private native Parameter[] getParameters0();

    @FastNative
    private native String[] getSignatureAnnotation();

    private String getSignatureAttribute() {
        String[] arrstring = this.getSignatureAnnotation();
        if (arrstring == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrstring[i]);
        }
        return stringBuilder.toString();
    }

    @FastNative
    private native boolean isAnnotationPresentNative(Class<? extends Annotation> var1);

    private Parameter[] privateGetParameters() {
        Parameter[] arrparameter;
        block3 : {
            block4 : {
                block2 : {
                    Object object = this.parameters;
                    arrparameter = object;
                    if (object != null) break block3;
                    try {
                        arrparameter = this.getParameters0();
                        if (arrparameter != null) break block2;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        object = new MalformedParametersException("Invalid parameter metadata in class file");
                        ((Throwable)object).initCause(illegalArgumentException);
                        throw object;
                    }
                    this.hasRealParameterData = false;
                    arrparameter = this.synthesizeAllParams();
                    break block4;
                }
                this.hasRealParameterData = true;
                this.verifyParameters(arrparameter);
            }
            this.parameters = arrparameter;
        }
        return arrparameter;
    }

    private Parameter[] synthesizeAllParams() {
        int n = this.getParameterCount();
        Parameter[] arrparameter = new Parameter[n];
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("arg");
            stringBuilder.append(i);
            arrparameter[i] = new Parameter(stringBuilder.toString(), 0, this, i);
        }
        return arrparameter;
    }

    private void verifyParameters(Parameter[] object) {
        if (this.getParameterTypes().length == ((Parameter[])object).length) {
            int n = ((Parameter[])object).length;
            for (int i = 0; i < n; ++i) {
                Parameter parameter = object[i];
                String string = parameter.getRealName();
                int n2 = parameter.getModifiers();
                if (string != null && (string.isEmpty() || string.indexOf(46) != -1 || string.indexOf(59) != -1 || string.indexOf(91) != -1 || string.indexOf(47) != -1)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid parameter name \"");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("\"");
                    throw new MalformedParametersException(((StringBuilder)object).toString());
                }
                if (n2 == (36880 & n2)) {
                    continue;
                }
                throw new MalformedParametersException("Invalid parameter modifiers");
            }
            return;
        }
        throw new MalformedParametersException("Wrong number of parameters in MethodParameters attribute");
    }

    @FastNative
    native int compareMethodParametersInternal(Method var1);

    final boolean equalNameAndParametersInternal(Method method) {
        boolean bl = this.getName().equals(method.getName()) && this.compareMethodParametersInternal(method) == 0;
        return bl;
    }

    boolean equalParamTypes(Class<?>[] arrclass, Class<?>[] arrclass2) {
        if (arrclass.length == arrclass2.length) {
            for (int i = 0; i < arrclass.length; ++i) {
                if (arrclass[i] == arrclass2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public final int getAccessFlags() {
        return this.accessFlags;
    }

    Type[] getAllGenericParameterTypes() {
        Type[] arrtype;
        block4 : {
            if (!this.hasGenericInformation()) {
                return this.getParameterTypes();
            }
            boolean bl = this.hasRealParameterData();
            Type[] arrtype2 = this.getGenericParameterTypes();
            arrtype = this.getParameterTypes();
            Type[] arrtype3 = new Type[arrtype.length];
            Parameter[] arrparameter = this.getParameters();
            int n = 0;
            if (bl) {
                for (int i = 0; i < arrtype3.length; ++i) {
                    Parameter parameter = arrparameter[i];
                    if (!parameter.isSynthetic() && !parameter.isImplicit()) {
                        arrtype3[i] = arrtype2[n];
                        ++n;
                        continue;
                    }
                    arrtype3[i] = arrtype[i];
                }
                return arrtype3;
            }
            if (arrtype2.length != arrtype.length) break block4;
            arrtype = arrtype2;
        }
        return arrtype;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        Objects.requireNonNull(class_);
        return this.getAnnotationNative(class_);
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> class_) {
        return AnnotatedElements.getDirectOrIndirectAnnotationsByType((AnnotatedElement)this, class_);
    }

    public final long getArtMethod() {
        return this.artMethod;
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.getDeclaredAnnotationsNative();
    }

    @Override
    public abstract Class<?> getDeclaringClass();

    final Class<?> getDeclaringClassInternal() {
        return this.declaringClass;
    }

    public abstract Class<?>[] getExceptionTypes();

    public Type[] getGenericExceptionTypes() {
        return Types.getTypeArray((ListOfTypes)this.getMethodOrConstructorGenericInfoInternal().genericExceptionTypes, (boolean)false);
    }

    public Type[] getGenericParameterTypes() {
        return Types.getTypeArray((ListOfTypes)this.getMethodOrConstructorGenericInfoInternal().genericParameterTypes, (boolean)false);
    }

    @FastNative
    final native String getMethodNameInternal();

    final GenericInfo getMethodOrConstructorGenericInfoInternal() {
        String string = this.getSignatureAttribute();
        Class[] arrclass = this.getExceptionTypes();
        GenericSignatureParser genericSignatureParser = new GenericSignatureParser(this.getDeclaringClass().getClassLoader());
        if (this instanceof Method) {
            genericSignatureParser.parseForMethod((GenericDeclaration)this, string, arrclass);
        } else {
            genericSignatureParser.parseForConstructor((GenericDeclaration)this, string, arrclass);
        }
        return new GenericInfo(genericSignatureParser.exceptionTypes, genericSignatureParser.parameterTypes, genericSignatureParser.returnType, genericSignatureParser.formalTypeParameters);
    }

    @FastNative
    final native Class<?> getMethodReturnTypeInternal();

    @Override
    public abstract int getModifiers();

    final int getModifiersInternal() {
        return Executable.fixMethodFlags(this.accessFlags);
    }

    @Override
    public abstract String getName();

    public abstract Annotation[][] getParameterAnnotations();

    final Annotation[][] getParameterAnnotationsInternal() {
        Annotation[][] arrannotation;
        Annotation[][] arrannotation2 = arrannotation = this.getParameterAnnotationsNative();
        if (arrannotation == null) {
            arrannotation2 = new Annotation[this.getParameterTypes().length][0];
        }
        return arrannotation2;
    }

    public int getParameterCount() {
        throw new AbstractMethodError();
    }

    @FastNative
    final native int getParameterCountInternal();

    public abstract Class<?>[] getParameterTypes();

    @FastNative
    final native Class<?>[] getParameterTypesInternal();

    public Parameter[] getParameters() {
        return (Parameter[])this.privateGetParameters().clone();
    }

    @Override
    public abstract TypeVariable<?>[] getTypeParameters();

    abstract boolean hasGenericInformation();

    final boolean hasGenericInformationInternal() {
        boolean bl = this.getSignatureAnnotation() != null;
        return bl;
    }

    boolean hasRealParameterData() {
        if (this.parameters == null) {
            this.privateGetParameters();
        }
        return this.hasRealParameterData;
    }

    @Override
    public final boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        Objects.requireNonNull(class_);
        return this.isAnnotationPresentNative(class_);
    }

    final boolean isBridgeMethodInternal() {
        boolean bl = (this.accessFlags & 64) != 0;
        return bl;
    }

    final boolean isDefaultMethodInternal() {
        boolean bl = (this.accessFlags & 4194304) != 0;
        return bl;
    }

    @Override
    public boolean isSynthetic() {
        boolean bl = (this.accessFlags & 4096) != 0;
        return bl;
    }

    public boolean isVarArgs() {
        boolean bl = (this.accessFlags & 128) != 0;
        return bl;
    }

    void printModifiersIfNonzero(StringBuilder stringBuilder, int n, boolean bl) {
        int n2 = this.getModifiers() & n;
        if (n2 != 0 && !bl) {
            stringBuilder.append(Modifier.toString(n2));
            stringBuilder.append(' ');
        } else {
            n = n2 & 7;
            if (n != 0) {
                stringBuilder.append(Modifier.toString(n));
                stringBuilder.append(' ');
            }
            if (bl) {
                stringBuilder.append("default ");
            }
            if ((n = n2 & -8) != 0) {
                stringBuilder.append(Modifier.toString(n));
                stringBuilder.append(' ');
            }
        }
    }

    void separateWithCommas(Class<?>[] arrclass, StringBuilder stringBuilder) {
        for (int i = 0; i < arrclass.length; ++i) {
            stringBuilder.append(arrclass[i].getTypeName());
            if (i >= arrclass.length - 1) continue;
            stringBuilder.append(",");
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String sharedToGenericString(int n, boolean bl) {
        StringBuilder stringBuilder;
        block14 : {
            stringBuilder = new StringBuilder();
            this.printModifiersIfNonzero(stringBuilder, n, bl);
            Object object = this.getTypeParameters();
            n = ((TypeVariable<?>[])object).length;
            int n2 = 0;
            if (n > 0) {
                stringBuilder.append('<');
                int n3 = ((TypeVariable<?>[])object).length;
                boolean bl2 = true;
                for (n = 0; n < n3; ++n) {
                    TypeVariable<?> typeVariable = object[n];
                    if (!bl2) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(typeVariable.toString());
                    bl2 = false;
                }
                stringBuilder.append("> ");
            }
            this.specificToGenericStringHeader(stringBuilder);
            stringBuilder.append('(');
            Type[] arrtype = this.getGenericParameterTypes();
            for (n = 0; n < arrtype.length; ++n) {
                void var8_13;
                object = arrtype[n].getTypeName();
                Object object2 = object;
                if (this.isVarArgs()) {
                    Object object3 = object;
                    if (n == arrtype.length - 1) {
                        String string = ((String)object).replaceFirst("\\[\\]$", "...");
                    }
                }
                stringBuilder.append((String)var8_13);
                if (n >= arrtype.length - 1) continue;
                stringBuilder.append(',');
            }
            try {
                stringBuilder.append(')');
                object = this.getGenericExceptionTypes();
                if (((TypeVariable<?>[])object).length <= 0) break block14;
                stringBuilder.append(" throws ");
                for (n = n2; n < ((TypeVariable<?>[])object).length; ++n) {
                    void var8_17;
                    if (object[n] instanceof Class) {
                        String string = ((Class)object[n]).getName();
                    } else {
                        String string = object[n].toString();
                    }
                    stringBuilder.append((String)var8_17);
                    if (n >= ((Object)object).length - 1) continue;
                    stringBuilder.append(',');
                }
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("<");
                ((StringBuilder)object).append(exception);
                ((StringBuilder)object).append(">");
                return ((StringBuilder)object).toString();
            }
        }
        return stringBuilder.toString();
    }

    String sharedToString(int n, boolean bl, Class<?>[] object, Class<?>[] arrclass) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            this.printModifiersIfNonzero(stringBuilder, n, bl);
            this.specificToStringHeader(stringBuilder);
            stringBuilder.append('(');
            this.separateWithCommas((Class<?>[])object, stringBuilder);
            stringBuilder.append(')');
            if (arrclass.length > 0) {
                stringBuilder.append(" throws ");
                this.separateWithCommas(arrclass, stringBuilder);
            }
            object = stringBuilder.toString();
            return object;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("<");
            ((StringBuilder)object).append(exception);
            ((StringBuilder)object).append(">");
            return ((StringBuilder)object).toString();
        }
    }

    abstract void specificToGenericStringHeader(StringBuilder var1);

    abstract void specificToStringHeader(StringBuilder var1);

    public abstract String toGenericString();

    static final class GenericInfo {
        final TypeVariable<?>[] formalTypeParameters;
        final ListOfTypes genericExceptionTypes;
        final ListOfTypes genericParameterTypes;
        final Type genericReturnType;

        GenericInfo(ListOfTypes listOfTypes, ListOfTypes listOfTypes2, Type type, TypeVariable<?>[] arrtypeVariable) {
            this.genericExceptionTypes = listOfTypes;
            this.genericParameterTypes = listOfTypes2;
            this.genericReturnType = type;
            this.formalTypeParameters = arrtypeVariable;
        }
    }

}

