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

    private void setupFakeInputStreamFromResourceFile(String additionalDataToAppend) throws URISyntaxException, IOException {
        List<String> fakeInputStreamData = new ArrayList<>();
        fakeInputStreamData.add(generateFakeInputData("team 1", 20, 10));
        fakeInputStreamData.add(generateFakeInputData("team 2", 28, 20));
        fakeInputStreamData.add(generateFakeInputData("team 3", 41, 30));
        if (additionalDataToAppend != null) {
            fakeInputStreamData.add(additionalDataToAppend);
        }
        when(mockResourceFileHelper.getResourceFileAsInputStream(anyString())).thenReturn(fakeInputStreamData.stream());
    }

    private String generateFakeInputData(String teamName, Integer scoredForTeam, Integer scoredAgainstTeam) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createDeadFiller(8));
        stringBuilder.append(StringUtils.rightPad(teamName, 16));
        stringBuilder.append(createDeadFiller(16));
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