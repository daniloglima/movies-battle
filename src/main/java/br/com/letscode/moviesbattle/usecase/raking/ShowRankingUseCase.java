package br.com.letscode.moviesbattle.usecase.raking;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;

import java.util.List;

public class ShowRankingUseCase {

    private final RoundRepository roundRepository;
    private final BattleRepository battleRepository;
    private final AccountRepository accountRepository;

    public ShowRankingUseCase(RoundRepository roundRepository, BattleRepository battleRepository, AccountRepository accountRepository) {
        this.roundRepository = roundRepository;
        this.battleRepository = battleRepository;
        this.accountRepository = accountRepository;
    }

    ShowRakingOutput handle(){
        List<TableAccount> accounts = accountRepository.findAll();

        List<Ranking> rankings = accounts.stream()
                .map(Ranking::hydrateWith)
                .map(ranking -> ranking.hydrateBattlesIds(battleRepository::findIdEndedBy))
                .map(ranking -> ranking.hydrateRounds(roundRepository::countRightsBy))
                .toList();

        return ShowRakingOutput.of(rankings);
    }

}