package com.anjoy.cloud.component.controller.upload;

import com.alibaba.fastjson.JSONObject;
import com.anjoy.cloud.component.exception.ServiceException;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.utils.DateUtil;
import com.anjoy.cloud.component.utils.FileUtils;
import com.anjoy.cloud.component.utils.ZipUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * web文件上传Controller
 *
 * @author Zhangyq
 * @version 2017-02-08
 */
@RestController
@RequestMapping(value = "webUpload")
public class WebUploadController {

    @Value("${microservice.uploadRootDir}")
    private String linuxUploadRootDir;

    @Value("${microservice.zipDir}")
    private String zipDir;

    @Value("${microservice.enableUpload}")
    private Boolean isEnableUpload;


    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "upload")
    @ResponseBody
    public JsonResult upload(HttpServletRequest request, HttpServletResponse response)throws Exception {
        if (!isEnableUpload){
            throw new ServiceException("服务器未开启上传支持");
        }
        try {
            //获取当前日期yyyy-MM-dd
            String currentDate = DateUtil.getCurrentDateTimeStr(DateUtil.FMT_DATE);
            //区分linux与windows下的根目录
            String rootDir = linuxUploadRootDir;
            //获取前台传递的路径
            String path = request.getParameter("path");
            if ("".equals(path) || path == null) {
                path = "unnamed";
            }
            // 文件存放的目录
            String tempDirPath = rootDir + path + File.separator + currentDate + File.separator;

            //判断文件夹是否存在，如果不存在，则创建文件夹。
            checkFileDir(tempDirPath);

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String fileName = null;
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                // 上传文件名
                MultipartFile mf = entity.getValue();
                String fileExt = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1)
                        .toLowerCase();
                //文件名换成uuid
                fileName = UUID.randomUUID().toString().replace("-","") + "." + fileExt;
//                fileName = DateUtils.getDate("yyyyMMddHHmmss") + "_" + new Random().nextInt(1000) + "." + fileExt;
                File uploadFile = new File(tempDirPath + fileName);
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
            }
            return new JsonResult(JsonResultCode.SUCCESS,"上传文件成功",path + File.separator + currentDate + File.separator + fileName);
//            return path + File.separator + currentDate + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("上传失败，请稍后再试",e);
        }
    }

    /**
     * base64文件上传，此接口专门针对圆舟的前端上传只能为base64
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "uploadBase64")
    @ResponseBody
    public JsonResult uploadBase64(@RequestBody JSONObject objParam, HttpServletRequest request, HttpServletResponse response) {
        if (!isEnableUpload){
            throw new ServiceException("服务器未开启上传支持");
        }
        try {

            //获取当前日期yyyy-MM-dd
            String currentDate = DateUtil.getCurrentDateTimeStr(DateUtil.FMT_DATE);
            //区分linux与windows下的根目录
            String rootDir = linuxUploadRootDir;
            //获取前台传递的路径
            String path = objParam.getString("path");
            if ("".equals(path) || path == null) {
                path = "unnamed";
            }
            // 文件存放的目录
            String tempDirPath = rootDir + path + File.separator + currentDate + File.separator;

            //判断文件夹是否存在，如果不存在，则创建文件夹。
            checkFileDir(tempDirPath);

            //base64转换
            String base64str = objParam.getString("base64str");

            String fileName = null;
            // 上传文件名
            MultipartFile mf = Base64ToImgFile(base64str);
            if (mf == null){
                throw new ServiceException("未找到base64的文件流");
            }
            String fileExt = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            //文件名换成uuid
            fileName = UUID.randomUUID().toString().replace("-","") + "." + fileExt;
            File uploadFile = new File(tempDirPath + fileName);
            FileCopyUtils.copy(mf.getBytes(), uploadFile);
            return new JsonResult(JsonResultCode.SUCCESS,"上传文件成功",path + File.separator + currentDate + File.separator + fileName);
//            return path + File.separator + currentDate + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("上传失败，请稍后再试",e);
        }
    }


    /**
     * 文件删除
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, HttpServletResponse response) {
        if (!isEnableUpload){
            throw new ServiceException("服务器未开启上传支持");
        }
        try {
            String filepath = request.getParameter("filepath");
            String rootDir = linuxUploadRootDir;
            filepath = rootDir + filepath;
            File fileToDelete = new File(filepath);
            if (fileToDelete.isDirectory()){throw new ServiceException("不允许删除路径");} //不允许删除路径
            if (!fileToDelete.exists()){return new JsonResult(JsonResultCode.SUCCESS,"删除文件成功",null);} //文件检查
            FileSystemUtils.deleteRecursively(fileToDelete);
            return new JsonResult(JsonResultCode.SUCCESS,"删除文件成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败，请稍后再试",e);
        }
    }

    /**
     * 创建zip压缩文件，注意zip文件有效期为1小时，可设置
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "makezip")
    @ResponseBody
    public JsonResult makezip(@RequestBody JSONObject objParam, HttpServletRequest request, HttpServletResponse response) {
        if (!isEnableUpload){
            throw new ServiceException("服务器未开启上传支持");
        }

        String rootDir = linuxUploadRootDir;

        //压缩文件存在的临时目录，目录内文件将在24小时内删除
        String tozipDir = rootDir + zipDir + File.separator;

        //判断文件夹是否存在，如果不存在，则创建文件夹。
        checkFileDir(tozipDir);

        String srcfile = objParam.getString("zipFiles");
        String[] srcfiles = srcfile.split(";");
        for (int i = 0; i < srcfiles.length; i++){
            //拼装物理路径
            srcfiles[i] = rootDir + srcfiles[i];
        }
        //压缩文件名
        String zipfilename =  UUID.randomUUID().toString().replace("-","") + ".zip";

        //压缩文件全路径+文件名
        String targetfile = tozipDir + zipfilename;



        try {
            ZipUtil.compress(srcfiles,targetfile);
            //返回相对地址
            return new JsonResult(JsonResultCode.SUCCESS,"压缩文件成功",zipDir + File.separator + zipfilename);
//            return zipDir + File.separator + zipfilename;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("压缩文件失败，请稍后再试",e);
        }
    }

    /**
     * 判断文件夹是否存在，如果不存在，则创建文件夹。
     *
     * @param filePath
     */
    private void checkFileDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    *
    * base64转换multipartfile工具
    *
    * */
    public static MultipartFile Base64ToImgFile(String Base64Str) {
        try {
            String[] baseStrs = Base64Str.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
