package com.nancal.web.uc.service;

import com.nancal.model.uc.UserTestModel;
import com.nancal.web.base.MvcResult;
import com.nancal.web.base.MvcTableReq;
import com.nancal.web.base.MvcTableResp;
import com.nancal.web.uc.service.queryform.UserTestQueryForm;

public interface UserTestService {
    MvcTableResp<UserTestModel> page(MvcTableReq<UserTestQueryForm> req);

    MvcResult<UserTestModel> create(UserTestModel userTestModel);

    MvcResult<Boolean> delete(Long id);

    MvcResult<Boolean> update(UserTestModel userTestModel);

    MvcResult<UserTestModel> detail(Long id);

}
