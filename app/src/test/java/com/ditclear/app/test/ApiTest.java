package com.ditclear.app.test;


import com.ditclear.app.Contributor;
import com.ditclear.app.GitHub;
import com.ditclear.app.GitHubFactory;
import com.ditclear.app.test.annotation.JSpec;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

/**
 * 页面描述：api测试
 * <p>
 * Created by ditclear on 2017/2/9.
 */
public class ApiTest {

    @Rule
    public ErrorRule mRule=new ErrorRule();

    public Observable<List<Contributor>> loadContributor(){
        return GitHubFactory.getInstance().create(GitHub.class)
                .contributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Test
    @JSpec(desc = "countDownLatch")
    public void countDownLatch() throws Exception {

        final CountDownLatch countDownLatch=new CountDownLatch(1);
        loadContributor()
                .subscribe(new Action1<List<Contributor>>() {
                    @Override
                    public void call(List<Contributor> contributors) {
                        countDownLatch.countDown();
                    }
                });

        countDownLatch.await();
        fail("countDownLatch");

    }

    @Test
    @JSpec(desc = "SchedulersHook")
    public void schedulersHook() throws Exception {

        loadContributor()
                .subscribe(new Action1<List<Contributor>>() {
                    @Override
                    public void call(List<Contributor> contributors) {
                        fail("sss");
                    }
                });

    }

    @BeforeClass
    public static void setup() {
        // 让Schedulers.io()返回当前线程
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook(){
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }

        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }


    @Test
    @JSpec(desc = "TestSubscribe")
    public void testSubscribe() throws Exception {
        TestSubscriber testSubscriber=new TestSubscriber();
        loadContributor().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        assertThat(testSubscriber.getOnCompletedEvents().get(0)).isInstanceOf(String.class);

    }

    @Test
    @JSpec(desc = "toBlocking")
    public void blocking() throws Exception {
        List<Contributor> list=loadContributor()
                .toBlocking().last();

        assertThat(list.get(0)).isInstanceOf(String.class);


    }
}
