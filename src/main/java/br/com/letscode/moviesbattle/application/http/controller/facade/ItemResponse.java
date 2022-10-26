package br.com.letscode.moviesbattle.application.http.controller.facade;

import br.com.letscode.moviesbattle.usecase.battle.round.Item;

public record ItemResponse(long id, String title) {

    public static ItemResponse of(Item item){
        return new ItemResponse(item.getId(), item.getTitle());
    }

}

