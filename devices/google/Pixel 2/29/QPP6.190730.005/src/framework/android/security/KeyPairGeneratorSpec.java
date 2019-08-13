/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.content.Context;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

@Deprecated
public final class KeyPairGeneratorSpec
implements AlgorithmParameterSpec {
    private final Context mContext;
    private final Date mEndDate;
    private final int mFlags;
    private final int mKeySize;
    private final String mKeyType;
    private final String mKeystoreAlias;
    private final BigInteger mSerialNumber;
    private final AlgorithmParameterSpec mSpec;
    private final Date mStartDate;
    private final X500Principal mSubjectDN;

    public KeyPairGeneratorSpec(Context context, String string2, String string3, int n, AlgorithmParameterSpec algorithmParameterSpec, X500Principal x500Principal, BigInteger bigInteger, Date date, Date date2, int n2) {
        if (context != null) {
            if (!TextUtils.isEmpty(string2)) {
                if (x500Principal != null) {
                    if (bigInteger != null) {
                        if (date != null) {
                            if (date2 != null) {
                                if (!date2.before(date)) {
                                    if (!date2.before(date)) {
                                        this.mContext = context;
                                        this.mKeystoreAlias = string2;
                                        this.mKeyType = string3;
                                        this.mKeySize = n;
                                        this.mSpec = algorithmParameterSpec;
                                        this.mSubjectDN = x500Principal;
                                        this.mSerialNumber = bigInteger;
                                        this.mStartDate = date;
                                        this.mEndDate = date2;
                                        this.mFlags = n2;
                                        return;
                                    }
                                    throw new IllegalArgumentException("endDate < startDate");
                                }
                                throw new IllegalArgumentException("endDate < startDate");
                            }
                            throw new IllegalArgumentException("endDate == null");
                        }
                        throw new IllegalArgumentException("startDate == null");
                    }
                    throw new IllegalArgumentException("serialNumber == null");
                }
                throw new IllegalArgumentException("subjectDN == null");
            }
            throw new IllegalArgumentException("keyStoreAlias must not be empty");
        }
        throw new IllegalArgumentException("context == null");
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Date getEndDate() {
        return this.mEndDate;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public String getKeyType() {
        return this.mKeyType;
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public BigInteger getSerialNumber() {
        return this.mSerialNumber;
    }

    public Date getStartDate() {
        return this.mStartDate;
    }

    public X500Principal getSubjectDN() {
        return this.mSubjectDN;
    }

    public boolean isEncryptionRequired() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public static final class Builder {
        private final Context mContext;
        private Date mEndDate;
        private int mFlags;
        private int mKeySize = -1;
        private String mKeyType;
        private String mKeystoreAlias;
        private BigInteger mSerialNumber;
        private AlgorithmParameterSpec mSpec;
        private Date mStartDate;
        private X500Principal mSubjectDN;

        public Builder(Context context) {
            if (context != null) {
                this.mContext = context;
                return;
            }
            throw new NullPointerException("context == null");
        }

        public KeyPairGeneratorSpec build() {
            return new KeyPairGeneratorSpec(this.mContext, this.mKeystoreAlias, this.mKeyType, this.mKeySize, this.mSpec, this.mSubjectDN, this.mSerialNumber, this.mStartDate, this.mEndDate, this.mFlags);
        }

        public Builder setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec != null) {
                this.mSpec = algorithmParameterSpec;
                return this;
            }
            throw new NullPointerException("spec == null");
        }

        public Builder setAlias(String string2) {
            if (string2 != null) {
                this.mKeystoreAlias = string2;
                return this;
            }
            throw new NullPointerException("alias == null");
        }

        public Builder setEncryptionRequired() {
            this.mFlags |= 1;
            return this;
        }

        public Builder setEndDate(Date date) {
            if (date != null) {
                this.mEndDate = date;
                return this;
            }
            throw new NullPointerException("endDate == null");
        }

        public Builder setKeySize(int n) {
            if (n >= 0) {
                this.mKeySize = n;
                return this;
            }
            throw new IllegalArgumentException("keySize < 0");
        }

        public Builder setKeyType(String string2) throws NoSuchAlgorithmException {
            if (string2 != null) {
                try {
                    KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported key type: ");
                    stringBuilder.append(string2);
                    throw new NoSuchAlgorithmException(stringBuilder.toString());
                }
                this.mKeyType = string2;
                return this;
            }
            throw new NullPointerException("keyType == null");
        }

        public Builder setSerialNumber(BigInteger bigInteger) {
            if (bigInteger != null) {
                this.mSerialNumber = bigInteger;
                return this;
            }
            throw new NullPointerException("serialNumber == null");
        }

        public Builder setStartDate(Date date) {
            if (date != null) {
                this.mStartDate = date;
                return this;
            }
            throw new NullPointerException("startDate == null");
        }

        public Builder setSubject(X500Principal x500Principal) {
            if (x500Principal != null) {
                this.mSubjectDN = x500Principal;
                return this;
            }
            throw new NullPointerException("subject == null");
        }
    }

}

