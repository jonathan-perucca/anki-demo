package com.weekendesk.anki.service;

import com.weekendesk.anki.domain.Deck;
import com.weekendesk.anki.domain.Session;
import com.weekendesk.anki.importer.DeckImporter;

import java.io.File;
import java.util.HashMap;

public class SessionService {

    private final DeckImporter deckImporter;

    public SessionService(DeckImporter deckImporter) {
        this.deckImporter = deckImporter;
    }

    public Session start(File file) {
        Session session = new Session(new HashMap<>());

        Deck deck = deckImporter.buildFrom(file);
        deck.getCards().forEach(card -> session.place(card, card.getLastBox()));

        return session;
    }
}
