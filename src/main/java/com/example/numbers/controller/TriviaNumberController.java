package com.example.numbers.controller;

import com.example.numbers.entity.TriviaNumber;
import com.example.numbers.service.TriviaNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/number")
public class TriviaNumberController {

    @Autowired
    private TriviaNumberService triviaNumberService;

    @PostMapping
    public ResponseEntity<TriviaNumber> save(@RequestBody TriviaNumber triviaNumber) {
        return new ResponseEntity<>(triviaNumberService.save(triviaNumber), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TriviaNumber> findAll() {
        return triviaNumberService.findAll();
    }

    @GetMapping("/{triviaNumber}")
    public ResponseEntity<TriviaNumber> findByTriviaNumber(@PathVariable long triviaNumber) {
        Optional<TriviaNumber> byTriviaNumber = triviaNumberService.findByTrivialNumber(triviaNumber);
        return byTriviaNumber.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{triviaNumber}")
    public ResponseEntity<TriviaNumber> updateTriviaNumber (@PathVariable long triviaNumber, @RequestBody TriviaNumber myNewTriviaNumber) {
        Optional<TriviaNumber> myTriviaNumber = triviaNumberService.findByTrivialNumber(triviaNumber);
        if (myTriviaNumber.isPresent()) {
            myNewTriviaNumber.setId(myTriviaNumber.get().getId());
            TriviaNumber updateTriviaNumber = triviaNumberService.save(myNewTriviaNumber);
            return ResponseEntity.ok(updateTriviaNumber);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{triviaNumber}")
    public ResponseEntity<TriviaNumber> deleteTriviaNumberByTriviaNumber(@PathVariable long triviaNumber) {
        triviaNumberService.deleteTriviaNumberByTriviaNumber(triviaNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
