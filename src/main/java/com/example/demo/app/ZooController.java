package com.example.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Zoo;
import com.example.demo.service.ZooService;

@Controller
@RequestMapping("/zoo")
public class ZooController {
	
	//下記のリクエストで使用するために、AnimalService型のフィールド用意 & @AutowiredでDIを実施する。	
	private final ZooService zooService;

	@Autowired
	public ZooController(ZooService zooService) {
		this.zooService = zooService;
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable int id, Model model){
		Zoo zoo = zooService.findById(id);
		
		model.addAttribute("zoo", zoo);
		
		return "zoo/detail";
	}

}
