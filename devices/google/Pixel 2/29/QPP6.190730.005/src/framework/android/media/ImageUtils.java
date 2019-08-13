/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Memory
 */
package android.media;

import android.media.Image;
import android.media.ImageWriter;
import android.util.Size;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import libcore.io.Memory;

class ImageUtils {
    ImageUtils() {
    }

    private static void directByteBufferCopy(ByteBuffer byteBuffer, int n, ByteBuffer byteBuffer2, int n2, int n3) {
        Memory.memmove((Object)byteBuffer2, (int)n2, (Object)byteBuffer, (int)n, (long)n3);
    }

    /*
     * Exception decompiling
     */
    private static Size getEffectivePlaneSizeForImage(Image var0, int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static int getEstimatedNativeAllocBytes(int var0, int var1_1, int var2_2, int var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:328)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:466)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static int getNumPlanesForFormat(int var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static void imageCopy(Image object, Image arrplane) {
        if (object != null && arrplane != null) {
            if (((Image)object).getFormat() == arrplane.getFormat()) {
                if (((Image)object).getFormat() != 34 && arrplane.getFormat() != 34) {
                    if (((Image)object).getFormat() != 36) {
                        if (((Image)object).getFormat() != 4098) {
                            if (arrplane.getOwner() instanceof ImageWriter) {
                                Image.Plane[] arrplane2;
                                Object object2 = new Size(((Image)object).getWidth(), ((Image)object).getHeight());
                                if (((Size)object2).equals(arrplane2 = new Size(arrplane.getWidth(), arrplane.getHeight()))) {
                                    arrplane2 = ((Image)object).getPlanes();
                                    arrplane = arrplane.getPlanes();
                                    for (int i = 0; i < arrplane2.length; ++i) {
                                        int n = arrplane2[i].getRowStride();
                                        int n2 = arrplane[i].getRowStride();
                                        object2 = arrplane2[i].getBuffer();
                                        ByteBuffer byteBuffer = arrplane[i].getBuffer();
                                        if (((ByteBuffer)object2).isDirect() && byteBuffer.isDirect()) {
                                            if (arrplane2[i].getPixelStride() == arrplane[i].getPixelStride()) {
                                                int n3 = ((Buffer)object2).position();
                                                ((Buffer)object2).rewind();
                                                byteBuffer.rewind();
                                                if (n == n2) {
                                                    byteBuffer.put((ByteBuffer)object2);
                                                } else {
                                                    int n4 = ((Buffer)object2).position();
                                                    int n5 = byteBuffer.position();
                                                    Size size = ImageUtils.getEffectivePlaneSizeForImage((Image)object, i);
                                                    int n6 = size.getWidth() * arrplane2[i].getPixelStride();
                                                    for (int j = 0; j < size.getHeight(); ++j) {
                                                        int n7 = n6;
                                                        if (j == size.getHeight() - 1) {
                                                            int n8 = ((Buffer)object2).remaining() - n4;
                                                            n7 = n6;
                                                            if (n6 > n8) {
                                                                n7 = n8;
                                                            }
                                                        }
                                                        ImageUtils.directByteBufferCopy((ByteBuffer)object2, n4, byteBuffer, n5, n7);
                                                        n4 += n;
                                                        n5 += n2;
                                                        n6 = n7;
                                                    }
                                                }
                                                ((Buffer)object2).position(n3);
                                                byteBuffer.rewind();
                                                continue;
                                            }
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Source plane image pixel stride ");
                                            ((StringBuilder)object).append(arrplane2[i].getPixelStride());
                                            ((StringBuilder)object).append(" must be same as destination image pixel stride ");
                                            ((StringBuilder)object).append(arrplane[i].getPixelStride());
                                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                                        }
                                        throw new IllegalArgumentException("Source and destination ByteBuffers must be direct byteBuffer!");
                                    }
                                    return;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("source image size ");
                                ((StringBuilder)object).append(object2);
                                ((StringBuilder)object).append(" is different with destination image size ");
                                ((StringBuilder)object).append(arrplane2);
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            throw new IllegalArgumentException("Destination image is not from ImageWriter. Only the images from ImageWriter are writable");
                        }
                        throw new IllegalArgumentException("Copy of RAW_DEPTH format has not been implemented");
                    }
                    throw new IllegalArgumentException("Copy of RAW_OPAQUE format has not been implemented");
                }
                throw new IllegalArgumentException("PRIVATE format images are not copyable");
            }
            throw new IllegalArgumentException("Src and dst images should have the same format");
        }
        throw new IllegalArgumentException("Images should be non-null");
    }
}

