package io.renren.modules.sys.service.impl;

import io.renren.common.utils.poi.ExcelFactory;
import io.renren.common.utils.poi.FileUtil;
import io.renren.common.utils.poi.model.ExcelBean;
import io.renren.modules.sys.service.ExcelService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/20 23:40.
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public boolean createExport(HttpServletRequest request, HttpServletResponse response, List<? extends ExcelBean> exportData, String template, String fileName, String outPath) throws IOException {
        fileName = FileUtil.encodeDownloadFileName(fileName,request.getHeader("user-agent"));

        response.setContentType(request.getServletContext().getMimeType(fileName));
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")  + ".xls");
        OutputStream outputStream = null;
        if(StringUtils.isEmpty(outPath)){//直接浏览器下载
            outputStream = response.getOutputStream();
        }else{//输出到本地
            File file = new File(outPath);
            outputStream = new FileOutputStream(file);
        }
        return ExcelFactory.export(template,exportData,outputStream);
    }

    @Override
    public List<T> readExcel(String filePath) {
        return ExcelFactory.read(filePath);
    }
}
