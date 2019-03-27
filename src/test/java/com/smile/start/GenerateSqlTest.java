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
        String tableName = "contract_extend_info";
        String str = " `serial_no` varchar(64) NOT NULL COMMENT '业务流水',\n" +
                "  `contract_serial_no` varchar(64) NOT NULL COMMENT '合同流水',\n" +
                "  `contract_code` varchar(64) DEFAULT NULL COMMENT '合同编号',\n" +
                "  `sp_company_name` varchar(256) DEFAULT NULL COMMENT '乙方公司名称',\n" +
                "  `sp_residence` varchar(512) DEFAULT NULL COMMENT '乙方住所',\n" +
                "  `sp_legal_person` varchar(64) DEFAULT NULL COMMENT '乙方法定代表人',\n" +
                "  `sp_contact_address` varchar(512) DEFAULT NULL COMMENT '乙方联系地址',\n" +
                "  `sp_post_code` varchar(16) DEFAULT NULL COMMENT '乙方邮编',\n" +
                "  `sp_telephone` varchar(32) DEFAULT NULL COMMENT '乙方电话',\n" +
                "  `sp_fax` varchar(32) DEFAULT NULL COMMENT '乙方传真',\n" +
                "  `obligor` varchar(512) DEFAULT NULL COMMENT '债务人',\n" +
                "  `sign_date` datetime DEFAULT NULL COMMENT '签署日期',\n" +
                "  `contract_name` varchar(256) DEFAULT NULL COMMENT '合同名称',\n" +
                "  `receivable_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款',\n" +
                "  `receivable_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款大写',\n" +
                "  `receivable_money_type` varchar(16) DEFAULT NULL COMMENT '应收账款类别：不低于、为',\n" +
                "  `obligor_enjoy_money` decimal(10,2) DEFAULT NULL COMMENT '债务人享有金额',\n" +
                "  `obligor_enjoy_money_upper` varchar(256) DEFAULT NULL COMMENT '债务人享有金额大写',\n" +
                "  `receivable_assignee_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款受让款',\n" +
                "  `receivable_assignee_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款受让款大写',\n" +
                "  `receivable_assignee_first_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款受让款首付款',\n" +
                "  `receivable_assignee_first_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款受让款首付款大写',\n" +
                "  `receivable_recovery_money` decimal(10,2) DEFAULT NULL COMMENT '应收账款回收款',\n" +
                "  `receivable_recovery_money_upper` varchar(256) DEFAULT NULL COMMENT '应收账款回收款大写',\n" +
                "  `receivable_recovery_money_type` varchar(16) DEFAULT NULL COMMENT '应收账款回收款类别：不低于、为',\n" +
                "  `receivable_recovery_money_paytime` datetime DEFAULT NULL COMMENT '应收账款支付日期',\n" +
                "  `fp_account_name` varchar(256) DEFAULT NULL COMMENT '甲方户名',\n" +
                "  `fp_bank_name` varchar(256) DEFAULT NULL COMMENT '甲方银行名称',\n" +
                "  `fp_account` varchar(256) DEFAULT NULL COMMENT '甲方银行账户',\n" +
                "  `sp_account_name` varchar(256) DEFAULT NULL COMMENT '乙方户名',\n" +
                "  `sp_bank_name` varchar(256) DEFAULT NULL COMMENT '乙方户名',\n" +
                "  `sp_account` varchar(256) DEFAULT NULL COMMENT '乙方银行账户',\n" +
                "  `compulsory_rescission_date` datetime DEFAULT NULL COMMENT '合同强制解除日期',\n" +
                "  `fp_signature_date` datetime DEFAULT NULL COMMENT '甲方签字日期',\n" +
                "  `sp_signature_date` datetime DEFAULT NULL COMMENT '乙方签字日期',\n" +
                "  `billing_start_date` datetime DEFAULT NULL COMMENT '计算起始日期',\n" +
                "  `interest_rate` decimal(10,2) DEFAULT NULL COMMENT '年利率',";

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
