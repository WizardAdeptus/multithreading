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

## Multithreading in Java

__How to create thread__
There are two ways to create a thread:

1. By extending Thread class
2. By implementing Runnable interface.

__Commonly used Constructors of Thread class:__

* Thread()
* Thread(String name)
* Thread(Runnable r)
* Thread(Runnable r,String name)

__Commonly used methods of Thread class:__

1. __public void run():__ is used to perform action for a thread.
1. __public void start():__ starts the execution of the thread.JVM calls the run() method on the thread.
1. __public void sleep(long miliseconds):__ Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
1. __public void join():__ waits for a thread to die.
1. __public void join(long miliseconds):__ waits for a thread to die for the specified miliseconds.
1. __public int getPriority():__ returns the priority of the thread.
1. __public int setPriority(int priority):__ changes the priority of the thread.
1. __public String getName():__ returns the name of the thread.
1. __public void setName(String name):__ changes the name of the thread.
1. __public Thread currentThread():__ returns the reference of currently executing thread.
1. __public int getId():__ returns the id of the thread.
1. __public Thread.State getState():__ returns the state of the thread.
1. __public boolean isAlive():__ tests if the thread is alive.
1. __public void yield():__ causes the currently executing thread object to temporarily pause and allow other threads to execute.
1. __public void suspend():__ is used to suspend the thread(depricated).
1. __public void resume():__ is used to resume the suspended thread(depricated).
1. __public void stop():__ is used to stop the thread(depricated).
1. __public boolean isDaemon():__ tests if the thread is a daemon thread.
1. __public void setDaemon(boolean b):__ marks the thread as daemon or user thread.
1. __public void interrupt():__ interrupts the thread.
1. __public boolean isInterrupted():__ tests if the thread has been interrupted.
1. __public static boolean interrupted():__ tests if the current thread has been interrupted.


__Can we start a thread twice__

No. After starting a thread, it can never be started again. If you does so, an _IllegalThreadStateException_ is thrown. In such case, thread will run once but for second time, it will throw exception.

__What if we call run() method directly instead start() method?__

Each thread starts in a separate call stack.
Invoking the run() method from main thread, the __run() method goes onto the current call stack__ rather than at the beginning of a new call stack.

__The join() method__

The join() method waits for a thread to die. In other words, it causes the currently running threads to stop executing until the thread it joins with completes its task.  
__Syntax:__  
public void join() throws InterruptedException
public void join(long milliseconds) throws InterruptedException

### Naming Thread and Current Thread

__Naming Thread__

The Thread class provides methods to change and get the name of a thread. By default, each thread has a name i.e. thread-0, thread-1 and so on. By we can change the name of the thread by using setName() method. The syntax of setName() and getName() methods are given below:

* __public String getName():__ is used to return the name of a thread.
* __public void setName(String name):__ is used to change the name of a thread.

__Current Thread__

The currentThread() method returns a reference of currently executing thread.
```java
public static Thread currentThread()
```

### Priority of a Thread (Thread Priority)

Each thread have a priority. Priorities are represented by a number between 1 and 10. In most cases, thread schedular schedules the threads according to their priority (known as preemptive scheduling). But it is not guaranteed because it depends on JVM specification that which scheduling it chooses.

__3 constants defiend in Thread class:__

* __public static int MIN_PRIORITY__
* __public static int NORM_PRIORITY__
* __public static int MAX_PRIORITY__

Default priority of a thread is 5 (NORM_PRIORITY). The value of MIN_PRIORITY is 1 and the value of MAX_PRIORITY is 10.

### Daemon Thread in Java

Daemon thread in java is a service provider thread that provides services to the user thread. Its life depend on the mercy of user threads i.e. when all the user threads dies, JVM terminates this thread automatically.

There are many java daemon threads running automatically e.g. gc, finalizer etc.

You can see all the detail by typing the jconsole in the command prompt. The jconsole tool provides information about the loaded classes, memory usage, running threads etc.

__Points to remember for Daemon Thread in Java__

* It provides services to user threads for background supporting tasks. It has no role in life than to serve user threads.
* Its life depends on user threads.
* It is a low priority thread.

***
__Why JVM terminates the daemon thread if there is no user thread?__  
The sole purpose of the daemon thread is that it provides services to user thread for background supporting task. If there is no user thread, why should JVM keep running this thread. That is why JVM terminates the daemon thread if there is no user thread.

***

