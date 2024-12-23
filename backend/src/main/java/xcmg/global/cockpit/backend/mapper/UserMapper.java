package xcmg.global.cockpit.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xcmg.global.cockpit.backend.vo.param.UserParamVO;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertBaseInfo(@Param("param") UserParamVO name);

    List<UserParamVO> getBaseInfo();
}
