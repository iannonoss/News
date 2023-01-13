package com.democom.news.config.jobConfig;

import com.democom.news.entity.Subscription;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ItemProcessorConfig implements ItemProcessor<Subscription, Subscription> {
    @Override
    public Subscription process(Subscription subscription) throws Exception {
        System.out.println("MyBatchProcessor : Processing data : "+subscription);
        Subscription newSub = new Subscription();
        newSub.setId(subscription.getId());
        newSub.setEnd_date(subscription.getEnd_date());
        newSub.setStateSubscription(subscription.getStateSubscription());
        return newSub;
    }
}
