/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketOption;

class SocketSecrets {
    SocketSecrets() {
    }

    private static <T> T getOption(Object object, SocketOption<T> socketOption) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!(object instanceof Socket)) break block2;
                    object = ((Socket)object).getImpl();
                    break block3;
                }
                if (!(object instanceof ServerSocket)) break block4;
                object = ((ServerSocket)object).getImpl();
            }
            return ((SocketImpl)object).getOption(socketOption);
        }
        throw new IllegalArgumentException();
    }

    private static <T> T getOption(DatagramSocket datagramSocket, SocketOption<T> socketOption) throws IOException {
        return datagramSocket.getImpl().getOption(socketOption);
    }

    private static <T> void setOption(Object object, SocketOption<T> socketOption, T t) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!(object instanceof Socket)) break block2;
                    object = ((Socket)object).getImpl();
                    break block3;
                }
                if (!(object instanceof ServerSocket)) break block4;
                object = ((ServerSocket)object).getImpl();
            }
            ((SocketImpl)object).setOption(socketOption, t);
            return;
        }
        throw new IllegalArgumentException();
    }

    private static <T> void setOption(DatagramSocket datagramSocket, SocketOption<T> socketOption, T t) throws IOException {
        datagramSocket.getImpl().setOption(socketOption, t);
    }
}

