package camp.abc.com.campignapp.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import camp.abc.com.campignapp.R;
import camp.abc.com.campignapp.database.DBHelper;
import camp.abc.com.campignapp.object.Campign;
import camp.abc.com.campignapp.object.Content;
import camp.abc.com.campignapp.utils.Const;

import static camp.abc.com.campignapp.utils.Const.LOCATION;

public class BannerTwo extends Fragment {

    int i = 0;
    private ImageView imgBannerOne, imgBannerTwo;
    private DBHelper dbHelper;
    private List<File> alBitmapOne = new ArrayList<>();
    private List<File> alBitmapTwo = new ArrayList<>();
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.banner_two, container, false);

        initialiView(v);
        getLocalData();
        viewScreen();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void viewScreen() {

        if (alBitmapOne.size() > 0) {
            try {
                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        Picasso.with(getActivity()).load(alBitmapOne.get(i)).into(imgBannerOne);
                        //   imgBannerOne.setImageBitmap(alBitmapOne.get(i));
                        i++;
                        if (i == alBitmapOne.size() - 1) i = 0;
                        start();
                    }
                }.start();
            } catch (IndexOutOfBoundsException e) {

            }

        }

        if (alBitmapTwo.size() > 0) {
            try {

                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        Picasso.with(getActivity()).load(alBitmapTwo.get(i)).into(imgBannerTwo);
                        //imgBannerTwo.setImageBitmap(alBitmapTwo.get(i));
                        i++;
                        if (i == alBitmapTwo.size() - 1) i = 0;
                        start();
                    }
                }.start();
            } catch (IndexOutOfBoundsException e) {

            }

        }

    }

    private void getLocalData() {
        try {

            ArrayList<Campign> alCampign = new ArrayList<>();

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

                        if (contentDesc.equals("50_50_1")) {
                            Log.e("Two1", " campignId " + campignId + "   contentId " + contentId);
                            File file = new File(context.getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".png");
                            alBitmapOne.add(file);
//                            try {
//
//                                FileInputStream streamIn = null;
//
//                                streamIn = new FileInputStream(file);
//
//                                Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
//
//                                alBitmapOne.add(bitmap);
//                                streamIn.close();
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }catch (OutOfMemoryError e){
//
//                            }

                        } else if (contentDesc.equals("50_50_2")) {
                            Log.e("TWO2", " campignId " + campignId + "   contentId " + contentId);
                            File file = new File(context.getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".png");
                            alBitmapTwo.add(file);
//                            try {
//
//                                FileInputStream streamIn = null;
//
//                                streamIn = new FileInputStream(file);
//
//                                Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
//
//                                alBitmapTwo.add(bitmap);
//                                streamIn.close();
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (OutOfMemoryError e) {
//
//                            }

                        }

                    }

                }

            }
        } catch (Exception e) {

        }
    }

    private void initialiView(View v) {
        imgBannerOne = (ImageView) v.findViewById(R.id.imgBannerOne);
        imgBannerTwo = (ImageView) v.findViewById(R.id.imgBannerTwo);
    }
}
