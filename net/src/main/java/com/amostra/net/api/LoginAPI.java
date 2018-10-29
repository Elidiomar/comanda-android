package com.amostra.net.api;

import com.amostra.net.data.model.Login;
import com.amostra.net.data.model.Register;
import com.amostra.net.data.model.Token;
import com.amostra.net.data.model.User;
import com.amostra.net.data.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.amostra.net.util.Constants.API.URL_API;

public interface LoginAPI {

    @POST(URL_API + "account/login")
    Call<UserResponse> loginUser(@Body Login login);

    @POST(URL_API + "account/register")
    Call<UserResponse> createUser(@Body Register register);

    @GET(URL_API + "account/validate")
    Call<Boolean> validateToken();
}
