Feature: Jugar sessió de joc
  COM A usuari registrat
  VULL jugar una sessió d'un joc que he adquirit
  PER PODER registrar el meu temps de joc

  # TA9.a
  Scenario: L'usuari inicia una sessió de joc d'un joc adquirit
    Given un usuari s'ha registrat amb l'e-mail "player@gmail.com", la contrasenya "password1$", el nom d'usuari "player" i la data de naixement "01-01-1995"
    And s'ha carregat el catàleg de la base de dades
    And l'usuari amb e-mail "player@gmail.com" ha adquirit el joc "Baldur's Gate 3"
    When l'usuari amb e-mail "player@gmail.com" inicia una sessió del joc "Baldur's Gate 3"
    Then el sistema mostra el missatge "Sessió de joc iniciada correctament"

  # TA9.b
  Scenario: L'usuari intenta iniciar una sessió d'un joc que no ha adquirit
    Given un usuari s'ha registrat amb l'e-mail "player@gmail.com", la contrasenya "password1$", el nom d'usuari "player" i la data de naixement "01-01-1995"
    And s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "player@gmail.com" inicia una sessió del joc "Cyberpunk 2077"
    Then el sistema mostra el missatge "No has adquirit aquest joc"

  # TA9.c
  Scenario: L'usuari intenta iniciar una sessió d'un joc que no existeix
    Given un usuari s'ha registrat amb l'e-mail "player@gmail.com", la contrasenya "password1$", el nom d'usuari "player" i la data de naixement "01-01-1995"
    When l'usuari amb e-mail "player@gmail.com" inicia una sessió del joc "Joc Inexistent"
    Then el sistema mostra el missatge "No s'ha trobat cap joc amb aquest títol"

  # TA9.d
  Scenario: Usuari no registrat intenta iniciar una sessió
    Given s'ha carregat el catàleg de la base de dades
    When l'usuari amb e-mail "noexiste@gmail.com" inicia una sessió del joc "Baldur's Gate 3"
    Then el sistema mostra el missatge "No existeix cap usuari amb aquest e-mail"