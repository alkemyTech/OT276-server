package com.alkemy.ong.ports.input.rs.api;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.function.Function;

public interface ApiConstants {
    String AUTHENTICATION_URI = "/auth";
    String ALKYMERS_URI = "/v1/alkymers";
    String USERS_URI = "/v1/users";
    String ORGANIZATIONS_URI = "/v1/organizations/public";
    String MEMBERS_URI = "/v1/members";
    String SLIDES_URI = "/v1/slides";
    String NEWS_URI = "/v1/news";
    String TESTIMONIALS_URI = "/v1/testimonials";
    String CATEGORIES_URI = "/v1/categories";
    String ACTIVITIES_URI = "/v1/activities";
    String COMMENTS_URI = "/v1/comments";
    String CONTACTS_URI = "/v1/contacts";
    String S3_URI =  "/s3/files";


    int DEFAULT_PAGE = 0;
    int DEFAULT_PAGE_SIZE = 10;

    Function<Integer, String> uriByPageAsString = (page) ->
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", page).toUriString();
}
