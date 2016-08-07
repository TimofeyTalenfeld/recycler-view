package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.R;

/**
 * Created by root on 8/2/16.
 */
public class SettingsDialog extends DialogFragment implements NumberPicker.OnValueChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.column_picker) NumberPicker columnPicker;
    @BindView(R.id.colors_group) RadioGroup colors;

    @State int columnSpan = 1;

    private Unbinder viewBinder;

    public static final int MIN_COLUMN_SPAN = 1;
    public static final int MAX_COLUMN_SPAN = 30;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_dialog, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
        getDialog().setTitle(getResources().getString(R.string.settings));

        initPicker();

        colors.setOnCheckedChangeListener(this);

        if(!(getActivity() instanceof SettingsListener)) {
            throw new IllegalStateException("Host activity must implement SettingsListener interface");
        }

    }

    private void initPicker() {
        columnPicker.setMinValue(MIN_COLUMN_SPAN);
        columnPicker.setMaxValue(MAX_COLUMN_SPAN);
        columnPicker.setValue(columnSpan);
        columnPicker.setOnValueChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        columnSpan = newVal;
        ((SettingsListener) getActivity()).onChangeColumnNumber(columnSpan);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ((SettingsListener) getActivity()).onChangeDecorationColor(((RadioButton)group.findViewById(checkedId)).getShadowColor());
    }

    public interface SettingsListener {
        void onChangeColumnNumber(int columnSpan);
        void onChangeDecorationColor(int color);
    }

}
