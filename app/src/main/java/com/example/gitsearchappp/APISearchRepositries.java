package com.example.gitsearchappp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APISearchRepositries {

    @GET("repositories")
    Call<Repositry> getSearchResult(@Query("q") String RepositoryName,
                                          @Query("sort") String sort,
                                          @Query("order") String order);
}
