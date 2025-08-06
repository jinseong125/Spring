package org.shark.file.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.shark.file.model.dto.AttachDTO;
import org.shark.file.model.dto.NoticeDTO;
import org.shark.file.repository.NoticeDAO;
import org.shark.file.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Transactional  //----- 서비스 클래스 레벨에 설정한 @Transaction에 의해서 모든 메소드는 트랜잭션 처리가 됩니다.
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

  private final NoticeDAO noticeDAO;
  private final FileUtil fileUtil;
  
  
  @Transactional(readOnly =  true)  //----- 읽기전용 최적화를 통해 트랜잭션 매니저의 불필요한 ㄷ동작을 방지하여 성능을 향상할 수 있습니다.
  @Override
  public List<NoticeDTO> findNotices() {
    return noticeDAO.getNotices();
  }

  @Override
  public Map<String, Object> findNoticeById(Integer nid) {
    return Map.of("notice", noticeDAO.getNoticeById(nid)
                  ,"attaches", noticeDAO.getAttaches(nid));
  }

  @Override
  public boolean addNotice(NoticeDTO notice, List<MultipartFile> files) {
    try {
      System.out.println("공지사항 등록 이전 nid : " + notice.getNid());
      int addedNoticeCount = noticeDAO.insertNotice(notice);
      System.out.println("공지사항 등록 이후 nid : " + notice.getNid());

      if (addedNoticeCount == 1) {
        for (MultipartFile file : files) {
          if (!file.isEmpty()) {
            // 저장 경로 생성
            String filePath = fileUtil.getFilePath();
            Path uploadPath = Paths.get(filePath);
            if (Files.notExists(uploadPath)) {
              Files.createDirectories(uploadPath);
            }

            // 파일 이름 처리
            String originalFilename = file.getOriginalFilename();
            String filesystemName = fileUtil.getFilesystemName(originalFilename);

            // 실제 파일 저장
            Path fullPath = uploadPath.resolve(filesystemName);
            file.transferTo(fullPath.toFile());

            // DB에 첨부파일 정보 저장
            AttachDTO attach = AttachDTO.builder()
                .nid(notice.getNid())
                .originalFilename(originalFilename)
                .filesystemName(filesystemName)
                .filePath(filePath)
                .build();

            int addedAttachResult = noticeDAO.insertNotice(attach);
            if (addedAttachResult == 0) {
             return false;
            }
          }
        }
        return true;
      } else {
        return false;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }


  @Override
  public boolean deleteNotice(Integer nid) {
    //----- 삭제하려는 공지사항에 등록된 첨부 목록 가져오기
    List<AttachDTO> attaches = noticeDAO.getAttaches(nid);    
    //----- 첨부 목록 참조해서 서버에 저장된 첨부 파일들을 삭제하기
    attaches.stream()
        .map(attach -> new File(attach.getFilePath(), attach.getFilesystemName()))
        .filter(file -> file.exists())  //----- 메소드 참조 방식의 코드 .filter(File::exists)
        .forEach(file -> {
          boolean deleted = file.delete();
          if (!deleted) {
            System.out.println("===== 파일 삭제 실패 : " + file.getPath());
          }
        });
    /*
    for (AttachDTO attach : attaches) {
      String path = attach.getFilePath() + "/" + attach.getFilesystemName();
      File file = new File(path);
      if (file.exists()) {
        boolean deleted = file.delete();
        if (!deleted) {
          System.out.println("===== 파일 삭제 실패 : " + file.getPath());
        }
      }
    }
    */
    //----- DB에서 공지사항 삭제하기 (ON DELETE CASCADE에 의해서 첨부 목록은 함께 삭제됩니다.
    return noticeDAO.deleteNoticeById(nid) == 1;
  }

  @Transactional
  @Override
  public AttachDTO findAttachById(Integer aid) {
    return noticeDAO.getAttachById(aid);
  }

  @Transactional
  @Override
  public Resource loadAttachAsResource(AttachDTO attach) {
    return new FileSystemResource(attach.getFilePath() + "/" + attach.getFilesystemName());
  }


}
