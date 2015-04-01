package com.gedeng.client.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.gedeng.client.adaptor.QXNetwork.*;
import com.gedeng.client.adaptor.QXNetwork.Messages.QxPushManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.gedeng.client.ClientError;
import com.gedeng.client.GDClient;
import com.gedeng.client.connector.params.*;
import com.gedeng.client.entity.*;
import com.gedeng.client.util.BitmapConvertor;
import com.gedeng.client.util.JsonDecoder;
import com.gedeng.client.util.TelephoneDirectory;

class Worker {

	private static final String URL_LOGIN = "/accounts/login";
	private static final String URL_LOGOUT = "/accounts/logout";
	private static final String URL_GET_USER_BY_TELEPHONE = "/accounts/info/byphone";
	private static final String URL_GET_USER_BY_ID = "/accounts/info/{user_id}";
	private static final String URL_GET_USERS_BY_IDS = "/accounts/info/batch";
	private static final String URL_GET_FOLLOWS = "/friendships/friends";
	private static final String URL_CREATE_FOLLOWSHIP = "/friendships/create";
	private static final String URL_DESTROY_FOLLOWSHIP = "/friendships/delete";
	private static final String URL_CHECK_FOLLOWSHIP = "/friendships/check";
	private static final String URL_CHECK_FOLLOWSHIP_BY_IDS = "/friendships/batch/check";
	private static final String URL_CREATE_POST = "/posts/create";
	private static final String URL_GET_POST_BY_ID = "/posts/{post_id}";
	private static final String URL_GET_POSTS = "/posts/square";
	private static final String URL_GET_POSTS_BY_AUTHORID = "/posts/list/byowner";
	private static final String URL_UP_VOTE_POST = "/posts/{post_id}/upvote";
	private static final String URL_DOWN_VOTE_POST = "/posts/{post_id}/downvote";
	private static final String URL_UPDATE_INFO = "/accounts/info/{user_id}/update";
	private static final String URL_GENERATE_CAPTCHA = "/captcha/send";
	private static final String URL_VERIFY_CAPTCHA = "/captcha/verify";
	private static final String URL_SIGNUP = "/accounts/signup";
	private static final String URL_GET_USERS_BY_TELEPHONES = "/accounts/batch/check/byphone";
	private static final String URL_CREATE_COMMENT = "/posts/{post_id}/comments/create";
	private static final String URL_GET_COMMENTS = "/posts/{post_id}/comments";
	private static final String URL_DELETE_POST = "/posts/{post_id}/delete";
	private static final String URL_DELETE_COMMENT = "/posts/comments/{comment_id}/delete";
	private static final String URL_FEEDBACK = "/feedback/create";
	private static final String URL_CHECK_VOTES = "/posts/batch/votes/check";
	
	protected Worker() {
	}

	protected static Worker mWorker = null;

	public static synchronized Worker getInstance() {
		if (mWorker == null) {
			initialize();
		}
		return mWorker;
	};

	public static synchronized Worker initialize() {
		mWorker = new Worker();
		return mWorker;
	}

