package dmzing.workd.network

import dmzing.workd.model.review.PhotoReviewDto
import dmzing.workd.model.review.ReviewCountDto
import dmzing.workd.model.review.SimpleReviewDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    /*FIXME
    * Server 통신에 사용될 함수 정의
    * */

    //리뷰 수 보기
    //edit by 이민형
    @GET("api/reviews/count")
    fun getReviewCounts(
        @Header("jwt") jwt : String
    ) : Call<ArrayList<ReviewCountDto>>

    //사진리뷰 전체보기
    //edit by 이민형
    @GET("api/reviews/photo/last/{pid}/course/{type}")
    fun getPhotoReviews(
        @Header("jwt") jwt : String,
        @Path("pid") pid : Int,
        @Path("type") type : String
    ) : Call<ArrayList<PhotoReviewDto>>

    //리뷰 전체보기
    //edit by 이민형
    @GET("api/reviews/last/{rid}/course/{type}")
    fun getSimpleReviews(
        @Header("jwt") jwt : String,
        @Path("rid") rid : Int,
        @Path("type") type : String
    ) : Call<ArrayList<SimpleReviewDto>>

}