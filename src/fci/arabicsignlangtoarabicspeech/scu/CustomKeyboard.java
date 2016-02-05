/**
 * Copyright 2013 Maarten Pennings
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you use this software in a product, an acknowledgment in the product
 * documentation would be appreciated but is not required.
 */

package fci.arabicsignlangtoarabicspeech.scu;

import nl.fampennings.keyboard.R;
import android.app.Activity;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
//import android.widget.Toast;
import android.widget.TextView;

/**
 * When an activity hosts a keyboardView, this class allows several EditText's to register for it.
 *
 * @author Maarten Pennings
 * @date   2012 December 23
 */
class CustomKeyboard 
{

    /** A link to the KeyboardView that is used to render this CustomKeyboard. */
    private KeyboardView mKeyboardView;
  
    /** A link to the activity that hosts the {@link #mKeyboardView}. */
    private Activity     mHostActivity;
    private int layoutid;
    private int viewid;
   // DbHelper feedReaderDbHelper;
    
    public static String wordd;
    
    /** The key (code) handler. */
    public OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener()
    {

       // public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
        public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
        public int PREVIOUS_CODE =100000;  // To not change category of the same category
        public final static int Codechangenumbers =100000;
        public final static int Codechangefamily = 100001;
        public final static int Codechangeletters = 100002;
        public final static int Codechangehome_one = 100003;
        public final static int Codechangearts = 1000004;
        public final static int Codechange_food_one= 100005;
        public final static int Codechange_direction = 100006;
        public final static int Codechange_jobs = 100007;
        public final static int Codechangecolors = 100008;
        public final static int Codechange_events = 100009;
        public final static int Codechange_sport= 100010;
        public final static int Codechange_general_one= 100016;
      
        
        
        public final static int empty=100030;
        
        //change tabs in the second keyboard 
        public final static int codeleft_1=110001;
        public final static int codeleft_2=110002;
        public final static int codeleft_3=110003;
       
        
        public final static int coderight_1=220001;
        public final static int coderight_2=220002;
        public final static int coderight_3=220003;
       
        
        
        
      //change xml of category (family)
        public final static int codeleft_fam1=940001;
        public final static int codeleft_fam2=940002;
        public final static int codeleft_fam3=940003;
        public final static int codeleft_fam4=940004;
        public final static int codeleft_fam5=940005;
        public final static int codeleft_fam6=940006;
        
        
        public final static int coderight_fam1=920001;
        public final static int coderight_fam2=920002;
        public final static int coderight_fam3=920003;
        public final static int coderight_fam4=920004;
        public final static int coderight_fam5=920005;
        public final static int coderight_fam6=920006;
        
        
        //change xml of category (Arts)
          public final static int codeleft_arts1=940007;
          public final static int codeleft_arts2=940008;
          public final static int codeleft_arts3=940009;
          public final static int codeleft_arts4=940010;
          
          
          public final static int coderight_arts1=920007;
          public final static int coderight_arts2=920008;
          public final static int coderight_arts3=920009;
          public final static int coderight_arts4=920010;
          
          
          
        //change xml of category (Colors)
          public final static int codeleft_colors1=940011;
          public final static int codeleft_colors2=940012;
          
          public final static int coderight_colors1=920011;
          public final static int coderight_colors2=920012;
          
          
        //change xml of category (directions)
          public final static int codeleft_direc1=940013;
          public final static int codeleft_direc2=940014;
          public final static int codeleft_direc3=940015;
          
          public final static int codeleft_direc4=940075;
          
          
          public final static int coderight_direc1=920013;
          public final static int coderight_direc2=920014;
          public final static int coderight_direc3=920015;  
          
          public final static int coderight_direc4=920075; 
          
          
          
        //change xml of category (events)
          public final static int codeleft_events1=940016;
          public final static int codeleft_events2=940017;
          public final static int codeleft_events3=940018;
          
          
          public final static int coderight_events1=920016;
          public final static int coderight_events2=920017;
          public final static int coderight_events3=920018;  
        
       
          
       
        //change xml of category (jobs)
          public final static int codeleft_jobs1=940019;
          public final static int codeleft_jobs2=940020;
          public final static int codeleft_jobs3=940021;
          public final static int codeleft_jobs4=940022;
          
          
          public final static int coderight_jobs1=920019;
          public final static int coderight_jobs2=920020;
          public final static int coderight_jobs3=920021;
          public final static int coderight_jobs4=920022;
          
          
          
          
          
          //change xml of category (Letters)
          public final static int codeleft_letters1=940023;
          public final static int codeleft_letters2=940024;
          public final static int codeleft_letters3=940025;
          public final static int codeleft_letters4=940026;
          public final static int codeleft_letters5=940027;
          
          
          
          public final static int coderight_letters1=920023;
          public final static int coderight_letters2=920024;
          public final static int coderight_letters3=920025;
          public final static int coderight_letters4=920026;
          public final static int coderight_letters5=920027;
          
          
          
          
          
          //change xml of category (Sports)
          public final static int codeleft_sport1=940028;
          public final static int codeleft_sport2=940029;
          public final static int codeleft_sport3=940030;
          public final static int codeleft_sport4=940031;
          public final static int codeleft_sport5=940032;
          
          public final static int codeleft_sport6=940076;
          
          
          
          public final static int coderight_sport1=920028;
          public final static int coderight_sport2=920029;
          public final static int coderight_sport3=920030;
          public final static int coderight_sport4=920031;
          public final static int coderight_sport5=920032;
          
          public final static int coderight_sport6=920076;

          
          
          
          
          
          
          //change xml of category (food)
          public final static int codeleft_food1=940033;
          public final static int codeleft_food2=940034;
          public final static int codeleft_food3=940035;
          public final static int codeleft_food4=940036;
          public final static int codeleft_food5=940037;
          public final static int codeleft_food6=940038;
          public final static int codeleft_food7=940039;
          public final static int codeleft_food8=940040;
          public final static int codeleft_food9=940041;
          public final static int codeleft_food10=940042;
          public final static int codeleft_food11=940043;
          
          
          public final static int coderight_food1=920033;
          public final static int coderight_food2=920034;
          public final static int coderight_food3=920035;
          public final static int coderight_food4=920036;
          public final static int coderight_food5=920037;
          public final static int coderight_food6=920038;
          public final static int coderight_food7=920039;
          public final static int coderight_food8=920040;
          public final static int coderight_food9=920041;
          public final static int coderight_food10=920042;
          public final static int coderight_food11=920043;
          
          
          
          
          
          //change xml of category (Home)
          public final static int codeleft_home1=940044;
          public final static int codeleft_home2=940045;
          public final static int codeleft_home3=940046;
          public final static int codeleft_home4=940047;
          public final static int codeleft_home5=940048;
          public final static int codeleft_home6=940049;
          public final static int codeleft_home7=940050;
          public final static int codeleft_home8=940051;
          public final static int codeleft_home9=940052;
          public final static int codeleft_home10=940053;
          public final static int codeleft_home11=940054;
          public final static int codeleft_home12=940055;
          public final static int codeleft_home13=940056;
          public final static int codeleft_home14=940057;
          public final static int codeleft_home15=940058;
          
          
          public final static int coderight_home1=920044;
          public final static int coderight_home2=920045;
          public final static int coderight_home3=920046;
          public final static int coderight_home4=920047;
          public final static int coderight_home5=920048;
          public final static int coderight_home6=920049;
          public final static int coderight_home7=920050;
          public final static int coderight_home8=920051;
          public final static int coderight_home9=920052;
          public final static int coderight_home10=920053;
          public final static int coderight_home11=920054;
          public final static int coderight_home12=920055;
          public final static int coderight_home13=920056;
          public final static int coderight_home14=920057;
          public final static int coderight_home15=920058;
          
          
          
          
          
          
        //change xml of category (General)
          public final static int codeleft_general1=940059;
          public final static int codeleft_general2=940060;
          public final static int codeleft_general3=940061;
          public final static int codeleft_general4=940062;
          public final static int codeleft_general5=940063;
          public final static int codeleft_general6=940064;
          public final static int codeleft_general7=940065;
          public final static int codeleft_general8=940066;
          public final static int codeleft_general9=940067;
          public final static int codeleft_general10=940068;
          public final static int codeleft_general11=940069;
          public final static int codeleft_general12=940070;
          public final static int codeleft_general13=940071;
          public final static int codeleft_general14=940072;
          public final static int codeleft_general15=940073;
          public final static int codeleft_general16=940074;
          
          
          public final static int codeleft_general17=940077;
          
          
          public final static int coderight_general1=920059;
          public final static int coderight_general2=920060;
          public final static int coderight_general3=920061;
          public final static int coderight_general4=920062;
          public final static int coderight_general5=920063;
          public final static int coderight_general6=920064;
          public final static int coderight_general7=920065;
          public final static int coderight_general8=920066;
          public final static int coderight_general9=920067;
          public final static int coderight_general10=920068;
          public final static int coderight_general11=920069;
          public final static int coderight_general12=920070;
          public final static int coderight_general13=920071;
          public final static int coderight_general14=920072;
          public final static int coderight_general15=920073;
          public final static int coderight_general16=920074;
          
          public final static int coderight_general17=920077;
          
          
          
          
          
        
            @Override 
        public void onKey(int primaryCode, int[] keyCodes) 
        {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) 
            	return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            ////////////////////////////////////////
            
//            MainActivity.uni =String.valueOf(primaryCode);
//            Log.d("minaSamirInsert",MainActivity.uni);
//            MainActivity.sentence.add(MainActivity.uni);
            
            
            if( primaryCode==CodeCancel ) 
            {
            	MainActivity.down();
            
            }
            else if(primaryCode == Codechangenumbers)
            {
            	if(PREVIOUS_CODE!=Codechangenumbers)
            	{
            		swipeKeyboardL1();
            		PREVIOUS_CODE=primaryCode;
            	}
            }
                
            else if(primaryCode == Codechangefamily)
            {
               	if(PREVIOUS_CODE!=Codechangefamily)
               	{
               		swipeKeyboardL2();
               		PREVIOUS_CODE=primaryCode;
            	}
                	
            }
            else if(primaryCode == Codechangeletters)
            {
               	if(PREVIOUS_CODE!=Codechangeletters)
               	{
               		swipeKeyboardL3();
               		PREVIOUS_CODE=primaryCode;
            	}
                	
            }
                
            else if(primaryCode == Codechangehome_one)
            {
               	if(PREVIOUS_CODE!=Codechangehome_one)
               	{
               		swipeKeyboardL4();
               		PREVIOUS_CODE=primaryCode;
            	}
           }
                else if(primaryCode == Codechangearts)
                {
                	if(PREVIOUS_CODE!=Codechangearts)
                	{
                		swipeKeyboardL5();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
               
                else if(primaryCode == Codechangecolors)
                {
                	if(Codechangecolors!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL6();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
               
                else if(primaryCode == Codechange_direction)
                {
                	if(Codechange_direction!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL7();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
               
                else if(primaryCode == Codechange_food_one)
                {
                	if(Codechange_food_one!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL8();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
               
                else if(primaryCode == Codechange_jobs)
                {
                	if(Codechange_jobs!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL9();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
                
                else if(primaryCode == Codechange_events)
                {
                	if(Codechange_events!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL10();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
               
                else if(primaryCode == Codechange_sport)
                {
                	if(Codechange_sport!=PREVIOUS_CODE)
                	{
                	swipeKeyboardL11();
                	PREVIOUS_CODE=primaryCode;
                	}
                	
                }
            
               
               
            
      
              
                
                //left
                
                else if(primaryCode == codeleft_1)
                {
                //	swipeKeyboardLeft_1();
                	
                }
                else if(primaryCode ==codeleft_2)
                {
                	if(codeleft_2!=PREVIOUS_CODE)
                	{
                		swipeKeyboardLeft_2();
                		PREVIOUS_CODE=primaryCode;
                	}
                }
                else if(primaryCode == codeleft_3)
                {
                	if(codeleft_3!=PREVIOUS_CODE)
                	{
                		swipeKeyboardLeft_3();
                		PREVIOUS_CODE=primaryCode;
                	}
                }
            
                
                //right
                else if(primaryCode == coderight_1)
                {
                	if(coderight_1!=PREVIOUS_CODE)
                	{
                		swipeKeyboardRight_1();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
                else if(primaryCode == coderight_2)
                {
                	if(coderight_2!=PREVIOUS_CODE)
                	{
                		swipeKeyboardRight_2();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
                else if(primaryCode == coderight_3)
                {
                	if(coderight_3!=PREVIOUS_CODE)
                	{
                		swipeKeyboardRight_3();
                		PREVIOUS_CODE=primaryCode;
                	}
                	
                }
              
             //*****************************************
            
                else if(primaryCode == Codechange_general_one)           	
                {
                	if(coderight_3!=PREVIOUS_CODE)
                	{
                		swipeKeyboardL12();
                		PREVIOUS_CODE=primaryCode;
                	}
               }
              
              
            
        //********************************************
            
            
            
               
            
            
          //*****************category (family) right********************************     
                else if(primaryCode == coderight_fam1){
                	swipekeyboardright_fam1();
                }
                
                
                else if(primaryCode == coderight_fam2){
                	swipekeyboardright_fam2();
                }
                else if(primaryCode == coderight_fam3){
                	swipekeyboardright_fam3();
                }
                
                
                else if(primaryCode == coderight_fam4){
                	swipekeyboardright_fam4();
                }
                else if(primaryCode == coderight_fam5){
                	swipekeyboardright_fam5();
                }
                
                
                else if(primaryCode == coderight_fam6){
                	swipekeyboardright_fam6();
                }
                
               
                
                
           //******************category (family) left********************************     
                else if(primaryCode == codeleft_fam1){
                	swipekeyboardleft_fam1();
                	
                }
                else if(primaryCode == codeleft_fam2){
                	swipekeyboardleft_fam2();
                	
                	
                }
                
                else if(primaryCode == codeleft_fam3){
                	swipekeyboardleft_fam3();
                	
                	
                }
                
                else if(primaryCode == codeleft_fam4){
                	swipekeyboardleft_fam4();
                	
                }
                else if(primaryCode == codeleft_fam5){
                	swipekeyboardleft_fam5();
                	
                	
                }
                
                else if(primaryCode == codeleft_fam6){
                	swipekeyboardleft_fam6();
                	
                	
                }
               
                
            
            
            
            
            
            
            //*****************category (Arts) right********************************     
                else if(primaryCode == coderight_arts1){
                	swipekeyboardright_arts1();
                }
                
                
                else if(primaryCode == coderight_arts2){
                	swipekeyboardright_arts2();
                }
                else if(primaryCode == coderight_arts3){
                	swipekeyboardright_arts3();
                }
                
                
                else if(primaryCode == coderight_arts4){
                	swipekeyboardright_arts4();
                }
                
                
                
                
              //******************category (Arts) left********************************     
                else if(primaryCode == codeleft_arts1){
                	swipekeyboardleft_arts1();
                	
                }
                else if(primaryCode == codeleft_arts2){
                	swipekeyboardleft_arts2();
                	
                	
                }
                
                else if(primaryCode == codeleft_arts3){
                	swipekeyboardleft_arts3();
                	
                	
                }
                
                else if(primaryCode == codeleft_arts4){
                	swipekeyboardleft_arts4();
                	
                }
            
          //*****************category (Colors) right********************************     
                else if(primaryCode == coderight_colors1){
                	swipekeyboardright_colors1();
                }
                
                
                else if(primaryCode == coderight_colors2){
                	swipekeyboardright_colors2();
                }
                
                
                
              //******************category (Colors) left********************************     
                else if(primaryCode == codeleft_colors1){
                	swipekeyboardleft_colors1();
                	
                }
                else if(primaryCode == codeleft_colors2){
                	swipekeyboardleft_colors2();
                	
                	
                }
                
            
          //*****************category (directions) right********************************     
                else if(primaryCode == coderight_direc1){
                	swipekeyboardright_direc1();
                }
                
                
                else if(primaryCode == coderight_direc2){
                	swipekeyboardright_direc2();
                }
                
                else if(primaryCode == coderight_direc3){
                	swipekeyboardright_direc3();
                }
            
                else if(primaryCode == coderight_direc4){
                	swipekeyboardright_direc4();
                }
                
                
                
              //******************category (directions) left********************************     
                else if(primaryCode == codeleft_direc1){
                	swipekeyboardleft_direc1();
                	
                }
                else if(primaryCode == codeleft_direc2){
                	swipekeyboardleft_direc2();
                	
                }
                
                else if(primaryCode == codeleft_direc3){
                	swipekeyboardleft_direc3();
                	
                	
                }
            
                else if(primaryCode == codeleft_direc4){
                	swipekeyboardleft_direc4();
                	
                	
                }
                
            
            
            //*****************category (Events) right********************************     
                else if(primaryCode == coderight_events1){
                	swipekeyboardright_events1();
                }
                
                
                else if(primaryCode == coderight_events2){
                	swipekeyboardright_events2();
                }
                
                else if(primaryCode == coderight_events3){
                	swipekeyboardright_events3();
                }
                
                
                
              //******************category (Events) left********************************     
                else if(primaryCode == codeleft_events1){
                	swipekeyboardleft_events1();
                	
                }
                else if(primaryCode == codeleft_events2){
                	swipekeyboardleft_events2();
                	
                }
                
                else if(primaryCode == codeleft_events3){
                	swipekeyboardleft_events3();
                	
                	
                }
                
            
            
            
          //*****************category (jobs) right********************************     
                else if(primaryCode == coderight_jobs1){
                	swipekeyboardright_jobs1();
                }
                
                
                else if(primaryCode == coderight_jobs2){
                	swipekeyboardright_jobs2();
                }
                else if(primaryCode == coderight_jobs3){
                	swipekeyboardright_jobs3();
                }
                
                
                else if(primaryCode == coderight_jobs4){
                	swipekeyboardright_jobs4();
                }
                
                
                
                
              //******************category (jobs) left********************************     
                else if(primaryCode == codeleft_jobs1){
                	swipekeyboardleft_jobs1();
                	
                }
                else if(primaryCode == codeleft_jobs2){
                	swipekeyboardleft_jobs2();
                	
                	
                }
                
                else if(primaryCode == codeleft_jobs3){
                	swipekeyboardleft_jobs3();
                	
                	
                }
                
                else if(primaryCode == codeleft_jobs4){
                	swipekeyboardleft_jobs4();
                	
                }
             
            
            
            //*****************category (Letters) right********************************     
                  else if(primaryCode == coderight_letters1){
                  	swipekeyboardright_letters1();
                  }
                  
                  
                  else if(primaryCode == coderight_letters2){
                  	swipekeyboardright_letters2();
                  }
                  else if(primaryCode == coderight_letters3){
                  	swipekeyboardright_letters3();
                  }
                  
                  
                  else if(primaryCode == coderight_letters4){
                  	swipekeyboardright_letters4();
                  }
                  else if(primaryCode == coderight_letters5){
                  	swipekeyboardright_letters5();
                  }
                  
                 
                 
                  
                  
             //******************category (Letters) left********************************     
                  else if(primaryCode == codeleft_letters1){
                  	swipekeyboardleft_letters1();
                  	
                  }
                  else if(primaryCode == codeleft_letters2){
                  	swipekeyboardleft_letters2();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_letters3){
                  	swipekeyboardleft_letters3();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_letters4){
                  	swipekeyboardleft_letters4();
                  	
                  }
                  else if(primaryCode == codeleft_letters5){
                  	swipekeyboardleft_letters5();
                  	
                  	
                  }
                  
                  
            
            
            //*****************category (Sports) right********************************     
                  else if(primaryCode == coderight_sport1){
                  	swipekeyboardright_sport1();
                  }
                  
                  
                  else if(primaryCode == coderight_sport2){
                  	swipekeyboardright_sport2();
                  }
                  else if(primaryCode == coderight_sport3){
                  	swipekeyboardright_sport3();
                  }
                  
                  
                  else if(primaryCode == coderight_sport4){
                  	swipekeyboardright_sport4();
                  }
                  else if(primaryCode == coderight_sport5){
                  	swipekeyboardright_sport5();
                  }
            
                  else if(primaryCode == coderight_sport6){
                    	swipekeyboardright_sport6();
                    }
                  
                 
                 
                  
                  
             //******************category (Sports) left********************************     
                  else if(primaryCode == codeleft_sport1){
                  	swipekeyboardleft_sport1();
                  	
                  }
                  else if(primaryCode == codeleft_sport2){
                  	swipekeyboardleft_sport2();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_sport3){
                  	swipekeyboardleft_sport3();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_sport4){
                  	swipekeyboardleft_sport4();
                  	
                  }
                  else if(primaryCode == codeleft_sport5){
                  	swipekeyboardleft_sport5();
                  	
                  	
                  }
            
                  else if(primaryCode == codeleft_sport6){
                    	swipekeyboardleft_sport6();
                    	
                    	
                    }
                  
              
            
            
            //*****************category (Food) right********************************     
                  else if(primaryCode == coderight_food1){
                  	swipekeyboardright_food1();
                  }
                  
                  
                  else if(primaryCode == coderight_food2){
                  	swipekeyboardright_food2();
                  }
                  else if(primaryCode == coderight_food3){
                  	swipekeyboardright_food3();
                  }
                  
                  
                  else if(primaryCode == coderight_food4){
                  	swipekeyboardright_food4();
                  }
                  else if(primaryCode == coderight_food5){
                  	swipekeyboardright_food5();
                  }
                  
                  
                  else if(primaryCode == coderight_food6){
                  	swipekeyboardright_food6();
                  }
            
            
                  else if(primaryCode == coderight_food7){
                    	swipekeyboardright_food7();
                    }
                    
                    
                    else if(primaryCode == coderight_food8){
                    	swipekeyboardright_food8();
                    }
                    else if(primaryCode == coderight_food9){
                    	swipekeyboardright_food9();
                    }
                    
                    
                    else if(primaryCode == coderight_food10){
                    	swipekeyboardright_food10();
                    }
            
                    else if(primaryCode == coderight_food11){
                    	swipekeyboardright_food11();
                    }
                  
                 
                  
                  
             //******************category (Food) left********************************     
                  else if(primaryCode == codeleft_food1){
                  	swipekeyboardleft_food1();
                  	
                  }
                  else if(primaryCode == codeleft_food2){
                  	swipekeyboardleft_food2();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_food3){
                  	swipekeyboardleft_food3();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_food4){
                  	swipekeyboardleft_food4();
                  	
                  }
                  else if(primaryCode == codeleft_food5){
                  	swipekeyboardleft_food5();
                  	
                  	
                  }
                  
                  else if(primaryCode == codeleft_food6){
                  	swipekeyboardleft_food6();
                  	
                  	
                  }
            
                  else if(primaryCode == codeleft_food7){
                    	swipekeyboardleft_food7();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_food8){
                    	swipekeyboardleft_food8();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_food9){
                    	swipekeyboardleft_food9();
                    	
                    }
                    else if(primaryCode == codeleft_food10){
                    	swipekeyboardleft_food10();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_food11){
                    	swipekeyboardleft_food11();
                    	
                    	
                    }
                 
          
          //*****************category (Home) right********************************     
                    else if(primaryCode == coderight_home1){
                    	swipekeyboardright_home1();
                    }
                    
                    
                    else if(primaryCode == coderight_home2){
                    	swipekeyboardright_home2();
                    }
                    else if(primaryCode == coderight_home3){
                    	swipekeyboardright_home3();
                    }
                    
                    
                    else if(primaryCode == coderight_home4){
                    	swipekeyboardright_home4();
                    }
                    else if(primaryCode == coderight_home5){
                    	swipekeyboardright_home5();
                    }
                    
                    
                    else if(primaryCode == coderight_home6){
                    	swipekeyboardright_home6();
                    }
              
              
                    else if(primaryCode == coderight_home7){
                      	swipekeyboardright_home7();
                      }
                      
                      
                      else if(primaryCode == coderight_home8){
                      	swipekeyboardright_home8();
                      }
                      else if(primaryCode == coderight_home9){
                      	swipekeyboardright_home9();
                      }
                      
                      
                      else if(primaryCode == coderight_home10){
                      	swipekeyboardright_home10();
                      }
              
                      else if(primaryCode == coderight_home11){
                      	swipekeyboardright_home11();
                      }
                      else if(primaryCode == coderight_home12){
                        	swipekeyboardright_home12();
                        }
                        else if(primaryCode == coderight_home13){
                        	swipekeyboardright_home13();
                        }
                        
                        
                        else if(primaryCode == coderight_home14){
                        	swipekeyboardright_home14();
                        }
                
                        else if(primaryCode == coderight_home15){
                        	swipekeyboardright_home15();
                        }
                    
                   
                    
                    
               //******************category (Home) left********************************     
                    else if(primaryCode == codeleft_home1){
                    	swipekeyboardleft_home1();
                    	
                    }
                    else if(primaryCode == codeleft_home2){
                    	swipekeyboardleft_home2();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_home3){
                    	swipekeyboardleft_home3();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_home4){
                    	swipekeyboardleft_home4();
                    	
                    }
                    else if(primaryCode == codeleft_home5){
                    	swipekeyboardleft_home5();
                    	
                    	
                    }
                    
                    else if(primaryCode == codeleft_home6){
                    	swipekeyboardleft_home6();
                    	
                    	
                    }
              
                    else if(primaryCode == codeleft_home7){
                      	swipekeyboardleft_home7();
                      	
                      	
                      }
                      
                      else if(primaryCode == codeleft_home8){
                      	swipekeyboardleft_home8();
                      	
                      	
                      }
                      
                      else if(primaryCode == codeleft_home9){
                      	swipekeyboardleft_home9();
                      	
                      }
                      else if(primaryCode == codeleft_home10){
                      	swipekeyboardleft_home10();
                      	
                      	
                      }
                      
                      else if(primaryCode == codeleft_home11){
                      	swipekeyboardleft_home11();
                      	
                      	
                      }
                   
                      else if(primaryCode == codeleft_home12){
                        	swipekeyboardleft_home12();
                        	
                        	
                        }
                        
                        else if(primaryCode == codeleft_home13){
                        	swipekeyboardleft_home13();
                        	
                        }
                        else if(primaryCode == codeleft_home14){
                        	swipekeyboardleft_home14();
                        	
                        	
                        }
                        
                        else if(primaryCode == codeleft_home15){
                        	swipekeyboardleft_home15();
                        	
                        	
                        }
            
            
            
            
       
          //*****************category (General) right********************************     
                        else if(primaryCode == coderight_general1){
                        	swipekeyboardright_general1();
                        }
                        
                        
                        else if(primaryCode == coderight_general2){
                        	swipekeyboardright_general2();
                        }
                        else if(primaryCode == coderight_general3){
                        	swipekeyboardright_general3();
                        }
                        
                        
                        else if(primaryCode == coderight_general4){
                        	swipekeyboardright_general4();
                        }
                        else if(primaryCode == coderight_general5){
                        	swipekeyboardright_general5();
                        }
                        
                        
                        else if(primaryCode == coderight_general6){
                        	swipekeyboardright_general6();
                        }
                  
                  
                        else if(primaryCode == coderight_general7){
                          	swipekeyboardright_general7();
                          }
                          
                          
                          else if(primaryCode == coderight_general8){
                          	swipekeyboardright_general8();
                          }
                          else if(primaryCode == coderight_general9){
                          	swipekeyboardright_general9();
                          }
                          
                          
                          else if(primaryCode == coderight_general10){
                          	swipekeyboardright_general10();
                          }
                  
                          else if(primaryCode == coderight_general11){
                          	swipekeyboardright_general11();
                          }
                          else if(primaryCode == coderight_general12){
                            	swipekeyboardright_general12();
                            }
                            else if(primaryCode == coderight_general13){
                            	swipekeyboardright_general13();
                            }
                            
                            
                            else if(primaryCode == coderight_general14){
                            	swipekeyboardright_general14();
                            }
                    
                            else if(primaryCode == coderight_general15){
                            	swipekeyboardright_general15();
                            }
            
                            else if(primaryCode == coderight_general16){
                            	swipekeyboardright_general16();
                            }
            
            
                            else if(primaryCode == coderight_general17){
                            	swipekeyboardright_general17();
                            }
                        
                       
                        
                        
                   //******************category (General) left********************************     
                        else if(primaryCode == codeleft_general1){
                        	swipekeyboardleft_general1();
                        	
                        }
                        else if(primaryCode == codeleft_general2){
                        	swipekeyboardleft_general2();
                        	
                        	
                        }
                        
                        else if(primaryCode == codeleft_general3){
                        	swipekeyboardleft_general3();
                        	
                        	
                        }
                        
                        else if(primaryCode == codeleft_general4){
                        	swipekeyboardleft_general4();
                        	
                        }
                        else if(primaryCode == codeleft_general5){
                        	swipekeyboardleft_general5();
                        	
                        	
                        }
                        
                        else if(primaryCode == codeleft_general6){
                        	swipekeyboardleft_general6();
                        	
                        	
                        }
                  
                        else if(primaryCode == codeleft_general7){
                          	swipekeyboardleft_general7();
                          	
                          	
                          }
                          
                          else if(primaryCode == codeleft_general8){
                          	swipekeyboardleft_general8();
                          	
                          	
                          }
                          
                          else if(primaryCode == codeleft_general9){
                          	swipekeyboardleft_general9();
                          	
                          }
                          else if(primaryCode == codeleft_general10){
                          	swipekeyboardleft_general10();
                          	
                          	
                          }
                          
                          else if(primaryCode == codeleft_general11){
                          	swipekeyboardleft_general11();
                          	
                          	
                          }
                       
                          else if(primaryCode == codeleft_general12){
                            	swipekeyboardleft_general12();
                            	
                            	
                            }
                            
                            else if(primaryCode == codeleft_general13){
                            	swipekeyboardleft_general13();
                            	
                            }
                            else if(primaryCode == codeleft_general14){
                            	swipekeyboardleft_general14();
                            	
                            	
                            }
                            
                            else if(primaryCode == codeleft_general15){
                            	swipekeyboardleft_general15();
                            	
                            	
                            }
            
            
                            else if(primaryCode == codeleft_general16){
                            	swipekeyboardleft_general16();
                            	
                            	
                            }
            
            
                            else if(primaryCode == codeleft_general17){
                            	swipekeyboardleft_general17();
                            	
                            	
                            }
                
                
            
            
            
            
            
            
            else
            { 
            	// insert character
                editable.insert(start, Character.toString((char) primaryCode));
                MainActivity.uni =String.valueOf(primaryCode);
                Log.d("minaSamirInsert",MainActivity.uni);
                MainActivity.sentence.add(MainActivity.ed.getSelectionEnd()-1, MainActivity.uni);
            }
            
            
        }



		@Override 
        public void swipeDown() 
        {
        	
        	MainActivity.down();
            
        }
        
        @Override 
        public void swipeLeft() 
        {
      }
       

      	
	
	
      
		@Override 
        public void swipeRight() 
        {
        	 }
        	
		
        	 /**
             * Called when the user quickly moves the finger from left to right.
             */
       

        @Override 
        public void swipeUp() 
        {
        	
        	MainActivity.up();
        }

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPress(int primaryCode) {
			
			// TODO Auto-generated method stub
			
		}
    };

    /**
     * Create a custom keyboard, that uses the KeyboardView (with resource id <var>viewid</var>) of the <var>host</var> activity,
     * and load the keyboard layout from xml file <var>layoutid</var> (see {@link Keyboard} for description).
     * Note that the <var>host</var> activity must have a <var>KeyboardView</var> in its layout (typically aligned with the bottom of the activity).
     * Note that the keyboard layout xml file may include key codes for navigation; see the constants in this class for their values.
     * Note that to enable EditText's to use this custom keyboard, call the {@link #registerEditText(int)}.
     *
     * @param host The hosting activity.
     * @param viewid The id of the KeyboardView.
     * @param layoutid The id of the xml file containing the keyboard layout.
     */
    public CustomKeyboard(Activity host, int viewid, int layoutid) 
    {
    	this.layoutid=layoutid;
    	this.viewid=viewid;
    	mHostActivity= host;
        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        if (viewid==R.id.keyboardview)
        {
        	 mKeyboardView.setPreviewEnabled(true);	
        		
        	 
        }
        if (viewid==R.id.keyboardview2)
        {
        	 mKeyboardView.setPreviewEnabled(true);	
        }
        
        /*
        else{  
             mKeyboardView.setPreviewEnabled(true); // NOTE Do not show the preview balloons
            }*/   
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    
    /////////////////////////////////

    public void CustomKeyboardChange( Activity host, int viewid, int layoutid) 
    {
    	
    	this.layoutid=layoutid;
    	mHostActivity= host;
        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        if (viewid==R.id.keyboardview)
        {
        	 mKeyboardView.setPreviewEnabled(true);	
        }
        if (viewid==R.id.keyboardview2)
        {
        	 mKeyboardView.setPreviewEnabled(true);	
        }
       
        
        
        /*else{  
             mKeyboardView.setPreviewEnabled(true); // NOTE Do not show the preview balloons
            } */ 
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    
    
    
    
    public int getviewid()
    {
    	return this.viewid;
    }
    
    public int getkeyboardid()
    {
    	return this.layoutid;
    }
   
    /////////////////////////////////
    //category numbers
    public void swipeKeyboardL1()
    {
		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.numbers);
		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabnum);
    }
    
    //category family
    public void swipeKeyboardL2()
    {
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.family);
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabfam);
    }
    
    //category letters
    public void swipeKeyboardL3()
    {
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.letters);
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tablet);
    }
    
    //category home
    public void swipeKeyboardL4()
    {
        CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.home_one);
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabhome_one);
    }
    //category arts
    public void swipeKeyboardL5()
    {
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.arts);
		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabart);
    }
   
  //category colors
    public void swipeKeyboardL6()
    {
  	   
		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.colors);
		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabcolor);

    }
    
 
    
  //category direction
    public void swipeKeyboardL7(){
   	   
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.directions);
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabdirct);

 }
    
 
  //category food
    public void swipeKeyboardL8(){
   	   
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.food_one);
 
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabfood_one);
}
    

    public void swipeKeyboardL9(){
    	   
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.jobs);
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabjob);

    }
     
   
    public void swipeKeyboardL10(){
    	   
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.events);
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabevent);
 }
   
    public void swipeKeyboardL11(){
    	   
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview,R.xml.sport);
 		CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabsport);
    
    }
    
 
    
  //category general
    public void swipeKeyboardL12(){
  	  CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general_one);
  	  CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabgeneral_one);
    }
 

    public void swipeKeyboardLeft_1()
    {
    	//
    	
    }
    
    public void swipeKeyboardLeft_2()
    {
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabnon1);
    	
    }
    public void swipeKeyboardLeft_3()
    {
    	CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabnon2);
    	
    }
  
    
    
    
    public void swipeKeyboardRight_1()
    {CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabnon2);
	}
    

    public void swipeKeyboardRight_2()
    {CustomKeyboardChange(mHostActivity,R.id.keyboardview2,R.xml.tabnon3);
	}

    public void swipeKeyboardRight_3()
    {
    	//
	}
    
  
    
    
