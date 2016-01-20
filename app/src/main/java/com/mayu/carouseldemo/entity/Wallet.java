package com.mayu.carouseldemo.entity;

import java.util.List;

/**
 * 私房钱
 * Created by mayu on 15/8/28,上午10:38.
 */
public class Wallet {
    public String subscribeIncome;//认购缓存
    public String accumulatedIncome;//累计收益
    public String availableBalance;//可用余额
    public List<Deal> subscribeInfo;
    public static class Deal{
        public String goodsName;//商品名称
        public String dividendsNum;//分享码
        public String goodsIco;//商品图
        public String subscribeIncome;//单条商品的认购收入
        public String goodsId;//商品id

        @Override
        public String toString() {
            return "Deal{" +
                    "goodsName='" + goodsName + '\'' +
                    ", dividendsNum='" + dividendsNum + '\'' +
                    ", goodsIco='" + goodsIco + '\'' +
                    ", subscribeIncome='" + subscribeIncome + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "subscribeIncome='" + subscribeIncome + '\'' +
                ", accumulatedIncome='" + accumulatedIncome + '\'' +
                ", availableBalance='" + availableBalance + '\'' +
                ", subscribeInfo=" + subscribeInfo +
                '}';
    }
}