	protected WorkerReturn dispatchWork(Param param) {
		if (param == null) {
			return null;
		}
		WorkerReturn workerReturn = null;
		switch (param.getApiType()) {
		case Param.PARAM_LOGIN:
			workerReturn = login((LoginParam) param);
			break;
		case Param.PARAM_LOGOUT:
			workerReturn = logout((LogoutParam) param);
			break;
		case Param.PARAM_GET_USER_BY_TELEPHONE:
			workerReturn = getUserByTelephone((GetUserByTelephoneParam) param);
			break;
		case Param.PARAM_GET_USER_BY_ID:
			workerReturn = getUserById((GetUserByIdParam) param);
			break;
		case Param.PARAM_GET_FOLLOWINGS:
			workerReturn = getFollowings((GetFollowingsParam) param);
			break;
		case Param.PARAM_CREATE_FOLLOWSHIP:
			workerReturn = createFollowship((CreateFollowshipParam) param);
			break;
		case Param.PARAM_DESTROY_FOLLOWSHIP:
			workerReturn = destroyFollowship((DestroyFollowshipParam) param);
			break;
		case Param.PARAM_CHECK_FOLLOWSHIP:
			workerReturn = checkFollowship((CheckFollowshipParam) param);
			break;
		case Param.PARAM_CREATE_POST:
			workerReturn = createPost((CreatePostParam) param);
			break;
		case Param.PARAM_GET_POST_BY_ID:
			workerReturn = getPostById((GetPostByIdParam) param);
			break;
		case Param.PARAM_GET_POSTS:
			workerReturn = getPosts((GetPostsParam) param);
			break;
		case Param.PARAM_GET_POSTS_BY_AUTHORID:
			workerReturn = getPostsByAuthorId((GetPostsByAuthorIdParam) param);
			break;
		case Param.PARAM_UP_VOTE_POST:
			workerReturn = upVotePost((UpVotePostParam) param);
			break;
		case Param.PARAM_DOWN_VOTE_POST:
			workerReturn = downVotePost((DownVotePostParam) param);
			break;
		case Param.PARAM_SEND_CAPTCHA:
			workerReturn = sendCaptcha((SendCaptchaParam) param);
			break;
		case Param.PARAM_VERIFY_CAPTCHA:
			workerReturn = verifyCaptcha((VerifyCaptchaParam) param);
			break;
		case Param.PARAM_SIGNUP:
			workerReturn = signup((SignupParam) param);
			break;
		case Param.PARAM_UPDATE_NICKNAME:
			workerReturn = updateNickname((UpdateNicknameParam) param);
			break;
		case Param.PARAM_UPDATE_PASSWORD:
			workerReturn = updatePassword((UpdatePasswordParam) param);
			break;
		case Param.PARAM_UPDATE_TELEPHONE:
			workerReturn = updateTelephone((UpdateTelephoneParam) param);
			break;
		case Param.PARAM_UPDATE_DESCRIPTION:
			workerReturn = updateDescription((UpdateDescriptionParam) param);
			break;
		case Param.PARAM_UPDATE_GENDER:
			workerReturn = updateGender((UpdateGenderParam) param);
			break;
		case Param.PARAM_RESET_PASSWORD:
			workerReturn = resetPassword((ResetPasswordParam) param);
			break;
		case Param.PARAM_UPDATE_PORTRAIT:
			workerReturn = updatePortrait((UpdatePortraitParam) param);
			break;
		case Param.PARAM_CREATE_COMMENT:
			workerReturn = createComment((CreateCommentParam) param);
			break;
		case Param.PARAM_GET_COMMENTS_BY_POSTID:
			workerReturn = getCommentsByPostId((GetCommentsByPostIdParam) param);
			break;
		case Param.PARAM_GET_CONTACTS:
			workerReturn = getContacts((GetContactsParam)param);
			break;
		case Param.PARAM_GET_IMAGE_BY_URL:
			workerReturn = getImageByUrl((GetImageByUrlParam)param);
			break;
		case Param.PARAM_DELETE_POST_BY_ID:
			workerReturn = deletePostById((DeletePostByIdParam)param);
			break;
		case Param.PARAM_DELETE_COMMENT_BY_ID:
			workerReturn = deleteCommentById((DeleteCommentByIdParam)param);
			break;
		case Param.PARAM_FEEDBACK:
			workerReturn = feedback((FeedbackParam)param);
			break;
		default:
			workerReturn = new WorkerReturn(-1);
			workerReturn.setReturn(ClientError.ERROR_API_NOT_EXIST);
			break;
		}
		return workerReturn;
	}

	

