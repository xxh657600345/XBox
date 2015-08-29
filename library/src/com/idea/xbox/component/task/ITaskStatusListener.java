package com.idea.xbox.component.task;

public interface ITaskStatusListener {
	/**
	 * 任务改变通知监听
	 * 
	 * @param task
	 *            任务详情
	 */
	public void onChangeStatus(Task task);
}
