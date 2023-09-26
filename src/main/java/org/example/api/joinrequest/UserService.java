package org.example.api.joinrequest;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Users> queryUserByIdBatch(List<UserWrapBatchService.Request> userReqs);

}
