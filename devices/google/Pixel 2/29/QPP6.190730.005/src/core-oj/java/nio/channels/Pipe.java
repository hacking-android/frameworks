/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

public abstract class Pipe {
    protected Pipe() {
    }

    public static Pipe open() throws IOException {
        return SelectorProvider.provider().openPipe();
    }

    public abstract SinkChannel sink();

    public abstract SourceChannel source();

    public static abstract class SinkChannel
    extends AbstractSelectableChannel
    implements WritableByteChannel,
    GatheringByteChannel {
        protected SinkChannel(SelectorProvider selectorProvider) {
            super(selectorProvider);
        }

        @Override
        public final int validOps() {
            return 4;
        }
    }

    public static abstract class SourceChannel
    extends AbstractSelectableChannel
    implements ReadableByteChannel,
    ScatteringByteChannel {
        protected SourceChannel(SelectorProvider selectorProvider) {
            super(selectorProvider);
        }

        @Override
        public final int validOps() {
            return 1;
        }
    }

}

