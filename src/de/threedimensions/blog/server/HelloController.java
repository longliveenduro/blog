package de.threedimensions.blog.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/*")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody
    String writeString() {
	return "Congratulations..spring is running !";
    }

}
