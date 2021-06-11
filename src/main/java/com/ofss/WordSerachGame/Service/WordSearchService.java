package com.ofss.WordSerachGame.Service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordSearchService {

    private enum Direction{
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_BACK,
        VERTICAL_BACK,
        DIAGONAL_BACK
    }


    private class Coordinate{
        int x;
        int y;
        Coordinate(int x, int y){
            this.x=x;
            this.y=y;
        }
    }

    /*
        take Grid size and will return us grid
     */
    public char[][] generateGrid(int gridSize ,List<String> words) {
        List<Coordinate> coordinates =new ArrayList<>();
        char[][] content=new char[gridSize][gridSize];
        for (int i = 0; i <gridSize ; i++) {
            for (int j = 0; j <gridSize ; j++) {
                coordinates.add(new Coordinate(i,j));
                content[i][j]='-';
            }
        }
        for (String word : words) {
            Collections.shuffle(coordinates);
            for(Coordinate coordinate:coordinates) {
                int x =coordinate.x;
                int y=coordinate.y;
                Direction selectedDirection= getDirectionForFit(content,word,coordinate);

                if(selectedDirection!=null){
                    switch(selectedDirection){
                        case HORIZONTAL:
                            for(char c:word.toCharArray()) {
                                content[x][y++] = c;
                            }break;
                        case VERTICAL:
                            for(char c:word.toCharArray()) {
                                content[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for(char c:word.toCharArray()) {
                                content[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_BACK:
                            for(char c:word.toCharArray()) {
                                content[x][y--] = c;
                            }
                            break;
                        case VERTICAL_BACK:
                            for(char c: word.toCharArray()){
                                content[x--][y]=c;
                            }
                            break;
                        case DIAGONAL_BACK:
                            for(char c: word.toCharArray()){
                                content[x--][y--]=c;
                            }
                    }

                    break;
                }
            }
        }
        //randomFillGrid(content);
        return content;
    }
    private Direction getDirectionForFit( char[][] content,String word, Coordinate coordinate){
        int gridSize=content[0].length;
        List<Direction> directions= Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction:directions){
            if(doesFit(content,word.toUpperCase(),coordinate,direction)){
                return direction;
            }
        }
        return null;
    }

    private boolean doesFit( char[][] content,String word, Coordinate coordinate,Direction direction) {
        int gridSize=content[0].length;
            switch (direction) {
                case HORIZONTAL:
                    if (coordinate.y + word.length() > gridSize) return false;
                    for (int i = 0; i < word.length(); i++) {
                        if (content[coordinate.x][coordinate.y + i] != '-' &&
                                content[coordinate.x][coordinate.y + i] != word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL:
                    if (coordinate.x + word.length() > gridSize) return false;
                    for (int i = 0; i < word.length(); i++) {
                        if (content[coordinate.x + i][coordinate.y] != '-'&&
                                content[coordinate.x + i][coordinate.y] != word.charAt(i)) return false;
                    }
                    break;
                case DIAGONAL:
                    if (coordinate.y + word.length() > gridSize || coordinate.x + word.length() > gridSize)
                        return false;
                    for (int i = 0; i < word.length(); i++) {
                        if (content[coordinate.x + i][coordinate.y + i] != '-' &&
                                content[coordinate.x + i][coordinate.y + i] != word.charAt(i)) return false;
                    }
                    break;
                case HORIZONTAL_BACK:
                    if (coordinate.y - word.length() < 0 )
                        return false;
                    for (int i = 0; i < word.length(); i++) {
                        if (content[coordinate.x ][coordinate.y - i] != '-' &&
                                content[coordinate.x ][coordinate.y - i] != word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL_BACK:
                    if(coordinate.x-word.length()<0) return false;
                    for(int i=0;i<word.length();i++){
                        if(content[coordinate.x-i][coordinate.y] !='-' &&
                                content[coordinate.x-i][coordinate.y] !=word.charAt(i)) return false;
                    }
                    break;
                case DIAGONAL_BACK:
                    if (coordinate.y - word.length() < 0 || coordinate.x - word.length() < 0)
                        return false;
                    for (int i = 0; i < word.length(); i++) {
                        if (content[coordinate.x - i][coordinate.y - i] != '-' &&
                                content[coordinate.x - i][coordinate.y - i] != word.charAt(i)) return false;
                    }
                    break;

            }

        return true;
    }
    public void display(char[][] content){
        int gridSize=content[0].length;
        for (int i = 0; i <gridSize ; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(content[i][j]);
            }
            System.out.println("");
        }
    }
    private void randomFillGrid(char[][] content){
        int gridSize=content[0].length;
        String allCAP="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i <gridSize ; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(content[i][j]=='-'){
                    int randomIndex= ThreadLocalRandom.current().nextInt(0,allCAP.length());

                    content[i][j]= allCAP.charAt(randomIndex);
                }
            }
        }
    }
}

