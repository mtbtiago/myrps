package net.sistemasc.myrps;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private enum betResult {
		TIE, USER, COMPUTER
	}
	
	// work fields
	private Boolean firstTime = true;
	private int betCount = 0;
	private int userWon = 0;
	private int androidWon = 0;
	private betResult lastBetUserWon = betResult.TIE;
	// views
	private Spinner spUserChoice;
	private TextView tvAndroidChoice;
	private TextView tvResult;
	private TextView tvPlayed;
	private TextView tvYouWon;
	private TextView tvAndroidWon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// assign views to code
		spUserChoice = (Spinner) findViewById(R.id.spUserChoice);
		spUserChoice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO http://stackoverflow.com/questions/5335306/how-can-i-get-an-event-in-android-spinner-when-the-current-selected-item-is-sele
				if (firstTime) {
					firstTime = false;
				} else {
					String androidChoice = getComputerChoice();
					tvAndroidChoice.setText("Android choice: "+androidChoice);
					tvResult.setText(calculate(
							parent.getItemAtPosition(position).toString(),
							androidChoice));

					betCount++;
					switch (lastBetUserWon) {
					case USER: 
						tvResult.setTextColor(Color.GREEN);
						userWon++;
						break;
					case COMPUTER: 
						tvResult.setTextColor(Color.RED);
						androidWon++;
						break;
					default:
						tvResult.setTextColor(Color.BLUE);
					}
					doDisplay();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});

		tvAndroidChoice = (TextView) findViewById(R.id.tvAndroidChoice);
		tvResult = (TextView) findViewById(R.id.tvResult);
		tvPlayed = (TextView) findViewById(R.id.tvPlayed);
		tvYouWon = (TextView) findViewById(R.id.tvYouWon);
		tvAndroidWon = (TextView) findViewById(R.id.tvAndroidWon);
	}

	private String calculate(String userChoice, String androidChoice) {
		if (userChoice.equalsIgnoreCase(androidChoice)) {
			lastBetUserWon = betResult.TIE;
			return "The result is a tie!";
		}
		if (userChoice.equalsIgnoreCase("rock")) {
			if (androidChoice.equalsIgnoreCase("scissors")) {
				lastBetUserWon = betResult.USER;
				return "rock wins";
			} else {
				lastBetUserWon = betResult.COMPUTER;
				return "paper wins";
			}
		}
		if (userChoice.equalsIgnoreCase("paper")) {
			if (androidChoice.equalsIgnoreCase("rock")) {
				lastBetUserWon = betResult.USER;
				return "paper wins";
			} else {
				lastBetUserWon = betResult.COMPUTER;
				return "scissors wins";
			}
		}
		if (userChoice.equalsIgnoreCase("scissors")) {
			if (androidChoice.equalsIgnoreCase("paper")) {
				lastBetUserWon = betResult.USER;
				return "scissors wins";
			} else {
				lastBetUserWon = betResult.COMPUTER;
				return "rock wins";
			}
		}
		return "This will never occur";
	};

	private String getComputerChoice() {
		Double result = Math.random();
		if (result < 0.34) {
			return "rock";
		} else if (result <= 0.67) {
			return "paper";
		} else {
			return "scissors";
		}
	}

	private void doDisplay() {
		tvYouWon.setText("You won: " + String.valueOf(userWon));
		tvAndroidWon.setText("Android won: " + String.valueOf(androidWon));
		tvPlayed.setText("Bet count: " + String.valueOf(betCount));
	}

}
