package model.excepcions;

/**
 * Excepció llançada quan un usuari intenta adquirir un joc que ja té
 */
public class JocJaAdquiritException extends Exception {
    public JocJaAdquiritException() {
        super("Ja has adquirit aquest joc");
    }
}