package com.mobiquity.service.impl;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.Package;
import com.mobiquity.service.PackerService;
import com.mobiquity.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the implementation of service layer which implements the single method of the Interface
 * Main method takes packages list after the file parsing and process the data according to the requirement to get the result string
 * There are other private methods are available to get the result
 * I have implemented the Knapsack Algorithm for this problem to get the desired result
 * */

@Service
public class PackerServiceImpl implements PackerService {

    @Override
    public String sendPackages(List<Package> packages) {

        List<String> packagesList = packages.stream()
                .map(p -> getSinglePackageItems(p.getItems(), p.getMaxWeight()))
                .collect(Collectors.toList());

        return packagesList.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    // Following is the Knapsack algorithm implementation which takes items list and find the result by sorting them using the cost to weight ratio
    private String getSinglePackageItems(List<Item> items, double packageLimit) {

        items.sort((i1, i2) -> {
            double ratio1 = i1.getCost() / i1.getWeight() + i1.getCost();
            double ratio2 = i2.getCost() / i2.getWeight() + i2.getCost();

            if (ratio1 == ratio2)
                return Double.compare(i1.getWeight(), i2.getWeight());

            return Double.compare(ratio2, ratio1);
        });

        List<Integer> selectedItems = new ArrayList<>();
        double totalWeight = 0;

        for (Item item : items) {
            if (totalWeight + item.getWeight() <= packageLimit) {
                selectedItems.add(item.getIndex());
                totalWeight += item.getWeight();
            }
        }

        String output = selectedItems.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(Constants.DELIMITER_COMMA_SPACE));

        if(output.isBlank())
            output = Constants.DASH;

        return output;
    }
}
