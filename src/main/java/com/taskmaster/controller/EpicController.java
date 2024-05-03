package com.taskmaster.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmaster.entity.Epic;
import com.taskmaster.enums.AppUserRole;
import com.taskmaster.enums.Status;
import com.taskmaster.exception.RestException;
import com.taskmaster.service.EpicService;
import com.taskmaster.utility.Transformer;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/epic")
public class EpicController {

	@Autowired
	private EpicService epicService;

	@PostMapping
	public ResponseEntity<String> newEpic(HttpServletRequest request, @RequestBody String payload) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals(AppUserRole.USER))
			throw new RestException("Access Denied!", HttpStatus.FORBIDDEN);

		Map<String, String> data = (Map<String, String>) Transformer.getJSONObject(payload);
		Long epicID = epicService
				.saveEpic(new Epic(data.get("title"), data.get("description"), data.get("team"), Status.BACKLOG));

		String jsonResponse = Transformer.getJSONString(Map.of("status", "success", "epicID", epicID));
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PutMapping(path = "{id}")
	public ResponseEntity<String> updateEpic(HttpServletRequest request, @PathVariable("id") Long id,
			@RequestBody String payload) {
		System.out.println("======= " + request.getSession().getAttribute("role"));
		Map<String, String> data = (Map<String, String>) Transformer.getJSONObject(payload);
		epicService.setEpicDetails(id, data);

		String jsonResponse = Transformer.getJSONString(Map.of("status", "success"));
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PutMapping(path = "{id}/{status}")
	public ResponseEntity<String> updateEpicStatus(HttpServletRequest request, @PathVariable("id") Long id,
			@PathVariable("status") String status) {
		epicService.setEpicStatus(id, Status.valueOf(status));

		String jsonResponse = Transformer.getJSONString(Map.of("status", "success"));
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<String> removeEpic(HttpServletRequest request, @PathVariable("id") Long id) {
		if (request.getSession() != null && request.getSession().getAttribute("role").equals(AppUserRole.USER))
			throw new RestException("Access Denied!", HttpStatus.FORBIDDEN);

		epicService.deleteEpic(id);

		String jsonResponse = Transformer.getJSONString(Map.of("status", "success"));
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

}
