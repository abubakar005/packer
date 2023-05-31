package com.mobiquity.dto;

import com.mobiquity.util.Constants;
import lombok.*;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

/**
 * This DTO is for storing a package of list items and max allowed weight
 * Used Lombok library to have all methods required through annotations and precise code
 * */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Package {

    private List<Item> items;

    @Max(Constants.MAX_WEIGHT_ALLOWED)
    private double maxWeight;

    public Package(double maxWeight) {
        this.maxWeight = maxWeight;
        items = new ArrayList<>();
    }
}
