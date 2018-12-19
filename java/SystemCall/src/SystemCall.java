package src.main.java;


import com.google.common.io.ByteStreams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class SystemCall {

    private static final Logger logger = Logger.getLogger(SystemCall.class.getName());

    public static void main(String[] args) {

        final String dataFileName = "huge_file.txt";
        final Duration timeout = Duration.ofSeconds(5);

        // Generate data file
        try (PrintWriter writer = new PrintWriter(dataFileName, "UTF-8")) {
            for (int i = 0; i < 10000; i++) {
                writer.println(String.format("This is line of text number %d", i));
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Open data input file
        File dataFile = new File(dataFileName);
        InputStream data = null;
        try {
            data = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Spawn subprocess
        List<String> command = Arrays.asList("grep", "9");
        doSystemCall(timeout, command, data);
    }

    private static void doSystemCall(Duration timeout, List<String> command, InputStream data) {
        logger.info("Spawn process");
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Could not spawn process", e);
        }

        final InputStream processStdout = process.getInputStream();
        final ByteArrayOutputStream processStdoutBuffer = new ByteArrayOutputStream();

        new Thread(new Runnable() {
            public void run() {
                try {
                    ByteStreams.copy(processStdout, processStdoutBuffer);
                } catch (IOException e) {
                    throw new RuntimeException("Could not read from process stdout", e);
                }
            }
        }).start();

        logger.info("Write to process stdin");
        try (OutputStream processStdin = process.getOutputStream()) {
            ByteStreams.copy(data, processStdin);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to process stdin", e);
        }

        logger.info("Wait until process has terminated");
        try {
            process.waitFor(timeout.getSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Run into timeout while waiting for process termination", e);
        }

        logger.info("Read from process stdout (buffer)");
        String processStdoutBufferString = null;
        try {
            processStdoutBufferString = processStdoutBuffer.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not convert process stdout buffer to string", e);
        }

        long lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new StringReader(processStdoutBufferString))) {
            String line = reader.readLine();
            while (line != null) {
                lineCount++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read from process stdout string", e);
        }
        logger.info(String.format("Successfully read %d lines from process stdout", lineCount));
    }
}
