package com.idea.xbox.component.task.download.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpStatus;

import com.idea.xbox.common.util.FileUtil;
import com.idea.xbox.common.util.StringUtil;
import com.idea.xbox.component.logger.Logger;
import com.idea.xbox.component.task.Task;
import com.idea.xbox.component.task.TaskException;
import com.idea.xbox.component.task.driver.ITaskDriver;

public class HttpDownloadDriver implements ITaskDriver {
	/**
	 * TAG
	 */
	private static final String TAG = "HttpDownloadDriver";

	/**
	 * 默认的缓冲区大小
	 */
	private static final int BYTE_LENGTH = 102400;

	/**
	 * 进度更新时间
	 */
	private static final int REPORT_TIME = 1000;

	/**
	 * 进度更新时间
	 */
	private static final int SLEEP_TIME = 500;

	/**
	 * http协议的下载任务对�?
	 */
	private HttpDownloadTask mDownloadTask = null;

	/**
	 * 读取�?
	 */
	private InputStream is = null;

	/**
	 * http网络连接对象
	 */
	private HttpURLConnection conn = null;

	/**
	 * 构�?�?
	 * 
	 * @param downloadTask
	 *            DownloadHttpTask
	 */
	public HttpDownloadDriver(HttpDownloadTask downloadTask) {
		this.mDownloadTask = downloadTask;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect() throws TaskException {
		DataOutputStream os = null;
		long length;
		long curSize = mDownloadTask.getCurrentSize();
		try {
			URL url = new URL(mDownloadTask.getDownloadUrl());

			conn = (HttpURLConnection) url.openConnection();
			if (mDownloadTask.isProxy()) {
				conn = (HttpURLConnection) url
						.openConnection(new Proxy(Proxy.Type.HTTP,
								new InetSocketAddress(mDownloadTask
										.getProxyHost(), mDownloadTask
										.getProxyPort())));
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			// 不使用Cache
			conn.setUseCaches(false);
			conn.setConnectTimeout(mDownloadTask.getTimeout());
			conn.setReadTimeout(mDownloadTask.getTimeout());
			// 设置请求类型
			if (mDownloadTask.isPost()) {
				conn.setRequestMethod("POST");
			} else {
				conn.setRequestMethod("GET");
			}
			if (curSize > 0) {
				conn.addRequestProperty("RANGE", "bytes=" + curSize + "-");
			}
			Map<String, String> header = mDownloadTask.getHeaders();
			for (String key : header.keySet()) {
				String value = header.get(key);
				conn.addRequestProperty(key, value);
			}
			mDownloadTask.getHeaders().clear();
			// 以内容实体方式发送请求参�?
			byte[] postBuf = mDownloadTask.getPostBuf();
			length = postBuf != null ? postBuf.length : curSize;
			if (mDownloadTask.isPost() && length > 0) {
				// 发�?POST请求必须设置允许输出
				conn.setDoOutput(true);
				// 维持长连�?
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Length",
						String.valueOf(length));
				conn.setRequestProperty("Content-Type",
						"application/octet-stream");

				// �?��写入数据
				os = new DataOutputStream(conn.getOutputStream());
				os.write(postBuf);
				os.flush();
			}
		} catch (IOException ex) {
			Logger.w(TAG, ex.getMessage(), ex);
			throw new TaskException(TaskException.SERVER_CONNECT_FAILED);
		} finally {
			// 关流
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Logger.w(TAG, e.getMessage(), e);
				}
			}
		}
		try {
			int responseCode = conn.getResponseCode();
			if (responseCode != HttpStatus.SC_OK
					&& responseCode != HttpStatus.SC_PARTIAL_CONTENT) {
				Logger.e(TAG, "HTTP response error code : " + responseCode);
				throw new TaskException(TaskException.SERVER_CONNECT_FAILED);
			}
			long hSize = 0L;
			// 从返回头中获取返回长�?
			String value = conn.getHeaderField("Content-Length");
			if (value != null && value.length() > 0) {
				try {
					hSize = Long.parseLong(value);
					Logger.i(TAG, "Content-Length : " + value);
				} catch (Exception e) {
					Logger.w(TAG, e.getMessage(), e);
				}
			} else {
				String contentRange = conn.getHeaderField("content-range");
				Logger.i(TAG, "content-range : " + contentRange);
				if (null != contentRange) {
					hSize = Long
							.parseLong(StringUtil.split(contentRange, "/")[1])
							- mDownloadTask.getCurrentSize();
				}
			}
			long cSize = conn.getContentLength();

			// 从流中获取返回长�?
			is = conn.getInputStream();

			Logger.i(TAG, "connect size : " + cSize + ", content length : "
					+ hSize);

			cSize = cSize > hSize ? cSize : hSize;
			cSize += curSize;
			mDownloadTask.setTotalSize(cSize);
			Logger.i(TAG, "totalSize=" + cSize);
		} catch (IOException ex) {
			Logger.w(TAG, ex.getMessage(), ex);
			throw new TaskException(TaskException.SERVER_CONNECT_FAILED);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read() throws TaskException {
		if (mDownloadTask.getStorePath() != null) {
			readDownloadFile();
		} else {
			readBytes();
		}
	}

	/**
	 * 网络读取下载文件
	 * 
	 * @throws TaskException
	 */
	private void readDownloadFile() throws TaskException {

		long currentSize = mDownloadTask.getCurrentSize();
		String storePath = mDownloadTask.getStorePath();
		// 存储每次从网络层读取到的数据
		// 临时数据缓冲�?
		int len = 0;
		byte[] buff = new byte[BYTE_LENGTH];
		byte[] bytes = null;
		long time = 0;
		conn.setReadTimeout(mDownloadTask.getTimeout());
		conn.setConnectTimeout(mDownloadTask.getTimeout());
		RandomAccessFile file = null;
		try {
			FileUtil.createFolder(storePath);
			file = new RandomAccessFile(FileUtil.getFileByPath(storePath), "rw");
			while ((mDownloadTask.getAction() != Task.ACTION_STOP && mDownloadTask
					.getAction() != Task.ACTION_DELETE)) {
				try {
					if ((len = is.read(buff)) == -1) {
						break;
					}
				} catch (IOException ex) {
					Logger.e(ex.getMessage(), "readDownloadFile fail", ex);
					throw new TaskException(TaskException.SERVER_CONNECT_FAILED);
				}
				bytes = new byte[len];
				System.arraycopy(buff, 0, bytes, 0, len);

				file.seek(file.length());
				file.write(bytes);
				currentSize += len;
				Logger.i(TAG, "readFileData : currentSize=" + currentSize);
				long currentTime = System.currentTimeMillis();
				if (time == 0 || (currentTime - time > REPORT_TIME)) {
					mDownloadTask.onProgress();
					time = currentTime;
				}
				mDownloadTask.setCurrentSize(currentSize);
				Thread.sleep(SLEEP_TIME);
			}
		} catch (TaskException ex1) {
			throw ex1;
		} catch (IOException ex2) {
			Logger.e(ex2.getMessage(), "write file fail", ex2);
			throw new TaskException(TaskException.WRITE_FILE_FAILED);
		} catch (InterruptedException ex3) {
			Logger.e(ex3.getMessage(), "write file fail", ex3);
			throw new TaskException(TaskException.WRITE_FILE_FAILED);
		} finally {
			try {
				if (null != file) {
					file.close();
				}
			} catch (IOException e) {
				Logger.i(e.getMessage(), "Read download file fail.");
			}
		}

	}

	/**
	 * 网络读取下载字节
	 * 
	 * @throws TaskException
	 */
	private void readBytes() throws TaskException {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			int ch = 0;
			byte[] d = new byte[BYTE_LENGTH];
			while ((ch = is.read(d)) != -1) {
				bos.write(d, 0, ch);
			}
			mDownloadTask.setFileByteBuf(bos.toByteArray());
		} catch (IOException e) {
			Logger.i(e.getMessage(), "Read byte fail.");
			throw new TaskException(TaskException.SERVER_CONNECT_FAILED);
		} finally {
			try {
				if(bos != null){
					bos.close();
				}
			} catch (IOException e) {
				Logger.i(e.getMessage(), "Read byte fail.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		try {
			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			Logger.i("clear net Exception:", e.toString());
		} finally {
			is = null;
		}
		try {
			if (conn != null) {
				conn.disconnect();
			}
		} catch (Exception e) {
			Logger.i("clear net Exception:", e.toString());
		} finally {
			conn = null;
		}
	}

}
