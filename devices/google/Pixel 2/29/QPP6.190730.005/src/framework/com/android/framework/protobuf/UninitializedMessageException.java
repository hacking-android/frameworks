/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLite;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UninitializedMessageException
extends RuntimeException {
    private static final long serialVersionUID = -7466929953374883507L;
    private final List<String> missingFields;

    public UninitializedMessageException(MessageLite messageLite) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.missingFields = null;
    }

    public UninitializedMessageException(List<String> list) {
        super(UninitializedMessageException.buildDescription(list));
        this.missingFields = list;
    }

    private static String buildDescription(List<String> object) {
        StringBuilder stringBuilder = new StringBuilder("Message missing required fields: ");
        boolean bl = true;
        Iterator<String> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append((String)object);
        }
        return stringBuilder.toString();
    }

    public InvalidProtocolBufferException asInvalidProtocolBufferException() {
        return new InvalidProtocolBufferException(this.getMessage());
    }

    public List<String> getMissingFields() {
        return Collections.unmodifiableList(this.missingFields);
    }
}

