package com.mobiquity.packer;

import com.mobiquity.dto.Package;
import com.mobiquity.exception.APIException;
import com.mobiquity.service.PackerService;
import com.mobiquity.util.FileParser;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest controller class that will expose a Rest API. This API will be invoked to send packages by providing a file path
 * I used the same class provided as a skeleton but made it controller by using the Library and putting annotation
 * */

@RestController
@RequestMapping(value = "api/v1/")
public class Packer {

  private PackerService packerService;

  public Packer(PackerService packerService) {
    this.packerService = packerService;
  }

  /**
   * @param filePath
   * @return
   * @throws APIException
   */
  @GetMapping("/send-package")
  @ResponseBody
  public String pack(@RequestParam("filePath") String filePath) throws APIException {

    List<Package> list = FileParser.getInstance().parseFile(filePath);

    return packerService.sendPackages(list);
  }
}
