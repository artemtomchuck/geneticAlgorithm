import java.awt.geom.Point2D;

/**
 * Created by arty on 27.02.16.
 */
public class Genetic {
    public double f(double x){
        return x*Math.sin(x);
    }

    public double[] initialPopulation;     // только X координаты для исходной популяции
    public Point2D.Double analyticDecision;


    public Genetic() {
        initialPopulation = new double[] {1.0, 2.0, 3.0, 14.0};

        Пока что будем работать только с областью определения. Мне не зачем еще таскать за собой значения ф-и.
        Критерием успешности алгоритма - это нахождение правильной точки из области определения ф-и.
        Значение функции всегда можно однозначно вычислить, исходя из значения точки Х.



        /* точное аналитическое решение из wolframalpha.com (14.1724,14.2074)
           Пока что для отладки кода взята простая целочисленная точка
         */
        analyticDecision = new Point2D.Double(14.0,13.868502979728184);

    }



    public static void main(String[] args) {

        Genetic genAl = new Genetic();

        for(double x : genAl.initialPopulation){


            System.out.println(genAl.f(x));

            if(genAl.f(x) == genAl.analyticDecision.getY()) {
                System.out.println("Decision was found");
                System.out.println("Ymax = " + genAl.f(x) + " at x = " + x);

                break;
            }
        }


/*
        System.out.println(genAl.analyticDecision.getX());
        System.out.println(genAl.analyticDecision.getY());
*/
        //double res = f(1.0);
        //System.out.println(res);

        //System.out.println("Это самая крутая реализация  алгоритма!");
    }
}
