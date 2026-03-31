package com.example.ipadress.navigation

sealed class AppDestination(val route: String) {
    data object Home : AppDestination("home")
    data object ClientForm : AppDestination("client_form?id={id}") {
        fun createRoute(id: Long = 0L) = "client_form?id=$id"
    }
    data object ClientDetail : AppDestination("client/{clientId}") {
        fun createRoute(clientId: Long) = "client/$clientId"
    }
    data object DepartmentForm : AppDestination("client/{clientId}/department_form?id={id}") {
        fun createRoute(clientId: Long, id: Long = 0L) = "client/$clientId/department_form?id=$id"
    }
    data object DepartmentDetail : AppDestination("client/{clientId}/department/{departmentId}") {
        fun createRoute(clientId: Long, departmentId: Long) = "client/$clientId/department/$departmentId"
    }
    data object DeviceForm : AppDestination("department/{departmentId}/device_form?id={id}") {
        fun createRoute(departmentId: Long, id: Long = 0L) = "department/$departmentId/device_form?id=$id"
    }
    data object DeviceDetail : AppDestination("department/{departmentId}/device/{deviceId}") {
        fun createRoute(departmentId: Long, deviceId: Long) = "department/$departmentId/device/$deviceId"
    }
    data object PcForm : AppDestination("department/{departmentId}/pc_form?id={id}") {
        fun createRoute(departmentId: Long, id: Long = 0L) = "department/$departmentId/pc_form?id=$id"
    }
    data object PcDetail : AppDestination("department/{departmentId}/pc/{pcId}") {
        fun createRoute(departmentId: Long, pcId: Long) = "department/$departmentId/pc/$pcId"
    }
}
