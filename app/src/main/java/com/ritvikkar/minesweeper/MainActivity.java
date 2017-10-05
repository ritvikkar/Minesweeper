package com.ritvikkar.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Ritvik Kar
 * @version 2.5
 * Minesweeper Android App
 */

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    boolean flagState = false;

    /**
     * @param savedInstanceState
     *
     * Called when MainActivity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = findViewById(R.id.layoutContent);
        final MinesweeperView minesweeperView = findViewById(R.id.minesweeperView);
        MinesweeperModel.getInstance().initBoard();

        final ImageView btnFlag = findViewById(R.id.btnFlag);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagState){
                    flagState = false;
                    btnFlag.setImageResource(R.drawable.flag_unclicked);
                }
                else {
                    flagState = true;
                    btnFlag.setImageResource(R.drawable.flag_clicked);
                }
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minesweeperView.clearBoard();
            }
        });

    }

    /**
     * @return flagState
     *
     * Returns the current status of the Flag Mode
     */
    public boolean flagMode() {
        return this.flagState;
    }

    public void gameOver() {
        final MinesweeperView minesweeperView = (MinesweeperView) findViewById(R.id.minesweeperView);
        Snackbar.make(layoutContent,"Game Over!",Snackbar.LENGTH_LONG).setAction("Reset",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        minesweeperView.clearBoard();
                    }
                }).show();
    }

    public void gameWon() {
        final MinesweeperView minesweeperView = (MinesweeperView) findViewById(R.id.minesweeperView);
        Snackbar.make(layoutContent,"Game Won!",Snackbar.LENGTH_LONG).setAction("Reset",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        minesweeperView.clearBoard();
                    }
                }).show();
    }
}
