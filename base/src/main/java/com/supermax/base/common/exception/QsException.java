package com.supermax.base.common.exception;

/*
 * @Author yinzh
 * @Date   2018/10/16 15:48
 * @Description
 */
public class QsException extends RuntimeException{

    private final QsExceptionType mType;
    private final String requestTag;

    public QsException(QsExceptionType type, String requestTag, String message) {
        super(message);
        this.mType =type;
        this.requestTag = requestTag;
    }

    public QsExceptionType getExceptionType() {
        return mType;
    }

    public Object getRequestTag() {
        return requestTag;
    }

}
