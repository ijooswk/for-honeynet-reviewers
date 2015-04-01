package com.fan2fan.web.views.userPackage.controller;

import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huangsz
 */
@Controller
@RequestMapping(value = "/packages/{packageId}/")
public class ShowPackageController {

    public static final Logger logger = LoggerFactory.getLogger(ShowPackageController.class);

    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPackagePage(@PathVariable int packageId, Model model) {
        logger.debug("package id is {}", packageId);
        UserPackage pkg = packageService.getPackage(packageId);
        model.addAttribute("package", pkg);

        return "package/showPackage";
    }
}
