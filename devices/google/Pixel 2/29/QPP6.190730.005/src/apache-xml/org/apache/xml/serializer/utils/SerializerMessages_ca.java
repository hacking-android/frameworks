/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages_ca
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGFORMAT", "El format del missatge ''{0}'' a la classe del missatge ''{1}'' ha fallat."};
        Object[] arrobject2 = new Object[]{"ER_SERIALIZER_NOT_CONTENTHANDLER", "La classe de serialitzador ''{0}'' no implementa org.xml.sax.ContentHandler."};
        Object[] arrobject3 = new Object[]{"ER_RESOURCE_COULD_NOT_FIND", "No s''ha trobat el recurs [ {0} ].\n {1}"};
        Object[] arrobject4 = new Object[]{"ER_INVALID_UTF16_SURROGATE", "S''ha detectat un suplent UTF-16 no v\u00e0lid: {0} ?"};
        Object[] arrobject5 = new Object[]{"ER_ILLEGAL_ATTRIBUTE_POSITION", "No es pot afegir l''atribut {0} despr\u00e9s dels nodes subordinats o abans que es produeixi un element. Es passar\u00e0 per alt l''atribut."};
        Object[] arrobject6 = new Object[]{"ER_NAMESPACE_PREFIX", "No s''ha declarat l''espai de noms pel prefix ''{0}''."};
        Object[] arrobject7 = new Object[]{"ER_STRAY_ATTRIBUTE", "L''atribut ''{0}'' es troba fora de l''element."};
        Object[] arrobject8 = new Object[]{"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "No s''ha pogut carregar el fitxer de propietats ''{0}'' del m\u00e8tode de sortida ''{1}'' (comproveu CLASSPATH)"};
        Object[] arrobject9 = new Object[]{"ER_PORT_WHEN_HOST_NULL", "El port no es pot establir quan el sistema principal \u00e9s nul"};
        Object[] arrobject10 = new Object[]{"ER_HOST_ADDRESS_NOT_WELLFORMED", "El format de l'adre\u00e7a del sistema principal no \u00e9s el correcte"};
        Object[] arrobject11 = new Object[]{"ER_SCHEME_NOT_CONFORMANT", "L'esquema no t\u00e9 conformitat."};
        Object[] arrobject12 = new Object[]{"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "La via d'acc\u00e9s cont\u00e9 una seq\u00fc\u00e8ncia d'escapament no v\u00e0lida"};
        Object[] arrobject13 = new Object[]{"ER_PATH_INVALID_CHAR", "La via d''acc\u00e9s cont\u00e9 un car\u00e0cter no v\u00e0lid {0}"};
        Object[] arrobject14 = new Object[]{"ER_FRAG_INVALID_CHAR", "El fragment cont\u00e9 un car\u00e0cter no v\u00e0lid"};
        Object[] arrobject15 = new Object[]{"ER_FRAG_FOR_GENERIC_URI", "El fragment nom\u00e9s es pot establir per a un URI gen\u00e8ric"};
        Object[] arrobject16 = new Object[]{"ER_NO_SCHEME_IN_URI", "No s'ha trobat cap esquema a l'URI"};
        Object[] arrobject17 = new Object[]{"ER_NO_FRAGMENT_STRING_IN_PATH", "No es pot especificar un fragment tant en la via d'acc\u00e9s com en el fragment"};
        Object[] arrobject18 = new Object[]{"ER_NO_USERINFO_IF_NO_HOST", "No es pot especificar informaci\u00f3 de l'usuari si no s'especifica el sistema principal"};
        Object[] arrobject19 = new Object[]{"ER_XML_VERSION_NOT_SUPPORTED", "Av\u00eds: la versi\u00f3 del document de sortida s''ha sol\u00b7licitat que sigui ''{0}''. Aquesta versi\u00f3 de XML no est\u00e0 suportada. La versi\u00f3 del document de sortida ser\u00e0 ''1.0''."};
        Object[] arrobject20 = new Object[]{"ER_ENCODING_NOT_SUPPORTED", "Av\u00eds: el temps d''execuci\u00f3 de Java no d\u00f3na suport a la codificaci\u00f3 ''{0}''."};
        Object[] arrobject21 = new Object[]{"FEATURE_NOT_SUPPORTED", "El par\u00e0metre ''{0}'' es reconeix per\u00f2 el valor sol\u00b7licitat no es pot establir."};
        Object[] arrobject22 = new Object[]{"DOMSTRING_SIZE_ERR", "La cadena resultant \u00e9s massa llarga per cabre en una DOMString: ''{0}''."};
        Object[] arrobject23 = new Object[]{"TYPE_MISMATCH_ERR", "El tipus de valor per a aquest nom de par\u00e0metre \u00e9s incompatible amb el tipus de valor esperat."};
        Object[] arrobject24 = new Object[]{"no-output-specified", "La destinaci\u00f3 de sortida per a les dades que s'ha d'escriure era nul\u00b7la."};
        Object[] arrobject25 = new Object[]{"unsupported-encoding", "S'ha trobat una codificaci\u00f3 no suportada."};
        Object[] arrobject26 = new Object[]{"ER_WARNING_WF_NOT_CHECKED", "No s'ha pogut crear cap inst\u00e0ncia per comprovar si t\u00e9 un format correcte o no. El par\u00e0metre del tipus ben format es va establir en cert, per\u00f2 la comprovaci\u00f3 de format no s'ha pogut realitzar."};
        Object[] arrobject27 = new Object[]{"wf-invalid-character", "El node ''{0}'' cont\u00e9 car\u00e0cters XML no v\u00e0lids."};
        Object[] arrobject28 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_COMMENT", "S''ha trobat un car\u00e0cter XML no v\u00e0lid (Unicode: 0x{0}) en el comentari."};
        Object[] arrobject29 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "S''ha trobat un car\u00e0cter XML no v\u00e0lid (Unicode: 0x{0}) en les dades d''instrucci\u00f3 de proc\u00e9s."};
        Object[] arrobject30 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_TEXT", "S''ha trobat un car\u00e0cter XML no v\u00e0lid (Unicode: 0x''{0})'' en el contingut de dades de car\u00e0cter del node."};
        Object[] arrobject31 = new Object[]{"wf-invalid-character-in-node-name", "S''han trobat car\u00e0cters XML no v\u00e0lids al node {0} anomenat ''{1}''."};
        Object[] arrobject32 = new Object[]{"ER_WF_DASH_IN_COMMENT", "La cadena \"--\" no est\u00e0 permesa dins dels comentaris."};
        Object[] arrobject33 = new Object[]{"ER_NS_PREFIX_CANNOT_BE_BOUND", "El prefix \"{0}\" no es pot vincular a l''espai de noms \"{1}\"."};
        Object[] arrobject34 = new Object[]{"ER_NULL_LOCAL_ELEMENT_NAME", "El nom local de l''element \"{0}\" \u00e9s nul."};
        Object[] arrobject35 = new Object[]{"ER_NULL_LOCAL_ATTR_NAME", "El nom local d''atr \"{0}\" \u00e9s nul."};
        Object[] arrobject36 = new Object[]{"unbound-prefix-in-entity-reference", "El text de recanvi del node de l''entitat \"{0}\" cont\u00e9 un node d''element \"{1}\" amb un prefix de no enlla\u00e7at \"{2}\"."};
        Object[] arrobject37 = new Object[]{"unbound-prefix-in-entity-reference", "El text de recanvi del node de l''entitat \"{0}\" cont\u00e9 un node d''atribut \"{1}\" amb un prefix de no enlla\u00e7at \"{2}\"."};
        return new Object[][]{{"BAD_MSGKEY", "La clau del missatge ''{0}'' no est\u00e0 a la classe del missatge ''{1}''"}, arrobject, arrobject2, arrobject3, {"ER_RESOURCE_COULD_NOT_LOAD", "No s''ha pogut carregar el recurs [ {0} ]: {1} \n {2} \t {3}"}, {"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Grand\u00e0ria del buffer <=0"}, arrobject4, {"ER_OIERROR", "Error d'E/S"}, arrobject5, arrobject6, arrobject7, {"ER_STRAY_NAMESPACE", "La declaraci\u00f3 de l''espai de noms ''{0}''=''{1}'' es troba fora de l''element."}, {"ER_COULD_NOT_LOAD_RESOURCE", "No s''ha pogut carregar ''{0}'' (comproveu CLASSPATH), ara s''est\u00e0 fent servir els valors per defecte."}, {"ER_ILLEGAL_CHARACTER", "S''ha intentat un car\u00e0cter de sortida del valor integral {0} que no est\u00e0 representat a una codificaci\u00f3 de sortida especificada de {1}."}, arrobject8, {"ER_INVALID_PORT", "N\u00famero de port no v\u00e0lid"}, arrobject9, arrobject10, arrobject11, {"ER_SCHEME_FROM_NULL_STRING", "No es pot establir un esquema des d'una cadena nul\u00b7la"}, arrobject12, arrobject13, arrobject14, {"ER_FRAG_WHEN_PATH_NULL", "El fragment no es pot establir si la via d'acc\u00e9s \u00e9s nul\u00b7la"}, arrobject15, arrobject16, {"ER_CANNOT_INIT_URI_EMPTY_PARMS", "No es pot inicialitzar l'URI amb par\u00e0metres buits"}, arrobject17, {"ER_NO_QUERY_STRING_IN_PATH", "No es pot especificar una cadena de consulta en la via d'acc\u00e9s i la cadena de consulta"}, {"ER_NO_PORT_IF_NO_HOST", "No es pot especificar el port si no s'especifica el sistema principal"}, arrobject18, arrobject19, {"ER_SCHEME_REQUIRED", "Es necessita l'esquema"}, {"ER_FACTORY_PROPERTY_MISSING", "L''objecte de propietats passat a SerializerFactory no t\u00e9 cap propietat ''{0}''."}, arrobject20, {"FEATURE_NOT_FOUND", "El par\u00e0metre ''{0}'' no es reconeix."}, arrobject21, arrobject22, arrobject23, arrobject24, arrobject25, {"ER_UNABLE_TO_SERIALIZE_NODE", "El node no s'ha pogut serialitzat."}, {"cdata-sections-splitted", "La secci\u00f3 CDATA cont\u00e9 un o m\u00e9s marcadors d'acabament ']]>'."}, arrobject26, arrobject27, arrobject28, arrobject29, {"ER_WF_INVALID_CHARACTER_IN_CDATA", "S''ha trobat un car\u00e0cter XML no v\u00e0lid (Unicode: 0x''{0})'' en els continguts de la CDATASection."}, arrobject30, arrobject31, arrobject32, {"ER_WF_LT_IN_ATTVAL", "El valor d''atribut \"{1}\" associat amb un tipus d''element \"{0}\" no pot contenir el car\u00e0cter ''<''."}, {"ER_WF_REF_TO_UNPARSED_ENT", "La refer\u00e8ncia de l''entitat no analitzada \"&{0};\" no est\u00e0 permesa."}, {"ER_WF_REF_TO_EXTERNAL_ENT", "La refer\u00e8ncia externa de l''entitat \"&{0};\" no est\u00e0 permesa en un valor d''atribut."}, arrobject33, arrobject34, arrobject35, arrobject36, arrobject37};
    }
}

