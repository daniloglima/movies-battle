package br.com.letscode.moviesbattle.application.http.controller.commons;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class AbstractController {

    protected long getUserId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Long> details = (Map<String, Long>) authentication.getDetails();
        return details.get("userId");
    }

}
