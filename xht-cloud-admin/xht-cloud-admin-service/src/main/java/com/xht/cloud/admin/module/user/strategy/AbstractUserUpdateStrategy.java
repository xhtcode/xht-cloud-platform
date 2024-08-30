package com.xht.cloud.admin.module.user.strategy;

import com.xht.cloud.admin.module.user.domain.PassWordBO;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.admin.module.user.factory.UserUpdateFactory;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.upload.UploadFileBuilder;
import com.xht.cloud.framework.file.upload.helper.MultipartFileHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;


/**
 * 描述 ： 策略模式 用户修改
 *
 * @author : 小糊涂
 **/
public abstract class AbstractUserUpdateStrategy implements IUserStrategy {

    /**
     * 根据用户id修改信息
     *
     * @param userId      用户id
     * @param requestUser 用户请求信息
     * @return true 修改成功
     */
    public abstract Boolean updateUserInfo(String userId, UserUpdateRequest requestUser);

    /**
     * 根据用户id修改头像
     *
     * @param userId        用户id
     * @param multipartFile 头像信息
     * @return true 修改成功
     */
    public final Boolean updateUserAvatar(String userId, MultipartFile multipartFile) {
        UploadFileBO uploadFileBO = MultipartFileHelper.init(multipartFile).format(checkFile());
        return updateUserAvatar(userId, uploadFileBO);
    }

    /**
     * @return 文件校验
     */
    protected Consumer<UploadFileBuilder> checkFile() {
        return (builder) -> {
        };
    }

    /**
     * 根据用户id修改头像
     *
     * @param userId       用户id
     * @param uploadFileBO 头像上传信息
     * @return true 修改成功
     */
    protected abstract Boolean updateUserAvatar(String userId, UploadFileBO uploadFileBO);

    /**
     * 根据用户id修改密码
     *
     * @param userId          用户id
     * @param passWordRequest 密码修改请求信息
     * @return true 修改成功
     */
    public abstract Boolean updatePassword(String userId, PassWordBO passWordRequest);

    /**
     * 初始化 策略工厂
     */
    @Override
    public final void afterPropertiesSet() {
        UserUpdateFactory.register(getUserType(), this);
    }

}
