package com.zxj.cloud_service_proxy_core.exception;

import com.zxj.cloud_service_proxy_core.constant.Constant;
import com.zxj.cloud_service_proxy_core.enums.ServiceProxyErrorCode;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;

/**
 * 运行时异常，请注意抛出位置，会触发事务回滚！！！！！！！！！
 * Created by ce on 2017/4/14.
 */
public class ServiceRuntimeException extends RuntimeException implements BaseExceptionInterface {


    private static final long serialVersionUID = -6509426443798876274L;

    private Integer errCode;

    private String errMsg;

    public ServiceRuntimeException(IntEnumConstant serviceErrorCode) {
        this(serviceErrorCode.getValue(), serviceErrorCode.getName());
    }

    public ServiceRuntimeException(IntEnumConstant serviceErrorCode, String errMsg) {
        this(serviceErrorCode.getValue(), serviceErrorCode.getName() + errMsg);
    }

    public ServiceRuntimeException(String errMsg) {
        this(ServiceProxyErrorCode.ERROR.getValue(), errMsg);
        this.setErrCode(ServiceProxyErrorCode.ERROR.getValue());
    }

    public ServiceRuntimeException(BaseExceptionInterface e) {
        this(e.getErrCode(), e.getErrMsg());
    }

    public ServiceRuntimeException(ServiceException e) {
        this(e.getErrCode(), e.getErrMsg());
    }

    public ServiceRuntimeException(Integer errCode, String errMsg) {
        super(String.format("errCode: %d, errMsg: %s", errCode, errMsg));
        this.setErrCode(errCode);
        this.setErrMsg(errMsg);
    }

    public ServiceRuntimeException(Object... infos) {
        this(getErrorCode(infos[0]), getInfos(infos));
    }

    public ServiceRuntimeException(Throwable e) {
        this(getErrorCode(e), getExceptionInfo(e));
    }

    public ServiceRuntimeException(Exception e) {
        this(getErrorCode(e), getExceptionInfo(e));
    }

    public ServiceRuntimeException(Exception e, String extraInfo) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }

    public ServiceRuntimeException(Throwable e, String extraInfo) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }

    public ServiceRuntimeException(String extraInfo, Throwable e) {
        this(getErrorCode(e), extraInfo + getExceptionInfo(e));
    }

    private static String getExceptionInfo(Object e) {
        if (e != null && e instanceof BaseExceptionInterface) {
            return ((BaseExceptionInterface) e).getErrMsg();
        } else if (e != null && e instanceof Constant) {
            return ((Constant) e).getName();
        } else {
            return e.toString();
        }
    }

    private static Integer getErrorCode(Object e) {
        if (e != null && e instanceof BaseExceptionInterface) {
            return ((BaseExceptionInterface) e).getErrCode();
        } else if (e != null && e instanceof IntEnumConstant) {
            return ((IntEnumConstant) e).getValue();
        } else {
            return ServiceProxyErrorCode.ERROR.getValue();
        }
    }

    public ServiceRuntimeException(IntEnumConstant serviceErrorCode, Object... info) {
        this(serviceErrorCode, getInfos(info));
    }

    private static String getInfos(Object[] info) {
        if (info != null && info.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object object : info) {
                if(object !=null) {
                    stringBuilder.append(getExceptionInfo(object));
                }
            }
            return stringBuilder.toString();
        }
        return "错误";
    }

    @Override
    public Integer getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}