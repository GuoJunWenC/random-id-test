package org.example.api.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/export")
@RestController
public class ExportController {
    @Autowired
    private FreemarkerUtil freemarkerUtil;

    @GetMapping("/exportWorld")
    public void exportWorld(Integer id, HttpServletResponse response) throws IOException {
        ProjectMonthReport projectMonthReport = new ProjectMonthReport();
        projectMonthReport = projectMonthReport.builder().id(id)
                .projectName("1222")
                .constructionMileage("23")
                .date("2023kasdnk")
                .build();
        if (Objects.isNull(projectMonthReport)) {
            throw new RuntimeException("项目不存在");
        }
        /*
         * projectMonthReport.setDate(projectMonthReport.getReportDate().toString());
         */
        Map<String, Object> map = new HashMap<>();
        List<ProjectExploitation> exploitationList = new ArrayList<>();
        exploitationList.add(ProjectExploitation.builder().advanceSite("12").unitName(
                "asdsawqeeqweqwewqwewqewqeEDWSWSWSWSWSWSWSWSWSWSFERW QWEDDWQ3EDRFEWfreckle非财务部规划 二刀肉访问发热体无4而发愁软文二锅头否4如染发给他房3让我给他发34 34温柔风 3丰田果 34儿童房个人43恶女个")
                .build());
        exploitationList.add(ProjectExploitation.builder().advanceSite("11").unitName("哈哈哈").build());
        List<ProjectPipelineRelocation> list = new ArrayList<>();
        list.add(ProjectPipelineRelocation.builder().designModification(1).unitName("asdsawqeeqweqwewqwewqewqe")
                .designModificationComplete(3).build());
        list.add(ProjectPipelineRelocation.builder().designModification(2).unitName("哈哈哈").designModificationComplete(4)
                .build());
        map.put("data", projectMonthReport);
        map.put("exploitationList", exploitationList);
        map.put("pipelineRelocationList", list);
        map.put("constructionProgressList", new ArrayList<>());
        map.put("constructionProgressList", new ArrayList<>());
        map.put("constructionProgressList", new ArrayList<>());

        freemarkerUtil.createFreemarkerDoc(map, response, "projectReport.ftl");
    }

}
