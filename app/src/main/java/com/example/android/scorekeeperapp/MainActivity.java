package com.example.android.scorekeeperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    int scorePlayerOne = 0;
    int scorePlayerTwo = 0;
    int tilesRemaining = 86;
    int turn = 0;
    EditText scoreEntry;
    EditText tilesLeft;
    TextView playerOneDisplay;
    TextView playerTwoDisplay;
    TextView tilesRemainingView;
    TextView playerOneName;
    TextView playerTwoName;

    private final String PLAYER_ONE_SCORE = "scorePlayerOne";
    private final String PLAYER_TWO_SCORE = "scorePlayerTwo";
    private final String TILES_REMAINING = "tilesRemaining";
    private final String TURN = "turn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            scorePlayerOne = savedInstanceState.getInt("scorePlayerOne");
            scorePlayerTwo = savedInstanceState.getInt("scorePlayerTwo");
            tilesRemaining = savedInstanceState.getInt("tilesRemaining");
            turn = savedInstanceState.getInt("turn");
        }
        setContentView(R.layout.activity_main);

        scoreEntry = findViewById(R.id.score_entry);
        scoreEntry.clearFocus();
        tilesLeft = findViewById(R.id.tiles_left_entry);
        playerOneDisplay = findViewById(R.id.player_one_score);
        playerOneDisplay.setText(String.valueOf(scorePlayerOne));
        playerTwoDisplay = findViewById(R.id.player_two_score);
        playerTwoDisplay.setText(String.valueOf(scorePlayerTwo));
        tilesRemainingView = findViewById(R.id.tile_count);
        tilesRemainingView.setText(String.valueOf(tilesRemaining));
        playerOneName = findViewById(R.id.player_one_name);
        playerTwoName = findViewById(R.id.player_two_name);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        //save scores for both players
        savedInstanceState.putInt(PLAYER_ONE_SCORE, scorePlayerOne);
        savedInstanceState.putInt(PLAYER_TWO_SCORE, scorePlayerTwo);
        savedInstanceState.putInt(TILES_REMAINING, tilesRemaining);
        savedInstanceState.putInt(TURN, turn);


    }

    public void updateScore(View view) {
        int currentScore;
        int tilesUsed;
        // get text from user entry and turn into an int
        currentScore = Integer.parseInt(scoreEntry.getText().toString());
        tilesUsed = Integer.parseInt(tilesLeft.getText().toString());
        //determine player turn and update score and tile views
        if (turn == 0) {
            scorePlayerOne = scorePlayerOne + currentScore;
            tilesRemaining = tilesRemaining - tilesUsed;
            playerOneDisplay.setText(String.valueOf(scorePlayerOne));
            tilesRemainingView.setText(String.valueOf(tilesRemaining));
            turn = 1;
            //clear edit texts
            scoreEntry.getText().clear();
            tilesLeft.getText().clear();
            tilesLeft.clearFocus();
            //set turn indicator drawable
            playerOneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            playerTwoName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.player_icon, 0, 0, 0);

        } else {
            scorePlayerTwo = scorePlayerTwo + currentScore;
            tilesRemaining = tilesRemaining - tilesUsed;
            playerTwoDisplay.setText(String.valueOf(scorePlayerTwo));
            tilesRemainingView.setText(String.valueOf(tilesRemaining));
            turn = 0;
            scoreEntry.getText().clear();
            tilesLeft.getText().clear();
            tilesLeft.clearFocus();
            //set turn indicator drawable
            playerTwoName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            playerOneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.player_icon, 0, 0, 0);

        }
    }

    //method to pass turn from one player to the other.
    public void skipTurn(View view) {
        if (turn == 0) {
            turn = 1;
            playerOneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            playerTwoName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.player_icon, 0, 0, 0);
        } else {
            turn = 0;
            playerTwoName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            playerOneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.player_icon, 0, 0, 0);
        }
    }

    // reset method to start new game
    public void reset(View view) {
        scorePlayerOne = 0;
        scorePlayerTwo = 0;
        tilesRemaining = 86;
        turn = 0;
        playerOneDisplay.setText(String.valueOf(scorePlayerOne));
        playerTwoDisplay.setText(String.valueOf(scorePlayerTwo));
        tilesRemainingView.setText(String.valueOf(tilesRemaining));
        playerOneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.player_icon, 0, 0, 0);
    }


}
