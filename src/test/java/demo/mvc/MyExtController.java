package demo.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openwebflow.mvc.tool.WebFlowParam;
import org.openwebflow.tool.ContextToolHolder;
import org.openwebflow.tool.ProcessEngineTool;
import org.openwebflow.tool.TaskTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyExtController
{
	@Autowired
	private ProcessEngineTool _processEngineTool;

	@RequestMapping("/doCompleteAdjustTask.action")
	public String doCompleteAdjustTask(@WebFlowParam
	ContextToolHolder holder, @RequestParam
	Map<String, Object> formValues, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		TaskTool helper = holder.getTaskTool();
		model.put("task", helper.getTask());
		model.put("process", helper.getProcessInstance());

		helper.completeTask(formValues);
		Logger.getLogger(this.getClass()).debug("ProcessEngineTool: " + _processEngineTool);
		Logger.getLogger(this.getClass()).info("...........doCompleteAdjustTask...........");

		return "/doCompleteTask";
	}
}
