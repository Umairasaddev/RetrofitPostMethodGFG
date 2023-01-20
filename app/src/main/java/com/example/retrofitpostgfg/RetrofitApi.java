package com.example.retrofitpostgfg;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

    //what request we are making here
    //post request
    //so we are making post request to  post a data
    //so we are annotating it with post
    @POST("users")
    //hm ny model class ko pass karna Q k data post karna or model class main data para
    //on below we are creating a method to post our data
    Call<DataModel> createPost(@Body DataModel dataModel);
}
