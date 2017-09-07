# Потоки в Java

## Life cycle of a Thread

1. Состояние потока: __New__<br>
Когда мы создаем объект __Thread__, используя оператор __new__, поток находится в состоянии __New__. В этом состоянии поток еще не работает.
2. Состояние пото: __Runnable__<br>
При вызове метода __start()__ созданного объекта __Thread__, его состояние изменяется на __Runnable__ и управление потоком передается Планировщику потоков(Thread scheduler).
3. Состояние потока: __Running__<br>
Когда поток будет запущен, его состояние изменится на __Running__. Планировщик потоков выбирает один поток из своего общего пула потоков и изменяет его состояние на Running. Сразу после этого процессор начинает выполнение этого потока. Во время выполнения состояние потока также может измениться на Runnable, Dead, или Blocked.
4. Состояние потока: __Blocked__ или __Waiting__<br>
Поток может ждать другой поток для завершения своей работы, например, ждать освобождение ресурсов или ввода-вывода. В этом случае его состояние изменится на Waiting. После того, как ожидание потока закончилось, его состояние изменится на Runnable и он возвратится в общий пул потоков.
5. Состояние потока: __Dead__<br>
После того, как поток завершает выполнение, его состояние изменится на Dead, то есть он отработал свое и уже не нужен.

__Thread__ в Java можно назвать _легковесным_ процессом. Он требует меньше ресурсов для создания и существует в процессе, деля с ним ресурсы.

![Life cycle of a Thread](/src/main/resources/img/lifecycle.png)

__Преимущества потоков:__

1. Потоки намного легче процессов, они требуют меньше времени и ресурсов.
2. Переключение контекста между потоками намного быстрее, чем между процессами.
3. Намного проще добиться взаимодействия между потоками, чем между процессами.

Java предоставляет два способа программно создать поток:

1. Реализация интерфейса java.lang.Runnable;
2. Расширение класса java.lang.Thread.

## Примеры

### Пример создания Thread с помощью интерфейса Runnable
Для того, чтобы класс был runnable, мы должны реализовать интерфейс __java.lang.Runnable__ и обеспечить реализацию метода __public void run()__. Чтобы использовать этот класс, как поток, мы должны создать объект __Thread__, передавая объект __runnable__ класса, а затем вызвать метод __start()__, чтобы выполнился метод __run()__ в отдельном потоке.

```java
public class HeavyWorkRunnable implements Runnable {
 
    @Override
    public void run() {
        System.out.println("Начинаем обработку в отдельном потоке - " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            // для примера будем выполнять обработку базы данных
            doDBProcessing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Заканчиваем обработку в отдельном потоке - " + Thread.currentThread().getName());
    }
 
    // метод псевдообработки базы данных
    private void doDBProcessing() throws InterruptedException {
        Thread.sleep(5000);
    }
 
}
```

### Пример создания Thread с помощью класса Thread

Мы можем наследовать класс __java.lang.Thread__ для создания собственного класса Thread и переопределить метод __run()__. Тогда мы можем создать экземпляр этого класса и вызвать метод __start()__ для того, чтобы выполнить метод __run()__.

```java
public class MyThread extends Thread {
 
    public MyThread(String name) {
        super(name);
    }
 
    @Override
    public void run() {
        System.out.println("Стартуем наш поток " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            // для примера будем выполнять обработку базы данных
            doDBProcessing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Заканчиваем наш поток " + Thread.currentThread().getName());
    }
    // метод псевдообработки базы данных
    private void doDBProcessing() throws InterruptedException {
        Thread.sleep(5000);
    }
     
}
```

Запуск потоков происходит следующим образом
```java
public class ThreadRunExample {
 
    public static void main(String[] args){
        Thread t1 = new Thread(new HeavyWorkRunnable(), "t1");
        Thread t2 = new Thread(new HeavyWorkRunnable(), "t2");
        System.out.println("Стартуем runnable потоки");
        t1.start();
        t2.start();
        System.out.println("Runnable потоки в работе");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        System.out.println("Стартуем наши кастомные потоки");
        t3.start();
        t4.start();
        System.out.println("Кастомные потоки в работе");
         
    }
}
```

__Runnable vs Thread__

Если ваш класс предоставляет больше возможностей, чем просто запускаться в виде Thread, то вам лучше реализовать интерфейс Runnable. Если же вам просто нужно запустить в отдельном потоке, то вы можете наследоваться от класса Thread.
Реализация Runnable является более предпочтительной, поскольку Java поддерживает реализацию нескольких интерфейсов. Если вы наследуете класс Thread, то вы уже не можете наследовать другие классы.




Источники:  
[Потоки в Java. Наследуемся от Thread, реализуем интерфейс Runnable](http://javadevblog.com/potoki-v-java-nasleduemsya-ot-thread-realizuem-interfejs-runnable.html)  
[Multithreading in Java](https://www.javatpoint.com/multithreading-in-java)  
[Cay S. Horstmann - Core Java, Volume I--Fundamentals](https://www.amazon.com/Core-Java-I-Fundamentals-10th/dp/0134177304)  
[Cay S. Horstmann - Core Java, Volume II--Advanced Features: 2](https://www.amazon.com/Core-Java-II-Advanced-Features-ebook/dp/B01MTY3K7C)  