package model;

import model.excepcions.IncorrectPasswordException;
import model.excepcions.JocNoAdquiritException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuari {
    private final String email;
    private final String contrasenya;
    private final String nomUsuari;
    private final LocalDate dataNaixement;
    private final LocalDate dataRegistre;

    private List<Adquisicio> adquisicions;

    public Usuari(
            String email,
            String contrasenya,
            String nomUsuari,
            LocalDate dataNaixement,
            LocalDate dataRegistre
    ) {
        this.email = email;
        this.contrasenya = contrasenya;
        this.nomUsuari = nomUsuari;
        this.dataNaixement = dataNaixement;
        this.dataRegistre = dataRegistre;

        this.adquisicions = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public Object getContrasenya() {
        return contrasenya;
    }
    public String getNomUsuari() {
        return nomUsuari;
    }

    public List<Adquisicio> getAdquisicions() {
        return adquisicions;
    }

    public void addAdquisicio(Adquisicio adquisicio) {
        if (adquisicions == null) {
            adquisicions = new ArrayList<>();
        }
        adquisicions.add(adquisicio);
    }

    public Adquisicio findByTitolJocIDataAdquisicio(String titolJoc, LocalDate dataAdquisicio) {
        if (adquisicions != null) {
            for (Adquisicio adquisicio : adquisicions) {
                if (adquisicio.getJoc().getTitol().equals(titolJoc)
                        && adquisicio.getData().equals(dataAdquisicio)) {
                    return adquisicio;
                }
            }
        }
        return null;
    }

    /**
     * Valida la contrasenya proporcionada amb la contrasenya de l'usuari.
     */
    public void validarContrasenya(String contrasenyaProporcionada) throws Exception {
        if (!this.contrasenya.equals(contrasenyaProporcionada)) {
            throw new IncorrectPasswordException();
        }
    }

    /**
     * Comprova si l'usuari té adquirit un joc amb el títol proporcionat.
     */
    public boolean teJocAdquirit(String titolJoc) {
        if (adquisicions == null || adquisicions.isEmpty()) {
            return false;
        }
        for (Adquisicio adq : adquisicions) {
            if (adq.getTitolJoc().equals(titolJoc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet a l'usuari adquirir un joc.
     */
    public void adquirirJoc(Joc joc, LocalDate dataAdquisicio, Double preu, String moneda) throws Exception {
        Adquisicio novaAdquisicio = Adquisicio.crearAdquisicio(joc, dataAdquisicio, preu, moneda);
        addAdquisicio(novaAdquisicio);
    }

    /**
     * Troba una adquisició per títol de joc.
     */
    public Adquisicio trobarAdquisicioPerId(String titolJoc) {
        if (adquisicions == null || adquisicions.isEmpty()) {
            return null;
        }
        for (Adquisicio adq : adquisicions) {
            if (adq.getTitolJoc().equals(titolJoc)) {
                return adq;
            }
        }
        return null;
    }

    /**
     * Permet a l'usuari iniciar una sessió de joc per un joc adquirit.
     */
    public void iniciarSessioJoc(String titolJoc, LocalDateTime dataInici) throws Exception {
        Adquisicio adquisicio = trobarAdquisicioPerId(titolJoc);
        if (adquisicio == null) {
            throw new JocNoAdquiritException();
        }

        // Creador: Usuari (a través d'Adquisicio) crea SessioJoc
        SessioJoc sessio = SessioJoc.crearSessioJocActiva(this.nomUsuari, adquisicio.getJoc(), dataInici);
        adquisicio.afegirSessioJoc(sessio);
    }


}