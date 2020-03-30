package com.clrvn.controller;

import com.clrvn.entity.Paper;
import com.clrvn.entity.User;
import com.clrvn.enums.ResultFailureEnum;
import com.clrvn.exception.PaperException;
import com.clrvn.service.IPaperService;
import com.clrvn.utils.*;
import com.clrvn.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Clrvn
 * @description
 * @className PaperController
 * @date 2019-05-20 23:12
 */
@Controller
@RequestMapping("/paper")
@Slf4j
public class PaperController {

    @Resource
    private IPaperService paperService;

    @GetMapping("/findPaperPage")
    @ResponseBody
    public Page<Paper> findPaperPage(HttpSession session, Integer pageIndex, Boolean isSystem) {

        User user = (User) session.getAttribute("USER");

        log.info("page={}", paperService.findAllByUserIdAndSystem(pageIndex, user.getId(), isSystem));

        return paperService.findAllByUserIdAndSystem(pageIndex, user.getId(), isSystem);
    }

    @GetMapping("/findAllPaperPage")
    @ResponseBody
    public Page<Paper> findAllPaperPage(Integer pageIndex, Boolean isSystem) {

        log.info("page={}", paperService.findAllBySystem(pageIndex, isSystem));

        return paperService.findAllBySystem(pageIndex, isSystem);
    }

    @DeleteMapping("/removeById")
    @ResponseBody
    public ResultVO removeById(Integer id) {

        try {
            int count = paperService.removePaperById(id);
            if (count > 0) {
                return ResultVOUtil.success();
            } else {
                return ResultVOUtil.failure(ResultFailureEnum.REMOVE_PAPER_FAILURE);
            }
        } catch (Exception ex) {
            throw new PaperException(ResultFailureEnum.REMOVE_PAPER_FAILURE);
        }

    }

    /**
     * 处理文件上传，并算出与系统论文的比较
     *
     * @param file    文件
     * @param session 会话
     * @return 成功
     */
    @PostMapping(value = "/addPaper")
    public String addPaper(@RequestParam("paperFile") MultipartFile file, @RequestParam("isSystem") Boolean isSystem,
                           @RequestParam("paperType") Integer paperType, @RequestParam("paperName") String paperName,
                           HttpSession session, Model model) {

        String fileName = file.getOriginalFilename();

        System.err.println(isSystem);
        System.err.println(paperName);
        System.err.println(paperType);

        if (fileName.endsWith(".doc")) {
            fileName = UIDUtils.getUId() + ".doc";
        } else if (fileName.endsWith(".docx")) {
            fileName = UIDUtils.getUId() + ".docx";
        }

        try {

            //文件上传
            FileUtil.uploadFile(file.getBytes(), FileUtil.UPLOAD_PATH + fileName);


            //添加
            Paper paper = new Paper();
            paper.setPaperName(paperName);
            paper.setPaperType(paperType);
            paper.setPaperPath(fileName);
            paper.setUser((User) session.getAttribute("USER"));
            if (isSystem) {
                paper.setReportPath(null);
            } else {

                //用户提交的论文与系统论文进行比较
                Paper systemPaper = paperService.getSystemPaperByType(paperType);

                //读取论文文件组装list
                List<String> userPaperList = FileUtil.getListByFile(FileUtil.UPLOAD_PATH + fileName);
                //读取论文文件组装list
                List<String> systemPaperList = FileUtil.getListByFile(FileUtil.UPLOAD_PATH + systemPaper.getPaperPath());

                //论文报告list
                List<String> reportList = RecheckingUtil.comparePaper(userPaperList, systemPaperList);

                String reportPath = UIDUtils.getUId();

                //写入报告文件
                FileUtil.writeFileByLines(reportList, FileUtil.UPLOAD_PATH + reportPath);

                model.addAttribute("userPaperList", userPaperList);
                model.addAttribute("systemPaperList", systemPaperList);
                paper.setReportPath(reportPath);
            }
            paper.setSystem(isSystem);
            paper.setCreateTime(new Date());

            //新增
            Paper newPaper = paperService.savePaper(paper);

            System.err.println(newPaper.getId());
            model.addAttribute("id", newPaper.getId());
        } catch (Exception e) {
            throw new PaperException(ResultFailureEnum.ADD_PAPER_FAILURE);
        }
        return "success";
    }

    /**
     * 下载报告
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/downloadReport")
    public ResponseEntity<byte[]> downloadReport(@RequestParam("id") Integer id) throws IOException {
        Paper paper = paperService.getOneById(id);
        String path = FileUtil.UPLOAD_PATH + paper.getReportPath();
        File file = new File(path);
        return ResponseUtils.buildResponseEntity(file, paper.getPaperName() + "的论文报告.txt");
    }

    /* *//**
     * 下载系统论文
     *
     * @param id
     * @return
     * @throws IOException
     *//*
    @GetMapping("/downloadSystem")
    public ResponseEntity<byte[]> downloadSystem(@RequestParam("id") Integer id) throws IOException {
        Paper paper = paperService.getOneById(id);
        Paper systemPaper = paperService.getSystemPaperByType(paper.getPaperType());
        String path = FileUtil.UPLOAD_PATH + systemPaper.getPaperPath();
        File file = new File(path);
        String paperTypeName;

        Integer paperType = systemPaper.getPaperType();
        if (paperType == PaperTypeConstant.COMPUTER_TECHNOLOGY) {
            paperTypeName = "计科";
        } else if (paperType == PaperTypeConstant.SOFTWARE_ENGINEERING) {
            paperTypeName = "软工";
        } else if (paperType == PaperTypeConstant.COMMUNICATION) {
            paperTypeName = "通信";
        } else {
            paperTypeName = "电子";
        }
        return ResponseUtils.buildResponseEntity(file, paperTypeName + "系统论文.doc");
    }*/


}
