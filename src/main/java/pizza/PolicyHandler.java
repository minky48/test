package pizza;

import pizza.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    // checkpoint1. SAGA pub/sub LDH 202011041103
    // [소스추가] Autowired
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_RequestDelivery(@Payload Paid paid){

        if(paid.isMe()){

            // checkpoint1. SAGA pub/sub LDH 202011041103
            // [소스추가] payment 정보 delivery에 등록
            Delivery delivery = new Delivery();
            delivery.setOrderId(paid.getOrderId());
            delivery.setDeliveryStatus("Delivered");
            deliveryRepository.save(delivery);

            System.out.println("##### listener RequestDelivery : " + paid.toJson());
        }
    }

}
