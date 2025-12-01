package model;

import model.excepcions.IncorrectPasswordException;

import java.time.LocalDate;
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

}
