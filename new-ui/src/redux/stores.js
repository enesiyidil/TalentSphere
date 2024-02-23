import { configureStore } from '@reduxjs/toolkit'
import { userReducer } from './reducers'

const defaultState = {
    role: "",
    token: "",
    authId: 0,
    switchAccount: false,
    search: "",
    userProfile: {
        name: "",
        photo: "",
        surname: "",
        email: "",
        phone: "",
        title: "",
        personals: [],
    },
    data: {
        name: "",
        companyId: 0,
        address: "",
        gallery: ["https://picsum.photos/200/300", "https://picsum.photos/300/300"],
        payments: [{
            id: "",
            amount: 0,
            createdDate: 0,
            dueDate: 0,
            paymentDate: 0,
            updatedDate: 0,
            description: "",
            type: ""
        }],
        personals: [{
            id: 0,
            shiftId: 0,
            name: "",
            surname: "",
            email: "",
            phone: "",
            title: "",
            photo: "",
            salary: 0,
            holidaysNumber: 0,
            createdDate: new Date,
            updatedDate: new Date,
        }],
        communications: [{id: 0, name: "", phoneNumber: ""}],
        holidays: [{
            name: "Ramazan Bayramı",
            startDate: new Date,
            endDate: new Date,
            description: "",
            personals: [],
        }],
        shifts: [{
            id: 0,
            name: "",
            startTime: new Date,
            endTime: new Date,
            breaks: [{id: 0, name: "", startTime: new Date, endTime: new Date}]
        }],
        createdDateTime: new Date,
        updatedDateTime: new Date,
    }
}

export const store = configureStore({
    reducer: userReducer,
    preloadedState: defaultState
})