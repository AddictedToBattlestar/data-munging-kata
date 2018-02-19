package com.nenaner.katas.datamunging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;

public class SoccerLeagueProcessor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String soccerLeagueDatFileLocation = "football.dat";

    @Autowired
    private ResourceFileHelper resourceFileHelper;

    public void outputTeamWithSmallestPointSpread() throws IOException, URISyntaxException {
        resourceFileHelper.getResourceFileAsInputStream(soccerLeagueDatFileLocation);
    }
}
