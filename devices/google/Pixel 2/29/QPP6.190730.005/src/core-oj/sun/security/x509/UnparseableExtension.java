/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.lang.reflect.Field;
import sun.misc.HexDumpEncoder;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;
import sun.security.x509.OIDMap;

class UnparseableExtension
extends Extension {
    private String name;
    private Throwable why;

    public UnparseableExtension(Extension object, Throwable throwable) {
        block3 : {
            super((Extension)object);
            this.name = "";
            object = OIDMap.getClass(((Extension)object).getExtensionId());
            if (object == null) break block3;
            try {
                Field field = ((Class)object).getDeclaredField("NAME");
                object = new StringBuilder();
                ((StringBuilder)object).append((String)field.get(null));
                ((StringBuilder)object).append(" ");
                this.name = ((StringBuilder)object).toString();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.why = throwable;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("Unparseable ");
        stringBuilder.append(this.name);
        stringBuilder.append("extension due to\n");
        stringBuilder.append(this.why);
        stringBuilder.append("\n\n");
        stringBuilder.append(new HexDumpEncoder().encodeBuffer(this.getExtensionValue()));
        return stringBuilder.toString();
    }
}

