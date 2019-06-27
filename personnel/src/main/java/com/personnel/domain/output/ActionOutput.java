package com.personnel.domain.output;


import com.personnel.model.Action;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionOutput extends Action {

    private Integer checkState = 0;
}
