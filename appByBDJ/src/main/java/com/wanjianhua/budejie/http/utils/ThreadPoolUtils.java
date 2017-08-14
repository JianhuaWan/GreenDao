package com.wanjianhua.budejie.http.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.wanjianhua.budejie.http.IHttpThreadExecutor;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ==========================================
 * <p/>
 * 作    者 : ying
 * <p/>
 * 创建时间 ： 2015/12/8.
 * <p/>
 * 用   途 :
 * <p/>
 * <p/>
 * ==========================================
 */
public class ThreadPoolUtils implements IHttpThreadExecutor {

    /**
     * 轮询线程
     */
    private Thread mThreadHandler;
    /**
     * 轮询handler
     */
    private Handler mHandler;

    /**
     * 最大核心线程数
     */
    private static final int MAX_THREAD_NUM = 5;

    /**
     * 线程池
     */
    private ExecutorService ThreadPool;

    /**
     * 任务队列
     */
    private LinkedList<Runnable> mQueue;

    /**
     * 任务调度方式
     */
    public Type mType = Type.FILO;

    /**
     * 防止handler没有初始化的控制量
     */
    private Semaphore mHandlerSemaphore = new Semaphore(0);

    /**
     * 控制线程池队列的信号量
     */
    private Semaphore mQueueSemphore;


    private ThreadPoolUtils() {
        init();
    }

    private static ThreadPoolUtils mInstance;

    public static ThreadPoolUtils getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolUtils.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolUtils();
                }
            }
        }
        return mInstance;
    }


    private void init() {
        mThreadHandler = new Thread() {
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        ThreadPool.execute(getTask());
                        try {
                            mQueueSemphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //handler初始化完成后  释放一个信号量
                mHandlerSemaphore.release();
                Looper.loop();
            }
        };
        mQueue = new LinkedList<>();
        mThreadHandler.start();
        ThreadPool = new ThreadPoolExecutor(MAX_THREAD_NUM, MAX_THREAD_NUM,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //每执行完一个线程 释放一个信号量
                mQueueSemphore.release();
            }
        };
        mQueueSemphore = new Semaphore(MAX_THREAD_NUM);
    }

    /**
     * 对外提供的添加任务方法
     *
     * @param runnable
     */
    public synchronized void addTask(Runnable runnable) {
        mQueue.add(runnable);
        if (mHandler == null) {
            try {
                mHandlerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mHandler.sendEmptyMessage(0x110);
    }

    private Runnable getTask() {
        if (mType == Type.FIFO)
            return mQueue.removeFirst();
        if (mQueue.size() > 0) {
            return mQueue.removeLast();
        }
        return null;
    }

    public enum Type {
        FIFO,
        FILO
    }

    public void shut() {
        mQueue.clear();
    }
}
