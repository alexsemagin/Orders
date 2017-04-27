package ru.rarus.restart.orders.ui.about_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.BuildConfig;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;


public class AboutFragment extends BaseFragment implements AboutView {
    private AboutPresenter presenter;

    String versionName = BuildConfig.VERSION_NAME;
    private final static String URI = "market://search?q=pub:1C-Rarus+Ltd.";


    @BindView(R.id.rarus_apps_clickable)
    LinearLayout llAps;

    @BindView(R.id.call_rarus_clickable)
    LinearLayout llCall;

    @BindView(R.id.mail_rarus_clickable)
    LinearLayout llMail;

    @BindView(R.id.app_version)
    TextView tvVersion;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new AboutPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_about_app, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar;
        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.title_about_app);
        toolbar.getMenu().clear();
        if (getActivity() instanceof AboutActivity) {
            toolbar.setNavigationIcon(R.drawable.vector_arrow_back);
            toolbar.setNavigationOnClickListener(mView -> getActivity().onBackPressed());
        }

        tvVersion.setText(versionName);
        setUpListeners();
        presenter.setView(this);
    }

    private void setUpListeners() {
        llAps.setOnClickListener(this::showApps);
        llCall.setOnClickListener(this::call);
        llMail.setOnClickListener(this::mail);
    }

    private void showApps(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(URI));
        startActivity(intent);
    }

    private void call(View v) {
        String phone = ((TextView) v.findViewById(R.id.contact_phone)).getText().toString();
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } catch (RuntimeException e) {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        }
    }

    private void mail(View v) {
        String email = ((TextView) v.findViewById(R.id.contact_email)).getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.about_app_write_email)));
    }

}
