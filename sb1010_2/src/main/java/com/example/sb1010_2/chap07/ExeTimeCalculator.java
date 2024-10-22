package com.example.sb1010_2.chap07;

public class ExeTimeCalculator implements Calculator {

    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.printf("%s.fatorial(%d) time = %d\n",
                delegate.getClass().getSimpleName(),
                num, (end - start));
        return result;
    }
}
