package com.personnel.service;

import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.ActionOutput;
import com.personnel.mapper.jpa.ActionRepository;
import com.personnel.mapper.mybatis.ActionMapper;
import com.personnel.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service

public class ActionService extends BaseService<ActionOutput, Action,Integer> {

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private ActionRepository repository;

    @Override
    public BaseMapper<Action, Integer> getMapper() {
        return repository;
    }
    @Override
    public MybatisBaseMapper<ActionOutput> getMybatisBaseMapper() {
        return actionMapper;
    }
    public List<ActionOutput> getByIdIn(List<Integer> ids){
        return actionMapper.findByRoles(ids);
    }

    public Map<String, List<String>> getActionOutput(List<ActionOutput> actions) {
        Map<String, List<String>> map = new HashMap<>();
        if (actions.size() <= 0) {
            return null;
        }
        List<Action> action = actions.stream().filter(Action -> Action.getCode().indexOf("A_PERSONNEL") >= 0).collect(toList());
        List<String> idList = action.stream().map(Action::getCode).collect(toList());
        for (var str : idList) {
            map.put(str, new ArrayList<>());
        }

        return map;
    }


    public List<ActionOutput> findByRoleId(Integer roleId){
        return actionMapper.findByRoleId(roleId);
    }


    public List<ActionOutput> selectAllTow(){
        return actionMapper.selectAllTow();
    }
}
