package br.com.letscode.moviesbattle.usecase.raking;

import br.com.letscode.moviesbattle.application.repository.AccountRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import br.com.letscode.moviesbattle.application.repository.BattleRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.battle.TableBattle;
import br.com.letscode.moviesbattle.application.repository.MoviesRepositoryInMemory;
import br.com.letscode.moviesbattle.application.repository.RoundRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowRankingUseCaseTest {

    @Test
    public void deve_validar_ranking_sem_contas_de_usuario(){

        ShowRankingUseCase usecase = new ShowRankingUseCase(
                new RoundRepositoryInMemory(),
                new BattleRepositoryInMemory(),
                new AccountRepositoryInMemory()
        );


        ShowRakingOutput output = usecase.handle();
        assertThat(output).isNotNull();
        assertThat(output.getResult()).isEmpty();
    }

    @Test
    public void deve_validar_ranking_sem_batalhas(){
        RoundRepository roundRepository = new RoundRepositoryInMemory();
        BattleRepository battleRepository = new BattleRepositoryInMemory();
        AccountRepository accountRepository = new AccountRepositoryInMemory();

        ShowRankingUseCase usecase = new ShowRankingUseCase(
                roundRepository,
                battleRepository,
                accountRepository
        );

        TableAccount account = accountRepository.save("user@email.com", "password");

        ShowRakingOutput output = usecase.handle();
        assertThat(output).isNotNull();
        assertThat(output.getResult()).isNotEmpty();

        Result first = output.getResult().get(0);

        assertThat(first.getScore()).isEqualTo(0);
        assertThat(first.getUserId()).isEqualTo(account.getId());
        assertThat(first.getUserName()).isEqualTo(account.getIdentity());
    }

    @Test
    public void deve_validar_ranking_sem_rounds(){
        RoundRepository roundRepository = new RoundRepositoryInMemory();
        BattleRepository battleRepository = new BattleRepositoryInMemory();
        AccountRepository accountRepository = new AccountRepositoryInMemory();

        ShowRankingUseCase usecase = new ShowRankingUseCase(
                roundRepository,
                battleRepository,
                accountRepository
        );

        TableAccount account = accountRepository.save("user@email.com", "password");

        battleRepository.save(account.getId(), false);

        var output = usecase.handle();
        assertThat(output).isNotNull();
        assertThat(output.getResult()).isNotEmpty();

        var first = output.getResult().get(0);

        assertThat(first.getScore()).isEqualTo(0);
        assertThat(first.getUserId()).isEqualTo(account.getId());
        assertThat(first.getUserName()).isEqualTo(account.getIdentity());
    }

    @Test
    public void deve_validar_ranking(){
        RoundRepository roundRepository = new RoundRepositoryInMemory();
        BattleRepository battleRepository = new BattleRepositoryInMemory();
        AccountRepository accountRepository = new AccountRepositoryInMemory();
        MoviesRepository movieRepository = new MoviesRepositoryInMemory();

        ShowRankingUseCase usecase = new ShowRankingUseCase(
                roundRepository,
                battleRepository,
                accountRepository
        );

        TableAccount userA = accountRepository.save("user@email.com", "password");
        TableBattle battleA = battleRepository.save(userA.getId(), false);

        var movieA = movieRepository.save("title A", 5);
        var movieB = movieRepository.save("title B", 6);
        var movieC = movieRepository.save("title C", 7);
        var movieD = movieRepository.save("title D", 8);

        var roundId = 0L;
        roundId = roundRepository.save(battleA.getId(), movieA.getId(), movieB.getId()).getId();
        roundRepository.answerById(roundId, true, movieB.getId());

        roundId = roundRepository.save(battleA.getId(), movieC.getId(), movieD.getId()).getId();
        roundRepository.answerById(roundId, true, movieD.getId());

        roundId = roundRepository.save(battleA.getId(), movieC.getId(), movieB.getId()).getId();
        roundRepository.answerById(roundId, false, movieC.getId());

        var output = usecase.handle();
        assertThat(output).isNotNull();
        assertThat(output.getResult()).isNotEmpty();

        var first = output.getResult().get(0);

        assertThat(first.getUserId()).isEqualTo(userA.getId());
        assertThat(first.getUserName()).isEqualTo(userA.getIdentity());
    }

}