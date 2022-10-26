package br.com.letscode.moviesbattle.application.http.controller;

import br.com.letscode.moviesbattle.application.http.controller.commons.AbstractController;
import br.com.letscode.moviesbattle.application.http.controller.facade.SignInRequest;
import br.com.letscode.moviesbattle.application.http.controller.facade.SignInResponse;
import br.com.letscode.moviesbattle.application.http.controller.facade.SignUpRequest;
import br.com.letscode.moviesbattle.application.provider.TokenData;
import br.com.letscode.moviesbattle.application.provider.TokenProvider;
import br.com.letscode.moviesbattle.usecase.account.signin.SignInInput;
import br.com.letscode.moviesbattle.usecase.account.signin.SignInOutput;
import br.com.letscode.moviesbattle.usecase.account.signin.SignInUseCase;
import br.com.letscode.moviesbattle.usecase.account.signup.SignUpInput;
import br.com.letscode.moviesbattle.usecase.account.signup.SignUpUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends AbstractController {
    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(SignInUseCase signInUseCase, SignUpUseCase signUpUseCase, TokenProvider tokenProvider) {
        this.signInUseCase = signInUseCase;
        this.signUpUseCase = signUpUseCase;
        this.tokenProvider = tokenProvider;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "")
    })
    @PostMapping(value = "/auth/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request){
        var input = SignUpInput.builder()
                .identity(request.identity())
                .password(request.password())
                .build();

        signUpUseCase.handle(input);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Login with credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a access token")
    })
    @PostMapping(value = "/auth/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){
        var input = SignInInput.builder()
                .identity(request.identity())
                .password(request.password())
                .build();

        SignInOutput output = signInUseCase.handle(input);

        var data = new TokenData(output.getId(), output.getIdentity());
        String accessToken = tokenProvider.buildWith(data);

        return ResponseEntity.ok(SignInResponse.of(accessToken));
    }

}
