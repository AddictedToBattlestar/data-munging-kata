package com.nenaner.katas.datamunging;

import lombok.Data;
import lombok.NonNull;

@Data
public class RangeEntity {
    @NonNull
    private String entityName;
    @NonNull
    private int lowValue;
    @NonNull
    private int highValue;
    @NonNull
    private String sourceRecordData;
}
