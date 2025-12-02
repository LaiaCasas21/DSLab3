package controlador;

public enum MessagesCAT {

    // Exception messages - US1
    EmptyEmailException("L'e-mail no pot ser buit"),
    BadlyFormattedEmailException("L'e-mail no té un format vàlid"),
    EmptyPasswordException("La contrasenya no pot ser buida"),
    PasswordHasLessThan8CharactersException("La contrasenya he de tenir 8 caràcters o més"),
    PasswordNeedsAtLeastOneLetterException("La contrasenya ha de tenir almenys una lletra"),
    PasswordNeedsAtLeastOneNumberException("La contrasenya he de tenir almenys un nombre"),
    PasswordNeedsAtLeastOneSymbolException("La contrasenya he de tenir almenys un símbol"),
    EmptyUsernameException("El nom d'usuari no pot ser buit"),
    EmptyBirthdateException("La data de naixement no pot ser buida"),
    InvalidBirthdateFormatException("La data de naixement ha de tenir el format dd-MM-yyyy"),
    EmailAlreadyRegisteredException("Ja existeix un usuari amb aquest e-mail"),
    UsernameAlreadyRegisteredException("Ja existeix un usuari amb aquest nom d'usuari"),

    // Exception messages - US2
    EmailNotRegisteredException("No existeix cap usuari amb aquest e-mail"),
    IncorrectPasswordException("La contrasenya no és correcta"),

    // Exception messages - US4, US5, US6
    EmptyCatalegException("No hi ha jocs disponibles al catàleg en aquest moment"),
    JocNotFoundException("No s'ha trobat cap joc amb aquest títol"),
    UsuariSenseAdquisicionsException("L'usuari no ha adquirit cap joc"),

    // Exception messages - US7 (Adquirir joc)
    JocJaAdquiritException("Ja has adquirit aquest joc"),
    JocNoDisponibleException("Aquest joc ja no està disponible per adquirir"),

    // Exception messages - US9 (Jugar sessió)
    JocNoAdquiritException("No has adquirit aquest joc"),

    // Success messages
    SuccessfulUserRegistration("Usuari registrat correctament"),
    SuccessfulLogin("Sessió iniciada correctament"),
    SuccessfulJocAdquisition("Joc adquirit correctament"), // US7
    SuccessfulSessioJocStarted("Sessió de joc iniciada correctament"), // US9
    ;

    private final String message;

    MessagesCAT(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static String translate(Exception e) {
        String exceptionName = e.getClass().getSimpleName();
        return valueOf(exceptionName).getMessage();
    }
}