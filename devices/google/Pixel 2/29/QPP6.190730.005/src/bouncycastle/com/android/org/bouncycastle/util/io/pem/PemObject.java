/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import com.android.org.bouncycastle.util.io.pem.PemGenerationException;
import com.android.org.bouncycastle.util.io.pem.PemObjectGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PemObject
implements PemObjectGenerator {
    private static final List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
    private byte[] content;
    private List headers;
    private String type;

    public PemObject(String string, List list, byte[] arrby) {
        this.type = string;
        this.headers = Collections.unmodifiableList(list);
        this.content = arrby;
    }

    public PemObject(String string, byte[] arrby) {
        this(string, EMPTY_LIST, arrby);
    }

    @Override
    public PemObject generate() throws PemGenerationException {
        return this;
    }

    public byte[] getContent() {
        return this.content;
    }

    public List getHeaders() {
        return this.headers;
    }

    public String getType() {
        return this.type;
    }
}

