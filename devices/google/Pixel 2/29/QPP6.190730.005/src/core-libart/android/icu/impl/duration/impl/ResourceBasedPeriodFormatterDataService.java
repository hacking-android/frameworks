/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.ICUData;
import android.icu.impl.duration.impl.DataRecord;
import android.icu.impl.duration.impl.PeriodFormatterData;
import android.icu.impl.duration.impl.PeriodFormatterDataService;
import android.icu.impl.duration.impl.XMLRecordReader;
import android.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ResourceBasedPeriodFormatterDataService
extends PeriodFormatterDataService {
    private static final String PATH = "data/";
    private static final ResourceBasedPeriodFormatterDataService singleton = new ResourceBasedPeriodFormatterDataService();
    private Collection<String> availableLocales;
    private Map<String, PeriodFormatterData> cache = new HashMap<String, PeriodFormatterData>();
    private PeriodFormatterData lastData = null;
    private String lastLocale = null;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private ResourceBasedPeriodFormatterDataService() {
        Throwable throwable2222;
        Serializable serializable = new ArrayList();
        InputStream inputStream = ICUData.getRequiredStream(this.getClass(), "data/index.txt");
        Object object = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader((Reader)object);
        while ((object = bufferedReader.readLine()) != null) {
            if (((String)(object = ((String)object).trim())).startsWith("#") || ((String)object).length() == 0) continue;
            serializable.add(object);
        }
        bufferedReader.close();
        try {
            inputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.availableLocales = Collections.unmodifiableList(serializable);
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IO Error reading data/index.txt: ");
                stringBuilder.append(iOException.toString());
                super(stringBuilder.toString());
                throw serializable;
            }
        }
        try {
            inputStream.close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    public static ResourceBasedPeriodFormatterDataService getInstance() {
        return singleton;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PeriodFormatterData get(String object) {
        int n = ((String)object).indexOf(64);
        CharSequence charSequence = object;
        if (n != -1) {
            charSequence = ((String)object).substring(0, n);
        }
        synchronized (this) {
            Object object2;
            if (this.lastLocale != null && this.lastLocale.equals(charSequence)) {
                return this.lastData;
            }
            object = object2 = this.cache.get(charSequence);
            if (object2 == null) {
                Object object3;
                block12 : {
                    object = charSequence;
                    do {
                        object3 = object;
                        if (this.availableLocales.contains(object)) break block12;
                        n = ((String)object).lastIndexOf("_");
                        if (n > -1) {
                            object = ((String)object).substring(0, n);
                            continue;
                        }
                        if ("test".equals(object)) break;
                        object = "test";
                    } while (true);
                    object3 = null;
                }
                if (object3 == null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Duration data not found for  ");
                    ((StringBuilder)object2).append((String)charSequence);
                    object = new MissingResourceException(((StringBuilder)object2).toString(), PATH, (String)charSequence);
                    throw object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("data/pfd_");
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(".xml");
                String string = ((StringBuilder)object).toString();
                try {
                    object = new InputStreamReader(ICUData.getRequiredStream(this.getClass(), string), "UTF-8");
                    XMLRecordReader xMLRecordReader = new XMLRecordReader((Reader)object);
                    object3 = DataRecord.read((String)object3, xMLRecordReader);
                    ((InputStreamReader)object).close();
                    object = object2;
                    if (object3 != null) {
                        object = new PeriodFormatterData((String)charSequence, (DataRecord)object3);
                    }
                    this.cache.put((String)charSequence, (PeriodFormatterData)object);
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to close() resource ");
                    ((StringBuilder)object).append(string);
                    object2 = new ICUUncheckedIOException(((StringBuilder)object).toString(), iOException);
                    throw object2;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unhandled encoding for resource ");
                    ((StringBuilder)charSequence).append(string);
                    MissingResourceException missingResourceException = new MissingResourceException(((StringBuilder)charSequence).toString(), string, "");
                    throw missingResourceException;
                }
            }
            this.lastData = object;
            this.lastLocale = charSequence;
            return object;
        }
    }

    @Override
    public Collection<String> getAvailableLocales() {
        return this.availableLocales;
    }
}

