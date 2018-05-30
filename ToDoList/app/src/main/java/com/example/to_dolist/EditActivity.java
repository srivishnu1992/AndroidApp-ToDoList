package com.example.to_dolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    String tasktitle;
    String taskdate;
    String tasktime;
    int taskNum;
    String selectedPriority;
    EditText title;
    static EditText edate;
    static EditText etime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle("Edit Task");

        title= findViewById(R.id.etTitle);
       edate= findViewById(R.id.etDate);
        etime= findViewById(R.id.etTime);

        RadioGroup priority=  findViewById(R.id.radioGroup);

        if(getIntent()!=null&&getIntent().getExtras()!=null)
        {
            Task task= (Task) getIntent().getExtras().getSerializable((MainActivity.TASK_KEY));
            title.setText(task.taskTitle);
            edate.setText(task.Date);
            etime.setText(task.time);
            taskNum=task.taskNum;
            Log.d("priority",task.priority);
            if(task.priority.equals("Low"))
            {
                priority.check(R.id.rbLow);
            }
            else if(task.priority.equals("Medium"))
            {
                priority.check(R.id.rbMedium);
                Log.d("priority",task.priority+"entered loop");
            }
            else if(task.priority.equals("high"))
            {
                priority.check(R.id.rbHigh);
            }
        }

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tasktitle=title.getText().toString();
                taskdate=edate.getText().toString();
                tasktime=etime.getText().toString();

                if(tasktitle.equals(""))
                    title.setError("Please enter title");
                if(taskdate.equals(""))
                    edate.setError("Please enter date");
                if(tasktime.equals(""))
                    etime.setError("Please enter time");

                RadioGroup priority=  findViewById(R.id.radioGroup);
                selectedPriority= ((RadioButton) findViewById(priority.getCheckedRadioButtonId())).getText().toString();
                Task task = new Task(tasktitle, taskdate, tasktime, taskNum,selectedPriority);
                if(tasktitle.equals("") || taskdate.equals("") || tasktime.equals("")) {
                    task = null;
                }
                if(task!=null) {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.EDIT_KEY, task);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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
        DialogFragment newFragment = new CreateTaskActivity.DatePickerFragment();
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
        DialogFragment newFragment = new CreateTaskActivity.TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}

