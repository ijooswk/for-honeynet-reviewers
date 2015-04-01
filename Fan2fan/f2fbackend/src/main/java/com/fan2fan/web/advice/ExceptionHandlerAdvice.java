package com.fan2fan.web.advice;

import com.fan2fan.service.Result;
import com.fan2fan.web.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * For the exception handles in the controller
 *
 * Created by huangsz on 2014/5/24.
 */
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ModelMap handleRuntimeException(Exception e, WebRequest request) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, Result.EXCEPTION.toString());
        logger.error("RuntimeException in Request: " + request.toString());
        logger.error(e.toString(), e);
        return map;
    }
}
