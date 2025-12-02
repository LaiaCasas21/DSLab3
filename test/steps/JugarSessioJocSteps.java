package steps;

import io.cucumber.java.en.When;
import resources.WorldState;

/**
 * Step definitions per US9 - Jugar Sessió de Joc
 */
public class JugarSessioJocSteps {
    private final WorldState state;

    public JugarSessioJocSteps(WorldState state) {this.state = state;}
    /**
     * Step: L'usuari inicia una sessió de joc
     */
    @When("l'usuari amb e-mail {string} inicia una sessió del joc {string}")
    public void lUsuariAmbEmailIniciaSessioDelJoc(String email, String titolJoc) {

        state.resultat = state.controlador.iniciarSessioJoc(email, titolJoc);
    }
}