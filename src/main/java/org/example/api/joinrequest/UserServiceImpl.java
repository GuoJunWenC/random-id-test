package org.example.api.joinrequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public Map<String, Users> queryUserByIdBatch(List<UserWrapBatchService.Request> userReqs) {
        // 全部参数
        List<Long> userIds = userReqs.stream().map(UserWrapBatchService.Request::getUserId).collect(Collectors.toList());
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        // 用in语句合并成一条SQL，避免多次请求数据库的IO
        queryWrapper.in("id", userIds);
        List<Users> users = usersMapper.selectList(queryWrapper);
        Map<Long, List<Users>> userGroup = users.stream().collect(Collectors.groupingBy(Users::getId));
        HashMap<String, Users> result = new HashMap<>();
        userReqs.forEach(val -> {
            List<Users> usersList = userGroup.get(val.getUserId());
            if (!CollectionUtils.isEmpty(usersList)) {
                result.put(val.getRequestId(), usersList.get(0));
            } else {
                // 表示没数据
                result.put(val.getRequestId(), null);
            }
        });
        return result;
    }
}
