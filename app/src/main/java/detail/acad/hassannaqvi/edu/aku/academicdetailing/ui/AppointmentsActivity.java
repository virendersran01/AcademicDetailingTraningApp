package detail.acad.hassannaqvi.edu.aku.academicdetailing.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.adapters.AppointmentListAdapter;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.contracts.NextMeetingContract;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.DatabaseHelper;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.databinding.ActivityAppointmentsBinding;

public class AppointmentsActivity extends AppCompatActivity {

    ActivityAppointmentsBinding bi;
    AppointmentListAdapter adapter;
    DatabaseHelper db;
    List<NextMeetingContract> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_appointments);
        db = new DatabaseHelper(this);

        this.setTitle("All Appointments");


        list = db.getAppointmentsList();

        if (list.size() > 0) {
            bi.noAppointment.setVisibility(View.GONE);
            adapter = new AppointmentListAdapter(this, list);
            bi.appointmentList.setLayoutManager(new LinearLayoutManager(this));
            bi.appointmentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            bi.appointmentList.setHasFixedSize(true);
            bi.appointmentList.setAdapter(adapter);
        } else {
            bi.noAppointment.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropdown_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.showCurrent:
                showCurrentAppointment();
                return true;
            case R.id.showAll:
                showAllAppointment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAllAppointment() {
        list = db.getAppointmentsList();

        if (list.size() > 0) {
            bi.noAppointment.setVisibility(View.GONE);
            adapter = new AppointmentListAdapter(this, list);
            bi.appointmentList.setLayoutManager(new LinearLayoutManager(this));
            bi.appointmentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            bi.appointmentList.setHasFixedSize(true);
            bi.appointmentList.setAdapter(adapter);
        } else {
            bi.noAppointment.setVisibility(View.VISIBLE);
        }
    }

    private void showCurrentAppointment() {

        list = db.getTodaysAppointment();

        if (list.size() > 0) {
            bi.noAppointment.setVisibility(View.GONE);
            adapter = new AppointmentListAdapter(this, list);
            bi.appointmentList.setLayoutManager(new LinearLayoutManager(this));
            bi.appointmentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            bi.appointmentList.setHasFixedSize(true);
            bi.appointmentList.setAdapter(adapter);
        } else {
            bi.noAppointment.setVisibility(View.VISIBLE);
        }
    }
}
