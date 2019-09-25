package com.bawp.playvideoexop;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
    }

    //http://buildappswithpaulo.com/videos/outro_music.mp4
    @Override
    protected void onStart() {
        super.onStart();
        player = ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());

        playerView.setPlayer(player);



        DataSource.Factory datasourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)));

        MediaSource videoSource = new ExtractorMediaSource.Factory(datasourceFactory)
                .createMediaSource(Uri.parse("http://buildappswithpaulo.com/videos/outro_music.mp4"));

        player.prepare(videoSource);



    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;

    }
}
