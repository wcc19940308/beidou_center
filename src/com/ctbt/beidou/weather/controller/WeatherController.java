package com.ctbt.beidou.weather.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("/weather")
public class WeatherController {

	private Logger logger = Logger.getLogger(getClass());

	@RequestMapping("/toWeatherList")
	public String toWeatherList(HttpServletRequest request,Model model){

		return "weather/weatherList";
	}

	@RequestMapping("/queryWeatherList")
	public String queryWeatherList(HttpServletRequest request,Model model){

		return "weather/weatherList";
	}

	@RequestMapping("/toWeatherEdit")
	public String toWeatherEdit(HttpServletRequest request,Model model){

		return "weather/weatherEdit";
	}
}
