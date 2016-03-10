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

        for(int i = 0; i < initialPopulation.length - 1; i++) {

            String child = crossover(Integer.toBinaryString((int)initialPopulation[i]),
                                     Integer.toBinaryString((int)initialPopulation[i+1]));


             // pseudo code
            //newPopulation[i] = crossover(initialPopulation[i], initialPopulation[i+1]);

            newPopulation[i] = Integer.parseInt(child, 2);

        }
        // дополняем граничные значения, т.е. скрещиваем последний элемент с первым
        // см. баг в crossover

        newPopulation[newPopulation.length-1] =  Integer.parseInt(
                crossover(Integer.toBinaryString((int)initialPopulation[initialPopulation.length-1]),
                         Integer.toBinaryString((int)initialPopulation[0]))

        );

        initialPopulation = newPopulation;

    }



    // вопрос. Зачем нам тип данных boolean ? Уже ведь есть готовая ф-я, которая переводит число в бинарное представление строки.


    // Есть баг, наверное. Допустим, имеем число 5, оно будет преобразовано в двоичное 101.
    // А у меня, по ходу, логика завязана на то, что это будет 0101.

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
        initialPopulation = new double[] {3.0, 2.0, 3.0, 4.0};
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

           // genAl.changePopulation();

        }


        for(double x : genAl.initialPopulation) {
            System.out.println(x);

        }

        genAl.changePopulation();


        System.out.println("NEW POPULATION");


        for(double x : genAl.initialPopulation) {
            System.out.println(x);

        }



        System.out.println(Integer.toBinaryString(5));

/*


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

*/
        //System.out.println(genAl.initialPopulation[1]);

        //System.out.println(genAl.initialPopulation.length);


    }
}
