package io.github.siaust.web_quiz_app.Controller;


import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


@Controller
public class MyErrorController implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(MyErrorController.class);

    @Autowired
    ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        model.addAllAttributes(errorAttributes.getErrorAttributes(webRequest, true));
        
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
    
}
