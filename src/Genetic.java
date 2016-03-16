import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by arty on 27.02.16.
 */

/* С отрицательными числами пока что беда. Но это можно будет пофиксить при желании позже.

    Exception in thread "main" java.lang.StringIndexOutOfBoundsException: String index out of range: 8
	at java.lang.String.charAt(String.java:658)
	at Genetic.crossover(Genetic.java:66)
	at Genetic.changePopulation(Genetic.java:36)
	at Genetic.main(Genetic.java:113)

 */
public class Genetic {
    public double f(double x){
        return x*Math.sin(x);
    } // область определения нашей ф-и [0,18]. Но для простоты сделаем [0,15], чтобы уложить искомое число из области определения в 4 бита ХХХХ

    public Double[] currentPopulation;     // только X координаты для исходной популяции
    public Double analyticDecision;        // значение ф-и всегда можно найти по её аргументу, поэтому нам не зачем его за собой таскать


    private String intToBinaryString(int input, int outputBitNum) {
        String res = Integer.toBinaryString(input);

        if(res.length() < outputBitNum) {
            res = new String(new char[ outputBitNum - res.length() ]).replace("\0", "0") + res;
        }

        return res;
    }

    public void changePopulation() {
        Double[] newPopulation  = currentPopulation.clone(); // важно, чтобы объект currentPopulation не изменился

        Double maxFromCurrentPopulation =  Collections.max(Arrays.asList(currentPopulation));
        int maxBitSize = Integer.toBinaryString(maxFromCurrentPopulation.intValue()).length();

        for(int i = 0; i < currentPopulation.length - 1; i++) {

            String child = crossover(intToBinaryString(currentPopulation[i].intValue(), maxBitSize),
                                     intToBinaryString(currentPopulation[i+1].intValue(), maxBitSize));

            newPopulation[i] = (double)Integer.parseInt(child, 2);

        }
        // дополняем граничные значения, т.е. скрещиваем последний элемент с первым

        newPopulation[newPopulation.length-1] =  (double)Integer.parseInt(
                crossover(intToBinaryString(currentPopulation[currentPopulation.length-1].intValue(), maxBitSize),
                         intToBinaryString(currentPopulation[0].intValue(), maxBitSize)), 2

        );

        currentPopulation = newPopulation;

    }

    // на вход требует две строки в бинарном представлении. Можно, конечно, подать и произвольные строки, но тогда результат просто нельзя будет преобразовать в двоичный код
     private String crossover(String e1, String e2){

        //int anchor = 1;  // in perspective rand()

        int anchor = randInt(0,e1.length()-1);


        StringBuilder res = new StringBuilder(e1);

        for(int i = anchor; i < e1.length(); i++){
            res.setCharAt(i, e2.charAt(i) );          // заменяем все символы e1 на символы e2 после якоря anchor
        }
            // мутация. Вероятность 5%
         if(randInt(1,100) == 5) {
             res = new StringBuilder(invertBits(res.toString())    );
         }

        return res.toString();
    }

    public static String invertBits(String e) {

        String res = new String();
        char replacement;

        for(int i = 0; i < e.length(); i++){
            //res.setCharAt(i, e2.charAt(i) );          // заменяем все символы e1 на символы e2 после якоря anchor

            switch(e.charAt(i)){
                case '0': replacement = '1';
                    break;
                case '1': replacement = '0';
                    break;
                case '+': replacement = '-';
                    break;
                case '-': replacement = '+';
                    break;
                default: replacement = '?'; // в таком случае процедура вызова что-то напорола, если имеем другие символы
                    break;
            }

            res += replacement;
        }

        return res;
    }

/*
    private int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
*/

    private static int randInt(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int)(Math.random() * ((max - min) + 1)) + min;


    }

    public Genetic(Double[] initialPopulation) {
        currentPopulation = initialPopulation;

/*
        Пока что будем работать только с областью определения. Мне не зачем еще таскать за собой значения ф-и.
        Критерием успешности алгоритма - это нахождение правильной точки из области определения ф-и.
        Значение функции всегда можно однозначно вычислить, исходя из значения точки Х.
*/


        /* точное аналитическое решение из wolframalpha.com (14.1724,14.2074)
           Пока что для отладки кода взята простая целочисленная точка
         */
        analyticDecision = 14.0;
    }

    public static void main(String[] args) {
/*
        Genetic genAl = new Genetic(new Double []{13.0, 40.0, 5.0, 6.0, 7.0, 8.0, 9.0});


        int populationNumber = 1;

        infiniteLoop:
        while(true) {
            for(double x : genAl.currentPopulation){

                if(x == genAl.analyticDecision) {
                    System.out.println("Decision was found");
                    System.out.println("Ymax = " + genAl.f(x) + " at x = " + x);

                    break infiniteLoop;
                }
            }

            genAl.changePopulation();
            populationNumber++;

            System.out.println("NEW POPULATION");
            for(double x : genAl.currentPopulation) {
                System.out.println(x);

            }
        }

        System.out.println(populationNumber + " популяция дала решение");
*/


        Double d =5.0;


        System.out.println(
        d.hashCode()
        );
/*

        System.out.println(
        invertBits("-11500")
        );
*/
        /*
System.out.println(
        Integer.parseInt("+5")


); // OK*/
/*
        System.out.println(
        Integer.toBinaryString(-6)             // метод предназначен только для беззнаковых преобразований!!! Хреново!!!
        );


        String s = Integer.toString(7,2); // the 2 is what converts it to binary  // решаем проблему обычным добавлением знака

        System.out.println(s);
*/
    }
}
