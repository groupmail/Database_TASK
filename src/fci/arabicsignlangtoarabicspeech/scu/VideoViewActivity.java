package fci.arabicsignlangtoarabicspeech.scu;



import fci.arabicsignlangtoarabicspeech.scu.CDM.Connection;
import fci.arabicsignlangtoarabicspeech.scu.CDM.URLs;
import nl.fampennings.keyboard.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends Activity implements OnClickListener {
	String hostAddress = Connection.HOST_ADDRESS + Connection.HOST_PORT;
	String text = "";
	TextView tv;
	VideoView vidView;
	ImageButton replayButton, newSign;

	Typeface arbfont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview);
		tv = (TextView) findViewById(R.id.signText);
		vidView = (VideoView) findViewById(R.id.signVideo);
		replayButton = (ImageButton) findViewById(R.id.replayButton);
		newSign = (ImageButton) findViewById(R.id.newSignButton);
		
		Toast.makeText(this, "This Part Still Under working and it will Convert from Arabic speech to Arabic Sing language", 4).show();
		replayButton.setOnClickListener(this);
		newSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(VideoViewActivity.this,MainActivity.class));

			}
		});

	}

	public void PlayVideoWithText() {
		// Text
		text = getIntent().getStringExtra("text");
		// English font from assets file
		Typeface engfont = Typeface.createFromAsset(this.getAssets(),
				"BuxtonSketch.ttf");

		arbfont = Typeface.createFromAsset(this.getAssets(), "arabtype.ttf");
		tv.setTypeface(engfont);
		tv.setText(text);
		Log.d("gp", text);

		String vidAddress = URLs.MERGE.replace("{Connection.HOST_ADDRESS}",
				Connection.HOST_ADDRESS) + text;
		Uri vidUri = Uri.parse(vidAddress);
		Log.d("video url", vidUri.toString());
		vidView.setVideoURI(vidUri);
		vidView.start();
	}

	// create action bar to enable user to repeat the sign whenever he wants
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem replayitem = menu.add(0, 0, 0, "Replay Sign");
		replayitem.setIcon(R.drawable.ic_action_repeat);
		replayitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			PlayVideoWithText(); // repeat video
		}
		return false;
	}

	@Override
	public void onClick(View arg0) {
		PlayVideoWithText();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// then you use
		String temp = "";
		if (!(temp = prefs.getString("CurrentIp", "")).equalsIgnoreCase("")) {
			Connection.HOST_ADDRESS = temp;
		}
		hostAddress = Connection.HOST_ADDRESS + Connection.HOST_PORT;
		Log.d("ip", Connection.HOST_ADDRESS);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PlayVideoWithText();
	}
}
