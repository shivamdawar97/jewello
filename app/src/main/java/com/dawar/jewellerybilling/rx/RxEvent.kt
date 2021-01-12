package com.dawar.jewellerybilling.rx

import com.dawar.jewellerybilling.database.entities.Pending

class RxEvent {
    data class PendingRestored(val pending: Pending)
    class ItemAdded
}