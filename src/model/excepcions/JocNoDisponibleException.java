package model.excepcions;

/**
 * Excepció llançada quan un usuari intenta adquirir un joc retirat o no disponible
 */
public class JocNoDisponibleException extends Exception {
    public JocNoDisponibleException() {
        super("Aquest joc ja no està disponible per adquirir");
    }
}