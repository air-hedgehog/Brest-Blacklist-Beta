package bignerdrunch.brestblacklistgen.new_crime;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;

public class NewCrimeActivity extends AppCompatActivity {

    private Button shareButton;

    private Toolbar toolbar;

    private EditText descriptionEditText;
    private EditText dateEditText;
    private EditText timeEditText;

    private TextInputLayout tilDescription;
    private TextInputLayout tilDate;
    private TextInputLayout tilTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_crime);

        initToolbar();
        shareButtonTrigger();
        dateTimeSetting();

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        shareButton = (Button)findViewById(R.id.share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Добавлено", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dateTimeSetting(){

        dateEditText =(EditText)findViewById(R.id.tilCrimeDate);
        timeEditText = (EditText)findViewById(R.id.tilCrimeTime);

        tilDate = (TextInputLayout)findViewById(R.id.tilCrimeDate_widget);
        tilTime = (TextInputLayout)findViewById(R.id.tilCrimeTime_widget);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        dateEditText.setText(Utils.getDate(dateCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dateEditText.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Calendar timeCalendar = Calendar.getInstance();

                        timeCalendar.set(0, 0, 0, hour, minute);
                        timeEditText.setText(Utils.getTime(timeCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        timeEditText.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

    }

    private void shareButtonTrigger(){

        descriptionEditText = (EditText)findViewById(R.id.tilCrimeDescription);
        tilDescription = (TextInputLayout)findViewById(R.id.tilCrimeDescription_widget);
        shareButton = (Button)findViewById(R.id.share_button);
        shareButton.setEnabled(false);
        tilDescription.setError(getString(R.string.error_empty_description));


        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0){
                    tilDescription.setErrorEnabled(false);
                    shareButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.new_crime_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Отменено", Toast.LENGTH_LONG).show();
            }
        });
    }
}
