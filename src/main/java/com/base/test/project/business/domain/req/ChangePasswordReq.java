package com.base.test.project.business.domain.req;

import com.base.test.common.constant.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author lzs
 * @Date 2023/9/13 9:16
 **/
@Data
public class ChangePasswordReq {
    @Pattern(regexp = Constants.REGEX_PASSWORD, message = Constants.MESSAGE_PASSWORD)
    @NotBlank
    private String oldPassword;
    @Pattern(regexp = Constants.REGEX_PASSWORD, message = Constants.MESSAGE_PASSWORD)
    @NotBlank
    private String password;
}
