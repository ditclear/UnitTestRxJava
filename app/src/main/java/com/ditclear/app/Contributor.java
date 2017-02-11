package com.ditclear.app;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 页面描述：Contributor
 * <p>
 * Created by ditclear on 2017/2/9.
 */

public class Contributor {
    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
    public static Observable<List<Contributor>> loadContributor(String owner, String repo){
        return GitHubFactory.getInstance().create(GitHub.class)
                .contributors(owner, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
