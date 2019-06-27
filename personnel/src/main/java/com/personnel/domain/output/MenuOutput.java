package com.personnel.domain.output;



import com.personnel.model.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuOutput extends Menu {
    private List<MenuOutput> children;

    private Integer checkState = 0;

    private Integer type = 0;

}
