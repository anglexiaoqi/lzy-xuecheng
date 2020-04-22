package com.xuecheng.web.export;

import com.xuecheng.domain.SysConfig;
import com.xuecheng.framework.export.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * @date 2020/4/22 11:51
 */

@Controller
@RequestMapping(value = "/export")
@Slf4j
public class ExportController {
    /**
     * 导出
     * @return
     * @throws Exception
     */
    @RequestMapping(value="doExport",method= RequestMethod.GET)
    public String export(String searchDatas, HttpServletResponse response) throws Exception{

        List list =new ArrayList();
        String filename ="测试"+".xls";
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + new String(filename.getBytes("gbk"), "ISO-8859-1"));
        OutputStream out =response.getOutputStream();
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        util.exportExcel(list, "测试", 65536, out);
        return null;
    }
}
