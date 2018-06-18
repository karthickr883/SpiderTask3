package com.example.karthik.spider3;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    EditText task1;
    Button add;
    DatabaseHelper databaseHelper;
    ArrayList<Data> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        list = (ListView) findViewById(R.id.tasks);
        Cursor initialData = databaseHelper.getData();
        while (initialData.moveToNext()) {
            tasks.add(new Data(initialData.getInt(0), initialData.getString(1)));
        }
        custom initialObj = new custom(this, R.layout.singlerow, tasks);
        list.setAdapter(initialObj);
        task1 = (EditText) findViewById(R.id.task);
        add = (Button) findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = task1.getText().toString();
                databaseHelper.addData(newTask);
                Cursor data = databaseHelper.getData();
                tasks.clear();
                while (data.moveToNext()) {
                    tasks.add(new Data(data.getInt(0), data.getString(1)));
                }
                custom adapter = new custom(MainActivity.this, R.layout.singlerow, tasks);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setMessage("Are u done with this task ?");
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Data delete = tasks.get(position);
                                int id = delete.getId();
                                databaseHelper.deleteData(id);
                                tasks.remove(position);
                                custom deletedList = new custom(MainActivity.this, R.layout.singlerow, tasks);
                                list.setAdapter(deletedList);
                            }
                        });
                        dialog.show();
                    }
                });
                task1.setText("");
            }
        });
    }

    private class custom extends ArrayAdapter<Data> {

        public custom(@NonNull Context context, @LayoutRes int resource, @NonNull List<Data> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater layoutInflater = getLayoutInflater();
                row = layoutInflater.inflate(R.layout.singlerow, parent, false);
            }
            Data currentTask = getItem(position);
            final TextView text, task;
            task = (TextView) row.findViewById(R.id.taske);
            task.setText("Task " + (position + 1));
            text = (TextView) row.findViewById(R.id.textView);
            Log.d("CHECKING   ", "Inside get view");
            text.setText(currentTask.getString());
            ImageButton del = (ImageButton) row.findViewById(R.id.button);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setMessage("Are u done with this task ?");
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Data delete = tasks.get(position);
                            int id = delete.getId();
                            databaseHelper.deleteData(id);
                            tasks.remove(position);
                            custom deletedList = new custom(MainActivity.this, R.layout.singlerow, tasks);
                            list.setAdapter(deletedList);
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();

                    task1.setText("");

                }
            });
            return row;

        }
    }
}

