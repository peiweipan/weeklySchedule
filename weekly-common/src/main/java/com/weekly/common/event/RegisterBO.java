package com.weekly.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBO {

    private Long userId;

//    private String uuid;

    private String openId;

    private String username;

    private String nickname;

    private String password;

    private Long[] roles;
}
