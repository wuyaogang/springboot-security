package com.wyg.web;

import com.wyg.po.User;
import com.wyg.service.IUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A14857 on 2019/2/13.
 */
@Controller
@RequestMapping("/import")
public class ImportController {
    @Autowired
    private IUserService userService;
    @RequestMapping("/downloadExcel")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表","UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        User user = userService.getUserById(1);//获得用户
        List<User> list = new ArrayList<>();
        list.add(user);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), User.class, list);
        workbook.write(response.getOutputStream());
    }
    @RequestMapping("/excelImport")
    @ResponseBody
    public String excelImport() {
        ImportParams importParams = new ImportParams();
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        try {
            List<User> objects = ExcelImportUtil.importExcel(new File("F:\\aa.xls"), User.class,
                    importParams);
            userService.importUser(objects);
            System.out.println(objects);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "no";
        }
    }
}
