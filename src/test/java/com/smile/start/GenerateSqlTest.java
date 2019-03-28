package com.smile.start;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

public class GenerateSqlTest {

    @Test
    public void testGenerateSql() {
        String tableName = "contract_receivable_confirmation";
        String str = " `serial_no` varchar(64) NOT NULL COMMENT '业务流水',\n" +
                "  `contract_serial_no` varchar(64) NOT NULL COMMENT '合同流水',\n" +
                "  `confirmation_code` varchar(64) DEFAULT NULL COMMENT '确认函编号',\n" +
                "  `assignor` varchar(256) DEFAULT NULL COMMENT '让与人',\n" +
                "  `sign_date` datetime DEFAULT NULL COMMENT '签署日期',\n" +
                "  `obligor` varchar(256) DEFAULT NULL COMMENT '债务人',\n" +
                "  `business_contract_name` varchar(256) DEFAULT NULL COMMENT '商务合同名称',\n" +
                "  `unpaid_receivable_assignee_money` decimal(10,2) DEFAULT NULL COMMENT '未支付应收账款受让款',\n" +
                "  `unpaid_receivable_assignee_money_upper` varchar(256) DEFAULT NULL COMMENT '未支付应收账款受让款大写',\n" +
                "  `unpaid_receivable_assignee_money_type` varchar(16) DEFAULT NULL COMMENT '未支付应收账款受让款类别：不低于、为',\n" +
                "  `receivable_assignee_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款受让款',\n" +
                "  `receivable_assignee_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款受让款大写',\n" +
                "  `receivable_assignee_money_type` varchar(16) DEFAULT NULL COMMENT '应收账款受让款类别：不低于、为',\n" +
                "  `receivable_recovery_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款回收款',\n" +
                "  `receivable_recovery_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款回收款大写',\n" +
                "  `receivable_expiry_date` datetime DEFAULT NULL COMMENT '应收账款回收款截止日期',\n" +
                "  `contract_receivable` decimal(10,2) DEFAULT NULL COMMENT '合同应收账款',\n" +
                "  `contract_receivable_upper` varchar(256) DEFAULT NULL COMMENT '合同应收账款大写',\n" +
                "  `assignor_abligor_receivable` decimal(10,2) DEFAULT NULL COMMENT '让与人对债务人的应收账款',\n" +
                "  `assignor_abligor_receivable_upper` varchar(256) DEFAULT NULL COMMENT '让与人对债务人的应收账款大写',\n" +
                "  `unpaid_assignor_abligor_receivable` decimal(10,2) DEFAULT NULL COMMENT '未支付让与人对债务人的应收账款',\n" +
                "  `unpaid_assignor_abligor_receivable_upper` varchar(256) DEFAULT NULL COMMENT '未支付让与人对债务人的应收账款大写'," +
                "\n" +
                "  `receivable_assignee_money_paid` decimal(10,2) DEFAULT NULL COMMENT '受让人已向让与人支付的应收账款受让款',\n" +
                "  `receivable_assignee_money_paid_upper` varchar(256) DEFAULT NULL COMMENT '受让人已向让与人支付的应收账款受让款大写',\n" +
                "  `assignor_commit_date` datetime DEFAULT NULL COMMENT '让与人提交资料日期',\n" +
                "  `assignee_account_name` varchar(256) DEFAULT NULL COMMENT '受让人户名',\n" +
                "  `assignee_bank_name` varchar(256) DEFAULT NULL COMMENT '受让人开户银行',\n" +
                "  `assignee_account` varchar(256) DEFAULT NULL COMMENT '受让人账户',\n" +
                "  `confirmation_address` varchar(256) DEFAULT NULL COMMENT '确认函签订地',\n" +
                "  `assignee_signature_date` datetime DEFAULT NULL COMMENT '受让人签字日期',\n" +
                "  `assignor_company_name` varchar(256) DEFAULT NULL COMMENT '让与人公司名称',\n" +
                "  `assignor_signature_date` datetime DEFAULT NULL COMMENT '让与人签字日期',\n" +
                "  `obligor_company_name` varchar(256) DEFAULT NULL COMMENT '债务人公司名称',\n" +
                "  `obligor_signature_date` datetime DEFAULT NULL COMMENT '债务人签字日期',\n" +
                "  `name_of_subject` varchar(512) DEFAULT NULL COMMENT '商务合同标的物名称',\n" +
                "  `invoice_money` decimal(10,2) DEFAULT NULL COMMENT '发票/收据所载金额（元）',\n" +
                "  `invoice_money_type` varchar(16) DEFAULT NULL COMMENT '发票/收据所载金额类别：不低于、为',";

        List<String> fields = Lists.newArrayList(Splitter.on("\n").split(str));
        List<String> fieldList = Lists.newArrayList();
        List<String> camelFieldList = Lists.newArrayList();
        fields.forEach(e -> {
            String substring = e.substring(e.indexOf("`") + 1, e.lastIndexOf("`"));
            fieldList.add(substring);
            camelFieldList.add(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, substring));
        });
        StringBuilder insertSql = new StringBuilder("insert into ");
        insertSql.append(tableName).append(" (");
        insertSql.append(Joiner.on(",").join(fieldList));
        insertSql.append(") values (#{");
        insertSql.append(Joiner.on("},#{").join(camelFieldList));
        insertSql.append("})");
        System.out.println(insertSql);

        StringBuilder updateSql = new StringBuilder("update ");
        updateSql.append(tableName).append(" set ");
        List<String> tempList = Lists.newArrayList();
        for(int i = 0; i < fieldList.size(); i++) {
            tempList.add(fieldList.get(i) + "=#{" + camelFieldList.get(i) + "}");
        }
        updateSql.append(Joiner.on(",").join(tempList));
        updateSql.append(" where id=#{id}");
        System.out.println(updateSql);
    }
}
