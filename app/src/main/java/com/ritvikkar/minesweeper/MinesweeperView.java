package com.ritvikkar.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

/**
 * @author Ritvik Kar
 * @version 2.5
 * Minesweeper Android App
 *
 * View class that handels all the UI elements and game play
 */

public class MinesweeperView extends View {

    private Bitmap unclicked;
    private Bitmap flag;
    private Bitmap mine;
    private Bitmap mineClicked;
    private Bitmap clicked;
    private Bitmap one ;
    private Bitmap two ;
    private Bitmap three;
    private Bitmap four;
    private Bitmap five;
    private Bitmap six;
    private Bitmap seven;
    private Bitmap eight;

    HashMap<Integer, Bitmap> numbersHash = new HashMap<Integer, Bitmap>();
    private int fields = MinesweeperModel.getInstance().getNum();

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        unclicked = BitmapFactory.decodeResource(getResources(), R.drawable.tile_unclicked);
        clicked = BitmapFactory.decodeResource(getResources(), R.drawable.tile_clicked);
        flag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        mine = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        mineClicked = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_clicked);
        one = BitmapFactory.decodeResource(getResources(), R.drawable.one);
        two = BitmapFactory.decodeResource(getResources(), R.drawable.two);
        three = BitmapFactory.decodeResource(getResources(), R.drawable.three);
        four = BitmapFactory.decodeResource(getResources(), R.drawable.four);
        five = BitmapFactory.decodeResource(getResources(), R.drawable.five);
        six = BitmapFactory.decodeResource(getResources(), R.drawable.six);
        seven = BitmapFactory.decodeResource(getResources(), R.drawable.seven);
        eight = BitmapFactory.decodeResource(getResources(), R.drawable.eight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        unclicked = Bitmap.createScaledBitmap(unclicked,getWidth()/fields,getHeight()/fields,false);
        flag = Bitmap.createScaledBitmap(flag,getWidth()/fields,getHeight()/fields,false);
        mine = Bitmap.createScaledBitmap(mine,getWidth()/fields,getHeight()/fields,false);
        mineClicked = Bitmap.createScaledBitmap(mineClicked,getWidth()/fields,getHeight()/fields,false);
        clicked = Bitmap.createScaledBitmap(clicked,getWidth()/fields,getHeight()/fields,false);
        one = Bitmap.createScaledBitmap(one,getWidth()/fields,getHeight()/fields,false);
        two = Bitmap.createScaledBitmap(two,getWidth()/fields,getHeight()/fields,false);
        three = Bitmap.createScaledBitmap(three,getWidth()/fields,getHeight()/fields,false);
        four = Bitmap.createScaledBitmap(four,getWidth()/fields,getHeight()/fields,false);
        five = Bitmap.createScaledBitmap(five,getWidth()/fields,getHeight()/fields,false);
        six  = Bitmap.createScaledBitmap(six,getWidth()/fields,getHeight()/fields,false);
        seven = Bitmap.createScaledBitmap(seven,getWidth()/fields,getHeight()/fields,false);
        eight = Bitmap.createScaledBitmap(eight,getWidth()/fields,getHeight()/fields,false);

        numbersHash.put(1,one);
        numbersHash.put(2,two);
        numbersHash.put(3,three);
        numbersHash.put(4,four);
        numbersHash.put(5,five);
        numbersHash.put(6,six);
        numbersHash.put(7,seven);
        numbersHash.put(8,eight);
    }

    @Override
    //onDraw is only called once
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTiles(canvas);
    }

    private void drawTiles(Canvas canvas) {
        Tile[][] tiles = MinesweeperModel.getInstance().getTiles();
        for (int i = 0; i < this.fields; i++) {
            for (int j = 0; j < this.fields; j++) {
                int x = i * getWidth() / fields;
                int y = j * getHeight() / fields;
                if (!tiles[i][j].isChecked()) {
                    if (tiles[i][j].isFlag())
                        canvas.drawBitmap(flag, x, y, null);
                    else
                        canvas.drawBitmap(unclicked, x, y, null);
                } else if (tiles[i][j].isChecked()) {
                    if (tiles[i][j].isMine() && tiles[i][j].isBoom())
                        canvas.drawBitmap(mineClicked, x, y, null);
                    else if (tiles[i][j].isMine())
                        canvas.drawBitmap(mine, x, y, null);
                    else if (tiles[i][j].getHint() != 0)
                        canvas.drawBitmap(numbersHash.get(tiles[i][j].getHint()), x, y, null);
                    else
                        canvas.drawBitmap(clicked, x, y, null);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                MinesweeperModel.getInstance().isGameRunning()) {
            int tX = (int)(event.getX()) / (getWidth() / fields);
            int tY = (int)(event.getY()) / (getHeight() / fields);
            Tile tile = MinesweeperModel.getInstance().getFieldContent(tX,tY);

            if(!tile.isChecked()){
                if (((MainActivity) getContext()).flagMode()) {
                    if(tile.isFlag())
                        tile.setFlag(false);
                    else
                        tile.setFlag(true);
                }
                else{
                    if(!tile.isFlag()){
                        tile.setChecked(true);

                        if(tile.getHint()==0 && !tile.isMine())
                            MinesweeperModel.getInstance().cascade(tile);

                        if(MinesweeperModel.getInstance().gameOver()) {
                            tile.setBoom();
                            MinesweeperModel.getInstance().showMines();
                            ((MainActivity) getContext()).gameOver();
                            MinesweeperModel.getInstance().setGameRunning(false);
                            //MinesweeperModel.getInstance().resetGame();
                        }
                        else if (MinesweeperModel.getInstance().gameWon()){
                            ((MainActivity) getContext()).gameWon();
                        }
                    }
                }
            }
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void clearBoard() {
        MinesweeperModel.getInstance().resetGame();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
