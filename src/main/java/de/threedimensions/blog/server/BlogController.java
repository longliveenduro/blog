package de.threedimensions.blog.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.threedimensions.blog.server.dao.BlogEntryDao;
import de.threedimensions.blog.server.dto.BlogEntryRef;
import de.threedimensions.blog.server.model.BlogEntry;

@Controller
@RequestMapping("/posts")
public class BlogController {

    private static UUID theUUID = UUID.randomUUID();

    @Autowired
    private BlogEntryDao blogEntryDao;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<BlogEntryRef> getBlogEntries() {
	List<BlogEntryRef> blogEntryRefs = new ArrayList<BlogEntryRef>();
	blogEntryRefs.add(new BlogEntryRef(UUID.randomUUID(), "Title 1", "posts/" + theUUID));
	return blogEntryRefs;
    }

    /**
     * Create a new blog entry.
     * 
     * @return url to newly created blog entry
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String createBlogEntry() {
	System.out.println("Blog Entry created.");
	return "posts/" + UUID.randomUUID();
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public @ResponseBody
    BlogEntry getBlogEntry(@PathVariable UUID postId) {
	// return blogEntryDao.loadBlogEntryById(postId);
	BlogEntry blogEntry = new BlogEntry(
		postId,
		"Blog Entry from Server with id " + postId,
		"Lorem Ipsum ist ein einfacher Demo-Text für die Print- und Schriftindustrie. Lorem Ipsum ist in der Industrie bereits der Standard Demo-Text seit 1500, als ein unbekannter Schriftsteller eine Hand voll Wörter nahm und diese durcheinander warf um ein Musterbuch zu erstellen. Es hat nicht nur 5 Jahrhunderte überlebt, sondern auch in Spruch in die elektronische Schriftbearbeitung geschafft (bemerke, nahezu unverändert). Bekannt wurde es 1960, mit dem erscheinen von \"Letraset\", welches Passagen von Lorem Ipsum enhielt, so wie Desktop Software wie \"Aldus PageMaker\" - ebenfalls mit Lorem Ipsum.",
		new DateTime());
	blogEntry.setId(postId);
	return blogEntry;
    }
}
