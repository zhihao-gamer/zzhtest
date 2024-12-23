package xcmg.global.cockpit.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xcmg.global.cockpit.backend.mapper.UserMapper;
import xcmg.global.cockpit.backend.service.UserService;
import xcmg.global.cockpit.backend.vo.param.UserParamVO;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String insertBaseInfo(UserParamVO name) {
        userMapper.insertBaseInfo(name);
//        List<UserParamVO> baseInfo = userMapper.getBaseInfo();
        return "success";
    }
}
