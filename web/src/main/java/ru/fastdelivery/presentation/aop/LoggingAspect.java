package ru.fastdelivery.presentation.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* ru.fastdelivery.usecase.DistanceAccountingUseCase.calc(ru.fastdelivery.domain.delivery.shipment.Shipment))")
    public void shipmentInfoPointcut(){}

    @AfterReturning("args(shipment) && shipmentInfoPointcut()")
    public void shipmentInfoAdvice(Shipment shipment) {
        log.info("""
                
                Shipment details:
                Weight: {} kilograms;
                Volume: {} cubic meters;
                Distance: {} kilometers
                """,
                shipment.weightAllPackages().kilograms(),
                shipment.volumeAllPackages().cubicMeters(),
                shipment.calculateDistance().kilometers());
    }
}
