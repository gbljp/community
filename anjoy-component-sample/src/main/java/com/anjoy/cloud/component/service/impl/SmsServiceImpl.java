package com.anjoy.cloud.component.service.impl;

import com.anjoy.cloud.component.entity.Sms;
import com.anjoy.cloud.component.dao.SmsDao;
import com.anjoy.cloud.component.service.SmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 手机短信验证码 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-02-14
 */
@Service
public class SmsServiceImpl extends ServiceImpl<SmsDao, Sms> implements SmsService {

}
