/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

final class EmptyArray {
    static final boolean[] BOOLEAN = new boolean[0];
    static final byte[] BYTE = new byte[0];
    static final char[] CHAR = new char[0];
    static final Class<?>[] CLASS;
    static final double[] DOUBLE;
    static final int[] INT;
    static final Object[] OBJECT;
    static final StackTraceElement[] STACK_TRACE_ELEMENT;
    static final String[] STRING;
    static final Throwable[] THROWABLE;

    static {
        DOUBLE = new double[0];
        INT = new int[0];
        CLASS = new Class[0];
        OBJECT = new Object[0];
        STRING = new String[0];
        THROWABLE = new Throwable[0];
        STACK_TRACE_ELEMENT = new StackTraceElement[0];
    }

    private EmptyArray() {
    }
}

