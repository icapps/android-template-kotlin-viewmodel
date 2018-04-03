package com.icapps.template.repository

import com.icapps.template.model.Beer
import com.icapps.template.model.Brewery

/**
 * @author maartenvangiel
 * @version 1
 */
object TestData {

    val breweries = listOf(
            Brewery(1, "Moortgat", "Address line 1", "City 1", "Belgium"),
            Brewery(2, "AB InBev", "Address line 2", "City 2", "The Netherlands"),
            Brewery(3, "De Kroon", "Address line 3", "City 3", "Kazachstan")
    )

    val beers = listOf(
            Beer(1, "Testbier 1", 5, "http://example.com/thumb1.png", "http://example.com/image1.png", breweries[0]),
            Beer(2, "Testbier 2", 4, "http://example.com/thumb2.png", "http://example.com/image2.png", breweries[1]),
            Beer(3, "Testbier 3", 3, "http://example.com/thumb3.png", "http://example.com/image3.png", breweries[2])
    )

}