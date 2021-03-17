package calls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("calls.wsdl");

        return marshaller;
    }

    @Bean
    public LogEntryClient logEntryClient(Jaxb2Marshaller marshaller) {
        LogEntryClient client = new LogEntryClient();
        client.setDefaultUri("http://localhost:8090/logEntries");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }
}
