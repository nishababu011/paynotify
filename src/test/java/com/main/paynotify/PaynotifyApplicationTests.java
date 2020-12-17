package com.main.paynotify;

import com.google.gson.Gson;
import com.main.paynotify.api.PayNotifyMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentTest
@AutoConfigureMockMvc
class PaynotifyApplicationTests {

	private static final String BASE_URL = "http://localhost:8090";

	private static final String API_PATH = "/publish/paymentNotification";

	@Autowired
	private MockMvc mvc;

	private Gson gson = new Gson();

	private KafkaConsumer<String, PayNotifyMessage> testConsumer;

	public void setUp() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.main.paynotify.api");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-notify-subscribe");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		testConsumer = new KafkaConsumer<String, PayNotifyMessage>(props);
	}

	@Test
	void test_SendNotificationMessage() throws Exception {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.clear();
		requestBody.put("paymentId", "payId-test");
		requestBody.put("customerId", "custId-test");
		requestBody.put("subscriptionId", "sub-id-1");
		requestBody.put("description", "Transfered 1000 for shopping");
		requestBody.put("transactionDate" , "12-12-2020");
		requestBody.put("transactionType",  "EFTPOS");

		mvc.perform(MockMvcRequestBuilders.post(API_PATH).contentType(MediaType.APPLICATION_JSON)
		.content(gson.toJson(requestBody))).andExpect(status().is2xxSuccessful());
		setUp();
		verifyConsumerMessage();

	}

	private void verifyConsumerMessage() {
		testConsumer.subscribe(Arrays.asList("topics.payments-incoming"));

		ConsumerRecords<String, PayNotifyMessage> records = null;
		while(records == null) {
			records = testConsumer.poll(10000);
		}
		boolean recordFound = false;
		for (ConsumerRecord record : records) {
			if (record.key() != null && record.key().toString().contains("payId-test")) {
				recordFound = true;
			}

		}
		assertThat(recordFound, is(true));

	}


}
