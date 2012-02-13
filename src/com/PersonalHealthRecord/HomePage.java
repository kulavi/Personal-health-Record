package com.PersonalHealthRecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class HomePage extends Activity {
	
	TextView welcome;
	TextView userIDT;
	Button logout;
	Session ss = new Session();
	String userID="";
	String userName="";
	LinearLayout l1;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.welcome_user1);
        l1 = (LinearLayout)findViewById(R.id.l1);
        l1.setPadding(0, 20, 0, 0);
        
        work();
     
    }

    void work()
    {
    	// Getting current MemberID for the purpose of transactions which was saved in a class called session..
    	String userID = ss.getSessionMemberID();
    
         userIDT = (TextView)findViewById(R.id.userID);
         userIDT.setText("Patient ID: "+userID);
         
         userName = ss.getSessionMemberName();
         welcome = (TextView)findViewById(R.id.welcome);
         welcome.setText("Welcome "+userName+" !!");
              
         logout = (Button)findViewById(R.id.logout);
         logout.setOnClickListener(new Button.OnClickListener() 
 		{ public void onClick (View v)
 			{ 
 				logout.setBackgroundResource(R.drawable.logout_h);
 				Intent i = new Intent(HomePage.this, PersonalHealthRecord.class);
 				startActivity(i);
 			}
 		});        
         
         OnClickListener radio_listener = new OnClickListener() {
             public void onClick(View v) {
                 
                 RadioButton rb = (RadioButton) v;
                                 
                 if((rb.getText()).equals("Enter Data"))
                 {
                 	Intent i = new Intent(HomePage.this, SelectOption.class);
     				startActivity(i);
                 }
                 else if((rb.getText()).equals("View local Data"))
                 {
                 	Intent i = new Intent(HomePage.this, SelectOptionView.class);
     				startActivity(i);
                 }
                 else if((rb.getText()).equals("Sync Data to PanHealth"))
                 {
                 	Intent i = new Intent(HomePage.this, SyncDataToPanHealthOption.class);
     				startActivity(i);
                 }
             }
         };
         
         final RadioButton enter_data = (RadioButton) findViewById(R.id.enter_data);
         final RadioButton view_data = (RadioButton) findViewById(R.id.view_data);
         final RadioButton sync_data = (RadioButton) findViewById(R.id.sync_data);
                  
         enter_data.setOnClickListener(radio_listener);
         view_data.setOnClickListener(radio_listener);
         sync_data.setOnClickListener(radio_listener);
   }
   
  
}
