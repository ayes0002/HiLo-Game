/**
 * {This is a game that randomly chooses a number between 1 and 1000 inclusive and you have 10 tries to complete the task}
 *
 * @Hjalmar {ayes0002@algonquinlive.com}
 */

package com.algonquincollege.ayes0002.guessanumber;

import android.app.Activity;
import android.app.DialogFragment;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MaxNum;
    private static final int MinNum;
    private static final String ABOUT_DIALOG_TAG;

    static {
        MaxNum = 1000;
        MinNum = 1;
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    int randomNum;
    int count = 0;
    EditText userGuessEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button guessB = (Button) findViewById(R.id.guessButton);
        randomNum = createRandomNumber();

        guessB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changes the text for the button according to the amount of tries
                // If the counter has less than 10 tries then it will be a guess button
                // If the counter has more than 10 tries it will be a reset button
                if(guessB.getText().toString().equals("Guess")) {

                    userGuessEditText = (EditText) findViewById(R.id.numberEntry);

                    String userGuessText = userGuessEditText.getText().toString();

                    // Validate that the user guessed a value
                    if (userGuessText.isEmpty()) {
                        userGuessEditText.setError("You need to try to guess the number");
                        userGuessEditText.requestFocus();
                        return;
                    }

                    int userGuess = Integer.parseInt(userGuessText);

                    // Validate number is within range (1-1000)

                    if (userGuess > MaxNum || userGuess < MinNum) {
                        userGuessEditText.setError("Sorry the number you submitted is out of the range (1-1000)");
                        userGuessEditText.requestFocus();
                        return;
                    }

                    // Counter to check the amount of tries
                    count++;


                    // Checks if the users pick matches the random number of if its too high or too low
                    if (userGuess < randomNum) {
                        Toast.makeText(getApplicationContext(), "Too low", Toast.LENGTH_SHORT).show();
                        userGuessEditText.setText("");
                    } else if (userGuess > randomNum) {
                        Toast.makeText(getApplicationContext(), "Too high", Toast.LENGTH_SHORT).show();
                        userGuessEditText.setText("");
                    }

                    // Output when the user wins
                    if (count <= 5 && userGuess == randomNum) {
                        Toast.makeText(getApplicationContext(), "Superior win!", Toast.LENGTH_SHORT).show();
                        userGuessEditText.setText("");
                        ImageView imgView=(ImageView) findViewById(R.id.imageView);
                        imgView.setImageResource(R.drawable.trophy);
                        guessB.setText("Reset");
                        return;
                    } else if (count <= 10 && userGuess == randomNum) {
                        Toast.makeText(getApplicationContext(), "Excellent win!", Toast.LENGTH_SHORT).show();
                        userGuessEditText.setText("");
                        ImageView imgView=(ImageView) findViewById(R.id.imageView);
                        imgView.setImageResource(R.drawable.medal);
                        guessB.setText("Reset");
                        return;
                    } else if (count > 10){
                        guessB.setText("Reset");
                        Toast.makeText(getApplicationContext(), "Please Reset", Toast.LENGTH_SHORT).show();
                        userGuessEditText.setText("");
                        ImageView imgView=(ImageView) findViewById(R.id.imageView);
                        imgView.setImageResource(R.drawable.at_least_you_tried);
                        return;
                    }
                } else if (guessB.getText().toString().equals("Reset")) {
                    randomNum = createRandomNumber();
                    count = 0;
                    guessB.setText("Guess");
                    userGuessEditText.setText("");
                    ImageView imgView=(ImageView) findViewById(R.id.imageView);
                    imgView.setImageResource(R.mipmap.ic_launcher);
                    return;
                }
            }

        });

        guessB.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), randomNum+"", Toast.LENGTH_SHORT).show();
                count = 0;
                randomNum = createRandomNumber();
                userGuessEditText.setText("");
                return false;
            }
        });
    }

    public int createRandomNumber() {
        Random randomNumber = new Random();
        int randomNum = randomNumber.nextInt(MaxNum) + 1;
        Log.v("TAG", "randomNum: "+ randomNum);
        return randomNum;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_action) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
