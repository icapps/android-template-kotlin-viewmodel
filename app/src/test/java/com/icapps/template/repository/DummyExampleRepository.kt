package com.icapps.template.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.icapps.template.model.Beer
import com.icapps.template.model.arch.Resource

/**
 * @author maartenvangiel
 * @version 1
 */
class DummyExampleRepository : ExampleRepository {

    override fun getBeers(): LiveData<Resource<List<Beer>>> {
        val result = MutableLiveData<Resource<List<Beer>>>()
        result.value = Resource.loading()

        Thread {
            Thread.sleep(100)
            result.value = Resource.success(TestData.beers)
        }.start()

        return result
    }

    override fun getBeer(input: Long): LiveData<Resource<Beer?>> {
        val result = MutableLiveData<Resource<Beer?>>()
        result.value = Resource.loading()

        Thread {
            Thread.sleep(100)
            val beer = TestData.beers.find { it.id == input }
            if (beer == null) {
                result.value = Resource.error("Failed to find beer with id $input")
            } else {
                result.value = Resource.success(beer)
            }

        }.start()

        return result
    }

}