//###################swipe(family)left####################################//
    
    public void swipekeyboardleft_fam1(){
    	//
    	
    }

    public void swipekeyboardleft_fam2(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.family);
    }
    public void swipekeyboardleft_fam3(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam1);
    }
    
 public void swipekeyboardleft_fam4(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam2);
    }
    public void swipekeyboardleft_fam5(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam3);
    }
    
public void swipekeyboardleft_fam6(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam4);
    }
    //####################swipe(family)right####################################//
    
    public void swipekeyboardright_fam1(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam1);
    }
    
    
 public void swipekeyboardright_fam2(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam2);
    }
 public void swipekeyboardright_fam3(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam3);
 }
    
 public void swipekeyboardright_fam4(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam4);
 }
 
 public void swipekeyboardright_fam5(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.fam5);
	 }
	    
	 public void swipekeyboardright_fam6(){
	 	
	 	//
	 }
	 
    
    
  
    
    
    
    //###################swipe(arts)left####################################//
    
    public void swipekeyboardleft_arts1(){
    	//
    	
    }
    
    
 public void swipekeyboardleft_arts2(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts);
    }
 public void swipekeyboardleft_arts3(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts1);
 }
    
 public void swipekeyboardleft_arts4(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts2);
 }


 
//####################swipe(arts)right####################################//
    
    public void swipekeyboardright_arts1(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts1);
    }
    
    
 public void swipekeyboardright_arts2(){
    	
    	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts2);
    }
 public void swipekeyboardright_arts3(){
 	
	 CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.arts3);
 }
 
 public void swipekeyboardright_arts4(){
	 	
	 //
 }
 
 
  
