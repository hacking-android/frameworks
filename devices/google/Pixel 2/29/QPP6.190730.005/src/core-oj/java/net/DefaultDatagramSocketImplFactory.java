/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.PrintStream;
import java.net.DatagramSocketImpl;
import java.net.PlainDatagramSocketImpl;
import java.net.SocketException;
import java.security.AccessController;
import sun.security.action.GetPropertyAction;

class DefaultDatagramSocketImplFactory {
    static Class<?> prefixImplClass;

    static {
        block10 : {
            String string;
            prefixImplClass = null;
            String string2 = string = null;
            string2 = string;
            Object object = new GetPropertyAction("impl.prefix", null);
            string2 = string;
            string = (String)AccessController.doPrivileged(object);
            if (string == null) break block10;
            string2 = string;
            string2 = string;
            object = new StringBuilder();
            string2 = string;
            ((StringBuilder)object).append("java.net.");
            string2 = string;
            ((StringBuilder)object).append(string);
            string2 = string;
            ((StringBuilder)object).append("DatagramSocketImpl");
            string2 = string;
            try {
                prefixImplClass = Class.forName(((StringBuilder)object).toString());
            }
            catch (Exception exception) {
                object = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't find class: java.net.");
                stringBuilder.append(string2);
                stringBuilder.append("DatagramSocketImpl: check impl.prefix property");
                ((PrintStream)object).println(stringBuilder.toString());
            }
        }
    }

    DefaultDatagramSocketImplFactory() {
    }

    static DatagramSocketImpl createDatagramSocketImpl(boolean bl) throws SocketException {
        Class<?> class_ = prefixImplClass;
        if (class_ != null) {
            try {
                class_ = (DatagramSocketImpl)class_.newInstance();
                return class_;
            }
            catch (Exception exception) {
                throw new SocketException("can't instantiate DatagramSocketImpl");
            }
        }
        return new PlainDatagramSocketImpl();
    }
}

