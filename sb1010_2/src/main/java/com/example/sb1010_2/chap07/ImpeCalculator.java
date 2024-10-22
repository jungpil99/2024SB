package com.example.sb1010_2.chap07;

public class ImpeCalculator implements Calculator {
    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = 1;
        for(long i = 1; i<= num; i++){
            result *= i;
        }
        long end = System.nanoTime();
        System.out.printf("ImpeCalculator factorial(%d) time = %d\n", num, (end -start));

        return result;
    }
}
