package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore, gameStatus, playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;

//    player1 => 0
//    player2 => 1
//    empty => 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},   // rows
            {0,3,6}, {1,4,7}, {2,5,8},   // columns
            {0,4,8}, {2,4,6}             // diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0; i<buttons.length; i++) {
            String buttonID = "btn"+i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")) {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());  // btn2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));  // 2

        if(activePlayer) {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if(roundCount == 9) {
            Toast.makeText(this, "Oops, Draw!", Toast.LENGTH_SHORT).show();
            playAgain();
        }else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount) {
            gameStatus.setText("Player One is winning!");
        }else if(playerTwoScoreCount > playerOneScoreCount) {
            gameStatus.setText("Player Two is winning!");
        }else{
            gameStatus.setText("");
        }

        if(activePlayer) {
            playerStatus.setText("X Turn");
        }else {
            playerStatus.setText("O Turn");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                gameStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for(int [] winningPosition: winningPositions) {
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain() {
        roundCount=0;
        activePlayer=true;

        for(int i=0; i< buttons.length;  i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}