//###################swipe(colors)left####################################//
 
 public void swipekeyboardleft_colors1(){
 	//
 	
 }
 
 
public void swipekeyboardleft_colors2(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.colors);
 }



//####################swipe(colors)right####################################//
 
 public void swipekeyboardright_colors1(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.colors1);
 }
 
 
public void swipekeyboardright_colors2(){
 	
 	//
 }

    
    
//###################swipe(directions)left####################################//

public void swipekeyboardleft_direc1(){
	//
	
}


public void swipekeyboardleft_direc2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.directions);
}
public void swipekeyboardleft_direc3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.direc1);
}

public void swipekeyboardleft_direc4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.direc2);
}


//####################swipe(directions)right####################################//

public void swipekeyboardright_direc1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.direc1);
}


public void swipekeyboardright_direc2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.direc2);
}
public void swipekeyboardright_direc3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.direc3);
}

public void swipekeyboardright_direc4(){
	
//
}


//###################swipe(Events)left####################################//

public void swipekeyboardleft_events1(){
	//
	
}


public void swipekeyboardleft_events2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.events);
}
public void swipekeyboardleft_events3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.events1);
}


//####################swipe(Events)right####################################//

public void swipekeyboardright_events1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.events1);
}


public void swipekeyboardright_events2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.events2);
}
public void swipekeyboardright_events3(){
	
//
}
  
  


