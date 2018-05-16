package com.example.android.scorekeeperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static android.text.TextUtils.isEmpty;


public class MainActivity extends AppCompatActivity {


    int scorePlayerOne = 0;
    int scorePlayerTwo = 0;
    int tilesRemaining = 86;
    int turn = 0;
    int letterPlace = 0;
    EditText scoreEntry, tilesLeft, doubleLetterEntry, tripleLetterEntry;
    TextView playerOneDisplay, playerTwoDisplay,tilesRemainingView,playerOneName,playerTwoName;
    CheckBox tripleWord, doubleWord, tripleLetter, doubleLetter;

    private final String PLAYER_ONE_SCORE = "scorePlayerOne";
    private final String PLAYER_TWO_SCORE = "scorePlayerTwo";
    private final String TILES_REMAINING = "tilesRemaining";
    private final String TURN = "turn";

    Map<Character, Integer> tileMap = new HashMap<>();
    String lettersCap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String wordPlayed;
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
        scoreEntry.setSelected(false);
        tilesLeft = findViewById(R.id.tiles_left_entry);
        playerOneDisplay = findViewById(R.id.player_one_score);
        playerOneDisplay.setText(String.valueOf(scorePlayerOne));
        playerTwoDisplay = findViewById(R.id.player_two_score);
        playerTwoDisplay.setText(String.valueOf(scorePlayerTwo));
        tilesRemainingView = findViewById(R.id.tile_count);
        tilesRemainingView.setText(String.valueOf(tilesRemaining));
        playerOneName = findViewById(R.id.player_one_name);
        playerTwoName = findViewById(R.id.player_two_name);
        tripleWord = findViewById(R.id.triple_word);
        doubleWord = findViewById(R.id.double_word);
        tripleLetter = findViewById(R.id.triple_letter);
        doubleLetter = findViewById(R.id.double_letter);
        doubleLetterEntry = findViewById(R.id.double_letter_entry);
        tripleLetterEntry = findViewById(R.id.triple_letter_entry);


        createLetterMap();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        //save scores for both players
        savedInstanceState.putInt(PLAYER_ONE_SCORE, scorePlayerOne);
        savedInstanceState.putInt(PLAYER_TWO_SCORE, scorePlayerTwo);
        savedInstanceState.putInt(TILES_REMAINING, tilesRemaining);
        savedInstanceState.putInt(TURN, turn);
        scoreEntry.clearFocus();

    }

    public void updateScore(View view) {
        int currentScore = scoreWord();
        int tilesUsed;
        // get text from user entry and turn into an int
        tilesUsed = Integer.parseInt(tilesLeft.getText().toString());
        //determine player turn and update score and tile views
        if (tilesUsed <= 0 || tilesUsed > 7) {
            Toast.makeText(getApplication().getBaseContext(), R.string.tiles_entry_error, Toast.LENGTH_LONG).show();
        } else {
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

    //method to score currently played word
    private int scoreWord() {

        wordPlayed = scoreEntry.getText().toString().toUpperCase();
       String tiles  =  tilesLeft.getText().toString()  ;
        int currentScore = 0;
        if (TextUtils.isEmpty(wordPlayed)||TextUtils.isEmpty(tiles) ) {
            Toast.makeText(getApplication().getBaseContext(), R.string.word_entry_error, Toast.LENGTH_LONG).show();

        } else {
            for (int i = 0; i < wordPlayed.length(); i++) {
                currentScore += tileMap.get(wordPlayed.charAt(i));
            }

        }
        return checkScoreIncreases(currentScore);
    }
    //check box toggle method
   public void toggleLetterScore(View v){
        if(v.getId()==R.id.double_letter){
            doubleLetter.setVisibility(View.GONE);
            doubleLetterEntry.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.triple_letter){
            tripleLetter.setVisibility(View.GONE);
            tripleLetterEntry.setVisibility(View.VISIBLE);
        }
    }
    // method to check for special score spaces
    private int checkScoreIncreases(int n){
        int score = n;
        if(tripleWord.isChecked()){
            score = n*3;
            tripleWord.toggle();
        }
        if(doubleWord.isChecked()){
            score = n*2;
            doubleWord.toggle();
        }
        if(doubleLetter.isChecked()) {
            letterPlace = Integer.parseInt(doubleLetterEntry.getText().toString());
            if (letterPlace >= 0) {
                if (letterPlace - 1 <= wordPlayed.length()) {
                    score += tileMap.get(wordPlayed.charAt(letterPlace - 1));
                    doubleLetter.toggle();
                }
                doubleLetter.setVisibility(View.VISIBLE);
                doubleLetterEntry.setVisibility(View.GONE);
            }else{
                Toast.makeText(getApplication().getBaseContext(), R.string.error2, Toast.LENGTH_LONG).show();
            }
            }
            if (tripleLetter.isChecked()) {
                letterPlace = Integer.parseInt(tripleLetterEntry.getText().toString());
                if (letterPlace >= 0) {
                    if (letterPlace - 1 <= wordPlayed.length()) {
                        score += tileMap.get(wordPlayed.charAt(letterPlace - 1)) * 2;
                        tripleLetter.toggle();
                    }
                    tripleLetter.setVisibility(View.VISIBLE);
                    tripleLetterEntry.setVisibility(View.GONE);

                }else{
                    Toast.makeText(getApplication().getBaseContext(), R.string.error2, Toast.LENGTH_LONG).show();
                }
            }

        return score;
    }


    //method to create hash map for letter values
    private void createLetterMap() {
        for (int i = 0; i < lettersCap.length(); i++) {
            if (lettersCap.charAt(i) == 'A' || lettersCap.charAt(i) == 'E' || lettersCap.charAt(i) == 'I' || lettersCap.charAt(i) == 'O'
                    || lettersCap.charAt(i) == 'N' || lettersCap.charAt(i) == 'R' || lettersCap.charAt(i) == 'S'
                    || lettersCap.charAt(i) == 'L' || lettersCap.charAt(i) == 'T' || lettersCap.charAt(i) == 'U') {
                tileMap.put(lettersCap.charAt(i), 1);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 1);
            }
            if (lettersCap.charAt(i) == 'D' || lettersCap.charAt(i) == 'G') {
                tileMap.put(lettersCap.charAt(i), 2);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 2);
            }
            if (lettersCap.charAt(i) == 'B' || lettersCap.charAt(i) == 'C' || lettersCap.charAt(i) == 'M' || lettersCap.charAt(i) == 'P') {
                tileMap.put(lettersCap.charAt(i), 3);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 3);
            }
            if (lettersCap.charAt(i) == 'F' || lettersCap.charAt(i) == 'H' || lettersCap.charAt(i) == 'V'
                    || lettersCap.charAt(i) == 'W' || lettersCap.charAt(i) == 'Y') {
                tileMap.put(lettersCap.charAt(i), 4);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 4);
            }
            if (lettersCap.charAt(i) == 'K') {
                tileMap.put(lettersCap.charAt(i), 5);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 5);
            }
            if (lettersCap.charAt(i) == 'J' || lettersCap.charAt(i) == 'X') {
                tileMap.put(lettersCap.charAt(i), 8);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 8);
            }
            if (lettersCap.charAt(i) == 'Q' || lettersCap.charAt(i) == 'Z') {
                tileMap.put(lettersCap.charAt(i), 10);
                tileMap.put(lettersCap.toLowerCase().charAt(i), 10);
            }

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
