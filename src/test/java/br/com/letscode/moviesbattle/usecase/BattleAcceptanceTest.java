package br.com.letscode.moviesbattle.usecase;

import br.com.letscode.moviesbattle.application.http.controller.facade.ItemResponse;
import br.com.letscode.moviesbattle.application.http.controller.facade.RankingResponse;
import br.com.letscode.moviesbattle.usecase.shared.AcceptanceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BattleAcceptanceTest extends AcceptanceTest {

    @BeforeAll
    public void beforeAll(){
        registerAccount();
        executeLoginWithAccount();
    }


    @Test
    public void deve_jogar_ate_terminar_a_partida() throws JsonProcessingException {
        doSecureRequest()
                .when().post("/battle/start")
                .then().statusCode(HttpStatus.SC_ACCEPTED);

        getRoundAndVote();

        doSecureRequest()
                .when().post("/battle/finish")
                .then().statusCode(HttpStatus.SC_OK);


        var rakingOutput = doSecureRequest()
                .when().get("/ranking")
                .then().statusCode(HttpStatus.SC_OK)
                .extract()
                .as(RankingResponse[].class);

        assertThat(rakingOutput).isNotEmpty();
        assertThat(rakingOutput).hasSize(3);
    }

    private void getRoundAndVote() {
        do {

            var output = doSecureRequest().get("/battle/round");

            if(output.statusCode() != HttpStatus.SC_OK){ break; }

            var roundOutput = output.as(ItemResponse[].class);

            var itemId = roundOutput[0].id();
            doSecureRequest()
                            .body(Map.of("answerId", itemId))
                            .post("/battle/round/vote");

        } while (true);
    }

}
