package com.fzu.result;

import com.fzu.utils.MsgCodeUtils;
import com.fzu.utils.Page;
import com.fzu.utils.ResponseCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ServiceResult<E> implements Serializable {
    private static final long serialVersionUID = -2235155451651905167L;
    private int msgCode;
    private String errMsg;
    private E data;
    private List<E> dataList;
    private Integer total;
    private Page<E> page;
    private LocalDateTime requestDateTime;
    private LocalDateTime receiptDateTime;
    private LocalDateTime returnDateTime;

    public ServiceResult() {
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ServiceResult(int msgCode) {
        this.msgCode = msgCode;
        this.errMsg = MsgCodeUtils.getErrMsg(msgCode);
        this.receiptDateTime = LocalDateTime.now();
    }

    public ServiceResult(int msgCode, String errMsg) {
        this.msgCode = msgCode;
        this.errMsg = errMsg;
        this.receiptDateTime = LocalDateTime.now();
    }

    public ServiceResult(int msgCode, String errMsg, E data) {
        this(msgCode, errMsg);
        this.data = data;
    }

    public ServiceResult(int msgCode, E data) {
        this(msgCode);
        this.data = data;
    }

    public ServiceResult(int msgCode, Page<E> page) {
        this(msgCode);
        this.page = page;
    }

    public ServiceResult(int msgCode, List<E> dataList) {
        this(msgCode);
        this.dataList = dataList;
    }

    public ServiceResult(int msgCode, List<E> dataList, Integer total) {
        this(msgCode);
        this.dataList = dataList;
        this.total = total;
    }

    public ServiceResult(int msgCode, String errMsg, List<E> dataList) {
        this(msgCode, errMsg);
        this.dataList = dataList;
    }

    public LocalDateTime getRequestDateTime() {
        return this.requestDateTime;
    }

    public void setRequestDateTime(LocalDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public LocalDateTime getReceiptDateTime() {
        return this.receiptDateTime;
    }

    public void setReceiptDateTime(LocalDateTime receiptDateTime) {
        this.receiptDateTime = receiptDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return this.returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public List<E> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public Page<E> getPage() {
        return this.page;
    }

    public void setPage(Page<E> page) {
        this.page = page;
    }

    public boolean isSuccess() {
        return this.msgCode == ResponseCode.SUCCESS.getCode();
    }

    public int getMsgCode() {
        return this.msgCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public E getData() {
        return this.data;
    }

    public static <E> ServiceResult<E> createBySuccessList(List<E> dataList) {
        return new ServiceResult(ResponseCode.SUCCESS.getCode(), dataList);
    }

    public static <E> ServiceResult<E> createBySuccess(List<E> dataList, Integer total) {
        return new ServiceResult(ResponseCode.SUCCESS.getCode(), dataList, total);
    }

    public static <E> ServiceResult<E> createBySuccess(E data) {
        return new ServiceResult(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <E> ServiceResult<E> createBySuccessMessage(String msg) {
        return createServerResponse(ResponseCode.CREATE_SUCCESS.getCode(), msg);
    }

    public static <E> ServiceResult<E> deleteBySuccessMessage(String msg) {
        return createServerResponse(ResponseCode.DELETE_SUCCESS.getCode(), msg);
    }

    public static <E> ServiceResult<E> createByError() {
        return createServerResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <E> ServiceResult<E> createByErrorMessage(String errorMessage) {
        return createServerResponse(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <E> ServiceResult<E> unLogin() {
        return createServerResponse(ResponseCode.UN_LOGIN.getCode(), MsgCodeUtils.getErrMsg(ResponseCode.UN_LOGIN.getCode()));
    }

    public static <E> ServiceResult<E> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return createServerResponse(errorCode, errorMessage);
    }

    private static <E> ServiceResult<E> createServerResponse(int code, String message) {
        return new ServiceResult(code, message);
    }
}
