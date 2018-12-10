import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Classname Producer
 * @Description TODO
 * @Date 2018/12/6 11:02
 * @Created by taojiangbing
 */
public class Producer {

    public final static String QUEUE_NAME="rabbitMQ.queuetest";
    public final static String EXCHANGE_NAME="rabbitMQ.exchange.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //  声明一个队列
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,QUEUE_NAME);
        String message = "Hello RabbitMQ";
        for(int i =0;i<100;i++){
            message = message +i;
            //发送消息到队列中
            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("Producer Send +'" + message + "'");
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