	protected WorkerReturn login(LoginParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("phone_number", param.getUsername());
			requestData.put("password", param.getPassword());
			QxHttpPostRequest loginRequest = new QxHttpPostRequest(URL_LOGIN,
					requestData);
			QxHttpResponse loginResponse = QxHttpClient.post(loginRequest);
			if (loginResponse == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (loginResponse.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				User nowUser = JsonDecoder.parseUser(loginResponse
						.getBodyObject());
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(nowUser);
				GDClient.getInstance().login(nowUser, param.getUsername(),param.getPassword());
				QxPushManager.bind(nowUser.getId());
			} else {
				ret.setReturn(loginResponse.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn logout(LogoutParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		QxHttpPostRequest request = new QxHttpPostRequest(URL_LOGOUT, null);
		QxHttpResponse response = QxHttpClient.post(request);
		if (response == null) {
			ret.setReturn(ClientError.ERROR_HTTP);
		} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
			ret.setReturn(GDClient.SUCCESS_FLAG);
			GDClient.getInstance().logout();
			QxPushManager.unbind();
		} else {
			ret.setReturn(response.getRet());
		}

		return ret;
	}

	protected WorkerReturn getUserByTelephone(GetUserByTelephoneParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("phone_number", param.getTelephone());
			QxHttpGetRequest request = new QxHttpGetRequest(
					URL_GET_USER_BY_TELEPHONE, requestData);
			QxHttpResponse response = QxHttpClient.get(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				User nowUser = JsonDecoder.parseUser(response.getBodyObject());
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(nowUser);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn getUserById(GetUserByIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpGetRequest request = new QxHttpGetRequest(
					URL_GET_USER_BY_ID.replace("{user_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.get(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				User nowUser = JsonDecoder.parseUser(response.getBodyObject());
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(nowUser);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn getFollowings(GetFollowingsParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("uid", GDClient.getInstance().getCurrentUser().getId());
				QxHttpGetRequest request = new QxHttpGetRequest(URL_GET_FOLLOWS,
						requestData);
				QxHttpResponse response = QxHttpClient.get(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					JSONArray userJsonArray = response.getBodyArray();
					User[] users = new User[userJsonArray.length()];
					for (int i = 0; i < userJsonArray.length(); i++) {
						users[i] = JsonDecoder.parseUser(userJsonArray
								.getJSONObject(i));
					}
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(users);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn createFollowship(CreateFollowshipParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("uid", GDClient.getInstance().getCurrentUser().getId());
				requestData.put("target_uid", param.getId());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_CREATE_FOLLOWSHIP, requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn destroyFollowship(DestroyFollowshipParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("uid", GDClient.getInstance().getCurrentUser().getId());
				requestData.put("target_uid", param.getId());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_DESTROY_FOLLOWSHIP, requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn checkFollowship(CheckFollowshipParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("uid", GDClient.getInstance().getCurrentUser().getId());
				requestData.put("target_uid", param.getId());
				QxHttpGetRequest request = new QxHttpGetRequest(
						URL_CHECK_FOLLOWSHIP, requestData);
				QxHttpResponse response = QxHttpClient.get(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setData(response.getBodyInt());
					ret.setReturn(GDClient.SUCCESS_FLAG);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn createPost(CreatePostParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("text", param.getText());
			requestData.put("type", param.getType());
			requestData.put("tag", new JSONArray());
			requestData.getJSONArray("tag").put(param.getTag());
			JSONArray imageArray = new JSONArray();
			Bitmap[] images = param.getImages();
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					imageArray.put(BitmapConvertor.Bitmap2String(images[i]));
				}
			}
			requestData.put("image", imageArray);
			Post nowPost = null;
			QxHttpPostRequest request = new QxHttpPostRequest(URL_CREATE_POST,
					requestData);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				nowPost = JsonDecoder.parsePost(response.getBodyObject());
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(nowPost);
			} else {
				ret.setReturn(response.getRet());
			}
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				GetUserByIdParam userParam = new GetUserByIdParam();
				userParam.setId(response.getBodyObject().getInt("owner_uid"));
				WorkerReturn userRet = getUserById(userParam);
				if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
					nowPost.setAuthor((User)(userRet.getData()));
				}
				else {
					ret.setReturn(userRet.getReturn());
					ret.setData(null);
				}
			}
			
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn getPostById(GetPostByIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpGetRequest request = new QxHttpGetRequest(
					URL_GET_POST_BY_ID.replace("{post_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.get(request);
			Post nowPost = null;
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				nowPost = JsonDecoder.parsePost(response.getBodyObject());
				if (nowPost == null) {
					ret.setReturn(ClientError.ERROR_CONTENT_NOT_EXIST);
				}
				else {
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(nowPost);
				}
			} else {
				ret.setReturn(response.getRet());
			}
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				GetUserByIdParam userParam = new GetUserByIdParam();
				userParam.setId(response.getBodyObject().getInt("owner_uid"));
				WorkerReturn userRet = getUserById(userParam);
				if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
					nowPost.setAuthor((User)(userRet.getData()));
				}
				else {
					ret.setReturn(userRet.getReturn());
					ret.setData(null);
				}
			}
			
			
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn getPosts(GetPostsParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("count", param.getCount());
			if (param.getMinId() > 0) {
				requestData.put("after_id", param.getMinId());
			}
			if (param.getMaxId() > 0) {
				requestData.put("before_id", param.getMaxId());
			}
			QxHttpGetRequest request = new QxHttpGetRequest(URL_GET_POSTS, requestData);
			QxHttpResponse response = QxHttpClient.get(request);
			ArrayList<Post> posts = null;
			JSONArray postJsonArray = null;
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				postJsonArray = response.getBodyArray();
				posts = new ArrayList<Post>();
				for (int i = 0; i < postJsonArray.length(); i++) {
					Post one = JsonDecoder.parsePost(postJsonArray
							.getJSONObject(i));
					if (one != null) {
						posts.add(one);
					}
				}
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
			
			StringBuilder postIdsBuilder = new StringBuilder();
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				String ids = JsonDecoder.getArrayElements(response.getBodyArray(),"owner_uid");
				WorkerReturn userRet = getUserByIds(ids);
				if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
					SparseArray<User> userMap = (SparseArray<User>)userRet.getData();
					for (Post post:posts) {
						post.setAuthor(userMap.get(post.getSource().getInt("owner_uid")));
						if (postIdsBuilder.length() > 0) {
							postIdsBuilder.append(',');
						}
						postIdsBuilder.append(post.getId()+"");
					}
					Post[] postsArray = new Post[posts.size()];
					posts.toArray(postsArray);
					ret.setData(postsArray);
				}
				else {
					ret.setReturn(userRet.getReturn());
					ret.setData(null);
				}
			}
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				if (GDClient.getInstance().loginStatus() != GDClient.LOGINSTATUS_NOT_LOGIN && postIdsBuilder.length() > 0) {
						
					WorkerReturn checkRet = checkVotes(GDClient.getInstance().getCurrentUser().getId()+"",postIdsBuilder.toString());
					if (checkRet.getReturn() == GDClient.SUCCESS_FLAG) {
						int[] check = (int[])checkRet.getData();
						int i=  0 ;
						for (Post post:posts) {
							post.setVoted(check[i++] == 0);
						}
					}
				}
			}
			
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}

		return ret;
	}

	protected WorkerReturn getPostsByAuthorId(GetPostsByAuthorIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("uid", param.getId());
			requestData.put("count", param.getCount());
			if (param.getMinId() >= 0) {
				requestData.put("after_id", param.getMinId());
			}
			if (param.getMaxId() >= 0) {
				requestData.put("before_id", param.getMaxId());
			}
			QxHttpGetRequest request = new QxHttpGetRequest(
					URL_GET_POSTS_BY_AUTHORID, requestData);
			QxHttpResponse response = QxHttpClient.get(request);
			ArrayList<Post> posts = null;
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				JSONArray postJsonArray = response.getBodyArray();
				posts = new ArrayList<Post>();
				for (int i = 0; i < postJsonArray.length(); i++) {
					Post one = JsonDecoder.parsePost(postJsonArray
							.getJSONObject(i));
					if (one != null) {
						posts.add(one);
					}
				}
				ret.setReturn(GDClient.SUCCESS_FLAG);
				
			} else {
				ret.setReturn(response.getRet());
			}
			
			StringBuilder postIdsBuilder = new StringBuilder();
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				GetUserByIdParam userParam = new GetUserByIdParam();
				userParam.setId(param.getId());
				WorkerReturn userRet = getUserById(userParam);
				if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
					for(Post post:posts){
						post.setAuthor((User)(userRet.getData()));
						if (postIdsBuilder.length() > 0) {
							postIdsBuilder.append(',');
						}
						postIdsBuilder.append(post.getId()+"");
					}
					Post[] postsArray = new Post[posts.size()];
					posts.toArray(postsArray);
					ret.setData(postsArray);
				}
				else {
					ret.setReturn(userRet.getReturn());
					ret.setData(null);
				}
			}
			
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				if (GDClient.getInstance().loginStatus() != GDClient.LOGINSTATUS_NOT_LOGIN && postIdsBuilder.length() > 0) {
						
					WorkerReturn checkRet = checkVotes(GDClient.getInstance().getCurrentUser().getId()+"",postIdsBuilder.toString());
					if (checkRet.getReturn() == GDClient.SUCCESS_FLAG) {
						int[] check = (int[])checkRet.getData();
						int i=  0 ;
						for (Post post:posts) {
							post.setVoted(check[i++] == 0);
						}
					}
				}
			}

		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}

		return ret;
	}

	protected WorkerReturn upVotePost(UpVotePostParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_UP_VOTE_POST.replace("{post_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}

		return ret;
	}
	
	protected WorkerReturn downVotePost(DownVotePostParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_DOWN_VOTE_POST.replace("{post_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}

		return ret;
	}

	protected WorkerReturn sendCaptcha(SendCaptchaParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("phone_number", param.getTelephone());
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_GENERATE_CAPTCHA, requestData);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn verifyCaptcha(VerifyCaptchaParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("phone_number", param.getTelephone());
			requestData.put("captcha", param.getCaptcha());
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_VERIFY_CAPTCHA, requestData);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(1);
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn signup(SignupParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("phone_number", param.getTelephone());
			requestData.put("password", param.getPassword());
			QxHttpPostRequest request = new QxHttpPostRequest(URL_SIGNUP,
					requestData);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				User nowUser = JsonDecoder.parseUser(response
						.getBodyObject());
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(nowUser);
				GDClient.getInstance().login(nowUser, param.getTelephone(),param.getPassword());
				
			} else {
				ret.setReturn(response.getRet());
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}

	protected WorkerReturn updateNickname(UpdateNicknameParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("nickname", param.getNickname());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					User nowUser = JsonDecoder.parseUser(response.getBodyObject());
					GDClient.getInstance().setUser(nowUser);
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(nowUser);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn updatePassword(UpdatePasswordParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("password_old", param.getOldPassword());
				requestData.put("password_new", param.getNewPassword());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
					GDClient.getInstance().setPassword(param.getNewPassword());
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn updateTelephone(UpdateTelephoneParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("phone_number_new", param.getNewTelephone());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
					GDClient.getInstance().setUsername(param.getNewTelephone());
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn updateDescription(UpdateDescriptionParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("description", param.getDescription());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					User nowUser = JsonDecoder.parseUser(response.getBodyObject());
					GDClient.getInstance().setUser(nowUser);
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(nowUser);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (Exception e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
	protected WorkerReturn updateGender(UpdateGenderParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("gender", param.getGender());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					User nowUser = JsonDecoder.parseUser(response.getBodyObject());
					GDClient.getInstance().setUser(nowUser);
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(nowUser);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (Exception e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
	protected WorkerReturn resetPassword(ResetPasswordParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("password_reset", param.getPassword());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}

	protected WorkerReturn updatePortrait(UpdatePortraitParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("avatar",
						BitmapConvertor.Bitmap2String(param.getPortrait()));
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_UPDATE_INFO.replace("{user_id}", GDClient
								.getInstance().getCurrentUser().getId() + ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					User nowUser = JsonDecoder.parseUser(response.getBodyObject());
					GDClient.getInstance().setUser(nowUser);
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(nowUser);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
	protected WorkerReturn createComment(CreateCommentParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		if (GDClient.getInstance().getCurrentUser() == null) {
			ret.setReturn(ClientError.ERROR_NOT_LOGIN);
		} else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("text",param.getText());
				requestData.put("target_uid",param.getTargetUserId());
				QxHttpPostRequest request = new QxHttpPostRequest(
						URL_CREATE_COMMENT.replace("{post_id}",param.getPostId()+ ""), requestData);
				QxHttpResponse response = QxHttpClient.post(request);
				Comment comment = null;
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					comment = JsonDecoder.parseComment(response.getBodyObject());
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(comment);
				} else {
					ret.setReturn(response.getRet());
				}
				
				
				if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					GetUserByIdParam userParam = new GetUserByIdParam();
					userParam.setId(response.getBodyObject().getInt("author_uid"));
					WorkerReturn userRet = getUserById(userParam);
					if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
						comment.setAuthor((User)(userRet.getData()));
					}
					else {
						ret.setReturn(userRet.getReturn());
						ret.setData(null);
					}
				}
				
				if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					GetUserByIdParam userParam = new GetUserByIdParam();
					userParam.setId(response.getBodyObject().getInt("target_uid"));
					WorkerReturn userRet = getUserById(userParam);
					if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
						comment.setTargetUser((User)(userRet.getData()));
					}
					else {
						ret.setReturn(userRet.getReturn());
						ret.setData(null);
					}
				}
				
			} catch (JSONException e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
	protected WorkerReturn getCommentsByPostId(GetCommentsByPostIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("count", param.getCount());
			if (param.getMinId() > 0) {
				requestData.put("after_id", param.getMinId());
			}
			if (param.getMaxId() > 0) {
				requestData.put("before_id", param.getMaxId());
			}
			QxHttpGetRequest request = new QxHttpGetRequest(URL_GET_COMMENTS.replace("{post_id}",param.getId()+""), requestData);
			QxHttpResponse response = QxHttpClient.get(request);
			ArrayList<Comment> comments = null;
			JSONArray commentJsonArray = null;
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				commentJsonArray = response.getBodyArray();
				comments = new ArrayList<Comment>();
				for (int i = 0; i < commentJsonArray.length(); i++) {
					Comment one = JsonDecoder.parseComment(commentJsonArray
							.getJSONObject(i));
					if (one != null) {
						comments.add(one);
					}
				}
				ret.setReturn(GDClient.SUCCESS_FLAG);
				
			} else {
				ret.setReturn(response.getRet());
			}
			
			
			if (response != null && response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				String ids = JsonDecoder.getArrayElements(response.getBodyArray(),"target_uid")
						+ "," +JsonDecoder.getArrayElements(response.getBodyArray(),"author_uid");
				WorkerReturn userRet = getUserByIds(ids);
				if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
					SparseArray<User> userMap = (SparseArray<User>)userRet.getData();
					for (Comment comment:comments) {
						comment.setTargetUser(userMap.get(comment.getSource().getInt("target_uid")));
						comment.setAuthor(userMap.get(comment.getSource().getInt("author_uid")));
					}
					Comment[] commentsArray = new Comment[comments.size()];
					comments.toArray(commentsArray);
					ret.setData(commentsArray);
				}
				else {
					ret.setReturn(userRet.getReturn());
					ret.setData(null);
				}
			}
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
	
		return ret;
	}

	protected WorkerReturn getContacts(
			GetContactsParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());

		try {
			HashMap<String,ArrayList<String>> map = TelephoneDirectory.getMobileAll(GDClient.getInstance().getContext().getContentResolver());
			Contact[] contacts = TelephoneDirectory.convert2Contects(map);
			if (contacts == null) {
				ret.setReturn(ClientError.ERROR_TELEPHONEDIR);
			}
			else {
				StringBuilder numbers = new StringBuilder();
				for (int i = 0 ; i < contacts.length ; i++) {
					if (i != 0) {
						numbers.append(',');
					}
					numbers.append(contacts[i].getTelephone());
				}
				
				JSONObject requestData = new JSONObject();
				requestData.put("phone_numbers",
						numbers.substring(0, numbers.length() - 2));
				QxHttpGetRequest request = new QxHttpGetRequest(
						URL_GET_USERS_BY_TELEPHONES, requestData);
				QxHttpResponse response = QxHttpClient.get(request);
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					ret.setReturn(GDClient.SUCCESS_FLAG);
				} else {
					ret.setReturn(response.getRet());
				}
				
				JSONArray uidArray = null;
				StringBuilder uidBuf = null;
				if (ret.getReturn() == GDClient.SUCCESS_FLAG) {
					uidArray = response.getBodyArray();
					uidBuf = new StringBuilder();
					for (int i = 0 ; i < uidArray.length() ; i++) {
						if (uidArray.getInt(i) > 0) {
							if (uidBuf.length() > 0) {
								uidBuf.append(',');
							}
							uidBuf.append(uidArray.getInt(i));
						}
					}
					if (uidBuf.length() > 0) {
						WorkerReturn userRet = getUserByIds(uidBuf.toString());
						if (userRet.getReturn() == GDClient.SUCCESS_FLAG) {
							SparseArray<User> userMap = (SparseArray<User>)userRet.getData();
							for (int i = 0 ; i < contacts.length ; i++) {
								if (uidArray.getInt(i) > 0) {
									contacts[i].setBindUser(userMap.get(uidArray.getInt(i)));
								}
							}
						}
						else {
							ret.setReturn(userRet.getReturn());
							ret.setData(null);
						}
					}
					else {
						return ret;
					}
				}
				
				//step 3 get following status
				if (ret.getReturn() == GDClient.SUCCESS_FLAG) {
					JSONObject requestDataStep3 = new JSONObject();
					requestDataStep3.put("uid", GDClient.getInstance().getCurrentUser().getId());
					requestDataStep3.put("target_uids", uidBuf.toString());
					QxHttpGetRequest requestStep3 = new QxHttpGetRequest(
							URL_CHECK_FOLLOWSHIP_BY_IDS, requestDataStep3);
					QxHttpResponse responseStep3 = QxHttpClient.get(requestStep3);
					if (responseStep3 == null) {
						ret.setReturn(ClientError.ERROR_HTTP);
					} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
						JSONArray followshipArray = responseStep3.getBodyArray();
						int j = 0;
						for (int i = 0 ; i < contacts.length ; i++) {
							if (contacts[i].getBindUser() != null && followshipArray.getInt(j++) == 1) {
								contacts[i].setIsFollowed(true);
							}
						}
					} else {
						ret.setReturn(response.getRet());
					}
				}
			}
			
		} catch (JSONException e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}

		return ret;
	}
	
	protected WorkerReturn getImageByUrl(GetImageByUrlParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
	        Bitmap bitmap = QxHttpClient.getImageByUrl(param.getUrl());
			if (bitmap == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else {
				ret.setReturn(GDClient.SUCCESS_FLAG);
				ret.setData(bitmap);
			}
				
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}
	protected WorkerReturn feedback(FeedbackParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("text", param.getText());
			if (param.getContact() != null) {
				requestData.put("contact", param.getContact());
			} else if (GDClient.getInstance().loginStatus() == GDClient.LOGINSTATUS_LOGIN) {
				requestData.put("contact", GDClient.getInstance().getUsername());
			}
			QxHttpPostRequest request = new QxHttpPostRequest(URL_FEEDBACK, requestData);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			}
				
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}
	
	protected WorkerReturn deletePostById(DeletePostByIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_DELETE_POST.replace("{post_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
				
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}
	
	protected WorkerReturn deleteCommentById(DeleteCommentByIdParam param) {
		WorkerReturn ret = new WorkerReturn(param.getApiType());
		try {
			QxHttpPostRequest request = new QxHttpPostRequest(
					URL_DELETE_COMMENT.replace("{comment_id}", param.getId() + ""),
					null);
			QxHttpResponse response = QxHttpClient.post(request);
			if (response == null) {
				ret.setReturn(ClientError.ERROR_HTTP);
			} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
				ret.setReturn(GDClient.SUCCESS_FLAG);
			} else {
				ret.setReturn(response.getRet());
			}
				
		} catch (Exception e) {
			ret.setReturn(ClientError.ERROR_JSON);
		}
		return ret;
	}
	
	
	protected WorkerReturn getUserByIds(String ids) {
		WorkerReturn ret = new WorkerReturn(Param.PARAM_INTERNAL);
		if (ids == null || ids.length() == 0) {
			ret.setData(new SparseArray<User>());
			ret.setReturn(GDClient.SUCCESS_FLAG);
		}
		else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("uids", ids);
				QxHttpGetRequest request = new QxHttpGetRequest(URL_GET_USERS_BY_IDS, requestData);
				QxHttpResponse response = QxHttpClient.get(request);
				
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					JSONArray userJsonArray = response.getBodyArray();
					SparseArray<User> userMap = new SparseArray<User>();
					for (int i = 0; i < userJsonArray.length(); i++) {
						User user = JsonDecoder.parseUser(userJsonArray
								.getJSONObject(i));
						userMap.put(user.getId(), user);
					}
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(userMap);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (Exception e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
	protected WorkerReturn checkVotes(String uid,String ids) {
		WorkerReturn ret = new WorkerReturn(Param.PARAM_INTERNAL);
		if (ids == null || ids.length() == 0) {
			ret.setData(new int[0]);
			ret.setReturn(GDClient.SUCCESS_FLAG);
		}
		else {
			try {
				JSONObject requestData = new JSONObject();
				requestData.put("post_ids", ids);
				requestData.put("uid",uid);
				QxHttpGetRequest request = new QxHttpGetRequest(URL_CHECK_VOTES, requestData);
				QxHttpResponse response = QxHttpClient.get(request);
				
				if (response == null) {
					ret.setReturn(ClientError.ERROR_HTTP);
				} else if (response.getRet() == QxHttpUtil.RESPONSE_SUCCESS) {
					JSONArray checkJsonArray = response.getBodyArray();
					int[] check = new int[checkJsonArray.length()];
					for (int i = 0; i < checkJsonArray.length(); i++) {
						check[i] = checkJsonArray.getInt(i);
					}
					ret.setReturn(GDClient.SUCCESS_FLAG);
					ret.setData(check);
				} else {
					ret.setReturn(response.getRet());
				}
			} catch (Exception e) {
				ret.setReturn(ClientError.ERROR_JSON);
			}
		}
		return ret;
	}
	
}
