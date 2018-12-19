package src.main.java;


import com.google.common.io.ByteStreams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import java.util.List;

import java.util.concurrent.TimeUnit;
import java.lang.ProcessBuilder;


import java.util.logging.Logger;

import java.time.Duration;


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

        // Spawn subprocess
        List<String> command = Arrays.asList("cat", dataFileName);
        doSystemCall(timeout, command);
    }

    private static void doSystemCall(Duration timeout, List<String> command) {
        logger.info("Spawn process");
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Could not spawn process", e);
        }

        logger.info("Wait until process has terminated");
        try {
            process.waitFor(timeout.getSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Run into timeout while waiting for process termination", e);
        }

        logger.info("Read from process stdout");
        InputStream processStdout = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(processStdout);
        BufferedReader br = new BufferedReader(isr);
        String line;
        long lineCount = 0;
        try {
            while ((line = br.readLine()) != null) {
                lineCount++;
                logger.fine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read from process stdout", e);
        }

        logger.info(String.format("Successfully read %d lines from process stdout", lineCount));
    }
}
