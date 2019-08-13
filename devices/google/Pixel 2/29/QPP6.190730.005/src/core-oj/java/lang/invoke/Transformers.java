/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.EmulatedStackFrame
 *  dalvik.system.EmulatedStackFrame$Range
 *  dalvik.system.EmulatedStackFrame$StackFrameAccessor
 *  dalvik.system.EmulatedStackFrame$StackFrameReader
 *  dalvik.system.EmulatedStackFrame$StackFrameWriter
 */
package java.lang.invoke;

import dalvik.system.EmulatedStackFrame;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import sun.invoke.util.Wrapper;

public class Transformers {
    private static final Method TRANSFORM_INTERNAL;

    static {
        try {
            TRANSFORM_INTERNAL = MethodHandle.class.getDeclaredMethod("transformInternal", EmulatedStackFrame.class);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError();
        }
    }

    private Transformers() {
    }

    public static class AlwaysThrow
    extends Transformer {
        private final Class<? extends Throwable> exceptionType;

        public AlwaysThrow(Class<?> class_, Class<? extends Throwable> class_2) {
            super(MethodType.methodType(class_, class_2));
            this.exceptionType = class_2;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw (Throwable)emulatedStackFrame.getReference(0, this.exceptionType);
        }
    }

    public static class BindTo
    extends Transformer {
        private final MethodHandle delegate;
        private final EmulatedStackFrame.Range range;
        private final Object receiver;

        public BindTo(MethodHandle methodHandle, Object object) {
            super(methodHandle.type().dropParameterTypes(0, 1));
            this.delegate = methodHandle;
            this.receiver = object;
            this.range = EmulatedStackFrame.Range.all((MethodType)this.type());
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class CatchException
    extends Transformer {
        private final Class<?> exType;
        private final MethodHandle handler;
        private final EmulatedStackFrame.Range handlerArgsRange;
        private final MethodHandle target;

        public CatchException(MethodHandle methodHandle, MethodHandle methodHandle2, Class<?> class_) {
            super(methodHandle.type());
            this.target = methodHandle;
            this.handler = methodHandle2;
            this.exType = class_;
            this.handlerArgsRange = EmulatedStackFrame.Range.of((MethodType)methodHandle.type(), (int)0, (int)(methodHandle2.type().parameterCount() - 1));
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class CollectArguments
    extends Transformer {
        private final MethodHandle collector;
        private final EmulatedStackFrame.Range collectorRange;
        private final int pos;
        private final EmulatedStackFrame.Range range1;
        private final EmulatedStackFrame.Range range2;
        private final int referencesOffset;
        private final int stackFrameOffset;
        private final MethodHandle target;

        CollectArguments(MethodHandle object, MethodHandle methodHandle, int n, MethodType methodType) {
            super(methodType);
            this.target = object;
            this.collector = methodHandle;
            this.pos = n;
            int n2 = methodHandle.type().parameterCount();
            int n3 = this.type().parameterCount();
            this.collectorRange = EmulatedStackFrame.Range.of((MethodType)this.type(), (int)n, (int)(n + n2));
            this.range1 = EmulatedStackFrame.Range.of((MethodType)this.type(), (int)0, (int)n);
            this.range2 = n + n2 < n3 ? EmulatedStackFrame.Range.of((MethodType)this.type(), (int)(n + n2), (int)n3) : null;
            object = methodHandle.type().rtype();
            if (object == Void.TYPE) {
                this.stackFrameOffset = 0;
                this.referencesOffset = 0;
            } else if (((Class)object).isPrimitive()) {
                this.stackFrameOffset = EmulatedStackFrame.getSize((Class)object);
                this.referencesOffset = 0;
            } else {
                this.stackFrameOffset = 0;
                this.referencesOffset = 1;
            }
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class Collector
    extends Transformer {
        private final int arrayOffset;
        private final char arrayTypeChar;
        private final EmulatedStackFrame.Range copyRange;
        private final int numArrayArgs;
        private final MethodHandle target;

        Collector(MethodHandle methodHandle, Class<?> class_, int n) {
            super(methodHandle.type().asCollectorType(class_, n));
            this.target = methodHandle;
            this.arrayOffset = methodHandle.type().parameterCount() - 1;
            this.arrayTypeChar = Wrapper.basicTypeChar(class_.getComponentType());
            this.numArrayArgs = n;
            this.copyRange = EmulatedStackFrame.Range.of((MethodType)methodHandle.type(), (int)0, (int)this.arrayOffset);
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class Constant
    extends Transformer {
        private double asDouble;
        private float asFloat;
        private int asInt;
        private long asLong;
        private Object asReference;
        private final Class<?> type;
        private char typeChar;

        public Constant(Class<?> serializable, Object object) {
            block11 : {
                block3 : {
                    block10 : {
                        block9 : {
                            block8 : {
                                block7 : {
                                    block6 : {
                                        block5 : {
                                            block4 : {
                                                block2 : {
                                                    super(MethodType.methodType(serializable));
                                                    this.type = serializable;
                                                    if (((Class)serializable).isPrimitive()) break block2;
                                                    this.asReference = object;
                                                    this.typeChar = (char)76;
                                                    break block3;
                                                }
                                                if (serializable != Integer.TYPE) break block4;
                                                this.asInt = (Integer)object;
                                                this.typeChar = (char)73;
                                                break block3;
                                            }
                                            if (serializable != Character.TYPE) break block5;
                                            this.asInt = ((Character)object).charValue();
                                            this.typeChar = (char)67;
                                            break block3;
                                        }
                                        if (serializable != Short.TYPE) break block6;
                                        this.asInt = ((Short)object).shortValue();
                                        this.typeChar = (char)83;
                                        break block3;
                                    }
                                    if (serializable != Byte.TYPE) break block7;
                                    this.asInt = ((Byte)object).byteValue();
                                    this.typeChar = (char)66;
                                    break block3;
                                }
                                if (serializable != Boolean.TYPE) break block8;
                                this.asInt = ((Boolean)object).booleanValue() ? 1 : 0;
                                this.typeChar = (char)90;
                                break block3;
                            }
                            if (serializable != Long.TYPE) break block9;
                            this.asLong = (Long)object;
                            this.typeChar = (char)74;
                            break block3;
                        }
                        if (serializable != Float.TYPE) break block10;
                        this.asFloat = ((Float)object).floatValue();
                        this.typeChar = (char)70;
                        break block3;
                    }
                    if (serializable != Double.TYPE) break block11;
                    this.asDouble = (Double)object;
                    this.typeChar = (char)68;
                }
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("unknown type: ");
            ((StringBuilder)serializable).append(this.typeChar);
            throw new AssertionError((Object)((StringBuilder)serializable).toString());
        }

        @Override
        public void transform(EmulatedStackFrame object) throws Throwable {
            EmulatedStackFrame.StackFrameWriter stackFrameWriter = new EmulatedStackFrame.StackFrameWriter();
            stackFrameWriter.attach((EmulatedStackFrame)object);
            stackFrameWriter.makeReturnValueAccessor();
            int n = this.typeChar;
            if (n != 70) {
                if (n != 76) {
                    if (n != 83) {
                        if (n != 90) {
                            if (n != 73) {
                                if (n != 74) {
                                    switch (n) {
                                        default: {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Unexpected typeChar: ");
                                            ((StringBuilder)object).append(this.typeChar);
                                            throw new AssertionError((Object)((StringBuilder)object).toString());
                                        }
                                        case 68: {
                                            stackFrameWriter.putNextDouble(this.asDouble);
                                            break;
                                        }
                                        case 67: {
                                            stackFrameWriter.putNextChar((char)this.asInt);
                                            break;
                                        }
                                        case 66: {
                                            stackFrameWriter.putNextByte((byte)this.asInt);
                                            break;
                                        }
                                    }
                                } else {
                                    stackFrameWriter.putNextLong(this.asLong);
                                }
                            } else {
                                stackFrameWriter.putNextInt(this.asInt);
                            }
                        } else {
                            n = this.asInt;
                            boolean bl = true;
                            if (n != 1) {
                                bl = false;
                            }
                            stackFrameWriter.putNextBoolean(bl);
                        }
                    } else {
                        stackFrameWriter.putNextShort((short)this.asInt);
                    }
                } else {
                    stackFrameWriter.putNextReference(this.asReference, this.type);
                }
            } else {
                stackFrameWriter.putNextFloat(this.asFloat);
            }
        }
    }

    static class Construct
    extends Transformer {
        private final EmulatedStackFrame.Range callerRange;
        private final MethodHandle constructorHandle;

        Construct(MethodHandle methodHandle, MethodType methodType) {
            super(methodType);
            this.constructorHandle = methodHandle;
            this.callerRange = EmulatedStackFrame.Range.all((MethodType)this.type());
        }

        private static void checkInstantiable(Class<?> class_) throws InstantiationException {
            if (Construct.isAbstract(class_)) {
                String string = class_.isInterface() ? "interface " : "abstract class ";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't instantiate ");
                stringBuilder.append(string);
                stringBuilder.append(class_);
                throw new InstantiationException(stringBuilder.toString());
            }
        }

        private static boolean isAbstract(Class<?> class_) {
            boolean bl = (class_.getModifiers() & 1024) == 1024;
            return bl;
        }

        MethodHandle getConstructorHandle() {
            return this.constructorHandle;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class DropArguments
    extends Transformer {
        private final MethodHandle delegate;
        private final EmulatedStackFrame.Range range1;
        private final EmulatedStackFrame.Range range2;

        public DropArguments(MethodType methodType, MethodHandle methodHandle, int n, int n2) {
            super(methodType);
            this.delegate = methodHandle;
            this.range1 = EmulatedStackFrame.Range.of((MethodType)methodType, (int)0, (int)n);
            int n3 = methodType.ptypes().length;
            this.range2 = n + n2 < n3 ? EmulatedStackFrame.Range.of((MethodType)methodType, (int)(n + n2), (int)n3) : null;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class ExplicitCastArguments
    extends Transformer {
        private final MethodHandle target;

        public ExplicitCastArguments(MethodHandle methodHandle, MethodType methodType) {
            super(methodType);
            this.target = methodHandle;
        }

        private static void box(EmulatedStackFrame.StackFrameReader object, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_2) {
            Object var4_4 = null;
            if (class_ == Boolean.TYPE) {
                object = object.nextBoolean();
            } else if (class_ == Byte.TYPE) {
                object = object.nextByte();
            } else if (class_ == Character.TYPE) {
                object = Character.valueOf(object.nextChar());
            } else if (class_ == Short.TYPE) {
                object = object.nextShort();
            } else if (class_ == Integer.TYPE) {
                object = object.nextInt();
            } else if (class_ == Long.TYPE) {
                object = object.nextLong();
            } else if (class_ == Float.TYPE) {
                object = Float.valueOf(object.nextFloat());
            } else if (class_ == Double.TYPE) {
                object = object.nextDouble();
            } else {
                ExplicitCastArguments.throwUnexpectedType(class_);
                object = var4_4;
            }
            stackFrameWriter.putNextReference(class_2.cast(object), class_2);
        }

        private static void explicitCast(EmulatedStackFrame.StackFrameReader object, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_2) {
            if (class_.equals(class_2)) {
                EmulatedStackFrame.StackFrameAccessor.copyNext((EmulatedStackFrame.StackFrameReader)object, (EmulatedStackFrame.StackFrameWriter)stackFrameWriter, class_);
            } else if (!class_.isPrimitive()) {
                object = object.nextReference(class_);
                if (class_2.isInterface()) {
                    stackFrameWriter.putNextReference(object, class_2);
                } else if (!class_2.isPrimitive()) {
                    stackFrameWriter.putNextReference(class_2.cast(object), class_2);
                } else {
                    ExplicitCastArguments.unbox(object, class_, stackFrameWriter, class_2);
                }
            } else if (class_2.isPrimitive()) {
                if (class_ == Boolean.TYPE) {
                    ExplicitCastArguments.explicitCastFromBoolean(object.nextBoolean(), stackFrameWriter, class_2);
                } else if (class_2 == Boolean.TYPE) {
                    ExplicitCastArguments.explicitCastToBoolean(object, class_, stackFrameWriter);
                } else {
                    ExplicitCastArguments.explicitCastPrimitives(object, class_, stackFrameWriter, class_2);
                }
            } else {
                ExplicitCastArguments.box(object, class_, stackFrameWriter, class_2);
            }
        }

        private void explicitCastArguments(EmulatedStackFrame emulatedStackFrame, EmulatedStackFrame arrclass) {
            EmulatedStackFrame.StackFrameReader stackFrameReader = new EmulatedStackFrame.StackFrameReader();
            stackFrameReader.attach(emulatedStackFrame);
            emulatedStackFrame = new EmulatedStackFrame.StackFrameWriter();
            emulatedStackFrame.attach((EmulatedStackFrame)arrclass);
            Class<?>[] arrclass2 = this.type().ptypes();
            arrclass = this.target.type().ptypes();
            for (int i = 0; i < arrclass2.length; ++i) {
                ExplicitCastArguments.explicitCast(stackFrameReader, arrclass2[i], (EmulatedStackFrame.StackFrameWriter)emulatedStackFrame, arrclass[i]);
            }
        }

        private static void explicitCastFromBoolean(boolean bl, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                stackFrameWriter.putNextByte((byte)(bl ? 1 : 0));
            } else if (class_ == Character.TYPE) {
                stackFrameWriter.putNextChar((char)(bl ? 1 : 0));
            } else if (class_ == Short.TYPE) {
                stackFrameWriter.putNextShort((short)(bl ? 1 : 0));
            } else if (class_ == Integer.TYPE) {
                stackFrameWriter.putNextInt((int)bl);
            } else if (class_ == Long.TYPE) {
                stackFrameWriter.putNextLong((long)bl);
            } else if (class_ == Float.TYPE) {
                stackFrameWriter.putNextFloat((float)bl);
            } else if (class_ == Double.TYPE) {
                stackFrameWriter.putNextDouble((double)bl);
            } else {
                ExplicitCastArguments.throwUnexpectedType(class_);
            }
        }

        private static void explicitCastPrimitives(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_2) {
            if (class_2 == Byte.TYPE) {
                stackFrameWriter.putNextByte(ExplicitCastArguments.readPrimitiveAsByte(stackFrameReader, class_));
            } else if (class_2 == Character.TYPE) {
                stackFrameWriter.putNextChar(ExplicitCastArguments.readPrimitiveAsChar(stackFrameReader, class_));
            } else if (class_2 == Short.TYPE) {
                stackFrameWriter.putNextShort(ExplicitCastArguments.readPrimitiveAsShort(stackFrameReader, class_));
            } else if (class_2 == Integer.TYPE) {
                stackFrameWriter.putNextInt(ExplicitCastArguments.readPrimitiveAsInt(stackFrameReader, class_));
            } else if (class_2 == Long.TYPE) {
                stackFrameWriter.putNextLong(ExplicitCastArguments.readPrimitiveAsLong(stackFrameReader, class_));
            } else if (class_2 == Float.TYPE) {
                stackFrameWriter.putNextFloat(ExplicitCastArguments.readPrimitiveAsFloat(stackFrameReader, class_));
            } else if (class_2 == Double.TYPE) {
                stackFrameWriter.putNextDouble(ExplicitCastArguments.readPrimitiveAsDouble(stackFrameReader, class_));
            } else {
                ExplicitCastArguments.throwUnexpectedType(class_2);
            }
        }

        private void explicitCastReturnValue(EmulatedStackFrame emulatedStackFrame, EmulatedStackFrame emulatedStackFrame2) {
            Class<?> class_ = this.target.type().rtype();
            Class<?> class_2 = this.type().rtype();
            if (class_2 != Void.TYPE) {
                EmulatedStackFrame.StackFrameWriter stackFrameWriter = new EmulatedStackFrame.StackFrameWriter();
                stackFrameWriter.attach(emulatedStackFrame);
                stackFrameWriter.makeReturnValueAccessor();
                if (class_ == Void.TYPE) {
                    if (class_2.isPrimitive()) {
                        ExplicitCastArguments.unboxNull(stackFrameWriter, class_2);
                    } else {
                        stackFrameWriter.putNextReference(null, class_2);
                    }
                } else {
                    emulatedStackFrame = new EmulatedStackFrame.StackFrameReader();
                    emulatedStackFrame.attach(emulatedStackFrame2);
                    emulatedStackFrame.makeReturnValueAccessor();
                    ExplicitCastArguments.explicitCast((EmulatedStackFrame.StackFrameReader)emulatedStackFrame, this.target.type().rtype(), stackFrameWriter, this.type().rtype());
                }
            }
        }

        private static void explicitCastToBoolean(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter) {
            stackFrameWriter.putNextBoolean(ExplicitCastArguments.toBoolean(ExplicitCastArguments.readPrimitiveAsByte(stackFrameReader, class_)));
        }

        private static byte readPrimitiveAsByte(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return (byte)stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return (byte)stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return (byte)stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return (byte)stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return (byte)stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (byte)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0;
        }

        private static char readPrimitiveAsChar(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return (char)stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return (char)stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return (char)stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return (char)stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return (char)stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (char)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return '\u0000';
        }

        private static double readPrimitiveAsDouble(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0.0;
        }

        private static float readPrimitiveAsFloat(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (float)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0.0f;
        }

        private static int readPrimitiveAsInt(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return (int)stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return (int)stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (int)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0;
        }

        private static long readPrimitiveAsLong(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return (long)stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (long)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0L;
        }

        private static short readPrimitiveAsShort(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?> class_) {
            if (class_ == Byte.TYPE) {
                return stackFrameReader.nextByte();
            }
            if (class_ == Character.TYPE) {
                return (short)stackFrameReader.nextChar();
            }
            if (class_ == Short.TYPE) {
                return stackFrameReader.nextShort();
            }
            if (class_ == Integer.TYPE) {
                return (short)stackFrameReader.nextInt();
            }
            if (class_ == Long.TYPE) {
                return (short)stackFrameReader.nextLong();
            }
            if (class_ == Float.TYPE) {
                return (short)stackFrameReader.nextFloat();
            }
            if (class_ == Double.TYPE) {
                return (short)stackFrameReader.nextDouble();
            }
            ExplicitCastArguments.throwUnexpectedType(class_);
            return 0;
        }

        private static void throwUnexpectedType(Class<?> class_) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected type: ");
            stringBuilder.append(class_);
            throw new InternalError(stringBuilder.toString());
        }

        private static boolean toBoolean(byte by) {
            boolean bl = true;
            if ((by & 1) != 1) {
                bl = false;
            }
            return bl;
        }

        private static void unbox(Object object, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_2) {
            if (object == null) {
                ExplicitCastArguments.unboxNull(stackFrameWriter, class_2);
            } else {
                ExplicitCastArguments.unboxNonNull(object, class_, stackFrameWriter, class_2);
            }
        }

        private static void unboxNonNull(Object object, Class<?> class_, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_2) {
            if (class_2 == Boolean.TYPE) {
                if (class_ == Boolean.class) {
                    stackFrameWriter.putNextBoolean(((Boolean)object).booleanValue());
                } else if (class_ != Float.class && class_ != Double.class) {
                    stackFrameWriter.putNextBoolean(ExplicitCastArguments.toBoolean((byte)((Long)object).longValue()));
                } else {
                    stackFrameWriter.putNextBoolean(ExplicitCastArguments.toBoolean((byte)((Double)object).doubleValue()));
                }
            } else if (class_2 == Byte.TYPE) {
                stackFrameWriter.putNextByte(((Byte)object).byteValue());
            } else if (class_2 == Character.TYPE) {
                stackFrameWriter.putNextChar(((Character)object).charValue());
            } else if (class_2 == Short.TYPE) {
                stackFrameWriter.putNextShort(((Short)object).shortValue());
            } else if (class_2 == Integer.TYPE) {
                stackFrameWriter.putNextInt(((Integer)object).intValue());
            } else if (class_2 == Long.TYPE) {
                stackFrameWriter.putNextLong(((Long)object).longValue());
            } else if (class_2 == Float.TYPE) {
                stackFrameWriter.putNextFloat(((Float)object).floatValue());
            } else if (class_2 == Double.TYPE) {
                stackFrameWriter.putNextDouble(((Double)object).doubleValue());
            } else {
                ExplicitCastArguments.throwUnexpectedType(class_2);
            }
        }

        private static void unboxNull(EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_) {
            if (class_ == Boolean.TYPE) {
                stackFrameWriter.putNextBoolean(false);
            } else if (class_ == Byte.TYPE) {
                stackFrameWriter.putNextByte((byte)0);
            } else if (class_ == Character.TYPE) {
                stackFrameWriter.putNextChar('\u0000');
            } else if (class_ == Short.TYPE) {
                stackFrameWriter.putNextShort((short)0);
            } else if (class_ == Integer.TYPE) {
                stackFrameWriter.putNextInt(0);
            } else if (class_ == Long.TYPE) {
                stackFrameWriter.putNextLong(0L);
            } else if (class_ == Float.TYPE) {
                stackFrameWriter.putNextFloat(0.0f);
            } else if (class_ == Double.TYPE) {
                stackFrameWriter.putNextDouble(0.0);
            } else {
                ExplicitCastArguments.throwUnexpectedType(class_);
            }
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class FilterArguments
    extends Transformer {
        private final MethodHandle[] filters;
        private final int pos;
        private final MethodHandle target;

        FilterArguments(MethodHandle methodHandle, int n, MethodHandle[] arrmethodHandle) {
            super(FilterArguments.deriveType(methodHandle, n, arrmethodHandle));
            this.target = methodHandle;
            this.pos = n;
            this.filters = arrmethodHandle;
        }

        private static MethodType deriveType(MethodHandle methodHandle, int n, MethodHandle[] arrmethodHandle) {
            Class[] arrclass = new Class[arrmethodHandle.length];
            for (int i = 0; i < arrmethodHandle.length; ++i) {
                arrclass[i] = arrmethodHandle[i].type().parameterType(0);
            }
            return methodHandle.type().replaceParameterTypes(n, arrmethodHandle.length + n, arrclass);
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class FilterReturnValue
    extends Transformer {
        private final EmulatedStackFrame.Range allArgs;
        private final MethodHandle filter;
        private final MethodHandle target;

        public FilterReturnValue(MethodHandle methodHandle, MethodHandle methodHandle2) {
            super(MethodType.methodType(methodHandle2.type().rtype(), methodHandle.type().ptypes()));
            this.target = methodHandle;
            this.filter = methodHandle2;
            this.allArgs = EmulatedStackFrame.Range.all((MethodType)this.type());
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class FoldArguments
    extends Transformer {
        private final MethodHandle combiner;
        private final EmulatedStackFrame.Range combinerArgs;
        private final int referencesOffset;
        private final int stackFrameOffset;
        private final MethodHandle target;
        private final EmulatedStackFrame.Range targetArgs;

        FoldArguments(MethodHandle object, MethodHandle methodHandle) {
            super(FoldArguments.deriveType((MethodHandle)object, methodHandle));
            this.target = object;
            this.combiner = methodHandle;
            this.combinerArgs = EmulatedStackFrame.Range.all((MethodType)methodHandle.type());
            this.targetArgs = EmulatedStackFrame.Range.all((MethodType)this.type());
            object = methodHandle.type().rtype();
            if (object == Void.TYPE) {
                this.stackFrameOffset = 0;
                this.referencesOffset = 0;
            } else if (((Class)object).isPrimitive()) {
                this.stackFrameOffset = EmulatedStackFrame.getSize((Class)object);
                this.referencesOffset = 0;
            } else {
                this.stackFrameOffset = 0;
                this.referencesOffset = 1;
            }
        }

        private static MethodType deriveType(MethodHandle methodHandle, MethodHandle methodHandle2) {
            if (methodHandle2.type().rtype() == Void.TYPE) {
                return methodHandle.type();
            }
            return methodHandle.type().dropParameterTypes(0, 1);
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class GuardWithTest
    extends Transformer {
        private final MethodHandle fallback;
        private final MethodHandle target;
        private final MethodHandle test;
        private final EmulatedStackFrame.Range testArgsRange;

        public GuardWithTest(MethodHandle methodHandle, MethodHandle methodHandle2, MethodHandle methodHandle3) {
            super(methodHandle2.type());
            this.test = methodHandle;
            this.target = methodHandle2;
            this.fallback = methodHandle3;
            this.testArgsRange = EmulatedStackFrame.Range.of((MethodType)methodHandle2.type(), (int)0, (int)methodHandle.type().parameterCount());
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class InsertArguments
    extends Transformer {
        private final int pos;
        private final EmulatedStackFrame.Range range1;
        private final EmulatedStackFrame.Range range2;
        private final MethodHandle target;
        private final Object[] values;

        InsertArguments(MethodHandle object, int n, Object[] arrobject) {
            super(((MethodHandle)object).type().dropParameterTypes(n, arrobject.length + n));
            this.target = object;
            this.pos = n;
            this.values = arrobject;
            object = this.type();
            this.range1 = EmulatedStackFrame.Range.of((MethodType)object, (int)0, (int)n);
            this.range2 = EmulatedStackFrame.Range.of((MethodType)object, (int)n, (int)((MethodType)object).parameterCount());
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    static class Invoker
    extends Transformer {
        private final EmulatedStackFrame.Range copyRange;
        private final boolean isExactInvoker;
        private final MethodType targetType;

        Invoker(MethodType methodType, boolean bl) {
            super(methodType.insertParameterTypes(0, MethodHandle.class));
            this.targetType = methodType;
            this.isExactInvoker = bl;
            this.copyRange = EmulatedStackFrame.Range.of((MethodType)this.type(), (int)1, (int)this.type().parameterCount());
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class PermuteArguments
    extends Transformer {
        private final int[] reorder;
        private final MethodHandle target;

        public PermuteArguments(MethodType methodType, MethodHandle methodHandle, int[] arrn) {
            super(methodType);
            this.target = methodHandle;
            this.reorder = arrn;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static class ReferenceArrayElementGetter
    extends Transformer {
        private final Class<?> arrayClass;

        public ReferenceArrayElementGetter(Class<?> class_) {
            super(MethodType.methodType(class_.getComponentType(), new Class[]{class_, Integer.TYPE}));
            this.arrayClass = class_;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            EmulatedStackFrame.StackFrameReader stackFrameReader = new EmulatedStackFrame.StackFrameReader();
            stackFrameReader.attach(emulatedStackFrame);
            Object[] arrobject = (Object[])stackFrameReader.nextReference(this.arrayClass);
            int n = stackFrameReader.nextInt();
            stackFrameReader = new EmulatedStackFrame.StackFrameWriter();
            stackFrameReader.attach(emulatedStackFrame);
            stackFrameReader.makeReturnValueAccessor();
            stackFrameReader.putNextReference(arrobject[n], this.arrayClass.getComponentType());
        }
    }

    public static class ReferenceArrayElementSetter
    extends Transformer {
        private final Class<?> arrayClass;

        public ReferenceArrayElementSetter(Class<?> class_) {
            super(MethodType.methodType(Void.TYPE, new Class[]{class_, Integer.TYPE, class_.getComponentType()}));
            this.arrayClass = class_;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            EmulatedStackFrame.StackFrameReader stackFrameReader = new EmulatedStackFrame.StackFrameReader();
            stackFrameReader.attach(emulatedStackFrame);
            ((Object[])stackFrameReader.nextReference(this.arrayClass))[stackFrameReader.nextInt()] = stackFrameReader.nextReference(this.arrayClass.getComponentType());
        }
    }

    public static class ReferenceIdentity
    extends Transformer {
        private final Class<?> type;

        public ReferenceIdentity(Class<?> class_) {
            super(MethodType.methodType(class_, class_));
            this.type = class_;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            EmulatedStackFrame.StackFrameReader stackFrameReader = new EmulatedStackFrame.StackFrameReader();
            stackFrameReader.attach(emulatedStackFrame);
            EmulatedStackFrame.StackFrameWriter stackFrameWriter = new EmulatedStackFrame.StackFrameWriter();
            stackFrameWriter.attach(emulatedStackFrame);
            stackFrameWriter.makeReturnValueAccessor();
            stackFrameWriter.putNextReference(stackFrameReader.nextReference(this.type), this.type);
        }
    }

    static class Spreader
    extends Transformer {
        private final int arrayOffset;
        private final char arrayTypeChar;
        private final EmulatedStackFrame.Range copyRange;
        private final int numArrayArgs;
        private final MethodHandle target;

        Spreader(MethodHandle object, MethodType methodType, int n) {
            super(methodType);
            this.target = object;
            this.arrayOffset = methodType.parameterCount() - 1;
            object = methodType.ptypes()[this.arrayOffset].getComponentType();
            if (object != null) {
                this.arrayTypeChar = Wrapper.basicTypeChar(object);
                this.numArrayArgs = n;
                this.copyRange = EmulatedStackFrame.Range.of((MethodType)methodType, (int)0, (int)this.arrayOffset);
                return;
            }
            throw new AssertionError((Object)"Trailing argument must be an array.");
        }

        public static void spreadArray(byte[] arrby, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType serializable, int n, int n2) {
            Class<?>[] arrclass = serializable.ptypes();
            for (int i = 0; i < n; ++i) {
                serializable = arrclass[i + n2];
                byte by = arrby[i];
                char c = Wrapper.basicTypeChar(serializable);
                if (c != 'B') {
                    if (c != 'D') {
                        if (c != 'F') {
                            if (c != 'L') {
                                if (c != 'S') {
                                    if (c != 'I') {
                                        if (c == 'J') {
                                            stackFrameWriter.putNextLong((long)by);
                                            continue;
                                        }
                                        throw new AssertionError();
                                    }
                                    stackFrameWriter.putNextInt((int)by);
                                    continue;
                                }
                                stackFrameWriter.putNextShort((short)by);
                                continue;
                            }
                            stackFrameWriter.putNextReference((Object)by, (Class)serializable);
                            continue;
                        }
                        stackFrameWriter.putNextFloat((float)by);
                        continue;
                    }
                    stackFrameWriter.putNextDouble((double)by);
                    continue;
                }
                stackFrameWriter.putNextByte(by);
            }
        }

        public static void spreadArray(char[] arrc, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType arrclass, int n, int n2) {
            arrclass = arrclass.ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                char c = arrc[i];
                char c2 = Wrapper.basicTypeChar(class_);
                if (c2 != 'C') {
                    if (c2 != 'D') {
                        if (c2 != 'F') {
                            if (c2 != 'L') {
                                if (c2 != 'I') {
                                    if (c2 == 'J') {
                                        stackFrameWriter.putNextLong((long)c);
                                        continue;
                                    }
                                    throw new AssertionError();
                                }
                                stackFrameWriter.putNextInt((int)c);
                                continue;
                            }
                            stackFrameWriter.putNextReference((Object)Character.valueOf(c), class_);
                            continue;
                        }
                        stackFrameWriter.putNextFloat((float)c);
                        continue;
                    }
                    stackFrameWriter.putNextDouble((double)c);
                    continue;
                }
                stackFrameWriter.putNextChar(c);
            }
        }

        public static void spreadArray(double[] arrd, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType arrclass, int n, int n2) {
            arrclass = arrclass.ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                double d = arrd[i];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'D') {
                    if (c == 'L') {
                        stackFrameWriter.putNextReference((Object)d, class_);
                        continue;
                    }
                    throw new AssertionError();
                }
                stackFrameWriter.putNextDouble(d);
            }
        }

        public static void spreadArray(float[] arrf, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType serializable, int n, int n2) {
            Class<?>[] arrclass = serializable.ptypes();
            for (int i = 0; i < n; ++i) {
                serializable = arrclass[i + n2];
                float f = arrf[i];
                char c = Wrapper.basicTypeChar(serializable);
                if (c != 'D') {
                    if (c != 'F') {
                        if (c == 'L') {
                            stackFrameWriter.putNextReference((Object)Float.valueOf(f), (Class)serializable);
                            continue;
                        }
                        throw new AssertionError();
                    }
                    stackFrameWriter.putNextFloat(f);
                    continue;
                }
                stackFrameWriter.putNextDouble((double)f);
            }
        }

        public static void spreadArray(int[] arrn, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType arrclass, int n, int n2) {
            arrclass = arrclass.ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                int n3 = arrn[i];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'D') {
                    if (c != 'F') {
                        if (c != 'L') {
                            if (c != 'I') {
                                if (c == 'J') {
                                    stackFrameWriter.putNextLong((long)n3);
                                    continue;
                                }
                                throw new AssertionError();
                            }
                            stackFrameWriter.putNextInt(n3);
                            continue;
                        }
                        stackFrameWriter.putNextReference((Object)n3, class_);
                        continue;
                    }
                    stackFrameWriter.putNextFloat((float)n3);
                    continue;
                }
                stackFrameWriter.putNextDouble((double)n3);
            }
        }

        public static void spreadArray(long[] arrl, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType arrclass, int n, int n2) {
            arrclass = arrclass.ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                long l = arrl[i];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'D') {
                    if (c != 'F') {
                        if (c != 'J') {
                            if (c == 'L') {
                                stackFrameWriter.putNextReference((Object)l, class_);
                                continue;
                            }
                            throw new AssertionError();
                        }
                        stackFrameWriter.putNextLong(l);
                        continue;
                    }
                    stackFrameWriter.putNextFloat((float)l);
                    continue;
                }
                stackFrameWriter.putNextDouble((double)l);
            }
        }

        public static void spreadArray(Object[] arrobject, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType object, int n, int n2) {
            Class<?>[] arrclass = ((MethodType)object).ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                object = arrobject[i];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'F') {
                    if (c != 'L') {
                        if (c != 'S') {
                            if (c != 'Z') {
                                if (c != 'I') {
                                    if (c != 'J') {
                                        switch (c) {
                                            default: {
                                                break;
                                            }
                                            case 'D': {
                                                stackFrameWriter.putNextDouble(((Double)object).doubleValue());
                                                break;
                                            }
                                            case 'C': {
                                                stackFrameWriter.putNextChar(((Character)object).charValue());
                                                break;
                                            }
                                            case 'B': {
                                                stackFrameWriter.putNextByte(((Byte)object).byteValue());
                                                break;
                                            }
                                        }
                                        continue;
                                    }
                                    stackFrameWriter.putNextLong(((Long)object).longValue());
                                    continue;
                                }
                                stackFrameWriter.putNextInt(((Integer)object).intValue());
                                continue;
                            }
                            stackFrameWriter.putNextBoolean(((Boolean)object).booleanValue());
                            continue;
                        }
                        stackFrameWriter.putNextShort(((Short)object).shortValue());
                        continue;
                    }
                    stackFrameWriter.putNextReference(object, class_);
                    continue;
                }
                stackFrameWriter.putNextFloat(((Float)object).floatValue());
            }
        }

        public static void spreadArray(short[] arrs, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType arrclass, int n, int n2) {
            arrclass = arrclass.ptypes();
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i + n2];
                short s = arrs[i];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'D') {
                    if (c != 'F') {
                        if (c != 'L') {
                            if (c != 'S') {
                                if (c != 'I') {
                                    if (c == 'J') {
                                        stackFrameWriter.putNextLong((long)s);
                                        continue;
                                    }
                                    throw new AssertionError();
                                }
                                stackFrameWriter.putNextInt((int)s);
                                continue;
                            }
                            stackFrameWriter.putNextShort(s);
                            continue;
                        }
                        stackFrameWriter.putNextReference((Object)s, class_);
                        continue;
                    }
                    stackFrameWriter.putNextFloat((float)s);
                    continue;
                }
                stackFrameWriter.putNextDouble((double)s);
            }
        }

        public static void spreadArray(boolean[] arrbl, EmulatedStackFrame.StackFrameWriter stackFrameWriter, MethodType serializable, int n, int n2) {
            Class<?>[] arrclass = serializable.ptypes();
            for (int i = 0; i < n; ++i) {
                serializable = arrclass[i + n2];
                boolean bl = arrbl[i];
                char c = Wrapper.basicTypeChar(serializable);
                if (c != 'L') {
                    if (c == 'Z') {
                        stackFrameWriter.putNextBoolean(bl);
                        continue;
                    }
                    throw new AssertionError();
                }
                stackFrameWriter.putNextReference((Object)bl, (Class)serializable);
            }
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

    public static abstract class Transformer
    extends MethodHandle
    implements Cloneable {
        protected Transformer(MethodType methodType) {
            super(TRANSFORM_INTERNAL.getArtMethod(), 5, methodType);
        }

        protected Transformer(MethodType methodType, int n) {
            super(TRANSFORM_INTERNAL.getArtMethod(), n, methodType);
        }

        public Object clone() throws CloneNotSupportedException {
            return Object.super.clone();
        }
    }

    static class VarargsCollector
    extends Transformer {
        final MethodHandle target;

        VarargsCollector(MethodHandle methodHandle) {
            super(methodHandle.type(), 6);
            if (VarargsCollector.lastParameterTypeIsAnArray(methodHandle.type().ptypes())) {
                this.target = methodHandle;
                return;
            }
            throw new IllegalArgumentException("target does not have array as last parameter");
        }

        private static boolean arityArgumentsConvertible(Class<?>[] arrclass, int n, Class<?> class_) {
            if (arrclass.length - 1 == n && arrclass[n].isArray() && arrclass[n].getComponentType() == class_) {
                return true;
            }
            while (n < arrclass.length) {
                if (!MethodType.canConvert(arrclass[n], class_)) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        private static Object booleanArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            boolean[] arrbl = new boolean[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                arrbl[i] = Wrapper.basicTypeChar(class_) != 'Z' ? ((Boolean)stackFrameReader.nextReference(class_)).booleanValue() : stackFrameReader.nextBoolean();
            }
            return arrbl;
        }

        private static Object byteArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            byte[] arrby = new byte[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                arrby[i] = Wrapper.basicTypeChar(class_) != 'B' ? ((Byte)stackFrameReader.nextReference(class_)).byteValue() : stackFrameReader.nextByte();
            }
            return arrby;
        }

        private static Object charArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            char[] arrc = new char[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                arrc[i] = Wrapper.basicTypeChar(class_) != 'C' ? ((Character)stackFrameReader.nextReference(class_)).charValue() : stackFrameReader.nextChar();
            }
            return arrc;
        }

        public static Object collectArguments(char c, Class<?> serializable, EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            if (c != 'F') {
                if (c != 'L') {
                    if (c != 'S') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            serializable = new StringBuilder();
                                            ((StringBuilder)serializable).append("Unexpected type: ");
                                            ((StringBuilder)serializable).append(c);
                                            throw new InternalError(((StringBuilder)serializable).toString());
                                        }
                                        case 'D': {
                                            return VarargsCollector.doubleArray(stackFrameReader, arrclass, n, n2);
                                        }
                                        case 'C': {
                                            return VarargsCollector.charArray(stackFrameReader, arrclass, n, n2);
                                        }
                                        case 'B': 
                                    }
                                    return VarargsCollector.byteArray(stackFrameReader, arrclass, n, n2);
                                }
                                return VarargsCollector.longArray(stackFrameReader, arrclass, n, n2);
                            }
                            return VarargsCollector.intArray(stackFrameReader, arrclass, n, n2);
                        }
                        return VarargsCollector.booleanArray(stackFrameReader, arrclass, n, n2);
                    }
                    return VarargsCollector.shortArray(stackFrameReader, arrclass, n, n2);
                }
                return VarargsCollector.referenceArray(stackFrameReader, arrclass, serializable, n, n2);
            }
            return VarargsCollector.floatArray(stackFrameReader, arrclass, n, n2);
        }

        private static void copyParameter(EmulatedStackFrame.StackFrameReader object, EmulatedStackFrame.StackFrameWriter stackFrameWriter, Class<?> class_) {
            char c = Wrapper.basicTypeChar(class_);
            if (c != 'F') {
                if (c != 'L') {
                    if (c != 'S') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Unexpected type: ");
                                            ((StringBuilder)object).append(class_);
                                            throw new InternalError(((StringBuilder)object).toString());
                                        }
                                        case 'D': {
                                            stackFrameWriter.putNextDouble(object.nextDouble());
                                            break;
                                        }
                                        case 'C': {
                                            stackFrameWriter.putNextChar(object.nextChar());
                                            break;
                                        }
                                        case 'B': {
                                            stackFrameWriter.putNextByte(object.nextByte());
                                            break;
                                        }
                                    }
                                } else {
                                    stackFrameWriter.putNextLong(object.nextLong());
                                }
                            } else {
                                stackFrameWriter.putNextInt(object.nextInt());
                            }
                        } else {
                            stackFrameWriter.putNextBoolean(object.nextBoolean());
                        }
                    } else {
                        stackFrameWriter.putNextShort(object.nextShort());
                    }
                } else {
                    stackFrameWriter.putNextReference(object.nextReference(class_), class_);
                }
            } else {
                stackFrameWriter.putNextFloat(object.nextFloat());
            }
        }

        private static Object doubleArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            double[] arrd = new double[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'B') {
                    if (c != 'D') {
                        if (c != 'F') {
                            if (c != 'S') {
                                if (c != 'I') {
                                    if (c != 'J') {
                                        arrd[i] = (Double)stackFrameReader.nextReference(class_);
                                        continue;
                                    }
                                    arrd[i] = stackFrameReader.nextLong();
                                    continue;
                                }
                                arrd[i] = stackFrameReader.nextInt();
                                continue;
                            }
                            arrd[i] = stackFrameReader.nextShort();
                            continue;
                        }
                        arrd[i] = stackFrameReader.nextFloat();
                        continue;
                    }
                    arrd[i] = stackFrameReader.nextDouble();
                    continue;
                }
                arrd[i] = stackFrameReader.nextByte();
            }
            return arrd;
        }

        private static Object floatArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            float[] arrf = new float[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'B') {
                    if (c != 'F') {
                        if (c != 'S') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    arrf[i] = ((Float)stackFrameReader.nextReference(class_)).floatValue();
                                    continue;
                                }
                                arrf[i] = stackFrameReader.nextLong();
                                continue;
                            }
                            arrf[i] = stackFrameReader.nextInt();
                            continue;
                        }
                        arrf[i] = stackFrameReader.nextShort();
                        continue;
                    }
                    arrf[i] = stackFrameReader.nextFloat();
                    continue;
                }
                arrf[i] = stackFrameReader.nextByte();
            }
            return arrf;
        }

        private static Object intArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            int[] arrn = new int[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'B') {
                    if (c != 'I') {
                        if (c != 'S') {
                            arrn[i] = (Integer)stackFrameReader.nextReference(class_);
                            continue;
                        }
                        arrn[i] = stackFrameReader.nextShort();
                        continue;
                    }
                    arrn[i] = stackFrameReader.nextInt();
                    continue;
                }
                arrn[i] = stackFrameReader.nextByte();
            }
            return arrn;
        }

        private static boolean lastParameterTypeIsAnArray(Class<?>[] arrclass) {
            if (arrclass.length == 0) {
                return false;
            }
            return arrclass[arrclass.length - 1].isArray();
        }

        private static Object longArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            long[] arrl = new long[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'B') {
                    if (c != 'S') {
                        if (c != 'I') {
                            if (c != 'J') {
                                arrl[i] = (Long)stackFrameReader.nextReference(class_);
                                continue;
                            }
                            arrl[i] = stackFrameReader.nextLong();
                            continue;
                        }
                        arrl[i] = stackFrameReader.nextInt();
                        continue;
                    }
                    arrl[i] = stackFrameReader.nextShort();
                    continue;
                }
                arrl[i] = stackFrameReader.nextByte();
            }
            return arrl;
        }

        private static Object makeArityArray(MethodType object, EmulatedStackFrame.StackFrameReader stackFrameReader, int n, Class<?> class_) {
            int n2 = ((MethodType)object).ptypes().length - n;
            class_ = class_.getComponentType();
            object = ((MethodType)object).ptypes();
            char c = Wrapper.basicTypeChar(class_);
            if (c != 'F') {
                if (c != 'L') {
                    if (c != 'S') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Unexpected type: ");
                                            ((StringBuilder)object).append(class_);
                                            throw new InternalError(((StringBuilder)object).toString());
                                        }
                                        case 'D': {
                                            return VarargsCollector.doubleArray(stackFrameReader, object, n, n2);
                                        }
                                        case 'C': {
                                            return VarargsCollector.charArray(stackFrameReader, object, n, n2);
                                        }
                                        case 'B': 
                                    }
                                    return VarargsCollector.byteArray(stackFrameReader, object, n, n2);
                                }
                                return VarargsCollector.longArray(stackFrameReader, object, n, n2);
                            }
                            return VarargsCollector.intArray(stackFrameReader, object, n, n2);
                        }
                        return VarargsCollector.booleanArray(stackFrameReader, object, n, n2);
                    }
                    return VarargsCollector.shortArray(stackFrameReader, object, n, n2);
                }
                return VarargsCollector.referenceArray(stackFrameReader, object, class_, n, n2);
            }
            return VarargsCollector.floatArray(stackFrameReader, object, n, n2);
        }

        private static MethodType makeTargetFrameType(MethodType methodType, MethodType methodType2) {
            int n = methodType2.ptypes().length;
            Class[] arrclass = new Class[n];
            System.arraycopy(methodType.ptypes(), 0, arrclass, 0, n - 1);
            arrclass[n - 1] = methodType2.ptypes()[n - 1];
            return MethodType.methodType(methodType.rtype(), arrclass);
        }

        private static void prepareFrame(EmulatedStackFrame emulatedStackFrame, EmulatedStackFrame object) {
            EmulatedStackFrame.StackFrameWriter stackFrameWriter = new EmulatedStackFrame.StackFrameWriter();
            stackFrameWriter.attach((EmulatedStackFrame)object);
            EmulatedStackFrame.StackFrameReader stackFrameReader = new EmulatedStackFrame.StackFrameReader();
            stackFrameReader.attach(emulatedStackFrame);
            object = object.getMethodType();
            int n = ((MethodType)object).ptypes().length - 1;
            for (int i = 0; i < n; ++i) {
                VarargsCollector.copyParameter(stackFrameReader, stackFrameWriter, ((MethodType)object).ptypes()[i]);
            }
            object = ((MethodType)object).ptypes()[n];
            stackFrameWriter.putNextReference(VarargsCollector.makeArityArray(emulatedStackFrame.getMethodType(), stackFrameReader, n, object), (Class)object);
        }

        private static Object referenceArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, Class<?> class_, int n, int n2) {
            Object object = Array.newInstance(class_, n2);
            for (int i = 0; i < n2; ++i) {
                Class<?> class_2 = arrclass[i + n];
                Object object2 = null;
                char c = Wrapper.basicTypeChar(class_2);
                if (c != 'F') {
                    if (c != 'L') {
                        if (c != 'S') {
                            if (c != 'Z') {
                                if (c != 'I') {
                                    if (c != 'J') {
                                        switch (c) {
                                            default: {
                                                break;
                                            }
                                            case 'D': {
                                                object2 = stackFrameReader.nextDouble();
                                                break;
                                            }
                                            case 'C': {
                                                object2 = Character.valueOf(stackFrameReader.nextChar());
                                                break;
                                            }
                                            case 'B': {
                                                object2 = stackFrameReader.nextByte();
                                                break;
                                            }
                                        }
                                    } else {
                                        object2 = stackFrameReader.nextLong();
                                    }
                                } else {
                                    object2 = stackFrameReader.nextInt();
                                }
                            } else {
                                object2 = stackFrameReader.nextBoolean();
                            }
                        } else {
                            object2 = stackFrameReader.nextShort();
                        }
                    } else {
                        object2 = stackFrameReader.nextReference(class_2);
                    }
                } else {
                    object2 = Float.valueOf(stackFrameReader.nextFloat());
                }
                Array.set(object, i, class_.cast(object2));
            }
            return object;
        }

        private static Object shortArray(EmulatedStackFrame.StackFrameReader stackFrameReader, Class<?>[] arrclass, int n, int n2) {
            short[] arrs = new short[n2];
            for (int i = 0; i < n2; ++i) {
                Class<?> class_ = arrclass[i + n];
                char c = Wrapper.basicTypeChar(class_);
                if (c != 'B') {
                    if (c != 'S') {
                        arrs[i] = (Short)stackFrameReader.nextReference(class_);
                        continue;
                    }
                    arrs[i] = stackFrameReader.nextShort();
                    continue;
                }
                arrs[i] = stackFrameReader.nextByte();
            }
            return arrs;
        }

        private static void throwWrongMethodTypeException(MethodType methodType, MethodType methodType2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot convert ");
            stringBuilder.append(methodType);
            stringBuilder.append(" to ");
            stringBuilder.append(methodType2);
            throw new WrongMethodTypeException(stringBuilder.toString());
        }

        @Override
        public MethodHandle asFixedArity() {
            return this.target;
        }

        @Override
        public boolean isVarargsCollector() {
            return true;
        }

        @Override
        public void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }
    }

}

