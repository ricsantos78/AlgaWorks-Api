package com.algafoods.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Trouble {


    private Integer status;
    private LocalDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private List<Field>fieldList;


    @Getter
    @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }

}
