package com.benrkia.market.configuration;

public class Answer {
    private String message;
    private boolean isSuccess;
    private Object object;
    private static Answer answer;

    private Answer(){}

    public static Answer getInstance(boolean isSuccess, String message){
        if(answer==null)
            answer = new Answer();
        answer.setMessage(message);
        answer.setSuccess(isSuccess);
        answer.setObject(null);
        return answer;
    }

    public static Answer getInstance(boolean isSuccess, String message, Object object){
        if(answer==null)
            answer = new Answer();
        answer.setMessage(message);
        answer.setSuccess(isSuccess);
        answer.setObject(object);
        return answer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
