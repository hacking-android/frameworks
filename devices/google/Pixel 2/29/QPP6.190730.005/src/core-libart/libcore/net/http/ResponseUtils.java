/*
 * Decompiled with CFR 0.145.
 */
package libcore.net.http;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    private static Map<String, String> parseContentTypeParameters(String object) {
        HashMap<Object, Object> hashMap = Collections.EMPTY_MAP;
        String[] arrstring = ((String)object).split(";");
        object = hashMap;
        if (arrstring.length > 1) {
            hashMap = new HashMap<Object, Object>();
            int n = 1;
            do {
                Object object2;
                object = hashMap;
                if (n >= arrstring.length) break;
                object = arrstring[n];
                if (!((String)object).isEmpty() && ((String[])(object2 = ((String)object).split("="))).length == 2) {
                    object = object2[0].trim().toLowerCase();
                    object2 = object2[1].trim();
                    if (!((String)object).isEmpty() && !((String)object2).isEmpty()) {
                        hashMap.put(object, object2);
                    }
                }
                ++n;
            } while (true);
        }
        return object;
    }

    public static Charset responseCharset(String string) throws IllegalCharsetNameException, UnsupportedCharsetException {
        Charset charset;
        Charset charset2 = charset = StandardCharsets.UTF_8;
        if (string != null) {
            string = ResponseUtils.parseContentTypeParameters(string).get("charset");
            charset2 = charset;
            if (string != null) {
                charset2 = Charset.forName(string);
            }
        }
        return charset2;
    }
}

