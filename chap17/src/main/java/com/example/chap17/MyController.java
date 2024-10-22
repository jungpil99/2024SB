package com.example.chap17;

import com.example.chap17.dao.IArticaleDao;
import com.example.chap17.dto.Article;
import com.example.chap17.dto.ArticleListModel;
import com.example.chap17.service.ArticleNotFoundException;
import com.example.chap17.service.ListArticleService;
import com.example.chap17.service.ReadArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @Autowired
    ReadArticleService readArticleService;

    @Autowired
    ListArticleService listArticleService;

    @GetMapping("/")
    public String root() {
        return "redirect:/list";
    }

    @GetMapping("list")
    public String list(Model model, HttpServletRequest request) {
        String pageNumberString = request.getParameter("p");
        int pageNumber = 1;
        if (pageNumberString != null && pageNumberString.length() > 0) {
            pageNumber = Integer.parseInt(pageNumberString);
        }
        ArticleListModel articleListModel = listArticleService.getArticleList(pageNumber);
        model.addAttribute("listModel", articleListModel);

        if (articleListModel.getTotalPageCount() > 0) {
            int beginPageNumber =
                    (articleListModel.getRequestPage() - 1) / 10 * 10 + 1;
            int endPageNumber = beginPageNumber + 9;
            if (endPageNumber > articleListModel.getTotalPageCount()) {
                endPageNumber = articleListModel.getTotalPageCount();
            }
            model.addAttribute("beginPage", beginPageNumber);
            model.addAttribute("endPage", endPageNumber);
        }
        return "list";
    }

    @GetMapping("/read")
    public String read(Model model, HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String viewPage = null;
        try {
            Article article = readArticleService.readArticle(articleId);
            model.addAttribute("article", article);
            viewPage = "/read.jsp";
        } catch(ArticleNotFoundException ex) {
            viewPage = "/article_not_found.jsp";
        }
        return "read";
    }
}
