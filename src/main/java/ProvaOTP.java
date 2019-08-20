import serverRdF.registrationRdF.WaitingThread;

import java.util.Random;
import java.util.Scanner;


import java.util.Random;
import java.util.Scanner;

    public class ProvaOTP {

        public ProvaOTP(){}

        public static void main(String[] args) {
            ProvaOTP main = new ProvaOTP();
            String otp = otp();
            System.out.println("OTP: " + otp);
            WaitingThread t1 = main.createThread();
            main.createCheckThread(t1, otp);
            System.out.println("Thread creati");
            return;
        }

        private static String otp(){
            String res = "";
            Random rd = new Random();
            for(int i=0; i<6; i++){
                res += rd.nextInt(10);
            }
            return res;
        }

        public void createCheckThread(WaitingThread t, String o){
            new CheckThread(t, o).start();
        }

        public WaitingThread createThread(){
            return new WaitingThread();
        }

        class WaitingThread extends Thread {
            public WaitingThread() {
                start();
            }

            public void run() {
                int tenMininSec = 10000;
                try {
                    sleep(tenMininSec);
                    System.out.println("NOPE");
                } catch (InterruptedException e) {
                    System.out.println("OK");
                }
            }
        }

        class CheckThread extends Thread{
            private WaitingThread thread;
            private String code;

            public CheckThread(WaitingThread t, String otp){
                thread = t;
                code = otp;
            }

            public void run(){
                check(thread);
            }

            private void check(WaitingThread t1){
                Scanner sc = new Scanner(System.in);
                String s = sc.next();
                if(s.equals(code)){
                    t1.interrupt();
                }
            }
        }
    }
