package ru.job4j.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.articles.model.Article;
import ru.job4j.articles.model.Word;
import ru.job4j.articles.service.generator.ArticleGenerator;
import ru.job4j.articles.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleArticleService implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleArticleService.class.getSimpleName());
    private static final String ARTICLE_GENERATED = "Сгенерирована статья № {}";

    private final ArticleGenerator articleGenerator;

    public SimpleArticleService(ArticleGenerator articleGenerator) {
        this.articleGenerator = articleGenerator;
    }

    @Override
    public void generate(Store<Word> wordStore, int count, Store<Article> articleStore) {
        LOGGER.info("Генерация статей в количестве {}", count);
        var words = wordStore.findAll();
        List<Article> articlesForStore = new ArrayList<>();
        IntStream.iterate(0, i -> i < count, i -> i + 1)
                .forEach(i -> {
                    LOGGER.info(ARTICLE_GENERATED, i);
                    articlesForStore.add(articleGenerator.generate(words));
                    if (articlesForStore.size() > 50000 || i == count - 1) {
                        articlesForStore.forEach(articleStore::save);
                        articlesForStore.clear();
                    }
                });
    }
}