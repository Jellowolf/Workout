package com.personal.jello.workout.utility;

import io.javalin.http.Context;

public class RequestUtility {
    public static String getQueryLocale(Context ctx) {
        return ctx.queryParam("locale");
    }
}
