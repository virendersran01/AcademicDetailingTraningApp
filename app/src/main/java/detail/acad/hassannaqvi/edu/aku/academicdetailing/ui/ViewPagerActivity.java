package detail.acad.hassannaqvi.edu.aku.academicdetailing.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.JSON.GeneratorClass;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.adapters.Adapter;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.contracts.SessionContract;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.databinding.ActivityViewPagerBinding;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.util.Utils;

import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.isComplete;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.ui.LoginActivity.db;


public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";
    ActivityViewPagerBinding bi;
    Adapter adapter;
    int lastItemPosition;
    boolean isClicked = false;
    int[] slides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_view_pager);

        slides = getIntent().getIntArrayExtra("slides");

        settingupViewPager();


    }

    private void settingupViewPager() {

        adapter = new Adapter(this, slides);
        bi.viewPager.setAdapter(adapter);
        bi.viewPager.beginFakeDrag();

        bi.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                lastItemPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        bi.nextSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition == slides.length - 1) {

                    if (MainApp.isMaternal) {
                        Utils.showPostDialoge(ViewPagerActivity.this, MainApp.maternalIndex,MainApp.subModuleName);
                        isComplete = false;


                    } else if (MainApp.isChild) {
                        Utils.showPostDialoge(ViewPagerActivity.this, MainApp.childlIndex,MainApp.subModuleName);
                        isComplete = false;
                    }


                } else {
                    bi.viewPager.setCurrentItem(getItem(+1), true);
                    saveDB();


                }


            }
        });

        bi.backSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastItemPosition >= 0) {
                    bi.viewPager.setCurrentItem(getItem(-1), true);

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            saveDB();
                        }
                    });


                }
            }
        });

        bi.exitSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainApp.endActivity(ViewPagerActivity.this, "Do You Want To Exit?", false);

            }
        });
    }

    private void saveDB() {

        SessionContract sC = new SessionContract();
        sC.setModule(MainApp.moduleName);
        sC.setSession(MainApp.moduleSession);
        sC.setSlideNumber(lastItemPosition);
        sC.setSessionTime(MainApp.getCurrentTime());
        db.addSessionData(sC);
    }


    private int getItem(int i) {
        return bi.viewPager.getCurrentItem() + i;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
