package spring.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.demo.dao.CarDao;
import spring.demo.entity.Car;

@Service
public class CarService {

	@Autowired
	private CarDao carDao;

	public List<Car> showList(Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		int pageNum = (Integer) map.get("pageNum");
		String keyWord = (String) map.get("keyWord");
		int start = pageNum * 5 - 5;
		params.put("start", start);
		params.put("keyWord", "%" + keyWord + "%");
		List<Car> carList = carDao.selectCarList(params);
		return carList;
	}
	
	public int selectAllCount(Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		String keyWord = (String) map.get("keyWord");
		params.put("keyWord", "%" + keyWord + "%");
		return carDao.selectCount(params);
	}
	
	public void updateACar(Car car) {
		if (car.getId() != 0) {
			carDao.updateOneCar(car);
		}
	}

	public void insertACar(Car car) {
		if (car.getId() != 0 && car != null) {
			carDao.insertOneCar(car);
		}
	}

	public void deleteACar(Car car) {
		if (car.getId() != 0) {
			carDao.deleteOneCar(car);
		}
	}
}