//###################swipe(jobs)left####################################//

public void swipekeyboardleft_jobs1(){
	//
	
}


public void swipekeyboardleft_jobs2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs);
}
public void swipekeyboardleft_jobs3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs1);
}

public void swipekeyboardleft_jobs4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs2);
}



//####################swipe(jobs)right####################################//

public void swipekeyboardright_jobs1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs1);
}


public void swipekeyboardright_jobs2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs2);
}
public void swipekeyboardright_jobs3(){
	
 CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.jobs3);
}

public void swipekeyboardright_jobs4(){
 	
 //
}


//###################swipe(Letters)left####################################//

public void swipekeyboardleft_letters1(){
	//
	
}


public void swipekeyboardleft_letters2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters);
}
public void swipekeyboardleft_letters3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters1);
}

public void swipekeyboardleft_letters4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters2);
}

public void swipekeyboardleft_letters5(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters3);
 }
    
 

//####################swipe(Letters)right####################################//

public void swipekeyboardright_letters1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters1);
}


public void swipekeyboardright_letters2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters2);
}
public void swipekeyboardright_letters3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters3);
}

public void swipekeyboardright_letters4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.letters4);
}

public void swipekeyboardright_letters5(){
 	
 	//
 }
    
 



//###################swipe(Sports)left####################################//

