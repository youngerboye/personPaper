package com.personnel.service;

import com.personnel.core.base.BaseMapper;
import com.personnel.core.base.BaseService;
import com.personnel.core.base.MybatisBaseMapper;
import com.personnel.domain.output.MenuOutput;
import com.personnel.domain.output.MenuTree;
import com.personnel.mapper.jpa.MenuRepository;
import com.personnel.mapper.mybatis.MenuMapper;
import com.personnel.model.Menu;
import com.personnel.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MenuService extends BaseService<MenuOutput, Menu,Integer> {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public BaseMapper<Menu, Integer> getMapper() {
        return repository;
    }

    @Override
    public MybatisBaseMapper<MenuOutput> getMybatisBaseMapper() {
        return menuMapper;
    }



    public List<MenuOutput> getAllList(){
        var menus = menuMapper.findAll();
        return getMenuOutput(menus,0);
    }

    public List<MenuOutput> findAll(){
        return menuMapper.findAll();
    }

    public List<MenuOutput> getMenuOutput(List<MenuOutput> menus,Integer parentId) {
        List<MenuOutput> parentMenus = new ArrayList<>();
        List<MenuOutput> firstMenu = menus.stream()
                .filter(Menu -> Menu.getParentId().equals(parentId))
                .sorted(Comparator.comparingInt(Menu::getDisPlayOrderBy)).collect(toList());
        parentMenus.addAll(firstMenu);
        for (MenuOutput parentMenu : parentMenus) {
            if (parentMenu.getHasChild().equals(1)) {
                List<MenuOutput> list = menus.stream().filter(Menu -> Menu.getParentId() .equals(parentMenu.getId())).collect(toList());
                parentMenu.setChildren(list);
                getMenuOutput(menus,parentMenu.getId());
            }
        }
        return parentMenus;
    }



    //所有的组织数据（用户树形下拉框类ztree）
    public List<MenuTree> toZtree(List<MenuOutput> all) {
        List<MenuTree> parentArray = new ArrayList<>();
        for (var o:all) {
            if(o.getParentId()==0){
                parentArray.add(new MenuTree(o));
            }
        }
        return getTree(all,parentArray);
    }
    public List<MenuTree> getTree(List<MenuOutput> list, List<MenuTree> parents) {
        for (MenuTree parentOrg:parents) {
            List<MenuTree> childs = new ArrayList<>();
            for (MenuOutput o:list) {
                if(parentOrg.getKey().equals(o.getParentId())){
                    childs.add(new MenuTree(o));
                }
            }
            parentOrg.setChildren(childs.size()==0?null:childs);
            if(childs.size()>0){
                getTree(list,childs)  ;
            }
        }
        return parents;
    }


    public List<MenuOutput> getMenuInit(){
        List<MenuOutput> menus = new ArrayList<>();
        if(getUsers().getAdministratorLevel() != 9){
            var ids = getUsers().getRoles().stream().map(UserRole::getRoleId).collect(toList());
            if(ids == null || ids.size() <= 0){
                return null;
            }else {
                menus = menuMapper.findByRoles(ids);
            }
        }else {
            menus = menuMapper.selectAll(null);
        }
        var sys = menus.stream()
                .filter(MenuOutput -> MenuOutput.getParentId().equals(0))
                .sorted(Comparator.comparingInt(Menu::getParentId)).collect(toList());
        if(menus == null || menus.size() <= 0){
            return null;
        }
        if(sys.size() > 1){
            return null;
        }
        return getMenuOutput(menus,sys.get(0).getId())
                .stream()
                .sorted(Comparator.comparingInt(MenuOutput::getDisPlayOrderBy)).collect(toList());
    }


    public List<MenuOutput> getByParentId(Integer parentId){
        return menuMapper.findByParentId(parentId);
    }

    public List<MenuOutput> getByIdIn(List<Integer> ids){
        return menuMapper.findByRoles(ids);
    }

    public List<MenuOutput> selectAllToW(){
        return menuMapper.selectAllToW();
    }
}
