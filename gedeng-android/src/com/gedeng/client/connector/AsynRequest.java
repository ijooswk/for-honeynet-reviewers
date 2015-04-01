package com.gedeng.client.connector;

import android.os.AsyncTask;

import com.gedeng.client.connector.params.Param;

class AsynRequest extends AsyncTask<Void, Integer, Integer>{

	private CallbackInterface callback;
	private Param param;
	private Response response;
	private int retryTimes;
	private int retrySpan;
	
	public AsynRequest(Param param ,Callback callback,int retryTimes, int retrySpan) {
		this.param = param;
		this.callback = callback;
		this.response = null;
	}
	
	@Override
	protected Integer doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		int retryTimesLocal = retryTimes;
		WorkerReturn ret = Worker.getInstance().dispatchWork(param);
		while((retryTimesLocal != 0) && (ret == null || ret.getReturn() != 0)) {
			if (ret != null && ErrorHandler.isFatal(ret)) {
				break;
			}
			try {
				wait(retrySpan);
			}
			catch (Exception e) {}
			retryTimesLocal --;
			publishProgress(retryTimes - retryTimesLocal);
			ret = Worker.getInstance().dispatchWork(param);
		}
		if (ErrorHandler.isFatal(ret)) {
			return ret.getReturn();
		}
		else {
			//TODO:debug parse response
			response = new Response();
			response.setData(ret.getData());
			return 0;
		}
		
	}
	
	@Override
    protected void onProgressUpdate(Integer... values) {
        callback.onRetrying(values[0]);
	}
	
	@Override
    protected void onPostExecute(Integer result) {
		if (result != 0) {
			callback.onFailed(result, ErrorHandler.translate(result));
		}
		else {
			callback.onSuccess(response);
		}
        callback.onComplete();
    }

}
