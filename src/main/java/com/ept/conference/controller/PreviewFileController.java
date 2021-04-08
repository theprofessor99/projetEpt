package com.ept.conference.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import com.ept.conference.model.Article;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.utility.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PreviewFileController {

    private final ArticleRepository articleRepository;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Autowired
    private ServletContext servletContext;

    public PreviewFileController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @RequestMapping("/preview/{id}")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @PathVariable("id") Long articleId) throws IOException {

        Article article = articleRepository.findById(articleId).get();
        String fileName = article.getFile();

        Path copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath(fileName));

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File file = new File(String.valueOf(copyLocation));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length()) //
                .body(resource);
    }

}
