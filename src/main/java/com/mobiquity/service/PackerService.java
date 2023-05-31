package com.mobiquity.service;

import com.mobiquity.dto.Package;

import java.util.List;

/**
 * This is the Abstraction of service layer which has single method
 * it will take list of packages as an input and will return the output string
 * */

public interface PackerService {
    String sendPackages(List<Package> packages);
}
