package com.example.numbers.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trivia_numbers")
public class TriviaNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @NotEmpty
    private long triviaNumber;
    @NotBlank
    @NotEmpty
    private String fact;

    public TriviaNumber(long triviaNumber, String fact) {
        this.triviaNumber = triviaNumber;
        this.fact = fact;
    }
}
