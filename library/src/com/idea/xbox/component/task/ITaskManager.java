package com.idea.xbox.component.task;

import java.util.List;

import android.os.Handler;

import com.idea.xbox.framework.beans.ICreatedable;

public interface ITaskManager extends ICreatedable {

	/**
	 * 创建任务
	 * 
	 * @param task
	 *            任务对象
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void createTask(ITask task) throws TaskException;

	/**
	 * 通过ID�?��某个任务
	 * 
	 * @param id
	 *            任务id
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void startTask(long id) throws TaskException;

	/**
	 * 通过ID重新�?��某个任务
	 * 
	 * @param id
	 *            任务id
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void restartTask(long id) throws TaskException;

	/**
	 * 通过ID停止某个任务
	 * 
	 * @param id
	 *            任务id
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void stopTask(long id) throws TaskException;

	/**
	 * 通过ID删除某个任务
	 * 
	 * @param id
	 *            任务id
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void deleteTask(long id) throws TaskException;

	/**
	 * 通过ID寻找某个任务
	 * 
	 * @param id
	 *            任务id
	 * @return 任务对象
	 */
	public ITask findTaskById(long id);

	/**
	 * �?���?��任务
	 * 
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void startAllTask() throws TaskException;

	/**
	 * 停止�?��任务
	 * 
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void stopAllTask() throws TaskException;

	/**
	 * 删除�?��任务
	 * 
	 * @throws TaskException
	 *             自定义装载器异常
	 */
	public void deleteAllTask() throws TaskException;

	/**
	 * 重新恢复下次没做完的任务
	 */
	public void startLastTasks();

	/**
	 * 获取可见的后台线�?
	 * 
	 * @return List<ITask> 获取可见的后台线�?
	 */
	public List<ITask> getDisplayTasks();

	/**
	 * 获取可见的后台线程的个数
	 * 
	 * @return int
	 */
	public int getDisplayTaskAmount();

	/**
	 * 添加消息句柄
	 * 
	 * @param handler
	 *            Handler 消息句柄
	 */
	public void addHandler(Handler handler);

	/**
	 * 移除消息句柄
	 * 
	 * @param handler
	 *            Handler 消息句柄
	 */
	public void removeHandler(Handler handler);

	/**
	 * 给任务加入一些特定的监听�?
	 * 
	 * @param taskStatusListener
	 *            ITaskStatusListener 监听�?
	 */
	public void addTaskStatusListener(ITaskStatusListener taskStatusListener);

	/**
	 * 移除加入的一些监听�?
	 * 
	 * @param taskStatusListener
	 *            ITaskStatusListener 监听�?
	 */
	public void removeTaskStatusListener(ITaskStatusListener taskStatusListener);

}
