package com.algafoods.domain.service;

import com.algafoods.domain.model.GroupModel;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupModel> findAll();
    Optional<GroupModel> findByCdGroup(Long cdGroup);
    GroupModel save(GroupModel groupModel);
    void delete(GroupModel groupModel);
}
