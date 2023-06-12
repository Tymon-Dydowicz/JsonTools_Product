package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JsonToolsApp {

    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApp.class, args);
        System.setProperty("java.awt.headless", "false");
        Gui gui = Gui.getInstance();
        gui.setVisible(true);
    }
}
