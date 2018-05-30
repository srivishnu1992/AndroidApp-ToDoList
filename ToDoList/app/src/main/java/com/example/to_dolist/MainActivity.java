package com.example.to_dolist;

import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    static final int REQ_CODE=100;
    static final int EDIT_CODE=101;

    static final String TASK_KEY="task";
    static final String CREATE_KEY="create";
    static final String EDIT_KEY="create";
    LinkedList taskList=new LinkedList();
    int totalTasknum;
    TextView taskTitle;
    TextView taskDate;
    TextView taskTime;
    TextView index;
    TextView priority;
    int ind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            taskTitle = (TextView) findViewById(R.id.tvTasktitle);
            taskDate = (TextView) findViewById(R.id.tvTaskdate);
            taskTime = (TextView) findViewById(R.id.tvTasktime);
            priority = (TextView) findViewById(R.id.tvTaskpriority);
            index = (TextView) findViewById(R.id.tvTasknum);

            if(taskList.size()<=0)
                taskTitle.setText("No tasks scheduled");

            ImageButton ibCreatetask = (ImageButton) findViewById(R.id.ibCreatetask);
            ibCreatetask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                    totalTasknum = taskList.size();
                    intent.putExtra(TASK_KEY, totalTasknum);
                    startActivityForResult(intent, REQ_CODE);


                }
            });

            ImageButton ibEdittask = (ImageButton) findViewById(R.id.ibEdittask);
            ibEdittask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    Log.d("index", index.getText().toString());
                    ind = Integer.parseInt(index.getText().toString().substring(0, 1));
                    Task task = new Task(taskTitle.getText().toString(), taskDate.getText().toString(), taskTime.getText().toString(), ind, priority.getText().toString());
                    intent.putExtra(TASK_KEY, task);
                    startActivityForResult(intent, EDIT_CODE);


                }
            });

            ImageButton ibGotoprev = (ImageButton) findViewById(R.id.ibGotoprev);
            ibGotoprev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ind = Integer.parseInt(index.getText().toString().substring(0, 1));
                    if (ind - 2 >= 0) {
                        Task prevtask = (Task) taskList.get(ind - 2);
                        taskTitle.setText(prevtask.taskTitle);
                        taskDate.setText(prevtask.Date.toString());
                        taskTime.setText(prevtask.time);
                        totalTasknum = taskList.size();
                        priority.setText(prevtask.priority);
                        index.setText(prevtask.taskNum + " of " + totalTasknum);
                        TextView text = findViewById(R.id.textView6);
                        text.setText("Priority");
                    } else {
                        Toast.makeText(getApplicationContext(), "This is the first activity", Toast.LENGTH_SHORT).show();
                    }

                }

            });
            ImageButton ibGotonext = (ImageButton) findViewById(R.id.ibGotonext);
            ibGotonext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ind = Integer.parseInt(index.getText().toString().substring(0, 1));
                    Log.d("index", index.getText().toString().substring(0, 1));
                    if (ind < taskList.size()) {
                        Task nexttask = (Task) taskList.get(ind);
                        Log.d("tasknextb", nexttask.toString());
                        taskTitle.setText(nexttask.taskTitle);
                        taskDate.setText(nexttask.Date.toString());
                        taskTime.setText(nexttask.time);
                        totalTasknum = taskList.size();
                        priority.setText(nexttask.priority);
                        index.setText(nexttask.taskNum + " of " + totalTasknum);
                        TextView text = findViewById(R.id.textView6);
                        text.setText("Priority");
                    } else {
                        Toast.makeText(getApplicationContext(), "This is the last activity", Toast.LENGTH_SHORT).show();
                    }

                }

            });
            ImageButton ibGotolast = (ImageButton) findViewById(R.id.ibGotolast);
            ibGotolast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ind = Integer.parseInt(index.getText().toString().substring(0, 1));
                    totalTasknum = taskList.size();

                    Task lasttask = (Task) taskList.get(totalTasknum - 1);
                    taskTitle.setText(lasttask.taskTitle);
                    taskDate.setText(lasttask.Date.toString());
                    taskTime.setText(lasttask.time);
                    priority.setText(lasttask.priority);
                    index.setText(lasttask.taskNum + " of " + totalTasknum);
                    TextView text = findViewById(R.id.textView6);
                    text.setText("Priority");


                }

            });
            ImageButton ibGotofirst = (ImageButton) findViewById(R.id.ibGotofirst);
            ibGotofirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ind = Integer.parseInt(index.getText().toString().substring(0, 1));
                    totalTasknum = taskList.size();
                    Task firsttask = (Task) taskList.get(0);
                    taskTitle.setText(firsttask.taskTitle);
                    taskDate.setText(firsttask.Date.toString());
                    taskTime.setText(firsttask.time);
                    priority.setText(firsttask.priority);
                    index.setText(firsttask.taskNum + " of " + totalTasknum);
                    TextView text = findViewById(R.id.textView6);
                    text.setText("Priority");


                }

            });

            ImageButton ibdelete = (ImageButton) findViewById(R.id.ibDeletetask);
            ibdelete.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ind = Integer.parseInt(index.getText().toString().substring(0, 1));

                                                taskList.remove(ind - 1);
                                                Collections.sort(taskList, (Comparator) new DateComp());
                                                int i = taskList.size();
                                                while (i > ind - 1) {
                                                    Log.d("taskind", String.valueOf(i));
                                                    Task task = (Task) taskList.get(i - 1);
                                                    Log.d("task", task.toString());
                                                    task.taskNum = taskList.indexOf(task);
                                                    i--;
                                                }

                                                TextView text = findViewById(R.id.textView6);

                                                if (ind > 1) {
                                                    Task prevtask = (Task) taskList.get(ind - 2);
                                                    taskTitle.setText(prevtask.taskTitle);
                                                    taskDate.setText(prevtask.Date.toString());
                                                    taskTime.setText(prevtask.time);
                                                    totalTasknum = taskList.size();
                                                    priority.setText(prevtask.priority);
                                                    index.setText(prevtask.taskNum + " of " + totalTasknum);
                                                    text.setText("Priority");
                                                } else if(taskList.size()>0){
                                                    Task nexttask = (Task) taskList.get(ind);
                                                    taskTitle.setText(nexttask.taskTitle);
                                                    taskDate.setText(nexttask.Date.toString());
                                                    taskTime.setText(nexttask.time);
                                                    totalTasknum = taskList.size();
                                                    priority.setText(nexttask.priority);
                                                    index.setText(nexttask.taskNum + " of " + totalTasknum);
                                                    text.setText("Priority");

                                                }
                                                else {
                                                    taskTitle.setText("No tasks scheduled");
                                                    taskDate.setText("");
                                                    taskTime.setText("");
                                                    priority.setText("");
                                                    text.setText("");
                                                    index.setText("");
                                                }


                                            }

                                        }

            );
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception Occurred", Toast.LENGTH_SHORT).show();
        }

}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Task task = (Task) data.getExtras().getSerializable(CREATE_KEY);
                Log.d("onActi", task.toString());
                taskList.add(task);
                Collections.sort(taskList, (Comparator) new DateComp());
                for(int i=0;i<taskList.size();i++) {
                    Task task1 = (Task) taskList.get(i);
                    task1.setTaskNum(taskList.indexOf(task1)+1);

                }
                taskTitle.setText(task.taskTitle);
                taskDate.setText(task.Date.toString());
                taskTime.setText(task.time);
                totalTasknum=taskList.size();
                priority.setText(task.priority);
                index.setText(task.getTaskNum()+" of "+totalTasknum);
                TextView text=findViewById(R.id.textView6);
                text.setText("Priority");

            }
        }

        if (requestCode == EDIT_CODE) {
            if (resultCode == RESULT_OK) {
                Task task = (Task) data.getExtras().getSerializable(EDIT_KEY);
                Log.d("onActi", task.toString());
                taskList.set(task.taskNum-1,task);
                Collections.sort(taskList, (Comparator) new DateComp());
                Collections.sort(taskList, (Comparator) new DateComp());
                for(int i=0;i<taskList.size();i++) {
                    Task task1 = (Task) taskList.get(i);
                    task1.setTaskNum(taskList.indexOf(task1)+1);

                }
                taskTitle.setText(task.taskTitle);
                taskDate.setText(task.Date.toString());
                taskTime.setText(task.time);
                totalTasknum=taskList.size();
                priority.setText(task.priority);
                index.setText(task.getTaskNum()+" of "+totalTasknum);
                TextView text=findViewById(R.id.textView6);
                text.setText("Priority");

            }
        }
    }
}
