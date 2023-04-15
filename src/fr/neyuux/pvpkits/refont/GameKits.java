package fr.neyuux.pvpkits.refont;

public class GameKits {

    private GameState state = GameState.WAITING;

    private GameConfig config;


    public void createGame() {
        this.config = new GameConfig();

        this.resetGame();
    }

    public void resetGame() {

    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameConfig getConfig() {
        return config;
    }
}
