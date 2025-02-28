package ci.smile.simswaporange.dao.config.configBatch;

import ci.smile.simswaporange.business.ActionEnMasseBusiness;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.dto.customize.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisNumbersBatchItemReader implements ItemReader<LockUnLockFreezeDtos> {
    private final RedisTemplate<String, LockUnLockFreezeDtos> redisTemplate;
    private final ActionEnMasseBusiness actionEnMasseBusiness;
    private Set<String> keys;
    private int index = 0;
    @Override
    public LockUnLockFreezeDtos read() throws Exception {
        log.info("essaye de lire les données dans redis");
        List<Result> resultList = new ArrayList<>();
        if (keys == null) {
            keys = redisTemplate.keys("numbersBatchLockNumbers_*");
            if(keys.size()>0){
                extracted(resultList);
                if (Utilities.isNotEmpty(resultList)){
                    log.info("va ecrire dans le fichier resultat");
                    actionEnMasseBusiness.lockUnlockNumberAfterTwentyFourHours(resultList, true,"LOCK");
                }
            }
        }
        if (index < keys.size()) {
            log.info("taille de numbersBatch_ dans redis {}", keys.size());
                String key = (String) keys.toArray()[index];
                LockUnLockFreezeDtos value = redisTemplate.opsForValue().get(key);
                index ++;
                return value;
        }
        return null;
    }

    private void extracted(List <Result> resultList){
        log.info("taille de numbersBatchLockNumbers_ dans redis {}", keys.size());
        do {
            String key = (String) keys.toArray()[index];
            LockUnLockFreezeDtos value = redisTemplate.opsForValue().get(key);
            log.info("code numbersBatchLockNumbers_ {}", value);
            log.info("code value {}", value.getCode());

            if (value != null && value.getStatus() != null && value.getStatus().equalsIgnoreCase("200")){
                value.getData().stream().forEach(args ->{
                    Result result = new Result();
                    result.setReason(args.getState());
                    result.setNumero(args.getMsisdn());
//                    result.setOfferName(args.getOfferName() != null ? args.getOfferName() : "0");
//                    result.setSerialNumber(args.getSerialNumber() != null ? args.getSerialNumber() : "0");
//                    result.setContractId(args.getContractId() > 0 ? String.valueOf(args.getContractId()) : "0");
//                    result.setTariffModelCode(args.getTariffModelCode() > 0 ? String.valueOf(args.getTariffModelCode()) : "0");
//                    result.setPortNumber(args.getPortNumber() > 0 ? String.valueOf(args.getPortNumber()) : "0");
//                    result.setLockStatus(args.getLockStatus());
//                    result.setStatus(args.getStatus());
//                    result.setActivationDate(args.getActivationDate());
//                    result.setFrozen(args.isFrozen());
                    result.setLogin("EST UN TRAITEMENT AUTOMATIQUE");
                    resultList.add(result);
                });
            } else if (value != null && value.getCode() != null && value.getCode().equalsIgnoreCase("500")) {
                value.getNumbers().stream().forEach(failesNumbers->{
                    Result result = new Result();
                    result.setReason(value.getMessage());
                    result.setNumero(failesNumbers);
                    result.setLogin("EST UN TRAITEMENT AUTOMATIQUE");
                    resultList.add(result);
                });
            }
            index ++;
        }while(index < keys.size());

        index = 0;
    }
}
