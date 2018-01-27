package spring.demo.dao;

import java.util.List;
import java.util.Map;

import spring.demo.entity.Car;

public interface CarDao {
	/**
	 * 使用queryById查询单个车信息
	 * 
	 * @param id
	 * @return
	 */
	Car queryById(int id);
	
	int selectCount(Map<String, Object> map);

	/**
	 * 模糊查询汽车的信息
	 * 
	 * @param
	 * @return
	 */
	List<Car> fuzzySearch(String keyWord);

	/**
	 * 使用selectCarList查询一个列表
	 * 
	 * @param
	 * @return
	 */
	List<Car> selectCarList(Map<String, Object> map);

	/**
	 * 使用updateOneCar更新单个车信息
	 * 
	 * @param Car
	 * @return
	 */
	void updateOneCar(Car car);

	/**
	 * 使用insertOneCar插入一个新车
	 * 
	 * @param Car
	 * @return
	 */
	void insertOneCar(Car car);

	/**
	 * 使用deleteOneCar删除一个新车
	 * 
	 * @param Car
	 * @return
	 */
	void deleteOneCar(Car car);

}
