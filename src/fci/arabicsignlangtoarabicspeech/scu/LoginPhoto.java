package fci.arabicsignlangtoarabicspeech.scu;



import nl.fampennings.keyboard.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


// this class for login photo using thread....
public class LoginPhoto extends Activity
{
		
	public static int SCREAN_WIDTH;
	public static String userName;
	private Intent intentMover;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1000); // time for login photo per millisecond

                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();

                }
                finally
                {
                	intentMover = new Intent("nl.fampennings.keyboard.MainActivity");
                    startActivity(intentMover);
                    finish(); //for end my login activity;

                }
            }

        };

        timer.start(); // Starting the thread;
        
        

    }
    
    

}
