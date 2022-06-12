package com.hangaries.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleModuleResponse implements Serializable {

    Role role;
    List<ModuleMaster> modules;

}
