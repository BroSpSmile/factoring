/**
 * 
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 资产报表
 * @author smile.jing
 * @version $Id: Assets.java, v 0.1 2019年8月18日 下午10:52:12 smile.jing Exp $
 */
public class Assets implements Serializable {

    /** UID */
    private static final long serialVersionUID = 1596208858554369973L;

    /** 期末余额 */
    private double            zcamount;

    /** 年初余额 */
    private double            zctotalamount;

    /** 期末负债 */
    private double            fzamount;

    /** 年初负债 */
    private double            fztotalamount;

    /**
     * @return the zcamount
     */
    public double getZcamount() {
        return zcamount;
    }

    /**
     * @param zcamount the zcamount to set
     */
    public void setZcamount(double zcamount) {
        this.zcamount = zcamount;
    }

    /**
     * @return the zctotalamount
     */
    public double getZctotalamount() {
        return zctotalamount;
    }

    /**
     * @param zctotalamount the zctotalamount to set
     */
    public void setZctotalamount(double zctotalamount) {
        this.zctotalamount = zctotalamount;
    }

    /**
     * @return the fzamount
     */
    public double getFzamount() {
        return fzamount;
    }

    /**
     * @param fzamount the fzamount to set
     */
    public void setFzamount(double fzamount) {
        this.fzamount = fzamount;
    }

    /**
     * @return the fztotalamount
     */
    public double getFztotalamount() {
        return fztotalamount;
    }

    /**
     * @param fztotalamount the fztotalamount to set
     */
    public void setFztotalamount(double fztotalamount) {
        this.fztotalamount = fztotalamount;
    }

}
