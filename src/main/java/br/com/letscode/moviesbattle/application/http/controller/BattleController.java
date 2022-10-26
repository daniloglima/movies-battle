package br.com.letscode.moviesbattle.application.http.controller;

import br.com.letscode.moviesbattle.application.http.controller.commons.AbstractController;
import br.com.letscode.moviesbattle.application.http.controller.facade.ItemResponse;
import br.com.letscode.moviesbattle.application.http.controller.facade.VoteRequest;
import br.com.letscode.moviesbattle.usecase.battle.end.EndBattleInput;
import br.com.letscode.moviesbattle.usecase.battle.end.EndBattleUseCase;
import br.com.letscode.moviesbattle.usecase.battle.round.RoundBattleInput;
import br.com.letscode.moviesbattle.usecase.battle.round.RoundBattleUseCase;
import br.com.letscode.moviesbattle.usecase.battle.start.StartBattleInput;
import br.com.letscode.moviesbattle.usecase.battle.start.StartBattleUseCase;
import br.com.letscode.moviesbattle.usecase.battle.vote.VoteInBattleInput;
import br.com.letscode.moviesbattle.usecase.battle.vote.VoteInBattleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BattleController extends AbstractController {

    private final StartBattleUseCase startBattleUseCase;
    private final RoundBattleUseCase roundBattleUseCase;
    private final EndBattleUseCase endBattleUseCase;
    private final VoteInBattleUseCase voteInBattleUseCase;

    @Autowired
    public BattleController(StartBattleUseCase startBattleUseCase, RoundBattleUseCase roundBattleUseCase, EndBattleUseCase endBattleUseCase, VoteInBattleUseCase voteInBattleUseCase) {
        this.startBattleUseCase = startBattleUseCase;
        this.roundBattleUseCase = roundBattleUseCase;
        this.endBattleUseCase = endBattleUseCase;
        this.voteInBattleUseCase = voteInBattleUseCase;
    }

    @Operation(summary = "Starts a new battle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The new battle started.")
    })
    @PostMapping(value = "/battle/start")
    public ResponseEntity<Void> startBattle(){ 
        var input = StartBattleInput.builder()
                .userId(getUserId())
                .build();
        startBattleUseCase.handle(input);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Returns a current Round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "")
    })
    @GetMapping(value = "/battle/round")
    public ResponseEntity<List<ItemResponse>> roundInBattle(){
        var input = RoundBattleInput.builder()
                .userId(getUserId())
                .build();
        var output = roundBattleUseCase.handle(input);
        var result = output.getItems()
                                          .stream()
                                          .map(ItemResponse::of)
                                          .toList();

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Vote in round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Your vote was accepted")
    })
    @PostMapping(value = "/battle/round/vote")
    public ResponseEntity<Void> voteInRound(@RequestBody VoteRequest request){
        var input = VoteInBattleInput.builder()
                .userId(getUserId())
                .answerId(request.answerId())
                .build();

        voteInBattleUseCase.handle(input);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Ends the battle in progress")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The battle is over.")
    })
    @PostMapping(value = "/battle/finish")
    public ResponseEntity<Void> endBattle(){
        var input = EndBattleInput.builder()
                .userId(getUserId())
                .build();

        endBattleUseCase.handle(input);
        return ResponseEntity.ok().build();
    }


}
