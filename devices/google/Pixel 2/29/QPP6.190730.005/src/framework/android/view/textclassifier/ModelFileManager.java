/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.textclassifier.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class ModelFileManager {
    private final Object mLock = new Object();
    private final Supplier<List<ModelFile>> mModelFileSupplier;
    private List<ModelFile> mModelFiles;

    public ModelFileManager(Supplier<List<ModelFile>> supplier) {
        this.mModelFileSupplier = Preconditions.checkNotNull(supplier);
    }

    public ModelFile findBestModelFile(LocaleList object) {
        object = object != null && !((LocaleList)object).isEmpty() ? ((LocaleList)object).toLanguageTags() : LocaleList.getDefault().toLanguageTags();
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse((String)object);
        Object object2 = null;
        for (ModelFile modelFile : this.listModelFiles()) {
            object = object2;
            if (modelFile.isAnyLanguageSupported(list)) {
                object = object2;
                if (modelFile.isPreferredTo((ModelFile)object2)) {
                    object = modelFile;
                }
            }
            object2 = object;
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ModelFile> listModelFiles() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mModelFiles != null) return this.mModelFiles;
            this.mModelFiles = Collections.unmodifiableList(this.mModelFileSupplier.get());
            return this.mModelFiles;
        }
    }

    public static final class ModelFile {
        public static final String LANGUAGE_INDEPENDENT = "*";
        private final File mFile;
        private final boolean mLanguageIndependent;
        private final List<Locale> mSupportedLocales;
        private final String mSupportedLocalesStr;
        private final int mVersion;

        public ModelFile(File file, int n, List<Locale> list, String string2, boolean bl) {
            this.mFile = Preconditions.checkNotNull(file);
            this.mVersion = n;
            this.mSupportedLocales = Preconditions.checkNotNull(list);
            this.mSupportedLocalesStr = Preconditions.checkNotNull(string2);
            this.mLanguageIndependent = bl;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object instanceof ModelFile) {
                object = (ModelFile)object;
                return TextUtils.equals(this.getPath(), ((ModelFile)object).getPath());
            }
            return false;
        }

        public String getName() {
            return this.mFile.getName();
        }

        public String getPath() {
            return this.mFile.getAbsolutePath();
        }

        public List<Locale> getSupportedLocales() {
            return Collections.unmodifiableList(this.mSupportedLocales);
        }

        public String getSupportedLocalesStr() {
            return this.mSupportedLocalesStr;
        }

        public int getVersion() {
            return this.mVersion;
        }

        public int hashCode() {
            return Objects.hash(this.getPath());
        }

        public boolean isAnyLanguageSupported(List<Locale.LanguageRange> list) {
            Preconditions.checkNotNull(list);
            boolean bl = this.mLanguageIndependent || Locale.lookup(list, this.mSupportedLocales) != null;
            return bl;
        }

        public boolean isPreferredTo(ModelFile modelFile) {
            if (modelFile == null) {
                return true;
            }
            if (!this.mLanguageIndependent && modelFile.mLanguageIndependent) {
                return true;
            }
            if (this.mLanguageIndependent && !modelFile.mLanguageIndependent) {
                return false;
            }
            return this.mVersion > modelFile.getVersion();
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(",");
            Iterator<Locale> iterator = this.mSupportedLocales.iterator();
            while (iterator.hasNext()) {
                stringJoiner.add(iterator.next().toLanguageTag());
            }
            return String.format(Locale.US, "ModelFile { path=%s name=%s version=%d locales=%s }", this.getPath(), this.getName(), this.mVersion, stringJoiner.toString());
        }
    }

    public static final class ModelFileSupplierImpl
    implements Supplier<List<ModelFile>> {
        private final File mFactoryModelDir;
        private final Pattern mModelFilenamePattern;
        private final Function<Integer, String> mSupportedLocalesSupplier;
        private final File mUpdatedModelFile;
        private final Function<Integer, Integer> mVersionSupplier;

        public ModelFileSupplierImpl(File file, String string2, File file2, Function<Integer, Integer> function, Function<Integer, String> function2) {
            this.mUpdatedModelFile = Preconditions.checkNotNull(file2);
            this.mFactoryModelDir = Preconditions.checkNotNull(file);
            this.mModelFilenamePattern = Pattern.compile(Preconditions.checkNotNull(string2));
            this.mVersionSupplier = Preconditions.checkNotNull(function);
            this.mSupportedLocalesSupplier = Preconditions.checkNotNull(function2);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private ModelFile createModelFile(File file) {
            Throwable throwable2222;
            ParcelFileDescriptor parcelFileDescriptor;
            block11 : {
                int n;
                ParcelFileDescriptor parcelFileDescriptor3;
                ParcelFileDescriptor parcelFileDescriptor2;
                int n2;
                Object object;
                block10 : {
                    block9 : {
                        if (!file.exists()) {
                            return null;
                        }
                        parcelFileDescriptor2 = null;
                        parcelFileDescriptor = null;
                        parcelFileDescriptor3 = ParcelFileDescriptor.open(file, 268435456);
                        if (parcelFileDescriptor3 != null) break block9;
                        ModelFileSupplierImpl.maybeCloseAndLogError(parcelFileDescriptor3);
                        return null;
                    }
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    n2 = parcelFileDescriptor3.getFd();
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    n = this.mVersionSupplier.apply(n2);
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    object = this.mSupportedLocalesSupplier.apply(n2);
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    if (!((String)object).isEmpty()) break block10;
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    object = new StringBuilder();
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    ((StringBuilder)object).append("Ignoring ");
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    ((StringBuilder)object).append(file.getAbsolutePath());
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    Log.d("androidtc", ((StringBuilder)object).toString());
                    ModelFileSupplierImpl.maybeCloseAndLogError(parcelFileDescriptor3);
                    return null;
                }
                parcelFileDescriptor = parcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                parcelFileDescriptor = parcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                ArrayList<Locale> arrayList = new ArrayList<Locale>();
                parcelFileDescriptor = parcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                String[] arrstring = ((String)object).split(",");
                parcelFileDescriptor = parcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                int n3 = arrstring.length;
                for (n2 = 0; n2 < n3; ++n2) {
                    parcelFileDescriptor = parcelFileDescriptor3;
                    parcelFileDescriptor2 = parcelFileDescriptor3;
                    arrayList.add(Locale.forLanguageTag(arrstring[n2]));
                }
                parcelFileDescriptor = parcelFileDescriptor3;
                parcelFileDescriptor2 = parcelFileDescriptor3;
                object = new ModelFile(file, n, arrayList, (String)object, "*".equals(object));
                ModelFileSupplierImpl.maybeCloseAndLogError(parcelFileDescriptor3);
                return object;
                {
                    catch (Throwable throwable2222) {
                        break block11;
                    }
                    catch (FileNotFoundException fileNotFoundException) {}
                    parcelFileDescriptor = parcelFileDescriptor2;
                    {
                        parcelFileDescriptor = parcelFileDescriptor2;
                        object = new StringBuilder();
                        parcelFileDescriptor = parcelFileDescriptor2;
                        ((StringBuilder)object).append("Failed to find ");
                        parcelFileDescriptor = parcelFileDescriptor2;
                        ((StringBuilder)object).append(file.getAbsolutePath());
                        parcelFileDescriptor = parcelFileDescriptor2;
                        Log.e("androidtc", ((StringBuilder)object).toString(), fileNotFoundException);
                    }
                    ModelFileSupplierImpl.maybeCloseAndLogError(parcelFileDescriptor2);
                    return null;
                }
            }
            ModelFileSupplierImpl.maybeCloseAndLogError(parcelFileDescriptor);
            throw throwable2222;
        }

        private static void maybeCloseAndLogError(ParcelFileDescriptor parcelFileDescriptor) {
            if (parcelFileDescriptor == null) {
                return;
            }
            try {
                parcelFileDescriptor.close();
            }
            catch (IOException iOException) {
                Log.e("androidtc", "Error closing file.", iOException);
            }
        }

        @Override
        public List<ModelFile> get() {
            File[] arrfile;
            ArrayList<ModelFile> arrayList = new ArrayList<ModelFile>();
            if (this.mUpdatedModelFile.exists() && (arrfile = this.createModelFile(this.mUpdatedModelFile)) != null) {
                arrayList.add((ModelFile)arrfile);
            }
            if (this.mFactoryModelDir.exists() && this.mFactoryModelDir.isDirectory()) {
                for (File file : this.mFactoryModelDir.listFiles()) {
                    ModelFile modelFile;
                    if (!this.mModelFilenamePattern.matcher(file.getName()).matches() || !file.isFile() || (modelFile = this.createModelFile(file)) == null) continue;
                    arrayList.add(modelFile);
                }
            }
            return arrayList;
        }
    }

}

