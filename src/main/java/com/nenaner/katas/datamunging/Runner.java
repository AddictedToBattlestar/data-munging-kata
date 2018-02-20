package com.nenaner.katas.datamunging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private WeatherDataProcessor weatherDataProcessor;

    @Autowired
    private SoccerLeagueProcessor soccerLeagueProcessor;

    @Override
    public void run(String... args) throws IOException, URISyntaxException {
        weatherDataProcessor.outputDayWithSmallestTemperatureSpread();
        soccerLeagueProcessor.outputTeamWithSmallestPointSpread();
    }
}
