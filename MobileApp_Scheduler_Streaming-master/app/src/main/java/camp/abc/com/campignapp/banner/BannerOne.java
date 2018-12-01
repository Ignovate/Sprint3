package camp.abc.com.campignapp.banner;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import camp.abc.com.campignapp.R;
import camp.abc.com.campignapp.database.DBHelper;
import camp.abc.com.campignapp.object.Campign;
import camp.abc.com.campignapp.object.Content;
import camp.abc.com.campignapp.utils.Const;

import static camp.abc.com.campignapp.utils.Const.LOCATION;

public class BannerOne extends Fragment {

    int i = 0;
    List<Campign> alCampign = new ArrayList<Campign>();
    private ImageView imgBanner;
    private DBHelper dbHelper;
    private ArrayList<File> alBitmap = new ArrayList<File>();
    private VideoView myVideoView;
    private CountDownTimer on;

    private Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.banner_one, container, false);
        initialiView(v);
        getLocalData();
        viewScreen();
        return v;
    }


    private void viewScreen() {

        if (alBitmap.size() > 0) {
            try {
                on = new CountDownTimer(10000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Log.e("seconds", "remaining: " + millisUntilFinished / 1000);
                    }
                    //
                    @Override
                    public void onFinish() {
                        File url = alBitmap.get(i);
                        String path = url.getPath();

                        if (path.endsWith(".mp4")) {

                            imgBanner.setVisibility(View.GONE);
                            myVideoView.setVisibility(View.VISIBLE);
                            myVideoView.setVideoPath(path);
                            myVideoView.setMediaController(new MediaController(getActivity()));
                            myVideoView.requestFocus();
                            myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                public void onPrepared(MediaPlayer mp) {
                                    myVideoView.start();
                                }
                            });

                        } else if (path.endsWith(".png") || path.endsWith(".jpg")) {
                            myVideoView.setVisibility(View.GONE);
                            imgBanner.setVisibility(View.VISIBLE);
                            Picasso.with(getActivity()).load(url).into(imgBanner);
                        }

                        i++;
                        if (i == alBitmap.size() - 1) i = 0;
                        start();
                    }
                }.start();
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getLocalData() {
        try {

            alCampign = dbHelper.getCampignData();

            for (int i = 0; i < alCampign.size(); i++) {

                Campign campign = alCampign.get(i);

                String campignId = campign.getCampignId();
                String adSpace = campign.getAdSpace();

                if (adSpace.equals(Const.SD)) {

                    ArrayList<Content> alContent = dbHelper.getContent(campignId, adSpace);

                    for (int j = 0; j < alContent.size(); j++) {

                        Content content = alContent.get(j);

                        String contentId = content.getContentId();

                        String contentDesc = content.getContentDesc();
                        String contenttype = content.getContentType();

                        if (contentDesc.equals("Banner") && contenttype.equals("1")) {

                            File file = new File(context.getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".png");
                            alBitmap.add(file);

                        } else if (contentDesc.equals("Banner") && contenttype.equals("2")) {

                            File file = new File(context.getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".mp4");
                            alBitmap.add(file);
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private void initialiView(View v) {
        imgBanner = (ImageView) v.findViewById(R.id.imgBanner);
        myVideoView = (VideoView) v.findViewById(R.id.myvideoview);

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.onFinish();
                String url = "http://209.97.130.34/lectrefy-api-service/api/v1.0/downloadfile/47.jpg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
