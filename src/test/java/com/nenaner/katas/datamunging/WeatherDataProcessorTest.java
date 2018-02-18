package com.nenaner.katas.datamunging;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherDataProcessorTest {

    @Mock
    private ResourceFileHelper mockResourceFileHelper;

    @InjectMocks
    private WeatherDataProcessor subject;

    private Stream<String> fakeStream;
    private String[] fakeInputStreamData = { "   1  10    19","   2  20    28", "   3  30    41"};
    private Logger mockLogger;

    @Before
    public void setup() throws IOException, URISyntaxException {
        MockitoAnnotations.initMocks(this);

        fakeStream = Arrays.stream(fakeInputStreamData);
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(fakeStream);

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
}