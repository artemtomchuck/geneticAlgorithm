import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by arty on 27.02.16.
 * Welcome to hell
 */

public class Genetic {
    public double fitnessFn(double x){ // ->MAX
        //return x*Math.sin(x);

        //return Math.exp(x)*Math.sin(x);

        //return 1/x;

        // return 2*x*x*x - 15*x*x+ 36*x - 14;

        //return Math.sin(x)*Math.cos(x);

        //попробуем подставить случайную функцию. Результат всега будет разным
        // return randInt(-100, 100)*Math.pow(x, randInt(0,5)*Math.sin(x));

        //return Math.pow(x, Math.sin(x));

        return Math.log(x)*Math.sin(x);

    }

    public Double[] currentPopulation;     // только X координаты для исходной популяции

    private Point2D.Double currentBestDecision;
    private int unsuccessfulPopulationNumberPerMutation;
    private int unsuccessfulMutationNumber;

    private int argumentMaxBitSize;

    private String intToBinaryString(int input, int outputBitNum) {
        String res = Integer.toBinaryString(input);

        if(res.length() < outputBitNum) {
            res = new String(new char[ outputBitNum - res.length() ]).replace("\0", "0") + res;
        }

        return res;
    }

    public void changePopulation() {
        Double[] newPopulation  = currentPopulation.clone(); // важно, чтобы объект currentPopulation не изменился

        for(int i = 0; i < currentPopulation.length; i++) {

            String child = crossover(intToBinaryString(currentPopulation[i].intValue(), argumentMaxBitSize),
                    intToBinaryString(currentPopulation[randInt(0,currentPopulation.length-1)].intValue(), argumentMaxBitSize));

            newPopulation[i] = (double)Integer.parseInt(child, 2);

        }

        currentPopulation = newPopulation;


        if(findBestCurrentDecision().getY() > currentBestDecision.getY()) {
            currentBestDecision = findBestCurrentDecision();
        }
        else
        {
            unsuccessfulPopulationNumberPerMutation++;
        }


        if(unsuccessfulPopulationNumberPerMutation >= 100) {
            unsuccessfulPopulationNumberPerMutation = 0;

            int randElementIndex = randInt(0,currentPopulation.length-1);

            currentPopulation[randElementIndex] =
                    (double) Integer.parseInt(
                            invertBits(
                                    intToBinaryString(currentPopulation[randElementIndex].intValue(), argumentMaxBitSize)

                                      ),
                                              2);


            unsuccessfulMutationNumber++;

        }


    }

    // на вход требует две строки в бинарном представлении. Можно, конечно, подать и произвольные строки, но тогда результат просто нельзя будет преобразовать в двоичный код
     private String crossover(String e1, String e2){

        //int anchor = 1;  // in perspective rand()

        int anchor = randInt(0,e1.length()-1);


        StringBuilder res = new StringBuilder(e1);

        for(int i = anchor; i < e1.length(); i++){
            res.setCharAt(i, e2.charAt(i) );          // заменяем все символы e1 на символы e2 после якоря anchor
        }

        return res.toString();
    }

    public static String invertBits(String e) {

        String res = "";
        char replacement;

        for(int i = 0; i < e.length(); i++){

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


    private int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void printCurrentPopulation() {
        for(double x : currentPopulation) {
            System.out.println(x);
        }
    }

    Point2D.Double findBestCurrentDecision() {

        Point2D.Double res = new Point2D.Double(currentPopulation[0], fitnessFn(currentPopulation[0])); // если мы не найдём лучшего решения на следующих особях данного поколения, то данная особь и есть решением

        // нам не нужно проверять нулевой элемент массива самого с собой. Поэтому начнём с первого
        for(int i = 1; i < currentPopulation.length; i++) {

            if (fitnessFn(currentPopulation[i]) > res.getY()) {
                res.setLocation(currentPopulation[i], fitnessFn(currentPopulation[i]));
            }
        }

        return res;
    }

    public Genetic(Double[] initialPopulation) {
        currentPopulation = initialPopulation;

/*
        Пока что будем работать только с областью определения. Мне не зачем еще таскать за собой значения ф-и.
        Критерием успешности алгоритма - это нахождение правильной точки из области определения ф-и.
        Значение функции всегда можно однозначно вычислить, исходя из значения точки Х.
*/

        Double maxFromInitialPopulation =  Collections.max(Arrays.asList(initialPopulation));

        // задаём область определения будущего решения по количетсву разрядов максимального элемента исходной популяции
        argumentMaxBitSize = Integer.toBinaryString(maxFromInitialPopulation.intValue()).length();


        currentBestDecision = findBestCurrentDecision();
        unsuccessfulPopulationNumberPerMutation = 0;
        unsuccessfulMutationNumber = 0;


    }

    private void pressAnyKeyToContinue()
    {
        System.out.println("Press any key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    /* TODO: слишком много всего просходит во вне класса
        В main должно быть только создание объекта и вызов метода решения.

     */
    public static void main(String[] args) {

        Genetic genAl = new Genetic(new Double []{65.0, 65.0, 65.0});


        System.out.println("INITIAL POPULATION");
        genAl.printCurrentPopulation();


        infiniteLoop:
        while(true) {

            if(genAl.unsuccessfulMutationNumber > 100) {
                System.out.println("Decision was found");
                System.out.println("Ymax = " + genAl.currentBestDecision.getY() + " at x = " + genAl.currentBestDecision.getX());

                break infiniteLoop;
            }

            genAl.changePopulation();



            System.out.println("NEW POPULATION");
            genAl.printCurrentPopulation();
            System.out.println(genAl.currentBestDecision);



           // genAl.pressAnyKeyToContinue();
        }



    }
}
