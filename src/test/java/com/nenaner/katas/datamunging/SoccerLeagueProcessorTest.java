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

public class SoccerLeagueProcessorTest {
    @Mock
    private ResourceFileHelper mockResourceFileHelper;

    @InjectMocks
    private SoccerLeagueProcessor subject;

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
        subject.outputTeamWithSmallestPointSpread();

        verify(mockResourceFileHelper).getResourceFileAsInputStream(SoccerLeagueProcessor.soccerLeagueDatFileLocation);
    }

    @Test
    public void basicLoggingTest() throws IOException, URISyntaxException {
        subject.outputTeamWithSmallestPointSpread();

        verify(mockLogger).info("Team \"Team 2\" had the least variation");
    }

    @Test
    public void basicLoggingTestVariation() throws IOException, URISyntaxException {
        setupFakeInputStreamFromResourceFile(generateFakeInputData("Team 4", 41, 40));

        subject.outputTeamWithSmallestPointSpread();

        verify(mockLogger).info("Team \"Team 4\" had the least variation");
    }

    @Test
    public void itHandlesUnparsableRecordsGracefully() throws IOException, URISyntaxException {
        setupFakeInputStreamFromResourceFile(createDeadFiller(59));

        subject.outputTeamWithSmallestPointSpread();

        verify(mockLogger).info("Team \"Team 2\" had the least variation");
    }

    private void setupFakeInputStreamFromResourceFile(String additionalDataToAppend) throws URISyntaxException, IOException {
        List<String> fakeInputStreamData = new ArrayList<>();
        fakeInputStreamData.add(generateFakeInputData("Team 1", 20, 10));
        fakeInputStreamData.add(generateFakeInputData("Team 2", 28, 20));
        fakeInputStreamData.add(generateFakeInputData("Team 3", 41, 30));
        if (additionalDataToAppend != null) {
            fakeInputStreamData.add(additionalDataToAppend);
        }
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(fakeInputStreamData.stream());
    }

    private String generateFakeInputData(String teamName, Integer scoredForTeam, Integer scoredAgainstTeam) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createDeadFiller(7));
        stringBuilder.append(StringUtils.rightPad(teamName, 16));
        stringBuilder.append(createDeadFiller(17));
        stringBuilder.append(StringUtils.leftPad(scoredForTeam.toString(), 6));
        stringBuilder.append(createDeadFiller(3));
        stringBuilder.append(StringUtils.leftPad(scoredAgainstTeam.toString(), 4));
        stringBuilder.append(createDeadFiller(6));
        return stringBuilder.toString();
    }

    private String createDeadFiller(int numberOfFillerCharacters) {
        return StringUtils.repeat('x', numberOfFillerCharacters);
    }
}