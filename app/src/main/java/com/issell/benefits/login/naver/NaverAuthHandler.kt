package com.issell.benefits.login.naver

import android.os.SystemClock
import android.util.Log
import com.issell.benefits.App
import com.issell.benefits.BuildConfig
import com.issell.benefits.main.MainPresenter
import com.issell.benefits.session.SessionManager
import com.issell.benefits.splash.SplashPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NaverAuthHandler {
    private const val BASE_URL = "https://nid.naver.com/"
    private const val PARAM_NAME_CLIENT_ID = "client_id"
    private const val PARAM_NAME_CLIENT_SECRET = "client_secret"
    private const val PARAM_NAME_ACCESS_TOKEN = "access_token"
    private const val PARAM_NAME_REFRESH_TOKEN = "refresh_token"
    private const val PARAM_NAME_GRANT_TYPE = "grant_type"
    private const val PARAM_VALUE_GRANT_TYPE_REFRESH = "refresh_token"
    private const val PARAM_VALUE_GRANT_TYPE_DELETE = "delete"
    private const val TAG = "NaverAuthHandler"
    fun sendRequestToDelete(presenter: MainPresenter) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NaverAuthRequestInterface::class.java)
        val map = HashMap<String, String>()
        map[PARAM_NAME_CLIENT_ID] = BuildConfig.NAVER_CLIENT_ID
        map[PARAM_NAME_CLIENT_SECRET] = BuildConfig.NAVER_CLIENT_SECRET
        map[PARAM_NAME_ACCESS_TOKEN] = App.prefs.accessToken.toString()
        map[PARAM_NAME_GRANT_TYPE] = PARAM_VALUE_GRANT_TYPE_DELETE

        service.requestToDelete(map)
            .enqueue(object : Callback<NaverAuthDeleteResponse> {
                override fun onFailure(call: Call<NaverAuthDeleteResponse>, t: Throwable) {
                    Log.e(TAG, "requestToDelete() is failed.")
                }

                override fun onResponse(
                    call: Call<NaverAuthDeleteResponse>,
                    response: Response<NaverAuthDeleteResponse>
                ) {
                    Log.e(TAG, "${response.body()?.result}")
                    presenter.completeDeletion()
                }
            })
    }


    fun sendRequestToRefresh(presenter: SplashPresenter) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NaverAuthRequestInterface::class.java)
        val map = HashMap<String, String>()
        map[PARAM_NAME_CLIENT_ID] = BuildConfig.NAVER_CLIENT_ID
        map[PARAM_NAME_CLIENT_SECRET] = BuildConfig.NAVER_CLIENT_SECRET
        map[PARAM_NAME_REFRESH_TOKEN] = App.prefs.refreshToken.toString()
        map[PARAM_NAME_GRANT_TYPE] = PARAM_VALUE_GRANT_TYPE_REFRESH

        service.requestToRefresh(map)
            .enqueue(object : Callback<NaverAuthRefreshResponse> {
                override fun onFailure(call: Call<NaverAuthRefreshResponse>, t: Throwable) {
                    presenter.alertAuthError()
                }

                override fun onResponse(
                    call: Call<NaverAuthRefreshResponse>,
                    response: Response<NaverAuthRefreshResponse>
                ) {

                    val res = response.body()
                    if (res == null) {
                        presenter.alertAuthError()
                        return
                    }
                    Log.i(SplashPresenter.TAG, "access_token = ${NaverAuthRefreshResponse.accessToken}")
                    Log.i(SplashPresenter.TAG, "token_type = ${NaverAuthRefreshResponse.tokenType}")
                    Log.i(SplashPresenter.TAG, "expires_in = ${NaverAuthRefreshResponse.expiresIn}")

                    SessionManager.Instance.accessToken = res.accessToken
                    SessionManager.Instance.authType = SessionManager.AUTH_TYPE_NAVER
                    SessionManager.Instance.beginAt = SystemClock.currentThreadTimeMillis()
                    SessionManager.Instance.expireAt =
                        SessionManager.Instance.beginAt + 3600 * 1000
                    // ** res.tokenType 은 "bearer"

                    // Prefs 업데이트
                    presenter.onFinishRefresh()
                }
            })
    }
}