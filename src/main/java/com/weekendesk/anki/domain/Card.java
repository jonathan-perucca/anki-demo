package com.weekendesk.anki.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(of = {"question", "answer"})
public class Card {

    private String question;
    private String answer;

    private Box lastBox;
}
