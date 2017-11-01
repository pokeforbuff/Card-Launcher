package com.developer.me.homelauncher.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.constants.Constants;

/**
 * Created by Sanidhya on 7/14/2017.
 */

public class MusicWidget extends Fragment implements View.OnClickListener {

    private View widgetView;
    private AudioManager audioManager;
    private TextView songTitle, songArtist;
    private ImageButton playButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        widgetView = inflater.inflate(R.layout.widget_music, container, false);
        initObjects();
        initViews();
        initWidget();
        registerMusicReceiver();
        return widgetView;
    }

    private void registerMusicReceiver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction(Constants.ANDROID_MUSIC_META_CHANGED);
        iF.addAction(Constants.ANDROID_MUSIC_PLAY_STATE_CHANGED);
        iF.addAction(Constants.ANDROID_MUSIC_PLAYBACK_COMPLETE);
        iF.addAction(Constants.ANDROID_MUSIC_PLAY_QUEUE_CHANGED);
        getActivity().registerReceiver(mReceiver, iF);
    }

    private void initWidget() {
        Intent intent = new Intent();
        intent.setAction(Constants.ANDROID_MUSIC_PLAY_STATE_CHANGED);
        getActivity().sendBroadcast(intent);
        if (audioManager.isMusicActive())
            playButton.setImageResource(R.drawable.ic_pause_white);
        else playButton.setImageResource(R.drawable.ic_play_arrow);
    }

    private void initViews() {
        songTitle = widgetView.findViewById(R.id.music_widget_title);
        songArtist = widgetView.findViewById(R.id.music_widget_artist);
        playButton = widgetView.findViewById(R.id.play_music);
        playButton.setOnClickListener(this);
    }

    private void initObjects() {
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String artist = intent.getStringExtra(Constants.MUSIC_INFO_ARTIST);
            String track = intent.getStringExtra(Constants.MUSIC_INFO_TRACK);
            Boolean isPlaying = intent.getBooleanExtra(Constants.MUSIC_INFO_PLAYING, true);
            songTitle.setText(track);
            songArtist.setText(artist);
            if (isPlaying)
                playButton.setImageResource(R.drawable.ic_pause_white);
            else playButton.setImageResource(R.drawable.ic_play_arrow);
        }
    };

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Constants.ANDROID_MUSIC_MUSIC_SERVICE_CMD);
        switch (view.getId()) {
            case R.id.play_music:
                intent.putExtra(Constants.MUSIC_CMD, Constants.MUSIC_CMD_TOGGLEPAUSE);
                break;
            case R.id.back_music:
                intent.putExtra(Constants.MUSIC_CMD,Constants.MUSIC_CMD_BWD);
                intent.putExtra(Constants.MUSIC_CMD, Constants.MUSIC_CMD_PLAY);
                break;
            case R.id.fwd_music:
                intent.putExtra(Constants.MUSIC_CMD,Constants.MUSIC_CMD_FWD);
                intent.putExtra(Constants.MUSIC_CMD, Constants.MUSIC_CMD_PLAY);
                break;
        }
        getActivity().sendBroadcast(intent);
    }
}
