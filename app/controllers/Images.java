package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.LinkUtil;

/**
 * Created by marcelsteffen on 12.09.16.
 */
public class Images extends Controller {
    public Result serve(String file) {
        return ok(new java.io.File(LinkUtil.baseUploadPath() + file));
    }
}