public void swipekeyboardleft_sport1(){
	//
	
}


public void swipekeyboardleft_sport2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport);
}
public void swipekeyboardleft_sport3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport1);
}

public void swipekeyboardleft_sport4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport2);
}

public void swipekeyboardleft_sport5(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport3);
}

public void swipekeyboardleft_sport6(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport4);
}
  


//####################swipe(Sports)right####################################//

public void swipekeyboardright_sport1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport1);
}


public void swipekeyboardright_sport2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport2);
}
public void swipekeyboardright_sport3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport3);
}

public void swipekeyboardright_sport4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sport4);
}

public void swipekeyboardright_sport5(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.sports5);
}


public void swipekeyboardright_sport6(){
	
	//
}





//###################swipe(Food)left####################################//

public void swipekeyboardleft_food1(){
	//
	
}


public void swipekeyboardleft_food2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food_one);
}
public void swipekeyboardleft_food3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food1);
}

public void swipekeyboardleft_food4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food2);
}

public void swipekeyboardleft_food5(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food3);
 }
    
 public void swipekeyboardleft_food6(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food4);
 }
 
 
 
 public void swipekeyboardleft_food7(){
		
		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food5);
	}

	public void swipekeyboardleft_food8(){
		
		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food6);
	}

	public void swipekeyboardleft_food9(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food7);
	 }
	    
	 public void swipekeyboardleft_food10(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food8);
	 }
	 
	 public void swipekeyboardleft_food11(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food9);
		 }


