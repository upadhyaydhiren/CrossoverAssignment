package com.crossover.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is error controller class that is implements {@link ErrorController}
 * Created by dhiren on 12/7/16.
 * @author dhiren
 * @since 12/7/16
 */

@Controller
class ErrorManageController implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }


    @RequestMapping(value = PATH)
    public String errorHandling()
    {
        return "404";
    }

}
