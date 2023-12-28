package javaproject.dapr.pub;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PubApplication {

//	private static final HttpClient httpClient = HttpClient.newBuilder()
//			.version(HttpClient.Version.HTTP_2)
//			.connectTimeout(Duration.ofSeconds(10))
//			.build();
//
//	private static final String PUBSUB_NAME = "kafka-commonpubsub";
//	private static final String TOPIC = "orders";
//	private static String DAPR_HOST = System.getenv().getOrDefault("http://pub-api-dapr", "http://localhost");
//	private static String DAPR_HTTP_PORT = System.getenv().getOrDefault("3500", "3500");
//
//	public static void main(String[] args) throws InterruptedException, IOException {
//		String uri = DAPR_HOST + ":" + DAPR_HTTP_PORT + "/v1.0/publish/" + PUBSUB_NAME + "/" + TOPIC;
//		Thread.sleep(20000);
//		for (int i = 1; i <= 10; i++) {
//			int orderId = i;
//			JSONObject obj = new JSONObject();
//			obj.put("orderId", orderId);
//
//			// Publish an event/message using Dapr PubSub via HTTP Post
//			HttpRequest request = HttpRequest.newBuilder()
//					.POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
//					.uri(URI.create(uri))
//					.header("Content-Type", "application/json")
//					.build();
//
//			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//			System.out.println("Published data:" + orderId);
//			TimeUnit.MILLISECONDS.sleep(1000);
//		}
//	}

	public static void main(String[] args) throws InterruptedException{
		Thread.sleep(20000);
		String TOPIC_NAME = "orders";
		String PUBSUB_NAME = "kafka-commonpubsub";
		DaprClient client = new DaprClientBuilder().build();

		for (int i = 1; i <= 10; i++) {
			int orderId = i;
			Order order = new Order(orderId);

			// Publish an event/message using Dapr PubSub
			client.publishEvent(
					PUBSUB_NAME,
					TOPIC_NAME,
					order).block();
			System.out.println("Published data: " + order.getOrderId());
			TimeUnit.MILLISECONDS.sleep(1000);
		}
	}

}

@AllArgsConstructor
@Getter
class Order {
	private int orderId;
}
