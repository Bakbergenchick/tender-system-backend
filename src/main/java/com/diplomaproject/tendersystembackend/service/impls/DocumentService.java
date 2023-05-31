package com.diplomaproject.tendersystembackend.service.impls;

import com.diplomaproject.tendersystembackend.model.Document;
import com.diplomaproject.tendersystembackend.repo.DocumentRepository;
import com.diplomaproject.tendersystembackend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        Document imageData = documentRepository.save(Document.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .documentData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional(readOnly=true)
    public byte[] downloadImage(String fileName){
        Optional<Document> documentByName = documentRepository.findDocumentByName(fileName);
        byte[] images=ImageUtils.decompressImage(documentByName.get().getDocumentData());
        return images;
    }
}
