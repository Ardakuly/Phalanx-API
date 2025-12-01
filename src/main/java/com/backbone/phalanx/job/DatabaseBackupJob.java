package com.backbone.phalanx.job;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
@Log4j2
public class DatabaseBackupJob {

    @Value("${spring.datasource.username}")
    private String DB_USER;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Scheduled(cron = "0 0 10 * * *", zone = "Asia/Almaty")
    public void backupDatabase() throws IOException, InterruptedException {

        String home = System.getProperty("user.home");
        String backupFolder = home + "/Desktop/backups";
        String fileName = backupFolder + "/db-" + LocalDate.now() + ".sql";

        File dir = new File(backupFolder);
        if (!dir.exists() && dir.mkdirs()) {
            log.info("[DATABASE]: Created backup directory at {}", backupFolder);
        }

        ProcessBuilder pb = new ProcessBuilder(
                "pg_dump",
                "-U", DB_USER,
                "-h", "localhost",
                "-d", "phalanx",
                "-f", fileName
        );
        pb.environment().put("PGPASSWORD", DB_PASSWORD);

        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            log.error("[DATABASE]: Backup FAILED (exit code {})", exitCode);
        } else {
            log.info("[DATABASE]: Backup SUCCESS — saved to {}", fileName);
        }
    }
}
