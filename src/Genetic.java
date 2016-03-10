/**
 * Created by arty on 27.02.16.
 */
public class Genetic {
    public double f(double x){
        return x*Math.sin(x);
    } // область определения нашей ф-и [0,18]. Но для простоты сделаем [0,15], чтобы уложить искомое число из области определения в 4 бита ХХХХ

    public double[] initialPopulation;     // только X координаты для исходной популяции
    public double analyticDecision;        // значение ф-и всегда можно найти по её аргументу, поэтому нам не зачем его за собой таскать

    public void changePopulation() {
        // здесь будет хардкод. Т.е. реализация этой процедуры зависит от массива initialPopulation
        double[] newPopulation = initialPopulation;

        // и здесь я понял, что голова уже совсем не варит и пора спать.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    }



    // вопрос. Зачем нам тип данных boolean ? Уже ведь есть готовая ф-я, которая переводит число в бинарное представление строки.


    // на вход требует две строки в бинарном представлении. Можно, конечно, подать и произвольные строки, но тогда результат просто нельзя будет преобразовать в двоичный код
    public String crossover(String e1, String e2){

        int anchor = 1;  // in perspective rand()
        StringBuilder res = new StringBuilder(e1);

        for(int i = anchor; i < e1.length(); i++){
            res.setCharAt(i, e2.charAt(i) );          // заменяем все символы e1 на символы e2 после якоря anchor
        }

        return res.toString();
    }


    public Genetic() {
        initialPopulation = new double[] {1.0, 2.0, 3.0, 4.0};
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

        Genetic genAl = new Genetic();

        for(double x : genAl.initialPopulation){

            if(x == genAl.analyticDecision) {
                System.out.println("Decision was found");
                System.out.println("Ymax = " + genAl.f(x) + " at x = " + x);

                break;
            }

            genAl.changePopulation();

        }





        System.out.println(Integer.toBinaryString(15)); // а есть ли обратное преобразование ? Есть. См. ниже


        int foo = Integer.parseInt("1111", 2);

        System.out.println(foo);



        StringBuilder myName = new StringBuilder("domanokz");
        myName.setCharAt(4, 'x');

        String myNameStr =  myName.toString();

        System.out.println(myNameStr);



        System.out.println(

                genAl.crossover("aaaaa", "bb9bb")

        );


        //System.out.println(genAl.initialPopulation[1]);

        //System.out.println(genAl.initialPopulation.length);


    }
}
