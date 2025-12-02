package steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import resources.WorldState;

/**
 * Step definitions per US7 - Adquirir Joc
 */
public class AdquirirJocSteps {
    private final WorldState state;
    public AdquirirJocSteps(WorldState state)
    {
        this.state = state;
    }
    /**
     * Step: L'usuari ha adquirit un joc prèviament
     */
    @Given("l'usuari amb e-mail {string} ha adquirit el joc {string}")
    public void lUsuariAmbEmailHaAdquiritElJoc(String email, String titolJoc) {
        //simular que l'usuari ja ha adquirit el joc
        state.resultat = state.controlador.adquirirJoc(email, titolJoc);
    }

    /**
     * Step: L'usuari adquireix un joc
     */
    @When("l'usuari amb e-mail {string} adquireix el joc {string}")
    public void lUsuariAmbEmailAdquireixElJoc(String email, String titolJoc) {
        //simular que l'usuari adquireix el joc
        state.resultat = state.controlador.adquirirJoc(email, titolJoc);
    }
}