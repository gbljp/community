package com.anjoy.frozen.seller.rocketmq;


import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionCheckListenerImpl implements TransactionCheckListener {
//    private AtomicInteger transactionIndex = new AtomicInteger(0);

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        //wuhy 由于阿里的开源版本删掉了服务器回查的功能，这边的代码其实是没用的

//        System.out.printf("server checking TrMsg %s%n", msg);
//
//        int value = transactionIndex.getAndIncrement();
//        if ((value % 6) == 0) {
//            throw new RuntimeException("Could not find db");
//        } else if ((value % 5) == 0) {
//            return LocalTransactionState.ROLLBACK_MESSAGE;
//        } else if ((value % 4) == 0) {
//            return LocalTransactionState.COMMIT_MESSAGE;
//        }

//        return LocalTransactionState.UNKNOW;
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
