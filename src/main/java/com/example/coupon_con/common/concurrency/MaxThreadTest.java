package com.example.coupon_con.common.concurrency;

/**
 * packageName    : com.example.coupon_con.concurrency
 * fileName       : MaxThreadTest
 * author         : JAEIK
 * date           : 6/19/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/19/25       JAEIK       최초 생성
 */
public class MaxThreadTest {
    public static void main(String[] args) {
        /* 내 노트북 OS ~ ulimit -u  스레드 수 최대 1392 개
           JVM 이 만들 수 있는 스레드 수 sleep (대기상태) 합쳐서 2026개
           1:1 매핑이라 1392개 한도 CPU 코어 수 에 따라 스레드 처리를 못하면 오류발생
           OS 스레드 할당불가 ,  JVM 스레드 생성 불가
        */
        int count = 0;
        try {
            while (true) {
                Thread t = new Thread(() -> {
                    try {
                        // CPU 5초 점유
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                });
                t.start();
                count++;
            }
        } catch (OutOfMemoryError | IllegalThreadStateException e) {
            e.printStackTrace();
            System.out.println("최대 생성 가능한 스레드 수: " + count);
        }
    }
}
