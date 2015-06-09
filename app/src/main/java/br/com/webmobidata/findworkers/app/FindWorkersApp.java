package br.com.webmobidata.findworkers.app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MarceloToyoda on 09/06/2015.
 */
public class FindWorkersApp extends Application {

    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();

    public void addAsyncTask(AsyncTask task){
        tasks.add(task);
    }

    public void removeAsyncTask(AsyncTask task){
        tasks.remove(task);
    }

    @Override
    public void onTerminate() {
        for (AsyncTask task : tasks){
            task.cancel(true);
        }
    }
}
