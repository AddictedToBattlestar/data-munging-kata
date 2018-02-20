package com.nenaner.katas.datamunging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class SoccerLeagueProcessor extends LowestSpreadProcessor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String soccerLeagueDatFileLocation = "football.dat";

    @Autowired
    private ResourceFileHelper resourceFileHelper;

    public void outputTeamWithSmallestPointSpread() throws IOException, URISyntaxException {
        super.resourceFileHelper = this.resourceFileHelper;
        super.findLowestSpread(soccerLeagueDatFileLocation);
    }

    @Override
    void outputComparisonResult(RangeEntity rangeEntityWithLowestSpread) {
        logger.info(String.format("Team \"%1$s\" had the least variation", rangeEntityWithLowestSpread.getEntityName()));
    }

    @Override
    RangeEntity parseEntity(String lineStringData) {
        return new RangeEntity(
                lineStringData.substring(7, 23).trim(),
                Integer.parseInt(lineStringData.substring(40, 46).trim()),
                Integer.parseInt(lineStringData.substring(49, 53).trim()),
                lineStringData
        );
    }
}
