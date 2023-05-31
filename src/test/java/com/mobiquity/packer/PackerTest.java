package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.service.impl.PackerServiceImpl;
import com.mobiquity.util.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class contains the tests which takes the file as an input and process
 * It contains all possible scenarios tests required to test this complete feature
 * NOTE: It reads the files from the project resource folder for testing purpose
 * */

class PackerTest {
    private static Packer packer;
    private static String FILE_PATH;

    @BeforeAll
    static void initialize() {
        packer = new Packer(new PackerServiceImpl());
        FILE_PATH = System.getProperty("user.dir")+"/src/test/resources/";
    }

    @Test
    public void throwExceptionWhenInputFilePathIsNull() {
        APIException exception = assertThrows(APIException.class, () -> packer.pack(null));
        assertEquals(Constants.ERROR_FILE_PATH_REQUIRED, exception.getMessage());
    }

    @Test
    public void throwExceptionWhenInputFilePathDoesNotExist() {
        APIException exception = assertThrows(APIException.class, () ->packer.pack("/invalid/path/test.txt"));
        assertEquals(Constants.ERROR_FILE_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void throwExceptionWhenInputFileDataIsNotValid() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->packer.pack(FILE_PATH+"invalid_file_data.txt"));
        assertEquals(String.format(Constants.ERROR_INVALID_DATA_FORMAT, "(9,89.95,€78) : 75"), exception.getLocalizedMessage());
    }

    @Test
    public void throwExceptionWhenItemCostIsMoreThanAllowed() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->packer.pack(FILE_PATH+"cost_more_than_allowed.txt"));
        assertEquals(Constants.ERROR_COST_NOT_ALLOWED, exception.getLocalizedMessage());
    }

    @Test
    public void throwExceptionWhenItemWeightIsMoreThanAllowed() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->packer.pack(FILE_PATH+"weight_more_than_allowed.txt"));
        assertEquals(Constants.ERROR_WEIGHT_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    public void throwExceptionWhenItemAreMoreThanAllowed() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->packer.pack(FILE_PATH+"items_more_than_allowed.txt"));
        assertEquals(Constants.ERROR_ITEMS_NOT_ALLOWED, exception.getMessage());
    }

    @Test
    public void returnOptimalResultsWhenValidSingleLineInputFilePassed() throws APIException {
        String expectedResult = "2, 7";
        String result = packer.pack(FILE_PATH+ "valid_single_line.txt");
        assertEquals(expectedResult, result);
    }

    @Test
    public void returnOptimalResultsWhenValidMultilineInputFilePassed() throws APIException {
        String expectedResult = (new StringBuilder())
                .append("4")
                .append(System.lineSeparator())
                .append("-")
                .append(System.lineSeparator())
                .append("2, 7")
                .append(System.lineSeparator())
                .append("8, 9")
                .toString();

        String result = packer.pack(FILE_PATH+ "valid_multiple_lines.txt");

        assertEquals(expectedResult, result);
    }
}