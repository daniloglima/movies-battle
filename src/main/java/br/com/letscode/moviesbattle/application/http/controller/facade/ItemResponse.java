package br.com.letscode.moviesbattle.application.http.controller.facade;

import br.com.letscode.moviesbattle.usecase.battle.round.Item;

public class ItemResponse {

    private final long id;
    private final String title;


    public ItemResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static ItemResponse of(Item item){
        return new ItemResponse(item.getId(), item.getTitle());
    }

}

