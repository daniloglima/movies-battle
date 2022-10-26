package br.com.letscode.moviesbattle.application.http.controller;

import br.com.letscode.moviesbattle.application.http.controller.commons.AbstractController;
import br.com.letscode.moviesbattle.application.http.controller.facade.RankingResponse;
import br.com.letscode.moviesbattle.usecase.raking.ShowRankingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class RankingController extends AbstractController {

    private final ShowRankingUseCase showRankingUseCase;

    @Autowired
    public RankingController(ShowRankingUseCase showRankingUseCase) {
        this.showRankingUseCase = showRankingUseCase;
    }

    @Operation(summary = "Returns actual ranking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "")
    })
    @GetMapping(value = "/ranking")
    public ResponseEntity<List<RankingResponse>> showRanking(){
        var output = showRankingUseCase.handle();
        var result = output.getResult()
                                              .stream()
                                              .map(RankingResponse::of)
                                              .toList();
        return ResponseEntity.ok(result);
    }
}
