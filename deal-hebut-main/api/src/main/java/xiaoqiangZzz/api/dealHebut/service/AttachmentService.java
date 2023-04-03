package xiaoqiangZzz.api.dealHebut.service;

import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealHebut.entity.Attachment;

public interface AttachmentService {
    Attachment uploadImage(MultipartFile file);
}
