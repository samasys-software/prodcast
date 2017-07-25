package com.primeforce.prodcast.dto;

/**
 * Created by sarathan732 on 6/25/2016.
 */
public class Test {

    public static void printPyramid(int line, int high){
        if(line == 0 ) return;
        printPyramid(line-1,high);
        int noOfChars = high*2-1;
        int numberOfSpaces = high-line;
        for(int counter=0;counter<numberOfSpaces;counter++){
            System.out.print(" ");
        }

        for(int counter=0;counter<line;counter++){
            System.out.print("$ ");
        }
        System.out.println();

    }
    public static void main(String...args){
        printPyramid( 4, 4 );
    }
}
