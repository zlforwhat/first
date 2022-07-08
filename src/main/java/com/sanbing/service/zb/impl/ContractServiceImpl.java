package com.sanbing.service.zb.impl;

import client.websocket.WsMarketClient;
import com.sanbing.common.constant.Constant;
import com.sanbing.service.zb.ContractService;
import handler.market.AllTickerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service("ZBContractService")
public class ContractServiceImpl implements ContractService {

    @PostConstruct
    @Override
    public void saveContractPrice() {
        WsMarketClient wsMarketClient = new WsMarketClient(Constant.ZB_CONTRACT_BASE_WS);
        wsMarketClient.SubscribeAllTicker(new AllTickerHandler());
    }
}