__Methods for Java Daemon thread by Thread class__

The java.lang.Thread class provides two methods for java daemon thread.

1. __public void setDaemon(boolean status)__ is used to mark the current thread as daemon thread or user thread.
2. __public boolean isDaemon()__ is used to check that current is daemon.

___Note:___  
_If you want to make a user thread as Daemon, it must not be started otherwise it will throw __IllegalThreadStateException__._

### Java Thread Pool

Java Thread pool represents a group of worker threads that are waiting for the job and reuse many times.

In case of thread pool, a group of fixed size threads are created. A thread from the thread pool is pulled out and assigned a job by the service provider. After completion of the job, thread is contained in the thread pool again.

***
__Advantage of Java Thread Pool__  
Better performance It saves time because there is no need to create new thread.

***
__Real time usage__  
It is used in Servlet and JSP where container creates a thread pool to process the request.

***

__Example of Java Thread Pool__

Let's see a simple example of java thread pool using ExecutorService and Executors.

__File: WorkerThread.java__
```java
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  

class WorkerThread implements Runnable {  
    private String message;  

    public WorkerThread(String s){  
        this.message = s;  
    } 

    public void run() {  
        System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);  

        // call processmessage method that sleeps the thread for 2 seconds 
        processmessage();

        // prints thread name   
        System.out.println(Thread.currentThread().getName() + " (End)");
    }  

    private void processmessage() {  
        try {  
            Thread.sleep(2000);  
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        }  
    }  
}
```

__File: JavaThreadPoolExample.java__
```java
public class TestThreadPool {  
     public static void main(String[] args) { 

        // creating a pool of 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);  

        for (int i = 0; i < 10; i++) {  
            Runnable worker = new WorkerThread("" + i);  

            // calling execute method of ExecutorService  
            executor.execute(worker);
        }  
        executor.shutdown();  

        while (!executor.isTerminated()) {
        }  
  
        System.out.println("Finished all threads");  
    }  
 }
```

### ThreadGroup in Java

Java provides a convenient way to group multiple threads in a single object. In such way, we can suspend, resume or interrupt group of threads by a single method call.

___Note:___  
_Now suspend(), resume() and stop() methods are deprecated._

Java thread group is implemented by java.lang.ThreadGroup class.

__Constructors of ThreadGroup class__

There are only two constructors of ThreadGroup class.

1. __ThreadGroup(String name)__ creates a thread group with given name.
2. __ThreadGroup(ThreadGroup parent, String name)__ creates a thread group with given parent group and name.

__Important methods of ThreadGroup class__

There are many methods in ThreadGroup class. A list of important methods are given below.

|№|Method|Description|
|:-:|:-|:-|
|1|__int activeCount()__|returns no. of threads running in current group|
|2|__int activeGroupCount()__|returns a no. of active group in this thread group|
|3|__void destroy()__|destroys this thread group and all its sub groups|
|4|__String getName()__|returns the name of this group|
|5|__ThreadGroup getParent()__|returns the parent of this group|
|6|__void interrupt()__|interrupts all threads of this group|
|7|__void list()__|prints information of this group to standard console|

А code to group multiple threads.
```java
ThreadGroup tg1 = new ThreadGroup("Group A");
Thread t1 = new Thread(tg1,new MyRunnable(),"one");
Thread t2 = new Thread(tg1,new MyRunnable(),"two");
Thread t3 = new Thread(tg1,new MyRunnable(),"three"); 
```

Now all 3 threads belong to one group. Here, tg1 is the thread group name, MyRunnable is the class that implements Runnable interface and "one", "two" and "three" are the thread names.

Now we can interrupt all threads by a single line of code only.
```java
Thread.currentThread().getThreadGroup().interrupt();
```







## Источники:  
[Потоки в Java. Наследуемся от Thread, реализуем интерфейс Runnable](http://javadevblog.com/potoki-v-java-nasleduemsya-ot-thread-realizuem-interfejs-runnable.html)  
[Multithreading in Java](https://www.javatpoint.com/multithreading-in-java)  
[Cay S. Horstmann - Core Java, Volume I--Fundamentals](https://www.amazon.com/Core-Java-I-Fundamentals-10th/dp/0134177304)  
[Cay S. Horstmann - Core Java, Volume II--Advanced Features: 2](https://www.amazon.com/Core-Java-II-Advanced-Features-ebook/dp/B01MTY3K7C)  