package co.banglabs.pips_lover.datahandle;

import android.os.IInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class DataAPI {

    private static final String url="https://free.currconv.com/api/v7/convert";
    private static final String key="2ec64af2c4a5303dd43f";
    private static final String url2="&q=";
    private static final String pear="USD_PHP,PHP_USD";

    public static ConverterScrvice converterScrvice = null;

    public static ConverterScrvice getConverterScrvice(){

        if(converterScrvice == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            converterScrvice = retrofit.create(ConverterScrvice.class);
        }
        return converterScrvice;

    }

    public interface ConverterScrvice{
        @GET("?apiKey="+key+url2+ pear)
        Call<CurrencyConverter> getPearValue();

    }

}
