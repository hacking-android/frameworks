/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Supplier;
import java.util.stream.Sink;

interface TerminalSink<T, R>
extends Sink<T>,
Supplier<R> {
}

