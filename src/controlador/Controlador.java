package controlador;

import controlador.interfaces.IDataService;
import model.*;
import model.excepcions.*;
import model.FormattadorLlista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static controlador.MessagesCAT.JocNoDisponibleException;

public class Controlador {
    private final IDataService dataService;
    private CarteraUsuaris carteraUsuaris;
    private CatalegJocs catalegJocs;

    public Controlador(IDataService dataService) {
        this.dataService = dataService;
        this.carteraUsuaris = new CarteraUsuaris();
        this.catalegJocs = new CatalegJocs();
    }

    public void loadDataFromResources() {
        dataService.loadDataInto(carteraUsuaris, catalegJocs);
    }

    public void loadUsuariDataFromResources() {
        dataService.loadUsuarisInto(carteraUsuaris);
    }

    public void loadJocDataFromResources() {
        dataService.loadJocsInto(catalegJocs);
    }

    public String registrarUsuari(
            String email,
            String contrasenya,
            String nomUsuari,
            String dataNaixement
    ) {
        try {
            ValidadorUsuari.validarEmail(email);
            ValidadorUsuari.validarContrasenya(contrasenya);
            ValidadorUsuari.validarNomUsuari(nomUsuari);
            LocalDate dataNaixementProcessada = ValidadorUsuari.validarIProcessarDataNaixement(dataNaixement);

            carteraUsuaris.validarEmailEstaDisponible(email);
            carteraUsuaris.validarNomUsuariEstaDisponible(nomUsuari);

            Usuari nouUsuari = new Usuari(
                    email,
                    contrasenya,
                    nomUsuari,
                    dataNaixementProcessada,
                    LocalDate.now()
            );

            carteraUsuaris.afegirUsuari(nouUsuari);
            return MessagesCAT.SuccessfulUserRegistration.getMessage();
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }

    /**
     * Logueja un usuari amb l'email i la contrasenya proporcionats.
     */
    public String loguejarUsuari(String email, String contrasenya) {
        try {
            // Validar camps buits (NO validar format de contrasenya en login)
            if (email == null || email.isEmpty()) {
                throw new EmptyEmailException();
            }
            if (contrasenya == null || contrasenya.isEmpty()) {
                throw new EmptyPasswordException();
            }

            // CarteraUsuaris cerca l'usuari (Expert en col·lecció d'usuaris)
            Usuari usuari = carteraUsuaris.findByEmail(email);
            if (usuari == null) {
                throw new EmailNotRegisteredException();
            }

            // Usuari valida la seva pròpia contrasenya (Expert en la seva informació)
            usuari.validarContrasenya(contrasenya);

            return MessagesCAT.SuccessfulLogin.getMessage();
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }

    /**
     * Visualitza la llista de jocs del catàleg ordenats per nom.
     */
    public String visualitzarLlistaJocsCataleg() {
        try {
            // CatalegJocs és l'expert en la col·lecció de jocs
            List<Joc> jocs = catalegJocs.getJocsOrdenatsPerNom();

            // Extracció dels títols
            List<String> titols = jocs.stream()
                    .map(Joc::getTitol)
                    .collect(Collectors.toList());

            // FormattadorLlista és l'expert en formatar llistes
            return FormattadorLlista.formatarLlistaAmbTitol(
                    "Llista de jocs del catàleg:",
                    titols
            );
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }

    /**
     * Visualitza la llista de jocs adquirits per un usuari ordenats per nom.
     */
    public String visualitzarLlistaJocsAdquiritsPerUsuari(String email) {
        try {
            // CarteraUsuaris és l'expert en usuaris i les seves adquisicions
            List<Adquisicio> adquisicions = carteraUsuaris.getAdquisicionsDeUsuariOrdenadesPerNom(email);

            // Extracció dels títols
            List<String> titols = adquisicions.stream()
                    .map(Adquisicio::getTitolJoc)
                    .collect(Collectors.toList());

            // FormattadorLlista és l'expert en formatar llistes
            return FormattadorLlista.formatarLlistaAmbTitol(
                    "Llista de jocs adquirits per l'usuari:",
                    titols
            );
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }

    public String veureDetallsJoc(String titol) {
        try {
            // CatalegJocs és l'expert en trobar jocs
            Joc joc = catalegJocs.findByTitol(titol);

            // Joc és l'expert en presentar els seus propis detalls
            return joc.formatarDetalls();
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }

    /**
     * Permet a un usuari adquirir un joc del catàleg.
     */
    public String adquirirJoc(String emailUsuari, String titolJoc) {
        try {
            // CarteraUsuaris cerca l'usuari (Expert en col·lecció d'usuaris)
            Usuari usuari = carteraUsuaris.findByEmail(emailUsuari);
            if (usuari == null) {
                throw new EmailNotRegisteredException();
            }

            // CatalegJocs cerca el joc (Expert en col·lecció de jocs)
            Joc joc = catalegJocs.findByTitol(titolJoc);

            // Joc valida si està disponible (Expert en el seu propi estat)
            if (!joc.estaDisponiblePerAdquirir()) {
                throw new JocNoDisponibleException();
            }

            // Usuari valida si ja té el joc (Expert en les seves adquisicions)
            if (usuari.teJocAdquirit(titolJoc)) {
                throw new JocJaAdquiritException();
            }

            // Usuari crea l'adquisició (Creador: conté les adquisicions)
            usuari.adquirirJoc(joc, LocalDate.now(), 0.0, "EUR");

            return MessagesCAT.SuccessfulJocAdquisition.getMessage();
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }
    /**
     * Permet a un usuari iniciar una sessió de joc per un joc adquirit.
     */
    public String iniciarSessioJoc(String emailUsuari, String titolJoc) {
        try {
            // CarteraUsuaris cerca l'usuari
            Usuari usuari = carteraUsuaris.findByEmail(emailUsuari);
            if (usuari == null) {
                throw new EmailNotRegisteredException();
            }

            // CatalegJocs verifica que el joc existeix
            catalegJocs.findByTitol(titolJoc);

            // Usuari inicia la sessió
            usuari.iniciarSessioJoc(titolJoc, LocalDateTime.now());

            return MessagesCAT.SuccessfulSessioJocStarted.getMessage();
        } catch (Exception e) {
            return MessagesCAT.translate(e);
        }
    }
}