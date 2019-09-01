package io.renren.common.utils.poi.model;

import java.io.Serializable;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/3/16 16:49.
 */
public class ExcelBean implements Serializable {

    /**
     * 操作代码
     */
    private String operationCode;

    /**
     * 结果信息
     */
    private String resultInfo;

    /**
     * 成功标记
     */
    private boolean successFlag;

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }
}
