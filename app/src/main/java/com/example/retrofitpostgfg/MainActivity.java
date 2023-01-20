package com.example.retrofitpostgfg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private Button sendDataBtn;
private TextView tvResponse;
private ProgressBar progressbar;
private EditText etName,etJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    etName = findViewById(R.id.etName);
    etJob = findViewById(R.id.etJob);
    sendDataBtn = findViewById(R.id.sendDataBtn);
    tvResponse = findViewById(R.id.tvResponse);
    progressbar = findViewById(R.id.progressbar);

    sendDataBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //first we check k dono text views empty to ni if empty user say kaho  data dal
            if (etName.getText().toString().isEmpty()  && etJob.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Enter name and job in the fields given Bitte", Toast.LENGTH_SHORT).show();
                return;
            }
            //calling a method to post data and passing our name and job
            postData(etName.getText().toString(),etJob.getText().toString());
        }
    });


    }

    private void postData(String name, String job) {

        progressbar.setVisibility(View.VISIBLE);
        //now create retrofit builder and pass our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/").addConverterFactory(GsonConverterFactory.create())
                .build();

        //noe create an instance for retrofit api class which is our model class
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        //passing data from our text fields to our model class
        DataModel dataModel = new DataModel(name, job);

        //calling a method to create a post and passing our model class and to pass retrofit api to datamodeol
        Call<DataModel> call = retrofitApi.createPost(dataModel);
        //now execute our method
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
        //this method will be called when we get response from api
                Toast.makeText(MainActivity.this, "Data added to api", Toast.LENGTH_SHORT).show();

                progressbar.setVisibility(View.GONE);

                etName.setText("");
                etJob.setText("");

                //we are getting response from our body and passing it to our modal class
                DataModel responsefromAPI = response.body();

                if(response.body() != null) {
                    String s = response.body().toString();
                }
                //getting data from modal class and adding it to our string
//                String responseString = "Response Code : " + response.code()
//                        + "Name : " + responsefromAPI.getName() + "\n" + "Job : " + responsefromAPI.getJob();
//
//                //displaying the string in text view
//                tvResponse.setText(responseString);//jo response aye ga api say usay text view main bhej dia

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

                //jab error aye to jo code aye ga error code woh display ho
                tvResponse.setText("Error found is " + t.getMessage());
            }
        });
    }
}