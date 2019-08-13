/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

public class MethodHandleImpl
extends MethodHandle
implements Cloneable {
    private HandleInfo info;

    MethodHandleImpl(long l, int n, MethodType methodType) {
        super(l, n, methodType);
    }

    public Object clone() throws CloneNotSupportedException {
        return Object.super.clone();
    }

    public native Member getMemberInternal();

    MethodHandleInfo reveal() {
        if (this.info == null) {
            this.info = new HandleInfo(this.getMemberInternal(), this);
        }
        return this.info;
    }

    static class HandleInfo
    implements MethodHandleInfo {
        private final MethodHandle handle;
        private final Member member;

        HandleInfo(Member member, MethodHandle methodHandle) {
            this.member = member;
            this.handle = methodHandle;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return this.member.getDeclaringClass();
        }

        @Override
        public MethodType getMethodType() {
            MethodType methodType;
            block2 : {
                int n;
                MethodType methodType2 = this.handle.type();
                boolean bl = false;
                methodType = methodType2;
                if (this.member instanceof Constructor) {
                    methodType = methodType2.changeReturnType(Void.TYPE);
                    bl = true;
                }
                if ((n = this.handle.getHandleKind()) == 0 || n == 1 || n == 2 || n == 4 || n == 9 || n == 10) {
                    bl = true;
                }
                if (!bl) break block2;
                methodType = methodType.dropParameterTypes(0, 1);
            }
            return methodType;
        }

        @Override
        public int getModifiers() {
            return this.member.getModifiers();
        }

        @Override
        public String getName() {
            Member member = this.member;
            if (member instanceof Constructor) {
                return "<init>";
            }
            return member.getName();
        }

        @Override
        public int getReferenceKind() {
            int n = this.handle.getHandleKind();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            switch (n) {
                                default: {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Unexpected handle kind: ");
                                    stringBuilder.append(this.handle.getHandleKind());
                                    throw new AssertionError((Object)stringBuilder.toString());
                                }
                                case 12: {
                                    return 4;
                                }
                                case 11: {
                                    return 2;
                                }
                                case 10: {
                                    return 3;
                                }
                                case 9: 
                            }
                            return 1;
                        }
                        return 6;
                    }
                    if (this.member instanceof Constructor) {
                        return 8;
                    }
                    return 7;
                }
                return 7;
            }
            if (this.member.getDeclaringClass().isInterface()) {
                return 9;
            }
            return 5;
        }

        @Override
        public <T extends Member> T reflectAs(Class<T> class_, MethodHandles.Lookup lookup) {
            try {
                lookup.checkAccess(this.member.getDeclaringClass(), this.member.getDeclaringClass(), this.member.getModifiers(), this.member.getName());
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalArgumentException("Unable to access member.", illegalAccessException);
            }
            return (T)this.member;
        }
    }

}

