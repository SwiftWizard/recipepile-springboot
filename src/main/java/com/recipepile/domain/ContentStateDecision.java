package com.recipepile.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
public class ContentStateDecision implements Comparable<ContentStateDecision>{
    private ContentState contentState;
    private User moderator;
    private LocalDateTime decisionTime;
    private String rationale;

    @Override
    public int compareTo(@NotNull ContentStateDecision contentStateDecision) {
        return  decisionTime.compareTo(contentStateDecision.getDecisionTime());
    }
}
