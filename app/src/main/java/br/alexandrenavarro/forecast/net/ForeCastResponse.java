package br.alexandrenavarro.forecast.net;

import br.alexandrenavarro.forecast.model.ForeCast;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class ForeCastResponse {

    private Data data;

    private class Data{
        protected ForeCast foreCast;
    }

    public ForeCast foreCast() {
        return data != null ? data.foreCast : null;
    }
}
