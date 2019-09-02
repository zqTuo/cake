package io.renren.modules.sys.service;

import io.renren.common.utils.poi.model.ExcelBean;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/20 23:39.
 */
public interface ExcelService {
    boolean createExport(HttpServletRequest request, HttpServletResponse response, List<? extends ExcelBean> exportData, String template, String fileName, String outPath) throws IOException;

    List<T> readExcel(String filePath);
}
