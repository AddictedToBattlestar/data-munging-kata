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
import java.util.Arrays;

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

        String[] fakeInputStreamData = {
                generateFakeInputData(1, 10, 20),
                generateFakeInputData(2, 20, 28),
                generateFakeInputData(3, 30, 41)};
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(Arrays.stream(fakeInputStreamData));

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
        verify(mockLogger).info("2");
    }

    @Test
    public void basicLoggingTestVariation() throws IOException, URISyntaxException {
        String[] fakeInputStreamData = {
                generateFakeInputData(1, 10, 20),
                generateFakeInputData(2, 20, 28),
                generateFakeInputData(3, 28, 41),
                generateFakeInputData(4, 41, 42)
        };
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(Arrays.stream(fakeInputStreamData));

        subject.outputDayWithSmallestTemperatureSpread();
        verify(mockLogger).info("4");
    }

    private String generateFakeInputData(Integer dayOfMonth, Integer lowTemp, Integer highTemp) {
        return StringUtils.leftPad(dayOfMonth.toString(), 4) +
                StringUtils.leftPad(lowTemp.toString(), 4) +
                StringUtils.leftPad(highTemp.toString(), 6);
    }
}