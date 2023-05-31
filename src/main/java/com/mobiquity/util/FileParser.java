package com.mobiquity.util;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.Package;
import com.mobiquity.exception.APIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This is the File parser class which fetch the data from the file at the provided path
 * It also does some validations on data in the file and throws exception in case of invalid data.
 * In case of exception it will not process the file.
 * It is based on Singleton pattern
 * After successful parsing it provides the list of packages (list of items, max weight) as a response
 */
public class FileParser {
    private static FileParser parser;
    private FileParser() {
    }

    /**
     * Get single parser instance each time requested.
     * @return Parser instance
     */
    public static FileParser getInstance() {
        if (parser == null) {
            synchronized (FileParser.class) {
                if (parser == null)
                    parser = new FileParser();
            }
        }
        return parser;
    }

    /**
     * @param filePath
     * @return
     * @throws APIException
     */
    // It takes the file path to start the parsing and response with list of Packages after successful
    public List<Package> parseFile(String filePath) throws APIException {

        List<Package> packages;
        // Verifying file path
        Path path = validateAndGetFilePath(filePath);

        try {
            packages = Files.lines(path)
                    .map(line -> {
                        try {
                            return getPackageByLine(line);
                        } catch (APIException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }

        return packages;
    }

    /**
     * @param filePath
     * @throws APIException
     */
    /**
     * This method takes the file path and verify if it is null or not. In case of null it throws exception 'file path required'
     * It also checks whether the path provided is valid or not by getting the file. If present then it returns the Path object to the calling method
     * It throws the exception if file not found at the provided path
     */
    private Path validateAndGetFilePath(String filePath) throws APIException {

        Optional
                .ofNullable(filePath)
                .orElseThrow(() -> new APIException(Constants.ERROR_FILE_PATH_REQUIRED));

        Path path = Paths.get(filePath);

        if (!path.toFile().exists())
            throw new APIException(Constants.ERROR_FILE_NOT_FOUND);

        return path;
    }

    /**
     * @param line
     * @return
     */
    // It will return the single package by reading the data after validation from a line. Line is the data row in the file
    private Package getPackageByLine(String line) throws APIException {

        if (!this.validateLine(line))
            throw new APIException(String.format(Constants.ERROR_INVALID_DATA_FORMAT, line));

        return new Package(getItemsListFromLine(line), getWeightCapacityFromLine(line));
    }

    /**
     * @param line
     * @return
     */
    // Validation of a single line from the file using Regex
    private boolean validateLine(String line) {

        if (!Pattern.compile(Constants.REGEX_LINE).matcher(line).find())
            return false;

        return true;
    }

    /**
     * @param line
     * @return
     */
    // It returns the weight capacity that a specific package have. It is the value before the colon in a line
    private double getWeightCapacityFromLine(String line) throws APIException {
        double weight = Double.parseDouble(line.split(Constants.REGEX_COLON)[Constants.INDEX_ZERO]);

        verifyMaxWeightAllowed(weight);

        return weight;
    }

    /**
     * @param weight
     */
    // Validating the max allowed weight on the file data
    private void verifyMaxWeightAllowed(double weight) throws APIException {
        if(weight > Constants.MAX_WEIGHT_ALLOWED)
            throw new APIException(Constants.ERROR_WEIGHT_NOT_ALLOWED);
    }

    /**
     * @param cost
     */
    // Validating the max allowed cost on the file data
    private void verifyMaxCostAllowed(int cost) throws APIException {
        if(cost > Constants.MAX_COST_ALLOWED)
            throw new APIException(Constants.ERROR_COST_NOT_ALLOWED);
    }

    /**
     * @param line
     * @return
     */
    // It returns the list of items in a single row/line by using the Pattern and Regex
    private List<Item> getItemsListFromLine(String line) throws APIException {
        String itemsData = line.split(Constants.REGEX_COLON)[Constants.INDEX_ONE];
        Matcher itemsDataMatcher = Pattern.compile(Constants.REGEX_ITEM).matcher(itemsData);
        List<Item> items = new ArrayList<>();

        while (itemsDataMatcher.find()) {
            double weight = Double.parseDouble(itemsDataMatcher.group(Constants.DATA_WEIGHT));
            int cost = Integer.parseInt(itemsDataMatcher.group(Constants.DATA_COST));

            verifyMaxWeightAllowed(weight);
            verifyMaxCostAllowed(cost);

            items.add(new Item(Integer.parseInt(itemsDataMatcher.group(Constants.DATA_INDEX)), weight, cost));

            checkItemsCount(items);
        }

        return items;
    }

    private void checkItemsCount(List<Item> items) throws APIException {
        if(items.size() > 15)
            throw new APIException(Constants.ERROR_ITEMS_NOT_ALLOWED);
    }
}
