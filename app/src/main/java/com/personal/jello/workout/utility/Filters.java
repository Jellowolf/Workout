package com.personal.jello.workout.utility;

import io.javalin.http.Handler;

import static com.personal.jello.workout.utility.RequestUtility.getQueryLocale;

public class Filters {
    public static Handler handleLocaleChange = ctx -> {
        if (getQueryLocale(ctx) != null) {
            ctx.sessionAttribute("locale", getQueryLocale(ctx));
            ctx.redirect(ctx.path());
        }
    };
}
