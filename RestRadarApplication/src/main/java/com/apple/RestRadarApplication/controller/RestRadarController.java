package com.apple.RestRadarApplication.controller;

import java.sql.Timestamp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apple.RestRadarApplication.bean.UserDetails;
import com.apple.RestRadarApplication.constants.RestRadarConstants;
import com.apple.RestRadarApplication.response.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(RestRadarConstants.PROBLEMS_URL)
public class RestRadarController {

	@ResponseBody
	@GetMapping
	public MessageResponse getMessage() {

		MessageResponse response = new MessageResponse();
		response.setMessage("Welcome to Radar Web Services");

		return response;
	}

	@ResponseBody
	@PostMapping
	// @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public Object userDetails(@RequestBody UserDetails userDetails, @RequestHeader("TO_UpperCase") Boolean upperCase)
			throws JsonProcessingException {

		if (upperCase==null) {
			MessageResponse response = new MessageResponse();
			response.setMessage("TO_UpperCase header cannot be null. Give is as true or false");

			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(response);
			return ResponseEntity.badRequest().body(data);

		} 
		if (upperCase == true) {
			UserDetails user = new UserDetails();
			user.setfName(userDetails.getfName().toUpperCase());
			user.setTitle(userDetails.getTitle().toUpperCase());
			return user;
		}

		return userDetails;
	}

	@PutMapping
	public ResponseEntity<String> updateUserDetails(@RequestBody UserDetails userDetails)
			throws JsonProcessingException {

		UserDetails user = new UserDetails();
		user.setfName(userDetails.getfName());
		user.setTitle(userDetails.getTitle());

		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(user);
		return ResponseEntity.ok().header("X_TIME_OF _EVENT", String.valueOf(new Timestamp(System.currentTimeMillis())))
				.body(data);
	}

	@ResponseBody
	@DeleteMapping("/{number}")
	public MessageResponse deleteNumber(@PathVariable Integer number) throws Exception {

		MessageResponse resp = new MessageResponse();

		if (number != null) {
			resp.setMessage("Given number " + number + " is deleted successfully");
		} else {
			throw new Exception("Number cannot be null");
		}

		return resp;
	}

}
