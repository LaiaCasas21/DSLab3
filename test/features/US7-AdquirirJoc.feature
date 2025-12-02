Feature: Adquirir joc
  COM A usuari registrat
  VULL adquirir un joc del catàleg
  PER PODER crear la meva col·lecció de jocs i jugar-hi

  # TA7.a
  Scenario Outline: L'usuari adquireix un joc disponible correctament
    Given un usuari s'ha registrat amb l'e-mail "<email>", la contrasenya "password1$", el nom d'usuari "<nomUsuari>" i la data de naixement "01-01-1990"
    And s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "<email>" adquireix el joc "<titolJoc>"
    Then el sistema mostra el missatge "Joc adquirit correctament"
    Examples:
      | email              | nomUsuari | titolJoc                           |
      | user1@gmail.com    | user1     | Elden Ring: Shadow of the Erdtree  |
      | user2@gmail.com    | user2     | Cyberpunk 2077                     |

  # TA7.b
  Scenario: L'usuari intenta adquirir un joc que no existeix al catàleg
    Given un usuari s'ha registrat amb l'e-mail "user@gmail.com", la contrasenya "password1$", el nom d'usuari "user" i la data de naixement "01-01-1990"
    And s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "user@gmail.com" adquireix el joc "Joc Inexistent"
    Then el sistema mostra el missatge "No s'ha trobat cap joc amb aquest títol"

  # TA7.c
  Scenario: L'usuari intenta adquirir un joc que ja ha adquirit prèviament
    Given un usuari s'ha registrat amb l'e-mail "user@gmail.com", la contrasenya "password1$", el nom d'usuari "user" i la data de naixement "01-01-1990"
    And s'ha carregat el catàleg de la base de dades
    And l'usuari amb e-mail "user@gmail.com" ha adquirit el joc "Baldur's Gate 3"
    When l'usuari amb e-mail "user@gmail.com" adquireix el joc "Baldur's Gate 3"
    Then el sistema mostra el missatge "Ja has adquirit aquest joc"

  # TA7.d
  Scenario: L'usuari intenta adquirir un joc retirat
    Given un usuari s'ha registrat amb l'e-mail "user@gmail.com", la contrasenya "password1$", el nom d'usuari "user" i la data de naixement "01-01-1990"
    And s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "user@gmail.com" adquireix el joc "Paragon"
    Then el sistema mostra el missatge "Aquest joc ja no està disponible per adquirir"

  # TA7.e
  Scenario: L'usuari sense registrar intenta adquirir un joc
    Given s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "noregistrat@gmail.com" adquireix el joc "Baldur's Gate 3"
    Then el sistema mostra el missatge "No existeix cap usuari amb aquest e-mail"