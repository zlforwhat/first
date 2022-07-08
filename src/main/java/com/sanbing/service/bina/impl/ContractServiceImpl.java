package com.sanbing.service.bina.impl;

import com.binance.client.SubscriptionClient;
import com.sanbing.common.constant.Constant;
import com.sanbing.service.bina.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service("BINAContractService")
public class ContractServiceImpl implements ContractService {

    @PostConstruct
    @Override
    public void saveContractPrice() {
        SubscriptionClient client = SubscriptionClient.create();
        client.subscribeAllBookTickerEvent((event) -> {
            if (event.getSymbol().endsWith("USDT"))
                Constant.BINA_CONTRACT_PRICE_MAP.put(event.getSymbol(), event.getBestBidPrice().toString());
//            log.info("event:{}", event.toString());
        }, null);
    }
}
