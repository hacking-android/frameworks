/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.security.util.AbstractAlgorithmConstraints;
import sun.security.util.AlgorithmDecomposer;
import sun.security.util.CertConstraintParameters;
import sun.security.util.Debug;
import sun.security.util.KeyUtil;

public class DisabledAlgorithmConstraints
extends AbstractAlgorithmConstraints {
    public static final String PROPERTY_CERTPATH_DISABLED_ALGS = "jdk.certpath.disabledAlgorithms";
    public static final String PROPERTY_JAR_DISABLED_ALGS = "jdk.jar.disabledAlgorithms";
    public static final String PROPERTY_TLS_DISABLED_ALGS = "jdk.tls.disabledAlgorithms";
    private static final Debug debug = Debug.getInstance("certpath");
    private final Constraints algorithmConstraints;
    private final String[] disabledAlgorithms;

    public DisabledAlgorithmConstraints(String string) {
        this(string, new AlgorithmDecomposer());
    }

    public DisabledAlgorithmConstraints(String string, AlgorithmDecomposer algorithmDecomposer) {
        super(algorithmDecomposer);
        this.disabledAlgorithms = DisabledAlgorithmConstraints.getAlgorithms(string);
        this.algorithmConstraints = new Constraints(this.disabledAlgorithms);
    }

    private void checkConstraints(Set<CryptoPrimitive> object, CertConstraintParameters certConstraintParameters) throws CertPathValidatorException {
        X509Certificate x509Certificate = certConstraintParameters.getCertificate();
        String string = x509Certificate.getSigAlgName();
        if (this.permits((Set<CryptoPrimitive>)object, string, null)) {
            if (this.permits((Set<CryptoPrimitive>)object, x509Certificate.getPublicKey().getAlgorithm(), null)) {
                this.algorithmConstraints.permits(certConstraintParameters);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Algorithm constraints check failed on disabled public key algorithm: ");
            ((StringBuilder)object).append(string);
            throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Algorithm constraints check failed on disabled signature algorithm: ");
        ((StringBuilder)object).append(string);
        throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
    }

    private boolean checkConstraints(Set<CryptoPrimitive> set, String string, Key key, AlgorithmParameters algorithmParameters) {
        if (key != null) {
            if (string != null && string.length() != 0 && !this.permits(set, string, algorithmParameters)) {
                return false;
            }
            if (!this.permits(set, key.getAlgorithm(), null)) {
                return false;
            }
            return this.algorithmConstraints.permits(key);
        }
        throw new IllegalArgumentException("The key cannot be null");
    }

    public boolean checkProperty(String string) {
        string = string.toLowerCase(Locale.ENGLISH);
        String[] arrstring = this.disabledAlgorithms;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (arrstring[i].toLowerCase(Locale.ENGLISH).indexOf(string) < 0) continue;
            return true;
        }
        return false;
    }

    public final void permits(Set<CryptoPrimitive> set, X509Certificate x509Certificate) throws CertPathValidatorException {
        this.checkConstraints(set, new CertConstraintParameters(x509Certificate));
    }

    public final void permits(Set<CryptoPrimitive> set, CertConstraintParameters certConstraintParameters) throws CertPathValidatorException {
        this.checkConstraints(set, certConstraintParameters);
    }

    @Override
    public final boolean permits(Set<CryptoPrimitive> set, String string, AlgorithmParameters algorithmParameters) {
        if (set != null && !set.isEmpty()) {
            return DisabledAlgorithmConstraints.checkAlgorithm(this.disabledAlgorithms, string, this.decomposer);
        }
        throw new IllegalArgumentException("No cryptographic primitive specified");
    }

    @Override
    public final boolean permits(Set<CryptoPrimitive> set, String string, Key key, AlgorithmParameters algorithmParameters) {
        if (string != null && string.length() != 0) {
            return this.checkConstraints(set, string, key, algorithmParameters);
        }
        throw new IllegalArgumentException("No algorithm name specified");
    }

    @Override
    public final boolean permits(Set<CryptoPrimitive> set, Key key) {
        return this.checkConstraints(set, "", key, null);
    }

    private static abstract class Constraint {
        String algorithm;
        Constraint nextConstraint = null;

        private Constraint() {
        }

        public abstract void permits(CertConstraintParameters var1) throws CertPathValidatorException;

        public boolean permits(Key key) {
            return true;
        }

        static enum Operator {
            EQ,
            NE,
            LT,
            LE,
            GT,
            GE;
            

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            static Operator of(String var0) {
                block8 : {
                    block3 : {
                        block4 : {
                            block5 : {
                                block6 : {
                                    block7 : {
                                        var1_1 = var0.hashCode();
                                        if (var1_1 == 60) break block3;
                                        if (var1_1 == 62) break block4;
                                        if (var1_1 == 1084) break block5;
                                        if (var1_1 == 1921) break block6;
                                        if (var1_1 == 1952) break block7;
                                        if (var1_1 != 1983 || !var0.equals(">=")) ** GOTO lbl-1000
                                        var1_1 = 5;
                                        break block8;
                                    }
                                    if (!var0.equals("==")) ** GOTO lbl-1000
                                    var1_1 = 0;
                                    break block8;
                                }
                                if (!var0.equals("<=")) ** GOTO lbl-1000
                                var1_1 = 3;
                                break block8;
                            }
                            if (!var0.equals("!=")) ** GOTO lbl-1000
                            var1_1 = 1;
                            break block8;
                        }
                        if (!var0.equals(">")) ** GOTO lbl-1000
                        var1_1 = 4;
                        break block8;
                    }
                    if (var0.equals("<")) {
                        var1_1 = 2;
                    } else lbl-1000: // 6 sources:
                    {
                        var1_1 = -1;
                    }
                }
                if (var1_1 == 0) return Operator.EQ;
                if (var1_1 == 1) return Operator.NE;
                if (var1_1 == 2) return Operator.LT;
                if (var1_1 == 3) return Operator.LE;
                if (var1_1 == 4) return Operator.GT;
                if (var1_1 == 5) {
                    return Operator.GE;
                }
                var2_2 = new StringBuilder();
                var2_2.append("Error in security property. ");
                var2_2.append(var0);
                var2_2.append(" is not a legal Operator");
                throw new IllegalArgumentException(var2_2.toString());
            }
        }

    }

    private static class Constraints {
        private static final Pattern keySizePattern = Pattern.compile("keySize\\s*(<=|<|==|!=|>|>=)\\s*(\\d+)");
        private Map<String, Set<Constraint>> constraintsMap = new HashMap<String, Set<Constraint>>();

        public Constraints(String[] object) {
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                Object object2;
                int n2;
                Object object3 = object[i];
                if (object3 == null || ((String)object3).isEmpty()) continue;
                String string = ((String)object3).trim();
                if (debug != null) {
                    object3 = debug;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Constraints: ");
                    ((StringBuilder)object2).append(string);
                    ((Debug)object3).println(((StringBuilder)object2).toString());
                }
                if ((n2 = string.indexOf(32)) > 0) {
                    String string2 = AlgorithmDecomposer.hashName(string.substring(0, n2).toUpperCase(Locale.ENGLISH));
                    String[] arrstring = string.substring(n2 + 1).split("&");
                    int n3 = arrstring.length;
                    boolean bl = false;
                    object2 = null;
                    object3 = null;
                    for (int j = 0; j < n3; ++j) {
                        boolean bl2;
                        String string3 = arrstring[j].trim();
                        Matcher matcher = keySizePattern.matcher(string3);
                        if (matcher.matches()) {
                            if (debug != null) {
                                Debug debug = debug;
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("Constraints set to keySize: ");
                                ((StringBuilder)object3).append(string3);
                                debug.println(((StringBuilder)object3).toString());
                            }
                            object3 = new KeySizeConstraint(string2, Constraint.Operator.of(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                            bl2 = bl;
                        } else {
                            bl2 = bl;
                            if (string3.equalsIgnoreCase("jdkCA")) {
                                if (debug != null) {
                                    debug.println("Constraints set to jdkCA.");
                                }
                                if (!bl) {
                                    object3 = new jdkCAConstraint(string2);
                                    bl2 = true;
                                } else {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Only one jdkCA entry allowed in property. Constraint: ");
                                    ((StringBuilder)object).append(string);
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                            }
                        }
                        if (object2 == null) {
                            if (!this.constraintsMap.containsKey(string2)) {
                                this.constraintsMap.putIfAbsent(string2, new HashSet());
                            }
                            if (object3 != null) {
                                this.constraintsMap.get(string2).add((Constraint)object3);
                            }
                        } else {
                            ((Constraint)object2).nextConstraint = object3;
                        }
                        object2 = object3;
                        bl = bl2;
                    }
                    continue;
                }
                this.constraintsMap.putIfAbsent(string.toUpperCase(Locale.ENGLISH), new HashSet());
            }
        }

        private Set<Constraint> getConstraints(String string) {
            return this.constraintsMap.get(string);
        }

        public void permits(CertConstraintParameters certConstraintParameters) throws CertPathValidatorException {
            Set<Constraint> set;
            Object object = certConstraintParameters.getCertificate();
            if (debug != null) {
                set = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Constraints.permits(): ");
                stringBuilder.append(((X509Certificate)object).getSigAlgName());
                ((Debug)((Object)set)).println(stringBuilder.toString());
            }
            if ((set = AlgorithmDecomposer.decomposeOneHash(((X509Certificate)object).getSigAlgName())) != null && !set.isEmpty()) {
                set.add((Constraint)((Object)((Certificate)object).getPublicKey().getAlgorithm()));
                object = set.iterator();
                while (object.hasNext()) {
                    set = this.getConstraints((String)object.next());
                    if (set == null) continue;
                    set = set.iterator();
                    while (set.hasNext()) {
                        ((Constraint)set.next()).permits(certConstraintParameters);
                    }
                }
                return;
            }
        }

        public boolean permits(Key key) {
            Set<Constraint> set = this.getConstraints(key.getAlgorithm());
            if (set == null) {
                return true;
            }
            set = set.iterator();
            while (set.hasNext()) {
                if (((Constraint)set.next()).permits(key)) continue;
                if (debug != null) {
                    set = debug;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("keySizeConstraint: failed key constraint check ");
                    stringBuilder.append(KeyUtil.getKeySize(key));
                    ((Debug)((Object)set)).println(stringBuilder.toString());
                }
                return false;
            }
            return true;
        }
    }

    private static class KeySizeConstraint
    extends Constraint {
        private int maxSize;
        private int minSize;
        private int prohibitedSize = -1;

        public KeySizeConstraint(String string, Constraint.Operator operator, int n) {
            this.algorithm = string;
            int n2 = 1.$SwitchMap$sun$security$util$DisabledAlgorithmConstraints$Constraint$Operator[operator.ordinal()];
            int n3 = 0;
            switch (n2) {
                default: {
                    this.minSize = Integer.MAX_VALUE;
                    this.maxSize = -1;
                    break;
                }
                case 6: {
                    this.minSize = 0;
                    if (n > 1) {
                        n3 = n - 1;
                    }
                    this.maxSize = n3;
                    break;
                }
                case 5: {
                    this.minSize = 0;
                    this.maxSize = n;
                    break;
                }
                case 4: {
                    this.minSize = n + 1;
                    this.maxSize = Integer.MAX_VALUE;
                    break;
                }
                case 3: {
                    this.minSize = n;
                    this.maxSize = Integer.MAX_VALUE;
                    break;
                }
                case 2: {
                    this.minSize = n;
                    this.maxSize = n;
                    break;
                }
                case 1: {
                    this.minSize = 0;
                    this.maxSize = Integer.MAX_VALUE;
                    this.prohibitedSize = n;
                }
            }
        }

        private boolean permitsImpl(Key key) {
            int n = this.algorithm.compareToIgnoreCase(key.getAlgorithm());
            boolean bl = true;
            if (n != 0) {
                return true;
            }
            n = KeyUtil.getKeySize(key);
            if (n == 0) {
                return false;
            }
            if (n > 0) {
                if (n < this.minSize || n > this.maxSize || this.prohibitedSize == n) {
                    bl = false;
                }
                return bl;
            }
            return true;
        }

        @Override
        public void permits(CertConstraintParameters certConstraintParameters) throws CertPathValidatorException {
            if (!this.permitsImpl(certConstraintParameters.getCertificate().getPublicKey())) {
                if (this.nextConstraint != null) {
                    this.nextConstraint.permits(certConstraintParameters);
                    return;
                }
                throw new CertPathValidatorException("Algorithm constraints check failed on keysize limits", null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
            }
        }

        @Override
        public boolean permits(Key key) {
            if (this.nextConstraint != null && this.nextConstraint.permits(key)) {
                return true;
            }
            if (debug != null) {
                Debug debug = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("KeySizeConstraints.permits(): ");
                stringBuilder.append(this.algorithm);
                debug.println(stringBuilder.toString());
            }
            return this.permitsImpl(key);
        }
    }

    private static class jdkCAConstraint
    extends Constraint {
        jdkCAConstraint(String string) {
            this.algorithm = string;
        }

        @Override
        public void permits(CertConstraintParameters certConstraintParameters) throws CertPathValidatorException {
            if (debug != null) {
                Debug debug = debug;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("jdkCAConstraints.permits(): ");
                stringBuilder.append(this.algorithm);
                debug.println(stringBuilder.toString());
            }
            if (certConstraintParameters.isTrustedMatch()) {
                if (this.nextConstraint != null) {
                    this.nextConstraint.permits(certConstraintParameters);
                    return;
                }
                throw new CertPathValidatorException("Algorithm constraints check failed on certificate anchor limits", null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
            }
        }
    }

}

