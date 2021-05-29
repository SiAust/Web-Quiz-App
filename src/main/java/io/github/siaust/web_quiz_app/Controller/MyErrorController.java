package io.github.siaust.web_quiz_app.Controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class MyErrorController implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(MyErrorController.class);

    @Autowired
    ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // TODO: Add error detail to log message
        log.info("An error occured");

        ServletWebRequest webRequest = new ServletWebRequest(request);
        model.addAllAttributes(errorAttributes
            .getErrorAttributes(webRequest, 
                ErrorAttributeOptions.of(
                    Include.STACK_TRACE, 
                    Include.EXCEPTION,
                    Include.MESSAGE)));
        
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
    
}
