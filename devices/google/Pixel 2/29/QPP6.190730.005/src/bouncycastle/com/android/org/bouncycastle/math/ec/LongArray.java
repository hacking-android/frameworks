/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

class LongArray
implements Cloneable {
    private static final short[] INTERLEAVE2_TABLE = new short[]{0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
    private static final int[] INTERLEAVE3_TABLE = new int[]{0, 1, 8, 9, 64, 65, 72, 73, 512, 513, 520, 521, 576, 577, 584, 585, 4096, 4097, 4104, 4105, 4160, 4161, 4168, 4169, 4608, 4609, 4616, 4617, 4672, 4673, 4680, 4681, 32768, 32769, 32776, 32777, 32832, 32833, 32840, 32841, 33280, 33281, 33288, 33289, 33344, 33345, 33352, 33353, 36864, 36865, 36872, 36873, 36928, 36929, 36936, 36937, 37376, 37377, 37384, 37385, 37440, 37441, 37448, 37449, 262144, 262145, 262152, 262153, 262208, 262209, 262216, 262217, 262656, 262657, 262664, 262665, 262720, 262721, 262728, 262729, 266240, 266241, 266248, 266249, 266304, 266305, 266312, 266313, 266752, 266753, 266760, 266761, 266816, 266817, 266824, 266825, 294912, 294913, 294920, 294921, 294976, 294977, 294984, 294985, 295424, 295425, 295432, 295433, 295488, 295489, 295496, 295497, 299008, 299009, 299016, 299017, 299072, 299073, 299080, 299081, 299520, 299521, 299528, 299529, 299584, 299585, 299592, 299593};
    private static final int[] INTERLEAVE4_TABLE = new int[]{0, 1, 16, 17, 256, 257, 272, 273, 4096, 4097, 4112, 4113, 4352, 4353, 4368, 4369, 65536, 65537, 65552, 65553, 65792, 65793, 65808, 65809, 69632, 69633, 69648, 69649, 69888, 69889, 69904, 69905, 1048576, 1048577, 1048592, 1048593, 1048832, 1048833, 1048848, 1048849, 1052672, 1052673, 1052688, 1052689, 1052928, 1052929, 1052944, 1052945, 1114112, 1114113, 1114128, 1114129, 1114368, 1114369, 1114384, 1114385, 1118208, 1118209, 1118224, 1118225, 1118464, 1118465, 1118480, 1118481, 16777216, 16777217, 16777232, 16777233, 16777472, 16777473, 16777488, 16777489, 16781312, 16781313, 16781328, 16781329, 16781568, 16781569, 16781584, 16781585, 16842752, 16842753, 16842768, 16842769, 16843008, 16843009, 16843024, 16843025, 16846848, 16846849, 16846864, 16846865, 16847104, 16847105, 16847120, 16847121, 17825792, 17825793, 17825808, 17825809, 17826048, 17826049, 17826064, 17826065, 17829888, 17829889, 17829904, 17829905, 17830144, 17830145, 17830160, 17830161, 17891328, 17891329, 17891344, 17891345, 17891584, 17891585, 17891600, 17891601, 17895424, 17895425, 17895440, 17895441, 17895680, 17895681, 17895696, 17895697, 268435456, 268435457, 268435472, 268435473, 268435712, 268435713, 268435728, 268435729, 268439552, 268439553, 268439568, 268439569, 268439808, 268439809, 268439824, 268439825, 268500992, 268500993, 268501008, 268501009, 268501248, 268501249, 268501264, 268501265, 268505088, 268505089, 268505104, 268505105, 268505344, 268505345, 268505360, 268505361, 269484032, 269484033, 269484048, 269484049, 269484288, 269484289, 269484304, 269484305, 269488128, 269488129, 269488144, 269488145, 269488384, 269488385, 269488400, 269488401, 269549568, 269549569, 269549584, 269549585, 269549824, 269549825, 269549840, 269549841, 269553664, 269553665, 269553680, 269553681, 269553920, 269553921, 269553936, 269553937, 285212672, 285212673, 285212688, 285212689, 285212928, 285212929, 285212944, 285212945, 285216768, 285216769, 285216784, 285216785, 285217024, 285217025, 285217040, 285217041, 285278208, 285278209, 285278224, 285278225, 285278464, 285278465, 285278480, 285278481, 285282304, 285282305, 285282320, 285282321, 285282560, 285282561, 285282576, 285282577, 286261248, 286261249, 286261264, 286261265, 286261504, 286261505, 286261520, 286261521, 286265344, 286265345, 286265360, 286265361, 286265600, 286265601, 286265616, 286265617, 286326784, 286326785, 286326800, 286326801, 286327040, 286327041, 286327056, 286327057, 286330880, 286330881, 286330896, 286330897, 286331136, 286331137, 286331152, 286331153};
    private static final int[] INTERLEAVE5_TABLE = new int[]{0, 1, 32, 33, 1024, 1025, 1056, 1057, 32768, 32769, 32800, 32801, 33792, 33793, 33824, 33825, 1048576, 1048577, 1048608, 1048609, 1049600, 1049601, 1049632, 1049633, 1081344, 1081345, 1081376, 1081377, 1082368, 1082369, 1082400, 1082401, 33554432, 33554433, 33554464, 33554465, 33555456, 33555457, 33555488, 33555489, 33587200, 33587201, 33587232, 33587233, 33588224, 33588225, 33588256, 33588257, 34603008, 34603009, 34603040, 34603041, 34604032, 34604033, 34604064, 34604065, 34635776, 34635777, 34635808, 34635809, 34636800, 34636801, 34636832, 34636833, 1073741824, 1073741825, 1073741856, 1073741857, 1073742848, 1073742849, 1073742880, 1073742881, 1073774592, 1073774593, 1073774624, 1073774625, 1073775616, 1073775617, 1073775648, 1073775649, 1074790400, 1074790401, 1074790432, 1074790433, 1074791424, 1074791425, 1074791456, 1074791457, 1074823168, 1074823169, 1074823200, 1074823201, 1074824192, 1074824193, 1074824224, 1074824225, 1107296256, 1107296257, 1107296288, 1107296289, 1107297280, 1107297281, 1107297312, 1107297313, 1107329024, 1107329025, 1107329056, 1107329057, 1107330048, 1107330049, 1107330080, 1107330081, 1108344832, 1108344833, 1108344864, 1108344865, 1108345856, 1108345857, 1108345888, 1108345889, 1108377600, 1108377601, 1108377632, 1108377633, 1108378624, 1108378625, 1108378656, 1108378657};
    private static final long[] INTERLEAVE7_TABLE = new long[]{0L, 1L, 128L, 129L, 16384L, 16385L, 16512L, 16513L, 0x200000L, 2097153L, 2097280L, 2097281L, 2113536L, 2113537L, 2113664L, 2113665L, 0x10000000L, 0x10000001L, 268435584L, 268435585L, 268451840L, 268451841L, 268451968L, 268451969L, 270532608L, 270532609L, 270532736L, 270532737L, 270548992L, 270548993L, 270549120L, 270549121L, 0x800000000L, 34359738369L, 0x800000080L, 34359738497L, 34359754752L, 34359754753L, 34359754880L, 34359754881L, 34361835520L, 34361835521L, 34361835648L, 34361835649L, 34361851904L, 34361851905L, 34361852032L, 34361852033L, 34628173824L, 34628173825L, 34628173952L, 34628173953L, 34628190208L, 34628190209L, 34628190336L, 34628190337L, 34630270976L, 34630270977L, 34630271104L, 34630271105L, 34630287360L, 34630287361L, 34630287488L, 34630287489L, 0x40000000000L, 4398046511105L, 4398046511232L, 4398046511233L, 0x40000004000L, 4398046527489L, 4398046527616L, 4398046527617L, 4398048608256L, 4398048608257L, 4398048608384L, 4398048608385L, 4398048624640L, 4398048624641L, 4398048624768L, 4398048624769L, 4398314946560L, 4398314946561L, 4398314946688L, 4398314946689L, 4398314962944L, 4398314962945L, 4398314963072L, 4398314963073L, 4398317043712L, 4398317043713L, 4398317043840L, 4398317043841L, 4398317060096L, 4398317060097L, 4398317060224L, 4398317060225L, 4432406249472L, 4432406249473L, 4432406249600L, 4432406249601L, 4432406265856L, 4432406265857L, 4432406265984L, 4432406265985L, 4432408346624L, 4432408346625L, 4432408346752L, 4432408346753L, 4432408363008L, 4432408363009L, 4432408363136L, 4432408363137L, 4432674684928L, 4432674684929L, 4432674685056L, 4432674685057L, 4432674701312L, 4432674701313L, 4432674701440L, 4432674701441L, 4432676782080L, 4432676782081L, 4432676782208L, 4432676782209L, 4432676798464L, 4432676798465L, 4432676798592L, 4432676798593L, 0x2000000000000L, 562949953421313L, 562949953421440L, 562949953421441L, 562949953437696L, 562949953437697L, 562949953437824L, 562949953437825L, 0x2000000200000L, 562949955518465L, 562949955518592L, 562949955518593L, 562949955534848L, 562949955534849L, 562949955534976L, 562949955534977L, 562950221856768L, 562950221856769L, 562950221856896L, 562950221856897L, 562950221873152L, 562950221873153L, 562950221873280L, 562950221873281L, 562950223953920L, 562950223953921L, 562950223954048L, 562950223954049L, 562950223970304L, 562950223970305L, 562950223970432L, 562950223970433L, 562984313159680L, 562984313159681L, 562984313159808L, 562984313159809L, 562984313176064L, 562984313176065L, 562984313176192L, 562984313176193L, 562984315256832L, 562984315256833L, 562984315256960L, 562984315256961L, 562984315273216L, 562984315273217L, 562984315273344L, 562984315273345L, 562984581595136L, 562984581595137L, 562984581595264L, 562984581595265L, 562984581611520L, 562984581611521L, 562984581611648L, 562984581611649L, 562984583692288L, 562984583692289L, 562984583692416L, 562984583692417L, 562984583708672L, 562984583708673L, 562984583708800L, 562984583708801L, 567347999932416L, 567347999932417L, 567347999932544L, 567347999932545L, 567347999948800L, 567347999948801L, 567347999948928L, 567347999948929L, 567348002029568L, 567348002029569L, 567348002029696L, 567348002029697L, 567348002045952L, 567348002045953L, 567348002046080L, 567348002046081L, 567348268367872L, 567348268367873L, 567348268368000L, 567348268368001L, 567348268384256L, 567348268384257L, 567348268384384L, 567348268384385L, 567348270465024L, 567348270465025L, 567348270465152L, 567348270465153L, 567348270481408L, 567348270481409L, 567348270481536L, 567348270481537L, 567382359670784L, 567382359670785L, 567382359670912L, 567382359670913L, 567382359687168L, 567382359687169L, 567382359687296L, 567382359687297L, 567382361767936L, 567382361767937L, 567382361768064L, 567382361768065L, 567382361784320L, 567382361784321L, 567382361784448L, 567382361784449L, 567382628106240L, 567382628106241L, 567382628106368L, 567382628106369L, 567382628122624L, 567382628122625L, 567382628122752L, 567382628122753L, 567382630203392L, 567382630203393L, 567382630203520L, 567382630203521L, 567382630219776L, 567382630219777L, 567382630219904L, 567382630219905L, 0x100000000000000L, 0x100000000000001L, 72057594037928064L, 72057594037928065L, 72057594037944320L, 72057594037944321L, 72057594037944448L, 72057594037944449L, 72057594040025088L, 72057594040025089L, 72057594040025216L, 72057594040025217L, 72057594040041472L, 72057594040041473L, 72057594040041600L, 72057594040041601L, 0x100000010000000L, 0x100000010000001L, 72057594306363520L, 72057594306363521L, 72057594306379776L, 72057594306379777L, 72057594306379904L, 72057594306379905L, 72057594308460544L, 72057594308460545L, 72057594308460672L, 72057594308460673L, 72057594308476928L, 72057594308476929L, 72057594308477056L, 72057594308477057L, 72057628397666304L, 72057628397666305L, 72057628397666432L, 72057628397666433L, 72057628397682688L, 72057628397682689L, 72057628397682816L, 72057628397682817L, 72057628399763456L, 72057628399763457L, 72057628399763584L, 72057628399763585L, 72057628399779840L, 72057628399779841L, 72057628399779968L, 72057628399779969L, 72057628666101760L, 72057628666101761L, 72057628666101888L, 72057628666101889L, 72057628666118144L, 72057628666118145L, 72057628666118272L, 72057628666118273L, 72057628668198912L, 72057628668198913L, 72057628668199040L, 72057628668199041L, 72057628668215296L, 72057628668215297L, 72057628668215424L, 72057628668215425L, 72061992084439040L, 72061992084439041L, 72061992084439168L, 72061992084439169L, 72061992084455424L, 72061992084455425L, 72061992084455552L, 72061992084455553L, 72061992086536192L, 72061992086536193L, 72061992086536320L, 72061992086536321L, 72061992086552576L, 72061992086552577L, 72061992086552704L, 72061992086552705L, 72061992352874496L, 72061992352874497L, 72061992352874624L, 72061992352874625L, 72061992352890880L, 72061992352890881L, 72061992352891008L, 72061992352891009L, 72061992354971648L, 72061992354971649L, 72061992354971776L, 72061992354971777L, 72061992354988032L, 72061992354988033L, 72061992354988160L, 72061992354988161L, 72062026444177408L, 72062026444177409L, 72062026444177536L, 72062026444177537L, 72062026444193792L, 72062026444193793L, 72062026444193920L, 72062026444193921L, 72062026446274560L, 72062026446274561L, 72062026446274688L, 72062026446274689L, 72062026446290944L, 72062026446290945L, 72062026446291072L, 72062026446291073L, 72062026712612864L, 72062026712612865L, 72062026712612992L, 72062026712612993L, 72062026712629248L, 72062026712629249L, 72062026712629376L, 72062026712629377L, 72062026714710016L, 72062026714710017L, 72062026714710144L, 72062026714710145L, 72062026714726400L, 72062026714726401L, 72062026714726528L, 72062026714726529L, 72620543991349248L, 72620543991349249L, 72620543991349376L, 72620543991349377L, 72620543991365632L, 72620543991365633L, 72620543991365760L, 72620543991365761L, 72620543993446400L, 72620543993446401L, 72620543993446528L, 72620543993446529L, 72620543993462784L, 72620543993462785L, 72620543993462912L, 72620543993462913L, 72620544259784704L, 72620544259784705L, 72620544259784832L, 72620544259784833L, 72620544259801088L, 72620544259801089L, 72620544259801216L, 72620544259801217L, 72620544261881856L, 72620544261881857L, 72620544261881984L, 72620544261881985L, 72620544261898240L, 72620544261898241L, 72620544261898368L, 72620544261898369L, 72620578351087616L, 72620578351087617L, 72620578351087744L, 72620578351087745L, 72620578351104000L, 72620578351104001L, 72620578351104128L, 72620578351104129L, 72620578353184768L, 72620578353184769L, 72620578353184896L, 72620578353184897L, 72620578353201152L, 72620578353201153L, 72620578353201280L, 72620578353201281L, 72620578619523072L, 72620578619523073L, 72620578619523200L, 72620578619523201L, 72620578619539456L, 72620578619539457L, 72620578619539584L, 72620578619539585L, 72620578621620224L, 72620578621620225L, 72620578621620352L, 72620578621620353L, 72620578621636608L, 72620578621636609L, 72620578621636736L, 72620578621636737L, 72624942037860352L, 72624942037860353L, 72624942037860480L, 72624942037860481L, 72624942037876736L, 72624942037876737L, 72624942037876864L, 72624942037876865L, 72624942039957504L, 72624942039957505L, 72624942039957632L, 72624942039957633L, 72624942039973888L, 72624942039973889L, 72624942039974016L, 72624942039974017L, 72624942306295808L, 72624942306295809L, 72624942306295936L, 72624942306295937L, 72624942306312192L, 72624942306312193L, 72624942306312320L, 72624942306312321L, 72624942308392960L, 72624942308392961L, 72624942308393088L, 72624942308393089L, 72624942308409344L, 72624942308409345L, 72624942308409472L, 72624942308409473L, 72624976397598720L, 72624976397598721L, 72624976397598848L, 72624976397598849L, 72624976397615104L, 72624976397615105L, 72624976397615232L, 72624976397615233L, 72624976399695872L, 72624976399695873L, 72624976399696000L, 72624976399696001L, 72624976399712256L, 72624976399712257L, 72624976399712384L, 72624976399712385L, 72624976666034176L, 72624976666034177L, 72624976666034304L, 72624976666034305L, 72624976666050560L, 72624976666050561L, 72624976666050688L, 72624976666050689L, 72624976668131328L, 72624976668131329L, 72624976668131456L, 72624976668131457L, 72624976668147712L, 72624976668147713L, 72624976668147840L, 72624976668147841L};
    private static final String ZEROES = "0000000000000000000000000000000000000000000000000000000000000000";
    static final byte[] bitLengths = new byte[]{0, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    private long[] m_ints;

    public LongArray(int n) {
        this.m_ints = new long[n];
    }

    public LongArray(BigInteger arrby) {
        if (arrby != null && arrby.signum() >= 0) {
            if (arrby.signum() == 0) {
                this.m_ints = new long[]{0L};
                return;
            }
            arrby = arrby.toByteArray();
            int n = arrby.length;
            int n2 = 0;
            int n3 = n;
            if (arrby[0] == 0) {
                n3 = n - 1;
                n2 = 1;
            }
            n = (n3 + 7) / 8;
            this.m_ints = new long[n];
            int n4 = n - 1;
            int n5 = n3 % 8 + n2;
            long l = 0L;
            int n6 = n2;
            n3 = n4;
            n = n6;
            if (n2 < n5) {
                for (n = n6; n < n5; ++n) {
                    l = l << 8 | (long)(arrby[n] & 255);
                }
                this.m_ints[n4] = l;
                n3 = n4 - 1;
            }
            while (n3 >= 0) {
                l = 0L;
                n2 = 0;
                while (n2 < 8) {
                    l = l << 8 | (long)(arrby[n] & 255);
                    ++n2;
                    ++n;
                }
                this.m_ints[n3] = l;
                --n3;
            }
            return;
        }
        throw new IllegalArgumentException("invalid F2m field value");
    }

    public LongArray(long[] arrl) {
        this.m_ints = arrl;
    }

    public LongArray(long[] arrl, int n, int n2) {
        if (n == 0 && n2 == arrl.length) {
            this.m_ints = arrl;
        } else {
            this.m_ints = new long[n2];
            System.arraycopy(arrl, n, this.m_ints, 0, n2);
        }
    }

    private static void add(long[] arrl, int n, long[] arrl2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            int n4 = n + i;
            arrl[n4] = arrl[n4] ^ arrl2[n2 + i];
        }
    }

    private static void add(long[] arrl, int n, long[] arrl2, int n2, long[] arrl3, int n3, int n4) {
        for (int i = 0; i < n4; ++i) {
            arrl3[n3 + i] = arrl[n + i] ^ arrl2[n2 + i];
        }
    }

    private static void addBoth(long[] arrl, int n, long[] arrl2, int n2, long[] arrl3, int n3, int n4) {
        for (int i = 0; i < n4; ++i) {
            int n5 = n + i;
            arrl[n5] = arrl[n5] ^ (arrl2[n2 + i] ^ arrl3[n3 + i]);
        }
    }

    private void addShiftedByBitsSafe(LongArray arrl, int n, int n2) {
        int n3 = n + 63 >>> 6;
        n = n2 >>> 6;
        if ((n2 &= 63) == 0) {
            LongArray.add(this.m_ints, n, arrl.m_ints, 0, n3);
            return;
        }
        long l = LongArray.addShiftedUp(this.m_ints, n, arrl.m_ints, 0, n3, n2);
        if (l != 0L) {
            arrl = this.m_ints;
            n = n3 + n;
            arrl[n] = arrl[n] ^ l;
        }
    }

    private static long addShiftedDown(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        long l = 0L;
        while (--n3 >= 0) {
            long l2 = arrl2[n2 + n3];
            int n5 = n + n3;
            arrl[n5] = arrl[n5] ^ (l2 >>> n4 | l);
            l = l2 << 64 - n4;
        }
        return l;
    }

    private static long addShiftedUp(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        long l = 0L;
        for (int i = 0; i < n3; ++i) {
            long l2 = arrl2[n2 + i];
            int n5 = n + i;
            arrl[n5] = arrl[n5] ^ (l2 << n4 | l);
            l = l2 >>> 64 - n4;
        }
        return l;
    }

    private static int bitLength(long l) {
        int n;
        int n2 = (int)(l >>> 32);
        if (n2 == 0) {
            n2 = (int)l;
            n = 0;
        } else {
            n = 32;
        }
        int n3 = n2 >>> 16;
        if (n3 == 0) {
            n3 = n2 >>> 8;
            byte[] arrby = bitLengths;
            n2 = n3 == 0 ? arrby[n2] : arrby[n3] + 8;
        } else {
            n2 = n3 >>> 8;
            n2 = n2 == 0 ? bitLengths[n3] + 16 : bitLengths[n2] + 24;
        }
        return n + n2;
    }

    private int degreeFrom(int n) {
        long[] arrl;
        long l;
        n = n + 62 >>> 6;
        do {
            if (n != 0) continue;
            return 0;
        } while ((l = (arrl = this.m_ints)[--n]) == 0L);
        return (n << 6) + LongArray.bitLength(l);
    }

    private static void distribute(long[] arrl, int n, int n2, int n3, int n4) {
        for (int i = 0; i < n4; ++i) {
            long l = arrl[n + i];
            int n5 = n2 + i;
            arrl[n5] = arrl[n5] ^ l;
            n5 = n3 + i;
            arrl[n5] = arrl[n5] ^ l;
        }
    }

    private static void flipBit(long[] arrl, int n, int n2) {
        arrl[n += n2 >>> 6] = arrl[n] ^ 1L << (n2 & 63);
    }

    private static void flipVector(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        n += n4 >>> 6;
        if ((n4 &= 63) == 0) {
            LongArray.add(arrl, n, arrl2, n2, n3);
        } else {
            long l = LongArray.addShiftedDown(arrl, n + 1, arrl2, n2, n3, 64 - n4);
            arrl[n] = arrl[n] ^ l;
        }
    }

    private static void flipWord(long[] arrl, int n, int n2, long l) {
        n = (n2 >>> 6) + n;
        if ((n2 &= 63) == 0) {
            arrl[n] = arrl[n] ^ l;
        } else {
            arrl[n] = arrl[n] ^ l << n2;
            if ((l >>>= 64 - n2) != 0L) {
                arrl[++n] = arrl[n] ^ l;
            }
        }
    }

    private static void interleave(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        if (n4 != 3) {
            if (n4 != 5) {
                if (n4 != 7) {
                    LongArray.interleave2_n(arrl, n, arrl2, n2, n3, bitLengths[n4] - 1);
                } else {
                    LongArray.interleave7(arrl, n, arrl2, n2, n3);
                }
            } else {
                LongArray.interleave5(arrl, n, arrl2, n2, n3);
            }
        } else {
            LongArray.interleave3(arrl, n, arrl2, n2, n3);
        }
    }

    private static long interleave2_32to64(int n) {
        short[] arrs = INTERLEAVE2_TABLE;
        short s = arrs[n & 255];
        short s2 = arrs[n >>> 8 & 255];
        short s3 = arrs[n >>> 16 & 255];
        return ((long)(arrs[n >>> 24] << 16 | s3) & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)(s | s2 << 16);
    }

    private static long interleave2_n(long l, int n) {
        long l2;
        while (n > 1) {
            n -= 2;
            l2 = LongArray.interleave4_16to64((int)l & 65535);
            l = LongArray.interleave4_16to64((int)(l >>> 16) & 65535) << 1 | l2 | LongArray.interleave4_16to64((int)(l >>> 32) & 65535) << 2 | LongArray.interleave4_16to64((int)(l >>> 48) & 65535) << 3;
        }
        l2 = l;
        if (n > 0) {
            l2 = LongArray.interleave2_32to64((int)l) | LongArray.interleave2_32to64((int)(l >>> 32)) << 1;
        }
        return l2;
    }

    private static void interleave2_n(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        for (int i = 0; i < n3; ++i) {
            arrl2[n2 + i] = LongArray.interleave2_n(arrl[n + i], n4);
        }
    }

    private static long interleave3(long l) {
        long l2 = LongArray.interleave3_21to63((int)l & 2097151);
        long l3 = LongArray.interleave3_21to63((int)(l >>> 21) & 2097151);
        return LongArray.interleave3_21to63((int)(l >>> 42) & 2097151) << 2 | (l2 | Long.MIN_VALUE & l | l3 << 1);
    }

    private static void interleave3(long[] arrl, int n, long[] arrl2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            arrl2[n2 + i] = LongArray.interleave3(arrl[n + i]);
        }
    }

    private static long interleave3_13to65(int n) {
        int[] arrn = INTERLEAVE5_TABLE;
        int n2 = arrn[n & 127];
        return ((long)arrn[n >>> 7] & 0xFFFFFFFFL) << 35 | 0xFFFFFFFFL & (long)n2;
    }

    private static long interleave3_21to63(int n) {
        int[] arrn = INTERLEAVE3_TABLE;
        int n2 = arrn[n & 127];
        int n3 = arrn[n >>> 7 & 127];
        return ((long)arrn[n >>> 14] & 0xFFFFFFFFL) << 42 | ((long)n3 & 0xFFFFFFFFL) << 21 | 0xFFFFFFFFL & (long)n2;
    }

    private static long interleave4_16to64(int n) {
        int[] arrn = INTERLEAVE4_TABLE;
        int n2 = arrn[n & 255];
        return ((long)arrn[n >>> 8] & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)n2;
    }

    private static long interleave5(long l) {
        return LongArray.interleave3_13to65((int)l & 8191) | LongArray.interleave3_13to65((int)(l >>> 13) & 8191) << 1 | LongArray.interleave3_13to65((int)(l >>> 26) & 8191) << 2 | LongArray.interleave3_13to65((int)(l >>> 39) & 8191) << 3 | LongArray.interleave3_13to65((int)(l >>> 52) & 8191) << 4;
    }

    private static void interleave5(long[] arrl, int n, long[] arrl2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            arrl2[n2 + i] = LongArray.interleave5(arrl[n + i]);
        }
    }

    private static long interleave7(long l) {
        long[] arrl = INTERLEAVE7_TABLE;
        return arrl[(int)l & 511] | Long.MIN_VALUE & l | arrl[(int)(l >>> 9) & 511] << 1 | arrl[(int)(l >>> 18) & 511] << 2 | arrl[(int)(l >>> 27) & 511] << 3 | arrl[(int)(l >>> 36) & 511] << 4 | arrl[(int)(l >>> 45) & 511] << 5 | arrl[(int)(l >>> 54) & 511] << 6;
    }

    private static void interleave7(long[] arrl, int n, long[] arrl2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            arrl2[n2 + i] = LongArray.interleave7(arrl[n + i]);
        }
    }

    private static void multiplyWord(long l, long[] arrl, int n, long[] arrl2, int n2) {
        if ((l & 1L) != 0L) {
            LongArray.add(arrl2, n2, arrl, 0, n);
        }
        int n3 = 1;
        while ((l >>>= 1) != 0L) {
            long l2;
            if ((l & 1L) != 0L && (l2 = LongArray.addShiftedUp(arrl2, n2, arrl, 0, n, n3)) != 0L) {
                int n4 = n2 + n;
                arrl2[n4] = arrl2[n4] ^ l2;
            }
            ++n3;
        }
    }

    private static void reduceBit(long[] arrl, int n, int n2, int n3, int[] arrn) {
        LongArray.flipBit(arrl, n, n2);
        n3 = n2 - n3;
        n2 = arrn.length;
        while (--n2 >= 0) {
            LongArray.flipBit(arrl, n, arrn[n2] + n3);
        }
        LongArray.flipBit(arrl, n, n3);
    }

    private static void reduceBitWise(long[] arrl, int n, int n2, int n3, int[] arrn) {
        int n4;
        while ((n4 = n2 - 1) >= n3) {
            n2 = n4;
            if (!LongArray.testBit(arrl, n, n4)) continue;
            LongArray.reduceBit(arrl, n, n4, n3, arrn);
            n2 = n4;
        }
    }

    private static int reduceInPlace(long[] arrl, int n, int n2, int n3, int[] arrn) {
        int n4 = n3 + 63 >>> 6;
        if (n2 < n4) {
            return n2;
        }
        int n5 = Math.min(n2 << 6, (n3 << 1) - 1);
        int n6 = n2;
        int n7 = (n2 << 6) - n5;
        n2 = n6;
        for (n6 = n7; n6 >= 64; n6 -= 64) {
            --n2;
        }
        n7 = arrn.length;
        int n8 = arrn[n7 - 1];
        n7 = n7 > 1 ? arrn[n7 - 2] : 0;
        n6 = Math.min(n5 - (n8 = Math.max(n3, n8 + 64)), n3 - n7) + n6 >> 6;
        if (n6 > 1) {
            n6 = n7 = n2 - n6;
            LongArray.reduceVectorWise(arrl, n, n2, n7, n3, arrn);
            while (n2 > n6) {
                arrl[n + --n2] = 0L;
            }
            n7 = n2;
            n2 = n6 << 6;
            n6 = n7;
        } else {
            n7 = n5;
            n6 = n2;
            n2 = n7;
        }
        n7 = n8;
        n8 = n2;
        if (n2 > n7) {
            LongArray.reduceWordWise(arrl, n, n6, n7, n3, arrn);
            n8 = n7;
        }
        if (n8 > n3) {
            LongArray.reduceBitWise(arrl, n, n8, n3, arrn);
        }
        return n4;
    }

    private static LongArray reduceResult(long[] arrl, int n, int n2, int n3, int[] arrn) {
        return new LongArray(arrl, n, LongArray.reduceInPlace(arrl, n, n2, n3, arrn));
    }

    private static void reduceVectorWise(long[] arrl, int n, int n2, int n3, int n4, int[] arrn) {
        int n5 = (n3 << 6) - n4;
        n4 = arrn.length;
        while (--n4 >= 0) {
            LongArray.flipVector(arrl, n, arrl, n + n3, n2 - n3, n5 + arrn[n4]);
        }
        LongArray.flipVector(arrl, n, arrl, n + n3, n2 - n3, n5);
    }

    private static void reduceWord(long[] arrl, int n, int n2, long l, int n3, int[] arrn) {
        n3 = n2 - n3;
        n2 = arrn.length;
        while (--n2 >= 0) {
            LongArray.flipWord(arrl, n, arrn[n2] + n3, l);
        }
        LongArray.flipWord(arrl, n, n3, l);
    }

    private static void reduceWordWise(long[] arrl, int n, int n2, int n3, int n4, int[] arrn) {
        long l;
        int n5 = n3 >>> 6;
        while (--n2 > n5) {
            l = arrl[n + n2];
            if (l == 0L) continue;
            arrl[n + n2] = 0L;
            LongArray.reduceWord(arrl, n, n2 << 6, l, n4, arrn);
        }
        n2 = n3 & 63;
        l = arrl[n + n5] >>> n2;
        if (l != 0L) {
            n5 = n + n5;
            arrl[n5] = arrl[n5] ^ l << n2;
            LongArray.reduceWord(arrl, n, n3, l, n4, arrn);
        }
    }

    private long[] resizedInts(int n) {
        long[] arrl = new long[n];
        long[] arrl2 = this.m_ints;
        System.arraycopy(arrl2, 0, arrl, 0, Math.min(arrl2.length, n));
        return arrl;
    }

    private static long shiftUp(long[] arrl, int n, int n2, int n3) {
        long l = 0L;
        for (int i = 0; i < n2; ++i) {
            long l2 = arrl[n + i];
            arrl[n + i] = l2 << n3 | l;
            l = l2 >>> 64 - n3;
        }
        return l;
    }

    private static long shiftUp(long[] arrl, int n, long[] arrl2, int n2, int n3, int n4) {
        long l = 0L;
        for (int i = 0; i < n3; ++i) {
            long l2 = arrl[n + i];
            arrl2[n2 + i] = l2 << n4 | l;
            l = l2 >>> 64 - n4;
        }
        return l;
    }

    private static void squareInPlace(long[] arrl, int n, int n2, int[] arrn) {
        int n3 = n << 1;
        n2 = n;
        n = n3;
        while (--n2 >= 0) {
            long l = arrl[n2];
            arrl[--n] = LongArray.interleave2_32to64((int)(l >>> 32));
            arrl[--n] = LongArray.interleave2_32to64((int)l);
        }
    }

    private static boolean testBit(long[] arrl, int n, int n2) {
        boolean bl = (arrl[n + (n2 >>> 6)] & 1L << (n2 & 63)) != 0L;
        return bl;
    }

    public LongArray addOne() {
        if (this.m_ints.length == 0) {
            return new LongArray(new long[]{1L});
        }
        long[] arrl = this.resizedInts(Math.max(1, this.getUsedLength()));
        arrl[0] = 1L ^ arrl[0];
        return new LongArray(arrl);
    }

    public void addShiftedByWords(LongArray longArray, int n) {
        int n2 = longArray.getUsedLength();
        if (n2 == 0) {
            return;
        }
        int n3 = n2 + n;
        if (n3 > this.m_ints.length) {
            this.m_ints = this.resizedInts(n3);
        }
        LongArray.add(this.m_ints, n, longArray.m_ints, 0, n2);
    }

    public Object clone() {
        return new LongArray(Arrays.clone(this.m_ints));
    }

    void copyTo(long[] arrl, int n) {
        long[] arrl2 = this.m_ints;
        System.arraycopy(arrl2, 0, arrl, n, arrl2.length);
    }

    public int degree() {
        long[] arrl;
        long l;
        int n = this.m_ints.length;
        do {
            if (n != 0) continue;
            return 0;
        } while ((l = (arrl = this.m_ints)[--n]) == 0L);
        return (n << 6) + LongArray.bitLength(l);
    }

    public boolean equals(Object object) {
        if (!(object instanceof LongArray)) {
            return false;
        }
        object = (LongArray)object;
        int n = this.getUsedLength();
        if (((LongArray)object).getUsedLength() != n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.m_ints[i] == ((LongArray)object).m_ints[i]) continue;
            return false;
        }
        return true;
    }

    public int getLength() {
        return this.m_ints.length;
    }

    public int getUsedLength() {
        return this.getUsedLengthFrom(this.m_ints.length);
    }

    public int getUsedLengthFrom(int n) {
        long[] arrl = this.m_ints;
        int n2 = Math.min(n, arrl.length);
        if (n2 < 1) {
            return 0;
        }
        n = n2;
        if (arrl[0] != 0L) {
            while (arrl[--n2] == 0L) {
            }
            return n2 + 1;
        }
        do {
            if (arrl[--n] == 0L) continue;
            return n + 1;
        } while (n > 0);
        return 0;
    }

    public int hashCode() {
        int n = this.getUsedLength();
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            long l = this.m_ints[i];
            n2 = (n2 * 31 ^ (int)l) * 31 ^ (int)(l >>> 32);
        }
        return n2;
    }

    public boolean isOne() {
        long[] arrl = this.m_ints;
        if (arrl[0] != 1L) {
            return false;
        }
        for (int i = 1; i < arrl.length; ++i) {
            if (arrl[i] == 0L) continue;
            return false;
        }
        return true;
    }

    public boolean isZero() {
        long[] arrl = this.m_ints;
        for (int i = 0; i < arrl.length; ++i) {
            if (arrl[i] == 0L) continue;
            return false;
        }
        return true;
    }

    public LongArray modInverse(int n, int[] object) {
        int n2 = this.degree();
        if (n2 != 0) {
            int[] arrn;
            if (n2 == 1) {
                return this;
            }
            LongArray longArray = (LongArray)this.clone();
            int n3 = n + 63 >>> 6;
            LongArray[] arrlongArray = new LongArray(n3);
            LongArray.reduceBit(arrlongArray.m_ints, 0, n, n, object);
            object = new LongArray(n3);
            object.m_ints[0] = 1L;
            LongArray longArray2 = new LongArray(n3);
            int[] arrn2 = new int[]{n2, n + 1};
            LongArray[] arrlongArray2 = new LongArray[]{longArray, arrlongArray};
            int[] arrn3 = arrn = new int[2];
            arrn3[0] = 1;
            arrn3[1] = 0;
            arrlongArray = new LongArray[]{object, longArray2};
            n3 = arrn2[1];
            int n4 = arrn[1];
            n = arrn2[1 - 1];
            int n5 = 1;
            n = n3 - n;
            object = longArray;
            do {
                int n6;
                if (n < 0) {
                    n = -n;
                    arrn2[n5] = n3;
                    arrn[n5] = n4;
                    n3 = 1 - n5;
                    n6 = arrn2[n3];
                    n4 = arrn[n3];
                    n5 = n;
                } else {
                    n6 = n3;
                    n3 = n5;
                    n5 = n;
                }
                arrlongArray2[n3].addShiftedByBitsSafe(arrlongArray2[1 - n3], arrn2[1 - n3], n5);
                int n7 = arrlongArray2[n3].degreeFrom(n6);
                if (n7 == 0) {
                    return arrlongArray[1 - n3];
                }
                n = arrn[1 - n3];
                arrlongArray[n3].addShiftedByBitsSafe(arrlongArray[1 - n3], n, n5);
                int n8 = n + n5;
                if (n8 > n4) {
                    n = n8;
                } else {
                    n = n4;
                    if (n8 == n4) {
                        n = arrlongArray[n3].degreeFrom(n4);
                    }
                }
                n5 += n7 - n6;
                n4 = n7;
                n6 = n;
                n = n5;
                n5 = n3;
                n3 = n4;
                n4 = n6;
            } while (true);
        }
        throw new IllegalStateException();
    }

    public LongArray modMultiply(LongArray arrl, int n, int[] arrn) {
        int n2;
        int n3 = this.degree();
        if (n3 == 0) {
            return this;
        }
        int n4 = arrl.degree();
        if (n4 == 0) {
            return arrl;
        }
        long[] arrl2 = this;
        long[] arrl3 = arrl;
        int n5 = n3;
        int n6 = n4;
        if (n3 > n4) {
            arrl3 = this;
            n5 = n4;
            n6 = n3;
            arrl2 = arrl;
        }
        int n7 = n5 + 63 >>> 6;
        n3 = n6 + 63 >>> 6;
        int n8 = n5 + n6 + 62 >>> 6;
        if (n7 == 1) {
            long l = arrl2.m_ints[0];
            if (l == 1L) {
                return arrl3;
            }
            arrl = new long[n8];
            LongArray.multiplyWord(l, arrl3.m_ints, n3, arrl, 0);
            return LongArray.reduceResult(arrl, 0, n8, n, arrn);
        }
        int n9 = n6 + 7 + 63 >>> 6;
        int[] arrn2 = new int[16];
        arrl = new long[n9 << 4];
        arrn2[1] = n9;
        System.arraycopy(arrl3.m_ints, 0, arrl, n9, n3);
        n4 = n9;
        for (n3 = 2; n3 < 16; ++n3) {
            n4 = n2 = n4 + n9;
            arrn2[n3] = n2;
            if ((n3 & 1) == 0) {
                LongArray.shiftUp(arrl, n4 >>> 1, arrl, n4, n9, 1);
                continue;
            }
            LongArray.add(arrl, n9, arrl, n4 - n9, arrl, n4, n9);
        }
        arrl3 = new long[arrl.length];
        LongArray.shiftUp(arrl, 0, arrl3, 0, arrl.length, 4);
        long[] arrl4 = arrl2.m_ints;
        arrl2 = new long[n8 << 3];
        n4 = n6;
        n6 = n5;
        block1 : for (n3 = 0; n3 < n7; ++n3) {
            long l = arrl4[n3];
            n2 = n3;
            n5 = n4;
            do {
                n4 = (int)l;
                int n10 = (int)(l >>>= 4);
                LongArray.addBoth(arrl2, n2, arrl, arrn2[n4 & 15], arrl3, arrn2[n10 & 15], n9);
                if ((l >>>= 4) == 0L) {
                    n4 = n5;
                    continue block1;
                }
                n2 += n8;
            } while (true);
        }
        n5 = arrl2.length;
        arrl3 = arrn2;
        arrl = arrl4;
        while ((n5 -= n8) != 0) {
            LongArray.addShiftedUp(arrl2, n5 - n8, arrl2, n5, n8, 8);
        }
        return LongArray.reduceResult(arrl2, 0, n8, n, arrn);
    }

    public LongArray modMultiplyAlt(LongArray arrl, int n, int[] arrn) {
        int n2 = this.degree();
        if (n2 == 0) {
            return this;
        }
        int n3 = arrl.degree();
        if (n3 == 0) {
            return arrl;
        }
        long[] arrl2 = this;
        long[] arrl3 = arrl;
        int n4 = n2;
        int n5 = n3;
        if (n2 > n3) {
            arrl3 = this;
            n4 = n3;
            n5 = n2;
            arrl2 = arrl;
        }
        int n6 = n4 + 63 >>> 6;
        n2 = n5 + 63 >>> 6;
        int n7 = n4 + n5 + 62 >>> 6;
        if (n6 == 1) {
            long l = arrl2.m_ints[0];
            if (l == 1L) {
                return arrl3;
            }
            arrl = new long[n7];
            LongArray.multiplyWord(l, arrl3.m_ints, n2, arrl, 0);
            return LongArray.reduceResult(arrl, 0, n7, n, arrn);
        }
        n4 = 64 < 64 ? 16 : 16 - 1;
        n3 = n5 + n4 + 63 >>> 6;
        int n8 = n3 * 8;
        int[] arrn2 = new int[1 << 4];
        arrn2[0] = n6;
        arrn2[1] = n4 = n6 + n8;
        for (n5 = 2; n5 < arrn2.length; ++n5) {
            arrn2[n5] = n4 += n7;
        }
        int n9 = n4 + n7 + 1;
        arrl = new long[n9];
        arrl2 = arrl2.m_ints;
        int n10 = 8;
        LongArray.interleave(arrl2, 0, arrl, 0, n6, 4);
        n4 = n6;
        System.arraycopy(arrl3.m_ints, 0, arrl, n4, n2);
        for (n5 = 1; n5 < n10; ++n5) {
            LongArray.shiftUp(arrl, n6, arrl, n4 += n3, n3, n5);
        }
        int n11 = 0;
        int n12 = (1 << 4) - 1;
        n4 = n2;
        n5 = n9;
        n9 = n11;
        block2 : do {
            n11 = 0;
            block3 : do {
                long l = arrl[n11] >>> n9;
                int n13 = 0;
                int n14 = n6;
                do {
                    n2 = n5;
                    int n15 = (int)l & n12;
                    if (n15 != 0) {
                        LongArray.add(arrl, n11 + arrn2[n15], arrl, n14, n3);
                    }
                    if (++n13 == n10) {
                        if (++n11 >= n6) {
                            n9 = n11 = n9 + 4 * 8;
                            if (n11 >= 64) {
                                if (n9 >= 64) {
                                    n5 = arrn2.length;
                                    while (--n5 > 1) {
                                        if (((long)n5 & 1L) == 0L) {
                                            LongArray.addShiftedUp(arrl, arrn2[n5 >>> 1], arrl, arrn2[n5], n7, 16);
                                            continue;
                                        }
                                        n4 = n5;
                                        LongArray.distribute(arrl, arrn2[n4], arrn2[n4 - 1], arrn2[1], n7);
                                    }
                                    return LongArray.reduceResult(arrl, arrn2[1], n7, n, arrn);
                                }
                                n2 = 64 - 4;
                                n12 &= n12 << 64 - n2;
                            } else {
                                n2 = n9;
                            }
                            LongArray.shiftUp(arrl, n6, n8, n10);
                            n9 = n2;
                            continue block2;
                        }
                        n5 = n2;
                        continue block3;
                    }
                    n14 += n3;
                    l >>>= 4;
                    n5 = n2;
                } while (true);
                break;
            } while (true);
            break;
        } while (true);
    }

    public LongArray modMultiplyLD(LongArray arrl, int n, int[] arrn) {
        int n2;
        int n3 = this.degree();
        if (n3 == 0) {
            return this;
        }
        int n4 = arrl.degree();
        if (n4 == 0) {
            return arrl;
        }
        LongArray longArray = this;
        long[] arrl2 = arrl;
        int n5 = n3;
        int n6 = n4;
        if (n3 > n4) {
            arrl2 = this;
            n5 = n4;
            n6 = n3;
            longArray = arrl;
        }
        int n7 = n5 + 63 >>> 6;
        n4 = n6 + 63 >>> 6;
        int n8 = n5 + n6 + 62 >>> 6;
        if (n7 == 1) {
            long l = longArray.m_ints[0];
            if (l == 1L) {
                return arrl2;
            }
            arrl = new long[n8];
            LongArray.multiplyWord(l, arrl2.m_ints, n4, arrl, 0);
            return LongArray.reduceResult(arrl, 0, n8, n, arrn);
        }
        int n9 = n6 + 7 + 63 >>> 6;
        int[] arrn2 = new int[16];
        long[] arrl3 = new long[n9 << 4];
        arrn2[1] = n3 = n9;
        System.arraycopy(arrl2.m_ints, 0, arrl3, n3, n4);
        for (n4 = 2; n4 < 16; ++n4) {
            n3 = n2 = n3 + n9;
            arrn2[n4] = n2;
            if ((n4 & 1) == 0) {
                LongArray.shiftUp(arrl3, n3 >>> 1, arrl3, n3, n9, 1);
                continue;
            }
            LongArray.add(arrl3, n9, arrl3, n3 - n9, arrl3, n3, n9);
        }
        long[] arrl4 = new long[arrl3.length];
        LongArray.shiftUp(arrl3, 0, arrl4, 0, arrl3.length, 4);
        arrl = longArray.m_ints;
        long[] arrl5 = new long[n8];
        for (n3 = 56; n3 >= 0; n3 -= 8) {
            for (n4 = 1; n4 < n7; n4 += 2) {
                n2 = (int)(arrl[n4] >>> n3);
                LongArray.addBoth(arrl5, n4 - 1, arrl3, arrn2[n2 & 15], arrl4, arrn2[n2 >>> 4 & 15], n9);
            }
            LongArray.shiftUp(arrl5, 0, n8, 8);
        }
        for (n6 = 56; n6 >= 0; n6 -= 8) {
            for (n5 = 0; n5 < n7; n5 += 2) {
                n3 = (int)(arrl[n5] >>> n6);
                LongArray.addBoth(arrl5, n5, arrl3, arrn2[n3 & 15], arrl4, arrn2[n3 >>> 4 & 15], n9);
            }
            if (n6 <= 0) continue;
            LongArray.shiftUp(arrl5, 0, n8, 8);
        }
        return LongArray.reduceResult(arrl5, 0, n8, n, arrn);
    }

    public LongArray modReduce(int n, int[] arrn) {
        long[] arrl = Arrays.clone(this.m_ints);
        return new LongArray(arrl, 0, LongArray.reduceInPlace(arrl, 0, arrl.length, n, arrn));
    }

    public LongArray modSquare(int n, int[] arrn) {
        int n2 = this.getUsedLength();
        if (n2 == 0) {
            return this;
        }
        int n3 = n2 << 1;
        long[] arrl = new long[n3];
        n2 = 0;
        while (n2 < n3) {
            long l = this.m_ints[n2 >>> 1];
            int n4 = n2 + 1;
            arrl[n2] = LongArray.interleave2_32to64((int)l);
            n2 = n4 + 1;
            arrl[n4] = LongArray.interleave2_32to64((int)(l >>> 32));
        }
        return new LongArray(arrl, 0, LongArray.reduceInPlace(arrl, 0, arrl.length, n, arrn));
    }

    public LongArray modSquareN(int n, int n2, int[] arrn) {
        int n3 = this.getUsedLength();
        if (n3 == 0) {
            return this;
        }
        long[] arrl = new long[n2 + 63 >>> 6 << 1];
        System.arraycopy(this.m_ints, 0, arrl, 0, n3);
        int n4 = n;
        n = n3;
        while (--n4 >= 0) {
            LongArray.squareInPlace(arrl, n, n2, arrn);
            n = LongArray.reduceInPlace(arrl, 0, arrl.length, n2, arrn);
        }
        return new LongArray(arrl, 0, n);
    }

    public LongArray multiply(LongArray arrn, int n, int[] arrobject) {
        int n2;
        int n3 = this.degree();
        if (n3 == 0) {
            return this;
        }
        int n4 = arrn.degree();
        if (n4 == 0) {
            return arrn;
        }
        long[] arrl = this;
        arrobject = arrn;
        int n5 = n3;
        n = n4;
        if (n3 > n4) {
            arrobject = this;
            n5 = n4;
            n = n3;
            arrl = arrn;
        }
        int n6 = n5 + 63 >>> 6;
        n3 = n + 63 >>> 6;
        int n7 = n5 + n + 62 >>> 6;
        if (n6 == 1) {
            long l = arrl.m_ints[0];
            if (l == 1L) {
                return arrobject;
            }
            arrn = new long[n7];
            LongArray.multiplyWord(l, arrobject.m_ints, n3, arrn, 0);
            return new LongArray(arrn, 0, n7);
        }
        int n8 = n + 7 + 63 >>> 6;
        arrn = new int[16];
        long[] arrl2 = new long[n8 << 4];
        arrn[1] = n8;
        System.arraycopy(arrobject.m_ints, 0, arrl2, n8, n3);
        n3 = n8;
        for (n4 = 2; n4 < 16; ++n4) {
            n3 = n2 = n3 + n8;
            arrn[n4] = n2;
            if ((n4 & 1) == 0) {
                LongArray.shiftUp(arrl2, n3 >>> 1, arrl2, n3, n8, 1);
                continue;
            }
            LongArray.add(arrl2, n8, arrl2, n3 - n8, arrl2, n3, n8);
        }
        arrobject = new long[arrl2.length];
        LongArray.shiftUp(arrl2, 0, arrobject, 0, arrl2.length, 4);
        arrl = arrl.m_ints;
        long[] arrl3 = new long[n7 << 3];
        block1 : for (n3 = 0; n3 < n6; ++n3) {
            long l = arrl[n3];
            n4 = n3;
            do {
                n2 = (int)l;
                int n9 = (int)(l >>>= 4);
                LongArray.addBoth(arrl3, n4, arrl2, arrn[n2 & 15], arrobject, arrn[n9 & 15], n8);
                if ((l >>>= 4) == 0L) {
                    continue block1;
                }
                n4 += n7;
            } while (true);
        }
        n = arrl3.length;
        arrobject = arrl2;
        do {
            n = n5 = n - n7;
            if (n5 == 0) break;
            LongArray.addShiftedUp(arrl3, n - n7, arrl3, n, n7, 8);
        } while (true);
        return new LongArray(arrl3, 0, n7);
    }

    public void reduce(int n, int[] arrn) {
        long[] arrl = this.m_ints;
        if ((n = LongArray.reduceInPlace(arrl, 0, arrl.length, n, arrn)) < arrl.length) {
            this.m_ints = new long[n];
            System.arraycopy(arrl, 0, this.m_ints, 0, n);
        }
    }

    public LongArray square(int n, int[] arrn) {
        n = this.getUsedLength();
        if (n == 0) {
            return this;
        }
        int n2 = n << 1;
        arrn = new long[n2];
        n = 0;
        while (n < n2) {
            long l = this.m_ints[n >>> 1];
            int n3 = n + 1;
            arrn[n] = (int)LongArray.interleave2_32to64((int)l);
            n = n3 + 1;
            arrn[n3] = (int)LongArray.interleave2_32to64((int)(l >>> 32));
        }
        return new LongArray(arrn, 0, arrn.length);
    }

    public boolean testBitZero() {
        boolean bl;
        long[] arrl = this.m_ints;
        int n = arrl.length;
        boolean bl2 = bl = false;
        if (n > 0) {
            bl2 = bl;
            if ((arrl[0] & 1L) != 0L) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public BigInteger toBigInteger() {
        int n;
        int n2 = this.getUsedLength();
        if (n2 == 0) {
            return ECConstants.ZERO;
        }
        long l = this.m_ints[n2 - 1];
        byte[] arrby = new byte[8];
        int n3 = 0;
        int n4 = 0;
        for (n = 7; n >= 0; --n) {
            int n5;
            block9 : {
                byte by;
                block8 : {
                    by = (byte)(l >>> n * 8);
                    if (n4 != 0) break block8;
                    n5 = n3;
                    if (by == 0) break block9;
                }
                n4 = 1;
                arrby[n3] = by;
                n5 = n3 + 1;
            }
            n3 = n5;
        }
        byte[] arrby2 = new byte[(n2 - 1) * 8 + n3];
        for (n = 0; n < n3; ++n) {
            arrby2[n] = arrby[n];
        }
        for (n = n2 - 2; n >= 0; --n) {
            l = this.m_ints[n];
            n4 = 7;
            while (n4 >= 0) {
                arrby2[n3] = (byte)(l >>> n4 * 8);
                --n4;
                ++n3;
            }
        }
        return new BigInteger(1, arrby2);
    }

    public String toString() {
        int n = this.getUsedLength();
        if (n == 0) {
            return "0";
        }
        Object object = this.m_ints;
        StringBuffer stringBuffer = new StringBuffer(Long.toBinaryString(object[--n]));
        while (--n >= 0) {
            object = Long.toBinaryString(this.m_ints[n]);
            int n2 = ((String)object).length();
            if (n2 < 64) {
                stringBuffer.append(ZEROES.substring(n2));
            }
            stringBuffer.append((String)object);
        }
        return stringBuffer.toString();
    }
}

