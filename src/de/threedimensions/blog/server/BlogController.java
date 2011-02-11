package de.threedimensions.blog.server;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.threedimensions.blog.shared.BlogEntry;

@Controller
@RequestMapping("/*")
public class BlogController {

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public @ResponseBody
    BlogEntry getBlogEntry() {
	return new BlogEntry("Blog Entry from Server", "Blog content from server", new Date());
    }
}
