package me.zhengjie.infra.websocket;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * 解决启动io.undertow.websockets.jsr UT026010: Buffer pool was not
 * set on WebSocketDeploymentInfo, the default pool will be used的警告
 *
 * @author odboy
 * @date 2025-01-11
 */
@Component
public class WebSocketServerFactory implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(
                    new DefaultByteBufferPool(false, 1024)
//                    new DefaultByteBufferPool(false, 1024,20,4)
            );
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}
