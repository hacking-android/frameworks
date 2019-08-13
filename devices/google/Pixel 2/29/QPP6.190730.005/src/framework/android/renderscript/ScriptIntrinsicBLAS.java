/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.Double2;
import android.renderscript.Element;
import android.renderscript.Float2;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.Type;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ScriptIntrinsicBLAS
extends ScriptIntrinsic {
    public static final int CONJ_TRANSPOSE = 113;
    public static final int LEFT = 141;
    public static final int LOWER = 122;
    public static final int NON_UNIT = 131;
    public static final int NO_TRANSPOSE = 111;
    public static final int RIGHT = 142;
    private static final int RsBlas_bnnm = 1000;
    private static final int RsBlas_caxpy = 29;
    private static final int RsBlas_ccopy = 28;
    private static final int RsBlas_cdotc_sub = 6;
    private static final int RsBlas_cdotu_sub = 5;
    private static final int RsBlas_cgbmv = 64;
    private static final int RsBlas_cgemm = 125;
    private static final int RsBlas_cgemv = 63;
    private static final int RsBlas_cgerc = 99;
    private static final int RsBlas_cgeru = 98;
    private static final int RsBlas_chbmv = 96;
    private static final int RsBlas_chemm = 137;
    private static final int RsBlas_chemv = 95;
    private static final int RsBlas_cher = 100;
    private static final int RsBlas_cher2 = 102;
    private static final int RsBlas_cher2k = 139;
    private static final int RsBlas_cherk = 138;
    private static final int RsBlas_chpmv = 97;
    private static final int RsBlas_chpr = 101;
    private static final int RsBlas_chpr2 = 103;
    private static final int RsBlas_cscal = 43;
    private static final int RsBlas_csscal = 45;
    private static final int RsBlas_cswap = 27;
    private static final int RsBlas_csymm = 126;
    private static final int RsBlas_csyr2k = 128;
    private static final int RsBlas_csyrk = 127;
    private static final int RsBlas_ctbmv = 66;
    private static final int RsBlas_ctbsv = 69;
    private static final int RsBlas_ctpmv = 67;
    private static final int RsBlas_ctpsv = 70;
    private static final int RsBlas_ctrmm = 129;
    private static final int RsBlas_ctrmv = 65;
    private static final int RsBlas_ctrsm = 130;
    private static final int RsBlas_ctrsv = 68;
    private static final int RsBlas_dasum = 12;
    private static final int RsBlas_daxpy = 26;
    private static final int RsBlas_dcopy = 25;
    private static final int RsBlas_ddot = 4;
    private static final int RsBlas_dgbmv = 56;
    private static final int RsBlas_dgemm = 119;
    private static final int RsBlas_dgemv = 55;
    private static final int RsBlas_dger = 90;
    private static final int RsBlas_dnrm2 = 11;
    private static final int RsBlas_drot = 39;
    private static final int RsBlas_drotg = 37;
    private static final int RsBlas_drotm = 40;
    private static final int RsBlas_drotmg = 38;
    private static final int RsBlas_dsbmv = 88;
    private static final int RsBlas_dscal = 42;
    private static final int RsBlas_dsdot = 2;
    private static final int RsBlas_dspmv = 89;
    private static final int RsBlas_dspr = 92;
    private static final int RsBlas_dspr2 = 94;
    private static final int RsBlas_dswap = 24;
    private static final int RsBlas_dsymm = 120;
    private static final int RsBlas_dsymv = 87;
    private static final int RsBlas_dsyr = 91;
    private static final int RsBlas_dsyr2 = 93;
    private static final int RsBlas_dsyr2k = 122;
    private static final int RsBlas_dsyrk = 121;
    private static final int RsBlas_dtbmv = 58;
    private static final int RsBlas_dtbsv = 61;
    private static final int RsBlas_dtpmv = 59;
    private static final int RsBlas_dtpsv = 62;
    private static final int RsBlas_dtrmm = 123;
    private static final int RsBlas_dtrmv = 57;
    private static final int RsBlas_dtrsm = 124;
    private static final int RsBlas_dtrsv = 60;
    private static final int RsBlas_dzasum = 16;
    private static final int RsBlas_dznrm2 = 15;
    private static final int RsBlas_icamax = 19;
    private static final int RsBlas_idamax = 18;
    private static final int RsBlas_isamax = 17;
    private static final int RsBlas_izamax = 20;
    private static final int RsBlas_sasum = 10;
    private static final int RsBlas_saxpy = 23;
    private static final int RsBlas_scasum = 14;
    private static final int RsBlas_scnrm2 = 13;
    private static final int RsBlas_scopy = 22;
    private static final int RsBlas_sdot = 3;
    private static final int RsBlas_sdsdot = 1;
    private static final int RsBlas_sgbmv = 48;
    private static final int RsBlas_sgemm = 113;
    private static final int RsBlas_sgemv = 47;
    private static final int RsBlas_sger = 82;
    private static final int RsBlas_snrm2 = 9;
    private static final int RsBlas_srot = 35;
    private static final int RsBlas_srotg = 33;
    private static final int RsBlas_srotm = 36;
    private static final int RsBlas_srotmg = 34;
    private static final int RsBlas_ssbmv = 80;
    private static final int RsBlas_sscal = 41;
    private static final int RsBlas_sspmv = 81;
    private static final int RsBlas_sspr = 84;
    private static final int RsBlas_sspr2 = 86;
    private static final int RsBlas_sswap = 21;
    private static final int RsBlas_ssymm = 114;
    private static final int RsBlas_ssymv = 79;
    private static final int RsBlas_ssyr = 83;
    private static final int RsBlas_ssyr2 = 85;
    private static final int RsBlas_ssyr2k = 116;
    private static final int RsBlas_ssyrk = 115;
    private static final int RsBlas_stbmv = 50;
    private static final int RsBlas_stbsv = 53;
    private static final int RsBlas_stpmv = 51;
    private static final int RsBlas_stpsv = 54;
    private static final int RsBlas_strmm = 117;
    private static final int RsBlas_strmv = 49;
    private static final int RsBlas_strsm = 118;
    private static final int RsBlas_strsv = 52;
    private static final int RsBlas_zaxpy = 32;
    private static final int RsBlas_zcopy = 31;
    private static final int RsBlas_zdotc_sub = 8;
    private static final int RsBlas_zdotu_sub = 7;
    private static final int RsBlas_zdscal = 46;
    private static final int RsBlas_zgbmv = 72;
    private static final int RsBlas_zgemm = 131;
    private static final int RsBlas_zgemv = 71;
    private static final int RsBlas_zgerc = 108;
    private static final int RsBlas_zgeru = 107;
    private static final int RsBlas_zhbmv = 105;
    private static final int RsBlas_zhemm = 140;
    private static final int RsBlas_zhemv = 104;
    private static final int RsBlas_zher = 109;
    private static final int RsBlas_zher2 = 111;
    private static final int RsBlas_zher2k = 142;
    private static final int RsBlas_zherk = 141;
    private static final int RsBlas_zhpmv = 106;
    private static final int RsBlas_zhpr = 110;
    private static final int RsBlas_zhpr2 = 112;
    private static final int RsBlas_zscal = 44;
    private static final int RsBlas_zswap = 30;
    private static final int RsBlas_zsymm = 132;
    private static final int RsBlas_zsyr2k = 134;
    private static final int RsBlas_zsyrk = 133;
    private static final int RsBlas_ztbmv = 74;
    private static final int RsBlas_ztbsv = 77;
    private static final int RsBlas_ztpmv = 75;
    private static final int RsBlas_ztpsv = 78;
    private static final int RsBlas_ztrmm = 135;
    private static final int RsBlas_ztrmv = 73;
    private static final int RsBlas_ztrsm = 136;
    private static final int RsBlas_ztrsv = 76;
    public static final int TRANSPOSE = 112;
    public static final int UNIT = 132;
    public static final int UPPER = 121;
    private Allocation mLUT;

    private ScriptIntrinsicBLAS(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicBLAS create(RenderScript renderScript) {
        return new ScriptIntrinsicBLAS(renderScript.nScriptIntrinsicCreate(13, Element.U32(renderScript).getID(renderScript)), renderScript);
    }

    static void validateConjTranspose(int n) {
        if (n != 111 && n != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void validateDiag(int n) {
        if (n != 131 && n != 132) {
            throw new RSRuntimeException("Invalid diag passed to BLAS");
        }
    }

    static void validateGEMV(Element element, int n, Allocation allocation, Allocation allocation2, int n2, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateTranspose(n);
        int n4 = allocation.getType().getY();
        int n5 = allocation.getType().getX();
        if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element) && allocation3.getType().getElement().isCompatible(element)) {
            if (allocation2.getType().getY() <= 1 && allocation3.getType().getY() <= 1) {
                if (n2 > 0 && n3 > 0) {
                    if (n == 111) {
                        n2 = (n5 - 1) * n2 + 1;
                        n = (n4 - 1) * n3 + 1;
                    } else {
                        n2 = (n4 - 1) * n2 + 1;
                        n = (n5 - 1) * n3 + 1;
                    }
                    if (allocation2.getType().getX() == n2 && allocation3.getType().getX() == n) {
                        return;
                    }
                    throw new RSRuntimeException("Incorrect vector dimensions for GEMV");
                }
                throw new RSRuntimeException("Vector increments must be greater than 0");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateGER(Element element, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        if (allocation3.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
            if (allocation.getType().getY() <= 1 && allocation2.getType().getY() <= 1) {
                int n3 = allocation3.getType().getY();
                int n4 = allocation3.getType().getX();
                if (n4 >= 1 && n3 >= 1) {
                    if (n > 0 && n2 > 0) {
                        if (allocation.getType().getX() == (n3 - 1) * n + 1) {
                            if (allocation2.getType().getX() == (n4 - 1) * n2 + 1) {
                                return;
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for GER");
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for GER");
                    }
                    throw new RSRuntimeException("Vector increments must be greater than 0");
                }
                throw new RSRuntimeException("M and N must be 1 or greater for GER");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateGERU(Element element, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        if (allocation3.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
            if (allocation.getType().getY() <= 1 && allocation2.getType().getY() <= 1) {
                int n3 = allocation3.getType().getY();
                int n4 = allocation3.getType().getX();
                if (n > 0 && n2 > 0) {
                    if (allocation.getType().getX() == (n3 - 1) * n + 1) {
                        if (allocation2.getType().getX() == (n4 - 1) * n2 + 1) {
                            return;
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for GERU");
                    }
                    throw new RSRuntimeException("Incorrect vector dimensions for GERU");
                }
                throw new RSRuntimeException("Vector increments must be greater than 0");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateHEMM(Element element, int n, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateSide(n);
        if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element) && allocation3.getType().getElement().isCompatible(element)) {
            int n2 = allocation.getType().getX();
            if (n2 == allocation.getType().getY()) {
                if (n == 141 && n2 != allocation2.getType().getY() || n == 142 && n2 != allocation2.getType().getX()) {
                    throw new RSRuntimeException("Called HEMM with invalid B");
                }
                if (allocation2.getType().getX() == allocation3.getType().getX() && allocation2.getType().getY() == allocation3.getType().getY()) {
                    return;
                }
                throw new RSRuntimeException("Called HEMM with mismatched B and C");
            }
            throw new RSRuntimeException("Called HEMM with non-square A");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateHER2K(Element element, int n, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        block6 : {
            block7 : {
                block10 : {
                    block9 : {
                        int n2;
                        block8 : {
                            if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) break block6;
                            ScriptIntrinsicBLAS.validateConjTranspose(n);
                            n2 = allocation3.getType().getX();
                            if (n2 != allocation3.getType().getY()) break block7;
                            if (n != 111) break block8;
                            if (allocation.getType().getY() != n2) {
                                throw new RSRuntimeException("Called HER2K with invalid matrices");
                            }
                            break block9;
                        }
                        if (allocation.getType().getX() != n2) break block10;
                    }
                    if (allocation.getType().getX() == allocation2.getType().getX() && allocation.getType().getY() == allocation2.getType().getY()) {
                        return;
                    }
                    throw new RSRuntimeException("Called HER2K with invalid A and B matrices");
                }
                throw new RSRuntimeException("Called HER2K with invalid matrices");
            }
            throw new RSRuntimeException("Called HER2K with non-square C");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateHERK(Element element, int n, Allocation allocation, Allocation allocation2) {
        block5 : {
            block6 : {
                block9 : {
                    block8 : {
                        int n2;
                        block7 : {
                            if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) break block5;
                            ScriptIntrinsicBLAS.validateConjTranspose(n);
                            n2 = allocation2.getType().getX();
                            if (n2 != allocation2.getType().getY()) break block6;
                            if (n != 111) break block7;
                            if (n2 != allocation.getType().getY()) {
                                throw new RSRuntimeException("Called HERK with invalid A");
                            }
                            break block8;
                        }
                        if (n2 != allocation.getType().getX()) break block9;
                    }
                    return;
                }
                throw new RSRuntimeException("Called HERK with invalid A");
            }
            throw new RSRuntimeException("Called HERK with non-square C");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void validateL3(Element element, int n, int n2, int n3, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        int n4;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        int n8 = -1;
        if (allocation != null && !allocation.getType().getElement().isCompatible(element) || allocation2 != null && !allocation2.getType().getElement().isCompatible(element) || allocation3 != null && !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation3 == null) throw new RSRuntimeException("Allocation C cannot be null");
        int n9 = allocation3.getType().getY();
        int n10 = allocation3.getType().getX();
        if (n3 == 142) {
            if (allocation == null && allocation2 != null || allocation != null && allocation2 == null) {
                throw new RSRuntimeException("Provided Matrix A without Matrix B, or vice versa");
            }
            n3 = n7;
            n2 = n8;
            if (allocation2 != null) {
                n3 = allocation.getType().getY();
                n2 = allocation.getType().getX();
            }
            n4 = n6;
            n = n3;
            n8 = n2;
            if (allocation != null) {
                n5 = allocation2.getType().getY();
                n4 = allocation2.getType().getX();
                n = n3;
                n8 = n2;
            }
        } else {
            n3 = n5;
            if (allocation != null) {
                if (n != 112 && n != 113) {
                    n3 = allocation.getType().getY();
                    n6 = allocation.getType().getX();
                } else {
                    n6 = allocation.getType().getY();
                    n3 = allocation.getType().getX();
                }
            }
            n5 = n3;
            n4 = n6;
            n = n7;
            if (allocation2 != null) {
                if (n2 != 112 && n2 != 113) {
                    n = allocation2.getType().getY();
                    n8 = allocation2.getType().getX();
                    n5 = n3;
                    n4 = n6;
                } else {
                    n8 = allocation2.getType().getY();
                    n = allocation2.getType().getX();
                    n4 = n6;
                    n5 = n3;
                }
            }
        }
        if (allocation != null && allocation2 != null) {
            if (n4 == n && n5 == n9 && n8 == n10) return;
            throw new RSRuntimeException("Called BLAS with invalid dimensions");
        }
        if (allocation != null) {
            if (n9 != n10) throw new RSRuntimeException("Matrix C is not symmetric");
            if (n5 == n9) return;
            throw new RSRuntimeException("Called BLAS with invalid dimensions");
        }
        if (allocation == null || allocation2 == null || n4 == n) return;
        throw new RSRuntimeException("Called BLAS with invalid dimensions");
    }

    static int validateSPMV(Element element, int n, Allocation allocation, Allocation allocation2, int n2, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element) && allocation3.getType().getElement().isCompatible(element)) {
            if (allocation2.getType().getY() <= 1 && allocation3.getType().getY() <= 1) {
                if (allocation.getType().getY() <= 1) {
                    n = (int)Math.sqrt((double)allocation.getType().getX() * 2.0);
                    if (allocation.getType().getX() == (n + 1) * n / 2) {
                        if (n2 > 0 && n3 > 0) {
                            if (allocation2.getType().getX() == (n - 1) * n2 + 1) {
                                if (allocation3.getType().getX() == (n - 1) * n3 + 1) {
                                    return n;
                                }
                                throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
                        }
                        throw new RSRuntimeException("Vector increments must be greater than 0");
                    }
                    throw new RSRuntimeException("Invalid dimension for Ap");
                }
                throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static int validateSPR(Element element, int n, Allocation allocation, int n2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n);
        if (allocation2.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element)) {
            if (allocation.getType().getY() <= 1) {
                if (allocation2.getType().getY() <= 1) {
                    n = (int)Math.sqrt((double)allocation2.getType().getX() * 2.0);
                    if (allocation2.getType().getX() == (n + 1) * n / 2) {
                        if (n2 > 0) {
                            if (allocation.getType().getX() == (n - 1) * n2 + 1) {
                                return n;
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for SPR");
                        }
                        throw new RSRuntimeException("Vector increments must be greater than 0");
                    }
                    throw new RSRuntimeException("Invalid dimension for Ap");
                }
                throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static int validateSPR2(Element element, int n, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        if (allocation3.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
            if (allocation.getType().getY() <= 1 && allocation2.getType().getY() <= 1) {
                if (allocation3.getType().getY() <= 1) {
                    n = (int)Math.sqrt((double)allocation3.getType().getX() * 2.0);
                    if (allocation3.getType().getX() == (n + 1) * n / 2) {
                        if (n2 > 0 && n3 > 0) {
                            if (allocation.getType().getX() == (n - 1) * n2 + 1 && allocation2.getType().getX() == (n - 1) * n3 + 1) {
                                return n;
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for SPR2");
                        }
                        throw new RSRuntimeException("Vector increments must be greater than 0");
                    }
                    throw new RSRuntimeException("Invalid dimension for Ap");
                }
                throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static int validateSYMV(Element element, int n, Allocation allocation, Allocation allocation2, Allocation allocation3, int n2, int n3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        n = allocation.getType().getY();
        if (allocation.getType().getX() == n) {
            if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element) && allocation3.getType().getElement().isCompatible(element)) {
                if (allocation2.getType().getY() <= 1 && allocation3.getType().getY() <= 1) {
                    if (n2 > 0 && n3 > 0) {
                        if (allocation2.getType().getX() == (n - 1) * n2 + 1) {
                            if (allocation3.getType().getX() == (n - 1) * n3 + 1) {
                                return n;
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
                    }
                    throw new RSRuntimeException("Vector increments must be greater than 0");
                }
                throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        throw new RSRuntimeException("A must be a square matrix for SYMV");
    }

    static int validateSYR(Element element, int n, Allocation allocation, int n2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n);
        if (allocation2.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element)) {
            n = allocation2.getType().getX();
            if (allocation.getType().getY() <= 1) {
                if (n == allocation2.getType().getY()) {
                    if (n2 > 0) {
                        if (allocation.getType().getX() == (n - 1) * n2 + 1) {
                            return n;
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for SYR");
                    }
                    throw new RSRuntimeException("Vector increments must be greater than 0");
                }
                throw new RSRuntimeException("A must be a symmetric matrix");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static int validateSYR2(Element element, int n, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        if (allocation3.getType().getElement().isCompatible(element) && allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
            if (allocation.getType().getY() <= 1 && allocation2.getType().getY() <= 1) {
                n = allocation3.getType().getX();
                if (n == allocation3.getType().getY()) {
                    if (n2 > 0 && n3 > 0) {
                        if (allocation.getType().getX() == (n - 1) * n2 + 1 && allocation2.getType().getX() == (n - 1) * n3 + 1) {
                            return n;
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for SYR");
                    }
                    throw new RSRuntimeException("Vector increments must be greater than 0");
                }
                throw new RSRuntimeException("A must be a symmetric matrix");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateSYR2K(Element element, int n, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateTranspose(n);
        if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element) && allocation3.getType().getElement().isCompatible(element)) {
            n = n == 112 ? allocation.getType().getX() : allocation.getType().getY();
            if (allocation3.getType().getX() == n && allocation3.getType().getY() == n) {
                if (allocation.getType().getX() == allocation2.getType().getX() && allocation.getType().getY() == allocation2.getType().getY()) {
                    return;
                }
                throw new RSRuntimeException("Invalid A and B in SYR2K");
            }
            throw new RSRuntimeException("Invalid symmetric matrix in SYR2K");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateSide(int n) {
        if (n != 141 && n != 142) {
            throw new RSRuntimeException("Invalid side passed to BLAS");
        }
    }

    static int validateTPMV(Element element, int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateDiag(n3);
        if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
            if (allocation2.getType().getY() <= 1) {
                if (allocation.getType().getY() <= 1) {
                    n = (int)Math.sqrt((double)allocation.getType().getX() * 2.0);
                    if (allocation.getType().getX() == (n + 1) * n / 2) {
                        if (n4 > 0) {
                            if (allocation2.getType().getX() == (n - 1) * n4 + 1) {
                                return n;
                            }
                            throw new RSRuntimeException("Incorrect vector dimensions for TPMV");
                        }
                        throw new RSRuntimeException("Vector increments must be greater than 0");
                    }
                    throw new RSRuntimeException("Invalid dimension for Ap");
                }
                throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateTRMM(Element element, int n, int n2, Allocation allocation, Allocation allocation2) {
        block5 : {
            block6 : {
                block9 : {
                    block8 : {
                        int n3;
                        block7 : {
                            int n4;
                            ScriptIntrinsicBLAS.validateSide(n);
                            ScriptIntrinsicBLAS.validateTranspose(n2);
                            if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) break block5;
                            n3 = allocation.getType().getY();
                            if (n3 != (n4 = allocation.getType().getX())) break block6;
                            int n5 = allocation2.getType().getY();
                            n2 = allocation2.getType().getX();
                            if (n != 141) break block7;
                            if (n4 != n5) {
                                throw new RSRuntimeException("Called TRMM with invalid matrices");
                            }
                            break block8;
                        }
                        if (n2 != n3) break block9;
                    }
                    return;
                }
                throw new RSRuntimeException("Called TRMM with invalid matrices");
            }
            throw new RSRuntimeException("Called TRMM with a non-symmetric matrix A");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateTRMV(Element element, int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateDiag(n3);
        n = allocation.getType().getY();
        if (allocation.getType().getX() == n) {
            if (allocation.getType().getElement().isCompatible(element) && allocation2.getType().getElement().isCompatible(element)) {
                if (allocation2.getType().getY() <= 1) {
                    if (n4 > 0) {
                        if (allocation2.getType().getX() == (n - 1) * n4 + 1) {
                            return;
                        }
                        throw new RSRuntimeException("Incorrect vector dimensions for TRMV");
                    }
                    throw new RSRuntimeException("Vector increments must be greater than 0");
                }
                throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
            }
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        throw new RSRuntimeException("A must be a square matrix for TRMV");
    }

    static void validateTRSM(Element element, int n, int n2, Allocation allocation, Allocation allocation2) {
        block5 : {
            block6 : {
                block9 : {
                    block8 : {
                        int n3;
                        block7 : {
                            ScriptIntrinsicBLAS.validateSide(n);
                            ScriptIntrinsicBLAS.validateTranspose(n2);
                            if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) break block5;
                            n3 = allocation.getType().getX();
                            if (n3 != allocation.getType().getY()) break block6;
                            int n4 = allocation2.getType().getY();
                            n2 = allocation2.getType().getX();
                            if (n != 141) break block7;
                            if (n3 != n4) {
                                throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
                            }
                            break block8;
                        }
                        if (n3 != n2) break block9;
                    }
                    return;
                }
                throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
            }
            throw new RSRuntimeException("Called TRSM with a non-symmetric matrix A");
        }
        throw new RSRuntimeException("Called BLAS with wrong Element type");
    }

    static void validateTranspose(int n) {
        if (n != 111 && n != 112 && n != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void validateUplo(int n) {
        if (n != 121 && n != 122) {
            throw new RSRuntimeException("Invalid uplo passed to BLAS");
        }
    }

    public void BNNM(Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3, int n3, int n4) {
        ScriptIntrinsicBLAS.validateL3(Element.U8(this.mRS), 111, 112, 0, allocation, allocation2, allocation3);
        if (n >= 0 && n <= 255) {
            if (n2 >= 0 && n2 <= 255) {
                int n5 = allocation.getType().getY();
                int n6 = allocation2.getType().getY();
                int n7 = allocation.getType().getX();
                this.mRS.nScriptIntrinsicBLAS_BNNM(this.getID(this.mRS), n5, n6, n7, allocation.getID(this.mRS), n, allocation2.getID(this.mRS), n2, allocation3.getID(this.mRS), n3, n4);
                return;
            }
            throw new RSRuntimeException("Invalid b_offset passed to BNNM");
        }
        throw new RSRuntimeException("Invalid a_offset passed to BNNM");
    }

    public void CGBMV(int n, int n2, int n3, Float2 float2, Allocation allocation, Allocation allocation2, int n4, Float2 float22, Allocation allocation3, int n5) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F32_2(this.mRS), n, allocation, allocation2, n4, allocation3, n5);
        if (n2 >= 0 && n3 >= 0) {
            int n6 = allocation.getType().getY();
            int n7 = allocation.getType().getX();
            this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 64, n, 0, 0, 0, 0, n6, n7, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), n4, n5, n2, n3);
            return;
        }
        throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
    }

    public void CGEMM(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        int n3;
        int n4;
        ScriptIntrinsicBLAS.validateTranspose(n);
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateL3(Element.F32_2(this.mRS), n, n2, 0, allocation, allocation2, allocation3);
        if (n != 111) {
            n4 = allocation.getType().getX();
            n3 = allocation.getType().getY();
        } else {
            n4 = allocation.getType().getY();
            n3 = allocation.getType().getX();
        }
        int n5 = n2 != 111 ? allocation2.getType().getY() : allocation2.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 125, n, n2, 0, 0, 0, n4, n5, n3, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CGEMV(int n, Float2 float2, Allocation allocation, Allocation allocation2, int n2, Float2 float22, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F32_2(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        int n4 = allocation.getType().getY();
        int n5 = allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 63, n, 0, 0, 0, 0, n4, n5, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void CGERC(Float2 float2, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateGERU(Element.F32_2(this.mRS), allocation, n, allocation2, n2, allocation3);
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 99, 0, 0, 0, 0, 0, n3, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void CGERU(Float2 float2, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateGERU(Element.F32_2(this.mRS), allocation, n, allocation2, n2, allocation3);
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 98, 0, 0, 0, 0, 0, n3, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void CHBMV(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, int n3, Float2 float22, Allocation allocation3, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateSYR2(Element.F32_2(this.mRS), n, allocation2, n3, allocation3, n4, allocation);
        if (n2 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 96, 0, 0, 0, n, 0, 0, n5, n2, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), n3, n4, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be 0 or greater for HBMV");
    }

    public void CHEMM(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateHEMM(Element.F32_2(this.mRS), n, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 137, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CHEMV(int n, Float2 float2, Allocation allocation, Allocation allocation2, int n2, Float2 float22, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F32_2(this.mRS), n, allocation2, n2, allocation3, n3, allocation);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 95, 0, 0, 0, n, 0, 0, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void CHER(int n, float f, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSYR(Element.F32_2(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 100, 0, 0, 0, n, 0, 0, n3, 0, f, 0.0f, allocation.getID(this.mRS), 0L, 0.0f, 0.0f, allocation2.getID(this.mRS), n2, 0, 0, 0);
    }

    public void CHER2(int n, Float2 float2, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F32_2(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 102, 0, 0, 0, n, 0, 0, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void CHER2K(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, float f, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateHER2K(Element.F32_2(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 == 111 ? allocation.getType().getX() : allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 139, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), f, 0.0f, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CHERK(int n, int n2, float f, Allocation allocation, float f2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateHERK(Element.F32_2(this.mRS), n2, allocation, allocation2);
        int n3 = n2 == 113 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 138, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, f, 0.0f, allocation.getID(this.mRS), 0L, f2, 0.0f, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CHPMV(int n, Float2 float2, Allocation allocation, Allocation allocation2, int n2, Float2 float22, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F32_2(this.mRS), n, allocation2, n2, allocation3, n3, allocation);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 97, 0, 0, 0, n, 0, 0, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void CHPR(int n, float f, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSPR(Element.F32_2(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 101, 0, 0, 0, n, 0, 0, n3, 0, f, 0.0f, allocation.getID(this.mRS), 0L, 0.0f, 0.0f, allocation2.getID(this.mRS), n2, 0, 0, 0);
    }

    public void CHPR2(int n, Float2 float2, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F32_2(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 103, 0, 0, 0, n, 0, 0, n4, 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void CSYMM(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateSide(n);
        ScriptIntrinsicBLAS.validateUplo(n2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            ScriptIntrinsicBLAS.validateL3(Element.F32_2(this.mRS), 0, 0, n, allocation, allocation2, allocation3);
            this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 126, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void CSYR2K(int n, int n2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateSYR2K(Element.F32_2(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 128, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CSYRK(int n, int n2, Float2 float2, Allocation allocation, Float2 float22, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateL3(Element.F32_2(this.mRS), n2, 0, 0, allocation, null, allocation2);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 127, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, float2.x, float2.y, allocation.getID(this.mRS), 0L, float22.x, float22.y, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CTBMV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        if (n4 >= 0) {
            ScriptIntrinsicBLAS.validateTRMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n5);
            int n6 = allocation.getType().getY();
            this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 66, n2, 0, 0, n, n3, 0, n6, n4, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void CTBSV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n5);
        int n6 = allocation.getType().getY();
        if (n4 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 69, n2, 0, 0, n, n3, 0, n6, n4, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void CTPMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 67, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n4, 0, 0, 0);
    }

    public void CTPSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 70, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n4, 0, 0, 0);
    }

    public void CTRMM(int n, int n2, int n3, int n4, Float2 float2, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRMM(Element.F32_2(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 129, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, 0, 0, 0, 0);
    }

    public void CTRMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 65, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n4, 0, 0, 0);
    }

    public void CTRSM(int n, int n2, int n3, int n4, Float2 float2, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRSM(Element.F32_2(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 130, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, 0, 0, 0, 0);
    }

    public void CTRSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Complex(this.getID(this.mRS), 68, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, n4, 0, 0, 0);
    }

    public void DGBMV(int n, int n2, int n3, double d, Allocation allocation, Allocation allocation2, int n4, double d2, Allocation allocation3, int n5) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F64(this.mRS), n, allocation, allocation2, n4, allocation3, n5);
        if (n2 >= 0 && n3 >= 0) {
            int n6 = allocation.getType().getY();
            int n7 = allocation.getType().getX();
            this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 56, n, 0, 0, 0, 0, n6, n7, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), n4, n5, n2, n3);
            return;
        }
        throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
    }

    public void DGEMM(int n, int n2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        int n3;
        int n4;
        ScriptIntrinsicBLAS.validateTranspose(n);
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateL3(Element.F64(this.mRS), n, n2, 0, allocation, allocation2, allocation3);
        if (n != 111) {
            n3 = allocation.getType().getX();
            n4 = allocation.getType().getY();
        } else {
            n3 = allocation.getType().getY();
            n4 = allocation.getType().getX();
        }
        int n5 = n2 != 111 ? allocation2.getType().getY() : allocation2.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 119, n, n2, 0, 0, 0, n3, n5, n4, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DGEMV(int n, double d, Allocation allocation, Allocation allocation2, int n2, double d2, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F64(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        int n4 = allocation.getType().getY();
        int n5 = allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 55, n, 0, 0, 0, 0, n4, n5, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void DGER(double d, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        ScriptIntrinsicBLAS.validateGER(Element.F64(this.mRS), allocation, n, allocation2, n2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 90, 0, 0, 0, 0, 0, n3, n4, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void DSBMV(int n, int n2, double d, Allocation allocation, Allocation allocation2, int n3, double d2, Allocation allocation3, int n4) {
        if (n2 >= 0) {
            int n5 = ScriptIntrinsicBLAS.validateSYMV(Element.F64(this.mRS), n, allocation, allocation2, allocation3, n3, n4);
            this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 88, 0, 0, 0, n, 0, 0, n5, n2, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), n3, n4, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void DSPMV(int n, double d, Allocation allocation, Allocation allocation2, int n2, double d2, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSPMV(Element.F64(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 89, 0, 0, 0, n, 0, 0, n4, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void DSPR(int n, double d, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSPR(Element.F64(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 92, 0, 0, 0, n, 0, 0, n3, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n2, 0, 0, 0);
    }

    public void DSPR2(int n, double d, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F64(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 94, 0, 0, 0, n, 0, 0, n4, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void DSYMM(int n, int n2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateSide(n);
        ScriptIntrinsicBLAS.validateUplo(n2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            ScriptIntrinsicBLAS.validateL3(Element.F64(this.mRS), 0, 0, n, allocation, allocation2, allocation3);
            this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 120, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void DSYMV(int n, double d, Allocation allocation, Allocation allocation2, int n2, double d2, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSYMV(Element.F64(this.mRS), n, allocation, allocation2, allocation3, n2, n3);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 87, 0, 0, 0, n, 0, 0, n4, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void DSYR(int n, double d, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSYR(Element.F64(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 91, 0, 0, 0, n, 0, 0, n3, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n2, 0, 0, 0);
    }

    public void DSYR2(int n, double d, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F64(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 93, 0, 0, 0, n, 0, 0, n4, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void DSYR2K(int n, int n2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateSYR2K(Element.F64(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 122, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DSYRK(int n, int n2, double d, Allocation allocation, double d2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateL3(Element.F64(this.mRS), n2, 0, 0, allocation, null, allocation2);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 121, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, d, allocation.getID(this.mRS), 0L, d2, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DTBMV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        if (n4 >= 0) {
            ScriptIntrinsicBLAS.validateTRMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n5);
            int n6 = allocation.getType().getY();
            this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 58, n2, 0, 0, n, n3, 0, n6, n4, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void DTBSV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n5);
        int n6 = allocation.getType().getY();
        if (n4 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 61, n2, 0, 0, n, n3, 0, n6, n4, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void DTPMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 59, n2, 0, 0, n, n3, 0, n5, 0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n4, 0, 0, 0);
    }

    public void DTPSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 62, n2, 0, 0, n, n3, 0, n5, 0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n4, 0, 0, 0);
    }

    public void DTRMM(int n, int n2, int n3, int n4, double d, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRMM(Element.F64(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 123, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, 0, 0, 0, 0);
    }

    public void DTRMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 57, n2, 0, 0, n, n3, 0, n5, 0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n4, 0, 0, 0);
    }

    public void DTRSM(int n, int n2, int n3, int n4, double d, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRSM(Element.F64(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 124, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, 0, 0, 0, 0);
    }

    public void DTRSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Double(this.getID(this.mRS), 60, n2, 0, 0, n, n3, 0, n5, 0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0L, n4, 0, 0, 0);
    }

    public void SGBMV(int n, int n2, int n3, float f, Allocation allocation, Allocation allocation2, int n4, float f2, Allocation allocation3, int n5) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F32(this.mRS), n, allocation, allocation2, n4, allocation3, n5);
        if (n2 >= 0 && n3 >= 0) {
            int n6 = allocation.getType().getY();
            int n7 = allocation.getType().getX();
            this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 48, n, 0, 0, 0, 0, n6, n7, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), n4, n5, n2, n3);
            return;
        }
        throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
    }

    public void SGEMM(int n, int n2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        int n3;
        int n4;
        ScriptIntrinsicBLAS.validateTranspose(n);
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateL3(Element.F32(this.mRS), n, n2, 0, allocation, allocation2, allocation3);
        if (n != 111) {
            n4 = allocation.getType().getX();
            n3 = allocation.getType().getY();
        } else {
            n4 = allocation.getType().getY();
            n3 = allocation.getType().getX();
        }
        int n5 = n2 != 111 ? allocation2.getType().getY() : allocation2.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 113, n, n2, 0, 0, 0, n4, n5, n3, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void SGEMV(int n, float f, Allocation allocation, Allocation allocation2, int n2, float f2, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F32(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        int n4 = allocation.getType().getY();
        int n5 = allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 47, n, 0, 0, 0, 0, n4, n5, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void SGER(float f, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        ScriptIntrinsicBLAS.validateGER(Element.F32(this.mRS), allocation, n, allocation2, n2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 82, 0, 0, 0, 0, 0, n3, n4, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void SSBMV(int n, int n2, float f, Allocation allocation, Allocation allocation2, int n3, float f2, Allocation allocation3, int n4) {
        if (n2 >= 0) {
            int n5 = ScriptIntrinsicBLAS.validateSYMV(Element.F32(this.mRS), n, allocation, allocation2, allocation3, n3, n4);
            this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 80, 0, 0, 0, n, 0, 0, n5, n2, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), n3, n4, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void SSPMV(int n, float f, Allocation allocation, Allocation allocation2, int n2, float f2, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSPMV(Element.F32(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 81, 0, 0, 0, n, 0, 0, n4, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void SSPR(int n, float f, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSPR(Element.F32(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 84, 0, 0, 0, n, 0, 0, n3, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n2, 0, 0, 0);
    }

    public void SSPR2(int n, float f, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F32(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 86, 0, 0, 0, n, 0, 0, n4, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void SSYMM(int n, int n2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateSide(n);
        ScriptIntrinsicBLAS.validateUplo(n2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            ScriptIntrinsicBLAS.validateL3(Element.F32(this.mRS), 0, 0, n, allocation, allocation2, allocation3);
            this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 114, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void SSYMV(int n, float f, Allocation allocation, Allocation allocation2, int n2, float f2, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSYMV(Element.F32(this.mRS), n, allocation, allocation2, allocation3, n2, n3);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 79, 0, 0, 0, n, 0, 0, n4, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void SSYR(int n, float f, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSYR(Element.F32(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 83, 0, 0, 0, n, 0, 0, n3, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n2, 0, 0, 0);
    }

    public void SSYR2(int n, float f, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F32(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 85, 0, 0, 0, n, 0, 0, n4, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void SSYR2K(int n, int n2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateSYR2K(Element.F32(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 116, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void SSYRK(int n, int n2, float f, Allocation allocation, float f2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateL3(Element.F32(this.mRS), n2, 0, 0, allocation, null, allocation2);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 115, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, f, allocation.getID(this.mRS), 0L, f2, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void STBMV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        if (n4 >= 0) {
            ScriptIntrinsicBLAS.validateTRMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n5);
            int n6 = allocation.getType().getY();
            this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 50, n2, 0, 0, n, n3, 0, n6, n4, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void STBSV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n5);
        int n6 = allocation.getType().getY();
        if (n4 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 53, n2, 0, 0, n, n3, 0, n6, n4, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void STPMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 51, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n4, 0, 0, 0);
    }

    public void STPSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 54, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n4, 0, 0, 0);
    }

    public void STRMM(int n, int n2, int n3, int n4, float f, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRMM(Element.F32(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 117, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, 0, 0, 0, 0);
    }

    public void STRMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 49, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n4, 0, 0, 0);
    }

    public void STRSM(int n, int n2, int n3, int n4, float f, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRSM(Element.F32(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 118, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, 0, 0, 0, 0);
    }

    public void STRSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F32(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Single(this.getID(this.mRS), 52, n2, 0, 0, n, n3, 0, n5, 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, n4, 0, 0, 0);
    }

    public void ZGBMV(int n, int n2, int n3, Double2 double2, Allocation allocation, Allocation allocation2, int n4, Double2 double22, Allocation allocation3, int n5) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F64_2(this.mRS), n, allocation, allocation2, n4, allocation3, n5);
        if (n2 >= 0 && n3 >= 0) {
            int n6 = allocation.getType().getY();
            int n7 = allocation.getType().getX();
            this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 72, n, 0, 0, 0, 0, n6, n7, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), n4, n5, n2, n3);
            return;
        }
        throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
    }

    public void ZGEMM(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        int n3;
        int n4;
        ScriptIntrinsicBLAS.validateTranspose(n);
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateL3(Element.F64_2(this.mRS), n, n2, 0, allocation, allocation2, allocation3);
        if (n != 111) {
            n4 = allocation.getType().getX();
            n3 = allocation.getType().getY();
        } else {
            n4 = allocation.getType().getY();
            n3 = allocation.getType().getX();
        }
        int n5 = n2 != 111 ? allocation2.getType().getY() : allocation2.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 131, n, n2, 0, 0, 0, n4, n5, n3, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZGEMV(int n, Double2 double2, Allocation allocation, Allocation allocation2, int n2, Double2 double22, Allocation allocation3, int n3) {
        ScriptIntrinsicBLAS.validateGEMV(Element.F64_2(this.mRS), n, allocation, allocation2, n2, allocation3, n3);
        int n4 = allocation.getType().getY();
        int n5 = allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 71, n, 0, 0, 0, 0, n4, n5, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void ZGERC(Double2 double2, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateGERU(Element.F64_2(this.mRS), allocation, n, allocation2, n2, allocation3);
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 108, 0, 0, 0, 0, 0, n3, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void ZGERU(Double2 double2, Allocation allocation, int n, Allocation allocation2, int n2, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateGERU(Element.F64_2(this.mRS), allocation, n, allocation2, n2, allocation3);
        int n3 = allocation3.getType().getY();
        int n4 = allocation3.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 107, 0, 0, 0, 0, 0, n3, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, allocation3.getID(this.mRS), n, n2, 0, 0);
    }

    public void ZHBMV(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, int n3, Double2 double22, Allocation allocation3, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateSYR2(Element.F64_2(this.mRS), n, allocation2, n3, allocation3, n4, allocation);
        if (n2 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 105, 0, 0, 0, n, 0, 0, n5, n2, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), n3, n4, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be 0 or greater for HBMV");
    }

    public void ZHEMM(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateHEMM(Element.F64_2(this.mRS), n, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 140, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHEMV(int n, Double2 double2, Allocation allocation, Allocation allocation2, int n2, Double2 double22, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F64_2(this.mRS), n, allocation2, n2, allocation3, n3, allocation);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 104, 0, 0, 0, n, 0, 0, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void ZHER(int n, double d, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSYR(Element.F64_2(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 109, 0, 0, 0, n, 0, 0, n3, 0, d, 0.0, allocation.getID(this.mRS), 0L, 0.0, 0.0, allocation2.getID(this.mRS), n2, 0, 0, 0);
    }

    public void ZHER2(int n, Double2 double2, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSYR2(Element.F64_2(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 111, 0, 0, 0, n, 0, 0, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void ZHER2K(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, double d, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateHER2K(Element.F64_2(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 == 111 ? allocation.getType().getX() : allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 142, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), d, 0.0, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHERK(int n, int n2, double d, Allocation allocation, double d2, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateHERK(Element.F64_2(this.mRS), n2, allocation, allocation2);
        int n3 = n2 == 113 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 141, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, d, 0.0, allocation.getID(this.mRS), 0L, d2, 0.0, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHPMV(int n, Double2 double2, Allocation allocation, Allocation allocation2, int n2, Double2 double22, Allocation allocation3, int n3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F64_2(this.mRS), n, allocation2, n2, allocation3, n3, allocation);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 106, 0, 0, 0, n, 0, 0, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void ZHPR(int n, double d, Allocation allocation, int n2, Allocation allocation2) {
        int n3 = ScriptIntrinsicBLAS.validateSPR(Element.F64_2(this.mRS), n, allocation, n2, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 110, 0, 0, 0, n, 0, 0, n3, 0, d, 0.0, allocation.getID(this.mRS), 0L, 0.0, 0.0, allocation2.getID(this.mRS), n2, 0, 0, 0);
    }

    public void ZHPR2(int n, Double2 double2, Allocation allocation, int n2, Allocation allocation2, int n3, Allocation allocation3) {
        int n4 = ScriptIntrinsicBLAS.validateSPR2(Element.F64_2(this.mRS), n, allocation, n2, allocation2, n3, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 112, 0, 0, 0, n, 0, 0, n4, 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, allocation3.getID(this.mRS), n2, n3, 0, 0);
    }

    public void ZSYMM(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateSide(n);
        ScriptIntrinsicBLAS.validateUplo(n2);
        if (allocation.getType().getX() == allocation.getType().getY()) {
            ScriptIntrinsicBLAS.validateL3(Element.F64_2(this.mRS), 0, 0, n, allocation, allocation2, allocation3);
            this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 132, 0, 0, n, n2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Matrix A is not symmetric");
    }

    public void ZSYR2K(int n, int n2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateSYR2K(Element.F64_2(this.mRS), n2, allocation, allocation2, allocation3);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 134, n2, 0, 0, n, 0, 0, allocation3.getType().getX(), n3, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZSYRK(int n, int n2, Double2 double2, Allocation allocation, Double2 double22, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateTranspose(n2);
        ScriptIntrinsicBLAS.validateUplo(n);
        ScriptIntrinsicBLAS.validateL3(Element.F64_2(this.mRS), n2, 0, 0, allocation, null, allocation2);
        int n3 = n2 != 111 ? allocation.getType().getY() : allocation.getType().getX();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 133, n2, 0, 0, n, 0, 0, allocation2.getType().getX(), n3, double2.x, double2.y, allocation.getID(this.mRS), 0L, double22.x, double22.y, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZTBMV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        if (n4 >= 0) {
            ScriptIntrinsicBLAS.validateTRMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n5);
            int n6 = allocation.getType().getY();
            this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 74, n2, 0, 0, n, n3, 0, n6, n4, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("K must be greater than or equal to 0");
    }

    public void ZTBSV(int n, int n2, int n3, int n4, Allocation allocation, Allocation allocation2, int n5) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n5);
        int n6 = allocation.getType().getY();
        if (n4 >= 0) {
            this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 77, n2, 0, 0, n, n3, 0, n6, n4, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n5, 0, 0, 0);
            return;
        }
        throw new RSRuntimeException("Number of diagonals must be positive");
    }

    public void ZTPMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 75, n2, 0, 0, n, n3, 0, n5, 0, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n4, 0, 0, 0);
    }

    public void ZTPSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        int n5 = ScriptIntrinsicBLAS.validateTPMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 78, n2, 0, 0, n, n3, 0, n5, 0, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n4, 0, 0, 0);
    }

    public void ZTRMM(int n, int n2, int n3, int n4, Double2 double2, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRMM(Element.F64_2(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 135, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, 0, 0, 0, 0);
    }

    public void ZTRMV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 73, n2, 0, 0, n, n3, 0, n5, 0, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n4, 0, 0, 0);
    }

    public void ZTRSM(int n, int n2, int n3, int n4, Double2 double2, Allocation allocation, Allocation allocation2) {
        ScriptIntrinsicBLAS.validateUplo(n2);
        ScriptIntrinsicBLAS.validateDiag(n4);
        ScriptIntrinsicBLAS.validateTRSM(Element.F64_2(this.mRS), n, n3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 136, n3, 0, n, n2, n4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, 0, 0, 0, 0);
    }

    public void ZTRSV(int n, int n2, int n3, Allocation allocation, Allocation allocation2, int n4) {
        ScriptIntrinsicBLAS.validateTRMV(Element.F64_2(this.mRS), n, n2, n3, allocation, allocation2, n4);
        int n5 = allocation.getType().getY();
        this.mRS.nScriptIntrinsicBLAS_Z(this.getID(this.mRS), 76, n2, 0, 0, n, n3, 0, n5, 0, 0.0, 0.0, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0, 0.0, 0L, n4, 0, 0, 0);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Diag {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Side {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Transpose {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Uplo {
    }

}

