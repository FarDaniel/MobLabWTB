package aut.moblab.wtb.network_data.interactors

import aut.moblab.wtb.network_data.apis.AwardApi
import aut.moblab.wtb.network_data.models.AwardItems
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AwardInteractor(private val api: AwardApi) {
    fun getAwards(movieId: String): Single<AwardItems> {
        return api.getAwards(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}