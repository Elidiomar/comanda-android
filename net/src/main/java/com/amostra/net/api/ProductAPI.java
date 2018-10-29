package com.amostra.net.api;

import com.amostra.net.data.model.Login;
import com.amostra.net.data.model.Register;
import com.amostra.net.data.model.UserResponse;
import com.amostra.net.data.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.amostra.net.util.Constants.API.URL_API;

public interface ProductAPI {

    @GET(URL_API + "product/GetProducts")
    Call<List<Product>> getProducts();

    @GET(URL_API + "product/get{id}")
    Call<Product> getProduct(@Path("id") int id);

    @POST(URL_API + "product/save")
    Call<Boolean> addProduct(@Body Product product);
}
