package model;

import java.util.List;

public class FormattadorLlista {

    /**
     * Formata una llista de strings amb un títol i retorna el text formatat.
     *
     * @param titol El títol de la llista (ex: "Llista de jocs del catàleg:")
     * @param elements La llista d'elements a mostrar
     * @return String formatat amb títol i elements separats per salts de línia
     */
    public static String formatarLlistaAmbTitol(String titol, List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return titol + "\n";
        }

        StringBuilder resultado = new StringBuilder(titol);
        resultado.append("\n");
        resultado.append(String.join("\n", elements));

        return resultado.toString();
    }

    /**
     * Formata una llista genèrica amb títol personalitzat.
     */
    public static String formatarLlista(List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return "";
        }
        return String.join("\n", elements);
    }
}