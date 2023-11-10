package br.ifsul.yesouno;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestService {
    @GET("/api")
    Call<YesNo> consultarYesNo();

}