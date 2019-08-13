/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import sun.nio.fs.DynamicFileAttributeView;
import sun.nio.fs.Util;

abstract class AbstractBasicFileAttributeView
implements BasicFileAttributeView,
DynamicFileAttributeView {
    private static final String CREATION_TIME_NAME = "creationTime";
    private static final String FILE_KEY_NAME = "fileKey";
    private static final String IS_DIRECTORY_NAME = "isDirectory";
    private static final String IS_OTHER_NAME = "isOther";
    private static final String IS_REGULAR_FILE_NAME = "isRegularFile";
    private static final String IS_SYMBOLIC_LINK_NAME = "isSymbolicLink";
    private static final String LAST_ACCESS_TIME_NAME = "lastAccessTime";
    private static final String LAST_MODIFIED_TIME_NAME = "lastModifiedTime";
    private static final String SIZE_NAME = "size";
    static final Set<String> basicAttributeNames = Util.newSet("size", "creationTime", "lastAccessTime", "lastModifiedTime", "fileKey", "isDirectory", "isRegularFile", "isSymbolicLink", "isOther");

    protected AbstractBasicFileAttributeView() {
    }

    final void addRequestedBasicAttributes(BasicFileAttributes basicFileAttributes, AttributesBuilder attributesBuilder) {
        if (attributesBuilder.match(SIZE_NAME)) {
            attributesBuilder.add(SIZE_NAME, basicFileAttributes.size());
        }
        if (attributesBuilder.match(CREATION_TIME_NAME)) {
            attributesBuilder.add(CREATION_TIME_NAME, basicFileAttributes.creationTime());
        }
        if (attributesBuilder.match(LAST_ACCESS_TIME_NAME)) {
            attributesBuilder.add(LAST_ACCESS_TIME_NAME, basicFileAttributes.lastAccessTime());
        }
        if (attributesBuilder.match(LAST_MODIFIED_TIME_NAME)) {
            attributesBuilder.add(LAST_MODIFIED_TIME_NAME, basicFileAttributes.lastModifiedTime());
        }
        if (attributesBuilder.match(FILE_KEY_NAME)) {
            attributesBuilder.add(FILE_KEY_NAME, basicFileAttributes.fileKey());
        }
        if (attributesBuilder.match(IS_DIRECTORY_NAME)) {
            attributesBuilder.add(IS_DIRECTORY_NAME, basicFileAttributes.isDirectory());
        }
        if (attributesBuilder.match(IS_REGULAR_FILE_NAME)) {
            attributesBuilder.add(IS_REGULAR_FILE_NAME, basicFileAttributes.isRegularFile());
        }
        if (attributesBuilder.match(IS_SYMBOLIC_LINK_NAME)) {
            attributesBuilder.add(IS_SYMBOLIC_LINK_NAME, basicFileAttributes.isSymbolicLink());
        }
        if (attributesBuilder.match(IS_OTHER_NAME)) {
            attributesBuilder.add(IS_OTHER_NAME, basicFileAttributes.isOther());
        }
    }

    @Override
    public String name() {
        return "basic";
    }

    @Override
    public Map<String, Object> readAttributes(String[] object) throws IOException {
        object = AttributesBuilder.create(basicAttributeNames, (String[])object);
        this.addRequestedBasicAttributes(this.readAttributes(), (AttributesBuilder)object);
        return ((AttributesBuilder)object).unmodifiableMap();
    }

    @Override
    public void setAttribute(String string, Object object) throws IOException {
        if (string.equals(LAST_MODIFIED_TIME_NAME)) {
            this.setTimes((FileTime)object, null, null);
            return;
        }
        if (string.equals(LAST_ACCESS_TIME_NAME)) {
            this.setTimes(null, (FileTime)object, null);
            return;
        }
        if (string.equals(CREATION_TIME_NAME)) {
            this.setTimes(null, null, (FileTime)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("'");
        ((StringBuilder)object).append(this.name());
        ((StringBuilder)object).append(":");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("' not recognized");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static class AttributesBuilder {
        private boolean copyAll;
        private Map<String, Object> map = new HashMap<String, Object>();
        private Set<String> names = new HashSet<String>();

        private AttributesBuilder(Set<String> object, String[] arrstring) {
            for (String string : arrstring) {
                if (string.equals("*")) {
                    this.copyAll = true;
                    continue;
                }
                if (object.contains(string)) {
                    this.names.add(string);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("' not recognized");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }

        static AttributesBuilder create(Set<String> set, String[] arrstring) {
            return new AttributesBuilder(set, arrstring);
        }

        void add(String string, Object object) {
            this.map.put(string, object);
        }

        boolean match(String string) {
            boolean bl = this.copyAll || this.names.contains(string);
            return bl;
        }

        Map<String, Object> unmodifiableMap() {
            return Collections.unmodifiableMap(this.map);
        }
    }

}

