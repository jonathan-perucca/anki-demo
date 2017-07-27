package com.weekendesk.anki.service;

import com.weekendesk.anki.domain.*;
import com.weekendesk.anki.importer.DeckImporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.weekendesk.anki.domain.Box.GREEN;
import static com.weekendesk.anki.domain.Box.ORANGE;

public class SessionService {

    private final DeckImporter deckImporter;

    public SessionService(DeckImporter deckImporter) {
        this.deckImporter = deckImporter;
    }

    public Session start(File file) {
        Deck deck = deckImporter.buildFrom(file);

        return new Session(deck);
    }

    public void studies(Session session, Student student) {
        List<Card> deckCards = session.getCardsFromDeck();
        while(!deckCards.isEmpty()) {
            List<Card> studyCards = new ArrayList<>(deckCards);
            for (Card studyCard : studyCards) {
                AnswerType answer = student.study(studyCard);

                if (answer == AnswerType.PARTIAL) {
                    session.place(studyCard, ORANGE);
                    session.removeFromDeck(studyCard);
                    deckCards.remove(studyCard);
                }
                if (answer == AnswerType.COMPLETE) {
                    session.place(studyCard, GREEN);
                    session.removeFromDeck(studyCard);
                    deckCards.remove(studyCard);
                }
            }
        }
        if(session.isSuccessful()) {
            // just for demo
            System.out.println("Congratulations");
        }
    }

    public void stop(Session session) {
        // TODO : feature : update file for history
    }
}
