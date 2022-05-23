package com.example.mdaproject.Weather;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mdaproject.R;

import java.util.List;

public class WeatherAPI extends AppCompatActivity {

    Button btn_cityID, btn_getWeatherID, btn_getWeatherByName;
    EditText dataInput;
    ListView weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_api);

        WeatherDataService weatherDataService = new WeatherDataService(WeatherAPI.this);


        //assign every button
        btn_cityID = findViewById(R.id.btn_getCityId);
        btn_getWeatherID = findViewById(R.id.button2);
        btn_getWeatherByName = findViewById(R.id.button3);

        dataInput = findViewById(R.id.datainput);
        weatherList = findViewById(R.id.lv);

        //click listeners for each button
        btn_cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                weatherDataService.getCityID(dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(WeatherAPI.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(WeatherAPI.this, "Returned an ID " + cityID, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_getWeatherID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               weatherDataService.getCityForecastByID(dataInput.getText().toString(), new WeatherDataService.ForecastByIdResponse() {
                   @Override
                   public void onError(String message) {

                   }

                   @Override
                   public void onResponse(List<WeatherReportModel> weatherReportModels) {
                       ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherAPI.this, android.R.layout.simple_list_item_1, weatherReportModels);
                       weatherList.setAdapter(arrayAdapter);
                   }
               });
            }
        });

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weatherDataService.getCityForecastByName(dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(WeatherAPI.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        weatherList.setAdapter(arrayAdapter);
                    }
                });


            }
        });

    }
}