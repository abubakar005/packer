package com.mobiquity.dto;

import com.mobiquity.util.Constants;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;

/**
 * This DTO is to store the data for each item received in the input file
 * Used Lombok library to have all methods required through annotations and precise code
 * */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {

    private int index;

    @Max(Constants.MAX_WEIGHT_ALLOWED)
    private double weight;

    @Max(Constants.MAX_COST_ALLOWED)
    private int cost;
}
