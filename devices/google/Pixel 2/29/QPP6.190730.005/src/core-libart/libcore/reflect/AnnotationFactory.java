/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import libcore.reflect.AnnotationMember;

public final class AnnotationFactory
implements InvocationHandler,
Serializable {
    private static final transient Map<Class<? extends Annotation>, AnnotationMember[]> cache = new WeakHashMap<Class<? extends Annotation>, AnnotationMember[]>();
    private AnnotationMember[] elements;
    private final Class<? extends Annotation> klazz;

    private AnnotationFactory(Class<? extends Annotation> arrannotationMember, AnnotationMember[] arrannotationMember2) {
        this.klazz = arrannotationMember;
        arrannotationMember = AnnotationFactory.getElementsDescription(this.klazz);
        if (arrannotationMember2 == null) {
            this.elements = arrannotationMember;
        } else {
            this.elements = new AnnotationMember[arrannotationMember.length];
            block0 : for (int i = this.elements.length - 1; i >= 0; --i) {
                for (AnnotationMember annotationMember : arrannotationMember2) {
                    if (!annotationMember.name.equals(arrannotationMember[i].name)) continue;
                    this.elements[i] = annotationMember.setDefinition(arrannotationMember[i]);
                    continue block0;
                }
                this.elements[i] = arrannotationMember[i];
            }
        }
    }

    public static <A extends Annotation> A createAnnotation(Class<? extends Annotation> class_, AnnotationMember[] object) {
        object = new AnnotationFactory(class_, (AnnotationMember[])object);
        return (A)((Annotation)Proxy.newProxyInstance(class_.getClassLoader(), new Class[]{class_}, (InvocationHandler)object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static AnnotationMember[] getElementsDescription(Class<? extends Annotation> class_) {
        AnnotationMember[] arrannotationMember = cache;
        // MONITORENTER : arrannotationMember
        Object object = cache.get(class_);
        if (object != null) {
            // MONITOREXIT : arrannotationMember
            return object;
        }
        // MONITOREXIT : arrannotationMember
        if (!class_.isAnnotation()) {
            arrannotationMember = new StringBuilder();
            arrannotationMember.append("Type is not annotation: ");
            arrannotationMember.append(class_.getName());
            throw new IllegalArgumentException(arrannotationMember.toString());
        }
        object = class_.getDeclaredMethods();
        arrannotationMember = new AnnotationMember[((Object[])object).length];
        int n = 0;
        do {
            if (n >= ((Object[])object).length) {
                object = cache;
                // MONITORENTER : object
                cache.put(class_, arrannotationMember);
                // MONITOREXIT : object
                return arrannotationMember;
            }
            Object object2 = object[n];
            String string = ((Method)object2).getName();
            Class<?> class_2 = ((Method)object2).getReturnType();
            try {
                AnnotationMember annotationMember;
                arrannotationMember[n] = annotationMember = new AnnotationMember(string, ((Method)object2).getDefaultValue(), class_2, (Method)object2);
            }
            catch (Throwable throwable) {
                arrannotationMember[n] = new AnnotationMember(string, throwable, class_2, (Method)object2);
            }
            ++n;
        } while (true);
    }

    private void readObject(ObjectInputStream arrannotationMember) throws IOException, ClassNotFoundException {
        arrannotationMember.defaultReadObject();
        arrannotationMember = AnnotationFactory.getElementsDescription(this.klazz);
        AnnotationMember[] arrannotationMember2 = this.elements;
        ArrayList<AnnotationMember> arrayList = new ArrayList<AnnotationMember>(arrannotationMember.length + arrannotationMember2.length);
        block0 : for (AnnotationMember annotationMember2 : arrannotationMember2) {
            int n = arrannotationMember.length;
            for (int i = 0; i < n; ++i) {
                if (arrannotationMember[i].name.equals(annotationMember2.name)) continue block0;
            }
            arrayList.add(annotationMember2);
        }
        block2 : for (AnnotationMember annotationMember : arrannotationMember) {
            for (AnnotationMember annotationMember2 : arrannotationMember2) {
                if (!annotationMember2.name.equals(annotationMember.name)) continue;
                arrayList.add(annotationMember2.setDefinition(annotationMember));
                continue block2;
            }
            arrayList.add(annotationMember);
        }
        this.elements = arrayList.toArray(new AnnotationMember[arrayList.size()]);
    }

    public boolean equals(Object object) {
        AnnotationMember[] arrannotationMember;
        if (object == this) {
            return true;
        }
        if (!this.klazz.isInstance(object)) {
            return false;
        }
        if (Proxy.isProxyClass(object.getClass()) && (arrannotationMember = Proxy.getInvocationHandler(object)) instanceof AnnotationFactory) {
            object = (AnnotationFactory)arrannotationMember;
            arrannotationMember = this.elements;
            if (arrannotationMember.length != ((AnnotationFactory)object).elements.length) {
                return false;
            }
            block5 : for (AnnotationMember annotationMember : arrannotationMember) {
                AnnotationMember[] arrannotationMember2 = ((AnnotationFactory)object).elements;
                int n = arrannotationMember2.length;
                for (int i = 0; i < n; ++i) {
                    if (!annotationMember.equals(arrannotationMember2[i])) continue;
                    continue block5;
                }
                return false;
            }
            return true;
        }
        for (AnnotationMember annotationMember : this.elements) {
            Object object2;
            block14 : {
                block15 : {
                    if (annotationMember.tag == '!') {
                        return false;
                    }
                    try {
                        if (!annotationMember.definingMethod.isAccessible()) {
                            annotationMember.definingMethod.setAccessible(true);
                        }
                        if ((object2 = annotationMember.definingMethod.invoke(object, new Object[0])) == null) break block14;
                    }
                    catch (Throwable throwable) {
                        return false;
                    }
                    if (annotationMember.tag != '[') break block15;
                    if (annotationMember.equalArrayValue(object2)) continue;
                    return false;
                }
                if (annotationMember.value.equals(object2)) continue;
                return false;
            }
            Object object3 = annotationMember.value;
            object2 = AnnotationMember.NO_VALUE;
            if (object3 == object2) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n = 0;
        AnnotationMember[] arrannotationMember = this.elements;
        int n2 = arrannotationMember.length;
        for (int i = 0; i < n2; ++i) {
            n += arrannotationMember[i].hashCode();
        }
        return n;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] arrobject) throws Throwable {
        String string = method.getName();
        object = method.getParameterTypes();
        int n = ((Class<?>[])object).length;
        int n2 = 0;
        if (n == 0) {
            if ("annotationType".equals(string)) {
                return this.klazz;
            }
            if ("toString".equals(string)) {
                return this.toString();
            }
            if ("hashCode".equals(string)) {
                return this.hashCode();
            }
            arrobject = null;
            AnnotationMember[] arrannotationMember = this.elements;
            n = arrannotationMember.length;
            do {
                object = arrobject;
                if (n2 >= n) break;
                object = arrannotationMember[n2];
                if (string.equals(((AnnotationMember)object).name)) break;
                ++n2;
            } while (true);
            if (object != null && method.equals(((AnnotationMember)object).definingMethod)) {
                if ((object = ((AnnotationMember)object).validateValue()) != null) {
                    return object;
                }
                throw new IncompleteAnnotationException(this.klazz, string);
            }
            throw new IllegalArgumentException(method.toString());
        }
        if (((Class<?>[])object).length == 1 && object[0] == Object.class && "equals".equals(string)) {
            return this.equals(arrobject[0]);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid method for annotation type: ");
        ((StringBuilder)object).append(method);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('@');
        stringBuilder.append(this.klazz.getName());
        stringBuilder.append('(');
        for (int i = 0; i < this.elements.length; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.elements[i]);
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

