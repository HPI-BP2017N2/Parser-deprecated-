package de.hpi.parser.respository;

import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ParsedOfferFileRepository implements ParsedOfferRepository {

    //constants
    @Getter(AccessLevel.PRIVATE) private static final String TARGET_DIR = "offer-json-files", FILE_ENDING = ".txt";

    public ParsedOfferFileRepository() throws Exception {
        File targetDir = new File(getTARGET_DIR());
        if(!targetDir.exists() && !targetDir.mkdirs()){
            throw new Exception("Could not create target directory for offers.");
        }
    }

    @Override
    public void save(long shopId, String offerJsonString) {
        String fileName = Long.toString(shopId) + " - " + System.currentTimeMillis() + FILE_ENDING;
        try (PrintWriter writer = new PrintWriter(new FileWriter(getTARGET_DIR() + File.separator + fileName))) {
            writer.write(offerJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(long shopId, ParsedOffer offer) {

    }
}
