package com.pedroperez.javaonmemoysearchcache.cache;

import com.pedroperez.javaonmemoysearchcache.model.Cat;
import com.pedroperez.javaonmemoysearchcache.transformer.CatParser;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class LuceneCacheTest {

    PodamFactory factory = new PodamFactoryImpl();
    @Autowired
    private CatParser catParser;
    @Autowired
    private IndexWriter indexWriter;
    @Autowired
    private Directory directory;

    @Test
    public void testMemoryCacheSearch() throws IOException {
        IndexReader indexReader = DirectoryReader.open(directory);
        //first clear cache
        indexWriter.deleteAll();
        indexWriter.commit();
        Assertions.assertEquals(0, indexReader.numDocs());
        for (int i = 0; i < 1000; i++) {
            Cat cat1 = factory.manufacturePojo(Cat.class);
            cat1.setName("cat_" + i);
            cat1.setId(i);
            indexWriter.addDocument(catParser.toDocument(cat1));
        }
        indexWriter.commit();
        //indexWriter.close();

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(new TermQuery(new Term("name", "cat_500")), BooleanClause.Occur.MUST);

        indexReader = DirectoryReader.open(directory);
        Assertions.assertEquals(1000, indexReader.numDocs());
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(builder.build(), 5, Sort.INDEXORDER);
        ScoreDoc[] hits = topDocs.scoreDocs;
        List<Cat> cats = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            Cat cat = catParser.toCat(searcher.doc(hit.doc));
            cats.add(cat);
        }
        Assertions.assertEquals(cats.size(), 1);
        Assertions.assertEquals(cats.get(0).getId(), 500);
    }
}
