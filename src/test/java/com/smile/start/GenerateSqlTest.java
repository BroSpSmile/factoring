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
        String tableName = "contract_fasa";
        String str = "`serial_no` varchar(64) NOT NULL COMMENT '业务流水',\n" +
                "  `contract_serial_no` varchar(64) NOT NULL COMMENT '合同流水',\n" +
                "  `fp_company_name` varchar(256) DEFAULT NULL COMMENT '甲方公司名称',\n" +
                "  `fp_residence` varchar(512) DEFAULT NULL COMMENT '甲方住所',\n" +
                "  `fp_legal_person` varchar(64) DEFAULT NULL COMMENT '甲方法定代表人',\n" +
                "  `fp_post_code` varchar(16) DEFAULT NULL COMMENT '甲方邮编',\n" +
                "  `fp_telephone` varchar(32) DEFAULT NULL COMMENT '甲方电话',\n" +
                "  `fp_fax` varchar(32) DEFAULT NULL COMMENT '甲方传真',\n" +
                "  `sign_address` varchar(512) DEFAULT NULL COMMENT '协议签署地',\n" +
                "  `sing_date` datetime DEFAULT NULL COMMENT '协议签署日期',\n" +
                "  `advisory_service_money` decimal(10,2) DEFAULT NULL COMMENT '财务顾问费',\n" +
                "  `advisory_service_money_upper` varchar(256) DEFAULT NULL COMMENT '财务顾问费大写',\n" +
                "  `advisory_service_money_appointment` varchar(512) DEFAULT NULL COMMENT '财务顾问费约定',\n" +
                "  `sp_bank_name` varchar(256) DEFAULT NULL COMMENT '乙方银行名称',\n" +
                "  `sp_account` varchar(256) DEFAULT NULL COMMENT '乙方银行账户',\n" +
                "  `expiry_date_month` int(11) DEFAULT NULL COMMENT '协议有效期月数',";

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
