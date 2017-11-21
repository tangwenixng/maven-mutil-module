package com.twx.webapp;

import com.twx.weather.WeatherService;

public class WeatherCall{
    public static void main(String[] args){
        WeatherService service = new WeatherService();
        String str = service.getWeather();
        System.out.println(str);
    }
}