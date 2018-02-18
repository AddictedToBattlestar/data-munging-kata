package com.nenaner.katas.datamunging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class WeatherDataProcessor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String weatherDatFileLocation = "weather.dat";
    private List<Integer> lineWithSmallestTemperatureSpread;

    @Autowired
    private ResourceFileHelper resourceFileHelper;

    public void outputDayWithSmallestTemperatureSpread() throws IOException, URISyntaxException {
        lineWithSmallestTemperatureSpread = null;
        try (Stream<String> fileData = resourceFileHelper.getResourceFileAsInputStream(weatherDatFileLocation)) {
            fileData.forEach((lineStringData) -> {
                List<Integer> lineData = parseLineData(lineStringData);
                if (isNewLineShorter(lineData)) {
                    lineWithSmallestTemperatureSpread = lineData;
                }
            });
        }
        logger.info(lineWithSmallestTemperatureSpread.get(0).toString());
    }

    private boolean isNewLineShorter(List<Integer> newLineData) {
        return lineWithSmallestTemperatureSpread == null || (lineWithSmallestTemperatureSpread.get(2) - lineWithSmallestTemperatureSpread.get(1) > (newLineData.get(2) - newLineData.get(1)));
    }

    private List<Integer> parseLineData(String lineData) {
        List<Integer> lineDataValues = new ArrayList<>();
        lineDataValues.add(Integer.parseInt(lineData.substring(0, 4).trim()));
        lineDataValues.add(Integer.parseInt(lineData.substring(4, 8).trim()));
        lineDataValues.add(Integer.parseInt(lineData.substring(8, 14).trim()));
        return lineDataValues;
    }
}
