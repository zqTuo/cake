package io.renren.common.utils.poi;

import io.renren.common.utils.poi.model.ExcelBean;
import io.renren.common.utils.poi.model.ExcelTemplateUtils;
import io.renren.common.utils.poi.model.PropertyModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/3/16 16:43.
 */
public class ExcelFactory {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static boolean export(String templateXmlName, List<? extends ExcelBean> exportData, OutputStream outputStream){
        List<PropertyModel> propertyModelList = ExcelTemplateUtils.getTemplateConfig(templateXmlName);
        if(propertyModelList == null){
            logger.info("模板文件：" + templateXmlName + "不存在");
            return false;
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFCellStyle style = workbook.createCellStyle();

        createHead(workbook,sheet,propertyModelList,style);

        HSSFRow currentRow = null;
        String column = null;
        Object object = null;
        Object value = null;

        //遍历导出 exportDate 的数据到表格中
        try {
            if(!CollectionUtils.isEmpty(exportData)){
                int index = 0;//序列号
                for(Object dataObject:exportData){
                    ++index;
                    int rowNum = sheet.getLastRowNum();
                    currentRow = sheet.createRow(++rowNum);

                    for(PropertyModel property:propertyModelList){
                        column = property.getName();
                        if("serialNum".equals(column)){
                            currentRow.createCell(property.getPosition()).setCellValue(index);
                            continue;
                        }

                        object = ObjectUtils.getObjectPropertyValue(dataObject,column);
                        value = ObjectUtils.getVlaueByType(object,property.getType(),property.getDateFormat(),property.getNumberFormat(),property.getDefaultValue());

                        if("Integer".equalsIgnoreCase(property.getType())){
                            currentRow.createCell(property.getPosition()).setCellValue(Integer.valueOf(value.toString()));
                        }else{
                            currentRow.createCell(property.getPosition()).setCellValue(value.toString());
                        }
                    }
                }
            }

            int coloumNum=sheet.getRow(1).getPhysicalNumberOfCells();//获得总列数
            for (int i = 0; i < coloumNum; i++) {
                sheet.autoSizeColumn((short)i); //自适应列宽
            }

            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出Excel失败：" + e.getMessage());
            return false;
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("导出Excel关闭失败！");
                }
            }
        }

        return true;
    }

    private static void createHead(HSSFWorkbook workbook, HSSFSheet sheet, List<PropertyModel> propertyModelList, HSSFCellStyle style) {
        HSSFRow headRow = sheet.createRow(0);
        //编写列，填写表内容
        for(PropertyModel property:propertyModelList){
            HSSFCell cell = headRow.createCell(property.getPosition());
            cell.setCellValue(property.getTitle());
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.RED.index);
            HSSFFont headFont = workbook.createFont();
            headFont.setFontName("Courier New");
            headFont.setFontHeightInPoints((short)14);
            headFont.setColor(HSSFColor.WHITE.index);
            style.setFont(headFont);

            style.setAlignment(HorizontalAlignment.CENTER);

            cell.setCellStyle(style);
        }
    }

    public static List<T> read(String filePath) {
        List<PropertyModel> propertyModelList = ExcelTemplateUtils.getTemplateConfig(filePath);
        // todo 待处理工厂读取excel
        return null;
    }
}
