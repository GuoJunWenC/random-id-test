package org.example.api.els.sync;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MysqlData {
    @Select(" select count(*) from sys_sequence ")
    Integer queryCount();
}
