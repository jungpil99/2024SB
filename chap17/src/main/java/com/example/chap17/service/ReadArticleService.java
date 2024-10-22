package com.example.chap17.service;

import com.example.chap17.dao.IArticaleDao;
import com.example.chap17.dto.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ReadArticleService {

	@Autowired
	IArticaleDao dao;

	private ReadArticleService() {
	}

	public Article readArticle(int articleId) throws ArticleNotFoundException {
		return selectArticle(articleId, true);
	}

	private Article selectArticle(int articleId, boolean increaseCount) throws ArticleNotFoundException{

			Article article = dao.selectById(articleId);
			if (article == null) {
				throw new ArticleNotFoundException(
						"해당 글이 없다: " + articleId);
			}
			if (increaseCount) {
				dao.increaseReadCount(articleId);
				article.setReadCount(article.getReadCount() + 1);
			}
			return article;
	}

	public Article getArticle(int articleId) throws ArticleNotFoundException{
		return selectArticle(articleId, false);
	}
}
