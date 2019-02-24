package com.smile.start.dao;

import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.model.contract.SignListTemplate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:31, SignListTemplateDao.java
 * @since 1.8
 */
@Mapper
public interface SignListTemplateDao {
    /**
     * 新增签署清单
     *
     * @param signListTemplate
     * @return
     */
    @Insert("insert into sign_list_template (serial_no,sign_list_name,sort,project_mode) values (#{serialNo},#{signListName},#{sort},#{projectMode})")
    long insert(SignListTemplate signListTemplate);

    /**
     * 更新签署清单
     * @param signListTemplate
     * @return
     */
    @Update("update sign_list_template set sign_list_name=#{signListName},sort=#{sort},project_mode=#{projectMode} where id=#{id}")
    int update(SignListTemplate signListTemplate);

    /**
     * 根据ID获取签署清单
     * @param id
     * @return
     */
    @Select("select * from sign_list_template where id = #{id}")
    SignListTemplate get(Long id);

    /**
     * 根据编号查询签署清单
     * @param serialNo
     * @return
     */
    @Select("select * from sign_list_template where serial_no=#{serialNo}")
    SignListTemplate findBySerialNo(String serialNo);

    /**
     * 查询全部签署清单
     *
     * @return
     */
    @Select("select * from sign_list_template where 1 = 1")
    List<SignListTemplate> findAll();

    /**
     * 按项目模式查询清单列表
     * @param projectMode
     * @return
     */
    @Select("select * from sign_list_template where project_mode = #{projectMode}")
    List<SignListTemplate> findByProjectMode(Integer projectMode);

    /**
     * 分页查询
     * @param signListTemplateSearchDTO
     * @return
     */
    @Select("<script>" + "select * from sign_list_template where 1=1 "
            + "<if test = 'signListName!=null'> and sign_list_name like CONCAT('%',#{signListName},'%')</if>"
            + "<if test = 'projectMode!=null'> and project_mode = #{projectMode}</if>"
            + "</script>")
    List<SignListTemplate> findByParam(SignListTemplateSearchDTO signListTemplateSearchDTO);

    /**
     * 删除签署清单
     * @param id
     * @return
     */
    @Delete("delete from sign_list_template where id = #{id}")
    void delete(Long id);
}