//####################swipe(Food)right####################################//

public void swipekeyboardright_food1(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food1);
}


public void swipekeyboardright_food2(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food2);
}
public void swipekeyboardright_food3(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food3);
}

public void swipekeyboardright_food4(){
	
	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food4);
}

public void swipekeyboardright_food5(){
 	
 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food5);
 }
    
 public void swipekeyboardright_food6(){
 	
	 CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food6);
 }
 
 
 
 public void swipekeyboardright_food7(){
		
		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food7);
	}
	public void swipekeyboardright_food8(){
		
		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food8);
	}

	public void swipekeyboardright_food9(){
		
		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food9);
	}

	public void swipekeyboardright_food10(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.food10);
	 }
	    
	 public void swipekeyboardright_food11(){
	 	
		 //
	 }
	 
 

	//###################swipe(Home)left####################################//

	 public void swipekeyboardleft_home1(){
	 	//
	 	
	 }


	 public void swipekeyboardleft_home2(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home_one);
	 }
	 public void swipekeyboardleft_home3(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home1);
	 }

	 public void swipekeyboardleft_home4(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home2);
	 }

	 public void swipekeyboardleft_home5(){
	  	
	  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home3);
	  }
	     
	  public void swipekeyboardleft_home6(){
	  	
	  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home4);
	  }
	  
	  
	  
	  public void swipekeyboardleft_home7(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home5);
	 	}

	 	public void swipekeyboardleft_home8(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home6);
	 	}

	 	public void swipekeyboardleft_home9(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home7);
	 	 }
	 	    
	 	 public void swipekeyboardleft_home10(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home8);
	 	 }
	 	 
	 	 public void swipekeyboardleft_home11(){
	 		 	
	 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home9);
	 		 }
	 	 
	 	 
	 	 
       public void swipekeyboardleft_home12(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home10);
	 	}

	 	public void swipekeyboardleft_home13(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home11);
	 	 }
	 	    
	 	 public void swipekeyboardleft_home14(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home12);
	 	 }
	 	 
	 	 public void swipekeyboardleft_home15(){
	 		 	
	 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home13);
	 		 }


	 //####################swipe(Home)right####################################//

	 public void swipekeyboardright_home1(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home1);
	 }


	 public void swipekeyboardright_home2(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home2);
	 }
	 public void swipekeyboardright_home3(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home3);
	 }

	 public void swipekeyboardright_home4(){
	 	
	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home4);
	 }

	 public void swipekeyboardright_home5(){
	  	
	  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home5);
	  }
	     
	  public void swipekeyboardright_home6(){
	  	
	 	 CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home6);
	  }
	  
	  
	  
	  public void swipekeyboardright_home7(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home7);
	 	}
	 	public void swipekeyboardright_home8(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home8);
	 	}

	 	public void swipekeyboardright_home9(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home9);
	 	}

	 	public void swipekeyboardright_home10(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home10);
	 	 }
	 	    
	 	 public void swipekeyboardright_home11(){
	 	 	
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home11);
	 	 }
	 	 
	 	 
