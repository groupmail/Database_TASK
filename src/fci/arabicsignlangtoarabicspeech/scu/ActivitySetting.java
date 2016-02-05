package fci.arabicsignlangtoarabicspeech.scu;

import nl.fampennings.keyboard.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ActivitySetting extends Activity {

	static CustomKeyboard mCustomKeyboard1;
	static CustomKeyboard mCustomKeyboard2;

	float fontSize;
	int speed;
	RadioGroup radioGroup;

	Editor editoEditor;
	SeekBar mySeekBarForFont, SeekBarSpeechSpeed;

	Button saveButton;
	Button ArabicKeyboard, SignKeyboard;
	private int keyboardType;

	private SharedPreferences myOwnPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_setting);

		
		myOwnPreference = getSharedPreferences("dataOfApp",MODE_PRIVATE);
		
		
		mySeekBarForFont = (SeekBar) findViewById(R.id.seekBar1);
		SeekBarSpeechSpeed = (SeekBar) findViewById(R.id.seekBar2);
		mySeekBarForFont.setProgress((int) myOwnPreference.getFloat("fontSize",100));
		SeekBarSpeechSpeed.setProgress(Integer
				.parseInt(MainActivity.SpeechSpeed) + 10);

		saveButton = (Button) findViewById(R.id.button1);

		ArabicKeyboard = (Button) findViewById(R.id.arabic_button);
		SignKeyboard = (Button) findViewById(R.id.sign_language_button);
		// radioGroup=(RadioGroup) findViewById(R.id.radioGroup1);
		
		keyboardType = myOwnPreference.getInt("keyboardtype",2);
		saveButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{

				// TODO Auto-generated method stub

				
				editoEditor = myOwnPreference.edit();

				editoEditor.putFloat("fontSize", mySeekBarForFont.getProgress());
				editoEditor.putInt("speed", SeekBarSpeechSpeed.getProgress());

				MainActivity.floatFontSize = mySeekBarForFont.getProgress();

				editoEditor.commit();

				MainActivity.ed.setTextSize(mySeekBarForFont.getProgress());
				MainActivity.SpeechSpeed = Integer.toString(myOwnPreference
						.getInt("speed", 10) - 10);

				editoEditor.putInt("keyboardtype", keyboardType);
				editoEditor.commit();
				onBackPressed();

			}
		});

		

		mySeekBarForFont
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
					}
				});

		SeekBarSpeechSpeed
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						// TODO Auto-generated method stub
					}
				});

		
		ArabicKeyboard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.mCustomKeyboard1.getmKeyboardView().setVisibility(View.GONE);
				MainActivity.mCustomKeyboard1.getmKeyboardView().setEnabled(false);
				MainActivity.mCustomKeyboard2.getmKeyboardView().setVisibility(View.GONE);
				MainActivity.mCustomKeyboard2.getmKeyboardView().setEnabled(false);
				MainActivity.scrollOfKeyboard.setVisibility(View.GONE);
				MainActivity.clearButton.setVisibility(View.GONE);
				MainActivity.editTextDimension.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				MainActivity.ed.setLayoutParams(MainActivity.editTextDimension);
				MainActivity.ed.setVisibility(View.VISIBLE);
				//MainActivity.Convert.setVisibility(View.VISIBLE);
				//MainActivity.symbols.setVisibility(View.VISIBLE);
				keyboardType=1;
				MainActivity.ed.setText("");
			}
		});

		SignKeyboard.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v)
			{

				MainActivity.ed.setInputType(InputType.TYPE_NULL);
				keyboardType=2;
				MainActivity.ed.setText("");

			}
		});
		
		
	}

}
