package io.renren.entity;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/1/19 14:31.
 */
public class TransInfo {
    private String KfAccount;

    public String getKfAccount() {
        return KfAccount;
    }

    public void setKfAccount(String kfAccount) {
        KfAccount = kfAccount;
    }

    public TransInfo() {
    }

    public TransInfo(String kfAccount) {
        KfAccount = kfAccount;
    }
}