public void swipekeyboardright_home12(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home12);
	 	}

	 	public void swipekeyboardright_home13(){
	 		
	 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home13);
	 	}

	 	public void swipekeyboardright_home14(){
	 	 	
	 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.home14);
	 	 }
	 	    
	 	 public void swipekeyboardright_home15(){
	 	 	
	 		//
	 	 }
	 	 
	  

	
        
	 	//###################swipe(General)left####################################//

		 public void swipekeyboardleft_general1(){
		 	//
		 	
		 }


		 public void swipekeyboardleft_general2(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general_one);
		 }
		 public void swipekeyboardleft_general3(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general1);
		 }

		 public void swipekeyboardleft_general4(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general2);
		 }

		 public void swipekeyboardleft_general5(){
		  	
		  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general3);
		  }
		     
		  public void swipekeyboardleft_general6(){
		  	
		  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general4);
		  }
		  
		  
		  
		  public void swipekeyboardleft_general7(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general5);
		 	}

		 	public void swipekeyboardleft_general8(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general6);
		 	}

		 	public void swipekeyboardleft_general9(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general7);
		 	 }
		 	    
		 	 public void swipekeyboardleft_general10(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general8);
		 	 }
		 	 
		 	 public void swipekeyboardleft_general11(){
		 		 	
		 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general9);
		 		 }
		 	 
		 	 
		 	 
	       public void swipekeyboardleft_general12(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general10);
		 	}

		 	public void swipekeyboardleft_general13(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general11);
		 	 }
		 	    
		 	 public void swipekeyboardleft_general14(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general12);
		 	 }
		 	 
		 	 public void swipekeyboardleft_general15(){
		 		 	
		 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general13);
		 		 }
		 	 
		 	public void swipekeyboardleft_general16(){
	 		 	
	 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general14);
	 		 }
		 	
		 	
            public void swipekeyboardleft_general17(){
	 		 	
	 		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general15);
	 		 }


		 //####################swipe(Home)right####################################//

		 public void swipekeyboardright_general1(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general1);
		 }


		 public void swipekeyboardright_general2(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general2);
		 }
		 public void swipekeyboardright_general3(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general3);
		 }

		 public void swipekeyboardright_general4(){
		 	
		 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general4);
		 }

		 public void swipekeyboardright_general5(){
		  	
		  	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general5);
		  }
		     
		  public void swipekeyboardright_general6(){
		  	
		 	 CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general6);
		  }
		  
		  
		  
		  public void swipekeyboardright_general7(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general7);
		 	}
		 	public void swipekeyboardright_general8(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general8);
		 	}

		 	public void swipekeyboardright_general9(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general9);
		 	}

		 	public void swipekeyboardright_general10(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general10);
		 	 }
		 	    
		 	 public void swipekeyboardright_general11(){
		 	 	
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general11);
		 	 }
		 	 
		 	 
	public void swipekeyboardright_general12(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general12);
		 	}

		 	public void swipekeyboardright_general13(){
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general13);
		 	}

		 	public void swipekeyboardright_general14(){
		 	 	
		 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general14);
		 	 }
		 	    
		 	 public void swipekeyboardright_general15(){
		 	 	
		 		
			 	 	CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general15);
		 	 }
		 	 
		 	 
		 	public void swipekeyboardright_general16(){
		 	 	
		 		
		 		CustomKeyboardChange(mHostActivity, R.id.keyboardview, R.xml.general16);
	 	 }
		 	
      public void swipekeyboardright_general17(){
		 	 	
		 		
		 	 	//
	 	 }
		 	 
		 	 
    /** Returns whether the CustomKeyboard is visible. */
    public boolean isCustomKeyboardVisible() 
    {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /** Make the CustomKeyboard visible, and hide the system keyboard for view v. */
    public void showCustomKeyboard( View v ) 
    {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /** Make the CustomKeyboard invisible. */
    public void hideCustomKeyboard() 
    {
    	
    	
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
        
    }
    
    
    public void showKeyboard()
    {
    	mKeyboardView.setVisibility(View.VISIBLE);
    	mKeyboardView.setEnabled(true);
    }


    /**
     * Register <var>EditText<var> with resource id <var>resid</var> (on the hosting activity) for using this custom keyboard.
     *
     * @param resid The resource id of the EditText that registers to the custom keyboard.
     * 
     * 
     * 
     */
    
    
    public void registerEditText(int resid) 
    {
        // Find the EditText 'resid'
        EditText edittext= (EditText)mHostActivity.findViewById(resid);
        
        
        // Make the custom keyboard appear
        //OnFocusChangeListener r=new OnFocusChangeListener();
        edittext.setOnFocusChangeListener(new OnFocusChangeListener() 
        {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override 
            public void onFocusChange(View v, boolean hasFocus) 
            {
                if( hasFocus )
                	showKeyboard(); 
                else 
                	hideCustomKeyboard();
            }
        });
        
        
        edittext.setOnClickListener(new OnClickListener() 
        {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override 
            public void onClick(View v) 
            {
                MainActivity.up();
            }
        });
        
        
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new OnTouchListener() 
        {
            @Override 
            public boolean onTouch(View v, MotionEvent event) 
            {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }


    public KeyboardView getmKeyboardView() {
		return mKeyboardView;
	}

	public void setmKeyboardView(KeyboardView mKeyboardView) {
		this.mKeyboardView = mKeyboardView;
	}

}


// NOTE How can we change the background color of some keys (like the shift/ctrl/alt)?
// NOTE What does android:keyEdgeFlags do/mean





 