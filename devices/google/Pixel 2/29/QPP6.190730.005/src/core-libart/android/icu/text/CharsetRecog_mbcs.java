/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetMatch;
import android.icu.text.CharsetRecognizer;
import java.util.Arrays;

abstract class CharsetRecog_mbcs
extends CharsetRecognizer {
    CharsetRecog_mbcs() {
    }

    @Override
    abstract String getName();

    int match(CharsetDetector charsetDetector, int[] arrn) {
        int n;
        block14 : {
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            n = 0;
            int n5 = 0;
            int n6 = 0;
            iteratedChar iteratedChar2 = new iteratedChar();
            iteratedChar2.reset();
            while (this.nextChar(iteratedChar2, charsetDetector)) {
                int n7;
                int n8;
                int n9;
                int n10;
                int n11 = n5 + 1;
                if (iteratedChar2.error) {
                    n10 = n + 1;
                    n7 = n2;
                    n8 = n3;
                    n9 = n4;
                } else {
                    long l = (long)iteratedChar2.charValue & 0xFFFFFFFFL;
                    if (l <= 255L) {
                        n7 = n2 + 1;
                        n8 = n3;
                        n9 = n4;
                        n10 = n;
                    } else {
                        n5 = n3 + 1;
                        n7 = n2;
                        n8 = n5;
                        n9 = n4;
                        n10 = n;
                        if (arrn != null) {
                            n7 = n2;
                            n8 = n5;
                            n9 = n4;
                            n10 = n;
                            if (Arrays.binarySearch(arrn, (int)l) >= 0) {
                                n9 = n4 + 1;
                                n10 = n;
                                n8 = n5;
                                n7 = n2;
                            }
                        }
                    }
                }
                n2 = n7;
                n3 = n8;
                n4 = n9;
                n = n10;
                n5 = n11;
                if (n10 < 2) continue;
                n2 = n7;
                n3 = n8;
                n4 = n9;
                n = n10;
                n5 = n11;
                if (n10 * 5 < n8) continue;
                n = n6;
                break block14;
            }
            if (n3 <= 10 && n == 0) {
                n = n3 == 0 && n5 < 10 ? 0 : 10;
            } else if (n3 < n * 20) {
                n = 0;
            } else if (arrn == null) {
                n = n4 = n3 + 30 - n * 20;
                if (n4 > 100) {
                    n = 100;
                }
            } else {
                double d = 90.0 / Math.log((float)n3 / 4.0f);
                n = Math.min((int)(Math.log(n4 + 1) * d + 10.0), 100);
            }
        }
        return n;
    }

    abstract boolean nextChar(iteratedChar var1, CharsetDetector var2);

    static class CharsetRecog_big5
    extends CharsetRecog_mbcs {
        static int[] commonChars = new int[]{41280, 41281, 41282, 41283, 41287, 41289, 41333, 41334, 42048, 42054, 42055, 42056, 42065, 42068, 42071, 42084, 42090, 42092, 42103, 42147, 42148, 42151, 42177, 42190, 42193, 42207, 42216, 42237, 42304, 42312, 42328, 42345, 42445, 42471, 42583, 42593, 42594, 42600, 42608, 42664, 42675, 42681, 42707, 42715, 42726, 42738, 42816, 42833, 42841, 42970, 43171, 43173, 43181, 43217, 43219, 43236, 43260, 43456, 43474, 43507, 43627, 43706, 43710, 43724, 43772, 44103, 44111, 44208, 44242, 44377, 44745, 45024, 45290, 45423, 45747, 45764, 45935, 46156, 46158, 46412, 46501, 46525, 46544, 46552, 46705, 47085, 47207, 47428, 47832, 47940, 48033, 48593, 49860, 50105, 50240, 50271};

        CharsetRecog_big5() {
        }

        @Override
        public String getLanguage() {
            return "zh";
        }

        @Override
        String getName() {
            return "Big5";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match((CharsetDetector)object, commonChars);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }

        @Override
        boolean nextChar(iteratedChar iteratedChar2, CharsetDetector charsetDetector) {
            int n;
            iteratedChar2.error = false;
            iteratedChar2.charValue = n = iteratedChar2.nextByte(charsetDetector);
            if (n < 0) {
                return false;
            }
            if (n > 127 && n != 255) {
                n = iteratedChar2.nextByte(charsetDetector);
                if (n < 0) {
                    return false;
                }
                iteratedChar2.charValue = iteratedChar2.charValue << 8 | n;
                if (n < 64 || n == 127 || n == 255) {
                    iteratedChar2.error = true;
                }
                return true;
            }
            return true;
        }
    }

    static abstract class CharsetRecog_euc
    extends CharsetRecog_mbcs {
        CharsetRecog_euc() {
        }

        @Override
        boolean nextChar(iteratedChar iteratedChar2, CharsetDetector charsetDetector) {
            int n;
            iteratedChar2.error = false;
            iteratedChar2.charValue = n = iteratedChar2.nextByte(charsetDetector);
            if (n < 0) {
                iteratedChar2.done = true;
            } else if (n > 141) {
                int n2 = iteratedChar2.nextByte(charsetDetector);
                iteratedChar2.charValue = iteratedChar2.charValue << 8 | n2;
                if (n >= 161 && n <= 254) {
                    if (n2 < 161) {
                        iteratedChar2.error = true;
                    }
                } else if (n == 142) {
                    if (n2 < 161) {
                        iteratedChar2.error = true;
                    }
                } else if (n == 143) {
                    n2 = iteratedChar2.nextByte(charsetDetector);
                    iteratedChar2.charValue = iteratedChar2.charValue << 8 | n2;
                    if (n2 < 161) {
                        iteratedChar2.error = true;
                    }
                }
            }
            return true ^ iteratedChar2.done;
        }

        static class CharsetRecog_euc_jp
        extends CharsetRecog_euc {
            static int[] commonChars = new int[]{41377, 41378, 41379, 41382, 41404, 41418, 41419, 41430, 41431, 42146, 42148, 42150, 42152, 42154, 42155, 42156, 42157, 42159, 42161, 42163, 42165, 42167, 42169, 42171, 42173, 42175, 42176, 42177, 42179, 42180, 42182, 42183, 42184, 42185, 42186, 42187, 42190, 42191, 42192, 42206, 42207, 42209, 42210, 42212, 42216, 42217, 42218, 42219, 42220, 42223, 42226, 42227, 42402, 42403, 42404, 42406, 42407, 42410, 42413, 42415, 42416, 42419, 42421, 42423, 42424, 42425, 42431, 42435, 42438, 42439, 42440, 42441, 42443, 42448, 42453, 42454, 42455, 42462, 42464, 42465, 42469, 42473, 42474, 42475, 42476, 42477, 42483, 47273, 47572, 47854, 48072, 48880, 49079, 50410, 50940, 51133, 51896, 51955, 52188, 52689};

            CharsetRecog_euc_jp() {
            }

            @Override
            public String getLanguage() {
                return "ja";
            }

            @Override
            String getName() {
                return "EUC-JP";
            }

            @Override
            CharsetMatch match(CharsetDetector object) {
                int n = this.match((CharsetDetector)object, commonChars);
                object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
                return object;
            }
        }

        static class CharsetRecog_euc_kr
        extends CharsetRecog_euc {
            static int[] commonChars = new int[]{45217, 45235, 45253, 45261, 45268, 45286, 45293, 45304, 45306, 45308, 45496, 45497, 45511, 45527, 45538, 45994, 46011, 46274, 46287, 46297, 46315, 46501, 46517, 46527, 46535, 46569, 46835, 47023, 47042, 47054, 47270, 47278, 47286, 47288, 47291, 47337, 47531, 47534, 47564, 47566, 47613, 47800, 47822, 47824, 47857, 48103, 48115, 48125, 48301, 48314, 48338, 48374, 48570, 48576, 48579, 48581, 48838, 48840, 48863, 48878, 48888, 48890, 49057, 49065, 49088, 49124, 49131, 49132, 49144, 49319, 49327, 49336, 49338, 49339, 49341, 49351, 49356, 49358, 49359, 49366, 49370, 49381, 49403, 49404, 49572, 49574, 49590, 49622, 49631, 49654, 49656, 50337, 50637, 50862, 51151, 51153, 51154, 51160, 51173, 51373};

            CharsetRecog_euc_kr() {
            }

            @Override
            public String getLanguage() {
                return "ko";
            }

            @Override
            String getName() {
                return "EUC-KR";
            }

            @Override
            CharsetMatch match(CharsetDetector object) {
                int n = this.match((CharsetDetector)object, commonChars);
                object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
                return object;
            }
        }

    }

    static class CharsetRecog_gb_18030
    extends CharsetRecog_mbcs {
        static int[] commonChars = new int[]{41377, 41378, 41379, 41380, 41392, 41393, 41457, 41459, 41889, 41900, 41914, 45480, 45496, 45502, 45755, 46025, 46070, 46323, 46525, 46532, 46563, 46767, 46804, 46816, 47010, 47016, 47037, 47062, 47069, 47284, 47327, 47350, 47531, 47561, 47576, 47610, 47613, 47821, 48039, 48086, 48097, 48122, 48316, 48347, 48382, 48588, 48845, 48861, 49076, 49094, 49097, 49332, 49389, 49611, 49883, 50119, 50396, 50410, 50636, 50935, 51192, 51371, 51403, 51413, 51431, 51663, 51706, 51889, 51893, 51911, 51920, 51926, 51957, 51965, 52460, 52728, 52906, 52932, 52946, 52965, 53173, 53186, 53206, 53442, 53445, 53456, 53460, 53671, 53930, 53938, 53941, 53947, 53972, 54211, 54224, 54269, 54466, 54490, 54754, 54992};

        CharsetRecog_gb_18030() {
        }

        @Override
        public String getLanguage() {
            return "zh";
        }

        @Override
        String getName() {
            return "GB18030";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match((CharsetDetector)object, commonChars);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }

        @Override
        boolean nextChar(iteratedChar iteratedChar2, CharsetDetector charsetDetector) {
            int n;
            iteratedChar2.error = false;
            iteratedChar2.charValue = n = iteratedChar2.nextByte(charsetDetector);
            if (n < 0) {
                iteratedChar2.done = true;
            } else if (n > 128) {
                int n2 = iteratedChar2.nextByte(charsetDetector);
                iteratedChar2.charValue = iteratedChar2.charValue << 8 | n2;
                if (!(n < 129 || n > 254 || n2 >= 64 && n2 <= 126 || n2 >= 80 && n2 <= 254)) {
                    if (n2 >= 48 && n2 <= 57 && (n = iteratedChar2.nextByte(charsetDetector)) >= 129 && n <= 254 && (n2 = iteratedChar2.nextByte(charsetDetector)) >= 48 && n2 <= 57) {
                        iteratedChar2.charValue = iteratedChar2.charValue << 16 | n << 8 | n2;
                    } else {
                        iteratedChar2.error = true;
                    }
                }
            }
            return true ^ iteratedChar2.done;
        }
    }

    static class CharsetRecog_sjis
    extends CharsetRecog_mbcs {
        static int[] commonChars = new int[]{33088, 33089, 33090, 33093, 33115, 33129, 33130, 33141, 33142, 33440, 33442, 33444, 33449, 33450, 33451, 33453, 33455, 33457, 33459, 33461, 33463, 33469, 33470, 33473, 33476, 33477, 33478, 33480, 33481, 33484, 33485, 33500, 33504, 33511, 33512, 33513, 33514, 33520, 33521, 33601, 33603, 33614, 33615, 33624, 33630, 33634, 33639, 33653, 33654, 33673, 33674, 33675, 33677, 33683, 36502, 37882, 38314};

        CharsetRecog_sjis() {
        }

        @Override
        public String getLanguage() {
            return "ja";
        }

        @Override
        String getName() {
            return "Shift_JIS";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match((CharsetDetector)object, commonChars);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }

        @Override
        boolean nextChar(iteratedChar iteratedChar2, CharsetDetector charsetDetector) {
            int n;
            iteratedChar2.error = false;
            iteratedChar2.charValue = n = iteratedChar2.nextByte(charsetDetector);
            if (n < 0) {
                return false;
            }
            if (n > 127 && (n <= 160 || n > 223)) {
                int n2 = iteratedChar2.nextByte(charsetDetector);
                if (n2 < 0) {
                    return false;
                }
                iteratedChar2.charValue = n << 8 | n2;
                if (!(n2 >= 64 && n2 <= 127 || n2 >= 128 && n2 <= 255)) {
                    iteratedChar2.error = true;
                }
                return true;
            }
            return true;
        }
    }

    static class iteratedChar {
        int charValue = 0;
        boolean done = false;
        boolean error = false;
        int nextIndex = 0;

        iteratedChar() {
        }

        int nextByte(CharsetDetector arrby) {
            if (this.nextIndex >= arrby.fRawLength) {
                this.done = true;
                return -1;
            }
            arrby = arrby.fRawInput;
            int n = this.nextIndex;
            this.nextIndex = n + 1;
            return arrby[n] & 255;
        }

        void reset() {
            this.charValue = 0;
            this.nextIndex = 0;
            this.error = false;
            this.done = false;
        }
    }

}

