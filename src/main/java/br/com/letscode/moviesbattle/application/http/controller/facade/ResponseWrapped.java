package br.com.letscode.moviesbattle.application.http.controller.facade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ResponseWrapped {

    private int status;
    private String message;
    private long timeStamp;
    private Object content;

}
