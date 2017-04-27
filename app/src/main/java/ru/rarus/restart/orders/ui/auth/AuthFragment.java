package ru.rarus.restart.orders.ui.auth;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rarus.restart.orders.R;
import ru.rarus.restart.orders.ui.MainActivity;
import ru.rarus.restart.orders.ui.abscracts.BaseFragment;
import ru.rarus.restart.orders.ui.settings.SettingsActivity;
import ru.rarus.restart.orders.ui.util.TransitionHelper;

import static ru.rarus.restart.orders.ui.MainActivity.IS_FIRST;

public class AuthFragment extends BaseFragment implements AuthView {

    @BindView(R.id.auth_toolbar)
    Toolbar authToolbar;
    @BindView(R.id.ivAppIcon)
    ImageView ivAppIcon;
    @BindView(R.id.tvAppName)
    TextView tvAppName;

    @BindView(R.id.confirm)
    ImageView confirm;

    @BindView(R.id.keyboard_one)
    TextView keyboardOne;
    @BindView(R.id.keyboard_two)
    TextView keyboardTwo;
    @BindView(R.id.keyboard_three)
    TextView keyboardThree;
    @BindView(R.id.keyboard_four)
    TextView keyboardFour;
    @BindView(R.id.keyboard_five)
    TextView keyboardFive;
    @BindView(R.id.keyboard_six)
    TextView keyboardSix;
    @BindView(R.id.keyboard_seven)
    TextView keyboardSeven;
    @BindView(R.id.keyboard_eight)
    TextView keyboardEight;
    @BindView(R.id.keyboard_nine)
    TextView keyboardNine;
    @BindView(R.id.keyboard_zero)
    TextView keyboardZero;
    @BindView(R.id.keyboard_cancel)
    TextView keyboardCancel;

    @BindView(R.id.input_pass)
    TextView inputPass;

    @BindView(R.id.demo)
    FrameLayout demo;

    private AuthPresenter mPresenter;
    private Animation progressAnim;
    private Boolean mProgress = false;

    @BindView(R.id.fragment_autorization_progress_bar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new AuthPresenter();
        progressAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotation360);
        progressAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (mProgress) mKnifeAndFork.startAnimation(progressAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirm.setOnClickListener(v -> mPresenter.signIn());
        demo.setOnClickListener( v -> mPresenter.signInDemo());
        authToolbar.inflateMenu(R.menu.menu_auth);
        authToolbar.setOnMenuItemClickListener(m -> {
            if (m.getItemId() == R.id.action_settings) mPresenter.onSettingsClick();
            return false;
        });

        keyboardOne.setOnClickListener(this::numberOnClick);
        keyboardTwo.setOnClickListener(this::numberOnClick);
        keyboardThree.setOnClickListener(this::numberOnClick);
        keyboardFour.setOnClickListener(this::numberOnClick);
        keyboardFive.setOnClickListener(this::numberOnClick);
        keyboardSix.setOnClickListener(this::numberOnClick);
        keyboardSeven.setOnClickListener(this::numberOnClick);
        keyboardEight.setOnClickListener(this::numberOnClick);
        keyboardNine.setOnClickListener(this::numberOnClick);
        keyboardZero.setOnClickListener(this::numberOnClick);
        keyboardCancel.setOnClickListener(v -> mPresenter.cancelClicked());

        mPresenter.setView(this);
        mPresenter.showLoginPass();

    }

    private void numberOnClick(View view) {

        if(!mProgress) {
            String numberClicked = ((TextView) view).getText().toString();
            mPresenter.numberClicked(numberClicked);
        }

    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(IS_FIRST, true);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void startSettingsFragment() {


//        Intent intent = new Intent(getContext(), SettingsActivity.class);
//       startActivity(intent);

        transitionToActivity(SettingsActivity.class);
    }


    private void transitionToActivity(Class target) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
        startActivity(target, pairs);
    }


    private void startActivity(Class target, Pair<View, String>[] pairs) {
        Intent i = new Intent(getActivity(), target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);

        startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public void showInputPass(String mLogin) {

        inputPass.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_grey_600));
        if (mLogin.equals("")) {

            confirmDropOut(false);
            inputPass.setText(getResources().getString(R.string.text_input_pass));

        } else {

            confirmDropOut(true);
            inputPass.setText(mLogin);

        }

    }

    @Override
    public void setProgress(boolean progress) {
        mProgress = progress;

        if (progress)
            confirmDropOut(false);
        else
            progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        inputPass.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_red_400));
        inputPass.setText(message);
    }

    private void confirmDropOut(boolean reverse) {

        // не запускаем ту же анимацию, если она уже запущена
        if (reverse && confirm.getScaleX() > 0 || !reverse && confirm.getScaleX() < 1) return;

        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                reverse ? R.animator.dropin : R.animator.dropout);


        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(mProgress) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.setTarget(confirm);

        set.start();

    }
}
