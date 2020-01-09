package com.mkp.shippingitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mkp.shippingitem.Jalan.AddJalan;
import com.mkp.shippingitem.Pasang.AddPasang;
import com.mkp.shippingitem.Pending.AddPending;
import com.mkp.shippingitem.Selesai.AddSelesai;
import com.mkp.shippingitem.Tiba.AddTiba;
import com.mkp.shippingitem.adapter.ModelAdapter;
import com.mkp.shippingitem.model.DataModel;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import java.util.ArrayList;

public class ShowHistory extends AppCompatActivity  implements SearchView.OnQueryTextListener  {

    private static final String TAG = ShowHistory.class.getName();
    ListView SubjectListView;
    private SearchView editText;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_no, txt_rate ;
    TextView txt_hasil;
    FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_history);

        SubjectListView = findViewById(R.id.listview1);

        editText = findViewById(R.id.edittext1);

        new req().execute();

        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();
        toolbar = findViewById(R.id.toolbar);

        txt_hasil   = findViewById(R.id.txt_hasil);
        fab         = findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_hasil.setText(null);
                DialogForm();
            }
        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(ShowHistory.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_add, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon_add);
        dialog.setTitle("Form Add");

        dialog.show();
    }
    private void setupSearchView() {
        editText.setIconifiedByDefault(false);
        editText.setOnQueryTextListener(this);
        editText.setSubmitButtonEnabled(true);
        editText.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            SubjectListView.clearTextFilter();
        } else {
            SubjectListView.setFilterText(newText);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void jalan1(View view) {
        Intent intent = new Intent(ShowHistory.this, AddJalan.class);
        startActivity(intent);
    }

    public void tiba1(View view) {
        Intent intent = new Intent(ShowHistory.this, AddTiba.class);
        startActivity(intent);
    }

    public void pasang1(View view) {
        Intent intent = new Intent(ShowHistory.this, AddPasang.class);
        startActivity(intent);
    }

    public void pending1(View view) {
        Intent intent =new Intent(ShowHistory.this, AddPending.class);
        startActivity(intent);
    }

    public void selesai(View view) {
        Intent intent = new Intent(ShowHistory.this, AddSelesai.class);
        startActivity(intent);
    }

    private class req extends AsyncTask<String, Void, ArrayList<DataModel>> {

        ProgressDialog pgDialog = new ProgressDialog(ShowHistory.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog.setMessage("\t Please Wait");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ArrayList<DataModel> doInBackground(String... strings) {

            try {
                return AppConstant.getApiShippingList().dm(ShowHistory.this);
            } catch (Exception e1) {
                LogHelper.verbose(TAG, "doInBackground " + e1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataModel> dataModels) {
            super.onPostExecute(dataModels);
            pgDialog.dismiss();
            try {

                final ModelAdapter adapter = new ModelAdapter(ShowHistory.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(ShowHistory.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }

        }
    }

}