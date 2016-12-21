package fr.univ_brest.lcs.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import fr.univ_brest.lcs.R;

public class VideoStreamActivity extends AppCompatActivity {

    VideoView myVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_stream);

        myVideoView = (VideoView) findViewById(R.id.videoView);

        String vidAddress = "http://iotubo.univ-brest.fr/";
        Uri vidUri = Uri.parse(vidAddress);

        myVideoView.setVideoURI(vidUri);
        myVideoView.setMediaController(new MediaController(this));
    }
}
