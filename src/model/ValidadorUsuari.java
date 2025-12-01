package model;

import model.excepcions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ValidadorUsuari {

    /**
     * Valida que un email no sigui buit i tingui un format correcte
     */
    public static void validarEmail(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new EmptyEmailException();
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$")) {
            throw new BadlyFormattedEmailException();
        }
    }

    /**
     * Valida que una contrasenya compleixi tots els requisits de seguretat
     */
    public static void validarContrasenya(String contrasenya) throws Exception {
        if (contrasenya == null || contrasenya.isEmpty()) {
            throw new EmptyPasswordException();
        }
        if (contrasenya.length() < 8) {
            throw new PasswordHasLessThan8CharactersException();
        }
        if (!contrasenya.matches(".*[a-zA-Z].*")) {
            throw new PasswordNeedsAtLeastOneLetterException();
        }
        if (!contrasenya.matches(".*\\d.*")) {
            throw new PasswordNeedsAtLeastOneNumberException();
        }
        if (!contrasenya.matches(".*[^A-Za-z0-9].*")) {
            throw new PasswordNeedsAtLeastOneSymbolException();
        }
    }

    /**
     * Valida que un nom d'usuari no sigui buit
     */
    public static void validarNomUsuari(String nomUsuari) throws Exception {
        if (nomUsuari == null || nomUsuari.isEmpty()) {
            throw new EmptyUsernameException();
        }
    }

    /**
     * Valida i processa una data de naixement en format String a LocalDate
     */
    public static LocalDate validarIProcessarDataNaixement(String dataNaixement) throws Exception {
        if (dataNaixement == null || dataNaixement.isEmpty()) {
            throw new EmptyBirthdateException();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        try {
            return LocalDate.parse(dataNaixement, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidBirthdateFormatException();
        }
    }
}