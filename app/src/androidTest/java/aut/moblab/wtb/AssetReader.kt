package aut.moblab.wtb

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStreamReader

object AssetReader {

    fun getResponse(fileName: String): String {
        try {
            val inputStream =
                InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "There was an exception!"
    }

}
