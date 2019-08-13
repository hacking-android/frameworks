/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.io;

import android.content.Context;
import android.content.res.Resources;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.KeyValueMap;
import android.filterfw.io.GraphIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

public abstract class GraphReader {
    protected KeyValueMap mReferences = new KeyValueMap();

    public void addReference(String string2, Object object) {
        this.mReferences.put(string2, object);
    }

    public void addReferencesByKeysAndValues(Object ... arrobject) {
        this.mReferences.setKeyValues(arrobject);
    }

    public void addReferencesByMap(KeyValueMap keyValueMap) {
        this.mReferences.putAll(keyValueMap);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FilterGraph readGraphResource(Context object, int n) throws GraphIOException {
        object = new InputStreamReader(((Context)object).getResources().openRawResource(n));
        StringWriter stringWriter = new StringWriter();
        char[] arrc = new char[1024];
        try {
            do {
                if ((n = ((InputStreamReader)object).read(arrc, 0, 1024)) <= 0) {
                    return this.readGraphString(stringWriter.toString());
                }
                stringWriter.write(arrc, 0, n);
            } while (true);
        }
        catch (IOException iOException) {
            throw new RuntimeException("Could not read specified resource file!");
        }
    }

    public abstract FilterGraph readGraphString(String var1) throws GraphIOException;

    public abstract KeyValueMap readKeyValueAssignments(String var1) throws GraphIOException;
}

