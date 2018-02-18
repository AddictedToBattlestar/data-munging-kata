package com.nenaner.katas.datamunging;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherDataProcessorTest {

    @Mock
    private ResourceFileHelper mockResourceFileHelper;

    @InjectMocks
    private WeatherDataProcessor subject;

    private Logger mockLogger;

    @Before
    public void setup() throws IOException, URISyntaxException {
        MockitoAnnotations.initMocks(this);

        setupFakeInputStreamFromResourceFile(null);

        mockLogger = mock(Logger.class);
        subject.logger = mockLogger;
    }

    @Test
    public void itPullsFromTheDataFile() throws IOException, URISyntaxException {
        subject.outputDayWithSmallestTemperatureSpread();
        verify(mockResourceFileHelper).getResourceFileAsInputStream(WeatherDataProcessor.weatherDatFileLocation);
    }

    @Test
    public void basicLoggingTest() throws IOException, URISyntaxException {
        subject.outputDayWithSmallestTemperatureSpread();
        verify(mockLogger).info("Day 2 of the month had the least variation");
    }

    @Test
    public void basicLoggingTestVariation() throws IOException, URISyntaxException {
        setupFakeInputStreamFromResourceFile(generateFakeInputData(4, 41, 42));

        subject.outputDayWithSmallestTemperatureSpread();
        verify(mockLogger).info("Day 4 of the month had the least variation");
    }

    private void setupFakeInputStreamFromResourceFile(String additionalDataToAppend) throws URISyntaxException, IOException {
        List<String> fakeInputStreamData = new ArrayList<>();
        fakeInputStreamData.add(generateFakeInputData(1, 10, 20));
        fakeInputStreamData.add(generateFakeInputData(2, 20, 28));
        fakeInputStreamData.add(generateFakeInputData(3, 30, 41));
        if (additionalDataToAppend != null) {
            fakeInputStreamData.add(additionalDataToAppend);
        }
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(fakeInputStreamData.stream());
    }

    private String generateFakeInputData(Integer dayOfMonth, Integer lowTemp, Integer highTemp) {
        return StringUtils.leftPad(dayOfMonth.toString(), 4) +
                StringUtils.leftPad(lowTemp.toString(), 4) +
                StringUtils.leftPad(highTemp.toString(), 6);
    }
}