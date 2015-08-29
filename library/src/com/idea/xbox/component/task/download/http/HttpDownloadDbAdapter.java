package com.idea.xbox.component.task.download.http;

import com.idea.xbox.component.task.Task;
import com.idea.xbox.component.task.download.DownloadTaskDbAdapter;
import com.idea.xbox.component.task.download.DownloadTaskInfo;

public class HttpDownloadDbAdapter extends DownloadTaskDbAdapter {

	protected Task copyToTask(Object object) throws Exception {
		DownloadTaskInfo downloadTaskInfo = (DownloadTaskInfo) object;
		HttpDownloadTask httpDownloadTask = new HttpDownloadTask();
		httpDownloadTask.setDownloadTaskInfo(downloadTaskInfo);
		return httpDownloadTask;
	}

	@Override
	public Class<?> getTaskClass() {
		return HttpDownloadTask.class;
	}

}
