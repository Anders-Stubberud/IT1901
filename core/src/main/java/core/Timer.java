package core;

public class Timer implements Runnable{
    private int seconds= 30; //maybe remove this and add this to function start

    public void run() {
        for (int i = seconds; i>=0; i--){
            System.out.println(i);


            try{
                Thread.sleep(1000);
                //countdown by 1000 ms every second
            }

            catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
