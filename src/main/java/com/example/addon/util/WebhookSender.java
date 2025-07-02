package com.mydonut.addon.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class WebhookSender {
    public static void send(String webhookUrl, String content) {
        new Thread(() -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String json = "{\"content\":\"" + content + "\"}";
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes());
                }
                conn.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
