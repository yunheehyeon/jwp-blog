package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.ArticleRepository;
import techcourse.myblog.domain.CategoryRepository;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/writing")
    public String showWritingPage() {
        return "article-edit";
    }

    @PostMapping("/articles/new")
    public RedirectView addArticle(Article articleParam) {
        Long latestId = articleRepository.add(articleParam);
        return new RedirectView("/articles/" + latestId);
    }

    @GetMapping("/articles/{articleId}")
    public String showArticleById(@PathVariable long articleId, Model model) {
        Article article = articleRepository.findById(articleId);
        model.addAttribute("article", article);
        return "article";
    }

    @PutMapping("/articles/{articleId}")
    public RedirectView updateArticle(@PathVariable long articleId, Article articleParam) {
        long updateId = articleRepository.updateById(articleParam, articleId);
        return new RedirectView("/articles/" + updateId);
    }

    @DeleteMapping("articles/{articleId}")
    public RedirectView deleteArticle(@PathVariable long articleId) {
        articleRepository.deleteById(articleId);
        return new RedirectView("/");
    }

    @GetMapping("/articles/{articleId}/edit")
    public String updateArticle(@PathVariable long articleId, Model model) {
        Article article = articleRepository.findById(articleId);
        model.addAttribute("article", article);
        return "article-edit";
    }
}
