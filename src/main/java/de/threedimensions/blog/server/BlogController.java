package de.threedimensions.blog.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.threedimensions.blog.server.model.BlogEntry;
import de.threedimensions.blog.server.model.BlogEntryRef;

@Controller
@RequestMapping("/*")
public class BlogController {

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public @ResponseBody
    List<BlogEntryRef> getBlogEntries() {
	List<BlogEntryRef> blogEntryRefs = new ArrayList<BlogEntryRef>();
	blogEntryRefs.add(new BlogEntryRef(1l, "posts/1"));
	blogEntryRefs.add(new BlogEntryRef(2l, "posts/2"));
	return blogEntryRefs;
    }

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public @ResponseBody
    BlogEntry getBlogEntry(@PathVariable Long postId) {
	BlogEntry blogEntry = new BlogEntry("Blog Entry from Server with id " + postId, "Blog content from server",
		new Date());
	blogEntry.setId(postId);
	return blogEntry;
    }
}
