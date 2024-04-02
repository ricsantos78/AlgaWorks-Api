package com.algafoods.domain.service.impl;

import com.algafoods.domain.model.GroupModel;
import com.algafoods.domain.service.GroupService;
import com.algafoods.infra.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public List<GroupModel> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<GroupModel> findByCdGroup(Long cdGroup) {
        return groupRepository.findByCdGroup(cdGroup);
    }

    @Override
    public GroupModel save(GroupModel groupModel) {
        if(groupModel.getCdGroup() == null) {
            groupModel.setCdGroup(findNextCdGroup());
        }
        return groupRepository.save(groupModel);
    }

    @Override
    public void delete(GroupModel groupModel) {
        groupRepository.delete(groupModel);
    }

    public Long findNextCdGroup(){
        var maxCdGroup = groupRepository.findMaxCdGroup();
        return maxCdGroup != null ? maxCdGroup + 1 : 1;
    }


}
