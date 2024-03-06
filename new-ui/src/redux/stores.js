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
        gender: "",
        packet: "",
        updatedDateTime: "",
        createdDateTime: "",
    },
    data: {
        name: "",
        companyId: 0,
        address: "",
        gallery: [],
        payments: [{
            id: "",
            amount: 0,
            companyId: 0,
            authId: 0,
            requestDate: "",
            approvalDate: "",
            dueDate: "",
            paymentDate: "",
            updatedDate: "",
            description: "",
            expenditureType: "",
            status: "",
            role: "",
            currency: "",
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
            comment: [0],
            createdDate: "",
            updatedDate: "",
        }],
        communications: [{id: 0, name: "", phoneNumber: ""}],
        holidays: [{
            name: "",
            startDate: "",
            endDate: "",
            description: "",
            personals: [],
        }],
        shifts: [{
            id: 0,
            name: "",
            startTime: "",
            endTime: "",
            breaks: [{id: 0, name: "", startTime: "", endTime: ""}]
        }],
        createdDateTime: "",
        updatedDateTime: "",
    }
}

export const store = configureStore({
    reducer: userReducer,
    preloadedState: defaultState
})