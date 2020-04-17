package com.popular.baking.fragments;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.popular.baking.R;
import com.popular.baking.constants.Constants;
import com.popular.baking.databinding.FragmentDetailsBinding;
import com.popular.baking.dto.Steps;
import com.popular.baking.view.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public static final String TAG = DetailsFragment.class.getSimpleName();
    FragmentDetailsBinding mFragmentDetailsBinding;

    private int defaultAninamtion;

    //Video preferences
    private long postionResume;
    private int windowResume;
    private int videoPlayedLast = -1;

    private List<Steps> steps;
    private String nameOfRecipe;

    //Exo Player
    private ExoPlayer exoPlayer;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,
                container, false);


        //Default Animation playtime in milliseconds
        defaultAninamtion = getResources().getInteger(android.R.integer.config_shortAnimTime);


        if (savedInstanceState != null) {
            postionResume = savedInstanceState.getLong(Constants.VIDEO_RESUME_POSITION);
            windowResume = savedInstanceState.getInt(Constants.WINDOW_RESUME);
            videoPlayedLast = savedInstanceState.getInt(Constants.PLAYED_LAST_VIDEO);
        }


        Bundle arguments = getArguments();


        if (arguments != null) {

            steps = arguments.getParcelableArrayList(getString(R.string.ADDITIONAL_STEPS));
            nameOfRecipe = arguments.getString(Constants.NAME_OF_RECIPE);

//            Log.i(TAG, "onCreateView: size of steps.." + steps.size());
            Log.i(TAG, "onCreateView: name of recipe.." + nameOfRecipe);
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getActivity().setTitle(nameOfRecipe);
        }

        String extraDetailSteps = getString(R.string.ADDITIONAL_STEPS);

        if (arguments.containsKey(extraDetailSteps)) {

            displayStepsDetails(arguments);
        } else {
            displayIngredientsOnScreen(arguments);
        }

        return mFragmentDetailsBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(exoPlayer != null){
            outState.putInt(Constants.PLAYED_LAST_VIDEO, videoPlayedLast);
            outState.putLong(Constants.VIDEO_RESUME_POSITION, exoPlayer.getContentPosition());
            outState.putInt(Constants.WINDOW_RESUME, exoPlayer.getCurrentWindowIndex());

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        destroyPlayer();
    }

    private void destroyPlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop(true);
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                if (exoPlayer != null) {
                    enableFullScreenMode();
                }
                break;

            default:
                exitFullScreenMode();

                break;
        }
    }

    private void exitFullScreenMode() {

        //make details visiable when exiting full screen
//        mFragmentDetailsBinding.innerStepDetails.setVisibility(View.VISIBLE);
        mFragmentDetailsBinding.stepDetails.setVisibility(View.VISIBLE);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().hide();
        }

        ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams)
                mFragmentDetailsBinding.exoVideoPlayer.getLayoutParams();

        float hozDps = getResources().getDimension(R.dimen.stand_padding);
        float vertDps = getResources().getDimension(R.dimen.bottom_padding);
        int hPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hozDps, getResources().getDisplayMetrics());
        int vPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, vertDps, getResources().getDisplayMetrics());


        mMarginLayoutParams.setMargins(hPixels, vPixels, hPixels, vPixels);
        mFragmentDetailsBinding.exoVideoPlayer.setLayoutParams(mMarginLayoutParams);
    }

    //SETUP MARGINS
    private void enableFullScreenMode() {

        //Hide screen details on full screen
//        mFragmentDetailsBinding.innerStepDetails.setVisibility(View.GONE);
        mFragmentDetailsBinding.stepDetails.setVisibility(View.GONE);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().hide();
        }

        ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams)
                mFragmentDetailsBinding.exoVideoPlayer.getLayoutParams();


        mMarginLayoutParams.setMargins(Constants.LEFT, Constants.TOP, Constants.RIGHT, Constants.BOTTOM);
        mFragmentDetailsBinding.exoVideoPlayer.setLayoutParams(mMarginLayoutParams);
    }


    private void setVideoView(int position) {

        //Set Details for video
//        mFragmentDetailsBinding.stepDetails.setText(steps.get(position).getDescription());
        String stepTextValues = steps.get(position).getDescription();
        Log.d(TAG, "setVideoView: Descriptions .." + steps.get(position).getDescription());

        String mThumbNailUrl = steps.get(position).getThumbnailURL();

        boolean preBtn = enablePreviousBtn(position);
        boolean nxtBtn = enableNextBtn(position);

        setStepsDetailText(stepTextValues);

        if (preBtn) {
            displayPreviousBtn(position);
        } else {
            hidePreviousBtn();
        }

        if (nxtBtn) {
            displayNextBtn(position);
        } else {
            hideNextBtn();
        }

        //lets check video
        if (videoDoesExists(steps.get(position).getVideoURL(), mThumbNailUrl)) {
            Uri validURI = Uri.parse(validURIPresent(steps.get(position).getVideoURL(), mThumbNailUrl));
            enableVideo(validURI);
            //Todo if tablet set to full screnn

        }
        displayVideo();

    }

    //Set steps text on layout
    private void setStepsDetailText(String text){

        Log.d(TAG, "setStepsDetailText: text.." + text);
        mFragmentDetailsBinding.stepDetails.setText(text);
    }

    //display Video
    private void displayVideo() {
        mFragmentDetailsBinding.exoVideoPlayer.setVisibility(View.VISIBLE);

    }

    private void enableVideo(Uri validURI) {
        //lets call exo util
        String agentContext = Util.getUserAgent(getContext(), getString(R.string.app_name));

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(
                new DefaultDataSourceFactory(getContext(), agentContext)
        ).createMediaSource(validURI);

        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector(getContext());
            LoadControl loadControl = new DefaultLoadControl();

            exoPlayer = new SimpleExoPlayer
                    .Builder(getContext())
                    .setLoadControl(loadControl)
                    .setTrackSelector(trackSelector)
                    .build();

            mFragmentDetailsBinding.exoVideoPlayer.setPlayer(exoPlayer);
        }

        exoPlayer.prepare(mediaSource);
        seekToPosition();
        exoPlayer.setPlayWhenReady(true);
    }


    //Seek position
    private void seekToPosition() {
        boolean hasResumePosition = windowResume != C.INDEX_UNSET;
        if (hasResumePosition) {
            mFragmentDetailsBinding
                    .exoVideoPlayer
                    .getPlayer()
                    .seekTo(windowResume, postionResume);
        }
    }


    //check to see if video does exists
    private boolean videoDoesExists(String url, String thumbNailUrl) {
        return url != null && !url.isEmpty() || thumbNailUrl != null && !thumbNailUrl.isEmpty();
    }

    private String validURIPresent(String url, String thumbNailUrl) {
        String validatedURL;
        if (url.isEmpty()) {
            validatedURL = thumbNailUrl;
        } else {
            validatedURL = url;
        }

        return validatedURL;
    }


    //DISPLAY PREVIOUS BUTTON
    private void displayPreviousBtn(int position) {
        int pos = position - 1;

        Log.d(TAG, "displayPreviousBtn:..Position" + position);
        mFragmentDetailsBinding.previousBtn.setVisibility(View.VISIBLE);

        mFragmentDetailsBinding.previousBtn.setOnClickListener(v -> moveToNewPosition(pos));

    }


    //Keep track of Video played
    private void moveToNewPosition(int pos) {
        videoPlayedLast = pos;

        windowResume = C.INDEX_UNSET;
        postionResume = C.INDEX_UNSET;

        setVideoView(pos);
    }

    //DISPLAY NEXT BUTTON
    private void displayNextBtn(int position) {
        int pos = position + 1;

        Log.d(TAG, "displayNextBtn: pos Next Button.." + pos);
        Log.d(TAG, "displayNextBtn: position Next Button.." + position);

        mFragmentDetailsBinding.nextBtn.setVisibility(View.VISIBLE);

        mFragmentDetailsBinding.nextBtn.setOnClickListener(v -> moveToNewPosition(pos));


    }

    private void hidePreviousBtn() {
        mFragmentDetailsBinding.previousBtn.setVisibility(View.GONE);

    }

    private void hideNextBtn() {
        mFragmentDetailsBinding.nextBtn.setVisibility(View.GONE);
    }


    private boolean enablePreviousBtn(int position) {

        boolean enableBn = position - 1 > Constants.STACKCOUNT ? true : false;
        Log.d(TAG, "enablePreviousBtn: " + enableBn);

        return enableBn;
    }

    private boolean enableNextBtn(int position) {

        boolean enableNextBn = enableNextBn = position + 1 < steps.size() ? true : false;
        Log.d(TAG, "enableNextBtn: " + enableNextBn);

        return enableNextBn;
    }


    private void displayIngredientsOnScreen(Bundle arguments) {
        hideScreenDetailsExceptIngridents();
        String ingrids = arguments.getString(getString(R.string.INGRIDENTS_EXTRA));
        ViewGroup.LayoutParams mLayoutParams = mFragmentDetailsBinding.stepDetails.getLayoutParams();
        mLayoutParams.height = MATCH_PARENT;

        mFragmentDetailsBinding.stepDetails.setLayoutParams(mLayoutParams);
        mFragmentDetailsBinding.stepDetails.setText(ingrids);
    }

    private void hideScreenDetailsExceptIngridents() {
        hideNextBtn();
        hidePreviousBtn();
//        mFragmentDetailsBinding.innerStepDetails.setVisibility(View.GONE);

    }

    //DISPLAY STEPS
    private void displayStepsDetails(Bundle arguments) {
        String stepPositions = getString((R.string.EXTRA_POSITION));
        int position;

        if (videoPlayedLast != Constants.VIDEO_PLAYED_LAST) {
            position = videoPlayedLast;
        } else {
            position = arguments.getInt(stepPositions);
            videoPlayedLast = position;
        }


        setVideoView(position);
    }
}
