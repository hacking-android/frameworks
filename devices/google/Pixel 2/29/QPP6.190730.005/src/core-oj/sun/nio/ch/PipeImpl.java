/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.nio.channels.Pipe;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.IOUtil;
import sun.nio.ch.SinkChannelImpl;
import sun.nio.ch.SourceChannelImpl;

class PipeImpl
extends Pipe {
    private final Pipe.SinkChannel sink;
    private final Pipe.SourceChannel source;

    PipeImpl(SelectorProvider selectorProvider) {
        long l = IOUtil.makePipe(true);
        int n = (int)(l >>> 32);
        int n2 = (int)l;
        FileDescriptor fileDescriptor = new FileDescriptor();
        IOUtil.setfdVal(fileDescriptor, n);
        this.source = new SourceChannelImpl(selectorProvider, fileDescriptor);
        fileDescriptor = new FileDescriptor();
        IOUtil.setfdVal(fileDescriptor, n2);
        this.sink = new SinkChannelImpl(selectorProvider, fileDescriptor);
    }

    @Override
    public Pipe.SinkChannel sink() {
        return this.sink;
    }

    @Override
    public Pipe.SourceChannel source() {
        return this.source;
    }
}

