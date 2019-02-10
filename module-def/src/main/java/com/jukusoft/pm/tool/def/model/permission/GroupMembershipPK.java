package com.jukusoft.pm.tool.def.model.permission;

import com.jukusoft.pm.tool.def.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GroupMembershipPK implements Serializable {

    protected User user;

    protected Group group;

}
