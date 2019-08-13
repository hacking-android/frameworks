/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.SortedMap;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import sun.security.action.GetPropertyAction;

public abstract class Pack200 {
    private static final String PACK_PROVIDER = "java.util.jar.Pack200.Packer";
    private static final String UNPACK_PROVIDER = "java.util.jar.Pack200.Unpacker";
    private static Class<?> packerImpl;
    private static Class<?> unpackerImpl;

    private Pack200() {
    }

    private static Object newInstance(String string) {
        synchronized (Pack200.class) {
            Class<?> class_;
            Object object;
            Object object2;
            Object object3;
            Object object4;
            Object object5;
            block20 : {
                Class<?> class_2;
                block19 : {
                    object4 = object = "(unknown)";
                    object3 = object;
                    object5 = object;
                    if (!PACK_PROVIDER.equals(string)) break block19;
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    class_2 = packerImpl;
                }
                object4 = object;
                object3 = object;
                object5 = object;
                class_2 = unpackerImpl;
                object2 = object;
                class_ = class_2;
                if (class_2 == null) {
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    object2 = new GetPropertyAction(string, "");
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    object2 = object = (String)AccessController.doPrivileged(object2);
                    class_ = class_2;
                    if (object == null) break block20;
                    object2 = object;
                    class_ = class_2;
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    if (((String)object).equals("")) break block20;
                    object4 = object;
                    object3 = object;
                    object5 = object;
                    class_ = Class.forName((String)object);
                    object2 = object;
                }
            }
            object4 = object2;
            object3 = object2;
            object5 = object2;
            try {
                object2 = class_.newInstance();
                return object2;
            }
            catch (IllegalAccessException illegalAccessException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot access class: ");
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(":\ncheck property ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" in your properties file.");
                object3 = new Error(((StringBuilder)object2).toString(), illegalAccessException);
                throw object3;
            }
            catch (InstantiationException instantiationException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not instantiate: ");
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(":\ncheck property ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" in your properties file.");
                object4 = new Error(((StringBuilder)object).toString(), instantiationException);
                throw object4;
            }
            catch (ClassNotFoundException classNotFoundException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Class not found: ");
                ((StringBuilder)object2).append((String)object5);
                ((StringBuilder)object2).append(":\ncheck property ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" in your properties file.");
                object = new Error(((StringBuilder)object2).toString(), classNotFoundException);
                throw object;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    public static Packer newPacker() {
        synchronized (Pack200.class) {
            Packer packer = (Packer)Pack200.newInstance(PACK_PROVIDER);
            return packer;
        }
    }

    public static Unpacker newUnpacker() {
        return (Unpacker)Pack200.newInstance(UNPACK_PROVIDER);
    }

    public static interface Packer {
        public static final String CLASS_ATTRIBUTE_PFX = "pack.class.attribute.";
        public static final String CODE_ATTRIBUTE_PFX = "pack.code.attribute.";
        public static final String DEFLATE_HINT = "pack.deflate.hint";
        public static final String EFFORT = "pack.effort";
        public static final String ERROR = "error";
        public static final String FALSE = "false";
        public static final String FIELD_ATTRIBUTE_PFX = "pack.field.attribute.";
        public static final String KEEP = "keep";
        public static final String KEEP_FILE_ORDER = "pack.keep.file.order";
        public static final String LATEST = "latest";
        public static final String METHOD_ATTRIBUTE_PFX = "pack.method.attribute.";
        public static final String MODIFICATION_TIME = "pack.modification.time";
        public static final String PASS = "pass";
        public static final String PASS_FILE_PFX = "pack.pass.file.";
        public static final String PROGRESS = "pack.progress";
        public static final String SEGMENT_LIMIT = "pack.segment.limit";
        public static final String STRIP = "strip";
        public static final String TRUE = "true";
        public static final String UNKNOWN_ATTRIBUTE = "pack.unknown.attribute";

        @Deprecated
        default public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        }

        public void pack(JarFile var1, OutputStream var2) throws IOException;

        public void pack(JarInputStream var1, OutputStream var2) throws IOException;

        public SortedMap<String, String> properties();

        @Deprecated
        default public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        }
    }

    public static interface Unpacker {
        public static final String DEFLATE_HINT = "unpack.deflate.hint";
        public static final String FALSE = "false";
        public static final String KEEP = "keep";
        public static final String PROGRESS = "unpack.progress";
        public static final String TRUE = "true";

        @Deprecated
        default public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        }

        public SortedMap<String, String> properties();

        @Deprecated
        default public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        }

        public void unpack(File var1, JarOutputStream var2) throws IOException;

        public void unpack(InputStream var1, JarOutputStream var2) throws IOException;
    }

}

