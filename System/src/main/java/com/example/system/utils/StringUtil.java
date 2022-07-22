package com.example.system.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class StringUtil {
    public static void main(String[] args) {
        telEncrypt();
        splitString1();
    }

    /**
     * 号码截取加密
     */
    public static void telEncrypt() {
        String ss = "15708447219";
        String s = ss.substring(0, 3) + "****" + ss.substring(7, 11);
        log.info("{}", s);
    }

    /**
     * 分割字符串1
     */
    public static void splitString1() {
        String str = "ITEM=SMFCTX, RETN=000000, DESC=success, IMSI=460110923513931, MSISDN=8619981298868, PDUSESSIONID1=12, SMFDNN1=ims, SMFID1=fb7b2270-c913-11ea-8b66-042151010600, SMFPCSCFRESCBURI1=http://[240e:180:2e2:ff00::31]:8080/nsmf-pdusession/v1/udm-pcscfRestNotify/460110923513931/01/00/00/00/ims/12, SMFPLMNID1=46011, PGWFQDN1=topon.s5s8.smf06-b-ze-dc02.cd.sc.node.epc.mnc011.mcc460.3gppnetwork.org, SMFREGISTERTIME1=20220419112814, SMFSNSSAI1=1-000000, PDUSESSIONID2=13, SMFDNN2=ctnet, SMFID2=ff31512a-c998-11ea-8ce9-041151010500, SMFPCSCFRESCBURI2=, SMFPLMNID2=46011, PGWFQDN2=topon.s5s8.smf05-b-ze-dc01.cd.sc.node.epc.mnc011.mcc460.3gppnetwork.org, SMFREGISTERTIME2=20220419112814, SMFSNSSAI2=1-000000";
        String ss = str.replace(" ", "");
        String[] split = ss.split(",");
        for (String s : split) {
            String[] split1 = s.split("=");
            log.info("{}", split1[0]);
        }
    }
}
