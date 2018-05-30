package com.example.to_dolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

public class CreateTaskActivity extends FragmentActivity  {
String tasktitle;
    String taskdate;
    String tasktime;
    int totalTasknum;
    String selectedPriority;

    EditText title;
    static EditText edate;
    static EditText etime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_task);
            setTitle("Create Task");
            title = findViewById(R.id.etTitle);
            edate = findViewById(R.id.etDate);
            etime = findViewById(R.id.etTime);
            edate.setKeyListener(null);


            if (getIntent() != null && getIntent().getExtras() != null) {
                totalTasknum = (int) getIntent().getExtras().getSerializable((MainActivity.TASK_KEY));
            }
            findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasktitle = title.getText().toString();
                    taskdate = edate.getText().toString();
                    tasktime = etime.getText().toString();

                    if(tasktitle.equals(""))
                        title.setError("Please enter title");
                    if(tasktitle.length()>20)
                        title.setError("Please enter less than 20 characters");

                    if(taskdate.equals(""))
                        edate.setError("Please enter date");

                    if(tasktime.equals(""))
                        etime.setError("Please enter time");



                    RadioGroup priority = findViewById(R.id.radioGroup);

                    selectedPriority = ((RadioButton) findViewById(priority.getCheckedRadioButtonId())).getText().toString();
                    Task task= new Task(tasktitle, taskdate, tasktime, ++totalTasknum, selectedPriority);
                    Log.d("task", "onClick:" + task.toString());
                    if(tasktitle.equals("") || taskdate.equals("") || tasktime.equals("") || tasktitle.length()>20) {
                        task = null;
                        --totalTasknum;
                    }

                    if (task != null) {
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.CREATE_KEY, task);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                   /* else {
                        setResult(RESULT_CANCELED);
                    } */

                }
            });
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
        }
    }


        public static class DatePickerFragment extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current date as the default date in the picker
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {
                edate.setText((month+1)+"/"+day+"/"+year);
            }
        }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            String timeSet = "";
            if (hourOfDay > 12) {
                hourOfDay -= 12;
                timeSet = "PM";
            } else if (hourOfDay == 0) {
                hourOfDay += 12;
                timeSet = "AM";
            } else if (hourOfDay == 12){
                timeSet = "PM";
            }else{
                timeSet = "AM";
            }

            String min = "";
            if (minute < 10)
                min = "0" + minute ;
            else
                min = String.valueOf(minute);

            // Append in a StringBuilder
            String aTime = new StringBuilder().append(hourOfDay).append(':')
                    .append(min ).append(" ").append(timeSet).toString();
            etime.setText(aTime);
        }

    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    }









