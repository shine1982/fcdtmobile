package com.facanditu.fcdtandroid.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by fengqin on 15/1/3.
 */
@ParseClassName("Photo")
public class Photo extends ParseObject {

    public String getUrl() {
        ParseFile parseFile = getParseFile("thumbnailPhoto");
        if(parseFile!=null)return parseFile.getUrl();
        return null;
    }

    public static ParseQuery<Photo> getQuery() {
        return ParseQuery.getQuery(Photo.class);
    }
}
