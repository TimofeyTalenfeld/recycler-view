package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.fragments.SettingsDialog;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements SettingsDialog.SettingsListener {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private DialogFragment settingsDialog;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            settingsDialog = new SettingsDialog();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment(), ContentFragment.class.getCanonicalName())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                settingsDialog.show(getSupportFragmentManager(), settingsDialog.getClass().getCanonicalName());
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onChangeColumnNumber(int columnSpan) {
        Fragment fragment = (getSupportFragmentManager().findFragmentByTag(ContentFragment.class.getCanonicalName()));
        if(fragment != null) {
            ((ContentFragment) fragment).updateColumnSpan(columnSpan);
        }
    }
}
