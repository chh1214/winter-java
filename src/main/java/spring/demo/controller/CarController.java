package spring.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import spring.demo.dao.CarDao;
import spring.demo.entity.Car;
import spring.demo.service.CarService;

@Controller
public class CarController {

	@Autowired
	private CarService carService;

	@RequestMapping(value = "/login")
	public ModelAndView Login(ModelAndView modelAndView) {
		modelAndView.setViewName("/login");
		return modelAndView;
	}

	@RequestMapping(value = "/userLogin")
	public ModelAndView userLogin(ModelAndView modelAndView, String name, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		try {
			token.setRememberMe(true);
			subject.login(token);
			modelAndView.setViewName("/admin/car");
			return modelAndView;
		} catch (Exception e) {
			modelAndView.addObject("loginMsg", "账号密码有误");
			modelAndView.setViewName("login");
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/car/showList")
	public Map<String, Object> carDetail(Integer pageNum, String keyWord) {
		Map<String, Object> car = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("keyWord", keyWord);
		int count = carService.selectAllCount(params);
		List<Car> carList = carService.showList(params);
		car.put("msg", "success");
		car.put("code", 1);
		car.put("count", count);
		car.put("list", carList);
		return car;
	}

	@ResponseBody
	@RequestMapping(value = "/car/addCar", method = RequestMethod.POST)
	public Map<String, Object> addCar(Car car) {
		Map<String, Object> carDetail = new HashMap<String, Object>();
		try {
			carService.insertACar(car);
			carDetail.put("msg", "success");
			carDetail.put("code", 1);
			return carDetail;
		} catch (Exception e) {
			carDetail.put("msg", "false");
			carDetail.put("code", 0);
			return carDetail;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/car/updateCar", method = RequestMethod.POST)
	public Map<String, Object> updateCar(Car car) {
		Map<String, Object> carDetail = new HashMap<String, Object>();
		try {
			carService.updateACar(car);
			carDetail.put("msg", "success");
			carDetail.put("code", 1);
			return carDetail;
		} catch (Exception e) {
			carDetail.put("msg", "false");
			carDetail.put("code", 0);
			return carDetail;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/car/deleteCar", method = RequestMethod.POST)
	public Map<String, Object> deleteCar(Car car) {
		Map<String, Object> carDetail = new HashMap<String, Object>();
		try {
			carService.deleteACar(car);
			carDetail.put("msg", "success");
			carDetail.put("code", 1);
			return carDetail;
		} catch (Exception e) {
			carDetail.put("msg", "false");
			carDetail.put("code", 0);
			return carDetail;
		}
	}
}
