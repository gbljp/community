package com.anjoy.cloud.component.component;

import com.anjoy.cloud.component.rocketmq.RocketmqEvent;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EasLogListener {


    /*
     *
     * 校验短信的mq监听
     * */
    @EventListener(condition = "#event.msgs[0].topic=='BusinessLog_EAM' && #event.msgs[0].tags=='EAS'")
    public void rocketmqMsgListen_easLog(RocketmqEvent event) {
        try {
            System.out.println("*** EasLogListener, rocketmqMsgListen_easLog监听到一个消息达到：" + event.getMsgs().get(0).getMsgId());
            System.out.println("*** body：" + new String(event.getMsgs().get(0).getBody(), RemotingHelper.DEFAULT_CHARSET));
            System.out.println("*** EasLogListener, rocketmqMsgListen_easLog处理成功：" + event.getMsgs().get(0).getMsgId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
