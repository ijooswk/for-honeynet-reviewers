package com.fan2fan.web.views.userPackage.controller;

import com.fan2fan.service.packages.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/packages/{id}/")
public class DeletePackageController {

    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePackage(@PathVariable int id, HttpServletResponse response) throws IOException {
        if (!packageService.isPackageExist(id)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        packageService.deletePackage(id);
    }
}
