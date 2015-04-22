package com.dev.callofbeer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dev.callofbeer.activities.CallOfBeerActivity;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import com.dev.callofbeer.R;

import java.util.Calendar;

/**
 * Created by matth on 04/03/15.
 */
public class CreateEventFragment extends Fragment implements OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    public Button butDate, butTime;
    NumberPicker np;
    ToggleButton tb;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.view_create_event, container, false);

        np = new NumberPicker(getActivity());
        np = (NumberPicker) view.findViewById(R.id.numpicker);
        np.setMinValue(0);
        np.setMaxValue(50);

        tb = new ToggleButton(getActivity());
        tb = (ToggleButton) view.findViewById(R.id.create_event_visibility_TB);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        butDate = (Button) view.findViewById(R.id.dateButton);
        int month = Calendar.MONTH;
        month++;
        butDate.setText(calendar.get(Calendar.DAY_OF_MONTH ) +"/"+ month +"/"+calendar.get(Calendar.YEAR));
        butDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        butTime = (Button) view.findViewById(R.id.timeButton);
        butTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + "H" + String.format("%02d", calendar.get(Calendar.MINUTE)));
        butTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(true);
                timePickerDialog.setCloseOnSingleTapMinute(true);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });
        if (savedInstanceState != null){
            DatePickerDialog dpd = (DatePickerDialog) getActivity().getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
            TimePickerDialog tpd = (TimePickerDialog) getActivity().getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        month++;
        butDate.setText(day + "/" + month + "/" + year);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        butTime.setText(hourOfDay + "H" + String.format("%02d", minute));
    }
}
