package br.com.webmobidata.findworkers.delegate;

import br.com.webmobidata.findworkers.app.FindWorkersApp;

/**
 * Created by MarceloToyoda on 09/06/2015.
 */
public interface AsyncTaskDelegate {
    void treatmentOnPostRequest();
    FindWorkersApp getApplication();
}
