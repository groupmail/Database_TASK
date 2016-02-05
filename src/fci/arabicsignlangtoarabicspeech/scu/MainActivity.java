package fci.arabicsignlangtoarabicspeech.scu;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import nl.fampennings.keyboard.R;

import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity
{
	
	static ScrollView scrollOfKeyboard;
	
	static int KeyBoardTpye=2 ;
	
	
	static Typeface tf,signTypeFace;
	
	SharedPreferences mySharedPreferenc;
	static float floatFontSize;
	////////related to ispeech
	
	static  String SpeechSpeed;
	
	private ProgressDialog pDialog;
	private static final String TAG = "iSpeech SDK Sample";
	static SpeechSynthesis synthesis;
	Context _context;
	////////for integration with farok
	Button speechButton;
	protected static final int RESULT_SPEECH = 1;
	//////////////////////////
	
    static CustomKeyboard mCustomKeyboard1;
    static CustomKeyboard mCustomKeyboard2;
    
    static CursorAdapter c;
    static EditText ed,ShowSign;
    static LayoutParams editTextDimension;
    static LayoutParams  clearbutton;
    Intent i;
    static Button clearButton,Convert;
    static TextView symbols;
    //RelativeLayout main;
    
   
    public static int keyboardKey;
    Spinner s;
    public static DbHelper feedReaderDbHelper;
   // InsertAll insertt;
    public static String uni;
    static ArrayList<String> sentence = new ArrayList();
    public static StringBuffer buffer = new StringBuffer();
    public static  String word;
    
    //root&match
    public static ArabicStemmer Stemmer=new ArabicStemmer();
    static ArrayList<String> matchedSigns = new ArrayList();
	static ArrayList<Integer> lengthword = new ArrayList();
	static ArrayList<String> words = new ArrayList();
	public static String stemmerFiles;
	public static String wordDB;
	public static String unicode;
    static ArrayList<String> root=new ArrayList();
    public static String ArabicWord;
    public static String uuu;
    
    @Override 
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySharedPreferenc=getSharedPreferences("dataOfApp",Context.MODE_PRIVATE);  
        scrollOfKeyboard=(ScrollView)findViewById(R.id.scroll);        
        
        
        /////////related to ispeech
        prepareTTSEngine();
		synthesis.setStreamType(AudioManager.STREAM_MUSIC);
        ////////////////////////////////
		//KeyBoardTpye= mySharedPreferenc.getInt("keyboardtype",1);  // chech will happen in function
		ed = (EditText) findViewById(R.id.edittext1);

		//text view to display symbols of matched words
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/egyptian_sign_languages.ttf");
		symbols = (TextView) findViewById(R.id.textView1);
		symbols.setTypeface(myTypeface);
		
		
		/////////////////////////////////////////////////////////////////////
	/*	
		ShowSign= (EditText) findViewById(R.id.edittext2);
        signTypeFace = Typeface.createFromAsset(getAssets(),"fonts/egyptian_sign_languages.ttf");
        ShowSign.setTypeface(signTypeFace);

        
      */  
        
        
        
        
        
        

    	ed.setInputType(InputType.TYPE_NULL);
    
        //////////////////////////////////////////////////
        
    	Convert=(Button) findViewById(R.id.button5);    
        
        
        floatFontSize=mySharedPreferenc.getFloat("fontSize",50);
        ed.setTextSize(floatFontSize);
        
        
        SpeechSpeed=Integer.toString(mySharedPreferenc.getInt("speed",10)-10);
        
               
//////////////////////////////        
        mCustomKeyboard1= new CustomKeyboard(this, R.id.keyboardview, R.xml.numbers);
        mCustomKeyboard2= new CustomKeyboard(this, R.id.keyboardview2, R.xml.tabnon1);
        //mCustomKeyboard3= new CustomKeyboard(this, R.id.keyboardview3, R.xml.lil);
        
        Log.d("MinaSamirMain", keyboardKey+"");
        keyboardKey=222222;
        
        feedReaderDbHelper=new DbHelper(this);
       //feedReaderDbHelper.removeAll();
       // feedReaderDbHelper.insert();
        
        //root&match
        Stemmer=new ArabicStemmer();
       // function to store files of suffixes,prefixes,.....
     	storefiles();
        
        
        ////////for integration with farok
        speechButton=(Button) findViewById(R.id.buttonGoogleAPi);
		speechButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				
				Intent intent1 = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

				try {
					startActivityForResult(intent1, RESULT_SPEECH);
				} catch (ActivityNotFoundException a) {
					Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT).show();
					Intent videoIntent = new Intent(getBaseContext(), VideoViewActivity.class);
					//videoIntent.putExtra("text", text.get(0));
					// videoIntent.putExtra("text", "ÓáÇã Úáíßã");

					// Log.d("gp", text.get(0));
					startActivity(videoIntent);
				}

			}
		});
		////////////////////////////////////////
        
        
    }  

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	//	ed=(EditText)findViewById(R.id.edittext1);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_settings:
			
			i=new Intent(this,ActivitySetting.class);
	        startActivity(i); 
			
	        break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

    
    ///////// related to ispeech
    private void prepareTTSEngine() {
		try {
			synthesis = SpeechSynthesis.getInstance(this);
			synthesis.setSpeechSynthesisEvent(new SpeechSynthesisEvent() {

				public void onPlaySuccessful() {
					Log.i(TAG, "onPlaySuccessful");
					if (pDialog.isShowing())
						pDialog.dismiss();
				}

				public void onPlayStopped() {
					Log.i(TAG, "onPlayStopped");
					if (pDialog.isShowing())
						pDialog.dismiss();
				}

				public void onPlayFailed(Exception e) {
					Log.e(TAG, "onPlayFailed");
					if (pDialog.isShowing())
						pDialog.dismiss();
					

					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Error[TTSActivity]: " + e.toString())
					       .setCancelable(false)
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}

				public void onPlayStart() {
					Log.i(TAG, "onPlayStart");
					if (pDialog.isShowing())
						pDialog.dismiss();
				}

				@Override
				public void onPlayCanceled() {
					Log.i(TAG, "onPlayCanceled");
					if (pDialog.isShowing())
						pDialog.dismiss();
				}
				
				
			});


		} catch (InvalidApiKeyException e) {
			Log.e(TAG, "Invalid API key\n" + e.getStackTrace());
			Toast.makeText(_context, "ERROR: Invalid API key", Toast.LENGTH_LONG).show();
		}

	}

    //root&match////////////////////////////////////////////////////////////////
    
    //to store files suuffixes , preffixes , ...
	public void storefiles(){

		//create suffixes and prefixes files in internal storage
		File file=getFilesDir();
	    stemmerFiles= file.toString();
	    
	    //1-definite_article.txt
		String filename1 = "definite_article.txt";
		String content1 = "ال وال بال كال فال";
		FileOutputStream outputStream1;

		try {
		  outputStream1 = openFileOutput(filename1,Context.MODE_PRIVATE);
		  outputStream1.write(content1.getBytes("UTF-16" ));
		  outputStream1.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		//2-diacritics.txt
		String filename2 = "diacritics.txt";
		String content2 = "َ  ً  ُ   ٍ   ْ   ِ   ٌ  ";
		FileOutputStream outputStream2;

		try {
		  outputStream2 = openFileOutput(filename2,Context.MODE_PRIVATE);
		  outputStream2.write(content2.getBytes("UTF-16" ));
		  outputStream2.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		/////////////////////////////////////
		
		//3-duplicate.txt
		String filename3 = "duplicate.txt";
		String content3 = "سد صف سل هم ظل خص ضر جن خط قل حل رد خل عم قر دك فخ هد سر صد خر غل رب طن خم عز مع سب حق ست حظ حد قص مر";
		FileOutputStream outputStream3;

		try {
		  outputStream3 = openFileOutput(filename3,Context.MODE_PRIVATE);
		  outputStream3.write(content3.getBytes("UTF-16" ));
		  outputStream3.close();
			} catch (Exception e) {
				  e.printStackTrace();
			}
		/////////////////////////////////////
		 
	    //4-first_waw.txt
		String filename4 = "first_waw.txt";
		String content4 = "أد أر أل أم بأ بخ بر بش بق بل به تد تر تن تي ثأ ثب" +
				          "ثر ثق ثل ثن جب جد جر جز جس جع جف جق جل جم جن جه حد"+
				          "حش حف حل حم حي خز خط خم خي دج دد در دع دق دك دي ذر"+
				          "رب رث رد رس رش رط رع رف رق رك رل رم رن ري زب زر زز" +
				          "زع زل زن زي سخ سد سط سع سق سل سم سن سي شب شج شح شر" +
				          "شع شق شك شل شم شن شي صب صد صف صل صم صي ضأ ضب ضح ضر" +
				          "ضع ضم طأ طب طد طر طس طش طف طن طي ظب ظف عب عث عد عر" +
				          "عز عس عظ عق عك عل عي غد غر غل غي فد فر فز فض فع فق" +
				          "في قب قت قح قد قذ قر قص قظ قع قف قق قل قن قي كأ كب" +
				          "كد كر كز كس كع كف كل كم كن كي لج لد لس لط لع لغ لف" +
				          "لم له لو لي مأ مد مس مض مق نن ني هب هج هد هر هق هل" +
				          "هم هن هي يب يل"
				          ;
		FileOutputStream outputStream4;

		try {
		  outputStream4 = openFileOutput(filename4,Context.MODE_PRIVATE);
		  outputStream4.write(content4.getBytes("UTF-16" ));
		  outputStream4.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		///////////////////////////
		

	    //5-first_yah.txt
		String filename5 = "first_yah.txt";
		String content5 = "أس بب بس تم ثق حر خت خن رع رق زب سر سن فخ فع قت قظ" +
		"قن مم من نع ود وم";
		FileOutputStream outputStream5;

		try {
		  outputStream5 = openFileOutput(filename5,Context.MODE_PRIVATE);
		  outputStream5.write(content5.getBytes("UTF-16" ));
		  outputStream5.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		//////////////////////////////////

	    //6-last_alif.txt
		String filename6 = "last_alif.txt";
		String content6 = "بر تل عد صف خط قو دن عل دع قص حص سو أخ حي طر أد من ند أز لغ رخ";
		FileOutputStream outputStream6;

		try {
		  outputStream6 = openFileOutput(filename6,Context.MODE_PRIVATE);
		  outputStream6.write(content6.getBytes("UTF-16" ));
		  outputStream6.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		////////////////////////////////
	    //7-last_hamza.txt
		String filename7 = "last_hamza.txt";
		String content7 = "بد بذ بر بس بط بك بو" + "تن" + "جر جز جس جش جف جأ" + "حد حم حن" + "خب خذ خر خس خط" + "در دف دن دو" + "ذر" + "رب رج رد رز رف رق" + "زن" + "سب سل سو سي" + "شط شن شي" + "صب صد" + "ضو" + "طر طف" + "ظم" + "عب" + "غو" + "فت فث فج فر فق في" + "قث قر قم قن قي" + "كف كل كم" + "لب لج لك" + "مر مل من مو" + "نب نت نس نش نك نو ني" + "هج هد هر هز هن هي" + "وب وث وض وط وك وم";
		FileOutputStream outputStream7;

		try {
		  outputStream7 = openFileOutput(filename7,Context.MODE_PRIVATE);
		  outputStream7.write(content7.getBytes("UTF-16" ));
		  outputStream7.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
        
		//////////////////////////////////

	    //8-last_maksoura.txt
		String filename8 = "last_maksoura.txt";
		String content8 = "كر رأ";
		FileOutputStream outputStream8;

		try {
		  outputStream8 = openFileOutput(filename8,Context.MODE_PRIVATE);
		  outputStream8.write(content8.getBytes("UTF-16" ));
		  outputStream8.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //9-last_yah.txt
		String filename9 = "last_yah.txt";
		String content9 = "أب أت أد أذ أز أس أق أل أن أو " + "بد بر بز بط بغ بق بك بل بن به" + "تق تك" + "ثد ثر ثن ثو" + "جب جد جر جز جل جن جه جو" + "حد حذ حر حش حص حظ حف حك حل حم حن حو حي" + "خب خز خش خص خط خف خل خن خو" + "دخ در دع دف دل دم دن ده دو" + "ذر ذك ذم ذو" + "رأ رب رث رج رح رخ رد رز رض رع رق رم رن رو ري" + "زب زر زك زن زو زي" + "سب سج سح سخ سد سر سع سف سق سل سم سو" + "شج شر شظ شف شق شك شه شو" + "صأ صب  صح صد صر صغ صف صل صم" + "ضح ضن ضه ضو" + "طب طخ طر طغ طف طق طل طم طه طو" + "ظب" + "عب عت عث عذ عر عز عس عش عص عط عظ عل عم عن عو عي" + "غب غث غد غر غو  غل غم غن غو غي" + "فت فد فر فص فض فع فل فن" + "قد قذ قر قص قض قع قل قن قه قو قي" + "كب كخ كد كر كس كف كل كم كن كه كو كي" + "لب لح لذ لش لظ لق له لو لي" + "مح مد مر مز مس مش مض مط مع مل من مه" + "نأ  ند نس نع نغ نف نق نك نم نه نو ني" + "هد هذ هر هن هو" + "وت وح  وخ ود ور وز وس وش وص وط وع وغ وف وق وك ول ون وه";
		FileOutputStream outputStream9;

		try {
		  outputStream9 = openFileOutput(filename9,Context.MODE_PRIVATE);
		  outputStream9.write(content9.getBytes("UTF-16" ));
		  outputStream9.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //10-mid_waw.txt
		String filename10 = "mid_waw.txt";
		String content10 = "أب أج أد أر أز أش أض أف أق أل أن أه أي" + "بب بت بح بخ بر بز بس بش بص بغ بق بل" + "تب تج تق ته" + "ثب ثر ثل ثي" + "جب جح جد جر جز جس جط جع جف جق جل جن جو جي" + "حب حت حث حج حد حذ حر حز حس حش حص حض حط حف حق حك حل حم حي" + "خج خخ خذ خر خص خض خف خل خن خو خي" + "دأ دب دح دخ دد در دس دش دط دغ دف دق دك دل دم دن دي" + "ذب ذد ذق ذي" + "رب رث رج رح رد رز رس رض رع رغ رق رل رم ري" + "زج زح زد زر زغ زق زل زم زن زي" + "سأ سج سح سخ سد سر سس سط سع سغ سف سق سك سل سم سي" + "شب شح شر شش شط شظ شف شق شك شل شم شن شه شي" + "صب صت صج صح صخ صر صص صع صغ صف صل صم صن" + "ضأ ضج ضر ضع ضي" + "طب طح طد طر طس طش طع طف طق طل طي" + "عج عد عذ عر عز عص عض عف عق عل عم عن عه عي" + "غأ غث غر غز غش غص غط غغ غل غي" + "فت فج فح فد فر فز فض فط فع فف فق فل فه" + "قب قت قح قد قر قس قش قص قض قط قع قق قل قم قن قه قي" + "كب كخ كد كر كز كس كش كع كف كك كم كن كي" + "لب لث لج لح لذ لز لس لص لط لع لف لق لك لم لن لي" +"مأ مت مج مر مل من مه" +"نأ نب نت نح نخ ند نر نس نش نص نط نع نف نق نل نم نن نه نو ني" + "هب هت هج هد هر هس هش هع هل هم هن هو هي" + "يد يم ";
		FileOutputStream outputStream10;

		try {
		  outputStream10 = openFileOutput(filename10,Context.MODE_PRIVATE);
		  outputStream10.write(content10.getBytes("UTF-16" ));
		  outputStream10.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //11-mid_yah.txt
		String filename11 = "mid_yah.txt";
		String content11 = "أب أد أر أس أض أك أل أم أن" + "بت بد بض بع بن" + "تح تر تس تل تم ته" + "جأ جب جر جش جف جل" + "حث حد حر حز حص حض حط حف حق حك حل حن حو حي" + "خب خر خس خش خط خل خم" + "دث در دس دك دم دن " + "ذع ذل" + "رب رث رح رس رش رض رع رف رق رل رم رن ري" + "زت زح زد زر زز زغ زف زق زل زن زي" + "سأ سب سج سح سخ سد سر سس سف سل" + "شأ شب شت شح شخ شد شط شع شف شق شل شم شن" + "صب صح صد صر صص صع صغ صف صم صن" + "ضر ضع ضف ضق ضم" + "طب طح طر طش طع طف طن" + "عب عث عد عر عس عش عط عف عل عن عه عي" + "غب غث غد غر غض غط غظ غل غم غن غي" + "فأ فح فد فش فض فظ فف فل فن" + "قأ قح قد قر قس قش قض قظ قف قل قم قن قي" + "كد كر كس كف كل كن كي" + "لث لس لف لق لل لم لن لي" + "مت مح مد مر مز مس مط مع مل من" + "نأ نب نح نر نع نف نق نك نل نم ني" + "هأ هب هت هج هر هش هض هط هف هل هم هن" + "وب ول";
		FileOutputStream outputStream11;

		try {
		  outputStream11 = openFileOutput(filename11,Context.MODE_PRIVATE);
		  outputStream11.write(content11.getBytes("UTF-16" ));
		  outputStream11.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
        
		//////////////////////////////////

	    //12-prefixes.txt
		String filename12 = "prefixes.txt";
		String content12 = "لل ل ا و س ب ي ن م ت ف";
		FileOutputStream outputStream12;

		try {
		  outputStream12 = openFileOutput(filename12,Context.MODE_PRIVATE);
		  outputStream12.write(content12.getBytes("UTF-16" ));
		  outputStream12.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //13-punctuation.txt
		String filename13 = "punctuation.txt";
		String content13 = ".   ,   ¡    :    " + "\"" + " '   ÷   ×   º   >   <    |   " + "\\" + "¿   '   !  @   #   $   %   ^   &   *   )   (   _   -   +   =" + ".   ,   :    ;  " + "'" + "   @   #   ~  ø";
		FileOutputStream outputStream13;

		try {
		  outputStream13 = openFileOutput(filename13,Context.MODE_PRIVATE);
		  outputStream13.write(content13.getBytes("UTF-16" ));
		  outputStream13.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //14-quad_roots.txt
		String filename14 = "quad_roots.txt";
		String content14 = "أبجد أبرق أبرل أبزم أبزن أبطر أبلز أبلس أثمد أجند أدرس أرجح أرجل" +
           "أرخن أرطق أرغط أرغن أرمن أرنب أرنك أزمل أسبن أستذ أستك أسطر أسطل" + 
           "أسطن أسفن أسقف أسكل أسور أشبن أطلس أغرق أفرز أفرق أفشن أفغن أفند" +
           "أقلد أقلم أقنم أكسج أكسد ألكل ألمس ألمن أمرك أنبج أنبر أنبق أنجل" +
           "أنجلز أنقلز أنكلز أورب" + "بأبأ بحبح بحتر بحثر بحرن بحلق بختر بخشش بخنق بربخ بربر بربط برثن" +
           "برجز برجس برجل بردخ بردع بردق برذع برزخ برشم برطع برطل برطم برعم" +
           "برغث برغش برغل برقش برقع برمج برنز برنط برنق برهن بروز بستر بستن" +
           "بسمل بشكر بصبص بطبط بطرق بطرك بعبع بعثر بعزق بغثر بغدد بقبق بقرج" +
           "بقشش بكرج بلبط بلبل بلسم بلشف بلعم بلغم بلقع بلقن بلمر بلهن بلور" +
           "بلين بندر بندق بهتر بهدل بهرج بهلل بوخر بوصل بوقل بيدر بيطر" + "تأتأ تختخ تربس ترجم ترفل تعتع تكتك تلفز تلفن تلمذ تمتم تنبر تنبل" + 
           "تهته توأم توبل تونس" + "ثألل ثرثر ثعبن ثعلب ثغثغ ثمني ثندو" + "جأجأ جثلق جحفل جدجد جدول جذمر جربذ جربز جربع جرثم جرجر جردل جركس" +
           "جرمز جرنل جزأر جسطن جصطن جعجع جعدن جلبب جلتن جلجل جلفط جلفن جلمد" +   
            "جلون جمجم جمرك جمهر جندب جندر جندل جنزر جهور جوزل جوسس جوسق جوهر" + "حبحب حذفر حذلق حربش حرشف حرقد حركث حركش حرمل حشرج حصحص حصرم حضرم" +
            "حلحل حلزن حلقم حمحم حمدل حملق حنبل حنجر حنجل حندس حنطر حنظل حوجل" +
            "حوصل حوقل" + "خذرف خربش خربط خربق خرخر خردق خردل خرزن خرشف خرطش خرطل خرطم خرطن " +
           "خرفش خرنق خستك خشخش خضخض خضرم خطرف خلبص خلخل خلقن خنجر خنخن خندق"+
           "خنزب خنزر خنشر خنصر خنفر خنفس خوزق خوشق خيشم" + "دبدب دحدح دحرج دخمس دربس دردر دردش درفل درهم دروش دستر دسكر دعبل" + 
            "دغدغ دفتر دقشم دقمق دكتر دلدل دلفن دلهم دمدم دمشق دمغج دملج دنجل" + 
            "دندش دندن دنكل دهقن دهلز دهور دورق دوزن دولب دولر ديجر دينم" + "ذبذب ذلذل ذولق" + "رأرأ رجرج رحرح رستق رسرس رسمل رشرش رضرض رعرع رغرغ رفرف رقرق رقوص" + 
            "رندح رهبن رهون روبص روشن" + "زبرج زحزح زحلق زحلو زخرف زربل زربن زرزر زركش زرنخ زعبر زعبط زعتر" +
            "زعزع زعفر زعنف زغبر زغرد زغرط زغزغ زغلل زفزف زقزق زلزل زلعم زمجر" + 
           "زمزم زمهر زنبر زنبق زنبل زنجر زندق زنزن زوبع زورق" + "سبحل سبسب سحلب سربل سرجن سرحن سرخس سردب سردر سردك سردن سرمد سرول"+  
           "سعدن سعلي سفتج سفسط سفسف سفلت سفلق سفيت سقرط سقسق سقلب سكسك سلجق" +
           "سلجم سلسل سلطح سلطن سلفت سمحق سمدر سمسر سمكر سمنت سنبق سنبك سنبل" +
          "سنجب سنجق سندل سندن سنطر سنكر سودن سوقر سوكر سيطر" + " شبرق شبشب شحور شخبط شختر شخشخ شخلل شديق شرأب شربك شربن شردق شرذم " + 
           " شرسف شرشر شرشف شرطن شركس شرمط شرنق شرول شعبذ شعشع شعنن شعوذ شفتر " +
           " شفشف شفشق شقذف شقرق شقشق شقلب شلشل شمأز شمخر شمرخ شمشم شنخب شنرق " + 
           " شنشن شنغب شنكر شنكل شنهق شوبق شوبك شورب شيطن شيوع" + "صحرو صرصر صعلك صفصف صقلب صلصل صلطح صمصم صملخ صنبر صنجق صندد صندق" + "صندل صنفر صهرج صهين صوبن صوتم صوحب صوصي صولج صومع صومل صيدل صيرف" + "ضحضح ضرغم ضعضع ضغضغ ضفدع ضمحل" + "طأطأ طأمن طبشر طبطب طبنج طحطح طحلب طربد طربش طرطر طرطس طرطش طرطق " +
          "  طرقع طزلق طصلق طغري طقطق طلمس طلين طمأن طمطم طنبر طنبش طنجر طنطن "+
           " طنفس طيلس" + "ظربن" + "عبرن عبقر عتلت عجرف عجعج عربد عربس عربن عرجن عرزل عرعر عرقب عرقل" + " عرنس عرنن عسبر عسجد عسعس عسقل عسكر عسلج عشتر عشعش عصعص عصفر عطعط " + " عفرت عقبل عقرب عقعق عقلن عكبر عكنن علجم علقم علمن علون عملق عنبر " + "عنتر عنجه عندل عندم عنصر عنصل عنعن عنفص عنقد عنقش عنكب عنون عوسج" + " غربل غرضف غرغر غرنق غضرف غطرس غطرف غلصم غلغل غلفن غلون غلين غمغم "+
           " غملج غندر غنغر غيدق غيطن غيهب" + " فأفأ فتفت فجعن فجفج فخفخ فدفد فذلك فرجن فرزن فرسخ فرشح فرشخ فرشي "+" فرصد فرطح فرعن فرفر فرقد فرقع فركش فركل فرمل فرمن فرنج فرنس فستن " + " فسطط فسطن فسفت فسفر فسفس فسقي فصفت فصفر فضفض فطحل فغفر فلجن فلسف " + " فلطح فلفل فلكن فلور فنجر فنجل فنجن فندق فنطس فهرس فهلو فولذ فيلج "+ " فيلق فيهق" + "قبرس قبرص قبطن قبقب قثتر قثطر قدمس قربس قربن قردب قردح قرسي قرصن " +
           " قرصي قرطس قرطل قرطم قرفص قرفل قرقذ قرقر قرقز قرقس قرقض قرقع قرقف " +
           " قرقل قركز قرمد قرمز قرمش قرمط قزدر قزقز قسطر قسطس قسطل قشتل قشطل " +
           " قشعر قشعم قشقش قشلق قشمش قصدر قصقص قطرن قطقط قطمر قعقع قفطن قفقس " +
           " قفقف قلشن قلعط قلفط قلقب قلقس قلقل قلنس قلوز قلوظ قليب قمبز قمرق " +
           " قمطر قمقم قنبر قنبز قنبل قندز قندس قندق قندل قنزع قنصل قنطر قنفذ " +
           " قنقر قهرم قهقر قهقه قوتب قوقز قوقس قوقع قولب قولح قومس قيتر قيثر " +
           " قيدم قيسر قيصر قيصن قيطن" + " كأكأ كبتل كبرت كبكب كتكت كثلك كحكح كربج كربس كربل كربن كرتن كرخن " + 
           " كردس كردن كرسع كرسف كرسي كرشن كركب كركر كركم كرمش كرمل كرنش كرنف " +
           " كزبر كزكز كسبر كستب كستك كسكس كشتب كشكش كشكل كشمر كشمش كعبر كعبل " +
           " كفكف كفهر كلسط كلكع كلكل كلور كمرك كمسر كندر كنكن كهرب كهرم كوكب " + 
           " كولس"  + "  لألأ لبلب لبنن لجلج لخبط لخلخ لعثم لعلع لغمط لفلف لقلق لملم لهذم "+
           " لهلق لهوج لوذع لولب" + "مأمأ متول مجمج محور مخرق مخطر مخمض مدين مذهب مرجح مرحب مرزب مرطب" + 
           " مركز مرمر مرمس مرمط مزمز مسخر مسطر مسكن مسمر مشور مصمص مضمض مطرن " +
           " معدن معمع معير مغرب مغطس مغنط مقنق مكدم مكرب مكنك ململ ملين مندل" +  
            "منطر منطق مهرج مومي ميدن" + "نبرس نحنح نخرب نخشش نربج نربش نرجس نرجل نردن نرفز نزنز نسطر نسنس"+ 
            "نشنش نضنض نطنط نعنع نغبش نفنف نقنق نمرس نمرق نمنم نهنه نورج نورص "+" نوسر نوفر نولن نيزك نيشن" + "هأهأ هبهب هتمر هدرج هدهد هذرم هرجل هرطق هرطم هرمس هرهر هرول هزبر" +
            "هزهز هفهف هلفت هلقم هلهل هلوس هلين هملج همهم هندب هندز هندس هندم" +"هنشر هنهن هودج هيطل هيكل هيمن هينم" +"وحوح ورنش وسوس وشوش وصوص وطوط وعوع ولدن ولول" + "يربع يسرع يعفر يمني ينبع ";
		FileOutputStream outputStream14;

		try {
		  outputStream14 = openFileOutput(filename14,Context.MODE_PRIVATE);
		  outputStream14.write(content14.getBytes("UTF-16" ));
		  outputStream14.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //15-stopwords.txt
		String filename15 = "stopwords.txt";
		String content15 = "ان بعد ضد  يلي الى في من حتى وهو يكون به وليس أحد على وكان تلك كذلك التي وبين فيها عليها إن وعلى لكن عن مساء ليس منذ الذي أما حين ومن لا ليسب وكانت أي ما عنه حول دون مع لكنه ولكن له هذا والتي فقط ثم هذه أنه تكون قد بين جدا لن نحو كان لهم لأن اليوم لم هؤلاء فإن فيه ذلك لو عند اللذين كل بد لدى وثي أن ومع فقد بل هو عنها منه بها وفي فهو تحت لها أو إذ علي عليه كما كيف هنا وقد كانت لذلك أمام هناك قبل معه يوم منها إلى إذا هل حيث هي اذا او و ما لا الي إلي مازال لازال لايزال مايزال اصبح أصبح أمسى امسى أضحى اضحى ظل مابرح مافتئ ماانفك بات صار ليس إن كأن ليت لعل لاسيما ولايزال الحالي ضمن اول وله ذات اي بدلا اليها انه الذين فانه وان والذي وهذا لهذا الا فكان ستكون مما  أبو بإن الذي اليه يمكن بهذا لدي  وأن  وهي وأبو آل الذي هن الذى ";
		FileOutputStream outputStream15;

		try {
		  outputStream15 = openFileOutput(filename15,Context.MODE_PRIVATE);
		  outputStream15.write(content15.getBytes("UTF-16" ));
		  outputStream15.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //16-strange.txt
		String filename16 = "strange.txt";
		String content16 = "خوجة بلورة وكوفي كوفي تلفزة ديسمبر مانديلا فرنسا شيراك إيران نيلسون الفرنسي متلفزة الأوروبية تحتلها";
		FileOutputStream outputStream16;

		try {
		  outputStream16 = openFileOutput(filename16,Context.MODE_PRIVATE);
		  outputStream16.write(content16.getBytes("UTF-16" ));
		  outputStream16.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //17-suffixes.txt
		String filename17 = "suffixes.txt";
		String content17 = "هما تما كما ان ها وا تم كم تن كن نا تا ما  ون ين هن هم ته تي ني ن ك   ه    ة    ت   ا   ي   ات";
		FileOutputStream outputStream17;

		try {
		  outputStream17 = openFileOutput(filename17,Context.MODE_PRIVATE);
		  outputStream17.write(content17.getBytes("UTF-16" ));
		  outputStream17.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //18-tri_patt.txt
		String filename18 = "tri_patt.txt";
		String content18 = "فعّل فاعل افعل تفعّل تفعل تفاعل انفعل افتعل افعلّ استفعل          تفعيل فعال افعال تفعل تفاعل انفعال افتعال افعلال استفعال         مفعل مفاعل مفعل متفعّل متفعل متفاعل منفعل مفتعل مفعلّ مستفعل             مفعول         فعول مفعال فعّال فعيل       أفعل فعلان فعلاء فعلى           فواعل مفاعيل افاعل فعيّل         يفتعل يستفعل تفتعل فعائل";
		FileOutputStream outputStream18;

		try {
		  outputStream18 = openFileOutput(filename18,Context.MODE_PRIVATE);
		  outputStream18.write(content18.getBytes("UTF-16" ));
		  outputStream18.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}

		//////////////////////////////////

	    //19-tri_roots.txt
		String filename19 = "tri_roots.txt";
		String content19 = " أبب أبد أبر أبض أبط أبق أبل أبن أبه أبو أبي أتم أتن أتو أتي أثث أثر "+
           " أثف أثل أثم أجب أجج أجر أجص أجل أجم أجن أحح أحد أحن أخت أخذ أخر أخو "+
           " أدب أدد أدم أدو أدي أذن أذي أرب أرث أرج أرخ أرس أرض أرف أرق أرك أرم " +
           " أرو أزب أزج أزح أزر أزز أزف أزق أزل أزم أزي أست أسد أسر أسس أسف أسل " +
           " أسن أسو أسي أشب أشر أشف أصد أصر أصص أصل أطر أطط أطم أفف أفق أفك أفل " +
           " أفن أقت أقق أقي أكد أكر أكف أكل أكم ألب ألس ألف ألق ألم أله ألو ألي "+
           " أمر أمس أمع أمل أمم أمن أمو أنب أنث أنس أنف أنق أنن أني أهب أهل أوب "+
           " أوج أود أور أوز أوش أوض أوف أوق أول أون أوه أوي أيب أيد أير أيس أيض "+
           " أيك أيل أيم أين" + 
           "بأر بأز بأس ببج ببر بتت بتر بتع بتك بتل بثث بثر بثق بجح بجد بجر بجس "+
            "بجع بجل بجم بجن بحت بحث بحح بحر بحش بخت بخخ بخر بخس بخع بخق بخل بخو"+ 
           " بدأ بدد بدر بدع بدل بدن بده بدو بدي بذأ بذخ بذذ بذر بذل برأ برب برج "+
            "برح برد برر برز برش برص برض برع برق برك برم بره بري بزر بزز بزغ بزق"+ 
            "بزل بزي بسأ بسر بسس بسط بسق بسل بسم بشر بشش بشع بشك بشم بصر بصص بصق"+ 
            "بصل بصم بضض بضع بطأ بطح بطخ بطر بطش بطط بطق بطل بطم بطن بطي بظر بظظ"+ 
           " بعث بعج بعد بعر بعض بعق بعل بغت بغر بغش بغض بغل بغي بقج بقر بقع بقق "+
           " بقل بقي بكأ بكت بكر بكل بكم بكي بلج بلح بلد بلر بلص بلط بلع بلغ بلف "+
           " بلق بلل بلم بله بلو بلي بنج بند بنن بني بهت بهج بهر بهز بهظ بهق بهل "+
            "بهم بهو بهي بوأ بوب بوت بوح بوخ بور بوز بوس بوش بوص بوغ بوق بول بيت"+ 
           " بيد بيض بيع بين" + 
            "  تأر تبب تبر تبع تبل تبن تجر تحف تخخ تخم ترب ترح ترخ ترس ترع ترف ترك"+ 
            "تره تسع تعب تعس تفح تفف تفك تفل تفه تقن تقو تقي تكك تكي تلد تلع تلف "+
            "تلل تلم تله تلو تمر تمم تنأ تهم توب توج توق توه تيح تير تيس تيل تيم "+
           "تيه"+
            "   ثأب ثأر ثبت ثبج ثبر ثبط ثبق ثبن ثجج ثخن ثدي ثرب ثرد ثرر ثرم ثرو ثري "+
            "ثعب ثعل ثغر ثغم ثغو ثفر ثفل ثفن ثقب ثقف ثقل ثكل ثكن ثلب ثلث ثلج ثلل"+ 
           " ثلم ثمر ثمل ثمم ثمن ثنن ثنو ثني ثوب ثور ثول ثوي "+
             
            "جأر جأش جبب جبح جبذ جبر جبس جبل جبن جبه جبي جثث جثل جثم جثو جحد جحر "+
            "جحش جحظ جحف جحم جخخ جخف جدب جدث جدح جدد جدر جدع جدف جدل جدو جدي جذب "+
           " جذذ جذر جذع جذف جذل جذم جذو جرأ جرب جرح جرد جرذ جرر جرز جرس جرش جرض" +
           " جرع جرف جرم جرن جري جزأ جزد جزر جزز جزع جزف جزل جزم جزي جسأ جسد جسر " +
           " جسس جسم جسو جشأ جشر جشش جشع جشم جصص جعب جعد جعر جعل جفأ جفت جفر جفف"+ 
           " جفل جفن جفو جكر جلب جلح جلخ جلد جلس جلص جلط جلف جلق جلل جلم جلو جلي "+
           " جمح جمخ جمد جمر جمس جمش جمع جمك جمل جمم جنب جنح جند جنز جنس جنف جنن "+
           " جني جهد جهر جهز جهش جهض جهل جهم جهي جوب جوح جود جور جوز جوس جوط جوع"+ 
           "جوف جوق جول جون جوو جوي جيأ جيب جير جيش جيف جيل "+
             
            "حبب حبذ حبر حبس حبش حبط حبق حبك حبل حبن حبو حتت حتد حتر حتف حتك حتم "+
           " حثث حثر حثل حثو حجب حجج حجر حجز حجف حجل حجم حجن حجو حدأ حدب حدث حدج "+
           " حدد حدر حدس حدف حدق حدل حدم حدو حدي حذر حذف حذق حذو حذي حرب حرث حرج "+
           " حرد حرر حرز حرس حرش حرص حرض حرف حرق حرك حرم حرن حرو حري حزب حزر حزز"+ 
            "حزق حزم حزن حسب حسد حسر حسس حسك حسم حسن حسو حشد حشر حشش حشف حشك حشم "+
            "حشو حشي حصب حصد حصر حصص حصف حصل حصن حصو حصي حضر حضض حضن حطب حطط حطم "+
           " حظر حظظ حظو حظي حفد حفر حفز حفظ حفف حفل حفن حفو حفي حقب حقد حقر حقق "+
           " حقل حقن حقو حكر حكك حكم حكي حلب حلج حلس حلف حلق حلك حلل حلم حلو حلي "+
           " حمأ حمد حمر حمز حمس حمش حمص حمض حمط حمق حمل حمم حمو حمي حنأ حنت حنث"+ 
           " حنش حنط حنف حنق حنك حنن حنو حني حوب حوت حوث حوج حود حوذ حور حوز حوس "+
           " حوش حوص حوض حوط حوف حوق حوك حول حوم حوي حيث حيد حير حيز حيص حيض حيط"+ 
           " حيف حيق حيك حيل حين حيو حيي "+
             
           " خبأ خبب خبت خبث خبر خبز خبص خبط خبع خبل خبو خبي ختر ختل ختم ختن خثر "+
            "خجل خدج خدد خدر خدش خدع خدل خدم خدن خذأ خذف خذل خذو خرأ خرب خرت خرج"+ 
            "خرد خرر خرز خرس خرص خرط خرع خرف خرق خرم خزر خزز خزع خزف خزق خزل خزم "+
            "خزن خزي خسأ خسر خسس خسف خشب خشت خشر خشش خشع خشف خشم خشن خشي خصب خصر "+
           " خصص خصف خصل خصم خصي خضب خضد خضر خضض خضع خضل خضم خطأ خطب خطر خطط خطف "+
            "خطل خطم خطو خطي خفت خفر خفس خفش خفض خفف خفق خفي خقن خلب خلج خلد خلس "+
           " خلص خلط خلع خلف خلق خلك خلل خلو خلي خمج خمد خمر خمس خمش خمص خمع خمل "+
           " خمم خمن خنث خنس خنص خنع خنف خنق خنن خنو خني خوج خوخ خوذ خور خوص خوض" +
           " خوف خول خون خوو خوي خيب خير خيس خيش خيط خيل خيم "+
             
           " دأب دبب دبج دبر دبس دبش دبغ دبق دبك دبل دثر دجج دجر دجل دجن دجو دحر "+
            "دحس دحش دحض دحل دحو دخس دخل دخن دخي درأ درب درج درد درر درز درس درع"+ 
            "درف درق درك درم درن دره درو دري دست دسر دسس دسم دسو دشت دشر دشش دشن "+
           " دشو دعب دعج دعر دعس دعع دعك دعم دعو دعي دغر دغش دغص دغل دغم دفأ دفر "+
            "دفس دفع دفف دفق دفل دفن دفي دقر دقع دقق دقل دكك دكن دلب دلج دلح دلس "+
            "دلع دلف دلق دلك دلل دله دلو دلي دمث دمج دمر دمس دمع دمغ دمك دمل دمم "+
           " دمن دمو دمي دنأ دنر دنس دنف دنق دنن دنو دني دهر دهس دهش دهق دهك دهم "+
           " دهن دهي دوأ دوب دوح دوخ دود دور دوس دوش دوط دوغ دوف دوق دوك دول دوم"+ 
           " دون دوي ديث دير ديس ديك ديم دين"+ 
             
           " ذأب ذبب ذبح ذبل ذحل ذخر ذرأ ذرب ذرح ذرر ذرع ذرف ذرق ذرو ذري ذعر ذعف "+
            "ذعق ذعن ذفر ذقن ذكر ذكو ذكي ذلف ذلق ذلل ذمر ذمم ذمي ذنب ذهب ذهل ذهن "+
            "ذوب ذود ذوق ذوي ذيع ذيل " +"رأب رأد رأس رأف رأم رأي ربأ ربب ربت ربح ربد ربص ربض ربط ربع ربغ ربق "+
           " ربك ربل ربن ربو ربي رتب رتت رتج رتع رتق رتك رتل رتم رتن رتو رثث رثو"+ 
            "رثي رجأ رجب رجج رجح رجز رجس رجع رجف رجل رجم رجن رجو رجي رحب رحض رحق "+
            "رحل رحم رحو رحي رخخ رخص رخم رخو رخي ردأ ردح ردد ردس ردع ردغ ردف ردم "+
           " ردن رده ردي رذذ رذل رزأ رزب رزح رزز رزغ رزق رزم رزن رزي رسب رسح رسخ"+ 
            "رسغ رسف رسل رسم رسن رسو رشح رشد رشش رشف رشق رشم رشن رشو رصد رصص رصع "+
            "رصف رصن رضب رضخ رضض رضع رضم رضو رضي رطب رطل رطم رطن رعب رعد رعش رعص" +
           " رعع رعف رعل رعم رعن رعو رعي رغب رغث رغد رغف رغم رغو رفأ رفت رفث رفح "+
            "رفد رفس رفش رفص رفض رفع رفف رفق رفل رفه رفو رقأ رقب رقد رقش رقص رقط"+ 
           " رقع رقق رقم رقن رقي ركب ركد ركز ركس ركض ركع ركك ركل ركم ركن ركو رمث "+
            "رمج رمح رمد رمز رمس رمش رمص رمض رمق رمك رمل رمم رمن رمي رنح رنخ رنق"+ 
           " رنم رنن رنو رني رهب رهج رهص رهط رهف رهق رهل رهم رهن رهو روب روث روج "+
           " روح رود روز روض روع روغ روق رول روم روي ريب ريث ريح ريس ريش ريض"+ 
            "ريع ريف ريق ريل ريم رين ريي "+
             
            "زأر زأط زأق زأم زأن زبب زبد زبر زبط زبق زبل زبن زبي زجج زجر زجل زجو "+
            "زحر زحف زحل زحم زخخ زخر زخم زرب زرد زرر زرع زرف زرق زري زعج زعر زعط "+
           " زعف زعق زعل زعم زغب زغد زغر زغط زغل زفت زفر زفف زفن زقق زقل زقم زقو "+
           " زكب زكر زكم زكن زكو زكي زلج زلط زلع زلف زلق زلل زلم زمت زمر زمط زمع "+
           " زمل زمم زمن زنأ زنج زنخ زند زنر زنق زنن زني زهد زهر زهف زهق زهم زهو"+ 
           " زوج زوح زود زور زوغ زوق زول زوم زون زوي زيت زيح زيد زير زيز زيغ زيف "+
           " زيق زيل زين زيي" +"سأر سأل سأم سبأ سبب سبت سبح سبخ سبر سبس سبط سبع سبغ سبق سبك سبل سبه "+
            "سبي ستت ستر ستف سته سجح سجد سجر سجس سجع سجف سجق سجل سجم سجن سجو سجي "+
            "سحب سحت سحج سحح سحر سحف سحق سحل سحم سحن سحي سخد سخر سخط سخف سخل سخم "+
           " سخن سخو سخي سدب سدد سدر سدس سدف سدل سدم سدن سدي سذب سذج سرب سرج سرح "+
           " سرخ سرد سرر سرط سرع سرف سرق سرم سرو سري سطب سطح سطر سطع سطل سطو سعد "+
           " سعر سعط سعف سعل سعن سعي سغب سفح سفد سفر سفط سفع سفف سفق سفك سفل سفن "+
           " سفه سفي سقر سقط سقع سقف سقل سقم سقو سقي سكب سكت سكر سكع سكف سكك سكن "+
           " سلأ سلب سلت سلح سلخ سلس سلط سلع سلف سلق سلك سلل سلم سلو سلي سمت سمج "+
           " سمح سمد سمر سمط سمع سمق سمك سمل سمم سمن سمو سمي سنج سنح سنخ سند سنر "+
           " سنط سنم سنن سنه سنو سهب سهد سهر سهف سهل سهم سهو سوأ سوج سوح سوخ سود "+
            "سور سوس سوط سوع سوغ سوف سوق سوك سول سوم سوي سيأ سيب سيج سيح سيخ سيد "+
            "سير سيس سيف سيل "+
             
            "شأب شأم شأن شأو شبب شبث شبح شبر شبط شبع شبق شبك شبل شبن شبه شبو شتت "+
            "شتر شتل شتم شتو شجب شجج شجر شجع شجن شجو شجي شحب شحت شحح شحذ شحر شحط "+
            "شحم شحن شخب شخخ شخر شخص شخط شخم شدخ شدد شدر شدف شدق شدن شده شدو شذب "+
            "شذذ شذر شذو شرب شرج شرح شرخ شرد شرر شرس شرش شرط شرع شرف شرق شرك شرم "+
           " شره شرو شري شزر شسع شصر شصص شطأ شطب شطح شطر شطط شطف شطن شظف شظي شعب "+
            "شعث شعر شعط شعع شعف شعل شعن شعو شغب شغر شغف شغل شفت شفر شفط شفع شفف "+
            "شفق شفن شفه شفو شفي شقح شقر شقف شقق شقل شقو شقي شكد شكر شكس شكك شكل "+
            "شكم شكه شكو شكي شلب شلت شلح شلف شلق شلل شلو شمت شمخ شمر شمس شمط شمع "+
           " شمل شمم شنأ شنب شنج شنخ شنر شنط شنع شنف شنق شنن شهب شهد شهر شهق شهل"+ 
           " شهم شهن شهو شهي شوب شوح شور شوش شوط شوظ شوف شوق شوك شول شوم شون شوه "+
           " شوي شيأ شيب شيت شيح شيخ شيد شيط شيع شيف شيق شيل شيم شين "+
             
            "صأب صأي صبأ صبب صبح صبر صبع صبغ صبن صبو صبي صتم صحب صحح صحر صحف صحل "+
            "صحن صحو صحي صخب صخر صدأ صدح صدد صدر صدع صدغ صدف صدق صدم صدي صرب صرح "+
            "صرخ صرد صرر صرط صرع صرف صرم صري صطب صطل صعب صعد صعر صعق صعل صغر صغو "+
           " صغي صفح صفد صفر صفع صفف صفق صفن صفو صفي صقب صقر صقع صقل صكك صلب صلت "+
           " صلج صلح صلخ صلد صلص صلع صلف صلل صلن صلو صلي صمت صمخ صمد صمغ صمل صمم "+
           " صمي صنج صنر صنع صنف صنم صنن صنو صهب صهد صهر صهل صهو صوب صوت صوج صوح "+
           " صوخ صور صوص صوع صوغ صوف صول صوم صون صيب صيح صيد صير صيص صيع صيغ صيف "+
           " صيم صين "+
             
           " ضأل ضأن ضبب ضبح ضبر ضبس ضبط ضبع ضبن ضجج ضجر ضجع ضحك ضحل ضحو ضحي ضخخ "+
           " ضخم ضدد ضرب ضرج ضرح ضرر ضرس ضرط ضرع ضرم ضرو ضعف ضغث ضغط ضغن ضفر ضفف "+
           " ضفو ضلع ضلل ضمج ضمخ ضمد ضمر ضمم ضمن ضنك ضنن ضني ضهد ضهر ضهل ضهي ضوأ"+ 
           " ضوج ضور ضوع ضوي ضير ضيع ضيف ضيق ضيم "+
             
            "طأس طبب طبخ طبر طبع طبق طبل طبن طبي طجن طحر طحل طحن طخر طخي طرأ طرب "+
           " طرح طرد طرر طرز طرس طرش طرف طرق طرم طرو طري طزج طزن طست طشت طعم طعن "+
           " طغت طغر طغم طغو طغي طفأ طفح طفر طفش طفف طفق طفل طفو طفي طقس طقق طقم "+
           " طقي طلب طلح طلس طلع طلق طلل طلم طلو طلي طمث طمح طمر طمس طمع طمم طمن "+
           " طمو طمي طنب طنج طنف طنن طهر طهق طهم طهو طهي طوب طوح طود طور طوس طوش "+
           " طوع طوف طوق طول طوي طيب طيح طير طيش طيع طيف طين "+
             
           " ظبي ظرر ظرف ظعن ظفر ظلع ظلف ظلل ظلم ظمأ ظنب ظنن ظهر "+
             
            "عبأ عبب عبث عبد عبر عبس عبط عبق عبك عبل عبو عبي عتب عتد عتر عتق عتك "+
           " عتل عتم عته عتو عتي عثث عثر عثم عثن عثو عثي عجب عجج عجر عجز عجف عجل "+
            "عجم عجن عجو عدد عدس عدل عدم عدن عدو عذب عذر عذق عذل عذو عذي عرب عرج "+
           " عرد عرر عرس عرش عرص عرض عرف عرق عرك عرم عرن عرو عري عزب عزر عزز عزف"+ 
           " عزق عزل عزم عزو عزي عسب عسر عسس عسف عسل عسو عسي عشب عشر عشش عشق عشم "+
           " عشو عشي عصب عصد عصر عصص عصف عصل عصم عصو عصي عضب عضد عضض عضل عضه عضو"+ 
           " عطب عطر عطس عطش عطف عطل عطن عطي عظل عظم عظي عفر عفس عفش عفص عفف عفن "+
           " عفو عقب عقد عقر عقص عقف عقق عقل عقم عكد عكر عكز عكس عكش عكف عكك عكم "+
           " علب علج علف علق علك علل علم علن علو علي عمد عمر عمش عمص عمق عمل عمم "+
           " عمن عمه عمي عنب عنت عنج عند عنز عنس عنف عنق عنن عنو عني عهد عهر عهل "+
           " عهن عوج عود عوذ عور عوز عوص عوض عوف عوق عول عوم عون عوه عوي عيب عيث "+
           " عيد عير عيس عيش عيط عيف عيل عين عيه عيي "+
             
           " غبب غبر غبس غبش غبط غبن غبو غبي غتت غثث غثي غجر غدد غدر غدف غدق غدن "+
           " غدو غدي غذذ غذو غرب غرد غرر غرز غرس غرش غرض غرف غرق غرل غرم غرن غرو "+
           " غري غزر غزز غزل غزو غسس غسق غسل غسن غشش غشم غشو غشي غصب غصص غصن غضب "+
           " غضر غضض غضن غضو غطر غطس غطش غطط غطو غفر غفف غفق غفل غفو غفي غقق غلب "+
           " غلس غلط غلظ غلف غلق غلل غلم غلو غلي غمد غمر غمز غمس غمش غمص غمض غمط "+
           " غمق غمم غمي غنج غنص غنم غنن غني غوأ غوث غور غوز غوش غوص غوط غوغ غول "+
           " غوي غيب غيث غيد غير غيض غيط غيظ غيل غيم غين غيي "+
             
           " فأت فأد فأر فأس فأل فتأ فتت فتح فتخ فتر فتش فتق فتك فتل فتن فتو فتي "+
           " فثأ فجأ فجج فجر فجع فجل فجو فحح فحش فحص فحل فحم فحو فخت فخخ فخذ فخر "+
           " فخم فدح فدخ فدر فدم فدن فدي فذذ فرأ فرج فرح فرخ فرد فرر فرز فرس فرش "+
           " فرص فرض فرط فرع فرغ فرق فرك فرم فرن فره فرو فري فزر فزز فزع فسح فسخ "+
           " فسد فسر فسق فسل فسو فشخ فشر فشش فشك فشل فشو فصح فصد فصص فصل فصم فصي "+
           " فضح فضض فضل فضو فضي فطح فطر فطس فطم فطن فظظ فظع فعل فعم فعو فعي فغر "+
           " فغم فغو فقأ فقح فقد فقر فقس فقش فقص فقط فقع فقم فقه فكر فكش فكك فكن "+
           " فكه فلت فلج فلح فلذ فلز فلس فلط فلع فلق فلك فلل فلم فلن فلو فلي فمم "+
           " فنخ فند فنر فنس فنط فنق فنك فنن فني فهد فهق فهم فهه فوت فوج فوح فود "+
           " فور فوز فوض فوط فوع فوف فوق فول فوه فيأ فيح فيد فيش فيض فيظ فيف فيل "+
           " فين "+
             
           " قأد قبب قبج قبح قبر قبس قبص قبض قبط قبع قبل قبن قبو قتب قتت قتد قتر "+
           " قتل قتم قثأ قثث قحب قحح قحط قحف قحل قحم قحو قدح قدد قدر قدس قدم قدو "+
           " قدي قذذ قذر قذع قذف قذل قذي قرأ قرب قرت قرج قرح قرد قرر قرس قرش قرص "+
           " قرض قرط قرظ قرع قرف قرق قرم قرن قرو قري قزح قزز قزع قزل قزم قزن قسح "+
           " قسر قسس قسط قسم قسو قشب قشد قشر قشش قشط قشع قشف قشل قصب قصج قصد قصر "+
           " قصص قصع قصف قصل قصم قصو قصي قضب قضض قضع قضف قضم قضو قضي قطب قطر قطط "+
           " قطع قطف قطل قطم قطن قطو قعد قعر قعس قعي قفر قفز قفش قفص قفع قفف قفل "+
           " قفو قلب قلح قلد قلس قلش قلص قلط قلع قلف قلق قلل قلم قلو قلي قمأ قمح "+
           " قمر قمز قمس قمش قمص قمط قمع قمل قمم قمن قنأ قنب قنت قنج قند قنص قنط "+
           " قنع قنل قنم قنن قنو قني قهر قهو قهي قوب قوت قوح قود قور قوس قوش قوص "+
           " قوض قوط قوع قوق قول قوم قون قوه قوي قيأ قيح قيد قير قيس قيش قيض قيظ "+
           " قيف قيل قيم قين قيي"+"  كأب كأد كأس كبب كبت كبح كبد كبر كبس كبش كبل كبن كبو كبي كتب كتت كتع "+
           " كتف كتل كتم كتن كثب كثث كثر كثف كحت كحح كحل كخي كدح كدد كدر كدس كدش "+
           " كدم كدي كذب كرب كرث كرج كرح كرد كرر كرز كرس كرش كرط كرع كرك كرم كره "+
           " كرو كري كزز كسب كسح كسد كسر كسع كسف كسل كسم كسو كسي كشح كشر كشش كشط "+
           " كشف كشك كضض كظر كظظ كظم كعب كعك كعم كغد كغذ كغط كفأ كفت كفح كفخ كفر "+
           " كفس كفف كفل كفن كفي كلأ كلب كلت كلح كلد كلس كلف كلك كلل كلم كلن كلو "+
           " كلي كمأ كمت كمح كمخ كمد كمر كمش كمع كمل كمم كمن كمه كمي كنب كند كنر "+
           " كنز كنس كنش كنع كنف كنن كنه كنو كني كهف كهل كهم كهن كهي كوب كوخ كود "+
           " كور كوز كوس كوش كوع كوف كوك كوم كون كوي كيد كير كيس كيف كيل كين كيي "+
             
           " لأك لأم لبأ لبب لبث لبخ لبد لبس لبط لبق لبك لبن لبو لبي لتت لثغ لثم "+
           " لثو لجأ لجب لجج لجم لجن لحب لحج لحح لحد لحس لحظ لحف لحق لحم لحن لحو "+
           " لحي لخص لخم لخن لدد لدغ لدن لذذ لذع لذق لذي لزب لزج لزز لزق لزم لسع "+
           " لسن لشي لصص لصق لضم لطخ لطس لطش لطع لطف لطم لظي لعب لعج لعس لعق لعن "+
           " لغب لغد لغز لغط لغم لغو لفت لفح لفظ لفع لفف لفق لفو لقب لقح لقس لقط "+
           " لقع لقف لقم لقن لقو لقي لكأ لكز لكع لكك لكم لكن لمج لمح لمز لمس لمص "+
           " لمظ لمع لمم لهب لهث لهج لهد لهط لهف لهق لهم لهو لهي لوب لوث لوج لوح "+
           " لوذ لوز لوس لوص لوط لوع لوف لوق لوك لوم لون لوي ليث ليس ليف ليق ليل "+
           " ليم لين ليي "+
             
           " مأق مأن متت متح متر متع متك متن مثث مثل مثن مجج مجد مجر مجس مجل مجن "+
           " محح محص محض محق محك محل محن محو محي مخخ مخر مخض مخط مخل مدح مدد مدر"+ 
           " مدن مدو مدي مذر مذق مذل مرأ مرث مرج مرح مرخ مرد مرر مرس مرش مرض مرط "+
           " مرع مرغ مرق مرن مرو مري مزج مزح مزر مزز مزع مزق مزن مزي مسح مسخ مسد "+
           " مسر مسس مسك مسو مسي مشج مشح مشش مشط مشق مشك مشو مشي مصر مصص مصل مضر "+
           " مضض مضغ مضي مطر مطط مطق مطل مطو مطي معج معد معر معز معس معض معط معك "+
           " معن معو معي مغث مغر مغص مغط مغل مغن مقت مقع مقل مكث مكر مكس مكك مكن "+
           " ملأ ملج ملح ملخ ملد ملس ملص ملط ملق ملك ملل ملم ملو ملي منأ منح منع "+
           " منن منو مني مهج مهد مهر مهق مهك مهل مهن مهو مهي موأ موت موج مور مول "+
           " مون موه ميت ميح ميد مير ميز ميس ميط ميع ميل مين "+
             
           " نأم نأي نبأ نبب نبت نبج نبح نبذ نبر نبز نبس نبش نبض نبط نبع نبغ نبق "+
           " نبك نبل نبه نبو نتأ نتج نتح نتر نتش نتع نتف نتن نثر نجب نجح نجد نجذ"+ 
           " نجر نجز نجس نجش نجع نجف نجل نجم نجو نحب نحت نحر نحز نحس نحف نحل نحم "+
           " نحو نخب نخخ نخر نخز نخس نخع نخل نخم نخو ندب ندح ندد ندر ندس ندف ندل "+
           " ندم نده ندو ندي نذر نذل نرد نزح نزر نزز نزع نزغ نزف نزق نزك نزل نزه "+
           " نزو نسأ نسب نسج نسخ نسر نسغ نسف نسق نسك نسل نسم نسو نسي نشأ نشب نشج "+
           " نشد نشر نشز نشش نشط نشع نشف نشق نشل نشن نشو نصب نصت نصح نصر نصص نصع "+
           " نصف نصل نصم نصو نضب نضج نضح نضد نضر نضض نضف نضل نضو نطح نطر نطس نطط "+
           " نطع نطف نطق نطل نظر نظف نظل نظم نعب نعت نعج نعر نعس نعش نعظ نعق نعل "+
           " نعم نعي نغب نغز نغش نغص نغل نغم نغو نغي نفث نفج نفح نفخ نفد نفذ نفر "+
           " نفس نفش نفض نفط نفع نفف نفق نفل نفو نفي نقب نقح نقد نقذ نقر نقز نقس "+
           " نقش نقص نقض نقط نقع نقف نقق نقل نقم نقه نقو نقي نكأ نكب نكت نكث نكح "+
           " نكد نكر نكز نكس نكش نكص نكف نكل نكه نكي نمر نمس نمش نمط نمق نمل نمم "+
           " نمو نمي نهب نهج نهد نهر نهز نهش نهض نهق نهك نهل نهم نهو نهي نوأ نوب "+
           " نوت نوح نوخ نود نور نوس نوش نوص نوط نوع نوف نوق نول نوم نون نوه نوو "+
           " نوي نيأ نيب نيح نير نيع نيف نيق نيك نيل نيم نيي "+
             
           " هأل هبب هبت هبر هبش هبط هبل هبو هتر هتف هتك هتم هتن هجأ هجج هجد هجر "+
           " هجس هجص هجع هجل هجم هجن هجو هدأ هدب هدج هدد هدر هدف هدل هدم هدن هدي "+
           " هذب هذر هذل هذي هرأ هرب هرج هرر هرس هرش هرع هرف هرق هرم هرو هري هزأ "+
           " هزج هزر هزز هزع هزل هزم هسس هشش هشم هصر هصص هضب هضض هضم هطع هطل هفت "+
           " هفف هفو هكر هكع هكم هلب هلس هلع هلك هلل هلم هلن همج همد همر همز همس "+
           " همش همع همك همل همم همو هنأ هند هنف هنم هنن هنه هنو هني هوب هوت هوج "+
           " هود هور هوس هوش هوع هول هوم هون هوو هوي هيأ هيب هيت هيج هير هيش هيض "+
           " هيط هيف هيل هيم هين "+
             
           " وأد وأر وأل وأم وبأ وبخ وبر وبش وبق وبل وبه وتد وتر وتن وتي وثأ وثب "+
            "وثر وثق وثل وثن وجب وجد وجر وجز وجس وجع وجف وجق وجل وجم وجن وجه وحد "+
           " وحش وحف وحل وحم وحي وخز وخط وخم وخي ودج ودد ودر ودع ودق ودك ودي وذر "+
           " ورب ورث ورد ورس ورش ورط ورع ورف ورق ورك ورل ورم ورن وري وزب وزر وزز "+
           " وزع وزل وزن وزي وسخ وسد وسط وسع وسق وسل وسم وسن وسي وشب وشج وشح وشر "+
           " وشع وشق وشك وشل وشم وشن وشي وصب وصد وصف وصل وصم وصي وضأ وضب وضح وضر "+
           " وضع وضم وطأ وطب وطد وطر وطس وطش وطف وطن وطي وظب وظف وعب وعث وعد وعر "+
           " وعز وعس وعظ وعق وعك وعل وعي وغد وغر وغل وغي وفد وفر وفز وفض وفع وفق "+
           " وفي وقب وقت وقح وقد وقذ وقر وقص وقظ وقع وقف وقق وقل وقن وقي وكأ وكب "+
           " وكد وكر وكز وكس وكع وكف وكل وكم وكن وكي ولج ولد ولس ولط ولع ولغ ولف "+
           " ولم وله ولو ولي ومأ ومد ومس ومض ومق ونن وني وهب وهج وهد وهر وهق وهل "+
           " وهم وهن وهي ويب ويل "+
             
           " يأس يبب يبس يتم يثق يحر يخت يخن يرع يرق يزب يسر يسن يفخ يفع يقت يقظ "+
           " يقن يمم يمن ينع يود يوم" ;
		FileOutputStream outputStream19;

		try {
		  outputStream19 = openFileOutput(filename19,Context.MODE_PRIVATE);
		  outputStream19.write(content19.getBytes("UTF-16" ));
		  outputStream19.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
	}
	
	//button text to sign 
	public void matched_word(View view)
    { 
        ArabicWord = ed.getText().toString();
		//get root for words of edit text
        String [] edd = ArabicWord.split(" ");
		root.clear();
		
		for(int i=0; i<edd.length;i++)
		{
			if(feedReaderDbHelper.getDatamatch(edd[i]))
			{
				int x =Integer.parseInt(uuu);
				symbols.append(Character.toString((char) x)+" ");
			}
			else
			{
		       String roo = Stemmer.stemWord(edd[i]);
	           words.clear();matchedSigns.clear();lengthword.clear();
	           feedReaderDbHelper.getDatamatch(roo);
	   		    if(lengthword.size()>0)
	   		       {
	   		         int minlengthword =getminlength(lengthword);
	   		         //Message.message(this,MainActivity.words.get(minlengthword));
	   		         //Message.message(this,MainActivity.matchedSigns.get(minlengthword));
	   		
	   		         int x =Integer.parseInt(matchedSigns.get(minlengthword));
	   		         symbols.append(Character.toString((char) x)+" ");
	   		       }
	   		    else {  
	   		    	    symbols.append(Character.toString((char) 8220)+" ");
		    	        for (int j = 0; j < edd[i].length(); j++) 
		    	          {
			                 String ch=edd[i].substring(j, j+1);
		                     feedReaderDbHelper.getDatamatch(ch);
		                     int x =Integer.parseInt(uuu);
		                     symbols.append(Character.toString((char) x)+" ");
		                  }
		    	        symbols.append(Character.toString((char) 8221)+" ");
		             }
			}
	    }
    }
	
	//to get min length of matched word
    public int getminlength (ArrayList<Integer> arr)
	{   
		int minlength=arr.get(0);
		int r=0;
		for(int i=0; i<arr.size();i++)
        {
            if(arr.get(i)<= minlength)
            {
            	minlength=arr.get(i);
                r=i;
            }
        }
	  	return r;
	}
    //////////////////////////////////////////////////////////////////////////////
    // isOnline function to check if the device is connect to internet or not
    // added by minaSamir
    private boolean isOnline()  
	{
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo ni = cm.getActiveNetworkInfo();
	    if(ni == null)
	    	return false;
	    else
	    	return true;
	}
    //////////////////////////////////////////////////////////////////////////////
    
    public void append_word(View view)
    {
    	if(isOnline())
    	{
    		
    		
    		///// Prepare the sentence
    		String sen =new String();
            for(int i=0; i<MainActivity.sentence.size();i++)
            {
            	word=feedReaderDbHelper.getData(MainActivity.sentence.get(i));
            	if (word.length()==1)
            	{
            		sen=sen.concat(word);
            	}
            	else
            	{
            		sen=sen.concat(" "+word+" ");
            	}
            }
    		
    		if(sen.isEmpty())
    		{
    			Toast.makeText(this, "Please choose the signs", Toast.LENGTH_SHORT).show();
    		}
    		else
    		{
    			pDialog = new ProgressDialog(MainActivity.this);
        		pDialog.setMessage("Prepare Sentence...");
        		pDialog.setCancelable(false);
        		
        		pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() 
        		{
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) 
        		    {
        		    	
        		        dialog.dismiss();
        		        
        		        synthesis.cancel();
        		        synthesis.stop();
        		        
        		        
        		        Log.d(TAG, "Cancelabledialag");
        		        Log.d(TAG, "Cancelabledialag");
        		        Log.d(TAG, "Cancelabledialag");
        		        //Toast.makeText(this, "dismissPdialog", Toast.LENGTH_SHORT).show();
        		        
        		    }
        		});
        		
        		pDialog.show();
        		
        		
            	
                
                
                //////////////////////////////////////////////
                // koooooooooookii
                Toast toast=Toast.makeText(this, sen, 4);
                toast.setGravity(Gravity.TOP, 0, 500); 
                toast.show();
            	///////////////////////////////////////////
                
                
                
                
                
            	////////// related to ispeech
            	pDialog.setMessage("Speeking Soon...");
            	pDialog.show();
            	try {
        			String ttsText = sen;//((EditText) findViewById(R.id.text)).getText().toString();
        			synthesis.setVoiceType("arabicmale");
        			synthesis.addOptionalCommand("speed",SpeechSpeed);
        			synthesis.speak(ttsText);

        		} catch (BusyException e) {
        			Log.e(TAG, "SDK is busy");
        			e.printStackTrace();
        			Toast.makeText(_context, "ERROR: SDK is busy", Toast.LENGTH_LONG).show();
        		} catch (NoNetworkException e) {
        			Log.e(TAG, "Network is not available\n" + e.getStackTrace());
        			Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
        		}
            	/////////////////////////////
    		}
    		
    		
    	}
    	else
    	{
    		Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    	}
    }
    
    
    public void clear_word(View view)
    {
    	sentence.clear();
    	Editable e = ed.getText();
    	e.clear();
    }
    
    static void down()
    {
    	
    	if(mCustomKeyboard1.isCustomKeyboardVisible()) 
    	{
    		mCustomKeyboard1.hideCustomKeyboard();
    		mCustomKeyboard2.hideCustomKeyboard();
    	}
    }
    
    
    
    
    public void mm(View view)
    {
    	Editable e = ed.getText();
    	int start = ed.getSelectionStart();
    	if( e!=null && start>0) 
    	{
        	e.delete(start - 1, start);
        	sentence.remove(ed.getSelectionEnd());
    	}
    }
    
    

        static void up()
        {
        	
        	if(!mCustomKeyboard1.isCustomKeyboardVisible()) 
        	{
        		mCustomKeyboard1.showKeyboard();
        		mCustomKeyboard2.showKeyboard();
        	}
    }
    
    //======================================================================================
    

    //========================================================================================================
   

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			Log.d("minaSamirCoifiguration", mCustomKeyboard1.getkeyboardid()+"" );		
			mCustomKeyboard1.CustomKeyboardChange(this, R.id.keyboardview, R.xml.category_land);
		}
		else
			mCustomKeyboard1.CustomKeyboardChange(this, R.id.keyboardview, R.xml.hexkbd);
			
	}

	public void minaSamir(View v)
	{	
		KeyBoardTpye= mySharedPreferenc.getInt("keyboardtype",2);  // chech will happen in function
        
		if(KeyBoardTpye==2)
	    {
	    	ed.setInputType(InputType.TYPE_NULL);
	    	//ed.onTouchEvent(event);               // Call native handler
			//ed.setInputType(inType);              // Restore input type
	    	clearButton = (Button) findViewById(R.id.button3);
	    	
	    	//// the type face
	    	tf = Typeface.createFromAsset(getAssets(),"fonts/egyptian_sign_languages.ttf");
	        ed.setTypeface(tf);
	    	//
			//ed.setText("");
			//edit text properties
	        editTextDimension = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	        clearbutton = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        
	        clearButton.setVisibility(View.VISIBLE);
	        ed.setVisibility(View.GONE);
	        
	        
			// b.setVisibility(v.GONE);
	        scrollOfKeyboard.setVisibility(View.VISIBLE);
	        
	        mCustomKeyboard1.showKeyboard();
	        mCustomKeyboard2.showKeyboard();
	     // ed.setInputType(InputType.TYPE_NULL);
	        editTextDimension.addRule(RelativeLayout.ABOVE, R.id.keyboardview2);
	        //clearbutton.addRule(RelativeLayout.RIGHT_OF,R.id.edittext1);
	        
	        ed.setLayoutParams(editTextDimension);
	        //b.setLayoutParams(clearbutton);
			ed.setVisibility(View.VISIBLE);
			Convert.setVisibility(View.GONE);
            symbols.setVisibility(View.GONE);
	    }
	    

		
        //Arabic keyboard
        else if(KeyBoardTpye==1)
        {
        	 ed.setInputType(InputType.TYPE_CLASS_TEXT);
        	 mCustomKeyboard1.getmKeyboardView().setVisibility(View.GONE);
             mCustomKeyboard1.getmKeyboardView().setEnabled(false);
             mCustomKeyboard2.getmKeyboardView().setVisibility(View.GONE);
             mCustomKeyboard2.getmKeyboardView().setEnabled(false);
             Convert.setVisibility(View.VISIBLE);
             symbols.setVisibility(View.VISIBLE);
             
        }
         
        
		

	}
		
	
	//////////// related to integration with farok
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				Intent videoIntent = new Intent(getBaseContext(),VideoViewActivity.class);
				videoIntent.putExtra("text", text.get(0));
				// videoIntent.putExtra("text", "ÓáÇã Úáíßã");

				Log.d("gp", text.get(0));
				startActivity(videoIntent);

			}
			break;
		}
		}
	}
	///////////////////////////////////////////
	
	
	
	@Override
	protected void onPause() 
	{
		synthesis.stop();	//Optional to stop the playback when the activity is paused
		super.onPause();
	}

	@Override 
    public void onBackPressed() 
    { 
		if(mCustomKeyboard1.isCustomKeyboardVisible())
    	{
    		
    	
    		ed.setVisibility(View.GONE);
    		
    		mCustomKeyboard2.hideCustomKeyboard();
    		mCustomKeyboard1.hideCustomKeyboard(); 
    		scrollOfKeyboard.setVisibility(View.GONE);
        	editTextDimension.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	ed.setLayoutParams(editTextDimension);
        	ed.setVisibility(View.VISIBLE);
        	clearButton.setVisibility(View.GONE);
        	
    	}
        else 
        {
      	      sentence.clear();
       	      this.finish();
        }
    }
}




