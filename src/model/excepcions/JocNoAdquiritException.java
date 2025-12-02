package model.excepcions;

/**
 * Excepció llançada quan un usuari intenta jugar a un joc que no ha adquirit
 */
public class JocNoAdquiritException extends Exception {
    public JocNoAdquiritException() {
        super("No has adquirit aquest joc");
    }
}