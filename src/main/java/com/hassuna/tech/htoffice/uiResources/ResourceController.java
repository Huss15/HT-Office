package com.hassuna.tech.htoffice.uiResources;

import com.hassuna.tech.htoffice.uiResources.tag.Tag;
import com.hassuna.tech.htoffice.uiResources.tag.TagRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final TagRepository tagRepository;

    public ResourceController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

   @GetMapping("/tags/all")
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
