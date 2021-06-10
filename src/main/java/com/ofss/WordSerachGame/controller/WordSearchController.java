package com.ofss.WordSerachGame.controller;

import com.ofss.WordSerachGame.Service.WordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/gridWord")
public class WordSearchController {

//    private int gridSize;
//    private char[][] content;

    @Autowired
    WordSearchService wordSearchService;

    @GetMapping ("/gridWord")
    public String createWordGrid(@RequestParam("gridSize") int gridSize, @RequestParam("words") List<String> words ){
      //  List<String> word =Arrays.asList(words.s
      /*
      captialize taking input
      comma seprated take care

       */
        char[][] gridChar=wordSearchService.generateGrid(gridSize,words);
        String gridToString="";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString+=gridChar[i][j] + " ";
            }
            gridToString+="\r\n";
        }
        return gridToString;
    }

}
