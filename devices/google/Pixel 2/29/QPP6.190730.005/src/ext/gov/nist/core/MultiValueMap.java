/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface MultiValueMap<K, V>
extends Map<K, List<V>>,
Serializable {
}

