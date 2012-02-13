package com.PersonalHealthRecord;

import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EnterGlucose extends Activity{
	
	private int mYear;
    private int mMonth;
    private int mDay;
    private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://pancare.panhealth.com/test/Service.asmx";	
	private static final String SOAP_ACTION = "http://tempuri.org/UpdatePhr";
	private static final String METHOD_NAME = "UpdatePhr";
    static final int DATE_DIALOG_ID = 0;
	Button submit;	
	String array_spinner[];
	Spinner s;
	TextView dateDisplay;
	TextView mTimeDisplay;
	private int mHour;
    private int mMinute;
    String testCondition="B";
    TextView uieditbox;
    static final int TIME_DIALOG_ID = 1;
    Button back;
    ImageButton changeDate;
    ImageButton changeTime;
    Session ss = new Session();
    DBAdapter db = new DBAdapter(this);
    LinearLayout l11;
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.enter_glucose1);
        l11 = (LinearLayout)findViewById(R.id.l10);
        l11.setPadding(0, 20, 0, 0);
        
        work();
        
              
    }
    
    DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }
    
    void updateDisplay() {
    	dateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/")
                    .append(mDay).append("/")
                    .append(mYear).append(" "));
    }
    
    void updateDisplay1() {
    	String str = ":00";
    	/*if(mHour>12)
    	{
			mHour=mHour-12;
			str=":00 PM";
    	}*/
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)).append(str));
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay1();
            }
        };
    
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    void work()
    {
    	// In order to do any transactions with database.. Need to open the database..
    	db.open();
        
    	// Getting current MemberID for the purpose of transactions which was saved in a class called session..
    	String userID = ss.getSessionMemberID();
    	                      
        TextView userIDT = (TextView)findViewById(R.id.userID);
        userIDT.setText("Patient ID: "+userID);
        
        array_spinner=new String[4];
        array_spinner[0]="No Meal";
        array_spinner[1]="Post Meal";
        array_spinner[2]="Pre Meal";
        array_spinner[3]="Fasting";
   
        s = (Spinner) findViewById(R.id.test);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
         
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				back.setBackgroundResource(R.drawable.back_h);
				Intent i = new Intent(EnterGlucose.this, SelectOption.class);
				startActivity(i);
			}
		});  
        
        changeDate = (ImageButton)findViewById(R.id.changeDate);
        changeDate.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(DATE_DIALOG_ID);
			}
		});
        
        changeTime = (ImageButton)findViewById(R.id.changeTime);
        changeTime.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				showDialog(TIME_DIALOG_ID);
			}
		});
        
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
            
        mTimeDisplay = (TextView)findViewById(R.id.mTimeDisplay);
              
        // getting current date and time...
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Functions called in order to set current date and time as default...
        updateDisplay();
        updateDisplay1();
        
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new Button.OnClickListener() 
		{ public void onClick (View v)
			{ 
				submit.setBackgroundResource(R.drawable.submit_h);
				int index = s.getSelectedItemPosition();
				String item = (String) s.getItemAtPosition(index);
				if(item.equals("Post Meal"))
					testCondition="A";
				
				String date_time = dateDisplay.getText().toString()+""+mTimeDisplay.getText().toString();
				uieditbox = (EditText)findViewById(R.id.uieditbox); 
				System.out.println("Date: "+date_time+" test: "+testCondition+" editbox: "+uieditbox.getText().toString());
				
				String memId = ss.getSessionMemberID();
				
				submit.setBackgroundResource(R.drawable.submit);
				
				if(!((uieditbox.getText().toString()).equals("")))
				{	
				
				int value = Integer.parseInt(uieditbox.getText().toString());
				
				// Using Soap protocol in order to pass data to the webservice... 
				// In this.. Only data is sent.. No response handling done..
				
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        		
				// adding parameters to be passed...
				
        		request.addProperty("MemberID", memId); //memId.toString());
        		request.addProperty("DataType", "BG");
        		request.addProperty("ComDeviceID", "SPB502");
        		request.addProperty("ComDeviceSrNo", "NVX8696SPB");
        		request.addProperty("TstConFood", testCondition);
        		request.addProperty("Bg", uieditbox.getText().toString());
        		request.addProperty("dtReadingDT", date_time);// "01/27/2011 HH:MM:00 AM"
        		request.addProperty("Source", "Android");
        		request.addProperty("Method", "Web");
        		
        		SoapSerializationEnvelope envelope = 
        			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

        		envelope.setOutputSoapObject(request);
        		envelope.dotNet=true;
        		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
        		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        				
        		
        		
        		try {
        			
        			
        			androidHttpTransport.call(SOAP_ACTION, envelope);
        			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        			
        			// Since data uploaded successfully on site.. 
        			// Insert query is fired to local database with status - "y"
        			
        			long id = db.insertLog(memId, "BG", "SPB502", 
        					"NVX8696SPB", item, value,0,0,0,0,0,0,
        					"",0,"",date_time, "y");
    				
        			System.out.println("Inserted: "+memId+ " BG , SPB502, VX8696SPB"+ item +","+ value+","+date_time+", y");
        			
    				if(id>0)
    					System.out.println("Added Successfully!");
    				else
    					System.out.println("Failed to Add!");        			
        			
    				// Reset all controls.. So that new data can be entered..
    				
        			TextView text = (TextView)findViewById(R.id.text);
					text.setText("Data Uploaded Successfully!!");
					uieditbox.setText("");
					final Calendar c = Calendar.getInstance();
			        mYear = c.get(Calendar.YEAR);
			        mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        mHour = c.get(Calendar.HOUR_OF_DAY);
			        mMinute = c.get(Calendar.MINUTE);

			        updateDisplay();
			        updateDisplay1();
        		
        		} catch (Exception e) {

        			// Since data not uploaded successfully on site.. 
        			// Insert query is fired to local database with status - "n"
        			
        			long id = db.insertLog(memId, "BG", "SPB502", 
        					"NVX8696SPB", item, value,0,0,0,0,0,0,
        					"",0,"",date_time, "n");
    				
        			
        			
    				if(id>0)
    					System.out.println("Added Successfully!");
    				else
    					System.out.println("Failed to Add!");
        			
    				// Reset all controls.. So that new data can be entered..
    				
					TextView text = (TextView)findViewById(R.id.text);
					text.setText("Sorry for inconvenience..\nDue to some problem, Data added to local database..");
					uieditbox.setText("");
					final Calendar c = Calendar.getInstance();
			        mYear = c.get(Calendar.YEAR);
			        mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        mHour = c.get(Calendar.HOUR_OF_DAY);
			        mMinute = c.get(Calendar.MINUTE);

			        updateDisplay();
			        updateDisplay1();
        			e.printStackTrace();
        		}

					
				}
				else
				{
					// Error message prompted if complete and necessary data not entered.. 
					TextView text = (TextView)findViewById(R.id.text);
					text.setText("Data not Entered!!");
				}
           	 
			
				
			}
		});
    }
    
}
