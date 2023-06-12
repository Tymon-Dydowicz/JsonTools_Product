package pl.put.poznan.transformer.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.put.poznan.transformer.logic.*;

import javax.swing.*;
import java.io.IOException;

public class Gui extends JFrame {

    private static Gui instance = null;
    private JPanel mainPanel;
    private JTextArea leftTopArea;
    private JTextArea outputText;
    private JPanel leftPanel;
    private JTextArea input2Text;
    private JTextArea input1Text;
    private JPanel leftTopPanel;
    private JPanel leftBottomPanel;
    private JPanel rightPanel;
    private JPanel middlePanel;
    private JLabel input1Label;
    private JLabel input2Label;
    private JLabel outputLabel;
    private JPanel middle1Panel;
    private JPanel middle2Panel;
    private JPanel middle3Panel;
    private JPanel middle4Panel;
    private JPanel middle5Panel;
    private JPanel middle6Panel;
    private JPanel middle7Panel;
    private JButton compareButton;
    private JButton selectKeysButton;
    private JButton deleteKeysButton;
    private JButton minifyButton;
    private JButton unminifyButton;
    private JButton importButton;
    private JButton exportButton;

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    public Gui() {
        this.setTitle("JsonTools");
        this.setContentPane(mainPanel);
        this.setSize(400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        leftTopPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftBottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle1Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle2Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle3Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle4Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle5Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle6Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middle7Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        compareButton.addActionListener(e -> {
            JsonComparator jsonComparator = new JsonComparator();
            String input1 = input1Text.getText();
            String input2 = input2Text.getText();
            String output = jsonComparator.compareJson(input1, input2);
            outputText.setText(output);
        });

        selectKeysButton.addActionListener(e -> {
            JsonSelectingKeys jsonSelectingKeys = new JsonSelectingKeys();
            String input1 = input1Text.getText();
            String input2 = input2Text.getText();
            String[] input2split = input2.split("\n");
            String output = null;
            try {
                output = jsonSelectingKeys.selectKeys(input1, input2split);
            } catch (JsonProcessingException ex) {
                throw new IllegalArgumentException(ex);
            }
            outputText.setText(output);
        });

        deleteKeysButton.addActionListener(e -> {
            JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
            String input1 = input1Text.getText();
            String input2 = input2Text.getText();
            String[] input2split = input2.split("\n");
            String output = null;
            try {
                output = jsonDeletingKeys.deleteKeys(input1, input2split);
            } catch (JsonProcessingException ex) {
                throw new IllegalArgumentException(ex);
            }
            outputText.setText(output);
        });

        minifyButton.addActionListener(e -> {
            Minificator minificator = new Minificator();
            String input1 = input1Text.getText();
            String output = null;
            try {
                output = minificator.minifyJson(input1);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            outputText.setText(output);
        });

        unminifyButton.addActionListener(e -> {
            Minificator minificator = new Minificator();
            String input1 = input1Text.getText();
            String output = null;
            try {
                output = minificator.unMinifyJson(input1);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            outputText.setText(output);
        });

        importButton.addActionListener(e -> {
            XmlToJsonImporter xmlToJsonImporter = new XmlToJsonImporter();
            String input1 = input1Text.getText();
            String output = null;
            try {
                output = xmlToJsonImporter.importToJson(input1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            outputText.setText(output);
        });

        exportButton.addActionListener(e -> {
            JsonToXmlExporter jsonToXmlExporter = new JsonToXmlExporter();
            String input1 = input1Text.getText();
            String output = null;
            try {
                output = jsonToXmlExporter.exportToXml(input1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            outputText.setText(output);
        });
    }
}
