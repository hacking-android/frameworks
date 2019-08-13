/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages_fr
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGFORMAT", "Le format du message ''{0}'' de la classe du message ''{1}'' est incorrect."};
        Object[] arrobject2 = new Object[]{"ER_SERIALIZER_NOT_CONTENTHANDLER", "La classe de la m\u00e9thode de s\u00e9rialisation ''{0}'' n''impl\u00e9mente pas org.xml.sax.ContentHandler."};
        Object[] arrobject3 = new Object[]{"ER_RESOURCE_COULD_NOT_FIND", "La ressource [ {0} ] est introuvable.\n {1}"};
        Object[] arrobject4 = new Object[]{"ER_NAMESPACE_PREFIX", "L''espace de noms du pr\u00e9fixe ''{0}'' n''a pas \u00e9t\u00e9 d\u00e9clar\u00e9."};
        Object[] arrobject5 = new Object[]{"ER_COULD_NOT_LOAD_RESOURCE", "Impossible de charger ''{0}'' (v\u00e9rifier CLASSPATH), les valeurs par d\u00e9faut sont donc employ\u00e9es"};
        Object[] arrobject6 = new Object[]{"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Impossible de charger le fichier de propri\u00e9t\u00e9s ''{0}'' pour la m\u00e9thode de sortie ''{1}'' (v\u00e9rifier CLASSPATH)"};
        Object[] arrobject7 = new Object[]{"ER_SCHEME_NOT_CONFORMANT", "Le processus n'est pas conforme."};
        Object[] arrobject8 = new Object[]{"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Le chemin d'acc\u00e8s contient une s\u00e9quence d'\u00e9chappement non valide"};
        Object[] arrobject9 = new Object[]{"ER_PATH_INVALID_CHAR", "Le chemin contient un caract\u00e8re non valide : {0}"};
        Object[] arrobject10 = new Object[]{"ER_FRAG_WHEN_PATH_NULL", "Le fragment ne peut \u00eatre d\u00e9fini quand le chemin d'acc\u00e8s est vide"};
        Object[] arrobject11 = new Object[]{"ER_NO_SCHEME_IN_URI", "Processus introuvable dans l'URI"};
        Object[] arrobject12 = new Object[]{"ER_NO_QUERY_STRING_IN_PATH", "La cha\u00eene de requ\u00eate ne doit pas figurer dans un chemin et une cha\u00eene de requ\u00eate"};
        Object[] arrobject13 = new Object[]{"ER_ENCODING_NOT_SUPPORTED", "Avertissement : Le codage ''{0}'' n''est pas pris en charge par l''environnement d''ex\u00e9cution Java."};
        Object[] arrobject14 = new Object[]{"DOMSTRING_SIZE_ERR", "La cha\u00eene obtenue est trop longue pour un DOMString : ''{0}''."};
        Object[] arrobject15 = new Object[]{"no-output-specified", "La sortie de destination des donn\u00e9es \u00e0 \u00e9crire \u00e9tait vide."};
        Object[] arrobject16 = new Object[]{"ER_UNABLE_TO_SERIALIZE_NODE", "Le noeud ne peut pas \u00eatre s\u00e9rialis\u00e9."};
        Object[] arrobject17 = new Object[]{"cdata-sections-splitted", "La section CDATA contient un ou plusieurs marqueurs de fin ']]>'."};
        Object[] arrobject18 = new Object[]{"ER_WARNING_WF_NOT_CHECKED", "Aucune instance du programme de v\u00e9rification de la formation n'a pu \u00eatre cr\u00e9\u00e9e.  La valeur true a \u00e9t\u00e9 attribu\u00e9e au param\u00e8tre well-formed mais la v\u00e9rification de la formation n'a pas pu \u00eatre effectu\u00e9e."};
        Object[] arrobject19 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_COMMENT", "Un caract\u00e8re XML non valide (Unicode : 0x{0}) a \u00e9t\u00e9 trouv\u00e9 dans le commentaire."};
        Object[] arrobject20 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "Un caract\u00e8re XML non valide (Unicode : 0x{0}) a \u00e9t\u00e9 trouv\u00e9 dans les donn\u00e9es de l''instruction de traitement."};
        Object[] arrobject21 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_CDATA", "Un caract\u00e8re XML non valide (Unicode: 0x{0}) a \u00e9t\u00e9 trouv\u00e9 dans le contenu de la CDATASection"};
        Object[] arrobject22 = new Object[]{"ER_WF_DASH_IN_COMMENT", "La cha\u00eene \"--\" est interdite dans des commentaires."};
        Object[] arrobject23 = new Object[]{"ER_WF_LT_IN_ATTVAL", "La valeur de l''attribut \"{1}\" associ\u00e9 \u00e0 un type d''\u00e9l\u00e9ment \"{0}\" ne doit pas contenir le caract\u00e8re ''<''."};
        Object[] arrobject24 = new Object[]{"ER_WF_REF_TO_EXTERNAL_ENT", "La r\u00e9f\u00e9rence d''entit\u00e9 externe \"&{0};\" n''est pas admise dans une valeur d''attribut."};
        Object[] arrobject25 = new Object[]{"ER_NS_PREFIX_CANNOT_BE_BOUND", "Le pr\u00e9fixe \"{0}\" ne peut pas \u00eatre li\u00e9 \u00e0 l''espace de noms \"{1}\"."};
        Object[] arrobject26 = new Object[]{"unbound-prefix-in-entity-reference", "Le texte de remplacement du noeud de l''entit\u00e9 \"{0}\" contient un noeud d''attribut \"{1}\" avec un pr\u00e9fixe non li\u00e9 \"{2}\"."};
        return new Object[][]{{"BAD_MSGKEY", "La cl\u00e9 du message ''{0}'' ne se trouve pas dans la classe du message ''{1}''"}, arrobject, arrobject2, arrobject3, {"ER_RESOURCE_COULD_NOT_LOAD", "La ressource [ {0} ] n''a pas pu charger : {1} \n {2} \t {3}"}, {"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Taille du tampon <=0"}, {"ER_INVALID_UTF16_SURROGATE", "Substitut UTF-16 non valide d\u00e9tect\u00e9 : {0} ?"}, {"ER_OIERROR", "Erreur d'E-S"}, {"ER_ILLEGAL_ATTRIBUTE_POSITION", "Ajout impossible de l''attribut {0} apr\u00e8s des noeuds enfants ou avant la production d''un \u00e9l\u00e9ment.  L''attribut est ignor\u00e9."}, arrobject4, {"ER_STRAY_ATTRIBUTE", "L''attribut ''{0}'' est \u00e0 l''ext\u00e9rieur de l''\u00e9l\u00e9ment."}, {"ER_STRAY_NAMESPACE", "La d\u00e9claration d''espace de noms ''{0}''=''{1}'' est \u00e0 l''ext\u00e9rieur de l''\u00e9l\u00e9ment."}, arrobject5, {"ER_ILLEGAL_CHARACTER", "Tentative de sortie d''un caract\u00e8re de la valeur enti\u00e8re {0} non repr\u00e9sent\u00e9e dans l''encodage de sortie de {1}."}, arrobject6, {"ER_INVALID_PORT", "Num\u00e9ro de port non valide"}, {"ER_PORT_WHEN_HOST_NULL", "Le port ne peut \u00eatre d\u00e9fini quand l'h\u00f4te est vide"}, {"ER_HOST_ADDRESS_NOT_WELLFORMED", "L'h\u00f4te n'est pas une adresse bien form\u00e9e"}, arrobject7, {"ER_SCHEME_FROM_NULL_STRING", "Impossible de d\u00e9finir le processus \u00e0 partir de la cha\u00eene vide"}, arrobject8, arrobject9, {"ER_FRAG_INVALID_CHAR", "Le fragment contient un caract\u00e8re non valide"}, arrobject10, {"ER_FRAG_FOR_GENERIC_URI", "Le fragment ne peut \u00eatre d\u00e9fini que pour un URI g\u00e9n\u00e9rique"}, arrobject11, {"ER_CANNOT_INIT_URI_EMPTY_PARMS", "Impossible d'initialiser l'URI avec des param\u00e8tres vides"}, {"ER_NO_FRAGMENT_STRING_IN_PATH", "Le fragment ne doit pas \u00eatre indiqu\u00e9 \u00e0 la fois dans le chemin et dans le fragment"}, arrobject12, {"ER_NO_PORT_IF_NO_HOST", "Le port peut ne pas \u00eatre sp\u00e9cifi\u00e9 si l'h\u00f4te n'est pas sp\u00e9cifi\u00e9"}, {"ER_NO_USERINFO_IF_NO_HOST", "Userinfo ne peut \u00eatre sp\u00e9cifi\u00e9 si l'h\u00f4te ne l'est pas"}, {"ER_XML_VERSION_NOT_SUPPORTED", "Avertissement : La version du document de sortie doit \u00eatre ''{0}''.  Cette version XML n''est pas prise en charge.  La version du document de sortie sera ''1.0''."}, {"ER_SCHEME_REQUIRED", "Processus requis !"}, {"ER_FACTORY_PROPERTY_MISSING", "L''objet Properties transmis \u00e0 SerializerFactory ne dispose pas de propri\u00e9t\u00e9 ''{0}''."}, arrobject13, {"FEATURE_NOT_FOUND", "Le param\u00e8tre ''{0}'' n''est pas reconnu."}, {"FEATURE_NOT_SUPPORTED", "Le param\u00e8tre ''{0}'' est reconnu mas la valeur demand\u00e9e ne peut pas \u00eatre d\u00e9finie."}, arrobject14, {"TYPE_MISMATCH_ERR", "Le type de valeur de ce param\u00e8tre est incompatible avec le type de valeur attendu."}, arrobject15, {"unsupported-encoding", "Codage non pris en charge."}, arrobject16, arrobject17, arrobject18, {"wf-invalid-character", "Le noeud ''{0}'' contient des caract\u00e8res XML non valides."}, arrobject19, arrobject20, arrobject21, {"ER_WF_INVALID_CHARACTER_IN_TEXT", "Un caract\u00e8re XML non valide (Unicode : 0x{0}) a \u00e9t\u00e9 trouv\u00e9 dans le contenu des donn\u00e9es de type caract\u00e8res du noeud."}, {"wf-invalid-character-in-node-name", "Un ou plusieurs caract\u00e8res non valides ont \u00e9t\u00e9 trouv\u00e9s dans le noeud {0} nomm\u00e9 ''{1}''."}, arrobject22, arrobject23, {"ER_WF_REF_TO_UNPARSED_ENT", "La r\u00e9f\u00e9rence d''entit\u00e9 non analys\u00e9e \"&{0};\" n''est pas admise."}, arrobject24, arrobject25, {"ER_NULL_LOCAL_ELEMENT_NAME", "Le nom local de l''\u00e9l\u00e9ment \"{0}\" a une valeur null."}, {"ER_NULL_LOCAL_ATTR_NAME", "Le nom local de l''attribut \"{0}\" a une valeur null."}, {"unbound-prefix-in-entity-reference", "le texte de remplacement du noeud de l''entit\u00e9 \"{0}\" contaient un noeud d''\u00e9l\u00e9ment \"{1}\" avec un pr\u00e9fixe non li\u00e9 \"{2}\"."}, arrobject26};
    }
}

