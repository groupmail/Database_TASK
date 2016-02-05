package fci.arabicsignlangtoarabicspeech.scu;

import android.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Message 
{
	public static void message(Context context,String message)
	{
		//Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		//toast.setGravity(Gravity.TOP|Gravity.LEFT, 1000, 5000); 
		toast.show();
	
	
	}

	
}
