/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.File;
import java.net.URL;
import sun.net.www.ParseUtil;

public class FileURLMapper {
    String path;
    URL url;

    public FileURLMapper(URL uRL) {
        this.url = uRL;
    }

    public boolean exists() {
        String string = this.getPath();
        if (string == null) {
            return false;
        }
        return new File(string).exists();
    }

    public String getPath() {
        String string = this.path;
        if (string != null) {
            return string;
        }
        string = this.url.getHost();
        if (string == null || "".equals(string) || "localhost".equalsIgnoreCase(string)) {
            this.path = this.url.getFile();
            this.path = ParseUtil.decode(this.path);
        }
        return this.path;
    }
}

