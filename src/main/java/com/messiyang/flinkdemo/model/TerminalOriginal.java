package com.messiyang.flinkdemo.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cuiyang
 * @desc
 */
public class TerminalOriginal implements Serializable {

    private static final long serialVersionUID = 3140741133333904344L;
    private BigDecimal terminal_id;//终端唯一标识
    private String org_no;//机构编号



    public TerminalOriginal(BigDecimal terminal_id, String org_no) {
        this.terminal_id = terminal_id;
        this.org_no = org_no;

    }


    public BigDecimal getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(BigDecimal terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }


}
