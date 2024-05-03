package com.taskmaster.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taskmaster.entity.Epic;
import com.taskmaster.enums.Status;
import com.taskmaster.service.EpicService;

@RestController
public class DashboardController {

	@Autowired
	private EpicService epicService;

	@GetMapping("/")
	public ModelAndView dashboard(Map<String, Object> model) {
		model.put("BACKLOG", getAllEpicByStatus(Status.BACKLOG));
		model.put("IN_PROGRESS", getAllEpicByStatus(Status.IN_PROGRESS));
		model.put("COMPLETED", getAllEpicByStatus(Status.COMPLETED));

		return new ModelAndView("dashboard", model);
	}

	private List<Epic> getAllEpicByStatus(Status status) {
		return epicService.getEpicsByStatus(status);
	}
}
