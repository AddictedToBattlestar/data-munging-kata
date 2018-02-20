package com.nenaner.katas.datamunging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

abstract class LowestSpreadProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RangeEntity currentRecordWithLowestSpread;

    ResourceFileHelper resourceFileHelper;

    void findLowestSpread(String sourceFileLocation) throws IOException, URISyntaxException {
        currentRecordWithLowestSpread = null;
        try (Stream<String> fileData = resourceFileHelper.getResourceFileAsInputStream(sourceFileLocation)) {
            fileData.forEach(this::parseAndEvaluateLineData);
        }
        outputComparisonResult(currentRecordWithLowestSpread);
    }

    abstract void outputComparisonResult(RangeEntity rangeEntityWithLowestSpread);

    abstract RangeEntity parseEntity(String lineStringData);

    private void parseAndEvaluateLineData(String lineStringData) {
        try {
            RangeEntity rangeEntity = parseEntity(lineStringData);
            if (doesLineHaveShorterSpread(rangeEntity)) {
                currentRecordWithLowestSpread = rangeEntity;
            }
        } catch (Exception ex) {
            logger.warn("Unable to parse line: " + lineStringData);
        }
    }

    private boolean doesLineHaveShorterSpread(RangeEntity rangeEntity) {
        return currentRecordWithLowestSpread == null || (getRangeEntitySpread(currentRecordWithLowestSpread) > getRangeEntitySpread(rangeEntity));
    }

    private int getRangeEntitySpread(RangeEntity rangeEntity) {
        return Math.abs(rangeEntity.getHighValue() - rangeEntity.getLowValue());
    }
}
