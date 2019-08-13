/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

interface MutabilityOracle {
    public static final MutabilityOracle IMMUTABLE = new MutabilityOracle(){

        @Override
        public void ensureMutable() {
            throw new UnsupportedOperationException();
        }
    };

    public void ensureMutable();

}

