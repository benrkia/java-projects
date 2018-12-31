package com.benrkia.bank;

import java.io.Serializable;

public class Response implements Serializable {

    private String header;
    private String errorMsg;
    private String successMsg;
    private Object res;
    private Object req;

    private Response(){ }

    public static Response createResponse(Object res, String msg, boolean isError){
        Response response = new Response();

        if(isError)
            response.setErrorMsg(msg);
        else
            response.setSuccessMsg(msg);

        response.setHeader("response");
        response.setRes(res);
        response.setReq(null);

        return response;
    }

    public static Response createRequest(Object req, String msg, boolean isError){
        Response response = new Response();

        if(isError)
            response.setErrorMsg(msg);
        else
            response.setSuccessMsg(msg);

        response.setHeader("request");
        response.setReq(req);
        response.setRes(null);

        return response;
    }

    private String getHeader() {
        return header;
    }

    private void setHeader(String header) {
        this.header = header;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    private void setErrorMsg(String errorMsg) {
        this.successMsg = null;
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    private void setSuccessMsg(String successMsg) {
        this.errorMsg = null;
        this.successMsg = successMsg;
    }

    public Object getRes() {
        return res;
    }

    private void setRes(Object res) {
        this.res = res;
    }

    public Object getReq() {
        return req;
    }

    private void setReq(Object req) {
        this.req = req;
    }

    public boolean isReq(){
        return getHeader().equals("request");
    }

    public boolean isError(){
        return this.errorMsg!=null;
    }
}
