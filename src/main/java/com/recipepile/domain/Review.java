package com.recipepile.domain;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
public class Review {
    private int rating;
    private String review;
    private LocalDateTime timeOfReview;

    @DBRef
    private User reviewer;
    private SortedSet<ContentStateDecision> contentStateDecisions = new TreeSet<>();

    public void addContentStateDecision(ContentStateDecision decision){
        this.contentStateDecisions.add(decision);
    }
}
