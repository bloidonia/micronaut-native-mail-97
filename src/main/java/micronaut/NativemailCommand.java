package micronaut;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.email.Attachment;
import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import io.micronaut.http.MediaType;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import software.amazon.awssdk.services.ses.model.SesRequest;
import software.amazon.awssdk.services.ses.model.SesResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Command(name = "nativemail", description = "...", mixinStandardHelpOptions = true)
@TypeHint(typeNames = {
        "org.apache.commons.logging.impl.LogFactoryImpl",
        "org.apache.commons.logging.LogFactory",
        "org.apache.commons.logging.impl.SimpleLog"
})
public class NativemailCommand implements Runnable {

    @Inject
    EmailSender<SesRequest, SesResponse> sender;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) {
        PicocliRunner.run(NativemailCommand.class, args);
    }

    public void run() {
        try {
            sender.send(
                    Email.builder()
                            .to("tim@bloidonia.com")
                            .from("tim@bloidonia.com")
                            .subject("test")
                            .body("It's " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                            .attachment(
                                    Attachment.builder()
                                            .filename("attachment.pdf")
                                            .contentType(MediaType.APPLICATION_PDF)
                                            .content(NativemailCommand.class.getResourceAsStream("/hi.pdf").readAllBytes())
                                            .build()
                            )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
