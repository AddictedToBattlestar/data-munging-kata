package com.nenaner.katas.datamunging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class WeatherDataProcessor extends LowestSpreadProcessor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String weatherDatFileLocation = "weather.dat";

    @Autowired
    private ResourceFileHelper resourceFileHelper;

    public void outputDayWithSmallestTemperatureSpread() throws IOException, URISyntaxException {
        super.resourceFileHelper = this.resourceFileHelper;
        super.findLowestSpread(weatherDatFileLocation);
    }

    @Override
    void outputComparisonResult(RangeEntity rangeEntityWithLowestSpread) {
        logger.info(String.format("Day %1$s of the month had the least variation", rangeEntityWithLowestSpread.getEntityName()));
    }

    @Override
    RangeEntity parseEntity(String lineStringData) {
        return new RangeEntity(
                getValueFromLineData(lineStringData, 1, 4).toString(),
                getValueFromLineData(lineStringData, 4, 8),
                getValueFromLineData(lineStringData, 8, 14),
                lineStringData
        );
    }

    private Integer getValueFromLineData(String lineData, int beginIndex, int endIndex) {
        return Integer.parseInt(lineData.replace("*", " ").substring(beginIndex, endIndex).trim());
    }
}
