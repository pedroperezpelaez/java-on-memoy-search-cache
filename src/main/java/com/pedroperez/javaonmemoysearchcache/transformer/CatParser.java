package com.pedroperez.javaonmemoysearchcache.transformer;

import com.pedroperez.javaonmemoysearchcache.model.Cat;
import com.pedroperez.javaonmemoysearchcache.model.CatType;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.springframework.stereotype.Component;

@Component
public class CatParser {

    public Document toDocument(Cat cat) {
        Document document = new Document();
        document.add(new StringField("id", String.valueOf(cat.getId()), Field.Store.YES));
        document.add(new StringField("name", cat.getName(), Field.Store.YES));
        document.add(new StringField("type", cat.getType().getDescription(), Field.Store.YES));
        document.add(new StringField("weight", String.valueOf(cat.getWeight()), Field.Store.YES));
        document.add(new StringField("height", String.valueOf(cat.getHeight()), Field.Store.YES));
        document.add(new StringField("length", String.valueOf(cat.getLength()), Field.Store.YES));
        document.add(new StringField("age", String.valueOf(cat.getAge()), Field.Store.YES));
        document.add(new StringField("favouriteFood", cat.getFavouriteFood(), Field.Store.YES));
        return document;
    }

    public Cat toCat(Document document) {
        Cat cat = new Cat();
        cat.setAge(Integer.parseInt(document.get("age")));
        cat.setHeight(Double.parseDouble(document.get("height")));
        cat.setHeight(Double.parseDouble(document.get("length")));
        cat.setHeight(Double.parseDouble(document.get("weight")));
        cat.setFavouriteFood(document.get("favouriteFood"));
        cat.setId(Long.parseLong(document.get("id")));
        cat.setName(document.get("name"));
        cat.setType(CatType.valueOf(document.get("type").toUpperCase()));
        return cat;
    }
}
