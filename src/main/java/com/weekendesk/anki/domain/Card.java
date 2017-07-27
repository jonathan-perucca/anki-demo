package com.weekendesk.anki.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"question", "answer"})
public class Card {

    private String question;
    private String answer;

    private Box lastBox;
}
