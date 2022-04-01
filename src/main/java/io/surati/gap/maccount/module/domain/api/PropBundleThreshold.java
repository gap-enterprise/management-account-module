package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.admin.base.prop.PropCompany;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Bundle settings from properties file.
 * @since 3.0
 */
public final class PropBundleThreshold implements BundleThreshold {

    private final File file;

    private final Properties prop;

    public PropBundleThreshold() {
        this(PropBundleThreshold.openConfigFile());
    }

    public PropBundleThreshold(final File file) {
        this.file = file;
        this.prop = PropBundleThreshold.loadConfigFile(this.file);
    }

    @Override
    public Integer partialPayment() {
        return Integer.valueOf(this.get("ma_bundle_threshold_partial_payment"));
    }

    @Override
    public Integer totalPayment() {
        return Integer.valueOf(this.get("ma_bundle_threshold_total_payment"));
    }

    @Override
    public void update(Integer partialpay, Integer totalpay) {
        this.save("ma_bundle_threshold_partial_payment", partialpay.toString());
        this.save("ma_bundle_threshold_total_payment", totalpay.toString());
    }

    /**
     * Save value at key.
     * @param key Key
     * @param value Value to save
     */
    private void save(String key, String value) {
        this.prop.setProperty(key, String.valueOf(value));

        try(final OutputStream output = new FileOutputStream(this.file)) {
            this.prop.store(output, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get value of key.
     * @param key Key
     * @return value
     */
    private String get(String key) {
        return this.prop.getProperty(key);
    }

    /**
     * Get Properties file from file
     * @param file Properties file
     * @return
     */
    private static Properties loadConfigFile(final File file) {
        final Properties prop = new Properties();
        try(final InputStream input = new FileInputStream(file)){
            prop.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    /**
     * Get default Properties file
     * @return file
     */
    private static File openConfigFile() {
        final Path path = Paths.get(System.getProperty("user.dir"), "config.properties");
        final File file = path.toFile();
        if(!file.exists()) {
            try {
                try (
                    InputStream in = PropCompany.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties")
                ) {
                    Files.copy(in, path);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return file;
    }
}
