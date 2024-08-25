package Controller;

import MatchInfo.GameMode;
import Model.Match;

public class MenuController {
    
    private Match match;

    // metodo per inizializzare Match a seconda della GameMode scelta
    public void initializeMatch(final GameMode g) {
        this.match = new Match(g);
        //lanciare il pannello di gioco
    }
}
