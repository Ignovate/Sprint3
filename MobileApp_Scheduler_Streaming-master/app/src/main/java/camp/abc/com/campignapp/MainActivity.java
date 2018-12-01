package camp.abc.com.campignapp;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import camp.abc.com.campignapp.adapter.Pager;
import camp.abc.com.campignapp.database.DBHelper;
import camp.abc.com.campignapp.object.Campign;
import camp.abc.com.campignapp.object.Compigncontent;
import camp.abc.com.campignapp.object.Content;
import camp.abc.com.campignapp.services.DateJobService;
import camp.abc.com.campignapp.utils.Const;
import camp.abc.com.campignapp.utils.Utils;

public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {


    private static final String TAG = "MainActivity";
    private static final int STORAGE_PERMISSION_CODE = 1000;
    int i = 0;

    ArrayList<Campign> alCampign = new ArrayList<>();
    ArrayList<Content> alContent = new ArrayList<>();
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DBHelper dbHelper;
    private ArrayList<File> cpBitmap = new ArrayList<>();
    private ArrayList<File> dpBitmap = new ArrayList<>();
    private AppCompatImageView mCP, mDP;
    private VideoView mVideoCP;

    private void initiateViews() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mCP = (AppCompatImageView) findViewById(R.id.imgBanner);
        mDP = (AppCompatImageView) findViewById(R.id.image_dp);
        mVideoCP = (VideoView) findViewById(R.id.myvideoview);
        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab5"));
        viewPager = (ViewPager) findViewById(R.id.pager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBHelper(this);

       if(dbHelper.getCount().size() > 0){

        } else {
           if (isReadStorageAllowed()) {
               scheduleJob();
               return;
           }
           requestStoragePermission();

        }


        //  getLocalData();
        String mydate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        TextView time = (TextView) findViewById(R.id.time);
        time.setText(mydate);


        initiateViews();

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    public void scheduleJob() {

        ComponentName componentName = new ComponentName(this, DateJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            scheduleJob();
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();




    }

    private void viewScreen() {

        Log.e("dfbplm", "fdvvf" + cpBitmap.size());

    }

    private void loadBanner() {

        alCampign = dbHelper.getCampignData();

        Log.e("alCampign : ", ": " + alCampign);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void getLocalData() {
        try {

            alCampign = dbHelper.getCampignData();

            for (int i = 0; i < alCampign.size(); i++) {

                Campign campign = alCampign.get(i);

                String campignId = campign.getCampignId();
                String adSpace = campign.getAdSpace();

                if (adSpace.equals(Const.CP)) {
                    Log.e("fdjnvfdnj", "dfmfdkvn" + adSpace);
                    ArrayList<Content> alContent = dbHelper.getContent(campignId, adSpace);

                    for (int j = 0; j < alContent.size(); j++) {

                        Content content = alContent.get(j);

                        String contentId = content.getContentId();

                        String contentDesc = content.getContentDesc();
                        String contenttype = content.getContentType();

                        if (contentDesc.equals("Banner") && contenttype.equals("1")) {
                            Log.e("jhkjhuy", adSpace + campignId + contentId);

                            Log.e("ONEddd", " campignId " + campignId + "   contentId " + contentId + "  contenttype " + contenttype);

                            File sdCard = Environment.getExternalStorageDirectory();

                            File directory = new File(sdCard.getAbsolutePath() + "/campignAppImages");

                            File file = new File(directory, adSpace + campignId + contentId + ".png"); //or any other format supported
                            cpBitmap.add(file);

                        } else if (contentDesc.equals("Banner") && contenttype.equals("2")) {

                            Log.e("jhkjhuy", adSpace + campignId + contentId);

                            File sdCard = Environment.getExternalStorageDirectory();

                            File directory = new File(sdCard.getAbsolutePath() + "/campignAppImages");

                            File file = new File(directory, adSpace + campignId + contentId + ".mp4"); //or any other format supported
                            cpBitmap.add(file);
                        }
                    }
                }
            }

            if (cpBitmap.size() > 0) {
                try {
                    new CountDownTimer(10000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            //  Log.e("ds", "remaining: " + millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            File url = cpBitmap.get(i);
                            String path = url.getPath();
                            if (path.endsWith(".png") || path.endsWith(".jpg")) {
                                mVideoCP.setVisibility(View.GONE);
                                mCP.setVisibility(View.VISIBLE);
                                Picasso.with(MainActivity.this).load(path).resize(100, 100).into(mCP);
                            }

                            i++;
                            if (i == cpBitmap.size() - 1) i = 0;
                            start();
                        }
                    }.start();
                } catch (IndexOutOfBoundsException e) {

                }
            }

        } catch (Exception e) {

        }
    }


}
