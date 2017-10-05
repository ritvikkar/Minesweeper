package com.ritvikkar.minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritvik Kar
 * @version 2.5
 * Minesweeper Android App
 *
 * Tile class that creates the Tile object
 */


public class Tile {
    private int x;
    private int y;
    private boolean isChecked;
    private boolean isMine;
    private boolean isFlag;
    private int mineHint;
    private boolean boom;
    private List<Tile> neighbours = new ArrayList<>();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isChecked = false;
        this.isMine = false;
        this.isFlag = false;
        this.mineHint = 0;
        this.boom = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public void setNeighbour(Tile tile) {
        neighbours.add(tile);
    }

    public List<Tile> getNeighbours(){
        return neighbours;
    }

    public void setHint(int mines) {
        this.mineHint = mines;
    }

    public int getHint(){
        return this.mineHint;
    }

    public void setBoom() {
        this.boom = true;
    }

    public boolean isBoom() {
        return this.boom;
    }

}
