package fr.neyuux.pvpkits.refont;

public enum GameState {

    WAITING,
    STARTING,
    PLAYING,
    FINISHED;


    public boolean isState(GameState state) {
        return this.equals(state);
    }

}
