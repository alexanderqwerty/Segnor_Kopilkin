package com.example.segnorkopilkin;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;


public class AddTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePicker.OnDateChangedListener {
    Spinner spinner;
    DatePicker datePicker;
    AppCompatEditText editText_getter_sender, editText_sum;
    Button button;
    DBTransaction database;
    Date date = new Date();
    public static final String get = "От кого";
    public static final String spent = "Кому";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        database = new DBTransaction(this);
        initToolBar();
        initSpinner();
        datePicker = findViewById(R.id.date_picker);
        editText_getter_sender = findViewById(R.id.edit_text_getter_sender);
        editText_sum = findViewById(R.id.edit_text_sum);
        button = findViewById(R.id.button_add);
        button.setOnClickListener(this::onClick);
        spinner.setOnItemSelectedListener(this);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR) - 1900;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);
        datePicker.init(year, month, day, this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.addtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void initSpinner() {
        spinner = findViewById(R.id.bar_spinner);
        String[] data = new String[]{"Перевод", "Получить"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            editText_getter_sender.setHint(spent);
        } else if (position == 1) {
            editText_getter_sender.setHint(get);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        if (checkForFill()) {
            if (editText_getter_sender.getHint().equals(spent))
                database.insert(editText_getter_sender.getText().toString(), "Я", -Float.parseFloat(editText_sum.getText().toString()), date.getTime());
            else if (editText_getter_sender.getHint().equals(get))
                database.insert("Я", editText_getter_sender.getText().toString(), Float.parseFloat(editText_sum.getText().toString()), date.getTime());
            Snackbar.make(v,"Данные успешно добавлены",Snackbar.LENGTH_SHORT).show();

        }

    }

    private boolean checkForFill() {
        boolean flag = true;
        if (editText_getter_sender.getText().length() == 0)
            flag = false;
        if (editText_sum.getText().length() == 0 || Float.parseFloat(editText_sum.getText().toString()) <= 0) {
            flag = false;
        }
        return flag;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date.setYear(year-1900);
        date.setMonth(monthOfYear);
        date.setDate(dayOfMonth);
    }
}