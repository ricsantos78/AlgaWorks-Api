package com.algafoods.api.controller;

import com.algafoods.api.assemblers.GroupModelAssembler;
import com.algafoods.api.assemblers.GroupModelDisassembler;
import com.algafoods.api.dto.GroupDto;
import com.algafoods.api.dto.input.GroupInputDto;
import com.algafoods.domain.exception.GroupNotFoundException;
import com.algafoods.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private final GroupModelAssembler groupModelAssembler;

    private final GroupModelDisassembler groupModelDisassembler;

    @GetMapping
    public List<GroupDto> listAll(){
        var groupModel = groupService.findAll();
        return groupModel.stream().map(groupModelAssembler::groupToModel).toList();
    }

    @GetMapping("/{cdGroup}")
    public GroupDto findById(@PathVariable Long cdGroup){
        return groupModelAssembler.groupToModel
                (groupService.findByCdGroup(cdGroup).orElseThrow(GroupNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto save(@RequestBody @Valid GroupInputDto groupInputDto){

        return groupModelAssembler.groupToModel
                (groupService.save(groupModelDisassembler.groupToDisassemblerModel(groupInputDto)));

    }

    @PutMapping("/{cdGroup}")
    public GroupDto update(@PathVariable Long cdGroup,
                           @RequestBody @Valid GroupInputDto groupInputDto){
        var groupModel = groupService.findByCdGroup(cdGroup).orElseThrow(GroupNotFoundException::new);

        groupModelDisassembler.groupToConverter(groupModel, groupInputDto);
        return groupModelAssembler.groupToModel(groupService.save(groupModel));
    }

    @DeleteMapping("/{cdGroup}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cdGroup){
        groupService.delete(groupService.findByCdGroup(cdGroup).orElseThrow(GroupNotFoundException::new));
    }
}
