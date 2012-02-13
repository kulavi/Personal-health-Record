package com.PersonalHealthRecord;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectOption extends Activity{
	
	Button option;
	Button back;
	Session ss = new Session();
	LinearLayout l11;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.select_option1);
        l11 = (LinearLayout)findViewById(R.id.l10);
        l11.setPadding(0, 20, 0, 0);
       
        work();
        
    }

    void work()
    {
    	// Getting current MemberID for the purpose of transactions which was saved in a class called session..
    	String userID = ss.getSessionMemberID();
    	
        TextView userIDT = (TextView)findViewById(R.id.userID);
        userIDT.setText("Patient ID: "+userID);
       
        
        ImageButton glucose = (ImageButton)findViewById(R.id.glucose);
        glucose.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				Intent i = new Intent(SelectOption.this, EnterGlucose.class);
				startActivity(i);
			}
		});
        
        ImageButton pressure = (ImageButton)findViewById(R.id.pressure);
        pressure.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				Intent i = new Intent(SelectOption.this, EnterBP.class);
				startActivity(i);
			}
		});
        
        ImageButton weight = (ImageButton)findViewById(R.id.weight);
        weight.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
			Intent i = new Intent(SelectOption.this, EnterWeight.class);
			startActivity(i);
			}
		});
        
        ImageButton HbA1c = (ImageButton)findViewById(R.id.HbA1c);
        HbA1c.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				Intent i = new Intent(SelectOption.this, EnterHbA1c.class);
				startActivity(i);
			}
		});
        
        ImageButton hemoglobin = (ImageButton)findViewById(R.id.hemoglobin);
        hemoglobin.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				Intent i = new Intent(SelectOption.this, EnterHemoglobin.class);
				startActivity(i);
			}
		});
        
      
        
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(SelectOption.this, HomePage.class);
				startActivity(i);
			}
		});
        
       
    }
    
   

}
