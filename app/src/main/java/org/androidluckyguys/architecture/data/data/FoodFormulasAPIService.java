package org.androidluckyguys.architecture.data.data;

import org.androidluckyguys.architecture.data.data.Receipe;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ramprasad
 */

public interface FoodFormulasAPIService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Receipe[]> getReceipesListData();
}
