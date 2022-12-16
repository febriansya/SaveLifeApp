package com.example.savelifeapp.data.model

import com.google.firebase.Timestamp

data class HistoryDonors(
    var jenisDonor: String? = "",
    var noKantong: String? = "",
    var tanggalDonor: String? = "",
    var timeStamp: Timestamp? = Timestamp.now(),
)



