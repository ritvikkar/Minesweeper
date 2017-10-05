package com.ritvikkar.minesweeper;
import java.util.Random;

/**
 * @author Ritvik Kar
 * @version 2.5
 * Minesweeper Android App
 *
 * Model class that sets up the board and all the game logic
 */

public class MinesweeperModel {
    private static MinesweeperModel minesweeperModel = null;

    private MinesweeperModel(){

    }

    private Tile[][] tiles;
    private int FIELDSIZE = 8;

    private int numMines = FIELDSIZE + FIELDSIZE /2;

    private boolean isGameRunning = true;

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public static MinesweeperModel getInstance() {
        if (minesweeperModel == null) {
            minesweeperModel = new MinesweeperModel();
        }
        return minesweeperModel;
    }

    public void initBoard(){
        setGameRunning(true);
        this.tiles = new Tile[FIELDSIZE][FIELDSIZE];

        //setting up the rest of the board
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                tiles[i][j] = new Tile(i,j);
            }
        }

        //setting the mines
        int minesPlaced = 0;

        Random random = new Random();
        while(minesPlaced < numMines) {
            //change to distribution algorithm
            int x = random.nextInt(FIELDSIZE);
            int y = random.nextInt(FIELDSIZE);
            if (!tiles[x][y].isMine()) {
                tiles[x][y].setMine(true);
                minesPlaced++;
            }
        }

        //setting the neighbours and mine hints
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                int hint = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if(i+k >= 0 && i+k<FIELDSIZE && j+l>=0 && j+l<FIELDSIZE && (k!=0 || l!=0)){
                            tiles[i][j].setNeighbour(tiles[i+k][j+l]);
                            if(tiles[i+k][j+l].isMine())
                                hint++;
                        }
                    }
                }
                tiles[i][j].setHint(hint);
            }
        }
    }

    public Tile getFieldContent(int xLoc, int yLoc){
        return this.tiles[xLoc][yLoc];
    }

    public boolean gameOver(){
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                if(tiles[i][j].isChecked() && tiles[i][j].isMine())
                    return true;
            }
        }
        return false;
    }

    public Tile[][] getTiles(){
        return this.tiles;
    }

    public void resetGame(){
        initBoard();
    }

    public int getNum() {
        return FIELDSIZE;
    }

    public void cascade(Tile tile) {
        for (int i = 0; i < tile.getNeighbours().size(); i++) {
            if(tile.getNeighbours().get(i).getHint()==0
                    && (!tile.getNeighbours().get(i).isMine())
                    && (!tile.getNeighbours().get(i).isChecked())){
                tile.getNeighbours().get(i).setChecked(true);
                cascade(tile.getNeighbours().get(i));
            }
            else{
                tile.getNeighbours().get(i).setChecked(true);
            }
        }
    }

    public boolean gameWon() {
        int unchecked = 0;
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                if(!tiles[i][j].isChecked()){
                    unchecked++;
                }
            }
        }
        if(unchecked == numMines) {
            setGameRunning(true);
            return true;
        }
        else
            return false;
    }

    public void showMines() {
        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {
                if(tiles[i][j].isMine()){
                    tiles[i][j].setChecked(true);
                }
            }
        }
    }
}
