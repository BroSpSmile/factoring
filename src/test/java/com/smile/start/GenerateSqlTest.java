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
        String tableName = "contract_shareholder_meeting";
        String str = " `serial_no` varchar(64) NOT NULL COMMENT '业务流水',\n" +
                "  `contract_serial_no` varchar(64) NOT NULL COMMENT '合同流水',\n" +
                "  `metting_time` datetime DEFAULT NULL COMMENT '会议时间',\n" +
                "  `metting_address` varchar(512) DEFAULT NULL COMMENT '会议地点',\n" +
                "  `sp_company_name` varchar(256) DEFAULT NULL COMMENT '乙方公司名称',\n" +
                "  `attending_shareholders` varchar(512) DEFAULT NULL COMMENT '出席股东',\n" +
                "  `meeting_number` int(11) DEFAULT NULL COMMENT '会议次数',\n" +
                "  `passing_rate` varchar(64) DEFAULT NULL COMMENT '表决权通过率',\n" +
                "  `signature_date` datetime DEFAULT NULL COMMENT '签字日期',";

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
